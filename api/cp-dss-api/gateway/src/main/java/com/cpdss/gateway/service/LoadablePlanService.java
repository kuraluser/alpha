/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.LoadablePlanServiceGrpc.LoadablePlanServiceBlockingStub;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
@Service
@Log4j2
public class LoadablePlanService {

  @GrpcClient("loadablePlanService")
  private LoadablePlanServiceBlockingStub loadablePlanServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
}
