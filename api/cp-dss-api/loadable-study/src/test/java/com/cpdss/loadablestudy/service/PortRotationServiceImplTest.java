/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import com.cpdss.loadablestudy.service.impl.PortRotationServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      PortRotationServiceImpl.class,
    })
public class PortRotationServiceImplTest {
  @Autowired PortRotationServiceImpl portRotationServiceImpl;

  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Test
  void testGetPortRotationPortIds() {
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(Arrays.asList(1l));

    var result = portRotationServiceImpl.getPortRotationPortIds(getLoadableStudy());
    assertEquals(1l, result.get(0));
  }

  @Test
  void testGetPortRotationCustomFields() {
    when(this.loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(getPortRotationIdAndPortId()));

    var result = portRotationServiceImpl.getPortRotationCustomFields(getLoadableStudy());
    assertEquals(1l, result.get(0).getId());
  }

  @Test
  void testFindLoadableStudyPortRotationById() {
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setId(1l);
    when(this.loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(portRotation);

    var result = portRotationServiceImpl.findLoadableStudyPortRotationById(1l);
    assertEquals(1l, result.getId());
  }

  @Test
  void testGetPortRotationIdAndPortIds() {
    when(this.loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(getPortRotationIdAndPortId()));

    var result = portRotationServiceImpl.getPortRotationIdAndPortIds(getLoadableStudy());
    assertEquals(1l, result.get(1l));
  }

  private PortRotationIdAndPortId getPortRotationIdAndPortId() {
    PortRotationIdAndPortId portRotationIdAndPortId =
        new PortRotationIdAndPortId() {
          @Override
          public Long getId() {
            return 1l;
          }

          @Override
          public Long getPortId() {
            return 1l;
          }
        };
    return portRotationIdAndPortId;
  }

  private LoadableStudy getLoadableStudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1l);
    return loadableStudy;
  }
}
