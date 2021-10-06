/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Log4j2
@Service
public class CommunicationService {

  @Autowired private RestTemplate restTemplate;


  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @Value("${algo.loadablestudy.api.url}")
  private String loadableStudyUrl;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @Value("${loadablestudy.communication.timelimit}")
  private Long timeLimit;

  public void getDataFromCommInShoreSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> shore)
      throws GenericServiceException {
    log.info("inside getDataFromCommInShoreSide ");
      }

    public void getDataFromCommInShipSide(
      Map<String, String> taskReqParams, EnumSet<MessageTypes> ship)
      throws GenericServiceException {
     }

}
