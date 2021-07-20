/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.CargoValve;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoValveRepository extends CommonCrudRepository<CargoValve, Long> {}
