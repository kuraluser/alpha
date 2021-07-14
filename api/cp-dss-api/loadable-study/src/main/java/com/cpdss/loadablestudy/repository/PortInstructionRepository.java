/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.PortInstruction;
import java.util.List;

public interface PortInstructionRepository extends CommonCrudRepository<PortInstruction, Long> {
  public List<PortInstruction> findByIsActive(boolean isActive);
}
