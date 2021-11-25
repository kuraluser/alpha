/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingDelay;
import com.cpdss.loadingplan.entity.LoadingDelayReason;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.ReasonForDelay;
import com.cpdss.loadingplan.repository.LoadingDelayReasonRepository;
import com.cpdss.loadingplan.repository.LoadingDelayRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.ReasonForDelayRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingDelayServiceImpl.class})
public class LoadingDelayServiceImplTest {

  @Autowired LoadingDelayServiceImpl loadingDelayService;

  @MockBean LoadingDelayRepository loadingDelayRepository;
  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean ReasonForDelayRepository reasonForDelayRepository;
  @MockBean LoadingDelayReasonRepository loadingDelayReasonRepository;

  @Test
  void testSaveLoadingDelayList() {
    List<LoadingPlanModels.LoadingDelays> list = new ArrayList<>();
    LoadingPlanModels.LoadingDelays delays =
        LoadingPlanModels.LoadingDelays.newBuilder()
            .setId(1L)
            .setLoadingInfoId(1L)
            .setCargoNominationId(1L)
            .setDuration("1")
            .setCargoId(1L)
            .setQuantity("1")
            .build();
    list.add(delays);
    LoadingPlanModels.LoadingDelay loadingDelays =
        LoadingPlanModels.LoadingDelay.newBuilder().addAllDelays(list).build();
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    Mockito.when(
            loadingDelayRepository.findByLoadingInformationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLLD());
    Mockito.when(
            loadingDelayRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLD());
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(loadingDelayReasonRepository.findAllByLoadingDelayAndIsActiveTrue(Mockito.any()))
        .thenReturn(getLLDR());
    Mockito.when(reasonForDelayRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getORD());
    try {
      this.loadingDelayService.saveLoadingDelayList(loadingDelays, loadingInformation);
      Mockito.verify(loadingDelayRepository)
          .save(Mockito.any(com.cpdss.loadingplan.entity.LoadingDelay.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<LoadingDelay> getLLD() {
    List<com.cpdss.loadingplan.entity.LoadingDelay> list = new ArrayList<>();
    com.cpdss.loadingplan.entity.LoadingDelay loadingDelay = new LoadingDelay();
    loadingDelay.setId(1L);

    list.add(loadingDelay);
    return list;
  }

  private Optional<LoadingDelay> getOLD() {
    LoadingDelay loadingDelay = new LoadingDelay();
    loadingDelay.setId(1L);
    return Optional.of(loadingDelay);
  }

  private Optional<LoadingInformation> getOLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    return Optional.of(loadingInformation);
  }

  private List<LoadingDelayReason> getLLDR() {
    List<LoadingDelayReason> list = new ArrayList<>();
    LoadingDelayReason loadingDelayReason = new LoadingDelayReason();
    loadingDelayReason.setId(1L);
    list.add(loadingDelayReason);
    return list;
  }

  private Optional<ReasonForDelay> getORD() {
    ReasonForDelay delay = new ReasonForDelay();
    delay.setId(1L);
    return Optional.of(delay);
  }
}
