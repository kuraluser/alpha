/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.discharge_plan.DischargeInformation;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

  @MockBean
  private DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub
      dischargeInformationGrpcService;

  @MockBean
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  @MockBean CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

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
  void testSaveSynopticalBallastDataElse() {
    List<LoadablePlanStowageBallastDetails> ballastDetailsList = new ArrayList<>();
    LoadablePlanStowageBallastDetails ballastDetails = new LoadablePlanStowageBallastDetails();
    ballastDetails.setTankXId(2l);
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
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(getOnBoardQuantities());

    synopticServiceUtils.saveSynopticalCargoData(
        getSynopticalTableRequest(),
        getLoadableStudyEntity(),
        getSynopticalTable(),
        getSynopticalRecord(),
        true);
    verify(this.onBoardQuantityRepository).saveAll(anyList());
  }

  @Test
  void testSaveSynopticalCargoDataElseIf() throws GenericServiceException {
    LoadablePattern loadablePattern = new LoadablePattern();
    List<Long> list = new ArrayList<>(Arrays.asList(1l, 2l));
    SynopticalTable synopticalTable = getSynopticalTable();
    synopticalTable.setOperationType("1");

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
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(getOnBoardQuantities());

    synopticServiceUtils.saveSynopticalCargoData(
        getSynopticalTableRequest(),
        getLoadableStudyEntity(),
        synopticalTable,
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

  @ParameterizedTest
  @ValueSource(longs = {1l, 2l})
  void testSaveSynopticalCargoByLoadablePatternElse(Long val) {
    LoadablePattern loadablePattern = new LoadablePattern();
    List<LoadableStudy.SynopticalCargoRecord> list = new ArrayList<>();
    LoadableStudy.SynopticalCargoRecord cargoRecord =
        LoadableStudy.SynopticalCargoRecord.newBuilder()
            .setIsCommingleCargo(false)
            .setActualWeight("1")
            .setPlannedWeight("1")
            .setTankId(1l)
            .setUllage("1")
            .setActualApi("1")
            .setActualTemperature("1")
            .build();
    list.add(cargoRecord);
    LoadableStudy.SynopticalRecord record =
        LoadableStudy.SynopticalRecord.newBuilder().addAllCargo(list).build();
    LoadablePatternCargoDetails cargoDetails = new LoadablePatternCargoDetails();
    cargoDetails.setTankId(val);

    when(this.loadablePatternRepository.getOne(anyLong())).thenReturn(loadablePattern);
    when(this.loadablePlanCommingleDetailsPortwiseRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getCommingleCargoEntities());
    when(this.loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(Arrays.asList(cargoDetails));
    synopticServiceUtils.saveSynopticalCargoByLoadablePattern(
        getSynopticalTableRequest(), getSynopticalTable(), record);
    verify(this.loadablePatternCargoDetailsRepository).saveAll(anyList());
  }

  @Test
  void testValidateSaveSynopticalOhqData() throws GenericServiceException {
    OnHandQuantity ohqEntity = new OnHandQuantity();
    ohqEntity.setArrivalQuantity(new BigDecimal(1));
    ohqEntity.setDepartureQuantity(new BigDecimal(1));
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePatternList.add(loadablePattern);
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternList);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                synopticServiceUtils.validateSaveSynopticalOhqData(
                    ohqEntity,
                    getSynopticalTable(),
                    LoadableStudy.SynopticalOhqRecord.newBuilder().setPlannedWeight("2").build(),
                    getLoadableStudyEntity()));

    assertAll(
        () ->
            assertEquals(
                CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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
        getLoadableStudyEntity(), getSynopticalTable(), getSynopticalRecord(), true, false);
    verify(this.onHandQuantityRepository).saveAll(anyList());
  }

  private List<LoadablePattern> getLoadablePatternList() {
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setLoadableStudy(getLoadableStudyEntity());
    loadablePatternList.add(loadablePattern);
    return loadablePatternList;
  }

  @Test
  void testSaveSynopticalOhqDataElse() throws GenericServiceException {
    List<OnHandQuantity> ohqList = new ArrayList<>();
    OnHandQuantity ohqEntity = new OnHandQuantity();
    ohqEntity.setTankXId(2l);
    ohqList.add(ohqEntity);
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getLoadablePatternList());
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(LoadableStudyPortRotation.class),
            anyBoolean()))
        .thenReturn(ohqList);
    synopticServiceUtils.saveSynopticalOhqData(
        getLoadableStudyEntity(), getSynopticalTable(), getSynopticalRecord(), false, false);
    verify(this.onHandQuantityRepository).saveAll(anyList());
  }

  @Test
  void testSaveSynopticalObqData() throws GenericServiceException {
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(getOnBoardQuantities());
    synopticServiceUtils.saveSynopticalObqData(
        getLoadableStudyEntity(), getSynopticalRecord(), true);
    verify(this.onBoardQuantityRepository).saveAll(anyList());
  }

  @Test
  void testSaveSynopticalObqDataElse() throws GenericServiceException {
    OnBoardQuantity obqEntity = getOnBoardQuantities().get(0);
    obqEntity.setTankId(3l);
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(obqEntity));
    synopticServiceUtils.saveSynopticalObqData(
        getLoadableStudyEntity(), getSynopticalRecord(), true);
    verify(this.onBoardQuantityRepository).saveAll(anyList());
  }

  private List<OnBoardQuantity> getOnBoardQuantities() {
    List<OnBoardQuantity> obqList = new ArrayList<>();
    OnBoardQuantity obqEntity = new OnBoardQuantity();
    obqEntity.setTankId(1l);
    obqEntity.setPlannedArrivalWeight(new BigDecimal(1));
    obqList.add(obqEntity);
    return obqList;
  }

  @Test
  void testValidateSaveSynopticalObqData() throws GenericServiceException {
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getLoadablePatternList());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                synopticServiceUtils.validateSaveSynopticalObqData(
                    getOnBoardQuantities().get(0),
                    LoadableStudy.SynopticalCargoRecord.newBuilder().setPlannedWeight("2").build(),
                    getLoadableStudyEntity()));

    assertAll(
        () ->
            assertEquals(
                CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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

  private List<DischargePatternQuantityCargoPortwiseDetails> getDisPortwiseDetails() {
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
    details.setOperationType("1");
    details.setOrderedQuantity(new BigDecimal(1));
    details.setDischargeCargoNominationId(1l);
    details.setCargoNominationId(1l);
    details.setTimeRequiredForDischarging(new BigDecimal(1));
    disList.add(details);
    return disList;
  }

  @Test
  void testBuildCargoToBeDischargedFroPort() {
    LoadableStudy.LoadingPlanIdRequest request =
        LoadableStudy.LoadingPlanIdRequest.newBuilder()
            .setId(1l)
            .setPatternId(1l)
            .setPortId(1l)
            .setOperationType("1")
            .setCargoNominationFilter(true)
            .build();
    LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    com.cpdss.common.generated.Common.ResponseStatus.Builder repBuilder =
        Common.ResponseStatus.newBuilder();

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
        .thenReturn(getDisPortwiseDetails());
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderById(
            anyLong(), anyBoolean()))
        .thenReturn(cargoNominations);
    when(cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadablePatternList().get(0)));

    DischargeInformation dischargeInformation =
        DischargeInformation.newBuilder().setDischargeInfoId(0L).build();
    when(dischargeInformationGrpcService.getCargoToBeDischarged(any()))
        .thenReturn(dischargeInformation);
    ReflectionTestUtils.setField(
        synopticServiceUtils, "dischargeInformationGrpcService", dischargeInformationGrpcService);

    synopticServiceUtils.buildCargoToBeDischargedFroPort(request, builder, repBuilder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testValidateSynopticalVesselData() throws GenericServiceException {
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(getLoadablePatternList());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                synopticServiceUtils.validateSynopticalVesselData(
                    getLoadableStudyEntity(), getSynopticalTable(), getSynopticalRecord()));

    assertAll(
        () ->
            assertEquals(
                CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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
  void testSetLoadingPortNameFromCargoOperation() {
    LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
    when(cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getCargoNominations().get(0)));
    when(loadablePlanService.fetchPortNameFromPortService(
            anyLong(), any(LoadableStudyPortRotation.class)))
        .thenReturn(LoadableStudy.LoadingPortDetail.newBuilder());

    var result = synopticServiceUtils.setLoadingPortNameFromCargoOperation(1l, 1l, builder);
    assertEquals(true, result);
  }

  @Test
  void testSaveSynopticalCommingleData() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().setLoadablePatternId(1l).build();
    when(loadablePlanCommingleDetailsPortwiseRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getCommingleCargoEntities());

    synopticServiceUtils.saveSynopticalCommingleData(
        request, Optional.of(getSynopticalTable()), getSynopticalRecord());
    verify(this.loadablePlanCommingleDetailsPortwiseRepository).saveAll(anyList());
  }

  @Test
  void testSaveSynopticalCommingleDataElse() {
    LoadableStudy.SynopticalTableRequest request =
        LoadableStudy.SynopticalTableRequest.newBuilder().setLoadablePatternId(1l).build();
    LoadablePlanComminglePortwiseDetails portwiseDetails = getCommingleCargoEntities().get(0);
    portwiseDetails.setTankId(2l);
    when(loadablePlanCommingleDetailsPortwiseRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(portwiseDetails));
    when(loadablePatternRepository.findById(anyLong()))
        .thenReturn(Optional.of(getLoadablePatternList().get(0)));

    synopticServiceUtils.saveSynopticalCommingleData(
        request, Optional.of(getSynopticalTable()), getSynopticalRecord());
    verify(this.loadablePlanCommingleDetailsPortwiseRepository).saveAll(anyList());
  }

  @Test
  void testBuildCargoToBeLoadedForPort() {
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
    when(this.loadablePlanCommingleDetailsPortwiseRepository
            .findCargoNominationIdsByPatternPortAndOperationType(anyLong(), anyLong(), anyString()))
        .thenReturn(getCommingleCargoEntities());
    when(this.loadablePlanQuantityRepository.findByPatternIdAndCargoNominationId(
            anyLong(), anyLong()))
        .thenReturn(getPlanQuantityList());

    LoadingPlanModels.LoadingInformation loadingInformation =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    when(loadingInfoServiceBlockingStub.getCargoToBeLoaded(any())).thenReturn(loadingInformation);

    ReflectionTestUtils.setField(
        synopticServiceUtils, "loadingInfoServiceBlockingStub", loadingInfoServiceBlockingStub);

    ReflectionTestUtils.setField(
        synopticServiceUtils,
        "commingleDetailsPortWiseRepository",
        loadablePlanCommingleDetailsPortwiseRepository);

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
  void testBuildSynopticalPortDataReplyByPortId() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    synopticalTableList.add(getSynopticalTable());
    com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.newBuilder();

    synopticServiceUtils.buildSynopticalPortDataReplyByPortId(synopticalTableList, replyBuilder);
    assertEquals("ARR", replyBuilder.getSynopticalRecords(0).getOperationType());
  }

  @Test
  void testValidateSaveSynopticalEtaEtdEstimates() throws GenericServiceException {
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePatternList.add(loadablePattern);
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternList);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                synopticServiceUtils.validateSaveSynopticalEtaEtdEstimates(
                    getSynopticalTable(),
                    getSynopticalRecord(),
                    getLoadableStudyEntity(),
                    getSynopticalTable().getLoadableStudyPortRotation()));

    assertAll(
        () ->
            assertEquals(
                CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testbuidlSynopticalTableVesselData() throws GenericServiceException {
    List<LoadablePattern> loadablePatternList = new ArrayList<>();
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
    portDetails.setPortRotation(getPortRotation());
    portDetailsSet.add(portDetails);
    cargo.setCargoNominationPortDetails(portDetailsSet);
    cargoNominations.add(cargo);
    return cargoNominations;
  }

  private LoadableStudyPortRotation getPortRotation() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(2L);
    loadableStudyPortRotation.setOperation(getCargoOp());
    loadableStudyPortRotation.setEta(LocalDateTime.now());
    loadableStudyPortRotation.setEtd(LocalDateTime.now());
    loadableStudyPortRotation.setLayCanTo(LocalDate.now());
    loadableStudyPortRotation.setLayCanFrom(LocalDate.now());
    loadableStudyPortRotation.setPortXId(1l);
    return loadableStudyPortRotation;
  }

  private CargoOperation getCargoOp() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1L);
    return cargoOperation;
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
    portwiseDetails.setCargo1Mt("1");
    portwiseDetails.setCargo2Mt("1");
    portwiseDetails.setCargo1Abbreviation("1");
    portwiseDetails.setCargo2Abbreviation("1");
    portwiseDetails.setLoadingOrder(1);
    portwiseDetails.setApi("1");
    portwiseDetails.setTemperature("1");
    portwiseDetails.setCargo2NominationId(1l);
    portwiseDetails.setCargo1NominationId(1l);
    commingleCargoEntities.add(portwiseDetails);
    return commingleCargoEntities;
  }

  private List<LoadablePatternCargoDetails> getcargoDetailsList() {
    List<LoadablePatternCargoDetails> cargoDetailsList = new ArrayList<>();
    LoadablePatternCargoDetails cargoDetails = new LoadablePatternCargoDetails();
    cargoDetails.setTankId(1l);
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

  private List<LoadableStudy.SynopticalCargoRecord> getCargoRecordList() {
    List<LoadableStudy.SynopticalCargoRecord> list = new ArrayList<>();
    LoadableStudy.SynopticalCargoRecord cargoRecord =
        LoadableStudy.SynopticalCargoRecord.newBuilder()
            .setIsCommingleCargo(true)
            .setActualWeight("1")
            .setPlannedWeight("1")
            .setTankId(1l)
            .setUllage("1")
            .setActualApi("1")
            .setActualTemperature("1")
            .build();
    list.add(cargoRecord);
    return list;
  }

  private com.cpdss.common.generated.LoadableStudy.SynopticalRecord getSynopticalRecord() {
    List<LoadableStudy.SynopticalBallastRecord> ballastRecordList = new ArrayList<>();
    LoadableStudy.SynopticalBallastRecord ballastRecord =
        LoadableStudy.SynopticalBallastRecord.newBuilder()
            .setActualWeight("1")
            .setTankId(1l)
            .build();
    ballastRecordList.add(ballastRecord);

    List<LoadableStudy.SynopticalOhqRecord> ohqRecordList = new ArrayList<>();
    LoadableStudy.SynopticalOhqRecord ohqRecord =
        LoadableStudy.SynopticalOhqRecord.newBuilder()
            .setTankId(1l)
            .setActualWeight("1")
            .setPlannedWeight("1")
            .build();
    ohqRecordList.add(ohqRecord);
    LoadableStudy.SynopticalCommingleRecord commingleRecord =
        LoadableStudy.SynopticalCommingleRecord.newBuilder()
            .setActualWeight("1")
            .setTankId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.SynopticalRecord record =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder()
            .addAllCargo(getCargoRecordList())
            .addAllBallast(ballastRecordList)
            .setEtaEtdEstimated("2021-12-15T15:42")
            .setOthersPlanned("1")
            .setOthersActual("1")
            .setConstantActual("1")
            .setConstantPlanned("1")
            .setTotalDwtActual("1")
            .setTotalDwtPlanned("1")
            .setDisplacementActual("1")
            .setDisplacementPlanned("1")
            .addAllOhq(ohqRecordList)
            .addAllCommingle(Arrays.asList(commingleRecord))
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
    portRotation.setEta(LocalDateTime.of(2021, 12, 15, 15, 42));
    portRotation.setEtd(LocalDateTime.of(2021, 12, 15, 15, 42));
    portRotation.setLoadableStudy(getLoadableStudyEntity());
    entity.setLoadableStudyPortRotation(portRotation);
    entity.setPortXid(1l);
    entity.setOperationType("ARR");
    entity.setDistance(new BigDecimal(1));
    entity.setEtaActual(LocalDateTime.now());
    entity.setEtdActual(LocalDateTime.now());
    entity.setLoadableStudyXId(1l);
    entity.setId(1l);
    entity.setTimeOfSunrise(LocalTime.now());
    entity.setTimeOfSunSet(LocalTime.now());
    entity.setOthersPlanned(new BigDecimal(2));
    entity.setConstantPlanned(new BigDecimal(2));
    return entity;
  }
}
