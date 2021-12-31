/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.dischargeplan.*;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.sequence.*;
import com.cpdss.gateway.domain.loadingplan.sequence.Cargo;
import com.cpdss.gateway.domain.vessel.VesselPump;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.utility.ExcelExportUtility;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {GenerateDischargingPlanExcelReportService.class})
public class GenerateDischargingPlanExcelReportServiceTest {
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  @Autowired GenerateDischargingPlanExcelReportService generateDischargingPlanExcelReportService;
  @MockBean DischargeInformationService dischargeInformationService;
  @MockBean ExcelExportUtility excelExportUtil;
  @MockBean private VesselInfoService vesselInfoService;
  @MockBean private DischargingInstructionService dischargingInstructionService;
  @MockBean LoadingPlanGrpcService loadingPlanGrpcService;
  @MockBean private com.cpdss.gateway.service.FileRepoService FileRepoService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  public String TEMP_LOCATION = "temp.xlsx";
  public String TEMPLATES_FILE_LOCATION =
      "/reports/discharging/Vessel_{type}_Discharging_Plan_Template.xlsx";

  //    @Test
  //      void testGenerateDischargingPlanExcel() throws Exception {
  //        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
  //        DischargePlanResponse requestPayload = new DischargePlanResponse();
  //        requestPayload.setBallastFrontTanks(getLLVT());
  //        requestPayload.setBallastCenterTanks(getLLVT());
  //        requestPayload.setBallastRearTanks(getLLVT());
  //        requestPayload.setDischargingInformation(getDI());
  //        requestPayload.setPlanStabilityParams(getLLPSP());
  //        requestPayload.setCargoTanks(getLLVT());
  //        requestPayload.setPlanStowageDetails(getLLPSD());
  //        requestPayload.setPlanBallastDetails(getLLPBD());
  //        requestPayload.setCurrentPortCargos(getLDQCD());
  //        Long vesselId = 1L;
  //        Long voyageId = 1L;
  //        Long infoId = 1L;
  //        Long portRotationId = 1L;
  //        Boolean downloadRequired = true;
  //        try {
  //
  // Mockito.when(vesselInfoService.getVesselParticulars(Mockito.anyLong())).thenReturn(getVPE());
  //
  // Mockito.when(dischargeInformationService.getDischargingPlan(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString()))
  //                    .thenReturn(getDPR());
  //
  // Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong())).thenReturn(getVR());
  //
  // Mockito.when(vesselInfoService.getVesselsByCompany(Mockito.anyLong(),Mockito.any())).thenReturn(getVRR());
  //
  // Mockito.when(portInfoGrpcService.getPortInfoByPortIds(Mockito.any())).thenReturn(getPR());
  //
  // Mockito.when(dischargingInstructionService.getDischargingInstructions(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong())).thenReturn(getDIR());
  //
  // Mockito.when(dischargeInformationService.getDischargingSequence(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong())).thenReturn(getLSR());
  //
  // Mockito.when(excelExportUtil.generateExcel(Mockito.any(),Mockito.anyString(),Mockito.anyString())).thenReturn(new File(TEMPLATES_FILE_LOCATION));
  //
  //
  //        //
  // ReflectionTestUtils.setField(generateDischargingPlanExcelReportService,"rootFolder","D:\\latestwork1");
  //      //
  // ReflectionTestUtils.setField(generateDischargingPlanExcelReportService,"excelExportUtil",this.excelExportUtil);
  //
  // ReflectionTestUtils.setField(generateDischargingPlanExcelReportService,"portInfoGrpcService",this.portInfoGrpcService);
  //            var response =
  // this.generateDischargingPlanExcelReportService.generateDischargingPlanExcel(requestPayload,vesselId,voyageId,infoId,portRotationId,downloadRequired);
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

  private LoadingSequenceResponse getLSR() {
    LoadingSequenceResponse loadingSequenceResponse = new LoadingSequenceResponse();
    loadingSequenceResponse.setMinXAxisValue(1L);
    loadingSequenceResponse.setStageTickPositions(getSL());
    loadingSequenceResponse.setCargoTankCategories(getLTC());
    loadingSequenceResponse.setCleaningTanks(getCT());
    loadingSequenceResponse.setCargos(getLC());
    loadingSequenceResponse.setCargoDischargingRates(getLCLR());
    loadingSequenceResponse.setDriveTanks(getLDT());
    loadingSequenceResponse.setBallastTankCategories(getLTC());
    loadingSequenceResponse.setBallasts(getLBB());
    loadingSequenceResponse.setCargoPumps(getLBP());
    loadingSequenceResponse.setBallastPumps(getLBP());
    loadingSequenceResponse.setStabilityParams(getLSP());
    return loadingSequenceResponse;
  }

  private List<StabilityParam> getLSP() {
    List<StabilityParam> list = new ArrayList<>();
    StabilityParam stabilityParam = new StabilityParam();
    stabilityParam.setName("fore_draft");
    stabilityParam.setData(getLLD());
    list.add(stabilityParam);
    return list;
  }

  private List<List> getLLD() {
    List<List> list = new ArrayList<>();
    List list1 = new ArrayList();
    list1.add(1L);
    list1.add(1L);
    list.add(list1);
    return list;
  }

  private List<BallastPump> getLBP() {
    List<BallastPump> list = new ArrayList<>();
    BallastPump ballastPump = new BallastPump();
    ballastPump.setPumpId(1L);
    ballastPump.setStart(1L);
    ballastPump.setEnd(2L);
    ballastPump.setQuantityM3(new BigDecimal(1));
    ballastPump.setRate(new BigDecimal(1));
    list.add(ballastPump);
    return list;
  }

  private List<Ballast> getLBB() {
    List<Ballast> list = new ArrayList<>();
    Ballast ballast = new Ballast();
    ballast.setTankId(1L);
    ballast.setQuantity(new BigDecimal(1));
    ballast.setColor("1");
    ballast.setStart(1L);
    ballast.setEnd(2L);
    ballast.setSounding(new BigDecimal(1));
    list.add(ballast);
    return list;
  }

  private List<DriveTank> getLDT() {
    List<DriveTank> list = new ArrayList<>();
    DriveTank driveTank = new DriveTank();
    driveTank.setStart(1L);
    driveTank.setTankName("1");

    list.add(driveTank);
    return list;
  }

  private List<CargoLoadingRate> getLCLR() {
    List<CargoLoadingRate> list = new ArrayList<>();
    CargoLoadingRate cargoLoadingRate = new CargoLoadingRate();
    cargoLoadingRate.setDischargingRates(getLB());
    cargoLoadingRate.setStartTime(1L);
    cargoLoadingRate.setEndTime(2L);
    list.add(cargoLoadingRate);
    return list;
  }

  private List<BigDecimal> getLB() {
    List<BigDecimal> list = new ArrayList<>();
    list.add(new BigDecimal(1));
    return list;
  }

  private CleaningTank getCT() {
    CleaningTank cleaningTank = new CleaningTank();
    cleaningTank.setFullTanks(getLCTD());
    cleaningTank.setTopTanks(getLCTD());
    cleaningTank.setBottomTanks(getLCTD());
    return cleaningTank;
  }

  private List<CowTankDetail> getLCTD() {
    List<CowTankDetail> list = new ArrayList<>();
    CowTankDetail cowTankDetail = new CowTankDetail();
    cowTankDetail.setTankId(1L);
    cowTankDetail.setStart(2L);
    cowTankDetail.setEnd(1L);
    list.add(cowTankDetail);
    return list;
  }

  private List<Cargo> getLC() {
    List<Cargo> list = new ArrayList<>();
    Cargo cargo = new Cargo();
    cargo.setTankId(1L);
    cargo.setQuantity(new BigDecimal(1));
    cargo.setAbbreviation("1");
    cargo.setColor("1");
    cargo.setStart(1L);
    cargo.setEnd(1L);
    cargo.setUllage(new BigDecimal(1));
    cargo.setCargoNominationId(1L);
    list.add(cargo);
    return list;
  }

  private List<TankCategory> getLTC() {
    List<TankCategory> list = new ArrayList<>();
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(1L);
    tankCategory.setUllage(new BigDecimal(1));
    tankCategory.setDisplayOrder(1);
    tankCategory.setQuantity(new BigDecimal(1));
    list.add(tankCategory);
    return list;
  }

  private Set<Long> getSL() {
    Set<Long> set = new HashSet<>();
    set.add(1L);
    return set;
  }

  private DischargingInstructionResponse getDIR() {
    DischargingInstructionResponse response = new DischargingInstructionResponse();
    response.setDischargingInstructionGroupList(getLDIG());
    response.setDischargingInstructionSubHeader(getLDISH());
    return response;
  }

  private List<DischargingInstructionSubHeader> getLDISH() {
    List<DischargingInstructions> list1 = new ArrayList<>();
    DischargingInstructions instructions = new DischargingInstructions(1L, 1L, 1L, "1", true, true);
    list1.add(instructions);
    List<DischargingInstructionSubHeader> list = new ArrayList<>();
    DischargingInstructionSubHeader dischargingInstructionSubHeader =
        new DischargingInstructionSubHeader(1L, 1L, 1L, "1", true, true, true, list1);
    list.add(dischargingInstructionSubHeader);
    return list;
  }

  private List<DischargingInstructionGroup> getLDIG() {
    List<DischargingInstructionGroup> list = new ArrayList<>();
    DischargingInstructionGroup dischargingInstructionGroup =
        new DischargingInstructionGroup(1L, "1");
    list.add(dischargingInstructionGroup);
    return list;
  }

  private List<LoadingPlanBallastDetails> getLLPBD() {
    List<LoadingPlanBallastDetails> list = new ArrayList<>();
    LoadingPlanBallastDetails loadingPlanBallastDetails = new LoadingPlanBallastDetails();
    loadingPlanBallastDetails.setTankId(1L);
    loadingPlanBallastDetails.setConditionType(1);
    loadingPlanBallastDetails.setValueType(2);
    loadingPlanBallastDetails.setColorCode("1");
    loadingPlanBallastDetails.setQuantityMT("1");
    list.add(loadingPlanBallastDetails);
    return list;
  }

  private List<LoadingPlanStowageDetails> getLLPSD() {
    List<LoadingPlanStowageDetails> list = new ArrayList<>();
    LoadingPlanStowageDetails loadingPlanStowageDetails = new LoadingPlanStowageDetails();
    loadingPlanStowageDetails.setTankId(1L);
    loadingPlanStowageDetails.setConditionType(1);
    loadingPlanStowageDetails.setValueType(2);
    loadingPlanStowageDetails.setCargoId(1L);
    loadingPlanStowageDetails.setQuantityMT("1");
    loadingPlanStowageDetails.setApi("1");
    loadingPlanStowageDetails.setTemperature("1");
    loadingPlanStowageDetails.setUllage("1");
    loadingPlanStowageDetails.setColorCode("1");
    loadingPlanStowageDetails.setAbbreviation("1");

    list.add(loadingPlanStowageDetails);
    return list;
  }

  private List<LoadingPlanStabilityParam> getLLPSP() {
    List<LoadingPlanStabilityParam> list = new ArrayList<>();
    LoadingPlanStabilityParam loadingPlanStabilityParam = new LoadingPlanStabilityParam();
    loadingPlanStabilityParam.setConditionType(1);
    loadingPlanStabilityParam.setAftDraft("1");
    loadingPlanStabilityParam.setForeDraft("1");
    loadingPlanStabilityParam.setTrim("1");
    list.add(loadingPlanStabilityParam);
    return list;
  }

  private PortInfo.PortReply getPR() {
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail portDetail = PortInfo.PortDetail.newBuilder().setName("1").build();
    list.add(portDetail);
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private VesselResponse getVRR() {
    VesselResponse vesselResponse = new VesselResponse();
    vesselResponse.setVessels(getLV());
    return vesselResponse;
  }

  private List<Vessel> getLV() {
    List<Vessel> list = new ArrayList<>();
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    vessel.setName("1");
    vessel.setCaptainName("1");
    vessel.setChiefOfficerName("1");
    vessel.setLoadlines(getLLL());
    list.add(vessel);
    return list;
  }

  private List<LoadLine> getLLL() {
    List<LoadLine> list = new ArrayList<>();
    LoadLine loadLine = new LoadLine();
    loadLine.setId(1L);
    loadLine.setName("1");
    list.add(loadLine);
    return list;
  }

  private VoyageResponse getVR() {
    VoyageResponse voyageResponse = new VoyageResponse();
    voyageResponse.setDischargePortRotations(getLPR());
    voyageResponse.setVoyageNumber("1");
    voyageResponse.setActiveLs(getLS());
    return voyageResponse;
  }

  private LoadableStudy getLS() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setLoadLineXId(1L);
    loadableStudy.setDraftRestriction(new BigDecimal(1));
    return loadableStudy;
  }

  private List<PortRotation> getLPR() {
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    List<PortRotation> list = new ArrayList<>();
    PortRotation portRotation = new PortRotation();
    portRotation.setId(1L);
    portRotation.setPortId(1L);
    portRotation.setEta(String.valueOf(dt.format(new Date())));
    portRotation.setEtd(String.valueOf(dt.format(new Date())));
    list.add(portRotation);
    return list;
  }

  private DischargePlanResponse getDPR() {
    DischargePlanResponse response = new DischargePlanResponse();
    response.setVoyageDate("1");
    response.setBallastFrontTanks(getLLVT());
    response.setBallastCenterTanks(getLLVT());
    response.setBallastRearTanks(getLLVT());
    response.setDischargingInformation(getDI());
    return response;
  }

  private DischargeInformation getDI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setMachineryInUses(getCMU());
    dischargeInformation.setBerthDetails(getLBD());
    dischargeInformation.setCargoVesselTankDetails(getCVTD());
    dischargeInformation.setCowPlan(getCP());
    return dischargeInformation;
  }

  private CowPlan getCP() {
    CowPlan cowPlan = new CowPlan();
    cowPlan.setAllCow(getL());
    cowPlan.setTopCow(getL());
    cowPlan.setBottomCow(getL());
    cowPlan.setCowStart("1");
    cowPlan.setCowEnd("1");
    cowPlan.setCowDuration("1");
    cowPlan.setCargoCow(getLCCD());
    return cowPlan;
  }

  private List<CargoForCowDetails> getLCCD() {
    List<CargoForCowDetails> list = new ArrayList<>();
    CargoForCowDetails cargoForCowDetails = new CargoForCowDetails();
    cargoForCowDetails.setTankIds(getL());
    cargoForCowDetails.setWashingCargoNominationId(1L);
    cargoForCowDetails.setCargoNominationId(1L);
    list.add(cargoForCowDetails);
    return list;
  }

  private List<Long> getL() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    return list;
  }

  private LoadingBerthDetails getLBD() {
    LoadingBerthDetails loadingBerthDetails = new LoadingBerthDetails();
    loadingBerthDetails.setSelectedBerths(getLBDD());
    loadingBerthDetails.setAvailableBerths(getLBDD());
    return loadingBerthDetails;
  }

  private List<BerthDetails> getLBDD() {
    List<BerthDetails> list = new ArrayList<>();
    BerthDetails berthDetails = new BerthDetails();
    berthDetails.setBerthId(1L);
    berthDetails.setBerthName("1");
    berthDetails.setHoseConnections("1");
    berthDetails.setMaxManifoldPressure("1");
    berthDetails.setAirDraftLimitation(new BigDecimal(1));
    berthDetails.setAirPurge(true);
    berthDetails.setRegulationAndRestriction("1");
    berthDetails.setItemsToBeAgreedWith("1");
    list.add(berthDetails);
    return list;
  }

  private CargoVesselTankDetails getCVTD() {
    CargoVesselTankDetails cargoVesselTankDetails = new CargoVesselTankDetails();
    cargoVesselTankDetails.setDischargeQuantityCargoDetails(getLDQCD());
    return cargoVesselTankDetails;
  }

  private List<DischargeQuantityCargoDetails> getLDQCD() {
    List<DischargeQuantityCargoDetails> list = new ArrayList<>();
    DischargeQuantityCargoDetails details = new DischargeQuantityCargoDetails();
    details.setColorCode("1");
    details.setCargoAbbreviation("1");
    details.setDischargeCargoNominationId(1L);

    list.add(details);
    return list;
  }

  private CargoMachineryInUse getCMU() {
    CargoMachineryInUse cargoMachineryInUse = new CargoMachineryInUse();
    cargoMachineryInUse.setVesselPumps(getLVP());
    return cargoMachineryInUse;
  }

  private List<VesselPump> getLVP() {
    List<VesselPump> list = new ArrayList<>();
    VesselPump vesselPump = new VesselPump();
    vesselPump.setPumpTypeId(1L);
    vesselPump.setPumpName("1");

    list.add(vesselPump);
    return list;
  }

  private List<List<VesselTank>> getLLVT() {
    List<List<VesselTank>> list = new ArrayList<>();
    List<VesselTank> list1 = new ArrayList<>();
    VesselTank vesselTank = new VesselTank();
    vesselTank.setId(1L);
    vesselTank.setShortName("1");
    vesselTank.setFullCapacityCubm("1");
    list1.add(vesselTank);
    list.add(list1);
    return list;
  }

  private VesselParticularsForExcel getVPE() {
    VesselParticularsForExcel vesselParticularsForExcel = new VesselParticularsForExcel();
    vesselParticularsForExcel.setVesselTypeId(1L);
    return vesselParticularsForExcel;
  }

  @Test
  void testGenerateDischargingPlanExcelException1() throws Exception {
    DischargePlanResponse requestPayload = new DischargePlanResponse();
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Boolean downloadRequired = true;
    try {
      Mockito.when(vesselInfoService.getVesselParticulars(Mockito.anyLong()))
          .thenReturn(getVPENS());
      var response =
          this.generateDischargingPlanExcelReportService.generateDischargingPlanExcel(
              requestPayload, vesselId, voyageId, infoId, portRotationId, downloadRequired);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselParticularsForExcel getVPENS() {
    VesselParticularsForExcel vesselParticularsForExcel = new VesselParticularsForExcel();
    return vesselParticularsForExcel;
  }

  @Test
  void testGenerateDischargingPlanExcelException2() throws Exception {
    DischargePlanResponse requestPayload = new DischargePlanResponse();
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Boolean downloadRequired = true;
    try {
      Mockito.when(vesselInfoService.getVesselParticulars(Mockito.anyLong())).thenReturn(getVPE());
      Mockito.when(
              dischargeInformationService.getDischargingPlan(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyString()))
          .thenReturn(getDPR());
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.when(vesselInfoService.getVesselsByCompany(Mockito.anyLong(), Mockito.anyString()))
          .thenReturn(getVRRR());
      var response =
          this.generateDischargingPlanExcelReportService.generateDischargingPlanExcel(
              requestPayload, vesselId, voyageId, infoId, portRotationId, downloadRequired);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselResponse getVRRR() {
    VesselResponse vesselResponse = new VesselResponse();
    vesselResponse.setVessels(getLVV());
    return vesselResponse;
  }

  private List<Vessel> getLVV() {
    List<Vessel> list = new ArrayList<>();
    return list;
  }

  @Test
  void testGenerateDischargingPlanExcelException3() throws Exception {
    DischargePlanResponse requestPayload = new DischargePlanResponse();
    requestPayload.setBallastFrontTanks(getLLVT());
    requestPayload.setBallastCenterTanks(getLLVT());
    requestPayload.setBallastRearTanks(getLLVT());
    requestPayload.setDischargingInformation(getDI());
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Boolean downloadRequired = true;
    try {
      Mockito.when(vesselInfoService.getVesselParticulars(Mockito.anyLong())).thenReturn(getVPE());
      Mockito.when(
              dischargeInformationService.getDischargingPlan(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyString()))
          .thenReturn(getDPR());
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.when(vesselInfoService.getVesselsByCompany(Mockito.anyLong(), Mockito.any()))
          .thenReturn(getVRRNS());
      var response =
          this.generateDischargingPlanExcelReportService.generateDischargingPlanExcel(
              requestPayload, vesselId, voyageId, infoId, portRotationId, downloadRequired);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselResponse getVRRNS() {
    VesselResponse vesselResponse = new VesselResponse();
    vesselResponse.setVessels(getLVNS());
    return vesselResponse;
  }

  private List<Vessel> getLVNS() {
    List<Vessel> list = new ArrayList<>();
    Vessel vessel = new Vessel();
    vessel.setId(21L);
    list.add(vessel);
    return list;
  }

  @Test
  void testGetPortInfo() {
    PortInfo.GetPortInfoByPortIdsRequest build =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder().build();
    Mockito.when(portInfoGrpcService.getPortInfoByPortIds(Mockito.any())).thenReturn(getPR());
    ReflectionTestUtils.setField(
        generateDischargingPlanExcelReportService, "portInfoGrpcService", this.portInfoGrpcService);
    var response = this.generateDischargingPlanExcelReportService.getPortInfo(build);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }
}
