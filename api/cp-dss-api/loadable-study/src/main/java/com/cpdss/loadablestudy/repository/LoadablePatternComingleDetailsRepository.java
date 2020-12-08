/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePatternComingleDetails;
import java.util.Optional;

/** @Author jerin.g */
public interface LoadablePatternComingleDetailsRepository
    extends CommonCrudRepository<LoadablePatternComingleDetails, Long> {

  /**
   * @param id
   * @param isActive
   * @return Optional<LoadablePatternComingleDetails>
   */
  public Optional<LoadablePatternComingleDetails> findByIdAndIsActive(Long id, Boolean isActive);

  /**
   * @param id
   * @param b
   * @return Optional<LoadablePatternComingleDetails>
   */
  public Optional<LoadablePatternComingleDetails> findByLoadablePatternDetailsIdAndIsActive(
      Long loadablePatternDetailsId, Boolean isActive);
}
