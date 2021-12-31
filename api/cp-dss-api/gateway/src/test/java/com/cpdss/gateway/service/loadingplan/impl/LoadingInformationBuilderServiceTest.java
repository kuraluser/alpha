/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static com.cpdss.gateway.common.GatewayConstants.FAILED;
import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.service.loadingplan.LoadingInformationBuilderService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingInformationBuilderService.class})
public class LoadingInformationBuilderServiceTest {

  @Autowired LoadingInformationBuilderService loadingInformationBuilderService;

  @MockBean
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  @Test
  void testSaveDataAsync() {
    LoadingInformationRequest request = new LoadingInformationRequest();
    request.setLoadingInfoId(1L);
    request.setSynopticalTableId(1L);
    request.setIsLoadingInfoComplete(true);
    request.setLoadingDetails(getLD());
    request.setLoadingStages(getLSR());
    request.setLoadingRates(getLR());
    request.setLoadingBerths(getLBD());
    request.setLoadingDelays(getLLD());
    request.setLoadingMachineries(getLLM());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInformation(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoStages(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoRates(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoBerths(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoDelays(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoMachinery(Mockito.any()))
        .thenReturn(getLISR());
    ReflectionTestUtils.setField(
        loadingInformationBuilderService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingInformationBuilderService.saveDataAsync(request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<LoadingMachinesInUse> getLLM() {
    List<LoadingMachinesInUse> list = new ArrayList<>();
    LoadingMachinesInUse use = new LoadingMachinesInUse();
    use.setCapacity(new BigDecimal(1));
    use.setMachineId(1L);
    use.setCapacity(new BigDecimal(1));
    use.setMachineTypeId(1);
    use.setIsUsing(true);
    list.add(use);
    return list;
  }

  private List<LoadingDelays> getLLD() {
    List<LoadingDelays> list = new ArrayList<>();
    LoadingDelays loadingDelays = new LoadingDelays();
    loadingDelays.setId(1L);
    loadingDelays.setCargoId(1L);
    loadingDelays.setDuration(new BigDecimal(1));
    loadingDelays.setQuantity(new BigDecimal(1));
    loadingDelays.setReasonForDelayIds(getL());
    loadingDelays.setCargoNominationId(1L);
    list.add(loadingDelays);
    return list;
  }

  private List<Long> getL() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    return list;
  }

  private List<BerthDetails> getLBD() {
    List<BerthDetails> list = new ArrayList<>();
    BerthDetails berthDetails = new BerthDetails();
    berthDetails.setAirDraftLimitation(new BigDecimal(1));
    berthDetails.setHoseConnections("1");
    berthDetails.setBerthId(1L);
    berthDetails.setLoadingBerthId(1L);
    berthDetails.setMaxManifoldHeight(new BigDecimal(1));
    berthDetails.setRegulationAndRestriction("1");
    berthDetails.setSeaDraftLimitation(new BigDecimal(1));
    berthDetails.setItemsToBeAgreedWith("1");
    berthDetails.setMaxShipDepth(new BigDecimal(1));
    berthDetails.setLineDisplacement("1");
    list.add(berthDetails);
    return list;
  }

  private LoadingRates getLR() {
    LoadingRates loadingRates = new LoadingRates();
    loadingRates.setLineContentRemaining(new BigDecimal(1));
    loadingRates.setMaxDeBallastingRate(new BigDecimal(1));
    loadingRates.setMaxLoadingRate(new BigDecimal(1));
    loadingRates.setMinDeBallastingRate(new BigDecimal(1));
    loadingRates.setMinLoadingRate(new BigDecimal(1));
    loadingRates.setNoticeTimeStopLoading(new BigDecimal(1));
    loadingRates.setNoticeTimeRateReduction(new BigDecimal(1));
    loadingRates.setReducedLoadingRate(new BigDecimal(1));
    loadingRates.setShoreLoadingRate(new BigDecimal(1));
    return loadingRates;
  }

  private LoadingStagesRequest getLSR() {
    LoadingStagesRequest request = new LoadingStagesRequest();
    request.setStageDuration(getSD());
    request.setStageOffset(getSO());
    request.setTrackStartEndStage(true);
    request.setTrackGradeSwitch(true);
    return request;
  }

  private StageOffset getSO() {
    StageOffset stageOffset = new StageOffset();
    stageOffset.setId(1L);
    stageOffset.setStageOffsetVal(1L);
    return stageOffset;
  }

  private StageDuration getSD() {
    StageDuration stageDuration = new StageDuration();
    stageDuration.setId(1L);
    stageDuration.setDuration(1L);
    return stageDuration;
  }

  private com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse
      getLISR() {
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse response =
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse
            .newBuilder()
            .setLoadingInfoId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private LoadingDetails getLD() {
    LoadingDetails loadingDetails = new LoadingDetails();
    loadingDetails.setTimeOfSunset("1");
    loadingDetails.setStartTime("1");
    loadingDetails.setTimeOfSunrise("1");
    loadingDetails.setTrimAllowed(getTA());
    return loadingDetails;
  }

  private TrimAllowed getTA() {
    TrimAllowed trimAllowed = new TrimAllowed();
    trimAllowed.setFinalTrim(new BigDecimal(1));
    trimAllowed.setInitialTrim(new BigDecimal(1));
    trimAllowed.setMaximumTrim(new BigDecimal(1));
    return trimAllowed;
  }

  @Test
  void testSaveDataAsyncException1() {
    LoadingInformationRequest request = new LoadingInformationRequest();
    request.setLoadingInfoId(1L);
    request.setSynopticalTableId(1L);
    request.setIsLoadingInfoComplete(true);
    request.setLoadingDetails(getLD());
    request.setLoadingStages(getLSR());
    request.setLoadingRates(getLR());
    request.setLoadingBerths(getLBD());
    request.setLoadingDelays(getLLD());
    request.setLoadingMachineries(getLLM());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInformation(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoStages(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoRates(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoBerths(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoDelays(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoMachinery(Mockito.any()))
        .thenReturn(getLISRNS());
    ReflectionTestUtils.setField(
        loadingInformationBuilderService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingInformationBuilderService.saveDataAsync(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse
      getLISRNS() {
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse response =
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoSaveResponse
            .newBuilder()
            .setLoadingInfoId(0L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return response;
  }

  @Test
  void testSaveDataAsyncException2() {
    LoadingInformationRequest request = new LoadingInformationRequest();
    request.setLoadingInfoId(1L);
    request.setSynopticalTableId(1L);
    request.setIsLoadingInfoComplete(true);
    request.setLoadingDetails(getLD());
    request.setLoadingStages(getLSR());
    request.setLoadingRates(getLR());
    request.setLoadingBerths(getLBD());
    request.setLoadingDelays(getLLD());
    request.setLoadingMachineries(getLLM());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInformation(Mockito.any()))
        .thenThrow(new RuntimeException());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoStages(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoRates(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoBerths(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoDelays(Mockito.any()))
        .thenReturn(getLISR());
    Mockito.when(loadingInfoServiceBlockingStub.saveLoadingInfoMachinery(Mockito.any()))
        .thenReturn(getLISRNS());
    ReflectionTestUtils.setField(
        loadingInformationBuilderService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingInformationBuilderService.saveDataAsync(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildToppingOffSequences() {
    List<ToppingOffSequence> toppingOffList = new ArrayList<>();
    ToppingOffSequence toppingOffSequence = new ToppingOffSequence();
    toppingOffSequence.setCargoAbbreviation("1");
    toppingOffSequence.setCargoId(1L);
    toppingOffSequence.setCargoName("1");
    toppingOffSequence.setColourCode("1");
    toppingOffSequence.setFillingRatio(new BigDecimal(1));
    toppingOffSequence.setId(1L);
    toppingOffSequence.setLoadingInfoId(1L);
    toppingOffSequence.setOrderNumber(1);
    toppingOffSequence.setQuantity(new BigDecimal(1));
    toppingOffSequence.setRemark("1");
    toppingOffSequence.setTankId(1L);
    toppingOffSequence.setUllage(new BigDecimal(1));
    toppingOffSequence.setDisplayOrder(1);
    toppingOffList.add(toppingOffSequence);
    var response = this.loadingInformationBuilderService.buildToppingOffSequences(toppingOffList);
    assertEquals(1L, response.get(0).getCargoId());
  }
}
