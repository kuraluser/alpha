/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInstructionServiceGrpc;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.InstructionResponse;
import com.cpdss.gateway.domain.PortInstruction;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class InstructionService {
  @GrpcClient("loadableStudyService")
  PortInstructionServiceGrpc.PortInstructionServiceBlockingStub portInstructionServiceBlockingStub;

  public InstructionResponse getInstructions(String correlationId) throws GenericServiceException {
    InstructionResponse response = new InstructionResponse();
    List<PortInstruction> instructionsList = new ArrayList<>();
    InstructionReply instructions =
        portInstructionServiceBlockingStub.getInstructions(EmptyRequest.newBuilder().build());
    List<InstructionDetail> instructionDetailsList = instructions.getInstructionDetailsList();
    instructionDetailsList.forEach(
        instruction -> {
          PortInstruction portInstruction = new PortInstruction();
          portInstruction.setId(instruction.getId());
          portInstruction.setInstruction(instruction.getInstruction());
          instructionsList.add(portInstruction);
        });
    response.setInstructions(instructionsList);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }
}
