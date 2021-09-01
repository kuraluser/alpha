/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.dischargeplan.CargoForCowDetails;
import com.cpdss.gateway.domain.dischargeplan.DischargeRates;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationBuilderService {

  @Autowired VesselInfoService vesselInfoService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  public LoadingDetails buildDischargeDetailFromMessage(
      DischargeDetails var1, Long portId, Long portRId) throws GenericServiceException {
    LoadingDetails var2 = new LoadingDetails();
    LoadableStudy.LoadingSynopticResponse var3 =
        loadingPlanGrpcService.fetchSynopticRecordForPortRotation(
            portRId, GatewayConstants.OPERATION_TYPE_ARR);
    if (!var3.getTimeOfSunrise().isEmpty()) {
      var2.setTimeOfSunrise(var3.getTimeOfSunrise());
    } else {
      var2.setTimeOfSunrise(var1.getTimeOfSunrise());
    }

    if (!var3.getTimeOfSunset().isEmpty()) {
      var2.setTimeOfSunset(var3.getTimeOfSunset());
    } else {
      var2.setTimeOfSunset(var1.getTimeOfSunset());
    }
    // If not found in LS, Synoptic Go to Port Master
    if (var2.getTimeOfSunrise() == null || var2.getTimeOfSunset() == null) {
      PortInfo.PortDetail response2 = this.loadingPlanGrpcService.fetchPortDetailByPortId(portId);
      var2.setTimeOfSunrise(response2.getSunriseTime());
      var2.setTimeOfSunset(response2.getSunsetTime());
      log.info(
          "Get Loading info, Sunrise/Sunset added from Port Info table {}, {}",
          response2.getSunriseTime(),
          response2.getSunsetTime());
    } else {
      log.info("Get Synoptic Table, Port Rotation Id {}", portRId);
    }

    if (!var1.getStartTime().isEmpty()) {
      var2.setStartTime(var1.getStartTime());
    }

    if (var1.hasTrimAllowed()) { // TO DO get trim data from admin rule
      TrimAllowed trimAllowed = new TrimAllowed();
      if (!var1.getTrimAllowed().getInitialTrim().isEmpty()) {
        trimAllowed.setInitialTrim(new BigDecimal(var1.getTrimAllowed().getInitialTrim()));
      }
      if (!var1.getTrimAllowed().getMaximumTrim().isEmpty()) {
        trimAllowed.setMaximumTrim(new BigDecimal(var1.getTrimAllowed().getMaximumTrim()));
      }
      if (!var1.getTrimAllowed().getFinalTrim().isEmpty()) {
        trimAllowed.setFinalTrim(new BigDecimal(var1.getTrimAllowed().getFinalTrim()));
      }
      var2.setTrimAllowed(trimAllowed);
    }
    return var2;
  }

  public DischargeRates buildDischargeRatesFromMessage(
      com.cpdss.common.generated.discharge_plan.DischargeRates var1) {
    DischargeRates var2 = new DischargeRates();
    if (var1.getInitialDischargeRate().isEmpty()) {
      // get admin data or default value
    } else {
      var2.setInitialDischargingRate(new BigDecimal(var1.getInitialDischargeRate()));
    }

    if (var1.getMaxDischargeRate().isEmpty()) {
      // get admin data or default value
    } else {
      var2.setMaxDischargingRate(new BigDecimal(var1.getMaxDischargeRate()));
    }

    if (var1.getMinBallastRate().isEmpty()) {
      // get admin data or default value
    } else {
      var2.setMinBallastRate(new BigDecimal(var1.getMaxBallastRate()));
    }

    if (var1.getMaxBallastRate().isEmpty()) {
      // get admin data or default value
    } else {
      var2.setMaxBallastRate(new BigDecimal(var1.getMaxBallastRate()));
    }
    return var2;
  }

  public List<BerthDetails> buildDischargeBerthsFromMessage(List<DischargeBerths> var1) {
    List<BerthDetails> list = new ArrayList<>();
    for (DischargeBerths lb : var1) {
      BerthDetails var2 = new BerthDetails();
      var2.setLoadingBerthId(lb.getId());
      var2.setLoadingInfoId(lb.getDischargeInfoId());
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
      var2.setRegulationAndRestriction(lb.getSpecialRegulationRestriction());
      var2.setItemsToBeAgreedWith(lb.getItemsToBeAgreedWith());
      var2.setHoseConnections(lb.getHoseConnections());
      var2.setLineDisplacement(lb.getLineDisplacement());
      var2.setAirPurge(lb.getAirPurge());
      var2.setCargoCirculation(lb.getCargoCirculation());
      var2.setMaxManifoldPressure(lb.getMaxManifoldPressure());
      list.add(var2);
    }
    log.info("Loading Plan Berth data added Size {}", var1.size());
    return list;
  }

  public CargoMachineryInUse buildDischargeMachinesFromMessage(
      List<LoadingPlanModels.LoadingMachinesInUse> var1, Long vesselId) {
    VesselInfo.VesselPumpsResponse grpcReply =
        vesselInfoService.getVesselPumpsFromVesselInfo(vesselId);
    CargoMachineryInUse machineryInUse = new CargoMachineryInUse();

    // Setting master data
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
            // created an array of ids from constants.
            if (GatewayConstants.DISCHARGING_VESSEL_PUMPS_VAL.contains(vp.getPumpTypeId())) {
              VesselPump pump = new VesselPump();
              BeanUtils.copyProperties(vp, pump);
              Optional.ofNullable(vp.getPumpCapacity())
                  .ifPresent(v -> pump.setPumpCapacity(new BigDecimal(v)));
              pump.setMachineType(Common.MachineType.VESSEL_PUMP_VALUE);
              vesselPumps.add(pump);
            }
          }
        }
        machineryInUse.setPumpTypes(pumpTypes);
        machineryInUse.setVesselPumps(vesselPumps);

        if (!grpcReply.getVesselManifoldList().isEmpty()) {
          List<VesselComponent> list1 = new ArrayList<>();
          for (VesselInfo.VesselComponent vc : grpcReply.getVesselManifoldList()) {
            VesselComponent vcDto = new VesselComponent();
            vcDto.setId(vc.getId());
            vcDto.setComponentCode(vc.getComponentCode());
            vcDto.setComponentName(vc.getComponentName());
            vcDto.setVesselId(vc.getVesselId());
            vcDto.setComponentType(vc.getComponentType());
            vcDto.setMachineTypeId(Common.MachineType.MANIFOLD_VALUE);
            list1.add(vcDto);
          }
          machineryInUse.setVesselManifold(list1);
        }

        if (!grpcReply.getVesselBottomLineList().isEmpty()) {
          List<VesselComponent> list2 = new ArrayList<>();
          for (VesselInfo.VesselComponent vc : grpcReply.getVesselBottomLineList()) {
            VesselComponent vcDto = new VesselComponent();
            vcDto.setId(vc.getId());
            vcDto.setComponentCode(vc.getComponentCode());
            vcDto.setComponentName(vc.getComponentName());
            vcDto.setVesselId(vc.getVesselId());
            vcDto.setComponentType(vc.getComponentType());
            vcDto.setMachineTypeId(Common.MachineType.BOTTOM_LINE_VALUE);
            list2.add(vcDto);
          }
          machineryInUse.setVesselBottomLine(list2);
        }

        if (!grpcReply.getTankTypeList().isEmpty()) {
          List<PumpType> tankTypes = new ArrayList<>();
          for (VesselInfo.TankType type : grpcReply.getTankTypeList()) {
            PumpType type1 = new PumpType();
            type1.setId(type.getId());
            type1.setName(type.getTypeName());
            tankTypes.add(type1);
          }
          machineryInUse.setTankTypes(tankTypes);
        }

        log.info(
            "Get loading info, Cargo machines Pump List Size {}, Type Size {} from Vessel Info",
            vesselPumps.size(),
            pumpTypes.size());
      } catch (Exception e) {
        log.error("Failed to process Vessel Pumps");
        e.printStackTrace();
      }
    }

    // Setting Loading info Data
    if (!var1.isEmpty()) {
      List<LoadingMachinesInUse> list2 = new ArrayList<>();
      for (LoadingPlanModels.LoadingMachinesInUse lm : var1) {
        LoadingMachinesInUse var2 = new LoadingMachinesInUse();
        var2.setId(lm.getId());
        var2.setMachineId(lm.getMachineId());
        var2.setLoadingInfoId(lm.getLoadingInfoId());
        var2.setMachineTypeId(lm.getMachineType().getNumber());
        var2.setCapacity(
            lm.getCapacity().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lm.getCapacity()));
        var2.setIsUsing(lm.getIsUsing());
        list2.add(var2);
      }
      log.info("Loading plan machine in use added, Size {}", var1.size());
      machineryInUse.setLoadingMachinesInUses(list2);
    }
    return machineryInUse;
  }

  public LoadingSequences buildDischargeSequencesAndDelayFromMessage(
      DischargeDelay dischargeDelay) {
    LoadingSequences loadingSequences = new LoadingSequences();
    List<ReasonForDelay> reasonForDelays = new ArrayList<>();
    for (LoadingPlanModels.DelayReasons var2 : dischargeDelay.getReasonsList()) {
      ReasonForDelay val1 = new ReasonForDelay();
      BeanUtils.copyProperties(var2, val1);
      reasonForDelays.add(val1);
    }
    List<LoadingDelays> loadingDelays = new ArrayList<>();
    for (DischargeDelays var2 : dischargeDelay.getDelaysList()) {
      LoadingDelays val1 = new LoadingDelays();
      Optional.ofNullable(var2.getDuration())
          .ifPresent(
              v -> {
                if (!v.isEmpty()) val1.setDuration(new BigDecimal(v));
              });
      Optional.ofNullable(var2.getQuantity())
          .ifPresent(
              v -> {
                if (!v.isEmpty()) val1.setQuantity(new BigDecimal(v));
              });
      BeanUtils.copyProperties(var2, val1);
      val1.setReasonForDelayIds(var2.getReasonForDelayIdsList());
      loadingDelays.add(val1);
    }
    loadingSequences.setReasonForDelays(reasonForDelays);
    loadingSequences.setLoadingDelays(loadingDelays);
    log.info(
        "manage sequence data added from  loading plan, Size {}", dischargeDelay.getDelaysCount());
    return loadingSequences;
  }

  public com.cpdss.gateway.domain.dischargeplan.CowPlan buildDischargeCowPlan(CowPlan cowPlan) {
    com.cpdss.gateway.domain.dischargeplan.CowPlan var1 =
        new com.cpdss.gateway.domain.dischargeplan.CowPlan();
    try {
      BeanUtils.copyProperties(cowPlan, var1);
      var topCow =
          cowPlan.getCowTankDetailsList().stream()
              .filter(v -> v.getCowType().equals(Common.COW_TYPE.TOP_COW))
              .map(CowTankDetails::getTankIdsList)
              .flatMap(Collection::stream)
              .collect(Collectors.toList());

      var bottomCow =
          cowPlan.getCowTankDetailsList().stream()
              .filter(v -> v.getCowType().equals(Common.COW_TYPE.BOTTOM_COW))
              .map(CowTankDetails::getTankIdsList)
              .flatMap(Collection::stream)
              .collect(Collectors.toList());

      var allCow =
          cowPlan.getCowTankDetailsList().stream()
              .filter(v -> v.getCowType().equals(Common.COW_TYPE.ALL_COW))
              .map(CowTankDetails::getTankIdsList)
              .flatMap(Collection::stream)
              .collect(Collectors.toList());

      var cargoCowTankList =
          cowPlan.getCowTankDetailsList().stream()
              .filter(v -> v.getCowType().equals(Common.COW_TYPE.CARGO))
              .map(v -> v.getCargoForCowList())
              .flatMap(Collection::stream)
              .map(
                  v -> {
                    return new CargoForCowDetails(
                        v.getCargoId(),
                        v.getCargoNominationId(),
                        v.getWashingCargoId(),
                        v.getWashingCargoNominationId(),
                        v.getTankIdsList());
                  })
              .collect(Collectors.toList());

      var1.setTopCow(topCow);
      var1.setBottomCow(bottomCow);
      var1.setAllCow(allCow);
      var1.setCargoCow(cargoCowTankList);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return var1;
  }
}
