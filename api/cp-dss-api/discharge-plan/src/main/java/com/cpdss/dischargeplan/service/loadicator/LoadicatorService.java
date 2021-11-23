/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.LoadableStudy.*;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.algo.*;
import com.cpdss.dischargeplan.domain.algo.LDIntactStability;
import com.cpdss.dischargeplan.domain.algo.LDStrength;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import com.cpdss.dischargeplan.service.DischargePlanAlgoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
public class LoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired RestTemplate restTemplate;
  @Autowired private DischargingSequenceRepository dischargingSequenceRepository;

  @Autowired
  private DischargingPlanPortWiseDetailsRepository dischargingPlanPortWiseDetailsRepository;

  @Autowired
  private DischargingPlanStowageDetailsRepository dischargingPlanStowageDetailsRepository;

  @Autowired
  private DischargingPlanBallastDetailsRepository dischargingPlanBallastDetailsRepository;

  @Autowired private DischargingPlanRobDetailsRepository dischargingPlanRobDetailsRepository;

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

  @Autowired UllageUpdateLoadicatorService ullageupdateLoadicatorService;

  @Autowired DischargingSequenceStabiltyParametersRepository dsSeqStabilityParamRepo;

  @Autowired PortDischargingPlanStabilityParametersRepository portDsPlanStbParamRepo;

  @Autowired DischargePlanAlgoService dischargePlanAlgoService;

  @Autowired DischargeInformationService dischargeInformationService;

  /**
   * get vessel detail for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  public VesselInfo.VesselReply getVesselDetailsForLoadicator(
      DischargeInformation dischargeInformation) throws GenericServiceException {
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(dischargeInformation.getVesselXid())
            .build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailByVesselId(replyBuilder);
    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(
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

  public void buildLdStrength(
      com.cpdss.common.generated.LoadableStudy.LDStrength ldStrength,
      LoadicatorStage loadicatorStage) {
    LDStrength strength = new LDStrength();
    strength.setBendingMomentPersentFrameNumber(ldStrength.getBendingMomentPersentFrameNumber());
    strength.setBendingMomentPersentJudgement(ldStrength.getBendingMomentPersentJudgement());
    strength.setBendingMomentPersentValue(ldStrength.getBendingMomentPersentValue());
    strength.setErrorDetails(ldStrength.getErrorDetails());
    strength.setId(ldStrength.getId());
    strength.setInnerLongiBhdFrameNumber(ldStrength.getInnerLongiBhdFrameNumber());
    strength.setInnerLongiBhdJudgement(ldStrength.getInnerLongiBhdJudgement());
    strength.setInnerLongiBhdValue(ldStrength.getInnerLongiBhdValue());
    strength.setMessageText(ldStrength.getMessageText());
    strength.setOuterLongiBhdFrameNumber(ldStrength.getOuterLongiBhdFrameNumber());
    strength.setOuterLongiBhdJudgement(ldStrength.getOuterLongiBhdJudgement());
    strength.setOuterLongiBhdValue(ldStrength.getOuterLongiBhdValue());
    strength.setSfFrameNumber(ldStrength.getSfFrameNumber());
    strength.setSfHopperFrameNumber(ldStrength.getSfHopperFrameNumber());
    strength.setSfHopperJudgement(ldStrength.getSfHopperJudgement());
    strength.setSfHopperValue(ldStrength.getSfHopperValue());
    strength.setSfSideShellFrameNumber(ldStrength.getSfSideShellFrameNumber());
    strength.setSfSideShellJudgement(ldStrength.getSfSideShellJudgement());
    strength.setSfSideShellValue(ldStrength.getSfSideShellValue());
    strength.setShearingForceJudgement(ldStrength.getShearingForceJudgement());
    strength.setShearingForcePersentValue(ldStrength.getShearingForcePersentValue());
    loadicatorStage.setLdStrength(strength);
  }

  public void buildLdIntactStability(
      com.cpdss.common.generated.LoadableStudy.LDIntactStability lDIntactStability,
      LoadicatorStage loadicatorStage) {
    LDIntactStability intactStability = new LDIntactStability();
    intactStability.setAngleatmaxrleverJudgement(lDIntactStability.getAngleatmaxrleverJudgement());
    intactStability.setAngleatmaxrleverValue(lDIntactStability.getAngleatmaxrleverValue());
    intactStability.setAreaofStability030Judgement(
        lDIntactStability.getAreaofStability030Judgement());
    intactStability.setAreaofStability030Value(lDIntactStability.getAreaofStability030Value());
    intactStability.setAreaofStability040Judgement(
        lDIntactStability.getAreaofStability040Judgement());
    intactStability.setAreaofStability040Value(lDIntactStability.getAreaofStability040Value());
    intactStability.setAreaofStability3040Judgement(
        lDIntactStability.getAreaofStability3040Judgement());
    intactStability.setAreaofStability3040Value(lDIntactStability.getAreaofStability3040Value());
    intactStability.setBigIntialGomJudgement(lDIntactStability.getBigIntialGomJudgement());
    intactStability.setBigintialGomValue(lDIntactStability.getBigintialGomValue());
    intactStability.setErrorDetails(lDIntactStability.getErrorDetails());
    intactStability.setErrorStatus(lDIntactStability.getErrorStatus());
    intactStability.setGmAllowableCurveCheckJudgement(
        lDIntactStability.getGmAllowableCurveCheckJudgement());
    intactStability.setGmAllowableCurveCheckValue(
        lDIntactStability.getGmAllowableCurveCheckValue());
    intactStability.setHeelBySteadyWindJudgement(lDIntactStability.getHeelBySteadyWindJudgement());
    intactStability.setHeelBySteadyWindValue(lDIntactStability.getHeelBySteadyWindValue());
    intactStability.setId(lDIntactStability.getId());
    intactStability.setMaximumRightingLeverJudgement(
        lDIntactStability.getMaximumRightingLeverJudgement());
    intactStability.setMaximumRightingLeverValue(lDIntactStability.getMaximumRightingLeverValue());
    intactStability.setMessageText(lDIntactStability.getMessageText());
    intactStability.setStabilityAreaBaJudgement(lDIntactStability.getStabilityAreaBaJudgement());
    intactStability.setStabilityAreaBaValue(lDIntactStability.getStabilityAreaBaValue());
    loadicatorStage.setLdIntactStability(intactStability);
  }

  public void buildLdTrim(LDtrim ldTrim, LoadicatorStage loadicatorStage) {
    LDTrim trim = new LDTrim();
    trim.setAftDraftValue(ldTrim.getAftDraftValue());
    trim.setAirDraftJudgement(ldTrim.getAirDraftJudgement());
    trim.setAirDraftValue(ldTrim.getAirDraftValue());
    trim.setDisplacementJudgement(ldTrim.getDisplacementJudgement());
    trim.setDisplacementValue(ldTrim.getDisplacementValue());
    trim.setErrorDetails(ldTrim.getErrorDetails());
    trim.setErrorStatus(ldTrim.getErrorStatus());
    trim.setForeDraftValue(ldTrim.getForeDraftValue());
    trim.setHeelValue(ldTrim.getHeelValue());
    trim.setId(ldTrim.getId());
    trim.setMaximumAllowableJudement(ldTrim.getMaximumAllowableJudement());
    trim.setMaximumAllowableVisibility(ldTrim.getMaximumAllowableVisibility());
    trim.setMaximumDraftJudgement(ldTrim.getMaximumDraftJudgement());
    trim.setMeanDraftValue(ldTrim.getMaximumDraftValue());
    trim.setMaximumDraftValue(ldTrim.getMaximumDraftValue());
    trim.setMeanDraftJudgement(ldTrim.getMeanDraftJudgement());
    trim.setMeanDraftValue(ldTrim.getMeanDraftValue());
    trim.setMessageText(ldTrim.getMessageText());
    trim.setMinimumForeDraftInRoughWeatherJudgement(
        ldTrim.getMinimumForeDraftInRoughWeatherJudgement());
    trim.setMinimumForeDraftInRoughWeatherValue(ldTrim.getMinimumForeDraftInRoughWeatherValue());
    trim.setTrimValue(ldTrim.getTrimValue());
    trim.setDeflection(ldTrim.getDeflection());
    loadicatorStage.setLdTrim(trim);
  }

  public Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds) {
    Map<Long, CargoNominationDetail> details = new HashMap<>();
    cargoNominationIds.forEach(
        id -> {
          CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
          builder.setCargoNominationId(id);
          CargoNominationDetailReply reply =
              loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
          if (reply.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
            log.info("Fetched details of cargo nomination with id {}", id);
            details.put(id, reply.getCargoNominationdetail());
          }
        });

    return details;
  }
  /**
   * Get cargo details
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  public CargoInfo.CargoReply getCargoInfoForLoadicator(DischargeInformation dischargeInformation)
      throws GenericServiceException {
    CargoInfo.CargoRequest cargoRequest =
        CargoInfo.CargoRequest.newBuilder()
            .setVesselId(dischargeInformation.getVesselXid())
            .setVoyageId(dischargeInformation.getVoyageXid())
            .build();
    CargoInfo.CargoReply cargoReply = this.getCargoInfo(cargoRequest);
    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(
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

  /**
   * Get port info for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  public PortInfo.PortReply getPortInfoForLoadicator(DischargeInformation dischargeInformation)
      throws GenericServiceException {
    PortInfo.PortRequest portRequest =
        PortInfo.PortRequest.newBuilder()
            .setVesselId(dischargeInformation.getVesselXid())
            .setVoyageId(dischargeInformation.getVoyageXid())
            .build();
    PortInfo.PortReply portReply = this.getPortInfo(portRequest);
    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(
        portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portReply;
  }

  public PortInfo.PortReply getPortInfo(PortInfo.PortRequest build) {
    return portInfoGrpcService.getPortInfo(build);
  }

  public void buildStowagePlan(
      DischargeInformation dischargeInformation,
      Integer time,
      String processId,
      CargoReply cargoReply,
      VesselReply vesselReply,
      PortReply portReply,
      Builder stowagePlanBuilder) {
    log.info("building stowage plan for time {}", time);
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
    stowagePlanBuilder.setPortId(dischargeInformation.getPortXid());
    stowagePlanBuilder.setStowageId(time);
    stowagePlanBuilder.setStatus(1);
    stowagePlanBuilder.setBookingListId(dischargeInformation.getId());
    Optional<PortInfo.PortDetail> portDetail =
        portReply.getPortsList().stream()
            .filter(port -> Long.valueOf(port.getId()).equals(dischargeInformation.getPortXid()))
            .findAny();
    if (portDetail.isPresent()) {
      Optional.ofNullable(portDetail.get().getCode()).ifPresent(stowagePlanBuilder::setPortCode);
      Optional.ofNullable(portDetail.get().getWaterDensity())
          .ifPresent(density -> stowagePlanBuilder.setSeaWaterDensity(valueOf(density)));
    }
    stowagePlanBuilder.setSynopticalId(dischargeInformation.getSynopticTableXid());
    stowagePlanBuilder.setPortRotationId(dischargeInformation.getPortRotationXid());
  }

  public void saveLoadicatorInfo(DischargeInformation dischargeInformation, String processId)
      throws GenericServiceException {
    log.info(
        "Sending stowage plans of discharge information {} to loadicator",
        dischargeInformation.getId());
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    Set<Long> cargoNominationIds = new LinkedHashSet<Long>();
    List<DischargingSequence> dischargeSequences =
        dischargingSequenceRepository.findByDischargeInformationAndIsActiveOrderBySequenceNumber(
            dischargeInformation, true);
    List<com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails>
        dischargingPlanPortWiseDetails =
            dischargingPlanPortWiseDetailsRepository
                .findByDischargeInformationIdAndToLoadicatorAndIsActive(
                    dischargeInformation.getId(), true, true);
    List<Long> portWiseDetailIds =
        dischargingPlanPortWiseDetails.stream()
            .map(DischargingPlanPortWiseDetails::getId)
            .collect(Collectors.toList());
    List<DischargingPlanStowageDetails> dischargePlanStowageDetails =
        dischargingPlanStowageDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    cargoNominationIds.addAll(
        dischargePlanStowageDetails.stream()
            .map(DischargingPlanStowageDetails::getCargoNominationId)
            .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
            .collect(Collectors.toList()));
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);
    List<DischargingPlanBallastDetails> dischargePlanBallastDetails =
        dischargingPlanBallastDetailsRepository.findByDischargingPlanPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    List<DischargingPlanRobDetails> dischargePlanRobDetails =
        dischargingPlanRobDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    Set<Integer> dischargeTimes = new LinkedHashSet<Integer>();
    dischargeTimes.addAll(
        dischargingPlanPortWiseDetails.stream()
            .map(DischargingPlanPortWiseDetails::getTime)
            .sorted()
            .collect(Collectors.toList()));
    Map<Integer, List<DischargingPlanStowageDetails>> stowageMap = new HashMap<>();
    Map<Integer, List<DischargingPlanBallastDetails>> ballastMap = new HashMap<>();
    Map<Integer, List<DischargingPlanRobDetails>> robMap = new HashMap<>();
    dischargeTimes.forEach(
        time -> {
          Boolean isGradeSwitchWithoutDelay =
              isGradeSwitchTimeWithoutDelay(dischargingPlanPortWiseDetails, time);
          Long portWiseDetailsId =
              getPortWiseDetailsIdForGradeSwitchWithoutDelay(
                  dischargeSequences, dischargingPlanPortWiseDetails, time);
          stowageMap.put(
              time,
              dischargePlanStowageDetails.stream()
                  .filter(
                      stowage -> stowage.getDischargingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      stowage ->
                          isGradeSwitchWithoutDelay
                              ? stowage
                                  .getDischargingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
          ballastMap.put(
              time,
              dischargePlanBallastDetails.stream()
                  .filter(
                      ballast -> ballast.getDischargingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      ballast ->
                          isGradeSwitchWithoutDelay
                              ? ballast
                                  .getDischargingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
          robMap.put(
              time,
              dischargePlanRobDetails.stream()
                  .filter(rob -> rob.getDischargingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      rob ->
                          isGradeSwitchWithoutDelay
                              ? rob.getDischargingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
        });

    CargoInfo.CargoReply cargoReply = getCargoInfoForLoadicator(dischargeInformation);
    VesselInfo.VesselReply vesselReply = getVesselDetailsForLoadicator(dischargeInformation);
    PortInfo.PortReply portReply = getPortInfoForLoadicator(dischargeInformation);

    loadicatorRequestBuilder.setTypeId(DischargePlanConstants.LOADICATOR_TYPE_ID);
    loadicatorRequestBuilder.setIsUllageUpdate(false);
    dischargeTimes.forEach(
        time -> {
          StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
          buildStowagePlan(
              dischargeInformation,
              time,
              processId,
              cargoReply,
              vesselReply,
              portReply,
              stowagePlanBuilder);
          buildLoadicatorStowagePlanDetails(
              dischargeInformation,
              stowageMap.get(time),
              cargoNomDetails,
              vesselReply,
              cargoReply,
              stowagePlanBuilder);
          buildLoadicatorCargoDetails(
              dischargeInformation,
              cargoNomDetails,
              stowageMap.get(time),
              cargoReply,
              stowagePlanBuilder);
          buildLoadicatorBallastDetails(
              dischargeInformation, ballastMap.get(time), vesselReply, stowagePlanBuilder);
          buildLoadicatorRobDetails(
              dischargeInformation, robMap.get(time), vesselReply, stowagePlanBuilder);
          loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
        });

    Loadicator.LoadicatorReply reply = this.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
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
  /**
   * Returns whether there is a grade switch without delay at the given time. If there are multiple
   * portWiseDetails for the same time, then it is actually due to a grade switch without any delay
   *
   * @param loadingPlanPortWiseDetails
   * @param time
   * @return
   */
  private Boolean isGradeSwitchTimeWithoutDelay(
      List<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetails, Integer time) {

    return dischargingPlanPortWiseDetails.stream()
            .filter(portWiseDetails -> portWiseDetails.getTime().equals(time))
            .collect(Collectors.toList())
            .size()
        > 1;
  }

  private void buildLoadicatorStowagePlanDetails(
      DischargeInformation dischargeInformation,
      List<DischargingPlanStowageDetails> stowageDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      VesselInfo.VesselReply vesselReply,
      CargoInfo.CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    stowageDetails.stream()
        .filter(stowage -> stowage.getCargoNominationId().longValue() != 0)
        .forEach(
            stowage -> {
              Loadicator.StowageDetails.Builder stowageDetailsBuilder =
                  Loadicator.StowageDetails.newBuilder();
              CargoNominationDetail cargoNomDetail =
                  cargoNomDetails.get(stowage.getCargoNominationId());
              Optional.ofNullable(cargoNomDetail.getCargoId())
                  .ifPresent(stowageDetailsBuilder::setCargoId);
              Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
              Optional.ofNullable(stowage.getQuantity())
                  .ifPresent(
                      quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(cargoNomDetail.getAbbreviation())
                  .ifPresent(stowageDetailsBuilder::setCargoName);
              Optional.ofNullable(dischargeInformation.getPortXid())
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

  private void buildLoadicatorCargoDetails(
      DischargeInformation dischargeInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<DischargingPlanStowageDetails> stowageDetails,
      CargoInfo.CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    stowageDetails.stream()
        .map(stowage -> stowage.getCargoNominationId())
        .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getAbbreviation()))
                  .ifPresent(cargoBuilder::setCargoAbbrev);
              Optional<DischargingPlanStowageDetails> stowageOpt =
                  stowageDetails.stream()
                      .filter(stwg -> stwg.getCargoNominationId().equals(cargoNominationId))
                      .findAny();
              stowageOpt.ifPresent(
                  stwg -> {
                    Optional.ofNullable(String.valueOf(stwg.getApi()))
                        .ifPresent(cargoBuilder::setApi);
                    Optional.ofNullable(String.valueOf(stwg.getTemperature()))
                        .ifPresent(cargoBuilder::setStandardTemp);
                  });
              Optional.ofNullable(cargoNomDetails.get(cargoNominationId).getCargoId())
                  .ifPresent(cargoBuilder::setCargoId);
              Optional.ofNullable(dischargeInformation.getPortXid())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
  }

  private void buildLoadicatorBallastDetails(
      DischargeInformation dischargeInformation,
      List<DischargingPlanBallastDetails> ballastDetails,
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
          Optional.ofNullable(dischargeInformation.getPortXid())
              .ifPresent(ballastBuilder::setPortId);
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

  private void buildLoadicatorRobDetails(
      DischargeInformation dischargeInformation,
      List<DischargingPlanRobDetails> robDetails,
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
  /**
   * @param loadingSequences
   * @param loadingPlanPortWiseDetails
   * @param time
   * @return
   */
  private Long getPortWiseDetailsIdForGradeSwitchWithoutDelay(
      List<DischargingSequence> loadingSequences,
      List<DischargingPlanPortWiseDetails> loadingPlanPortWiseDetails,
      Integer time) {
    List<DischargingPlanPortWiseDetails> filteredPortDetails =
        loadingPlanPortWiseDetails.stream()
            .filter(portWiseDetails -> portWiseDetails.getTime().equals(time))
            .collect(Collectors.toList());
    if (filteredPortDetails.size() > 1) {
      log.info("Found grade switch without delay stage at time {}", time);
      return filteredPortDetails.get(0).getDischargingSequence().getSequenceNumber()
              > filteredPortDetails.get(1).getDischargingSequence().getSequenceNumber()
          ? filteredPortDetails.get(0).getId()
          : filteredPortDetails.get(1).getId();
    }
    return null;
  }

  public void saveJson(com.cpdss.common.generated.LoadableStudy.JsonRequest.Builder jsonBuilder) {
    this.loadableStudyGrpcService.saveJson(jsonBuilder.build());
  }

  /**
   * @param request
   * @param reply
   * @throws GenericServiceException
   */
  public void getLoadicatorData(
      DischargingInfoLoadicatorDataRequest request,
      com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataReply.Builder reply)
      throws GenericServiceException {
    Optional<DischargeInformation> dischargeInfoOpt =
        dischargeInformationRepository.findByIdAndIsActiveTrue(
            request.getDischargingInformationId());
    if (!request.getIsUllageUpdate()) { // Discharge Plan Loadicator Data
      LoadicatorAlgoRequest algoRequest = new LoadicatorAlgoRequest();

      // Build discharge info data to algo for plan regeneration
      buildLoadicatorAlgoRequest(dischargeInfoOpt.get(), request, algoRequest);

      // Save Request JSON Data at LS json_data table
      saveLoadicatorRequestJson(algoRequest, dischargeInfoOpt.get().getId());

      try {
        // Send Payload to Algo
        LoadicatorAlgoResponse lar =
            restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);
        // Save Response JSON Data at LS json_data table
        saveLoadicatorResponseJson(lar, dischargeInfoOpt.get().getId());

        // Save new stability data into tables
        saveDischargeSequenceStabilityParameters(dischargeInfoOpt.get(), lar);

        // Update status after new data comes in
        Optional<DischargingInformationStatus> dischargingInfoStatusOpt =
            dischargePlanAlgoService.getDischargingInformationStatus(
                DischargePlanConstants.PLAN_GENERATED_ID);
        dischargeInformationService.updateDischargingInformationStatuses(
            dischargingInfoStatusOpt.get(),
            dischargingInfoStatusOpt.get(),
            dischargingInfoStatusOpt.get(),
            dischargeInfoOpt.get().getId());
        dischargePlanAlgoService.updateDischargingInfoAlgoStatus(
            dischargeInfoOpt.get(), request.getProcessId(), dischargingInfoStatusOpt.get(), null);
        dischargeInformationService.updateIsDischargingSequenceGeneratedStatus(
            dischargeInfoOpt.get().getId(), true);
        dischargeInformationService.updateIsDischargingPlanGeneratedStatus(
            dischargeInfoOpt.get().getId(), true);
      } catch (HttpStatusCodeException e) {
        // Update status after error occurs
        log.error("Error occured in ALGO side while calling loadicator_results API");
        Optional<DischargingInformationStatus> errorOccurredStatusOpt =
            dischargePlanAlgoService.getDischargingInformationStatus(
                DischargePlanConstants.DISCHARGING_INFORMATION_ERROR_OCCURRED_ID);
        dischargeInformationService.updateDischargingInformationStatus(
            errorOccurredStatusOpt.get(), dischargeInfoOpt.get().getId());
        dischargePlanAlgoService.updateDischargingInfoAlgoStatus(
            dischargeInfoOpt.get(), request.getProcessId(), errorOccurredStatusOpt.get(), null);
        dischargePlanAlgoService.saveAlgoInternalError(
            dischargeInfoOpt.get(), null, Lists.newArrayList(e.getResponseBodyAsString()));
      }
    } else { // Update Ullage Loadicator Data
      ullageupdateLoadicatorService.getLoadicatorData(request, dischargeInfoOpt.get());
    }
  }

  public void saveDischargeSequenceStabilityParameters(
      DischargeInformation dsInfo, LoadicatorAlgoResponse lar) {
    List<DischargingSequenceStabilityParameters> entityData = new ArrayList<>();

    // 1. Save Discharge Plan Sequence stability param
    //  1.1 Delete existing discharge sequence stability param
    dsSeqStabilityParamRepo.deleteByDischargingInformationId(dsInfo.getId());
    log.info("Discharge Plan Stability Param, Deleted for DS Id - {}", dsInfo.getId());
    for (var ldRs : lar.getLoadicatorResults()) {
      DischargingSequenceStabilityParameters dsPm = new DischargingSequenceStabilityParameters();
      this.buildDischargeSequenceStabilityParameters(dsInfo, ldRs, dsPm);
      entityData.add(dsPm);
    }
    // 1.2 Save new records
    dsSeqStabilityParamRepo.saveAll(entityData);
    log.info("Discharge Plan Stability Param, Saved new record Size - {} ", entityData.size());

    // 2. Save Port Discharge Plan Stability Parma
    savePortDischargePlanStabilityParam(dsInfo, lar);
  }

  private void savePortDischargePlanStabilityParam(
      DischargeInformation dsInfo, LoadicatorAlgoResponse lar) {

    // Fetch older data in heap, then delete that.
    var OldArrData =
        portDsPlanStbParamRepo.getDataByInfoAndConditionAndValueTypes(
            dsInfo.getId(),
            DischargePlanConstants.DISCHARGE_PLAN_ARRIVAL_CONDITION_VALUE,
            DischargePlanConstants.DISCHARGE_PLAN_PLANNED_TYPE_VALUE);

    var OldDepData =
        portDsPlanStbParamRepo.getDataByInfoAndConditionAndValueTypes(
            dsInfo.getId(),
            DischargePlanConstants.DISCHARGE_PLAN_DEPARTURE_CONDITION_VALUE,
            DischargePlanConstants.DISCHARGE_PLAN_PLANNED_TYPE_VALUE);

    portDsPlanStbParamRepo.deleteByDischargingInformationId(dsInfo.getId());
    log.info("Port Discharge Plan Stability Data Deleted for DS Id - {}", dsInfo.getId());

    LoadicatorResult newArrDataLod = lar.getLoadicatorResults().get(0);
    LoadicatorResult newDepDataLod =
        lar.getLoadicatorResults().get(lar.getLoadicatorResults().size() - 1);

    PortDischargingPlanStabilityParameters newArrEntity =
        new PortDischargingPlanStabilityParameters();
    this.buildPortDischargePlanStabilityParams(
        dsInfo,
        newArrDataLod,
        newArrEntity,
        DischargePlanConstants.DISCHARGE_PLAN_ARRIVAL_CONDITION_VALUE,
        DischargePlanConstants.DISCHARGE_PLAN_PLANNED_TYPE_VALUE,
        OldArrData);

    PortDischargingPlanStabilityParameters newDepEntity =
        new PortDischargingPlanStabilityParameters();
    this.buildPortDischargePlanStabilityParams(
        dsInfo,
        newDepDataLod,
        newDepEntity,
        DischargePlanConstants.DISCHARGE_PLAN_DEPARTURE_CONDITION_VALUE,
        DischargePlanConstants.DISCHARGE_PLAN_PLANNED_TYPE_VALUE,
        OldDepData);

    // Save new Port Discharge Plan Stability Data
    portDsPlanStbParamRepo.save(newArrEntity);
    log.info("Port Discharge Plan Stability Data Save for Arr, Id - {}", newArrEntity.getId());
    portDsPlanStbParamRepo.save(newDepEntity);
    log.info("Port Discharge Plan Stability Data Save for DEP, Id - {}", newDepEntity.getId());
  }

  private void buildLoadicatorAlgoRequest(
      DischargeInformation dischargeInformation,
      DischargingInfoLoadicatorDataRequest request,
      LoadicatorAlgoRequest algoRequest)
      throws GenericServiceException {
    algoRequest.setDischargingInformationId(dischargeInformation.getId());
    algoRequest.setDischargeStudyProcessId(dischargeInformation.getDischargeStudyProcessId());
    algoRequest.setProcessId(request.getProcessId());
    algoRequest.setVesselId(dischargeInformation.getVesselXid());
    algoRequest.setPortId(dischargeInformation.getPortXid());
    algoRequest.setPortRotationId(dischargeInformation.getPortRotationXid());
    List<LoadicatorStage> stages = new ArrayList<LoadicatorStage>();
    request
        .getLoadingInfoLoadicatorDetailsList()
        .forEach(
            loadicatorDetails -> {
              LoadicatorStage loadicatorStage = new LoadicatorStage();
              loadicatorStage.setTime(loadicatorDetails.getTime());
              buildLdTrim(loadicatorDetails.getLDtrim(), loadicatorStage);
              buildLdIntactStability(loadicatorDetails.getLDIntactStability(), loadicatorStage);
              buildLdStrength(loadicatorDetails.getLDStrength(), loadicatorStage);
              stages.add(loadicatorStage);
            });
    algoRequest.setStages(stages);
  }

  private void saveLoadicatorRequestJson(LoadicatorAlgoRequest algoRequest, Long dischargeInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(dischargeInfoId);
    jsonBuilder.setJsonTypeId(
        DischargePlanConstants.DISCHARGE_INFORMATION_LOADICATOR_REQUEST_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/dischargeInfoLoadicatorRequest_"
                  + dischargeInfoId
                  + ".json"),
          algoRequest);
      jsonBuilder.setJson(mapper.writeValueAsString(algoRequest));
      this.saveJson(jsonBuilder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save request JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (IOException e) {
      throw new GenericServiceException(
          "Could not save request JSON to Filesystem",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private void saveLoadicatorResponseJson(LoadicatorAlgoResponse algoResponse, Long dischargeInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(dischargeInfoId);
    jsonBuilder.setJsonTypeId(
        DischargePlanConstants.DISCHARGE_INFORMATION_LOADICATOR_RESPONSE_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/dischargeInfoLoadicatorResponse_"
                  + dischargeInfoId
                  + ".json"),
          algoResponse);
      jsonBuilder.setJson(mapper.writeValueAsString(algoResponse));
      this.saveJson(jsonBuilder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save request JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (IOException e) {
      throw new GenericServiceException(
          "Could not save request JSON to Filesystem",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public void buildDischargeSequenceStabilityParameters(
      DischargeInformation dsInfo,
      LoadicatorResult result,
      DischargingSequenceStabilityParameters stbParam) {
    stbParam.setAftDraft(
        StringUtils.isEmpty(result.getCalculatedDraftAftPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftAftPlanned()));
    stbParam.setBendingMoment(
        StringUtils.isEmpty(result.getBendingMoment())
            ? null
            : new BigDecimal(result.getBendingMoment()));
    stbParam.setForeDraft(
        StringUtils.isEmpty(result.getCalculatedDraftFwdPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftFwdPlanned()));
    stbParam.setIsActive(true);
    stbParam.setList(
        StringUtils.isEmpty(result.getList()) ? null : new BigDecimal(result.getList()));
    stbParam.setDischargingInformation(dsInfo);
    stbParam.setMeanDraft(
        StringUtils.isEmpty(result.getCalculatedDraftMidPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftMidPlanned()));
    stbParam.setPortXId(dsInfo.getPortXid());
    stbParam.setShearingForce(
        StringUtils.isEmpty(result.getShearingForce())
            ? null
            : new BigDecimal(result.getShearingForce()));
    stbParam.setTime(result.getTime());
    stbParam.setTrim(
        StringUtils.isEmpty(result.getCalculatedTrimPlanned())
            ? null
            : new BigDecimal(result.getCalculatedTrimPlanned()));
  }

  public void buildPortDischargePlanStabilityParams(
      DischargeInformation dsInfo,
      LoadicatorResult ldResult,
      PortDischargingPlanStabilityParameters newEntity,
      Integer conditionType,
      Integer valueType,
      Optional<PortDischargingPlanStabilityParameters> oldEntity) {
    newEntity.setAftDraft(
        StringUtils.isEmpty(ldResult.getCalculatedDraftAftPlanned())
            ? null
            : new BigDecimal(ldResult.getCalculatedDraftAftPlanned()));
    newEntity.setBendingMoment(
        StringUtils.isEmpty(ldResult.getBendingMoment())
            ? null
            : new BigDecimal(ldResult.getBendingMoment()));
    newEntity.setForeDraft(
        StringUtils.isEmpty(ldResult.getCalculatedDraftFwdPlanned())
            ? null
            : new BigDecimal(ldResult.getCalculatedDraftFwdPlanned()));
    newEntity.setIsActive(true);
    newEntity.setList(
        StringUtils.isEmpty(ldResult.getList()) ? null : new BigDecimal(ldResult.getList()));
    newEntity.setDischargingInformation(dsInfo);
    newEntity.setMeanDraft(
        StringUtils.isEmpty(ldResult.getCalculatedDraftMidPlanned())
            ? null
            : new BigDecimal(ldResult.getCalculatedDraftMidPlanned()));
    newEntity.setPortXId(dsInfo.getPortXid());
    newEntity.setShearingForce(
        StringUtils.isEmpty(ldResult.getShearingForce())
            ? null
            : new BigDecimal(ldResult.getShearingForce()));
    newEntity.setTrim(
        StringUtils.isEmpty(ldResult.getCalculatedTrimPlanned())
            ? null
            : new BigDecimal(ldResult.getCalculatedTrimPlanned()));
    newEntity.setConditionType(conditionType);
    newEntity.setPortRotationXId(dsInfo.getPortRotationXid());
    newEntity.setValueType(valueType);
    /*oldEntity.ifPresent( // no column in table
    stability -> {
      newEntity.setFreeboard(stability.getFreeboard());
      newEntity.setManifoldHeight(stability.getManifoldHeight());
    });*/
  }
}
