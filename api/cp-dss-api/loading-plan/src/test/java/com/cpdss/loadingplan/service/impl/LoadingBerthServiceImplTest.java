/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadingBerthDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingBerthServiceImpl.class})
public class LoadingBerthServiceImplTest {

  @Autowired LoadingBerthServiceImpl loadingBerthService;

  @MockBean LoadingBerthDetailsRepository loadingBerthDetailRepository;
  @MockBean LoadingInformationRepository loadingInformationRepository;

  @Test
  void testSaveLoadingBerthList() {
    List<LoadingPlanModels.LoadingBerths> loadingBerthsList = new ArrayList<>();
    LoadingPlanModels.LoadingBerths loadingBerths =
        LoadingPlanModels.LoadingBerths.newBuilder()
            .setId(1L)
            .setBerthId(1L)
            .setLoadingInfoId(1L)
            .setAirDraftLimitation("1")
            .setDepth("1")
            .setMaxManifoldHeight("1")
            .setSeaDraftLimitation("1")
            .setSpecialRegulationRestriction("1")
            .setHoseConnections("1")
            .setItemsToBeAgreedWith("1")
            .setLineDisplacement("1")
            .build();
    loadingBerthsList.add(loadingBerths);
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    Mockito.when(
            loadingBerthDetailRepository.findByLoadingInformationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLLBD());
    Mockito.when(loadingBerthDetailRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLBD());
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLI());
    try {
      this.loadingBerthService.saveLoadingBerthList(loadingBerthsList, loadingInformation);
      Mockito.verify(loadingBerthDetailRepository).save(Mockito.any(LoadingBerthDetail.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<LoadingBerthDetail> getLLBD() {
    List<LoadingBerthDetail> list = new ArrayList<>();
    LoadingBerthDetail loadingBerthDetail = new LoadingBerthDetail();
    loadingBerthDetail.setId(1L);
    list.add(loadingBerthDetail);
    return list;
  }

  private Optional<LoadingBerthDetail> getOLBD() {
    LoadingBerthDetail detail = new LoadingBerthDetail();
    detail.setId(1L);
    return Optional.of(detail);
  }

  private Optional<LoadingInformation> getOLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    return Optional.of(loadingInformation);
  }
}
