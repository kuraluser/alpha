/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleVesselDropDownValues;
import java.util.List;

public interface RuleVesselDropDownValuesRepository
    extends CommonCrudRepository<RuleVesselDropDownValues, Long> {

  List<RuleVesselDropDownValues> findByIsActive(Boolean isActive);
}
