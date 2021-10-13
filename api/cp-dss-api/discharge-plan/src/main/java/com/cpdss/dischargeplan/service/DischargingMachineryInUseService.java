/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingMachineryInUse;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingMachineryInUseRepository;
import java.math.BigDecimal;
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
}
