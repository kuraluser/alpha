/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingMachineryInUseRepository;
import com.cpdss.loadingplan.service.LoadingMachineryInUseService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class LoadingMachineryInUseServiceImpl implements LoadingMachineryInUseService {

  @Autowired LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Override
  public void saveLoadingMachineryList(
      List<LoadingMachinesInUse> saveRequest, LoadingInformation loadingInformation)
      throws Exception {
    if (!saveRequest.isEmpty() && loadingInformation != null) {
      for (LoadingMachinesInUse lm : saveRequest) {
        Optional<LoadingMachineryInUse> lmVar1 =
            this.loadingMachineryInUseRepository
                .findByLoadingInformationAndMachineXIdAndMachineTypeXidAndIsActiveTrue(
                    loadingInformation, lm.getMachineId(), lm.getMachineTypeValue());
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
          lmVar1 = Optional.of(new LoadingMachineryInUse());
          lmVar1.get().setMachineXId(lm.getMachineId());
          lmVar1.get().setMachineTypeXid(lm.getMachineTypeValue());
          lmVar1.get().setIsUsing(lm.getIsUsing());
          lmVar1.get().setMachineXId(lm.getMachineId());
          lmVar1.get().setLoadingInformation(loadingInformation);
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
        this.loadingMachineryInUseRepository.save(lmVar1.get());
      }
    }
  }

  private void buildLoadingMachineryInUse(
      LoadingMachinesInUse loadingMachinesInUse, LoadingMachineryInUse machinery) throws Exception {
    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(
            loadingMachinesInUse.getLoadingInfoId());
    if (loadingInformationOpt.isPresent()) {
      machinery.setLoadingInformation(loadingInformationOpt.get());
    } else {
      throw new Exception("Cannot find the loading information for the machinery");
    }
    Optional.ofNullable(loadingMachinesInUse.getMachineId()).ifPresent(machinery::setMachineXId);
    Optional.ofNullable(loadingMachinesInUse.getMachineType().getNumber())
        .ifPresent(machinery::setMachineTypeXid);

    machinery.setCapacity(
        StringUtils.isEmpty(loadingMachinesInUse.getCapacity())
            ? null
            : new BigDecimal(loadingMachinesInUse.getCapacity()));
    machinery.setIsUsing(loadingMachinesInUse.getIsUsing());
    machinery.setIsActive(true);
  }
}
