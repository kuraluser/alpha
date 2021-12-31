/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsStatus;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsUpdateRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {DischargingInstructionService.class})
public class DischargingInstructionServiceTest {

  @Autowired DischargingInstructionService dischargingInstructionService;

  @MockBean
  private DischargingInstructionServiceGrpc.DischargingInstructionServiceBlockingStub
      dischargingInstructionServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testAddDischargingInstruction() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsSaveRequest request = new DischargingInstructionsSaveRequest();
    request.setInstructionHeaderId(1L);
    request.setInstruction("1");
    request.setInstructionTypeId(1L);
    request.setIsChecked(true);
    request.setIsSingleHeader(true);
    request.setIsSubHeader(true);
    request.setSubHeaderId(1L);
    Mockito.when(
            this.dischargingInstructionServiceBlockingStub.addDischargingInstruction(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.addDischargingInstruction(
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
  void testAddDischargingInstructionException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsSaveRequest request = new DischargingInstructionsSaveRequest();
    request.setInstructionHeaderId(1L);
    request.setInstruction("1");
    request.setInstructionTypeId(1L);
    request.setIsChecked(true);
    request.setIsSingleHeader(true);
    request.setIsSubHeader(true);
    request.setSubHeaderId(1L);
    Mockito.when(
            this.dischargingInstructionServiceBlockingStub.addDischargingInstruction(Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.addDischargingInstruction(
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
  void testUpdateDischargingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsUpdateRequest request = new DischargingInstructionsUpdateRequest();
    request.setInstructionList(getLDIS());
    Mockito.when(
            this.dischargingInstructionServiceBlockingStub.updateDischargingInstructions(
                Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.updateDischargingInstructions(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<DischargingInstructionsStatus> getLDIS() {
    List<DischargingInstructionsStatus> list = new ArrayList<>();
    DischargingInstructionsStatus dischargingInstructionsStatus =
        new DischargingInstructionsStatus();
    dischargingInstructionsStatus.setInstructionId(1L);
    dischargingInstructionsStatus.setIsChecked(true);
    list.add(dischargingInstructionsStatus);
    return list;
  }

  @Test
  void testUpdateDischargingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsUpdateRequest request = new DischargingInstructionsUpdateRequest();
    request.setInstructionList(getLDIS());
    Mockito.when(
            this.dischargingInstructionServiceBlockingStub.updateDischargingInstructions(
                Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.updateDischargingInstructions(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testEditDischargingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsStatus request = new DischargingInstructionsStatus();
    request.setInstructionId(1L);
    request.setInstruction("1");
    Mockito.when(this.dischargingInstructionServiceBlockingStub.editInstructions(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.editDischargingInstructions(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testEditDischargingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsStatus request = new DischargingInstructionsStatus();
    request.setInstructionId(1L);
    request.setInstruction("1");
    Mockito.when(this.dischargingInstructionServiceBlockingStub.editInstructions(Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.editDischargingInstructions(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetDischargingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Mockito.when(
            dischargingInstructionServiceBlockingStub.getDischargingInstructions(Mockito.any()))
        .thenReturn(getDID());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.getDischargingInstructions(
              vesselId, infoId, portRotationId);
      assertEquals(
          1L, response.getDischargingInstructionSubHeader().get(0).getInstructionHeaderId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargingInstructionDetails getDID() {
    List<DischargingInstructions> list2 = new ArrayList<>();
    DischargingInstructions dischargingInstructions =
        DischargingInstructions.newBuilder()
            .setInstructionTypeId(1L)
            .setInstructionHeaderId(1L)
            .setInstruction("1")
            .setInstructionId(1L)
            .setIsChecked(true)
            .setIsEditable(true)
            .build();
    list2.add(dischargingInstructions);
    List<DischargingInstructionSubHeader> list1 = new ArrayList<>();
    DischargingInstructionSubHeader header =
        DischargingInstructionSubHeader.newBuilder()
            .setInstructionTypeId(1L)
            .setInstructionHeaderId(1L)
            .setSubHeaderId(1L)
            .setSubHeaderName("1")
            .setIsChecked(true)
            .setIsHeaderInstruction(true)
            .setIsEditable(true)
            .addAllDischargingInstructionsList(list2)
            .build();
    list1.add(header);
    List<DischargingInstructionGroup> list = new ArrayList<>();
    DischargingInstructionGroup group =
        DischargingInstructionGroup.newBuilder().setGroupId(1L).setGroupName("1").build();
    list.add(group);
    DischargingInstructionDetails dischargingInstructionDetails =
        DischargingInstructionDetails.newBuilder()
            .addAllDischargingInstructionSubHeader(list1)
            .addAllDischargingInstructionGroupList(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return dischargingInstructionDetails;
  }

  @Test
  void testGetDischargingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    Mockito.when(
            dischargingInstructionServiceBlockingStub.getDischargingInstructions(Mockito.any()))
        .thenReturn(getDIDNS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.getDischargingInstructions(
              vesselId, infoId, portRotationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargingInstructionDetails getDIDNS() {
    List<DischargingInstructions> list2 = new ArrayList<>();
    DischargingInstructions dischargingInstructions =
        DischargingInstructions.newBuilder()
            .setInstructionTypeId(1L)
            .setInstructionHeaderId(1L)
            .setInstruction("1")
            .setInstructionId(1L)
            .setIsChecked(true)
            .setIsEditable(true)
            .build();
    list2.add(dischargingInstructions);
    List<DischargingInstructionSubHeader> list1 = new ArrayList<>();
    DischargingInstructionSubHeader header =
        DischargingInstructionSubHeader.newBuilder()
            .setInstructionTypeId(1L)
            .setInstructionHeaderId(1L)
            .setSubHeaderId(1L)
            .setSubHeaderName("1")
            .setIsChecked(true)
            .setIsHeaderInstruction(true)
            .setIsEditable(true)
            .addAllDischargingInstructionsList(list2)
            .build();
    list1.add(header);
    List<DischargingInstructionGroup> list = new ArrayList<>();
    DischargingInstructionGroup group =
        DischargingInstructionGroup.newBuilder().setGroupId(1L).setGroupName("1").build();
    list.add(group);
    DischargingInstructionDetails dischargingInstructionDetails =
        DischargingInstructionDetails.newBuilder()
            .addAllDischargingInstructionSubHeader(list1)
            .addAllDischargingInstructionGroupList(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return dischargingInstructionDetails;
  }

  @Test
  void testDeleteDischargingInstructions() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsStatus request = new DischargingInstructionsStatus();
    request.setInstructionId(1L);
    Mockito.when(
            this.dischargingInstructionServiceBlockingStub.deleteDischargingInstructions(
                Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.deleteDischargingInstructions(
              vesselId, infoId, portRotationId, request);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDeleteDischargingInstructionsException() {
    Long vesselId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    DischargingInstructionsStatus request = new DischargingInstructionsStatus();
    request.setInstructionId(1L);
    Mockito.when(
            this.dischargingInstructionServiceBlockingStub.deleteDischargingInstructions(
                Mockito.any()))
        .thenReturn(getRSNS());
    ReflectionTestUtils.setField(
        dischargingInstructionService,
        "dischargingInstructionServiceBlockingStub",
        this.dischargingInstructionServiceBlockingStub);
    try {
      var response =
          this.dischargingInstructionService.deleteDischargingInstructions(
              vesselId, infoId, portRotationId, request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
