/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply;
import com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest;
import com.cpdss.dischargeplan.entity.DischargingInformationAlgoStatus;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import com.cpdss.dischargeplan.repository.DischargeInformationStatusRepository;
import com.cpdss.dischargeplan.repository.DischargingInformationAlgoStatusRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {DischargeInfoStatusCheckService.class})
public class DischargeInfoStatusCheckServiceTest {

  @Autowired DischargeInfoStatusCheckService dischargeInfoStatusCheckService;
  @MockBean DischargeInformationStatusRepository dischargeInformationStatusRepository;

  @MockBean DischargingInformationAlgoStatusRepository algoStatusRepository;

  @Test
  void testCheckStatus() {
    DischargeInfoStatusRequest request =
        DischargeInfoStatusRequest.newBuilder()
            .setDischargeInfoId(1L)
            .setConditionType(0)
            .setProcessId("1")
            .build();
    DischargeInfoStatusReply.Builder builder =
        DischargeInfoStatusReply.newBuilder().setDischargeInfoStatusId(1L);
    Mockito.when(
            this.algoStatusRepository.findByProcessIdAndDischargeInformationIdAndIsActiveTrue(
                Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(getDIAS());
    try {
      this.dischargeInfoStatusCheckService.checkStatus(request, builder);
      assertEquals(1L, builder.getDischargeInfoId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<DischargingInformationAlgoStatus> getDIAS() {
    DischargingInformationAlgoStatus dischargingInformationAlgoStatus =
        new DischargingInformationAlgoStatus();
    dischargingInformationAlgoStatus.setProcessId("1");
    dischargingInformationAlgoStatus.setDischargingInformationStatus(getDIS());
    dischargingInformationAlgoStatus.setLastModifiedDateTime(LocalDateTime.now());
    return Optional.of(dischargingInformationAlgoStatus);
  }

  private DischargingInformationStatus getDIS() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    dischargingInformationStatus.setId(1L);
    return dischargingInformationStatus;
  }
}
