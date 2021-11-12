/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.vesselinfo.domain.CargoTankMaster;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.domain.VesselRule;
import com.cpdss.vesselinfo.domain.VesselTankDetails;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VesselInfoServiceTest {

  @InjectMocks private VesselInfoService vesselInfoService;

  @Mock private VesselRepository vesselRepository;
  @Mock private VesselTankRepository vesselTankRepository;
  @Mock private HydrostaticTableRepository hydrostaticTableRepository;
  @Mock private VesselDraftConditionRepository vesselDraftConditionRepository;
  @Mock private VesselTankTcgRepository vesselTankTcgRepository;
  @Mock private CalculationSheetRepository calculationSheetRepository;
  @Mock private CalculationSheetTankgroupRepository calculationSheetTankgroupRepository;
  @Mock private MinMaxValuesForBmsfRepository minMaxValuesForBmsfRepository;
  @Mock private StationValuesRepository stationValuesRepository;
  @Mock private InnerBulkHeadValuesRepository innerBulkHeadValuesRepository;
  @Mock private UllageTableDataRepository ullageTableDataRepository;
  @Mock private VesselPumpTankMappingRepository vesselPumpTankMappingRepository;
  @Mock private VesselFlowRateRepository vesselFlowRateRepository;
  @Mock private VesselPumpService vesselPumpService;
  @Mock private HydrostaticService hydrostaticService;
  @Mock private TankCategoryRepository tankCategoryRepository;
  @Mock private RuleVesselMappingRepository ruleVesselMappingRepository;
  @Mock private RuleVesselMappingInputRespository ruleVesselMappingInputRespository;
  @Mock private RuleTemplateRepository ruleTemplateRepository;
  @Mock private RuleTemplateInputRepository ruleTemplateInputRepository;
  @Mock private VesselValveSequenceRepository vesselValveSequenceRepository;
  @Mock private RuleVesselDropDownValuesRepository ruleVesselDropDownValuesRepository;
  @Mock private RuleTypeRepository ruleTypeRepository;
  @Mock private VesselValveEducationProcessRepository educationProcessRepository;
  @Mock private VesselChartererMappingRepository chartererMappingRepository;
  @Mock private PumpTypeRepository pumpTypeRepository;
  @Mock VesselPumpRepository vesselPumpRepository;
  @Mock TankTypeRepository tankTypeRepository;
  @Mock VesselManifoldRepository vesselManifoldRepository;
  @Mock VesselBottomLineRepository vesselBottomLineRepository;
  @Mock VesselValveAirPurgeSequenceRepository airPurgeSequenceRepository;
  @Mock VesselValveStrippingSequenceRepository strippingSequenceRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final Long TEST_ID = 1L;

  @Test
  void testGetVesselTanks() {
    Vessel vessel = new Vessel();
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(vessel);
    when(this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
            any(Vessel.class), anyList(), anyBoolean()))
        .thenReturn(this.prepareVesselTankEntities());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanksInvalidVessel() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanksRunTimeException() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<VesselTank> prepareVesselTankEntities() {
    List<VesselTank> tanks = new ArrayList<>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              VesselTank tank = new VesselTank();
              tank.setId(Long.valueOf(i));
              tank.setShortName("tank-" + i);
              tank.setFrameNumberFrom("frm-from" + i);
              tank.setFrameNumberTo("frame-to" + i);
              TankCategory tankCategory = new TankCategory();
              tankCategory.setId(Long.valueOf(i));
              tankCategory.setName("tankCategory-" + i);
              tank.setTankCategory(tankCategory);
              tank.setTankName("tank-name-" + i);
              tank.setHeightFrom(String.valueOf(i));
              tank.setHeightTo(String.valueOf(i));
              tank.setFillCapacityCubm(BigDecimal.TEN);
              tank.setFillCapacityCubm(BigDecimal.ONE);
              tank.setTankGroup(i);
              tank.setTankOrder(i);
              tank.setIsSlopTank(false);
              tanks.add(tank);
            });
    return tanks;
  }

  @Test
  void testGetVesselDetailsForAlgo() throws GenericServiceException {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    this.setUllageTrimCorrections(vessel);
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(vessel);
    when(this.vesselDraftConditionRepository.findByVesselAndIsActive(
            any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(new VesselDraftCondition()));
    when(this.vesselTankRepository.findByVesselAndIsActive(any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(new VesselTank()));
    when(this.hydrostaticTableRepository.findByVesselAndIsActive(any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(new HydrostaticTable()));
    when(this.vesselTankTcgRepository.findByVesselIdAndIsActive(any(), anyBoolean()))
        .thenReturn(Arrays.asList(new VesselTankTcg()));
    when(this.calculationSheetRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new CalculationSheet()));
    when(this.calculationSheetTankgroupRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new CalculationSheetTankgroup()));
    when(this.minMaxValuesForBmsfRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new MinMaxValuesForBmsf()));
    when(this.stationValuesRepository.findByVesselId(any()))
        .thenReturn(Arrays.asList(new StationValues()));
    when(this.innerBulkHeadValuesRepository.findByVesselId((any())))
        .thenReturn(Arrays.asList(new InnerBulkHeadValues()));
    when(this.ullageTableDataRepository.findByVesselOrderByVesselTankAscUllageDepthAsc(
            (any(Vessel.class))))
        .thenReturn(Arrays.asList(new UllageTableData()));
    when(this.vesselFlowRateRepository.findByVessel((any(Vessel.class))))
        .thenReturn(Arrays.asList(new VesselFlowRate()));
    VesselInfo.VesselPumpsResponse.Builder vesselPumpResponseBuilder =
        VesselInfo.VesselPumpsResponse.newBuilder();
    lenient()
        .when(
            this.vesselPumpService.getVesselPumpsAndTypes(
                vesselPumpResponseBuilder, vessel.getId()))
        .thenReturn(vesselPumpResponseBuilder.build());

    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private void setUllageTrimCorrections(Vessel vessel) {
    vessel.setUllageTrimCorrections(new HashSet<>());
    UllageTrimCorrection correction = new UllageTrimCorrection();
    correction.setId(TEST_ID);
    correction.setTrim0(BigDecimal.ONE);
    correction.setTrim1(BigDecimal.ONE);
    correction.setTrim2(BigDecimal.ONE);
    correction.setTrim3(BigDecimal.ONE);
    correction.setTrim4(BigDecimal.ONE);
    correction.setTrim5(BigDecimal.ONE);
    correction.setTrim6(BigDecimal.ONE);
    correction.setTrimM1(BigDecimal.ONE);
    correction.setTrimM2(BigDecimal.ONE);
    correction.setTrimM3(BigDecimal.ONE);
    correction.setTrimM4(BigDecimal.ONE);
    correction.setTrimM5(BigDecimal.ONE);
    correction.setUllageDepth(BigDecimal.ONE);
    correction.setIsActive(true);
    vessel.getUllageTrimCorrections().add(correction);
  }

  @Test
  void testGetVesselDetailsForAlgoInvalidVessel() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsForAlgoRunTimeException() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsById() {
    VesselRequest request =
        VesselRequest.newBuilder()
            .setVesselId(1L)
            .setVesselDraftConditionId(1L)
            .setDraftExtreme("1")
            .build();
    Mockito.when(
            this.vesselRepository.findVesselDetailsById(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(getVesselDetails());
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVessel());
    Mockito.when(
            this.hydrostaticService.fetchAllDataByDraftAndVessel(
                Mockito.any(Vessel.class), Mockito.any()))
        .thenReturn(getHydroStaticTable());
    Mockito.when(
            this.hydrostaticTableRepository.getTPCFromDraf(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getList());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsById(request, responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsByIdRunTimeException() {
    Mockito.when(
            this.vesselRepository.findVesselDetailsById(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsById(
        VesselRequest.newBuilder()
            .setVesselId(1L)
            .setVesselDraftConditionId(1L)
            .setDraftExtreme("1")
            .build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private VesselDetails getVesselDetails() {
    VesselDetails vesselDetails =
        new VesselDetails(
            new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), "1", new BigDecimal(1), true);
    return vesselDetails;
  }

  private Vessel getVessel() {
    Vessel vessel = new Vessel();
    return vessel;
  }

  private List<HydrostaticTable> getHydroStaticTable() {
    List<HydrostaticTable> hydrostaticTables = new ArrayList<HydrostaticTable>();
    HydrostaticTable hydro = new HydrostaticTable();
    hydro.setDisplacement(new BigDecimal(1));
    hydro.setId(1L);
    hydrostaticTables.add(hydro);
    return hydrostaticTables;
  }

  List<BigDecimal> getList() {
    List<BigDecimal> list = new ArrayList<BigDecimal>();
    list.add(new BigDecimal(1));
    return list;
  }

  @Test
  void testFindVesselTanksByCategory() {
    Long vesselId = 1L;
    List<Long> tankCategoryIds = new ArrayList<Long>();
    tankCategoryIds.add(1L);
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVes());
    Mockito.when(this.tankCategoryRepository.getOne(Mockito.anyLong())).thenReturn(gettc());
    Mockito.when(
            this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
                Mockito.any(Vessel.class), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getListVesselTank());
    try {
      var a = this.vesselInfoService.findVesselTanksByCategory(vesselId, tankCategoryIds);
      assertEquals(
          Optional.of(getListVesselTank().get(0).getId()), Optional.of(a.get(0).getTankId()));

    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testFindVesselTanksByCategoryInvalidVessel() {
    Long vesselId = 1L;
    List<Long> tankCategoryIds = new ArrayList<Long>();
    tankCategoryIds.add(1L);
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(null);
    try {
      this.vesselInfoService.findVesselTanksByCategory(vesselId, tankCategoryIds);
      assertEquals("400", CommonErrorCodes.E_HTTP_BAD_REQUEST);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Vessel getVes() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    return vessel;
  }

  private List<VesselTank> getListVesselTank() {
    List<VesselTank> vesselTanks = new ArrayList<VesselTank>();
    VesselTank vessel = new VesselTank();
    vessel.setId(1L);
    vessel.setTankCategory(getTankCategory());
    vessel.setShortName("1");
    vessel.setFrameNumberFrom("1");
    vessel.setFrameNumberTo("1");
    vessel.setTankName("1");
    vessel.setHeightFrom("1");
    vessel.setHeightTo("1");
    vessel.setFillCapacityCubm(new BigDecimal(1));
    vessel.setDensity(new BigDecimal(1));
    vessel.setTankGroup(1);
    vessel.setTankOrder(1);
    vessel.setIsSlopTank(true);
    vessel.setFullCapacityCubm(new BigDecimal(1));
    vessel.setTankDisplayOrder(1);
    vessel.setShowInOhqObq(true);
    vessel.setTankPositionCategory("1");
    vesselTanks.add(vessel);
    return vesselTanks;
  }

  private TankCategory getTankCategory() {
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(1L);
    tankCategory.setName("1");
    tankCategory.setShortName("1");
    tankCategory.setColorCode("1");
    return tankCategory;
  }

  @Test
  void testGetVesselCargoTanks() {
    VesselRequest request = VesselRequest.newBuilder().setVesselId(1L).build();
    List<Long> CARGO_TANK_CATEGORIES = new ArrayList<Long>();
    CARGO_TANK_CATEGORIES.add(1L);
    Long tankCategoryId = 1L;

    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getvesel());
    Mockito.when(this.tankCategoryRepository.getOne(Mockito.anyLong())).thenReturn(gettc());
    Mockito.when(
            this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
                Mockito.any(Vessel.class), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getLVesselTank());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselCargoTanks(request, responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselCargoTanksInvalidCargoTanks() {
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(null);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselCargoTanks(
        VesselRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselCargoTanksRunTimeException() {
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselCargoTanks(
        VesselRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private Vessel getvesel() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    return vessel;
  }

  private List<VesselTank> getLVesselTank() {
    List<VesselTank> vessel = new ArrayList<VesselTank>();
    VesselTank vs = new VesselTank();
    vs.setId(1L);
    vs.setTankName("1");
    vs.setShortName("1");
    vs.setTankCategory(gettc());
    vs.setTankType(getTT());
    vessel.add(vs);
    return vessel;
  }

  private TankType getTT() {
    TankType tankType = new TankType();
    tankType.setId(1L);
    return tankType;
  }

  private TankCategory gettc() {
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(1L);
    return tankCategory;
  }

  @Test
  void testGetVesselDetailForSynopticalTable() {
    VesselRequest request =
        VesselRequest.newBuilder()
            .addTankCategories(1L)
            .setVesselId(1L)
            .setVesselDraftConditionId(1L)
            .setDraftExtreme("1")
            .build();
    List<Long> tankCategoryIds = new ArrayList<Long>();
    tankCategoryIds.add(1L);
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVes());
    Mockito.when(this.tankCategoryRepository.getOne(Mockito.anyLong())).thenReturn(gettc());
    Mockito.when(
            this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
                Mockito.any(Vessel.class), Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getListVesselTank());
    Mockito.when(
            this.vesselRepository.findVesselDetailsById(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(getVesselDetails());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailForSynopticalTable(request, responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailForSynopticalTableInvalidVesselDetails() {
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(null);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailForSynopticalTable(
        VesselRequest.newBuilder()
            .addTankCategories(1L)
            .setVesselId(1L)
            .setVesselDraftConditionId(1L)
            .setDraftExtreme("1")
            .build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailForSynopticalTableRunTimeException() {
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailForSynopticalTable(
        VesselRequest.newBuilder()
            .addTankCategories(1L)
            .setVesselId(1L)
            .setVesselDraftConditionId(1L)
            .setDraftExtreme("1")
            .build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailByVesselId() {
    VesselRequest request = VesselRequest.newBuilder().setVesselId(1L).build();
    Mockito.when(
            this.vesselRepository.findVesselDetailsByVesselId(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVesselInfo());
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVess());
    Mockito.when(
            this.vesselTankRepository.findByVesselAndIsActive(
                Mockito.any(Vessel.class), Mockito.anyBoolean()))
        .thenReturn(getLVesselTank());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailByVesselId(request, responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailByVesselIdRunTimeException() {
    Mockito.when(
            this.vesselRepository.findVesselDetailsByVesselId(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVesselInfo());
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailByVesselId(
        VesselRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private com.cpdss.vesselinfo.domain.VesselInfo getVesselInfo() {
    com.cpdss.vesselinfo.domain.VesselInfo vessel =
        new com.cpdss.vesselinfo.domain.VesselInfo(
            1L,
            "1",
            "1",
            "1",
            "1",
            new BigDecimal(1),
            new BigDecimal(1),
            null,
            null,
            new BigDecimal(1),
            true);
    return vessel;
  }

  private Vessel getVess() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    return vessel;
  }

  @Test
  void testGetVesselInfoByPaging() {
    VesselInfo.VesselRequestWithPaging request =
        VesselInfo.VesselRequestWithPaging.newBuilder().build();
    Mockito.when(this.vesselRepository.findVesselIdAndNames()).thenReturn(getListObj());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselInfoByPaging(request, responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetVesselInfoByPagingRunTimeException() {
    Mockito.when(this.vesselRepository.findVesselIdAndNames()).thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselInfoByPaging(
        VesselInfo.VesselRequestWithPaging.newBuilder().build(), responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  private List<Object[]> getListObj() {
    List<Object[]> list = new ArrayList<Object[]>();
    Object[] array = new Object[20];
    array[0] = 1;
    array[1] = 2;
    list.add(array);
    return list;
  }

  @Test
  void testGetVesselInfoBytankIds() {
    VesselInfo.VesselTankRequest request =
        VesselInfo.VesselTankRequest.newBuilder().addTankIds(1L).build();
    Mockito.when(this.vesselTankRepository.findTankDetailsByTankIds(Mockito.anyList()))
        .thenReturn(getVesselTankDetails());
    StreamRecorder<VesselInfo.VesselTankResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselInfoBytankIds(request, responseObserver);
    List<VesselInfo.VesselTankResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetVesselInfoBytankIdsRunTimeException() {
    Mockito.when(this.vesselTankRepository.findTankDetailsByTankIds(Mockito.anyList()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselInfo.VesselTankResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselInfoBytankIds(
        VesselInfo.VesselTankRequest.newBuilder().addTankIds(1L).build(), responseObserver);
    List<VesselInfo.VesselTankResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  private List<VesselTankDetails> getVesselTankDetails() {
    List<VesselTankDetails> list = new ArrayList<VesselTankDetails>();
    VesselTankDetails vs = new VesselTankDetails(1L, "1", "1", 1);
    list.add(vs);
    return list;
  }

  @Test
  void testGetDWTFromVesselByVesselId() {
    com.cpdss.common.generated.VesselInfo.VesselDWTRequest request =
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest.newBuilder()
            .setVesselId(1L)
            .setDraftValue("1")
            .build();
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVesel());
    Mockito.when(
            this.hydrostaticService.fetchAllDataByDraftAndVessel(
                Mockito.any(Vessel.class), Mockito.any()))
        .thenReturn(getHydroStaticTable());
    StreamRecorder<VesselInfo.VesselDWTResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getDWTFromVesselByVesselId(request, responseObserver);
    List<VesselInfo.VesselDWTResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetDWTFromVesselByVesselIdRunTimeException() {
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselInfo.VesselDWTResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest.newBuilder()
            .setVesselId(1L)
            .setDraftValue("1")
            .build(),
        responseObserver);
    List<VesselInfo.VesselDWTResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private Vessel getVesel() {
    Vessel vessel = new Vessel();
    vessel.setLightweight(new BigDecimal(1));
    vessel.setId(1L);
    vessel.setCompanyXId(1L);
    return vessel;
  }

  @Test
  void testGetVesselInfoByVesselId() {
    VesselInfo.VesselIdRequest request =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1L).build();
    Mockito.when(
            this.vesselRepository.findVesselDetailsByVesselId(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVesselInfo());
    StreamRecorder<VesselInfo.VesselIdResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselInfoByVesselId(request, responseObserver);
    List<VesselInfo.VesselIdResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselInfoByVesselIdInvalidInfo() {
    Mockito.when(
            this.vesselRepository.findVesselDetailsByVesselId(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(null);
    StreamRecorder<VesselInfo.VesselIdResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselInfoByVesselId(
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselInfo.VesselIdResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselPumpsByVesselId() throws Exception {
    VesselInfo.VesselIdRequest request =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1L).build();
    Mockito.when(this.vesselPumpService.getVesselPumpsAndTypes(Mockito.any(), Mockito.anyLong()))
        .thenReturn(getvsl());
    StreamRecorder<VesselInfo.VesselPumpsResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselPumpsByVesselId(request, responseObserver);
    List<VesselInfo.VesselPumpsResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetVesselPumpsByVesselIdRunTimeException() throws Exception {
    Mockito.when(this.vesselPumpService.getVesselPumpsAndTypes(Mockito.any(), Mockito.anyLong()))
        .thenReturn(null);
    StreamRecorder<VesselInfo.VesselPumpsResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselPumpsByVesselId(
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselInfo.VesselPumpsResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  private VesselInfo.VesselPumpsResponse getvsl() {
    VesselInfo.VesselPumpsResponse vessel = VesselInfo.VesselPumpsResponse.newBuilder().build();
    return vessel;
  }

  @Test
  void testGetRulesByVesselIdAndSectionId() {
    Common.RulePlans obj = Common.RulePlans.newBuilder().setHeader("1").build();
    VesselInfo.VesselRuleRequest request =
        VesselInfo.VesselRuleRequest.newBuilder()
            .addRulePlan(obj)
            .setIsFetchEnabledRules(false)
            .setIsNoDefaultRule(false)
            .setSectionId(1L)
            .setVesselId(1L)
            .build();
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVess());
    Mockito.when(this.ruleVesselDropDownValuesRepository.findByIsActive(Mockito.anyBoolean()))
        .thenReturn(getListRuleDrop());
    Mockito.when(
            this.vesselTankRepository.findCargoTankMaster(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoTankMaster());
    Mockito.when(this.ruleTypeRepository.findByIsActive(Mockito.anyBoolean()))
        .thenReturn(getListRuleType());
    Mockito.when(this.vesselRepository.findRulesAgainstVessel(Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getListVesselRule());
    StreamRecorder<VesselInfo.VesselRuleReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getRulesByVesselIdAndSectionId(request, responseObserver);
    List<VesselInfo.VesselRuleReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetRulesByVesselIdAndSectionIdInvalidRule() {
    Common.RulePlans obj = Common.RulePlans.newBuilder().setHeader("1").build();
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(null);
    StreamRecorder<VesselInfo.VesselRuleReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getRulesByVesselIdAndSectionId(
        VesselInfo.VesselRuleRequest.newBuilder()
            .addRulePlan(obj)
            .setIsFetchEnabledRules(false)
            .setIsNoDefaultRule(false)
            .setSectionId(1L)
            .setVesselId(1L)
            .build(),
        responseObserver);
    List<VesselInfo.VesselRuleReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<RuleVesselDropDownValues> getListRuleDrop() {
    List<RuleVesselDropDownValues> list = new ArrayList<>();
    RuleVesselDropDownValues ruleVesselDropDownValues = new RuleVesselDropDownValues();
    ruleVesselDropDownValues.setId(1L);
    ruleVesselDropDownValues.setDropDownValue("1");
    ruleVesselDropDownValues.setRuleTemplateXid(1L);
    list.add(ruleVesselDropDownValues);
    return list;
  }

  private List<CargoTankMaster> getListCargoTankMaster() {
    List<CargoTankMaster> list = new ArrayList<>();
    CargoTankMaster cargoTankMaster = new CargoTankMaster(1L, "1");
    list.add(cargoTankMaster);
    return list;
  }

  private List<RuleType> getListRuleType() {
    List<RuleType> ruleTypes = new ArrayList<>();
    RuleType ruleType = new RuleType();
    ruleType.setId(1L);
    ruleType.setRuleType("1");
    ruleTypes.add(ruleType);
    return ruleTypes;
  }

  private List<VesselRule> getListVesselRule() {
    List<VesselRule> vesselRules = new ArrayList<>();
    VesselRule vesselRule =
        new VesselRule(
            "1", 1L, 1L, true, true, "1", 1L, null, "1", "1", "1", "1", "1", "1", true, true, null,
            1L, 1L, null, null);
    vesselRules.add(vesselRule);
    return vesselRules;
  }

  @Test
  void testGetVesselValveSequence() {
    VesselRequest request = VesselRequest.newBuilder().setVesselId(1L).build();
    StreamRecorder<VesselInfo.VesselValveSequenceReply> responseObserver = StreamRecorder.create();
    List<VesselValveSequence> sequenceList = new ArrayList<>();
    VesselValveSequence vesselValveSequence = new VesselValveSequence();
    vesselValveSequence.setId(1L);
    vesselValveSequence.setIsCommonValve(true);
    vesselValveSequence.setIsShut(true);
    vesselValveSequence.setPipelineId(1);
    vesselValveSequence.setPipelineColor("1");
    vesselValveSequence.setPipelineName("1");
    vesselValveSequence.setPipelineType("1");
    vesselValveSequence.setPumpCode("1");
    vesselValveSequence.setPumpName("1");
    vesselValveSequence.setPumpType("1");
    vesselValveSequence.setSequenceNumber(1);
    vesselValveSequence.setSequenceOperationId(1);
    vesselValveSequence.setSequenceOperationName("1");
    vesselValveSequence.setSequenceTypeId(1);
    vesselValveSequence.setSequenceTypeName("1");
    vesselValveSequence.setSequenceVesselMappingId(1);
    vesselValveSequence.setTankShortName("1");
    vesselValveSequence.setStageNumber("1");
    vesselValveSequence.setValveCategory("1");
    vesselValveSequence.setValveId(1);
    vesselValveSequence.setValveCategoryId(1);
    vesselValveSequence.setValveNumber("1");
    vesselValveSequence.setValveSide(1);
    vesselValveSequence.setValveTypeId(1);
    vesselValveSequence.setValveTypeName("1");
    vesselValveSequence.setVesselName("1");
    vesselValveSequence.setVesselXid(1L);
    vesselValveSequence.setVesselTankXid(1);
    sequenceList.add(vesselValveSequence);
    List<VesselValveEducationProcess> vvep = new ArrayList<>();
    VesselValveEducationProcess vessel = new VesselValveEducationProcess();
    vessel.setId(1L);
    vessel.setEductionProcessMasterId(1);
    vessel.setEductorId(1);
    vessel.setEductorName("1");
    vessel.setSequenceNumber(1);
    vessel.setStepName("1");
    vessel.setValveNumber("1");
    vessel.setStageNumber(1);
    vessel.setValveId(1);
    vessel.setStageName("1");
    vvep.add(vessel);
    Mockito.when(this.vesselPumpService.buildVesselValveEducator(Mockito.anyList()))
        .thenReturn(getVesselEducation());
    Mockito.when(airPurgeSequenceRepository.findAllByVesselId(Mockito.any()))
        .thenReturn(getLVVAPS());

    Mockito.when(strippingSequenceRepository.findAllByVesselId(Mockito.anyLong()))
        .thenReturn(getLVVSS());

    this.vesselInfoService.getVesselValveSequence(request, responseObserver);
    List<VesselInfo.VesselValveSequenceReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  private List<VesselValveAirPurgeSequence> getLVVAPS() {
    List<VesselValveAirPurgeSequence> list = new ArrayList<>();
    VesselValveAirPurgeSequence vesselValveAirPurgeSequence = new VesselValveAirPurgeSequence();
    vesselValveAirPurgeSequence.setId(1L);
    vesselValveAirPurgeSequence.setVesselId(1L);
    vesselValveAirPurgeSequence.setVesselName("1");
    vesselValveAirPurgeSequence.setShortname("1");
    vesselValveAirPurgeSequence.setTankId(1L);
    vesselValveAirPurgeSequence.setPumpCode("1");
    vesselValveAirPurgeSequence.setPumpId(1L);
    vesselValveAirPurgeSequence.setSequenceNumber(1);
    vesselValveAirPurgeSequence.setValveNumber("1");
    vesselValveAirPurgeSequence.setValveId(1);
    vesselValveAirPurgeSequence.setIsShut(true);
    vesselValveAirPurgeSequence.setIsCopWarmup(true);
    list.add(vesselValveAirPurgeSequence);
    return list;
  }

  private List<VesselValveStrippingSequence> getLVVSS() {
    List<VesselValveStrippingSequence> list = new ArrayList<>();
    VesselValveStrippingSequence vesselValveStrippingSequence = new VesselValveStrippingSequence();
    vesselValveStrippingSequence.setId(1L);
    vesselValveStrippingSequence.setVesselId(1L);
    vesselValveStrippingSequence.setVesselName("1");
    vesselValveStrippingSequence.setPipeLineId(1);
    vesselValveStrippingSequence.setPipeLineName("1");
    vesselValveStrippingSequence.setColour("1");
    vesselValveStrippingSequence.setValveId(1);
    vesselValveStrippingSequence.setValve("1");
    vesselValveStrippingSequence.setSequenceNumber(1);
    list.add(vesselValveStrippingSequence);
    return list;
  }

  @Test
  void testGetVesselValveSequenceRunTimeException() {
    Mockito.when(this.vesselPumpService.buildVesselValveSeqMessage(Mockito.anyList()))
        .thenThrow(new RuntimeException("customexception"));
    StreamRecorder<VesselInfo.VesselValveSequenceReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselValveSequence(
        VesselRequest.newBuilder().build(), responseObserver);
    List<VesselInfo.VesselValveSequenceReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  private List<VesselInfo.VesselValveSequence> getListVesselValveSequence() {
    List<VesselInfo.VesselValveSequence> vesselValveSequences = new ArrayList<>();
    VesselInfo.VesselValveSequence valveSequence =
        VesselInfo.VesselValveSequence.newBuilder().build();
    return vesselValveSequences;
  }

  private List<VesselInfo.VesselValveEducationProcess> getVesselEducation() {
    List<VesselInfo.VesselValveEducationProcess> list = new ArrayList<>();
    VesselInfo.VesselValveEducationProcess vessel =
        VesselInfo.VesselValveEducationProcess.newBuilder().build();
    list.add(vessel);
    return list;
  }

  @Test
  void testGetLoadingInfoRulescase1() {
    VesselInfo.LoadingInfoRulesRequest request =
        VesselInfo.LoadingInfoRulesRequest.newBuilder().setVesselId(1L).build();
    Mockito.when(this.ruleVesselMappingRepository.findLoadingInfoRulesByVesselId(Mockito.anyLong()))
        .thenReturn(getListRuleVMapping());
    Mockito.when(
            this.ruleVesselMappingInputRespository.findByRuleVesselMappingAndIsActive(
                Mockito.any(RuleVesselMapping.class), Mockito.anyBoolean()))
        .thenReturn(getListRuleVMappingInput());
    StreamRecorder<VesselInfo.LoadingInfoRulesReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getLoadingInfoRules(request, responseObserver);
    List<VesselInfo.LoadingInfoRulesReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoRulescaseRunTimeException() {
    Mockito.when(this.ruleVesselMappingRepository.findLoadingInfoRulesByVesselId(Mockito.anyLong()))
        .thenThrow(new RuntimeException("custom exception"));
    StreamRecorder<VesselInfo.LoadingInfoRulesReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getLoadingInfoRules(
        VesselInfo.LoadingInfoRulesRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselInfo.LoadingInfoRulesReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<RuleVesselMapping> getListRuleVMapping() {
    List<RuleVesselMapping> list = new ArrayList<>();
    RuleVesselMapping rule = new RuleVesselMapping();
    rule.setId(1L);
    list.add(rule);
    return list;
  }

  private List<RuleVesselMappingInput> getListRuleVMappingInput() {
    List<RuleVesselMappingInput> inputs = new ArrayList<RuleVesselMappingInput>();
    RuleVesselMappingInput rule = new RuleVesselMappingInput();
    rule.setPrefix("1");
    rule.setSuffix("1");
    rule.setDefaultValue("1");
    inputs.add(rule);
    return inputs;
  }

  @Test
  void testGetLoadingInfoRulescase2() {
    VesselInfo.LoadingInfoRulesRequest request =
        VesselInfo.LoadingInfoRulesRequest.newBuilder().build();
    Mockito.when(this.ruleVesselMappingRepository.findLoadingInfoRulesByVesselId(Mockito.anyLong()))
        .thenReturn(getListRVMapping());
    Mockito.when(this.ruleTemplateRepository.findLoadingInfoRules())
        .thenReturn(getListRuleTemplate());
    Mockito.when(
            this.ruleTemplateInputRepository.findByRuleTemplateAndIsActive(
                Mockito.any(RuleTemplate.class), Mockito.anyBoolean()))
        .thenReturn(getListRuleTemplateInput());
    StreamRecorder<VesselInfo.LoadingInfoRulesReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getLoadingInfoRules(request, responseObserver);
    List<VesselInfo.LoadingInfoRulesReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private List<RuleVesselMapping> getListRVMapping() {
    List<RuleVesselMapping> list = new ArrayList<>();
    return list;
  }

  private List<RuleTemplate> getListRuleTemplate() {
    List<RuleTemplate> ruleTemplates = new ArrayList<>();
    RuleTemplate ruleTemplate = new RuleTemplate();
    ruleTemplate.setId(1L);

    ruleTemplates.add(ruleTemplate);
    return ruleTemplates;
  }

  private List<RuleTemplateInput> getListRuleTemplateInput() {
    List<RuleTemplateInput> ruleTemplateInputs = new ArrayList<>();
    RuleTemplateInput ruleTemplateInput = new RuleTemplateInput();
    ruleTemplateInput.setPrefix("1");
    ruleTemplateInput.setSuffix("1");
    ruleTemplateInput.setDefaultValue("1");
    ruleTemplateInputs.add(ruleTemplateInput);
    return ruleTemplateInputs;
  }

  @Test
  void testGetAllVesselsByCompany() {
    VesselRequest request = VesselRequest.newBuilder().build();
    Mockito.when(this.vesselRepository.findByIsActive(Mockito.anyBoolean()))
        .thenReturn(getListVessel());
    Mockito.when(
            this.chartererMappingRepository.findByVesselAndIsActive(
                Mockito.any(Vessel.class), Mockito.anyBoolean()))
        .thenReturn(getVesselChartererMapping());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getAllVesselsByCompany(request, responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetAllVesselsByCompanyRunTimeException() {
    Mockito.when(this.vesselRepository.findByIsActive(Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getAllVesselsByCompany(
        VesselRequest.newBuilder().build(), responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<Vessel> getListVessel() {
    List<Vessel> list = new ArrayList<>();
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    vessel.setChiefofficerId(1L);
    vessel.setImoNumber("1");
    vessel.setMasterId(1L);
    vessel.setName("1");
    vessel.setVesselFlag(getVesselFlag());
    vessel.setHasLoadicator(true);
    vessel.setVesselDraftConditionCollection(getDraftConditionCollection());
    list.add(vessel);
    return list;
  }

  private VesselFlag getVesselFlag() {
    VesselFlag vesselFlag = new VesselFlag();
    vesselFlag.setFlagImagePath("1");
    vesselFlag.setId(1L);
    return vesselFlag;
  }

  private Set<VesselDraftCondition> getDraftConditionCollection() {
    Set<VesselDraftCondition> vesselDraftConditions = new HashSet<>();
    VesselDraftCondition vesselDraftCondition = new VesselDraftCondition();
    vesselDraftCondition.setId(1L);
    vesselDraftCondition.setDraftCondition(getDraft());
    vesselDraftCondition.setDraftExtreme(new BigDecimal(1));
    vesselDraftConditions.add(vesselDraftCondition);
    return vesselDraftConditions;
  }

  private DraftCondition getDraft() {
    DraftCondition draftCondition = new DraftCondition();
    draftCondition.setIsActive(true);
    draftCondition.setId(1L);
    draftCondition.setName("1");
    return draftCondition;
  }

  private VesselChartererMapping getVesselChartererMapping() {
    VesselChartererMapping vesselChartererMapping = new VesselChartererMapping();
    vesselChartererMapping.setCharterer(getCharterer());
    return vesselChartererMapping;
  }

  private Charterer getCharterer() {
    Charterer charterer = new Charterer();
    charterer.setId(1L);
    charterer.setName("1");
    return charterer;
  }

  @Test
  void testSaveRulesAgainstVessel() {
    Common.RulesInputs input =
        Common.RulesInputs.newBuilder()
            .setId("1")
            .setDefaultValue("1")
            .setMax("1")
            .setMin("1")
            .setPrefix("1")
            .setSuffix("1")
            .setType("1")
            .setIsMandatory(true)
            .build();
    Common.Rules rule =
        Common.Rules.newBuilder()
            .addInputs(input)
            .setId("1")
            .setRuleTemplateId("1")
            .setDisplayInSettings(true)
            .setNumericPrecision(1L)
            .setNumericScale(1L)
            .setRuleType("1")
            .setEnable(true)
            .setIsHardRule(true)
            .build();
    Common.RulePlans obj = Common.RulePlans.newBuilder().addRules(rule).setHeader("1").build();
    VesselInfo.VesselRuleRequest request =
        VesselInfo.VesselRuleRequest.newBuilder().addRulePlan(obj).build();
    Vessel vessel = new Vessel();
    List<RuleVesselDropDownValues> listOfDropDownValue = new ArrayList<>();
    RuleVesselDropDownValues ruleVesselDropDownValues = new RuleVesselDropDownValues();
    ruleVesselDropDownValues.setRuleTemplateXid(1L);
    listOfDropDownValue.add(ruleVesselDropDownValues);

    List<CargoTankMaster> cargoTankMaster = new ArrayList<>();
    CargoTankMaster cargoTankMaster1 = new CargoTankMaster(1L, null);
    cargoTankMaster.add(cargoTankMaster1);

    List<RuleType> ruleTypeList = new ArrayList<>();
    RuleType ruleType = new RuleType();
    ruleType.setId(1L);
    ruleType.setRuleType("1");
    ruleTypeList.add(ruleType);

    Mockito.when(this.ruleTemplateRepository.findByIsActive(Mockito.anyBoolean()))
        .thenReturn(getLRuleTemplate());
    Mockito.when(this.ruleVesselMappingRepository.findById(Mockito.anyLong()))
        .thenReturn(getRuleVesselMapping());
    Mockito.when(this.ruleVesselMappingInputRespository.findById(Mockito.anyLong()))
        .thenReturn(getRuleVMInput());
    this.vesselInfoService.saveRulesAgainstVessel(
        request, vessel, listOfDropDownValue, cargoTankMaster, ruleTypeList);
    Mockito.verify(ruleVesselMappingRepository).saveAll(Mockito.anyList());
  }

  private List<RuleTemplate> getLRuleTemplate() {
    List<RuleTemplate> ruleTemplates = new ArrayList<>();
    RuleTemplate ruleTemplate = new RuleTemplate();
    ruleTemplate.setId(1L);
    ruleTemplates.add(ruleTemplate);
    return ruleTemplates;
  }

  private Optional<RuleVesselMapping> getRuleVesselMapping() {
    RuleVesselMapping ruleVesselMapping = new RuleVesselMapping();
    ruleVesselMapping.setId(1L);
    return Optional.of(ruleVesselMapping);
  }

  private Optional<RuleVesselMappingInput> getRuleVMInput() {
    RuleVesselMappingInput ruleVesselMappingInput = new RuleVesselMappingInput();
    ruleVesselMappingInput.setId(1L);
    return Optional.of(ruleVesselMappingInput);
  }
}
