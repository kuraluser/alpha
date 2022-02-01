/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.CargoDischargingRate;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CargoDischargingRateRepository
    extends CommonCrudRepository<CargoDischargingRate, Long> {

  List<CargoDischargingRate> findByDischargingSequenceAndIsActiveTrueOrderById(
      DischargingSequence dischargingSequence);

  @Query(
      "from CargoDischargingRate dr where dr.dischargingSequence.id in ?1 and dr.isActive = true order by dr.id")
  List<CargoDischargingRate> findByDischargingSequenceInAndIsActiveTrueOrderById(
      List<Long> dischargeSequencesIds);

  @Modifying
  @Transactional
  @Query("UPDATE CargoDischargingRate SET isActive = false WHERE dischargingSequence = ?1")
  public void deleteByDischargingSequence(DischargingSequence dischargingSequence);
}
