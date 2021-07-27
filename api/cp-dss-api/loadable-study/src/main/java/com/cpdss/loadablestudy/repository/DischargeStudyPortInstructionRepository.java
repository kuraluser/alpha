/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.DischargeStudyPortInstruction;
import java.util.List;

public interface DischargeStudyPortInstructionRepository
    extends CommonCrudRepository<DischargeStudyPortInstruction, Long> {

  public List<DischargeStudyPortInstruction> findByDischargeStudyIdAndPortIdInAndIsActive(
      Long dischargeStudyId, List<Long> portIds, boolean isActive);
}
