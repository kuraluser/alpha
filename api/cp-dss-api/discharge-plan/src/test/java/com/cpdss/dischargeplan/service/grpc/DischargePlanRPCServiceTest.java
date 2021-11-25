/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.*;
import com.cpdss.dischargeplan.service.loadicator.LoadicatorService;
import com.cpdss.dischargeplan.service.loadicator.UllageUpdateLoadicatorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

@SpringJUnitConfig(classes = {DischargePlanRPCService.class})
public class DischargePlanRPCServiceTest {

  @Autowired DischargePlanRPCService dischargePlanRPCService;

  @MockBean DischargePlanSynchronizeService dischargePlanSynchronizeService;

  @MockBean DischargePlanAlgoService dischargePlanAlgoService;
  @MockBean PortDischargingPlanBallastDetailsRepository pdpBallastDetailsRepository;
  @MockBean PortDischargingPlanStowageDetailsRepository pdpStowageDetailsRepository;
  @MockBean PortDischargingPlanRobDetailsRepository pdpRobDetailsRepository;
  @MockBean PortDischargingPlanStabilityParametersRepository pdpStabilityParametersRepository;
  @MockBean BillOfLaddingRepository billOfLaddingRepo;
  @MockBean DischargeInformationService dischargeInformationService;

  @MockBean
  PortDischargingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @MockBean
  PortDischargingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @MockBean DischargePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @MockBean DischargeInfoStatusCheckService dischargeInfoStatusCheckService;

  @MockBean
  private PortDischargingPlanStowageDetailsRepository portDischargingPlanStowageDetailsRepository;

  @MockBean private DischargeInformationBuilderService dischargeInformationBuilderService;

  @MockBean RestTemplate restTemplate;
  @MockBean DischargeInformationRepository dischargeInformationRepository;
  @MockBean DischargingSequenceService dischargeSequenceService;

  @MockBean LoadicatorService loadicatorService;

  @MockBean DischargeCargoHistoryService cargoHistoryService;

  @MockBean private DischargeInformationStatusRepository dischargeInformationStatusRepository;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  //  @Test
  //  void testDischargePlanSynchronization() {
  //    List<PortData> list = new ArrayList<>();
  //    PortData portData =
  //
  // PortData.newBuilder().setPortRotationId(1L).setSynopticTableId(1L).setPortId(1L).build();
  //    list.add(portData);
  //    DischargeStudyDataTransferRequest request =
  //        DischargeStudyDataTransferRequest.newBuilder()
  //            .addAllPortData(list)
  //            .setDischargeProcessId("1")
  //            .setVoyageId(1L)
  //            .setVesselId(1L)
  //            .setDischargePatternId(1L)
  //            .build();
  //    StreamObserver<Common.ResponseStatus> responseObserver = StreamRecorder.create();
  //    Mockito.when(
  //            dischargeInformationStatusRepository.findByIdAndIsActive(
  //                Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getODIS());
  //
  // Mockito.when(dischargeInformationRepository.saveAll(Mockito.anyList())).thenReturn(getLDI());
  //    Mockito.when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(Mockito.any()))
  //        .thenReturn(getVRR());
  //
  //    ReflectionTestUtils.setField(
  //        dischargePlanAlgoService,
  //        "dischargeInformationStatusRepository",
  //        this.dischargeInformationStatusRepository);
  //    ReflectionTestUtils.setField(
  //        dischargePlanSynchronizeService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
  //  }

  private Optional<DischargingInformationStatus> getODIS() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    dischargingInformationStatus.setId(1L);
    return Optional.of(dischargingInformationStatus);
  }

  private List<DischargeInformation> getLDI() {
    List<DischargeInformation> list = new ArrayList<>();
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setVesselXid(1L);

    list.add(dischargeInformation);
    return list;
  }

  private VesselInfo.VesselRuleReply getVRR() {
    List<Common.RulePlans> list = new ArrayList<>();
    Common.RulePlans rulePlans = Common.RulePlans.newBuilder().build();
    list.add(rulePlans);
    VesselInfo.VesselRuleReply vesselRuleReply =
        VesselInfo.VesselRuleReply.newBuilder()
            .addAllRulePlan(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return vesselRuleReply;
  }
}
