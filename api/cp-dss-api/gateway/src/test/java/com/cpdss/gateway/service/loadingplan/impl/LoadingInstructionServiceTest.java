/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsStatus;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsUpdateRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingInstructionService.class})
public class LoadingInstructionServiceTest {

  @Autowired LoadingInstructionService loadingInstructionService;

  @MockBean
  public LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub
      loadingInstructionServiceBlockingStub;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testGetLoadingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Mockito.when(loadingInstructionServiceBlockingStub.getLoadingInstructions(Mockito.any()))
        .thenReturn(getLIR());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.getLoadingInstructions(vesselId, infoId, portRotationId);
      assertEquals(1L, response.getLoadingInstructionGroupList().get(0).getGroupId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInstructionDetails getLIR() {
    List<LoadingPlanModels.LoadingInstructions> list2 = new ArrayList<>();
    LoadingPlanModels.LoadingInstructions instructions =
        LoadingPlanModels.LoadingInstructions.newBuilder()
            .setInstructionTypeId(1L)
            .setInstructionHeaderId(1L)
            .setInstructionId(1L)
            .setInstruction("1")
            .setIsChecked(true)
            .setIsEditable(true)
            .build();
    list2.add(instructions);
    List<LoadingPlanModels.LoadingInstructionSubHeader> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingInstructionSubHeader header =
        LoadingPlanModels.LoadingInstructionSubHeader.newBuilder()
            .setInstructionHeaderId(1L)
            .setInstructionTypeId(1L)
            .setSubHeaderId(1L)
            .setSubHeaderName("1")
            .setIsChecked(true)
            .setIsHeaderInstruction(true)
            .setIsEditable(true)
            .addAllLoadingInstructionsList(list2)
            .build();
    list1.add(header);
    List<LoadingPlanModels.LoadingInstructionGroup> list = new ArrayList<>();
    LoadingPlanModels.LoadingInstructionGroup group =
        LoadingPlanModels.LoadingInstructionGroup.newBuilder()
            .setGroupId(1L)
            .setGroupName("1")
            .build();
    list.add(group);
    LoadingPlanModels.LoadingInstructionDetails response =
        LoadingPlanModels.LoadingInstructionDetails.newBuilder()
            .addAllLoadingInstructionSubHeader(list1)
            .addAllLoadingInstructionGroupList(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testGetLoadingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Mockito.when(loadingInstructionServiceBlockingStub.getLoadingInstructions(Mockito.any()))
        .thenReturn(getLIRNS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.getLoadingInstructions(vesselId, infoId, portRotationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInstructionDetails getLIRNS() {
    List<LoadingPlanModels.LoadingInstructions> list2 = new ArrayList<>();
    LoadingPlanModels.LoadingInstructions instructions =
        LoadingPlanModels.LoadingInstructions.newBuilder()
            .setInstructionTypeId(1L)
            .setInstructionHeaderId(1L)
            .setInstructionId(1L)
            .setInstruction("1")
            .setIsChecked(true)
            .setIsEditable(true)
            .build();
    list2.add(instructions);
    List<LoadingPlanModels.LoadingInstructionSubHeader> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingInstructionSubHeader header =
        LoadingPlanModels.LoadingInstructionSubHeader.newBuilder()
            .setInstructionHeaderId(1L)
            .setInstructionTypeId(1L)
            .setSubHeaderId(1L)
            .setSubHeaderName("1")
            .setIsChecked(true)
            .setIsHeaderInstruction(true)
            .setIsEditable(true)
            .addAllLoadingInstructionsList(list2)
            .build();
    list1.add(header);
    List<LoadingPlanModels.LoadingInstructionGroup> list = new ArrayList<>();
    LoadingPlanModels.LoadingInstructionGroup group =
        LoadingPlanModels.LoadingInstructionGroup.newBuilder()
            .setGroupId(1L)
            .setGroupName("1")
            .build();
    list.add(group);
    LoadingPlanModels.LoadingInstructionDetails response =
        LoadingPlanModels.LoadingInstructionDetails.newBuilder()
            .addAllLoadingInstructionSubHeader(list1)
            .addAllLoadingInstructionGroupList(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return response;
  }

  @Test
  void testAddLoadingInstruction() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsSaveRequest request = new LoadingInstructionsSaveRequest();
    request.setInstructionHeaderId(1L);
    request.setInstruction("1");
    request.setInstructionTypeId(1L);
    request.setIsChecked(true);
    request.setIsSingleHeader(true);
    request.setIsSubHeader(true);
    request.setSubHeaderId(1L);
    Mockito.when(this.loadingInstructionServiceBlockingStub.addLoadingInstruction(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.addLoadingInstruction(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Common.ResponseStatus getRS() {
    Common.ResponseStatus responseStatus =
        Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build();
    return responseStatus;
  }

  @Test
  void testAddLoadingInstructionException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsSaveRequest request = new LoadingInstructionsSaveRequest();
    request.setInstructionHeaderId(1L);
    request.setInstruction("1");
    request.setInstructionTypeId(1L);
    request.setIsChecked(true);
    request.setIsSingleHeader(true);
    request.setIsSubHeader(true);
    request.setSubHeaderId(1L);
    Mockito.when(this.loadingInstructionServiceBlockingStub.addLoadingInstruction(Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.addLoadingInstruction(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Common.ResponseStatus getRSNS() {
    Common.ResponseStatus responseStatus =
        Common.ResponseStatus.newBuilder().setStatus(FAILED).build();
    return responseStatus;
  }

  @Test
  void testUpdateLoadingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsUpdateRequest request = new LoadingInstructionsUpdateRequest();
    request.setInstructionList(getLLIS());
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.updateLoadingInstructions(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.updateLoadingInstructions(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<LoadingInstructionsStatus> getLLIS() {
    List<LoadingInstructionsStatus> list = new ArrayList<>();
    LoadingInstructionsStatus status = new LoadingInstructionsStatus();
    status.setInstructionId(1L);
    status.setIsChecked(true);
    list.add(status);
    return list;
  }

  @Test
  void testUpdateLoadingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsUpdateRequest request = new LoadingInstructionsUpdateRequest();
    request.setInstructionList(getLLIS());
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.updateLoadingInstructions(Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.updateLoadingInstructions(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDeleteLoadingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.deleteLoadingInstructions(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.deleteLoadingInstructions(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDeleteLoadingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.deleteLoadingInstructions(Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.deleteLoadingInstructions(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testEditLoadingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    request.setInstruction("1");
    Mockito.when(this.loadingInstructionServiceBlockingStub.editInstructions(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.editLoadingInstructions(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testEditLoadingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    request.setInstruction("1");
    Mockito.when(this.loadingInstructionServiceBlockingStub.editInstructions(Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        loadingInstructionService,
        "loadingInstructionServiceBlockingStub",
        this.loadingInstructionServiceBlockingStub);
    try {
      var response =
          this.loadingInstructionService.editLoadingInstructions(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
