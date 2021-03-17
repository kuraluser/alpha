/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails;
import java.util.List;
import java.util.Optional;

public interface LoadablePatternCargoDetailsRepository
    extends CommonCrudRepository<LoadablePatternCargoDetails, Long> {

  public List<LoadablePatternCargoDetails> findByLoadablePatternIdAndIsActive(
      Long loadablePatternId, boolean isActive);

  public List<LoadablePatternCargoDetails>
      findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
          Long loadablePatternId, Long portId, String operationType, boolean isActive);

  public Optional<LoadablePatternCargoDetails> findById(Long loadablePatternId);

  public List<LoadablePatternCargoDetails> findByLoadablePatternIdInAndIsActive(
      List<Long> loadablePatternId, boolean isActive);
}
