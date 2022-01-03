/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.loadicator;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.SynopticalOperationServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.communication.DischargePlanStagingService;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import com.cpdss.dischargeplan.service.DischargePlanAlgoService;
import com.cpdss.dischargeplan.service.DischargePlanCommunicationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(classes = {UllageUpdateLoadicatorService.class})
public class UllageUpdateLoadicatorServiceTest {

  @Autowired UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @MockBean DischargeInformationRepository dischargeInformationRepository;

  @MockBean RestTemplate restTemplate;

  @MockBean DischargeInformationStatusRepository dischargeInformationStatusRepository;

  @MockBean DischargeInformationService dischargeInformationService;

  @MockBean AlgoErrorHeadingRepository algoErrorHeadingRepository;

  @MockBean PortDischargingPlanStowageDetailsRepository portDischargingPlanStowageDetailsRepository;

  @MockBean PortDischargingPlanBallastDetailsRepository portDischargingPlanBallastDetailsRepository;

  @MockBean PortDischargingPlanRobDetailsRepository portDischargingPlanRobDetailsRepository;

  @MockBean
  PortDischargingPlanStowageTempDetailsRepository portDischargingPlanStowageTempDetailsRepository;

  @MockBean
  PortDischargingPlanBallastTempDetailsRepository portDischargingPlanBallastDetailsTempRepository;

  @MockBean private DischargePlanStagingService dischargePlanStagingService;

  @MockBean
  private DischargePlanCommunicationStatusRepository dischargePlanCommunicationStatusRepository;

  @MockBean private DischargePlanCommunicationService dischargePlanCommunicationService;

  @MockBean DischargePlanAlgoService dischargingPlanAlgoService;
  @MockBean LoadicatorService loadicatorService;

  @MockBean
  SynopticalOperationServiceGrpc.SynopticalOperationServiceBlockingStub
      synopticalOperationServiceBlockingStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private PortDischargingPlanCommingleDetailsRepository
      portDischargingPlanCommingleDetailsRepository;

  @MockBean private AlgoErrorsRepository algoErrorsRepository;

  @MockBean
  private PortDischargingPlanCommingleTempDetailsRepository
      portDischargingPlanCommingleTempDetailsRepository;

  @MockBean
  private PortDischargingPlanStabilityParametersRepository
      portDischargingPlanStabilityParametersRepository;

  @Test
  void testSaveLoadicatorInfoForUllageUpdate() {
    List<LoadingPlanModels.UpdateUllage> list = new ArrayList<>();
    LoadingPlanModels.UpdateUllage updateUllage =
        LoadingPlanModels.UpdateUllage.newBuilder().setDischargingInfoId(1L).build();
    list.add(updateUllage);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllUpdateUllage(list).build();
    Mockito.when(dischargeInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getODI());
    Mockito.when(this.vesselInfoGrpcService.getVesselDetailByVesselId(Mockito.any()))
        .thenReturn(getVR());

    ReflectionTestUtils.setField(
        loadicatorService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setVesselXid(1L);
    dischargeInformation.setPortXid(1L);
    return Optional.of(dischargeInformation);
  }

  private VesselInfo.VesselReply getVR() {
    List<VesselInfo.VesselDetail> list = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder().setHasLoadicator(false).build();
    list.add(vesselDetail);
    VesselInfo.VesselReply reply =
        VesselInfo.VesselReply.newBuilder()
            .addAllVessels(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }
}
