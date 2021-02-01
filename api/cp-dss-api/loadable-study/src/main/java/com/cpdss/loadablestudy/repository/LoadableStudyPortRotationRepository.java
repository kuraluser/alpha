/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy.id = ?1 AND LSPR.isActive = ?2 ORDER BY LSPR.portOrder")
  public List<LoadableStudyPortRotation> findByLoadableStudyAndIsActive(
      Long loadableStudyId, Boolean isActive);

  /**
   * @param loadableStudy
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 ORDER BY LSPR.portOrder")
  public List<Long> findByLoadableStudyAndIsActive(
      final LoadableStudy loadableStudy, final boolean isActive);

  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 AND LSPR.portOrder =?3")
  public Long findByLoadableStudyAndIsActiveAndPortOrder(
      final LoadableStudy loadableStudy, final boolean isActive, final Long portOrder);

  @Transactional
  @Modifying
  @Query(
      "Update LoadableStudyPortRotation set isActive = false where loadableStudy = ?1 AND operation.id = 1 AND portXId in ?2 ")
  public void deleteLoadingPortRotation(final LoadableStudy loadableStudy, List<Long> portId);

  @Transactional
  @Modifying
  @Query(
      "Update LoadableStudyPortRotation set isActive = false where loadableStudy = ?1 AND operation.id = 1 AND portXId = ?2 ")
  public void deleteLoadingPortRotationByPort(final LoadableStudy loadableStudy, Long portId);

  public LoadableStudyPortRotation findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(
      LoadableStudy loadableStudy, boolean isActive);

  @Query(
      "SELECT LSPR.portXId FROM LoadableStudyPortRotation LSPR WHERE LSPR.portOrder = (SELECT MAX(portOrder) as PO FROM LoadableStudyPortRotation WHERE loadableStudy = ?1 and operation = ?2 and isActive = ?3) AND LSPR.loadableStudy = ?1 AND LSPR.operation = ?2 and LSPR.isActive = ?3")
  public Long findLastPort(
      final LoadableStudy loadableStudy,
      final CargoOperation cargoOperation,
      final boolean isActive);
}
