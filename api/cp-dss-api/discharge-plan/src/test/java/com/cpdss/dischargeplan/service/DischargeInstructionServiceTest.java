/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingInstruction;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingInstructionHeaderRepository;
import com.cpdss.dischargeplan.repository.DischargingInstructionRepository;
import com.cpdss.dischargeplan.repository.DischargingInstructionTemplateRepository;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {DischargingInstructionService.class})
public class DischargeInstructionServiceTest {

  @Autowired DischargingInstructionService dischargingInstructionService;

  @MockBean DischargingInstructionRepository dischargingInstructionRepository;

  @MockBean DischargingInstructionTemplateRepository dischargingInstructionTemplateRepository;

  @MockBean DischargeInformationRepository dischargingInformationRepository;

  @MockBean DischargingInstructionHeaderRepository dischargingInstructionHeaderRepository;

  @MockBean DischargeInformationService dischargeInformationService;

  /** Add discharging Instructions */
  @Test
  void addInstructionTest() {
    Builder request = DischargingInstructionsSave.newBuilder();
    StreamRecorder<ResponseStatus> responseObserver = StreamRecorder.create();
    DischargingInstruction newInstruction = new DischargingInstruction();
    newInstruction.setId(1L);
    DischargeInformation info = new DischargeInformation();
    info.setPortXid(536L);
    Mockito.when(dischargeInformationService.getDischargeInformation(any(Long.class)))
        .thenReturn(info);
    Mockito.when(dischargingInstructionRepository.save(any(DischargingInstruction.class)))
        .thenReturn(newInstruction);
    dischargingInstructionService.addDischargingInstruction(request.build(), responseObserver);
    assertNull(responseObserver.getError());
    Mockito.verify(dischargeInformationService, Mockito.atLeastOnce())
        .getDischargeInformation(any(Long.class));
    Mockito.verify(dischargingInstructionRepository, Mockito.atLeastOnce())
        .save(any(DischargingInstruction.class));
  }

  /** Update discharging instructions */
  @Test
  void updateDischargingInstructionsTest() {
    com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus.Builder newBuilder =
        DischargingInstructionStatus.newBuilder();
    newBuilder.setInstruction("test");
    newBuilder.setInstructionId(1L);
    newBuilder.setIsChecked(false);
    com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate.Builder builder =
        DischargingInstructionsUpdate.newBuilder();
    builder.addInstructionList(newBuilder.build());
    StreamRecorder<ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.doNothing()
        .when(dischargingInstructionRepository)
        .updateInstructionStatus(any(Long.class), any(Boolean.class));
    dischargingInstructionService.updateDischargingInstructions(builder.build(), responseObserver);
    assertNull(responseObserver.getError());
    Mockito.verify(dischargingInstructionRepository, Mockito.atLeastOnce())
        .updateInstructionStatus(any(Long.class), anyBoolean());
  }

  /** edit discharging instructions */
  @Test
  void editDischargingInstructionsTest() {

    com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus.Builder builder =
        DischargingInstructionStatus.newBuilder();
    StreamRecorder<ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.doNothing()
        .when(dischargingInstructionRepository)
        .editInstruction(any(Long.class), any(String.class));
    dischargingInstructionService.editInstructions(builder.build(), responseObserver);
    assertNull(responseObserver.getError());
    Mockito.verify(dischargingInstructionRepository, Mockito.atLeastOnce())
        .editInstruction(any(Long.class), any(String.class));
  }

  /** delete discharging instructions */
  @Test
  void deleteDischargingInstructionsTest() {

    com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus.Builder builder =
        DischargingInstructionStatus.newBuilder();
    StreamRecorder<ResponseStatus> responseObserver = StreamRecorder.create();
    Mockito.doNothing().when(dischargingInstructionRepository).deleteInstruction(any(Long.class));
    dischargingInstructionService.deleteDischargingInstructions(builder.build(), responseObserver);
    assertNull(responseObserver.getError());
    Mockito.verify(dischargingInstructionRepository, Mockito.atLeastOnce())
        .deleteInstruction(any(Long.class));
  }
}
