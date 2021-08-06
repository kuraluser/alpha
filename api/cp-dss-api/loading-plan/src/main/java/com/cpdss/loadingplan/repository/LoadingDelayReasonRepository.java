/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingDelay;
import com.cpdss.loadingplan.entity.LoadingDelayReason;
import com.cpdss.loadingplan.entity.ReasonForDelay;
import java.util.List;
import java.util.Optional;

public interface LoadingDelayReasonRepository
    extends CommonCrudRepository<LoadingDelayReason, Long> {

  Optional<LoadingDelayReason> findByLoadingDelayAndIsActiveTrue(LoadingDelay var1);

  Optional<LoadingDelayReason> findByLoadingDelayAndReasonForDelayAndIsActiveTrue(
      LoadingDelay var1, ReasonForDelay var2);

  List<LoadingDelayReason> findAllByLoadingDelayAndIsActiveTrue(LoadingDelay var1);
}
