/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import com.cpdss.dischargeplan.entity.DischargingTankTransfer;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargingTankTransferRepository
    extends CommonCrudRepository<DischargingTankTransfer, Long> {

  @Modifying
  @Query("UPDATE DischargingTankTransfer SET isActive = false WHERE dischargingSequence = ?1")
  void deleteByDischargingSequence(DischargingSequence dischargingSequence);

  List<DischargingTankTransfer> findByDischargingSequenceAndIsActiveTrue(
      DischargingSequence dischargeSequence);

  @Query(
      "from DischargingTankTransfer dr where dr.dischargingSequence.id in ?1 and dr.isActive = true")
  List<DischargingTankTransfer> findByDischargingSequenceInAndIsActiveTrue(
      List<Long> dischargeSequencesIds);
}
