/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargePlanCommingleDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DischargePlanCommingleDetailsRepository
    extends CommonCrudRepository<DischargePlanCommingleDetails, Long> {

  @Transactional
  @Modifying
  @Query(
      "UPDATE DischargePlanCommingleDetails SET isActive = false WHERE dischargeInformation = ?1")
  public void deleteByDischargingInformation(DischargeInformation dischargingInformation);

  @Query(
      "FROM DischargePlanCommingleDetails PL INNER JOIN DischargeInformation LI ON PL.dischargeInformation.id = LI.id AND LI.dischargingPatternXid = ?1 AND PL.isActive = ?2")
  public List<DischargePlanCommingleDetails> findByDischargePatternXIdAndIsActive(
      Long patternId, Boolean isActive);
}
