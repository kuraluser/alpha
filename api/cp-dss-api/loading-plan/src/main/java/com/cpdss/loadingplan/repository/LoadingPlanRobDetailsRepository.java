/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanRobDetailsRepository
    extends CommonCrudRepository<LoadingPlanRobDetails, Long> {

  List<LoadingPlanRobDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);
}
