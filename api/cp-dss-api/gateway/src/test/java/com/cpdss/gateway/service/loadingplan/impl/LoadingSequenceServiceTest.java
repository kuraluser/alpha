/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static com.cpdss.gateway.common.GatewayConstants.FAILED;
import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.AlgoError;
import com.cpdss.gateway.domain.loadingplan.AlgoLoadingInformation;
import com.cpdss.gateway.domain.loadingplan.sequence.*;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingSequenceService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingSequenceService.class})
public class LoadingSequenceServiceTest {

  @Autowired LoadingSequenceService loadingSequenceService;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @MockBean private LoadingPlanGrpcService loadingPlanGrpcService;

  @Test
  void testBuildLoadingSequence() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    Long vesselId = 1L;
    List<LoadingPlanModels.LoadingPlanTankDetails> list2 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails details1 =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setCargoNominationId(1L)
            .setTankId(1L)
            .setQuantity("1")
            .setUllage("1")
            .setApi("1")
            .setSounding("1")
            .build();
    list2.add(details1);
    List<LoadingPlanModels.LoadingPlanCommingleDetails> list3 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanCommingleDetails commingleDetails =
        LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder()
            .setCargoNomination1Id(1L)
            .setCargoNomination2Id(1L)
            .setTankId(1L)
            .setQuantityMT("1")
            .setUllage("1")
            .setApi("1")
            .build();
    list3.add(commingleDetails);
    List<LoadingPlanModels.LoadingPlanPortWiseDetails> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanPortWiseDetails details =
        LoadingPlanModels.LoadingPlanPortWiseDetails.newBuilder()
            .addAllLoadingPlanBallastDetails(list2)
            .addAllLoadingPlanCommingleDetails(list3)
            .addAllLoadingPlanStowageDetails(list2)
            .setTime(1)
            .build();
    list1.add(details);
    List<LoadingPlanModels.PumpOperation> list4 = new ArrayList<>();
    LoadingPlanModels.PumpOperation pumpOperation =
        LoadingPlanModels.PumpOperation.newBuilder()
            .setPumpXId(1L)
            .setRate("1")
            .setStartTime(1)
            .setEndTime(1)
            .setQuantityM3("1")
            .build();
    list4.add(pumpOperation);
    List<LoadingPlanModels.LoadingSequence> list = new ArrayList<>();
    LoadingPlanModels.LoadingSequence sequence =
        LoadingPlanModels.LoadingSequence.newBuilder()
            .addAllBallastOperations(list4)
            .addAllLoadingPlanPortWiseDetails(list1)
            .setEductorOperation(
                LoadingPlanModels.EductorOperation.newBuilder()
                    .setEndTime(1)
                    .setEductorPumpsUsed("1")
                    .setTanksUsed("1")
                    .setBallastPumpsUsed("1")
                    .setStartTime(1)
                    .build())
            .setStartTime(1)
            .setEndTime(1)
            .setCargoLoadingRate1("1")
            .setCargoLoadingRate2("1")
            .setCargoNominationId(1L)
            .setStageName("initialCondition")
            .build();
    list.add(sequence);
    LoadingPlanModels.LoadingSequenceReply reply =
        LoadingPlanModels.LoadingSequenceReply.newBuilder()
            .setInterval(1)
            .setStartDate(sdf.format(new Date()))
            .setPortId(1L)
            .setLoadablePatternId(1L)
            .setPortRotationId(1L)
            .addAllLoadingSequences(list)
            .build();
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    response.setMinXAxisValue(1L);
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
        loadingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        loadingSequenceService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    ReflectionTestUtils.setField(
        loadingSequenceService, "portInfoGrpcService", this.portInfoGrpcService);
    try {
      this.loadingSequenceService.buildLoadingSequence(vesselId, reply, response);
      assertEquals(1L, response.getCargos().get(0).getCargoId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselInfo.VesselPumpsResponse getVPR() {
    List<VesselInfo.PumpType> list1 = new ArrayList<>();
    VesselInfo.PumpType pumpType = VesselInfo.PumpType.newBuilder().setId(1L).setName("1").build();
    list1.add(pumpType);
    List<VesselInfo.VesselPump> list = new ArrayList<>();
    VesselInfo.VesselPump vesselPump =
        VesselInfo.VesselPump.newBuilder().setId(1L).setPumpCode("1").build();
    list.add(vesselPump);
    VesselInfo.VesselPumpsResponse response =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .addAllPumpType(list1)
            .addAllVesselPump(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
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
        LoadableStudy.LoadablePlanBallastDetails.newBuilder()
            .setTankId(1L)
            .setColorCode("1")
            .build();
    list.add(details);
    return list;
  }

  private VesselInfo.VesselReply getVR() {
    List<VesselInfo.VesselTankDetail> list = new ArrayList<>();
    VesselInfo.VesselTankDetail detail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankId(1L)
            .setShortName("1")
            .setTankDisplayOrder(1)
            .build();
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
            .setCargoNominationdetail(
                LoadableStudy.CargoNominationDetail.newBuilder()
                    .setCargoId(1L)
                    .setId(1L)
                    .setApi("1")
                    .setColor("1")
                    .setCargoName("1")
                    .setAbbreviation("1")
                    .build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testBuildLoadingSequenceException1() {
    Long vesselId = 1L;
    LoadingPlanModels.LoadingSequenceReply reply =
        LoadingPlanModels.LoadingSequenceReply.newBuilder().build();
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    Mockito.when(vesselInfoGrpcService.getVesselTanks(Mockito.any())).thenReturn(getVRNS());
    ReflectionTestUtils.setField(
        loadingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      this.loadingSequenceService.buildLoadingSequence(vesselId, reply, response);
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
  void testBuildLoadingSequenceException2() {
    Long vesselId = 1L;
    List<LoadingPlanModels.LoadingSequence> list = new ArrayList<>();
    LoadingPlanModels.LoadingSequence sequence =
        LoadingPlanModels.LoadingSequence.newBuilder().setCargoNominationId(1L).build();
    list.add(sequence);
    LoadingPlanModels.LoadingSequenceReply reply =
        LoadingPlanModels.LoadingSequenceReply.newBuilder()
            .setPortId(1L)
            .setLoadablePatternId(1L)
            .setPortRotationId(1L)
            .addAllLoadingSequences(list)
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
        loadingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        loadingSequenceService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    ReflectionTestUtils.setField(
        loadingSequenceService, "portInfoGrpcService", this.portInfoGrpcService);
    try {
      this.loadingSequenceService.buildLoadingSequence(vesselId, reply, response);
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
    cargo.setStart(1L);
    cargo.setTankId(1L);
    cargos.add(cargo);
    Set<TankCategory> cargoTankCategories = new HashSet<>();
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(1L);
    cargoTankCategories.add(tankCategory);
    this.loadingSequenceService.removeEmptyCargos(cargos, cargoTankCategories);
  }

  @Test
  void testRemoveEmptyBallasts() {
    List<Ballast> ballasts = new ArrayList<>();
    Ballast ballast = new Ballast();
    ballast.setTankId(1L);
    ballast.setStart(3L);
    ballast.setSounding(new BigDecimal(1));
    ballasts.add(ballast);
    Set<TankCategory> ballastTankCategories = new HashSet<>();
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(1L);
    ballastTankCategories.add(tankCategory);
    List<EductionOperation> ballastEduction = new ArrayList<>();
    EductionOperation eductionOperation = new EductionOperation();
    eductionOperation.setTanks(getL());
    eductionOperation.setTimeEnd(2L);
    ballastEduction.add(eductionOperation);
    this.loadingSequenceService.removeEmptyBallasts(
        ballasts, ballastTankCategories, ballastEduction);
  }

  private List<Long> getL() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    return list;
  }

  @Test
  void testUpdateCargoLoadingRateIntervals() {
    List<CargoLoadingRate> cargoLoadingRates = new ArrayList<>();
    CargoLoadingRate cargoLoadingRate = new CargoLoadingRate();
    cargoLoadingRate.setStartTime(4L);
    cargoLoadingRate.setEndTime(1L);
    cargoLoadingRates.add(cargoLoadingRate);
    Set<Long> stageTickPositions = new HashSet<>();
    stageTickPositions.add(2L);
    this.loadingSequenceService.updateCargoLoadingRateIntervals(
        cargoLoadingRates, stageTickPositions);
  }

  @Test
  void testBuildLoadingPlanSaveRequest() {
    LoadingPlanAlgoRequest loadingPlanAlgoRequest = new LoadingPlanAlgoRequest();
    loadingPlanAlgoRequest.setHasLoadicator(true);
    loadingPlanAlgoRequest.setProcessId("1");
    loadingPlanAlgoRequest.setEvents(getLE());
    loadingPlanAlgoRequest.setStages(getLLSSP());
    loadingPlanAlgoRequest.setPlans(getMSL());
    loadingPlanAlgoRequest.setErrors(getLAE());
    loadingPlanAlgoRequest.setLoadingInformation(getALI());
    Long vesselId = 1L;
    Long loadingInfoId = 1L;
    LoadingPlanModels.LoadingPlanSaveRequest.Builder builder =
        LoadingPlanModels.LoadingPlanSaveRequest.newBuilder();
    Mockito.when(vesselInfoGrpcService.getVesselPumpsByVesselId(Mockito.any()))
        .thenReturn(getVPR());
    ReflectionTestUtils.setField(
        loadingSequenceService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    this.loadingSequenceService.buildLoadingPlanSaveRequest(
        loadingPlanAlgoRequest, vesselId, loadingInfoId, builder);
    assertEquals("1", builder.getAlgoErrors(0).getErrorHeading());
  }

  private AlgoLoadingInformation getALI() {
    AlgoLoadingInformation algoLoadingInformation = new AlgoLoadingInformation();
    return algoLoadingInformation;
  }

  private List<AlgoError> getLAE() {
    List<AlgoError> list = new ArrayList<>();
    AlgoError algoError = new AlgoError();
    algoError.setErrorHeading("1");
    algoError.setErrorDetails(getLSS());
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
    param.setAfterDraft("1");
    param.setBendinMoment("1");
    param.setForeDraft("1");
    param.setShearForce("1");
    param.setTime("1");
    param.setTrim("1");
    param.setMeanDraft("1");
    param.setList("1");
    list.add(param);
    return list;
  }

  private Map<String, LoadingPlan> getMSL() {
    Map<String, LoadingPlan> map = new HashMap<>();
    LoadingPlan loadingPlan = new LoadingPlan();
    loadingPlan.setLoadablePlanStowageDetails(getLLPSD());
    loadingPlan.setLoadablePlanBallastDetails(getLLPBD());
    loadingPlan.setLoadablePlanRoBDetails(getLLPRD());
    loadingPlan.setBendinMoment("1");
    loadingPlan.setShearForce("1");
    loadingPlan.setForeDraft("1");
    loadingPlan.setMeanDraft("1");
    loadingPlan.setAfterDraft("1");
    loadingPlan.setTrim("1");
    loadingPlan.setFreeboard("1");
    loadingPlan.setList("1");
    loadingPlan.setManifoldHeight("1");
    loadingPlan.setLoadableQuantityCommingleCargoDetails(getLLQCD());
    map.put("arrival", loadingPlan);
    return map;
  }

  private List<Event> getLE() {
    List<Event> list = new ArrayList<>();
    Event event = new Event();
    event.setCargoNominationId(1L);
    event.setSequence(getLS());
    list.add(event);
    return list;
  }

  private List<Sequence> getLS() {
    List<Sequence> list = new ArrayList<>();
    Sequence sequence = new Sequence();
    sequence.setStageWiseCargoLoadingRates(getMS());
    sequence.setBallast(getML());
    sequence.setTankWiseCargoLoadingRates(getLMSS());
    sequence.setDeballastingRates(getMSS());
    sequence.setEduction(getE());
    sequence.setLoadablePlanPortWiseDetails(getLLPPWD());
    sequence.setStage("1");
    sequence.setTimeStart("1");
    sequence.setTimeEnd("1");
    sequence.setToLoadicator(true);
    list.add(sequence);
    return list;
  }

  private List<LoadingPlanPortWiseDetails> getLLPPWD() {
    List<LoadingPlanPortWiseDetails> list = new ArrayList<>();
    LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = new LoadingPlanPortWiseDetails();
    loadingPlanPortWiseDetails.setDeballastingRates(getMS());
    loadingPlanPortWiseDetails.setTime("1");
    loadingPlanPortWiseDetails.setLoadablePlanBallastDetails(getLLPBD());
    loadingPlanPortWiseDetails.setLoadablePlanRoBDetails(getLLPRD());
    loadingPlanPortWiseDetails.setBendinMoment("1");
    loadingPlanPortWiseDetails.setShearForce("1");
    loadingPlanPortWiseDetails.setForeDraft("1");
    loadingPlanPortWiseDetails.setAfterDraft("1");
    loadingPlanPortWiseDetails.setMeanDraft("1");
    loadingPlanPortWiseDetails.setTrim("1");
    loadingPlanPortWiseDetails.setList("1");
    loadingPlanPortWiseDetails.setLoadablePlanStowageDetails(getLLPSD());
    loadingPlanPortWiseDetails.setLoadableQuantityCommingleCargoDetails(getLLQCD());
    list.add(loadingPlanPortWiseDetails);
    return list;
  }

  private List<LoadingQuantityCommingleCargoDetails> getLLQCD() {
    List<LoadingQuantityCommingleCargoDetails> list = new ArrayList<>();
    LoadingQuantityCommingleCargoDetails commingleCargoDetails =
        new LoadingQuantityCommingleCargoDetails();
    commingleCargoDetails.setApi("1");
    commingleCargoDetails.setCargo2Id(1L);
    commingleCargoDetails.setCargo1Id(1L);
    commingleCargoDetails.setAbbreviation("1");
    commingleCargoDetails.setCargoNomination2Id(1L);
    commingleCargoDetails.setCargoNomination1Id(1L);
    commingleCargoDetails.setColorCode("1");
    commingleCargoDetails.setTankId(1L);
    commingleCargoDetails.setTankName("1");
    commingleCargoDetails.setQuantityMT("1");
    commingleCargoDetails.setQuantityM3("1");
    commingleCargoDetails.setTemperature("1");
    commingleCargoDetails.setUllage("1");
    commingleCargoDetails.setQuantity1M3("1");
    commingleCargoDetails.setQuantity2M3("1");
    commingleCargoDetails.setQuantity1MT("1");
    commingleCargoDetails.setQuantity2MT("1");
    commingleCargoDetails.setUllage1("1");
    commingleCargoDetails.setUllage2("1");
    list.add(commingleCargoDetails);
    return list;
  }

  private List<LoadingPlanStowageDetails> getLLPSD() {
    List<LoadingPlanStowageDetails> list = new ArrayList<>();
    LoadingPlanStowageDetails loadingPlanStowageDetails = new LoadingPlanStowageDetails();
    loadingPlanStowageDetails.setApi("1");
    loadingPlanStowageDetails.setCargoNominationId(1L);
    loadingPlanStowageDetails.setQuantityMT("1");
    loadingPlanStowageDetails.setQuantityM3("1");
    loadingPlanStowageDetails.setTankId(1L);
    loadingPlanStowageDetails.setTemperature("1");
    loadingPlanStowageDetails.setUllage("1");
    loadingPlanStowageDetails.setAbbreviation("1");
    loadingPlanStowageDetails.setColorCode("1");
    loadingPlanStowageDetails.setCargoId(1L);
    list.add(loadingPlanStowageDetails);
    return list;
  }

  private List<LoadingPlanRobDetails> getLLPRD() {
    List<LoadingPlanRobDetails> list = new ArrayList<>();
    LoadingPlanRobDetails loadingPlanRobDetails = new LoadingPlanRobDetails();
    loadingPlanRobDetails.setQuantityMT("1");
    loadingPlanRobDetails.setQuantityM3("1");
    loadingPlanRobDetails.setTankId(1L);
    loadingPlanRobDetails.setDensity(new BigDecimal(1));
    loadingPlanRobDetails.setColorCode("1");
    list.add(loadingPlanRobDetails);
    return list;
  }

  private List<LoadingPlanBallastDetails> getLLPBD() {
    List<LoadingPlanBallastDetails> list = new ArrayList<>();
    LoadingPlanBallastDetails ballastDetails = new LoadingPlanBallastDetails();
    ballastDetails.setQuantityMT("1");
    ballastDetails.setQuantityM3("1");
    ballastDetails.setSounding("1");
    ballastDetails.setColorCode("1");
    ballastDetails.setTankId(1L);
    ballastDetails.setSg("1");
    list.add(ballastDetails);
    return list;
  }

  private Eduction getE() {
    Eduction eduction = new Eduction();
    eduction.setTimeStart("1");
    eduction.setTimeEnd("1");
    eduction.setPumpSelected(getMSO());
    eduction.setBallastPumpSelected(getMSO());
    eduction.setTank(getMS());
    return eduction;
  }

  private Map<String, Object> getMSO() {
    Map<String, Object> map = new HashMap<>();
    Object obj = new Object();
    map.put("1", obj);
    return map;
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

  private Map<String, String> getMSS() {
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("1", "1");
    return stringStringMap;
  }

  private Map<String, String> getMS() {
    Map<String, String> map = new HashMap<>();
    map.put("0", "None");
    return map;
  }
}
