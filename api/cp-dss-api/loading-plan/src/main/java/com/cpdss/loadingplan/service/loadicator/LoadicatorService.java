/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.Loadicator;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.LoadicatorServiceGrpc;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDetail;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import com.cpdss.loadingplan.entity.LoadingSequenceStabilityParameters;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanPortWiseDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceStabiltyParametersRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
public class LoadicatorService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired LoadingSequenceRepository loadingSequenceRepository;
  @Autowired LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @Autowired LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;

  @Autowired
  LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("loadicatorService")
  private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  public void saveLoadicatorInfo(LoadingInformation loadingInformation, String processId)
      throws GenericServiceException {
    log.info(
        "Sending stowage plans of loading information {} to loadicator",
        loadingInformation.getId());
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    List<LoadingSequence> loadingSequences =
        loadingSequenceRepository.findByLoadingInformationAndIsActiveOrderBySequenceNumber(
            loadingInformation, true);
    Set<Long> cargoNominationIds =
        loadingSequences.stream()
            .map(sequence -> sequence.getCargoNominationXId())
            .collect(Collectors.toSet());
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);

    List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails =
        loadingPlanPortWiseDetailsRepository.findByLoadingInformationIdAndToLoadicatorAndIsActive(
            loadingInformation.getId(), true, true);
    List<Long> portWiseDetailIds =
        loadingPlanPortWiseDetails.stream()
            .map(LoadingPlanPortWiseDetails::getId)
            .collect(Collectors.toList());
    List<LoadingPlanStowageDetails> loadingPlanStowageDetails =
        loadingPlanStowageDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    List<LoadingPlanBallastDetails> loadingPlanBallastDetails =
        loadingPlanBallastDetailsRepository.findByLoadingPlanPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    List<LoadingPlanRobDetails> loadingPlanRobDetails =
        loadingPlanRobDetailsRepository.findByPortWiseDetailIdsAndIsActive(portWiseDetailIds, true);
    Set<Integer> loadingTimes =
        loadingPlanPortWiseDetails.stream()
            .map(portWiseDetails -> portWiseDetails.getTime())
            .sorted()
            .collect(Collectors.toSet());
    Map<Integer, List<LoadingPlanStowageDetails>> stowageMap =
        new HashMap<Integer, List<LoadingPlanStowageDetails>>();
    Map<Integer, List<LoadingPlanBallastDetails>> ballastMap =
        new HashMap<Integer, List<LoadingPlanBallastDetails>>();
    Map<Integer, List<LoadingPlanRobDetails>> robMap =
        new HashMap<Integer, List<LoadingPlanRobDetails>>();
    loadingTimes.stream()
        .sorted()
        .forEach(
            time -> {
              stowageMap.put(
                  time,
                  loadingPlanStowageDetails.stream()
                      .filter(
                          stowage -> stowage.getLoadingPlanPortWiseDetails().getTime().equals(time))
                      .collect(Collectors.toList()));
              ballastMap.put(
                  time,
                  loadingPlanBallastDetails.stream()
                      .filter(
                          ballast -> ballast.getLoadingPlanPortWiseDetails().getTime().equals(time))
                      .collect(Collectors.toList()));
              robMap.put(
                  time,
                  loadingPlanRobDetails.stream()
                      .filter(rob -> rob.getLoadingPlanPortWiseDetails().getTime().equals(time))
                      .collect(Collectors.toList()));
            });

    CargoInfo.CargoReply cargoReply = getCargoInfoForLoadicator(loadingInformation);
    VesselInfo.VesselReply vesselReply = getVesselDetailsForLoadicator(loadingInformation);
    PortInfo.PortReply portReply = getPortInfoForLoadicator(loadingInformation);

    loadicatorRequestBuilder.setTypeId(LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_TYPE_ID);
    loadingTimes.stream()
        .sorted()
        .forEach(
            time -> {
              StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
              buildStowagePlan(
                  loadingInformation,
                  time,
                  processId,
                  cargoReply,
                  vesselReply,
                  portReply,
                  stowagePlanBuilder);
              buildLoadicatorStowagePlanDetails(
                  loadingInformation,
                  stowageMap.get(time),
                  cargoNomDetails,
                  vesselReply,
                  cargoReply,
                  stowagePlanBuilder);
              buildLoadicatorCargoDetails(
                  loadingInformation,
                  cargoNomDetails,
                  stowageMap.get(time),
                  cargoReply,
                  stowagePlanBuilder);
              buildLoadicatorBallastDetails(
                  loadingInformation, ballastMap.get(time), vesselReply, stowagePlanBuilder);
              buildLoadicatorRobDetails(
                  loadingInformation, robMap.get(time), vesselReply, stowagePlanBuilder);
              loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
            });

    Loadicator.LoadicatorReply reply = this.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to send Stowage plans to Loadicator",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public Loadicator.LoadicatorReply saveLoadicatorInfo(
      Loadicator.LoadicatorRequest loadicatorRequest) {
    return loadicatorGrpcService.saveLoadicatorInfo(loadicatorRequest);
  }

  private void buildLoadicatorRobDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanRobDetails> robDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    robDetails.forEach(
        rob -> {
          Loadicator.OtherTankInfo.Builder otherTankBuilder = Loadicator.OtherTankInfo.newBuilder();
          otherTankBuilder.setTankId(rob.getTankXId());
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(rob.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(otherTankBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(otherTankBuilder::setShortName);
          }
          Optional.ofNullable(rob.getQuantity())
              .ifPresent(qty -> otherTankBuilder.setQuantity(valueOf(qty)));
          stowagePlanBuilder.addOtherTankInfo(otherTankBuilder.build());
        });
  }

  private void buildLoadicatorBallastDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanBallastDetails> ballastDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    ballastDetails.forEach(
        ballast -> {
          Loadicator.BallastInfo.Builder ballastBuilder = Loadicator.BallastInfo.newBuilder();
          Optional.ofNullable(String.valueOf(ballast.getQuantity()))
              .ifPresent(ballastBuilder::setQuantity);
          Optional.ofNullable(stowagePlanBuilder.getStowageId())
              .ifPresent(ballastBuilder::setStowageId);
          Optional.ofNullable(ballast.getTankXId()).ifPresent(ballastBuilder::setTankId);
          Optional.ofNullable(loadingInformation.getPortXId()).ifPresent(ballastBuilder::setPortId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(ballast.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(ballastBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(ballastBuilder::setShortName);
          }
          stowagePlanBuilder.addBallastInfo(ballastBuilder.build());
        });
  }

  private void buildLoadicatorCargoDetails(
      LoadingInformation loadingInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<LoadingPlanStowageDetails> stowageDetails,
      CargoInfo.CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    stowageDetails.stream()
        .map(stowage -> stowage.getCargoNominationId())
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getAbbreviation()))
                  .ifPresent(cargoBuilder::setCargoAbbrev);
              Optional.ofNullable(String.valueOf(cargoNomDetails.get(cargoNominationId).getApi()))
                  .ifPresent(cargoBuilder::setApi);
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getTemperature()))
                  .ifPresent(cargoBuilder::setStandardTemp);
              Optional.ofNullable(cargoNomDetails.get(cargoNominationId).getCargoId())
                  .ifPresent(cargoBuilder::setCargoId);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
  }

  private Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds)
      throws GenericServiceException {
    Map<Long, CargoNominationDetail> details = new HashMap<Long, CargoNominationDetail>();
    cargoNominationIds.forEach(
        id -> {
          CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
          builder.setCargoNominationId(id);
          CargoNominationDetailReply reply =
              loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
          if (reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
            log.info("Fetched details of cargo nomination with id {}", id);
            details.put(id, reply.getCargoNominationdetail());
          }
        });

    return details;
  }

  private void buildLoadicatorStowagePlanDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanStowageDetails> stowageDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      VesselInfo.VesselReply vesselReply,
      CargoInfo.CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    stowageDetails.forEach(
        stowage -> {
          Loadicator.StowageDetails.Builder stowageDetailsBuilder =
              Loadicator.StowageDetails.newBuilder();
          CargoNominationDetail cargoNomDetail =
              cargoNomDetails.get(stowage.getCargoNominationId());
          Optional.ofNullable(cargoNomDetail.getCargoId())
              .ifPresent(stowageDetailsBuilder::setCargoId);
          Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
          Optional.ofNullable(stowage.getQuantity())
              .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(cargoNomDetail.getAbbreviation())
              .ifPresent(stowageDetailsBuilder::setCargoName);
          Optional.ofNullable(loadingInformation.getPortXId())
              .ifPresent(stowageDetailsBuilder::setPortId);
          Optional.ofNullable(stowageDetailsBuilder.getStowageId())
              .ifPresent(stowageDetailsBuilder::setStowageId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(stowage.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(stowageDetailsBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(stowageDetailsBuilder::setShortName);
          }
          stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
        });
  }

  private void buildStowagePlan(
      LoadingInformation loadingInformation,
      Integer time,
      String processId,
      CargoReply cargoReply,
      VesselReply vesselReply,
      PortReply portReply,
      Builder stowagePlanBuilder) {
    stowagePlanBuilder.setProcessId(processId);
    VesselInfo.VesselDetail vessel = vesselReply.getVesselsList().get(0);
    Optional.ofNullable(vessel.getId()).ifPresent(stowagePlanBuilder::setVesselId);
    Optional.ofNullable(vessel.getImoNumber()).ifPresent(stowagePlanBuilder::setImoNumber);
    Optional.ofNullable(vessel.getTypeOfShip()).ifPresent(stowagePlanBuilder::setShipType);
    Optional.ofNullable(vessel.getCode()).ifPresent(stowagePlanBuilder::setVesselCode);
    Optional.ofNullable(vessel.getProvisionalConstant())
        .ifPresent(stowagePlanBuilder::setProvisionalConstant);
    Optional.ofNullable(vessel.getDeadweightConstant())
        .ifPresent(stowagePlanBuilder::setDeadweightConstant);
    stowagePlanBuilder.setPortId(loadingInformation.getPortXId());
    stowagePlanBuilder.setStowageId(time);
    stowagePlanBuilder.setStatus(1);
    stowagePlanBuilder.setBookingListId(loadingInformation.getId());
    Optional<PortInfo.PortDetail> portDetail =
        portReply.getPortsList().stream()
            .filter(port -> Long.valueOf(port.getId()).equals(loadingInformation.getPortXId()))
            .findAny();
    if (portDetail.isPresent()) {
      Optional.ofNullable(portDetail.get().getCode()).ifPresent(stowagePlanBuilder::setPortCode);
      Optional.ofNullable(portDetail.get().getWaterDensity())
          .ifPresent(density -> stowagePlanBuilder.setSeaWaterDensity(valueOf(density)));
    }
    stowagePlanBuilder.setSynopticalId(loadingInformation.getSynopticalTableXId());
  }

  /**
   * Get port info for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  private PortInfo.PortReply getPortInfoForLoadicator(LoadingInformation loadingInformation)
      throws GenericServiceException {
    PortInfo.PortRequest portRequest =
        PortInfo.PortRequest.newBuilder()
            .setVesselId(loadingInformation.getVesselXId())
            .setVoyageId(loadingInformation.getVoyageId())
            .build();
    PortInfo.PortReply portReply = this.GetPortInfo(portRequest);
    if (!LoadingPlanConstants.SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portReply;
  }

  public PortInfo.PortReply GetPortInfo(PortInfo.PortRequest build) {
    return portInfoGrpcService.getPortInfo(build);
  }

  /**
   * get vessel detail for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  private VesselInfo.VesselReply getVesselDetailsForLoadicator(
      LoadingInformation loadingInformation) throws GenericServiceException {
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(loadingInformation.getVesselXId())
            .build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailByVesselId(replyBuilder);
    if (!LoadingPlanConstants.SUCCESS.equalsIgnoreCase(
        vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return vesselReply;
  }

  public VesselInfo.VesselReply getVesselDetailByVesselId(VesselInfo.VesselRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselDetailByVesselId(replyBuilder);
  }

  /**
   * Get cargo details
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  private CargoInfo.CargoReply getCargoInfoForLoadicator(LoadingInformation loadingInformation)
      throws GenericServiceException {
    CargoInfo.CargoRequest cargoRequest =
        CargoInfo.CargoRequest.newBuilder()
            .setVesselId(loadingInformation.getVesselXId())
            .setVoyageId(loadingInformation.getVoyageId())
            .build();
    CargoInfo.CargoReply cargoReply = this.getCargoInfo(cargoRequest);
    if (!LoadingPlanConstants.SUCCESS.equalsIgnoreCase(
        cargoReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoReply;
  }

  public CargoInfo.CargoReply getCargoInfo(CargoInfo.CargoRequest build) {
    return cargoInfoGrpcService.getCargoInfo(build);
  }

  public void getLoadicatorData(
      LoadingInfoLoadicatorDataRequest request,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply
              .Builder
          reply)
      throws GenericServiceException {
    log.info(
        "Recieved stability parameters of Loading Information {} from Loadicator",
        request.getLoadingInformationId());
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInformationId());
    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInformationId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    saveLoadingSequenceStabilityParameters(
        loadingInfoOpt.get(), request.getLoadingInfoLoadicatorDetailsList());
  }

  private void saveLoadingSequenceStabilityParameters(
      LoadingInformation loadingInformation, List<LoadingInfoLoadicatorDetail> loadicatorDetails) {
    List<LoadingSequenceStabilityParameters> loadingSequenceStabilityParameters =
        new ArrayList<LoadingSequenceStabilityParameters>();
    deleteLoadingSequenceStabilityParameters(loadingInformation);
    log.info(
        "Saving loading sequence stability parameters of loading information {}",
        loadingInformation.getId());
    loadicatorDetails.forEach(
        details -> {
          LoadingSequenceStabilityParameters stabilityParameters =
              new LoadingSequenceStabilityParameters();
          buildLoadingSequenceStabilityParameters(loadingInformation, details, stabilityParameters);
          loadingSequenceStabilityParameters.add(stabilityParameters);
        });
    loadingSequenceStabiltyParametersRepository.saveAll(loadingSequenceStabilityParameters);
  }

  private void deleteLoadingSequenceStabilityParameters(LoadingInformation loadingInformation) {
    log.info(
        "Deleting old stability parameters of Loading Information {}", loadingInformation.getId());
    loadingSequenceStabiltyParametersRepository.deleteByLoadingInformationId(
        loadingInformation.getId());
  }

  private void buildLoadingSequenceStabilityParameters(
      LoadingInformation loadingInformation,
      LoadingInfoLoadicatorDetail details,
      LoadingSequenceStabilityParameters stabilityParameters) {
    stabilityParameters.setAftDraft(
        StringUtils.isEmpty(details.getLDtrim().getAftDraftValue())
            ? null
            : new BigDecimal(details.getLDtrim().getAftDraftValue()));
    stabilityParameters.setBendingMoment(
        StringUtils.isEmpty(details.getLDStrength().getBendingMomentPersentValue())
            ? null
            : new BigDecimal(details.getLDStrength().getBendingMomentPersentValue()));
    stabilityParameters.setForeDraft(
        StringUtils.isEmpty(details.getLDtrim().getForeDraftValue())
            ? null
            : new BigDecimal(details.getLDtrim().getForeDraftValue()));
    stabilityParameters.setIsActive(true);
    stabilityParameters.setList(
        StringUtils.isEmpty(details.getLDtrim().getHeelValue())
            ? null
            : new BigDecimal(details.getLDtrim().getHeelValue()));
    stabilityParameters.setLoadingInformation(loadingInformation);
    stabilityParameters.setMeanDraft(
        StringUtils.isEmpty(details.getLDtrim().getMeanDraftValue())
            ? null
            : new BigDecimal(details.getLDtrim().getMeanDraftValue()));
    stabilityParameters.setPortXId(loadingInformation.getPortXId());
    stabilityParameters.setShearingForce(
        StringUtils.isEmpty(details.getLDStrength().getShearingForcePersentValue())
            ? null
            : new BigDecimal(details.getLDStrength().getShearingForcePersentValue()));
    stabilityParameters.setTime(details.getTime());
    stabilityParameters.setTrim(
        StringUtils.isEmpty(details.getLDtrim().getTrimValue())
            ? null
            : new BigDecimal(details.getLDtrim().getTrimValue()));
  }
}
