/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargeBerths;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import java.math.BigDecimal;
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
      DischargingBerthService.class,
    })
public class DischargingBerthServiceTest {

  @Autowired DischargingBerthService dischargingBerthService;

  @MockBean DischargeBerthDetailRepository dischargingBerthDetailRepository;
  @MockBean DischargeInformationRepository dischargingInformationRepository;

  @Test
  void testSaveDischargingBerthList() {
    List<DischargeBerths> dischargingBerthsList = new ArrayList<>();
    DischargeBerths dischargeBerths =
        DischargeBerths.newBuilder()
            .setAirDraftLimitation("1")
            .setDepth("1")
            .setMaxManifoldHeight("1")
            .setMaxManifoldPressure("1")
            .setSeaDraftLimitation("1")
            .setSpecialRegulationRestriction("1")
            .setHoseConnections("1")
            .setItemsToBeAgreedWith("1")
            .setLineDisplacement("1")
            .setAirPurge(false)
            .setCargoCirculation(true)
            .setId(1L)
            .setBerthId(1L)
            .setDischargeInfoId(1L)
            .build();
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setId(1L);
    Mockito.when(
            dischargingBerthDetailRepository.findByDischargingInformationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLDBD());
    Mockito.when(dischargingBerthDetailRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getODBD());
    Mockito.when(dischargingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getODI());
    try {
      this.dischargingBerthService.saveDischargingBerthList(
          dischargingBerthsList, dischargingInformation);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    return Optional.of(dischargeInformation);
  }

  private List<DischargingBerthDetail> getLDBD() {
    List<DischargingBerthDetail> list = new ArrayList<>();
    DischargingBerthDetail dischargingBerthDetail = new DischargingBerthDetail();
    dischargingBerthDetail.setId(1L);
    list.add(dischargingBerthDetail);
    return list;
  }

  private Optional<DischargingBerthDetail> getODBD() {
    DischargingBerthDetail dischargingBerthDetail = new DischargingBerthDetail();
    dischargingBerthDetail.setLineContentDisplacement(new BigDecimal(1));
    return Optional.of(dischargingBerthDetail);
  }
}
