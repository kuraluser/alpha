/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingSequenceStabilityParameters;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingSequenceStabiltyParametersRepository
    extends CommonCrudRepository<LoadingSequenceStabilityParameters, Long> {

  public List<LoadingSequenceStabilityParameters> findByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE LoadingSequenceStabilityParameters SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  public List<LoadingSequenceStabilityParameters> findByLoadingInformationAndIsActiveOrderByTime(
      LoadingInformation loadingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query(
      "UPDATE LoadingSequenceStabilityParameters SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  public List<LoadingSequenceStabilityParameters> findByLoadingInformationId(
      Long loadingInformation);
}
