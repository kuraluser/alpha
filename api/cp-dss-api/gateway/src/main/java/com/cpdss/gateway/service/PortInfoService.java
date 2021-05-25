/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PortInfoService {

  @Autowired PortInfoServiceGrpc.PortInfoServiceBlockingStub infoServiceBlockingStub;

  public PortInfo.BerthInfoResponse getBerthInfoByPortId(Long id) {
    PortInfo.BerthInfoResponse response =
        this.infoServiceBlockingStub.getBerthDetailsByPortId(
            PortInfo.PortIdRequest.newBuilder().setPortId(id).build());
    if (response.getResponseStatus().getStatus().equalsIgnoreCase("SUCCESS")) {
      return response;
    }
    return null;
  }
}
