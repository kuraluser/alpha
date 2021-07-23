/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanStowageDetailsRepository
    extends CommonCrudRepository<LoadingPlanStowageDetails, Long> {

  List<LoadingPlanStowageDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);
}
