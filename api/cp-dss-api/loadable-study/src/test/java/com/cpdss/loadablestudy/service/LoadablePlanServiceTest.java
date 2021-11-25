/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(classes = {LoadablePlanService.class})
public class LoadablePlanServiceTest {

  @Autowired LoadablePlanService loadablePlanService;
  @MockBean CargoNominationRepository cargoNominationRepository;
  @MockBean LoadablePatternCargoDetailsRepository lpCargoDetailsRepository;
  @MockBean VoyageRepository voyageRepository;
  @MockBean LoadableStudyPortRotationRepository portRotationRepository;
  @Mock private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @Mock private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private SynopticService synopticService;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;
  @MockBean private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean private AlgoService algoService;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadableStudyService loadableStudyService;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private JsonDataService jsonDataService;

  @MockBean
  private LoadablePatternCargoToppingOffSequenceRepository
      loadablePatternCargoToppingOffSequenceRepository;

  @MockBean private CommunicationService communicationService;
  @MockBean private VoyageService voyageService;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Value("${algo.stowage.edit.api.url}")
  private String algoUpdateUllageUrl;

  // need grpc config
  //  @Test
  //  void testBuildLoadablePlanQuantity() {
  //    List<LoadablePlanQuantity> loadablePlanQuantities = new ArrayList<>();
  //    LoadablePlanQuantity loadablePlanQuantity = new LoadablePlanQuantity();
  //    loadablePlanQuantity.setId(1L);
  //    loadablePlanQuantity.setDifferenceColor("1");
  //    loadablePlanQuantity.setDifferencePercentage("1");
  //    loadablePlanQuantity.setEstimatedApi(new BigDecimal(1));
  //    loadablePlanQuantity.setCargoNominationTemperature(new BigDecimal(1));
  //    loadablePlanQuantity.setGrade("1");
  //    loadablePlanQuantity.setLoadableBbls60f("1");
  //    loadablePlanQuantity.setLoadableKl("1");
  //    loadablePlanQuantity.setLoadableBblsDbs("1");
  //    loadablePlanQuantity.setLoadableLt("1");
  //    loadablePlanQuantity.setLoadableMt("1");
  //    loadablePlanQuantity.setMaxTolerence("1");
  //    loadablePlanQuantity.setMinTolerence("1");
  //    loadablePlanQuantity.setOrderBbls60f("1");
  //    loadablePlanQuantity.setOrderBblsDbs("1");
  //    loadablePlanQuantity.setCargoXId(1L);
  //    loadablePlanQuantity.setOrderQuantity(new BigDecimal(1));
  //    loadablePlanQuantity.setSlopQuantity("1");
  //    loadablePlanQuantity.setTimeRequiredForLoading("1");
  //    loadablePlanQuantity.setCargoNominationId(1L);
  //    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
  //        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
  //    Mockito.when(
  //            this.cargoNominationRepository.findByIdAndIsActive(
  //                Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getCargoNomination());
  //  }

  private Optional<CargoNomination> getCargoNomination() {
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1L);
    cargoNomination.setCargoNominationPortDetails(getCNPD());
    return Optional.of(cargoNomination);
  }

  private Set<CargoNominationPortDetails> getCNPD() {
    Set<CargoNominationPortDetails> cargoNominationPortDetails = new HashSet<>();
    CargoNominationPortDetails cargoNominationPortDetails1 = new CargoNominationPortDetails();
    cargoNominationPortDetails1.setPortId(1L);
    cargoNominationPortDetails.add(cargoNominationPortDetails1);
    return cargoNominationPortDetails;
  }

  @Test
  void testBuildLoadablePlanCommingleDetails() {
    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails = new ArrayList<>();
    LoadablePlanCommingleDetails loadablePlanCommingleDetails1 = new LoadablePlanCommingleDetails();
    loadablePlanCommingleDetails1.setId(1L);
    loadablePlanCommingleDetails1.setApi("1");
    loadablePlanCommingleDetails1.setCargo1Abbreviation("1");
    loadablePlanCommingleDetails1.setCargo1Bbls60f("1");
    loadablePlanCommingleDetails1.setCargo1BblsDbs("1");
    loadablePlanCommingleDetails1.setCargo1Kl("1");
    loadablePlanCommingleDetails1.setCargo1Lt("1");
    loadablePlanCommingleDetails1.setCargo1Mt("1");
    loadablePlanCommingleDetails1.setCargo1Percentage("1");
    loadablePlanCommingleDetails1.setCargo2Abbreviation("1");
    loadablePlanCommingleDetails1.setCargo2Bbls60f("1");
    loadablePlanCommingleDetails1.setCargo1BblsDbs("1");
    loadablePlanCommingleDetails1.setCargo2Kl("1");
    loadablePlanCommingleDetails1.setCargo2Lt("1");
    loadablePlanCommingleDetails1.setCargo2Mt("1");
    loadablePlanCommingleDetails1.setCargo2Percentage("1");
    loadablePlanCommingleDetails1.setGrade("1");
    loadablePlanCommingleDetails1.setQuantity("1");
    loadablePlanCommingleDetails1.setTankName("1");
    loadablePlanCommingleDetails1.setSlopQuantity("1");
    loadablePlanCommingleDetails1.setTemperature("1");
    loadablePlanCommingleDetails1.setTankShortName("1");
    loadablePlanCommingleDetails1.setCorrectedUllage("1");
    loadablePlanCommingleDetails1.setCorrectionFactor("1");
    loadablePlanCommingleDetails1.setFillingRatio("1");
    loadablePlanCommingleDetails1.setRdgUllage("1");
    loadablePlanCommingleDetails1.setTankId(1L);
    loadablePlanCommingleDetails.add(loadablePlanCommingleDetails1);
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
    this.loadablePlanService.buildLoadablePlanCommingleDetails(
        loadablePlanCommingleDetails, replyBuilder);
    assertEquals(1L, replyBuilder.getLoadableQuantityCommingleCargoDetails(0).getId());
    assertEquals("1", replyBuilder.getLoadablePlanStowageDetails(0).getCorrectedUllage());
  }

  @Test
  void testBuildBallastGridDetails() {
    List<LoadablePlanBallastDetails> loadablePlanBallastDetails = new ArrayList<>();
    LoadablePlanBallastDetails loadablePlanBallastDetails1 = new LoadablePlanBallastDetails();
    loadablePlanBallastDetails1.setId(1L);
    loadablePlanBallastDetails1.setCorrectedLevel("1");
    loadablePlanBallastDetails1.setCorrectionFactor("1");
    loadablePlanBallastDetails1.setCubicMeter("1");
    loadablePlanBallastDetails1.setInertia("1");
    loadablePlanBallastDetails1.setLcg("1");
    loadablePlanBallastDetails1.setMetricTon("1");
    loadablePlanBallastDetails1.setPercentage("1");
    loadablePlanBallastDetails1.setRdgLevel("1");
    loadablePlanBallastDetails1.setSg("1");
    loadablePlanBallastDetails1.setTankId(1L);
    loadablePlanBallastDetails1.setTcg("1");
    loadablePlanBallastDetails1.setVcg("1");
    loadablePlanBallastDetails1.setTankName("1");
    loadablePlanBallastDetails1.setColorCode("1");
    loadablePlanBallastDetails.add(loadablePlanBallastDetails1);
    List<LoadablePlanStowageDetailsTemp> ballstTempList = new ArrayList<>();
    LoadablePlanStowageDetailsTemp temp = new LoadablePlanStowageDetailsTemp();
    temp.setRdgUllage(new BigDecimal(1));
    temp.setCorrectionFactor(new BigDecimal(1));
    temp.setCorrectedUllage(new BigDecimal(1));
    temp.setQuantity(new BigDecimal(1));
    temp.setFillingRatio(new BigDecimal(1));
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(loadablePlanBallastDetails1);
    this.loadablePlanService.buildBallastGridDetails(
        loadablePlanBallastDetails, ballstTempList, replyBuilder);
    assertEquals(1L, replyBuilder.getLoadablePlanBallastDetails(0).getId());
  }

  @Test
  void testSetTempBallastDetails() {
    LoadablePlanBallastDetails lpbd = new LoadablePlanBallastDetails();
    lpbd.setId(1L);
    List<LoadablePlanStowageDetailsTemp> ballastTempList = new ArrayList<>();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setRdgUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectedUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectionFactor(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setQuantity(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setFillingRatio(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(lpbd);
    ballastTempList.add(loadablePlanStowageDetailsTemp);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder();
    this.loadablePlanService.setTempBallastDetails(lpbd, ballastTempList, builder);
    assertEquals("1", builder.getCorrectionFactor());
  }

  // need grpc
  @Test
  void testSetLoadingPortNameFromCargoOperation() {
    LoadablePlanQuantity lpQuantity = new LoadablePlanQuantity();
    lpQuantity.setCargoXId(1L);
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
    Mockito.when(
            this.cargoNominationRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getCargoNomination());
  }

  @Test
  void testConvertToBbls() {
    float value = 1f;
    float api = 1f;
    float temperature = 1f;
    ConversionUnit conversionUnit = ConversionUnit.LT;
    var bool = this.loadablePlanService.convertToBbls(value, api, temperature, conversionUnit);
    Assert.assertEquals(5.996119f, bool, 0.0f);
  }

  @Test
  void testGetConversionConstant() {
    ConversionUnit conversionUnit = ConversionUnit.LT;
    float api = 1f;
    float temperature = 1f;
    var bool = this.loadablePlanService.getConversionConstant(conversionUnit, api, temperature);
    Assert.assertEquals(0.16677454f, bool, 0.0f);
  }

  //  @Test
  //  void testDrawVesselPlanTable() {
  //    XSSFWorkbook workbook = new XSSFWorkbook();
  //    XSSFSheet spreadsheet = workbook.createSheet();
  //    VesselPlanTable vesselPlanTable = VesselPlanTable.builder().build();
  //    vesselPlanTable.setVesselName("1");
  //    vesselPlanTable.setVoyageNo("1");
  //    vesselPlanTable.setDate("1");
  //    vesselPlanTable.setFrameFromCellsList(getLF());
  //    vesselPlanTable.setVesselTanksTableList(getVTTL());
  //    int startRow = 1;
  //    int starColumn = 1;
  //    String vesselPlanTableTitle = VesselPlanTableTitles.VESSEL_NAME.getColumnName();
  //    String stowagePlanTableTitles = StowagePlanTableTitles.CARGO_CODE.getColumnName();
  //    var sheetCoordinates =
  //        this.loadablePlanService.drawVesselPlanTable(
  //            spreadsheet, vesselPlanTable, startRow, starColumn);
  //    assertEquals(24, sheetCoordinates.getRow());
  //    assertEquals(1, sheetCoordinates.getColumn());
  //  }

  private List<Float> getLF() {
    List<Float> fo = new ArrayList<>();
    fo.add(1f);
    return fo;
  }

  private List<VesselTanksTable> getVTTL() {
    List<VesselTanksTable> vesselTanksTableList = new ArrayList<>();
    VesselTanksTable vesselTanksTable = VesselTanksTable.builder().build();
    vesselTanksTable.setTankNo("1");
    vesselTanksTable.setFrameNoTo(1f);
    vesselTanksTable.setFrameNoFrom(1f);
    vesselTanksTable.setColorCode("1");
    vesselTanksTable.setCargoCode("1");
    vesselTanksTable.setUllage(1d);
    vesselTanksTable.setLoadedPercentage(1d);
    vesselTanksTable.setShipsNBbls(1d);
    vesselTanksTable.setShipsMt(1d);
    vesselTanksTable.setShipsKlAt15C(1d);

    vesselTanksTableList.add(vesselTanksTable);
    return vesselTanksTableList;
  }

  @Test
  void testConvertFromBbls() {
    float value = 1f;
    float api = 1f;
    float temperature = 1f;
    ConversionUnit conversionUnit = ConversionUnit.LT;
    var bool = this.loadablePlanService.convertFromBbls(value, api, temperature, conversionUnit);
    Assert.assertEquals(0.16677454f, bool, 0.0f);
  }

  //  @Test
  //  void testDrawPortOperationTable() {
  //    XSSFWorkbook workbook = new XSSFWorkbook();
  //    XSSFSheet spreadsheet = workbook.createSheet();
  //    PortOperationTable portOperationTable = PortOperationTable.builder().build();
  //    portOperationTable.setOperationsTableList(getLOT());
  //    int startRow = 1;
  //    int starColumn = 1;
  //    String portOperationsTableTitle = PortOperationsTableTitles.PORT_NAME.getColumnName();
  //    var sheetCoordinates =
  //        this.loadablePlanService.drawPortOperationTable(
  //            spreadsheet, portOperationTable, startRow, starColumn);
  //    assertEquals(2, sheetCoordinates.getColumn());
  //    assertEquals("1", portOperationTable.getOperationsTableList().get(0).getOperation());
  //  }

  private List<OperationsTable> getLOT() {
    List<OperationsTable> operationsTables = new ArrayList<>();
    OperationsTable operationsTable = OperationsTable.builder().build();
    operationsTable.setOperation("1");
    operationsTable.setPortName("1");
    operationsTable.setCountry("1");
    operationsTable.setLaycanRange("1");
    operationsTable.setEta("1");
    operationsTable.setEtd("1");
    operationsTable.setArrFwdDraft(1d);
    operationsTable.setArrAftDraft(1d);
    operationsTable.setArrDisplacement(1d);
    operationsTable.setDepFwdDraft(1d);
    operationsTable.setDepAftDraft(1d);
    operationsTable.setDepDisp(1d);
    operationsTables.add(operationsTable);
    return operationsTables;
  }

  //  @Test
  //  void testDrawCargoDetailsTable() {
  //    XSSFWorkbook workbook = new XSSFWorkbook();
  //    XSSFSheet spreadsheet = workbook.createSheet();
  //    CargoDetailsTable cargoDetailsTable = CargoDetailsTable.builder().build();
  //    cargoDetailsTable.setCargosTableList(getLCT());
  //    cargoDetailsTable.setNBblsTotal(1d);
  //    cargoDetailsTable.setMtTotal(1d);
  //    cargoDetailsTable.setKl15CTotal(1d);
  //    cargoDetailsTable.setLtTotal(1d);
  //    cargoDetailsTable.setDiffBblsTotal(1d);
  //    cargoDetailsTable.setDiffPercentageTotal(1d);
  //    cargoDetailsTable.setCargoNominationTotal(1d);
  //    int startRow = 1;
  //    int starColumn = 1;
  //    String cargoTableColumnDetails = CargoDetailsTableTitles.CARGO_CODE.getColumnName();
  //    var sheetCoordinates =
  //        this.loadablePlanService.drawCargoDetailsTable(
  //            spreadsheet, cargoDetailsTable, startRow, starColumn);
  //    assertEquals(3, sheetCoordinates.getColumn());
  //    assertEquals(13, sheetCoordinates.getRow());
  //  }

  private List<CargosTable> getLCT() {
    List<CargosTable> cargosTables = new ArrayList<>();
    CargosTable cargosTable = CargosTable.builder().build();
    cargosTable.setCargoCode("1");
    cargosTable.setColorCode("1");
    cargosTable.setLoadingPort("1");
    cargosTable.setApi(1d);
    cargosTable.setTemp(1d);
    cargosTable.setCargoNomination(1d);
    cargosTable.setTolerance("1");
    cargosTable.setNBbls(1d);
    cargosTable.setMt(1d);
    cargosTable.setKl15C(1d);
    cargosTable.setLt(1d);
    cargosTable.setDiffBbls(1d);
    cargosTable.setDiffPercentage(1d);
    cargosTables.add(cargosTable);
    return cargosTables;
  }

  // need grpc
  //  @Test
  //  void testBuildCargoDetailsTable() {
  //    long loadableStudyId = 1L;
  //    long loadablePatternId = 1L;
  //    PortInfo.PortDetail portReply = PortInfo.PortDetail.newBuilder().setName("1").build();
  //    List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetails =
  //        new ArrayList<>();
  //    LoadableStudy.LoadableQuantityCargoDetails loadableQuantityCargoDetails1 =
  //        LoadableStudy.LoadableQuantityCargoDetails.newBuilder()
  //            .setColorCode("1")
  //            .setMinTolerence("1")
  //            .setMaxTolerence("1")
  //            .setEstimatedAPI("1")
  //            .setEstimatedTemp("1")
  //            .setCargoAbbreviation("1")
  //            .setCargoId(1L)
  //            .build();
  //    loadableQuantityCargoDetails.add(loadableQuantityCargoDetails1);
  //    List<LoadableStudy.LoadablePlanStowageDetails> loadablePlanStowageDetails = new
  // ArrayList<>();
  //    LoadableStudy.LoadablePlanStowageDetails loadablePlanStowageDetails1 =
  //        LoadableStudy.LoadablePlanStowageDetails.newBuilder()
  //            .setWeight("1")
  //            .setCargoAbbreviation("1")
  //            .setTankId(1L)
  //            .build();
  //    loadablePlanStowageDetails.add(loadablePlanStowageDetails1);
  //    LoadableStudy.LoadablePlanDetailsReply loadablePlanDetails =
  //        LoadableStudy.LoadablePlanDetailsReply.newBuilder()
  //            .addAllLoadablePlanStowageDetails(loadablePlanStowageDetails)
  //            .addAllLoadableQuantityCargoDetails(loadableQuantityCargoDetails)
  //            .build();
  //    Mockito.when(
  //
  // this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
  //                Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getLCN());
  //    Mockito.when(
  //            this.loadablePatternRepository.findByIdAndIsActive(
  //                Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getLP());
  //    try {
  //      var cargoDetailsTable =
  //          this.loadablePlanService.buildCargoDetailsTable(loadableStudyId, loadablePatternId);
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //  }

  private List<CargoNomination> getLCN() {
    List<CargoNomination> cargoNominations = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setAbbreviation("1");
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setCargoNominationPortDetails(getCNPD());
    cargoNominations.add(cargoNomination);
    return cargoNominations;
  }

  // buildLoadablePlanDetails need grpc

  @Test
  void testBuildTankDetail() {
    VesselInfo.VesselTankDetail detail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setFrameNumberFrom("1")
            .setFrameNumberTo("1")
            .setShortName("1")
            .setTankCategoryId(1L)
            .setTankCategoryName("1")
            .setTankId(1L)
            .setTankName("1")
            .setIsSlopTank(true)
            .setDensity("1")
            .setFillCapacityCubm("1")
            .setHeightFrom("1")
            .setHeightTo("1")
            .setTankOrder(1)
            .setTankDisplayOrder(1)
            .setTankGroup(1)
            .setFullCapacityCubm("1")
            .build();
    var tankdetail = this.loadablePlanService.buildTankDetail(detail);
    assertEquals("1", tankdetail.getFrameNumberFrom());
  }

  @Test
  void testBuildLoadablePlanStowageCargoDetails() {
    List<LoadablePlanStowageDetails> loadablePlanStowageDetails = new ArrayList<>();
    LoadablePlanStowageDetails loadablePlanStowageDetails1 = new LoadablePlanStowageDetails();
    loadablePlanStowageDetails1.setId(1L);
    loadablePlanStowageDetails1.setAbbreviation("1");
    loadablePlanStowageDetails1.setApi("1");
    loadablePlanStowageDetails1.setCorrectedUllage("1");
    loadablePlanStowageDetails1.setCorrectionFactor("1");
    loadablePlanStowageDetails1.setFillingPercentage("1");
    loadablePlanStowageDetails1.setObservedBarrels("1");
    loadablePlanStowageDetails1.setObservedBarrelsAt60("1");
    loadablePlanStowageDetails1.setObservedM3("1");
    loadablePlanStowageDetails1.setRdgUllage("1");
    loadablePlanStowageDetails1.setTankname("1");
    loadablePlanStowageDetails1.setTankId(1L);
    loadablePlanStowageDetails1.setCargoNominationTemperature(new BigDecimal(1));
    loadablePlanStowageDetails1.setWeight("1");
    loadablePlanStowageDetails1.setColorCode("1");
    loadablePlanStowageDetails1.setCorrectedUllage("1");
    loadablePlanStowageDetails1.setCorrectionFactor("1");
    loadablePlanStowageDetails1.setFillingPercentage("1");
    loadablePlanStowageDetails1.setCargoNominationId(1L);
    loadablePlanStowageDetails.add(loadablePlanStowageDetails1);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    VesselInfo.VesselTankResponse vesselTankData =
        VesselInfo.VesselTankResponse.newBuilder().addVesselTankOrder(getvesselTO()).build();
    VesselInfo.VesselTankOrder vesselTankOrder =
        VesselInfo.VesselTankOrder.newBuilder()
            .setTankId(1L)
            .setShortName("1")
            .setTankDisplayOrder(1)
            .build();
    Mockito.when(
            this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsInAndIsActive(
                Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getLPSDT());
    this.loadablePlanService.buildLoadablePlanStowageCargoDetails(
        loadablePlanStowageDetails, replyBuilder, vesselTankData);
    assertEquals(1L, replyBuilder.getLoadablePlanStowageDetails(0).getCargoNominationId());
  }

  private List<LoadablePlanStowageDetailsTemp> getLPSDT() {
    List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTemps = new ArrayList<>();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setId(1L);
    loadablePlanStowageDetailsTemp.setCorrectedUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectionFactor(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setFillingRatio(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setQuantity(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setRdgUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setLoadablePlanStowageDetails(getLoadablePSD());
    loadablePlanStowageDetailsTemps.add(loadablePlanStowageDetailsTemp);
    return loadablePlanStowageDetailsTemps;
  }

  private LoadablePlanStowageDetails getLoadablePSD() {
    LoadablePlanStowageDetails loadablePlanStowageDetails = new LoadablePlanStowageDetails();
    loadablePlanStowageDetails.setId(1L);
    return loadablePlanStowageDetails;
  }

  private VesselInfo.VesselTankOrder getvesselTO() {
    VesselInfo.VesselTankOrder vesselTankOrder =
        VesselInfo.VesselTankOrder.newBuilder()
            .setTankId(1L)
            .setShortName("1")
            .setTankDisplayOrder(1)
            .build();
    return vesselTankOrder;
  }

  @Test
  void testBuildBallastTankLayout() {
    List<VesselInfo.VesselTankDetail> vesselTankDetails = new ArrayList<>();
    VesselInfo.VesselTankDetail vesselTankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankPositionCategory("FRONT")
            .setTankGroup(1)
            .setTankOrder(1)
            .build();
    vesselTankDetails.add(vesselTankDetail);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    this.loadablePlanService.buildBallastTankLayout(vesselTankDetails, replyBuilder);
    assertEquals(false, replyBuilder.getBallastFrontTanksList().isEmpty());
  }

  private Optional<LoadablePattern> getLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setLoadableStudy(getLS());
    return Optional.of(loadablePattern);
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVesselXId(1L);
    return loadableStudy;
  }

  // need grpc
  //  @Test
  //  void testGetLoadablePlanDetails() {
  //    Long vesselId = 1L;
  //    List<Long> tankCategory = new ArrayList<>();
  //    tankCategory.add(1L);
  //    LoadableStudy.LoadablePlanDetailsRequest request =
  //        LoadableStudy.LoadablePlanDetailsRequest.newBuilder().setLoadablePatternId(1L).build();
  //    LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
  //        LoadableStudy.LoadablePlanDetailsReply.newBuilder();
  //    Mockito.when(
  //            this.loadablePatternRepository.findByIdAndIsActive(
  //                Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getLP());
  //    Mockito.when(
  //            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(),
  // Mockito.anyBoolean()))
  //        .thenReturn(getOLS());
  //    try {
  //      var loadablePlanDetailsReply =
  //          this.loadablePlanService.getLoadablePlanDetails(request, replyBuilder);
  //
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getOLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setPortRotations(getportR());
    return Optional.of(loadableStudy);
  }

  private Set<LoadableStudyPortRotation> getportR() {
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setOperation(getCargoOp());
    loadableStudyPortRotation.setEta(LocalDateTime.now());
    loadableStudyPortRotation.setEtd(LocalDateTime.now());
    loadableStudyPortRotation.setLayCanTo(LocalDate.now());
    loadableStudyPortRotation.setLayCanFrom(LocalDate.now());
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    return loadableStudyPortRotations;
  }

  private CargoOperation getCargoOp() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1L);
    return cargoOperation;
  }

  @Test
  void testValidateLoadableStudyForConfimPlan() {
    com.cpdss.loadablestudy.entity.LoadableStudy ls =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    ls.setVesselXId(1L);
    ls.setPortRotations(getportR());
    var bool = this.loadablePlanService.validateLoadableStudyForConfimPlan(ls);
    assertEquals(Optional.of(1L), Optional.ofNullable(ls.getVesselXId()));
  }

  @Test
  void testSaveComment() {
    LoadablePlanComments entity = new LoadablePlanComments();
    entity.setId(1L);
    entity.setComments("1");
    entity.setCreatedBy("1");
    entity.setCreatedDateTime(LocalDateTime.now());
    entity.setLastModifiedDateTime(LocalDateTime.now());
    LoadableStudy.SaveCommentRequest request =
        LoadableStudy.SaveCommentRequest.newBuilder().setUser(1L).build();
    LoadableStudy.SaveCommentReply.Builder replyBuilder =
        LoadableStudy.SaveCommentReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    var saveCommentReply = this.loadablePlanService.saveComment(request, replyBuilder);
    assertEquals("SUCCESS", replyBuilder.getResponseStatus().getStatus());
  }
}
