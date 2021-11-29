/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.*;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "loadablestudy.communication.timelimit = 60")
@SpringJUnitConfig(
    classes = {
      CommunicationService.class,
    })
public class CommunicationServiceTest {
  @Autowired CommunicationService communicationService;

  @MockBean private LoadableStudyServiceShore loadableStudyServiceShore;
  @MockBean VoyageService voyageService;
  @MockBean LoadableQuantityService loadableQuantityService;
  @MockBean LoadableStudyService loadableStudyService;
  @MockBean LoadablePatternService loadablePatternService;
  @MockBean JsonDataService jsonDataService;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadablePlanService loadablePlanService;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @MockBean private LoadicatorService loadicatorService;
  @MockBean private LoadableStudyStagingService loadableStudyStagingService;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;
  @MockBean private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  private static final String SUCCESS = "SUCCESS";

  @Value("${loadablestudy.communication.timelimit}")
  private Long timeLimit;

  @Test
  void testGetDataFromCommInShoreSide() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = MessageTypes.loadableShip;
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doNothing()
        .when(spyService)
        .saveLoadableStudyShore(any(EnvoyReader.EnvoyReaderResultReply.class));
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    spyService.getDataFromCommInShoreSide("1", taskReqParams, shore);
  }

  @Test
  void testGetDataFromCommInShipSide() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = MessageTypes.loadableShore;
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doNothing()
        .when(spyService)
        .saveLoadableStudyShore(any(EnvoyReader.EnvoyReaderResultReply.class));
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);

    spyService.getDataFromCommInShipSide("1", taskReqParams, shore);
  }

  @Test
  void testSaveLoadableStudyShore() throws GenericServiceException {
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(loadableStudyServiceShore.setLoadableStudyShore(anyString(), anyString()))
        .thenReturn(getLoadableStudyEntity());
    doNothing().when(voyageService).checkIfVoyageClosed(anyLong());
    doNothing().when(loadableQuantityService).validateLoadableStudyWithLQ(any(LoadableStudy.class));
    doNothing()
        .when(loadableStudyService)
        .buildLoadableStudy(
            anyLong(),
            any(LoadableStudy.class),
            any(com.cpdss.loadablestudy.domain.LoadableStudy.class),
            any(ModelMapper.class));
    doNothing().when(jsonDataService).saveJsonToDatabase(anyLong(), anyLong(), anyString());
    AlgoResponse algoResponse = new AlgoResponse();
    algoResponse.setProcessId("1");
    when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(algoResponse);
    doNothing()
        .when(loadablePatternService)
        .updateProcessIdForLoadableStudy(anyString(), any(), anyLong(), anyString(), anyBoolean());
    doNothing().when(loadableStudyRepository).updateLoadableStudyStatus(anyLong(), anyLong());
    ReflectionTestUtils.setField(communicationService, "timeLimit", 2l);

    communicationService.saveLoadableStudyShore(resultReply);
  }

  @Test
  void testsaveAlgoPatternFromShore() {
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    doNothing()
        .when(loadablePatternService)
        .saveLoadablePatternDetails(
            anyString(),
            any(com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder.class));

    communicationService.saveAlgoPatternFromShore(resultReply);
  }

  @Test
  void testCheckLoadableStudyStatus() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    List<LoadableStudyCommunicationStatus> communicationStatusList = new ArrayList<>();
    LoadableStudyCommunicationStatus status = new LoadableStudyCommunicationStatus();
    status.setMessageUUID("1");
    status.setReferenceId(1l);
    status.setCommunicationDateTime(LocalDateTime.now());
    communicationStatusList.add(status);
    when(loadableStudyCommunicationStatusRepository
            .findByCommunicationStatusOrderByCommunicationDateTimeASC(anyString()))
        .thenReturn(communicationStatusList);
    EnvoyWriter.WriterReply statusReply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setEventDownloadStatus("2")
            .build();
    when(this.envoyWriterService.statusCheck(any(EnvoyWriter.EnvoyWriterRequest.class)))
        .thenReturn(statusReply);
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudyEntity()));
    doNothing()
        .when(loadableStudyCommunicationStatusRepository)
        .updateLoadableStudyCommunicationStatus(anyString(), anyLong());
    ReflectionTestUtils.setField(communicationService, "timeLimit", 2l);
    communicationService.checkLoadableStudyStatus(taskReqParams);
  }

  @Test
  void testPassResultPayloadToEnvoyWriter() throws GenericServiceException {
    CommunicationService spyService = spy(CommunicationService.class);

    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder
        algoResponseCommunication =
            com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder()
                .setMessageId("1");
    VesselInfo.VesselIdResponse vesselResponse =
        VesselInfo.VesselIdResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .build();
    EnvoyWriter.WriterReply reply = EnvoyWriter.WriterReply.newBuilder().setMessageId("1").build();
    when(this.vesselInfoGrpcService.getVesselInfoByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(vesselResponse);
    when(envoyWriterService.getCommunicationServer(any(EnvoyWriter.EnvoyWriterRequest.class)))
        .thenReturn(reply);

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "envoyWriterService", envoyWriterService);
    var result =
        spyService.passResultPayloadToEnvoyWriter(
            algoResponseCommunication, getLoadableStudyEntity());
    assertEquals("1", result.getMessageId());
  }

  @Test
  void testgetVesselDetailsForEnvoy() throws GenericServiceException {
    CommunicationService spyService = spy(CommunicationService.class);
    VesselInfo.VesselIdResponse vesselResponse =
        VesselInfo.VesselIdResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .build();
    when(this.vesselInfoGrpcService.getVesselInfoByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(vesselResponse);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    var result = spyService.getVesselDetailsForEnvoy(1l);
    assertEquals("1", result.getName());
  }

  @Test
  void testPassRequestPayloadToEnvoyWriter() throws GenericServiceException {
    CommunicationService spyService = spy(CommunicationService.class);
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build();
    EnvoyWriter.WriterReply reply = EnvoyWriter.WriterReply.newBuilder().setMessageId("1").build();
    VesselInfo.VesselIdResponse vesselResponse =
        VesselInfo.VesselIdResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .build();
    when(this.vesselInfoGrpcService.getVesselInfoByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(vesselResponse);
    when(envoyWriterService.getCommunicationServer(any(EnvoyWriter.EnvoyWriterRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "envoyWriterService", envoyWriterService);

    var result = spyService.passRequestPayloadToEnvoyWriter("1", 1l, "1");
    assertEquals("1", result.getMessageId());
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLoadableStudyEntity() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    loadableStudy.setId(1l);
    loadableStudy.setVesselXId(1l);
    return loadableStudy;
  }
}
