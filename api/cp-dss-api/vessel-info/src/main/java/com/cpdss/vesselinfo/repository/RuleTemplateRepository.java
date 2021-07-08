/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RuleTemplateRepository extends CommonCrudRepository<RuleTemplate, Long> {

  List<RuleTemplate> findByIsActive(boolean isActive);

  @Query("FROM RuleTemplate RT WHERE RT.id in (4, 61, 62, 64, 71, 76) AND RT.isActive = true")
  List<RuleTemplate> findLoadingInfoRules();
}
