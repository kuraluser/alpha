/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.loadableStudy.LoadableStudyModels;
import com.cpdss.loadablestudy.entity.DischargeStudyPortInstruction;
import com.cpdss.loadablestudy.entity.PortInstruction;
import com.cpdss.loadablestudy.repository.DischargeStudyPortInstructionRepository;
import com.cpdss.loadablestudy.repository.PortInstructionRepository;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      PortInstructionService.class,
    })
public class PortInstructionServiceTest {
  @Autowired PortInstructionService portInstructionService;
  @MockBean DischargeStudyPortInstructionRepository dischargeStudyPortInstructionRepository;
  @MockBean PortInstructionRepository portInstructionRepository;
  public static final String FAILED = "FAILED";

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

  @Test
  void testGetInstructions() {
    PortInstructionService spyService = spy(PortInstructionService.class);
    LoadableStudyModels.EmptyRequest request =
        LoadableStudyModels.EmptyRequest.newBuilder().build();
    StreamRecorder<LoadableStudyModels.InstructionReply> responseObserver = StreamRecorder.create();
    List<PortInstruction> instructions = new ArrayList<>();
    PortInstruction portInstruction = new PortInstruction();
    portInstruction.setId(1l);
    portInstruction.setPortInstruction("1");
    instructions.add(portInstruction);
    when(portInstructionRepository.findByIsActive(anyBoolean())).thenReturn(instructions);
    ReflectionTestUtils.setField(
        spyService, "portInstructionRepository", portInstructionRepository);

    spyService.getInstructions(request, responseObserver);
    List<LoadableStudyModels.InstructionReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    Assert.assertEquals(1l, replies.get(0).getInstructionDetails(0).getId());
  }
}
