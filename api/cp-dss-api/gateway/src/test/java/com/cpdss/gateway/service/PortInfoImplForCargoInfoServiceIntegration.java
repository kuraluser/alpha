/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply;
import com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PortInfoImplForCargoInfoServiceIntegration extends PortInfoServiceImplBase {

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String INVALID_CARGOID = "INVALID_CARGOID";
  static int testIteration = 0;

  @Override
  public void getPortInfo(PortRequest request, StreamObserver<PortReply> responseObserver) {
    PortReply.Builder portReply = PortReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    if (testIteration == 0) {
      responseStatus.setStatus(SUCCESS);
    } else {
      responseStatus.setStatus(FAILED);
    }
    portReply.setResponseStatus(responseStatus);
    PortDetail.Builder portDetail = PortDetail.newBuilder();
    portDetail.setId(2L);
    portDetail.setCode("testCode");
    portDetail.setMaxAirDraft("2.03");
    portDetail.setMaxDraft("3.45");
    portDetail.setWaterDensity("5.5");
    portReply.addPorts(portDetail);
    responseObserver.onNext(portReply.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getPortInfoByCargoId(
      GetPortInfoByCargoIdRequest request,
      StreamObserver<GetPortInfoByCargoIdReply> responseObserver) {
    GetPortInfoByCargoIdReply.Builder replyBuilder = GetPortInfoByCargoIdReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    if (request.getCargoId() == 0) {
      responseStatus.setStatus(SUCCESS);
    } else {
      responseStatus.setStatus(FAILED);
    }
    replyBuilder.setResponseStatus(responseStatus);
    PortDetail.Builder portDetail = PortDetail.newBuilder();
    portDetail.setId(1L);
    portDetail.setName("testPortName");
    replyBuilder.addPorts(portDetail);
    responseObserver.onNext(replyBuilder.build());
    responseObserver.onCompleted();
  }
}
