/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import java.util.List;

public interface LoadableStudyRuleRepository
    extends CommonCrudRepository<LoadableStudyRules, Long> {

  List<LoadableStudyRules> findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdIn(
      LoadableStudy loadableStudy, Long vesselId, boolean isActive, List<Long> vesselRuleXId);

  List<LoadableStudyRules> findByLoadableStudyAndVesselXIdAndIsActive(
      LoadableStudy loadbleStudyId, Long vesselId, Boolean isActive);
}
