/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
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

  public List<LoadableStudyPortRotation> findByLoadableStudyAndOperation_idAndIsActive(
      final LoadableStudy loadableStudy, Long operation, final boolean isActive);
  /**
   * Get active port rotation for discharging ports against a loadable study order by port order
   *
   * @param loadableStudy
   * @param operation
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 ORDER BY LSPR.portOrder, LSPR.lastModifiedDateTime DESC")
  public List<LoadableStudyPortRotation> findByLoadableStudyAndIsActiveOrderByPortOrder(
      final LoadableStudy loadableStudy, final boolean isActive);

  /**
   * Get active port rotation for discharging ports against a loadable study order by operation type
   * and port order
   *
   * @param loadableStudy
   * @param isActive
   * @return List<LoadableStudyPortRotation>
   */
  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 AND LSPR.isActive = ?2 ORDER BY LSPR.operation.id, LSPR.portOrder")
  public List<LoadableStudyPortRotation> findByLoadableStudyAndIsActiveOrderByOperationAndPortOrder(
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
      "SELECT LSPR.portXId, LSPR.id FROM LoadableStudyPortRotation LSPR WHERE LSPR.portOrder = (SELECT MAX(portOrder) as PO FROM LoadableStudyPortRotation WHERE loadableStudy = ?1 and operation = ?2 and isActive = ?3) AND LSPR.loadableStudy = ?1 AND LSPR.operation = ?2 and LSPR.isActive = ?3")
  public Object findLastPort(
      final LoadableStudy loadableStudy,
      final CargoOperation cargoOperation,
      final boolean isActive);

  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation where loadableStudy = ?1 AND operation.id = 4 AND portXId in ?2 AND isActive = true")
  public List<Long> getTransitPorts(LoadableStudy loadableStudy, List<Long> portIds);

  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation where loadableStudy = ?1 AND operation.id = 1  AND isActive = true")
  public List<Long> getLoadingPorts(LoadableStudy loadableStudy);

  @Query(
      "SELECT DISTINCT portXId, lspr.loadableStudy.id, lspr.portOrder FROM LoadableStudyPortRotation lspr where lspr.loadableStudy.id IN ?1 AND lspr.operation.id = 1  AND isActive = true ORDER BY lspr.portOrder ASC")
  public List<Object[]> getDistinctLoadingPorts(List<Long> loadableStudy);

  @Query(
      "SELECT portXId FROM LoadableStudyPortRotation where loadableStudy = ?1 AND operation.id = 2  AND isActive = true")
  public List<Long> getDischarigingPorts(LoadableStudy loadableStudy);

  @Query(
      "SELECT DISTINCT portXId, lspr.loadableStudy.id, lspr.id FROM LoadableStudyPortRotation lspr where lspr.loadableStudy.id IN ?1 AND lspr.operation.id = 2  AND isActive = true ORDER BY lspr.id DESC")
  public List<Object[]> getDistinctDischarigingPortsById(List<Long> loadableStudy);

  public LoadableStudyPortRotation findFirstByLoadableStudyAndIsActiveOrderByPortOrderAsc(
      LoadableStudy loadableStudy, boolean isActive);

  public LoadableStudyPortRotation findByIdAndIsActive(Long id, boolean isActive);

  public LoadableStudyPortRotation
      findFirstByLoadableStudyAndOperationAndIsActiveOrderByPortOrderDesc(
          LoadableStudy loadableStudy, final CargoOperation operation, boolean isActive);

  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy.id = ?1 AND LSPR.isActive = ?2 ")
  public List<LoadableStudyPortRotation> findByLoadableStudyIdAndIsActive(
      final Long loadableStudyId, final boolean isActive);

  @Query(
      "select var.id as id, var.portXId as portId from LoadableStudyPortRotation var where var.loadableStudy.id = ?1 and var.isActive = ?2")
  List<PortRotationIdAndPortId> findAllIdAndPortIdsByLSId(Long loadableStudyId, boolean isActive);

  @Query(
      "select min(portOrder) from LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1 "
          + "AND LSPR.isActive = true AND LSPR.portXId in ?2 and operation.id=1")
  public Long findSmallestOrderForPorts(LoadableStudy loadableStudy, List<Long> portIds);

  public List<LoadableStudyPortRotation> findByLoadableStudyAndPortXIdAndIsActiveAndOperation(
      LoadableStudy loadableStudy, Long portId, boolean isActive, CargoOperation operation);

  public LoadableStudyPortRotation findByLoadableStudyAndPortXIdAndIsActive(
      LoadableStudy loadableStudy, Long portId, boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadableStudyPortRotation SET isPortRotationOhqComplete = ?2 WHERE id = ?1 AND isActive = true")
  public void updateIsOhqCompleteByIdAndIsActiveTrue(Long id, Boolean isOhqComplete);

  @Query(
      "FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy= ?1 AND  LSPR.portXId= ?2 and LSPR.operation.id = 2 AND LSPR.isActive = true")
  public LoadableStudyPortRotation findByLoadableStudyAndPortAndOperation(
      LoadableStudy loadableStudy, long portId);

  List<LoadableStudyPortRotation> findByLoadableStudyAndOperation_idNotAndIsActive(
      LoadableStudy loadableStudy, Long dischargingOperationId, boolean isActive);

  @Query("SELECT LSPR FROM LoadableStudyPortRotation LSPR WHERE LSPR.loadableStudy = ?1")
  public List<LoadableStudyPortRotation> findByLoadableStudy(final LoadableStudy loadableStudy);
}
