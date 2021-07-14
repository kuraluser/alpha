/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.FAILED;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInstructionServiceGrpc.PortInstructionServiceImplBase;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply;
import com.cpdss.loadablestudy.entity.DischargeStudyPortInstruction;
import com.cpdss.loadablestudy.entity.PortInstruction;
import com.cpdss.loadablestudy.repository.DischargeStudyPortInstructionRepository;
import com.cpdss.loadablestudy.repository.PortInstructionRepository;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * to handle port wise instructions
 *
 * @author arun.j
 */
@Slf4j
@GrpcService
public class PortInstructionService extends PortInstructionServiceImplBase {
  @Autowired DischargeStudyPortInstructionRepository dischargeStudyPortInstructionRepository;
  @Autowired PortInstructionRepository portInstructionRepository;

  public Map<Long, List<DischargeStudyPortInstruction>> getPortWiseInstructions(
      long dischargeStudyId, List<Long> portIds) {
    List<DischargeStudyPortInstruction> portInstructions =
        dischargeStudyPortInstructionRepository.findByDischargeStudyIdAndPortIdIn(
            dischargeStudyId, portIds);

    return portInstructions.stream()
        .collect(Collectors.groupingBy(DischargeStudyPortInstruction::getPortId));
  }

  @Override
  public void getInstructions(
      EmptyRequest request, StreamObserver<InstructionReply> responseObserver) {
    InstructionReply.Builder builder = InstructionReply.newBuilder();
    try {
      List<PortInstruction> instructions = portInstructionRepository.findByIsActive(true);
      instructions.forEach(
          ins -> {
            InstructionDetail.Builder detail = InstructionDetail.newBuilder();
            detail.setId(ins.getId());
            detail.setInstruction(ins.getPortInstruction());
            builder.addInstructionDetails(detail.build());
          });
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
