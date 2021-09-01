/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleVesselMapping;
import com.cpdss.vesselinfo.entity.RuleVesselMappingInput;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RuleVesselMappingInputRespository
    extends CommonCrudRepository<RuleVesselMappingInput, Long> {

  List<RuleVesselMappingInput> findByRuleVesselMappingAndIsActive(
      RuleVesselMapping ruleVesselMapping, Boolean isActive);

  @Query(
      "SELECT count(RVM) FROM RuleVesselMappingInput RVM WHERE RVM.suffix = ?1 AND RVM.isActive = ?2")
  Long checkIsRuleTemplateInputMappedForSuffix(String suffix, Boolean isActive);

  @Query(
      "SELECT count(RVM) FROM RuleVesselMappingInput RVM WHERE RVM.prefix = ?1 AND RVM.isActive = ?2")
  Long checkIsRuleTemplateInputMappedForPrefix(String prefix, Boolean isActive);

  @Query(
      "SELECT count(RVM) FROM RuleVesselMappingInput RVM WHERE RVM.prefix = ?1 AND RVM.suffix = ?2 AND RVM.isActive = ?3")
  Long checkIsRuleTemplateInputMapped(String prefix, String suffix, Boolean isActive);
}
