/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.LoadingDelayReasonRepository;
import com.cpdss.loadingplan.service.LoadingInformationBuilderService;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingInformationBuilderService.class})
public class LoadingInformationBuilderServiceTest {

  @Autowired LoadingInformationBuilderService loadingInformationBuilderService;

  @MockBean LoadingDelayReasonRepository loadingDelayReasonRepository;

  @Test
  void testBuildLoadingDetailsMessage() {
    LoadingInformation var1 = new LoadingInformation();
    var1.setId(1L);
    var1.setSunriseTime(LocalTime.now());
    var1.setSunsetTime(LocalTime.now());
    var1.setStartTime(LocalTime.now());
    var1.setInitialTrim(new BigDecimal(1));
    var1.setMaximumTrim(new BigDecimal(1));
    var1.setFinalTrim(new BigDecimal(1));
    var loadingDetails = this.loadingInformationBuilderService.buildLoadingDetailsMessage(var1);
    assertEquals("1", loadingDetails.getTrimAllowed().getInitialTrim());
  }

  @Test
  void testBuildLoadingRateMessage() {
    LoadingInformation var1 = new LoadingInformation();
    var1.setId(1L);
    var1.setInitialLoadingRate(new BigDecimal(1));
    var1.setMaxLoadingRate(new BigDecimal(1));
    var1.setReducedLoadingRate(new BigDecimal(1));
    var1.setMinDeBallastRate(new BigDecimal(1));
    var1.setMaxDeBallastRate(new BigDecimal(1));
    var1.setNoticeTimeForRateReduction(1);
    var1.setNoticeTimeForStopLoading(1);
    var1.setLineContentRemaining(new BigDecimal(1));
    var1.setMinLoadingRate(new BigDecimal(1));
    var1.setShoreLoadingRate(new BigDecimal(1));
    var loadingRates = this.loadingInformationBuilderService.buildLoadingRateMessage(var1);
    assertEquals("1", loadingRates.getMaxDeBallastingRate());
  }

  @Test
  void TestbuildLoadingBerthsMessage() {
    List<LoadingBerthDetail> list = new ArrayList<>();
    LoadingBerthDetail detail = new LoadingBerthDetail();
    detail.setId(1L);
    detail.setBerthXId(1L);
    detail.setDepth(new BigDecimal(1));
    detail.setSeaDraftLimitation(new BigDecimal(1));
    detail.setAirDraftLimitation(new BigDecimal(1));
    detail.setMaxManifoldHeight(new BigDecimal(1));
    detail.setSpecialRegulationRestriction("1");
    detail.setItemToBeAgreedWith("1");
    detail.setHoseConnections("1");
    detail.setLineDisplacement(new BigDecimal(1));
    detail.setLoadingInformation(getLI());
    list.add(detail);
    var berths = this.loadingInformationBuilderService.buildLoadingBerthsMessage(list);
    assertEquals("1", berths.get(0).getAirDraftLimitation());
  }

  private LoadingInformation getLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    return loadingInformation;
  }

  @Test
  void TestbuildLoadingMachineryInUseMessage() {
    List<LoadingMachineryInUse> list = new ArrayList<>();
    LoadingMachineryInUse machinesInUse = new LoadingMachineryInUse();
    machinesInUse.setId(1L);
    machinesInUse.setLoadingInformation(getLI());
    machinesInUse.setMachineXId(1L);
    machinesInUse.setMachineTypeXid(1);
    machinesInUse.setCapacity(new BigDecimal(1));
    machinesInUse.setIsUsing(true);
    list.add(machinesInUse);
    var machinery = this.loadingInformationBuilderService.buildLoadingMachineryInUseMessage(list);
    assertEquals("1", machinery.get(0).getCapacity());
  }

  @Test
  void testbuildLoadingStageMessage() {
    LoadingInformation var1 = new LoadingInformation();
    var1.setId(1L);
    var1.setStageOffset(getSO());
    var1.setStageDuration(getSD());
    var1.setTrackStartEndStage(true);
    var1.setTrackGradeSwitch(true);
    List<StageOffset> list3 = new ArrayList<>();
    StageOffset stageOffset = new StageOffset();
    stageOffset.setId(1L);
    stageOffset.setStageOffsetVal(1);
    list3.add(stageOffset);
    List<com.cpdss.loadingplan.entity.StageDuration> list4 = new ArrayList<>();
    StageDuration stageDuration = new StageDuration();
    stageDuration.setId(1L);
    stageDuration.setDuration(1);
    list4.add(stageDuration);
    var loadingStages =
        this.loadingInformationBuilderService.buildLoadingStageMessage(var1, list3, list4);
    assertEquals(1, loadingStages.getStageOffset());
  }

  private StageOffset getSO() {
    StageOffset stageOffset = new StageOffset();
    stageOffset.setId(1L);
    return stageOffset;
  }

  private StageDuration getSD() {
    StageDuration stageDuration = new StageDuration();
    stageDuration.setId(1L);
    return stageDuration;
  }

  @Test
  void testbuildStageDurationMasterMessage() {
    List<com.cpdss.loadingplan.entity.StageDuration> list = new ArrayList<>();
    com.cpdss.loadingplan.entity.StageDuration stageDuration = new StageDuration();
    stageDuration.setId(1L);
    stageDuration.setDuration(1);
    list.add(stageDuration);
    var durations = this.loadingInformationBuilderService.buildStageDurationMasterMessage(list);
    assertEquals(1L, durations.get(0).getDuration());
  }

  @Test
  void testBuildStageOffsetMasterMessage() {
    List<StageOffset> list = new ArrayList<>();
    StageOffset stageOffset = new StageOffset();
    stageOffset.setId(1L);
    stageOffset.setStageOffsetVal(1);
    list.add(stageOffset);
    var offsets = this.loadingInformationBuilderService.buildStageOffsetMasterMessage(list);
    assertEquals(1L, offsets.get(0).getStageOffsetVal());
  }

  @Test
  void testBuildLoadingDelayMessage() {
    List<ReasonForDelay> list = new ArrayList<>();
    ReasonForDelay delay = new ReasonForDelay();
    delay.setId(1L);
    delay.setReason("1");
    list.add(delay);
    List<com.cpdss.loadingplan.entity.LoadingDelay> list6 = new ArrayList<>();
    com.cpdss.loadingplan.entity.LoadingDelay loadingDelay = new LoadingDelay();
    loadingDelay.setId(1L);
    loadingDelay.setLoadingInformation(getLI());
    loadingDelay.setLoadingDelayReasons(getLDR());
    loadingDelay.setDuration(new BigDecimal(1));
    loadingDelay.setCargoXId(1L);
    loadingDelay.setQuantity(new BigDecimal(1));
    loadingDelay.setCargoNominationId(1L);
    list6.add(loadingDelay);
    Mockito.when(loadingDelayReasonRepository.findAllByLoadingDelayAndIsActiveTrue(Mockito.any()))
        .thenReturn(getLDR());
    var Delay = this.loadingInformationBuilderService.buildLoadingDelayMessage(list, list6);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(delay.getId()));
  }

  private List<LoadingDelayReason> getLDR() {
    List<LoadingDelayReason> list = new ArrayList<>();
    LoadingDelayReason delayReason = new LoadingDelayReason();
    delayReason.setId(1L);
    delayReason.setReasonForDelay(getRD());
    list.add(delayReason);
    return list;
  }

  private ReasonForDelay getRD() {
    ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setId(1L);
    return reasonForDelay;
  }

  @Test
  void testBuildToppingOffMessage() {
    List<CargoToppingOffSequence> list = new ArrayList<>();
    CargoToppingOffSequence toppingOffSequence = new CargoToppingOffSequence();
    toppingOffSequence.setId(1L);
    toppingOffSequence.setLoadingInformation(getLI());
    toppingOffSequence.setOrderNumber(1);
    toppingOffSequence.setTankXId(1L);
    toppingOffSequence.setCargoXId(1L);
    toppingOffSequence.setUllage(new BigDecimal(1));
    toppingOffSequence.setQuantity(new BigDecimal(1));
    toppingOffSequence.setFillingRatio(new BigDecimal(1));
    toppingOffSequence.setRemarks("1");
    toppingOffSequence.setApi(new BigDecimal(1));
    toppingOffSequence.setTemperature(new BigDecimal(1));
    toppingOffSequence.setDisplayOrder(1);
    toppingOffSequence.setAbbreviation("1");
    toppingOffSequence.setCargoNominationXId(1L);
    list.add(toppingOffSequence);
    var toppingOffs = this.loadingInformationBuilderService.buildToppingOffMessage(list);
    assertEquals("1", toppingOffs.get(0).getQuantity());
  }

  @Test
  void testBuildLoadingInfoFromRpcMessage() {
    LoadingPlanModels.LoadingInformation source =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .setLoadingDetail(
                LoadingPlanModels.LoadingDetails.newBuilder()
                    .setTrimAllowed(
                        LoadingPlanModels.TrimAllowed.newBuilder()
                            .setMaximumTrim("1")
                            .setInitialTrim("1")
                            .setFinalTrim("1")
                            .build())
                    .setStartTime("11:10")
                    .build())
            .build();
    LoadingInformation target = new LoadingInformation();
    var result =
        this.loadingInformationBuilderService.buildLoadingInfoFromRpcMessage(source, target);
    assertEquals(new BigDecimal(1), result.getMaximumTrim());
  }

  @Test
  void testBuildLoadingPlanTankBallastMessage() {
    List<PortLoadingPlanBallastDetails> list = new ArrayList<>();
    PortLoadingPlanBallastDetails portLoadingPlanBallastDetails =
        new PortLoadingPlanBallastDetails();
    portLoadingPlanBallastDetails.setId(1L);
    portLoadingPlanBallastDetails.setQuantity(new BigDecimal(1));
    portLoadingPlanBallastDetails.setTankXId(1L);
    portLoadingPlanBallastDetails.setQuantityM3(new BigDecimal(1));
    portLoadingPlanBallastDetails.setSounding(new BigDecimal(1));
    portLoadingPlanBallastDetails.setConditionType(1);
    portLoadingPlanBallastDetails.setValueType(1);
    portLoadingPlanBallastDetails.setColorCode("1");
    portLoadingPlanBallastDetails.setSg(new BigDecimal(1));
    list.add(portLoadingPlanBallastDetails);
    try {
      var response = this.loadingInformationBuilderService.buildLoadingPlanTankBallastMessage(list);
      assertEquals(1L, response.get(0).getTankId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testbuildLoadingPlanTankStowageMessage() {
    List<PortLoadingPlanStowageDetails> list = new ArrayList<>();
    PortLoadingPlanStowageDetails portLoadingPlanStowageDetails =
        new PortLoadingPlanStowageDetails();
    portLoadingPlanStowageDetails.setId(1L);
    portLoadingPlanStowageDetails.setApi(new BigDecimal(1));
    portLoadingPlanStowageDetails.setTemperature(new BigDecimal(1));
    portLoadingPlanStowageDetails.setCargoNominationXId(1L);
    portLoadingPlanStowageDetails.setQuantity(new BigDecimal(1));
    portLoadingPlanStowageDetails.setUllage(new BigDecimal(1));
    portLoadingPlanStowageDetails.setTankXId(1L);
    portLoadingPlanStowageDetails.setQuantityM3(new BigDecimal(1));
    portLoadingPlanStowageDetails.setConditionType(1);
    portLoadingPlanStowageDetails.setColorCode("1");
    portLoadingPlanStowageDetails.setValueType(1);
    portLoadingPlanStowageDetails.setAbbreviation("1");
    portLoadingPlanStowageDetails.setCargoXId(1L);
    list.add(portLoadingPlanStowageDetails);
    try {
      var response = this.loadingInformationBuilderService.buildLoadingPlanTankStowageMessage(list);
      assertEquals(1L, response.get(0).getCargoId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildLoadingPlanTankRobMessage() {
    List<PortLoadingPlanRobDetails> list = new ArrayList<>();
    PortLoadingPlanRobDetails planRobDetails = new PortLoadingPlanRobDetails();
    planRobDetails.setId(1L);
    planRobDetails.setQuantity(new BigDecimal(1));
    planRobDetails.setTankXId(1L);
    planRobDetails.setQuantityM3(new BigDecimal(1));
    planRobDetails.setConditionType(1);
    planRobDetails.setValueType(1);
    planRobDetails.setColorCode("1");
    planRobDetails.setDensity(new BigDecimal(1));
    list.add(planRobDetails);
    try {
      var response = this.loadingInformationBuilderService.buildLoadingPlanTankRobMessage(list);
      assertEquals(1L, response.get(0).getTankId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildLoadingPlanTankStabilityMessage() {
    List<PortLoadingPlanStabilityParameters> list = new ArrayList<>();
    PortLoadingPlanStabilityParameters parameters = new PortLoadingPlanStabilityParameters();
    parameters.setForeDraft(new BigDecimal(1));
    parameters.setAftDraft(new BigDecimal(1));
    parameters.setMeanDraft(new BigDecimal(1));
    parameters.setBendingMoment(new BigDecimal(1));
    parameters.setTrim(new BigDecimal(1));
    parameters.setShearingForce(new BigDecimal(1));
    parameters.setFreeboard(new BigDecimal(1));
    parameters.setManifoldHeight(new BigDecimal(1));
    parameters.setConditionType(1);
    parameters.setValueType(1);
    list.add(parameters);
    try {
      var response =
          this.loadingInformationBuilderService.buildLoadingPlanTankStabilityMessage(list);
      assertEquals("1", response.get(0).getAftDraft());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildLoadingPlanCommingleMessage() {
    List<PortLoadingPlanCommingleDetails> list = new ArrayList<>();
    PortLoadingPlanCommingleDetails commingleDetails = new PortLoadingPlanCommingleDetails();
    commingleDetails.setGrade("1");
    commingleDetails.setApi("1");
    commingleDetails.setCargo1XId(1L);
    commingleDetails.setCargo2XId(1L);
    commingleDetails.setCargoNomination1XId(1L);
    commingleDetails.setCargoNomination2XId(1L);
    commingleDetails.setId(1L);
    commingleDetails.setTankId(1L);
    commingleDetails.setQuantity("1");
    commingleDetails.setQuantityM3("1");
    commingleDetails.setTemperature("1");
    commingleDetails.setQuantity1MT("1");
    commingleDetails.setQuantity2MT("1");
    commingleDetails.setQuantity1M3("1");
    commingleDetails.setQuantity2M3("1");
    commingleDetails.setUllage("1");
    commingleDetails.setUllage1("1");
    commingleDetails.setUllage2("1");
    commingleDetails.setConditionType(1);
    commingleDetails.setValueType(1);
    list.add(commingleDetails);
    try {
      var response = this.loadingInformationBuilderService.buildLoadingPlanCommingleMessage(list);
      assertEquals("1", response.get(0).getApi());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
