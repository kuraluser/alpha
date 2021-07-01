/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import java.util.List;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.RuleVesselDropDownValues;

public interface RuleVesselDropDownValuesRepository
    extends CommonCrudRepository<RuleVesselDropDownValues, Long> {
	 
	List<RuleVesselDropDownValues> findByIsActive(Boolean isActive);
}
