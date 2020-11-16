/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import java.util.List;

public interface LoadableStudyPortRotationRepository
    extends CommonCrudRepository<LoadableStudyPortRotation, Long> {

  /**
   * Get active port rotation list against a loadable study by excluding discharging ports
   *
   * @param loadableStudy
   * @param operation
   * @param isActive
   * @return
   */
  public List<LoadableStudyPortRotation>
      findByLoadableStudyAndOperationNotAndIsActiveOrderByPortOrder(
          final LoadableStudy loadableStudy,
          final CargoOperation operation,
          final boolean isActive);

  /**
   * Get active port rotation for discharging ports against a loadable study
   *
   * @param loadableStudy
   * @param operation
   * @param isActive
   * @return
   */
  public List<LoadableStudyPortRotation> findByLoadableStudyAndOperationAndIsActive(
      final LoadableStudy loadableStudy, final CargoOperation operation, final boolean isActive);

  /**
   * Get active port rotation for discharging ports against a loadable study order by port order
   *
   * @param loadableStudy
   * @param operation
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  public List<LoadableStudyPortRotation> findByLoadableStudyAndOperationAndIsActiveOrderByPortOrder(
      final LoadableStudy loadableStudy, final CargoOperation operation, final boolean isActive);
}
