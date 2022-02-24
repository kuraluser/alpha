/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.discharge_plan.DischargingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.repository.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class DischargingSequenceService {
  @Autowired DischargeInformationRepository dischargingInformationRepository;
  @Autowired DischargingSequenceRepository dischargingSequenceRepository;
  @Autowired DeballastingRateRepository deBallastingRateRepository;
  @Autowired CargoDischargingRateRepository cargoLoadingRateRepository;
  @Autowired BallastOperationRepository ballastOperationRepository;
  @Autowired DischargingPlanPortWiseDetailsRepository portWiseDetailsRepository;
  @Autowired DischargingPlanBallastDetailsRepository ballastDetailsRepository;
  @Autowired DischargingPlanRobDetailsRepository robDetailsRepository;
  @Autowired DischargingPlanStabilityParametersRepository stabilityParametersRepository;
  @Autowired DischargingPlanStowageDetailsRepository stowageDetailsRepository;
  @Autowired EductionOperationRepository eductionOperationRepository;
  @Autowired CowTankDetailRepository cowTankDetailRepository;

  @Autowired
  DischargingSequenceStabiltyParametersRepository dischargingSequenceStabilityParamsRepository;

  @Autowired DischargingPlanCommingleDetailsRepository commingleDetailsRepository;

  @GrpcClient("loadableStudyService")
  LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @Autowired DischargingDriveTankRepository dischargingDriveTankRepository;

  @Autowired DischargingTankTransferRepository dischargingTankTransferRepository;

  /**
   * Get Sequences
   *
   * @param request
   * @param builder
   * @throws Exception
   */
  public void getDischargingSequences(
      LoadingSequenceRequest request, DischargeSequenceReply.Builder builder) throws Exception {
    log.info(
        "Fetching Discharging sequences of discharging information {}", request.getLoadingInfoId());
    Optional<DischargeInformation> dischargingInfoOpt =
        dischargingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInfoId());
    if (dischargingInfoOpt.isEmpty()) {
      log.info("Cannot find discharging information with id {}", request.getLoadingInfoId());
      throw new GenericServiceException(
          "Could not find discharging information " + request.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    String startDate = this.getStartDate(dischargingInfoOpt.get().getPortRotationXid());
    builder.setStartDate(startDate);
    builder.setInterval(dischargingInfoOpt.get().getDischargingStagesDuration().getDuration());
    builder.setVesselId(dischargingInfoOpt.get().getVesselXid());
    builder.setVoyageId(dischargingInfoOpt.get().getVoyageXid());
    builder.setPortId(dischargingInfoOpt.get().getPortXid());
    builder.setDischargePatternId(dischargingInfoOpt.get().getDischargingPatternXid());
    builder.setPortRotationId(dischargingInfoOpt.get().getPortRotationXid());
    List<com.cpdss.dischargeplan.entity.DischargingSequence> dischargingSequences =
        dischargingSequenceRepository.findByDischargeInformationAndIsActiveOrderBySequenceNumber(
            dischargingInfoOpt.get(), true);
    buildDischargingSequences(dischargingSequences, builder);

    List<DischargingSequenceStabilityParameters> stabilityParameters =
        dischargingSequenceStabilityParamsRepository
            .findByDischargingInformationAndIsActiveOrderByTime(dischargingInfoOpt.get(), true);
    buildDischargingSequenceStabilityParameters(stabilityParameters, builder);
    List<CowTankDetail> cowTankDetails =
        cowTankDetailRepository.findByDischargingXidAndIsActiveTrue(
            dischargingInfoOpt.get().getId());
    buildCowTankDetails(cowTankDetails, builder);
    List<DischargingDriveTank> dischargingDriveTanks =
        dischargingDriveTankRepository.findByDischargingInformationAndIsActiveTrue(
            dischargingInfoOpt.get());
    buildDriveTankDetails(dischargingDriveTanks, builder);
  }

  /**
   * Builds Drive tank details message
   *
   * @param dischargingDriveTanks
   * @param builder
   */
  private void buildDriveTankDetails(
      List<DischargingDriveTank> dischargingDriveTanks, DischargeSequenceReply.Builder builder) {
    log.info("Building Drive tank details");
    List<DriveTankDetail> driveTankDetailList = new ArrayList<>();
    dischargingDriveTanks.forEach(
        dischargingDriveTank -> {
          DriveTankDetail.Builder tankBuilder = DriveTankDetail.newBuilder();
          Optional.ofNullable(dischargingDriveTank.getTankId()).ifPresent(tankBuilder::setTankId);
          Optional.ofNullable(dischargingDriveTank.getEndTime())
              .ifPresent(endTime -> tankBuilder.setTimeEnd(endTime.toString()));
          Optional.ofNullable(dischargingDriveTank.getStartTime())
              .ifPresent(startTime -> tankBuilder.setTimeStart(startTime.toString()));
          Optional.ofNullable(dischargingDriveTank.getTankShortName())
              .ifPresent(tankBuilder::setTankShortName);
          driveTankDetailList.add(tankBuilder.build());
        });
    builder.addAllDriveTankDetails(driveTankDetailList);
  }

  /**
   * Builds cow tank details message
   *
   * @param cowTankDetails
   * @param builder
   */
  private void buildCowTankDetails(
      List<CowTankDetail> cowTankDetails, DischargeSequenceReply.Builder builder) {
    log.info("Populating COW tank details");
    CleaningTanks.Builder cleaningTanksBuilder = CleaningTanks.newBuilder();
    List<CleaningTankDetails> topTanks = new ArrayList<CleaningTankDetails>();
    List<CleaningTankDetails> bottomTanks = new ArrayList<CleaningTankDetails>();
    List<CleaningTankDetails> fullTanks = new ArrayList<CleaningTankDetails>();
    cowTankDetails.forEach(
        cowTankDetail -> {
          CleaningTankDetails.Builder tankDetailsBuilder = CleaningTankDetails.newBuilder();
          Optional.ofNullable(cowTankDetail.getTankXid()).ifPresent(tankDetailsBuilder::setTankId);
          Optional.ofNullable(cowTankDetail.getTimeEnd())
              .ifPresent(timeEnd -> tankDetailsBuilder.setTimeEnd(timeEnd.toString()));
          Optional.ofNullable(cowTankDetail.getTimeStart())
              .ifPresent(timeStart -> tankDetailsBuilder.setTimeStart(timeStart.toString()));
          Optional.ofNullable(cowTankDetail.getTankShortName())
              .ifPresent(tankDetailsBuilder::setTankShortName);
          switch (cowTankDetail.getCowTypeXid()) {
            case Common.COW_TYPE.ALL_COW_VALUE:
              fullTanks.add(tankDetailsBuilder.build());
              break;
            case Common.COW_TYPE.TOP_COW_VALUE:
              topTanks.add(tankDetailsBuilder.build());
              break;
            case Common.COW_TYPE.BOTTOM_COW_VALUE:
              bottomTanks.add(tankDetailsBuilder.build());
              break;
            default:
              log.error("Unidentified COW type discovered while populating COW tanks");
              break;
          }
        });
    cleaningTanksBuilder.addAllBottomTank(bottomTanks);
    cleaningTanksBuilder.addAllFullTank(fullTanks);
    cleaningTanksBuilder.addAllTopTank(topTanks);
    builder.setCleaningTanks(cleaningTanksBuilder.build());
  }

  private void buildDischargingSequenceStabilityParameters(
      List<DischargingSequenceStabilityParameters> stabilityParameters,
      DischargeSequenceReply.Builder builder) {
    log.info("Populating Loading sequence stability parameters");
    stabilityParameters.forEach(
        param -> {
          LoadingPlanStabilityParameters.Builder paramBuilder =
              LoadingPlanStabilityParameters.newBuilder();
          Optional.ofNullable(param.getAftDraft())
              .ifPresent(aftDraft -> paramBuilder.setAftDraft(String.valueOf(aftDraft)));
          Optional.ofNullable(param.getBendingMoment())
              .ifPresent(bm -> paramBuilder.setBm(String.valueOf(bm)));
          Optional.ofNullable(param.getForeDraft())
              .ifPresent(foreDraft -> paramBuilder.setForeDraft(String.valueOf(foreDraft)));
          Optional.ofNullable(param.getShearingForce())
              .ifPresent(sf -> paramBuilder.setSf(String.valueOf(sf)));
          Optional.ofNullable(param.getTrim())
              .ifPresent(trim -> paramBuilder.setTrim(String.valueOf(trim)));
          Optional.ofNullable(param.getList())
              .ifPresent(list -> paramBuilder.setList(String.valueOf(list)));
          Optional.ofNullable(param.getTime()).ifPresent(paramBuilder::setTime);
          Optional.ofNullable(param.getGomValue())
              .ifPresent(value -> paramBuilder.setGomValue(value.toString()));
          Optional.ofNullable(param.getBmFrameNumber())
              .ifPresent(bmFrameNo -> paramBuilder.setBmFrameNumber(bmFrameNo.toString()));
          Optional.ofNullable(param.getSfFrameNumber())
              .ifPresent(sfFrameNo -> paramBuilder.setSfFrameNumber(sfFrameNo.toString()));
          builder.addDischargeSequenceStabilityParameters(paramBuilder.build());
        });
  }

  private String getStartDate(Long portRotationXId) throws Exception {
    log.info("Fetching port rotation details with id {} from loadable study MS", portRotationXId);
    PortRotationRequest.Builder builder = PortRotationRequest.newBuilder();
    builder.setId(portRotationXId);
    PortRotationDetailReply reply =
        loadableStudyGrpcService.getLoadableStudyPortRotationByPortRotationId(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
      throw new Exception("Could not find port rotation with id " + portRotationXId);
    }
    return reply.getPortRotationDetail().getEta();
  }

  private void buildDischargingSequences(
      List<com.cpdss.dischargeplan.entity.DischargingSequence> dischargeSequences,
      DischargeSequenceReply.Builder builder) {

    List<Long> dischargeSequencesIds =
        dischargeSequences.stream()
            .map(com.cpdss.dischargeplan.entity.DischargingSequence::getId)
            .collect(Collectors.toList());

    List<DeballastingRate> deBallastingRates =
        deBallastingRateRepository.findByDischargingSequenceInAndIsActiveTrueOrderById(
            dischargeSequencesIds);
    List<CargoDischargingRate> cargoDischargeRates =
        cargoLoadingRateRepository.findByDischargingSequenceInAndIsActiveTrueOrderById(
            dischargeSequencesIds);
    List<BallastOperation> ballastOperations =
        ballastOperationRepository.findByDischargingSequenceInAndIsActiveTrueOrderById(
            dischargeSequencesIds);
    List<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetails =
        portWiseDetailsRepository.findByDischargingSequenceInAndIsActiveTrueOrderById(
            dischargeSequencesIds);
    List<EductionOperation> eductionOperations =
        eductionOperationRepository.findByDischargingSequenceInAndIsActiveTrue(
            dischargeSequencesIds);
    List<DischargingTankTransfer> dischargingTankTransfers =
        dischargingTankTransferRepository.findByDischargingSequenceInAndIsActiveTrue(
            dischargeSequencesIds);

    dischargeSequences.forEach(
        dischargeSequence -> {
          com.cpdss.common.generated.discharge_plan.DischargingSequence.Builder sequenceBuilder =
              com.cpdss.common.generated.discharge_plan.DischargingSequence.newBuilder();
          Optional.ofNullable(dischargeSequence.getCargoDischargingRate1())
              .ifPresent(rate1 -> sequenceBuilder.setCargoDischargingRate1(String.valueOf(rate1)));
          Optional.ofNullable(dischargeSequence.getCargoDischargingRate2())
              .ifPresent(rate2 -> sequenceBuilder.setCargoDischargingRate2(String.valueOf(rate2)));
          Optional.ofNullable(dischargeSequence.getCargoNominationXId())
              .ifPresent(sequenceBuilder::setCargoNominationId);
          Optional.ofNullable(dischargeSequence.getEndTime())
              .ifPresent(sequenceBuilder::setEndTime);
          Optional.ofNullable(dischargeSequence.getPortXId()).ifPresent(sequenceBuilder::setPortId);
          Optional.ofNullable(dischargeSequence.getSequenceNumber())
              .ifPresent(sequenceBuilder::setSequenceNumber);
          Optional.ofNullable(dischargeSequence.getStageName())
              .ifPresent(sequenceBuilder::setStageName);
          Optional.ofNullable(dischargeSequence.getStartTime())
              .ifPresent(sequenceBuilder::setStartTime);
          Optional.ofNullable(dischargeSequence.getToLoadicator())
              .ifPresent(sequenceBuilder::setToLoadicator);

          List<DeballastingRate> ballastingRates = new ArrayList<>();
          deBallastingRates.forEach(
              rate -> {
                if (rate.getDischargingSequenceId().equals(dischargeSequence.getId()))
                  ballastingRates.add(rate);
              });
          buildDeBallastingRates(sequenceBuilder, ballastingRates);

          List<CargoDischargingRate> dischargeRates = new ArrayList<>();
          cargoDischargeRates.forEach(
              rate -> {
                if (rate.getDischargingSequenceId().equals(dischargeSequence.getId()))
                  dischargeRates.add(rate);
              });
          buildCargoLoadingRates(sequenceBuilder, dischargeRates);

          // Includes both ballast and cargo pump details here
          List<BallastOperation> operations = new ArrayList<>();
          ballastOperations.forEach(
              rate -> {
                if (rate.getDischargingSequenceId().equals(dischargeSequence.getId()))
                  operations.add(rate);
              });
          buildBallastOperations(sequenceBuilder, operations);

          List<DischargingPlanPortWiseDetails> planPortWiseDetails = new ArrayList<>();
          dischargingPlanPortWiseDetails.forEach(
              portDetails -> {
                if (portDetails.getDischargingSequenceId().equals(dischargeSequence.getId()))
                  planPortWiseDetails.add(portDetails);
              });
          buildDischargingPlanPortWiseDetails(sequenceBuilder, planPortWiseDetails);

          List<EductionOperation> operationList = new ArrayList<>();
          eductionOperations.forEach(
              rate -> {
                if (rate.getDischargingSequenceId().equals(dischargeSequence.getId()))
                  operationList.add(rate);
              });
          buildEductionOperations(sequenceBuilder, operationList);

          List<DischargingTankTransfer> tankTransfers = new ArrayList<>();
          dischargingTankTransfers.forEach(
              rate -> {
                if (rate.getDischargingSequenceId().equals(dischargeSequence.getId()))
                  tankTransfers.add(rate);
              });
          buildTankTransfers(sequenceBuilder, tankTransfers);
          builder.addDischargeSequences(sequenceBuilder.build());
        });
  }

  /**
   * Builds Tank Transfer details
   *
   * @param sequenceBuilder
   * @param dischargingTankTransfers
   */
  private void buildTankTransfers(
      DischargingSequence.Builder sequenceBuilder,
      List<DischargingTankTransfer> dischargingTankTransfers) {
    log.info("Populating Discharging Tank Transfers");
    List<TankTransfer> tankTransfers = new ArrayList<>();
    dischargingTankTransfers.forEach(
        dischargingTankTransfer -> {
          TankTransfer.Builder builder = TankTransfer.newBuilder();
          Optional.ofNullable(dischargingTankTransfer.getCargoNominationId())
              .ifPresent(builder::setCargoNominationId);
          Optional.ofNullable(dischargingTankTransfer.getToTankId())
              .ifPresent(builder::setToTankId);
          builder.addAllFromTankIds(
              StringUtils.hasLength(dischargingTankTransfer.getFromTankIds())
                  ? Arrays.asList(dischargingTankTransfer.getFromTankIds().split(",")).stream()
                      .map(tankId -> Long.valueOf(tankId))
                      .collect(Collectors.toList())
                  : new ArrayList<Long>());
          Optional.ofNullable(dischargingTankTransfer.getTimeEnd()).ifPresent(builder::setTimeEnd);
          Optional.ofNullable(dischargingTankTransfer.getTimeStart())
              .ifPresent(builder::setTimeStart);
          Optional.ofNullable(dischargingTankTransfer.getPurpose()).ifPresent(builder::setPurpose);
          if (dischargingTankTransfer.getDischargingTankTransferDetails() != null) {
            dischargingTankTransfer
                .getDischargingTankTransferDetails()
                .forEach(
                    dischargingTankTransferDetails -> {
                      TankTransferDetail.Builder detailBuilder = TankTransferDetail.newBuilder();
                      Optional.ofNullable(dischargingTankTransferDetails.getTankXId())
                          .ifPresent(detailBuilder::setTankId);
                      Optional.ofNullable(dischargingTankTransferDetails.getEndQuantity())
                          .ifPresent(
                              endQuantity -> detailBuilder.setEndQuantity(endQuantity.toString()));
                      Optional.ofNullable(dischargingTankTransferDetails.getEndUllage())
                          .ifPresent(endUllage -> detailBuilder.setEndUllage(endUllage.toString()));
                      Optional.ofNullable(dischargingTankTransferDetails.getStartQuantity())
                          .ifPresent(
                              startQuantity ->
                                  detailBuilder.setStartQuantity(startQuantity.toString()));
                      Optional.ofNullable(dischargingTankTransferDetails.getStartUllage())
                          .ifPresent(
                              startUllage -> detailBuilder.setStartUllage(startUllage.toString()));
                      builder.addTankTransferDetails(detailBuilder.build());
                    });
          }
          tankTransfers.add(builder.build());
        });
    sequenceBuilder.addAllTankTransfers(tankTransfers);
  }

  /**
   * @param sequenceBuilder
   * @param eductionOperations
   */
  private void buildEductionOperations(
      DischargingSequence.Builder sequenceBuilder, List<EductionOperation> eductionOperations) {
    List<EductorOperation> eductorOperations = new ArrayList<EductorOperation>();
    eductionOperations.forEach(
        eductionOperation -> {
          log.info(
              "Populating eduction operation, tanks: {}, pumps: {}",
              eductionOperation.getTanksUsed(),
              eductionOperation.getEductorsUsed());
          EductorOperation.Builder builder = EductorOperation.newBuilder();
          Optional.ofNullable(eductionOperation.getEductorsUsed())
              .ifPresent(builder::setEductorPumpsUsed);
          Optional.ofNullable(eductionOperation.getEndTime()).ifPresent(builder::setEndTime);
          Optional.ofNullable(eductionOperation.getId()).ifPresent(builder::setId);
          Optional.ofNullable(eductionOperation.getStartTime()).ifPresent(builder::setStartTime);
          Optional.ofNullable(eductionOperation.getTanksUsed()).ifPresent(builder::setTanksUsed);
          eductorOperations.add(builder.build());
        });
    sequenceBuilder.addAllEductorOperation(eductorOperations);
  }

  private void buildBallastOperations(
      DischargingSequence.Builder sequenceBuilder, List<BallastOperation> ballastOperations) {
    log.info("Populating Ballast Operations");
    ballastOperations.forEach(
        ballastOp -> {
          PumpOperation.Builder builder = PumpOperation.newBuilder();
          Optional.ofNullable(ballastOp.getEndTime()).ifPresent(builder::setEndTime);
          Optional.ofNullable(ballastOp.getPumpName()).ifPresent(builder::setPumpName);
          Optional.ofNullable(ballastOp.getPumpXId()).ifPresent(builder::setPumpXId);
          Optional.ofNullable(ballastOp.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(ballastOp.getRate())
              .ifPresent(rate -> builder.setRate(String.valueOf(rate)));
          Optional.ofNullable(ballastOp.getStartTime()).ifPresent(builder::setStartTime);
          sequenceBuilder.addBallastOperations(builder.build());
        });
  }

  private void buildDischargingPlanPortWiseDetails(
      DischargingSequence.Builder sequenceBuilder,
      List<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetails) {
    log.info("Populating Portwise details");
    List<Long> dischargingPlanPortWiseDetailsIds =
        dischargingPlanPortWiseDetails.stream()
            .map(DischargingPlanPortWiseDetails::getId)
            .collect(Collectors.toList());

    List<DeballastingRate> deBallastingRates =
        deBallastingRateRepository.findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(
            dischargingPlanPortWiseDetailsIds);
    List<DischargingPlanBallastDetails> ballastDetails =
        ballastDetailsRepository.findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(
            dischargingPlanPortWiseDetailsIds);
    List<DischargingPlanRobDetails> robDetails =
        robDetailsRepository.findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(
            dischargingPlanPortWiseDetailsIds);
    List<DischargingPlanStabilityParameters> stabilityParameters =
        stabilityParametersRepository.findByDischargingPlanPortWiseDetailsInAndIsActiveTrue(
            dischargingPlanPortWiseDetailsIds);
    List<DischargingPlanCommingleDetails> commingleDetails =
        commingleDetailsRepository.findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(
            dischargingPlanPortWiseDetailsIds);
    List<DischargingPlanStowageDetails> stowageDetails =
        stowageDetailsRepository.findByDischargingPlanPortWiseDetailsInAndIsActiveTrueOrderById(
            dischargingPlanPortWiseDetailsIds);

    dischargingPlanPortWiseDetails.forEach(
        portWiseDetails -> {
          DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder =
              DischargePlanPortWiseDetails.newBuilder();
          Optional.ofNullable(portWiseDetails.getTime()).ifPresent(portWiseDetailsBuilder::setTime);

          List<DeballastingRate> deBallastingRates1 = new ArrayList<>();
          deBallastingRates.forEach(
              rate -> {
                if (rate.getDischargingPlanPortWiseDetailsId().equals(portWiseDetails.getId()))
                  deBallastingRates1.add(rate);
              });
          buildDeBallastingRates(portWiseDetailsBuilder, deBallastingRates1);

          List<DischargingPlanBallastDetails> ballastDetails1 = new ArrayList<>();
          ballastDetails.forEach(
              ballast -> {
                if (ballast.getDischargingPlanPortWiseDetailsId().equals(portWiseDetails.getId()))
                  ballastDetails1.add(ballast);
              });
          buildBallastDetails(portWiseDetailsBuilder, ballastDetails1);

          List<DischargingPlanRobDetails> robDetails1 = new ArrayList<>();
          robDetails.forEach(
              rob -> {
                if (rob.getDischargingPlanPortWiseDetailsId().equals(portWiseDetails.getId()))
                  robDetails1.add(rob);
              });
          buildRobDetails(portWiseDetailsBuilder, robDetails1);

          Optional<DischargingPlanStabilityParameters> stabilityParametersOpt = Optional.empty();
          stabilityParametersOpt =
              stabilityParameters.stream()
                  .filter(
                      stabilityparam ->
                          stabilityparam
                              .getDischargingPlanPortWiseDetailsId()
                              .equals(portWiseDetails.getId()))
                  .findAny();
          buildStabilityParams(portWiseDetailsBuilder, stabilityParametersOpt);

          List<DischargingPlanCommingleDetails> commingleDetails1 = new ArrayList<>();
          commingleDetails.forEach(
              commingle -> {
                if (commingle.getDischargingPlanPortWiseDetailsId().equals(portWiseDetails.getId()))
                  commingleDetails1.add(commingle);
              });
          buildCommingleDetails(portWiseDetailsBuilder, commingleDetails1);

          List<DischargingPlanStowageDetails> stowageDetails1 = new ArrayList<>();
          stowageDetails.forEach(
              stowage -> {
                if (stowage.getDischargingPlanPortWiseDetailsId().equals(portWiseDetails.getId()))
                  stowageDetails1.add(stowage);
              });
          buildStowageDetails(portWiseDetailsBuilder, stowageDetails1);
          sequenceBuilder.addDischargePlanPortWiseDetails(portWiseDetailsBuilder.build());
        });
  }

  private void buildCommingleDetails(
      DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
      List<DischargingPlanCommingleDetails> commingleDetails) {
    commingleDetails.forEach(
        commingle -> {
          LoadingPlanCommingleDetails.Builder builder = LoadingPlanCommingleDetails.newBuilder();
          Optional.ofNullable(commingle.getAbbreviation()).ifPresent(builder::setAbbreviation);
          Optional.ofNullable(commingle.getApi()).ifPresent(api -> builder.setApi(api.toString()));
          Optional.ofNullable(commingle.getCargo1XId()).ifPresent(builder::setCargo1Id);
          Optional.ofNullable(commingle.getCargo2XId()).ifPresent(builder::setCargo2Id);
          Optional.ofNullable(commingle.getCargoNomination1XId())
              .ifPresent(builder::setCargoNomination1Id);
          Optional.ofNullable(commingle.getCargoNomination2XId())
              .ifPresent(builder::setCargoNomination2Id);
          Optional.ofNullable(commingle.getId()).ifPresent(builder::setId);
          Optional.ofNullable(commingle.getQuantity())
              .ifPresent(quantity -> builder.setQuantityMT(quantity.toString()));
          Optional.ofNullable(commingle.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(quantityM3.toString()));
          Optional.ofNullable(commingle.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(commingle.getTemperature())
              .ifPresent(temperature -> builder.setTemperature(temperature.toString()));
          Optional.ofNullable(commingle.getUllage())
              .ifPresent(ullage -> builder.setUllage(ullage.toString()));
          portWiseDetailsBuilder.addDischargingPlanCommingleDetails(builder.build());
        });
  }

  private void buildStowageDetails(
      DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
      List<DischargingPlanStowageDetails> dischargePlanStowageDetails) {
    dischargePlanStowageDetails.forEach(
        stowage -> {
          LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
          Optional.ofNullable(stowage.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(stowage.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(stowage.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(stowage.getApi())
              .ifPresent(api -> builder.setApi(String.valueOf(api)));
          Optional.ofNullable(stowage.getCargoNominationId())
              .ifPresent(builder::setCargoNominationId);
          Optional.ofNullable(stowage.getTemperature())
              .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));
          Optional.ofNullable(stowage.getUllage())
              .ifPresent(ullage -> builder.setUllage(String.valueOf(ullage)));
          Optional.ofNullable(stowage.getAbbreviation()).ifPresent(builder::setAbbreviation);
          Optional.ofNullable(stowage.getColorCode()).ifPresent(builder::setColorCode);
          portWiseDetailsBuilder.addDischargingPlanStowageDetails(builder.build());
        });
  }

  private void buildStabilityParams(
      DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
      Optional<DischargingPlanStabilityParameters> loadingPlanStabilityParameters) {
    LoadingPlanStabilityParameters.Builder builder = LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(loadingPlanStabilityParameters.get().getBm())
        .ifPresent(bm -> builder.setBm(String.valueOf(bm)));
    Optional.ofNullable(loadingPlanStabilityParameters.get().getDraft())
        .ifPresent(draft -> builder.setDraft(String.valueOf(draft)));
    Optional.ofNullable(loadingPlanStabilityParameters.get().getSf())
        .ifPresent(sf -> builder.setSf(String.valueOf(sf)));
    portWiseDetailsBuilder.setDischargingPlanStabilityParameters(builder.build());
  }

  private void buildRobDetails(
      DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
      List<DischargingPlanRobDetails> robDetails) {
    robDetails.forEach(
        rob -> {
          LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
          Optional.ofNullable(rob.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(rob.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(rob.getTankXId()).ifPresent(builder::setTankId);
          portWiseDetailsBuilder.addDischargingPlanRobDetails(builder.build());
        });
  }

  private void buildBallastDetails(
      DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
      List<DischargingPlanBallastDetails> ballastDetails) {
    ballastDetails.forEach(
        ballast -> {
          LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
          Optional.ofNullable(ballast.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(ballast.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(ballast.getSounding())
              .ifPresent(sounding -> builder.setSounding(String.valueOf(sounding)));
          Optional.ofNullable(ballast.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(ballast.getColorCode()).ifPresent(builder::setColorCode);
          portWiseDetailsBuilder.addDischargingPlanBallastDetails(builder.build());
        });
  }

  private void buildDeBallastingRates(
      DischargePlanPortWiseDetails.Builder portwiseBuilder,
      List<DeballastingRate> deballastingRates) {
    deballastingRates.forEach(
        rate -> {
          DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
          Optional.ofNullable(rate.getDeBallastingRate())
              .ifPresent(
                  deBallastingRate ->
                      builder.setDeBallastingRate(String.valueOf(deBallastingRate)));
          Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(rate.getTime()).ifPresent(builder::setTime);
          portwiseBuilder.addDeballastingRates(builder.build());
        });
  }

  private void buildCargoLoadingRates(
      DischargingSequence.Builder sequenceBuilder, List<CargoDischargingRate> cargoLoadingRates) {
    cargoLoadingRates.forEach(
        rate -> {
          DischargingRate.Builder builder = DischargingRate.newBuilder();
          Optional.ofNullable(rate.getDischargingRate())
              .ifPresent(loadingRate -> builder.setDischargingRate(String.valueOf(loadingRate)));
          Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
          builder.setEndTime(sequenceBuilder.getEndTime());
          builder.setStartTime(sequenceBuilder.getStartTime());
          sequenceBuilder.addDischargingRates(builder.build());
        });
  }

  private void buildDeBallastingRates(
      DischargingSequence.Builder sequenceBuilder, List<DeballastingRate> deballastingRates) {
    deballastingRates.forEach(
        rate -> {
          DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
          Optional.ofNullable(rate.getDeBallastingRate())
              .ifPresent(
                  deBallastingRate ->
                      builder.setDeBallastingRate(String.valueOf(deBallastingRate)));
          Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(rate.getTime()).ifPresent(builder::setTime);
          sequenceBuilder.addDeBallastingRates(builder.build());
        });
  }

  /**
   * Fetches the discharging hours of the given discharge pattern id and port rotation id
   *
   * @param request request
   * @param builder message builder
   */
  public void getDischargingHours(LoadingHoursRequest request, LoadingHoursReply.Builder builder)
      throws GenericServiceException {
    List<DischargeInformation> dischargeInformationList =
        dischargingInformationRepository.findByVesselXidAndDischargingPatternXidAndIsActiveTrue(
            request.getVesselId(), request.getLoadingPatternId());
    for (Long portRotationId : request.getPortRotationIdsList()) {
      log.info(
          "Fetching Discharging Hours for discharge pattern {}, port rotation {}",
          request.getLoadingPatternId(),
          portRotationId);
      Optional<DischargeInformation> dischargeInfoOpt =
          dischargeInformationList.stream()
              .filter(
                  loadingInformation ->
                      loadingInformation.getPortRotationXid().equals(portRotationId))
              .findFirst();
      if (dischargeInfoOpt.isEmpty()) {
        log.error(
            "Cannot find discharging information with discharge pattern id {}, port rotation id {}",
            request.getLoadingPatternId(),
            portRotationId);
        throw new GenericServiceException(
            "Could not find discharging information.",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      List<com.cpdss.dischargeplan.entity.DischargingSequence> dischargingSequences =
          dischargingSequenceRepository.findByDischargeInformationAndIsActiveOrderBySequenceNumber(
              dischargeInfoOpt.get(), true);
      if (dischargingSequences.isEmpty()) {
        log.error(
            "Discharging Sequence is not generated for discharging information {}",
            dischargeInfoOpt.get().getId());
      }
      Optional<com.cpdss.dischargeplan.entity.DischargingSequence> lastStageOpt =
          dischargingSequences.stream()
              .max(
                  Comparator.comparing(
                      com.cpdss.dischargeplan.entity.DischargingSequence::getSequenceNumber));
      lastStageOpt.ifPresent(
          dischargingSequence -> {
            LoadingPlanModels.LoadingHours.Builder hoursBuilder =
                LoadingPlanModels.LoadingHours.newBuilder();
            hoursBuilder.setPortRotationId(portRotationId);
            hoursBuilder.setLoadingHours(
                (returnZeroIfNull(dischargingSequence.getEndTime())
                        .divide(new BigDecimal(60), 4, RoundingMode.HALF_EVEN))
                    .toString());
            builder.addDischargingHours(hoursBuilder.build());
          });
    }
  }

  /**
   * Returns BigDecimal value of Integer input if not null. Else returns BigDecimal zero.
   *
   * @param integerValue input value of BigDecimal
   * @return BigDecimal value
   */
  private BigDecimal returnZeroIfNull(Integer integerValue) {
    return integerValue != null ? new BigDecimal(integerValue) : BigDecimal.ZERO;
  }
}
