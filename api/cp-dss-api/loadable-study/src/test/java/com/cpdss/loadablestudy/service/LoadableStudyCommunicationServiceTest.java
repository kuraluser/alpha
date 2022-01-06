/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.ProcessIdentifiers;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      LoadableStudyCommunicationService.class,
    })
public class LoadableStudyCommunicationServiceTest {
  @Autowired LoadableStudyCommunicationService loadableStudyCommunicationService;

  @MockBean private LoadableStudyStagingService loadableStudyStagingService;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private CommingleCargoRepository commingleCargoRepository;
  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @MockBean
  private LoadablePatternCargoToppingOffSequenceRepository
      loadablePatternCargoToppingOffSequenceRepository;

  @MockBean private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private StabilityParameterRepository stabilityParameterRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean CargoOperationRepository cargoOperationRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private LoadablePlanRepository loadablePlanRepository;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private LoadablePlanService loadablePlanService;
  @MockBean private CowHistoryRepository cowHistoryRepository;

  @MockBean
  private DischargePatternQuantityCargoPortwiseRepository
      dischargePatternQuantityCargoPortwiseRepository;

  @MockBean private GenerateDischargeStudyJson generateDischargeStudyJson;
  @MockBean private VoyageStatusRepository voyageStatusRepository;

  @MockBean
  private LoadableStudyPortRotationCommuncationRepository
      loadableStudyPortRotationCommuncationRepository;

  @MockBean LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @MockBean LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean LoadablePlanStowageDetailsTempRepository loadablePlanStowageDetailsTempRepository;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean private LoadableStudyServiceShore loadableStudyServiceShore;
  @MockBean private DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;

  @Test
  void testGetLoadableStudyStagingDataWithRetryStatus() throws GenericServiceException {
    LoadableStudyCommunicationService spyService = spy(LoadableStudyCommunicationService.class);

    when(loadableStudyStagingService.getAllWithStatus(anyString()))
        .thenReturn(getDataTransferStageList());
    doNothing().when(spyService).processStagingData(anyList(), anyString(), anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);

    spyService.getLoadableStudyStagingData("retry", "1", "1");
    verify(spyService).processStagingData(anyList(), anyString(), anyString());
  }

  @Test
  void testGetLoadableStudyStagingDataWithIn_progressStatus() throws GenericServiceException {
    LoadableStudyCommunicationService spyService = spy(LoadableStudyCommunicationService.class);

    when(loadableStudyStagingService.getAllWithStatusAndTime(anyString(), any(LocalDateTime.class)))
        .thenReturn(getDataTransferStageList());
    doNothing().when(spyService).processStagingData(anyList(), anyString(), anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);

    spyService.getLoadableStudyStagingData("in_progress", "1", "1");
    verify(spyService).processStagingData(anyList(), anyString(), anyString());
  }

  @Test
  void testGetStowageStagingData() throws GenericServiceException {
    LoadableStudyCommunicationService spyService = spy(LoadableStudyCommunicationService.class);
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    transferStage.setProcessGroupId("ValidatePlan");
    transferStageList.add(transferStage);

    when(loadableStudyStagingService.getAllWithStatus(anyString())).thenReturn(transferStageList);
    doNothing().when(spyService).processStagingData(anyList(), anyString(), anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);

    spyService.getStowageStagingData("retry", "1", "1");
    verify(spyService).processStagingData(anyList(), anyString(), anyString());
  }

  @Test
  void testGetDischargeStudyStagingData() throws GenericServiceException {
    LoadableStudyCommunicationService spyService = spy(LoadableStudyCommunicationService.class);
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    transferStage.setProcessGroupId("DischargeStudy");
    transferStageList.add(transferStage);

    when(loadableStudyStagingService.getAllWithStatus(anyString())).thenReturn(transferStageList);
    doNothing().when(spyService).processStagingData(anyList(), anyString(), anyString());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyStagingService", loadableStudyStagingService);

    spyService.getDischargeStudyStagingData("retry", "1", "1");
    verify(spyService).processStagingData(anyList(), anyString(), anyString());
  }

  @Test
  void testProcessStagingDataVoyage() throws GenericServiceException, IOException {
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    String j = "[{\"id\":1,\"voyage_status\":1}]";
    transferStage.setData(j);
    transferStage.setProcessIdentifier("voyage");
    transferStage.setProcessGroupId("");
    transferStageList.add(transferStage);
    HashMap<String, String> map = new HashMap<>();

    doNothing()
        .when(loadableStudyStagingService)
        .updateStatusForProcessId(anyString(), anyString());
    when(loadableStudyStagingService.getAttributeMapping(any(Object.class))).thenReturn(map);
    when(loadablePatternService.generateLoadablePatterns(
            any(LoadableStudy.AlgoRequest.class), any(LoadableStudy.AlgoReply.Builder.class)))
        .thenReturn(LoadableStudy.AlgoReply.newBuilder());
    when(voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));

    loadableStudyCommunicationService.processStagingData(transferStageList, "cloud", "1");
    verify(loadableStudyStagingService).updateStatusCompletedForProcessId(anyString(), anyString());
  }

  @Test
  void testProcessStagingDataLoadable_study() throws GenericServiceException, IOException {
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    String j = "[{\"voyage_xid\":1,\"loadable_study_status_xid\":1,\"id\":1}]";
    transferStage.setData(j);
    transferStage.setProcessIdentifier("loadable_study");
    transferStage.setProcessGroupId("LoadableStudy");
    transferStageList.add(transferStage);
    HashMap<String, String> map = new HashMap<>();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1l);
    JsonArray jsonArray = JsonParser.parseString(j).getAsJsonArray();

    doNothing()
        .when(loadableStudyStagingService)
        .updateStatusForProcessId(anyString(), anyString());
    when(loadableStudyStagingService.getAttributeMapping(any(Object.class))).thenReturn(map);
    when(loadablePatternService.generateLoadablePatterns(
            any(LoadableStudy.AlgoRequest.class), any(LoadableStudy.AlgoReply.Builder.class)))
        .thenReturn(LoadableStudy.AlgoReply.newBuilder());
    when(loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(loadableStudyRepository.save(any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(loadableStudy);
    when(loadableStudyStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudyStatus()));
    when(loadableStudyStagingService.getAsEntityJson(any(HashMap.class), any(JsonArray.class)))
        .thenReturn(jsonArray);

    loadableStudyCommunicationService.processStagingData(transferStageList, "cloud", "1");
    verify(loadablePatternService)
        .generateLoadablePatterns(
            any(LoadableStudy.AlgoRequest.class), any(LoadableStudy.AlgoReply.Builder.class));
  }

  @Test
  void testProcessStagingDataValidatePlan() throws GenericServiceException, IOException {
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    String j = "[{\"voyage_xid\":1,\"loadablePatternXId\":1,\"id\":1}]";
    transferStage.setData(j);
    transferStage.setProcessIdentifier("loadable_pattern");
    transferStage.setProcessGroupId("ValidatePlan");
    transferStageList.add(transferStage);
    HashMap<String, String> map = new HashMap<>();
    com.cpdss.loadablestudy.entity.LoadablePattern loadablePattern =
        new com.cpdss.loadablestudy.entity.LoadablePattern();
    loadablePattern.setId(1l);
    JsonArray jsonArray = JsonParser.parseString(j).getAsJsonArray();

    doNothing()
        .when(loadableStudyStagingService)
        .updateStatusForProcessId(anyString(), anyString());
    when(loadableStudyStagingService.getAttributeMapping(any(Object.class))).thenReturn(map);
    when(loadablePlanService.validateLoadablePlan(
            any(LoadableStudy.LoadablePlanDetailsRequest.class),
            any(LoadableStudy.AlgoReply.Builder.class)))
        .thenReturn(LoadableStudy.AlgoReply.newBuilder());
    when(loadablePatternRepository.findById(anyLong())).thenReturn(Optional.of(loadablePattern));
    when(loadablePatternRepository.saveAll(anyList())).thenReturn(Arrays.asList(loadablePattern));
    when(loadableStudyStagingService.getAsEntityJson(any(HashMap.class), any(JsonArray.class)))
        .thenReturn(jsonArray);
    when(loadableStudyServiceShore.updateCommunicationStatus(anyString(), anyLong()))
        .thenReturn(new LoadableStudyCommunicationStatus());

    loadableStudyCommunicationService.processStagingData(transferStageList, "cloud", "1");
    verify(loadablePlanService)
        .validateLoadablePlan(
            any(LoadableStudy.LoadablePlanDetailsRequest.class),
            any(LoadableStudy.AlgoReply.Builder.class));
  }

  @Test
  void testProcessStagingDataDischargeStudy() throws GenericServiceException, IOException {
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    String j = "[{\"voyage_xid\":1,\"loadable_study_status_xid\":1,\"id\":1}]";
    transferStage.setData(j);
    transferStage.setProcessIdentifier("loadable_study");
    transferStage.setProcessGroupId("DischargeStudy");
    transferStageList.add(transferStage);
    HashMap<String, String> map = new HashMap<>();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1l);
    JsonArray jsonArray = JsonParser.parseString(j).getAsJsonArray();

    doNothing()
        .when(loadableStudyStagingService)
        .updateStatusForProcessId(anyString(), anyString());
    when(loadableStudyStagingService.getAttributeMapping(any(Object.class))).thenReturn(map);
    when(generateDischargeStudyJson.generateDischargePatterns(
            any(LoadableStudy.AlgoRequest.class), any(LoadableStudy.AlgoReply.Builder.class)))
        .thenReturn(LoadableStudy.AlgoReply.newBuilder());
    when(loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(loadableStudyRepository.save(any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(loadableStudy);
    when(loadableStudyStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudyStatus()));
    when(loadableStudyStagingService.getAsEntityJson(any(HashMap.class), any(JsonArray.class)))
        .thenReturn(jsonArray);

    loadableStudyCommunicationService.processStagingData(transferStageList, "cloud", "1");
    verify(generateDischargeStudyJson)
        .generateDischargePatterns(
            any(LoadableStudy.AlgoRequest.class), any(LoadableStudy.AlgoReply.Builder.class));
  }

  @ParameterizedTest
  @EnumSource(ProcessIdentifiers.class)
  void testProcessStagingData(ProcessIdentifiers str) throws GenericServiceException, IOException {
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = getDataTransferStageList().get(0);
    String j =
        "[{\"voyage_xid\":1,\"loadable_study_status_xid\":1,\"id\":1,\"voyage_status\":1,\"operation_xid\":1,"
            + "\"port_rotation_xid\":1,\"json_type_xid\":1,\"loadable_study_status\":1,\"loadablePatternXId\":1,"
            + "\"loadable_pattern_xid\":1,\"error_heading_xid\":1,\"cargo_nomination_xid\":1,\"synoptical_table_xid\":1}]";
    transferStage.setData(j);
    transferStage.setProcessIdentifier(str.toString());
    transferStage.setProcessGroupId("");
    transferStageList.add(transferStage);
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1l);
    HashMap<String, String> map = new HashMap<>();
    JsonArray jsonArray = JsonParser.parseString(j).getAsJsonArray();

    doNothing()
        .when(loadableStudyStagingService)
        .updateStatusForProcessId(anyString(), anyString());
    when(loadableStudyStagingService.getAttributeMapping(any(Object.class))).thenReturn(map);
    when(loadablePlanService.validateLoadablePlan(
            any(LoadableStudy.LoadablePlanDetailsRequest.class),
            any(LoadableStudy.AlgoReply.Builder.class)))
        .thenReturn(LoadableStudy.AlgoReply.newBuilder());
    when(voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(voyageRepository.save(any(Voyage.class))).thenReturn(new Voyage());
    when(loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(loadableStudyRepository.save(any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(loadableStudy);
    when(loadableStudyStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudyStatus()));
    when(commingleCargoRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CommingleCargo()));
    when(cargoNominationRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    when(cargoNominationOperationDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNominationPortDetails()));
    when(loadableStudyPortRotationRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudyPortRotation()));
    when(onBoardQuantityRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.OnBoardQuantity()));
    when(loadableQuantityRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableQuantity()));
    when(jsonTypeRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.JsonType()));
    when(loadableStudyAlgoStatusRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus()));
    when(loadablePlanRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlan()));
    when(loadablePatternRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePattern()));
    when(algoErrorHeadingRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.AlgoErrorHeading()));
    when(algoErrorsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.AlgoErrors()));
    when(loadablePlanConstraintsRespository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanConstraints()));
    when(loadablePlanQuantityRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanQuantity()));
    when(loadablePlanCommingleDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails()));
    when(loadablePatternCargoToppingOffSequenceRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(
                new com.cpdss.loadablestudy.entity.LoadablePatternCargoToppingOffSequence()));
    when(loadablePlanStowageDetailsRespository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails()));
    when(loadablePlanBallastDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails()));
    when(loadablePlanCommingleDetailsPortwiseRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails()));
    when(loadableStudyCommunicationStatusRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudyCommunicationStatus()));
    when(stabilityParameterRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.StabilityParameters()));
    when(loadablePatternCargoDetailsRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails()));
    when(loadablePlanStowageBallastDetailsRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(new com.cpdss.loadablestudy.entity.LoadablePlanStowageBallastDetails()));
    when(synopticalTableLoadicatorDataRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(new com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData()));
    when(cowHistoryRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CowHistory()));
    when(dischargePatternQuantityCargoPortwiseRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(
                new com.cpdss.loadablestudy.entity.DischargePatternQuantityCargoPortwiseDetails()));
    when(loadableStudyStagingService.getAsEntityJson(any(HashMap.class), any(JsonArray.class)))
        .thenReturn(jsonArray);

    loadableStudyCommunicationService.processStagingData(transferStageList, "cloud", "1");
    verify(loadableStudyStagingService).updateStatusCompletedForProcessId(anyString(), anyString());
  }

  private List<DataTransferStage> getDataTransferStageList() {
    List<DataTransferStage> transferStageList = new ArrayList<>();
    DataTransferStage transferStage = new DataTransferStage();
    transferStage.setProcessGroupId("AlgoResult");
    transferStage.setProcessId("1");
    transferStage.setData("1");
    transferStage.setProcessIdentifier("voyage");
    transferStage.setId(1l);
    transferStageList.add(transferStage);
    return transferStageList;
  }
}
