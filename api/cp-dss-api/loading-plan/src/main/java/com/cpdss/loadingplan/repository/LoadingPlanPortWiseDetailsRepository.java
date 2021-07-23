/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanPortWiseDetailsRepository
    extends CommonCrudRepository<LoadingPlanPortWiseDetails, Long> {

  List<LoadingPlanPortWiseDetails> findByLoadingSequenceAndIsActiveTrueOrderById(
      LoadingSequence loadingSequence);
}
