/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {CargoNominationService.class})
public class CargoNominationServiceTest {

  @Autowired private CargoNominationService cargoNominationService;
  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private CommingleCargoRepository commingleCargoRepository;
  @MockBean private ApiTempHistoryRepository apiTempHistoryRepository;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @MockBean CargoOperationRepository cargoOperationRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private SynopticService synopticService;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private CargoNominationValveSegregationRepository valveSegregationRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private VoyageService voyageService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;
  @MockBean private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanGrpcService;
  @MockBean private CommingleColourRepository commingleColourRepository;

  @MockBean
  private CommingleCargoToDischargePortwiseDetailsRepository
      commingleCargoToDischargePortwiseDetailsRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testGetCargoNominationByLoadableStudyId() {
    Long loadableStudyId = 1L;
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoNomination());
    try {
      var cargo = this.cargoNominationService.getCargoNominationByLoadableStudyId(loadableStudyId);
      assertEquals(cargo.get(0).getCargoXId(), getListCargoNomination().get(0).getCargoXId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetCargoNominationByLoadableStudyIdWithGenericException() {
    Long loadableStudyId = 1L;
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(new ArrayList<CargoNomination>());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.cargoNominationService.getCargoNominationByLoadableStudyId(loadableStudyId));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private List<CargoNomination> getListCargoNomination() {
    List<CargoNomination> cargoNominations = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setId(1L);
    cargoNomination.setCargoXId(1L);
    cargoNomination.setLoadableStudyXId(1L);
    cargoNomination.setPriority(1L);
    cargoNomination.setColor("1");
    cargoNomination.setAbbreviation("1");
    cargoNomination.setCargoXId(1L);
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setCargoNominationPortDetails(getCargoNPDetails());
    cargoNomination.setMaxTolerance(new BigDecimal(1));
    cargoNomination.setMinTolerance(new BigDecimal(1));
    cargoNomination.setSegregationXId(1L);
    cargoNomination.setLsCargoNominationId(1L);
    cargoNominations.add(cargoNomination);
    return cargoNominations;
  }

  @Test
  @Disabled
  void testGetCargoNominations() {
    Long loadableStudyId = 1L;
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoNomination());
    var cargonomination = this.cargoNominationService.getCargoNominations(loadableStudyId);
    assertEquals(
        cargonomination.get(0).getCargoXId(), getListCargoNomination().get(0).getCargoXId());
  }

  @Test
  void testCreateDsCargoNomination() {
    Long dischargeStudyId = 1L;
    CargoNomination cargo = new CargoNomination();
    cargo.setAbbreviation("1");
    cargo.setApi(new BigDecimal(1));
    cargo.setCargoXId(1L);
    cargo.setColor("1");
    cargo.setIsActive(true);
    cargo.setLoadableStudyXId(1L);
    cargo.setMaxTolerance(new BigDecimal(1));
    cargo.setMinTolerance(new BigDecimal(1));
    cargo.setPriority(1L);
    cargo.setQuantity(new BigDecimal(1));
    cargo.setSegregationXId(1L);
    cargo.setTemperature(new BigDecimal(1));
    cargo.setVersion(1L);
    cargo.setLsCargoNominationId(1L);
    cargo.setCargoNominationPortDetails(getCargoNPDetails());
    Long portId = 1L;
    Long operationId = 1L;
    var cargonomination =
        this.cargoNominationService.createDsCargoNomination(
            dischargeStudyId, cargo, portId, operationId, 1);
    assertEquals(cargonomination.getCargoXId(), cargo.getCargoXId());
  }

  private Set<CargoNominationPortDetails> getCargoNPDetails() {
    Set<CargoNominationPortDetails> cargoNominationPortDetails = new HashSet<>();
    CargoNominationPortDetails cargoNominationPortDetails1 = new CargoNominationPortDetails();
    cargoNominationPortDetails1.setId(1L);
    cargoNominationPortDetails1.setIsActive(true);
    cargoNominationPortDetails1.setPortId(1L);
    cargoNominationPortDetails1.setMode(1L);
    cargoNominationPortDetails1.setQuantity(new BigDecimal(1));
    cargoNominationPortDetails.add(cargoNominationPortDetails1);

    CargoNominationPortDetails portDetails = new CargoNominationPortDetails();
    portDetails.setId(1L);
    portDetails.setIsActive(true);
    portDetails.setPortId(3L);
    portDetails.setMode(1L);
    portDetails.setQuantity(new BigDecimal(1));
    cargoNominationPortDetails.add(portDetails);
    return cargoNominationPortDetails;
  }

  @Test
  void testCreateCargoNominationPortDetails() {
    CargoNomination dischargeStudyCargo = new CargoNomination();
    CargoNomination cargo = new CargoNomination();
    cargo.setId(1L);
    cargo.setCargoNominationPortDetails(getCargoNPDetails());
    Long portId = 1L;
    Long operationId = 1L;
    var cargonomination =
        this.cargoNominationService.createCargoNominationPortDetails(
            dischargeStudyCargo, cargo, portId, operationId, 1);
    assertEquals(cargonomination.contains(1L), getCargoNPDetails().contains(1L));
  }

  @Test
  void testCreateCargoNominationPortDetailsElse() {
    CargoNomination dischargeStudyCargo = new CargoNomination();
    Long portId = 1L;
    Long operationId = 1L;
    var cargonomination =
        this.cargoNominationService.createCargoNominationPortDetails(
            dischargeStudyCargo, dischargeStudyCargo, null, portId, 1);
    assertEquals(cargonomination.contains(1L), getCargoNPDetails().contains(1L));
  }

  @Test
  void testGetValveSegregation() {
    LoadableStudy.ValveSegregationRequest request =
        LoadableStudy.ValveSegregationRequest.newBuilder().build();
    LoadableStudy.ValveSegregationReply.Builder reply =
        LoadableStudy.ValveSegregationReply.newBuilder();
    Mockito.when(this.valveSegregationRepository.findAll()).thenReturn(getListCargoNVS());
    var valvesegration = this.cargoNominationService.getValveSegregation(request, reply);
    assertEquals(SUCCESS, reply.getResponseStatus().getStatus());
  }

  private List<CargoNominationValveSegregation> getListCargoNVS() {
    List<CargoNominationValveSegregation> cargoNominationValveSegregations = new ArrayList<>();
    CargoNominationValveSegregation cargoNominationValveSegregation =
        new CargoNominationValveSegregation();
    cargoNominationValveSegregation.setId(1L);
    cargoNominationValveSegregation.setName("1");
    cargoNominationValveSegregations.add(cargoNominationValveSegregation);
    return cargoNominationValveSegregations;
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testDeleteCargoNomination(Integer i) {
    LoadableStudy.CargoNominationRequest request =
        LoadableStudy.CargoNominationRequest.newBuilder().build();
    LoadableStudy.CargoNominationReply.Builder cargoNominationReplyBuilder =
        LoadableStudy.CargoNominationReply.newBuilder();
    Mockito.when(this.cargoNominationRepository.findById(Mockito.anyLong()))
        .thenReturn(getCargoNomination());
    if (i == 1) {
      Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong()))
          .thenReturn(getLoadableStudy());
    } else {
      Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong()))
          .thenReturn(Optional.empty());
    }

    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());

    Mockito.when(
            this.cargoNominationRepository.getCountCargoNominationWithPortIds(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong()))
        .thenReturn(0L);
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLSPR());
    try {
      var cargoNominationReply =
          this.cargoNominationService.deleteCargoNomination(request, cargoNominationReplyBuilder);
      assertEquals(SUCCESS, cargoNominationReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDeleteCargoNominationWithGenericException() {
    LoadableStudy.CargoNominationRequest request =
        LoadableStudy.CargoNominationRequest.newBuilder().build();
    LoadableStudy.CargoNominationReply.Builder cargoNominationReplyBuilder =
        LoadableStudy.CargoNominationReply.newBuilder();
    Mockito.when(this.cargoNominationRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.empty());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.cargoNominationService.deleteCargoNomination(
                    request, cargoNominationReplyBuilder));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getLoadableStudy() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setId(1L);
    return Optional.of(loadableStudy);
  }

  private Voyage getVoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    return voyage;
  }

  private List<LoadablePattern> getLoadablePattern() {
    List<LoadablePattern> loadablePatterns = new ArrayList<>();
    return loadablePatterns;
  }

  private Optional<CargoNomination> getCargoNomination() {
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setId(1L);
    cargoNomination.setCargoXId(2L);
    cargoNomination.setLoadableStudyXId(1L);
    cargoNomination.setAbbreviation("!");
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setCargoXId(1l);
    cargoNomination.setColor("1");
    cargoNomination.setMaxTolerance(new BigDecimal(1));
    cargoNomination.setMinTolerance(new BigDecimal(1));
    cargoNomination.setPriority(1l);
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setSegregationXId(1l);
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setVersion(1l);
    cargoNomination.setCargoNominationPortDetails(getCargoNPDetails());
    return Optional.of(cargoNomination);
  }

  private List<LoadableStudyPortRotation> getLLSPR() {
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setOperation(getCOperation());
    portRotation.setPortXId(1l);
    loadableStudyPortRotations.add(portRotation);

    LoadableStudyPortRotation rotation = new LoadableStudyPortRotation();
    rotation.setOperation(getCOperation());
    rotation.setPortXId(2l);
    loadableStudyPortRotations.add(rotation);
    return loadableStudyPortRotations;
  }

  private CargoOperation getCOperation() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1L);
    return cargoOperation;
  }

  @Test
  void testValidateDeleteCargoNomination() {
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setId(1L);
    Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong()))
        .thenReturn(getLoadableStudy());
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePattern());
    try {
      this.cargoNominationService.validateDeleteCargoNomination(cargoNomination);
      assertEquals(1L, Optional.ofNullable(cargoNomination.getId()));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testValidateLoadableStudyWithCommingle(Integer i) {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);

    if (i == 1) {
      Mockito.when(
              cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                  Mockito.anyLong(), Mockito.anyBoolean()))
          .thenReturn(getListCargoNomination());
    } else {
      List<CargoNomination> cargoNominations = new ArrayList<>();

      Mockito.when(
              cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                  Mockito.anyLong(), Mockito.anyBoolean()))
          .thenReturn(cargoNominations);
    }
    Mockito.when(
            commingleCargoRepository.findByLoadableStudyXIdAndPurposeXidAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getCommingleCargo());
    try {
      this.cargoNominationService.validateLoadableStudyWithCommingle(loadableStudy);
      assertEquals(Optional.of(1L), Optional.ofNullable(getCommingleCargo().get(0).getId()));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testValidateLoadableStudyWithCommingleWithGenericException() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    CommingleCargo cargo = getCommingleCargo().get(0);
    cargo.setQuantity(new BigDecimal(2));
    Mockito.when(
            cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoNomination());
    Mockito.when(
            commingleCargoRepository.findByLoadableStudyXIdAndPurposeXidAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(Arrays.asList(cargo));

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.cargoNominationService.validateLoadableStudyWithCommingle(loadableStudy));

    assertAll(
        () ->
            assertEquals(
                CommonErrorCodes.E_CPDSS_LS_INVALID_COMMINGLE_QUANTITY,
                ex.getCode(),
                "Invalid error code"),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
  }

  private List<com.cpdss.loadablestudy.entity.CommingleCargo> getCommingleCargo() {
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos = new ArrayList<>();
    CommingleCargo commingleCargo = new CommingleCargo();
    commingleCargo.setId(1L);
    commingleCargo.setQuantity(new BigDecimal(1));
    commingleCargos.add(commingleCargo);
    return commingleCargos;
  }

  @Test
  void testBuildCargoNominationPortDetails() {
    long loadableStudyId = 1L;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoNomination());
    Mockito.when(
            this.cargoNominationOperationDetailsRepository.findByCargoNominationInAndIsActiveTrue(
                (List<CargoNomination>) Mockito.any()))
        .thenReturn(getLCNPDetails());
    this.cargoNominationService.buildCargoNominationPortDetails(loadableStudyId, loadableStudy);
    assertEquals(
        Optional.of(1L),
        Optional.of(loadableStudy.getCargoNominationOperationDetails().get(0).getId()));
  }

  private List<CargoNominationPortDetails> getLCNPDetails() {
    List<CargoNominationPortDetails> cargoNominationPortDetails = new ArrayList<>();
    CargoNominationPortDetails cargoNominationPortDetails1 = new CargoNominationPortDetails();
    cargoNominationPortDetails1.setCargoNomination(getCargoNomination().get());
    cargoNominationPortDetails1.setId(1L);
    cargoNominationPortDetails1.setPortId(1L);
    cargoNominationPortDetails1.setQuantity(new BigDecimal(1));
    cargoNominationPortDetails.add(cargoNominationPortDetails1);
    return cargoNominationPortDetails;
  }

  @Test
  void testSaveAll() {
    List<CargoNomination> entities = new ArrayList<>();
    this.cargoNominationService.saveAll(entities);
    Mockito.verify(cargoNominationRepository).saveAll(Mockito.any());
  }

  @Test
  @Disabled
  void testSaveDischargeStudyCargoNominations() throws GenericServiceException {
    CargoNominationService spyService = spy(CargoNominationService.class);
    List<CargoNomination> cargos = new ArrayList<>();
    cargos.add(getCargoNomination().get());
    List<LoadingPlanModels.MaxQuantityDetails> maxQuantityDetailsList = new ArrayList<>();
    LoadingPlanModels.MaxQuantityDetails maxQuantityDetails =
        LoadingPlanModels.MaxQuantityDetails.newBuilder()
            .setCargoNominationId(1l)
            .setMaxQuantity("1")
            .setApi("1")
            .setTemp("1")
            .build();
    maxQuantityDetailsList.add(maxQuantityDetails);
    LoadingPlanModels.MaxQuantityResponse response =
        LoadingPlanModels.MaxQuantityResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllCargoMaxQuantity(maxQuantityDetailsList)
            .build();
    when(loadingPlanGrpcService.getCargoNominationMaxQuantity(
            any(LoadingPlanModels.MaxQuantityRequest.class)))
        .thenReturn(response);
    when(cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(cargos);
    when(cargoNominationRepository.saveAll(anyList())).thenReturn(cargos);
    ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);
    var result = spyService.saveDsichargeStudyCargoNominations(1l, 1l, 1l, 1l);
    assertEquals(1l, result.get(0).getId());
  }

  @Test
  void testSaveDischargeStudyCargoNominationsWithGenericException() throws GenericServiceException {
    CargoNominationService spyService = spy(CargoNominationService.class);
    List<CargoNomination> cargos = new ArrayList<>();
    cargos.add(getCargoNomination().get());
    List<LoadingPlanModels.MaxQuantityDetails> maxQuantityDetailsList = new ArrayList<>();
    LoadingPlanModels.MaxQuantityDetails maxQuantityDetails =
        LoadingPlanModels.MaxQuantityDetails.newBuilder()
            .setCargoNominationId(1l)
            .setMaxQuantity("1")
            .setApi("1")
            .setTemp("1")
            .build();
    maxQuantityDetailsList.add(maxQuantityDetails);
    LoadingPlanModels.MaxQuantityResponse response =
        LoadingPlanModels.MaxQuantityResponse.newBuilder()
            .addAllCargoMaxQuantity(maxQuantityDetailsList)
            .build();
    when(loadingPlanGrpcService.getCargoNominationMaxQuantity(
            any(LoadingPlanModels.MaxQuantityRequest.class)))
        .thenReturn(response);
    when(cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(cargos);
    when(cargoNominationRepository.saveAll(anyList())).thenReturn(cargos);
    ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> spyService.saveDsichargeStudyCargoNominations(1l, 1l, 1l, 1l));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetCargoNominationById() throws GenericServiceException {
    LoadableStudy.CargoNominationRequest request =
        LoadableStudy.CargoNominationRequest.newBuilder().build();
    LoadableStudy.CargoNominationReply.Builder replyBuilder =
        LoadableStudy.CargoNominationReply.newBuilder();
    List<CargoNomination> cargos = new ArrayList<>();
    cargos.add(getCargoNomination().get());
    List<ApiTempHistory> apiTempHistories = new ArrayList<>();
    ApiTempHistory history = new ApiTempHistory();
    history.setCargoId(1l);
    history.setVesselId(1l);
    history.setLoadingPortId(1l);
    history.setApi(new BigDecimal(1));
    history.setTemp(new BigDecimal(1));
    apiTempHistories.add(history);

    when(apiTempHistoryRepository.findByOrderByCreatedDateTimeDesc()).thenReturn(apiTempHistories);
    when(apiTempHistoryRepository
            .findByLoadingPortIdAndCargoIdAndIsActiveOrderByCreatedDateTimeDesc(
                anyLong(), anyLong(), anyBoolean()))
        .thenReturn(apiTempHistories);

    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(getLoadableStudy());
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
            anyLong(), anyBoolean()))
        .thenReturn(cargos);

    var result = cargoNominationService.getCargoNominationById(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testGetMaxQuantityFromBillOfLadding(Integer i) {
    CargoNominationService spyService = spy(CargoNominationService.class);
    List<com.cpdss.common.generated.Common.BillOfLadding> list = new ArrayList<>();
    com.cpdss.common.generated.Common.BillOfLadding billOfLadding =
        Common.BillOfLadding.newBuilder().setQuantityMt("1").build();
    list.add(billOfLadding);
    if (i == 1) {
      LoadingInformationSynopticalReply reply =
          LoadingInformationSynopticalReply.newBuilder()
              .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
              .addAllBillOfLadding(list)
              .build();
      when(loadingPlanGrpcService.getBillOfLaddingDetails(
              any(LoadingPlanModels.BillOfLaddingRequest.class)))
          .thenReturn(reply);
      ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);

      var result = spyService.getMaxQuantityFromBillOfLadding(1l);
      assertFalse(result == null);
    } else {
      LoadingInformationSynopticalReply reply =
          LoadingInformationSynopticalReply.newBuilder().addAllBillOfLadding(list).build();
      when(loadingPlanGrpcService.getBillOfLaddingDetails(
              any(LoadingPlanModels.BillOfLaddingRequest.class)))
          .thenReturn(reply);
      ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);

      var result = spyService.getMaxQuantityFromBillOfLadding(1l);
      assertEquals(result, null);
    }
  }

  @Test
  void testGetMaxQuantityFromBillOfLaddingElse() {
    CargoNominationService spyService = spy(CargoNominationService.class);
    List<com.cpdss.common.generated.Common.BillOfLadding> list = new ArrayList<>();
    com.cpdss.common.generated.Common.BillOfLadding billOfLadding =
        Common.BillOfLadding.newBuilder().setQuantityMt("1").build();
    list.add(billOfLadding);
    list.add(Common.BillOfLadding.newBuilder().setQuantityMt("2").build());
    LoadingInformationSynopticalReply reply =
        LoadingInformationSynopticalReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllBillOfLadding(list)
            .build();
    when(loadingPlanGrpcService.getBillOfLaddingDetails(
            any(LoadingPlanModels.BillOfLaddingRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);

    var result = spyService.getMaxQuantityFromBillOfLadding(1l);
    assertEquals(3.0, result);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testGetCargoNominationByCargoNominationId(Integer i) {
    CargoNominationService spyService = spy(CargoNominationService.class);
    LoadableStudy.CargoNominationRequest request =
        LoadableStudy.CargoNominationRequest.newBuilder().build();
    LoadableStudy.CargoNominationDetailReply.Builder builder =
        LoadableStudy.CargoNominationDetailReply.newBuilder();
    com.cpdss.common.generated.CargoInfo.CargoDetailReply reply =
        CargoInfo.CargoDetailReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    if (i == 1) {
      when(this.cargoNominationRepository.getOne(anyLong())).thenReturn(getCargoNomination().get());
    } else {
      when(this.cargoNominationRepository.getOne(anyLong())).thenReturn(null);
    }
    when(cargoInfoGrpcService.getCargoInfoById(any(CargoInfo.CargoRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    var result = spyService.getCargoNominationByCargoNominationId(request, builder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoById() {
    CargoNominationService spyService = spy(CargoNominationService.class);
    CargoInfo.CargoRequest request = CargoInfo.CargoRequest.newBuilder().build();
    com.cpdss.common.generated.CargoInfo.CargoDetailReply reply =
        CargoInfo.CargoDetailReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(cargoInfoGrpcService.getCargoInfoById(any(CargoInfo.CargoRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);

    var result = spyService.getCargoInfoById(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testBuildCargoNominationDetails() {
    CargoNominationService spyService = spy(CargoNominationService.class);
    ModelMapper modelMapper = new ModelMapper();
    List<CargoNomination> cargos = new ArrayList<>();
    cargos.add(getCargoNomination().get());
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    loadableStudy.setId(1l);
    CargoInfo.CargoDetail cargoDetail =
        CargoInfo.CargoDetail.newBuilder().setIsCondensateCargo(true).setIsHrvpCargo(true).build();

    com.cpdss.common.generated.CargoInfo.CargoDetailReply reply =
        CargoInfo.CargoDetailReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setCargoDetail(cargoDetail)
            .build();
    when(cargoInfoGrpcService.getCargoInfoById(any(CargoInfo.CargoRequest.class)))
        .thenReturn(reply);
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(cargos);

    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    spyService.buildCargoNominationDetails(1l, loadableStudy, modelMapper);
    assertEquals(true, loadableStudy.getCargoNomination().get(0).getIsCondensateCargo());
  }

  @Test
  void testBuildCargoNominationDetailsElse() {
    CargoNominationService spyService = spy(CargoNominationService.class);
    ModelMapper modelMapper = new ModelMapper();
    List<CargoNomination> cargos = new ArrayList<>();
    cargos.add(getCargoNomination().get());
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    loadableStudy.setId(1l);
    CargoInfo.CargoDetail cargoDetail =
        CargoInfo.CargoDetail.newBuilder().setIsCondensateCargo(true).setIsHrvpCargo(true).build();

    com.cpdss.common.generated.CargoInfo.CargoDetailReply reply =
        CargoInfo.CargoDetailReply.newBuilder().setCargoDetail(cargoDetail).build();
    when(cargoInfoGrpcService.getCargoInfoById(any(CargoInfo.CargoRequest.class)))
        .thenReturn(reply);
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(cargos);

    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    spyService.buildCargoNominationDetails(1l, loadableStudy, modelMapper);
  }

  @ParameterizedTest
  @ValueSource(longs = {1l, 2l})
  void testGetMaxQuantityForCargoNomination(Long l) throws GenericServiceException {
    CargoNominationService spyService = spy(CargoNominationService.class);
    List<Long> cargoNominations = new ArrayList<>();
    Set<CargoNomination> firstPortCargos = new HashSet<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setLsCargoNominationId(l);
    firstPortCargos.add(cargoNomination);

    List<LoadingPlanModels.MaxQuantityDetails> maxQuantityDetailsList = new ArrayList<>();
    LoadingPlanModels.MaxQuantityDetails maxQuantityDetails =
        LoadingPlanModels.MaxQuantityDetails.newBuilder()
            .setCargoNominationId(1l)
            .setMaxQuantity("1")
            .build();
    maxQuantityDetailsList.add(maxQuantityDetails);
    LoadingPlanModels.MaxQuantityResponse response =
        LoadingPlanModels.MaxQuantityResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllCargoMaxQuantity(maxQuantityDetailsList)
            .build();
    when(loadingPlanGrpcService.getCargoNominationMaxQuantity(
            any(LoadingPlanModels.MaxQuantityRequest.class)))
        .thenReturn(response);
    ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);
    var result = spyService.getMaxQuantityForCargoNomination(cargoNominations, firstPortCargos);
    if (l == 1l) assertEquals(1l, result.get(0).getLsCargoNominationId());
    else assertEquals(2l, result.get(0).getLsCargoNominationId());
  }

  @Test
  void testGetMaxQuantityForCargoNominationWithGenericException() throws GenericServiceException {
    CargoNominationService spyService = spy(CargoNominationService.class);
    List<Long> cargoNominations = new ArrayList<>();
    Set<CargoNomination> firstPortCargos = new HashSet<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setLsCargoNominationId(1l);
    firstPortCargos.add(cargoNomination);

    LoadingPlanModels.MaxQuantityResponse response =
        LoadingPlanModels.MaxQuantityResponse.newBuilder().build();
    when(loadingPlanGrpcService.getCargoNominationMaxQuantity(
            any(LoadingPlanModels.MaxQuantityRequest.class)))
        .thenReturn(response);
    ReflectionTestUtils.setField(spyService, "loadingPlanGrpcService", loadingPlanGrpcService);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> spyService.getMaxQuantityForCargoNomination(cargoNominations, firstPortCargos));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testSaveCargoNomination(Integer i) throws GenericServiceException {
    List<LoadableStudy.LoadingPortDetail> portDetailList = new ArrayList<>();
    LoadableStudy.LoadingPortDetail portDetail =
        LoadableStudy.LoadingPortDetail.newBuilder().setPortId(1l).setQuantity("1").build();
    portDetailList.add(portDetail);
    portDetailList.add(LoadableStudy.LoadingPortDetail.newBuilder().setPortId(4l).build());
    LoadableStudy.CargoNominationRequest request =
        LoadableStudy.CargoNominationRequest.newBuilder()
            .setCargoNominationDetail(
                LoadableStudy.CargoNominationDetail.newBuilder()
                    .setId(1l)
                    .setCargoId(5l)
                    .setLoadableStudyId(1l)
                    .setPriority(1l)
                    .setAbbreviation("1")
                    .setColor("1")
                    .setQuantity("1")
                    .setMaxTolerance("1")
                    .setMinTolerance("1")
                    .setApiEst("1")
                    .setTempEst("1")
                    .setSegregationId(1l)
                    .addAllLoadingPortDetails(portDetailList)
                    .build())
            .build();

    LoadableStudy.CargoNominationReply.Builder builder =
        LoadableStudy.CargoNominationReply.newBuilder();
    List<Long> transitPorts = new ArrayList<>();
    when(this.loadableStudyPortRotationRepository.getTransitPorts(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyList()))
        .thenReturn(transitPorts);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLoadableStudy());
    doNothing().when(voyageService).checkIfVoyageClosed(anyLong());
    doNothing()
        .when(loadablePatternService)
        .isPatternGeneratedOrConfirmed(any(com.cpdss.loadablestudy.entity.LoadableStudy.class));

    doNothing()
        .when(this.commingleCargoRepository)
        .deleteCommingleCargoByLodableStudyXIdAndCargoXId(anyLong(), anyLong());
    doNothing()
        .when(loadableStudyPortRotationService)
        .validateTransitPorts(any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyList());
    when(loadableStudyPortRotationService.findMaxPortOrderForLoadableStudy(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(1l);
    doNothing()
        .when(this.commingleCargoRepository)
        .deleteCommingleCargoByLodableStudyXIdAndCargoXId(anyLong(), anyLong());

    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(CargoOperation.class),
            anyBoolean()))
        .thenReturn(getLLSPR());
    when(cargoOperationRepository.getOne(anyLong())).thenReturn(getCOperation());
    when(this.cargoNominationRepository.getCountCargoNominationWithPortIds(
            anyLong(), any(CargoNomination.class), anyLong()))
        .thenReturn(0l);
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(
                Arrays.asList(
                    PortInfo.PortDetail.newBuilder()
                        .setId(4l)
                        .setWaterDensity("1")
                        .setMaxDraft("1")
                        .setMaxAirDraft("1")
                        .build()))
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(getLLSPR());

    doNothing()
        .when(loadableStudyPortRotationService)
        .validateTransitPorts(any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyList());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(reply);
    doNothing()
        .when(synopticService)
        .buildPortsInfoSynopticalTable(any(LoadableStudyPortRotation.class), anyLong(), anyLong());
    ReflectionTestUtils.setField(
        cargoNominationService, "portInfoGrpcService", portInfoGrpcService);
    //    ReflectionTestUtils.setField(
    //        spyService, "cargoNominationRepository", cargoNominationRepository);
    //    ReflectionTestUtils.setField(spyService, "cargoOperationRepository",
    // cargoOperationRepository);
    //    ReflectionTestUtils.setField(
    //        spyService, "loadableStudyPortRotationRepository",
    // loadableStudyPortRotationRepository);
    //    ReflectionTestUtils.setField(spyService, "commingleCargoRepository",
    // commingleCargoRepository);
    //    ReflectionTestUtils.setField(spyService, "loadablePatternService",
    // loadablePatternService);
    //    ReflectionTestUtils.setField(spyService, "voyageService", voyageService);
    //    ReflectionTestUtils.setField(spyService, "loadableStudyRepository",
    // loadableStudyRepository);
    //    ReflectionTestUtils.setField(
    //        spyService, "loadableStudyPortRotationService", loadableStudyPortRotationService);
    //    ReflectionTestUtils.setField(
    //        spyService, "cargoNominationRepository", cargoNominationRepository);
    //    ReflectionTestUtils.setField(spyService, "apiTempHistoryRepository",
    // apiTempHistoryRepository);
    if (i == 1) {
      when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
          .thenReturn(getCargoNomination());
      var result = cargoNominationService.saveCargoNomination(request, builder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } else {
      when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
          .thenReturn(Optional.empty());
      final GenericServiceException ex =
          assertThrows(
              GenericServiceException.class,
              () -> cargoNominationService.saveCargoNomination(request, builder));

      assertAll(
          () ->
              assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
          () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
    }
  }
}
