/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PortInfoImplForLoadableStudyServiceIntegrationTest extends PortInfoServiceImplBase {

  private static final String SUCCESS = "SUCCESS";

  @Override
  public void getPortInfoByPortIds(
      GetPortInfoByPortIdsRequest request, StreamObserver<PortReply> responseObserver) {
    PortReply.Builder portReply = PortReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
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
}
