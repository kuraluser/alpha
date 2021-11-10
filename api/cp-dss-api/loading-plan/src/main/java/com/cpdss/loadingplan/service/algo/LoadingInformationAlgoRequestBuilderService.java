/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.Common.RulesInputs;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingSynopticResponse;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingBerths;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoAlgoRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRates;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingToppingOff;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.domain.algo.BerthDetails;
import com.cpdss.loadingplan.domain.algo.CargoMachineryInUse;
import com.cpdss.loadingplan.domain.algo.LoadablePlanPortWiseDetails;
import com.cpdss.loadingplan.domain.algo.LoadableQuantityCargoDetails;
import com.cpdss.loadingplan.domain.algo.LoadingDelays;
import com.cpdss.loadingplan.domain.algo.LoadingInformationAlgoRequest;
import com.cpdss.loadingplan.domain.algo.LoadingRule;
import com.cpdss.loadingplan.domain.algo.LoadingSequences;
import com.cpdss.loadingplan.domain.algo.OnBoardQuantity;
import com.cpdss.loadingplan.domain.algo.OnHandQuantity;
import com.cpdss.loadingplan.domain.algo.ReasonForDelay;
import com.cpdss.loadingplan.domain.algo.ToppingOffSequence;
import com.cpdss.loadingplan.domain.algo.TrimAllowed;
import com.cpdss.loadingplan.domain.rules.RulePlans;
import com.cpdss.loadingplan.domain.rules.RuleResponse;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import com.cpdss.loadingplan.service.LoadingPlanRuleService;
import com.cpdss.loadingplan.service.LoadingPortTideService;
import com.cpdss.loadingplan.utility.RuleUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LoadingInformationAlgoRequestBuilderService {

  @GrpcClient("loadingInformationService")
  private LoadingInformationServiceBlockingStub loadingInfoServiceBlockingStub;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Autowired LoadingPortTideService loadingPortTideDetailsService;

  @Autowired LoadingPlanRuleService loadingPlanRuleService;

  /**
   * Creates the ALGO request
   *
   * @param request
   * @return
   * @throws GenericServiceException
   */
  public LoadingInformationAlgoRequest createAlgoRequest(LoadingInfoAlgoRequest request)
      throws GenericServiceException {
    LoadingInformationAlgoRequest algoRequest = new LoadingInformationAlgoRequest();
    Optional<com.cpdss.loadingplan.entity.LoadingInformation> loadingInfoOpt =
        this.loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInfoId());
    if (loadingInfoOpt.isPresent()) {
      algoRequest.setVesselId(loadingInfoOpt.get().getVesselXId());
      algoRequest.setVoyageId(loadingInfoOpt.get().getVoyageId());
      algoRequest.setPortId(loadingInfoOpt.get().getPortXId());

      // Self Call of GRPC to get loading info, need to change in future.
      LoadingInformation loadingInformation =
          getLoadingInformation(
              loadingInfoOpt.get().getVesselXId(),
              loadingInfoOpt.get().getVoyageId(),
              request.getLoadingInfoId(),
              loadingInfoOpt.get().getLoadablePatternXId(),
              loadingInfoOpt.get().getPortRotationXId());
      log.info("Build Loading Info From getLoadingInfo");
      // Build Loading Info From getLoadingInfo - grpc response
      buildLoadingInformation(algoRequest, loadingInformation, loadingInfoOpt.get());
      log.info("Build OHQ, OBQ From Loadable study");
      // Build OHQ, OBQ From Loadable study
      buildLoadablePatternPortWiseDetails(algoRequest, loadingInfoOpt.get());

      // Build hourly based tide details which upload from loading info page
      buildPortTideDetails(algoRequest, loadingInfoOpt.orElse(null));

      // Build Loading Rule, service is in loading-plan (self)
      buildLoadingRules(algoRequest, loadingInfoOpt.get());
      // buildLoadingRulesFromVessel(algoRequest, loadingInfoOpt.get().getVesselXId()); not using
    } else {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return algoRequest;
  }

  private void buildLoadingRules(
      LoadingInformationAlgoRequest algoRequest,
      com.cpdss.loadingplan.entity.LoadingInformation loadingInformation) {
    LoadingPlanModels.LoadingPlanRuleReply rpcReply =
        loadingPlanRuleService.getLoadingPlanRuleForAlgo(
            loadingInformation.getVesselXId(), loadingInformation.getId());
    if (rpcReply != null) {
      List<RulePlans> rulePlans = RuleUtility.buildLoadingPlanRule(rpcReply);
      algoRequest.setLoadingRules(new RuleResponse(rulePlans));
    }
  }

  private void buildPortTideDetails(
      LoadingInformationAlgoRequest algoRequest,
      com.cpdss.loadingplan.entity.LoadingInformation info) {
    if (info != null && info.getPortXId() != null) {
      List<PortTideAlgo> list =
          loadingPortTideDetailsService.findRecentTideDetailsByPortIdAndLoadingInfoId(
              info.getPortXId(), info.getId());
      algoRequest.setPortTideDetails(list);
    }
  }

  private void buildLoadingRulesFromVessel(
      LoadingInformationAlgoRequest algoRequest, Long vesselXId)
      throws NumberFormatException, GenericServiceException {
    log.info("Populating loading rules");
    LoadingInfoRulesRequest.Builder requestBuilder = LoadingInfoRulesRequest.newBuilder();
    requestBuilder.setVesselId(vesselXId);
    LoadingInfoRulesReply reply =
        this.vesselInfoService.getLoadingInfoRules(requestBuilder.build());
    if (!LoadingPlanConstants.SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error occured while fetching loading rules from vessel-info MS",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }

    List<LoadingRule> loadingRules = new ArrayList<LoadingRule>();
    reply
        .getRulesList()
        .forEach(
            rule -> {
              LoadingRule loadingRule = new LoadingRule();
              loadingRule.setRuleId(Long.parseLong(rule.getId()));
              if (rule.getInputsCount() > 0) {
                RulesInputs input = rule.getInputs(0);
                loadingRule.setRuleTitle(
                    input.getPrefix()
                        + (StringUtils.isEmpty(input.getSuffix())
                            ? ""
                            : " <X>" + input.getSuffix()));
                loadingRule.setValue(
                    StringUtils.isEmpty(input.getDefaultValue())
                        ? null
                        : Integer.parseInt(input.getDefaultValue()));
              }
              loadingRules.add(loadingRule);
            });

    // algoRequest.setLoadingRules(loadingRules);
  }

  private void buildLoadingInformation(
      LoadingInformationAlgoRequest algoRequest,
      LoadingInformation loadingInformation,
      com.cpdss.loadingplan.entity.LoadingInformation entity)
      throws NumberFormatException, GenericServiceException {
    log.info("Populating Loading Information {}", entity.getId());
    com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo =
        new com.cpdss.loadingplan.domain.algo.LoadingInformation();
    buildLoadingBerths(loadingInfo, entity.getPortXId(), loadingInformation.getLoadingBerthsList());
    buildLoadingDelays(loadingInfo, loadingInformation.getLoadingDelays());
    buildLoadingDetail(loadingInfo, loadingInformation.getLoadingDetail(), entity);
    loadingInfo.setLoadingInfoId(loadingInformation.getLoadingInfoId());
    buildLoadingMachines(loadingInfo, loadingInformation.getLoadingMachinesList(), entity);
    buildLoadingRate(loadingInfo, loadingInformation.getLoadingRate());
    buildLoadingStage(loadingInfo, loadingInformation.getLoadingStage());
    buildCargoVesselTankDetails(loadingInfo, entity);
    buildToppingOffSequence(loadingInfo, loadingInformation.getToppingOffSequenceList());
    algoRequest.setLoadingInformation(loadingInfo);
  }

  private void buildToppingOffSequence(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      List<LoadingToppingOff> toppingOffSequenceList) {
    log.info("Populating Topping off sequence");
    List<ToppingOffSequence> toppingOffSequences = new ArrayList<ToppingOffSequence>();
    toppingOffSequenceList.forEach(
        toppingOff -> {
          ToppingOffSequence toppingOffSequence = new ToppingOffSequence();
          Optional.ofNullable(toppingOff.getCargoAbbreviation())
              .ifPresent(toppingOffSequence::setCargoAbbreviation);
          Optional.ofNullable(toppingOff.getCargoId()).ifPresent(toppingOffSequence::setCargoId);
          Optional.ofNullable(toppingOff.getCargoName())
              .ifPresent(toppingOffSequence::setCargoName);
          Optional.ofNullable(toppingOff.getColourCode())
              .ifPresent(toppingOffSequence::setColourCode);
          toppingOffSequence.setFillingRatio(
              StringUtils.isEmpty(toppingOff.getFillingRatio())
                  ? null
                  : new BigDecimal(toppingOff.getFillingRatio()));
          Optional.ofNullable(toppingOff.getId()).ifPresent(toppingOffSequence::setId);
          Optional.ofNullable(toppingOff.getLoadingInfoId())
              .ifPresent(toppingOffSequence::setLoadingInfoId);
          Optional.ofNullable(toppingOff.getOrderNumber())
              .ifPresent(toppingOffSequence::setOrderNumber);
          toppingOffSequence.setQuantity(
              StringUtils.isEmpty(toppingOff.getQuantity())
                  ? null
                  : new BigDecimal(toppingOff.getQuantity()));
          Optional.ofNullable(toppingOff.getRemark()).ifPresent(toppingOffSequence::setRemark);
          Optional.ofNullable(toppingOff.getTankId()).ifPresent(toppingOffSequence::setTankId);
          toppingOffSequence.setUllage(
              StringUtils.isEmpty(toppingOff.getUllage())
                  ? null
                  : new BigDecimal(toppingOff.getUllage()));
          toppingOffSequences.add(toppingOffSequence);
        });
    loadingInfo.setToppingOffSequence(toppingOffSequences);
  }

  private void buildCargoVesselTankDetails(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      com.cpdss.loadingplan.entity.LoadingInformation entity)
      throws NumberFormatException, GenericServiceException {
    log.info(
        "Populating cargo vessel tank details of Port Rotation {} and Loadable Pattern {}",
        entity.getPortRotationXId(),
        entity.getLoadablePatternXId());
    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyService.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setPatternId(entity.getLoadablePatternXId())
                .setOperationType("DEP")
                .setPortRotationId(entity.getPortRotationXId())
                .setPortId(entity.getPortXId())
                .setCargoNominationFilter(false)
                .setPlanningType(Common.PLANNING_TYPE.LOADABLE_STUDY)
                .build());

    if (!LoadingPlanConstants.SUCCESS.equals(response.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch cargoVesselTankDetails from Loadable-Study MS",
          response.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(response.getResponseStatus().getCode())));
    }

    List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails =
        new ArrayList<LoadableQuantityCargoDetails>();
    response
        .getLoadableQuantityCargoDetailsList()
        .forEach(
            cargo -> {
              LoadableQuantityCargoDetails loadableQuantity = new LoadableQuantityCargoDetails();
              // Commenting because algo not needed it
              /*Optional.ofNullable(cargo.getCargoAbbreviation())
                  .ifPresent(loadableQuantity::setCargoAbbreviation);
              Optional.ofNullable(cargo.getColorCode()).ifPresent(loadableQuantity::setColorCode);
              Optional.ofNullable(cargo.getDifferenceColor())
                  .ifPresent(loadableQuantity::setDifferenceColor);
              Optional.ofNullable(cargo.getDifferencePercentage())
                  .ifPresent(loadableQuantity::setDifferencePercentage);
              Optional.ofNullable(cargo.getGrade()).ifPresent(loadableQuantity::setGrade);
              Optional.ofNullable(cargo.getId()).ifPresent(loadableQuantity::setId);
              Optional.ofNullable(cargo.getLoadableBbls60F())
                  .ifPresent(loadableQuantity::setLoadableBbls60f);
              Optional.ofNullable(cargo.getLoadableBblsdbs())
                  .ifPresent(loadableQuantity::setLoadableBblsdbs);
              Optional.ofNullable(cargo.getLoadableKL()).ifPresent(loadableQuantity::setLoadableKL);
              Optional.ofNullable(cargo.getLoadableLT()).ifPresent(loadableQuantity::setLoadableLT);
              Optional.ofNullable(cargo.getLoadableMT()).ifPresent(loadableQuantity::setLoadableMT);*/
              Optional.ofNullable(cargo.getLoadingOrder())
                  .ifPresent(loadableQuantity::setLoadingOrder);
              /*Optional.ofNullable(cargo.getMaxTolerence())
                  .ifPresent(loadableQuantity::setMaxTolerence);
              Optional.ofNullable(cargo.getMinTolerence())
                  .ifPresent(loadableQuantity::setMinTolerence);
              Optional.ofNullable(cargo.getOrderBbls60F())
                  .ifPresent(loadableQuantity::setOrderBbls60f);
              Optional.ofNullable(cargo.getOrderBblsdbs())
                  .ifPresent(loadableQuantity::setOrderBblsdbs);
              Optional.ofNullable(cargo.getOrderedMT())
                  .ifPresent(loadableQuantity::setOrderedQuantity);
              Optional.ofNullable(cargo.getPriority()).ifPresent(loadableQuantity::setPriority);
              Optional.ofNullable(cargo.getSlopQuantity())
                  .ifPresent(loadableQuantity::setSlopQuantity);
              Optional.ofNullable(cargo.getTimeRequiredForLoading())
                  .ifPresent(loadableQuantity::setTimeRequiredForLoading);*/

              Optional.ofNullable(cargo.getCargoId()).ifPresent(loadableQuantity::setCargoId);
              Optional.ofNullable(cargo.getCargoNominationId())
                  .ifPresent(loadableQuantity::setCargoNominationId);
              Optional.ofNullable(cargo.getEstimatedAPI())
                  .ifPresent(loadableQuantity::setEstimatedAPI);
              Optional.ofNullable(cargo.getEstimatedTemp())
                  .ifPresent(loadableQuantity::setEstimatedTemp);
              loadableQuantityCargoDetails.add(loadableQuantity);
            });
    loadingInfo.setLoadableQuantityCargoDetails(loadableQuantityCargoDetails);
  }

  private void buildLoadingStage(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      LoadingStages loadingStage) {
    log.info("Populating loading stage details");
    com.cpdss.loadingplan.domain.algo.LoadingStages loadingStages =
        new com.cpdss.loadingplan.domain.algo.LoadingStages();
    /*    List<StageOffset> stageOffsetList = new ArrayList<StageOffset>();
    loadingStage
        .getStageOffsetsList()
        .forEach(
            offset -> {
              StageOffset stageOffset = new StageOffset();
              stageOffset.setId(offset.getId());
              stageOffset.setStageOffsetVal(offset.getStageOffsetVal());
              stageOffsetList.add(stageOffset);
            });
    loadingStages.setStageOffsetList(stageOffsetList);

    List<StageDuration> stageDurationList = new ArrayList<StageDuration>();
    loadingStage
        .getStageDurationsList()
        .forEach(
            duration -> {
              StageDuration stageDuration = new StageDuration();
              stageDuration.setId(duration.getId());
              stageDuration.setDuration(duration.getDuration());
              stageDurationList.add(stageDuration);
            });
    loadingStages.setStageDurationList(stageDurationList);*/

    loadingStages.setStageDuration(loadingStage.getStageDuration());
    loadingStages.setStageOffset(loadingStage.getStageOffset());
    loadingStages.setTrackGradeSwitch(loadingStage.getTrackGradeSwitch());
    loadingStages.setTrackStartEndStage(loadingStage.getTrackStartEndStage());
    loadingInfo.setLoadingStages(loadingStages);
  }

  private void buildLoadingRate(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo, LoadingRates loadingRate) {
    log.info("Populating Loading rates");
    com.cpdss.loadingplan.domain.algo.LoadingRates loadingRates =
        new com.cpdss.loadingplan.domain.algo.LoadingRates();
    loadingRates.setId(loadingRate.getId());
    loadingRates.setLineContentRemaining(
        StringUtils.isEmpty(loadingRate.getLineContentRemaining())
            ? null
            : new BigDecimal(loadingRate.getLineContentRemaining()));
    loadingRates.setMaxDeBallastingRate(
        StringUtils.isEmpty(loadingRate.getMaxDeBallastingRate())
            ? null
            : new BigDecimal(loadingRate.getMaxDeBallastingRate()));
    loadingRates.setMaxLoadingRate(
        StringUtils.isEmpty(loadingRate.getMaxLoadingRate())
            ? null
            : new BigDecimal(loadingRate.getMaxLoadingRate()));
    loadingRates.setMinDeBallastingRate(
        StringUtils.isEmpty(loadingRate.getMinDeBallastingRate())
            ? null
            : new BigDecimal(loadingRate.getMinDeBallastingRate()));
    loadingRates.setMinLoadingRate(
        StringUtils.isEmpty(loadingRate.getMinLoadingRate())
            ? null
            : new BigDecimal(loadingRate.getMinLoadingRate()));
    loadingRates.setShoreLoadingRate(
        StringUtils.isEmpty(loadingRate.getShoreLoadingRate())
            ? null
            : new BigDecimal(loadingRate.getShoreLoadingRate()));
    loadingRates.setNoticeTimeRateReduction(
        StringUtils.isEmpty(loadingRate.getNoticeTimeRateReduction())
            ? null
            : new BigDecimal(loadingRate.getNoticeTimeRateReduction()));
    loadingRates.setNoticeTimeStopLoading(
        StringUtils.isEmpty(loadingRate.getNoticeTimeStopLoading())
            ? null
            : new BigDecimal(loadingRate.getNoticeTimeStopLoading()));
    loadingInfo.setLoadingRates(loadingRates);
  }

  private void buildLoadingMachines(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      List<LoadingMachinesInUse> loadingMachinesList,
      com.cpdss.loadingplan.entity.LoadingInformation loadingInformation)
      throws NumberFormatException, GenericServiceException {
    log.info("Populating loading machines in use");
    CargoMachineryInUse cargoMachineryInUse = new CargoMachineryInUse();

    VesselInfo.VesselPumpsResponse grpcReply =
        this.vesselInfoService.getVesselPumpsByVesselId(
            VesselInfo.VesselIdRequest.newBuilder()
                .setVesselId(loadingInformation.getVesselXId())
                .build());

    if (!LoadingPlanConstants.SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel pump details from Loadable-Study MS",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }
    List<com.cpdss.loadingplan.domain.algo.LoadingMachinesInUse> machineList =
        new ArrayList<com.cpdss.loadingplan.domain.algo.LoadingMachinesInUse>();
    loadingMachinesList.forEach(
        machine -> {
          com.cpdss.loadingplan.domain.algo.LoadingMachinesInUse loadingMachine =
              new com.cpdss.loadingplan.domain.algo.LoadingMachinesInUse();
          loadingMachine.setCapacity(
              StringUtils.isEmpty(machine.getCapacity())
                  ? null
                  : new BigDecimal(machine.getCapacity()));
          loadingMachine.setId(machine.getId());
          loadingMachine.setLoadingInfoId(machine.getLoadingInfoId());
          loadingMachine.setMachineId(machine.getMachineId());
          loadingMachine.setMachineTypeId(machine.getMachineType().getNumber());
          this.setMachineNameAndTypeByIdAndTypeId(
              grpcReply,
              machine.getMachineId(),
              machine.getMachineType().getNumber(),
              loadingMachine);
          machineList.add(loadingMachine);
        });
    cargoMachineryInUse.setLoadingMachinesInUses(machineList);

    loadingInfo.setMachineryInUses(cargoMachineryInUse);
  }

  private void setMachineNameAndTypeByIdAndTypeId(
      VesselInfo.VesselPumpsResponse grpcReply,
      Long machineId,
      Integer typeId,
      com.cpdss.loadingplan.domain.algo.LoadingMachinesInUse loadingMachine) {
    if (typeId == Common.MachineType.VESSEL_PUMP_VALUE) {
      Optional<VesselInfo.VesselPump> vesselPump =
          grpcReply.getVesselPumpList().stream().filter(v -> v.getId() == machineId).findFirst();
      if (vesselPump.isPresent()) {
        loadingMachine.setMachineName(vesselPump.get().getPumpName());
        loadingMachine.setMachineTypeName(Common.MachineType.VESSEL_PUMP.name());
      }
    }
    if (typeId == Common.MachineType.MANIFOLD_VALUE) {
      Optional<VesselInfo.VesselComponent> vesselPump =
          grpcReply.getVesselManifoldList().stream()
              .filter(v -> v.getId() == machineId)
              .findFirst();
      if (vesselPump.isPresent()) {
        loadingMachine.setMachineName(vesselPump.get().getComponentName());
        loadingMachine.setMachineTypeName(Common.MachineType.MANIFOLD.name());
        loadingMachine.setTankTypeName(vesselPump.get().getTankTypeName());
      }
    }
    if (typeId == Common.MachineType.BOTTOM_LINE_VALUE) {
      Optional<VesselInfo.VesselComponent> vesselPump =
          grpcReply.getVesselBottomLineList().stream()
              .filter(v -> v.getId() == machineId)
              .findFirst();
      if (vesselPump.isPresent()) {
        loadingMachine.setMachineName(vesselPump.get().getComponentName());
        loadingMachine.setMachineTypeName(Common.MachineType.BOTTOM_LINE.name());
      }
    }
  }

  private void buildLoadingDetail(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      LoadingDetails loadingDetail,
      com.cpdss.loadingplan.entity.LoadingInformation loadingInformation)
      throws GenericServiceException {
    log.info("Populating Loading detail");
    com.cpdss.loadingplan.domain.algo.LoadingDetails loadingDetails =
        new com.cpdss.loadingplan.domain.algo.LoadingDetails();
    loadingDetails.setStartTime(loadingDetail.getStartTime());
    loadingDetails.setTimeOfSunrise(loadingDetail.getTimeOfSunrise());
    loadingDetails.setTimeOfSunset(loadingDetail.getTimeOfSunset());
    TrimAllowed trimAllowed = new TrimAllowed();
    if (loadingDetail.getTrimAllowed() != null) {
      trimAllowed.setFinalTrim(
          StringUtils.isEmpty(loadingDetail.getTrimAllowed().getFinalTrim())
              ? null
              : new BigDecimal(loadingDetail.getTrimAllowed().getFinalTrim()));
      trimAllowed.setInitialTrim(
          StringUtils.isEmpty(loadingDetail.getTrimAllowed().getInitialTrim())
              ? null
              : new BigDecimal(loadingDetail.getTrimAllowed().getInitialTrim()));
      trimAllowed.setMaximumTrim(
          StringUtils.isEmpty(loadingDetail.getTrimAllowed().getMaximumTrim())
              ? null
              : new BigDecimal(loadingDetail.getTrimAllowed().getMaximumTrim()));
    }
    loadingDetails.setTrimAllowed(trimAllowed);

    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyService.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setIdType("PORT_ROTATION")
                .setId(loadingInformation.getPortRotationXId())
                .setPatternId(loadingInformation.getLoadablePatternXId())
                .build());

    if (!response.getResponseStatus().getStatus().equals("SUCCESS")) {
      log.error("Failed to get Synoptic data from LS ", response.getResponseStatus().getMessage());
      throw new GenericServiceException(
          "Failed to get Synoptic Data for Port",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (response.getSynopticDataList().isEmpty()) {
      log.info(
          "No data found for Port Rotation {} in Synoptic table",
          loadingInformation.getPortRotationXId());
    } else {
      LoadingSynopticResponse synopticalResponse =
          response.getSynopticDataList().stream()
              .filter(v -> v.getOperationType().equalsIgnoreCase("ARR"))
              .findFirst()
              .get();
      Optional.ofNullable(synopticalResponse.getTimeOfSunrise())
          .ifPresent(loadingDetails::setTimeOfSunrise);
      Optional.ofNullable(synopticalResponse.getTimeOfSunset())
          .ifPresent(loadingDetails::setTimeOfSunset);
    }
    loadingInfo.setLoadingDetails(loadingDetails);
  }

  private void buildLoadingDelays(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      LoadingDelay loadingDelays) {
    log.info("Populating loading delays");
    LoadingSequences loadingSequences = new LoadingSequences();
    List<ReasonForDelay> reasonsForDelay = new ArrayList<ReasonForDelay>();
    loadingDelays
        .getReasonsList()
        .forEach(
            delayReason -> {
              ReasonForDelay reason = new ReasonForDelay();
              reason.setId(delayReason.getId());
              reason.setReason(delayReason.getReason());
              reasonsForDelay.add(reason);
            });
    loadingSequences.setReasonForDelays(reasonsForDelay);

    List<LoadingDelays> loadingDelaysList = new ArrayList<LoadingDelays>();
    loadingDelays
        .getDelaysList()
        .forEach(
            delay -> {
              LoadingDelays ld = new LoadingDelays();
              ld.setCargoId(delay.getCargoId());
              ld.setCargoNominationId(delay.getCargoNominationId());
              ld.setDuration(
                  StringUtils.isEmpty(delay.getDuration())
                      ? null
                      : new BigDecimal(delay.getDuration()));
              ld.setId(delay.getId());
              ld.setLoadingInfoId(delay.getLoadingInfoId());
              ld.setQuantity(
                  StringUtils.isEmpty(delay.getQuantity())
                      ? null
                      : new BigDecimal(delay.getQuantity()));
              ld.setReasonForDelayIds(delay.getReasonForDelayIdsList());
              loadingDelaysList.add(ld);
            });
    loadingSequences.setLoadingDelays(loadingDelaysList);

    loadingInfo.setLoadingSequences(loadingSequences);
  }

  private void buildLoadingBerths(
      com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfo,
      Long portId,
      List<LoadingBerths> loadingBerthsList) {
    log.info("Populating loading berths");
    PortInfo.BerthInfoResponse masterBerths = this.getBerthInfoByPortId(portId);
    List<BerthDetails> berthDetails = new ArrayList<BerthDetails>();
    loadingBerthsList.forEach(
        berth -> {
          BerthDetails berthDetail = new BerthDetails();
          berthDetail.setAirDraftLimitation(
              StringUtils.isEmpty(berth.getAirDraftLimitation())
                  ? null
                  : new BigDecimal(berth.getAirDraftLimitation()));
          berthDetail.setId(berth.getBerthId());
          berthDetail.setPortId(portId);
          Optional<PortInfo.BerthDetail> selectedBerthOpt =
              masterBerths.getBerthsList().stream()
                  .filter(masterBerth -> berth.getBerthId() == masterBerth.getId())
                  .findFirst();
          selectedBerthOpt.ifPresent(
              selectedBerth -> {
                berthDetail.setBerthName(selectedBerth.getBerthName());
                berthDetail.setMaxDraft(
                    StringUtils.isEmpty(selectedBerth.getMaxDraft())
                        ? null
                        : new BigDecimal(selectedBerth.getMaxDraft()));
                berthDetail.setMaxLoa(
                    StringUtils.isEmpty(selectedBerth.getMaxLoa())
                        ? null
                        : new BigDecimal(selectedBerth.getMaxLoa()));
                berthDetail.setMaxShpChannel(
                    StringUtils.isEmpty(selectedBerth.getMaxShipChannel())
                        ? null
                        : new BigDecimal(selectedBerth.getMaxShipChannel()));
              });
          berthDetail.setHoseConnections(berth.getHoseConnections());
          berthDetail.setItemsToBeAgreedWith(berth.getItemsToBeAgreedWith());
          berthDetail.setLoadingBerthId(berth.getId());
          berthDetail.setLoadingInfoId(berth.getLoadingInfoId());
          berthDetail.setMaxManifoldHeight(
              StringUtils.isEmpty(berth.getMaxManifoldHeight())
                  ? null
                  : new BigDecimal(berth.getMaxManifoldHeight()));
          berthDetail.setRegulationAndRestriction(berth.getSpecialRegulationRestriction());
          berthDetail.setSeaDraftLimitation(
              StringUtils.isEmpty(berth.getSeaDraftLimitation())
                  ? null
                  : new BigDecimal(berth.getSeaDraftLimitation()));
          berthDetail.setMaxShipDepth(
              StringUtils.isEmpty(berth.getDepth()) ? null : new BigDecimal(berth.getDepth()));
          berthDetail.setLineDisplacement(berth.getLineDisplacement());
          // Setting controllingDepth, underKeelClearance from port info
          this.getPortInfoIntoBerthData(berth.getBerthId(), berthDetail);

          berthDetails.add(berthDetail);
        });

    loadingInfo.setBerthDetails(berthDetails);
  }

  /**
   * grpc Call to port info, to get berth data
   *
   * @param portId
   * @param PortInfo.PortInfoBerthInfoResponse
   */
  public PortInfo.BerthInfoResponse getBerthInfoByPortId(Long portId) {
    PortInfo.BerthInfoResponse response =
        this.portInfoServiceBlockingStub.getBerthDetailsByPortId(
            PortInfo.PortIdRequest.newBuilder().setPortId(portId).build());
    if (response.getResponseStatus().getStatus().equalsIgnoreCase("SUCCESS")) {
      return response;
    }
    return null;
  }

  /**
   * grpc Call to port info, In future this data can get from berth table.
   *
   * @param berthId
   * @param berthDetails
   */
  private void getPortInfoIntoBerthData(Long berthId, BerthDetails berthDetails) {
    PortInfo.LoadingAlgoBerthData portReply =
        this.portInfoServiceBlockingStub.getLoadingPlanBerthData(
            PortInfo.BerthIdsRequest.newBuilder().addBerthIds(berthId).build());
    if (portReply != null && portReply.getResponseStatus().getStatus().equals("SUCCESS")) {
      if (!portReply.getPortControllingDepth().isEmpty()) {
        berthDetails.setControllingDepth(portReply.getPortControllingDepth());
      }
      if (!portReply.getBerthUKC().isEmpty()) {
        berthDetails.setUnderKeelClearance(portReply.getBerthUKC());
      }
      if (!portReply.getPortSeawaterDensity().isEmpty()) {
        berthDetails.setSeawaterDensity(portReply.getPortSeawaterDensity());
      }
    }
    log.info("Get port berth data from port info - {}", portReply.getResponseStatus().getStatus());
  }

  private LoadingInformation getLoadingInformation(
      Long vesselId, Long voyageId, Long loadingInfoId, Long patternId, Long portRotationId)
      throws GenericServiceException {
    log.info("Calling getLoadingInformation in Loading-Plan MS");
    LoadingPlanModels.LoadingInformationRequest.Builder builder =
        LoadingPlanModels.LoadingInformationRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setVoyageId(voyageId);
    builder.setLoadingPlanId(loadingInfoId);
    if (patternId != null) builder.setLoadingPatternId(patternId);
    if (portRotationId != null) builder.setPortRotationId(portRotationId);
    LoadingPlanModels.LoadingInformation reply =
        loadingInfoServiceBlockingStub.getLoadingInformation(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to fetch Loading Information",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    return reply;
  }

  private void buildLoadablePatternPortWiseDetails(
      LoadingInformationAlgoRequest algoRequest,
      com.cpdss.loadingplan.entity.LoadingInformation loadingInformation)
      throws GenericServiceException {
    log.info(
        "Populating loadablePatternPortWiseDetails of Loadable Pattern {}",
        loadingInformation.getLoadablePatternXId());
    LoadablePlanDetailsRequest.Builder requestBuilder = LoadablePlanDetailsRequest.newBuilder();
    requestBuilder.setLoadablePatternId(loadingInformation.getLoadablePatternXId());
    requestBuilder.setIsDischargePlan(false);
    LoadablePatternPortWiseDetailsJson response =
        this.loadableStudyService.getLoadablePatternDetailsJson(requestBuilder.build());
    if (!response.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get Portwise details from Loadable-Study MS",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      algoRequest.setLoadablePlanPortWiseDetails(
          Arrays.asList(
              mapper.readValue(
                  response.getLoadablePatternDetails(), LoadablePlanPortWiseDetails[].class)));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Failed to deserialize port wise details",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    buildOnHandQuantities(algoRequest, loadingInformation, response.getLoadableStudyId());
    buildOnBoardQuantities(algoRequest, loadingInformation, response.getLoadableStudyId());
  }

  private void buildOnBoardQuantities(
      LoadingInformationAlgoRequest algoRequest,
      com.cpdss.loadingplan.entity.LoadingInformation loadingInformation,
      long loadableStudyId)
      throws NumberFormatException, GenericServiceException {
    log.info(
        "Populating onBoardQuantities of Port {} in Loadable Study {}",
        loadingInformation.getPortXId(),
        loadableStudyId);
    OnBoardQuantityRequest request =
        OnBoardQuantityRequest.newBuilder()
            .setVoyageId(loadingInformation.getVoyageId())
            .setLoadableStudyId(loadableStudyId)
            .setVesselId(loadingInformation.getVesselXId())
            .setPortId(loadingInformation.getPortXId())
            .build();
    OnBoardQuantityReply grpcReply = loadableStudyService.getOnBoardQuantity(request);
    if (!LoadingPlanConstants.SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch on board quantities from Loadable-Study MS",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }

    List<OnBoardQuantity> onBoardQuantities = new ArrayList<OnBoardQuantity>();
    grpcReply
        .getOnBoardQuantityList()
        .forEach(
            detail -> {
              OnBoardQuantity dto = new OnBoardQuantity();
              dto.setId(detail.getId());
              dto.setCargoId(0 == detail.getCargoId() ? null : detail.getCargoId());
              dto.setColorCode(isEmpty(detail.getColorCode()) ? null : detail.getColorCode());
              dto.setAbbreviation(
                  isEmpty(detail.getAbbreviation()) ? null : detail.getAbbreviation());
              dto.setSounding(
                  isEmpty(detail.getSounding())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getSounding()));
              dto.setQuantity(
                  isEmpty(detail.getWeight())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getWeight()));
              dto.setActualWeight(
                  isEmpty(detail.getActualWeight())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getActualWeight()));
              dto.setVolume(
                  isEmpty(detail.getVolume())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getVolume()));
              dto.setTankId(detail.getTankId());
              dto.setTankName(detail.getTankName());
              dto.setApi(
                  isEmpty(detail.getDensity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDensity()));
              if (detail.getTemperature() != null && detail.getTemperature().length() > 0) {
                dto.setTemperature(new BigDecimal(detail.getTemperature()));
              }

              onBoardQuantities.add(dto);
            });

    algoRequest.setOnBoardQuantity(onBoardQuantities);
  }

  private void buildOnHandQuantities(
      LoadingInformationAlgoRequest algoRequest,
      com.cpdss.loadingplan.entity.LoadingInformation loadingInformation,
      long loadableStudyId)
      throws NumberFormatException, GenericServiceException {
    log.info(
        "Populating onHandQuantities of Port Rotation {} in Loadable Study {}",
        loadingInformation.getPortRotationXId(),
        loadableStudyId);
    OnHandQuantityRequest request =
        OnHandQuantityRequest.newBuilder()
            .setCompanyId(1L)
            .setVesselId(loadingInformation.getVesselXId())
            .setLoadableStudyId(loadableStudyId)
            .setPortRotationId(loadingInformation.getPortRotationXId())
            .build();
    OnHandQuantityReply grpcReply = loadableStudyService.getOnHandQuantity(request);
    if (!LoadingPlanConstants.SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch on hand quantities from Loadable-Study MS",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }

    List<OnHandQuantity> ohqList = new ArrayList<OnHandQuantity>();
    grpcReply
        .getOnHandQuantityList()
        .forEach(
            detail -> {
              OnHandQuantity onHandQuantity = new OnHandQuantity();
              onHandQuantity.setId(detail.getId());
              onHandQuantity.setTankId(detail.getTankId());
              onHandQuantity.setTankName(detail.getTankName());
              onHandQuantity.setFuelTypeId(detail.getFuelTypeId());
              onHandQuantity.setFuelTypeName(detail.getFuelType());
              onHandQuantity.setFuelTypeShortName(detail.getFuelTypeShortName());
              onHandQuantity.setPortRotationId(detail.getPortRotationId());
              onHandQuantity.setPortId(detail.getPortId());
              onHandQuantity.setArrivalQuantity(
                  isEmpty(detail.getArrivalQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getArrivalQuantity()));
              onHandQuantity.setActualArrivalQuantity(
                  isEmpty(detail.getActualArrivalQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getActualArrivalQuantity()));
              onHandQuantity.setArrivalVolume(
                  isEmpty(detail.getArrivalVolume())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getArrivalVolume()));
              onHandQuantity.setDepartureQuantity(
                  isEmpty(detail.getDepartureQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDepartureQuantity()));
              onHandQuantity.setActualDepartureQuantity(
                  isEmpty(detail.getActualDepartureQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getActualDepartureQuantity()));
              onHandQuantity.setDepartureVolume(
                  isEmpty(detail.getDepartureVolume())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDepartureVolume()));
              onHandQuantity.setColorCode(
                  isEmpty(detail.getColorCode()) ? null : detail.getColorCode());
              onHandQuantity.setDensity(
                  isEmpty(detail.getDensity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDensity()));
              ohqList.add(onHandQuantity);
            });
    algoRequest.setOnHandQuantity(ohqList);
  }
}
