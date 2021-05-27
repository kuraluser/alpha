/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PortInfoService {

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  public PortInfo.BerthInfoResponse getBerthInfoByPortId(Long id) {
    PortInfo.BerthInfoResponse response =
        this.portInfoServiceBlockingStub.getBerthDetailsByPortId(
            PortInfo.PortIdRequest.newBuilder().setPortId(id).build());
    if (response.getResponseStatus().getStatus().equalsIgnoreCase("SUCCESS")) {
      return response;
    }
    return null;
  }
}
