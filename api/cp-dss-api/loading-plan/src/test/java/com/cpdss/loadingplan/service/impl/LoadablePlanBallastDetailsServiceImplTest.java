/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanBallastDetailsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadablePlanBallastDetailsServiceImpl.class})
public class LoadablePlanBallastDetailsServiceImplTest {

  @Autowired LoadablePlanBallastDetailsServiceImpl loadablePlanBallastDetailsService;

  @MockBean LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @Test
  void testSaveLoadablePlanBallastDetailsList() {
    List<LoadableStudy.LoadablePlanBallastDetails> ballastDetailsList = new ArrayList<>();
    LoadableStudy.LoadablePlanBallastDetails ballastDetails =
        LoadableStudy.LoadablePlanBallastDetails.newBuilder()
            .setColorCode("1")
            .setCorrectedLevel("1")
            .setCorrectionFactor("1")
            .setCubicMeter("1")
            .setId(1L)
            .setInertia("1")
            .setLcg("1")
            .setMetricTon("1")
            .setPercentage("1")
            .setRdgLevel("1")
            .setSg("1")
            .setTankId(1L)
            .setTankName("1")
            .setTcg("1")
            .setVcg("1")
            .build();
    ballastDetailsList.add(ballastDetails);
    LoadingInformation loadingInformation = new LoadingInformation();
    this.loadablePlanBallastDetailsService.saveLoadablePlanBallastDetailsList(
        ballastDetailsList, loadingInformation);
    Mockito.verify(loadablePlanBallastDetailsRepository)
        .save(Mockito.any(com.cpdss.loadingplan.entity.LoadablePlanBallastDetails.class));
  }
}
