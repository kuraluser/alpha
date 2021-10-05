/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.CargoValve;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoValveRepository extends CommonCrudRepository<CargoValve, Long> {}
