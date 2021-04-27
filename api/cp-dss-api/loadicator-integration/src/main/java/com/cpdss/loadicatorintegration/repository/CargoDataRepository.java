/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.CargoData;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import java.util.Optional;

public interface CargoDataRepository extends CommonCrudRepository<CargoData, Long> {

  public Optional<CargoData> findByStowagePlan(StowagePlan stowagePlan);
}
