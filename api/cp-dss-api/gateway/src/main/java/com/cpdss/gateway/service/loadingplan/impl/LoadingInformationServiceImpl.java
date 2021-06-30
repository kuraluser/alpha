/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.cpdss.gateway.domain.UpdateUllage;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.PortInfoService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Loading Information Tab Grid Data Populate here
 *
 * @author Johnsooraj.x
 * @since 26-05-2021
 */
@Slf4j
@Service
public class LoadingInformationServiceImpl implements LoadingInformationService {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Autowired VesselInfoService vesselInfoService;

  @Autowired PortInfoService portInfoService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  @Autowired LoadingInformationBuilderService loadingInfoBuilderService;

  @Autowired LoadableStudyService loadableStudyService;

  /**
   * Sunset/Sunrise Only Collect from Synoptic Table in LS
   *
   * @param vesselId Long
   * @param voyageId Long
   * @param portRId Long
   * @return loadingDetails with Sunset/Sunrise Only
   */
  @Override
  public LoadingDetails getLoadingDetailsByPortRotationId(
      LoadingPlanModels.LoadingDetails var1,
      Long vesselId,
      Long voyageId,
      Long portRId,
      Long portId) {
    LoadingDetails var = new LoadingDetails();
    try {
      // Set Values from Loading plan
      Optional.ofNullable(var1.getStartTime()).ifPresent(var::setStartTime);
      if (var1.hasTrimAllowed()) {
        TrimAllowed trimAllowed = new TrimAllowed();
        Optional.ofNullable(var1.getTrimAllowed().getInitialTrim())
            .ifPresent(
                v -> {
                  if (!v.isEmpty()) trimAllowed.setInitialTrim(new BigDecimal(v));
                });
        Optional.ofNullable(var1.getTrimAllowed().getMaximumTrim())
            .ifPresent(
                v -> {
                  if (!v.isEmpty()) trimAllowed.setMaximumTrim(new BigDecimal(v));
                });
        Optional.ofNullable(var1.getTrimAllowed().getFinalTrim())
            .ifPresent(
                v -> {
                  if (!v.isEmpty()) trimAllowed.setFinalTrim(new BigDecimal(v));
                });
        var.setTrimAllowed(trimAllowed);
      }

      // For Sunrise and Sunset, 1st  call to LS
      LoadableStudy.LoadingSynopticResponse response =
          this.loadingPlanGrpcService.fetchSynopticRecordForPortRotationArrivalCondition(portRId);
      var.setTimeOfSunrise(
          response.getTimeOfSunrise().isEmpty() ? null : response.getTimeOfSunrise());
      var.setTimeOfSunset(response.getTimeOfSunset().isEmpty() ? null : response.getTimeOfSunset());

      // If not found in LS, Synoptic Go to Port Master
      if (var.getTimeOfSunrise() == null || var.getTimeOfSunset() == null) {
        PortInfo.PortDetail response2 = this.loadingPlanGrpcService.fetchPortDetailByPortId(portId);
        var.setTimeOfSunrise(response2.getSunriseTime());
        var.setTimeOfSunset(response2.getSunsetTime());
        log.info(
            "Get Loading info, Sunrise/Sunset added from Port Info table {}, {}",
            response2.getSunriseTime(),
            response2.getSunsetTime());
      } else {
        log.info("Get Synoptic Table, Port Rotation Id {}", portRId);
      }
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    return var;
  }

  // Call in VesselInfoService
  @Override
  public LoadingRates getLoadingRateForVessel(LoadingPlanModels.LoadingRates var1, Long vesselId) {
    LoadingRates loadingRates = new LoadingRates();
    try {
      loadingRates.setInitialLoadingRate(
          var1.getInitialLoadingRate().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getInitialLoadingRate()));
      loadingRates.setMaxLoadingRate(
          var1.getMaxLoadingRate().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getMaxLoadingRate()));
      loadingRates.setReducedLoadingRate(
          var1.getReducedLoadingRate().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getReducedLoadingRate()));
      loadingRates.setMinDeBallastingRate(
          var1.getMinDeBallastingRate().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getMinDeBallastingRate()));
      loadingRates.setMaxDeBallastingRate(
          var1.getMaxDeBallastingRate().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getMaxDeBallastingRate()));
      loadingRates.setNoticeTimeStopLoading(
          var1.getNoticeTimeStopLoading().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getNoticeTimeStopLoading()));
      loadingRates.setNoticeTimeRateReduction(
          var1.getNoticeTimeRateReduction().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getNoticeTimeRateReduction()));
      loadingRates.setLineContentRemaining(
          var1.getLineContentRemaining().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(var1.getLineContentRemaining()));
      log.info("Loading Rates added from Loading plan Service");
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to cast loading rates");
    }
    return loadingRates;
  }

  @Override
  public List<BerthDetails> getMasterBerthDetailsByPortId(Long portId) {
    PortInfo.BerthInfoResponse response = this.portInfoService.getBerthInfoByPortId(portId);
    List<BerthDetails> berthDetails = new ArrayList<>();
    if (response != null && !response.getBerthsList().isEmpty()) {
      try {
        for (PortInfo.BerthDetail bd : response.getBerthsList()) {
          BerthDetails dto = new BerthDetails();
          Optional.ofNullable(bd.getId()).ifPresent(dto::setBerthId);
          Optional.ofNullable(bd.getPortId()).ifPresent(dto::setPortId);
          // Optional.ofNullable(bd.getId()).ifPresent(dto::setLoadingInfoId);
          dto.setMaxShpChannel(
              bd.getMaxShipChannel().isEmpty()
                  ? BigDecimal.ZERO
                  : new BigDecimal(bd.getMaxShipChannel()));
          Optional.ofNullable(bd.getBerthName()).ifPresent(dto::setBerthName);
          // Optional.ofNullable(bd.getId()).ifPresent(dto::setLoadingBerthId);

          dto.setMaxShipDepth(
              bd.getMaxShipDepth().isEmpty()
                  ? BigDecimal.ZERO
                  : new BigDecimal(bd.getMaxShipDepth()));
          dto.setMaxShipDepth(
              bd.getSeaDraftLimitation().isEmpty()
                  ? BigDecimal.ZERO
                  : new BigDecimal(bd.getSeaDraftLimitation()));
          dto.setMaxShipDepth(
              bd.getSeaDraftLimitation().isEmpty()
                  ? BigDecimal.ZERO
                  : new BigDecimal(bd.getSeaDraftLimitation()));
          dto.setMaxShipDepth(
              bd.getMaxManifoldHeight().isEmpty()
                  ? BigDecimal.ZERO
                  : new BigDecimal(bd.getMaxManifoldHeight()));
          Optional.ofNullable(bd.getRegulationAndRestriction())
              .ifPresent(dto::setRegulationAndRestriction);
          dto.setMaxShipDepth(
              bd.getMaxLoa().isEmpty() ? BigDecimal.ZERO : new BigDecimal(bd.getMaxLoa()));
          dto.setMaxShipDepth(
              bd.getMaxDraft().isEmpty() ? BigDecimal.ZERO : new BigDecimal(bd.getMaxDraft()));
          berthDetails.add(dto);
        }
      } catch (Exception e) {
        log.error("Failed to process berth details");
        e.printStackTrace();
      }
      log.info("Get Loading Info, Berth details added, Berth list Size {}", berthDetails.size());
    }
    return berthDetails;
  }

  @Override
  public List<BerthDetails> buildLoadingPlanBerthDetails(
      List<LoadingPlanModels.LoadingBerths> var1) {
    List<BerthDetails> list = new ArrayList<>();
    for (LoadingPlanModels.LoadingBerths lb : var1) {
      BerthDetails var2 = new BerthDetails();
      var2.setLoadingBerthId(lb.getId());
      var2.setLoadingInfoId(lb.getLoadingInfoId());
      var2.setBerthId(lb.getBerthId());
      var2.setMaxShipDepth(
          lb.getDepth().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lb.getDepth()));
      var2.setSeaDraftLimitation(
          lb.getSeaDraftLimitation().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(lb.getSeaDraftLimitation()));
      var2.setAirDraftLimitation(
          lb.getAirDraftLimitation().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(lb.getAirDraftLimitation()));
      var2.setMaxManifoldHeight(
          lb.getMaxManifoldHeight().isEmpty()
              ? BigDecimal.ZERO
              : new BigDecimal(lb.getMaxManifoldHeight()));
      var2.setRegulationAndRestriction(lb.getDepth());
      var2.setHoseConnections(lb.getHoseConnections());
      list.add(var2);
    }
    log.info("Loading Plan Berth data added Size {}", var1.size());
    return list;
  }

  @Override
  public CargoMachineryInUse getCargoMachinesInUserFromVessel(
      List<LoadingPlanModels.LoadingMachinesInUse> var1, Long vesselId) {
    VesselInfo.VesselPumpsResponse grpcReply =
        vesselInfoService.getVesselPumpsFromVesselInfo(vesselId);
    CargoMachineryInUse machineryInUse = new CargoMachineryInUse();
    if (grpcReply != null) {
      try {
        List<PumpType> pumpTypes = new ArrayList<>();
        if (grpcReply.getPumpTypeCount() > 0) {
          for (VesselInfo.PumpType vpy : grpcReply.getPumpTypeList()) {
            PumpType type = new PumpType();
            BeanUtils.copyProperties(vpy, type);
            pumpTypes.add(type);
          }
        }
        List<VesselPump> vesselPumps = new ArrayList<>();
        if (grpcReply.getVesselPumpCount() > 0) {
          for (VesselInfo.VesselPump vp : grpcReply.getVesselPumpList()) {
            VesselPump pump = new VesselPump();
            BeanUtils.copyProperties(vp, pump);
            vesselPumps.add(pump);
          }
        }
        machineryInUse.setPumpTypes(pumpTypes);
        machineryInUse.setVesselPumps(vesselPumps);
        log.info(
            "Get loading info, Cargo machines Pump List Size {}, Type Size {} from Vessel Info",
            vesselPumps.size(),
            pumpTypes.size());
      } catch (Exception e) {
        log.error("Failed to process Vessel Pumps");
        e.printStackTrace();
      }
    }

    if (!var1.isEmpty()) {
      List<LoadingMachinesInUse> list2 = new ArrayList<>();
      for (LoadingPlanModels.LoadingMachinesInUse lm : var1) {
        LoadingMachinesInUse var2 = new LoadingMachinesInUse();
        var2.setId(lm.getId());
        var2.setPumpId(lm.getPumpId());
        var2.setLoadingInfoId(lm.getLoadingInfoId());
        var2.setCapacity(
            lm.getCapacity().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lm.getCapacity()));
        list2.add(var2);
      }
      log.info("Loading plan machine in use added, Size {}", var1.size());
      machineryInUse.setLoadingMachinesInUses(list2);
    }
    return machineryInUse;
  }

  @Override
  public LoadingStages getLoadingStagesAndMasters(LoadingPlanModels.LoadingStages var1) {
    LoadingStages loadingStages = new LoadingStages();
    try {
      BeanUtils.copyProperties(var1, loadingStages);
      List<StageOffset> list1 = new ArrayList<>();
      List<StageDuration> list2 = new ArrayList<>();
      for (LoadingPlanModels.StageOffsets val1 : var1.getStageOffsetsList()) {
        StageOffset stageOffset = new StageOffset();
        Optional.ofNullable(val1.getId()).ifPresent(stageOffset::setId);
        Optional.ofNullable(val1.getStageOffsetVal()).ifPresent(stageOffset::setStageOffsetVal);
        list1.add(stageOffset);
      }
      for (LoadingPlanModels.StageDuration val1 : var1.getStageDurationsList()) {
        StageDuration duration = new StageDuration();
        Optional.ofNullable(val1.getId()).ifPresent(duration::setId);
        Optional.ofNullable(val1.getDuration()).ifPresent(duration::setDuration);
        list2.add(duration);
      }
      loadingStages.setStageOffsetList(list1);
      loadingStages.setStageDurationList(list2);
      log.info(
          "Loading Plan Stages added Stage Id {}, Offset {}, Duration {}",
          var1.getId(),
          var1.getStageOffset(),
          var1.getStageDuration());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return loadingStages;
  }

  @Override
  public List<ToppingOffSequence> getToppingOffSequence(
      List<LoadingPlanModels.LoadingToppingOff> list1) {
    List<ToppingOffSequence> list2 = new ArrayList<>();
    try {
      for (LoadingPlanModels.LoadingToppingOff var1 : list1) {
        ToppingOffSequence var2 = new ToppingOffSequence();
        Optional.ofNullable(var1.getId()).ifPresent(var2::setId);
        Optional.ofNullable(var1.getLoadingInfoId()).ifPresent(var2::setLoadingInfoId);
        Optional.ofNullable(var1.getOrderNumber()).ifPresent(var2::setOrderNumber);
        Optional.ofNullable(var1.getTankId()).ifPresent(var2::setTankId);
        Optional.ofNullable(var1.getCargoId()).ifPresent(var2::setCargoId);
        Optional.ofNullable(var1.getCargoName()).ifPresent(var2::setCargoName);
        Optional.ofNullable(var1.getCargoAbbreviation()).ifPresent(var2::setCargoAbbreviation);
        Optional.ofNullable(var1.getColourCode()).ifPresent(var2::setColourCode);
        Optional.ofNullable(var1.getRemark()).ifPresent(var2::setRemark);
        var2.setUllage(
            var1.getUllage().isEmpty() ? BigDecimal.ZERO : new BigDecimal(var1.getUllage()));
        var2.setQuantity(
            var1.getQuantity().isEmpty() ? BigDecimal.ZERO : new BigDecimal(var1.getQuantity()));
        var2.setFillingRatio(
            var1.getFillingRatio().isEmpty()
                ? BigDecimal.ZERO
                : new BigDecimal(var1.getFillingRatio()));
        var2.setApi(var1.getApi().isEmpty() ? BigDecimal.ZERO : new BigDecimal(var1.getApi()));
        var2.setTemperature(
            var1.getApi().isEmpty() ? BigDecimal.ZERO : new BigDecimal(var1.getTemperature()));
        Optional.ofNullable(var1.getDisplayOrder()).ifPresent(var2::setDisplayOrder);
        list2.add(var2);
        log.info("Loading Plan Topping off list Id {}", var1.getId());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list2;
  }

  @Override
  public List<LoadableQuantityCargoDetails> getLoadablePlanCargoDetailsByPort(
      Long patternId, String operationType, Long portRotationId, Long portId) {
    List<LoadableStudy.LoadableQuantityCargoDetails> list =
        this.loadingPlanGrpcService.fetchLoadablePlanCargoDetails(
            patternId, operationType, portRotationId, portId);
    return this.buildLoadablePlanQuantity(list);
  }

  @Override
  public LoadingSequences getLoadingSequence(LoadingPlanModels.LoadingDelay loadingDelay) {
    LoadingSequences loadingSequences = new LoadingSequences();
    List<ReasonForDelay> reasonForDelays = new ArrayList<>();
    for (LoadingPlanModels.DelayReasons var2 : loadingDelay.getReasonsList()) {
      ReasonForDelay val1 = new ReasonForDelay();
      BeanUtils.copyProperties(var2, val1);
      reasonForDelays.add(val1);
    }
    List<LoadingDelays> loadingDelays = new ArrayList<>();
    for (LoadingPlanModels.LoadingDelays var2 : loadingDelay.getDelaysList()) {
      LoadingDelays val1 = new LoadingDelays();
      Optional.ofNullable(var2.getDuration())
          .ifPresent(
              v -> {
                if (!v.isEmpty()) val1.setDuration(new BigDecimal(v));
              });
      BeanUtils.copyProperties(var2, val1);
      loadingDelays.add(val1);
    }
    loadingSequences.setReasonForDelays(reasonForDelays);
    loadingSequences.setLoadingDelays(loadingDelays);
    log.info(
        "manage sequence data added from  loading plan, Size {}", loadingDelay.getDelaysCount());
    return loadingSequences;
  }

  private List<com.cpdss.gateway.domain.LoadableQuantityCargoDetails> buildLoadablePlanQuantity(
      List<LoadableStudy.LoadableQuantityCargoDetails> list) {
    List<com.cpdss.gateway.domain.LoadableQuantityCargoDetails> response = new ArrayList<>();
    log.info("Cargo to be loaded data from LS, Size {}", list.size());
    for (LoadableStudy.LoadableQuantityCargoDetails lqcd : list) {
      com.cpdss.gateway.domain.LoadableQuantityCargoDetails cargoDetails =
          new com.cpdss.gateway.domain.LoadableQuantityCargoDetails();
      cargoDetails.setDifferenceColor(lqcd.getDifferenceColor());
      cargoDetails.setDifferencePercentage(lqcd.getDifferencePercentage());
      cargoDetails.setEstimatedAPI(lqcd.getEstimatedAPI());
      cargoDetails.setEstimatedTemp(lqcd.getEstimatedTemp());
      cargoDetails.setGrade(lqcd.getGrade());
      cargoDetails.setId(lqcd.getId());

      cargoDetails.setLoadableBbls60f(lqcd.getLoadableBbls60F());
      cargoDetails.setLoadableBblsdbs(lqcd.getLoadableBblsdbs());
      cargoDetails.setLoadableKL(lqcd.getLoadableKL());
      cargoDetails.setLoadableLT(lqcd.getLoadableLT());
      cargoDetails.setLoadableMT(lqcd.getLoadableMT());

      cargoDetails.setMaxTolerence(lqcd.getMaxTolerence());
      cargoDetails.setMinTolerence(lqcd.getMinTolerence());
      cargoDetails.setOrderBbls60f(lqcd.getOrderBbls60F());
      cargoDetails.setOrderBblsdbs(lqcd.getOrderBblsdbs());
      cargoDetails.setOrderedQuantity(lqcd.getOrderedMT());

      cargoDetails.setSlopQuantity(lqcd.getSlopQuantity());
      cargoDetails.setTimeRequiredForLoading(lqcd.getTimeRequiredForLoading());

      cargoDetails.setCargoNominationTemperature(lqcd.getCargoNominationTemperature());
      cargoDetails.setCargoId(lqcd.getCargoId());
      cargoDetails.setCargoAbbreviation(lqcd.getCargoAbbreviation());
      cargoDetails.setColorCode(lqcd.getColorCode());
      cargoDetails.setPriority(lqcd.getPriority());
      cargoDetails.setLoadingOrder(lqcd.getLoadingOrder());

      cargoDetails.setOrderQuantity(lqcd.getOrderQuantity());
      cargoDetails.setCargoNominationQuantity(lqcd.getCargoNominationQuantity());

      response.add(cargoDetails);
    }
    return response;
  }

  @Override
  public LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException {
    try {
      log.info("Calling saveLoadingInformation in loading-plan microservice via GRPC");
      LoadingInformation loadingInformation =
          loadingInfoBuilderService.buildLoadingInformation(request);
      ResponseStatus response =
          this.loadingPlanGrpcService.saveLoadingInformation(loadingInformation);
      if (response.getStatus().equalsIgnoreCase(SUCCESS)) {
        // Updating synoptical table
        this.updateSynopticalTable(request.getLoadingDetails(), request.getSynopticalTableId());
        return buildLoadingInformationResponse(correlationId);
      } else {
        log.error("Failed to save LoadingInformation {}", request.getLoadingInfoId());
        throw new GenericServiceException(
            "Failed to save Loading Information",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to save LoadingInformation {}", request.getLoadingInfoId());
      e.printStackTrace();
      throw new GenericServiceException(
          "Failed to save Loading Information",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * Call to algo, process it save at Loading Plan Service 1. Go to LS to call algo 2. Save the
   * response at Loading plan service 3. Respond to caller with updated value€
   *
   * @param updateUllage
   * @return
   */
  @Override
  public UpdateUllage processUpdateUllage(
      Long vesselId,
      Long voyageId,
      Long loadingInfoId,
      Long portRotationId,
      UpdateUllage updateUllage,
      String correlationId)
      throws GenericServiceException {

    // get Active voyage
    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    if (activeVoyage == null) {
      log.error("Update Ullage, No active voyage found");
      throw new GenericServiceException(
          "No active voyage found",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Update Ullage, Active voyage found: Id {}", activeVoyage.getId());

    // Build grpc request to LS
    LoadableStudy.UpdateUllageRequest.Builder grpcRequestToLS =
        LoadableStudy.UpdateUllageRequest.newBuilder();
    grpcRequestToLS.setUpdateUllageForLoadingPlan(true); // to skip save to Temp table in LS
    this.loadableStudyService.buildUpdateUllageRequest(
        updateUllage, activeVoyage.getPatternId(), grpcRequestToLS);

    // Call to LS for process
    LoadableStudy.UpdateUllageReply grpcReplyFromLS =
        this.loadableStudyService.updateUllage(grpcRequestToLS.build());

    if (!SUCCESS.equals(grpcReplyFromLS.getResponseStatus().getStatus())) {
      log.error("Update Ullage, Failed to call Algo");
      throw new GenericServiceException(
          "Failed in confirmPlanStatus from grpc service",
          grpcReplyFromLS.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(grpcReplyFromLS.getResponseStatus().getHttpStatusCode())));
    }

    log.info("Update Ullage, Success from LS, Algo api");

    // Call to LP for save at table
    LoadingPlanModels.UpdateUllageLoadingRequest.Builder grpcRequestLP =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder();
    grpcRequestLP.setLoadingInfoId(loadingInfoId);
    grpcRequestLP.setVesselId(vesselId);
    grpcRequestLP.setVoyageId(voyageId);
    grpcRequestLP.setPortRotationId(portRotationId);
    grpcRequestLP.setCargoToppingOffId(
        updateUllage.getId()); // Primary key for Table cargo_topping_off_sequence

    grpcRequestLP.setTankId(updateUllage.getTankId());
    grpcRequestLP.setCargoId(
        grpcReplyFromLS.getLoadablePlanStowageDetails().getCargoNominationId());
    grpcRequestLP.setFillingRatio(
        grpcReplyFromLS.getLoadablePlanStowageDetails().getFillingRatio());
    grpcRequestLP.setCorrectedUllage(
        grpcReplyFromLS.getLoadablePlanStowageDetails().getCorrectedUllage());
    grpcRequestLP.setCorrectionFactor(
        grpcReplyFromLS.getLoadablePlanStowageDetails().getCorrectionFactor());
    grpcRequestLP.setQuantity(grpcReplyFromLS.getLoadablePlanStowageDetails().getWeight());
    Boolean updatedAtLoadingPlan =
        this.loadingPlanGrpcService.updateUllageAtLoadingPlan(grpcRequestLP.build());
    if (!updatedAtLoadingPlan) {
      log.error("Update Ullage, Failed to update Loading information");
      throw new GenericServiceException(
          "Update ullage at Loading Plan Failed!",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Update Ullage, Success save at Loading Info DB");
    // Return updated value
    return this.loadableStudyService.buildeUpdateUllageResponse(grpcReplyFromLS, correlationId);
  }

  /**
   * Updates Synoptical table
   *
   * @param loadingDetails
   * @param synopticalId
   * @throws Exception
   */
  private void updateSynopticalTable(
      com.cpdss.gateway.domain.loadingplan.LoadingDetails loadingDetails, Long synopticalId)
      throws Exception {
    this.loadableStudyService.saveLoadingInfoToSynopticalTable(
        synopticalId, loadingDetails.getTimeOfSunrise(), loadingDetails.getTimeOfSunset());
  }

  LoadingInformationResponse buildLoadingInformationResponse(String correlationId) {
    LoadingInformationResponse response = new LoadingInformationResponse();
    CommonSuccessResponse successResponse =
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId);
    response.setResponseStatus(successResponse);
    return response;
  }

  @Override
  public LoadingInfoAlgoResponse generateLoadingPlan(Long infoId) throws GenericServiceException {
    try {
      log.info("Calling generateLoadingPlan in loading-plan microservice via GRPC");
      LoadingInfoAlgoResponse algoResponse = new LoadingInfoAlgoResponse();
      ResponseStatus response = this.loadingPlanGrpcService.generateLoadingPlan(infoId);
      if (response.getStatus().equalsIgnoreCase(SUCCESS)) {
        CommonSuccessResponse successResponse = new CommonSuccessResponse("SUCCESS", "");
        algoResponse.setResponseStatus(successResponse);
        return algoResponse;
      } else {
        log.error("Failed to save LoadingInformation {}", infoId);
        throw new GenericServiceException(
            "Failed to save Loading Information",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to save LoadingInformation {}", infoId);
      throw new GenericServiceException(
          "Failed to generate Loading Plan",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }
}
