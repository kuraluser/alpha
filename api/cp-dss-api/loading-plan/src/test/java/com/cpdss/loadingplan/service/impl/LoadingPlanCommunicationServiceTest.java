/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.utility.LoadingPlanConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import java.util.*;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingPlanCommunicationService.class})
public class LoadingPlanCommunicationServiceTest {

  @Autowired LoadingPlanCommunicationService loadingPlanCommunicationService;
  @MockBean LoadingInformationAlgoStatusRepository loadingInformationAlgoStatusRepository;
  @MockBean EductionOperationRepository eductionOperationRepository;
  @MockBean CargoLoadingRateRepository cargoLoadingRateRepository;
  @MockBean BallastOperationRepository ballastOperationRepository;
  @MockBean private EntityManager entityManager;
  @MockBean private LoadingPlanStagingService loadingPlanStagingService;
  //  @MockBean private LoadingInformationRepository loadingInformationRepository;

  @MockBean
  private LoadingInformationCommunicationRepository loadingInformationCommunicationRepository;

  @MockBean private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @MockBean private LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @MockBean private LoadingDelayRepository loadingDelayRepository;
  @MockBean private LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @MockBean private LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean private UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @MockBean
  private PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @MockBean private PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @MockBean private LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @MockBean private LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @MockBean private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;

  @MockBean private PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;

  @MockBean
  private PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @MockBean private PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;

  @MockBean
  private PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @MockBean private LoadingSequenceRepository loadingSequenceRepository;
  @MockBean private LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;

  @MockBean
  private LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @MockBean
  private LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;

  @MockBean
  private PortLoadingPlanCommingleTempDetailsRepository
      portLoadingPlanCommingleTempDetailsRepository;

  @MockBean
  private PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @MockBean private BillOfLandingRepository billOfLandingRepository;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @MockBean private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @MockBean private StageOffsetRepository stageOffsetRepository;
  @MockBean private StageDurationRepository stageDurationRepository;
  @MockBean private LoadingInformationStatusRepository loadingInfoStatusRepository;
  @MockBean private PyUserRepository pyUserRepository;
  @MockBean private PortTideDetailsRepository portTideDetailsRepository;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private LoadingInstructionRepository loadingInstructionRepository;
  @MockBean private LoadingDelayReasonRepository loadingDelayReasonRepository;
  @MockBean private ReasonForDelayRepository reasonForDelayRepository;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  //    @Test
  //     void  testGetDataFromCommunication() {
  //        Map<String, String> taskReqParams
  //        EnumSet<MessageTypes > messageTypesEnum = new
  //    }

  @Test
  void testPassRequestPayloadToEnvoyWriter() {
    String requestJson = "1";
    Long vesselId = 1L;
    String messageType = "1";
    Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
        .thenReturn(getVIR());
    Mockito.when(this.envoyWriterService.getCommunicationServer(Mockito.any())).thenReturn(getWR());
    ReflectionTestUtils.setField(
        loadingPlanCommunicationService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        loadingPlanCommunicationService, "envoyWriterService", this.envoyWriterService);
    try {
      var reply =
          this.loadingPlanCommunicationService.passRequestPayloadToEnvoyWriter(
              requestJson, vesselId, messageType);
      assertEquals(SUCCESS, reply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselInfo.VesselIdResponse getVIR() {
    VesselInfo.VesselIdResponse response =
        VesselInfo.VesselIdResponse.newBuilder()
            .setVesselDetail(
                VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private EnvoyWriter.WriterReply getWR() {
    EnvoyWriter.WriterReply reply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetVesselDetailsForEnvoy() {
    Long vesselId = 1L;
    Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
        .thenReturn(getVIR());
    ReflectionTestUtils.setField(
        loadingPlanCommunicationService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      var reply = this.loadingPlanCommunicationService.getVesselDetailsForEnvoy(vesselId);
      assertEquals("1", reply.getName());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //     void testGetLoadingPlanStagingData() {
  //      String status = "in_progress";
  //      String env = "1";
  //      String taskName = "1";
  //
  // Mockito.when(loadingPlanStagingService.getAllWithStatusAndTime(Mockito.any(),Mockito.any())).thenReturn(getLDTS());
  //      try {
  //          this.loadingPlanCommunicationService.getLoadingPlanStagingData(status,env,taskName);
  //      } catch (GenericServiceException e) {
  //          e.printStackTrace();
  //      }
  //
  //  }

  private List<DataTransferStage> getLDTS() {
    List<DataTransferStage> list = new ArrayList<>();
    DataTransferStage stage = new DataTransferStage();
    stage.setData("1");
    stage.setProcessGroupId("LoadingPlan");
    stage.setProcessId("1");
    list.add(stage);
    return list;
  }
}
