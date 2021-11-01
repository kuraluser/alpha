/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingSequenceRepository extends CommonCrudRepository<LoadingSequence, Long> {

  public List<LoadingSequence> findByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  public List<LoadingSequence> findByLoadingInformationAndIsActiveOrderBySequenceNumber(
      LoadingInformation loadingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE LoadingSequence SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query("UPDATE LoadingSequence SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Query(
      "SELECT DISTINCT cargoNominationXId FROM LoadingSequence WHERE loadingInformation = ?1 AND isActive = ?2")
  public List<Long> findToBeLoadedCargoNominationIdByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  public List<LoadingSequence> findByLoadingInformationId(Long loadingInformationId);
}
