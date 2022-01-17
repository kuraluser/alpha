/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.common.utils.MessageTypes.*;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.FAILED;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.repository.communication.LoadingPlanDataTransferInBoundRepository;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;

@TestPropertySource(properties = "loadingplan.communication.timelimit = 1")
@SpringJUnitConfig(classes = {LoadingPlanCommunicationService.class})
public class LoadingPlanCommunicationServiceTest {

  @Autowired LoadingPlanCommunicationService loadingPlanCommunicationService;

  @MockBean private LoadingPlanStagingService loadingPlanStagingService;

  @MockBean private ReasonForDelayRepository reasonForDelayRepository;

  @MockBean
  private LoadingInformationCommunicationRepository loadingInformationCommunicationRepository;

  @MockBean private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @MockBean private LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @MockBean private LoadingDelayRepository loadingDelayRepository;
  @MockBean private LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @MockBean private LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean private UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @MockBean
  private PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @MockBean private PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @MockBean private LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @MockBean private LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @MockBean private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;

  @MockBean private PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;

  @MockBean
  private PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @MockBean private PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;

  @MockBean
  private PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @MockBean private LoadingSequenceRepository loadingSequenceRepository;
  @MockBean private LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;

  @MockBean
  private LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @MockBean
  private LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;

  @MockBean
  private PortLoadingPlanCommingleTempDetailsRepository
      portLoadingPlanCommingleTempDetailsRepository;

  @MockBean private LoadingDelayReasonRepository loadingDelayReasonRepository;

  @MockBean
  private PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @MockBean private BillOfLandingRepository billOfLandingRepository;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @MockBean private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @MockBean private StageOffsetRepository stageOffsetRepository;
  @MockBean private StageDurationRepository stageDurationRepository;
  @MockBean private LoadingInformationStatusRepository loadingInfoStatusRepository;
  @MockBean private PyUserRepository pyUserRepository;
  @MockBean private LoadingInformationAlgoStatusRepository loadingInformationAlgoStatusRepository;
  @MockBean private BallastOperationRepository ballastOperationRepository;
  @MockBean private EductionOperationRepository eductionOperationRepository;
  @MockBean private CargoLoadingRateRepository cargoLoadingRateRepository;
  @MockBean private PortTideDetailsRepository portTideDetailsRepository;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private LoadingInstructionRepository loadingInstructionRepository;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @MockBean private LoadingPlanCommingleDetailsRepository loadingPlanCommingleDetailsRepository;

  @MockBean
  private LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;

  @MockBean private LoadingRuleRepository loadingRuleRepository;
  @MockBean private LoadingRuleInputRepository loadingRuleInputRepository;
  @MockBean LoadingPlanDataTransferInBoundRepository dataTransferInBoundRepository;

  @Value("${cpdss.build.env}")
  private String env;

  @Value("${loadingplan.communication.timelimit}")
  private Long timeLimit;

  @Test
  void testGetDataFromCommunication() throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnumSet<MessageTypes> messageTypesEnum = MessageTypes.loadableShore;

    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(
            EnvoyReader.EnvoyReaderResultReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .setPatternResultJson("1")
                .build());
    doNothing().when(loadingPlanStagingService).save(anyString());
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);

    spyService.getDataFromCommunication(taskReqParams, messageTypesEnum);
    verify(loadingPlanStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommunicationWithGenericException() throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnumSet<MessageTypes> messageTypesEnum = MessageTypes.loadableShore;

    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(
            EnvoyReader.EnvoyReaderResultReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("500").build())
                .setPatternResultJson("1")
                .build());
    doNothing().when(loadingPlanStagingService).save(anyString());
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> spyService.getDataFromCommunication(taskReqParams, messageTypesEnum));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetDataFromCommunicationSaveWithGenericException() throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnumSet<MessageTypes> messageTypesEnum = MessageTypes.loadableShore;

    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(
            EnvoyReader.EnvoyReaderResultReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .setPatternResultJson("1")
                .build());
    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(loadingPlanStagingService)
        .save(anyString());
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);

    spyService.getDataFromCommunication(taskReqParams, messageTypesEnum);
    verify(loadingPlanStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommunicationSaveWithException() throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, DISCHARGESTUDY);
    EnumSet<MessageTypes> messageTypesEnum = MessageTypes.loadableShore;

    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(
            EnvoyReader.EnvoyReaderResultReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .setPatternResultJson("1")
                .build());
    doThrow(new RuntimeException("1")).when(loadingPlanStagingService).save(anyString());
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);

    spyService.getDataFromCommunication(taskReqParams, messageTypesEnum);
    verify(loadingPlanStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testGetDataFromCommunicationSaveWithResourceAccessException()
      throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    Map<String, String> taskReqParams = new HashMap<>();
    taskReqParams.put("ClientId", "1");
    taskReqParams.put("ShipId", "1");
    EnumSet<MessageTypes> messageTypesEnum = MessageTypes.loadableShore;

    when(envoyReaderGrpcService.getResultFromCommServer(
            any(EnvoyReader.EnvoyReaderResultRequest.class)))
        .thenReturn(
            EnvoyReader.EnvoyReaderResultReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .setPatternResultJson("1")
                .build());
    doThrow(new ResourceAccessException("1")).when(loadingPlanStagingService).save(anyString());
    ReflectionTestUtils.setField(spyService, "envoyReaderGrpcService", envoyReaderGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);

    spyService.getDataFromCommunication(taskReqParams, messageTypesEnum);
    verify(loadingPlanStagingService, atLeast(1)).save(anyString());
  }

  @Test
  void testPassRequestPayloadToEnvoyWriter() {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    String requestJson = "1";
    Long vesselId = 1L;
    String messageType = "1";
    Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
        .thenReturn(getVIR());
    Mockito.when(this.envoyWriterService.getCommunicationServer(Mockito.any())).thenReturn(getWR());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "envoyWriterService", this.envoyWriterService);
    ReflectionTestUtils.setField(spyService, "timeLimit", 1l);
    try {
      var response = spyService.passRequestPayloadToEnvoyWriter(requestJson, vesselId, messageType);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testPassRequestPayloadToEnvoyWriterException() {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    String requestJson = "1";
    Long vesselId = 1L;
    String messageType = "1";
    Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
        .thenReturn(getVIRNS());
    Mockito.when(this.envoyWriterService.getCommunicationServer(Mockito.any())).thenReturn(getWR());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "envoyWriterService", this.envoyWriterService);
    ReflectionTestUtils.setField(spyService, "timeLimit", 1l);
    try {
      var response = spyService.passRequestPayloadToEnvoyWriter(requestJson, vesselId, messageType);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetLoadingPlanStagingData() throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    String status = "retry";
    String env = "1";
    String taskName = "1";

    Mockito.when(loadingPlanStagingService.getAllWithStatus(Mockito.any())).thenReturn(getLDTS());
    Mockito.doNothing()
        .when(spyService)
        .getStagingData(Mockito.anyList(), Mockito.anyString(), Mockito.anyString());

    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);
    spyService.getLoadingPlanStagingData(status, env, taskName);
    verify(spyService).getStagingData(Mockito.anyList(), Mockito.anyString(), Mockito.anyString());
  }

  @ParameterizedTest
  @ValueSource(strings = {"retry", "in_progress"})
  void testGetUllageUpdateStagingData(String str) throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    String status = str;
    String env = "1";
    String taskName = "1";

    when(loadingPlanStagingService.getAllWithStatusAndTime(anyString(), any(LocalDateTime.class)))
        .thenReturn(getLDTS());
    Mockito.when(loadingPlanStagingService.getAllWithStatus(Mockito.any())).thenReturn(getLDTS());
    Mockito.doNothing()
        .when(spyService)
        .getStagingData(Mockito.anyList(), Mockito.anyString(), Mockito.anyString());

    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);
    spyService.getUllageUpdateStagingData(status, env, taskName);
    verify(spyService).getStagingData(Mockito.anyList(), Mockito.anyString(), Mockito.anyString());
  }

  @Test
  void testGetVesselDetailsForEnvoy() throws GenericServiceException {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    when(this.vesselInfoGrpcService.getVesselInfoByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(
            VesselInfo.VesselIdResponse.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .setVesselDetail(VesselInfo.VesselDetail.newBuilder().setId(1l).build())
                .build());

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselDetailsForEnvoy(1l);
    assertEquals(1l, result.getId());
  }

  @Test
  void testGetVesselDetailsForEnvoyWithGenericException() {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    when(this.vesselInfoGrpcService.getVesselInfoByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(
            VesselInfo.VesselIdResponse.newBuilder()
                .setVesselDetail(VesselInfo.VesselDetail.newBuilder().setId(1l).build())
                .build());

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    final GenericServiceException ex =
        assertThrows(GenericServiceException.class, () -> spyService.getVesselDetailsForEnvoy(1l));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
  }

  @ParameterizedTest
  @EnumSource(
      value = ProcessIdentifiers.class,
      names = {"loading_information", "pyuser", "voyage", "loading_information_algo_status"},
      mode = EnumSource.Mode.EXCLUDE)
  void testGetStagingData(ProcessIdentifiers id) throws GenericServiceException {
    List<DataTransferStage> list = new ArrayList<>();
    DataTransferStage dataTransferStage = new DataTransferStage();
    String data =
        "[{\"stages_min_amount_xid\":1,\"stages_duration_xid\":1,\"loading_status_xid\":1,"
            + "\"arrival_status_xid\":1,\"departure_status_xid\":1,\"loading_xid\":1,"
            + "\"loading_delay_xid\":1,\"reason_xid\":1,\"loading_information_xid\":1,"
            + "\"loading_sequences_xid\":1,\"loading_plan_portwise_details_xid\":1,\"error_heading_xid\":1,"
            + "\"loading_information_status_xid\":1}]";
    dataTransferStage.setData(data);
    dataTransferStage.setId(1L);
    dataTransferStage.setProcessGroupId("LoadingPlan_Save");
    dataTransferStage.setProcessIdentifier(id.toString());
    dataTransferStage.setProcessId("1");
    list.add(dataTransferStage);

    JsonArray jsonArray = JsonParser.parseString(data).getAsJsonArray();

    Mockito.doNothing()
        .when(loadingPlanStagingService)
        .updateStatusForProcessId(Mockito.anyString(), anyString());
    Mockito.when(loadingPlanStagingService.getAttributeMapping(Mockito.any())).thenReturn(getHSS());

    Mockito.when(
            loadingPlanStagingService.getAsEntityJson(Mockito.any(), Mockito.any(JsonArray.class)))
        .thenReturn(jsonArray);
    when(stageOffsetRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(new StageOffset()));
    when(stageDurationRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(new StageDuration()));
    when(reasonForDelayRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(new ReasonForDelay()));
    when(loadingInfoStatusRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new LoadingInformationStatus()));
    when(cargoToppingOffSequenceRepository.findById(anyLong()))
        .thenReturn(Optional.of(new CargoToppingOffSequence()));
    when(loadingBerthDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingBerthDetail()));
    when(loadingDelayRepository.findById(anyLong())).thenReturn(Optional.of(new LoadingDelay()));
    when(loadingDelayReasonRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingDelayReason()));
    when(loadingMachineryInUseRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingMachineryInUse()));
    when(loadingSequenceRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingSequence()));
    when(loadingPlanPortWiseDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingPlanPortWiseDetails()));
    when(portLoadingPlanStabilityParametersRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanStabilityParameters()));
    when(portLoadingPlanRobDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanRobDetails()));
    when(loadingPlanBallastDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingPlanBallastDetails()));
    when(loadingPlanRobDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingPlanRobDetails()));
    when(portLoadingPlanBallastDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanBallastDetails()));
    when(portLoadingPlanBallastTempDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanBallastTempDetails()));
    when(portLoadingPlanStowageDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanStowageDetails()));
    when(portLoadingPlanStowageTempDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanStowageTempDetails()));
    when(loadingPlanStowageDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingPlanStowageDetails()));
    when(loadingSequenceStabiltyParametersRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingSequenceStabilityParameters()));
    when(loadingPlanStabilityParametersRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingPlanStabilityParameters()));
    when(portLoadingPlanCommingleTempDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanCommingleTempDetails()));
    when(portLoadingPlanCommingleDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new PortLoadingPlanCommingleDetails()));
    when(billOfLandingRepository.findById(anyLong())).thenReturn(Optional.of(new BillOfLanding()));
    when(ballastOperationRepository.findById(anyLong()))
        .thenReturn(Optional.of(new BallastOperation()));
    when(eductionOperationRepository.findById(anyLong()))
        .thenReturn(Optional.of(new EductionOperation()));
    when(cargoLoadingRateRepository.findById(anyLong()))
        .thenReturn(Optional.of(new CargoLoadingRate()));
    when(loadingInfoStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingInformationStatus()));
    when(algoErrorHeadingRepository.findById(anyLong()))
        .thenReturn(Optional.of(new AlgoErrorHeading()));
    when(algoErrorsRepository.findById(anyLong())).thenReturn(Optional.of(new AlgoErrors()));
    when(loadingInstructionRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingInstruction()));
    when(loadingInfoStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingInformationStatus()));
    when(loadingInformationCommunicationRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadingInformation()));
    when(loadingInformationCommunicationRepository.save(any()))
        .thenReturn(new LoadingInformation());
    when(loadingInformationAlgoStatusRepository.save(any()))
        .thenReturn(new LoadingInformationAlgoStatus());
    when(loadableStudyServiceBlockingStub.saveLoadablePatternForCommunication(
            any(LoadableStudy.LoadableStudyPatternCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyPatternCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveLoadicatorDataSynopticalForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveLoadableStudyPortRotationDataForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveJsonDataForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveSynopticalDataForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveLoadablePlanStowageBallastDetailsForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveLoadablePatternCargoDetailsForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveLoadablePlanCommingleDetailsPortwiseForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveOnBoardQuantityForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyServiceBlockingStub.saveOnHandQuantityForCommunication(
            any(LoadableStudy.LoadableStudyCommunicationRequest.class)))
        .thenReturn(
            LoadableStudy.LoadableStudyCommunicationReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    ReflectionTestUtils.setField(
        loadingPlanCommunicationService,
        "loadingInformationRepository",
        loadingInformationCommunicationRepository);
    ReflectionTestUtils.setField(
        loadingPlanCommunicationService,
        "loadableStudyServiceBlockingStub",
        loadableStudyServiceBlockingStub);

    loadingPlanCommunicationService.getStagingData(list, "", "");
    verify(loadingPlanStagingService, atLeast(1))
        .updateStatusForProcessId(Mockito.anyString(), anyString());
  }

  private com.cpdss.common.generated.EnvoyWriter.WriterReply getWR() {
    com.cpdss.common.generated.EnvoyWriter.WriterReply reply =
        com.cpdss.common.generated.EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private VesselInfo.VesselIdResponse getVIR() {
    VesselInfo.VesselIdResponse response =
        VesselInfo.VesselIdResponse.newBuilder()
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private VesselInfo.VesselIdResponse getVIRNS() {
    VesselInfo.VesselIdResponse response =
        VesselInfo.VesselIdResponse.newBuilder()
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return response;
  }

  private HashMap<String, String> getHSS() {
    HashMap<String, String> map = new HashMap<>();
    map.put("1", "1");
    return map;
  }

  private List<DataTransferStage> getLDTS() {
    List<DataTransferStage> list = new ArrayList<>();
    DataTransferStage dataTransferStage = new DataTransferStage();
    dataTransferStage.setData("[{\\\"loadingInfoId\\\":1}\"]");
    dataTransferStage.setId(1L);
    dataTransferStage.setProcessGroupId("LoadingPlan_Save");
    dataTransferStage.setProcessIdentifier("loading_information");
    dataTransferStage.setProcessId("1");
    list.add(dataTransferStage);

    DataTransferStage stage = new DataTransferStage();
    stage.setProcessGroupId("LoadingPlan");
    list.add(stage);

    DataTransferStage transferStage = new DataTransferStage();
    transferStage.setProcessGroupId("LoadingPlan_AlgoResult");
    list.add(transferStage);

    DataTransferStage stage1 = new DataTransferStage();
    stage1.setProcessGroupId("Ullage_Update");
    list.add(stage1);
    return list;
  }

  @Test
  void testSaveActivatedVoyage() {
    LoadingPlanCommunicationService spyService = spy(LoadingPlanCommunicationService.class);
    LoadableStudy.VoyageActivateRequest grpcRequest =
        LoadableStudy.VoyageActivateRequest.newBuilder().build();
    Mockito.when(
            this.loadableStudyServiceBlockingStub.saveActivatedVoyage(
                Mockito.any(LoadableStudy.VoyageActivateRequest.class)))
        .thenReturn(getVAR());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyServiceBlockingStub", this.loadableStudyServiceBlockingStub);
    var response = spyService.saveActivatedVoyage(grpcRequest);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  private com.cpdss.common.generated.LoadableStudy.VoyageActivateReply getVAR() {
    com.cpdss.common.generated.LoadableStudy.VoyageActivateReply reply =
        com.cpdss.common.generated.LoadableStudy.VoyageActivateReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }
}
