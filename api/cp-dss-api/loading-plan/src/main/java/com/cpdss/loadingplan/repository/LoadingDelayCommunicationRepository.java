/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.loadingplan.entity.LoadingDelay;
import java.util.Optional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface LoadingDelayCommunicationRepository extends LoadingDelayRepository {

  @Override
  @Transactional(isolation = Isolation.READ_UNCOMMITTED)
  public Optional<LoadingDelay> findById(Long id);

  /// List<LoadingDelay> saveAll(List<LoadingDelay> loadingDelays);

  @Override
  LoadingDelay save(LoadingDelay loadingDelay);
}
