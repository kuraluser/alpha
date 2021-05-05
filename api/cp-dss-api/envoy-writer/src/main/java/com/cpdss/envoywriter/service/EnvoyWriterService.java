/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.service;

import com.cpdss.common.generated.EnvoyWriter;
import com.cpdss.common.generated.EnvoyWriterServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

@Log4j2
@GrpcService
public class EnvoyWriterService extends EnvoyWriterServiceGrpc.EnvoyWriterServiceImplBase {

  @Override
  public void getLoadableStudy(
      EnvoyWriter.LoadableStudyJson request,
      StreamObserver<EnvoyWriter.WriterReply> responseObserver) {
    log.info("Inside getLoadableStudy service");
    String imoNumber = request.getImoNumber();
  }
}
