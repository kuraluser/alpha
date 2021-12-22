/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import com.cpdss.loadingplan.repository.LoadingMachineryInUseRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class LoadingMachineInUseService {
  @Autowired LoadingMachineryInUseRepository loadingMachineryInUseRepository;

  /**
   * To save Machines of Loading plan
   *
   * @param loadInfo
   * @param rpcReplay
   */
  public void saveMachineInUseByLoadingInfo(
      LoadingInformation loadInfo, VesselInfo.VesselPumpsResponse rpcReplay) {
    log.info("Save All machines in DS Info Id - {}", loadInfo.getId());
    List<LoadingMachineryInUse> newMachines = new ArrayList<>();
    // Save vessel pump
    for (var pump : rpcReplay.getVesselPumpList()) {
      if (pump.getId() != 0) {
        LoadingMachineryInUse inUse = new LoadingMachineryInUse();
        inUse.setIsUsing(true);
        inUse.setIsActive(true);
        inUse.setMachineXId(pump.getId());
        inUse.setMachineTypeXid(Common.MachineType.VESSEL_PUMP_VALUE);
        inUse.setCapacity(
            pump.getPumpCapacity().isEmpty()
                ? BigDecimal.ZERO
                : new BigDecimal(pump.getPumpCapacity()));
        inUse.setLoadingInformation(loadInfo);
        newMachines.add(inUse);
      }
    }

    // Save manifolds
    for (var mani : rpcReplay.getVesselManifoldList()) {
      if (mani.getId() != 0) {
        LoadingMachineryInUse inUse = new LoadingMachineryInUse();
        inUse.setIsUsing(true);
        inUse.setIsActive(true);
        inUse.setMachineXId(mani.getId());
        inUse.setMachineTypeXid(Common.MachineType.MANIFOLD_VALUE);
        inUse.setCapacity(BigDecimal.ZERO);
        inUse.setLoadingInformation(loadInfo);
        newMachines.add(inUse);
      }
    }

    // Save bottom lines
    for (var bott : rpcReplay.getVesselBottomLineList()) {
      if (bott.getId() != 0) {
        LoadingMachineryInUse inUse = new LoadingMachineryInUse();
        inUse.setIsUsing(true);
        inUse.setIsActive(true);
        inUse.setMachineXId(bott.getId());
        inUse.setMachineTypeXid(Common.MachineType.BOTTOM_LINE_VALUE);
        inUse.setCapacity(BigDecimal.ZERO);
        inUse.setLoadingInformation(loadInfo);
        newMachines.add(inUse);
      }
    }
    loadingMachineryInUseRepository.saveAll(newMachines);
    log.info("Total machines saves - {}", newMachines.size());
  }
}
