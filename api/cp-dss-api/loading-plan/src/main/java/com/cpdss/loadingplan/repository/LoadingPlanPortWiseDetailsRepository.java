/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanPortWiseDetailsRepository
    extends CommonCrudRepository<LoadingPlanPortWiseDetails, Long> {}
