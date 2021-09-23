/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingInstructionTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargingInstructionTemplateRepository
    extends CommonCrudRepository<DischargingInstructionTemplate, Long> {

  @Query(
      "From DischargingInstructionTemplate LIT where LIT.dischargingInsructionType.id = ?1"
          + " AND LIT.referenceXId = ?2 OR LIT.dischargingInsructionType.id = ?3 AND LIT.isActive = true")
  public List<DischargingInstructionTemplate> findALLByDischargingInsructionTypeIdAndReferenceId(
      Long var1, Long var2, Long var3);
}
