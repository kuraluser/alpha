/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleVesselMapping;
import com.cpdss.vesselinfo.entity.RuleVesselMappingInput;
import java.util.List;

public interface RuleVesselMappingInputRespository
    extends CommonCrudRepository<RuleVesselMappingInput, Long> {

  List<RuleVesselMappingInput> findByRuleVesselMappingAndIsActive(
      RuleVesselMapping ruleVesselMapping, Boolean isActive);
}
