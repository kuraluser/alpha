/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.junit.Assert.assertEquals;

import com.cpdss.loadingplan.repository.PortTideDetailsRepository;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingPortTideServiceImpl.class})
public class LoadingPortTideServiceImplTest {

  @Autowired LoadingPortTideServiceImpl loadingPortTideService;

  @MockBean PortTideDetailsRepository portTideDetailsRepository;

  @Test
  void testFindAllByPortIdAndPageable() {
    Long portId = 1L;
    Mockito.when(
            this.portTideDetailsRepository
                .findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(
                    Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLPTA());
    var portTideAlgo = this.loadingPortTideService.findAllByPortIdAndPageable(portId);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(portTideAlgo.get(0).getId()));
  }

  private List<PortTideAlgo> getLPTA() {
    List<PortTideAlgo> list = new ArrayList<>();
    PortTideAlgo portTideAlgo = new PortTide();
    list.add(portTideAlgo);
    return list;
  }

  private class PortTide implements PortTideAlgo {
    @Override
    public Long getId() {
      return 1L;
    }

    @Override
    public Long getPortXid() {
      return 1L;
    }

    @Override
    public Date getTideDate() {
      return null;
    }

    @Override
    public BigDecimal getTideHeight() {
      return new BigDecimal(1);
    }

    @Override
    public LocalTime getTideTime() {
      return null;
    }
  }

  @Test
  void testFindAllByPortIdLoadingInfoId() {
    Long portId = 1L;
    Long infoId = 1L;
    Mockito.when(
            this.portTideDetailsRepository
                .findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(
                    Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLPTA());
    var portTideAlgo = this.loadingPortTideService.findAllByPortIdLoadingInfoId(portId, infoId);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(portTideAlgo.get(0).getId()));
  }

  @Test
  void testFindRecentTideDetailsByPortId() {
    Long id = 1L;
    Mockito.when(
            this.portTideDetailsRepository
                .findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(
                    Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLPTA());
    var portTideAlgo = this.loadingPortTideService.findRecentTideDetailsByPortId(id);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(portTideAlgo.get(0).getId()));
  }

  @Test
  void testFindRecentTideDetailsByPortIdAndLoadingInfoId() {
    Long portId = 1L;
    Long infoId = 1L;
    Mockito.when(
            this.portTideDetailsRepository.findByLoadingXidAndPortXidAndIsActiveTrue(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLPTA());
    var portTideAlgo =
        this.loadingPortTideService.findRecentTideDetailsByPortIdAndLoadingInfoId(portId, infoId);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(portTideAlgo.get(0).getId()));
  }
}
