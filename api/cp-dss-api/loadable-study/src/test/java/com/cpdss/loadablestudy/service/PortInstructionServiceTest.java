/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertThat;

import com.cpdss.loadablestudy.entity.DischargeStudyPortInstruction;
import com.cpdss.loadablestudy.repository.DischargeStudyPortInstructionRepository;
import com.cpdss.loadablestudy.repository.PortInstructionRepository;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      PortInstructionService.class,
    })
public class PortInstructionServiceTest {
  @Autowired PortInstructionService portInstructionService;
  @MockBean DischargeStudyPortInstructionRepository dischargeStudyPortInstructionRepository;
  @MockBean PortInstructionRepository portInstructionRepository;

  @Test
  void testGetPortWiseInstructions() {
    long dischargeStudyId = 1l;
    List<Long> portIds = new ArrayList<>();
    java.util.List<DischargeStudyPortInstruction> portInstructions = new ArrayList<>();
    DischargeStudyPortInstruction dischargeStudyPortInstruction =
        new DischargeStudyPortInstruction();
    dischargeStudyPortInstruction.setPortId(1l);
    portInstructions.add(dischargeStudyPortInstruction);
    Mockito.when(
            dischargeStudyPortInstructionRepository.findByDischargeStudyIdAndPortIdInAndIsActive(
                Mockito.anyLong(), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(portInstructions);
    var result = portInstructionService.getPortWiseInstructions(dischargeStudyId, portIds);
    assertThat(result, IsMapContaining.hasKey(1L));
  }

  @Test
  void testSaveAll() {
    List<DischargeStudyPortInstruction> portInstructionsToSave = new ArrayList<>();
    portInstructionService.saveAll(portInstructionsToSave);
    Mockito.verify(dischargeStudyPortInstructionRepository).saveAll(Mockito.anyList());
  }
}
