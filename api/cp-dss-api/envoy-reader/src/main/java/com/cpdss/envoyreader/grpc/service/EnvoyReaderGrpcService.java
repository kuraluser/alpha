/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.grpc.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.EnvoyReader.ReaderReply;
import com.cpdss.common.generated.EnvoyReader.ResultJson;
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
public void getResults(ResultJson request, StreamObserver<ReaderReply> responseObserver) {
	 ReaderReply.Builder builder = ReaderReply.newBuilder();
	 try {
		
		 envoyReaderService.getResult(request, builder);

	      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());

	    } catch (Exception e) {
	      log.error("Exception when getLoadableStudy in Envoy Writer micro service", e);
	      builder.setResponseStatus(
	          ResponseStatus.newBuilder()
	              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
	              .setMessage("Exception when getLoadableStudy in Envoy Writer micro service")
	              .setStatus(FAILED)
	              .build());
	    } finally {
	      responseObserver.onNext(builder.build());
	      responseObserver.onCompleted();
	    }
  
}
  
}
