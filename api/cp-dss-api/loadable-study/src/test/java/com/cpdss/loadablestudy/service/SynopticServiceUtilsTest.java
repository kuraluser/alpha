/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      SynopticServiceUtils.class,
    })
public class SynopticServiceUtilsTest {
  @Autowired SynopticServiceUtils synopticServiceUtils;

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
  @MockBean LoadablePlanService loadablePlanService;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean DischargePatternQuantityCargoPortwiseRepository disCargoQuantityRepository;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testSaveSynopticalBallastData() {
    List<LoadablePlanStowageBallastDetails> ballastDetailsList = new ArrayList<>();
    LoadablePlanStowageBallastDetails ballastDetails = new LoadablePlanStowageBallastDetails();
    ballastDetails.setTankXId(1l);
    ballastDetailsList.add(ballastDetails);
    when(this.loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(ballastDetailsList);
    synopticServiceUtils.saveSynopticalBallastData(1l, getSynopticalRecord(), getSynopticalTable());
    verify(this.loadablePlanStowageBallastDetailsRepository).saveAll(anyList());
  }

  @Test
  void testSaveSynopticalCargoData() throws GenericServiceException {
    LoadablePattern loadablePattern = new LoadablePattern();
    List<Long> list = new ArrayList<>(Arrays.asList(1l, 2l));
    when(loadableStudyPortRotationService.getPortRoationPortIds(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(list);
    when(this.loadablePatternRepository.getOne(anyLong())).thenReturn(loadablePattern);
    when(this.loadablePlanCommingleDetailsPortwiseRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getCommingleCargoEntities());
    when(this.loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getcargoDetailsList());
    synopticServiceUtils.saveSynopticalCargoData(
        getSynopticalTableRequest(),
        getLoadableStudyEntity(),
        getSynopticalTable(),
        getSynopticalRecord(),
        true);
    verify(this.loadablePlanCommingleDetailsPortwiseRepository).saveAll(anyList());
  }

  @Test
  void testSaveSynopticalCargoByLoadablePattern() {
    LoadablePattern loadablePattern = new LoadablePattern();
    when(this.loadablePatternRepository.getOne(anyLong())).thenReturn(loadablePattern);
    when(this.loadablePlanCommingleDetailsPortwiseRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getCommingleCargoEntities());
    when(this.loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getcargoDetailsList());
    synopticServiceUtils.saveSynopticalCargoByLoadablePattern(
        getSynopticalTableRequest(), getSynopticalTable(), getSynopticalRecord());
    verify(this.loadablePlanCommingleDetailsPortwiseRepository).saveAll(anyList());
  }

  @Test
  void testvalidateSaveSynopticalOhqData() throws GenericServiceException {
    OnHandQuantity ohqEntity = new OnHandQuantity();
    ohqEntity.setArrivalQuantity(new BigDecimal(1));
    ohqEntity.setDepartureQuantity(new BigDecimal(1));
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePatternList.add(loadablePattern);
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternList);
    synopticServiceUtils.validateSaveSynopticalOhqData(
        ohqEntity,
        getSynopticalTable(),
        LoadableStudy.SynopticalOhqRecord.newBuilder().setPlannedWeight("1").build(),
        getLoadableStudyEntity());
  }

  @Test
  void testSaveSynopticalOhqData() throws GenericServiceException {
    List<OnHandQuantity> ohqList = new ArrayList<>();
    OnHandQuantity ohqEntity = new OnHandQuantity();
    ohqEntity.setTankXId(1l);
    ohqList.add(ohqEntity);
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(LoadableStudyPortRotation.class),
            anyBoolean()))
        .thenReturn(ohqList);
    synopticServiceUtils.saveSynopticalOhqData(
        getLoadableStudyEntity(), getSynopticalTable(), getSynopticalRecord(), true);
    verify(this.onHandQuantityRepository).saveAll(anyList());
  }

  @Test
  void testsaveSynopticalObqData() throws GenericServiceException {
    List<OnBoardQuantity> obqList = new ArrayList<>();
    OnBoardQuantity obqEntity = new OnBoardQuantity();
    obqEntity.setTankId(1l);
    obqList.add(obqEntity);
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(obqList);
    synopticServiceUtils.saveSynopticalObqData(
        getLoadableStudyEntity(), getSynopticalRecord(), true);
    verify(this.onBoardQuantityRepository).saveAll(anyList());
  }

  @Test
  void testValidateSaveSynopticalObqData() throws GenericServiceException {
    List<OnBoardQuantity> obqList = new ArrayList<>();
    OnBoardQuantity obqEntity = new OnBoardQuantity();
    obqEntity.setTankId(1l);
    obqList.add(obqEntity);
    obqEntity.setPlannedArrivalWeight(new BigDecimal(1));
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePatternList.add(loadablePattern);
    synopticServiceUtils.validateSaveSynopticalObqData(
        obqEntity,
        LoadableStudy.SynopticalCargoRecord.newBuilder().setPlannedWeight("1").build(),
        getLoadableStudyEntity());
  }

  @Test
  void testbuildBallastDetailsBasedOnPort() {
    LoadableStudy.LoadingPlanIdRequest request =
        LoadableStudy.LoadingPlanIdRequest.newBuilder().setId(1l).setPatternId(1l).build();
    LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    com.cpdss.common.generated.Common.ResponseStatus.Builder repBuilder =
        Common.ResponseStatus.newBuilder();
    when(this.loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndIsActive(
                anyLong(), anyLong(), anyBoolean()))
        .thenReturn(getBallastDetails());
    synopticServiceUtils.buildBallastDetailsBasedOnPort(request, builder, repBuilder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
    assertEquals(1l, builder.getLoadablePlanBallastDetails(0).getTankId());
  }

  @Test
  void testBuildPortRotationResponse() throws GenericServiceException {
    LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    com.cpdss.common.generated.Common.ResponseStatus.Builder repBuilder =
        Common.ResponseStatus.newBuilder();
    List<SynopticalTable> list = new ArrayList<>();
    list.add(getSynopticalTable());
    when(synopticalTableRepository.findAllByPortRotationId(anyLong())).thenReturn(list);
    synopticServiceUtils.buildPortRotationResponse(1l, builder, repBuilder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testbuildCargoToBeDischargedFroPort() {
    LoadableStudy.LoadingPlanIdRequest request =
        LoadableStudy.LoadingPlanIdRequest.newBuilder()
            .setId(1l)
            .setPatternId(1l)
            .setPortId(1l)
            .setCargoNominationFilter(true)
            .build();
    LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    com.cpdss.common.generated.Common.ResponseStatus.Builder repBuilder =
        Common.ResponseStatus.newBuilder();
    List<DischargePatternQuantityCargoPortwiseDetails> disList = new ArrayList<>();
    DischargePatternQuantityCargoPortwiseDetails details =
        new DischargePatternQuantityCargoPortwiseDetails();
    details.setDischargeCargoNominationId(1l);
    details.setPortId(1l);
    details.setId(1l);
    details.setEstimatedAPI(new BigDecimal(1));
    details.setEstimatedTemp(new BigDecimal(1));
    details.setCargoNominationTemperature(new BigDecimal(1));
    details.setDischargeMT(new BigDecimal(1));
    details.setDifferencePercentage(new BigDecimal(1));
    details.setCargoId(1l);
    details.setCargoAbbreviation("1");
    details.setColorCode("1");
    details.setPriority(1);
    details.setSlopQuantity(new BigDecimal(1));
    details.setLoadingOrder(1);
    details.setOrderedQuantity(new BigDecimal(1));
    details.setDischargeCargoNominationId(1l);
    details.setCargoNominationId(1l);
    details.setTimeRequiredForDischarging(new BigDecimal(1));
    disList.add(details);

    List<CargoNomination> cargoNominations = new ArrayList<>();
    CargoNomination cargo = new CargoNomination();
    cargo.setId(1l);
    Set<CargoNominationPortDetails> portDetailsSet = new HashSet<>();
    CargoNominationPortDetails portDetails = new CargoNominationPortDetails();
    portDetails.setPortId(1l);
    portDetails.setCargoNomination(cargo);
    portDetailsSet.add(portDetails);
    cargo.setCargoNominationPortDetails(portDetailsSet);
    cargoNominations.add(cargo);

    when(this.disCargoQuantityRepository.findAllByLoadablePatternIdAndIsActiveTrue(anyLong()))
        .thenReturn(disList);
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderById(
            anyLong(), anyBoolean()))
        .thenReturn(cargoNominations);
    when(cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());

    synopticServiceUtils.buildCargoToBeDischargedFroPort(request, builder, repBuilder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testgetCargoNominationIdList() {
    LoadablePattern lp = new LoadablePattern();
    lp.setLoadableStudy(getLoadableStudyEntity());
    when(loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(lp));
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderById(
            anyLong(), anyBoolean()))
        .thenReturn(getCargoNominations());

    var result = synopticServiceUtils.getCargoNominationIdList(1l, 1l);
    assertTrue(result.contains(1l));
  }

  @Test
  void testbuildCargoToBeLoadedForPort() {
    LoadableStudy.LoadingPlanIdRequest request =
        LoadableStudy.LoadingPlanIdRequest.newBuilder()
            .setId(1l)
            .setPatternId(1l)
            .setPortId(1l)
            .setPortRotationId(1l)
            .setOperationType("1")
            .setCargoNominationFilter(true)
            .build();
    LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    com.cpdss.common.generated.Common.ResponseStatus.Builder repBuilder =
        Common.ResponseStatus.newBuilder();
    LoadablePattern lp = new LoadablePattern();
    lp.setLoadableStudy(getLoadableStudyEntity());
    when(loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(lp));
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderById(
            anyLong(), anyBoolean()))
        .thenReturn(getCargoNominations());
    when(this.loadablePlanQuantityRepository.PORT_WISE_CARGO_DETAILS(
            anyLong(), anyString(), anyLong(), anyLong()))
        .thenReturn(getPlanQuantityList());
    synopticServiceUtils.buildCargoToBeLoadedForPort(request, builder, repBuilder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testgetCargoNominationQuantity() {
    when(cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getCargoNominations().get(0)));

    var result = synopticServiceUtils.getCargoNominationQuantity(1l);
    assertEquals(new BigDecimal(1), result);
  }

  @Test
  void testgetCargoNominationQuantityWithException() {
    when(cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(new RuntimeException());

    var result = synopticServiceUtils.getCargoNominationQuantity(1l);
    assertEquals(new BigDecimal(0), result);
  }

  @Test
  void testbuildSynopticalTableRecord() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    synopticalTableList.add(getSynopticalTable());
    synopticServiceUtils.buildSynopticalTableRecord(
        1l, getSynopticalTable().getLoadableStudyPortRotation(), synopticalTableList, "1");
    assertEquals(2, synopticalTableList.size());
  }

  @Test
  void testsetBallastDetails() {
    com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
    List<VesselInfo.VesselTankDetail> sortedTankList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankCategoryId(2l)
            .setTankId(1l)
            .setShortName("1")
            .setFullCapacityCubm("1")
            .build();
    sortedTankList.add(tankDetail);
    synopticServiceUtils.setBallastDetails(
        getSynopticalTableRequest(),
        getSynopticalTable(),
        builder,
        getBallastDetails(),
        sortedTankList);
    assertEquals(1l, builder.getBallast(0).getTankId());
  }

  @Test
  void testsetPortDetailForSynoptics() {
    SynopticServiceUtils spyService = spy(SynopticServiceUtils.class);
    com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail portDetail = PortInfo.PortDetail.newBuilder().setTimezoneId(1l).build();
    list.add(portDetail);
    PortInfo.PortReply reply = PortInfo.PortReply.newBuilder().addAllPorts(list).build();
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    spyService.setPortDetailForSynoptics(getSynopticalTable(), builder);
    assertEquals(1l, builder.getPortTimezoneId());
  }

  @Test
  void testgetTimezoneConvertedDate() {
    var result = synopticServiceUtils.getTimezoneConvertedDate(LocalDate.now(), 1, true);
    assertEquals(LocalDate.now().getDayOfMonth(), result.getDayOfMonth());
  }

  @Test
  void testgetTimezoneConvertedDateTime() {
    var result = synopticServiceUtils.getTimezoneConvertedDate(LocalDateTime.now(), 1);
    assertEquals(LocalDate.now().getDayOfMonth(), result.getDayOfMonth());
  }

  @Test
  void testgetTimezoneConvertedString() {
    var result = synopticServiceUtils.getTimezoneConvertedString(LocalDateTime.now(), 1, "1");
    System.out.println(result);
    assertTrue(result.contains(String.valueOf(LocalDate.now().getDayOfMonth())));
  }

  @Test
  void testgetsynopticalTableList() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    synopticalTableList.add(getSynopticalTable());
    when(this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveOrderByPortOrder(
            anyLong(), anyBoolean()))
        .thenReturn(synopticalTableList);
    var result = synopticServiceUtils.getsynopticalTableList(1l, 1l);
    assertEquals(1l, result.get(0).getId());
  }

  @Test
  void testbuildSynopticalPortDataReplyByPortId() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    synopticalTableList.add(getSynopticalTable());
    com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.newBuilder();
    synopticServiceUtils.buildSynopticalPortDataReplyByPortId(synopticalTableList, replyBuilder);
    assertEquals("1", replyBuilder.getSynopticalRecords(0).getOperationType());
  }

  @Test
  void testValidateSaveSynopticalEtaEtdEstimates() throws GenericServiceException {
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePatternList.add(loadablePattern);
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternList);
    synopticServiceUtils.validateSaveSynopticalEtaEtdEstimates(
        getSynopticalTable(),
        getSynopticalRecord(),
        getLoadableStudyEntity(),
        getSynopticalTable().getLoadableStudyPortRotation());
  }

  @Test
  void testbuidlSynopticalTableVesselData() throws GenericServiceException {
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePatternList.add(loadablePattern);
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternList);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudyEntity()));
    var entity = getSynopticalTable();
    synopticServiceUtils.buidlSynopticalTableVesselData(entity, getSynopticalRecord());
    assertEquals(new BigDecimal(1), entity.getOthersActual());
  }

  private List<LoadablePlanStowageBallastDetails> getBallastDetails() {
    List<LoadablePlanStowageBallastDetails> ballastDetails = new ArrayList<>();
    LoadablePlanStowageBallastDetails details = new LoadablePlanStowageBallastDetails();
    details.setColorCode("1");
    details.setCorrectionFactor("1");
    details.setFillingPercentage("1");
    details.setTankXId(1l);
    details.setPortXId(1l);
    details.setOperationType("1");
    details.setQuantity(new BigDecimal(1));
    details.setActualQuantity(new BigDecimal(1));
    details.setCorrectionFactor("1");
    details.setCorrectedUllage("1");
    details.setColorCode("1");
    details.setFillingPercentage("1");
    ballastDetails.add(details);
    return ballastDetails;
  }

  private List<LoadablePlanQuantity> getPlanQuantityList() {
    List<LoadablePlanQuantity> list = new ArrayList<>();
    LoadablePlanQuantity quantity = new LoadablePlanQuantity();
    quantity.setId(1l);
    quantity.setGrade("1");
    quantity.setEstimatedApi(new BigDecimal(1));
    quantity.setEstimatedTemperature(new BigDecimal(1));
    quantity.setCargoNominationTemperature(new BigDecimal(1));
    quantity.setOrderBbls60f("1");
    quantity.setOrderBblsDbs("1");
    quantity.setMinTolerence("1");
    quantity.setMaxTolerence("1");
    quantity.setLoadableBbls60f("1");
    quantity.setLoadableBblsDbs("1");
    quantity.setLoadableKl("1");
    quantity.setLoadableMt("1");
    quantity.setLoadableLt("1");
    quantity.setDifferencePercentage("1");
    quantity.setDifferenceColor("1");
    quantity.setCargoColor("1");
    quantity.setCargoXId(1l);
    quantity.setCargoAbbreviation("1");
    quantity.setPriority(1);
    quantity.setLoadingOrder(1);
    quantity.setSlopQuantity("1");
    quantity.setOrderQuantity(new BigDecimal(1));
    quantity.setCargoNominationId(1l);
    quantity.setTimeRequiredForLoading("1");
    quantity.setOrderBblsDbs("1");
    quantity.setOrderBblsDbs("1");
    list.add(quantity);
    return list;
  }

  private List<CargoNomination> getCargoNominations() {
    List<CargoNomination> cargoNominations = new ArrayList<>();
    CargoNomination cargo = new CargoNomination();
    cargo.setId(1l);
    cargo.setCargoXId(1l);
    cargo.setQuantity(new BigDecimal(1));
    Set<CargoNominationPortDetails> portDetailsSet = new HashSet<>();
    CargoNominationPortDetails portDetails = new CargoNominationPortDetails();
    portDetails.setPortId(1l);
    portDetails.setCargoNomination(cargo);
    portDetailsSet.add(portDetails);
    cargo.setCargoNominationPortDetails(portDetailsSet);
    cargoNominations.add(cargo);
    return cargoNominations;
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLoadableStudyEntity() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1l);
    return loadableStudy;
  }

  private List<LoadablePlanComminglePortwiseDetails> getCommingleCargoEntities() {
    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        commingleCargoEntities = new ArrayList<>();
    LoadablePlanComminglePortwiseDetails portwiseDetails =
        new LoadablePlanComminglePortwiseDetails();
    portwiseDetails.setTankId(1l);
    portwiseDetails.setId(1l);
    portwiseDetails.setCargo2NominationId(1l);

    commingleCargoEntities.add(portwiseDetails);
    return commingleCargoEntities;
  }

  private List<LoadablePatternCargoDetails> getcargoDetailsList() {
    List<LoadablePatternCargoDetails> cargoDetailsList = new ArrayList<>();
    LoadablePatternCargoDetails cargoDetails = new LoadablePatternCargoDetails();
    cargoDetailsList.add(cargoDetails);
    return cargoDetailsList;
  }

  private com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest
      getSynopticalTableRequest() {
    com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request =
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.newBuilder()
            .setLoadablePatternId(1l)
            .build();
    return request;
  }

  private com.cpdss.common.generated.LoadableStudy.SynopticalRecord getSynopticalRecord() {
    List<LoadableStudy.SynopticalBallastRecord> ballastRecordList = new ArrayList<>();
    LoadableStudy.SynopticalBallastRecord ballastRecord =
        LoadableStudy.SynopticalBallastRecord.newBuilder()
            .setActualWeight("1")
            .setTankId(1l)
            .build();
    ballastRecordList.add(ballastRecord);

    List<LoadableStudy.SynopticalCargoRecord> list = new ArrayList<>();
    LoadableStudy.SynopticalCargoRecord cargoRecord =
        LoadableStudy.SynopticalCargoRecord.newBuilder()
            .setIsCommingleCargo(true)
            .setActualWeight("1")
            .setTankId(1l)
            .build();
    list.add(cargoRecord);

    List<LoadableStudy.SynopticalOhqRecord> ohqRecordList = new ArrayList<>();
    LoadableStudy.SynopticalOhqRecord ohqRecord =
        LoadableStudy.SynopticalOhqRecord.newBuilder()
            .setTankId(1l)
            .setActualWeight("1")
            .setPlannedWeight("1")
            .build();
    ohqRecordList.add(ohqRecord);

    com.cpdss.common.generated.LoadableStudy.SynopticalRecord record =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder()
            .addAllCargo(list)
            .addAllBallast(ballastRecordList)
            .setEtaEtdEstimated("1")
            .setOthersPlanned("1")
            .setOthersActual("1")
            .setConstantActual("1")
            .setConstantPlanned("1")
            .setTotalDwtActual("1")
            .setTotalDwtPlanned("1")
            .setDisplacementActual("1")
            .setDisplacementPlanned("1")
            .addAllOhq(ohqRecordList)
            .build();

    return record;
  }

  private SynopticalTable getSynopticalTable() {
    SynopticalTable entity = new SynopticalTable();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setId(1l);
    CargoOperation operation = new CargoOperation();
    operation.setId(1l);
    portRotation.setOperation(operation);
    portRotation.setEta(LocalDateTime.now());
    portRotation.setEtd(LocalDateTime.now());
    portRotation.setLoadableStudy(getLoadableStudyEntity());
    entity.setLoadableStudyPortRotation(portRotation);
    entity.setPortXid(1l);
    entity.setOperationType("1");
    entity.setDistance(new BigDecimal(1));
    entity.setEtaActual(LocalDateTime.now());
    entity.setEtdActual(LocalDateTime.now());
    entity.setLoadableStudyXId(1l);
    entity.setId(1l);
    entity.setTimeOfSunrise(LocalTime.now());
    entity.setTimeOfSunSet(LocalTime.now());

    return entity;
  }
}
