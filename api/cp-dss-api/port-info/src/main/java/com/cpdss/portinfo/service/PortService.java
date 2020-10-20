/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceImplBase;
import com.cpdss.portinfo.entity.Port;
import com.cpdss.portinfo.repository.PortRepository;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

/** Service with operations related to port information */
@Log4j2
@GrpcService
public class PortService extends PortInfoServiceImplBase {

  @Autowired private PortRepository portRepository;
  
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  /** retrieves port info from port master */
  @Override
  public void getPortInfo(PortRequest request, StreamObserver<PortReply> responseObserver) {
	  PortReply.Builder portReply = PortReply.newBuilder();
    try {
      Iterable<Port> portList = portRepository.findAll();
      portList.forEach(
          port -> {
            PortDetail.Builder portDetail = PortDetail.newBuilder();
            if (port.getId()!=null) {
            	portDetail.setId(port.getId());
            }
            if (!StringUtils.isEmpty(port.getName())) {
            	portDetail.setName(port.getName());
            }
            portReply.addPorts(portDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      portReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPortInfo method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      portReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(portReply.build());
      responseObserver.onCompleted();
    }
  }
}
