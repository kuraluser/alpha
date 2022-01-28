/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.DATE_FORMAT;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails;
import com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply;
import com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.repository.projections.LoadingPlanQtyAndOrder;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {SynopticService.class})
public class SynopticServiceTest {

  @Autowired SynopticService synopticService;
  @MockBean SynopticalTableRepository synopticalTableRepository;
  @MockBean LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean CargoNominationRepository cargoNominationRepository;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean CargoOperationRepository cargoOperationRepository;
  @MockBean LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private CargoNominationService cargoNominationService;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean OnHandQuantityService onHandQuantityService;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private VoyageService voyageService;
  @MockBean private EntityManager entityManager;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private VoyageStatusRepository voyageStatusRepository;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private LoadablePatternService loadablePatternService;

  @MockBean
  private DischargePatternQuantityCargoPortwiseRepository
      dischargePatternQuantityCargoPortwiseRepository;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean SynopticServiceUtils synpoticServiceUtils;

  @MockBean
  private DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub
      dischargeInformationGrpcService;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final String ETA_ETD_FORMAT = "dd-MM-yyyy HH:mm";

  @Test
  void testFetchLoadingInformationSynopticDetails() throws GenericServiceException {
    LoadableStudy.LoadingPlanIdRequest request =
        LoadableStudy.LoadingPlanIdRequest.newBuilder()
            .setId(1L)
            .setOperationType("LOADABLE_STUDY")
            .setIdType("PORT_ROTATION")
            .setPatternId(1L)
            .setPortRotationId(1L)
            .setPortId(1L)
            .build();
    LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    Common.ResponseStatus.Builder repBuilder = Common.ResponseStatus.newBuilder();

    doNothing()
        .when(synpoticServiceUtils)
        .buildPortRotationResponse(
            anyLong(),
            any(LoadableStudy.LoadingPlanCommonResponse.Builder.class),
            any(Common.ResponseStatus.Builder.class));
    doNothing()
        .when(synpoticServiceUtils)
        .buildCargoToBeLoadedForPort(
            any(LoadableStudy.LoadingPlanIdRequest.class),
            any(LoadableStudy.LoadingPlanCommonResponse.Builder.class),
            any(Common.ResponseStatus.Builder.class));
    doNothing()
        .when(synpoticServiceUtils)
        .buildCargoToBeDischargedFroPort(
            any(LoadableStudy.LoadingPlanIdRequest.class),
            any(LoadableStudy.LoadingPlanCommonResponse.Builder.class),
            any(Common.ResponseStatus.Builder.class));
    doNothing()
        .when(synpoticServiceUtils)
        .buildBallastDetailsBasedOnPort(
            any(LoadableStudy.LoadingPlanIdRequest.class),
            any(LoadableStudy.LoadingPlanCommonResponse.Builder.class),
            any(Common.ResponseStatus.Builder.class));
    this.synopticService.fetchLoadingInformationSynopticDetails(request, builder, repBuilder);
    verify(synpoticServiceUtils)
        .buildBallastDetailsBasedOnPort(
            any(LoadableStudy.LoadingPlanIdRequest.class),
            any(LoadableStudy.LoadingPlanCommonResponse.Builder.class),
            any(Common.ResponseStatus.Builder.class));
  }

  private List<SynopticalTable> getST() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    synopticalTable.setLoadableStudyXId(1L);
    synopticalTable.setPortXid(1L);
    synopticalTable.setOperationType("1");
    synopticalTable.setTimeOfSunrise(LocalTime.now());
    synopticalTable.setTimeOfSunSet(LocalTime.now());
    synopticalTable.setDistance(new BigDecimal(1));
    synopticalTable.setEtaActual(LocalDateTime.now());
    synopticalTable.setEtdActual(LocalDateTime.now());
    synopticalTable.setLoadableStudyPortRotation(getLSPR());
    synopticalTableList.add(synopticalTable);
    return synopticalTableList;
  }

  private LoadableStudyPortRotation getLSPR() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setEta(LocalDateTime.now());
    loadableStudyPortRotation.setEtd(LocalDateTime.now());
    loadableStudyPortRotation.setPortOrder(1L);
    loadableStudyPortRotation.setPortXId(1l);
    loadableStudyPortRotation.setActive(true);
    CargoOperation operation = new CargoOperation();
    operation.setName("1");
    operation.setId(1L);
    loadableStudyPortRotation.setOperation(operation);
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    return loadableStudyPortRotation;
  }

  private List<LoadablePlanQuantity> getLPQ() {
    List<LoadablePlanQuantity> loadablePlanQuantities = new ArrayList<>();
    LoadablePlanQuantity loadablePlanQuantity = new LoadablePlanQuantity();
    loadablePlanQuantity.setCargoXId(1L);
    loadablePlanQuantity.setId(1L);
    loadablePlanQuantity.setGrade("1");
    loadablePlanQuantity.setEstimatedApi(new BigDecimal(1));
    loadablePlanQuantity.setEstimatedTemperature(new BigDecimal(1));
    loadablePlanQuantity.setCargoNominationTemperature(new BigDecimal(1));
    loadablePlanQuantity.setOrderBblsDbs("1");
    loadablePlanQuantity.setOrderBbls60f("1");
    loadablePlanQuantity.setMinTolerence("1");
    loadablePlanQuantity.setMaxTolerence("1");
    loadablePlanQuantity.setLoadableBblsDbs("1");
    loadablePlanQuantity.setLoadableBbls60f("1");
    loadablePlanQuantity.setLoadableLt("1");
    loadablePlanQuantity.setLoadableMt("1");
    loadablePlanQuantity.setLoadableKl("1");
    loadablePlanQuantity.setDifferencePercentage("1");
    loadablePlanQuantity.setDifferenceColor("1");
    loadablePlanQuantity.setCargoAbbreviation("1");
    loadablePlanQuantity.setCargoColor("1");
    loadablePlanQuantity.setPriority(1);
    loadablePlanQuantity.setLoadingOrder(1);
    loadablePlanQuantity.setSlopQuantity("1");
    loadablePlanQuantity.setOrderQuantity(new BigDecimal(1));
    loadablePlanQuantity.setCargoNominationId(1L);
    loadablePlanQuantity.setTimeRequiredForLoading("1");
    loadablePlanQuantities.add(loadablePlanQuantity);
    return loadablePlanQuantities;
  }

  private Optional<CargoNomination> getCN() {
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setQuantity(new BigDecimal(1));
    return Optional.of(cargoNomination);
  }

  private List<LoadablePlanStowageBallastDetails> getLPSBD() {
    List<LoadablePlanStowageBallastDetails> list = new ArrayList<>();
    LoadablePlanStowageBallastDetails loadablePlanStowageBallastDetails =
        new LoadablePlanStowageBallastDetails();
    loadablePlanStowageBallastDetails.setId(1L);
    loadablePlanStowageBallastDetails.setColorCode("1");
    loadablePlanStowageBallastDetails.setCorrectionFactor("1");
    loadablePlanStowageBallastDetails.setFillingPercentage("1");
    loadablePlanStowageBallastDetails.setTankXId(1L);
    loadablePlanStowageBallastDetails.setOperationType("1");
    loadablePlanStowageBallastDetails.setPortRotationId(1l);
    list.add(loadablePlanStowageBallastDetails);
    return list;
  }

  @Test
  void testSaveLoadingInformationToSynopticalTable() {
    LoadableStudy.LoadingInfoSynopticalUpdateRequest request =
        LoadableStudy.LoadingInfoSynopticalUpdateRequest.newBuilder()
            .setSynopticalTableId(1L)
            .setTimeOfSunrise("1")
            .setTimeOfSunset("1")
            .build();
    Common.ResponseStatus.Builder builder = Common.ResponseStatus.newBuilder();
    Mockito.when(
            this.synopticalTableRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOST());
    try {
      var responseStatus =
          this.synopticService.saveLoadingInformationToSynopticalTable(request, builder);
      Mockito.verify(synopticalTableRepository).save(Mockito.any(SynopticalTable.class));
      assertEquals(SUCCESS, responseStatus.getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<SynopticalTable> getOST() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    synopticalTable.setLoadableStudyPortRotation(getLSPR());
    synopticalTable.setOperationType("ARR");
    synopticalTable.setLoadableStudyXId(1L);
    return Optional.of(synopticalTable);
  }

  @Test
  void testBuildPortsInfoSynopticalTable() {
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    entity.setLoadableStudy(getLS());
    entity.setSynopticalTable(getST());
    Long requestedOperationId = 1L;
    Long requestedPortId = 1L;
    this.synopticService.buildPortsInfoSynopticalTable(
        entity, requestedOperationId, requestedPortId);
    assertEquals(Optional.of(1L), Optional.of(entity.getSynopticalTable().get(0).getId()));
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setLoadLineXId(1l);
    loadableStudy.setLoadableStudyStatus(getLSS());
    loadableStudy.setId(1L);
    loadableStudy.setPlanningTypeXId(2);
    loadableStudy.setName("ACTIVE");
    loadableStudy.setDraftMark(new BigDecimal(1));
    loadableStudy.setCreatedDateTime(LocalDateTime.now());
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    loadableStudyPortRotations.add(getLSPR());
    loadableStudy.setPortRotations(loadableStudyPortRotations);
    return loadableStudy;
  }

  private LoadableStudyStatus getLSS() {
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(1L);
    loadableStudyStatus.setName("CONFIRMED");
    return loadableStudyStatus;
  }

  private CargoOperation getCO() {
    CargoOperation cargoOperation = new CargoOperation();
    return cargoOperation;
  }

  private Object getObj() {
    Object object = new Object();
    return object;
  }

  private Page<SynopticalTable> getPST() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setIsActive(true);
    synopticalTable.setOperationType("DEP");
    return new PageImpl<>(Collections.singletonList(synopticalTable));
  }

  @Test
  void testGetSynopticalTablePortRotations() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLSPR());
    var loadableStudyPortRotation =
        this.synopticService.getSynopticalTablePortRotations(loadableStudy);
    assertEquals(
        Optional.of(1L), Optional.ofNullable(loadableStudyPortRotation.get(0).getPortXId()));
  }

  private List<LoadableStudyPortRotation> getLLSPR() {
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setPortOrder(1L);
    loadableStudyPortRotation.setActive(true);
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    return loadableStudyPortRotations;
  }

  @Test
  void testPopulateOnHandQuantityData() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setId(1L);

    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setPortOrder(1L);
    portRotation.setId(1L);
    List<PortDischargingPlanRobDetails> robDetailsList = new ArrayList<>();
    PortDischargingPlanRobDetails robDetails =
        PortDischargingPlanRobDetails.newBuilder()
            .setTankXId(1l)
            .setQuantity(1)
            .setDensity(1)
            .setPortXId(1l)
            .build();
    robDetailsList.add(robDetails);
    PortDischargingPlanRobDetailsReply reply =
        PortDischargingPlanRobDetailsReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllPortDischargingPlanRobDetails(robDetailsList)
            .build();
    List<VesselInfo.VesselTankInfo> tankInfoList = new ArrayList<>();
    VesselInfo.VesselTankInfo tankInfo =
        VesselInfo.VesselTankInfo.newBuilder().setTankCategoryId(1l).setTankId(1l).build();
    tankInfoList.add(tankInfo);
    VesselInfo.VesselTankReply vesselTankReply =
        VesselInfo.VesselTankReply.newBuilder().addAllVesselTankInfo(tankInfoList).build();

    when(this.vesselInfoGrpcService.getVesselTanksByTankIds(
            any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(vesselTankReply);
    Mockito.when(this.voyageStatusRepository.getOne(Mockito.anyLong()))
        .thenReturn(getVoyageStatus());
    Mockito.when(
            this.voyageRepository
                .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                    Mockito.anyLong(), Mockito.anyBoolean(), Mockito.any()))
        .thenReturn(getVoyage());
    Mockito.when(
            this.loadableStudyRepository
                .findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
                    Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyInt()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(getCO());
    Mockito.when(
            this.loadableStudyPortRotationRepository
                .findFirstByLoadableStudyAndOperationAndIsActiveOrderByPortOrderDesc(
                    Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLSPR());
    Mockito.when(
            this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
                Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLOHQ());
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLLSPR());
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getLLSPR());

    when(this.dischargeInformationGrpcService.getPortDischargingPlanRobDetails(
            any(PortDischargingPlanRobDetailsRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "entityManager", entityManager);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "dischargeInformationGrpcService", dischargeInformationGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "onHandQuantityRepository", onHandQuantityRepository);
    ReflectionTestUtils.setField(spyService, "cargoOperationRepository", cargoOperationRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(spyService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(spyService, "voyageStatusRepository", voyageStatusRepository);

    spyService.populateOnHandQuantityData(Optional.of(loadableStudy), portRotation);
    Mockito.verify(onHandQuantityRepository).saveAll(Mockito.anyList());
  }

  private Voyage getVoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setVesselXId(1L);
    return voyage;
  }

  private VoyageStatus getVoyageStatus() {
    VoyageStatus voyageStatus = new VoyageStatus();
    return voyageStatus;
  }

  private List<OnHandQuantity> getLOHQ() {
    List<OnHandQuantity> onHandQuantities = new ArrayList<>();
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    onHandQuantity.setId(1L);
    onHandQuantity.setTankXId(1L);
    onHandQuantity.setFuelTypeXId(1L);
    onHandQuantity.setPortRotation(getLSPR());
    onHandQuantity.setDepartureQuantity(new BigDecimal(1));
    onHandQuantities.add(onHandQuantity);
    return onHandQuantities;
  }

  @Test
  void testBuildSynopticalRecord() {
    SynopticalTable synopticalEntity = new SynopticalTable();
    synopticalEntity.setId(1L);
    synopticalEntity.setPortXid(1L);
    synopticalEntity.setOperationType("1");
    synopticalEntity.setDistance(new BigDecimal(1));
    synopticalEntity.setSpeed(new BigDecimal(1));
    synopticalEntity.setRunningHours(new BigDecimal(1));
    synopticalEntity.setInPortHours(new BigDecimal(1));
    synopticalEntity.setTimeOfSunrise(LocalTime.now());
    synopticalEntity.setTimeOfSunSet(LocalTime.now());
    synopticalEntity.setHwTideFrom(new BigDecimal(1));
    synopticalEntity.setHwTideTimeFrom(LocalTime.now());
    synopticalEntity.setHwTideTo(new BigDecimal(1));
    synopticalEntity.setHwTideTimeTo(LocalTime.now());
    synopticalEntity.setLwTideFrom(new BigDecimal(1));
    synopticalEntity.setLwTideTimeFrom(LocalTime.now());
    synopticalEntity.setLwTideTo(new BigDecimal(1));
    synopticalEntity.setLwTideTimeTo(LocalTime.now());
    synopticalEntity.setLoadableStudyPortRotation(getLSPR());
    synopticalEntity.setEtaActual(LocalDateTime.now());
    synopticalEntity.setEtdActual(LocalDateTime.now());
    LoadableStudy.SynopticalRecord.Builder builder = LoadableStudy.SynopticalRecord.newBuilder();
    List<PortInfo.PortDetail> portDetails = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder()
            .setId(1L)
            .setName("1")
            .setWaterDensity("1")
            .setHwTideFrom("1")
            .setHwTideTo("1")
            .setLwTideFrom("1")
            .setLwTideTo("1")
            .setHwTideTimeFrom("1")
            .setHwTideTimeTo("1")
            .setLwTideTimeFrom("1")
            .setLwTideTimeTo("1")
            .setSunriseTime("1")
            .setSunsetTime("1")
            .build();
    PortInfo.PortReply portReply = PortInfo.PortReply.newBuilder().addAllPorts(portDetails).build();
    this.synopticService.buildSynopticalRecord(synopticalEntity, builder, portReply);
    assertEquals("1", builder.getDistance());
  }

  @Test
  void testSetSynopticalEtaEtdEstimated() {
    SynopticalTable synopticalEntity = new SynopticalTable();
    synopticalEntity.setLoadableStudyPortRotation(getLSPR());
    synopticalEntity.setOperationType("ARR");
    LoadableStudy.SynopticalRecord.Builder builder = LoadableStudy.SynopticalRecord.newBuilder();
    List<LoadableStudyPortRotation> portRotations = new ArrayList<>();
    this.synopticService.setSynopticalEtaEtdEstimated(synopticalEntity, builder, portRotations);
    assertEquals(1L, builder.getPortRotationId());
  }

  private List<LoadablePlanComminglePortwiseDetails> getPortwiseDetailsList() {
    List<LoadablePlanComminglePortwiseDetails> commingleCargoDetails = new ArrayList<>();
    LoadablePlanComminglePortwiseDetails loadablePlanComminglePortwiseDetails =
        new LoadablePlanComminglePortwiseDetails();
    loadablePlanComminglePortwiseDetails.setApi("1");
    loadablePlanComminglePortwiseDetails.setTankId(1l);
    loadablePlanComminglePortwiseDetails.setCargo1Mt("1");
    loadablePlanComminglePortwiseDetails.setCargo2Mt("1");
    loadablePlanComminglePortwiseDetails.setPortRotationXid(1L);
    loadablePlanComminglePortwiseDetails.setOperationType("1");
    loadablePlanComminglePortwiseDetails.setActualQuantity(new BigDecimal(1));
    loadablePlanComminglePortwiseDetails.setCorrectedUllage("1");
    loadablePlanComminglePortwiseDetails.setFillingRatio("1");
    loadablePlanComminglePortwiseDetails.setLoadablePattern(new LoadablePattern());
    commingleCargoDetails.add(loadablePlanComminglePortwiseDetails);
    return commingleCargoDetails;
  }

  private List<OnBoardQuantity> getObqEntities() {
    List<OnBoardQuantity> obqEntities = new ArrayList<>();
    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    onBoardQuantity.setTankId(1L);
    onBoardQuantity.setActualArrivalWeight(new BigDecimal(1));
    onBoardQuantity.setPlannedArrivalWeight(new BigDecimal(1));
    onBoardQuantity.setDensity(new BigDecimal(1));
    onBoardQuantity.setAbbreviation("1");
    obqEntities.add(onBoardQuantity);
    return obqEntities;
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> getCargoDetails() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetailsList =
        new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoDetails =
        new LoadablePatternCargoDetails();
    cargoDetails.setCargoId(1L);
    cargoDetails.setOperationType("1");
    cargoDetails.setPortRotationId(1L);
    cargoDetails.setTankId(1l);
    cargoDetails.setCargoNominationId(1l);
    cargoDetails.setId(1l);
    cargoDetails.setPlannedQuantity(new BigDecimal(1));
    cargoDetails.setActualQuantity(new BigDecimal(1));
    cargoDetails.setAbbreviation("1");
    cargoDetails.setColorCode("1");
    cargoDetails.setActualRdgUllage(new BigDecimal(1));
    cargoDetails.setCorrectedUllage(new BigDecimal(1));
    cargoDetails.setActualApi(new BigDecimal(1));
    cargoDetails.setActualTemperature(new BigDecimal(1));
    cargoDetails.setFillingRatio("1");
    cargoDetailsList.add(cargoDetails);
    return cargoDetailsList;
  }

  @Test
  void testSetSynopticalCargoDetails() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().build();

    SynopticalTable synopticalEntity = new SynopticalTable();
    synopticalEntity.setLoadableStudyPortRotation(getLSPR());
    synopticalEntity.setOperationType("ARR");
    synopticalEntity.setPortXid(1L);

    LoadableStudy.SynopticalRecord.Builder builder = LoadableStudy.SynopticalRecord.newBuilder();
    List<VesselInfo.VesselTankDetail> sortedTankList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankCategoryId(1L)
            .setTankId(1L)
            .setShortName("1")
            .setFullCapacityCubm("1")
            .build();
    sortedTankList.add(tankDetail);

    Long firstPortId = 1L;
    Voyage voyage = new Voyage();
    this.synopticService.setSynopticalCargoDetails(
        request,
        getCargoDetails(),
        getObqEntities(),
        synopticalEntity,
        builder,
        sortedTankList,
        firstPortId,
        voyage,
        getPortwiseDetailsList(),
        Arrays.asList(1l));
    assertEquals("1", builder.getCargoActualTotal());
  }

  private LoadablePlanCommingleDetails getCommingleDetails() {
    LoadablePlanCommingleDetails commingleDetails = new LoadablePlanCommingleDetails();
    commingleDetails.setTankId(1l);
    commingleDetails.setGrade("1");
    commingleDetails.setCommingleColour("1");
    commingleDetails.setCargo1Mt("1");
    commingleDetails.setCargo2Mt("1");
    commingleDetails.setCargo1Lt("1");
    commingleDetails.setCargo2Lt("1");
    commingleDetails.setCargo1Abbreviation("1");
    commingleDetails.setCargo2Abbreviation("1");
    commingleDetails.setCargo1NominationId(1l);
    commingleDetails.setCargo2NominationId(1l);
    return commingleDetails;
  }

  @Test
  void testSetSynopticalCargoDetailsElseif() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().setLoadablePatternId(1l).build();

    SynopticalTable synopticalEntity = new SynopticalTable();
    synopticalEntity.setLoadableStudyPortRotation(getLSPR());
    synopticalEntity.setOperationType("1");
    synopticalEntity.setPortXid(1L);

    LoadableStudy.SynopticalRecord.Builder builder = LoadableStudy.SynopticalRecord.newBuilder();
    List<VesselInfo.VesselTankDetail> sortedTankList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankCategoryId(1L)
            .setTankId(1L)
            .setShortName("1")
            .setFullCapacityCubm("1")
            .build();
    sortedTankList.add(tankDetail);

    Long firstPortId = 1L;
    Voyage voyage = new Voyage();
    LoadingPlanQtyAndOrder planQtyAndOrder =
        new LoadingPlanQtyAndOrder() {
          @Override
          public Long getId() {
            return 1l;
          }

          @Override
          public Integer getLoadingOrder() {
            return 1;
          }
        };

    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLS()));
    when(loadablePlanQuantityRepository.findByCargoNominationIdAndIsActiveTrue(anyLong()))
        .thenReturn(Arrays.asList(planQtyAndOrder));

    when(this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(Arrays.asList(getCommingleDetails()));

    this.synopticService.setSynopticalCargoDetails(
        request,
        getCargoDetails(),
        getObqEntities(),
        synopticalEntity,
        builder,
        sortedTankList,
        firstPortId,
        voyage,
        getPortwiseDetailsList(),
        Arrays.asList(1l));
    assertEquals("1", builder.getCargoActualTotal());
  }

  @Test
  void testBuildObqDataForSynopticalTable1() {
    VesselInfo.VesselTankDetail tank =
        VesselInfo.VesselTankDetail.newBuilder().setTankId(1L).build();
    List<com.cpdss.loadablestudy.domain.CargoHistory> cargoHistories = new ArrayList<>();
    List<OnBoardQuantity> obqEntities = new ArrayList<>();
    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    onBoardQuantity.setTankId(1L);
    onBoardQuantity.setActualArrivalWeight(new BigDecimal(1));
    onBoardQuantity.setPlannedArrivalWeight(new BigDecimal(1));
    onBoardQuantity.setDensity(new BigDecimal(1));
    onBoardQuantity.setAbbreviation("1");
    obqEntities.add(onBoardQuantity);
    LoadableStudy.SynopticalCargoRecord.Builder cargoBuilder =
        LoadableStudy.SynopticalCargoRecord.newBuilder();
    Voyage voyage = new Voyage();
    this.synopticService.buildObqDataForSynopticalTable(
        tank, cargoHistories, obqEntities, cargoBuilder, voyage);
    assertEquals("1", cargoBuilder.getCargoAbbreviation());
  }

  @Test
  void testBuildObqDataForSynopticalTable2() {
    VesselInfo.VesselTankDetail tank =
        VesselInfo.VesselTankDetail.newBuilder().setTankId(2L).build();
    List<com.cpdss.loadablestudy.domain.CargoHistory> cargoHistories = new ArrayList<>();
    com.cpdss.loadablestudy.domain.CargoHistory cargoHistory =
        new com.cpdss.loadablestudy.domain.CargoHistory();
    cargoHistory.setTankId(2L);
    cargoHistory.setQuantity(new BigDecimal(1));
    cargoHistories.add(cargoHistory);
    List<OnBoardQuantity> obqEntities = new ArrayList<>();
    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    onBoardQuantity.setTankId(1L);
    obqEntities.add(onBoardQuantity);
    LoadableStudy.SynopticalCargoRecord.Builder cargoBuilder =
        LoadableStudy.SynopticalCargoRecord.newBuilder();
    Voyage voyage = new Voyage();
    Mockito.when(voyageService.findCargoHistoryForPrvsVoyage(Mockito.any()))
        .thenReturn(cargoHistories);
    this.synopticService.buildObqDataForSynopticalTable(
        tank, cargoHistories, obqEntities, cargoBuilder, voyage);
    assertEquals("1", cargoBuilder.getPlannedWeight());
  }

  @Test
  void testSetSynopticalOhqData() {
    List<OnHandQuantity> ohqEntities = new ArrayList<>();
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    onHandQuantity.setPortRotation(getLSPR());
    onHandQuantity.setTankXId(1L);
    onHandQuantity.setArrivalQuantity(new BigDecimal(1));
    onHandQuantity.setActualArrivalQuantity(new BigDecimal(1));
    onHandQuantity.setDensity(new BigDecimal(1));
    ohqEntities.add(onHandQuantity);
    SynopticalTable synopticalEntity = new SynopticalTable();
    synopticalEntity.setOperationType("ARR");
    synopticalEntity.setLoadableStudyPortRotation(getLSPR());
    LoadableStudy.SynopticalRecord.Builder builder = LoadableStudy.SynopticalRecord.newBuilder();

    this.synopticService.setSynopticalOhqData(
        ohqEntities, synopticalEntity, builder, getVesselTankDetailList());
    assertEquals("1", builder.getOhq(0).getActualWeight());
  }

  private List<VesselInfo.VesselTankDetail> getVesselTankDetailList() {
    List<VesselInfo.VesselTankDetail> sortedTankList = new ArrayList<>();
    VesselInfo.VesselTankDetail vesselTankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setShortName("1")
            .setTankCategoryShortName("1")
            .setFullCapacityCubm("1")
            .setTankId(1L)
            .setTankCategoryId(5L)
            .setShowInOhqObq(true)
            .setTankDisplayOrder(1)
            .build();
    sortedTankList.add(vesselTankDetail);
    return sortedTankList;
  }

  @Test
  void testSetSynopticalTableVesselParticulars() {
    SynopticalTable synopticalEntity = new SynopticalTable();
    synopticalEntity.setOthersPlanned(new BigDecimal(1));
    synopticalEntity.setOthersActual(new BigDecimal(1));
    synopticalEntity.setConstantPlanned(new BigDecimal(1));
    synopticalEntity.setConstantActual(new BigDecimal(1));
    synopticalEntity.setDeadWeightActual(new BigDecimal(1));
    synopticalEntity.setDeadWeightPlanned(new BigDecimal(1));
    synopticalEntity.setDisplacementActual(new BigDecimal(1));
    synopticalEntity.setDisplacementPlanned(new BigDecimal(1));
    LoadableStudy.SynopticalRecord.Builder builder = LoadableStudy.SynopticalRecord.newBuilder();

    this.synopticService.setSynopticalTableVesselParticulars(
        synopticalEntity, builder, getVesselDetails());
    assertEquals("1", builder.getOthersPlanned());
  }

  private VesselInfo.VesselLoadableQuantityDetails getVesselDetails() {
    VesselInfo.VesselLoadableQuantityDetails vesselLoadableQuantityDetails =
        VesselInfo.VesselLoadableQuantityDetails.newBuilder()
            .setHasLoadicator(true)
            .setDisplacmentDraftRestriction("1")
            .setConstant("1")
            .setDwt("1")
            .build();
    return vesselLoadableQuantityDetails;
  }

  @Test
  void testSetSynopticalPortValues() {
    PortInfo.PortDetail port =
        PortInfo.PortDetail.newBuilder()
            .setName("1")
            .setWaterDensity("1")
            .setHwTideFrom("1")
            .setHwTideTo("1")
            .setLwTideFrom("1")
            .setLwTideTo("1")
            .setHwTideTimeFrom("1")
            .setHwTideTimeTo("1")
            .setLwTideTimeFrom("1")
            .setLwTideTimeTo("1")
            .setSunriseTime("1")
            .setSunsetTime("1")
            .build();
    com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
    this.synopticService.setSynopticalPortValues(port, builder);
    assertEquals("1", builder.getLwTideFrom());
    assertEquals("1", builder.getTimeOfSunrise());
  }

  @Test
  void testBuildPortIdsRequestSynoptical() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.Builder portReqBuilder =
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setPortXid(1L);
    synopticalTableList.add(synopticalTable);
    this.synopticService.buildPortIdsRequestSynoptical(portReqBuilder, synopticalTableList);
    assertEquals(1L, portReqBuilder.getId(0));
  }

  @Test
  void testSetSynopticalTableLoadicatorData() {
    SynopticalTable synopticalEntity = new SynopticalTable();
    Long loadablePatternId = 1L;
    com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
    Mockito.when(
            this.synopticalTableLoadicatorDataRepository
                .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                    Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getSTLD());
    this.synopticService.setSynopticalTableLoadicatorData(
        synopticalEntity, loadablePatternId, builder);
    assertEquals("1", builder.getLoadicatorData().getDeflection());
  }

  private SynopticalTableLoadicatorData getSTLD() {
    SynopticalTableLoadicatorData synopticalTableLoadicatorData =
        new SynopticalTableLoadicatorData();
    synopticalTableLoadicatorData.setBlindSector(new BigDecimal(1));
    synopticalTableLoadicatorData.setDeflection(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedDraftAftActual(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedDraftAftPlanned(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedDraftFwdActual(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedDraftFwdPlanned(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedDraftMidActual(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedDraftMidPlanned(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedTrimActual(new BigDecimal(1));
    synopticalTableLoadicatorData.setCalculatedTrimPlanned(new BigDecimal(1));
    synopticalTableLoadicatorData.setList(new BigDecimal(1));
    synopticalTableLoadicatorData.setBendingMoment(new BigDecimal(1));
    synopticalTableLoadicatorData.setShearingForce(new BigDecimal(1));
    synopticalTableLoadicatorData.setBallastActual(new BigDecimal(1));
    return synopticalTableLoadicatorData;
  }

  @Test
  void testSetFinalDraftValues1() {
    com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder =
        com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.newBuilder();
    SynopticalTableLoadicatorData loadicatorData = new SynopticalTableLoadicatorData();
    loadicatorData.setDeflection(new BigDecimal(1));
    loadicatorData.setCalculatedDraftFwdActual(new BigDecimal(1));
    loadicatorData.setCalculatedDraftAftActual(new BigDecimal(1));
    loadicatorData.setCalculatedDraftMidActual(new BigDecimal(1));
    this.synopticService.setFinalDraftValues(dataBuilder, loadicatorData);
    assertEquals("1.01", dataBuilder.getFinalDraftAft());
    assertEquals("1.01", dataBuilder.getFinalDraftMid());
  }

  @Test
  void testSetFinalDraftValues2() {
    com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder =
        com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.newBuilder();
    SynopticalTableLoadicatorData loadicatorData = new SynopticalTableLoadicatorData();
    loadicatorData.setDeflection(new BigDecimal(1));
    loadicatorData.setCalculatedDraftFwdPlanned(new BigDecimal(1));
    loadicatorData.setCalculatedDraftAftPlanned(new BigDecimal(1));
    loadicatorData.setCalculatedDraftMidPlanned(new BigDecimal(1));
    this.synopticService.setFinalDraftValues(dataBuilder, loadicatorData);
    assertEquals("1.01", dataBuilder.getFinalDraftAft());
    assertEquals("1.01", dataBuilder.getFinalDraftMid());
  }

  @Test
  void testGetSynopticalPortDataByPortId() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(1L)
            .setPortId(1L)
            .build();
    LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        LoadableStudy.SynopticalTableReply.newBuilder();
    Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(getOLS());
    Mockito.when(
            this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
                Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyLong()))
        .thenReturn(getST());
    try {
      this.synopticService.getSynopticalPortDataByPortId(request, replyBuilder);
      assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getOLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setConfirmedLoadableStudyId(1l);
    loadableStudy.setVesselXId(1l);
    loadableStudy.setLoadLineXId(1l);
    Set<LoadableStudyPortRotation> portRotationSet = new HashSet<>();
    portRotationSet.add(getLSPR());
    loadableStudy.setPortRotations(portRotationSet);
    loadableStudy.setDraftMark(new BigDecimal(1));
    return Optional.of(loadableStudy);
  }

  @Test
  void testSaveSynopticalTable() {
    List<LoadableStudy.SynopticalOhqRecord> list = new ArrayList<>();
    LoadableStudy.SynopticalOhqRecord synopticalOhqRecord =
        LoadableStudy.SynopticalOhqRecord.newBuilder()
            .setPlannedWeight("1")
            .setActualWeight("1")
            .setTankId(1L)
            .build();
    list.add(synopticalOhqRecord);
    List<LoadableStudy.SynopticalCargoRecord> synopticalCargoRecords = new ArrayList<>();
    LoadableStudy.SynopticalCargoRecord synopticalCargoRecord =
        LoadableStudy.SynopticalCargoRecord.newBuilder()
            .setTankId(1L)
            .setActualWeight("1")
            .setPlannedWeight("1")
            .build();
    synopticalCargoRecords.add(synopticalCargoRecord);
    List<LoadableStudy.SynopticalBallastRecord> synopticalBallastRecords = new ArrayList<>();
    LoadableStudy.SynopticalBallastRecord synopticalBallastRecord =
        LoadableStudy.SynopticalBallastRecord.newBuilder()
            .setTankId(1L)
            .setActualWeight("1")
            .build();
    synopticalBallastRecords.add(synopticalBallastRecord);
    List<LoadableStudy.SynopticalRecord> synopticalRecords = new ArrayList<>();
    LoadableStudy.SynopticalRecord synopticalRecord =
        LoadableStudy.SynopticalRecord.newBuilder()
            .setPortId(1L)
            .addAllOhq(list)
            .addAllCargo(synopticalCargoRecords)
            .addAllBallast(synopticalBallastRecords)
            .setId(1L)
            .setDistance("1")
            .setSpeed("1")
            .setBallastActual("1")
            .setRunningHours("1")
            .setInPortHours("1")
            .setTimeOfSunrise("01:15")
            .setTimeOfSunset("01:15")
            .setHwTideFrom("1")
            .setLwTideFrom("1")
            .setHwTideTimeFrom("01:23")
            .setHwTideTimeTo("01:23")
            .setLwTideTimeFrom("01:23")
            .setLwTideTimeTo("01:23")
            .setOthersActual("1")
            .setOthersPlanned("1")
            .setConstantActual("1")
            .setLoadicatorData(
                LoadableStudy.SynopticalTableLoadicatorData.newBuilder()
                    .setCalculatedDraftFwdActual("1")
                    .setCalculatedDraftMidActual("1")
                    .setCalculatedTrimActual("1")
                    .setBlindSector("1")
                    .setDeflection("1")
                    .setCalculatedDraftAftActual("1")
                    .build())
            .setSpecificGravity("1")
            .setEtaEtdEstimated("03-07-2021 11:23")
            .setEtaEtdActual("03-07-2021 11:23")
            .setConstantPlanned("1")
            .setTotalDwtActual("1")
            .setTotalDwtPlanned("1")
            .setDisplacementActual("1")
            .setDisplacementPlanned("1")
            .build();
    synopticalRecords.add(synopticalRecord);
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder()
            .addAllSynopticalRecord(synopticalRecords)
            .setLoadablePatternId(1L)
            .setLoadableStudyId(1L)
            .build();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLP());
    Mockito.when(
            this.synopticalTableRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOST());
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(synopticalTableRepository.save(Mockito.any())).thenReturn(getOST().get());
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.synopticalTableLoadicatorDataRepository
                .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                    Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getSTLDD());
    Mockito.when(
            this.loadablePlanStowageBallastDetailsRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPSBD());
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLong());
    Mockito.when(
            this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
                Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLOBQ());
    Mockito.when(
            this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
                Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLOHQ());
    try {
      var synopticalTableReply =
          this.synopticService.saveSynopticalTable(request, getReplyBuilder());
      assertEquals(SUCCESS, synopticalTableReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<LoadablePattern> getOLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    return Optional.of(loadablePattern);
  }

  private List<LoadablePattern> getLP() {
    List<LoadablePattern> loadablePatterns = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setCaseNumber(1);
    loadablePattern.setLoadableStudy(getLS());
    loadablePattern.setLoadableStudyStatus(1L);
    loadablePatterns.add(loadablePattern);
    return loadablePatterns;
  }

  private SynopticalTableLoadicatorData getSTLDD() {
    SynopticalTableLoadicatorData synopticalTableLoadicatorData =
        new SynopticalTableLoadicatorData();
    synopticalTableLoadicatorData.setId(1L);
    return synopticalTableLoadicatorData;
  }

  private List<Long> getLong() {
    List<Long> lo = new ArrayList<>();
    lo.add(1L);
    return lo;
  }

  private List<OnBoardQuantity> getLOBQ() {
    List<OnBoardQuantity> onBoardQuantities = new ArrayList<>();
    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    onBoardQuantity.setTankId(1L);
    onBoardQuantities.add(onBoardQuantity);
    return onBoardQuantities;
  }

  @Test
  void testBuildSynopticalTableEntity() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    SynopticalTable entity = new SynopticalTable();
    entity.setLoadableStudyXId(1L);
    LoadableStudy.SynopticalRecord record =
        LoadableStudy.SynopticalRecord.newBuilder()
            .setDistance("1")
            .setSpeed("1")
            .setRunningHours("1")
            .setInPortHours("1")
            .setTimeOfSunset("06:25")
            .setTimeOfSunrise("11:19")
            .setHwTideFrom("1")
            .setHwTideTo("1")
            .setLwTideFrom("1")
            .setLwTideTo("1")
            .setHwTideTimeFrom("06:25")
            .setHwTideTimeTo("06:25")
            .setLwTideTimeFrom("06:25")
            .setLwTideTimeTo("06:25")
            .setOthersPlanned("1")
            .setOthersActual("2")
            .setConstantPlanned("1")
            .setConstantActual("1")
            .setTotalDwtPlanned("1")
            .setTotalDwtActual("1")
            .setDisplacementPlanned("1")
            .setDisplacementActual("1")
            .setEtaEtdActual("03-07-2021 11:23")
            .build();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    LocalTime sunrise = LocalTime.from(dtf.parse(record.getTimeOfSunrise()));
    try {
      var synopticalTable = this.synopticService.buildSynopticalTableEntity(entity, record);
      assertEquals(new BigDecimal(1), entity.getSpeed());
      assertEquals(sunrise, entity.getTimeOfSunrise());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveSynopticalEtaEtdEstimates() {
    SynopticalTable entity = new SynopticalTable();
    entity.setLoadableStudyPortRotation(getLSPR());
    entity.setOperationType("1");
    entity.setLoadableStudyXId(1L);
    LoadableStudy.SynopticalRecord record =
        LoadableStudy.SynopticalRecord.newBuilder()
            .setSpecificGravity("1")
            .setEtaEtdEstimated("03-07-2021 11:23")
            .build();
    LocalDateTime etaEtdActual =
        LocalDateTime.from(
            DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(record.getEtaEtdEstimated()));
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    try {
      this.synopticService.saveSynopticalEtaEtdEstimates(entity, record);
      assertEquals(etaEtdActual, entity.getLoadableStudyPortRotation().getEtd());
      assertEquals(new BigDecimal(1), entity.getLoadableStudyPortRotation().getSeaWaterDensity());
      Mockito.verify(loadableStudyPortRotationRepository)
          .save(Mockito.any(LoadableStudyPortRotation.class));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveSynopticalLoadicatorData() {
    SynopticalTable entity = new SynopticalTable();
    Long loadablepatternId = 1L;
    LoadableStudy.SynopticalRecord record =
        LoadableStudy.SynopticalRecord.newBuilder()
            .setBallastActual("1")
            .setLoadicatorData(
                LoadableStudy.SynopticalTableLoadicatorData.newBuilder()
                    .setDeflection("1")
                    .setCalculatedDraftFwdActual("1")
                    .setCalculatedDraftFwdActual("1")
                    .setCalculatedDraftMidActual("1")
                    .setCalculatedTrimActual("1")
                    .setBlindSector("1")
                    .build())
            .build();
    Mockito.when(
            this.synopticalTableLoadicatorDataRepository
                .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                    Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getSTLD());
    try {
      this.synopticService.saveSynopticalLoadicatorData(entity, loadablepatternId, record);
      Mockito.verify(synopticalTableLoadicatorDataRepository)
          .save(Mockito.any(SynopticalTableLoadicatorData.class));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildSynopticalTableEtaEtdActuals() {
    SynopticalTable entity = new SynopticalTable();
    entity.setOperationType("ARR");
    LoadableStudy.SynopticalRecord record =
        LoadableStudy.SynopticalRecord.newBuilder().setEtaEtdActual("03-07-2021 11:23").build();
    LocalDateTime etaEtdActual =
        LocalDateTime.from(
            DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(record.getEtaEtdActual()));
    this.synopticService.buildSynopticalTableEtaEtdActuals(entity, record);
    assertEquals(etaEtdActual, entity.getEtaActual());
  }

  @Test
  void testGetPortInfo() {
    SynopticService spyService = spy(SynopticService.class);
    PortInfo.GetPortInfoByPortIdsRequest portRequest =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder().build();
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);

    var result = spyService.getPortInfo(portRequest);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailForSynopticalTable() {
    SynopticService spyService = spy(SynopticService.class);
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(vesselInfoGrpcService.getVesselDetailForSynopticalTable(
            any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    var result = spyService.getVesselDetailForSynopticalTable(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void getSynopticDataByLoadableStudyId() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().setLoadableStudyId(1L).build();
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(getCO());
    when(loadableStudyPortRotationService.getLastPortRotationId(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    Mockito.when(
            loadableStudyPortRotationRepository.findLastPort(
                Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getObj());
    Mockito.when(loadableStudyPortRotationRepository.getOne(Mockito.anyLong()))
        .thenReturn(getLSPR());
    Mockito.when(
            synopticalTableRepository.findByloadableStudyPortRotation(Mockito.any(), Mockito.any()))
        .thenReturn(getPST());
    doReturn(vesselReply)
        .when(spyService)
        .getSynopticalTableVesselData(
            any(LoadableStudy.SynopticalTableRequest.class),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
    doReturn(getPortReply()).when(spyService).getSynopticalTablePortDetails(anyList());
    doReturn(getLLSPR())
        .when(spyService)
        .getSynopticalTablePortRotations(any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
    doNothing()
        .when(spyService)
        .buildSynopticalTableReply(
            any(LoadableStudy.SynopticalTableRequest.class),
            anyList(),
            any(PortInfo.PortReply.class),
            anyList(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyList(),
            any(VesselInfo.VesselLoadableQuantityDetails.class),
            anyList(),
            any(LoadableStudy.SynopticalTableReply.Builder.class));

    ReflectionTestUtils.setField(
        spyService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationService", loadableStudyPortRotationService);
    ReflectionTestUtils.setField(spyService, "cargoOperationRepository", cargoOperationRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);

    var result = spyService.getSynopticDataByLoadableStudyId(request, getReplyBuilder());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void getSynopticalTablePortDetails() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    doNothing()
        .when(spyService)
        .buildPortIdsRequestSynoptical(
            any(PortInfo.GetPortInfoByPortIdsRequest.Builder.class), anyList());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    var result = spyService.getSynopticalTablePortDetails(getSynopticalTableList());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void getSynopticalTableVesselData() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    doReturn(vesselReply)
        .when(spyService)
        .getVesselDetailForSynopticalTable(any(VesselInfo.VesselRequest.class));

    var result = spyService.getSynopticalTableVesselData(getSynopticalTableRequest(), getLS());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void getSynopticalTable() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    LoadableStudy.SynopticalTableReply.Builder replyBuilder = getReplyBuilder();
    List<LoadableStudy.LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadableStudy.LoadablePattern loadablePattern =
        LoadableStudy.LoadablePattern.newBuilder()
            .setLoadablePatternStatusId(2l)
            .setLoadablePatternId(1l)
            .build();
    loadablePatternList.add(loadablePattern);

    LoadableStudy.LoadablePatternReply.Builder lpReply =
        LoadableStudy.LoadablePatternReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllLoadablePattern(loadablePatternList);
    when(loadablePatternService.getLoadablePatternList(
            any(com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.class),
            any(com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder.class)))
        .thenReturn(lpReply);
    when(this.loadablePatternRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(getOLS());
    doReturn(getOLS()).when(spyService).checkDischargeStarted(anyLong(), anyLong());
    when(synpoticServiceUtils.getsynopticalTableList(anyLong(), anyLong()))
        .thenReturn(getSynopticalTableList());
    doReturn(getVesselReply())
        .when(spyService)
        .getSynopticalTableVesselData(
            any(LoadableStudy.SynopticalTableRequest.class),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
    doReturn(getPortReply()).when(spyService).getSynopticalTablePortDetails(anyList());
    doReturn(getLLSPR())
        .when(spyService)
        .getSynopticalTablePortRotations(any(com.cpdss.loadablestudy.entity.LoadableStudy.class));

    doNothing()
        .when(spyService)
        .buildSynopticalTableReply(
            any(LoadableStudy.SynopticalTableRequest.class),
            anyList(),
            any(PortInfo.PortReply.class),
            anyList(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyList(),
            any(VesselInfo.VesselLoadableQuantityDetails.class),
            anyList(),
            any(LoadableStudy.SynopticalTableReply.Builder.class));

    ReflectionTestUtils.setField(spyService, "loadablePatternService", loadablePatternService);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(spyService, "synpoticServiceUtils", synpoticServiceUtils);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternRepository", loadablePatternRepository);
    spyService.getSynopticalTable(getSynopticalTableRequest(), replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void buildSynopticalTableReply() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().build();
    List<LoadablePatternCargoDetails> cargoes = new ArrayList<>();

    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(getLOBQ());
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(LoadableStudyPortRotation.class),
            anyBoolean()))
        .thenReturn(getLOHQ());
    doNothing()
        .when(spyService)
        .populateOnHandQuantityData(any(Optional.class), any(LoadableStudyPortRotation.class));
    when(this.onHandQuantityRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getLOHQ());
    when(this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(cargoes);
    when(this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getLPSBD());
    when(this.loadablePlanCommingleDetailsPortwiseRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getPortwiseDetailsList());
    doNothing()
        .when(spyService)
        .buildSynopticalRecord(
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.Builder.class),
            any(PortInfo.PortReply.class));
    doNothing()
        .when(spyService)
        .setSynopticalEtaEtdEstimated(
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.Builder.class),
            anyList());
    doNothing()
        .when(spyService)
        .setSynopticalCargoDetails(
            any(LoadableStudy.SynopticalTableRequest.class),
            anyList(),
            anyList(),
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.Builder.class),
            anyList(),
            anyLong(),
            any(Voyage.class),
            anyList(),
            anyList());
    doNothing()
        .when(spyService)
        .setSynopticalOhqData(
            anyList(),
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.Builder.class),
            anyList());
    doNothing()
        .when(spyService)
        .setSynopticalTableVesselParticulars(
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.Builder.class),
            any(VesselInfo.VesselLoadableQuantityDetails.class));
    doNothing()
        .when(spyService)
        .setSynopticalTableLoadicatorData(
            any(SynopticalTable.class),
            anyLong(),
            any(LoadableStudy.SynopticalRecord.Builder.class));
    doNothing()
        .when(synpoticServiceUtils)
        .setBallastDetails(
            any(LoadableStudy.SynopticalTableRequest.class),
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.Builder.class),
            anyList(),
            anyList());
    doNothing()
        .when(synpoticServiceUtils)
        .setPortDetailForSynoptics(
            any(SynopticalTable.class), any(LoadableStudy.SynopticalRecord.Builder.class));
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getOLS());
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "onBoardQuantityRepository", onBoardQuantityRepository);
    ReflectionTestUtils.setField(spyService, "synpoticServiceUtils", synpoticServiceUtils);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanCommingleDetailsPortwiseRepository",
        loadablePlanCommingleDetailsPortwiseRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanStowageBallastDetailsRepository",
        loadablePlanStowageBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(spyService, "onHandQuantityRepository", onHandQuantityRepository);

    LoadableStudy.SynopticalTableReply.Builder builder = getReplyBuilder();
    spyService.buildSynopticalTableReply(
        request,
        getSynopticalTableList(),
        getPortReply(),
        getLLSPR(),
        getLS(),
        getVesselTankDetailList(),
        getVesselDetails(),
        Arrays.asList(1l),
        builder);
    assertFalse(builder.getSynopticalRecordsList().isEmpty());
  }

  @Test
  void testBuildPortOperationsTable() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    List<LoadablePatternCargoDetails> cargoes = new ArrayList<>();
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    List<Long> list = new ArrayList<>(Arrays.asList(1l));

    when(loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getLPSBD());
    when(onHandQuantityRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getLOHQ());
    when(synopticalTableRepository.findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
            anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getOST());
    when(this.synopticalTableLoadicatorDataRepository
            .findBySynopticalTableAndLoadablePatternIdAndIsActive(any(), anyLong(), anyBoolean()))
        .thenReturn(getSTLD());
    when(synpoticServiceUtils.getTimezoneConvertedString(any(), anyLong(), anyString()))
        .thenReturn("1");
    when(synpoticServiceUtils.getTimezoneConvertedDate(any(), anyLong(), anyBoolean()))
        .thenReturn(LocalDate.now());
    when(vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    doCallRealMethod()
        .when(loadableStudyPortRotationService)
        .buildLoadableStudyPortRotationDetails(
            anyLong(),
            any(com.cpdss.loadablestudy.domain.LoadableStudy.class),
            any(ModelMapper.class));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getLLSPR());
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getOLS());
    doCallRealMethod()
        .when(loadableStudyPortRotationService)
        .buildportRotationDetails(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(com.cpdss.loadablestudy.domain.LoadableStudy.class));
    doReturn(list)
        .when(loadableStudyPortRotationService)
        .getPortRoationPortIds(any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
    doReturn(getPortReply())
        .when(loadableStudyPortRotationService)
        .getPortInfo(any(PortInfo.GetPortInfoByPortIdsRequest.class));
    when(loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(cargoes);

    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableStudyPortRotationRepository",
        loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationService", loadableStudyPortRotationService);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(spyService, "synpoticServiceUtils", synpoticServiceUtils);
    ReflectionTestUtils.setField(
        spyService,
        "synopticalTableLoadicatorDataRepository",
        synopticalTableLoadicatorDataRepository);
    ReflectionTestUtils.setField(
        spyService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(spyService, "onHandQuantityRepository", onHandQuantityRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanStowageBallastDetailsRepository",
        loadablePlanStowageBallastDetailsRepository);

    var result = spyService.buildPortOperationsTable(1l, 1l);
    assertTrue(!(result.getOperationsTableList().isEmpty()));
  }

  private VesselInfo.VesselReply getVesselReply() {
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVesselTanks(getVesselTankDetailList())
            .setVesselLoadableQuantityDetails(
                VesselInfo.VesselLoadableQuantityDetails.newBuilder()
                    .setVesselLightWeight("1")
                    .build())
            .build();
    return vesselReply;
  }

  @Test
  void testGetSynopticalDataByPortId() throws GenericServiceException {
    SynopticService spyService = spy(SynopticService.class);
    List<LoadableStudy.TankList> tankLists = new ArrayList<>();

    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
                Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyLong()))
        .thenReturn(getST());
    doReturn(getVesselReply())
        .when(spyService)
        .getSynopticalTableVesselData(
            any(LoadableStudy.SynopticalTableRequest.class),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
    doNothing()
        .when(spyService)
        .buildSynopticalTableReply(
            any(LoadableStudy.SynopticalTableRequest.class),
            anyList(),
            any(PortInfo.PortReply.class),
            anyList(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyList(),
            any(VesselInfo.VesselLoadableQuantityDetails.class),
            anyList(),
            any(LoadableStudy.SynopticalTableReply.Builder.class));
    when(onHandQuantityService.groupTanks(anyList())).thenReturn(tankLists);
    doReturn(getPortReply()).when(spyService).getSynopticalTablePortDetails(anyList());
    doReturn(getLLSPR())
        .when(spyService)
        .getSynopticalTablePortRotations(any(com.cpdss.loadablestudy.entity.LoadableStudy.class));

    ReflectionTestUtils.setField(spyService, "onHandQuantityService", onHandQuantityService);
    ReflectionTestUtils.setField(
        spyService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);

    var result =
        spyService.getSynopticalDataByPortId(getSynopticalTableRequest(), getReplyBuilder());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private PortInfo.PortReply getPortReply() {
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder()
            .setAverageTideHeight("1")
            .setCode("1")
            .setWaterDensity("1")
            .setId(1l)
            .setName("1")
            .setTideHeight("1")
            .setCountryName("1")
            .setTimezoneId(1l)
            .setTimezoneOffsetVal("1")
            .setTimezoneAbbreviation("1")
            .build();
    portDetailList.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(portDetailList)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return portReply;
  }

  private LoadableStudy.SynopticalTableReply.Builder getReplyBuilder() {
    LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        LoadableStudy.SynopticalTableReply.newBuilder();
    return replyBuilder;
  }

  private LoadableStudy.SynopticalTableRequest getSynopticalTableRequest() {
    List<LoadableStudy.SynopticalRecord> recordList = new ArrayList<>();
    LoadableStudy.SynopticalRecord record =
        LoadableStudy.SynopticalRecord.newBuilder().setPortRotationId(1l).build();
    recordList.add(record);
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(1L)
            .setLoadablePatternId(1l)
            .setVoyageId(1L)
            .addAllSynopticalRecord(recordList)
            .setPortId(1L)
            .setVesselId(1l)
            .build();
    return request;
  }

  private List<SynopticalTable> getSynopticalTableList() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    synopticalTable.setPortXid(1L);
    synopticalTable.setOperationType("1");
    synopticalTable.setLoadableStudyPortRotation(getLSPR());
    synopticalTableList.add(synopticalTable);
    return synopticalTableList;
  }

  @Test
  @Disabled
  void testUpdateSynopticalTable() throws GenericServiceException {
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    doCallRealMethod()
        .when(voyageService)
        .fetchActiveVoyageByVesselId(
            any(LoadableStudy.ActiveVoyage.Builder.class), anyLong(), anyLong());
    Mockito.when(
            this.voyageRepository.findActiveVoyagesByVesselId(Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLV());
    List<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies = new ArrayList<>();
    loadableStudies.add(getOLS().get());
    Mockito.when(this.loadableStudyRepository.findByListOfVoyage(List.of(1L)))
        .thenReturn(loadableStudies);
    Mockito.when(cargoNominationService.getCargoNominations(Mockito.anyLong()))
        .thenReturn(getLCN());
    Mockito.when(
            loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLP());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "cargoNominationService", cargoNominationService);
    ReflectionTestUtils.setField(
        voyageService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(voyageService, "formatter", formatter);

    when(loadableStudyRepository.findById(anyLong())).thenReturn(getOLS());
    when(synopticalTableRepository.findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
            anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getOST());
    doNothing()
        .when(synpoticServiceUtils)
        .saveSynopticalBallastData(
            anyLong(), any(LoadableStudy.SynopticalRecord.class), any(SynopticalTable.class));
    doNothing()
        .when(synpoticServiceUtils)
        .saveSynopticalCargoData(
            any(LoadableStudy.SynopticalTableRequest.class),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.class),
            anyBoolean());
    doNothing()
        .when(synpoticServiceUtils)
        .saveSynopticalOhqData(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(SynopticalTable.class),
            any(LoadableStudy.SynopticalRecord.class),
            anyBoolean(),
            anyBoolean());

    synopticService.updateSynopticalTable(getSynopticalTableRequest(), responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
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
    cargoNomination.setLastModifiedDateTime(LocalDateTime.now());
    list.add(cargoNomination);
    return list;
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
    Set<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies = new HashSet<>();
    loadableStudies.add(getLS());
    voyage.setLoadableStudies(loadableStudies);
    voyages.add(voyage);
    return voyages;
  }

  private VoyageStatus getVS() {
    VoyageStatus voyageStatus = new VoyageStatus();
    voyageStatus.setName("ACTIVE");
    voyageStatus.setId(1L);
    return voyageStatus;
  }
}
