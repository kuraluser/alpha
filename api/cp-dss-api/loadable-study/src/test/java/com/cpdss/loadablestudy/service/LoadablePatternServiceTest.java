/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.domain.CommunicationStatus;
import com.cpdss.loadablestudy.domain.PatternDetails;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
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
  @MockBean private LoadableStudyStagingService loadableStudyStagingService;

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
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean CargoService cargoService;
  @MockBean private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanService;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testIsPatternGeneratedOrConfirmed() {
    LoadableStudy loadableStudy = new LoadableStudy();
    when(this.loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    when(this.loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    try {
      this.loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudy);
      verify(this.loadablePatternRepository)
          .findLoadablePatterns(Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<LoadablePattern> getLoadablePattern() {
    List<LoadablePattern> loadablePatterns = new ArrayList<>();
    LoadablePattern pattern = new LoadablePattern();
    pattern.setLoadableStudy(getLStudy());
    pattern.setId(1l);
    loadablePatterns.add(pattern);
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
    when(this.loadablePatternRepository.findByIdAndIsActive(
            Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    when(this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
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
    loadablePattern.setCaseNumber(1);
    loadablePattern.setLoadableStudy(getLStudy());
    return Optional.of(loadablePattern);
  }

  private LoadableStudy getLStudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setVesselXId(1l);
    loadableStudy.setPlanningTypeXId(1);
    loadableStudy.setPortRotations(getLSPR());
    loadableStudy.setVoyage(getVoyage());
    return loadableStudy;
  }

  private List<LoadablePlanCommingleDetails> getLPCD() {
    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails = new ArrayList<>();
    LoadablePlanCommingleDetails details = new LoadablePlanCommingleDetails();
    details.setTankId(1L);
    details.setPriority(1);
    details.setQuantity("1");
    details.setGrade("1");
    details.setId(1L);
    details.setLoadingOrder(1);
    details.setApi("1");
    loadablePlanCommingleDetails.add(details);
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
    when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    when(loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
            Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyInt()))
        .thenReturn(getLS());
    when(loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
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
    VoyageStatus voyageStatus = new VoyageStatus();
    voyageStatus.setId(3L);
    voyage.setVoyageStatus(voyageStatus);
    return voyage;
  }

  private Optional<LoadableStudy> getLS() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setPortRotations(getLSPR());
    loadableStudy.setName("1");
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setVesselXId(1l);
    return Optional.of(loadableStudy);
  }

  @Test
  void testSaveLoadablePatterns() throws GenericServiceException {
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails> loadablePlanDetails =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder().setId(1L).build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .addAllLoadablePlanDetails(loadablePlanDetails)
            .setHasLodicator(false)
            .setRequestType("LS")
            .setLoadableStudyId(1l)
            .setProcesssId("1")
            .addAlgoErrors(algoErrors)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    doNothing()
        .when(loadableStudyAlgoStatusRepository)
        .updateLoadableStudyAlgoStatus(anyLong(), anyString(), anyBoolean());
    doNothing().when(loadableStudyRepository).updateLoadableStudyStatus(anyLong(), anyLong());
    when(algoService.saveAlgoErrorToDB(
            any(com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.class),
            any(LoadablePattern.class),
            any(LoadableStudy.class),
            anyBoolean()))
        .thenReturn(Arrays.asList(new AlgoErrors()));
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(getLSCS());
    when(loadableStudyStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong()))
        .thenReturn(new JsonArray());
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(EnvoyWriter.WriterReply.newBuilder().build());

    ReflectionTestUtils.setField(loadablePatternService, "enableCommunication", true);
    ReflectionTestUtils.setField(loadablePatternService, "env", "env");

    var algoReply = this.loadablePatternService.saveLoadablePatterns(request, builder);
    assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @CsvSource({"true,LS", "false,DS"})
  void testSaveLoadablePatternsElse(Boolean bool, String str) throws GenericServiceException {
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails> loadablePlanDetails =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails loadablePlanDetails1 =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.newBuilder()
            .setCaseNumber(1)
            .setLoadablePatternId(1l)
            .addAllLoadablePlanPortWiseDetails(
                getPatternAlgoRequest()
                    .getLoadablePlanDetails(0)
                    .getLoadablePlanPortWiseDetailsList())
            .setStabilityParameters(
                com.cpdss.common.generated.LoadableStudy.StabilityParameter.newBuilder()
                    .setAfterDraft("1")
                    .setBendinMoment("1")
                    .setForwardDraft("1")
                    .setHeel("1")
                    .setMeanDraft("1")
                    .setShearForce("1")
                    .setTrim("1")
                    .setAirDraft("1")
                    .setFreeboard("1")
                    .setManifoldHeight("1")
                    .build())
            .addAllConstraints(Arrays.asList("1"))
            .build();
    loadablePlanDetails.add(loadablePlanDetails1);
    com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder().setId(1L).build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .addAllLoadablePlanDetails(loadablePlanDetails)
            .setHasLodicator(bool)
            .setRequestType(str)
            .setLoadableStudyId(1l)
            .setProcesssId("1")
            .addAlgoErrors(algoErrors)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);

    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    when(loadableStudyPortRotationService.getLastPort(
            any(LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(new CargoOperation());
    when(loadablePatternRepository.findOneByLoadableStudyAndCaseNumberAndIsActiveTrue(
            any(LoadableStudy.class), anyInt()))
        .thenReturn(getLP());
    when(loadicatorService.saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class)))
        .thenReturn(Loadicator.LoadicatorReply.newBuilder().build());
    when(algoService.saveAlgoErrorToDB(
            any(com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.class),
            any(LoadablePattern.class),
            any(LoadableStudy.class),
            anyBoolean()))
        .thenReturn(Arrays.asList(new AlgoErrors()));
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(getLSCS());
    when(loadableStudyStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong()))
        .thenReturn(new JsonArray());
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(EnvoyWriter.WriterReply.newBuilder().build());
    when(loadablePlanService.buildLoadablePlanStowageDetails(anyLong()))
        .thenReturn(
            Arrays.asList(
                com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder()
                    .build()));
    doNothing().when(jsonDataService).updateJsonData(anyLong(), anyLong(), anyString());
    doNothing()
        .when(loadableQuantityService)
        .saveLoadableQuantity(
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails.class),
            any(LoadablePattern.class));
    when(commingleCargoRepository
            .findByLoadableStudyXIdAndCargoNomination1IdAndCargoNomination2IdAndIsActiveTrue(
                anyLong(), anyLong(), anyLong()))
        .thenReturn(Optional.of(new CommingleCargo()));
    when(loadablePlanStowageDetailsRespository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadablePlanStowageDetails()));
    when(loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(loadableStudyPortRotation);
    doNothing()
        .when(loadicatorService)
        .saveLodicatorDataForSynoptical(
            any(LoadablePattern.class),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.class),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.class),
            anyString(),
            anyLong());
    CowTypeMaster master = new CowTypeMaster();
    master.setId(1l);
    when(cowTypeMasterRepository.findByCowTypeAndIsActiveTrue(anyString()))
        .thenReturn(Optional.of(master));

    ReflectionTestUtils.setField(loadablePatternService, "enableCommunication", true);
    ReflectionTestUtils.setField(loadablePatternService, "env", "env");

    var algoReply = this.loadablePatternService.saveLoadablePatterns(request, builder);
    assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
  }

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
  void testSavePatternValidateResult1() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .setProcesssId("1")
            .setValidated(false)
            .setLoadablePatternId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    when(this.loadablePatternRepository.findByIdAndIsActive(
            Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(getLSCS());
    when(loadableStudyStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong()))
        .thenReturn(new JsonArray());
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(
            EnvoyWriter.WriterReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(this.loadableStudyCommunicationStatusRepository.save(
            any(LoadableStudyCommunicationStatus.class)))
        .thenReturn(getLSCS().get());

    try {
      var algoReply = this.loadablePatternService.savePatternValidateResult(request, builder);
      assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
    } catch (GenericServiceException | JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSavePatternValidateResultValidated()
      throws GenericServiceException, JsonProcessingException {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .setProcesssId("1")
            .setValidated(true)
            .setHasLodicator(true)
            .setLoadablePatternId(1L)
            .addAllLoadablePlanDetails(getPatternAlgoRequest().getLoadablePlanDetailsList())
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);

    when(this.loadablePatternRepository.findByIdAndIsActive(
            Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    when(loadableStudyPortRotationService.getLastPort(
            any(LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(new CargoOperation());
    doNothing()
        .when(loadableQuantityService)
        .saveLoadableQuantity(
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails.class),
            any(LoadablePattern.class));
    when(commingleCargoRepository
            .findByLoadableStudyXIdAndCargoNomination1IdAndCargoNomination2IdAndIsActiveTrue(
                anyLong(), anyLong(), anyLong()))
        .thenReturn(Optional.of(new CommingleCargo()));
    when(loadablePlanStowageDetailsRespository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadablePlanStowageDetails()));
    when(loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(loadableStudyPortRotation);
    doNothing()
        .when(loadicatorService)
        .saveLodicatorDataForSynoptical(
            any(LoadablePattern.class),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.class),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.class),
            anyString(),
            anyLong());
    CowTypeMaster master = new CowTypeMaster();
    master.setId(1l);
    when(cowTypeMasterRepository.findByCowTypeAndIsActiveTrue(anyString()))
        .thenReturn(Optional.of(master));
    when(loadablePatternRepository.findOneByLoadableStudyAndCaseNumberAndIsActiveTrue(
            any(LoadableStudy.class), anyInt()))
        .thenReturn(getLP());
    when(loadicatorService.saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class)))
        .thenReturn(Loadicator.LoadicatorReply.newBuilder().build());
    when(algoService.saveAlgoErrorToDB(
            any(com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.class),
            any(LoadablePattern.class),
            any(LoadableStudy.class),
            anyBoolean()))
        .thenReturn(Arrays.asList(new AlgoErrors()));
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(getLSCS());
    when(loadableStudyStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong()))
        .thenReturn(new JsonArray());
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(EnvoyWriter.WriterReply.newBuilder().build());
    when(loadablePlanService.buildLoadablePlanStowageDetails(anyLong()))
        .thenReturn(
            Arrays.asList(
                com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder()
                    .build()));
    doNothing().when(jsonDataService).updateJsonData(anyLong(), anyLong(), anyString());

    var algoReply = loadablePatternService.savePatternValidateResult(request, builder);
    assertEquals(SUCCESS, algoReply.getResponseStatus().getStatus());
  }

  @Test
  void testDeleteExistingPlanDetails() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    this.loadablePatternService.deleteExistingPlanDetails(loadablePattern);
    verify(loadablePlanCommingleDetailsPortwiseRepository).deleteByLoadablePatternId(anyLong());
  }

  @Test
  void testGetLoadablePatternList() {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.newBuilder();
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    when(this.loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
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

  @Disabled
  @Test
  void testGenerateLoadablePatterns() {
    com.cpdss.common.generated.LoadableStudy.AlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.AlgoRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.AlgoReply.newBuilder();
    when(loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    when(loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(
            Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getPRIPI());
    when(loadableQuantityRepository.findByLSIdAndPortRotationId(
            Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLQ());
    when(jsonTypeRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
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

  @Test
  void testConfirmPlanStatus() {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .setVoyageId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder();

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());

    loadablePatternService.confirmPlanStatus(request, replyBuilder);
    assertEquals(FAILED, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testConfirmPlanStatusElse() {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .setVoyageId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder()
            .setLoadablePatternStatusId(12l);

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPAS());
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());
    when(loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
            anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(getLP().get()));

    loadablePatternService.confirmPlanStatus(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testConfirmPlanStatusElse2() {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .setVoyageId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder()
            .setLoadablePatternStatusId(12l);
    List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTemps = getLPSDT();
    loadablePlanStowageDetailsTemps.add(new LoadablePlanStowageDetailsTemp());

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPAS());
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(loadablePlanStowageDetailsTemps);
    when(loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
            anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(getLP().get()));

    loadablePatternService.confirmPlanStatus(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testConfirmPlanStatusEmptyStatus() {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .setVoyageId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder()
            .setLoadablePatternStatusId(1l);
    List<LoadablePatternAlgoStatus> loadablePatternAlgoStatuses = new ArrayList<>();

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(loadablePatternAlgoStatuses);
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());
    when(loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
            anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(new LoadablePattern()));

    loadablePatternService.confirmPlanStatus(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
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
    when(loadableStudyStatusRepository.getOne(Mockito.anyLong()))
        .thenReturn(loadableStudy.getLoadableStudyStatus());
    this.loadablePatternService.updateProcessIdForLoadableStudy(
        processId, loadableStudy, loadableStudyStatus, messageId, generatedFromShore);
    Mockito.verify(loadableStudyAlgoStatusRepository)
        .save(Mockito.any(LoadableStudyAlgoStatus.class));
  }

  private Set<LoadableStudyPortRotation> getLSPR() {
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setActive(true);
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotation.setSynopticalTable(getST());
    CargoOperation operation = new CargoOperation();
    operation.setId(1L);
    loadableStudyPortRotation.setOperation(operation);
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    return loadableStudyPortRotations;
  }

  private List<SynopticalTable> getST() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    synopticalTable.setIsActive(false);
    return Arrays.asList(synopticalTable);
  }

  private List<LoadablePatternAlgoStatus> getLPAS() {
    List<LoadablePatternAlgoStatus> loadablePatternAlgoStatuses = new ArrayList<>();
    LoadablePatternAlgoStatus loadablePatternAlgoStatus = new LoadablePatternAlgoStatus();
    loadablePatternAlgoStatus.setId(1L);
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(12L);
    loadablePatternAlgoStatus.setLoadableStudyStatus(loadableStudyStatus);
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
    when(loadablePlanCommingleDetailsRepository.findByIdAndIsActive(
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

  private List<LoadablePlanStowageDetailsTemp> getLPSDT() {
    List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTemps = new ArrayList<>();
    return loadablePlanStowageDetailsTemps;
  }

  @Test
  void testConfirmPlan() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder();
    LoadablePatternService spyService = spy(LoadablePatternService.class);
    when(this.loadablePatternRepository.findByIdAndIsActive(
            Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    when(loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningType(
            Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean(), anyInt()))
        .thenReturn(getLPattern());
    when(loadingPlanService.loadingPlanSynchronization(
            any(LoadingPlanModels.LoadingPlanSyncRequest.class)))
        .thenReturn(
            LoadingPlanModels.LoadingPlanSyncReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getAlgoStatus());
    when(loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(toppingOffSequenceRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(new ArrayList<>());

    Mockito.doNothing().when(voyageService).checkIfDischargingStarted(anyLong(), anyLong());

    ReflectionTestUtils.setField(
        spyService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(spyService, "loadingPlanService", loadingPlanService);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyAlgoStatusRepository", loadableStudyAlgoStatusRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "toppingOffSequenceRepository", toppingOffSequenceRepository);
    ReflectionTestUtils.setField(spyService, "voyageService", voyageService);
    var confirmPlanReply = spyService.confirmPlan(request, replyBuilder);
    assertEquals(SUCCESS, confirmPlanReply.getResponseStatus().getStatus());
  }

  private List<LoadableStudyAlgoStatus> getAlgoStatus() {
    LoadableStudyAlgoStatus algoStatus = new LoadableStudyAlgoStatus();
    algoStatus.setCreatedDateTime(LocalDateTime.now());
    algoStatus.setProcessId("");
    return Arrays.asList(algoStatus);
  }

  @Test
  void testSaveLoadablePatternDetails() throws InvalidProtocolBufferException {
    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
    ObjectMapper mapper = new ObjectMapper();
    String json = JsonFormat.printer().print(getBuilder());

    when(this.loadableStudyCommunicationStatusRepository.findByMessageUUID(Mockito.any()))
        .thenReturn(getLSCS());
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(getLS());

    this.loadablePatternService.saveLoadablePatternDetails(json, builder);
    verify(loadableStudyCommunicationStatusRepository)
        .updateCommunicationStatus(
            CommunicationStatus.COMPLETED.getId(), false, getLS().get().getId());
  }

  private com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder getBuilder() {
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails> loadablePlanDetails =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails loadablePlanDetails1 =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.newBuilder()
            .setCaseNumber(1)
            .build();
    loadablePlanDetails.add(loadablePlanDetails1);
    com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .setId(1L)
            .build();

    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest patternResult =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .addAlgoErrors(algoErrors)
            .addAllLoadablePlanDetails(loadablePlanDetails)
            .build();

    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder load =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder()
            .setLoadablePatternAlgoRequest(patternResult)
            .setLoadicatorResultsRequest(
                com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest.newBuilder()
                    .build());
    return load;
  }

  @Test
  void testSaveLoadablePatternDetailsEmpty() throws InvalidProtocolBufferException {
    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder load =
        getBuilder()
            .setLoadablePatternAlgoRequest(
                com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
                    .addAllLoadablePlanDetails(
                        List.of(
                            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails
                                .newBuilder()
                                .build()))
                    .build());

    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
    String json = JsonFormat.printer().print(load);

    when(this.loadableStudyCommunicationStatusRepository.findByMessageUUID(Mockito.any()))
        .thenReturn(getLSCS());
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(getLS());

    this.loadablePatternService.saveLoadablePatternDetails(json, builder);
    verify(loadableStudyCommunicationStatusRepository)
        .updateCommunicationStatus(
            CommunicationStatus.COMPLETED.getId(), false, getLS().get().getId());
  }

  @Test
  void testSaveLoadablePatternDetailsWithGenericException()
      throws JsonProcessingException, InvalidProtocolBufferException, GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder builder =
        com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
    String json = JsonFormat.printer().print(getBuilder());

    when(this.loadableStudyCommunicationStatusRepository.findByMessageUUID(Mockito.any()))
        .thenReturn(getLSCS());
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    this.loadablePatternService.saveLoadablePatternDetails(json, builder);
    verify(this.loadableStudyRepository).findById(Mockito.anyLong());
  }

  private Optional<LoadableStudyCommunicationStatus> getLSCS() {
    LoadableStudyCommunicationStatus loadableStudyCommunicationStatus =
        new LoadableStudyCommunicationStatus();
    loadableStudyCommunicationStatus.setReferenceId(1L);
    loadableStudyCommunicationStatus.setMessageUUID("1");
    return Optional.of(loadableStudyCommunicationStatus);
  }

  @Test
  void testGetLoadablePatternDetailsJson() throws Exception {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.newBuilder().build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson.newBuilder();

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    doNothing()
        .when(dischargePlanService)
        .buildDischargeablePlanPortWiseDetails(
            any(LoadablePattern.class),
            any(com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest.class));
    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanPortWiseDetails(
            any(LoadablePattern.class),
            any(com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest.class));

    var result = loadablePatternService.getLoadablePatternDetailsJson(request, builder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest
      getPatternAlgoRequest() {
    List<com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails>
        sequenceDetailsList = new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails sequenceDetails =
        com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails.newBuilder()
            .setCargoId(1l)
            .setTankId(1l)
            .setOrderNumber(1)
            .build();
    sequenceDetailsList.add(sequenceDetails);

    List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails>
        cargoDetailsList = new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails cargoDetails =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder()
            .setApi("1")
            .setCargo1Abbreviation("1")
            .setCargo1MT("1")
            .setCargo1Percentage("1")
            .setCargo2Abbreviation("1")
            .setCargo2MT("1")
            .setCargo2Percentage("1")
            .setGrade("1")
            .setQuantity("1")
            .setTankName("1")
            .setTemp("1")
            .setOrderedMT("1")
            .setPriority(1)
            .setLoadingOrder(1)
            .setTankId(1l)
            .setFillingRatio("1")
            .setCorrectionFactor("1")
            .setCorrectedUllage("1")
            .setRdgUllage("1")
            .setCargo2NominationId(1l)
            .setCargo1NominationId(1l)
            .setTankShortName("1")
            .setCommingleColour("1")
            .addAllToppingOffSequences(sequenceDetailsList)
            .build();
    cargoDetailsList.add(cargoDetails);

    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails> stowageDetailsList =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails stowageDetails =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder()
            .setApi("1")
            .setCargoAbbreviation("1")
            .setColorCode("1")
            .setFillingRatio("1")
            .setRdgUllage("1")
            .setTankId(1l)
            .setTankName("1")
            .setWeight("1")
            .setQuantityMT("1")
            .setTemperature("1")
            .setCorrectionFactor("1")
            .setCorrectedUllage("1")
            .setCargoNominationId(1l)
            .setCargoNominationTemperature("1")
            .setOnboard("1")
            .setMaxTankVolume("1")
            .build();
    stowageDetailsList.add(stowageDetails);

    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails> ballastDetailsList =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails ballastDetails =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder()
            .setMetricTon("1")
            .setPercentage("1")
            .setSg("1")
            .setTankName("1")
            .setTankId(1l)
            .setRdgLevel("1")
            .setCorrectedLevel("1")
            .setCorrectionFactor("1")
            .setMaxTankVolume("1")
            .setVolume("1")
            .build();
    ballastDetailsList.add(ballastDetails);
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails quantityCargoDetails =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails.newBuilder()
            .setCargoId(1l)
            .setCargoNominationId(1l)
            .setCargoAbbreviation("1")
            .setEstimatedAPI("1")
            .setEstimatedTemp("1")
            .setPriority(1)
            .setColorCode("1")
            .setDischargeMT("1")
            .setOrderedQuantity("1")
            .setSlopQuantity("1")
            .setLoadingOrder(1)
            .setDifferencePercentage("1")
            .setTimeRequiredForDischarging("1")
            .setDischargingRate("1")
            .setCargoNominationTemperature("1")
            .addAllCowDetails(
                Arrays.asList(
                    com.cpdss.common.generated.LoadableStudy.COWDetail.newBuilder()
                        .setWashType("1")
                        .setTankId(1l)
                        .setShortName("1")
                        .build()))
            .addAllToppingOffSequences(sequenceDetailsList)
            .build();

    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails> portWiseDetailsList =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails portWiseDetails =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails.newBuilder()
            .setPortId(1l)
            .setDepartureCondition(
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder()
                    .addAllLoadableQuantityCommingleCargoDetails(cargoDetailsList)
                    .addAllLoadablePlanStowageDetails(stowageDetailsList)
                    .addAllLoadablePlanBallastDetails(ballastDetailsList)
                    .addAllLoadableQuantityCargoDetails(Arrays.asList(quantityCargoDetails))
                    .build())
            .setPortRotationId(1l)
            .setArrivalCondition(
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder()
                    .addAllLoadableQuantityCommingleCargoDetails(cargoDetailsList)
                    .addAllLoadablePlanStowageDetails(stowageDetailsList)
                    .addAllLoadablePlanBallastDetails(ballastDetailsList)
                    .addAllLoadableQuantityCargoDetails(Arrays.asList(quantityCargoDetails))
                    .build())
            .build();
    portWiseDetailsList.add(portWiseDetails);

    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails> planDetailsList =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails planDetails =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails.newBuilder()
            .addAllLoadablePlanPortWiseDetails(portWiseDetailsList)
            .build();
    planDetailsList.add(planDetails);

    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.newBuilder()
            .setProcesssId("1")
            .setValidated(true)
            .setHasLodicator(false)
            .setLoadablePatternId(1L)
            .addAllLoadablePlanDetails(planDetailsList)
            .build();
    return request;
  }

  @Test
  void testfetchSavedPatternFromDB() {
    PatternDetails details = new PatternDetails();
    LoadablePatternCargoToppingOffSequence sequence = new LoadablePatternCargoToppingOffSequence();
    LoadablePlanStowageDetails stowageDetails = new LoadablePlanStowageDetails();
    LoadablePlanBallastDetails ballastDetails = new LoadablePlanBallastDetails();
    LoadablePlanComminglePortwiseDetails portwiseDetails =
        new LoadablePlanComminglePortwiseDetails();
    LoadablePatternCargoDetails cargoDetails = new LoadablePatternCargoDetails();
    LoadablePlanStowageBallastDetails stowageBallastDetails =
        new LoadablePlanStowageBallastDetails();
    SynopticalTableLoadicatorData loadicatorData = new SynopticalTableLoadicatorData();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1l);

    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getPlanQuantityList());
    when(toppingOffSequenceRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(Arrays.asList(sequence));
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(Arrays.asList(getOLPCD().get()));
    when(loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(Arrays.asList(stowageDetails));
    when(loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(Arrays.asList(ballastDetails));
    when(loadablePlanCommingleDetailsPortwiseRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(portwiseDetails));
    when(loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(cargoDetails));
    when(loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(stowageBallastDetails));
    when(synopticalTableLoadicatorDataRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(loadicatorData));
    when(synopticalTableRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(synopticalTable));

    loadablePatternService.fetchSavedPatternFromDB(details, getLoadablePattern().get(0));
    assertEquals(1l, details.getSynopticalTableDtoList().get(0).getId());
  }

  @Test
  void testGetVesselTankDetailsByTankIds() {
    LoadablePatternService spyService = spy(LoadablePatternService.class);
    VesselInfo.VesselTankRequest request = VesselInfo.VesselTankRequest.newBuilder().build();
    VesselInfo.VesselTankResponse response =
        VesselInfo.VesselTankResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(response);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselTankDetailsByTankIds(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanks() {
    LoadablePatternService spyService = spy(LoadablePatternService.class);
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    VesselInfo.VesselReply response =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(response);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselTanks(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private List<LoadablePlanQuantity> getPlanQuantityList() {
    List<LoadablePlanQuantity> quantityList = new ArrayList<>();
    LoadablePlanQuantity quantity = new LoadablePlanQuantity();
    quantity.setMaxTolerence("1");
    quantity.setMinTolerence("1");
    quantity.setPriority(1);
    quantity.setLoadableMt("1");
    quantity.setOrderQuantity(new BigDecimal(1));
    quantity.setCargoAbbreviation("1");
    quantity.setCargoColor("1");
    quantity.setLoadingOrder(1);
    quantity.setEstimatedApi(new BigDecimal(1));
    quantity.setCargoNominationTemperature(new BigDecimal(1));
    quantityList.add(quantity);
    return quantityList;
  }

  private VesselInfo.VesselReply getVesselReply() {
    List<VesselInfo.VesselTankDetail> tankDetailList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankCategoryId(1l)
            .setTankPositionCategory("1")
            .build();
    tankDetailList.add(tankDetail);

    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVesselTanks(tankDetailList)
            .build();
    return vesselReply;
  }

  private List<StabilityParameters> getParametersList() {
    List<StabilityParameters> parametersList = new ArrayList<>();
    StabilityParameters parameters = new StabilityParameters();
    parameters.setAftDraft("1");
    parameters.setBendingMoment("1");
    parameters.setFwdDraft("1");
    parameters.setHeal("1");
    parameters.setMeanDraft("1");
    parameters.setShearingForce("1");
    parameters.setTrimValue("1");
    parametersList.add(parameters);
    return parametersList;
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testGetLoadablePatternDetails(Integer i) throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.newBuilder();

    List<LoadablePlanConstraints> constraintsList = new ArrayList<>();
    LoadablePlanConstraints constraints = new LoadablePlanConstraints();
    constraints.setConstraintsData("1");
    constraintsList.add(constraints);

    List<LoadablePlanStowageDetails> stowageDetailsList = new ArrayList<>();
    LoadablePlanStowageDetails stowageDetails = new LoadablePlanStowageDetails();
    stowageDetails.setTankId(1l);
    stowageDetailsList.add(stowageDetails);

    List<LoadablePlanBallastDetails> loadablePlanBallastDetails = new ArrayList<>();
    List<com.cpdss.common.generated.LoadableStudy.TankList> tankLists = new ArrayList<>();

    VesselInfo.VesselTankResponse tankResponse =
        VesselInfo.VesselTankResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();

    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanStowageCargoDetails(
            anyList(),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder.class),
            any(VesselInfo.VesselTankResponse.class));
    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanQuantity(
            anyList(), any(com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder.class));
    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanCommingleDetails(
            anyList(), any(com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder.class));
    when(loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(loadablePlanBallastDetails);
    when(this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsInAndIsActive(
            any(), anyBoolean()))
        .thenReturn(getLPSDT());
    doNothing()
        .when(loadablePlanService)
        .buildBallastGridDetails(
            anyList(),
            anyList(),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder.class));
    when(loadableStudyService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(onHandQuantityService.groupTanks(anyList())).thenReturn(tankLists);
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(tankResponse);
    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    when(loadablePlanService.validateLoadableStudyForConfimPlan(any(LoadableStudy.class)))
        .thenReturn(true);
    when(loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
            Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPattern());
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPAS());
    if (i == 1) {
      List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTemps = getLPSDT();
      loadablePlanStowageDetailsTemps.add(new LoadablePlanStowageDetailsTemp());

      when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
              any(LoadablePattern.class), anyBoolean()))
          .thenReturn(loadablePlanStowageDetailsTemps);
    } else {
      when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
              any(LoadablePattern.class), anyBoolean()))
          .thenReturn(getLPSDT());
    }
    when(loadablePlanConstraintsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(constraintsList);
    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getPlanQuantityList());
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPCD());
    when(loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(stowageDetailsList);
    when(stabilityParameterRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getParametersList());

    ReflectionTestUtils.setField(
        loadablePatternService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = loadablePatternService.getLoadablePatternDetails(request, builder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    assertEquals(true, builder.getLoadablePattern(0).getValidated());
  }

  @Test
  void testGetLoadablePatternDetails() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.newBuilder()
            .setLoadableStudyId(1L)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.newBuilder();

    List<LoadablePlanConstraints> constraintsList = new ArrayList<>();
    LoadablePlanConstraints constraints = new LoadablePlanConstraints();
    constraints.setConstraintsData("1");
    constraintsList.add(constraints);

    List<LoadablePlanStowageDetails> stowageDetailsList = new ArrayList<>();
    LoadablePlanStowageDetails stowageDetails = new LoadablePlanStowageDetails();
    stowageDetails.setTankId(1l);
    stowageDetailsList.add(stowageDetails);

    List<LoadablePlanBallastDetails> loadablePlanBallastDetails = new ArrayList<>();
    List<com.cpdss.common.generated.LoadableStudy.TankList> tankLists = new ArrayList<>();

    VesselInfo.VesselTankResponse tankResponse =
        VesselInfo.VesselTankResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();

    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanStowageCargoDetails(
            anyList(),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder.class),
            any(VesselInfo.VesselTankResponse.class));
    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanQuantity(
            anyList(), any(com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder.class));
    doNothing()
        .when(loadablePlanService)
        .buildLoadablePlanCommingleDetails(
            anyList(), any(com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder.class));
    when(loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(loadablePlanBallastDetails);
    when(this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsInAndIsActive(
            any(), anyBoolean()))
        .thenReturn(getLPSDT());
    doNothing()
        .when(loadablePlanService)
        .buildBallastGridDetails(
            anyList(),
            anyList(),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder.class));
    when(loadableStudyService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(onHandQuantityService.groupTanks(anyList())).thenReturn(tankLists);
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(tankResponse);
    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    when(loadablePlanService.validateLoadableStudyForConfimPlan(any(LoadableStudy.class)))
        .thenReturn(true);
    when(loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
            Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPattern());
    List<LoadablePatternAlgoStatus> loadablePatternAlgoStatuses = new ArrayList<>();

    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(loadablePatternAlgoStatuses);
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());

    when(loadablePlanConstraintsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(constraintsList);
    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getPlanQuantityList());
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPCD());
    when(loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(stowageDetailsList);
    when(stabilityParameterRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getParametersList());

    ReflectionTestUtils.setField(
        loadablePatternService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = loadablePatternService.getLoadablePatternDetails(request, builder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    assertEquals(true, builder.getLoadablePattern(0).getValidated());
  }
}
