/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.Loadicator;
import com.cpdss.common.generated.Loadicator.BallastInfo;
import com.cpdss.common.generated.Loadicator.CargoInfo;
import com.cpdss.common.generated.Loadicator.LoadicatorReply;
import com.cpdss.common.generated.Loadicator.LoadicatorRequest;
import com.cpdss.common.generated.Loadicator.OtherTankInfo;
import com.cpdss.common.generated.Loadicator.StowageDetails;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataReply;
import com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.loadicatorintegration.domain.StowagePlanDetail;
import com.cpdss.loadicatorintegration.entity.IntactStability;
import com.cpdss.loadicatorintegration.entity.LoadicatorStrength;
import com.cpdss.loadicatorintegration.entity.LoadicatorTrim;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import com.cpdss.loadicatorintegration.repository.*;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

/** @Author jerin.g */
@SpringJUnitConfig(classes = {LoadicatorService.class})
public class LoadicatorIntegrationServiceTest {

  @Autowired private LoadicatorService loadicatorService;
  @MockBean private StowagePlanRepository stowagePlanRepository;
  @MockBean private LoadicatorTrimRepository loadicatorTrimRepository;
  @MockBean private LoadicatorStrengthRepository loadicatorStrengthRepository;
  @MockBean private LoadicatorIntactStabilityRepository loadicatorIntactStabilityRepository;
  @MockBean private CargoDataRepository cargoDataRepository;
  @MockBean private StowageDetailsRepository stowageDetailsRepository;
  @MockBean private SimpleAsyncTaskExecutor taskExecutor;
  @MockBean private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyService;
  @MockBean private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanService;

  @MockBean
  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub dischargingPlanService;

  private static final String SUCCESS = "SUCCESS";
  private static final String DUMMY_STRING = "DUMMY_STRING";
  private static final Long DUMMY_LONG = 1L;

  @Test
  void testSaveLoadicatorInfo() {
    List<StowagePlan> stowagePlanList = new ArrayList<>();
    StowagePlan stowagePlan = new StowagePlan();
    stowagePlan.setStowageId(DUMMY_LONG);
    stowagePlan.setBookingListId(DUMMY_LONG);
    stowagePlan.setProcessId(DUMMY_STRING);
    stowagePlanList.add(stowagePlan);
    when(this.stowagePlanRepository.saveAll(any())).thenReturn(stowagePlanList);

    LoadicatorService spyService = Mockito.spy(this.loadicatorService);
    when(this.loadicatorIntactStabilityRepository.findByStowagePlanIdIn(any()))
        .thenReturn(createIntactStabilityList());
    when(this.stowagePlanRepository.findPortForStability(any()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorStrengthRepository.findByStowagePlanIdIn(any()))
        .thenReturn(createLoadicatorStrengthDetails());
    when(this.stowagePlanRepository.findPortForStrength(any()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorTrimRepository.findByStowagePlanIdIn(any()))
        .thenReturn(createLoadicatorTrimDetails());
    when(this.stowagePlanRepository.findPortForTrim(any())).thenReturn(createStowagePlanDetails());

    Mockito.doReturn(
            LoadicatorDataReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build())
        .when(spyService)
        .getLoadicatorDatas(any(LoadicatorDataRequest.class));

    StreamRecorder<LoadicatorReply> responseObserver = StreamRecorder.create();
    spyService.saveLoadicatorInfo(createLoadicatorRequest(), responseObserver);
    List<LoadicatorReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadicatorDatas() {
    LoadicatorDataRequest request = LoadicatorDataRequest.newBuilder().build();
    LoadableStudy.LoadicatorDataReply reply =
        LoadicatorDataReply.newBuilder()
            .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(loadableStudyService.getLoadicatorData(any(LoadableStudy.LoadicatorDataRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(loadicatorService, "loadableStudyService", loadableStudyService);

    var result = loadicatorService.getLoadicatorDatas(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetStatus() throws InterruptedException {
    when(this.stowagePlanRepository.findByIdIn(any())).thenReturn(createStowagePlanList());
    when(stowagePlanRepository.findCountOfStowagePlansToBeProcessed()).thenReturn(1l);
    when(loadableStudyService.getLoadicatorData(any(LoadableStudy.LoadicatorDataRequest.class)))
        .thenReturn(LoadicatorDataReply.newBuilder().build());
    when(this.loadicatorTrimRepository.findByStowagePlanIdIn(anyList()))
        .thenReturn(createLoadicatorTrimDetails());
    when(this.stowagePlanRepository.findPortForTrim(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorStrengthRepository.findByStowagePlanIdIn(anyList()))
        .thenReturn(createLoadicatorStrengthDetails());
    when(this.stowagePlanRepository.findPortForStrength(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(loadicatorIntactStabilityRepository.findByStowagePlanIdIn(anyList()))
        .thenReturn(createIntactStabilityList());
    when(this.stowagePlanRepository.findPortForStability(anyLong()))
        .thenReturn(createStowagePlanDetails());

    ReflectionTestUtils.setField(loadicatorService, "loadableStudyService", loadableStudyService);

    loadicatorService.getStatus(createStowagePlanList(), createLoadicatorRequest());
    verify(loadableStudyService).getLoadicatorData(any(LoadableStudy.LoadicatorDataRequest.class));
  }

  @Test
  void testGetStatusWithTypeId2() throws InterruptedException {
    List<Loadicator.StowagePlan> planList = new ArrayList<>();
    Loadicator.StowagePlan plan =
        Loadicator.StowagePlan.newBuilder().setBookingListId(1l).setStowageId(1l).build();
    planList.add(plan);
    LoadicatorRequest request =
        LoadicatorRequest.newBuilder()
            .setTypeId(2l)
            .setIsPattern(true)
            .addAllStowagePlanDetails(planList)
            .setIsUllageUpdate(true)
            .setConditionType(1)
            .build();
    when(this.stowagePlanRepository.findByIdIn(any())).thenReturn(createStowagePlanList());
    when(stowagePlanRepository.findCountOfStowagePlansToBeProcessed()).thenReturn(1l);
    when(loadicatorTrimRepository.findByStowagePlanId(anyLong()))
        .thenReturn(createLoadicatorTrimDetails().get(0));
    when(loadicatorStrengthRepository.findByStowagePlanId(anyLong()))
        .thenReturn(createLoadicatorStrengthDetails().get(0));
    when(loadicatorIntactStabilityRepository.findByStowagePlanId(anyLong()))
        .thenReturn(createIntactStabilityList().get(0));
    when(this.stowagePlanRepository.findPortForTrim(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.stowagePlanRepository.findPortForStrength(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.stowagePlanRepository.findPortForStability(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(loadingPlanService.getLoadicatorData(
            any(LoadingPlanModels.LoadingInfoLoadicatorDataRequest.class)))
        .thenReturn(LoadingPlanModels.LoadingInfoLoadicatorDataReply.newBuilder().build());

    ReflectionTestUtils.setField(loadicatorService, "loadingPlanService", loadingPlanService);

    loadicatorService.getStatus(createStowagePlanList(), request);
    verify(loadingPlanService)
        .getLoadicatorData(any(LoadingPlanModels.LoadingInfoLoadicatorDataRequest.class));
  }

  @Test
  void testGetStatusWithTypeId3() throws InterruptedException {
    List<Loadicator.StowagePlan> planList = new ArrayList<>();
    Loadicator.StowagePlan plan =
        Loadicator.StowagePlan.newBuilder().setBookingListId(1l).setStowageId(1l).build();
    planList.add(plan);
    LoadicatorRequest request =
        LoadicatorRequest.newBuilder()
            .setTypeId(3l)
            .setIsPattern(true)
            .addAllStowagePlanDetails(planList)
            .setIsUllageUpdate(true)
            .setConditionType(1)
            .build();
    when(this.stowagePlanRepository.findByIdIn(any())).thenReturn(createStowagePlanList());
    when(stowagePlanRepository.findCountOfStowagePlansToBeProcessed()).thenReturn(1l);
    when(loadicatorTrimRepository.findByStowagePlanId(anyLong()))
        .thenReturn(createLoadicatorTrimDetails().get(0));
    when(loadicatorStrengthRepository.findByStowagePlanId(anyLong()))
        .thenReturn(createLoadicatorStrengthDetails().get(0));
    when(loadicatorIntactStabilityRepository.findByStowagePlanId(anyLong()))
        .thenReturn(createIntactStabilityList().get(0));
    when(this.stowagePlanRepository.findPortForTrim(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.stowagePlanRepository.findPortForStrength(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.stowagePlanRepository.findPortForStability(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(dischargingPlanService.getLoadicatorData(any(DischargingInfoLoadicatorDataRequest.class)))
        .thenReturn(DischargingInfoLoadicatorDataReply.newBuilder().build());

    ReflectionTestUtils.setField(
        loadicatorService, "dischargingPlanService", dischargingPlanService);

    loadicatorService.getStatus(createStowagePlanList(), request);
    verify(dischargingPlanService)
        .getLoadicatorData(any(DischargingInfoLoadicatorDataRequest.class));
  }

  @Test
  void testSendLoadicatorData() {
    when(this.loadicatorTrimRepository.findByStowagePlanIdIn(anyList()))
        .thenReturn(createLoadicatorTrimDetails());
    when(this.stowagePlanRepository.findPortForTrim(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorStrengthRepository.findByStowagePlanIdIn(anyList()))
        .thenReturn(createLoadicatorStrengthDetails());
    when(this.stowagePlanRepository.findPortForStrength(anyLong()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorIntactStabilityRepository.findByStowagePlanIdIn(anyList()))
        .thenReturn(createIntactStabilityList());
    when(this.stowagePlanRepository.findPortForStability(anyLong()))
        .thenReturn(createStowagePlanDetails());

    var result = loadicatorService.sendLoadicatorData(createStowagePlanList(), true);
    assertEquals(true, result.getIsPattern());
    assertEquals(1l, result.getLoadicatorPatternDetails(0).getLDIntactStability(0).getPortId());
  }

  @Test
  void testGetLoadicatorData() {
    LoadicatorDataRequest request = LoadicatorDataRequest.newBuilder().build();

    when(loadableStudyService.getLoadicatorData(any(LoadicatorDataRequest.class)))
        .thenReturn(
            LoadicatorDataReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    ReflectionTestUtils.setField(loadicatorService, "loadableStudyService", loadableStudyService);

    var result = loadicatorService.getLoadicatorData(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private List<StowagePlan> createStowagePlanList() {
    List<StowagePlan> stowagePlans = new ArrayList<>();
    StowagePlan stowagePlan = new StowagePlan();
    stowagePlan.setStatus(3l);
    stowagePlan.setId(1l);
    stowagePlan.setProcessId("1");
    stowagePlan.setStowageId(1l);
    stowagePlan.setBookingListId(1l);
    stowagePlans.add(stowagePlan);
    return stowagePlans;
  }
  /** @return List<LoadicatorTrim> */
  private List<LoadicatorTrim> createLoadicatorTrimDetails() {
    List<LoadicatorTrim> loadicatorTrims = new ArrayList<LoadicatorTrim>();
    LoadicatorTrim trim = new LoadicatorTrim();
    trim.setId(1l);
    trim.setStowagePlanId(1l);
    trim.setAftDraft(new BigDecimal(1));
    trim.setForeDraft(new BigDecimal(1));
    trim.setTrim(new BigDecimal(1));
    trim.setHeel(new BigDecimal(1));
    trim.setMeanDraft(new BigDecimal(1));
    trim.setMeanDraftJudgement("1");
    trim.setDisplacementJudgement("1");
    trim.setDisplacementValue(new BigDecimal(1));
    trim.setMaximumDraft(new BigDecimal(1));
    trim.setMaximumDraftJudgement("1");
    trim.setAirDraft(new BigDecimal(1));
    trim.setAirDraftJudgement("1");
    trim.setMinimumForeDraftInRoughWeatherValue(new BigDecimal(1));
    trim.setMinimumForeDraftInRoughWeatherValueJudgement("1");
    trim.setMaximumAllowableVisibility(new BigDecimal(1));
    trim.setMaximumAllowableJudgement("1");
    trim.setErrorStatus(true);
    trim.setErrorDetails("1");
    trim.setMessageText("1");
    trim.setDeflection(new BigDecimal(1));
    loadicatorTrims.add(trim);
    return loadicatorTrims;
  }

  /** @return List<LoadicatorStrength> */
  private List<LoadicatorStrength> createLoadicatorStrengthDetails() {
    List<LoadicatorStrength> loadicatorStrengths = new ArrayList<LoadicatorStrength>();
    LoadicatorStrength strength = new LoadicatorStrength();
    strength.setId(DUMMY_LONG);
    strength.setStowagePlanId(1l);
    strength.setShearingForcePresentValue(new BigDecimal(1));
    strength.setShearingForceJudgement("1");
    strength.setSfFrameNumber(new BigDecimal(1));
    strength.setSfSideShellValue(new BigDecimal(1));
    strength.setSfSideShellJudgement("1");
    strength.setSfSideShellFrameNumber(new BigDecimal(1));
    strength.setSfHopperValue(new BigDecimal(1));
    strength.setSfHopperJudgement("1");
    strength.setSfHopperFrameNumber(new BigDecimal(1));
    strength.setOuterLongiBhdFrameNumber(new BigDecimal(1));
    strength.setOuterLongiBhdValue(new BigDecimal(1));
    strength.setInnerLongiBhdFrameNumber(new BigDecimal(1));
    strength.setInnerLongiBhdValue(new BigDecimal(1));
    strength.setInnerLongiBhdJudgement("1");
    strength.setBendingMomentPersentValue(new BigDecimal(1));
    strength.setBendingMomentPersentJudgement("1");
    strength.setBendingMomentPersentFrameNumber(new BigDecimal(1));
    strength.setErrorStatus(true);
    strength.setErrorDetails("1");
    strength.setMessageText("1");
    loadicatorStrengths.add(strength);
    return loadicatorStrengths;
  }

  /** @return StowagePlanDetail */
  private StowagePlanDetail createStowagePlanDetails() {
    StowagePlanDetail stowagePlanDetail = new StowagePlanDetail();
    stowagePlanDetail.setPortId(DUMMY_LONG);
    stowagePlanDetail.setSynopticalId(DUMMY_LONG);
    stowagePlanDetail.setPortRotationId(DUMMY_LONG);
    return stowagePlanDetail;
  }

  /** @return List<IntactStability> */
  private List<IntactStability> createIntactStabilityList() {
    List<IntactStability> intactStabilities = new ArrayList<IntactStability>();
    IntactStability stability = new IntactStability();
    stability.setId(DUMMY_LONG);
    stability.setStowagePlanId(1l);
    stability.setBigintialGomvalue(new BigDecimal(1));
    stability.setBigintialGomjudgement("1");
    stability.setMaximumRightingLeverValue(new BigDecimal(1));
    stability.setMaximumRightingLeverJudgement("1");
    stability.setAngleatMaxrLeverValue(new BigDecimal(1));
    stability.setAngleatMaxrLeverJudgement("1");
    stability.setAreaOfStability_0_30_Value(new BigDecimal(1));
    stability.setAreaOfstability_0_30_Judgement("1");
    stability.setAreaOfStability_0_40_Value(new BigDecimal(1));
    stability.setAreaOfStability_0_40_Judgement("1");
    stability.setAreaOfStability_30_40_Value(new BigDecimal(1));
    stability.setAreaOfStability_30_40_Judgement("1");
    stability.setHeelBySteadyWindValue(new BigDecimal(1));
    stability.setHeelBySteadyWindJudgement("1");
    stability.setStabilityAreaBaValue(new BigDecimal(1));
    stability.setStabilityAreaBaJudgement("1");
    stability.setGmAllowableCurveCheckValue(new BigDecimal(1));
    stability.setGm_allowableCurveCheckJudgement("1");
    stability.setErrorStatus(true);
    stability.setErrorDetails("1");
    stability.setMessageText("1");
    intactStabilities.add(stability);
    return intactStabilities;
  }

  /** @return LoadicatorRequest */
  private LoadicatorRequest createLoadicatorRequest() {
    LoadicatorRequest.Builder loadicatorRequestBuilder = LoadicatorRequest.newBuilder();
    List<Loadicator.StowagePlan> planList = new ArrayList<>();
    Loadicator.StowagePlan plan =
        Loadicator.StowagePlan.newBuilder().setBookingListId(1l).setStowageId(1l).build();
    planList.add(plan);
    loadicatorRequestBuilder.setTypeId(1l).setIsPattern(true).addAllStowagePlanDetails(planList);
    loadicatorRequestBuilder.addStowagePlanDetails(buildStowagePlanDetails());
    return loadicatorRequestBuilder.build();
  }

  /** @return com.cpdss.common.generated.Loadicator.StowagePlan */
  private com.cpdss.common.generated.Loadicator.StowagePlan buildStowagePlanDetails() {
    com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder =
        com.cpdss.common.generated.Loadicator.StowagePlan.newBuilder();
    stowagePlanBuilder.addStowageDetails(buildStowageDetails());
    stowagePlanBuilder.addBallastInfo(buildBallastInfo());
    stowagePlanBuilder.addOtherTankInfo(buildOtherTankInfo());
    stowagePlanBuilder.addCargoInfo(buildCargoInfo());
    return stowagePlanBuilder.build();
  }

  /** @return CargoInfo */
  private CargoInfo buildCargoInfo() {
    CargoInfo.Builder builder = CargoInfo.newBuilder();
    return builder.build();
  }

  /** @return OtherTankInfo */
  private OtherTankInfo buildOtherTankInfo() {
    OtherTankInfo.Builder builder = OtherTankInfo.newBuilder();
    return builder.build();
  }

  /** @return BallastInfo */
  private BallastInfo buildBallastInfo() {
    BallastInfo.Builder builder = BallastInfo.newBuilder();
    return builder.build();
  }

  /** @return StowageDetails */
  private StowageDetails buildStowageDetails() {
    StowageDetails.Builder builder = StowageDetails.newBuilder();
    return builder.build();
  }
}
