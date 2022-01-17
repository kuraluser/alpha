/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import com.cpdss.dischargeplan.entity.DischargingTankTransferDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** @author pranav.k */
@Repository
public interface DischargingTankTransferDetailsRepository
    extends CommonCrudRepository<DischargingTankTransferDetails, Long> {

  @Modifying
  @Query(
      "UPDATE DischargingTankTransferDetails DTTD SET DTTD.isActive = false WHERE DTTD.dischargingTankTransfer.id IN (SELECT DTT.id FROM DischargingTankTransfer DTT WHERE DTT.dischargingSequence = ?1 AND DTT.isActive = true) AND DTTD.isActive = true")
  void deleteByDischargingSequence(DischargingSequence dischargingSequence);
}
