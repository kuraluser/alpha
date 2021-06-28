/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoadableStudyRuleRepository
    extends CommonCrudRepository<LoadableStudyRules, Long> {

  List<LoadableStudyRules> findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdIn(
      LoadableStudy loadableStudy, Long vesselId, boolean isActive, List<Long> vesselRuleXId);

  List<LoadableStudyRules> findByLoadableStudyAndVesselXIdAndIsActive(
      LoadableStudy loadbleStudyId, Long vesselId, Boolean isActive);

  List<LoadableStudyRules> findByLoadableStudyAndIsActive(
      LoadableStudy loadbleStudyId, Boolean isActive);

  @Modifying
  @Query("UPDATE LoadableStudyRules SET isActive = false WHERE id IN (:id)")
  int updateLoadbleStudyRulesStatus(@Param("id") List<Long> id);
}
