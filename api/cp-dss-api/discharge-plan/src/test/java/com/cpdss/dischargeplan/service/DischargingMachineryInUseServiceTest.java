/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingMachineryInUse;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingMachineryInUseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      DischargingMachineryInUseService.class,
    })
public class DischargingMachineryInUseServiceTest {

  @Autowired DischargingMachineryInUseService dischargingMachineryInUseService;
  @MockBean DischargingMachineryInUseRepository dischargingMachineryInUseRepository;
  @MockBean DischargeInformationRepository dischargingInformationRepository;

  @Test
  void testSaveDischargingMachineryList() {
    List<LoadingPlanModels.LoadingMachinesInUse> saveRequest = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse loadingMachinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder()
            .setCapacity("1")
            .setIsUsing(true)
            .setMachineId(1L)
            .setMachineTypeValue(3)
            .setId(1L)
            .build();
    saveRequest.add(loadingMachinesInUse);
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setId(1L);
    Mockito.when(
            this.dischargingMachineryInUseRepository
                .findByDischargingInformationAndMachineXidAndMachineTypeXidAndIsActiveTrue(
                    Mockito.any(), Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(getODMU());
    try {
      this.dischargingMachineryInUseService.saveDischargingMachineryList(
          saveRequest, dischargingInformation);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<DischargingMachineryInUse> getODMU() {
    DischargingMachineryInUse dischargingMachineryInUse = new DischargingMachineryInUse();
    dischargingMachineryInUse.setId(1L);
    return Optional.of(dischargingMachineryInUse);
  }
}
