/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingDelay;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingDelayRepository extends CommonCrudRepository<LoadingDelay, Long> {

  public Optional<LoadingDelay> findByIdAndIsActive(Long id, Boolean isActive);
}
