/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.CargoLoadingRate;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoLoadingRateRepository extends CommonCrudRepository<CargoLoadingRate, Long> {}
