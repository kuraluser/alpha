package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * author:Lincy Ignus
 */
public interface LoadableStudyCommunicationStatusRepository extends CommonCrudRepository<LoadableStudyCommunicationStatus, Long> {


    public Optional<LoadableStudyCommunicationStatus> findByReferenceIdAndMessageType(
            Long referenceId, String messageType);

    public Optional<LoadableStudyCommunicationStatus> findByMessageUUID(
            Long messageUUID);
    @Query(
            "FROM LoadableStudyCommunicationStatus LS WHERE LS.messageUUID IS NOT NULL AND LS.communicationStatus = ?1 ")
    public List<LoadableStudyCommunicationStatus> findByCommunicationStatusOrderByCommunicationDateTimeASC(
            final String communicationStatus);

    @Transactional
    @Modifying
    @Query("UPDATE LoadableStudyCommunicationStatus LS SET LS.communicationStatus = ?1 WHERE referenceId = ?2 ")
    public void updateLoadableStudyCommunicationStatus(String communicationStatus, Long referenceId);
}
