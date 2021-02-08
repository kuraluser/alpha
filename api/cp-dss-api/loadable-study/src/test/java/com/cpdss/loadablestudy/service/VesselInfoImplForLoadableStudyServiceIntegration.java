/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class VesselInfoImplForLoadableStudyServiceIntegration extends VesselInfoServiceImplBase {

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  static int testIteration = 0;

  @Override
  public void getVesselCargoTanks(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder vesselReply = VesselReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    vesselReply.setResponseStatus(responseStatus);
    VesselTankDetail.Builder detailBuilder = VesselTankDetail.newBuilder();
    detailBuilder.setTankId(2);
    detailBuilder.setTankName("testName");
    detailBuilder.setShortName("testShortName");
    vesselReply.addVesselTanks(detailBuilder);
    responseObserver.onNext(vesselReply.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getVesselDetailForSynopticalTable(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder vesselReply = VesselReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    vesselReply.setResponseStatus(responseStatus);
    VesselTankDetail.Builder detailBuilder = VesselTankDetail.newBuilder();
    detailBuilder.setTankId(2);
    detailBuilder.setTankName("testName");
    detailBuilder.setShortName("testShortName");
    vesselReply.addVesselTanks(detailBuilder);
    responseObserver.onNext(vesselReply.build());
    responseObserver.onCompleted();
  }
}
