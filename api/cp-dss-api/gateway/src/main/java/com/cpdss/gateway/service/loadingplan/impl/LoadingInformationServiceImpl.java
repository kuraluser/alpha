/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import com.cpdss.gateway.service.PortInfoService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired VesselInfoService vesselInfoService;

  @Autowired PortInfoService portInfoService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

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
            .ifPresent(v -> trimAllowed.setInitialTrim(new BigDecimal(v)));
        Optional.ofNullable(var1.getTrimAllowed().getMaximumTrim())
            .ifPresent(v -> trimAllowed.setMaximumTrim(new BigDecimal(v)));
        Optional.ofNullable(var1.getTrimAllowed().getFinalTrim())
            .ifPresent(v -> trimAllowed.setFinalTrim(new BigDecimal(v)));
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
          Optional.ofNullable(bd.getId()).ifPresent(dto::setId);
          Optional.ofNullable(bd.getPortId()).ifPresent(dto::setPortId);
          // Optional.ofNullable(bd.getId()).ifPresent(dto::setLoadingInfoId);
          dto.setMaxShpChannel(
              bd.getMaxShipChannel().isEmpty()
                  ? BigDecimal.ZERO
                  : new BigDecimal(bd.getMaxShipChannel()));
          Optional.ofNullable(bd.getBerthName()).ifPresent(dto::setBerthName);
          Optional.ofNullable(bd.getId()).ifPresent(dto::setLoadingBerthId);

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
      var2.setId(lb.getBerthId());
      var2.setMaxShipDepth(
          lb.getDepth().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lb.getDepth()));
      var2.setSeaDraftLimitation(
          lb.getDepth().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lb.getDepth()));
      var2.setAirDraftLimitation(
          lb.getDepth().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lb.getDepth()));
      var2.setMaxManifoldHeight(
          lb.getDepth().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lb.getDepth()));
      var2.setRegulationAndRestriction(lb.getDepth());
      list.add(var2);
    }
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
        list2.add(var2);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list2;
  }
}
