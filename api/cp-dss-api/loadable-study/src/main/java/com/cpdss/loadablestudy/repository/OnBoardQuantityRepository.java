/* Licensed at AlphaOri Technologies */
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

  /**
   * @param id
   * @param isActive
   * @return
   */
  public OnBoardQuantity findByIdAndIsActive(Long id, Boolean isActive);

  /**
   * @param loadableStudy
   * @param portId
   * @param isActive
   * @return
   */
  public List<OnBoardQuantity> findByLoadableStudyAndPortIdAndIsActive(
      LoadableStudy loadableStudy, Long portId, Boolean isActive);
}
