/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.loadingplan.repository.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/** @Author ravi.r */

// @SpringJUnitConfig(classes = {LoadingPlanService.class})
class LoadingPlanServiceTest {

  @MockBean BillOfLandingRepository billOfLandingRepository;
  @MockBean PortLoadingPlanStowageTempDetailsRepository loadingPlanStowageDetailsTempRepository;
  @MockBean PortLoadingPlanBallastTempDetailsRepository loadingPlanBallastDetailsTempRepository;
  @MockBean PortLoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @MockBean PortLoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @MockBean PortLoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;

  @Test
  public void testullageUpdate() throws GenericServiceException {
    BillOfLandingRepository repos = mock(BillOfLandingRepository.class);

    doThrow(new RuntimeException())
        .when(repos)
        .updateBillOfLandingRepository(
            "09",
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            Integer.valueOf(0 + ""),
            Integer.valueOf(0 + ""));

    verify(repos, times(0))
        .updateBillOfLandingRepository(
            "09",
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(0),
            Integer.valueOf(0 + ""),
            Integer.valueOf(0 + ""));
  }

  @Test
  public void testBillLanding() throws GenericServiceException {}

  @Test
  public void testullageUpdateRob() throws GenericServiceException {}
}
