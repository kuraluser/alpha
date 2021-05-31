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
import java.util.ArrayList;
import java.util.List;
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
      BeanUtils.copyProperties(var1, var);
      if (var1.hasTrimAllowed()) {
        TrimAllowed trimAllowed = new TrimAllowed();
        BeanUtils.copyProperties(var1.getTrimAllowed(), trimAllowed);
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
  public LoadingRates getLoadingRateForVessel(Long vesselId) {
    return null;
  }

  @Override
  public List<BerthDetails> getBerthDetailsByPortId(Long portId) {
    PortInfo.BerthInfoResponse response = this.portInfoService.getBerthInfoByPortId(portId);
    List<BerthDetails> berthDetails = new ArrayList<>();
    if (response != null && !response.getBerthsList().isEmpty()) {
      try {
        for (PortInfo.BerthDetail bd : response.getBerthsList()) {
          BerthDetails dto = new BerthDetails();
          BeanUtils.copyProperties(bd, dto);
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
  public CargoMachineryInUse getCargoMachinesInUserFromVessel(Long vesselId) {
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
    return machineryInUse;
  }
}
