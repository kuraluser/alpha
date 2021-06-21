/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleTemplate;
import java.util.List;

public interface RuleTemplateRepository extends CommonCrudRepository<RuleTemplate, Long> {

  List<RuleTemplate> findByIsActive(boolean isActive);
}
