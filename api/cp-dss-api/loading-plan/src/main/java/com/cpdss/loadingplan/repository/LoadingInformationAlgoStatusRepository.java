/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformationAlgoStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingInformationAlgoStatusRepository
    extends CommonCrudRepository<LoadingInformationAlgoStatus, Long> {

  Optional<LoadingInformationAlgoStatus> findByProcessIdAndIsActiveTrue(String processId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadingInformationAlgoStatus SET loadingInformationStatus.id = ?1 WHERE processId = ?2")
  public void updateLoadingInformationAlgoStatus(Long loadingInformationStatusId, String processId);

  Optional<LoadingInformationAlgoStatus> findByProcessIdAndLoadingInformationIdAndIsActiveTrue(
      String processId, Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadingInformationAlgoStatus SET isActive = false WHERE loadingInformation.id = ?1 AND processId = ?2")
  public void deleteLoadingInformationAlgoStatus(Long loadingInfoId, String processId);
}
