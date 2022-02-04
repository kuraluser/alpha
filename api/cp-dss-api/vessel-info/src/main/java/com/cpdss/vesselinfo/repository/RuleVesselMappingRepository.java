/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleVesselMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface RuleVesselMappingRepository extends CommonCrudRepository<RuleVesselMapping, Long> {

  @Query(
      "FROM RuleVesselMapping RVM WHERE RVM.ruleTemplate.id in (4, 61, 62, 64, 71, 76) AND RVM.vessel.id = ?1 AND RVM.isActive = true")
  public List<RuleVesselMapping> findLoadingInfoRulesByVesselId(Long vesselId);

  @Query(
      "FROM RuleVesselMapping RVM WHERE RVM.vessel.id = ?1  AND RVM.ruleTemplate.id = ?3 AND RVM.isActive = ?2")
  Optional<RuleVesselMapping> checkIsRuleTemplateMapped(
      Long id, Boolean isActive, Long ruleTemplateId);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM rule_vessel_mapping u where vessel_xid IN ?1",
      nativeQuery = true)
  String getRuleVesselMappings(List<Long> vesselIds);
}
