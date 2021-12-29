/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudyRuleInput;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoadableStudyRuleInputRepository
    extends CommonCrudRepository<LoadableStudyRuleInput, Long> {

  @Modifying
  @Query("UPDATE LoadableStudyRuleInput SET isActive = false WHERE id IN (:id)")
  void updateLoadbleStudyRulesInputStatus(@Param("id") List<Long> id);

  List<LoadableStudyRuleInput> findAllByLoadableStudyRuleXId(LoadableStudyRules loadableStudyRules);
}
