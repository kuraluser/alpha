/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.AlgoErrors;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AlgoErrorsRepository extends CommonCrudRepository<AlgoErrors, Long> {

  @Modifying
  @Transactional
  @Query(
      "UPDATE AlgoErrors SET isActive = false WHERE algoErrorHeading.id IN (SELECT AEH.id FROM AlgoErrorHeading AEH WHERE AEH.dischargingInformation = ?1)")
  public void deleteByLoadingInformation(DischargeInformation dischargingInformation);
}
