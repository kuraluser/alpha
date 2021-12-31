/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.*;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingPlanCommunicationService.class})
public class LoadingPlanCommunicationServiceTest {

  @Autowired LoadingPlanCommunicationService loadingPlanCommunicationService;

  @MockBean private LoadingPlanStagingService loadingPlanStagingService;

  @MockBean private ReasonForDelayRepository reasonForDelayRepository;

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

  @MockBean private LoadingDelayReasonRepository loadingDelayReasonRepository;

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
  @MockBean private LoadingInformationAlgoStatusRepository loadingInformationAlgoStatusRepository;
  @MockBean private BallastOperationRepository ballastOperationRepository;
  @MockBean private EductionOperationRepository eductionOperationRepository;
  @MockBean private CargoLoadingRateRepository cargoLoadingRateRepository;
  @MockBean private PortTideDetailsRepository portTideDetailsRepository;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private LoadingInstructionRepository loadingInstructionRepository;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  //    @Test
  //    void testPassRequestPayloadToEnvoyWriter() {
  //        String requestJson = "1";
  //        Long vesselId = 1L;
  //        String messageType = "1";
  //        Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
  //                .thenReturn(getVIR());
  //
  // Mockito.when(this.envoyWriterService.getCommunicationServer(Mockito.any())).thenReturn(getWR());
  //        ReflectionTestUtils.setField(
  //                loadingPlanCommunicationService, "vesselInfoGrpcService",
  // this.vesselInfoGrpcService);
  //        ReflectionTestUtils.setField(
  //                loadingPlanCommunicationService, "envoyWriterService", this.envoyWriterService);
  //        try {
  //            var response =
  //                    this.loadingPlanCommunicationService.passRequestPayloadToEnvoyWriter(
  //                            requestJson, vesselId, messageType);
  //            assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }
  //
  //    private com.cpdss.common.generated.EnvoyWriter.WriterReply getWR() {
  //        com.cpdss.common.generated.EnvoyWriter.WriterReply reply =
  //                com.cpdss.common.generated.EnvoyWriter.WriterReply.newBuilder()
  //
  // .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //                        .build();
  //        return reply;
  //    }
  //
  //    private VesselInfo.VesselIdResponse getVIR() {
  //        VesselInfo.VesselIdResponse response =
  //                VesselInfo.VesselIdResponse.newBuilder()
  //                        .setVesselDetail(
  //
  // VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
  //
  // .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //                        .build();
  //        return response;
  //    }
  //
  //    @Test
  //    void testPassRequestPayloadToEnvoyWriterException() {
  //        String requestJson = "1";
  //        Long vesselId = 1L;
  //        String messageType = "1";
  //        Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
  //                .thenReturn(getVIRNS());
  //
  // Mockito.when(this.envoyWriterService.getCommunicationServer(Mockito.any())).thenReturn(getWR());
  //        ReflectionTestUtils.setField(
  //                loadingPlanCommunicationService, "vesselInfoGrpcService",
  // this.vesselInfoGrpcService);
  //        ReflectionTestUtils.setField(
  //                loadingPlanCommunicationService, "envoyWriterService", this.envoyWriterService);
  //        try {
  //            var response =
  //                    this.loadingPlanCommunicationService.passRequestPayloadToEnvoyWriter(
  //                            requestJson, vesselId, messageType);
  //            assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }
  //
  //    private VesselInfo.VesselIdResponse getVIRNS() {
  //        VesselInfo.VesselIdResponse response =
  //                VesselInfo.VesselIdResponse.newBuilder()
  //                        .setVesselDetail(
  //
  // VesselInfo.VesselDetail.newBuilder().setName("1").setImoNumber("1").build())
  //
  // .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
  //                        .build();
  //        return response;
  //    }
  //
  //    //    @Test
  //    //     void testGetDataFromCommunication() {
  //    //        Map<String, String> taskReqParams = new HashMap<>();
  //    //        EnumSet<MessageTypes > messageTypesEnum = new HashMap<
  //    //    }
  //
  //    //    @Test
  //    //     void testGetLoadingPlanStagingData() {
  //    //        String status = "retry";
  //    //        String env = "1";
  //    //        String taskName = "1";
  //    //        try {
  //    //
  //    //
  // Mockito.when(loadingPlanStagingService.getAllWithStatus(Mockito.any())).thenReturn(getLDTS());
  //    //
  //    //
  // Mockito.doNothing().when(loadingPlanStagingService).updateStatusForProcessId(Mockito.any(),Mockito.any());
  //    //
  //    //
  // Mockito.when(loadingPlanStagingService.getAttributeMapping(Mockito.any())).thenReturn(getHSS());
  //    //
  //    //
  // Mockito.when(loadingPlanStagingService.getAsEntityJson(Mockito.any(),Mockito.any())).thenReturn(getJA());
  //    ////
  //    //
  // Mockito.doNothing().when(loadingPlanCommunicationService).getStagingData(Mockito.anyList(),Mockito.anyString(),Mockito.anyString());
  //    //
  // this.loadingPlanCommunicationService.getLoadingPlanStagingData(status,env,taskName);
  //    //        } catch (GenericServiceException e) {
  //    //            e.printStackTrace();
  //    //        }
  //    //    }
  //
  //    private JsonArray getJA() {
  //        JsonArray jsonElements = new JsonArray();
  //        jsonElements.add("1");
  //        return jsonElements;
  //    }
  //
  //    private HashMap<String, String> getHSS() {
  //        HashMap<String, String> map = new HashMap<>();
  //        map.put("1", "1");
  //        return map;
  //    }
  //
  //    private List<DataTransferStage> getLDTS() {
  //        List<DataTransferStage> list = new ArrayList<>();
  //        DataTransferStage dataTransferStage = new DataTransferStage();
  //        dataTransferStage.setData("[{\\\"loadingInfoId\\\":1}\"]");
  //        dataTransferStage.setId(1L);
  //        dataTransferStage.setProcessGroupId("LoadingPlan_Save");
  //        dataTransferStage.setProcessIdentifier("loading_information");
  //        dataTransferStage.setProcessId("1");
  //        list.add(dataTransferStage);
  //        return list;
  //    }
  //
  //    @Test
  //    void testSaveActivatedVoyage() {
  //        LoadableStudy.VoyageActivateRequest grpcRequest =
  //                LoadableStudy.VoyageActivateRequest.newBuilder().build();
  //        Mockito.when(this.loadableStudyServiceBlockingStub.saveActivatedVoyage(Mockito.any()))
  //                .thenReturn(getVAR());
  //        ReflectionTestUtils.setField(
  //                loadingPlanCommunicationService,
  //                "loadableStudyServiceBlockingStub",
  //                this.loadableStudyServiceBlockingStub);
  //        var response = this.loadingPlanCommunicationService.saveActivatedVoyage(grpcRequest);
  //        assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  //    }
  //
  //    private com.cpdss.common.generated.LoadableStudy.VoyageActivateReply getVAR() {
  //        com.cpdss.common.generated.LoadableStudy.VoyageActivateReply reply =
  //                com.cpdss.common.generated.LoadableStudy.VoyageActivateReply.newBuilder()
  //
  // .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //                        .build();
  //        return reply;
  //    }
}
