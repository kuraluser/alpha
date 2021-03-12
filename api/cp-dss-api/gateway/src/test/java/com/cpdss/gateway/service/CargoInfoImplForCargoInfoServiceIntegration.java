/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.CargoInfo.CargoDetail;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceImplBase;
import com.cpdss.common.generated.Common.ResponseStatus;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CargoInfoImplForCargoInfoServiceIntegration extends CargoInfoServiceImplBase {

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  static int testIteration = 0;

  @Override
  public void getCargoInfo(CargoRequest request, StreamObserver<CargoReply> responseObserver) {
    CargoReply.Builder cargoReply = CargoReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    if (testIteration == 0) {
      responseStatus.setStatus(SUCCESS);
    } else {
      responseStatus.setStatus(FAILED);
    }
    cargoReply.setResponseStatus(responseStatus);
    CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
    cargoDetail.setId(2L);
    cargoDetail.setApi("testApi");
    cargoDetail.setAbbreviation("testAbbr");
    cargoReply.addCargos(cargoDetail);
    responseObserver.onNext(cargoReply.build());
    responseObserver.onCompleted();
  }
}
