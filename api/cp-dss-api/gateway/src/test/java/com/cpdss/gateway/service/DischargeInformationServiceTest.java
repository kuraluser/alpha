/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.mockito.ArgumentMatchers.anyLong;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.service.dischargeplan.*;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(classes = DischargeInformationService.class)
public class DischargeInformationServiceTest {

  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_DISCHARGE_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;

  DischargeInformationService dischargeInformationService;

  @MockBean DischargeInformationGrpcService dischargeInformationGrpcService;

  @MockBean DischargeInformationBuilderService infoBuilderService;

  @MockBean LoadingPlanGrpcService loadingPlanGrpcService;
  @MockBean LoadingPlanBuilderService loadingPlanBuilderService;
  @MockBean LoadingInformationService loadingInformationService;
  @MockBean LoadingPlanService loadingPlanService;
  @MockBean VesselInfoService vesselInfoService;
  @MockBean DischargingSequenceService dischargingSequenceService;
  @MockBean LoadableStudyService loadableStudyService;
  @MockBean GenerateDischargingPlanExcelReportService dischargingPlanExcelReportService;

  @BeforeEach
  public void init() {
    this.dischargeInformationService = Mockito.mock(DischargeInformationService.class);
  }

  @Test
  public void getDischargeInformationTestCase1() throws GenericServiceException {
    Mockito.when(
            this.dischargeInformationService.getDischargeInformation(
                anyLong(), anyLong(), anyLong()))
        .thenReturn(getDummyDischargeInfoData());
  }

  private DischargeInformation getDummyDischargeInfoData() {
    DischargeInformation dischargeInformation = new DischargeInformation();

    return dischargeInformation;
  }
}
