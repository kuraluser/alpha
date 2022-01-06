/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.common.rest.CommonErrorCodes.E_GEN_INTERNAL_ERR;
import static com.cpdss.common.utils.MessageTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.*;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;
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
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);

    doNothing().when(loadableStudyStagingService).save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    spyService.getDataFromCommInShoreSide("1", taskReqParams, shore);
    verify(loadableStudyStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommInShoreSideElse() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doNothing().when(loadableStudyStagingService).save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    spyService.getDataFromCommInShoreSide("1", taskReqParams, shore);
    verify(envoyReaderGrpcService, atLeast(1))
        .getResultFromCommServer(any(EnvoyReader.EnvoyReaderResultRequest.class));
  }

  @Test
  void testGetDataFromCommInShoreSideWithGenericException2() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(loadableStudyStagingService)
        .save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    spyService.getDataFromCommInShoreSide("1", taskReqParams, shore);
    verify(loadableStudyStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommInShoreSideWithResourceAccessException() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doThrow(new ResourceAccessException("1")).when(loadableStudyStagingService).save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    spyService.getDataFromCommInShoreSide("1", taskReqParams, shore);
    verify(loadableStudyStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommInShoreSideWithException() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doThrow(new RuntimeException("1")).when(loadableStudyStagingService).save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    spyService.getDataFromCommInShoreSide("1", taskReqParams, shore);
    verify(loadableStudyStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommInShoreSideWithGenericException() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = MessageTypes.loadableShip;
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("500").build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> spyService.getDataFromCommInShoreSide("1", taskReqParams, shore));

    assertAll(
        () -> assertEquals(E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetDataFromCommInShipSide() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = EnumSet.of(ALGORESULT, PATTERNDETAIL);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doNothing().when(loadableStudyStagingService).save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);

    spyService.getDataFromCommInShipSide("1", taskReqParams, shore);
    verify(loadableStudyStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommInShipSideElse() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = EnumSet.of(ALGORESULT, PATTERNDETAIL);
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setPatternResultJson("")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    doNothing().when(loadableStudyStagingService).save(anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);

    spyService.getDataFromCommInShipSide("1", taskReqParams, shore);
    verify(envoyReaderGrpcService, atLeast(1))
        .getResultFromCommServer(any(EnvoyReader.EnvoyReaderResultRequest.class));
  }

  @Test
  void testGetDataFromCommInShipSideWithGenericException() throws GenericServiceException {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    CommunicationService spyService = spy(CommunicationService.class);
    EnumSet<MessageTypes> shore = MessageTypes.loadableShore;
    EnvoyReader.EnvoyReaderResultReply resultReply =
        EnvoyReader.EnvoyReaderResultReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("500").build())
            .setPatternResultJson("1")
            .build();
    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(resultReply);
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> spyService.getDataFromCommInShipSide("1", taskReqParams, shore));

    assertAll(
        () -> assertEquals(E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testSaveAlgoPatternFromShore() {
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
    verify(loadablePatternService)
        .saveLoadablePatternDetails(
            anyString(),
            any(com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder.class));
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
    status.setCreatedDateTime(LocalDateTime.now());
    status.setCommunicationDateTime(LocalDateTime.now());
    communicationStatusList.add(status);
    when(loadableStudyCommunicationStatusRepository
            .findByCommunicationStatusOrderByCommunicationDateTimeASC(anyString()))
        .thenReturn(communicationStatusList);
    when(loadableStudyCommunicationStatusRepository
            .findByCommunicationStatusAndMessageTypeOrderByCommunicationDateTimeAsc(
                anyString(), anyString()))
        .thenReturn(communicationStatusList);
    EnvoyWriter.WriterReply statusReply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setEventDownloadStatus("2")
            .setMessageId("1")
            .setStatusCode("200")
            .build();
    when(this.envoyWriterService.statusCheck(any(EnvoyWriter.EnvoyWriterRequest.class)))
        .thenReturn(statusReply);
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudyEntity()));
    doNothing()
        .when(loadableStudyCommunicationStatusRepository)
        .updateLoadableStudyCommunicationStatus(anyString(), anyLong());
    JsonData jsonData = new JsonData();
    jsonData.setJsonData("1");
    when(jsonDataService.getJsonData(anyLong(), anyLong())).thenReturn(jsonData);
    AlgoResponse response = new AlgoResponse();
    response.setProcessId("1");
    when(restTemplate.postForObject(anyString(), anyString(), any(Class.class)))
        .thenReturn(response);
    doNothing()
        .when(loadablePatternService)
        .updateProcessIdForLoadableStudy(
            anyString(), any(LoadableStudy.class), anyLong(), anyString(), anyBoolean());
    doNothing().when(loadableStudyRepository).updateLoadableStudyStatus(anyLong(), anyLong());

    ReflectionTestUtils.setField(communicationService, "timeLimit", 2l);
    ReflectionTestUtils.setField(communicationService, "env", "ship");
    ReflectionTestUtils.setField(communicationService, "loadableStudyUrl", "url");
    ReflectionTestUtils.setField(communicationService, "envoyWriterService", envoyWriterService);

    communicationService.checkLoadableStudyStatus(taskReqParams);
    verify(loadableStudyRepository).findByIdAndIsActive(anyLong(), anyBoolean());
  }

  @Test
  void testCheckLoadableStudyStatusWithGenericException() {
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    List<LoadableStudyCommunicationStatus> communicationStatusList = new ArrayList<>();
    LoadableStudyCommunicationStatus status = new LoadableStudyCommunicationStatus();
    status.setMessageUUID("1");
    status.setReferenceId(1l);
    status.setCreatedDateTime(LocalDateTime.now());
    status.setCommunicationDateTime(LocalDateTime.now());
    communicationStatusList.add(status);
    when(loadableStudyCommunicationStatusRepository
            .findByCommunicationStatusAndMessageTypeOrderByCommunicationDateTimeAsc(
                anyString(), anyString()))
        .thenReturn(communicationStatusList);
    EnvoyWriter.WriterReply statusReply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().build())
            .setEventDownloadStatus("2")
            .setMessageId("1")
            .setStatusCode("200")
            .build();
    when(this.envoyWriterService.statusCheck(any(EnvoyWriter.EnvoyWriterRequest.class)))
        .thenReturn(statusReply);

    ReflectionTestUtils.setField(communicationService, "timeLimit", 2l);
    ReflectionTestUtils.setField(communicationService, "env", "ship");
    ReflectionTestUtils.setField(communicationService, "loadableStudyUrl", "url");
    ReflectionTestUtils.setField(communicationService, "envoyWriterService", envoyWriterService);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> communicationService.checkLoadableStudyStatus(taskReqParams));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
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
  void testGetVesselDetailsForEnvoy() throws GenericServiceException {
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
  void testGetVesselDetailsForEnvoyWithGenericException() {
    CommunicationService spyService = spy(CommunicationService.class);
    VesselInfo.VesselIdResponse vesselResponse =
        VesselInfo.VesselIdResponse.newBuilder()
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .build();
    when(this.vesselInfoGrpcService.getVesselInfoByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(vesselResponse);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    final GenericServiceException ex =
        assertThrows(GenericServiceException.class, () -> spyService.getVesselDetailsForEnvoy(1l));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
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
    loadableStudy.setVoyage(voyage);
    loadableStudy.setId(1l);
    loadableStudy.setVesselXId(1l);
    return loadableStudy;
  }
}
