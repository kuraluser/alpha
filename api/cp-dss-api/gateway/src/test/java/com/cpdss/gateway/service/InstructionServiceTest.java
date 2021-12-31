/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInstructionServiceGrpc;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {InstructionService.class})
public class InstructionServiceTest {

  @Autowired InstructionService instructionService;

  @MockBean
  PortInstructionServiceGrpc.PortInstructionServiceBlockingStub portInstructionServiceBlockingStub;

  @Test
  void testGetInstructions() {
    String correlationId = "1";
    Mockito.when(portInstructionServiceBlockingStub.getInstructions(Mockito.any()))
        .thenReturn(getIR());
    ReflectionTestUtils.setField(
        instructionService,
        "portInstructionServiceBlockingStub",
        this.portInstructionServiceBlockingStub);
    try {
      var response = this.instructionService.getInstructions(correlationId);
      assertEquals("1", response.getInstructions().get(0).getInstruction());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.InstructionReply getIR() {
    List<LoadableStudyModels.InstructionDetail> list = new ArrayList<>();
    LoadableStudyModels.InstructionDetail detail =
        LoadableStudyModels.InstructionDetail.newBuilder().setId(1L).setInstruction("1").build();
    list.add(detail);
    LoadableStudyModels.InstructionReply reply =
        LoadableStudyModels.InstructionReply.newBuilder().addAllInstructionDetails(list).build();
    return reply;
  }
}
