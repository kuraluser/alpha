/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingRates;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import com.cpdss.gateway.service.PortInfoService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
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

  /**
   * Collect from Synoptic Table in LS
   *
   * @param vesselId Long
   * @param voyageId Long
   * @param portRId Long
   * @return
   */
  @Override
  public LoadingDetails getLoadingDetailsByPortRotationId(
      Long vesselId, Long voyageId, Long portRId) {
    return null;
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
      } catch (Exception e) {
        log.error("Failed to process Vessel Pumps");
        e.printStackTrace();
      }
    }
    return machineryInUse;
  }
}
