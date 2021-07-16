/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanStowageDetailsRepository
    extends CommonCrudRepository<LoadingPlanStowageDetails, Long> {}
