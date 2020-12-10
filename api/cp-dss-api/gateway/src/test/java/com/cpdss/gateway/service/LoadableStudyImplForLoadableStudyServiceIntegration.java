/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.CommingleCargo;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoReply;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class LoadableStudyImplForLoadableStudyServiceIntegration
    extends LoadableStudyServiceImplBase {

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  static int testIteration = 0;

  @Override
  public void getCargoNominationById(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    cargoNominationReply.setResponseStatus(responseStatus);
    CargoNominationDetail.Builder detailBuilder = CargoNominationDetail.newBuilder();
    detailBuilder.setId(1);
    detailBuilder.setColor("test_color");
    detailBuilder.setCargoId(2);
    cargoNominationReply.addCargoNominations(detailBuilder);
    responseObserver.onNext(cargoNominationReply.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getCommingleCargo(
      CommingleCargoRequest request, StreamObserver<CommingleCargoReply> responseObserver) {
    CommingleCargoReply.Builder commingleCargoReply = CommingleCargoReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    commingleCargoReply.setResponseStatus(responseStatus);
    CommingleCargo.Builder commingleCargo = CommingleCargo.newBuilder();
    commingleCargo.setPurposeId(1L);
    commingleCargo.setSlopOnly(true);
    List<Long> tanksList = new ArrayList<>();
    tanksList.add(1L);
    commingleCargo.addAllPreferredTanks(tanksList);
    commingleCargo.setId(1L);
    commingleCargo.setCargo1Id(1L);
    commingleCargo.setCargo1Pct("10");
    commingleCargo.setCargo2Id(1L);
    commingleCargo.setCargo2Pct("10");
    commingleCargo.setQuantity("100");
    commingleCargoReply.addCommingleCargo(commingleCargo);
    responseObserver.onNext(commingleCargoReply.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getPurposeOfCommingle(
      PurposeOfCommingleRequest request, StreamObserver<PurposeOfCommingleReply> responseObserver) {
    PurposeOfCommingleReply.Builder purposeOfCommingleReply = PurposeOfCommingleReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    purposeOfCommingleReply.setResponseStatus(responseStatus);
    responseObserver.onNext(purposeOfCommingleReply.build());
    responseObserver.onCompleted();
  }

  @Override
  public void saveCommingleCargo(
      CommingleCargoRequest request, StreamObserver<CommingleCargoReply> responseObserver) {
    CommingleCargoReply.Builder commingleCargoReply = CommingleCargoReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    commingleCargoReply.setResponseStatus(responseStatus);
    responseObserver.onNext(commingleCargoReply.build());
    responseObserver.onCompleted();
  }
}
