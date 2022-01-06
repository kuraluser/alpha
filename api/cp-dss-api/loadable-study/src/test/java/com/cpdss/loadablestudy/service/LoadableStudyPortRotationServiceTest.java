/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      LoadableStudyPortRotationService.class,
    })
public class LoadableStudyPortRotationServiceTest {
  @Autowired LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean LoadableQuantityRepository loadableQuantityRepository;
  @MockBean CargoOperationRepository cargoOperationRepository;
  @MockBean VoyageService voyageService;
  @MockBean SynopticService synopticService;
  @MockBean LoadableStudyService studyService;
  @MockBean LoadablePatternService loadablePatternService;
  @MockBean SynopticalTableRepository synopticalTableRepository;
  @MockBean BackLoadingService backLoadingService;
  @MockBean PortInstructionService portInstructionService;
  @MockBean CowDetailService cowDetailService;
  @MockBean OnHandQuantityService onHandQuantityService;
  @MockBean VoyageRepository voyageRepository;
  @MockBean LoadablePatternRepository loadablePatternRepository;
  @MockBean EntityManager entityManager;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @MockBean private CargoNominationService cargoNominationService;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testFindMaxPortOrderForLoadableStudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    LoadableStudyPortRotation maxPortOrderEntity = new LoadableStudyPortRotation();
    maxPortOrderEntity.setPortOrder(1l);
    when(this.loadableStudyPortRotationRepository
            .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(
                any(LoadableStudy.class), anyBoolean()))
        .thenReturn(maxPortOrderEntity);
    var result = loadableStudyPortRotationService.findMaxPortOrderForLoadableStudy(loadableStudy);
    assertEquals(1l, result);
  }

  @Test
  void testSetPortOrdering() {
    LoadableStudy loadableStudy = new LoadableStudy();
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1l);
    portRotation.setOperation(cargoOperation);
    loadableStudyPortRotations.add(portRotation);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    loadableStudyPortRotationService.setPortOrdering(loadableStudy);
    Mockito.verify(this.loadableStudyPortRotationRepository).saveAll(Mockito.anyList());
  }

  @Test
  void testSetPortOrderingElse() {
    LoadableStudy loadableStudy = new LoadableStudy();
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(2l);
    portRotation.setOperation(cargoOperation);
    loadableStudyPortRotations.add(portRotation);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    loadableStudyPortRotationService.setPortOrdering(loadableStudy);
    Mockito.verify(this.loadableStudyPortRotationRepository).saveAll(Mockito.anyList());
  }

  @Test
  void testValidateTransitPorts() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy = new LoadableStudy();
    List<Long> requestedPortIds = new ArrayList<>();
    requestedPortIds.add(1l);
    when(this.loadableStudyPortRotationRepository.getTransitPorts(
            any(LoadableStudy.class), Mockito.anyList()))
        .thenReturn(requestedPortIds);
    try {
      loadableStudyPortRotationService.validateTransitPorts(loadableStudy, requestedPortIds);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetLastPortRotationId() {
    LoadableStudy loadableStudy = new LoadableStudy();
    CargoOperation loading = new CargoOperation();
    Object ob[] = {1l, 1l};
    when(loadableStudyPortRotationRepository.findLastPort(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(ob);
    var result = loadableStudyPortRotationService.getLastPortRotationId(loadableStudy, loading);
    assertEquals(1l, result);
  }

  @Test
  void testGetLastPortRotationData() {
    LoadableStudy loadableStudy = new LoadableStudy();
    CargoOperation loading = new CargoOperation();
    Object ob[] = {new Object()};
    when(loadableStudyPortRotationRepository.findLastPort(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(ob);
    loadableStudyPortRotationService.getLastPortRotationData(loadableStudy, loading, true);
    verify(loadableStudyPortRotationRepository)
        .findLastPort(any(LoadableStudy.class), any(CargoOperation.class), anyBoolean());
  }

  @Test
  void testGetLastPort() {
    LoadableStudy loadableStudy = new LoadableStudy();
    CargoOperation loading = new CargoOperation();
    Object ob[] = {1l, 1l};
    when(loadableStudyPortRotationRepository.findLastPort(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(ob);
    var result = loadableStudyPortRotationService.getLastPort(loadableStudy, loading);
    assertEquals(1l, result);
  }

  @Test
  void testBuildLoadableStudyPortRotationDetails() {
    long loadableStudyId = 1l;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    ModelMapper modelMapper = new ModelMapper();
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    loadableStudyPortRotations.add(getLoadableStudyPortRotation());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            Mockito.anyLong(), anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    loadableStudyPortRotationService.buildLoadableStudyPortRotationDetails(
        loadableStudyId, loadableStudy, modelMapper);
    assertFalse(loadableStudy.getLoadableStudyPortRotation().isEmpty());
  }

  @Test
  void testGetPortRoationPortIds() {
    LoadableStudy loadableStudy = new LoadableStudy();
    List<Long> portIds = new ArrayList<>();
    portIds.add(1l);
    portIds.add(1l);
    portIds.add(2l);
    portIds.add(2l);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(portIds);
    var result = loadableStudyPortRotationService.getPortRoationPortIds(loadableStudy);
    System.out.println(result);
    List list = new ArrayList(Arrays.asList(1l, 2l));
    assertEquals(list, result);
  }

  @Test
  void testSaveLoadableStudyPortRotationList() {
    List<com.cpdss.common.generated.LoadableStudy.PortRotationDetail> portRotationDetailList =
        new ArrayList<>();
    portRotationDetailList.add(getPortRotationDetail());
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .addAllPortRotationDetails(portRotationDetailList)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    Optional<LoadableStudy> loadableStudyOpt = Optional.of(getLoadableStudy());
    Optional<LoadableStudyPortRotation> portRotation = Optional.of(getLoadableStudyPortRotation());
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1l);
    cargoOperation.setName("1");
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(loadableStudyOpt);
    when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(voyage);
    when(this.loadableStudyPortRotationRepository.findById(Mockito.anyLong()))
        .thenReturn(portRotation);
    when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(cargoOperation);
    try {
      var result =
          loadableStudyPortRotationService.saveLoadableStudyPortRotationList(request, replyBuilder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"ARR", "1"})
  void testBuildLoadableStudyPortRotationEntity(String str) {
    LoadableStudyPortRotation entity = getLoadableStudyPortRotation();
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1l);
    cargoOperation.setName("1");
    com.cpdss.common.generated.LoadableStudy.PortRotationDetail.Builder builder =
        getPortRotationDetail().toBuilder();
    builder.setOperationType(str);

    when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(cargoOperation);
    loadableStudyPortRotationService.buildLoadableStudyPortRotationEntity(entity, builder.build());
    var result = entity.getSynopticalTable().get(0).getEtaActual();
    LocalDateTime aDateTime = LocalDateTime.of(1999, 11, 21, 11, 11);
    assertEquals(aDateTime, result);
  }

  @Test
  void testBuildLoadableStudyPortRotationEntityNullValues() {
    LoadableStudyPortRotation entity = getLoadableStudyPortRotation();
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1l);
    cargoOperation.setName("1");

    when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(cargoOperation);
    loadableStudyPortRotationService.buildLoadableStudyPortRotationEntity(
        entity, com.cpdss.common.generated.LoadableStudy.PortRotationDetail.newBuilder().build());
    var result = entity.getSynopticalTable().get(0).getEtaActual();
    assertEquals(null, result);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testsaveLoadableStudyPortRotation(Integer i) {
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    LoadableStudy loadableStudy = getLoadableStudy();
    loadableStudy.setPlanningTypeXId(i);
    Optional<LoadableStudyPortRotation> portRoationOpt =
        Optional.of(getLoadableStudyPortRotation());
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    when(loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyBoolean()))
        .thenReturn(generatedPatterns);
    when(this.loadableStudyRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(getLoadableStudy()));
    when(this.loadableStudyPortRotationRepository.findById(Mockito.anyLong()))
        .thenReturn(portRoationOpt);
    when(this.loadableStudyPortRotationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(getLoadableStudyPortRotation());
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(new LoadableStudyPortRotation()));

    try {
      var result =
          loadableStudyPortRotationService.saveLoadableStudyPortRotation(
              getPortRotationDetail(), replyBuilder);
      Mockito.verify(this.synopticalTableRepository).deleteByPortRotationId(Mockito.anyLong());
      Mockito.verify(this.loadableQuantityRepository).deleteByPortRotationId(Mockito.anyLong());
      Mockito.verify(this.loadableStudyPortRotationRepository)
          .updateIsOhqCompleteByIdAndIsActiveTrue(Mockito.anyLong(), anyBoolean());
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<LoadableQuantity> getLoadableQuantities() {
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setTotalQuantity(new BigDecimal(1));
    loadableQuantity.setLoadableStudyPortRotation(getLoadableStudyPortRotation());
    List<LoadableQuantity> quantities = new ArrayList<>();
    quantities.add(loadableQuantity);
    return quantities;
  }

  @Test
  void testGetPortRotationByLoadableStudyId() {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();

    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());

    var result =
        loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
            request, portRotationReplyBuilder);
    assertEquals(FAILED, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetPortRotationByLoadableStudyIdEmptyPorts() {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    List<LoadableStudyPortRotation> ports = new ArrayList<>();

    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(ports);
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudy()));
    when(loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
            Mockito.anyLong(), anyBoolean()))
        .thenReturn(getLoadableQuantities());

    var result =
        loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
            request, portRotationReplyBuilder);
    assertEquals(FAILED, result.getResponseStatus().getStatus());
  }

  private BackLoading getBackLoading() {
    BackLoading backLoading = new BackLoading();
    backLoading.setAbbreviation("1");
    backLoading.setApi(new BigDecimal(1));
    backLoading.setColour("1");
    backLoading.setCargoId(1l);
    backLoading.setId(1l);
    backLoading.setQuantity(new BigDecimal(1));
    backLoading.setTemperature(new BigDecimal(1));
    return backLoading;
  }

  @Test
  void testGetPortRotationByLoadableStudyIdElse() {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();

    Map<Long, List<BackLoading>> backloadingDataByportIds = new HashMap<>();
    backloadingDataByportIds.put(1l, Arrays.asList(getBackLoading()));

    List<DischargeStudyPortInstruction> dischargeStudyPortInstructionList = new ArrayList<>();
    DischargeStudyPortInstruction dischargeStudyPortInstruction =
        new DischargeStudyPortInstruction();
    dischargeStudyPortInstruction.setPortInstructionId(1l);
    dischargeStudyPortInstructionList.add(dischargeStudyPortInstruction);
    Map<Long, List<DischargeStudyPortInstruction>> instructionsForThePort = new HashMap<>();
    instructionsForThePort.put(1l, dischargeStudyPortInstructionList);

    Map<Long, DischargeStudyCowDetail> cowDetails = new HashMap<>();
    DischargeStudyCowDetail dischargeStudyCowDetail = new DischargeStudyCowDetail();
    dischargeStudyCowDetail.setCowType(1l);
    dischargeStudyCowDetail.setPercentage(1l);
    dischargeStudyCowDetail.setTankIds("1");
    cowDetails.put(1l, dischargeStudyCowDetail);

    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(Arrays.asList(getLoadableStudyPortRotation()));
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudy()));
    when(loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
            Mockito.anyLong(), anyBoolean()))
        .thenReturn(getLoadableQuantities());
    when(backLoadingService.getBackloadingDataByportIds(Mockito.anyLong(), Mockito.anyList()))
        .thenReturn(backloadingDataByportIds);
    when(portInstructionService.getPortWiseInstructions(Mockito.anyLong(), Mockito.anyList()))
        .thenReturn(instructionsForThePort);
    when(cowDetailService.getCowDetailForDS(Mockito.anyLong())).thenReturn(dischargeStudyCowDetail);
    var result =
        loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
            request, portRotationReplyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testDeletePortRotationWithGenericException() {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    LoadableStudy loadableStudy = getLoadableStudy();
    LoadableStudyStatus status = new LoadableStudyStatus();
    status.setId(3l);
    loadableStudy.setLoadableStudyStatus(status);
    when(this.loadableStudyRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(loadableStudy));

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                loadableStudyPortRotationService.deletePortRotation(
                    request, portRotationReplyBuilder));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testDeletePortRotationWithGenericException2() {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                loadableStudyPortRotationService.deletePortRotation(
                    request, portRotationReplyBuilder));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @ParameterizedTest
  @ValueSource(longs = {1l, 2l})
  void testDeletePortRotationWithGenericException3(Long l) {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    Optional<LoadableStudy> loadableStudyOpt = Optional.of(getLoadableStudy());
    LoadableStudyPortRotation portRotation = getLoadableStudyPortRotation();
    CargoOperation operation = new CargoOperation();
    operation.setId(l);
    portRotation.setOperation(operation);

    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(loadableStudyOpt);
    when(this.loadableStudyPortRotationRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(portRotation));

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                loadableStudyPortRotationService.deletePortRotation(
                    request, portRotationReplyBuilder));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testDeletePortRotation() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setVesselId(1l)
            .setIsLandingPage(true)
            .setLoadableStudyId(1l)
            .setVoyageId(1l)
            .build();

    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder portRotationReplyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    LoadableStudy loadableStudy = getLoadableStudy();
    loadableStudy.setPlanningTypeXId(2);
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    LoadableStudyPortRotation portRotation = getLoadableStudyPortRotation();
    CargoOperation operation = new CargoOperation();
    operation.setId(3l);
    portRotation.setOperation(operation);

    CargoNomination nomination = new CargoNomination();
    Set<CargoNominationPortDetails> portDetailsSet = new HashSet<>();
    CargoNominationPortDetails portDetails = new CargoNominationPortDetails();
    portDetails.setPortId(1l);
    portDetailsSet.add(portDetails);
    nomination.setCargoNominationPortDetails(portDetailsSet);

    when(this.loadableStudyPortRotationRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(portRotation));
    when(loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyBoolean()))
        .thenReturn(generatedPatterns);
    when(this.loadableStudyRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(voyage);
    when(cargoNominationService.getCargoNominations(anyLong()))
        .thenReturn(Arrays.asList(nomination));

    var result =
        loadableStudyPortRotationService.deletePortRotation(request, portRotationReplyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetPortRotationByPortRotationId() {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder().build();
    com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.newBuilder();
    when(this.loadableStudyPortRotationRepository.findByIdAndIsActive(
            Mockito.anyLong(), anyBoolean()))
        .thenReturn(getLoadableStudyPortRotation());
    try {
      loadableStudyPortRotationService.getPortRotationByPortRotationId(request, builder);
      assertEquals(1l, builder.getPortRotationDetail().getOperationId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetPortRotationByPortRotationIdWithException() throws Exception {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder().build();
    com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.newBuilder();
    when(this.loadableStudyPortRotationRepository.findByIdAndIsActive(
            Mockito.anyLong(), anyBoolean()))
        .thenReturn(null);
    final Exception ex =
        assertThrows(
            Exception.class,
            () ->
                loadableStudyPortRotationService.getPortRotationByPortRotationId(request, builder));
  }

  private PortInfo.PortReply getPortReply() {
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder()
            .setAverageTideHeight("1")
            .setId(1l)
            .setName("1")
            .setLat("1")
            .setLon("1")
            .setCode("1")
            .setWaterDensity("1")
            .setCountryName("1")
            .setTideHeight("!")
            .setTimezoneId(1l)
            .setTimezoneOffsetVal("1")
            .setTimezoneAbbreviation("!")
            .build();
    portDetailList.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(portDetailList)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return portReply;
  }

  @Test
  void testBuildportRotationDetails() {
    LoadableStudyPortRotationService spyService = spy(LoadableStudyPortRotationService.class);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    List<Long> portIds = new ArrayList<>(Arrays.asList(1l));

    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(portIds);
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);

    spyService.buildportRotationDetails(getLoadableStudy(), loadableStudy);
    assertEquals(1l, loadableStudy.getPortDetails().get(0).getId());
  }

  @Test
  void testGetPortInfo() {
    LoadableStudyPortRotationService spyService = spy(LoadableStudyPortRotationService.class);
    PortInfo.GetPortInfoByPortIdsRequest request =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder().build();
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    var result = spyService.getPortInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadableStudyShore() {
    LoadableStudyPortRotationService spyService = spy(LoadableStudyPortRotationService.class);
    com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest.newBuilder().build();
    com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse.newBuilder();

    List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder()
            .setId(1l)
            .setName("1")
            .setImoNumber("1")
            .setFlag("1")
            .build();
    vesselDetailList.add(vesselDetail);

    VesselInfo.VesselRequest vesselRequest = VesselInfo.VesselRequest.newBuilder().build();
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder().setVesselId(1l).addAllVessels(vesselDetailList).build();
    List<com.cpdss.common.generated.LoadableStudy.VoyageDetail> voyageDetailList =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.VoyageDetail voyageDetail =
        com.cpdss.common.generated.LoadableStudy.VoyageDetail.newBuilder()
            .setId(1l)
            .setStatus("Active")
            .build();
    voyageDetailList.add(voyageDetail);
    com.cpdss.common.generated.LoadableStudy.VoyageListReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.VoyageListReply.newBuilder()
            .addAllVoyages(voyageDetailList);
    List<LoadableStudy> list = new ArrayList<>();
    list.add(getLoadableStudy());
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    loadableStudyPortRotations.add(getLoadableStudyPortRotation());

    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    when(loadableStudyRepository.findByVesselXId(anyLong())).thenReturn(list);
    when(voyageService.getVoyagesByVessel(
            any(com.cpdss.common.generated.LoadableStudy.VoyageRequest.class),
            any(com.cpdss.common.generated.LoadableStudy.VoyageListReply.Builder.class)))
        .thenReturn(replyBuilder);
    when(vesselInfoGrpcService.getAllVesselsByCompany(any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    when(voyageService.getActiveVoyagesByVessel(
            any(com.cpdss.common.generated.LoadableStudy.VoyageRequest.class),
            any(com.cpdss.common.generated.LoadableStudy.VoyageListReply.Builder.class)))
        .thenReturn(replyBuilder);

    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "vesselInfoGrpcService", vesselInfoGrpcService);

    loadableStudyPortRotationService.getLoadableStudyShore(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadableStudyShoreWithException() {
    LoadableStudyPortRotationService spyService = spy(LoadableStudyPortRotationService.class);
    com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest.newBuilder().build();
    com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse.newBuilder();

    when(vesselInfoGrpcService.getAllVesselsByCompany(any(VesselInfo.VesselRequest.class)))
        .thenThrow(new RuntimeException("1"));
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "vesselInfoGrpcService", vesselInfoGrpcService);

    loadableStudyPortRotationService.getLoadableStudyShore(request, builder);
    assertTrue(builder.getShoreListList().isEmpty());
  }

  @Test
  void testGetLoadableStudyList() {
    LoadableStudyPortRotationService spyService = spy(LoadableStudyPortRotationService.class);
    com.cpdss.common.generated.LoadableStudy.LoadableStudyReply reply =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.newBuilder().setId(1l).build();
    com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.newBuilder().build();
    when(loadableStudyServiceBlockingStub.findLoadableStudiesByVesselAndVoyage(
            any(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyServiceBlockingStub", loadableStudyServiceBlockingStub);

    spyService.getLoadableStudyList(request);
    verify(loadableStudyServiceBlockingStub)
        .findLoadableStudiesByVesselAndVoyage(
            any(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.class));
  }

  @Test
  void testGetLoadableStudyPortRotation() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setLoadableStudyId(1l)
            .setIsLandingPage(true)
            .setVesselId(1l)
            .setVoyageId(1l)
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    loadableStudyPortRotations.add(getLoadableStudyPortRotation());
    when(loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(getLoadableStudy()));
    when(synopticService.checkDischargeStarted(anyLong(), anyLong()))
        .thenReturn(Optional.of(Arrays.asList(getLoadableStudy())));
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    when(loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
            any(LoadableStudy.class)))
        .thenReturn(Optional.of(getLoadableQuantities().get(0)));
    when(this.cargoOperationRepository.findByIsActiveOrderById(anyBoolean()))
        .thenReturn(Arrays.asList(getLoadableStudyPortRotation().getOperation()));
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "portInfoGrpcService", portInfoGrpcService);

    loadableStudyPortRotationService.getLoadableStudyPortRotation(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  private LoadableStudyPortRotation getLoadableStudyPortRotation() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1l);
    loadableStudyPortRotation.setLoadableStudy(getLoadableStudy());
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1l);
    cargoOperation.setName("1");
    loadableStudyPortRotation.setOperation(cargoOperation);
    loadableStudyPortRotation.setBerthXId(1l);
    loadableStudyPortRotation.setPortXId(1l);
    loadableStudyPortRotation.setIsbackloadingEnabled(true);
    loadableStudyPortRotation.setAirDraftRestriction(new BigDecimal(1));
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    loadableStudyPortRotation.setDistanceBetweenPorts(new BigDecimal(1));
    loadableStudyPortRotation.setTimeOfStay(new BigDecimal(1));
    loadableStudyPortRotation.setMaxDraft(new BigDecimal(1));
    LocalDateTime aDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
    loadableStudyPortRotation.setEta(aDateTime);
    loadableStudyPortRotation.setEtd(aDateTime);
    LocalDate date = LocalDate.of(2001, 2, 21);
    loadableStudyPortRotation.setLayCanFrom(date);
    loadableStudyPortRotation.setLayCanTo(date);
    loadableStudyPortRotation.setPortOrder(1l);
    loadableStudyPortRotation.setEta(LocalDateTime.now());
    loadableStudyPortRotation.setEtd(LocalDateTime.now());

    loadableStudyPortRotation.setPortRotationType("1");
    loadableStudyPortRotation.setFreshCrudeOil(true);
    loadableStudyPortRotation.setFreshCrudeOilQuantity(new BigDecimal(1));
    loadableStudyPortRotation.setFreshCrudeOilTime(new BigDecimal(1));
    loadableStudyPortRotation.setSynopticalTable(getSynopticalTableList());
    return loadableStudyPortRotation;
  }

  private LoadableStudy getLoadableStudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setName("1");
    loadableStudy.setId(1l);
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    loadableStudy.setActive(true);
    loadableStudy.setVoyage(voyage);
    loadableStudy.setVesselXId(1l);
    loadableStudy.setPlanningTypeXId(1);
    loadableStudy.setConfirmedLoadableStudyId(1l);
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("Confirmed");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    return loadableStudy;
  }

  private List<com.cpdss.loadablestudy.entity.SynopticalTable> getSynopticalTableList() {
    List<com.cpdss.loadablestudy.entity.SynopticalTable> synopticalTableList = new ArrayList<>();
    com.cpdss.loadablestudy.entity.SynopticalTable synopticalTable =
        new com.cpdss.loadablestudy.entity.SynopticalTable();
    synopticalTable.setId(1l);
    synopticalTable.setDistance(new BigDecimal(1));
    synopticalTable.setOperationType(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL);
    synopticalTable.setEtaActual(LocalDateTime.now());
    synopticalTable.setEtdActual(LocalDateTime.now());
    synopticalTableList.add(synopticalTable);

    com.cpdss.loadablestudy.entity.SynopticalTable synopticalTable2 =
        new com.cpdss.loadablestudy.entity.SynopticalTable();
    synopticalTable2.setId(1l);
    synopticalTable2.setDistance(new BigDecimal(1));
    synopticalTable2.setOperationType(SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE);
    synopticalTable2.setEtaActual(LocalDateTime.now());
    synopticalTable2.setEtdActual(LocalDateTime.now());
    synopticalTableList.add(synopticalTable2);
    return synopticalTableList;
  }

  private com.cpdss.common.generated.LoadableStudy.PortRotationDetail getPortRotationDetail() {
    com.cpdss.common.generated.LoadableStudy.PortRotationDetail portRotationDetail =
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail.newBuilder()
            .setId(1l)
            .setBerthId(1l)
            .setPortId(2l)
            .setDistanceBetweenPorts("1")
            .setMaxDraft("1")
            .setMaxAirDraft("1")
            .setSeaWaterDensity("1")
            .setTimeOfStay("1")
            .setEta("21-11-1999 11:11")
            .setEtd("21-11-1999 11:11")
            .setLayCanFrom("21-11-1999")
            .setLayCanTo("21-11-1999")
            .setOperationId(1l)
            .setEtaActual("21-11-1999 11:11")
            .setEtdActual("21-11-1999 11:11")
            .setOperationType(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)
            .setLoadableStudyId(1l)
            .setPortOrder(1l)
            .setIsLandingPage(true)
            .build();
    return portRotationDetail;
  }
}
