/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.SynopticalOperationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

/** @Author ravi.r */
@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(classes = {LoadingPlanService.class})
class LoadingPlanServiceTest {

  @Autowired LoadingPlanService loadingPlanService;

  @MockBean LoadingInformationService loadingInformationService;
  @MockBean CargoToppingOffSequenceService cargoToppingOffSequenceService;
  @MockBean LoadablePlanBallastDetailsService loadablePlanBallastDetailsService;
  @MockBean LoadablePlanCommingleDetailsService loadablePlanCommingleDetailsService;
  @MockBean LoadablePlanQuantityService loadablePlanQuantityService;
  @MockBean LoadablePlanStowageDetailsService loadablePlanStowageDetailsService;
  @MockBean PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;

  @MockBean PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @MockBean PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @MockBean PortLoadingPlanStabilityParametersRepository plpStabilityParametersRepository;
  @MockBean PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;
  @MockBean LoadingInformationBuilderService informationBuilderService;
  @MockBean LoadingBerthDetailsRepository berthDetailsRepository;
  @MockBean CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @MockBean BillOfLaddingRepository billOfLaddingRepo;

  @MockBean PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @MockBean PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @MockBean
  PortLoadingPlanCommingleTempDetailsRepository portLoadingPlanCommingleTempDetailsRepository;

  @MockBean BillOfLandingRepository billOfLandingRepository;

  @MockBean PortLoadingPlanRobDetailsRepositoryTemp loadingPlanRobDetailsTempRepository;

  @MockBean StageOffsetRepository stageOffsetRepository;
  @MockBean StageDurationRepository stageDurationRepository;
  @MockBean ReasonForDelayRepository reasonForDelayRepository;
  @MockBean LoadingDelayRepository loadingDelayRepository;
  @MockBean LoadingSequenceRepository loadingSequenceRepository;
  @MockBean LoadingInformationRepository loadingInformationRepository;

  @MockBean private UllageUpdateLoadicatorService ullageUpdateLoadicatorService;
  @MockBean private LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean private LoadingPlanRuleService loadingPlanRuleService;

  @MockBean
  private SynopticalOperationServiceGrpc.SynopticalOperationServiceBlockingStub
      synopticalOperationServiceBlockingStub;

  @MockBean private LoadingPlanCommunicationService loadingPlancommunicationService;
  @MockBean private LoadingPlanStagingService loadingPlanStagingService;

  @MockBean
  private LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;

  @MockBean LoadingInformationStatusRepository loadingInfoStatusRepository;

  @MockBean LoadingMachineInUseService loadingMachineInUseService;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testloadingPlanSynchronization() {
    List<LoadableStudy.LoadablePlanStowageDetails> list4 = new ArrayList<>();
    LoadableStudy.LoadablePlanStowageDetails stowageDetails =
        LoadableStudy.LoadablePlanStowageDetails.newBuilder().build();
    list4.add(stowageDetails);
    List<LoadableStudy.LoadableQuantityCargoDetails> list3 = new ArrayList<>();
    LoadableStudy.LoadableQuantityCargoDetails cargoDetails =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder().build();
    list3.add(cargoDetails);
    List<LoadableStudy.LoadableQuantityCommingleCargoDetails> list2 = new ArrayList<>();
    LoadableStudy.LoadableQuantityCommingleCargoDetails details =
        LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder().build();
    list2.add(details);
    List<LoadableStudy.LoadablePlanBallastDetails> list1 = new ArrayList<>();
    LoadableStudy.LoadablePlanBallastDetails loadablePlanBallastDetails =
        LoadableStudy.LoadablePlanBallastDetails.newBuilder().build();
    list1.add(loadablePlanBallastDetails);
    List<LoadingPlanModels.CargoToppingOffSequence> list = new ArrayList<>();
    LoadingPlanModels.CargoToppingOffSequence cargoToppingOffSequence =
        LoadingPlanModels.CargoToppingOffSequence.newBuilder().build();
    list.add(cargoToppingOffSequence);
    LoadingPlanModels.LoadingPlanSyncDetails request =
        LoadingPlanModels.LoadingPlanSyncDetails.newBuilder()
            .setLoadablePlanDetailsReply(
                LoadableStudy.LoadablePlanDetailsReply.newBuilder()
                    .addAllLoadablePlanStowageDetails(list4)
                    .addAllLoadableQuantityCargoDetails(list3)
                    .addAllLoadableQuantityCommingleCargoDetails(list2)
                    .addAllLoadablePlanBallastDetails(list1)
                    .build())
            .addAllCargoToppingOffSequences(list)
            .setLoadingInformationDetail(
                LoadingPlanModels.LoadingInformationDetail.newBuilder()
                    .setPortId(1L)
                    .setLoadablePatternId(1L)
                    .build())
            .build();
    LoadingPlanModels.LoadingPlanSyncReply.Builder builder =
        LoadingPlanModels.LoadingPlanSyncReply.newBuilder();
    Mockito.when(
            loadingInformationService.saveLoadingInformationDetail(Mockito.any(), Mockito.any()))
        .thenReturn(getLI());
    this.loadingPlanService.loadingPlanSynchronization(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  private LoadingInformation getLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    return loadingInformation;
  }

  @Test
  void testloadingPlanSynchronizationFailed() {
    LoadingPlanModels.LoadingPlanSyncDetails request =
        LoadingPlanModels.LoadingPlanSyncDetails.newBuilder()
            .setLoadingInformationDetail(
                LoadingPlanModels.LoadingInformationDetail.newBuilder()
                    .setPortId(1L)
                    .setLoadablePatternId(1L)
                    .build())
            .build();
    LoadingPlanModels.LoadingPlanSyncReply.Builder builder =
        LoadingPlanModels.LoadingPlanSyncReply.newBuilder();
    Mockito.when(
            loadingInformationService.saveLoadingInformationDetail(Mockito.any(), Mockito.any()))
        .thenThrow(new RuntimeException("internal error"));
    this.loadingPlanService.loadingPlanSynchronization(request, builder);
    assertEquals(
        "Error occured while saving loading information in database",
        builder.getResponseStatus().getMessage());
  }

  //  @Test
  //  void testGetLoadingPlan() {
  //    LoadingPlanModels.LoadingInformationRequest request =
  // LoadingPlanModels.LoadingInformationRequest.newBuilder().setLoadingPlanId(1L).build();
  //    LoadingPlanModels.LoadingPlanReply.Builder masterBuilder =
  // LoadingPlanModels.LoadingPlanReply.newBuilder();
  //
  // Mockito.when(loadingInformationService.getLoadingInformation(Mockito.anyLong())).thenReturn(getOLI());
  //
  // Mockito.when(this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(Mockito.any())).thenReturn(getLCTS());
  //    Mockito.when(this.stageOffsetRepository.findAll()).thenReturn(getLSO());
  //   Mockito.when(this.stageDurationRepository.findAll()).thenReturn(getLSD());
  //
  // Mockito.when(portLoadingPlanBallastDetailsRepository.findByLoadingInformationAndIsActive(Mockito.any(),Mockito.anyBoolean())).thenReturn(getLPLPBD());
  //
  // Mockito.when(portLoadingPlanStowageDetailsRepository.findByLoadingInformationAndIsActive(Mockito.any(),Mockito.anyBoolean())).thenReturn(getLPLPSD());
  //
  // Mockito.when(portLoadingPlanRobDetailsRepository.findByLoadingInformationAndIsActive(Mockito.anyLong(),Mockito.anyBoolean())).thenReturn(getLPLPRD());
  //
  // Mockito.when(plpStabilityParametersRepository.findByLoadingInformationAndIsActive(Mockito.any(),Mockito.anyBoolean())).thenReturn(getLPLPSP());
  //
  // Mockito.when(plpCommingleDetailsRepository.findByLoadingInformationAndIsActive(Mockito.any(),Mockito.anyBoolean())).thenReturn(getLPLPCD());
  //
  // Mockito.when(loadingSequenceRepository.findToBeLoadedCargoNominationIdByLoadingInformationAndIsActive(Mockito.any(),Mockito.anyBoolean())).thenReturn(getL());
  // //
  // Mockito.when(this.informationBuilderService.buildLoadingStageMessage(Mockito.any(),Mockito.anyList(),Mockito.anyList())).thenReturn(getLSB());
  //  //
  // ReflectionTestUtils.setField(loadingPlanService,"informationBuilderService",this.informationBuilderService);
  //    try {
  //      this.loadingPlanService.getLoadingPlan(request,masterBuilder);
  //      assertEquals(SUCCESS,masterBuilder.getResponseStatus().getStatus());
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //
  //  }

  private List<Long> getL() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    return list;
  }

  private LoadingPlanModels.LoadingStages getLSB() {
    LoadingPlanModels.LoadingStages stages =
        LoadingPlanModels.LoadingStages.newBuilder().setId(1L).build();
    return stages;
  }

  private List<PortLoadingPlanCommingleDetails> getLPLPCD() {
    List<PortLoadingPlanCommingleDetails> list = new ArrayList<>();
    PortLoadingPlanCommingleDetails commingleDetails = new PortLoadingPlanCommingleDetails();
    commingleDetails.setGrade("1");
    commingleDetails.setApi("1");
    commingleDetails.setCargo1XId(1L);
    commingleDetails.setCargo2XId(1L);
    commingleDetails.setCargoNomination1XId(1L);
    commingleDetails.setCargoNomination2XId(1L);
    commingleDetails.setId(1L);
    commingleDetails.setQuantity("1");
    commingleDetails.setQuantityM3("1");
    commingleDetails.setTankId(1L);
    commingleDetails.setTemperature("1");
    commingleDetails.setQuantity1MT("1");
    commingleDetails.setQuantity2MT("1");
    commingleDetails.setQuantity1M3("1");
    commingleDetails.setQuantity2M3("1");
    commingleDetails.setUllage1("1");
    commingleDetails.setUllage2("1");
    commingleDetails.setUllage("1");
    commingleDetails.setConditionType(1);
    commingleDetails.setValueType(1);
    commingleDetails.setColorCode("1");
    commingleDetails.setGrade("1");
    commingleDetails.setTankName("1");
    commingleDetails.setCargo1Abbreviation("1");
    commingleDetails.setCargo2Abbreviation("1");
    commingleDetails.setCargo1Percentage("1");
    commingleDetails.setCargo2Percentage("1");
    commingleDetails.setCargo1BblsDbs("1");
    commingleDetails.setCargo2BblsDbs("1");
    commingleDetails.setCargo1Bbls60f("1");
    commingleDetails.setCargo2Bbls60f("1");
    commingleDetails.setCargo1Lt("1");
    commingleDetails.setCargo2Lt("1");
    commingleDetails.setCargo1Mt("1");
    commingleDetails.setCargo2Mt("1");
    commingleDetails.setCargo1Kl("1");
    commingleDetails.setCargo2Kl("1");
    commingleDetails.setIsActive(true);
    commingleDetails.setPriority(1);
    commingleDetails.setOrderQuantity("1");
    commingleDetails.setLoadingOrder(1);
    commingleDetails.setFillingRatio("1");
    commingleDetails.setCorrectedUllage(1L);
    commingleDetails.setCorrectionFactor("1");
    commingleDetails.setRdgUllage("1");
    commingleDetails.setSlopQuantity("1");
    commingleDetails.setTimeRequiredForLoading("1");
    commingleDetails.setLoadablePatternId(1L);
    list.add(commingleDetails);
    return list;
  }

  private List<PortLoadingPlanStabilityParameters> getLPLPSP() {
    List<PortLoadingPlanStabilityParameters> list = new ArrayList<>();
    PortLoadingPlanStabilityParameters parameters = new PortLoadingPlanStabilityParameters();
    parameters.setForeDraft(new BigDecimal(1));
    parameters.setMeanDraft(new BigDecimal(1));
    parameters.setAftDraft(new BigDecimal(1));
    parameters.setTrim(new BigDecimal(1));
    parameters.setBendingMoment(new BigDecimal(1));
    parameters.setShearingForce(new BigDecimal(1));
    parameters.setFreeboard(new BigDecimal(1));
    parameters.setManifoldHeight(new BigDecimal(1));
    parameters.setConditionType(1);
    parameters.setValueType(1);
    list.add(parameters);
    return list;
  }

  private List<PortLoadingPlanRobDetails> getLPLPRD() {
    List<PortLoadingPlanRobDetails> list = new ArrayList<>();
    PortLoadingPlanRobDetails robDetails = new PortLoadingPlanRobDetails();
    robDetails.setId(1L);
    robDetails.setQuantity(new BigDecimal(1));
    robDetails.setTankXId(1L);
    robDetails.setQuantityM3(new BigDecimal(1));
    robDetails.setConditionType(1);
    robDetails.setValueType(1);
    robDetails.setColorCode("1");
    robDetails.setDensity(new BigDecimal(1));
    list.add(robDetails);
    return list;
  }

  private List<PortLoadingPlanStowageDetails> getLPLPSD() {
    List<PortLoadingPlanStowageDetails> list = new ArrayList<>();
    PortLoadingPlanStowageDetails portLoadingPlanStowageDetails =
        new PortLoadingPlanStowageDetails();
    portLoadingPlanStowageDetails.setCargoNominationXId(1L);
    portLoadingPlanStowageDetails.setId(1L);
    portLoadingPlanStowageDetails.setApi(new BigDecimal(1));
    portLoadingPlanStowageDetails.setTemperature(new BigDecimal(1));
    portLoadingPlanStowageDetails.setQuantity(new BigDecimal(1));
    portLoadingPlanStowageDetails.setTankXId(1L);
    portLoadingPlanStowageDetails.setUllage(new BigDecimal(1));
    portLoadingPlanStowageDetails.setQuantityM3(new BigDecimal(1));
    portLoadingPlanStowageDetails.setConditionType(1);
    portLoadingPlanStowageDetails.setValueType(1);
    portLoadingPlanStowageDetails.setColorCode("1");
    portLoadingPlanStowageDetails.setAbbreviation("1");
    portLoadingPlanStowageDetails.setCargoXId(1L);
    list.add(portLoadingPlanStowageDetails);
    return list;
  }

  private List<PortLoadingPlanBallastDetails> getLPLPBD() {
    List<PortLoadingPlanBallastDetails> list = new ArrayList<>();
    PortLoadingPlanBallastDetails details = new PortLoadingPlanBallastDetails();
    details.setId(1L);
    details.setQuantity(new BigDecimal(1));
    details.setTankXId(1L);
    details.setQuantityM3(new BigDecimal(1));
    details.setSounding(new BigDecimal(1));
    details.setConditionType(1);
    details.setValueType(1);
    details.setColorCode("1");
    details.setSg(new BigDecimal(1));
    details.setCorrectedUllage(new BigDecimal(1));
    details.setCorrectionFactor(new BigDecimal(1));
    details.setFillingPercentage(new BigDecimal(1));
    details.setTemperature(new BigDecimal(1));
    list.add(details);
    return list;
  }

  private List<StageDuration> getLSD() {
    List<StageDuration> list = new ArrayList<>();
    StageDuration stageDuration = new StageDuration();
    stageDuration.setId(1L);
    stageDuration.setDuration(1);
    list.add(stageDuration);
    return list;
  }

  private List<StageOffset> getLSO() {
    List<StageOffset> list = new ArrayList<>();
    StageOffset stageOffset = new StageOffset();
    stageOffset.setStageOffsetVal(1);
    stageOffset.setId(1L);
    list.add(stageOffset);
    return list;
  }

  private Optional<LoadingInformation> getOLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    loadingInformation.setStageOffset(getLSO().get(0));
    loadingInformation.setStageDuration(getLSD().get(0));
    loadingInformation.setTrackGradeSwitch(true);
    loadingInformation.setTrackStartEndStage(true);
    loadingInformation.setLoadingInformationStatus(getLS());
    loadingInformation.setArrivalStatus(getLS());
    loadingInformation.setDepartureStatus(getLS());
    loadingInformation.setLoadingPlanDetailsFromAlgo("{\"loadingInfoId\":1}");
    loadingInformation.setLoadablePatternXId(1L);
    return Optional.of(loadingInformation);
  }

  private LoadingInformationStatus getLS() {
    LoadingInformationStatus status = new LoadingInformationStatus();
    status.setId(1L);
    return status;
  }

  private List<CargoToppingOffSequence> getLCTS() {
    List<CargoToppingOffSequence> list = new ArrayList<>();
    CargoToppingOffSequence cargoToppingOffSequence = new CargoToppingOffSequence();
    cargoToppingOffSequence.setTankXId(1L);
    cargoToppingOffSequence.setId(1L);
    cargoToppingOffSequence.setLoadingInformation(getLI());
    cargoToppingOffSequence.setOrderNumber(1);
    cargoToppingOffSequence.setCargoXId(1L);
    cargoToppingOffSequence.setUllage(new BigDecimal(1));
    cargoToppingOffSequence.setQuantity(new BigDecimal(1));
    cargoToppingOffSequence.setFillingRatio(new BigDecimal(1));
    cargoToppingOffSequence.setRemarks("1");
    cargoToppingOffSequence.setApi(new BigDecimal(1));
    cargoToppingOffSequence.setTemperature(new BigDecimal(1));
    cargoToppingOffSequence.setDisplayOrder(1);
    cargoToppingOffSequence.setCargoNominationXId(1L);
    cargoToppingOffSequence.setAbbreviation("1");
    list.add(cargoToppingOffSequence);
    return list;
  }

  @Test
  void testGetBillOfLaddingDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setPatternId(1L)
            .setPortId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            this.billOfLaddingRepo.findByLoadablePatternXIdAndPortIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLBL());
    this.loadingPlanService.getBillOfLaddingDetails(request, builder);
    assertEquals("1", builder.getBillOfLadding(0).getApi());
  }

  private List<BillOfLadding> getLBL() {
    List<BillOfLadding> list = new ArrayList<>();
    BillOfLadding billOfLadding = new BillOfLadding();
    billOfLadding.setId(1L);
    billOfLadding.setBlRefNo("1");
    billOfLadding.setApi(new BigDecimal(1));
    billOfLadding.setTemperature(new BigDecimal(1));
    billOfLadding.setCargoNominationId(1L);
    billOfLadding.setPortId(1L);
    billOfLadding.setQuantityMt(new BigDecimal(1));
    billOfLadding.setQuantityBbls(new BigDecimal(1));
    billOfLadding.setQuantityKl(new BigDecimal(1));
    billOfLadding.setQuantityLT(new BigDecimal(1));
    list.add(billOfLadding);
    return list;
  }

  @Test
  void testGetPortWiseStowageDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setPatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            portLoadingPlanStowageDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPL());
    this.loadingPlanService.getPortWiseStowageDetails(request, builder);
    assertEquals("1", builder.getPortLoadablePlanStowageDetails(0).getApi());
  }

  private List<PortLoadingPlanStowageDetails> getLPL() {
    List<PortLoadingPlanStowageDetails> list = new ArrayList<>();
    PortLoadingPlanStowageDetails details = new PortLoadingPlanStowageDetails();
    details.setAbbreviation("1");
    details.setApi(new BigDecimal(1));
    details.setCargoNominationXId(1L);
    details.setColorCode("1");
    details.setCorrectedUllage(new BigDecimal(1));
    details.setCorrectionFactor(new BigDecimal(1));
    details.setFillingPercentage(new BigDecimal(1));
    details.setId(1L);
    details.setRdgUllage(new BigDecimal(1));
    details.setTankXId(1L);
    details.setTemperature(new BigDecimal(1));
    details.setWeight(new BigDecimal(1));
    details.setQuantity(new BigDecimal(1));
    details.setValueType(1);
    details.setConditionType(1);
    details.setUllage(new BigDecimal(1));
    list.add(details);
    return list;
  }

  @Test
  void testGetPortWiseBallastDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setPatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            portLoadingPlanBallastDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPLPBD());
    this.loadingPlanService.getPortWiseBallastDetails(request, builder);
    assertEquals("1", builder.getPortLoadingPlanBallastDetails(0).getColorCode());
  }

  @Test
  void testGetPortWiseRobDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setPatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            portLoadingPlanRobDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPLPRD());
    this.loadingPlanService.getPortWiseRobDetails(request, builder);
    assertEquals("1", builder.getPortLoadingPlanRobDetails(0).getActualPlanned());
  }

  @Test
  void testGetLoadableStudyShoreTwo1()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    List<LoadingPlanModels.CommingleUpdate> list5 = new ArrayList<>();
    LoadingPlanModels.CommingleUpdate commingleUpdate =
        LoadingPlanModels.CommingleUpdate.newBuilder()
            .setIsUpdate(true)
            .setApi("1")
            .setFillingPercentage("1")
            .setQuantityM3("1")
            .setQuantityMT("1")
            .setTemperature("1")
            .setQuantity1M3("1")
            .setQuantity2M3("1")
            .setQuantity1MT("1")
            .setQuantity2MT("1")
            .setUllage("1")
            .setUllage1("1")
            .setUllage2("1")
            .setColorCode("1")
            .setTankId(1L)
            .setArrivalDeparture(1)
            .setLoadingInformationId(1L)
            .build();
    list5.add(commingleUpdate);
    List<LoadingPlanModels.RobUpdate> list4 = new ArrayList<>();
    LoadingPlanModels.RobUpdate robUpdate =
        LoadingPlanModels.RobUpdate.newBuilder()
            .setIsUpdate(true)
            .setQuantity("1")
            .setTankId(1L)
            .setLoadingInformationId(1L)
            .setArrivalDepartutre(1)
            .setActualPlanned(1)
            .build();
    list4.add(robUpdate);
    List<LoadingPlanModels.BallastUpdate> list3 = new ArrayList<>();
    LoadingPlanModels.BallastUpdate update =
        LoadingPlanModels.BallastUpdate.newBuilder()
            .setIsUpdate(true)
            .setSg("1")
            .setCorrectedUllage("1")
            .setColorCode("1")
            .setQuantity("1")
            .setSounding("1")
            .setFillingPercentage("1")
            .setCorrectionFactor("1")
            .setTankId(1L)
            .setLoadingInformationId(1L)
            .setArrivalDepartutre(1)
            .build();
    list3.add(update);
    List<LoadingPlanModels.UpdateUllage> list2 = new ArrayList<>();
    LoadingPlanModels.UpdateUllage updateUllage =
        LoadingPlanModels.UpdateUllage.newBuilder()
            .setLoadingInformationId(1L)
            .setArrivalDepartutre(1)
            .setIsUpdate(true)
            .setQuantity("1")
            .setUllage("1")
            .setApi("1")
            .setTemperature("1")
            .setCorrectedUllage("1")
            .setFillingPercentage("1")
            .setCorrectionFactor("1")
            .setTankId(1L)
            .build();
    list2.add(updateUllage);
    List<LoadingPlanModels.BillOfLandingRemove> list1 = new ArrayList<>();
    LoadingPlanModels.BillOfLandingRemove remove =
        LoadingPlanModels.BillOfLandingRemove.newBuilder().setId(1L).build();
    list1.add(remove);
    List<LoadingPlanModels.BillOfLanding> list = new ArrayList<>();
    LoadingPlanModels.BillOfLanding landing =
        LoadingPlanModels.BillOfLanding.newBuilder()
            .setIsUpdate(true)
            .setBlRefNumber("1")
            .setBblAt60F("1")
            .setQuantityMt("1")
            .setQuantityLt("1")
            .setKlAt15C("1")
            .setApi("1")
            .setTemperature("1")
            .setId(1L)
            .build();
    list.add(landing);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder()
            .setIsValidate("true")
            .addAllCommingleUpdate(list5)
            .addAllRobUpdate(list4)
            .addAllBallastUpdate(list3)
            .addAllUpdateUllage(list2)
            .addAllBillOfLandingRemove(list1)
            .addAllBillOfLanding(list)
            .build();
    LoadingPlanModels.UllageBillReply.Builder builder =
        LoadingPlanModels.UllageBillReply.newBuilder();
    Mockito.when(
            portLoadingPlanBallastTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPBT());
    Mockito.when(
            portLoadingPlanStowageTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLSTD());
    Mockito.when(
            portLoadingPlanRobDetailsRepository
                .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPRD());
    Mockito.when(
            portLoadingPlanCommingleTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPPCTD());
    Mockito.when(ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(Mockito.any()))
        .thenReturn("1");
    this.loadingPlanService.getLoadableStudyShoreTwo(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  private List<PortLoadingPlanBallastTempDetails> getLPBT() {
    List<PortLoadingPlanBallastTempDetails> list = new ArrayList<>();
    PortLoadingPlanBallastTempDetails port = new PortLoadingPlanBallastTempDetails();
    port.setQuantity(new BigDecimal(1));
    port.setColorCode("1");
    port.setCorrectedUllage(new BigDecimal(1));
    port.setCorrectionFactor(new BigDecimal(1));
    port.setFillingPercentage(new BigDecimal(1));
    port.setId(1L);
    port.setTemperature(new BigDecimal(1));
    port.setQuantity(new BigDecimal(1));
    port.setValueType(1);
    port.setConditionType(1);
    port.setSounding(new BigDecimal(1));
    port.setSg(new BigDecimal(1));
    port.setTankXId(1L);
    list.add(port);
    return list;
  }

  private List<PortLoadingPlanStowageTempDetails> getLSTD() {
    List<PortLoadingPlanStowageTempDetails> list = new ArrayList<>();
    PortLoadingPlanStowageTempDetails port = new PortLoadingPlanStowageTempDetails();
    port.setQuantity(new BigDecimal(1));
    port.setTankXId(1L);
    port.setId(1L);
    port.setApi(new BigDecimal(1));
    port.setTemperature(new BigDecimal(1));
    port.setUllage(new BigDecimal(1));
    port.setAbbreviation("1");
    port.setCargoNominationXId(1L);
    port.setColorCode("1");
    port.setCorrectedUllage(new BigDecimal(1));
    port.setCorrectionFactor(new BigDecimal(1));
    port.setFillingPercentage(new BigDecimal(1));
    port.setRdgUllage(new BigDecimal(1));
    port.setTankXId(1L);
    port.setWeight(new BigDecimal(1));
    port.setQuantity(new BigDecimal(1));
    port.setValueType(1);
    port.setConditionType(1);
    list.add(port);
    return list;
  }

  private List<PortLoadingPlanRobDetails> getLPRD() {
    List<PortLoadingPlanRobDetails> list = new ArrayList<>();
    PortLoadingPlanRobDetails port = new PortLoadingPlanRobDetails();
    list.add(port);
    return list;
  }

  private List<PortLoadingPlanCommingleTempDetails> getLPPCTD() {
    List<PortLoadingPlanCommingleTempDetails> list = new ArrayList<>();
    PortLoadingPlanCommingleTempDetails port = new PortLoadingPlanCommingleTempDetails();
    port.setQuantity("1");
    port.setTankId(1L);
    list.add(port);
    return list;
  }

  @Test
  void testGetLoadableStudyShoreTwo2() throws GenericServiceException {
    List<LoadingPlanModels.CommingleUpdate> list5 = new ArrayList<>();
    LoadingPlanModels.CommingleUpdate commingleUpdate =
        LoadingPlanModels.CommingleUpdate.newBuilder()
            .setIsUpdate(false)
            .setLoadingInformationId(1L)
            .setTankId(1L)
            .setTemperature("1")
            .setQuantityMT("1")
            .setFillingPercentage("1")
            .setApi("1")
            .setCargoNomination1Id(1L)
            .setCargoNomination2Id(1L)
            .setCargo1Id(1L)
            .setCargo2Id(1L)
            .setActualPlanned(1)
            .setArrivalDeparture(1)
            .setUllage("1")
            .setQuantity1MT("1")
            .setQuantity2MT("1")
            .setQuantity1M3("1")
            .setQuantity2M3("1")
            .setUllage1("1")
            .setUllage2("1")
            .setColorCode("1")
            .setAbbreviation("1")
            .build();
    list5.add(commingleUpdate);
    List<LoadingPlanModels.RobUpdate> list4 = new ArrayList<>();
    LoadingPlanModels.RobUpdate robUpdate =
        LoadingPlanModels.RobUpdate.newBuilder()
            .setIsUpdate(false)
            .setLoadingInformationId(1L)
            .setTankId(1L)
            .setQuantity("1")
            .setPortXid(1L)
            .setPortRotationXid(1L)
            .setArrivalDepartutre(1)
            .setColourCode("1")
            .setDensity("1")
            .build();
    list4.add(robUpdate);
    List<LoadingPlanModels.BallastUpdate> list3 = new ArrayList<>();
    LoadingPlanModels.BallastUpdate update =
        LoadingPlanModels.BallastUpdate.newBuilder()
            .setIsUpdate(false)
            .setLoadingInformationId(1L)
            .setPortRotationXid(1L)
            .setPortXid(1L)
            .setTankId(1L)
            .setTemperature("1")
            .setCorrectedUllage("1")
            .setQuantity("1")
            .setObservedM3("1")
            .setFillingPercentage("1")
            .setSounding("1")
            .setActualPlanned(1)
            .setArrivalDepartutre(1)
            .setColorCode("1")
            .setSg("1")
            .build();
    list3.add(update);
    List<LoadingPlanModels.UpdateUllage> list2 = new ArrayList<>();
    LoadingPlanModels.UpdateUllage updateUllage =
        LoadingPlanModels.UpdateUllage.newBuilder()
            .setLoadingInformationId(1L)
            .setArrivalDepartutre(1)
            .setIsUpdate(false)
            .setTankId(1L)
            .setTemperature("1")
            .setCorrectedUllage("1")
            .setQuantity("1")
            .setFillingPercentage("1")
            .setApi("1")
            .setCargoNominationXid(1L)
            .setPortXid(1L)
            .setPortRotationXid(1L)
            .setActualPlanned(1)
            .setCorrectionFactor("1")
            .setUllage("1")
            .setColorCode("1")
            .setAbbreviation("1")
            .setCargoId(1L)
            .build();
    list2.add(updateUllage);
    List<LoadingPlanModels.BillOfLandingRemove> list1 = new ArrayList<>();
    LoadingPlanModels.BillOfLandingRemove remove =
        LoadingPlanModels.BillOfLandingRemove.newBuilder().setId(1L).build();
    list1.add(remove);
    List<LoadingPlanModels.BillOfLanding> list = new ArrayList<>();
    LoadingPlanModels.BillOfLanding landing =
        LoadingPlanModels.BillOfLanding.newBuilder()
            .setIsUpdate(false)
            .setId(0L)
            .setLoadingId(1L)
            .setPortId(1L)
            .setCargoId(1L)
            .setApi("1")
            .setTemperature("1")
            .setVersion(1L)
            .setQuantityMt("1")
            .setQuantityLt("1")
            .setKlAt15C("1")
            .setBlRefNumber("1")
            .setBblAt60F("1")
            .build();
    list.add(landing);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder()
            .setIsValidate("false")
            .addAllCommingleUpdate(list5)
            .addAllRobUpdate(list4)
            .addAllBallastUpdate(list3)
            .addAllUpdateUllage(list2)
            .addAllBillOfLandingRemove(list1)
            .addAllBillOfLanding(list)
            .build();
    LoadingPlanModels.UllageBillReply.Builder builder =
        LoadingPlanModels.UllageBillReply.newBuilder();
    Mockito.when(
            portLoadingPlanBallastTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPBT1());
    Mockito.when(
            portLoadingPlanStowageTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLSTD1());
    Mockito.when(
            portLoadingPlanRobDetailsRepository
                .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPRD1());
    Mockito.when(
            portLoadingPlanCommingleTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPPCTD1());
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(loadingPlanAlgoService.getLoadingInformationStatus(Mockito.anyLong()))
        .thenReturn(getOLIS());
    this.loadingPlanService.getLoadableStudyShoreTwo(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  private Optional<LoadingInformationStatus> getOLIS() {
    LoadingInformationStatus status = new LoadingInformationStatus();
    status.setId(1L);
    return Optional.of(status);
  }

  private List<PortLoadingPlanBallastTempDetails> getLPBT1() {
    List<PortLoadingPlanBallastTempDetails> list = new ArrayList<>();
    return list;
  }

  private List<PortLoadingPlanStowageTempDetails> getLSTD1() {
    List<PortLoadingPlanStowageTempDetails> list = new ArrayList<>();
    return list;
  }

  private List<PortLoadingPlanRobDetails> getLPRD1() {
    List<PortLoadingPlanRobDetails> list = new ArrayList<>();
    return list;
  }

  private List<PortLoadingPlanCommingleTempDetails> getLPPCTD1() {
    List<PortLoadingPlanCommingleTempDetails> list = new ArrayList<>();
    return list;
  }

  @Test
  void testGetLoadableStudyShoreTwoFailed() throws GenericServiceException {
    List<LoadingPlanModels.UpdateUllage> list2 = new ArrayList<>();
    LoadingPlanModels.UpdateUllage updateUllage =
        LoadingPlanModels.UpdateUllage.newBuilder()
            .setLoadingInformationId(1L)
            .setArrivalDepartutre(1)
            .setIsUpdate(false)
            .setTankId(1L)
            .setTemperature("1")
            .setCorrectedUllage("1")
            .setQuantity("1")
            .setFillingPercentage("1")
            .setApi("1")
            .setCargoNominationXid(1L)
            .setPortXid(1L)
            .setPortRotationXid(1L)
            .setActualPlanned(1)
            .setCorrectionFactor("1")
            .setUllage("1")
            .setColorCode("1")
            .setAbbreviation("1")
            .setCargoId(1L)
            .build();
    list2.add(updateUllage);
    List<LoadingPlanModels.BillOfLandingRemove> list1 = new ArrayList<>();
    LoadingPlanModels.BillOfLandingRemove remove =
        LoadingPlanModels.BillOfLandingRemove.newBuilder().setId(1L).build();
    list1.add(remove);
    List<LoadingPlanModels.BillOfLanding> list = new ArrayList<>();
    LoadingPlanModels.BillOfLanding landing =
        LoadingPlanModels.BillOfLanding.newBuilder()
            .setIsUpdate(false)
            .setId(0L)
            .setLoadingId(1L)
            .setPortId(1L)
            .setCargoId(1L)
            .setApi("1")
            .setTemperature("1")
            .setVersion(1L)
            .setQuantityMt("1")
            .setQuantityLt("1")
            .setKlAt15C("1")
            .setBlRefNumber("1")
            .setBblAt60F("1")
            .build();
    list.add(landing);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder()
            .setIsValidate("false")
            .addAllUpdateUllage(list2)
            .addAllBillOfLandingRemove(list1)
            .addAllBillOfLanding(list)
            .build();
    LoadingPlanModels.UllageBillReply.Builder builder =
        LoadingPlanModels.UllageBillReply.newBuilder();
    Mockito.when(
            portLoadingPlanBallastTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenThrow(new RuntimeException("error"));
    this.loadingPlanService.getLoadableStudyShoreTwo(request, builder);
    assertEquals(FAILED, builder.getResponseStatus().getStatus());
  }

  @Test
  void testUpdateLoadingPlanStatus() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    LoadingInformationStatus loadingInfoStatus = new LoadingInformationStatus();
    int conditionType = 2;
    this.loadingPlanService.updateLoadingPlanStatus(
        loadingInformation, loadingInfoStatus, conditionType);
  }

  @Test
  void testSaveUpdatedLoadingPlanDetails()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    loadingInformation.setVesselXId(1L);
    loadingInformation.setPortXId(1L);
    loadingInformation.setPortRotationXId(1L);
    loadingInformation.setLoadablePatternXId(1L);
    Integer conditionType = 1;
    Mockito.when(
            portLoadingPlanStowageTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLSTD());
    Mockito.when(
            portLoadingPlanBallastTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPBT());
    Mockito.when(
            portLoadingPlanCommingleTempDetailsRepository
                .findByLoadingInformationAndConditionTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPPCTD());
    Mockito.when(
            portLoadingPlanRobDetailsRepository
                .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                    Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
        .thenReturn(getLPLPRD());
    Mockito.when(synopticalOperationServiceBlockingStub.updateSynopticalTable(Mockito.any()))
        .thenReturn(getResponse());
    ReflectionTestUtils.setField(
        loadingPlanService,
        "synopticalOperationServiceBlockingStub",
        this.synopticalOperationServiceBlockingStub);
    this.loadingPlanService.saveUpdatedLoadingPlanDetails(loadingInformation, conditionType);
    Mockito.verify(portLoadingPlanBallastDetailsRepository).saveAll(Mockito.anyList());
  }

  private Common.ResponseStatus getResponse() {
    Common.ResponseStatus responseStatus =
        Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build();
    return responseStatus;
  }

  @Test
  void testGetPortWiseStowageTempDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setPatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            portLoadingPlanStowageTempDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLSTD());
    this.loadingPlanService.getPortWiseStowageTempDetails(request, builder);
    assertEquals("1", builder.getPortLoadablePlanStowageTempDetails(0).getApi());
  }

  @Test
  void testGetPortWiseBallastTempDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setPatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            portLoadingPlanBallastTempDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPBT());
    this.loadingPlanService.getPortWiseBallastTempDetails(request, builder);
    assertEquals("1", builder.getPortLoadingPlanBallastTempDetails(0).getSg());
  }

  @Test
  void testGetPortWiseCommingleDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder()
            .setVesselId(1L)
            .setPortRotationId(1L)
            .setPatternId(1L)
            .build();
    LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder();
    Mockito.when(
            this.loadingInformationRepository
                .findByVesselXIdAndLoadablePatternXIdAndPortRotationXIdAndIsActiveTrue(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(
            this.portLoadingPlanCommingleDetailsRepository.findByLoadingInformationAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLPLPCD());
    this.loadingPlanService.getPortWiseCommingleDetails(request, builder);
    assertEquals("1", builder.getLoadablePlanCommingleDetails(0).getCargo1Kl());
  }
}
