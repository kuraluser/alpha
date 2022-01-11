/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.DATE_FORMAT;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {VoyageService.class})
@TestPropertySource(properties = "cpdss.voyage.validation.enable = false")
public class VoyageServiceTest {

  @Autowired private VoyageService voyageService;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private CargoService cargoService;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private VoyageStatusRepository voyageStatusRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private VoyageHistoryRepository voyageHistoryRepository;
  @MockBean private CargoHistoryRepository cargoHistoryRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private ApiTempHistoryRepository apiTempHistoryRepository;
  @MockBean private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private SynopticService synopticService;
  @MockBean private CargoNominationService cargoNominationService;
  @MockBean private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanService;
  @MockBean private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub dischargePlanService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  @Test
  void testFetchActiveVoyageByVesselId() {
    LoadableStudy.ActiveVoyage.Builder builder = LoadableStudy.ActiveVoyage.newBuilder();
    Long vesselId = 1L;
    Long activeStatus = 1L;
    Mockito.when(
            this.voyageRepository.findActiveVoyagesByVesselId(Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLV());
    Mockito.when(cargoNominationService.getCargoNominations(Mockito.anyLong()))
        .thenReturn(getLCN());
    Mockito.when(
            loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLP());
    this.voyageService.fetchActiveVoyageByVesselId(builder, vesselId, activeStatus);
    assertEquals(1L, builder.getId());
  }

  @Test
  void testFetchActiveVoyageByVesselIdConfirmedLs() {
    LoadableStudy.ActiveVoyage.Builder builder = LoadableStudy.ActiveVoyage.newBuilder();
    Long vesselId = 1L;
    Long activeStatus = 1L;
    Voyage voyage = getLV().get(0);
    Set<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies = new HashSet<>();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setLoadableStudyStatus(getLSS());
    loadableStudy.setId(1L);
    loadableStudy.setPlanningTypeXId(1);
    loadableStudy.setName("ACTIVE");
    loadableStudy.setDraftMark(new BigDecimal(1));
    loadableStudy.setCreatedDateTime(LocalDateTime.now());
    loadableStudy.setPortRotations(getPR());
    loadableStudies.add(loadableStudy);
    voyage.setLoadableStudies(loadableStudies);
    Mockito.when(
            this.voyageRepository.findActiveVoyagesByVesselId(Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(Arrays.asList(voyage));
    Mockito.when(cargoNominationService.getCargoNominations(Mockito.anyLong()))
        .thenReturn(getLCN());
    Mockito.when(
            loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLP());
    this.voyageService.fetchActiveVoyageByVesselId(builder, vesselId, activeStatus);
    assertEquals(1L, builder.getId());
  }

  private List<Voyage> getLV() {
    List<Voyage> voyages = new ArrayList<>();
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setVesselXId(1L);
    voyage.setActualEndDate(LocalDateTime.now());
    voyage.setActualStartDate(LocalDateTime.now());
    voyage.setVoyageStartDate(LocalDateTime.now());
    voyage.setVoyageEndDate(LocalDateTime.now());
    voyage.setVoyageNo("1");
    voyage.setVoyageStatus(getVS());
    voyage.setLoadableStudies(getLS());
    voyages.add(voyage);
    return voyages;
  }

  private VoyageStatus getVS() {
    VoyageStatus voyageStatus = new VoyageStatus();
    voyageStatus.setName("ACTIVE");
    voyageStatus.setId(3L);
    return voyageStatus;
  }

  private Set<com.cpdss.loadablestudy.entity.LoadableStudy> getLS() {
    Set<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies = new HashSet<>();
    loadableStudies.add(getLoadableStudy());
    return loadableStudies;
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLoadableStudy() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setLoadableStudyStatus(getLSS());
    loadableStudy.setId(1L);
    loadableStudy.setPlanningTypeXId(2);
    loadableStudy.setName("ACTIVE");
    loadableStudy.setDraftMark(new BigDecimal(1));
    loadableStudy.setCreatedDateTime(LocalDateTime.now());
    loadableStudy.setPortRotations(getPR());
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    loadableStudy.setVoyage(voyage);
    loadableStudy.setCharterer("1");
    return loadableStudy;
  }

  private LoadableStudyStatus getLSS() {
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(1L);
    loadableStudyStatus.setName("CONFIRMED");
    return loadableStudyStatus;
  }

  private Set<LoadableStudyPortRotation> getPR() {
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    loadableStudyPortRotations.add(getPortRotation());
    return loadableStudyPortRotations;
  }

  private LoadableStudyPortRotation getPortRotation() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotation.setPortOrder(1L);
    loadableStudyPortRotation.setBerthXId(1L);
    loadableStudyPortRotation.setActive(true);
    loadableStudyPortRotation.setOperation(getCO());
    loadableStudyPortRotation.setEtd(LocalDateTime.now());
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    loadableStudyPortRotation.setSynopticalTable(Arrays.asList(getSynopticalTable().get()));
    return loadableStudyPortRotation;
  }

  private List<CargoNomination> getLCN() {
    List<CargoNomination> list = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1L);
    cargoNomination.setId(1L);
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setColor("1");
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setCargoNominationPortDetails(getSCNPD());
    cargoNomination.setLastModifiedDateTime(LocalDateTime.now());
    list.add(cargoNomination);
    return list;
  }

  private CargoOperation getCO() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1L);
    return cargoOperation;
  }

  private List<LoadablePattern> getLP() {
    List<LoadablePattern> list = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setCaseNumber(1);
    loadablePattern.setLoadableStudyStatus(1L);
    list.add(loadablePattern);
    return list;
  }

  @Test
  void testCheckIfVoyageClosed() {
    Long voyageId = 1L;
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVo());
    try {
      this.voyageService.checkIfVoyageClosed(voyageId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Voyage getVo() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setLoadableStudies(getLS());
    VoyageStatus voyageStatus = new VoyageStatus();
    voyageStatus.setName("ACTIVE");
    voyageStatus.setId(2L);
    voyage.setVoyageStatus(voyageStatus);
    voyage.setVoyageNo("1");
    return voyage;
  }

  @Test
  void testSaveVoyage() {
    LoadableStudy.VoyageRequest request =
        LoadableStudy.VoyageRequest.newBuilder()
            .setStartTimezoneId(1)
            .setEndTimezoneId(1)
            .setCaptainId(1L)
            .setEndDate("15-09-2021 05:34")
            .setStartDate("15-09-2021 05:34")
            .setChiefOfficerId(1L)
            .setCompanyId(1L)
            .setVesselId(1L)
            .setVoyageNo("1")
            .build();
    LoadableStudy.VoyageReply.Builder builder = LoadableStudy.VoyageReply.newBuilder();
    Mockito.when(
            voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLVe());
    Mockito.when(this.voyageStatusRepository.getOne(Mockito.anyLong())).thenReturn(getVS());
    Mockito.when(voyageRepository.save(Mockito.any())).thenReturn(getVo());
    this.voyageService.saveVoyage(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
    assertEquals(1L, builder.getVoyageId());
    verify(voyageRepository).save(Mockito.any(Voyage.class));
  }

  private List<Voyage> getLVe() {
    List<Voyage> list = new ArrayList<>();
    return list;
  }

  @Test
  void testGetVoyagesByVessel() {
    LoadableStudy.VoyageRequest request =
        LoadableStudy.VoyageRequest.newBuilder().setVesselId(1L).build();
    LoadableStudy.VoyageListReply.Builder builder = LoadableStudy.VoyageListReply.newBuilder();
    Voyage voyage = getLV().get(0);
    Set<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies = new HashSet<>();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setLoadableStudyStatus(getLSS());
    loadableStudy.setId(1L);
    loadableStudy.setPlanningTypeXId(1);
    loadableStudy.setName("ACTIVE");
    loadableStudy.setDraftMark(new BigDecimal(1));
    loadableStudy.setCreatedDateTime(LocalDateTime.now());
    loadableStudy.setPortRotations(getPR());
    loadableStudies.add(loadableStudy);
    voyage.setLoadableStudies(loadableStudies);

    Mockito.when(
            this.voyageRepository.findByVesselXIdAndIsActiveOrderByCreatedDateTimeDesc(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(Arrays.asList(voyage));
    when(synopticService.checkDischargeStarted(anyLong(), anyLong())).thenReturn(getOLS());
    Mockito.when(
            loadableStudyRepository.getLoadableStudyByVesselVoyagePlanningType(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(getOLS());
    when(this.loadableStudyPortRotationRepository
            .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(
                any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getPortRotation());
    ReflectionTestUtils.setField(voyageService, "dayDifference", "3");
    var voyageListReply = this.voyageService.getVoyagesByVessel(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testCheckIfVoyageActive() throws GenericServiceException {
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLV().get(0));

    final GenericServiceException ex =
        assertThrows(GenericServiceException.class, () -> voyageService.checkIfVoyageActive(1l));
    assertAll(
        () ->
            assertEquals(
                CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private Optional<List<com.cpdss.loadablestudy.entity.LoadableStudy>> getOLS() {
    List<com.cpdss.loadablestudy.entity.LoadableStudy> list = new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();

    list.add(loadableStudy);
    return Optional.of(list);
  }

  @Test
  void testfindCargoHistoryForPrvsVoyage() {
    Voyage voyage = new Voyage();
    voyage.setVoyageEndDate(LocalDateTime.now());
    voyage.setVoyageStartDate(LocalDateTime.now());
    voyage.setVesselXId(1L);
    voyage.setVoyageNo("1");
    Mockito.when(this.voyageStatusRepository.getOne(Mockito.anyLong())).thenReturn(getVS());
    Mockito.when(
            this.voyageRepository
                .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                    Mockito.anyLong(), Mockito.anyBoolean(), Mockito.any()))
        .thenReturn(voyage);
    var cargoHistory = this.voyageService.findCargoHistoryForPrvsVoyage(voyage);
    assertTrue(cargoHistory.isEmpty());
  }

  @Test
  void testSaveVoyageStatus() throws GenericServiceException {
    LoadableStudy.SaveVoyageStatusRequest request =
        LoadableStudy.SaveVoyageStatusRequest.newBuilder()
            .setActualStartDate("12-09-2021 11:23")
            .setActualEndDate("")
            .setStatus("START")
            .setVoyageId(1L)
            .build();
    LoadableStudy.SaveVoyageStatusReply.Builder replyBuilder =
        LoadableStudy.SaveVoyageStatusReply.newBuilder();
    LoadableStudyAlgoStatus algoStatus = new LoadableStudyAlgoStatus();
    algoStatus.setCreatedDateTime(LocalDateTime.now());
    algoStatus.setProcessId("1");

    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLV().get(0));
    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLVe());
    Mockito.when(
            this.voyageStatusRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOVS());
    Mockito.when(
            loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
                Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyInt()))
        .thenReturn(getOLSS());
    Mockito.when(
            loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
                Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLP());
    Mockito.when(
            cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLCN());
    when(this.loadingPlanService.loadingPlanSynchronization(
            any(LoadingPlanModels.LoadingPlanSyncRequest.class)))
        .thenReturn(
            LoadingPlanModels.LoadingPlanSyncReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(getPortRotation()));
    when(this.synopticalTableRepository
            .findByLoadableStudyXIdAndLoadableStudyPortRotation_idAndIsActive(
                anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(getSynopticalTable().get()));
    when(this.loadableStudyPortRotationRepository
            .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(
                any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getPortRotation());
    doNothing().when(cargoService).saveCargoHistoryFromOperationsModule(anyLong(), anyLong());
    when(loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(algoStatus));
    when(this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getCargoDetailsList());
    when(this.toppingOffSequenceRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(Arrays.asList(getToppingOffSequence()));

    var result = voyageService.saveVoyageStatus(request, replyBuilder);
    assertEquals(SUCCESS, result.build().getResponseStatus().getStatus());
    verify(voyageRepository).save(any(Voyage.class));
  }

  private LoadablePatternCargoToppingOffSequence getToppingOffSequence() {
    LoadablePatternCargoToppingOffSequence toppingOffSequence =
        new LoadablePatternCargoToppingOffSequence();
    toppingOffSequence.setCargoXId(1l);
    toppingOffSequence.setLoadablePattern(getLP().get(0));
    toppingOffSequence.setOrderNumber(1);
    toppingOffSequence.setTankXId(1l);
    toppingOffSequence.setDisplayOrder(1);
    toppingOffSequence.setPortRotationXId(1l);
    return toppingOffSequence;
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> getCargoDetailsList() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails =
        new ArrayList<>();
    LoadablePatternCargoDetails cargoDetails = new LoadablePatternCargoDetails();
    cargoDetails.setCargoId(1l);
    cargoDetails.setId(1l);
    cargoDetails.setTankId(1l);
    cargoDetails.setCargoNominationId(1l);
    cargoDetails.setPlannedQuantity(new BigDecimal(1));
    cargoDetails.setColorCode("1");
    cargoDetails.setAbbreviation("1");
    cargoDetails.setColorCode("1");
    cargoDetails.setCorrectedUllage(new BigDecimal(1));
    cargoDetails.setFillingRatio("1");
    cargoDetails.setPortRotationId(1l);
    cargoDetails.setOperationType("DEP");
    loadablePatternCargoDetails.add(cargoDetails);
    return loadablePatternCargoDetails;
  }

  private Optional<SynopticalTable> getSynopticalTable() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setId(1l);
    synopticalTable.setOperationType("DEP");
    synopticalTable.setLoadableStudyPortRotation(portRotation);
    synopticalTable.setEtdActual(LocalDateTime.now());
    synopticalTable.setIsActive(true);
    return Optional.of(synopticalTable);
  }

  @Test
  void testUpdateApiTempWithCargoNominations() {
    Voyage voyage = new Voyage();
    voyage.setVesselXId(1l);
    when(loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
            any(Voyage.class), anyLong(), anyBoolean(), anyInt()))
        .thenReturn(getOLSS());
    when(loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
            any(getOLSS().get().getClass()), anyLong(), anyBoolean()))
        .thenReturn(getOLP());
    when(cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLCN());
    voyageService.updateApiTempWithCargoNominations(voyage);
    verify(apiTempHistoryRepository).save(any(ApiTempHistory.class));
  }

  @Test
  void testsaveApiTempWithPortDetails() {
    voyageService.saveApiTempWithPortDetails(new ApiTempHistory());
    verify(apiTempHistoryRepository).save(any(ApiTempHistory.class));
  }

  @Test
  void testgetVoyageByVoyageId() {
    LoadableStudy.VoyageInfoRequest request = LoadableStudy.VoyageInfoRequest.newBuilder().build();
    LoadableStudy.VoyageInfoReply.Builder replyBuilder = LoadableStudy.VoyageInfoReply.newBuilder();
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getVo());

    voyageService.getVoyageByVoyageId(request, replyBuilder);
    assertEquals("1", replyBuilder.getVoyageDetail().getVoyageNumber());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testGetVoyages(Integer i) throws GenericServiceException {
    VoyageService spyService = spy(VoyageService.class);
    LoadableStudy.VoyageListReply.Builder builder = LoadableStudy.VoyageListReply.newBuilder();
    LoadableStudy.VoyageRequest request =
        LoadableStudy.VoyageRequest.newBuilder()
            .setVesselId(1l)
            .setStartDate("dd-MM-yyyy")
            .setFromStartDate("20-12-2014")
            .setToStartDate("20-09-2014")
            .build();
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    Mockito.doReturn(portReply).when(spyService).GetPortInfo(any(PortInfo.PortRequest.class));
    CargoInfo.CargoReply cargoReply =
        CargoInfo.CargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    List<Object[]> list = new ArrayList<>();
    Object obj[] = new Object[3];
    obj[0] = 1l;
    obj[1] = 1l;
    list.add(obj);
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy = getLoadableStudy();
    loadableStudy.setPlanningTypeXId(i);

    Mockito.doReturn(cargoReply).when(spyService).getCargoInfo(any(CargoInfo.CargoRequest.class));
    when(loadableStudyRepository.findByListOfVoyage(anyList()))
        .thenReturn(Arrays.asList(loadableStudy));
    when(this.loadableStudyPortRotationRepository.getDistinctLoadingPorts(anyList()))
        .thenReturn(list);
    when(this.loadableStudyPortRotationRepository.getDistinctDischarigingPortsById(anyList()))
        .thenReturn(list);
    when(this.cargoNominationRepository.findByLoadableStudyIdIn(anyList())).thenReturn(list);
    when(voyageRepository.findByIsActiveAndVesselXIdAndActualStartDateBetween(
            anyBoolean(), anyLong(), any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(getLV());
    List<Voyage> voyageList = new ArrayList<>();
    when(voyageRepository
            .findByIsActiveAndVesselXIdOrderByVoyageStatusDescAndLastModifiedDateTimeDesc(
                anyBoolean(), anyLong()))
        .thenReturn(voyageList);
    ReflectionTestUtils.setField(spyService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    var result = spyService.getVoyages(request, builder);
    assertEquals(SUCCESS, result.build().getResponseStatus().getStatus());
  }

  @Test
  void testBuildVoyageDetails() {
    ModelMapper modelMapper = new ModelMapper();
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    loadableStudy.setVoyageId(1l);
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    voyageService.buildVoyageDetails(modelMapper, loadableStudy);
    assertEquals(1l, loadableStudy.getVoyage().getId().longValue());
  }

  @Test
  void testGetPortInfo() {
    VoyageService spyService = spy(VoyageService.class);
    PortInfo.PortRequest request = PortInfo.PortRequest.newBuilder().build();
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();

    when(portInfoGrpcService.getPortInfo(any(PortInfo.PortRequest.class))).thenReturn(portReply);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);

    var result = spyService.GetPortInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfo() {
    VoyageService spyService = spy(VoyageService.class);
    CargoInfo.CargoRequest request = CargoInfo.CargoRequest.newBuilder().build();
    CargoInfo.CargoReply cargoReply =
        CargoInfo.CargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();

    when(cargoInfoGrpcService.getCargoInfo(any(CargoInfo.CargoRequest.class)))
        .thenReturn(cargoReply);
    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);

    var result = spyService.getCargoInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private Optional<VoyageStatus> getOVS() {
    VoyageStatus voyageStatus = new VoyageStatus();
    voyageStatus.setId(1L);
    return Optional.of(voyageStatus);
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getOLSS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVesselXId(1l);
    return Optional.of(loadableStudy);
  }

  private Optional<LoadablePattern> getOLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setLoadableStudyStatus(1L);
    loadablePattern.setId(1L);
    loadablePattern.setLoadableStudy(getOLSS().get());
    return Optional.of(loadablePattern);
  }

  private Set<CargoNominationPortDetails> getSCNPD() {
    Set<CargoNominationPortDetails> set = new HashSet<>();
    CargoNominationPortDetails cargoNominationPortDetails = new CargoNominationPortDetails();
    cargoNominationPortDetails.setPortId(1L);
    set.add(cargoNominationPortDetails);
    return set;
  }
}
