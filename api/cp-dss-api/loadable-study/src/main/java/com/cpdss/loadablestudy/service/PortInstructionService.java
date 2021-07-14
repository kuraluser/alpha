/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.entity.DischargeStudyPortInstruction;
import com.cpdss.loadablestudy.repository.DischargeStudyPortInstructionRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * to handle port wise instructions
 *
 * @author arun.j
 */
@Slf4j
@Service
public class PortInstructionService {
  @Autowired DischargeStudyPortInstructionRepository dischargeStudyPortInstructionRepository;

  public Map<Long, List<DischargeStudyPortInstruction>> getPortWiseInstructions(
      long dischargeStudyId, List<Long> portIds) {
    List<DischargeStudyPortInstruction> portInstructions =
        dischargeStudyPortInstructionRepository.findByDischargeStudyIdAndPortIdIn(
            dischargeStudyId, portIds);

    return portInstructions.stream()
        .collect(Collectors.groupingBy(DischargeStudyPortInstruction::getPortId));
  }
}
