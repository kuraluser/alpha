/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingMachineryInUseRepository;
import com.cpdss.loadingplan.service.LoadingMachineryInUseService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
      List<LoadingMachinesInUse> loadingMachinesList, LoadingInformation loadingInformation)
      throws Exception {
    List<LoadingMachineryInUse> existingMachineries =
        loadingMachineryInUseRepository.findByLoadingInformationIdAndIsActive(
            loadingInformation.getId(), true);
    List<Long> requestedMachineries =
        loadingMachinesList.stream()
            .map(machinery -> machinery.getId())
            .collect(Collectors.toList());

    existingMachineries.stream()
        .filter(machinery -> !requestedMachineries.contains(machinery.getId()))
        .forEach(
            machinery -> {
              loadingMachineryInUseRepository.deleteById(machinery.getId());
            });

    for (LoadingMachinesInUse machine : loadingMachinesList) {
      log.info(
          "Saving LoadingMachinery {} for LoadingInformation {}",
          machine.getId(),
          machine.getLoadingInfoId());
      LoadingMachineryInUse loadingMachineryInUse = null;
      if (machine.getId() == 0) {
        loadingMachineryInUse = new LoadingMachineryInUse();
      } else {
        Optional<LoadingMachineryInUse> loadingMachineryOpt =
            loadingMachineryInUseRepository.findByIdAndIsActiveTrue(machine.getId());
        if (loadingMachineryOpt.isPresent()) {
          loadingMachineryInUse = loadingMachineryOpt.get();
        } else {
          log.error("Exception occured while saving LoadingMachineryInUse {}", machine.getId());
          throw new Exception("Cannot find cargo machinery");
        }
      }

      buildLoadingMachineryInUse(machine, loadingMachineryInUse);
      loadingMachineryInUseRepository.save(loadingMachineryInUse);
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
    Optional.ofNullable(loadingMachinesInUse.getMachineTypeValue())
        .ifPresent(machinery::setMachineTypeXid);
    machinery.setCapacity(
        StringUtils.isEmpty(loadingMachinesInUse.getCapacity())
            ? null
            : new BigDecimal(loadingMachinesInUse.getCapacity()));
    machinery.setIsUsing(loadingMachinesInUse.getIsUsing());
    machinery.setIsActive(true);
  }
}
