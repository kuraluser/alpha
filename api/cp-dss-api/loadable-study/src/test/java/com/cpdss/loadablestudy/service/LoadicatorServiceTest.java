/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(classes = {LoadicatorService.class})
public class LoadicatorServiceTest {

  @Autowired private LoadicatorService loadicatorService;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean JsonTypeRepository jsonTypeRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @MockBean JsonDataRepository jsonDataRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private LoadableStudyService loadableStudyService;
  @MockBean private JsonDataService jsonDataService;
  @MockBean private CommunicationService communicationService;
  @MockBean private AlgoService algoService;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testSaveLodicatorDataForSynoptical() {

    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    LoadableStudy.LoadablePlanDetailsReply arrivalCondition =
        LoadableStudy.LoadablePlanDetailsReply.newBuilder()
            .setStabilityParameter(
                LoadableStudy.StabilityParameter.newBuilder()
                    .setForwardDraft("1")
                    .setMeanDraft("1")
                    .setTrim("1")
                    .setBendinMoment("1")
                    .setShearForce("1")
                    .setAfterDraft("1")
                    .build())
            .build();
    LoadableStudy.LoadablePlanDetails lpd = LoadableStudy.LoadablePlanDetails.newBuilder().build();
    String portType = "1";
    Long portRotationId = 1L;
    Mockito.when(loadableStudyPortRotationRepository.getOne(Mockito.anyLong()))
        .thenReturn(getLSPortRotation());
    Mockito.when(
            synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getSynopticalTable());
    this.loadicatorService.saveLodicatorDataForSynoptical(
        loadablePattern, arrivalCondition, lpd, portType, portRotationId);
  }

  private LoadableStudyPortRotation getLSPortRotation() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    return loadableStudyPortRotation;
  }

  private Optional<SynopticalTable> getSynopticalTable() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    return Optional.of(synopticalTable);
  }

  // need grpc
  @Test
  void testSaveLoadicatorInfo() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudyEntity.setId(1L);
    loadableStudyEntity.setVesselXId(1L);
    loadableStudyEntity.setVoyage(getVoyage());
    String processId = "1";
    Long patternId = 1L;
    List<LoadablePattern> loadablePatternsList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePatternsList.add(loadablePattern);
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdInAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLPCD());
    Mockito.when(
            this.loadablePlanCommingleDetailsPortwiseRepository
                .findByLoadablePatternIdInAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLPCPD());
    Mockito.when(
            this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdInAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLPBD());
    Mockito.when(
            this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLST());
    // Mockito.when(this.onHandQuantityRepository.findByLoadableStudyAndIsActive(Mockito.any(),Mockito.anyBoolean())).thenReturn();

  }

  private Optional<LoadablePattern> getLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    return Optional.of(loadablePattern);
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> getLLPCD() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails =
        new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails loadablePatternCargoDetails1 =
        new LoadablePatternCargoDetails();
    loadablePatternCargoDetails.add(loadablePatternCargoDetails1);
    return loadablePatternCargoDetails;
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails> getLLPCPD() {
    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        loadablePlanComminglePortwiseDetails = new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails
        loadablePlanComminglePortwiseDetails1 = new LoadablePlanComminglePortwiseDetails();
    loadablePlanComminglePortwiseDetails.add(loadablePlanComminglePortwiseDetails1);
    return loadablePlanComminglePortwiseDetails;
  }

  private List<LoadablePlanStowageBallastDetails> getLLPBD() {
    List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetails = new ArrayList<>();
    LoadablePlanStowageBallastDetails loadablePlanStowageBallastDetails1 =
        new LoadablePlanStowageBallastDetails();
    loadablePlanStowageBallastDetails.add(loadablePlanStowageBallastDetails1);
    return loadablePlanStowageBallastDetails;
  }

  private List<SynopticalTable> getLST() {
    List<SynopticalTable> synopticalTables = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTables.add(synopticalTable);
    return synopticalTables;
  }

  private List<OnHandQuantity> getLOHQ() {
    List<OnHandQuantity> onHandQuantities = new ArrayList<>();
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    onHandQuantities.add(onHandQuantity);
    return onHandQuantities;
  }

  private Voyage getVoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    return voyage;
  }

  @Test
  void testSaveLoadicatorResults() {
    List<LoadableStudy.LodicatorResultDetails> lodicatorResultDetails = new ArrayList<>();
    LoadableStudy.LodicatorResultDetails lodicatorResultDetails1 =
        LoadableStudy.LodicatorResultDetails.newBuilder()
            .setBlindSector("1")
            .setCalculatedDraftAftPlanned("1")
            .setCalculatedDraftFwdPlanned("1")
            .setCalculatedTrimPlanned("1")
            .setCalculatedDraftMidPlanned("1")
            .setDeflection("1")
            .setList("1")
            .setPortId(1L)
            .setOperationId(1L)
            .build();
    lodicatorResultDetails.add(lodicatorResultDetails1);
    List<LoadableStudy.LoadicatorPatternDetailsResults> loadicatorPatternDetailsResults =
        new ArrayList<>();
    LoadableStudy.LoadicatorPatternDetailsResults loadicatorPatternDetailsResults1 =
        LoadableStudy.LoadicatorPatternDetailsResults.newBuilder()
            .setLoadablePatternId(1L)
            .addLoadicatorResultDetails(lodicatorResultDetails1)
            .build();
    loadicatorPatternDetailsResults.add(loadicatorPatternDetailsResults1);
    LoadableStudy.LoadicatorResultsRequest request =
        LoadableStudy.LoadicatorResultsRequest.newBuilder()
            .setLoadableStudyId(1L)
            .addLoadicatorResultsPatternWise(loadicatorPatternDetailsResults1)
            .build();
    LoadableStudy.AlgoReply.Builder replyBuilder = LoadableStudy.AlgoReply.newBuilder();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    var algoReply = this.loadicatorService.saveLoadicatorResults(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    return Optional.of(loadableStudy);
  }

  @Test
  void testSaveLoadicatorResults1() {
    List<LoadableStudy.LodicatorResultDetails> lodicatorResultDetails = new ArrayList<>();
    LoadableStudy.LodicatorResultDetails lodicatorResultDetails1 =
        LoadableStudy.LodicatorResultDetails.newBuilder()
            .setBlindSector("1")
            .setCalculatedDraftAftPlanned("1")
            .setCalculatedDraftFwdPlanned("1")
            .setCalculatedTrimPlanned("1")
            .setCalculatedDraftMidPlanned("1")
            .setDeflection("1")
            .setList("1")
            .setPortId(1L)
            .setOperationId(1L)
            .build();
    lodicatorResultDetails.add(lodicatorResultDetails1);
    List<LoadableStudy.LoadicatorPatternDetailsResults> loadicatorPatternDetailsResults =
        new ArrayList<>();
    LoadableStudy.LoadicatorPatternDetailsResults loadicatorPatternDetailsResults1 =
        LoadableStudy.LoadicatorPatternDetailsResults.newBuilder()
            .setLoadablePatternId(1L)
            .addLoadicatorResultDetails(lodicatorResultDetails1)
            .build();
    loadicatorPatternDetailsResults.add(loadicatorPatternDetailsResults1);
    LoadableStudy.LoadicatorResultsRequest request =
        LoadableStudy.LoadicatorResultsRequest.newBuilder()
            .setLoadableStudyId(1L)
            .addLoadicatorResultsPatternWise(loadicatorPatternDetailsResults1)
            .build();
    this.loadicatorService.saveLoadicatorResults(request);
    Mockito.verify(synopticalTableLoadicatorDataRepository)
        .save(Mockito.any(SynopticalTableLoadicatorData.class));
  }
}
