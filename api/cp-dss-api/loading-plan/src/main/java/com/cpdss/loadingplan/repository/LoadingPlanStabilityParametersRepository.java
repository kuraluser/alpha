/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanStabilityParametersRepository
    extends CommonCrudRepository<LoadingPlanStabilityParameters, Long> {}
