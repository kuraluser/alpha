/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import java.util.List;
import java.util.Map;

public interface PortRotationService {

  List<Long> getPortRotationPortIds(LoadableStudy study);

  Map<Long, Long> getPortRotationIdAndPortIds(LoadableStudy lStudy);

  List<PortRotationIdAndPortId> getPortRotationCustomFields(LoadableStudy lStudy);

  LoadableStudyPortRotation findLoadableStudyPortRotationById(Long id);
}
