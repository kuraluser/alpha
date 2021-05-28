/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.grpc.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson;
import com.cpdss.common.generated.EnvoyWriter.WriterReply;
import com.cpdss.common.generated.EnvoyWriterServiceGrpc.EnvoyWriterServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.envoywriter.service.EnvoyWriterService;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/** @Author jerin.g */
@Log4j2
@GrpcService
public class EnvoyWriterGrpcService extends EnvoyWriterServiceImplBase {

  @Autowired private EnvoyWriterService envoyWriterService;

  private static final String FAILED = "FAILED";

  @Override
  public void getLoadableStudy(
      LoadableStudyJson request, StreamObserver<WriterReply> responseObserver) {
    log.info("Inside getLoadableStudy in Envoy Writer micro service");
    WriterReply.Builder builder = WriterReply.newBuilder();
    try {

      envoyWriterService.passDataToCommunicationServer(request, builder);

    } catch (IOException e) {
      log.error("IOException in when getLoadableStudy in Envoy Writer micro service: " + e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("IOException in Envoy Writer micro service")
              .setStatus(FAILED)
              .build());

    } catch (Exception e) {
      log.error("Exception when getLoadableStudy in Envoy Writer micro service: ", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in Envoy Writer micro service")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
