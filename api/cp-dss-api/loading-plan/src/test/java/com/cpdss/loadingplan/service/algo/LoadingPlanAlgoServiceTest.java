/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.EnvoyWriter;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import com.cpdss.loadingplan.service.loadicator.LoadicatorService;
import com.google.gson.JsonArray;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(
    classes = {
      LoadingPlanAlgoService.class,
    })
public class LoadingPlanAlgoServiceTest {
  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;

  @MockBean RestTemplate restTemplate;
  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean LoadingInformationStatusRepository loadingInfoStatusRepository;
  @MockBean LoadingInformationAlgoStatusRepository loadingInfoAlgoStatusRepository;
  @MockBean LoadingSequenceRepository loadingSequenceRepository;
  @MockBean BallastValveRepository ballastValveRepository;
  @MockBean CargoValveRepository cargoValveRepository;
  @MockBean DeballastingRateRepository deballastingRateRepository;
  @MockBean LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @MockBean LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @MockBean LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @MockBean LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @MockBean LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;
  @MockBean CargoLoadingRateRepository cargoLoadingRateRepository;
  @MockBean PortLoadingPlanBallastDetailsRepository portBallastDetailsRepository;
  @MockBean PortLoadingPlanRobDetailsRepository portRobDetailsRepository;
  @MockBean PortLoadingPlanStabilityParametersRepository portStabilityParamsRepository;
  @MockBean PortLoadingPlanStowageDetailsRepository portStowageDetailsRepository;
  @MockBean BallastOperationRepository ballastOperationRepository;
  @MockBean LoadingSequenceStabiltyParametersRepository loadingSequenceStabilityParamsRepository;
  @MockBean AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean AlgoErrorsRepository algoErrorsRepository;
  @MockBean PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;
  @MockBean PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;
  @MockBean LoadingPlanCommingleDetailsRepository loadingPlanCommingleDetailsRepository;
  @MockBean PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @MockBean
  PortLoadingPlanCommingleTempDetailsRepository portLoadingPlanCommingleTempDetailsRepository;

  @MockBean EductionOperationRepository eductionOperationRepository;
  @MockBean LoadingInformationAlgoRequestBuilderService loadingInfoAlgoRequestBuilderService;
  @MockBean LoadingPlanBuilderService loadingPlanBuilderService;
  @MockBean LoadicatorService loadicatorService;
  // @MockBean LoadingPlanCommunicationService loadingPlancommunicationService;
  @MockBean LoadingPlanStagingService loadingPlanStagingService;
  @MockBean LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;
  @MockBean private LoadingPlanCommunicationService communicationService;
  @MockBean private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyService;
  private static final String SUCCESS = "SUCCESS";

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  private EnvoyWriter.WriterReply getWriterReply() {
    EnvoyWriter.WriterReply ewReply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setMessageId("1")
            .build();
    return ewReply;
  }

  @Test
  void testGenerateLoadingPlan() throws GenericServiceException {
    LoadingPlanAlgoService spyService = spy(LoadingPlanAlgoService.class);
    LoadingPlanModels.LoadingInfoAlgoRequest request =
        LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder().build();
    LoadingPlanModels.LoadingInfoAlgoReply.Builder builder =
        LoadingPlanModels.LoadingInfoAlgoReply.newBuilder();

    LoadingPlanCommunicationStatus communicationStatus = new LoadingPlanCommunicationStatus();

    when(this.loadingPlanCommunicationStatusRepository.save(
            any(LoadingPlanCommunicationStatus.class)))
        .thenReturn(communicationStatus);
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(getWriterReply());
    when(loadingPlanStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong(), any()))
        .thenReturn(new JsonArray());
    when(loadingInformationRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    doReturn(Optional.of(getStatus())).when(spyService).getLoadingInformationStatus(anyLong());
    doNothing()
        .when(loadingInformationRepository)
        .updateLoadingInfoWithInfoStatus(
            any(LoadingInformationStatus.class), anyBoolean(), anyBoolean(), anyLong());
    doNothing()
        .when(spyService)
        .createLoadingInformationAlgoStatus(
            any(LoadingInformation.class), anyString(), any(LoadingInformationStatus.class), any());

    ReflectionTestUtils.setField(spyService, "enableCommunication", true);
    ReflectionTestUtils.setField(spyService, "env", "ship");
    ReflectionTestUtils.setField(
        spyService, "loadingInformationRepository", loadingInformationRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);
    ReflectionTestUtils.setField(spyService, "communicationService", communicationService);
    ReflectionTestUtils.setField(
        spyService,
        "loadingPlanCommunicationStatusRepository",
        loadingPlanCommunicationStatusRepository);

    spyService.generateLoadingPlan(request, builder);
    assertEquals(1l, builder.getLoadingInfoId());
  }

  @Test
  void testGetLoadingInformationStatus() throws GenericServiceException {
    when(loadingInfoStatusRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getStatus()));
    var result = loadingPlanAlgoService.getLoadingInformationStatus(1l);
    assertEquals(1l, result.get().getId());
  }

  @Test
  void testSaveAlgoLoadingPlanStatus() throws GenericServiceException {
    LoadableStudy.AlgoStatusRequest request =
        LoadableStudy.AlgoStatusRequest.newBuilder()
            .setLoadableStudystatusId(1l)
            .setProcesssId("1")
            .build();
    when(loadingInfoAlgoStatusRepository.findByProcessIdAndIsActiveTrue(anyString()))
        .thenReturn(Optional.of(getAlgoStatus()));
    loadingPlanAlgoService.saveAlgoLoadingPlanStatus(request);
    verify(loadingInfoAlgoStatusRepository)
        .updateLoadingInformationAlgoStatus(anyLong(), anyString());
  }

  private LoadingInformationAlgoStatus getAlgoStatus() {
    LoadingInformationAlgoStatus status = new LoadingInformationAlgoStatus();
    status.setLoadingInformationStatus(getStatus());
    status.setLastModifiedDateTime(LocalDateTime.now());
    return status;
  }

  @Test
  void testCreateLoadingInformationAlgoStatus() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1l);
    loadingInformation.setVesselXId(1l);
    LoadingInformationStatus status = new LoadingInformationStatus();
    loadingPlanAlgoService.createLoadingInformationAlgoStatus(loadingInformation, "1", status, 1);
    verify(loadingInfoAlgoStatusRepository).save(any(LoadingInformationAlgoStatus.class));
  }

  @Test
  void testSaveLoadingSequenceAndPlan() throws GenericServiceException {
    LoadingPlanAlgoService spyService = spy(LoadingPlanAlgoService.class);
    LoadingPlanModels.LoadingPlanSaveResponse.Builder builder =
        LoadingPlanModels.LoadingPlanSaveResponse.newBuilder();
    LoadingPlanCommunicationStatus communicationStatus = new LoadingPlanCommunicationStatus();
    List<com.cpdss.loadingplan.entity.LoadingSequence> oldLoadingSequences = new ArrayList<>();
    com.cpdss.loadingplan.entity.LoadingSequence sequence =
        new com.cpdss.loadingplan.entity.LoadingSequence();
    oldLoadingSequences.add(sequence);
    List<LoadingPlanPortWiseDetails> detailsList = new ArrayList<>();

    when(loadingSequenceRepository.findByLoadingInformationAndIsActive(
            any(LoadingInformation.class), anyBoolean()))
        .thenReturn(oldLoadingSequences);
    when(loadingSequenceRepository.save(any(com.cpdss.loadingplan.entity.LoadingSequence.class)))
        .thenReturn(oldLoadingSequences.get(0));
    when(loadingInformationRepository.findById(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    doReturn(Optional.of(getStatus())).when(spyService).getLoadingInformationStatus(anyLong());
    doNothing()
        .when(loadingInformationRepository)
        .updateLoadingInformationStatus(any(LoadingInformationStatus.class), anyLong());
    doNothing()
        .when(spyService)
        .updateLoadingInfoAlgoStatus(
            any(LoadingInformation.class), anyString(), any(LoadingInformationStatus.class));
    doNothing()
        .when(algoErrorHeadingRepository)
        .deleteByLoadingInformation(any(LoadingInformation.class));
    doNothing()
        .when(algoErrorsRepository)
        .deleteByLoadingInformation(any(LoadingInformation.class));
    when(algoErrorsRepository.save(any(AlgoErrors.class))).thenReturn(new AlgoErrors());
    doReturn(Optional.of(getStatus())).when(spyService).getLoadingInformationStatus(anyLong());
    doNothing()
        .when(loadingInformationRepository)
        .updateLoadingInformationStatus(any(LoadingInformationStatus.class), anyLong());
    doNothing()
        .when(spyService)
        .updateLoadingInfoAlgoStatus(
            any(LoadingInformation.class), anyString(), any(LoadingInformationStatus.class));
    doNothing()
        .when(loadingPlanBuilderService)
        .buildLoadingSequence(
            any(LoadingSequence.class),
            any(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.class),
            any(LoadingInformation.class));
    doNothing()
        .when(loadingPlanBuilderService)
        .buildBallastValve(
            any(LoadingSequence.class),
            any(BallastValve.class),
            any(LoadingPlanModels.Valve.class));
    List<BallastValve> ballastValves = new ArrayList<BallastValve>();
    doNothing()
        .when(loadingPlanBuilderService)
        .buildCargoValve(
            any(LoadingSequence.class), any(CargoValve.class), any(LoadingPlanModels.Valve.class));
    List<CargoValve> cargoValves = new ArrayList<CargoValve>();
    when(this.cargoValveRepository.saveAll(anyList())).thenReturn(cargoValves);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildDeBallastingRate(
            any(LoadingSequence.class),
            any(DeballastingRate.class),
            any(LoadingPlanModels.DeBallastingRate.class));
    List<DeballastingRate> deballastingRates = new ArrayList<DeballastingRate>();
    when(this.deballastingRateRepository.saveAll(anyList())).thenReturn(deballastingRates);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildLoadingPlanPortWiseDetails(
            any(LoadingSequence.class),
            any(LoadingPlanPortWiseDetails.class),
            any(LoadingPlanModels.LoadingPlanPortWiseDetails.class));
    when(this.loadingPlanPortWiseDetailsRepository.save(any(LoadingPlanPortWiseDetails.class)))
        .thenReturn(new com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails());
    doNothing()
        .when(loadingPlanBuilderService)
        .buildLoadingPlanRobDetails(
            any(com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails.class),
            any(LoadingPlanRobDetails.class),
            any(LoadingPlanModels.LoadingPlanTankDetails.class));
    List<LoadingPlanRobDetails> loadingPlanRobDetails = new ArrayList<LoadingPlanRobDetails>();
    when(this.loadingPlanRobDetailsRepository.saveAll(anyList())).thenReturn(loadingPlanRobDetails);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildStabilityParameters(
            any(com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails.class),
            any(LoadingPlanStabilityParameters.class),
            any(LoadingPlanModels.LoadingPlanStabilityParameters.class));
    when(loadingPlanStabilityParametersRepository.save(any()))
        .thenReturn(new com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters());
    doNothing()
        .when(loadingPlanBuilderService)
        .buildLoadingPlanCommingleDetails(
            any(com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails.class),
            any(LoadingPlanCommingleDetails.class),
            any(LoadingPlanModels.LoadingPlanCommingleDetails.class));
    List<com.cpdss.loadingplan.entity.LoadingPlanCommingleDetails> loadingPlanCommingleDetails =
        new ArrayList<com.cpdss.loadingplan.entity.LoadingPlanCommingleDetails>();
    when(loadingPlanCommingleDetailsRepository.saveAll(anyList()))
        .thenReturn(loadingPlanCommingleDetails);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildCargoLoadingRate(
            any(LoadingSequence.class),
            any(CargoLoadingRate.class),
            any(LoadingPlanModels.LoadingRate.class));
    List<CargoLoadingRate> cargoLoadingRates = new ArrayList<CargoLoadingRate>();
    when(cargoLoadingRateRepository.saveAll(anyList())).thenReturn(cargoLoadingRates);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildBallastOperation(
            any(LoadingSequence.class),
            any(BallastOperation.class),
            any(LoadingPlanModels.PumpOperation.class));
    List<BallastOperation> ballastOperations = new ArrayList<BallastOperation>();
    when(ballastOperationRepository.saveAll(anyList())).thenReturn(ballastOperations);
    when(eductionOperationRepository.save(any())).thenReturn(new EductionOperation());
    when(loadingPlanPortWiseDetailsRepository.findByLoadingSequenceAndIsActiveTrueOrderById(
            any(LoadingSequence.class)))
        .thenReturn(detailsList);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildLoadingSequenceStabilityParams(
            any(LoadingInformation.class),
            any(LoadingPlanModels.LoadingPlanStabilityParameters.class),
            any(LoadingSequenceStabilityParameters.class));
    List<LoadingSequenceStabilityParameters> loadingSequenceStabilityParams =
        new ArrayList<LoadingSequenceStabilityParameters>();
    when(loadingSequenceStabilityParamsRepository.saveAll(anyList()))
        .thenReturn(loadingSequenceStabilityParams);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildPortBallast(
            any(LoadingInformation.class),
            any(PortLoadingPlanBallastDetails.class),
            any(LoadingPlanModels.LoadingPlanTankDetails.class));
    List<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetails =
        new ArrayList<PortLoadingPlanBallastDetails>();
    when(portBallastDetailsRepository.saveAll(anyList())).thenReturn(portLoadingPlanBallastDetails);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildPortRob(
            any(LoadingInformation.class),
            any(PortLoadingPlanRobDetails.class),
            any(LoadingPlanModels.LoadingPlanTankDetails.class));
    List<PortLoadingPlanRobDetails> portLoadingPlanRobDetails =
        new ArrayList<PortLoadingPlanRobDetails>();
    when(portRobDetailsRepository.saveAll(anyList())).thenReturn(portLoadingPlanRobDetails);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildPortStabilityParams(
            any(LoadingInformation.class),
            any(PortLoadingPlanStabilityParameters.class),
            any(LoadingPlanModels.LoadingPlanStabilityParameters.class));
    List<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParams =
        new ArrayList<PortLoadingPlanStabilityParameters>();
    when(portStabilityParamsRepository.saveAll(anyList()))
        .thenReturn(portLoadingPlanStabilityParams);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildPortStowage(
            any(LoadingInformation.class),
            any(PortLoadingPlanStowageDetails.class),
            any(LoadingPlanModels.LoadingPlanTankDetails.class));
    List<PortLoadingPlanStowageDetails> portLoadingPlanStowages =
        new ArrayList<PortLoadingPlanStowageDetails>();
    when(portStowageDetailsRepository.saveAll(anyList())).thenReturn(portLoadingPlanStowages);
    doNothing()
        .when(loadingPlanBuilderService)
        .buildPortCommingle(
            any(LoadingInformation.class),
            any(PortLoadingPlanCommingleDetails.class),
            any(LoadingPlanModels.LoadingPlanCommingleDetails.class));
    List<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetails =
        new ArrayList<PortLoadingPlanCommingleDetails>();
    when(portLoadingPlanCommingleDetailsRepository.saveAll(anyList()))
        .thenReturn(portLoadingPlanCommingleDetails);
    when(loadingPlanStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong(), any()))
        .thenReturn(new JsonArray());
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(getWriterReply());
    when(this.loadingPlanCommunicationStatusRepository.save(
            any(LoadingPlanCommunicationStatus.class)))
        .thenReturn(communicationStatus);
    doNothing()
        .when(loadicatorService)
        .saveLoadicatorInfo(any(LoadingInformation.class), anyString());
    doReturn(Optional.of(getStatus()))
        .when(loadingInfoStatusRepository)
        .findByIdAndIsActive(anyLong(), anyBoolean());
    doNothing()
        .when(spyService)
        .updateLoadingInfoAlgoStatus(
            any(LoadingInformation.class), anyString(), any(LoadingInformationStatus.class));

    ReflectionTestUtils.setField(spyService, "enableCommunication", true);
    ReflectionTestUtils.setField(spyService, "env", "ship");
    ReflectionTestUtils.setField(spyService, "loadicatorService", loadicatorService);
    ReflectionTestUtils.setField(
        spyService,
        "loadingPlanCommunicationStatusRepository",
        loadingPlanCommunicationStatusRepository);
    ReflectionTestUtils.setField(spyService, "communicationService", communicationService);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStagingService", loadingPlanStagingService);
    ReflectionTestUtils.setField(
        spyService,
        "portLoadingPlanCommingleDetailsRepository",
        portLoadingPlanCommingleDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanBuilderService", loadingPlanBuilderService);
    ReflectionTestUtils.setField(
        spyService, "portStowageDetailsRepository", portStowageDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "portStabilityParamsRepository", portStabilityParamsRepository);
    ReflectionTestUtils.setField(spyService, "portRobDetailsRepository", portRobDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "portBallastDetailsRepository", portBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadingSequenceStabilityParamsRepository",
        loadingSequenceStabilityParamsRepository);
    ReflectionTestUtils.setField(spyService, "ballastValveRepository", ballastValveRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingInformationRepository", loadingInformationRepository);
    ReflectionTestUtils.setField(spyService, "algoErrorsRepository", algoErrorsRepository);
    ReflectionTestUtils.setField(
        spyService, "algoErrorHeadingRepository", algoErrorHeadingRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingSequenceRepository", loadingSequenceRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanPortWiseDetailsRepository", loadingPlanPortWiseDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "eductionOperationRepository", eductionOperationRepository);
    ReflectionTestUtils.setField(
        spyService, "ballastOperationRepository", ballastOperationRepository);
    ReflectionTestUtils.setField(
        spyService, "cargoLoadingRateRepository", cargoLoadingRateRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanCommingleDetailsRepository", loadingPlanCommingleDetailsRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadingPlanStabilityParametersRepository",
        loadingPlanStabilityParametersRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanRobDetailsRepository", loadingPlanRobDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanPortWiseDetailsRepository", loadingPlanPortWiseDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "deballastingRateRepository", deballastingRateRepository);
    ReflectionTestUtils.setField(spyService, "cargoValveRepository", cargoValveRepository);

    loadingPlanAlgoService.saveLoadingSequenceAndPlan(builder, getRequest());
    verify(this.portLoadingPlanCommingleDetailsRepository).saveAll(anyList());
    verify(eductionOperationRepository).save(any(EductionOperation.class));
  }

  @Test
  void testUpdateLoadingInfoAlgoStatus() throws GenericServiceException {
    doNothing()
        .when(this.loadingInfoAlgoStatusRepository)
        .updateLoadingInformationAlgoStatus(anyLong(), anyLong(), anyString());

    loadingPlanAlgoService.updateLoadingInfoAlgoStatus(getLoadingInfoEntity(), "1", getStatus());
  }

  @Test
  void testgetLoadingInfoAlgoStatus() throws GenericServiceException {
    LoadingPlanModels.LoadingInfoStatusRequest request =
        LoadingPlanModels.LoadingInfoStatusRequest.newBuilder()
            .setConditionType(1)
            .setLoadingInfoId(1l)
            .setProcessId("1")
            .build();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoStatusReply.Builder
        builder =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoStatusReply
                .newBuilder();
    when(loadingInfoAlgoStatusRepository
            .findByProcessIdAndLoadingInformationIdAndConditionTypeAndIsActiveTrue(
                anyString(), anyLong(), anyInt()))
        .thenReturn(Optional.of(getAlgoStatus()));

    loadingPlanAlgoService.getLoadingInfoAlgoStatus(request, builder);
    assertEquals(1l, builder.getLoadingInfoId());
    assertEquals(1l, builder.getLoadingInfoStatusId());
  }

  @Test
  void testGetLoadingInfoAlgoErrors() throws GenericServiceException {
    LoadableStudy.AlgoErrorRequest request =
        LoadableStudy.AlgoErrorRequest.newBuilder()
            .setLoadingInformationId(1l)
            .setConditionType(1)
            .build();
    LoadableStudy.AlgoErrorReply.Builder builder = LoadableStudy.AlgoErrorReply.newBuilder();
    List<AlgoErrors> errorsList = new ArrayList<>();
    AlgoErrors algoErrors = new AlgoErrors();
    algoErrors.setErrorMessage("1");
    errorsList.add(algoErrors);
    List<AlgoErrorHeading> headingList = new ArrayList<>();
    AlgoErrorHeading heading = new AlgoErrorHeading();
    heading.setId(1L);
    heading.setErrorHeading("1");
    heading.setAlgoErrors(errorsList);
    headingList.add(heading);
    when(algoErrorHeadingRepository.findByLoadingInformationIdAndConditionTypeAndIsActiveTrue(
            anyLong(), anyInt()))
        .thenReturn(headingList);

    loadingPlanAlgoService.getLoadingInfoAlgoErrors(request, builder);
    assertEquals("1", builder.getAlgoErrors(0).getErrorMessages(0));
    assertEquals("1", builder.getAlgoErrors(0).getErrorHeading());
  }

  private LoadingPlanModels.LoadingPlanSaveRequest getRequest() {
    List<LoadingPlanModels.LoadingSequence> sequenceList = new ArrayList<>();
    List<LoadingPlanModels.Valve> valveList = new ArrayList<>();
    List<LoadingPlanModels.DeBallastingRate> rateList = new ArrayList<>();
    List<LoadingPlanModels.LoadingPlanPortWiseDetails> portWiseDetailsList = new ArrayList<>();
    List<LoadingPlanModels.LoadingRate> loadingRateList = new ArrayList<>();
    List<LoadingPlanModels.PumpOperation> pumpOperationList = new ArrayList<>();

    LoadingPlanModels.LoadingSequence sequence =
        LoadingPlanModels.LoadingSequence.newBuilder()
            .addAllBallastValves(valveList)
            .addAllCargoValves(valveList)
            .addAllDeBallastingRates(rateList)
            .addAllLoadingPlanPortWiseDetails(portWiseDetailsList)
            .addAllLoadingRates(loadingRateList)
            .addAllBallastOperations(pumpOperationList)
            .setEductorOperation(
                LoadingPlanModels.EductorOperation.newBuilder()
                    .setEndTime(1)
                    .setStartTime(1)
                    .setTanksUsed("1")
                    .build())
            .build();
    sequenceList.add(sequence);
    List<String> list = Arrays.asList("user");
    List<com.cpdss.common.generated.LoadableStudy.AlgoErrors> algoErrorsList = new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
        LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .addAllErrorMessages(list)
            .build();
    algoErrorsList.add(algoErrors);
    LoadingPlanModels.LoadingPlanSaveRequest request =
        LoadingPlanModels.LoadingPlanSaveRequest.newBuilder()
            .setLoadingInfoId(1l)
            .setLoadingPlanDetailsFromAlgo("1")
            .addAllLoadingSequences(sequenceList)
            .setHasLoadicator(true)
            .addAllAlgoErrors(algoErrorsList)
            .build();
    return request;
  }

  private LoadingInformationStatus getStatus() {
    LoadingInformationStatus status = new LoadingInformationStatus();
    status.setId(1l);
    return status;
  }

  private LoadingInformation getLoadingInfoEntity() {
    LoadingInformation loadingInfoOpt = new LoadingInformation();
    loadingInfoOpt.setVoyageId(1l);
    loadingInfoOpt.setVesselXId(1l);
    loadingInfoOpt.setPortXId(1l);
    loadingInfoOpt.setLoadablePatternXId(1l);
    loadingInfoOpt.setPortRotationXId(1l);
    loadingInfoOpt.setId(1l);
    return loadingInfoOpt;
  }
}
