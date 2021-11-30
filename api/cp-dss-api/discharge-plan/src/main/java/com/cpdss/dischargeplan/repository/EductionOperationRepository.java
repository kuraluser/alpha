/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import com.cpdss.dischargeplan.entity.EductionOperation;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** @author pranav.k */
@Repository
public interface EductionOperationRepository extends CommonCrudRepository<EductionOperation, Long> {

  @Modifying
  @Transactional
  @Query(value = "UPDATE EductionOperation SET isActive = false WHERE dischargingSequence = ?1")
  public void deleteByDischargingSequence(DischargingSequence dischargingSequence);

  public List<EductionOperation> findByDischargingSequenceAndIsActiveTrue(
      DischargingSequence loadingSequence);
}
