/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service.impl;

import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import com.cpdss.loadablestudy.service.PortRotationService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PortRotationServiceImpl implements PortRotationService {

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Override
  public List<Long> getPortRotationPortIds(LoadableStudy study) {
    List<Long> portIds =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(study, true);
    return portIds.stream().distinct().collect(Collectors.toList());
  }

  @Override
  public List<PortRotationIdAndPortId> getPortRotationCustomFields(LoadableStudy lStudy) {
    List<PortRotationIdAndPortId> id =
        loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(lStudy.getId(), true);
    return id;
  }

  @Override
  public Map<Long, Long> getPortRotationIdAndPortIds(LoadableStudy lStudy) {
    return getPortRotationCustomFields(lStudy).stream()
        .collect(
            Collectors.toMap(PortRotationIdAndPortId::getId, PortRotationIdAndPortId::getPortId));
  }
}
