/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingMachineryInUseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingMachineryInUseServiceImpl.class})
public class LoadingMachineryInUseServiceImplTest {

  @Autowired LoadingMachineryInUseServiceImpl loadingMachineryInUseService;

  @MockBean LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @MockBean LoadingInformationRepository loadingInformationRepository;

  @Test
  void testSaveLoadingMachineryList() {
    List<LoadingPlanModels.LoadingMachinesInUse> saveRequest = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder()
            .setIsUsing(true)
            .setCapacity("1")
            .setMachineId(1L)
            .setMachineTypeValue(2)
            .build();
    saveRequest.add(machinesInUse);
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    Mockito.when(
            this.loadingMachineryInUseRepository
                .findByLoadingInformationAndMachineXIdAndMachineTypeXidAndIsActiveTrue(
                    Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(getOLMU());
    try {
      this.loadingMachineryInUseService.saveLoadingMachineryList(saveRequest, loadingInformation);
      Mockito.verify(loadingMachineryInUseRepository).save(Mockito.any());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<LoadingMachineryInUse> getOLMU() {
    LoadingMachineryInUse machinery = new LoadingMachineryInUse();

    return Optional.of(machinery);
  }
}
