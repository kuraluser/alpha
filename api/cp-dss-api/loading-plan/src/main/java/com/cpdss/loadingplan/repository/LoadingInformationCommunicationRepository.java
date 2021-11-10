/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.loadingplan.entity.LoadingInformation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LoadingInformationCommunicationRepository extends LoadingInformationRepository {

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  LoadingInformation save(LoadingInformation loadingInformation);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Modifying
  @Query(
      value =
          "UPDATE loading_information SET loading_status_xid = ?1, arrival_status_xid=?2, departure_status_xid=?3, "
              + "stages_min_amount_xid=?4, stages_duration_xid=?5 WHERE id = ?6",
      nativeQuery = true)
  void updateLoadingInformationStatusWithId(
      Long loadingInformationStatus,
      Long arrivalStatus,
      Long departureStatus,
      Long stageOffset,
      Long stageDuration,
      Long id);
}
