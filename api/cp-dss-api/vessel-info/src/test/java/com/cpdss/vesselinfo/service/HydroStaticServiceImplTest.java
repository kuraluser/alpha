/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.repository.HydrostaticTableRepository;
import com.cpdss.vesselinfo.service.impl.HydroStaticServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      HydroStaticServiceImpl.class,
    })
public class HydroStaticServiceImplTest {
  @Autowired HydroStaticServiceImpl hydroStaticServiceImpl;

  @MockBean HydrostaticTableRepository hydrostaticTableRepository;

  @Test
  void testFetchAllDataByDraftAndVessel() {
    when(hydrostaticTableRepository.findAll(any(Specification.class))).thenReturn(getTable());
    when(hydrostaticTableRepository.findFirstByVesselOrderByDraftDesc(any(Vessel.class)))
        .thenReturn(Optional.of(getTable().get(0)));
    when(hydrostaticTableRepository.findFirstByVesselOrderByDraftAsc(any(Vessel.class)))
        .thenReturn(Optional.of(getTable().get(0)));

    var result = hydroStaticServiceImpl.fetchAllDataByDraftAndVessel(getVesl(), new BigDecimal(1));
    assertEquals(1l, result.get(0).getId());
  }

  @Test
  void testFetchAllDataByDraftAndVesselEmpty() {
    List<HydrostaticTable> tableList = new ArrayList<>();
    when(hydrostaticTableRepository.findAll(any(Specification.class))).thenReturn(tableList);
    when(hydrostaticTableRepository.findFirstByVesselOrderByDraftDesc(any(Vessel.class)))
        .thenReturn(Optional.of(getTable().get(0)));
    when(hydrostaticTableRepository.findFirstByVesselOrderByDraftAsc(any(Vessel.class)))
        .thenReturn(Optional.of(getTable().get(0)));

    var result = hydroStaticServiceImpl.fetchAllDataByDraftAndVessel(getVesl(), new BigDecimal(1));
    assertEquals(1l, result.get(0).getId());
  }

  @Test
  void testFetchAllDataByDraftAndVesselEmpty2() {
    List<HydrostaticTable> tableList = new ArrayList<>();
    when(hydrostaticTableRepository.findAll(any(Specification.class))).thenReturn(tableList);
    when(hydrostaticTableRepository.findFirstByVesselOrderByDraftDesc(any(Vessel.class)))
        .thenReturn(Optional.of(getTable().get(0)));
    when(hydrostaticTableRepository.findFirstByVesselOrderByDraftAsc(any(Vessel.class)))
        .thenReturn(Optional.of(getTable().get(0)));

    var result = hydroStaticServiceImpl.fetchAllDataByDraftAndVessel(getVesl(), new BigDecimal(2));
    assertEquals(1l, result.get(0).getId());
  }

  private Vessel getVesl() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    return vessel;
  }

  private List<HydrostaticTable> getTable() {
    List<HydrostaticTable> tableList = new ArrayList<>();
    HydrostaticTable table = new HydrostaticTable();
    table.setId(1l);
    table.setDraft(new BigDecimal(1));
    tableList.add(table);
    return tableList;
  }
}
