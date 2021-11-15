/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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

  @MockBean private SynopticServiceUtils synopticServiceUtils;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final String ETA_ETD_FORMAT = "dd-MM-yyyy HH:mm";

  //    @Test
  //    void testFetchLoadingInformationSynopticDetails() {
  //        LoadableStudy.LoadingPlanIdRequest request =
  // LoadableStudy.LoadingPlanIdRequest.newBuilder().setId(1L).setOperationType("1").setIdType("PORT_ROTATION")
  //                .setPatternId(1L).setPortRotationId(1L).setPortId(1L).build();
  //        LoadableStudy.LoadingPlanCommonResponse.Builder builder =
  // LoadableStudy.LoadingPlanCommonResponse.newBuilder();
  //        Common.ResponseStatus.Builder repBuilder = Common.ResponseStatus.newBuilder();
  //
  // Mockito.when(synopticalTableRepository.findAllByPortRotationId(Mockito.anyLong())).thenReturn(getST());
  //
  // Mockito.when(this.loadablePlanQuantityRepository.PORT_WISE_CARGO_DETAILS(Mockito.anyLong(),
  // Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
  //                .thenReturn(getLPQ());
  //        Mockito.when(cargoNominationRepository.findByIdAndIsActive(Mockito.anyLong(),
  // Mockito.anyBoolean())).thenReturn(getCN());
  //
  // Mockito.when(this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndPortRotationIdAndIsActive(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
  //                .thenReturn(getLPSBD());
  //
  // ReflectionTestUtils.setField(synopticServiceUtils,"synopticalTableRepository",this.synopticalTableRepository);
  //
  // ReflectionTestUtils.setField(synopticServiceUtils,"loadablePlanQuantityRepository",this.loadablePlanQuantityRepository);
  //
  // ReflectionTestUtils.setField(synopticServiceUtils,"cargoNominationRepository",this.cargoNominationRepository);
  //
  // ReflectionTestUtils.setField(synopticServiceUtils,"loadablePlanStowageBallastDetailsRepository",this.loadablePlanStowageBallastDetailsRepository);
  //        try {
  //            this.synopticService.fetchLoadingInformationSynopticDetails(request, builder,
  // repBuilder);
  //            assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

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
    return loadableStudy;
  }

  // need grpc
  @Test
  void testGetSynopticDataByLoadableStudyId() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().setLoadableStudyId(1L).build();
    LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        LoadableStudy.SynopticalTableReply.newBuilder();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(getCO());
    Mockito.when(
            loadableStudyPortRotationRepository.findLastPort(
                Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getObj());
    Mockito.when(loadableStudyPortRotationRepository.getOne(Mockito.anyLong()))
        .thenReturn(getLSPR());
    Mockito.when(
            synopticalTableRepository.findByloadableStudyPortRotation(Mockito.any(), Mockito.any()))
        .thenReturn(getPST());
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

  // need grpc
  @Test
  void testGetSynopticalTablePortDetails() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    synopticalTable.setPortXid(1L);
    synopticalTableList.add(synopticalTable);
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
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    return loadableStudyPortRotations;
  }

  @Test
  void testpopulateOnHandQuantityData() throws GenericServiceException {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setId(1L);
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        Optional.ofNullable(loadableStudy);
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setPortOrder(1L);
    portRotation.setId(1L);
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
        .thenReturn(loadableStudyOpt);
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
    //    this.synopticService.populateOnHandQuantityData(loadableStudyOpt, portRotation);
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

  @Test
  void testSetSynopticalCargoDetails() {
    List<Long> patternIds = new ArrayList<>();
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().build();
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetails =
        new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails loadablePatternCargoDetails =
        new LoadablePatternCargoDetails();
    loadablePatternCargoDetails.setCargoId(1L);
    loadablePatternCargoDetails.setOperationType("1");
    loadablePatternCargoDetails.setPortRotationId(1L);
    cargoDetails.add(loadablePatternCargoDetails);
    List<OnBoardQuantity> obqEntities = new ArrayList<>();
    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    onBoardQuantity.setTankId(1L);
    onBoardQuantity.setActualArrivalWeight(new BigDecimal(1));
    onBoardQuantity.setPlannedArrivalWeight(new BigDecimal(1));
    onBoardQuantity.setDensity(new BigDecimal(1));
    onBoardQuantity.setAbbreviation("1");
    obqEntities.add(onBoardQuantity);
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
    List<LoadablePlanComminglePortwiseDetails> commingleCargoDetails = new ArrayList<>();
    LoadablePlanComminglePortwiseDetails loadablePlanComminglePortwiseDetails =
        new LoadablePlanComminglePortwiseDetails();
    loadablePlanComminglePortwiseDetails.setApi("1");
    loadablePlanComminglePortwiseDetails.setPortRotationXid(1L);
    loadablePlanComminglePortwiseDetails.setOperationType("1");
    commingleCargoDetails.add(loadablePlanComminglePortwiseDetails);
    this.synopticService.setSynopticalCargoDetails(
        request,
        cargoDetails,
        obqEntities,
        synopticalEntity,
        builder,
        sortedTankList,
        firstPortId,
        voyage,
        commingleCargoDetails,
        patternIds);
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
    List<VesselInfo.VesselTankDetail> sortedTankList = new ArrayList<>();
    VesselInfo.VesselTankDetail vesselTankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setShortName("1")
            .setTankCategoryShortName("1")
            .setFullCapacityCubm("1")
            .setTankId(1L)
            .setTankCategoryId(5L)
            .setShowInOhqObq(true)
            .build();
    sortedTankList.add(vesselTankDetail);
    this.synopticService.setSynopticalOhqData(
        ohqEntities, synopticalEntity, builder, sortedTankList);
    assertEquals("1", builder.getOhq(0).getActualWeight());
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
    VesselInfo.VesselLoadableQuantityDetails vesselLoadableQuantityDetails =
        VesselInfo.VesselLoadableQuantityDetails.newBuilder()
            .setHasLoadicator(true)
            .setDisplacmentDraftRestriction("1")
            .setConstant("1")
            .setDwt("1")
            .build();
    this.synopticService.setSynopticalTableVesselParticulars(
        synopticalEntity, builder, vesselLoadableQuantityDetails);
    assertEquals("1", builder.getOthersPlanned());
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
    LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        LoadableStudy.SynopticalTableReply.newBuilder();
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
      var synopticalTableReply = this.synopticService.saveSynopticalTable(request, replyBuilder);
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

  //   @Test
  //    void testValidateSynopticalVesselData() {
  //       com.cpdss.loadablestudy.entity.LoadableStudy loadablestudy = new
  // com.cpdss.loadablestudy.entity.LoadableStudy();
  //       SynopticalTable entity = new SynopticalTable();
  //       LoadableStudy.SynopticalRecord record =
  // LoadableStudy.SynopticalRecord.newBuilder().build();
  //       Mockito.when(this.loadablePatternRepository.findLoadablePatterns(Mockito.anyLong(),
  // Mockito.any(), Mockito.anyBoolean())).thenReturn(getLP());
  //       Mockito.when(this.loadablePatternRepository.findLoadablePatterns(Mockito.anyLong(),
  // Mockito.any(), Mockito.anyBoolean())).thenReturn(getLP());
  //       try {
  //        this.synopticService.validateSynopticalVesselData(loadablestudy,entity,record);
  //       } catch (GenericServiceException e) {
  //           e.printStackTrace();
  //       }
  //   }

  // need grpc
  @Test
  void testGetSynopticalDataByPortId() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(1L)
            .setVoyageId(1L)
            .setPortId(1L)
            .build();
    LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        LoadableStudy.SynopticalTableReply.newBuilder();
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
  }
}
