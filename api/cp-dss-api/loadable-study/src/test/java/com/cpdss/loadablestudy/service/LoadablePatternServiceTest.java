/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.build.env = ship")
@TestPropertySource(properties = "cpdss.communication.enable = true")
@SpringJUnitConfig(classes = {LoadablePatternService.class})
public class LoadablePatternServiceTest {

  @Autowired private LoadablePatternService loadablePatternService;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadablePlanService loadablePlanService;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private JsonDataRepository jsonDataRepository;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @MockBean private LoadableQuantityService loadableQuantityService;
  @MockBean private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @MockBean
  private DischargePatternQuantityCargoPortwiseRepository
      dischargePatternQuantityCargoPortwiseRepository;

  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private StabilityParameterRepository stabilityParameterRepository;
  @MockBean private LoadicatorService loadicatorService;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private AlgoService algoService;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private VoyageService voyageService;
  @MockBean private CargoNominationService cargoNominationService;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadableStudyService loadableStudyService;
  @MockBean JsonDataService jsonDataService;
  @MockBean CommunicationService communicationService;
  @MockBean private OnHandQuantityService onHandQuantityService;
  @MockBean private CowTypeMasterRepository cowTypeMasterRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;
  @MockBean private DischargePlanService dischargePlanService;
  @MockBean private CommingleCargoRepository commingleCargoRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private CargoService cargoService;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testIsPatternGeneratedOrConfirmed() {
    LoadableStudy loadableStudy = new LoadableStudy();
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    try {
      this.loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudy);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<LoadablePattern> getLoadablePattern() {
    List<LoadablePattern> loadablePatterns = new ArrayList<>();
    return loadablePatterns;
  }

  @Test
  void testGetLoadableCommingleByPatternId() {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.newBuilder()
            .setLoadablePatternId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPCD());
    try {
      this.loadablePatternService.getLoadableCommingleByPatternId(request, builder);
      assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<LoadablePattern> getLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setLoadableStudy(getLStudy());
    return Optional.of(loadablePattern);
  }

  private LoadableStudy getLStudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVoyage(getVoyage());
    return loadableStudy;
  }

  private List<LoadablePlanCommingleDetails> getLPCD() {
    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails = new ArrayList<>();
    LoadablePlanCommingleDetails loadablePlanCommingleDetails1 = new LoadablePlanCommingleDetails();
    loadablePlanCommingleDetails1.setTankId(1L);
    loadablePlanCommingleDetails.add(loadablePlanCommingleDetails1);
    return loadablePlanCommingleDetails;
  }

  @Test
  void testGetLoadablePatternByVoyageAndStatus() {
    com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.newBuilder()
            .setVoyageId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.newBuilder();
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    Mockito.when(
            loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
                Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyInt()))
        .thenReturn(getLS());
    Mockito.when(
            loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
                Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    try {
      var loadablePatternConfirmedReply =
          this.loadablePatternService.getLoadablePatternByVoyageAndStatus(request, builder);
      assertEquals(SUCCESS, loadablePatternConfirmedReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Voyage getVoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    return voyage;
  }

  private Optional<LoadableStudy> getLS() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setPortRotations(getLSPR());
    loadableStudy.setName("1");
    loadableStudy.setVoyage(getVoyage());
    return Optional.of(loadableStudy);
  }

  //    @Test
  //    void testSaveLoadablePatterns() {
  //        List<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails> loadablePlanDetails =
  //                new ArrayList<>();
  //        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails loadablePlanDetails1 =
  //                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.newBuilder()
  //                        .setCaseNumber(1)
  //                        .build();
  //        loadablePlanDetails.add(loadablePlanDetails1);
  //        com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
  //
  // com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder().setId(1L).build();
  //        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
  //                com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
  //                        .addAllLoadablePlanDetails(loadablePlanDetails)
  //                        .setHasLodicator(true)
  //                        .setRequestType("1")
  //                        .addAlgoErrors(algoErrors)
  //                        .build();
  //        com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder =
  //                com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
  //        Mockito.when(
  //                        this.loadableStudyRepository.findByIdAndIsActive(
  //                                Mockito.anyLong(), Mockito.anyBoolean()))
  //                .thenReturn(getLS());
  //        try {
  //            var algoReply = this.loadablePatternService.saveLoadablePatterns(request, builder);
  //            assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

  @Test
  void testSaveLoadablePlanCommingleCargo() {
    List<com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails>
        cargoToppingOffSequenceDetails = new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails
        toppingOffSequenceDetails =
            com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails.newBuilder()
                .setCargoId(1L)
                .setTankId(1L)
                .setOrderNumber(1)
                .build();
    cargoToppingOffSequenceDetails.add(toppingOffSequenceDetails);
    List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails>
        loadableQuantityCommingleCargoDetailsList = new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails
        loadableQuantityCommingleCargoDetails =
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails
                .newBuilder()
                .addAllToppingOffSequences(cargoToppingOffSequenceDetails)
                .setTankShortName("1")
                .setCorrectedUllage("1")
                .setCorrectionFactor("1")
                .setRdgUllage("1")
                .setSlopQuantity("1")
                .setTimeRequiredForLoading("1")
                .setCargo2NominationId(1L)
                .setQuantity("1")
                .setTankName("1")
                .setTemp("1")
                .setOrderedMT("1")
                .setPriority(1)
                .setLoadingOrder(1)
                .setTankId(1L)
                .setFillingRatio("1")
                .setApi("1")
                .setCargo1Abbreviation("1")
                .setCargo1MT("1")
                .setCargo1Percentage("1")
                .setCargo2Abbreviation("1")
                .setCargo2MT("1")
                .setCargo2Percentage("1")
                .build();
    loadableQuantityCommingleCargoDetailsList.add(loadableQuantityCommingleCargoDetails);
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setLoadableStudy(getLStudy());
    this.loadablePatternService.saveLoadablePlanCommingleCargo(
        loadableQuantityCommingleCargoDetailsList, loadablePattern);
    Mockito.verify(loadablePlanCommingleDetailsRepository).save(Mockito.any());
    Mockito.verify(toppingOffSequenceRepository).save(Mockito.any());
  }

  @Test
  void testSavePatternValidateResult1() {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .setProcesssId("1")
            .setValidated(false)
            .setLoadablePatternId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    try {
      var algoReply = this.loadablePatternService.savePatternValidateResult(request, builder);
      assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
    } catch (GenericServiceException | JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDeleteExistingPlanDetails() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    this.loadablePatternService.deleteExistingPlanDetails(loadablePattern);
  }

  @Test
  void testGetLoadablePatternList() {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.newBuilder();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    Mockito.when(
            this.loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPattern());
    try {
      var loadablePatternReply =
          this.loadablePatternService.getLoadablePatternList(request, replyBuilder);
      assertEquals(SUCCESS, loadablePatternReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<LoadablePattern> getLPattern() {
    List<LoadablePattern> loadablePatterns = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setCreatedDate(LocalDate.now());
    loadablePattern.setLoadableStudyStatus(1L);
    loadablePattern.setCaseNumber(1);
    loadablePattern.setLoadableStudy(getLStudy());
    loadablePatterns.add(loadablePattern);
    return loadablePatterns;
  }

  // doubt// completed with doubt
  @Test
  void testGenerateLoadablePatterns() {
    com.cpdss.common.generated.LoadableStudy.AlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    Mockito.when(
            loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getPRIPI());
    Mockito.when(
            loadableQuantityRepository.findByLSIdAndPortRotationId(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLQ());
    Mockito.when(jsonTypeRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getJson());
    try {
      var algoReply = this.loadablePatternService.generateLoadablePatterns(request, replyBuilder);
      assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<PortRotationIdAndPortId> getPRIPI() {
    List<PortRotationIdAndPortId> portIds = new ArrayList<>();
    PortRotationIdAndPortId portRotationIdAndPortId = new PortRotation();
    portIds.add(portRotationIdAndPortId);
    return portIds;
  }

  private class PortRotation implements PortRotationIdAndPortId {
    @Override
    public Long getId() {
      return 1L;
    }

    @Override
    public Long getPortId() {
      return null;
    }
  }

  private Optional<LoadableQuantity> getLQ() {
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId(1L);
    return Optional.of(loadableQuantity);
  }

  private Optional<JsonType> getJson() {
    JsonType jsonType = new JsonType();
    jsonType.setId(1L);
    return Optional.of(jsonType);
  }

  @Test
  void testUpdateProcessIdForLoadableStudy() {
    String processId = "1";
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setVesselXId(1L);
    Long loadableStudyStatus = 1L;
    String messageId = "1";
    boolean generatedFromShore = true;
    Mockito.when(loadableStudyStatusRepository.getOne(Mockito.anyLong()))
        .thenReturn(loadableStudy.getLoadableStudyStatus());
    this.loadablePatternService.updateProcessIdForLoadableStudy(
        processId, loadableStudy, loadableStudyStatus, messageId, generatedFromShore);
    Mockito.verify(loadableStudyAlgoStatusRepository)
        .save(Mockito.any(LoadableStudyAlgoStatus.class));
  }
  // need grpc
  @Test
  void testGetLoadablePatternDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.newBuilder();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    Mockito.when(
            loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPattern());
    Mockito.when(
            loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPAS());
  }

  private Set<LoadableStudyPortRotation> getLSPR() {
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    return loadableStudyPortRotations;
  }

  private List<LoadablePatternAlgoStatus> getLPAS() {
    List<LoadablePatternAlgoStatus> loadablePatternAlgoStatuses = new ArrayList<>();
    LoadablePatternAlgoStatus loadablePatternAlgoStatus = new LoadablePatternAlgoStatus();
    loadablePatternAlgoStatus.setId(1L);
    loadablePatternAlgoStatus.setLoadableStudyStatus(getLSS());
    loadablePatternAlgoStatuses.add(loadablePatternAlgoStatus);
    return loadablePatternAlgoStatuses;
  }

  private LoadableStudyStatus getLSS() {
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(1L);
    return loadableStudyStatus;
  }

  @Test
  void testGetLoadablePatternCommingleDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest.newBuilder()
            .setLoadablePatternCommingleDetailsId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply.newBuilder();
    Mockito.when(
            loadablePlanCommingleDetailsRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLPCD());
    var loadablePatternCommingleDetailsReply =
        this.loadablePatternService.getLoadablePatternCommingleDetails(request, builder);
    assertEquals(SUCCESS, loadablePatternCommingleDetailsReply.getResponseStatus().getStatus());
  }

  private Optional<LoadablePlanCommingleDetails> getOLPCD() {
    LoadablePlanCommingleDetails loadablePlanCommingleDetails = new LoadablePlanCommingleDetails();
    loadablePlanCommingleDetails.setTankId(1L);
    loadablePlanCommingleDetails.setApi("1");
    loadablePlanCommingleDetails.setCargo1Abbreviation("1");
    loadablePlanCommingleDetails.setCargo2Abbreviation("1");
    loadablePlanCommingleDetails.setCargo1Percentage("1");
    loadablePlanCommingleDetails.setCargo2Percentage("1");
    loadablePlanCommingleDetails.setCargo1Mt("1");
    loadablePlanCommingleDetails.setCargo2Mt("1");
    loadablePlanCommingleDetails.setGrade("1");
    loadablePlanCommingleDetails.setQuantity("1");
    loadablePlanCommingleDetails.setTankName("1");
    loadablePlanCommingleDetails.setTemperature("1");
    loadablePlanCommingleDetails.setId(1L);
    return Optional.of(loadablePlanCommingleDetails);
  }

  @Test
  void testConfirmPlanStatus() {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .setVoyageId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());

    Mockito.when(
            loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPAS());
    Mockito.when(
            stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPSDT());
    Mockito.when(
            loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPattern());
    var confirmPlanReply = this.loadablePatternService.confirmPlan(request, replyBuilder);
    assertEquals(SUCCESS, confirmPlanReply.getResponseStatus().getStatus());
  }

  private List<LoadablePlanStowageDetailsTemp> getLPSDT() {
    List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTemps = new ArrayList<>();
    return loadablePlanStowageDetailsTemps;
  }

  @Test
  void testconfirmPlan() {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPattern());
    var confirmPlanReply = this.loadablePatternService.confirmPlan(request, replyBuilder);
    assertEquals(SUCCESS, confirmPlanReply.getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadablePatternDetails() {
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails> loadablePlanDetails =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails loadablePlanDetails1 =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.newBuilder()
            .setCaseNumber(1)
            .build();
    loadablePlanDetails.add(loadablePlanDetails1);
    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication responseCommunication =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder()
            .setMessageId("1")
            .build();
    String patternResultJson = "1";
    com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .setId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder load =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest patternResult =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .addAlgoErrors(algoErrors)
            .addAllLoadablePlanDetails(loadablePlanDetails)
            .build();
    Mockito.when(this.loadableStudyCommunicationStatusRepository.findByMessageUUID(Mockito.any()))
        .thenReturn(getLSCS());
    Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(getLS());
    this.loadablePatternService.saveLoadablePatternDetails(patternResultJson, load);
  }

  private Optional<LoadableStudyCommunicationStatus> getLSCS() {
    LoadableStudyCommunicationStatus loadableStudyCommunicationStatus =
        new LoadableStudyCommunicationStatus();
    loadableStudyCommunicationStatus.setReferenceId(1L);
    return Optional.of(loadableStudyCommunicationStatus);
  }
}
