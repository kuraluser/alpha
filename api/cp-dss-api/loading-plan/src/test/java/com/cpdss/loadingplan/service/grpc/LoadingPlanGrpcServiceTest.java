/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.entity.BillOfLadding;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanCommingleDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.PyUserRepository;
import com.cpdss.loadingplan.service.LoadingCargoHistoryService;
import com.cpdss.loadingplan.service.LoadingInformationBuilderService;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.LoadingSequenceService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingPlanRuleServiceImpl;
import com.cpdss.loadingplan.service.loadicator.LoadicatorService;
import io.grpc.internal.testing.StreamRecorder;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadingPlanGrpcService.class,
    })
public class LoadingPlanGrpcServiceTest {
  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;
  @MockBean LoadingPlanService loadingPlanService;
  @MockBean LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean LoadingSequenceService loadingSequenceService;
  @MockBean LoadicatorService loadicatorService;
  @MockBean LoadingPlanRuleServiceImpl loadingPlanRuleService;
  @MockBean PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @MockBean BillOfLaddingRepository billOfLaddingRepository;
  @MockBean PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;
  @MockBean LoadingCargoHistoryService loadingCargoHistoryService;
  @MockBean LoadingInformationBuilderService informationBuilderService;
  @MockBean LoadingPlanStagingService loadingPlanStagingService;
  @MockBean PyUserRepository pyUserRepository;
  @MockBean LoadingInformationRepository loadingInformationRepository;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testLoadingPlanSynchronization() {
    LoadingPlanModels.LoadingPlanSyncRequest request =
        LoadingPlanModels.LoadingPlanSyncRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingPlanSyncReply> responseObserver =
        StreamRecorder.create();
    LoadingPlanModels.LoadingInformation builder =
        LoadingPlanModels.LoadingInformation.newBuilder().build();

    doNothing()
        .when(this.loadingPlanService)
        .loadingPlanSynchronization(
            any(LoadingPlanModels.LoadingPlanSyncRequest.class),
            any(LoadingPlanModels.LoadingPlanSyncReply.Builder.class));

    loadingPlanGrpcService.loadingPlanSynchronization(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanSyncReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testLoadingPlanSynchronizationWithException() {
    LoadingPlanModels.LoadingPlanSyncRequest request =
        LoadingPlanModels.LoadingPlanSyncRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingPlanSyncReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanService)
        .loadingPlanSynchronization(
            any(LoadingPlanModels.LoadingPlanSyncRequest.class),
            any(LoadingPlanModels.LoadingPlanSyncReply.Builder.class));

    loadingPlanGrpcService.loadingPlanSynchronization(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanSyncReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetOrSaveRulesForLoadingPlan() throws GenericServiceException {
    LoadingPlanModels.LoadingPlanRuleRequest request =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingPlanRuleReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanRuleService)
        .getOrSaveRulesForLoadingPlan(
            any(LoadingPlanModels.LoadingPlanRuleRequest.class),
            any(LoadingPlanModels.LoadingPlanRuleReply.Builder.class));

    loadingPlanGrpcService.getOrSaveRulesForLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanRuleReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetOrSaveRulesForLoadingPlanWithException() throws GenericServiceException {
    LoadingPlanModels.LoadingPlanRuleRequest request =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingPlanRuleReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanRuleService)
        .getOrSaveRulesForLoadingPlan(
            any(LoadingPlanModels.LoadingPlanRuleRequest.class),
            any(LoadingPlanModels.LoadingPlanRuleReply.Builder.class));

    loadingPlanGrpcService.getOrSaveRulesForLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanRuleReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingPlan() throws GenericServiceException {
    LoadingPlanModels.LoadingPlanSaveRequest request =
        LoadingPlanModels.LoadingPlanSaveRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingPlanSaveResponse> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanAlgoService)
        .saveLoadingSequenceAndPlan(
            any(LoadingPlanModels.LoadingPlanSaveResponse.Builder.class),
            any(LoadingPlanModels.LoadingPlanSaveRequest.class));

    loadingPlanGrpcService.saveLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingPlanWithException() throws GenericServiceException {
    LoadingPlanModels.LoadingPlanSaveRequest request =
        LoadingPlanModels.LoadingPlanSaveRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingPlanSaveResponse> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanAlgoService)
        .saveLoadingSequenceAndPlan(
            any(LoadingPlanModels.LoadingPlanSaveResponse.Builder.class),
            any(LoadingPlanModels.LoadingPlanSaveRequest.class));

    loadingPlanGrpcService.saveLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingSequences() throws Exception {
    LoadingPlanModels.LoadingSequenceRequest request =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder().setLoadingInfoId(1l).build();
    StreamRecorder<LoadingPlanModels.LoadingSequenceReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingSequenceService)
        .getLoadingSequences(
            any(LoadingPlanModels.LoadingSequenceRequest.class),
            any(LoadingPlanModels.LoadingSequenceReply.Builder.class));

    loadingPlanGrpcService.getLoadingSequences(request, responseObserver);
    List<LoadingPlanModels.LoadingSequenceReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingSequencesWithException() throws Exception {
    LoadingPlanModels.LoadingSequenceRequest request =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder().setLoadingInfoId(1l).build();
    StreamRecorder<LoadingPlanModels.LoadingSequenceReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingSequenceService)
        .getLoadingSequences(
            any(LoadingPlanModels.LoadingSequenceRequest.class),
            any(LoadingPlanModels.LoadingSequenceReply.Builder.class));

    loadingPlanGrpcService.getLoadingSequences(request, responseObserver);
    List<LoadingPlanModels.LoadingSequenceReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingPlan() throws Exception {
    LoadingPlanModels.LoadingInformationRequest request =
        LoadingPlanModels.LoadingInformationRequest.newBuilder().setLoadingPlanId(1l).build();
    StreamRecorder<LoadingPlanModels.LoadingPlanReply> responseObserver = StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanService)
        .getLoadingPlan(
            any(LoadingPlanModels.LoadingInformationRequest.class),
            any(LoadingPlanModels.LoadingPlanReply.Builder.class));

    loadingPlanGrpcService.getLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetLoadingPlanWithException() throws Exception {
    LoadingPlanModels.LoadingInformationRequest request =
        LoadingPlanModels.LoadingInformationRequest.newBuilder().setLoadingPlanId(1l).build();
    StreamRecorder<LoadingPlanModels.LoadingPlanReply> responseObserver = StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanService)
        .getLoadingPlan(
            any(LoadingPlanModels.LoadingInformationRequest.class),
            any(LoadingPlanModels.LoadingPlanReply.Builder.class));

    loadingPlanGrpcService.getLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingPlanReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetUpdateUllageDetails() throws Exception {
    LoadingPlanModels.UpdateUllageDetailsRequest request =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UpdateUllageDetailsResponse> responseObserver =
        StreamRecorder.create();

    loadingPlanGrpcService.getUpdateUllageDetails(request, responseObserver);
    List<LoadingPlanModels.UpdateUllageDetailsResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetBillOfLaddingDetails() {
    LoadingPlanModels.BillOfLaddingRequest request =
        LoadingPlanModels.BillOfLaddingRequest.newBuilder().setCargoNominationId(1l).build();
    StreamRecorder<LoadingInformationSynopticalReply> responseObserver = StreamRecorder.create();

    when(billOfLaddingRepository.findByCargoNominationIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLaddingList());

    loadingPlanGrpcService.getBillOfLaddingDetails(request, responseObserver);
    List<LoadingInformationSynopticalReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetBillOfLaddingDetailsWithException() {
    LoadingPlanModels.BillOfLaddingRequest request =
        LoadingPlanModels.BillOfLaddingRequest.newBuilder().setCargoNominationId(1l).build();
    StreamRecorder<LoadingInformationSynopticalReply> responseObserver = StreamRecorder.create();

    when(billOfLaddingRepository.findByCargoNominationIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(new RuntimeException("1"));

    loadingPlanGrpcService.getBillOfLaddingDetails(request, responseObserver);
    List<LoadingInformationSynopticalReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoNominationMaxQuantity() {
    LoadingPlanModels.MaxQuantityRequest request =
        LoadingPlanModels.MaxQuantityRequest.newBuilder()
            .addAllCargoNominationId(Arrays.asList(1l))
            .build();
    StreamRecorder<LoadingPlanModels.MaxQuantityResponse> responseObserver =
        StreamRecorder.create();

    when(billOfLaddingRepository.findByCargoNominationIdInAndIsActive(anyList(), anyBoolean()))
        .thenReturn(getLaddingList());

    loadingPlanGrpcService.getCargoNominationMaxQuantity(request, responseObserver);
    List<LoadingPlanModels.MaxQuantityResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoNominationMaxQuantityWithException() {
    LoadingPlanModels.MaxQuantityRequest request =
        LoadingPlanModels.MaxQuantityRequest.newBuilder()
            .addAllCargoNominationId(Arrays.asList(1l))
            .build();
    StreamRecorder<LoadingPlanModels.MaxQuantityResponse> responseObserver =
        StreamRecorder.create();

    when(billOfLaddingRepository.findByCargoNominationIdInAndIsActive(anyList(), anyBoolean()))
        .thenThrow(new RuntimeException("1"));

    loadingPlanGrpcService.getCargoNominationMaxQuantity(request, responseObserver);
    List<LoadingPlanModels.MaxQuantityResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadicatorData()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    LoadingPlanModels.LoadingInfoLoadicatorDataRequest request =
        LoadingPlanModels.LoadingInfoLoadicatorDataRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoLoadicatorDataReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(loadicatorService)
        .getLoadicatorData(
            any(LoadingPlanModels.LoadingInfoLoadicatorDataRequest.class),
            any(LoadingPlanModels.LoadingInfoLoadicatorDataReply.Builder.class));

    loadingPlanGrpcService.getLoadicatorData(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoLoadicatorDataReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadicatorDataWithException()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    LoadingPlanModels.LoadingInfoLoadicatorDataRequest request =
        LoadingPlanModels.LoadingInfoLoadicatorDataRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoLoadicatorDataReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(loadicatorService)
        .getLoadicatorData(
            any(LoadingPlanModels.LoadingInfoLoadicatorDataRequest.class),
            any(LoadingPlanModels.LoadingInfoLoadicatorDataReply.Builder.class));

    loadingPlanGrpcService.getLoadicatorData(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoLoadicatorDataReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadableStudyShoreTwo() throws GenericServiceException {
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UllageBillReply> responseObserver = StreamRecorder.create();

    doNothing()
        .when(loadingPlanService)
        .getLoadableStudyShoreTwo(
            any(LoadingPlanModels.UllageBillRequest.class),
            any(LoadingPlanModels.UllageBillReply.Builder.class));

    loadingPlanGrpcService.getLoadableStudyShoreTwo(request, responseObserver);
    List<LoadingPlanModels.UllageBillReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetLoadableStudyShoreTwoWithException() throws GenericServiceException {
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UllageBillReply> responseObserver = StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(loadingPlanService)
        .getLoadableStudyShoreTwo(
            any(LoadingPlanModels.UllageBillRequest.class),
            any(LoadingPlanModels.UllageBillReply.Builder.class));

    loadingPlanGrpcService.getLoadableStudyShoreTwo(request, responseObserver);
    List<LoadingPlanModels.UllageBillReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testValidateStowageAndBillOfLadding() {
    List<LoadingPlanModels.PortWiseCargo> cargoList = new ArrayList<>();
    LoadingPlanModels.PortWiseCargo cargo =
        LoadingPlanModels.PortWiseCargo.newBuilder()
            .setPortRotationId(1l)
            .setPortId(1l)
            .addAllCargoIds(Arrays.asList(1l))
            .build();
    cargoList.add(cargo);
    LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request =
        LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.newBuilder()
            .addAllPortWiseCargos(cargoList)
            .setPatternId(1L)
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();

    when(portLoadingPlanStowageDetailsRepository
            .findByPortRotationXIdAndIsActiveAndConditionTypeAndValueType(
                anyLong(), anyBoolean(), anyInt(), anyInt()))
        .thenReturn(getStowageDetailsList());
    when(billOfLaddingRepository
            .findByCargoNominationIdInAndLoadingInformation_LoadablePatternXIdAndIsActive(
                anyList(), anyLong(), anyBoolean()))
        .thenReturn(getLaddingList());

    loadingPlanGrpcService.validateStowageAndBillOfLadding(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
  }

  @Test
  void testValidateStowageAndBillOfLaddingWithException() {
    List<LoadingPlanModels.PortWiseCargo> cargoList = new ArrayList<>();
    LoadingPlanModels.PortWiseCargo cargo =
        LoadingPlanModels.PortWiseCargo.newBuilder()
            .setPortRotationId(1l)
            .setPortId(1l)
            .addAllCargoIds(Arrays.asList(1l))
            .build();
    cargoList.add(cargo);
    LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request =
        LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.newBuilder()
            .addAllPortWiseCargos(cargoList)
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();

    when(portLoadingPlanStowageDetailsRepository.findByPortRotationXIdInAndIsActive(
            anyList(), anyBoolean()))
        .thenThrow(new RuntimeException("1"));

    loadingPlanGrpcService.validateStowageAndBillOfLadding(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getStatus());
  }

  @Test
  void testgetLoadingPlanCommingleDetails() {
    LoadingPlanModels.LoadingInformationSynopticalRequest request =
        LoadingPlanModels.LoadingInformationSynopticalRequest.newBuilder()
            .setSynopticalId(1L)
            .build();
    StreamRecorder<LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> responseObserver =
        StreamRecorder.create();

    when(portLoadingPlanCommingleDetailsRepository.findByLoadablePatternIdAndIsActiveTrue(
            anyLong()))
        .thenReturn(getCommingleDetailsList());
    loadingPlanGrpcService.getLoadingPlanCommingleDetails(request, responseObserver);
    List<LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testgetLoadingPlanCargoHistory() {
    Common.CargoHistoryOpsRequest request = Common.CargoHistoryOpsRequest.newBuilder().build();
    StreamRecorder<Common.CargoHistoryResponse> responseObserver = StreamRecorder.create();

    doNothing()
        .when(loadingCargoHistoryService)
        .buildCargoDetailsFromStowageData(
            any(Common.CargoHistoryOpsRequest.class),
            any(Common.CargoHistoryResponse.Builder.class));

    loadingPlanGrpcService.getLoadingPlanCargoHistory(request, responseObserver);
    List<Common.CargoHistoryResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetLoadingPlanCargoHistoryWithException() {
    Common.CargoHistoryOpsRequest request = Common.CargoHistoryOpsRequest.newBuilder().build();
    StreamRecorder<Common.CargoHistoryResponse> responseObserver = StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(loadingCargoHistoryService)
        .buildCargoDetailsFromStowageData(
            any(Common.CargoHistoryOpsRequest.class),
            any(Common.CargoHistoryResponse.Builder.class));

    loadingPlanGrpcService.getLoadingPlanCargoHistory(request, responseObserver);
    List<Common.CargoHistoryResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<PortLoadingPlanStowageDetails> getStowageDetailsList() {
    List<PortLoadingPlanStowageDetails> stowageDetailsList = new ArrayList<>();
    PortLoadingPlanStowageDetails stowageDetails = new PortLoadingPlanStowageDetails();
    stowageDetails.setPortRotationXId(1l);
    stowageDetails.setConditionType(2);
    stowageDetails.setValueType(1);
    stowageDetails.setCargoNominationXId(1l);
    stowageDetails.setQuantity(new BigDecimal(1));
    stowageDetails.setQuantityM3(new BigDecimal(1));
    stowageDetailsList.add(stowageDetails);
    return stowageDetailsList;
  }

  private List<PortLoadingPlanCommingleDetails> getCommingleDetailsList() {
    List<PortLoadingPlanCommingleDetails> commingleDetailsList = new ArrayList<>();
    PortLoadingPlanCommingleDetails commingleDetails = new PortLoadingPlanCommingleDetails();
    commingleDetails.setId(1l);
    commingleDetails.setGrade("1");
    commingleDetails.setTankName("1");
    commingleDetails.setQuantity("1");
    commingleDetails.setApi("1");
    commingleDetails.setTemperature("1");
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
    commingleDetails.setOrderQuantity("1");
    commingleDetails.setPriority(1);
    commingleDetails.setLoadingOrder(1);
    commingleDetails.setTankId(1l);
    commingleDetails.setFillingRatio("1");
    commingleDetails.setCorrectedUllage(1l);
    commingleDetails.setRdgUllage("1");
    commingleDetails.setCorrectionFactor("1");
    commingleDetails.setSlopQuantity("1");
    commingleDetails.setTimeRequiredForLoading("1");
    commingleDetails.setShortName("1");
    LoadingInformation loadingInformation = new LoadingInformation();
    LoadingInformationStatus loadingInformationStatus = new LoadingInformationStatus();
    loadingInformationStatus.setId(13L);
    loadingInformation.setDepartureStatus(loadingInformationStatus);
    loadingInformation.setPortRotationXId(1L);
    loadingInformation.setPortXId(1L);
    commingleDetails.setLoadingInformation(loadingInformation);
    commingleDetailsList.add(commingleDetails);
    return commingleDetailsList;
  }

  private List<com.cpdss.loadingplan.entity.BillOfLadding> getLaddingList() {
    List<com.cpdss.loadingplan.entity.BillOfLadding> billOfLaddingList = new ArrayList<>();
    BillOfLadding ladding = new BillOfLadding();
    ladding.setId(1l);
    ladding.setPortId(1l);
    ladding.setCargoNominationId(1l);
    ladding.setQuantityBbls(new BigDecimal(1));
    ladding.setQuantityMt(new BigDecimal(1));
    ladding.setQuantityKl(new BigDecimal(1));
    ladding.setQuantityLT(new BigDecimal(1));
    ladding.setApi(new BigDecimal(1));
    ladding.setTemperature(new BigDecimal(1));
    LoadingInformation loadingInformation = new LoadingInformation();
    LoadingInformationStatus loadingInformationStatus = new LoadingInformationStatus();
    loadingInformationStatus.setId(13L);
    loadingInformation.setDepartureStatus(loadingInformationStatus);
    ladding.setLoadingInformation(loadingInformation);
    billOfLaddingList.add(ladding);
    return billOfLaddingList;
  }
}
