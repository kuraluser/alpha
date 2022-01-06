/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class VesselInfoServiceTest {

  @InjectMocks private VesselInfoService vesselInfoService;

  @Mock private VesselRepository vesselRepository;
  @Mock private VesselTankRepository vesselTankRepository;
  @Mock private HydrostaticTableRepository hydrostaticTableRepository;
  @Mock private VesselDraftConditionRepository vesselDraftConditionRepository;
  @Mock private VesselTankTcgRepository vesselTankTcgRepository;
  @Mock private BendingMomentRepository bendingMomentRepository;
  @Mock private ShearingForceRepository shearingForceRepository;
  @Mock private CalculationSheetRepository calculationSheetRepository;
  @Mock private CalculationSheetTankgroupRepository calculationSheetTankgroupRepository;
  @Mock private MinMaxValuesForBmsfRepository minMaxValuesForBmsfRepository;
  @Mock private StationValuesRepository stationValuesRepository;
  @Mock private InnerBulkHeadValuesRepository innerBulkHeadValuesRepository;
  @Mock private UllageTableDataRepository ullageTableDataRepository;
  @Mock private VesselFlowRateRepository vesselFlowRateRepository;
  @Mock private VesselPumpTankMappingRepository vesselPumpTankMappingRepository;
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
  @Mock VVStrippingSequenceCargoValveRepository sequenceCargoValveRepository;
  @Mock VesselCowService vesselCowService;
  @Mock private BendingMomentRepositoryType2 bendingMomentRepositoryType2;
  @Mock private ShearingForceRepositoryType2 shearingForceRepositoryType2;
  @Mock private BendingMomentRepositoryType4 bendingMomentRepositoryType4;
  @Mock private ShearingForceRepositoryType4 shearingForceRepositoryType4;
  @Mock private BendingMomentShearingForceRepositoryType3 bendingMomentShearingForceRepositoryType3;
  @Mock private VesselParticularService vesselParticularService;

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
  void testGetVesselDetailsForAlgo() {
    VesselAlgoRequest request = VesselAlgoRequest.newBuilder().setVesselId(1L).build();
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    Vessel vessel = getVesl();
    vessel.setBm_sf_model_type(1);
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(vessel);
    Mockito.when(
            vesselDraftConditionRepository.findByVesselAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLVDC());
    Mockito.when(vesselTankRepository.findByVesselAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLVesselTank());
    Mockito.when(
            hydrostaticTableRepository.findByVesselAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLHST());
    Mockito.when(
            vesselTankTcgRepository.findByVesselIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLVTT());
    Mockito.when(bendingMomentRepository.findByVessel(Mockito.any())).thenReturn(getLBMT());
    Mockito.when(shearingForceRepository.findByVessel(Mockito.any())).thenReturn(getLSFT());
    Mockito.when(calculationSheetRepository.findByVessel(Mockito.any())).thenReturn(getLCS());
    Mockito.when(calculationSheetTankgroupRepository.findByVessel(Mockito.any()))
        .thenReturn(getCSTG());
    Mockito.when(minMaxValuesForBmsfRepository.findByVessel(Mockito.any())).thenReturn(getMM());
    Mockito.when(stationValuesRepository.findByVesselId(Mockito.anyLong())).thenReturn(getLSV());
    Mockito.when(innerBulkHeadValuesRepository.findByVesselId(Mockito.anyLong()))
        .thenReturn(getBHV());
    Mockito.when(
            ullageTableDataRepository.findByVesselOrderByVesselTankAscUllageDepthAsc(Mockito.any()))
        .thenReturn(getLUTD());
    Mockito.when(vesselFlowRateRepository.findByVessel(Mockito.any())).thenReturn(getLVFR());
    Mockito.when(
            vesselPumpTankMappingRepository.findByVesselXidAndIsActive(
                Mockito.any(), Mockito.any()))
        .thenReturn(getVPTM());
    try {
      Mockito.when(this.vesselPumpService.getVesselPumpsAndTypes(Mockito.any(), Mockito.anyLong()))
          .thenReturn(getVPR());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.vesselInfoService.getVesselDetailsForAlgo(request, responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsForAlgoType2() {
    VesselAlgoRequest request = VesselAlgoRequest.newBuilder().setVesselId(1L).build();
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    Vessel vessel = getVesl();
    vessel.setBm_sf_model_type(2);
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(vessel);
    Mockito.when(
            vesselDraftConditionRepository.findByVesselAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLVDC());
    Mockito.when(vesselTankRepository.findByVesselAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLVesselTank());
    Mockito.when(
            hydrostaticTableRepository.findByVesselAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLHST());
    Mockito.when(
            vesselTankTcgRepository.findByVesselIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLVTT());
    Mockito.when(bendingMomentRepositoryType2.findByVessel(Mockito.any())).thenReturn(getLBMT2());
    Mockito.when(shearingForceRepositoryType2.findByVessel(Mockito.any())).thenReturn(getLSFT2());
    Mockito.when(calculationSheetRepository.findByVessel(Mockito.any())).thenReturn(getLCS());
    Mockito.when(calculationSheetTankgroupRepository.findByVessel(Mockito.any()))
        .thenReturn(getCSTG());
    Mockito.when(minMaxValuesForBmsfRepository.findByVessel(Mockito.any())).thenReturn(getMM());
    Mockito.when(stationValuesRepository.findByVesselId(Mockito.anyLong())).thenReturn(getLSV());
    Mockito.when(innerBulkHeadValuesRepository.findByVesselId(Mockito.anyLong()))
        .thenReturn(getBHV());
    Mockito.when(
            ullageTableDataRepository.findByVesselOrderByVesselTankAscUllageDepthAsc(Mockito.any()))
        .thenReturn(getLUTD());
    Mockito.when(vesselFlowRateRepository.findByVessel(Mockito.any())).thenReturn(getLVFR());
    Mockito.when(
            vesselPumpTankMappingRepository.findByVesselXidAndIsActive(
                Mockito.any(), Mockito.any()))
        .thenReturn(getVPTM());
    try {
      Mockito.when(this.vesselPumpService.getVesselPumpsAndTypes(Mockito.any(), Mockito.anyLong()))
          .thenReturn(getVPR());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.vesselInfoService.getVesselDetailsForAlgo(request, responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsForAlgoType4() {
    VesselAlgoRequest request = VesselAlgoRequest.newBuilder().setVesselId(1L).build();
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    Vessel vessel = getVesl();
    vessel.setBm_sf_model_type(4);
    Mockito.when(this.vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(vessel);
    Mockito.when(
            vesselDraftConditionRepository.findByVesselAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLVDC());
    Mockito.when(vesselTankRepository.findByVesselAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLVesselTank());
    Mockito.when(
            hydrostaticTableRepository.findByVesselAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLHST());
    Mockito.when(
            vesselTankTcgRepository.findByVesselIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLVTT());
    Mockito.when(bendingMomentRepositoryType4.findByVessel(Mockito.any())).thenReturn(getLBMT4());
    Mockito.when(shearingForceRepositoryType4.findByVessel(Mockito.any())).thenReturn(getLSFT4());
    when(bendingMomentShearingForceRepositoryType3.findByVessel(Mockito.any()))
        .thenReturn(getLBMSFT3());
    Mockito.when(calculationSheetRepository.findByVessel(Mockito.any())).thenReturn(getLCS());
    Mockito.when(calculationSheetTankgroupRepository.findByVessel(Mockito.any()))
        .thenReturn(getCSTG());
    Mockito.when(minMaxValuesForBmsfRepository.findByVessel(Mockito.any())).thenReturn(getMM());
    Mockito.when(stationValuesRepository.findByVesselId(Mockito.anyLong())).thenReturn(getLSV());
    Mockito.when(innerBulkHeadValuesRepository.findByVesselId(Mockito.anyLong()))
        .thenReturn(getBHV());
    Mockito.when(
            ullageTableDataRepository.findByVesselOrderByVesselTankAscUllageDepthAsc(Mockito.any()))
        .thenReturn(getLUTD());
    Mockito.when(vesselFlowRateRepository.findByVessel(Mockito.any())).thenReturn(getLVFR());
    Mockito.when(
            vesselPumpTankMappingRepository.findByVesselXidAndIsActive(
                Mockito.any(), Mockito.any()))
        .thenReturn(getVPTM());
    try {
      Mockito.when(this.vesselPumpService.getVesselPumpsAndTypes(Mockito.any(), Mockito.anyLong()))
          .thenReturn(getVPR());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.vesselInfoService.getVesselDetailsForAlgo(request, responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private VesselInfo.VesselPumpsResponse getVPR() {
    VesselInfo.VesselPumpsResponse response = VesselInfo.VesselPumpsResponse.newBuilder().build();
    return response;
  }

  private List<VesselPumpTankMapping> getVPTM() {
    List<VesselPumpTankMapping> list = new ArrayList<>();
    VesselPumpTankMapping vesselPumpTankMapping = new VesselPumpTankMapping();
    vesselPumpTankMapping.setVesselPumps(getVP());
    vesselPumpTankMapping.setVesselXid(1);
    vesselPumpTankMapping.setVesselTank(getLVesselTank().get(0));
    list.add(vesselPumpTankMapping);
    return list;
  }

  private VesselPumps getVP() {
    VesselPumps vesselPumps = new VesselPumps();
    vesselPumps.setId(1L);
    vesselPumps.setPumpCode("1");
    vesselPumps.setPumpName("1");
    vesselPumps.setPumpType(getPT());
    return vesselPumps;
  }

  private PumpType getPT() {
    PumpType pumpType = new PumpType();
    pumpType.setId(1L);
    return pumpType;
  }

  private List<VesselFlowRate> getLVFR() {
    List<VesselFlowRate> list = new ArrayList<>();
    VesselFlowRate vesselFlowRate = new VesselFlowRate();
    vesselFlowRate.setFlowRateParameter(getFP());
    vesselFlowRate.setFlowRateOne(new BigDecimal(1));
    vesselFlowRate.setFlowRateSix(new BigDecimal(1));
    vesselFlowRate.setFlowRateSeven(new BigDecimal(1));
    vesselFlowRate.setFlowRateTwelve(new BigDecimal(1));

    list.add(vesselFlowRate);
    return list;
  }

  private FlowRateParameter getFP() {
    FlowRateParameter flowRateParameter = new FlowRateParameter();
    flowRateParameter.setId(1L);
    flowRateParameter.setFlowRateParameter("1");
    return flowRateParameter;
  }

  private List<UllageTableData> getLUTD() {
    List<UllageTableData> list = new ArrayList<>();
    UllageTableData ullageTableData = new UllageTableData();
    ullageTableData.setId(1L);
    ullageTableData.setVesselTank(getListVesselTank().get(0));
    ullageTableData.setUllageDepth(new BigDecimal(1));
    ullageTableData.setEvenKeelCapacityCubm(new BigDecimal(1));
    ullageTableData.setSoundDepth(new BigDecimal(1));
    list.add(ullageTableData);
    return list;
  }

  private List<InnerBulkHeadValues> getBHV() {
    List<InnerBulkHeadValues> list = new ArrayList<>();
    InnerBulkHeadValues innerBulkHeadValues = new InnerBulkHeadValues();
    innerBulkHeadValues.setId(1L);
    innerBulkHeadValues.setFrameNumber(new BigDecimal(1));
    innerBulkHeadValues.setForeAlpha(new BigDecimal(1));
    innerBulkHeadValues.setForeCenterCargotankId(new BigDecimal(1));
    innerBulkHeadValues.setForeC1(new BigDecimal(1));
    innerBulkHeadValues.setForeWingTankId("1");
    innerBulkHeadValues.setForeC2(new BigDecimal(1));
    innerBulkHeadValues.setForeBallastTank("1");
    innerBulkHeadValues.setForeC3(new BigDecimal(1));
    innerBulkHeadValues.setForeBwCorrection(new BigDecimal(1));
    innerBulkHeadValues.setForeC4(new BigDecimal(1));
    innerBulkHeadValues.setForeMaxAllowence(new BigDecimal(1));
    innerBulkHeadValues.setForeMinAllowence(new BigDecimal(1));
    innerBulkHeadValues.setAftAlpha(new BigDecimal(1));
    innerBulkHeadValues.setAftCenterCargotankId(new BigDecimal(1));
    innerBulkHeadValues.setAftC1(new BigDecimal(1));
    innerBulkHeadValues.setAftWingTankId("1");
    innerBulkHeadValues.setAftC2(new BigDecimal(1));
    innerBulkHeadValues.setAftBallastTank("1");
    innerBulkHeadValues.setAftC3(new BigDecimal(1));
    innerBulkHeadValues.setAftBwCorrection(new BigDecimal(1));
    innerBulkHeadValues.setAftC4(new BigDecimal(1));
    innerBulkHeadValues.setAftMaxFlAllowence(new BigDecimal(1));
    innerBulkHeadValues.setAftMinFlAllowence(new BigDecimal(1));
    list.add(innerBulkHeadValues);
    return list;
  }

  private List<StationValues> getLSV() {
    List<StationValues> list = new ArrayList<>();
    StationValues stationValues = new StationValues();
    stationValues.setId(1L);
    stationValues.setFrameNumberFrom(new BigDecimal(1));
    stationValues.setFrameNumberTo(new BigDecimal(1));
    stationValues.setStationTo(new BigDecimal(1));
    stationValues.setStattionFrom(new BigDecimal(1));
    stationValues.setDistance(new BigDecimal(1));
    list.add(stationValues);
    return list;
  }

  private List<MinMaxValuesForBmsf> getMM() {
    List<MinMaxValuesForBmsf> list = new ArrayList<>();
    MinMaxValuesForBmsf minMaxValuesForBmsf = new MinMaxValuesForBmsf();
    minMaxValuesForBmsf.setId(1L);
    minMaxValuesForBmsf.setFrameNumber(new BigDecimal(1));
    minMaxValuesForBmsf.setMinBm(new BigDecimal(1));
    minMaxValuesForBmsf.setMaxBm(new BigDecimal(1));
    minMaxValuesForBmsf.setMinSf(new BigDecimal(1));
    minMaxValuesForBmsf.setMaxSf(new BigDecimal(1));
    list.add(minMaxValuesForBmsf);
    return list;
  }

  private List<CalculationSheetTankgroup> getCSTG() {
    List<CalculationSheetTankgroup> list = new ArrayList<>();
    CalculationSheetTankgroup calculationSheetTankgroup = new CalculationSheetTankgroup();
    calculationSheetTankgroup.setId(1L);
    calculationSheetTankgroup.setTankGroup(1);
    calculationSheetTankgroup.setLcg(new BigDecimal(1));
    calculationSheetTankgroup.setFrameNumber(new BigDecimal(1));
    list.add(calculationSheetTankgroup);
    return list;
  }

  private List<CalculationSheet> getLCS() {
    List<CalculationSheet> list = new ArrayList<>();
    CalculationSheet sheet = new CalculationSheet();
    sheet.setId(1L);
    sheet.setTankGroup(1);
    sheet.setTankId(1);
    sheet.setWeightRatio(new BigDecimal(1));
    sheet.setLcg(new BigDecimal(1));
    list.add(sheet);
    return list;
  }

  private List<ShearingForceType1> getLSFT() {
    List<ShearingForceType1> list = new ArrayList<>();
    ShearingForceType1 type1 = new ShearingForceType1();
    type1.setId(1L);
    type1.setFrameNumber(new BigDecimal(1));
    type1.setBaseDraft(new BigDecimal(1));
    type1.setBaseValue(new BigDecimal(1));
    type1.setDraftCorrection(new BigDecimal(1));
    type1.setTrimCorrection(new BigDecimal(1));
    list.add(type1);
    return list;
  }

  private List<ShearingForceType2> getLSFT2() {
    List<ShearingForceType2> list = new ArrayList<>();
    ShearingForceType2 type = new ShearingForceType2();
    type.setId(1L);
    type.setFrameNumber(new BigDecimal(1));
    type.setDisplacement(new BigDecimal(1));
    type.setFrameNumber(new BigDecimal(1));
    type.setBuay(new BigDecimal(1));
    type.setDifft(new BigDecimal(1));
    type.setCorrt(new BigDecimal(1));
    type.setIsActive(true);
    list.add(type);
    return list;
  }

  private List<ShearingForceType4> getLSFT4() {
    List<ShearingForceType4> list = new ArrayList<>();
    ShearingForceType4 type = new ShearingForceType4();
    type.setId(1L);
    type.setFrameNumber(new BigDecimal(1));
    type.setTrim_m1(new BigDecimal(1));
    type.setTrim_1(new BigDecimal(1));
    type.setTrim_3(new BigDecimal(1));
    type.setTrim_4(new BigDecimal(1));
    type.setTrim_5(new BigDecimal(1));
    type.setTrim_2(new BigDecimal(1));
    list.add(type);
    return list;
  }

  private List<BendingMomentShearingForceType3> getLBMSFT3() {
    List<BendingMomentShearingForceType3> list = new ArrayList<>();
    BendingMomentShearingForceType3 type = new BendingMomentShearingForceType3();
    type.setId(1L);
    type.setFrameNumber(new BigDecimal(1));
    list.add(type);
    return list;
  }

  private List<BendingMomentType1> getLBMT() {
    List<BendingMomentType1> list = new ArrayList<>();
    BendingMomentType1 bendingMomentType1 = new BendingMomentType1();
    bendingMomentType1.setId(1L);
    bendingMomentType1.setFrameNumber(new BigDecimal(1));
    bendingMomentType1.setBaseDraft(new BigDecimal(1));
    bendingMomentType1.setBaseValue(new BigDecimal(1));
    bendingMomentType1.setDraftCorrection(new BigDecimal(1));
    bendingMomentType1.setTrimCorrection(new BigDecimal(1));
    list.add(bendingMomentType1);
    return list;
  }

  private List<BendingMomentType2> getLBMT2() {
    List<BendingMomentType2> list = new ArrayList<>();
    BendingMomentType2 type = new BendingMomentType2();
    type.setId(1L);
    type.setDisplacement(new BigDecimal(1));
    type.setFrameNumber(new BigDecimal(1));
    type.setBuay(new BigDecimal(1));
    type.setDifft(new BigDecimal(1));
    type.setCorrt(new BigDecimal(1));
    type.setIsActive(true);
    list.add(type);
    return list;
  }

  private List<BendingMomentType4> getLBMT4() {
    List<BendingMomentType4> list = new ArrayList<>();
    BendingMomentType4 type = new BendingMomentType4();
    type.setId(1L);
    type.setFrameNumber(new BigDecimal(1));
    type.setTrim_m1(new BigDecimal(1));
    type.setTrim_1(new BigDecimal(1));
    type.setTrim_3(new BigDecimal(1));
    type.setTrim_4(new BigDecimal(1));
    type.setTrim_5(new BigDecimal(1));
    type.setTrim_2(new BigDecimal(1));
    list.add(type);
    return list;
  }

  private List<VesselTankTcg> getLVTT() {
    List<VesselTankTcg> list = new ArrayList<>();
    VesselTankTcg vesselTankTcg = new VesselTankTcg();
    vesselTankTcg.setId(1L);
    vesselTankTcg.setCapacity(new BigDecimal(1));
    vesselTankTcg.setTankId(1L);
    vesselTankTcg.setTcg(new BigDecimal(1));
    vesselTankTcg.setLcg(new BigDecimal(1));
    vesselTankTcg.setVcg(new BigDecimal(1));
    vesselTankTcg.setInertia(new BigDecimal(1));
    list.add(vesselTankTcg);
    return list;
  }

  private List<HydrostaticTable> getLHST() {
    List<HydrostaticTable> list = new ArrayList<>();
    HydrostaticTable hydrostaticTable = new HydrostaticTable();
    hydrostaticTable.setId(1L);
    hydrostaticTable.setTrim(new BigDecimal(1));
    hydrostaticTable.setDraft(new BigDecimal(1));
    hydrostaticTable.setDisplacement(new BigDecimal(1));
    hydrostaticTable.setLcb(new BigDecimal(1));
    hydrostaticTable.setLcf(new BigDecimal(1));
    hydrostaticTable.setMtc(new BigDecimal(1));
    hydrostaticTable.setTpc(new BigDecimal(1));
    hydrostaticTable.setVcb(new BigDecimal(1));
    hydrostaticTable.setTkm(new BigDecimal(1));
    hydrostaticTable.setLkm(new BigDecimal(1));
    list.add(hydrostaticTable);
    return list;
  }

  private List<VesselDraftCondition> getLVDC() {
    List<VesselDraftCondition> list = new ArrayList<>();
    VesselDraftCondition condition = new VesselDraftCondition();
    condition.setId(1L);
    condition.setDraftCondition(getDraft());
    condition.setDepth(new BigDecimal(1));
    condition.setFreeboard(new BigDecimal(1));
    condition.setDraftExtreme(new BigDecimal(1));
    condition.setDisplacement(new BigDecimal(1));
    condition.setDeadweight(new BigDecimal(1));
    list.add(condition);
    return list;
  }

  private Vessel getVesl() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    vessel.setName("1");
    vessel.setBuilder("1");
    vessel.setImoNumber("1");
    vessel.setPortOfRegistry("1");
    vessel.setOfficialNumber("1");
    vessel.setSignalLetter("1");
    vessel.setNavigationAreaId(1);
    vessel.setTypeOfShip("1");
    vessel.setDateOfLaunching(new Date());
    vessel.setDateOfDelivery(new Date());
    vessel.setDateKeelLaid(new Date());
    vessel.setClass1("1");
    vessel.setNavigationArea("1");
    vessel.setBm_sf_model_type(1);
    vessel.setBreadthMolded(new BigDecimal(1));
    vessel.setRegisterLength(new BigDecimal(1));
    vessel.setLengthOverall(new BigDecimal(1));
    vessel.setLengthBetweenPerpendiculars(new BigDecimal(1));
    vessel.setDepthMolded(new BigDecimal(1));
    vessel.setDesignedLoaddraft(new BigDecimal(1));
    vessel.setDraftFullLoadSummer(new BigDecimal(1));
    vessel.setThicknessOfUpperDeckStringerPlate(new BigDecimal(1));
    vessel.setThicknessOfKeelplate(new BigDecimal(1));
    vessel.setDeadweight(new BigDecimal(1));
    vessel.setLightweight(new BigDecimal(1));
    vessel.setLcg(new BigDecimal(1));
    vessel.setKeelToMastHeight(new BigDecimal(1));
    vessel.setDeadweightConstant(new BigDecimal(1));
    vessel.setProvisionalConstant(new BigDecimal(1));
    vessel.setDeadweightConstantLcg(new BigDecimal(1));
    vessel.setProvisionalConstantLcg(new BigDecimal(1));
    vessel.setGrossTonnage(new BigDecimal(1));
    vessel.setNetTonnage(new BigDecimal(1));
    vessel.setDeadweightConstantTcg(new BigDecimal(1));
    vessel.setHasLoadicator(true);
    vessel.setMaxLoadRate(new BigDecimal(1));
    vessel.setMastRiser(new BigDecimal(1));
    vessel.setHeightOfManifoldAboveDeck(new BigDecimal(1));
    vessel.setUllageTrimCorrections(getUTC());
    return vessel;
  }

  private Set<UllageTrimCorrection> getUTC() {
    Set<UllageTrimCorrection> set = new HashSet<>();
    UllageTrimCorrection correction = new UllageTrimCorrection();
    correction.setIsActive(true);
    correction.setId(1L);
    correction.setTankId(1L);
    correction.setUllageDepth(new BigDecimal(1));
    correction.setTrimM1(new BigDecimal(1));
    correction.setTrimM2(new BigDecimal(1));
    correction.setTrimM3(new BigDecimal(1));
    correction.setTrimM4(new BigDecimal(1));
    correction.setTrimM5(new BigDecimal(1));
    correction.setTrim0(new BigDecimal(1));
    correction.setTrim1(new BigDecimal(1));
    correction.setTrim2(new BigDecimal(1));
    correction.setTrim3(new BigDecimal(1));
    correction.setTrim4(new BigDecimal(1));
    correction.setTrim5(new BigDecimal(1));
    correction.setTrim6(new BigDecimal(1));
    set.add(correction);
    return set;
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
            .setDraftForTpc("1")
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
    vs.setTankType(getTT());
    vs.setShortName("1");
    vs.setTankOrder(1);
    vs.setDensity(new BigDecimal(1));
    vs.setTankCategory(gettc());
    vs.setCoatingTypeXid(1);
    vs.setFrameNumberFrom("1");
    vs.setFrameNumberTo("1");
    vs.setFullCapacityCubm(new BigDecimal(1));
    vs.setLcg(new BigDecimal(1));
    vs.setTcg(new BigDecimal(1));
    vs.setVcg(new BigDecimal(1));
    vs.setFillCapacityCubm(new BigDecimal(1));
    vs.setIsLoadicatorUsing(true);
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
    tankCategory.setName("1");
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
    array[0] = new BigInteger("1");
    array[1] = "1";
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
  void testGetVesselPumpsByVesselIdWithException() throws Exception {
    Mockito.when(this.vesselPumpService.getVesselPumpsAndTypes(Mockito.any(), Mockito.anyLong()))
        .thenThrow(new RuntimeException("1"));
    StreamRecorder<VesselInfo.VesselPumpsResponse> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselPumpsByVesselId(
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1L).build(), responseObserver);
    List<VesselInfo.VesselPumpsResponse> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private VesselInfo.VesselPumpsResponse getvsl() {
    VesselInfo.VesselPumpsResponse vessel = VesselInfo.VesselPumpsResponse.newBuilder().build();
    return vessel;
  }

  @ParameterizedTest
  @CsvSource({
    "true,Dropdown,**,**",
    "false,MultiSelect,Commence loading only in,tanks",
    "true,MultiSelect, 1, 1"
  })
  void testGetRulesByVesselIdAndSectionId(Boolean bool, String type, String pre, String suf) {
    Common.RulePlans obj = Common.RulePlans.newBuilder().setHeader("1").build();
    VesselInfo.VesselRuleRequest request =
        VesselInfo.VesselRuleRequest.newBuilder()
            .addRulePlan(obj)
            .setIsFetchEnabledRules(false)
            .setIsNoDefaultRule(bool)
            .setSectionId(1L)
            .setVesselId(1L)
            .build();
    VesselRule vesselRule =
        new VesselRule(
            "1", 1L, 1L, true, true, "1", 1L, null, pre, suf, "1", "1", "1", type, true, true, null,
            1L, 1L, null, null);
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
        .thenReturn(Arrays.asList(vesselRule));
    StreamRecorder<VesselInfo.VesselRuleReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getRulesByVesselIdAndSectionId(request, responseObserver);
    List<VesselInfo.VesselRuleReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetRulesByVesselIdAndSectionIdElse() {
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
    when(vesselRepository.findDefaultAdminRule(anyLong())).thenReturn(getListVesselRule());
    Mockito.when(this.ruleVesselDropDownValuesRepository.findByIsActive(Mockito.anyBoolean()))
        .thenReturn(getListRuleDrop());
    Mockito.when(
            this.vesselTankRepository.findCargoTankMaster(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListCargoTankMaster());
    Mockito.when(this.ruleTypeRepository.findByIsActive(Mockito.anyBoolean()))
        .thenReturn(getListRuleType());
    Mockito.when(this.vesselRepository.findRulesAgainstVessel(Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(null);
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

  @ParameterizedTest
  @CsvSource({"Dropdown,**,**", "MultiSelect,Commence loading only in,tanks", "MultiSelect, 1, 1"})
  void testSaveRulesAgainstVessel(String type, String pre, String suf) {
    Common.RulesInputs input =
        Common.RulesInputs.newBuilder()
            .setId("1")
            .setDefaultValue("1")
            .setMax("1")
            .setMin("1")
            .setPrefix(pre)
            .setSuffix(suf)
            .setType(type)
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
    ruleVesselDropDownValues.setId(1l);
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

  @Test
  void testGetVesselsInformation() {
    VesselInfo.VesselsInfoRequest request =
        VesselInfo.VesselsInfoRequest.newBuilder()
            .setSortBy("asc")
            .setOrderBy("asc")
            .setPageNo(1)
            .setPageSize(1l)
            .setVesselName("")
            .setVesselType("")
            .setBuilder("")
            .setDateOfLaunch("")
            .build();
    StreamRecorder<VesselInfo.VesselsInformationReply> responseObserver = StreamRecorder.create();
    Page<Vessel> vesselPage = new PageImpl<Vessel>(Arrays.asList(getVesl()));

    when(vesselRepository.findByIsActive(anyBoolean(), any(Pageable.class))).thenReturn(vesselPage);

    vesselInfoService.getVesselsInformation(request, responseObserver);
    List<VesselInfo.VesselsInformationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselsInformationElse() {
    VesselInfo.VesselsInfoRequest request =
        VesselInfo.VesselsInfoRequest.newBuilder()
            .setSortBy("asc")
            .setOrderBy("asc")
            .setPageNo(1)
            .setPageSize(1l)
            .setVesselName("1")
            .setVesselType("1")
            .setBuilder("1")
            .setDateOfLaunch("12-12-2012")
            .build();
    StreamRecorder<VesselInfo.VesselsInformationReply> responseObserver = StreamRecorder.create();
    Page<Vessel> vesselPage = new PageImpl<Vessel>(Arrays.asList(getVesl()));

    when(vesselRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(vesselPage);

    vesselInfoService.getVesselsInformation(request, responseObserver);
    List<VesselInfo.VesselsInformationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselsInformationWithException() {
    VesselInfo.VesselsInfoRequest request =
        VesselInfo.VesselsInfoRequest.newBuilder()
            .setSortBy("asc")
            .setOrderBy("asc")
            .setPageNo(1)
            .setPageSize(1l)
            .setVesselName("")
            .setVesselType("")
            .setBuilder("")
            .setDateOfLaunch("")
            .build();
    StreamRecorder<VesselInfo.VesselsInformationReply> responseObserver = StreamRecorder.create();

    when(vesselRepository.findByIsActive(anyBoolean(), any(Pageable.class)))
        .thenThrow(new RuntimeException("1"));

    vesselInfoService.getVesselsInformation(request, responseObserver);
    List<VesselInfo.VesselsInformationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselParticulars() throws GenericServiceException {
    VesselInfo.LoadingInfoRulesRequest request =
        VesselInfo.LoadingInfoRulesRequest.newBuilder().setVesselId(1l).build();
    StreamRecorder<VesselInfo.VesselParticulars> responseObserver = StreamRecorder.create();

    doNothing()
        .when(vesselParticularService)
        .getVesselParticulars(
            any(VesselInfo.VesselParticulars.Builder.class),
            any(VesselInfo.LoadingInfoRulesRequest.class));

    vesselInfoService.getVesselParticulars(request, responseObserver);
    List<VesselInfo.VesselParticulars> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselParticularsWithGenericException() throws GenericServiceException {
    VesselInfo.LoadingInfoRulesRequest request =
        VesselInfo.LoadingInfoRulesRequest.newBuilder().setVesselId(1l).build();
    StreamRecorder<VesselInfo.VesselParticulars> responseObserver = StreamRecorder.create();

    doCallRealMethod()
        .when(vesselParticularService)
        .getVesselParticulars(
            any(VesselInfo.VesselParticulars.Builder.class),
            any(VesselInfo.LoadingInfoRulesRequest.class));
    when(vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    ReflectionTestUtils.setField(
        vesselInfoService, "vesselParticularService", vesselParticularService);
    ReflectionTestUtils.setField(vesselParticularService, "vesselRepository", vesselRepository);

    vesselInfoService.getVesselParticulars(request, responseObserver);
    List<VesselInfo.VesselParticulars> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetaildInformation() {
    VesselInfo.VesselIdRequest request =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1l).build();
    StreamRecorder<VesselInfo.VesselDetaildInfoReply> responseObserver = StreamRecorder.create();

    when(vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getVesl());
    when(this.tankCategoryRepository.getOne(anyLong())).thenReturn(new TankCategory());
    when(this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
            any(Vessel.class), anyList(), anyBoolean()))
        .thenReturn(getLVesselTank());

    vesselInfoService.getVesselDetaildInformation(request, responseObserver);
    List<VesselInfo.VesselDetaildInfoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetaildInformationWithException() {
    VesselInfo.VesselIdRequest request =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(1l).build();
    StreamRecorder<VesselInfo.VesselDetaildInfoReply> responseObserver = StreamRecorder.create();

    when(vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(new RuntimeException("1"));

    vesselInfoService.getVesselDetaildInformation(request, responseObserver);
    List<VesselInfo.VesselDetaildInfoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanksByTankIdsWithException() {
    VesselInfo.VesselTankRequest request = VesselInfo.VesselTankRequest.newBuilder().build();
    StreamRecorder<VesselInfo.VesselTankReply> responseObserver = StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.vesselParticularService)
        .getVesselTanksByTankIds(
            any(VesselInfo.VesselTankRequest.class), any(VesselInfo.VesselTankReply.Builder.class));

    vesselInfoService.getVesselTanksByTankIds(request, responseObserver);
    List<VesselInfo.VesselTankReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanksByTankIds() {
    VesselInfo.VesselTankRequest request = VesselInfo.VesselTankRequest.newBuilder().build();
    StreamRecorder<VesselInfo.VesselTankReply> responseObserver = StreamRecorder.create();

    doNothing()
        .when(this.vesselParticularService)
        .getVesselTanksByTankIds(
            any(VesselInfo.VesselTankRequest.class), any(VesselInfo.VesselTankReply.Builder.class));

    vesselInfoService.getVesselTanksByTankIds(request, responseObserver);
    List<VesselInfo.VesselTankReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
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
