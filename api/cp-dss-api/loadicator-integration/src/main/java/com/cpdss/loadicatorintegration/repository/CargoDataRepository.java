/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.CargoData;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import java.util.List;

public interface CargoDataRepository extends CommonCrudRepository<CargoData, Long> {

  public List<CargoData> findByStowagePlan(StowagePlan stowagePlan);
}
