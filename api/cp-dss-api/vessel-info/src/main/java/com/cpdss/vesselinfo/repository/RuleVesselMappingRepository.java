/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleVesselMapping;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RuleVesselMappingRepository extends CommonCrudRepository<RuleVesselMapping, Long> {

  @Query(
      "FROM RuleVesselMapping RVM WHERE RVM.ruleTemplate.id in (4, 61, 62, 62, 71) AND RVM.vessel.id = ?1 AND RVM.isActive = true")
  public List<RuleVesselMapping> findLoadingInfoRulesByVesselId(Long vesselId);
}
