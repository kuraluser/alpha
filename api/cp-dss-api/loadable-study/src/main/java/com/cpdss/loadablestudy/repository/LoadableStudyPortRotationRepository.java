/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;

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
  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 ORDER BY LSPR.portOrder")
  public List<LoadableStudyPortRotation> findByLoadableStudyAndIsActiveOrderByPortOrder(
      final LoadableStudy loadableStudy, final boolean isActive);

  /**
   * @param loadableStudyId
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy.id = ?1 AND LSPR.isActive = ?2")
  public List<LoadableStudyPortRotation> findByLoadableStudyAndIsActive(
      Long loadableStudyId, Boolean isActive);

  /**
   * @param loadableStudy
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 ORDER BY LSPR.portOrder")
  public Set<Long> findByLoadableStudyAndIsActive(
      final LoadableStudy loadableStudy, final boolean isActive);

  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 AND LSPR.portOrder =?3")
  public Long findByLoadableStudyAndIsActiveAndPortOrder(
      final LoadableStudy loadableStudy, final boolean isActive, final Long portOrder);
}
