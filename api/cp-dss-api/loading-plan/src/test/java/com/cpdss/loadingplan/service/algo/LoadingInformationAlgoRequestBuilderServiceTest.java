/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.domain.algo.LoadablePlanPortWiseDetails;
import com.cpdss.loadingplan.domain.rules.RulePlans;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import com.cpdss.loadingplan.service.LoadingInformationService;
import com.cpdss.loadingplan.service.LoadingPlanRuleService;
import com.cpdss.loadingplan.service.LoadingPortTideService;
import com.cpdss.loadingplan.utility.RuleUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      LoadingInformationAlgoRequestBuilderService.class,
    })
public class LoadingInformationAlgoRequestBuilderServiceTest {
  @Autowired
  LoadingInformationAlgoRequestBuilderService loadingInformationAlgoRequestBuilderService;

  @MockBean private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;
  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean LoadingPortTideService loadingPortTideDetailsService;
  @MockBean LoadingPlanRuleService loadingPlanRuleService;
  @MockBean LoadingInformationService loadingInformationService;
  private static final String SUCCESS = "SUCCESS";

  @Test
  void testCreateAlgoRequest() throws GenericServiceException, JsonProcessingException {
    LoadingInformationAlgoRequestBuilderService spyService =
        spy(LoadingInformationAlgoRequestBuilderService.class);
    LoadingPlanModels.LoadingInfoAlgoRequest request =
        LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder().setLoadingInfoId(1l).build();
    List<PortInfo.BerthDetail> berthDetailList = new ArrayList<>();
    PortInfo.BerthDetail berthDetail =
        PortInfo.BerthDetail.newBuilder()
            .setBerthName("1")
            .setMaxDraft("1")
            .setMaxLoa("1")
            .setMaxShipChannel("1")
            .setPortMaxPermissibleDraft("1")
            .build();
    berthDetailList.add(berthDetail);
    PortInfo.BerthInfoResponse response =
        PortInfo.BerthInfoResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllBerths(berthDetailList)
            .build();
    ObjectMapper mapper = new ObjectMapper();
    LoadablePlanPortWiseDetails portWiseDetails = new LoadablePlanPortWiseDetails();
    portWiseDetails.setPortId(1l);
    String jsonResult = mapper.writeValueAsString(Arrays.asList(portWiseDetails));
    LoadableStudy.LoadablePatternPortWiseDetailsJson json =
        LoadableStudy.LoadablePatternPortWiseDetailsJson.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setLoadablePatternDetails(jsonResult)
            .build();

    List<PortTideAlgo> list = new ArrayList<>();
    LoadingPlanModels.LoadingPlanRuleReply rpcReply =
        LoadingPlanModels.LoadingPlanRuleReply.newBuilder().build();
    List<RulePlans> rulePlans = new ArrayList<>();
    MockedStatic<RuleUtility> utilities = mockStatic(RuleUtility.class);

    utilities
        .when(
            () ->
                RuleUtility.buildLoadingPlanRule(any(LoadingPlanModels.LoadingPlanRuleReply.class)))
        .thenReturn(rulePlans);
    when(loadingPlanRuleService.getLoadingPlanRuleForAlgo(anyLong(), anyLong()))
        .thenReturn(rpcReply);
    when(loadingPortTideDetailsService.findRecentTideDetailsByPortIdAndLoadingInfoId(
            anyLong(), anyLong()))
        .thenReturn(list);
    when(loadableStudyService.getOnBoardQuantity(any(LoadableStudy.OnBoardQuantityRequest.class)))
        .thenReturn(getObqReply());
    when(loadableStudyService.getOnHandQuantity(any(LoadableStudy.OnHandQuantityRequest.class)))
        .thenReturn(getOhqReply());
    when(this.loadableStudyService.getLoadablePatternDetailsJson(
            any(LoadableStudy.LoadablePlanDetailsRequest.class)))
        .thenReturn(json);
    when(this.loadingInformationRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    when(loadingInformationService.getLoadingInformation(
            any(LoadingPlanModels.LoadingInformationRequest.class),
            any(LoadingPlanModels.LoadingInformation.Builder.class)))
        .thenReturn(getLoadingInfo());
    when(this.loadableStudyService.getSynopticDataForLoadingPlan(
            any(LoadableStudy.LoadingPlanIdRequest.class)))
        .thenReturn(getResponse());
    when(this.vesselInfoService.getVesselPumpsByVesselId(any(VesselInfo.VesselIdRequest.class)))
        .thenReturn(getGrpcResponse());
    when(this.portInfoServiceBlockingStub.getBerthDetailsByPortId(
            any(PortInfo.PortIdRequest.class)))
        .thenReturn(response);

    ReflectionTestUtils.setField(
        spyService, "loadingPortTideDetailsService", loadingPortTideDetailsService);
    ReflectionTestUtils.setField(spyService, "loadingPlanRuleService", loadingPlanRuleService);
    ReflectionTestUtils.setField(
        spyService, "portInfoServiceBlockingStub", portInfoServiceBlockingStub);
    ReflectionTestUtils.setField(spyService, "vesselInfoService", vesselInfoService);
    ReflectionTestUtils.setField(spyService, "loadableStudyService", loadableStudyService);
    ReflectionTestUtils.setField(
        spyService, "loadingInformationService", loadingInformationService);
    ReflectionTestUtils.setField(
        spyService, "loadingInformationRepository", loadingInformationRepository);

    var result = spyService.createAlgoRequest(request);
    assertTrue(result.getLoadingRules().getPlan().isEmpty());
  }

  @Test
  void testGetBerthInfoByPortId() {
    LoadingInformationAlgoRequestBuilderService spyService =
        spy(LoadingInformationAlgoRequestBuilderService.class);
    PortInfo.BerthInfoResponse response =
        PortInfo.BerthInfoResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(this.portInfoServiceBlockingStub.getBerthDetailsByPortId(
            any(PortInfo.PortIdRequest.class)))
        .thenReturn(response);
    ReflectionTestUtils.setField(
        spyService, "portInfoServiceBlockingStub", portInfoServiceBlockingStub);

    var result = spyService.getBerthInfoByPortId(1l);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private LoadableStudy.LoadingPlanCommonResponse getResponse() {
    List<LoadableStudy.LoadingSynopticResponse> synopticResponses = new ArrayList<>();
    LoadableStudy.LoadingSynopticResponse synopticResponse =
        LoadableStudy.LoadingSynopticResponse.newBuilder()
            .setOperationType("arr")
            .setTimeOfSunrise("1")
            .setTimeOfSunset("1")
            .build();
    synopticResponses.add(synopticResponse);
    List<LoadableStudy.LoadableQuantityCargoDetails> cargoDetailsList = new ArrayList<>();
    LoadableStudy.LoadingPlanCommonResponse response =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllSynopticData(synopticResponses)
            .addAllLoadableQuantityCargoDetails(cargoDetailsList)
            .build();
    return response;
  }

  private LoadableStudy.OnBoardQuantityReply getObqReply() {
    List<LoadableStudy.OnBoardQuantityDetail> obqList = new ArrayList<>();
    LoadableStudy.OnBoardQuantityDetail obq =
        LoadableStudy.OnBoardQuantityDetail.newBuilder()
            .setId(1l)
            .setTankId(1l)
            .setTankName("1")
            .setColorCode("1")
            .setDensity("1")
            .setCargoId(1l)
            .setAbbreviation("1")
            .setSounding("1")
            .setWeight("1")
            .setActualWeight("1")
            .setVolume("1")
            .setTemperature("1")
            .build();
    obqList.add(obq);
    LoadableStudy.OnBoardQuantityReply obqReply =
        LoadableStudy.OnBoardQuantityReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllOnBoardQuantity(obqList)
            .build();
    return obqReply;
  }

  private LoadableStudy.OnHandQuantityReply getOhqReply() {
    List<LoadableStudy.OnHandQuantityDetail> ohqList = new ArrayList<>();
    LoadableStudy.OnHandQuantityDetail ohq =
        LoadableStudy.OnHandQuantityDetail.newBuilder()
            .setId(1l)
            .setTankId(1l)
            .setTankName("1")
            .setFuelTypeId(1l)
            .setFuelType("1")
            .setFuelTypeShortName("1")
            .setPortRotationId(1l)
            .setPortId(1l)
            .setArrivalQuantity("1")
            .setActualArrivalQuantity("1")
            .setArrivalVolume("1")
            .setDepartureQuantity("1")
            .setDepartureVolume("1")
            .setColorCode("1")
            .setDensity("1")
            .build();
    ohqList.add(ohq);
    LoadableStudy.OnHandQuantityReply grpcReply =
        LoadableStudy.OnHandQuantityReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllOnHandQuantity(ohqList)
            .build();
    return grpcReply;
  }

  private VesselInfo.VesselPumpsResponse getGrpcResponse() {
    List<VesselInfo.VesselPump> vesselPumpList = new ArrayList<>();
    VesselInfo.VesselPump vesselPump =
        VesselInfo.VesselPump.newBuilder().setId(1l).setPumpName("1").build();
    vesselPumpList.add(vesselPump);
    List<VesselInfo.VesselComponent> componentList = new ArrayList<>();
    VesselInfo.VesselComponent component =
        VesselInfo.VesselComponent.newBuilder().setComponentName("1").setTankTypeName("1").build();
    componentList.add(component);
    VesselInfo.VesselPumpsResponse grpcReply =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVesselPump(vesselPumpList)
            .addAllVesselBottomLine(componentList)
            .addAllVesselManifold(componentList)
            .build();
    return grpcReply;
  }

  private LoadingPlanModels.LoadingInformation getLoadingInfo() {
    List<LoadingPlanModels.LoadingBerths> berthsList = new ArrayList<>();
    LoadingPlanModels.LoadingBerths loadingBerths =
        LoadingPlanModels.LoadingBerths.newBuilder()
            .setAirDraftLimitation("1")
            .setBerthId(1l)
            .setHoseConnections("1")
            .setItemsToBeAgreedWith("1")
            .setId(1l)
            .setLoadingInfoId(1l)
            .setMaxManifoldHeight("1")
            .setSpecialRegulationRestriction("1")
            .setSeaDraftLimitation("1")
            .setDepth("1")
            .setLineDisplacement("1")
            .build();
    berthsList.add(loadingBerths);

    List<LoadingPlanModels.DelayReasons> reasonsList = new ArrayList<>();
    LoadingPlanModels.DelayReasons reasons =
        LoadingPlanModels.DelayReasons.newBuilder().setId(1l).setReason("1").build();
    reasonsList.add(reasons);

    List<LoadingPlanModels.LoadingDelays> delaysList = new ArrayList<>();
    LoadingPlanModels.LoadingDelays delays =
        LoadingPlanModels.LoadingDelays.newBuilder()
            .setCargoId(1l)
            .setCargoNominationId(1l)
            .setDuration("1")
            .setId(1l)
            .setLoadingInfoId(1l)
            .setQuantity("1")
            .addAllReasonForDelayIds(new ArrayList<>(Arrays.asList(1l)))
            .build();
    delaysList.add(delays);

    List<LoadingPlanModels.LoadingMachinesInUse> machinesInUseList = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder()
            .setCapacity("1")
            .setId(1l)
            .setLoadingInfoId(1l)
            .setMachineId(1l)
            .setMachineType(Common.MachineType.forNumber(1))
            .build();
    machinesInUseList.add(machinesInUse);

    List<LoadingPlanModels.LoadingToppingOff> toppingOffList = new ArrayList<>();
    LoadingPlanModels.LoadingToppingOff toppingOff =
        LoadingPlanModels.LoadingToppingOff.newBuilder()
            .setCargoName("1")
            .setCargoAbbreviation("1")
            .setCargoId(1l)
            .setColourCode("1")
            .setFillingRatio("1")
            .setId(1l)
            .setLoadingInfoId(1l)
            .setOrderNumber(1)
            .setQuantity("1")
            .setRemark("1")
            .setTankId(1l)
            .setUllage("1")
            .build();
    toppingOffList.add(toppingOff);

    LoadingPlanModels.LoadingInformation loadingInformation =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .addAllLoadingBerths(berthsList)
            .setLoadingDetail(
                LoadingPlanModels.LoadingDetails.newBuilder()
                    .setStartTime("1")
                    .setTimeOfSunrise("1")
                    .setTimeOfSunset("1")
                    .setTrimAllowed(
                        LoadingPlanModels.TrimAllowed.newBuilder()
                            .setFinalTrim("1")
                            .setInitialTrim("1")
                            .setMaximumTrim("1")
                            .build())
                    .build())
            .setLoadingDelays(
                LoadingPlanModels.LoadingDelay.newBuilder()
                    .addAllReasons(reasonsList)
                    .addAllDelays(delaysList)
                    .build())
            .setLoadingInfoId(1l)
            .addAllLoadingMachines(machinesInUseList)
            .setLoadingRate(
                LoadingPlanModels.LoadingRates.newBuilder()
                    .setId(1l)
                    .setLineContentRemaining("1")
                    .setMaxDeBallastingRate("1")
                    .setMaxLoadingRate("1")
                    .setMinDeBallastingRate("1")
                    .setMinLoadingRate("1")
                    .setShoreLoadingRate("1")
                    .setNoticeTimeRateReduction("1")
                    .setNoticeTimeStopLoading("1")
                    .build())
            .setLoadingStage(
                LoadingPlanModels.LoadingStages.newBuilder()
                    .setStageDuration(1)
                    .setStageOffset(1)
                    .setTrackGradeSwitch(true)
                    .setTrackStartEndStage(true)
                    .build())
            .addAllToppingOffSequence(toppingOffList)
            .build();
    return loadingInformation;
  }

  private LoadingInformation getLoadingInfoEntity() {
    LoadingInformation loadingInfoOpt = new LoadingInformation();
    loadingInfoOpt.setVoyageId(1l);
    loadingInfoOpt.setVesselXId(1l);
    loadingInfoOpt.setPortXId(1l);
    loadingInfoOpt.setLoadablePatternXId(1l);
    loadingInfoOpt.setPortRotationXId(1l);
    loadingInfoOpt.setId(1l);
    return loadingInfoOpt;
  }
}
