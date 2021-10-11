/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.AlgoErrors;
import com.cpdss.loadingplan.entity.LoadingInformation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AlgoErrorsRepository extends CommonCrudRepository<AlgoErrors, Long> {

  @Modifying
  @Transactional
  @Query(
      "UPDATE AlgoErrors SET isActive = false WHERE algoErrorHeading.id IN (SELECT AEH.id FROM AlgoErrorHeading AEH WHERE AEH.loadingInformation = ?1)")
  public void deleteByLoadingInformation(LoadingInformation loadingInformation);

  @Modifying
  @Transactional
  @Query(
      "UPDATE AlgoErrors SET isActive = false WHERE algoErrorHeading.id IN (SELECT AEH.id FROM AlgoErrorHeading AEH WHERE AEH.loadingInformation = ?1 AND AEH.conditionType = ?2)")
  public void deleteByLoadingInformationAndConditionType(
      LoadingInformation loadingInformation, Integer conditionType);
}
