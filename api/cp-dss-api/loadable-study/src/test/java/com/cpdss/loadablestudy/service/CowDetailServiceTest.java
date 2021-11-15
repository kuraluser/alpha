/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.cpdss.loadablestudy.entity.DischargeStudyCowDetail;
import com.cpdss.loadablestudy.repository.DischargeStudyCowDetailRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      CowDetailService.class,
    })
public class CowDetailServiceTest {
  @Autowired CowDetailService cowDetailService;

  @MockBean DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;

  @Test
  void testGetCowDetailForThePort() {
    long dischargestudyId = 1L;
    List<Long> portIds = new ArrayList<Long>(Arrays.asList(1L));
    Mockito.when(
            dischargeStudyCowDetailRepository.findByDischargeStudyStudyIdAndPortIdInAndIsActive(
                Mockito.anyLong(), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getCowDetails());
    var CowDetailForThePort = cowDetailService.getCowDetailForThePort(dischargestudyId, portIds);
    assertEquals("1", getCowDetails().get(0).getTankIds());
    assertThat(CowDetailForThePort, IsMapContaining.hasKey(1L));
  }

  @Test
  void testGetCowDetailForOnePort() {
    long dischargestudyId = 1L;
    Long portId = 1L;
    DischargeStudyCowDetail dischargeStudyCowDetail = new DischargeStudyCowDetail();
    dischargeStudyCowDetail.setPercentage(1L);
    dischargeStudyCowDetail.setCowType(1L);
    dischargeStudyCowDetail.setPortId(1L);
    dischargeStudyCowDetail.setDischargeStudyStudyId(1L);
    dischargeStudyCowDetail.setIsActive(true);
    dischargeStudyCowDetail.setTankIds("1");
    Mockito.when(
            dischargeStudyCowDetailRepository.findByDischargeStudyStudyIdAndPortIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(dischargeStudyCowDetail);
    var dsCowDetail = cowDetailService.getCowDetailForOnePort(dischargestudyId, portId);
    assertEquals(Long.valueOf(1L), dischargeStudyCowDetail.getCowType());
    assertEquals("1", dsCowDetail.getTankIds());
  }

  @Test
  void testSaveAll() {
    this.cowDetailService.saveAll(getCowDetails());
    Mockito.verify(dischargeStudyCowDetailRepository).saveAll(Mockito.anyList());
  }

  private List<DischargeStudyCowDetail> getCowDetails() {
    List<DischargeStudyCowDetail> cowDetails = new ArrayList<>();
    DischargeStudyCowDetail dischargeStudyCowDetail = new DischargeStudyCowDetail();
    dischargeStudyCowDetail.setPercentage(1L);
    dischargeStudyCowDetail.setCowType(1L);
    dischargeStudyCowDetail.setPortId(1L);
    dischargeStudyCowDetail.setDischargeStudyStudyId(1L);
    dischargeStudyCowDetail.setIsActive(true);
    dischargeStudyCowDetail.setTankIds("1");
    cowDetails.add(dischargeStudyCowDetail);
    return cowDetails;
  }
}
