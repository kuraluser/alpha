/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.BallastValve;
import org.springframework.stereotype.Repository;

@Repository
public interface BallastValveRepository extends CommonCrudRepository<BallastValve, Long> {}
