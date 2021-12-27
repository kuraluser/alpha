/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanCommunicationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** author:Selvy Thomas */
public interface LoadingPlanCommunicationStatusRepository
    extends CommonCrudRepository<LoadingPlanCommunicationStatus, Long> {

  Optional<List<LoadingPlanCommunicationStatus>>
      findByCommunicationStatusAndMessageTypeOrderByCommunicationDateTimeAsc(
          final String communicationStatus, final String messageType);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadingPlanCommunicationStatus LS SET LS.communicationStatus = ?1, LS.isActive = ?2 WHERE referenceId = ?3 ")
  void updateCommunicationStatus(String communicationStatus, Boolean isActive, Long referenceId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadingPlanCommunicationStatus LS SET LS.communicationStatus = ?1 WHERE referenceId = ?2 ")
  void updateCommunicationStatus(String communicationStatus, Long referenceId);
}
