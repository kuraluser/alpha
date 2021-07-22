/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.DeballastingRate;
import org.springframework.stereotype.Repository;

@Repository
public interface DeballastingRateRepository extends CommonCrudRepository<DeballastingRate, Long> {}
