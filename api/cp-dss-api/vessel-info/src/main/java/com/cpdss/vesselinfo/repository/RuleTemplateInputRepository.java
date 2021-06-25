/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleTemplate;
import com.cpdss.vesselinfo.entity.RuleTemplateInput;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleTemplateInputRepository extends CommonCrudRepository<RuleTemplateInput, Long> {

  public List<RuleTemplateInput> findByRuleTemplateAndIsActive(
      RuleTemplate ruleTemplate, Boolean isActive);
}
