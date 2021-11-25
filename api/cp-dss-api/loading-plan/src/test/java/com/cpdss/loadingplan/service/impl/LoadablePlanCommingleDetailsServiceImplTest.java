/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadingplan.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanCommingleDetailsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadablePlanCommingleDetailsServiceImpl.class})
public class LoadablePlanCommingleDetailsServiceImplTest {

  @Autowired LoadablePlanCommingleDetailsServiceImpl loadablePlanCommingleDetailsService;
  @MockBean LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @Test
  void testSaveLoadablePlanCommingleDetailsList() {
    List<LoadableStudy.LoadableQuantityCommingleCargoDetails> commingleDetailsList =
        new ArrayList<>();
    LoadableStudy.LoadableQuantityCommingleCargoDetails commingleCargoDetails =
        LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder()
            .setApi("1")
            .setCargo1Abbreviation("1")
            .setCargo1Bbls60F("1")
            .setCargo1Bblsdbs("1")
            .setCargo1KL("1")
            .setCargo1LT("1")
            .setCargo1MT("1")
            .setCargo1Percentage("1")
            .setCargo2Abbreviation("1")
            .setCargo2Bbls60F("1")
            .setCargo2Bblsdbs("1")
            .setCargo2KL("1")
            .setCargo2LT("1")
            .setCargo2MT("1")
            .setCargo2Percentage("1")
            .setCorrectionFactor("1")
            .setCorrectedUllage("1")
            .setFillingRatio("1")
            .setGrade("1")
            .setId(1L)
            .setLoadingOrder(1)
            .setOrderedMT("1")
            .setPriority(1)
            .setQuantity("1")
            .setRdgUllage("1")
            .setSlopQuantity("1")
            .setTankId(1L)
            .setTankName("1")
            .setTemp("1")
            .setTimeRequiredForLoading("1")
            .build();
    commingleDetailsList.add(commingleCargoDetails);
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setLoadablePatternXId(1L);
    this.loadablePlanCommingleDetailsService.saveLoadablePlanCommingleDetailsList(
        commingleDetailsList, loadingInformation);
    Mockito.verify(loadablePlanCommingleDetailsRepository)
        .save(Mockito.any(LoadablePlanCommingleDetails.class));
  }
}
