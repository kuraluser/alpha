/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.BillOfLadding;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface BillOfLaddingRepository extends CommonCrudRepository<BillOfLadding, Long> {

  @Query(
      "FROM BillOfLadding BL INNER JOIN DischargeInformation LI ON BL.dischargeInformation.id = LI.id AND LI.dischargingPatternXid = ?1 AND BL.portId = ?2 AND BL.isActive = ?3")
  public List<BillOfLadding> findByDischargePatternXIdAndPortIdAndIsActive(
      Long patternId, Long portId, Boolean isActive);

  public List<BillOfLadding> findByCargoNominationIdAndIsActive(
      Long cargoNominationId, Boolean isActive);

  public List<BillOfLadding> findByCargoNominationIdInAndIsActive(
      List<Long> cargoNominationId, Boolean isActive);

  public List<BillOfLadding> findByPortIdInAndIsActive(
      List<Long> cargoNominationId, Boolean isActive);

  @Query(
      "FROM BillOfLadding BL INNER JOIN DischargeInformation LI ON BL.dischargeInformation.id = LI.id AND LI.dischargingPatternXid = ?1 AND BL.isActive = ?2")
  public List<BillOfLadding> findByDischargePatternXIdAndIsActive(
      Long loadablePatternId, Boolean isActive);
}
