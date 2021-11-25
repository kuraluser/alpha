/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.generated.Common;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.service.LoadingCargoHistoryService;
import com.cpdss.loadingplan.service.LoadingInformationService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingCargoHistoryService.class})
public class LoadingCargoHistoryServiceTest {

  @Autowired LoadingCargoHistoryService loadingCargoHistoryService;

  @MockBean LoadingInformationService loadingInformationService;

  @MockBean PortLoadingPlanStowageDetailsRepository stowageDetailsRepository;

  @Test
  void testBuildCargoDetailsFromStowageData() {
    com.cpdss.common.generated.Common.CargoHistoryOpsRequest request =
        Common.CargoHistoryOpsRequest.newBuilder().setVesselId(1L).setVoyageId(1L).build();
    Common.CargoHistoryResponse.Builder builder = Common.CargoHistoryResponse.newBuilder();
    Mockito.when(
            loadingInformationService.getAllLoadingInfoByVoyageId(Mockito.any(), Mockito.anyLong()))
        .thenReturn(getLLI());
    Mockito.when(stowageDetailsRepository.findCargoHistoryData(Mockito.anyLong()))
        .thenReturn(getLPLPSD());
    this.loadingCargoHistoryService.buildCargoDetailsFromStowageData(request, builder);
    assertEquals(1L, builder.getCargoHistory(0).getCargoId());
    assertEquals("SUCCESS", builder.getResponseStatus().getStatus());
  }

  private List<LoadingInformation> getLLI() {
    List<LoadingInformation> list = new ArrayList<>();
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    list.add(loadingInformation);
    return list;
  }

  private List<PortLoadingPlanStowageDetails> getLPLPSD() {
    List<PortLoadingPlanStowageDetails> list = new ArrayList<>();
    PortLoadingPlanStowageDetails details = new PortLoadingPlanStowageDetails();
    details.setTankXId(1L);
    details.setApi(new BigDecimal(1));
    details.setTemperature(new BigDecimal(1));
    details.setCargoNominationXId(1L);
    details.setCargoXId(1L);
    details.setPortXId(1L);
    details.setQuantity(new BigDecimal(1));
    details.setLastModifiedDateTime(LocalDateTime.now());
    list.add(details);
    return list;
  }
}
