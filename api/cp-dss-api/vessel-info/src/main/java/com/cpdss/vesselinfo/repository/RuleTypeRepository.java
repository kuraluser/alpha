/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleType;
import java.util.List;

public interface RuleTypeRepository extends CommonCrudRepository<RuleType, Long> {

  List<RuleType> findByIsActive(boolean isActive);
}
