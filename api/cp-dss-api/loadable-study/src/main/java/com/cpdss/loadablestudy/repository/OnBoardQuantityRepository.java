/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import java.util.List;

/** @Author jerin.g */
public interface OnBoardQuantityRepository extends CommonCrudRepository<OnBoardQuantity, Long> {
  /**
   * @param loadableStudy
   * @param isActive
   * @return List<OnBoardQuantity>
   */
  public List<OnBoardQuantity> findByLoadableStudyAndIsActive(
      LoadableStudy loadableStudy, Boolean isActive);
}
