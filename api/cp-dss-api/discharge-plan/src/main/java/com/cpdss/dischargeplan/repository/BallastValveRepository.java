/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.BallastValve;
import org.springframework.stereotype.Repository;

@Repository
public interface BallastValveRepository extends CommonCrudRepository<BallastValve, Long> {}
