/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import java.util.List;
import java.util.Optional;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.CargoData;
import com.cpdss.loadicatorintegration.entity.StowagePlan;

public interface CargoDataRepository extends CommonCrudRepository<CargoData, Long> {
	
	public Optional<CargoData> findByStowagePlan(StowagePlan stowagePlan);
}
