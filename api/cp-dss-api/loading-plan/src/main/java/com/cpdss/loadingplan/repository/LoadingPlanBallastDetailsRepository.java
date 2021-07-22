/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanBallastDetailsRepository
    extends CommonCrudRepository<LoadingPlanBallastDetails, Long> {}
