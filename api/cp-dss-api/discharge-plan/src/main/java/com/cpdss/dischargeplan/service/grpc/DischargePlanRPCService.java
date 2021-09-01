/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.FAILED;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest;
import com.cpdss.common.generated.discharge_plan.PortData;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@GrpcService
public class DischargePlanRPCService extends DischargePlanServiceGrpc.DischargePlanServiceImplBase {

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Override
  public void dischargePlanSynchronization(
      DischargeStudyDataTransferRequest request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    try {
      log.info("in Dischargeinfo micro servie-plan synchronisaton");
      List<PortData> portDataList = request.getPortDataList();
      List<DischargeInformation> infos = new ArrayList<>();
      portDataList.stream()
          .forEach(
              port -> {
                DischargeInformation dischargeInformation = new DischargeInformation();
                dischargeInformation.setVoyageXid(request.getVoyageId());
                dischargeInformation.setVesselXid(request.getVesselId());
                dischargeInformation.setDischargingPatternXid(request.getDischargePatternId());
                dischargeInformation.setPortRotationXid(port.getPortRotationId());
                dischargeInformation.setSynopticTableXid(port.getSynopticTableId());
                infos.add(dischargeInformation);
              });
      dischargeInformationRepository.saveAll(infos);
      response.setStatus(SUCCESS);
      response.setMessage(SUCCESS);
    } catch (Exception e) {

      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      e.printStackTrace();
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }
}
