/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static com.cpdss.gateway.common.GatewayConstants.FAILED;
import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.discharge_plan.DischargePlanPortWiseDetails;
import com.cpdss.common.generated.discharge_plan.DischargeSequenceReply;
import com.cpdss.common.generated.discharge_plan.DischargingPlanSaveRequest;
import com.cpdss.common.generated.discharge_plan.DischargingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.AlgoError;
import com.cpdss.gateway.domain.Cleaning;
import com.cpdss.gateway.domain.CleaningTankDetails;
import com.cpdss.gateway.domain.dischargeplan.AlgoDischargingInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanAlgoRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanPortWiseDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.*;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingSequenceService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {DischargingSequenceService.class})
public class DischargingSequenceServiceTest {

  @Autowired DischargingSequenceService dischargingSequenceService;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @MockBean LoadingSequenceService loadingSequenceService;

  @MockBean private LoadingPlanGrpcService loadingPlanGrpcService;

  @Test ///// 355 line no
  void testBuildDischargingPlanSaveRequest() {
    DischargingPlanAlgoRequest dischargingPlanAlgoRequest = new DischargingPlanAlgoRequest();
    dischargingPlanAlgoRequest.setHasLoadicator(true);
    dischargingPlanAlgoRequest.setEvents(getLE());
    dischargingPlanAlgoRequest.setProcessId("1");
    dischargingPlanAlgoRequest.setPlans(getMSD());
    dischargingPlanAlgoRequest.setStages(getLLSSP());
    dischargingPlanAlgoRequest.setErrors(getLAE());
    dischargingPlanAlgoRequest.setDischargingInformation(getADI());
    Long vesselId = 1L;
    Long loadingInfoId = 1L;
    DischargingPlanSaveRequest.Builder builder = DischargingPlanSaveRequest.newBuilder();
    Mockito.when(vesselInfoGrpcService.getVesselPumpsByVesselId(Mockito.any()))
        .thenReturn(getVPR());
    ReflectionTestUtils.setField(
        dischargingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    this.dischargingSequenceService.buildDischargingPlanSaveRequest(
        dischargingPlanAlgoRequest, vesselId, loadingInfoId, builder);
    assertEquals(1L, builder.getPortDischargingPlanStowageDetails(0).getTankId());
  }

  private AlgoDischargingInformation getADI() {
    AlgoDischargingInformation information = new AlgoDischargingInformation();
    information.setDischargeInfoId(1L);
    return information;
  }

  private List<AlgoError> getLAE() {
    List<AlgoError> list = new ArrayList<>();
    AlgoError algoError = new AlgoError();
    algoError.setErrorDetails(getLSS());
    algoError.setErrorHeading("1");
    list.add(algoError);
    return list;
  }

  private List<String> getLSS() {
    List<String> list = new ArrayList<>();
    list.add("1");
    return list;
  }

  private List<LoadingSequenceStabilityParam> getLLSSP() {
    List<LoadingSequenceStabilityParam> list = new ArrayList<>();
    LoadingSequenceStabilityParam param = new LoadingSequenceStabilityParam();
    param.setForeDraft("1");
    param.setAfterDraft("1");
    param.setMeanDraft("1");
    param.setShearForce("1");
    param.setBendinMoment("1");
    param.setTime("1");
    param.setTrim("1");
    param.setList("1");
    list.add(param);
    return list;
  }

  private Map<String, DischargingPlan> getMSD() {
    DischargingPlan dischargingPlan = new DischargingPlan();
    dischargingPlan.setDischargePlanStowageDetails(getLLPSD());
    dischargingPlan.setDischargePlanBallastDetails(getLLPBD());
    dischargingPlan.setDischargePlanRoBDetails(getLLPRD());
    dischargingPlan.setBendinMoment("1");
    dischargingPlan.setShearForce("1");
    dischargingPlan.setForeDraft("1");
    dischargingPlan.setAfterDraft("1");
    dischargingPlan.setMeanDraft("1");
    dischargingPlan.setTrim("1");
    dischargingPlan.setList("1");
    dischargingPlan.setFreeboard("1");
    dischargingPlan.setManifoldHeight("1");
    dischargingPlan.setDischargeQuantityCommingleCargoDetails(getLLQCCD());
    Map<String, DischargingPlan> stringDischargingPlanMap = new HashMap<>();
    stringDischargingPlanMap.put("arrival", dischargingPlan);
    return stringDischargingPlanMap;
  }

  private VesselInfo.VesselPumpsResponse getVPR() {
    List<VesselInfo.VesselPump> list = new ArrayList<>();
    VesselInfo.VesselPump vesselPump =
        VesselInfo.VesselPump.newBuilder().setId(0L).setPumpName("1").build();
    list.add(vesselPump);
    VesselInfo.VesselPumpsResponse response =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .addAllVesselPump(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private List<Event> getLE() {
    List<Event> list = new ArrayList<>();
    Event event = new Event();
    event.setSequence(getLS());
    event.setDriveTank(getDT());
    event.setCargoNominationId(1L);
    list.add(event);
    return list;
  }

  private DriveTank getDT() {
    DriveTank tank = new DriveTank();
    tank.setTankShortName("1");
    tank.setTankId(1L);
    return tank;
  }

  private List<Sequence> getLS() {
    List<Sequence> list = new ArrayList<>();
    Sequence sequence = new Sequence();
    sequence.setStageWiseCargoDischargingRates(getM());
    sequence.setStageWiseCargoDischargingRates(getMM());
    sequence.setCargo(getML());
    sequence.setBallast(getML());
    sequence.setTcp(getML());
    sequence.setDeballastingRates(getMSS());
    sequence.setTankWiseCargoDischargingRates(getLMSS());
    sequence.setDischargePlanPortWiseDetails(getLDPWD());
    sequence.setStage("1");
    sequence.setTimeEnd("1");
    sequence.setTimeStart("1");
    sequence.setToLoadicator(true);
    sequence.setCleaning(getC());
    sequence.setStripping(getLEU());
    list.add(sequence);
    return list;
  }

  private List<Eduction> getLEU() {
    List<Eduction> list = new ArrayList<>();
    Eduction eduction = new Eduction();
    eduction.setTankId(1L);
    eduction.setTimeStart("1");
    eduction.setTimeEnd("1");
    list.add(eduction);
    return list;
  }

  private Cleaning getC() {
    Cleaning cleaning = new Cleaning();
    cleaning.setBtmClean(getLCTD());
    cleaning.setTopClean(getLCTD());
    cleaning.setFullClean(getLCTD());
    return cleaning;
  }

  private List<CleaningTankDetails> getLCTD() {
    List<CleaningTankDetails> list = new ArrayList<>();
    CleaningTankDetails details = new CleaningTankDetails();
    details.setTankShortName("1");
    details.setTankId(1L);
    details.setTimeStart("1");
    details.setTimeEnd("1");
    list.add(details);
    return list;
  }

  private List<DischargingPlanPortWiseDetails> getLDPWD() {
    List<DischargingPlanPortWiseDetails> list = new ArrayList<>();
    DischargingPlanPortWiseDetails details = new DischargingPlanPortWiseDetails();
    details.setDeballastingRates(getMSS());
    details.setTime("1");
    details.setDischargePlanBallastDetails(getLLPBD());
    details.setDischargePlanRoBDetails(getLLPRD());
    details.setBendinMoment("1");
    details.setShearForce("1");
    details.setForeDraft("1");
    details.setAfterDraft("1");
    details.setMeanDraft("1");
    details.setTrim("1");
    details.setList("1");
    details.setDischargePlanStowageDetails(getLLPSD());
    details.setDischargeQuantityCommingleCargoDetails(getLLQCCD());
    list.add(details);
    return list;
  }

  private List<LoadingQuantityCommingleCargoDetails> getLLQCCD() {
    List<LoadingQuantityCommingleCargoDetails> list = new ArrayList<>();
    LoadingQuantityCommingleCargoDetails details = new LoadingQuantityCommingleCargoDetails();
    details.setAbbreviation("1");
    details.setApi("1");
    details.setCargo1Id(1L);
    details.setCargo2Id(1L);
    details.setCargoNomination1Id(1L);
    details.setCargoNomination2Id(1L);
    details.setColorCode("1");
    details.setQuantityMT("1");
    details.setQuantityM3("1");
    details.setTankId(1L);
    details.setTankName("1");
    details.setTemperature("1");
    details.setUllage("1");
    list.add(details);
    return list;
  }

  private List<LoadingPlanStowageDetails> getLLPSD() {
    List<LoadingPlanStowageDetails> list = new ArrayList<>();
    LoadingPlanStowageDetails details = new LoadingPlanStowageDetails();
    details.setApi("1");
    details.setDsCargoNominationId(1L);
    details.setCargoId(1L);
    details.setCargoNominationId(1L);
    details.setQuantityM3("1");
    details.setQuantityMT("1");
    details.setTankId(1L);
    details.setTemperature("1");
    details.setUllage("1");
    details.setAbbreviation("1");
    details.setColorCode("1");
    list.add(details);
    return list;
  }

  private List<LoadingPlanRobDetails> getLLPRD() {
    List<LoadingPlanRobDetails> list = new ArrayList<>();
    LoadingPlanRobDetails details = new LoadingPlanRobDetails();
    details.setQuantityM3("1");
    details.setQuantityMT("1");
    details.setTankId(1L);
    details.setColorCode("1");
    details.setDensity(new BigDecimal(1));
    list.add(details);
    return list;
  }

  private List<LoadingPlanBallastDetails> getLLPBD() {
    List<LoadingPlanBallastDetails> list = new ArrayList<>();
    LoadingPlanBallastDetails details = new LoadingPlanBallastDetails();
    details.setQuantityM3("1");
    details.setQuantityMT("1");
    details.setSounding("1");
    details.setColorCode("1");
    details.setTankId(1L);
    details.setSg("1");
    list.add(details);
    return list;
  }

  private List<Map<String, String>> getLMSS() {
    List<Map<String, String>> list = new ArrayList<>();
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("1", "1");
    list.add(stringStringMap);
    return list;
  }

  private Map<String, List<Pump>> getML() {
    List<Pump> pumps = new ArrayList<>();
    Pump pump = new Pump();
    pump.setQuantityM3("1");
    pump.setRate("1");
    pump.setTimeEnd("1");
    pump.setTimeStart("1");
    pumps.add(pump);
    Map<String, List<Pump>> stringStringMap = new HashMap<>();
    stringStringMap.put("Gravity", pumps);
    return stringStringMap;
  }

  private Map<String, String> getM() {
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("0", "None");
    return stringStringMap;
  }

  private Map<String, String> getMM() {
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("1", "None");
    return stringStringMap;
  }

  private Map<String, String> getMSS() {
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("1", "1");
    return stringStringMap;
  }

  @Test
  void testBuildDischargingSequence() {
    Long vesselId = 1L;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    List<LoadingPlanModels.EductorOperation> list1 = new ArrayList<>();
    LoadingPlanModels.EductorOperation eductorOperation =
        LoadingPlanModels.EductorOperation.newBuilder()
            .setEductorPumpsUsed("1")
            .setTanksUsed("1")
            .setStartTime(1)
            .setEndTime(1)
            .build();
    list1.add(eductorOperation);
    List<LoadingPlanModels.LoadingPlanTankDetails> list3 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails loadingPlanTankDetails =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setCargoNominationId(1L)
            .setQuantity("1")
            .setTankId(1L)
            .setUllage("1")
            .setApi("1")
            .build();
    list3.add(loadingPlanTankDetails);
    List<LoadingPlanModels.LoadingPlanCommingleDetails> list4 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanCommingleDetails details =
        LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder()
            .setCargoNomination1Id(1L)
            .setCargoNomination2Id(1L)
            .setTankId(1L)
            .setQuantityMT("1")
            .setUllage("1")
            .build();
    list4.add(details);
    List<DischargePlanPortWiseDetails> list2 = new ArrayList<>();
    DischargePlanPortWiseDetails dischargePlanPortWiseDetails =
        DischargePlanPortWiseDetails.newBuilder()
            .addAllDischargingPlanCommingleDetails(list4)
            .addAllDischargingPlanStowageDetails(list3)
            .build();
    list2.add(dischargePlanPortWiseDetails);
    List<DischargingSequence> list = new ArrayList<>();
    DischargingSequence sequence =
        DischargingSequence.newBuilder()
            .addAllDischargePlanPortWiseDetails(list2)
            .addAllEductorOperation(list1)
            .setCargoNominationId(1L)
            .setStageName("initialCondition")
            .setStartTime(1)
            .build();
    list.add(sequence);
    DischargeSequenceReply reply =
        DischargeSequenceReply.newBuilder()
            .setInterval(1)
            .setPortId(1L)
            .setStartDate(sdf.format(new Date()))
            .addAllDischargeSequences(list)
            .setDischargePatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    response.setMinXAxisValue(1L);
    response.setMaxXAxisValue(1L);
    Mockito.when(vesselInfoGrpcService.getVesselTanks(Mockito.any())).thenReturn(getVR());
    Mockito.when(loadableStudyGrpcService.getCargoNominationByCargoNominationId(Mockito.any()))
        .thenReturn(getCNDR());
    Mockito.when(
            loadingPlanGrpcService.fetchLoadablePlanBallastDetails(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLPBD());
    Mockito.when(portInfoGrpcService.getPortInfoByPortIds(Mockito.any())).thenReturn(getPR());
    Mockito.when(vesselInfoGrpcService.getVesselPumpsByVesselId(Mockito.any()))
        .thenReturn(getVPR());
    ReflectionTestUtils.setField(
        dischargingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        dischargingSequenceService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    ReflectionTestUtils.setField(
        dischargingSequenceService, "portInfoGrpcService", this.portInfoGrpcService);
    try {
      this.dischargingSequenceService.buildDischargingSequence(vesselId, reply, response);
      Assertions.assertEquals(new BigDecimal(1), response.getCargos().get(0).getQuantity());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPR() {
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail detail = PortInfo.PortDetail.newBuilder().setTimezoneOffsetVal("1").build();
    list.add(detail);
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private List<LoadableStudy.LoadablePlanBallastDetails> getLPBD() {
    List<LoadableStudy.LoadablePlanBallastDetails> list = new ArrayList<>();
    LoadableStudy.LoadablePlanBallastDetails details =
        LoadableStudy.LoadablePlanBallastDetails.newBuilder().build();
    list.add(details);
    return list;
  }

  private VesselInfo.VesselReply getVR() {
    List<VesselInfo.VesselTankDetail> list = new ArrayList<>();
    VesselInfo.VesselTankDetail detail =
        VesselInfo.VesselTankDetail.newBuilder().setTankId(1L).build();
    list.add(detail);
    VesselInfo.VesselReply reply =
        VesselInfo.VesselReply.newBuilder()
            .addAllVesselTanks(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private LoadableStudy.CargoNominationDetailReply getCNDR() {
    LoadableStudy.CargoNominationDetailReply reply =
        LoadableStudy.CargoNominationDetailReply.newBuilder()
            .setCargoNominationdetail(LoadableStudy.CargoNominationDetail.newBuilder().build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testBuildDischargingSequenceException1() {
    Long vesselId = 1L;
    DischargeSequenceReply reply = DischargeSequenceReply.newBuilder().build();
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    Mockito.when(vesselInfoGrpcService.getVesselTanks(Mockito.any())).thenReturn(getVRNS());
    ReflectionTestUtils.setField(
        dischargingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      this.dischargingSequenceService.buildDischargingSequence(vesselId, reply, response);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselInfo.VesselReply getVRNS() {
    VesselInfo.VesselReply reply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testBuildDischargingSequenceException2() {
    Long vesselId = 1L;
    List<DischargingSequence> list = new ArrayList<>();
    DischargingSequence sequence =
        DischargingSequence.newBuilder().setCargoNominationId(1L).build();
    list.add(sequence);
    DischargeSequenceReply reply =
        DischargeSequenceReply.newBuilder()
            .setPortId(1L)
            .setStartDate("2021-11-12 11:21")
            .addAllDischargeSequences(list)
            .setDischargePatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    Mockito.when(vesselInfoGrpcService.getVesselTanks(Mockito.any())).thenReturn(getVR());
    Mockito.when(loadableStudyGrpcService.getCargoNominationByCargoNominationId(Mockito.any()))
        .thenReturn(getCNDR());
    Mockito.when(
            loadingPlanGrpcService.fetchLoadablePlanBallastDetails(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLPBD());
    Mockito.when(portInfoGrpcService.getPortInfoByPortIds(Mockito.any())).thenReturn(getPRNS());
    ReflectionTestUtils.setField(
        dischargingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        dischargingSequenceService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    ReflectionTestUtils.setField(
        dischargingSequenceService, "portInfoGrpcService", this.portInfoGrpcService);
    try {
      this.dischargingSequenceService.buildDischargingSequence(vesselId, reply, response);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPRNS() {
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testRemoveEmptyCargos() {
    List<Cargo> cargos = new ArrayList<>();
    Cargo cargo = new Cargo();
    cargo.setTankId(1L);
    cargo.setQuantity(new BigDecimal(0));
    cargos.add(cargo);
    Set<TankCategory> cargoTankCategories = new HashSet<>();
    this.dischargingSequenceService.removeEmptyCargos(cargos, cargoTankCategories);
  }
}
