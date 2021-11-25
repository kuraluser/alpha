/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargeDelay;
import com.cpdss.common.generated.discharge_plan.DischargeDelays;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingDelay;
import com.cpdss.dischargeplan.entity.DischargingDelayReason;
import com.cpdss.dischargeplan.entity.ReasonForDelay;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayReasonRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayRepository;
import com.cpdss.dischargeplan.repository.ReasonForDelayRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      DischargingDelayService.class,
    })
public class DischargingDelayServiceTest {

  @Autowired DischargingDelayService dischargingDelayService;
  @MockBean DischargingDelayRepository dischargingDelayRepository;
  @MockBean DischargeInformationRepository dischargingInformationRepository;
  @MockBean ReasonForDelayRepository reasonForDelayRepository;
  @MockBean DischargingDelayReasonRepository dischargingDelayReasonRepository;

  @Test
  void testSaveDischargingDelayList() {
    List<Long> list1 = new ArrayList<>();
    list1.add(1L);
    List<DischargeDelays> list = new ArrayList<>();
    DischargeDelays dischargeDelays =
        DischargeDelays.newBuilder()
            .setCargoNominationId(1L)
            .setDuration("1")
            .setCargoId(1L)
            .setQuantity("1")
            .addAllReasonForDelayIds(list1)
            .setId(1L)
            .setDischargeInfoId(1L)
            .build();
    list.add(dischargeDelays);
    DischargeDelay dischargeDelay = DischargeDelay.newBuilder().addAllDelays(list).build();
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setId(1L);
    Mockito.when(
            dischargingDelayRepository.findByDischargingInformationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLDD());
    Mockito.when(
            dischargingDelayRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getODD());
    Mockito.when(dischargingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getODI());
    Mockito.when(
            dischargingDelayReasonRepository.findAllByDischargingDelayAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDDR());
    Mockito.when(reasonForDelayRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getORD());
    try {
      this.dischargingDelayService.saveDischargingDelayList(dischargeDelay, dischargingInformation);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<ReasonForDelay> getORD() {
    ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setId(1L);
    return Optional.of(reasonForDelay);
  }

  private List<DischargingDelayReason> getLDDR() {
    List<DischargingDelayReason> list = new ArrayList<>();
    DischargingDelayReason dischargingDelayReason = new DischargingDelayReason();
    dischargingDelayReason.setId(1L);
    list.add(dischargingDelayReason);
    return list;
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    return Optional.of(dischargeInformation);
  }

  private List<DischargingDelay> getLDD() {
    List<DischargingDelay> list = new ArrayList<>();
    DischargingDelay dischargingDelay = new DischargingDelay();
    dischargingDelay.setId(1L);
    list.add(dischargingDelay);
    return list;
  }

  private Optional<DischargingDelay> getODD() {
    DischargingDelay dischargingDelay = new DischargingDelay();
    dischargingDelay.setId(1L);
    return Optional.of(dischargingDelay);
  }
}
