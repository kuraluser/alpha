/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.loadablestudy.entity.BackLoading;
import com.cpdss.loadablestudy.repository.BackLoadingRepository;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      BackLoadingService.class,
    })
public class BackLoadingServiceTest {
  @Autowired BackLoadingService backLoadingService;
  @MockBean BackLoadingRepository backLoadingRepository;

  @Test
  void testGetBackloadingDataByportIds() {
    long loadableStudyId = 1l;
    List<Long> portIds = new ArrayList<>();
    List<BackLoading> backLoadingList = new ArrayList<>();
    BackLoading backLoading = new BackLoading();
    backLoading.setPortId(1l);
    backLoadingList.add(backLoading);
    Mockito.when(
            backLoadingRepository.findByDischargeStudyIdAndPortIdInAndIsActive(
                Mockito.anyLong(), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(backLoadingList);
    var result = backLoadingService.getBackloadingDataByportIds(loadableStudyId, portIds);
    assertThat(result, IsMapContaining.hasKey(1L));
  }

  @Test
  void testGetBackLoadings() {
    long loadableStudyId = 1l;
    List<Long> portIds = new ArrayList<>();
    List<BackLoading> backLoadingList = new ArrayList<>();
    BackLoading backLoading = new BackLoading();
    backLoading.setPortId(1l);
    backLoadingList.add(backLoading);
    Mockito.when(
            backLoadingRepository.findByDischargeStudyIdAndPortIdInAndIsActive(
                Mockito.anyLong(), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(backLoadingList);
    var result = backLoadingService.getBackLoadings(loadableStudyId, portIds);
    assertEquals(1L, result.get(0).getPortId());
  }

  @Test
  void testsaveAll() {
    List<BackLoading> backLoadingToSave = new ArrayList<>();
    backLoadingService.saveAll(backLoadingToSave);
    Mockito.verify(backLoadingRepository).saveAll(Mockito.anyList());
  }
}
