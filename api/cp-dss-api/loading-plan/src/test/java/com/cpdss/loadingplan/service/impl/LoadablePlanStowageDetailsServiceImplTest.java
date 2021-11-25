/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanStowageDetailsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadablePlanStowageDetailsServiceImpl.class})
public class LoadablePlanStowageDetailsServiceImplTest {

  @Autowired LoadablePlanStowageDetailsServiceImpl loadablePlanStowageDetailsService;
  @MockBean LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;

  @Test
  void testSaveLoadablePlanStowageDetailsList() {
    List<LoadableStudy.LoadablePlanStowageDetails> stowageDetailsList = new ArrayList<>();
    LoadableStudy.LoadablePlanStowageDetails stowageDetails =
        LoadableStudy.LoadablePlanStowageDetails.newBuilder()
            .setApi("1")
            .setCargoAbbreviation("1")
            .setCargoNominationId(1L)
            .setColorCode("1")
            .setCorrectedUllage("1")
            .setCorrectionFactor("1")
            .setFillingRatio("1")
            .setId(1L)
            .setObservedBarrels("1")
            .setObservedBarrelsAt60("1")
            .setObservedM3("1")
            .setRdgUllage("1")
            .setTankId(1L)
            .setTankName("1")
            .setTemperature("1")
            .setWeight("1")
            .build();
    stowageDetailsList.add(stowageDetails);
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setLoadablePatternXId(1L);
    this.loadablePlanStowageDetailsService.saveLoadablePlanStowageDetailsList(
        stowageDetailsList, loadingInformation);
    Mockito.verify(loadablePlanStowageDetailsRepository)
        .save(Mockito.any(com.cpdss.loadingplan.entity.LoadablePlanStowageDetails.class));
  }
}
