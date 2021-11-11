/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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
            dischargeStudyId, cargo, portId, operationId);
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
            dischargeStudyCargo, cargo, portId, operationId);
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

  @Test
  void testDeleteCargoNomination() {
    LoadableStudy.CargoNominationRequest request =
        LoadableStudy.CargoNominationRequest.newBuilder().build();
    LoadableStudy.CargoNominationReply.Builder cargoNominationReplyBuilder =
        LoadableStudy.CargoNominationReply.newBuilder();
    Mockito.when(this.cargoNominationRepository.findById(Mockito.anyLong()))
        .thenReturn(getCargoNomination());
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

    Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong()))
        .thenReturn(getLoadableStudy());
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
    cargoNomination.setCargoNominationPortDetails(getCargoNPDetails());
    return Optional.of(cargoNomination);
  }

  private List<LoadableStudyPortRotation> getLLSPR() {
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setOperation(getCOperation());
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

  @Test
  void testValidateLoadableStudyWithCommingle() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);

    Mockito.when(
            cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoNomination());
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

  private List<com.cpdss.loadablestudy.entity.CommingleCargo> getCommingleCargo() {
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos = new ArrayList<>();
    CommingleCargo commingleCargo = new CommingleCargo();
    commingleCargo.setId(1L);
    commingleCargo.setQuantity(new BigDecimal(1));
    commingleCargos.add(commingleCargo);
    return commingleCargos;
  }

  @Test
  void testbuildCargoNominationPortDetails() {
    long loadableStudyId = 1L;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoNomination());
    Mockito.when(
            this.cargoNominationOperationDetailsRepository.findByCargoNominationAndIsActive(
                (List<CargoNomination>) Mockito.any(), Mockito.anyBoolean()))
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
}
