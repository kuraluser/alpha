/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for loadable study entity {@link LoadableStudy}
 *
 * @author suhail.k
 */
public interface LoadableStudyRepository extends CommonCrudRepository<LoadableStudy, Long> {

  List<LoadableStudy>
      findByVesselXIdAndVoyageAndPlanningTypeXIdAndIsActiveTrueOrderByCreatedDateTimeDesc(
          final Long vesselXId, final Voyage voyage, Integer typeId);

  List<LoadableStudy>
      findByVoyageAndVesselXIdAndPlanningTypeXIdAndIsActiveTrueOrderByCreatedDateTimeDesc(
          final Voyage voyage, final Long vesselXId, Integer typeId);

  default List<LoadableStudy> findAllLoadableStudy(
      Long vesselXId, Voyage voyage, Integer planningId) {
    return findByVesselXIdAndVoyageAndPlanningTypeXIdAndIsActiveTrueOrderByCreatedDateTimeDesc(
        vesselXId, voyage, planningId);
  }

  @Query(
      value =
          "SELECT LS FROM LoadableStudy LS LEFT JOIN FETCH LS.attachments "
              + "WHERE LS.voyage= ?1 AND  LS.vesselXId = ?2 "
              + "AND LS.planningTypeXId =?3 AND LS.isActive = true"
              + " ORDER BY LS.createdDateTime desc")
  List<LoadableStudy>
      findByVoyageAndVesselXIdAndPlanningTypeXIdAndIsActiveTrueOrderByCreatedDateTimeDescWithAttachments(
          final Voyage voyage, final Long vesselXId, Integer typeId);

  public List<LoadableStudy> findByVesselXId(final Long vesselXId);

  public List<LoadableStudy> findByVesselXIdAndVoyageAndIsActiveAndLoadableStudyStatus_id(
      final Long vesselXId, final Voyage voyage, final boolean isActive, Long id);

  // Use at test Only
  public List<LoadableStudy> findByVesselXIdAndVoyageAndIsActiveOrderByCreatedDateTimeDesc(
      final Long vesselXId, final Voyage voyage, final boolean isActive);

  public Optional<LoadableStudy> findByIdAndIsActive(Long id, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadableStudy SET loadableStudyStatus.id = ?1 where id = ?2")
  public void updateLoadableStudyStatus(Long loadableStudyStatusId, Long id);

  @Query(
      "FROM LoadableStudy LS WHERE LS.voyage= ?1 AND  LS.loadableStudyStatus.id = ?2 AND LS.isActive = ?3 AND LS.planningTypeXId =?4")
  public Optional<LoadableStudy> findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
      Voyage voyage, Long status, Boolean isActive, Integer planningId);

  public LoadableStudy findByVoyageAndNameIgnoreCaseAndIsActiveAndPlanningTypeXId(
      Voyage voyage, String name, boolean isActive, Integer planningId);

  @Query("FROM LoadableStudy LS WHERE LS.voyage.id IN ?1 AND LS.isActive = true")
  List<LoadableStudy> findByListOfVoyage(List<Long> voyageIds);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadableStudy LS SET LS.feedbackLoop = ?1, LS.feedbackLoopCount = ?2 WHERE id = ?3")
  public void updateLoadableStudyFeedbackLoopAndFeedbackLoopCount(
      Boolean feedbackLoop, Integer feedbackLoopCount, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE LoadableStudy LS SET LS.isPortsComplete = ?2 WHERE LS.id = ?1")
  public void updateLoadableStudyIsPortsComplete(Long loadableStudyId, Boolean isPortsComplete);

  public Optional<LoadableStudy> findByIdAndIsActiveAndVesselXId(
      Long id, Boolean isActive, Long vesselId);

  public boolean existsByNameIgnoreCaseAndPlanningTypeXIdAndVoyageAndIsActive(
      String name, int i, Voyage voyage, boolean b);

  public boolean existsByNameIgnoreCaseAndPlanningTypeXIdAndVoyageAndIsActiveAndIdNot(
      String name, int i, Voyage voyage, boolean b, Long id);

  @Query(
      "From LoadableStudy LS WHERE LS.voyage.id= ?2 AND  LS.vesselXId = ?1 AND LS.planningTypeXId = ?3 AND isActive = true")
  Optional<List<LoadableStudy>> getLoadableStudyByVesselVoyagePlanningType(
      Long vesselId, Long voyageId, Integer dischargingOperationId);

  boolean existsByIdAndPlanningTypeXIdAndVoyageAndIsActive(
      long id, int planningTypeId, Voyage voyage, boolean b);

  @Transactional
  @Modifying
  @Query("UPDATE LoadableStudy LS SET LS.isObqComplete = ?1 WHERE id = ?2")
  public void updateOBQStatusById(Boolean isOBQCompleted, Long id);

  @Query(
      value =
          "select ls.voyage_xid From loadable_study ls where ls.vessel_xid = ?1 and ls.planning_type_xid = ?2 "
              + " and is_active = true",
      nativeQuery = true)
  List<Long> getActiveVoyageIdsByVesselIdAndPlanningType(
      Long vesselId, Integer dischargingOperationId);

  @Query(
      value =
          "select temp.id,temp.etd from ( "
              + "select ls.id,lspr.etd,lspr.port_order,row_number() over (partition by ls.id order by port_order desc) "
              + "as row_number  from  loadable_study ls inner join loadable_study_port_rotation lspr"
              + " on ls.id = lspr.loadable_study_xid "
              + "where ls.vessel_xid  =?1 and lspr.is_active = true ) temp where temp.row_number =1",
      nativeQuery = true)
  public List<Object[]> getVesellEtd(Long vesselId);

  List<LoadableStudy>
      findByVesselXIdAndVoyageAndIsActiveAndLoadableStudyStatus_idAndPlanningTypeXId(
          long vesselId, Voyage voyage, boolean b, Long confirmedStatusId, int i);
}
