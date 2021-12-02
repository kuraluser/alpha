/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingMachineryInUse;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingMachineryInUseRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class DischargingMachineryInUseService {

  @Autowired DischargingMachineryInUseRepository dischargingMachineryInUseRepository;
  @Autowired DischargeInformationRepository dischargingInformationRepository;

  public void saveDischargingMachineryList(
      List<LoadingMachinesInUse> saveRequest, DischargeInformation dischargingInformation)
      throws Exception {
    if (!saveRequest.isEmpty() && dischargingInformation != null) {
      for (LoadingMachinesInUse lm : saveRequest) {
        Optional<DischargingMachineryInUse> lmVar1 =
            this.dischargingMachineryInUseRepository
                .findByDischargingInformationAndMachineXidAndMachineTypeXidAndIsActiveTrue(
                    dischargingInformation, lm.getMachineId(), lm.getMachineTypeValue());
        if (lmVar1.isPresent()) {
          // Update Record
          lmVar1.get().setIsUsing(lm.getIsUsing());
          lmVar1
              .get()
              .setCapacity(
                  lm.getCapacity().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lm.getCapacity()));
          lmVar1.get().setIsActive(lm.getIsUsing()); // If it using, make it Ture else False.
        } else {
          // New Record
          lmVar1 = Optional.of(new DischargingMachineryInUse());
          lmVar1.get().setMachineXid(lm.getMachineId());
          lmVar1.get().setMachineTypeXid(lm.getMachineTypeValue());
          lmVar1.get().setIsUsing(lm.getIsUsing());
          lmVar1.get().setDischargingInformation(dischargingInformation);
          lmVar1
              .get()
              .setCapacity(
                  lm.getCapacity().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lm.getCapacity()));
          lmVar1.get().setIsActive(true); // New entry must be true
        }
        // Set capacity to 0, bug fix: DSS-4016
        if (Common.MachineType.MANIFOLD_VALUE == lm.getMachineTypeValue()
            || Common.MachineType.BOTTOM_LINE_VALUE == lm.getMachineTypeValue()) {
          lmVar1.get().setCapacity(BigDecimal.ZERO);
        }
        this.dischargingMachineryInUseRepository.save(lmVar1.get());
      }
    }
  }

  public void saveMachineInUseByDsInfo(
      DischargeInformation dsInfo, VesselInfo.VesselPumpsResponse rpcReplay) {
    log.info("Save All machines in DS Info Id - {}", dsInfo.getId());
    List<DischargingMachineryInUse> newMachines = new ArrayList<>();
    // Save vessel pump
    for (var pump : rpcReplay.getVesselPumpList()) {
      if (pump.getId() != 0) {
        DischargingMachineryInUse inUse = new DischargingMachineryInUse();
        inUse.setIsUsing(true);
        inUse.setIsActive(true);
        inUse.setMachineXid(pump.getId());
        inUse.setMachineTypeXid(Common.MachineType.VESSEL_PUMP_VALUE);
        inUse.setCapacity(
            pump.getPumpCapacity().isEmpty()
                ? BigDecimal.ZERO
                : new BigDecimal(pump.getPumpCapacity()));
        inUse.setDischargingInformation(dsInfo);
        newMachines.add(inUse);
      }
    }

    // Save manifolds
    for (var mani : rpcReplay.getVesselManifoldList()) {
      if (mani.getId() != 0) {
        DischargingMachineryInUse inUse = new DischargingMachineryInUse();
        inUse.setIsUsing(true);
        inUse.setIsActive(true);
        inUse.setMachineXid(mani.getId());
        inUse.setMachineTypeXid(Common.MachineType.MANIFOLD_VALUE);
        inUse.setCapacity(BigDecimal.ZERO);
        inUse.setDischargingInformation(dsInfo);
        newMachines.add(inUse);
      }
    }

    // Save bottom lines
    for (var bott : rpcReplay.getVesselBottomLineList()) {
      if (bott.getId() != 0) {
        DischargingMachineryInUse inUse = new DischargingMachineryInUse();
        inUse.setIsUsing(true);
        inUse.setIsActive(true);
        inUse.setMachineXid(bott.getId());
        inUse.setMachineTypeXid(Common.MachineType.BOTTOM_LINE_VALUE);
        inUse.setCapacity(BigDecimal.ZERO);
        inUse.setDischargingInformation(dsInfo);
        newMachines.add(inUse);
      }
    }
    dischargingMachineryInUseRepository.saveAll(newMachines);
    log.info("Total machines saves - {}", newMachines.size());
  }
}
