/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.grpc.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.EnvoyReader;
import com.cpdss.common.generated.EnvoyReaderServiceGrpc.EnvoyReaderServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.envoyreader.service.EnvoyReaderService;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/** @Author jerin.g */
@Log4j2
@GrpcService
public class EnvoyReaderGrpcService extends EnvoyReaderServiceImplBase {
  @Autowired private EnvoyReaderService envoyReaderService;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Override
  public void getResultFromCommServer(
          EnvoyReader.EnvoyReaderResultRequest request,
          StreamObserver<EnvoyReader.EnvoyReaderResultReply> responseObserver) {
    log.info("Inside getResultFromCommServer in Envoy Reader micro service");
    EnvoyReader.EnvoyReaderResultReply.Builder builder =
            EnvoyReader.EnvoyReaderResultReply.newBuilder();
    try {

      EnvoyReader.EnvoyReaderResultReply result =
              envoyReaderService.getDataFromCommunicationServer(request, builder);

      builder.setPatternResultJson(result.getPatternResultJson());
      builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    } catch (Exception e) {
      log.error("Exception when getResultFromCommServer in Envoy Reader micro service", e);
      builder.setResponseStatus(
              Common.ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Exception when getResultFromCommServer in Envoy Reader micro service")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
