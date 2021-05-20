/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.grpc.service;

import com.cpdss.common.generated.EnvoyReaderServiceGrpc.EnvoyReaderServiceImplBase;
import com.cpdss.envoyreader.service.EnvoyReaderService;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/** @Author jerin.g */
@GrpcService
public class EnvoyReaderGrpcService extends EnvoyReaderServiceImplBase {
  @Autowired private EnvoyReaderService envoyReaderService;
}
