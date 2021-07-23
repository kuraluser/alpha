/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanStabilityParametersRepository
    extends CommonCrudRepository<LoadingPlanStabilityParameters, Long> {

  Optional<LoadingPlanStabilityParameters> findByLoadingPlanPortWiseDetailsAndIsActiveTrue(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);
}
