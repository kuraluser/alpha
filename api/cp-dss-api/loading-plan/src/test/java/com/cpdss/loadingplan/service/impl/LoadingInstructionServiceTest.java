/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.FAILED;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionHeaderRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionTemplateRepository;
import io.grpc.internal.testing.StreamRecorder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingInstructionService.class})
public class LoadingInstructionServiceTest {

  @Autowired LoadingInstructionService loadingInstructionService;

  @MockBean LoadingInstructionRepository loadingInstructionRepository;

  @MockBean LoadingInstructionTemplateRepository loadingInstructionTemplateRepository;

  @MockBean LoadingInformationRepository loadingInformationRepository;

  @MockBean LoadingInstructionHeaderRepository loadingInstructionHeaderRepository;

  @MockBean LoadingInformationServiceImpl loadingInformationServiceImpl;

  @Test
  void testUpdateLoadingInstructions() {
    List<LoadingPlanModels.LoadingInstructionStatus> list = new ArrayList<>();
    LoadingPlanModels.LoadingInstructionStatus status =
        LoadingPlanModels.LoadingInstructionStatus.newBuilder()
            .setInstructionId(1L)
            .setIsChecked(true)
            .build();
    list.add(status);
    LoadingPlanModels.LoadingInstructionsUpdate request =
        LoadingPlanModels.LoadingInstructionsUpdate.newBuilder()
            .addAllInstructionList(list)
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    this.loadingInstructionService.updateLoadingInstructions(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
  }

  @Test
  void testUpdateLoadingInstructionsFailed() {
    List<LoadingPlanModels.LoadingInstructionStatus> list = new ArrayList<>();
    LoadingPlanModels.LoadingInstructionStatus status =
        LoadingPlanModels.LoadingInstructionStatus.newBuilder()
            .setInstructionId(1L)
            .setIsChecked(true)
            .build();
    list.add(status);
    LoadingPlanModels.LoadingInstructionsUpdate request =
        LoadingPlanModels.LoadingInstructionsUpdate.newBuilder()
            .addAllInstructionList(list)
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.doThrow(new RuntimeException("internal error"))
        .when(loadingInstructionRepository)
        .updateInstructionStatus(Mockito.anyLong(), Mockito.anyBoolean());

    this.loadingInstructionService.updateLoadingInstructions(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getStatus());
  }

  @Test
  void testDeleteLoadingInstructions() {
    LoadingPlanModels.LoadingInstructionStatus request =
        LoadingPlanModels.LoadingInstructionStatus.newBuilder().setInstructionId(1L).build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    this.loadingInstructionService.deleteLoadingInstructions(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
  }

  @Test
  void testDeleteLoadingInstructionsFailed() {
    LoadingPlanModels.LoadingInstructionStatus request =
        LoadingPlanModels.LoadingInstructionStatus.newBuilder().setInstructionId(1L).build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.doThrow(new RuntimeException("internal error"))
        .when(loadingInstructionRepository)
        .deleteInstruction(Mockito.anyLong());
    this.loadingInstructionService.deleteLoadingInstructions(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getStatus());
  }

  @Test
  void testEditInstructions() {
    LoadingPlanModels.LoadingInstructionStatus request =
        LoadingPlanModels.LoadingInstructionStatus.newBuilder()
            .setInstructionId(1L)
            .setInstruction("1")
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    this.loadingInstructionService.editInstructions(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
  }

  @Test
  void testEditInstructionsFailed() {
    LoadingPlanModels.LoadingInstructionStatus request =
        LoadingPlanModels.LoadingInstructionStatus.newBuilder()
            .setInstructionId(1L)
            .setInstruction("1")
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.doThrow(new RuntimeException("internal error"))
        .when(loadingInstructionRepository)
        .editInstruction(Mockito.anyLong(), Mockito.anyString());
    this.loadingInstructionService.editInstructions(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getStatus());
  }

  @Test
  void testAddLoadingInstruction1() {
    LoadingPlanModels.LoadingInstructionsSave request =
        LoadingPlanModels.LoadingInstructionsSave.newBuilder()
            .setIsSubHeader(true)
            .setInstruction("1")
            .setHeaderId(1L)
            .setIsChecked(true)
            .setIsSingleHeader(true)
            .setVesselId(1L)
            .setPortRotationId(1L)
            .setLoadingInfoId(1L)
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.when(
            loadingInformationServiceImpl.getLoadingInformation(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenReturn(getOI());
    Mockito.when(loadingInstructionRepository.save(Mockito.any())).thenReturn(getLI());
    this.loadingInstructionService.addLoadingInstruction(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
  }

  @Test
  void testAddLoadingInstruction2() {
    LoadingPlanModels.LoadingInstructionsSave request =
        LoadingPlanModels.LoadingInstructionsSave.newBuilder()
            .setIsSubHeader(false)
            .setInstruction("1")
            .setHeaderId(1L)
            .setIsChecked(true)
            .setIsSingleHeader(true)
            .setVesselId(1L)
            .setPortRotationId(1L)
            .setLoadingInfoId(1L)
            .build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.when(
            loadingInformationServiceImpl.getLoadingInformation(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenReturn(getOI());
    Mockito.when(loadingInstructionRepository.save(Mockito.any())).thenReturn(getLI());
    this.loadingInstructionService.addLoadingInstruction(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getStatus());
  }

  private Optional<LoadingInformation> getOI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    loadingInformation.setPortXId(1L);
    return Optional.of(loadingInformation);
  }

  private LoadingInstruction getLI() {
    LoadingInstruction loadingInstruction = new LoadingInstruction();
    loadingInstruction.setId(1L);
    return loadingInstruction;
  }

  @Test
  void testAddLoadingInstructionFailed() {
    LoadingPlanModels.LoadingInstructionsSave request =
        LoadingPlanModels.LoadingInstructionsSave.newBuilder().build();
    StreamRecorder<Common.ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.when(this.loadingInformationRepository.findById(Mockito.anyLong()))
        .thenThrow(new RuntimeException("internal error"));
    this.loadingInstructionService.addLoadingInstruction(request, responseObserver);
    List<Common.ResponseStatus> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getStatus());
  }

  /// doubt in logic

  //  @Test
  //     void testGetLoadingInstructions() {
  //        LoadingPlanModels.LoadingInstructionRequest request =
  // LoadingPlanModels.LoadingInstructionRequest.newBuilder().setLoadingInfoId(1L).setPortRotationId(1L)
  //                .setVesselId(1L).build();
  //      StreamRecorder<LoadingPlanModels.LoadingInstructionDetails> responseObserver =
  // StreamRecorder.create();
  //
  // Mockito.when(loadingInformationServiceImpl.getLoadingInformation(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong()))
  //              .thenReturn(getOI());
  //
  // Mockito.when(loadingInstructionHeaderRepository.findAllByIsActiveTrue()).thenReturn(getLLIH());
  //      Mockito.when(loadingInstructionRepository.findAny(Mockito.anyLong())).thenReturn(false);
  //
  // Mockito.when(loadingInstructionTemplateRepository.findALLByLoadingInsructionTypeIdAndReferenceId(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong()))
  //              .thenReturn(getLLIT());
  //
  // Mockito.when(loadingInstructionRepository.getAllLoadingInstructions(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong())).thenReturn(getLLI());
  //      this.loadingInstructionService.getLoadingInstructions(request,responseObserver);
  //      List<LoadingPlanModels.LoadingInstructionDetails> replies = responseObserver.getValues();
  //      assertEquals(1, replies.size());
  //      assertNull(responseObserver.getError());
  //      assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  //
  //  }

  private List<LoadingInstructionHeader> getLLIH() {
    List<LoadingInstructionHeader> list = new ArrayList<>();
    LoadingInstructionHeader header = new LoadingInstructionHeader();
    header.setId(1L);
    header.setHeaderName("1");
    list.add(header);
    return list;
  }

  private List<LoadingInstructionTemplate> getLLIT() {
    List<LoadingInstructionTemplate> list = new ArrayList<>();
    LoadingInstructionTemplate loadingInstructionTemplate = new LoadingInstructionTemplate();
    loadingInstructionTemplate.setLoading_instruction("1");
    loadingInstructionTemplate.setLoadingInsructionType(getLIT());
    loadingInstructionTemplate.setLoadingInstructionHeaderXId(getLLIH().get(0));
    loadingInstructionTemplate.setReferenceXId(1L);
    loadingInstructionTemplate.setIsActive(true);
    loadingInstructionTemplate.setId(1L);
    loadingInstructionTemplate.setIsHeaderInstruction(true);
    loadingInstructionTemplate.setParentInstructionXId(1L);
    list.add(loadingInstructionTemplate);
    return list;
  }

  private LoadingInstructionType getLIT() {
    LoadingInstructionType loadingInstructionType = new LoadingInstructionType();
    loadingInstructionType.setId(1L);
    return loadingInstructionType;
  }

  private List<LoadingInstruction> getLLI() {
    List<LoadingInstruction> list = new ArrayList<>();
    LoadingInstruction loadingInstruction = new LoadingInstruction();
    loadingInstruction.setId(1L);
    loadingInstruction.setCreatedDateTime(LocalDateTime.now());
    loadingInstruction.setLoadingInstruction("1");
    loadingInstruction.setIsChecked(true);
    loadingInstruction.setLoadingInstructionHeaderXId(1L);
    loadingInstruction.setLoadingTypeXId(1L);
    loadingInstruction.setIsHeaderInstruction(true);
    loadingInstruction.setLoadingInstructionTemplateXId(1L);
    loadingInstruction.setTemplateParentXId(1L);
    // loadingInstruction.setParentInstructionXId(1L);
    list.add(loadingInstruction);
    return list;
  }

  @Test
  void testGetLoadingInstructionsFailed() {
    LoadingPlanModels.LoadingInstructionRequest request =
        LoadingPlanModels.LoadingInstructionRequest.newBuilder()
            .setLoadingInfoId(1L)
            .setPortRotationId(1L)
            .setVesselId(1L)
            .build();
    StreamRecorder<LoadingPlanModels.LoadingInstructionDetails> responseObserver =
        StreamRecorder.create();
    Mockito.when(this.loadingInformationRepository.findById(Mockito.anyLong()))
        .thenThrow(new RuntimeException("internal error"));
    this.loadingInstructionService.getLoadingInstructions(request, responseObserver);
    List<LoadingPlanModels.LoadingInstructionDetails> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }
}
