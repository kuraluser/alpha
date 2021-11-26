/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.grpc;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.service.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.impl.LoadingInformationDischargeService;
import io.grpc.internal.testing.StreamRecorder;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadingInformationGrpcService.class,
    })
public class LoadingInformationGrpcServiceTest {
  @Autowired LoadingInformationGrpcService loadingInformationGrpcService;
  @MockBean LoadingInformationService loadingInformationService;
  @MockBean CargoToppingOffSequenceService toppingOffSequenceService;
  @MockBean LoadingInformationDischargeService loadingInfoService;
  @MockBean LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean LoadingMachineryInUseService loadingMachineryInUseService;
  @MockBean LoadingDelayService loadingDelayService;
  @MockBean LoadingBerthService loadingBerthService;
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testGetLoadingInformation() throws GenericServiceException {
    LoadingPlanModels.LoadingInformationRequest request =
        LoadingPlanModels.LoadingInformationRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInformation> responseObserver = StreamRecorder.create();
    LoadingPlanModels.LoadingInformation builder =
        LoadingPlanModels.LoadingInformation.newBuilder().build();

    when(this.loadingInformationService.getLoadingInformation(
            any(LoadingPlanModels.LoadingInformationRequest.class),
            any(LoadingPlanModels.LoadingInformation.Builder.class)))
        .thenReturn(builder);

    loadingInformationGrpcService.getLoadingInformation(request, responseObserver);
    List<LoadingPlanModels.LoadingInformation> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetLoadingInformationWithException() throws GenericServiceException {
    LoadingPlanModels.LoadingInformationRequest request =
        LoadingPlanModels.LoadingInformationRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInformation> responseObserver = StreamRecorder.create();
    LoadingPlanModels.LoadingInformation builder =
        LoadingPlanModels.LoadingInformation.newBuilder().build();

    when(this.loadingInformationService.getLoadingInformation(
            any(LoadingPlanModels.LoadingInformationRequest.class),
            any(LoadingPlanModels.LoadingInformation.Builder.class)))
        .thenThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS));

    loadingInformationGrpcService.getLoadingInformation(request, responseObserver);
    List<LoadingPlanModels.LoadingInformation> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadigInformationByVoyage() throws Exception {
    LoadingPlanModels.LoadingInformationSynopticalRequest request =
        LoadingPlanModels.LoadingInformationSynopticalRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInformationSynopticalReply> responseObserver =
        StreamRecorder.create();
    LoadingPlanModels.LoadingInformationSynopticalReply.Builder builder =
        LoadingPlanModels.LoadingInformationSynopticalReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    when(this.loadingInfoService.getLoadigInformationByVoyage(
            any(LoadingPlanModels.LoadingInformationSynopticalRequest.class),
            any(LoadingPlanModels.LoadingInformationSynopticalReply.Builder.class)))
        .thenReturn(builder);

    loadingInformationGrpcService.getLoadigInformationByVoyage(request, responseObserver);
    List<LoadingPlanModels.LoadingInformationSynopticalReply> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testGetLoadigInformationByVoyageWithException() throws Exception {
    LoadingPlanModels.LoadingInformationSynopticalRequest request =
        LoadingPlanModels.LoadingInformationSynopticalRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInformationSynopticalReply> responseObserver =
        StreamRecorder.create();
    LoadingPlanModels.LoadingInformationSynopticalReply.Builder builder =
        LoadingPlanModels.LoadingInformationSynopticalReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    when(this.loadingInfoService.getLoadigInformationByVoyage(
            any(LoadingPlanModels.LoadingInformationSynopticalRequest.class),
            any(LoadingPlanModels.LoadingInformationSynopticalReply.Builder.class)))
        .thenThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS));

    loadingInformationGrpcService.getLoadigInformationByVoyage(request, responseObserver);
    List<LoadingPlanModels.LoadingInformationSynopticalReply> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInformation() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.saveLoadingInformation(
            any(LoadingPlanModels.LoadingInformation.class)))
        .thenReturn(getLoadingInfoEntity());

    loadingInformationGrpcService.saveLoadingInformation(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInformationWithException() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.saveLoadingInformation(
            any(LoadingPlanModels.LoadingInformation.class)))
        .thenThrow(new RuntimeException("1"));

    loadingInformationGrpcService.saveLoadingInformation(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoRates() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    when(loadingInformationService.saveLoadingInfoRates(
            any(LoadingPlanModels.LoadingRates.class),
            any(LoadingInformation.class),
            any(LoadingPlanModels.LoadingInfoSaveResponse.Builder.class)))
        .thenReturn(getLoadingInfoEntity());
    doNothing()
        .when(this.loadingInformationService)
        .updateIsLoadingInfoCompeteStatus(anyLong(), anyBoolean());

    loadingInformationGrpcService.saveLoadingInfoRates(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoRatesWithException() {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenThrow(new RuntimeException("1"));

    loadingInformationGrpcService.saveLoadingInfoRates(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoStages() {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    when(loadingInformationService.saveLoadingInfoStages(
            any(LoadingPlanModels.LoadingStages.class), any(LoadingInformation.class)))
        .thenReturn(getLoadingInfoEntity());
    doNothing()
        .when(this.loadingInformationService)
        .updateIsLoadingInfoCompeteStatus(anyLong(), anyBoolean());

    loadingInformationGrpcService.saveLoadingInfoStages(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoStagesWithException() {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenThrow(new RuntimeException("1"));

    loadingInformationGrpcService.saveLoadingInfoStages(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoBerths() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    doNothing()
        .when(loadingBerthService)
        .saveLoadingBerthList(anyList(), any(LoadingInformation.class));
    doNothing()
        .when(this.loadingInformationService)
        .updateIsLoadingInfoCompeteStatus(anyLong(), anyBoolean());

    loadingInformationGrpcService.saveLoadingInfoBerths(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoBerthsWithException() {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenThrow(new RuntimeException("1"));

    loadingInformationGrpcService.saveLoadingInfoBerths(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoMachinery() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    doNothing()
        .when(loadingMachineryInUseService)
        .saveLoadingMachineryList(anyList(), any(LoadingInformation.class));
    doNothing()
        .when(this.loadingInformationService)
        .updateIsLoadingInfoCompeteStatus(anyLong(), anyBoolean());

    loadingInformationGrpcService.saveLoadingInfoMachinery(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoMachineryWithException() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenThrow(new RuntimeException("1"));

    loadingInformationGrpcService.saveLoadingInfoMachinery(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoDelays() throws Exception {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .setLoadingInfoId(1l)
            .setIsLoadingInfoComplete(true)
            .build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    doNothing()
        .when(loadingDelayService)
        .saveLoadingDelayList(
            any(LoadingPlanModels.LoadingDelay.class), any(LoadingInformation.class));
    doNothing()
        .when(this.loadingInformationService)
        .updateIsLoadingInfoCompeteStatus(anyLong(), anyBoolean());

    loadingInformationGrpcService.saveLoadingInfoDelays(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadingInfoDelaysWithException() {
    LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder().setLoadingInfoId(1l).build();
    StreamRecorder<LoadingPlanModels.LoadingInfoSaveResponse> responseObserver =
        StreamRecorder.create();

    when(this.loadingInformationService.getLoadingInformation(anyLong()))
        .thenThrow(new RuntimeException("1"));

    loadingInformationGrpcService.saveLoadingInfoDelays(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoSaveResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUpdateUllage() throws Exception {
    LoadingPlanModels.UpdateUllageLoadingRequest request =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UpdateUllageLoadingReplay> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(toppingOffSequenceService)
        .updateUllageFromLsAlgo(
            any(LoadingPlanModels.UpdateUllageLoadingRequest.class),
            any(LoadingPlanModels.UpdateUllageLoadingReplay.Builder.class));
    loadingInformationGrpcService.updateUllage(request, responseObserver);
    List<LoadingPlanModels.UpdateUllageLoadingReplay> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testUpdateUllageWithException() throws Exception {
    LoadingPlanModels.UpdateUllageLoadingRequest request =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UpdateUllageLoadingReplay> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(toppingOffSequenceService)
        .updateUllageFromLsAlgo(
            any(LoadingPlanModels.UpdateUllageLoadingRequest.class),
            any(LoadingPlanModels.UpdateUllageLoadingReplay.Builder.class));

    loadingInformationGrpcService.updateUllage(request, responseObserver);
    List<LoadingPlanModels.UpdateUllageLoadingReplay> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUpdateUllageWithGenericException() throws Exception {
    LoadingPlanModels.UpdateUllageLoadingRequest request =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UpdateUllageLoadingReplay> responseObserver =
        StreamRecorder.create();

    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(toppingOffSequenceService)
        .updateUllageFromLsAlgo(
            any(LoadingPlanModels.UpdateUllageLoadingRequest.class),
            any(LoadingPlanModels.UpdateUllageLoadingReplay.Builder.class));

    loadingInformationGrpcService.updateUllage(request, responseObserver);
    List<LoadingPlanModels.UpdateUllageLoadingReplay> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveAlgoLoadingPlanStatus() throws Exception {
    LoadableStudy.AlgoStatusRequest request = LoadableStudy.AlgoStatusRequest.newBuilder().build();
    StreamRecorder<LoadableStudy.AlgoStatusReply> responseObserver = StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanAlgoService)
        .saveAlgoLoadingPlanStatus(any(LoadableStudy.AlgoStatusRequest.class));

    loadingInformationGrpcService.saveAlgoLoadingPlanStatus(request, responseObserver);
    List<LoadableStudy.AlgoStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveAlgoLoadingPlanStatusWithException() throws Exception {
    LoadableStudy.AlgoStatusRequest request = LoadableStudy.AlgoStatusRequest.newBuilder().build();
    StreamRecorder<LoadableStudy.AlgoStatusReply> responseObserver = StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanAlgoService)
        .saveAlgoLoadingPlanStatus(any(LoadableStudy.AlgoStatusRequest.class));

    loadingInformationGrpcService.saveAlgoLoadingPlanStatus(request, responseObserver);
    List<LoadableStudy.AlgoStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveAlgoLoadingPlanStatusWithGenericException() throws Exception {
    LoadableStudy.AlgoStatusRequest request = LoadableStudy.AlgoStatusRequest.newBuilder().build();
    StreamRecorder<LoadableStudy.AlgoStatusReply> responseObserver = StreamRecorder.create();

    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(this.loadingPlanAlgoService)
        .saveAlgoLoadingPlanStatus(any(LoadableStudy.AlgoStatusRequest.class));

    loadingInformationGrpcService.saveAlgoLoadingPlanStatus(request, responseObserver);
    List<LoadableStudy.AlgoStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateLoadingPlan() throws Exception {
    LoadingPlanModels.LoadingInfoAlgoRequest request =
        LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoAlgoReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanAlgoService)
        .generateLoadingPlan(
            any(LoadingPlanModels.LoadingInfoAlgoRequest.class),
            any(LoadingPlanModels.LoadingInfoAlgoReply.Builder.class));

    loadingInformationGrpcService.generateLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoAlgoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateLoadingPlanWithException() throws Exception {
    LoadingPlanModels.LoadingInfoAlgoRequest request =
        LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoAlgoReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .doNothing()
        .when(this.loadingPlanAlgoService)
        .generateLoadingPlan(
            any(LoadingPlanModels.LoadingInfoAlgoRequest.class),
            any(LoadingPlanModels.LoadingInfoAlgoReply.Builder.class));

    loadingInformationGrpcService.generateLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoAlgoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateLoadingPlanWithGenericException() throws Exception {
    LoadingPlanModels.LoadingInfoAlgoRequest request =
        LoadingPlanModels.LoadingInfoAlgoRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoAlgoReply> responseObserver =
        StreamRecorder.create();

    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(this.loadingPlanAlgoService)
        .generateLoadingPlan(
            any(LoadingPlanModels.LoadingInfoAlgoRequest.class),
            any(LoadingPlanModels.LoadingInfoAlgoReply.Builder.class));

    loadingInformationGrpcService.generateLoadingPlan(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoAlgoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoAlgoStatus() throws Exception {
    LoadingPlanModels.LoadingInfoStatusRequest request =
        LoadingPlanModels.LoadingInfoStatusRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoStatusReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanAlgoService)
        .getLoadingInfoAlgoStatus(
            any(LoadingPlanModels.LoadingInfoStatusRequest.class),
            any(LoadingPlanModels.LoadingInfoStatusReply.Builder.class));

    loadingInformationGrpcService.getLoadingInfoAlgoStatus(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoAlgoStatusWithException() throws Exception {
    LoadingPlanModels.LoadingInfoStatusRequest request =
        LoadingPlanModels.LoadingInfoStatusRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoStatusReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanAlgoService)
        .getLoadingInfoAlgoStatus(
            any(LoadingPlanModels.LoadingInfoStatusRequest.class),
            any(LoadingPlanModels.LoadingInfoStatusReply.Builder.class));

    loadingInformationGrpcService.getLoadingInfoAlgoStatus(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoAlgoStatusWithGenericException() throws Exception {
    LoadingPlanModels.LoadingInfoStatusRequest request =
        LoadingPlanModels.LoadingInfoStatusRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.LoadingInfoStatusReply> responseObserver =
        StreamRecorder.create();

    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(this.loadingPlanAlgoService)
        .getLoadingInfoAlgoStatus(
            any(LoadingPlanModels.LoadingInfoStatusRequest.class),
            any(LoadingPlanModels.LoadingInfoStatusReply.Builder.class));

    loadingInformationGrpcService.getLoadingInfoAlgoStatus(request, responseObserver);
    List<LoadingPlanModels.LoadingInfoStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoAlgoErrors() throws Exception {
    LoadableStudy.AlgoErrorRequest request = LoadableStudy.AlgoErrorRequest.newBuilder().build();
    StreamRecorder<LoadableStudy.AlgoErrorReply> responseObserver = StreamRecorder.create();

    doNothing()
        .when(this.loadingPlanAlgoService)
        .getLoadingInfoAlgoErrors(
            any(LoadableStudy.AlgoErrorRequest.class),
            any(LoadableStudy.AlgoErrorReply.Builder.class));

    loadingInformationGrpcService.getLoadingInfoAlgoErrors(request, responseObserver);
    List<LoadableStudy.AlgoErrorReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoAlgoErrorsWithException() throws Exception {
    LoadableStudy.AlgoErrorRequest request = LoadableStudy.AlgoErrorRequest.newBuilder().build();
    StreamRecorder<LoadableStudy.AlgoErrorReply> responseObserver = StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingPlanAlgoService)
        .getLoadingInfoAlgoErrors(
            any(LoadableStudy.AlgoErrorRequest.class),
            any(LoadableStudy.AlgoErrorReply.Builder.class));

    loadingInformationGrpcService.getLoadingInfoAlgoErrors(request, responseObserver);
    List<LoadableStudy.AlgoErrorReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingInfoAlgoErrorsWithGenericException() throws Exception {
    LoadableStudy.AlgoErrorRequest request = LoadableStudy.AlgoErrorRequest.newBuilder().build();
    StreamRecorder<LoadableStudy.AlgoErrorReply> responseObserver = StreamRecorder.create();

    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(this.loadingPlanAlgoService)
        .getLoadingInfoAlgoErrors(
            any(LoadableStudy.AlgoErrorRequest.class),
            any(LoadableStudy.AlgoErrorReply.Builder.class));

    loadingInformationGrpcService.getLoadingInfoAlgoErrors(request, responseObserver);
    List<LoadableStudy.AlgoErrorReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUploadPortTideDetails() throws Exception {
    LoadingPlanModels.UploadTideDetailRequest request =
        LoadingPlanModels.UploadTideDetailRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UploadTideDetailStatusReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingInformationService)
        .uploadPortTideDetails(any(LoadingPlanModels.UploadTideDetailRequest.class));

    loadingInformationGrpcService.uploadPortTideDetails(request, responseObserver);
    List<LoadingPlanModels.UploadTideDetailStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUploadPortTideDetailsWithException() throws Exception {
    LoadingPlanModels.UploadTideDetailRequest request =
        LoadingPlanModels.UploadTideDetailRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UploadTideDetailStatusReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingInformationService)
        .uploadPortTideDetails(any(LoadingPlanModels.UploadTideDetailRequest.class));

    loadingInformationGrpcService.uploadPortTideDetails(request, responseObserver);
    List<LoadingPlanModels.UploadTideDetailStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUploadPortTideDetailsWithGenericException() throws Exception {
    LoadingPlanModels.UploadTideDetailRequest request =
        LoadingPlanModels.UploadTideDetailRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.UploadTideDetailStatusReply> responseObserver =
        StreamRecorder.create();

    doThrow(new GenericServiceException("1", "1", HttpStatusCode.MULTI_STATUS))
        .when(this.loadingInformationService)
        .uploadPortTideDetails(any(LoadingPlanModels.UploadTideDetailRequest.class));

    loadingInformationGrpcService.uploadPortTideDetails(request, responseObserver);
    List<LoadingPlanModels.UploadTideDetailStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDownloadPortTideDetails() throws Exception {
    LoadingPlanModels.DownloadTideDetailRequest request =
        LoadingPlanModels.DownloadTideDetailRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.DownloadTideDetailStatusReply> responseObserver =
        StreamRecorder.create();

    doNothing()
        .when(this.loadingInformationService)
        .downloadPortTideDetails(
            any(XSSFWorkbook.class),
            any(LoadingPlanModels.DownloadTideDetailRequest.class),
            any(LoadingPlanModels.DownloadTideDetailStatusReply.Builder.class));

    loadingInformationGrpcService.downloadPortTideDetails(request, responseObserver);
    List<LoadingPlanModels.DownloadTideDetailStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testDownloadPortTideDetailsWithException() throws Exception {
    LoadingPlanModels.DownloadTideDetailRequest request =
        LoadingPlanModels.DownloadTideDetailRequest.newBuilder().build();
    StreamRecorder<LoadingPlanModels.DownloadTideDetailStatusReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(this.loadingInformationService)
        .downloadPortTideDetails(
            any(XSSFWorkbook.class),
            any(LoadingPlanModels.DownloadTideDetailRequest.class),
            any(LoadingPlanModels.DownloadTideDetailStatusReply.Builder.class));

    loadingInformationGrpcService.downloadPortTideDetails(request, responseObserver);
    List<LoadingPlanModels.DownloadTideDetailStatusReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private LoadingInformation getLoadingInfoEntity() {
    com.cpdss.loadingplan.entity.LoadingInformation response = new LoadingInformation();
    response.setId(1l);
    response.setPortRotationXId(1l);
    response.setSynopticalTableXId(1l);
    response.setVesselXId(1l);
    response.setVoyageId(1l);
    return response;
  }
}
