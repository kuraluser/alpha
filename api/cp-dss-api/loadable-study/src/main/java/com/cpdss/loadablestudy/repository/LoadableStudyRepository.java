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

  public List<LoadableStudy> findByVesselXIdAndVoyageAndIsActiveOrderByCreatedDateTimeDesc(
      final Long vesselXId, final Voyage voyage, final boolean isActive);

  public Optional<LoadableStudy> findByIdAndIsActive(Long id, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadableStudy SET loadableStudyStatus.id = ?1 where id = ?2")
  public void updateLoadableStudyStatus(Long loadableStudyStatusId, Long id);

  @Query(
      "FROM LoadableStudy LS WHERE LS.voyage= ?1 AND  LS.loadableStudyStatus.id = ?2 AND LS.isActive = ?3")
  public Optional<LoadableStudy> findByVoyageAndLoadableStudyStatusAndIsActive(
      Voyage voyage, Long status, Boolean isActive);

  public LoadableStudy findByVoyageAndNameIgnoreCaseAndIsActive(
      Voyage voyage, String name, boolean isActive);

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

  @Transactional
  @Modifying
  @Query("UPDATE LoadableStudy LS SET LS.messageUUID = ?1 WHERE id = ?2 ")
  public void updateLoadableStudyUUID(String messageUUID, Long id);

  public Optional<LoadableStudy> findByMessageUUIDAndIsActive(String messageUUID, boolean isActive);
}
