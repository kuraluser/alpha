/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
    // Mockito.verify(loadableStudyPortRotationRepository).saveAll(Mockito.anyList());
    try {
      var result =
          loadableStudyPortRotationService.saveLoadableStudyPortRotationList(request, replyBuilder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildLoadableStudyPortRotationEntity() {
    LoadableStudyPortRotation entity = getLoadableStudyPortRotation();
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1l);
    cargoOperation.setName("1");
    when(this.cargoOperationRepository.getOne(Mockito.anyLong())).thenReturn(cargoOperation);
    loadableStudyPortRotationService.buildLoadableStudyPortRotationEntity(
        entity, getPortRotationDetail());
    var result = entity.getSynopticalTable().get(0).getEtaActual();
    LocalDateTime aDateTime = LocalDateTime.of(1999, 11, 21, 11, 11);
    assertEquals(aDateTime, result);
  }

  @Test
  void testsaveLoadableStudyPortRotation() {
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();
    Optional<LoadableStudy> loadableStudyOpt = Optional.of(getLoadableStudy());
    Optional<LoadableStudyPortRotation> portRoationOpt =
        Optional.of(getLoadableStudyPortRotation());
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    when(loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyBoolean()))
        .thenReturn(generatedPatterns);
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(loadableStudyOpt);
    when(this.loadableStudyPortRotationRepository.findById(Mockito.anyLong()))
        .thenReturn(portRoationOpt);
    when(this.loadableStudyPortRotationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(getLoadableStudyPortRotation());
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

  @Test
  @Disabled
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
    Optional<LoadableStudy> loadableStudyOpt = Optional.of(getLoadableStudy());
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setTotalQuantity(new BigDecimal(1));
    loadableQuantity.setLoadableStudyPortRotation(getLoadableStudyPortRotation());
    List<LoadableQuantity> quantities = new ArrayList<>();
    quantities.add(loadableQuantity);
    List<LoadableStudyPortRotation> ports = new ArrayList<>();
    ports.add(getLoadableStudyPortRotation());

    Map<Long, List<BackLoading>> backloadingDataByportIds = new HashMap<>();
    List<BackLoading> backLoadingList = new ArrayList<>();
    BackLoading backLoading = new BackLoading();
    backLoading.setAbbreviation("1");
    backLoading.setApi(new BigDecimal(1));
    backLoading.setColour("1");
    backLoading.setCargoId(1l);
    backLoading.setId(1l);
    backLoading.setQuantity(new BigDecimal(1));
    backLoading.setTemperature(new BigDecimal(1));
    backLoadingList.add(backLoading);
    backloadingDataByportIds.put(1l, backLoadingList);

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
        .thenReturn(ports);
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(loadableStudyOpt);
    when(loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
            Mockito.anyLong(), anyBoolean()))
        .thenReturn(quantities);
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
  void testdeletePortRotation() {
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
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    Optional<LoadableStudyPortRotation> portRoationOpt =
        Optional.of(getLoadableStudyPortRotation());

    when(this.loadableStudyPortRotationRepository.findById(Mockito.anyLong()))
        .thenReturn(portRoationOpt);
    when(loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            anyBoolean()))
        .thenReturn(generatedPatterns);
    when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(loadableStudyOpt);
    when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), anyBoolean()))
        .thenReturn(voyage);
    try {
      var result =
          loadableStudyPortRotationService.deletePortRotation(request, portRotationReplyBuilder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
  void testbuildportRotationDetails() {
    LoadableStudyPortRotationService spyService = spy(LoadableStudyPortRotationService.class);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    List<Long> portIds = new ArrayList<>(Arrays.asList(1l));
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder()
            .setAverageTideHeight("1")
            .setId(1l)
            .setName("1")
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
        PortInfo.PortReply.newBuilder().addAllPorts(portDetailList).build();
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(portIds);
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(portReply);
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
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(reply);
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
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(reply);
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
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(spyService, "voyageService", voyageService);

    spyService.getLoadableStudyShore(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
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
    loadableStudyPortRotation.setPortRotationType("1");
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
    loadableStudy.setPlanningTypeXId(1);
    loadableStudy.setConfirmedLoadableStudyId(1l);
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("1");
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
    synopticalTable.setId(1l);
    synopticalTableList.add(synopticalTable);
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
            .setIsLandingPage(true)
            .build();
    return portRotationDetail;
  }
}
