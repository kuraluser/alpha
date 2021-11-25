/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanQuantityRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadablePlanQuantityServiceImpl.class})
public class LoadablePlanQuantityServiceImplTest {

  @Autowired LoadablePlanQuantityServiceImpl loadablePlanQuantityService;

  @MockBean LoadablePlanQuantityRepository loadablePlanQuantityRepository;

  @Test
  void testSaveLoadablePlanQuantyList() {
    List<LoadableStudy.LoadableQuantityCargoDetails> cargoDetailsList = new ArrayList<>();
    LoadableStudy.LoadableQuantityCargoDetails cargoDetails =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder()
            .setCargoAbbreviation("1")
            .setCargoId(1L)
            .setCargoNominationId(1L)
            .setColorCode("1")
            .setDifferenceColor("1")
            .setDifferencePercentage("1")
            .setEstimatedTemp("1")
            .setEstimatedAPI("1")
            .setGrade("1")
            .setId(1L)
            .setLoadableBbls60F("1")
            .setLoadableBblsdbs("1")
            .setLoadableKL("1")
            .setLoadableMT("1")
            .setLoadableLT("1")
            .setLoadingOrder(1)
            .setMinTolerence("1")
            .setMaxTolerence("1")
            .setOrderBbls60F("1")
            .setOrderBblsdbs("1")
            .setOrderedMT("1")
            .setPriority(1)
            .setSlopQuantity("1")
            .setTimeRequiredForLoading("1")
            .build();
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setLoadablePatternXId(1L);
    this.loadablePlanQuantityService.saveLoadablePlanQuantyList(
        cargoDetailsList, loadingInformation);
  }
}
