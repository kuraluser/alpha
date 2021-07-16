/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanRobDetailsRepository
    extends CommonCrudRepository<LoadingPlanRobDetails, Long> {}
