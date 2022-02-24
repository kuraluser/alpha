/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.PortWiseTimeRequiredForLoading;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** @author pranav.k */
@Repository
public interface PortWiseTimeRequiredForLoadingRepository
    extends CommonCrudRepository<PortWiseTimeRequiredForLoading, Long> {

  public List<PortWiseTimeRequiredForLoading> findByLoadablePatternXIdAndIsActiveTrue(
      Long loadablePatternId);

  Optional<PortWiseTimeRequiredForLoading> findByLoadablePatternXIdAndPortRotationIdAndIsActiveTrue(
      Long loadablePatternId, Long portRotationId);
}
