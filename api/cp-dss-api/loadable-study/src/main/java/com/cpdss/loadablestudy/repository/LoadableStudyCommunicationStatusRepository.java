/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** author:Lincy Ignus */
public interface LoadableStudyCommunicationStatusRepository
    extends CommonCrudRepository<LoadableStudyCommunicationStatus, Long> {

  public Optional<LoadableStudyCommunicationStatus> findByReferenceIdAndMessageType(
      Long referenceId, String messageType);

  public Optional<LoadableStudyCommunicationStatus> findByMessageUUID(String messageUUID);

  @Query(
      "FROM LoadableStudyCommunicationStatus LS WHERE LS.messageUUID IS NOT NULL AND LS.communicationStatus = ?1 ")
  public List<LoadableStudyCommunicationStatus>
      findByCommunicationStatusOrderByCommunicationDateTimeASC(final String communicationStatus);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadableStudyCommunicationStatus LS SET LS.communicationStatus = ?1 WHERE referenceId = ?2 ")
  public void updateLoadableStudyCommunicationStatus(String communicationStatus, Long referenceId);

  // Get recent message
  Optional<LoadableStudyCommunicationStatus>
      findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(
          Long referenceId, String messageType);

  List<LoadableStudyCommunicationStatus>
      findByCommunicationStatusAndMessageTypeOrderByCommunicationDateTimeAsc(
          final String communicationStatus, final String messageType);
}
