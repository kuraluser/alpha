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

  default List<LoadableStudy> findAllLoadableStudy(
      Long vesselXId, Voyage voyage, Integer planningId) {
    return findByVesselXIdAndVoyageAndPlanningTypeXIdAndIsActiveTrueOrderByCreatedDateTimeDesc(
        vesselXId, voyage, planningId);
  }

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
      "FROM LoadableStudy LS WHERE LS.voyage= ?1 AND  LS.loadableStudyStatus.id = ?2 AND LS.isActive = ?3")
  public Optional<LoadableStudy> findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
      Voyage voyage, Long status, Boolean isActive, Integer planningId);

  public LoadableStudy findByVoyageAndNameIgnoreCaseAndIsActiveAndPlanningTypeXId(
      Voyage voyage, String name, boolean isActive, Integer planningId);

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

  /*@Transactional
  @Modifying
  @Query("UPDATE LoadableStudy LS SET LS.messageUUID = ?1, LS.sequenceNo = ?2 WHERE id = ?3")
  public void updateLoadableStudyUUIDAndSeqNo(String messageUUID, String sequenceNo, Long id);*/

  public Optional<LoadableStudy> findByIdAndIsActiveAndVesselXId(
      Long id, Boolean isActive, Long vesselId);

  public boolean existsByNameAndPlanningTypeXIdAndVoyageAndIsActive(
      String name, int i, Voyage voyage, boolean b);
}
