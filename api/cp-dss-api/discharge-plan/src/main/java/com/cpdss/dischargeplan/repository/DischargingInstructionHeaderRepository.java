/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingInstructionHeader;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargingInstructionHeaderRepository
    extends CommonCrudRepository<DischargingInstructionHeader, Long> {

  @Query(
      value = "SELECT * FROM loading_instructions_header where is_active=true",
      nativeQuery = true)
  public List<DischargingInstructionHeader> getAllDischargingInstructionHeader();

  public List<DischargingInstructionHeader> findAllByIsActiveTrue();
}
