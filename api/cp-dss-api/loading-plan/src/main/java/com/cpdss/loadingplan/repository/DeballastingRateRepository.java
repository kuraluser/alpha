/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DeballastingRateRepository extends CommonCrudRepository<DeballastingRate, Long> {

  List<DeballastingRate> findByLoadingSequenceAndIsActiveTrueOrderById(
      LoadingSequence loadingSequence);

  List<DeballastingRate> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
      LoadingPlanPortWiseDetails loadingPlanPortWiseDetails);
}
