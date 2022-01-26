/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.UllageReportFileParsingService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@SpringJUnitConfig(classes = {LoadingPlanServiceImpl.class})
public class LoadingPlanServiceImplTest {

  @Autowired LoadingPlanServiceImpl loadingPlanService;

  //    @MockBean
  //    LoadingInformationService loadingInformationService;

  @MockBean LoadingInformationServiceImpl loadingInformationServiceImpl;

  //    @MockBean
  //    LoadingPlanGrpcService loadingPlanGrpcService;

  @MockBean LoadingPlanGrpcServiceImpl loadingPlanGrpcServiceImpl;

  @MockBean UllageReportFileParsingService ullageReportFileParsingService;

  @MockBean GenerateLoadingPlanExcelReportService loadingPlanExcelReportService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @MockBean
  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub
      dischargePlanServiceBlockingStub;

  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  //    @MockBean
  //    LoadingPlanService loadingPlanService;

  @MockBean VesselInfoService vesselInfoService;

  @MockBean LoadingSequenceService loadingSequenceService;

  @MockBean LoadingPlanBuilderService loadingPlanBuilderService;

  @MockBean private LoadingInformationBuilderService loadingInformationBuilderService;

  //  @MockBean LoadingInformationService loadingInformationService;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  @Test
  void testGetLoadingPortRotationDetails() throws GenericServiceException {
    Long vesselId = 1L;
    Long portRId = 1L;
    Mockito.when(this.loadingPlanGrpcServiceImpl.getActiveVoyageDetails(Mockito.anyLong()))
        .thenReturn(getVR());
    var response = this.loadingPlanService.getLoadingPortRotationDetails(vesselId, portRId);
  }

  private VoyageResponse getVR() {
    VoyageResponse voyageResponse = new VoyageResponse();
    voyageResponse.setVoyageNumber("1");
    voyageResponse.setPortRotations(getLPR());
    voyageResponse.setId(1L);
    voyageResponse.setPatternId(1L);
    voyageResponse.setActualStartDate("1");
    voyageResponse.setActiveLs(getLS());
    voyageResponse.setActiveDs(getLS());
    voyageResponse.setDischargePatternId(1L);
    voyageResponse.setDischargePortRotations(getLPR());
    return voyageResponse;
  }

  private com.cpdss.gateway.domain.LoadableStudy getLS() {
    com.cpdss.gateway.domain.LoadableStudy loadableStudy =
        new com.cpdss.gateway.domain.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setName("1");
    return loadableStudy;
  }

  private List<PortRotation> getLPR() {
    List<PortRotation> list = new ArrayList<>();
    PortRotation portRotation = new PortRotation();
    portRotation.setId(1L);
    portRotation.setPortId(1L);
    portRotation.setPortOrder(1L);
    list.add(portRotation);
    return list;
  }

  @Test
  void testSaveLoadingInfoStatus() {
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setLoadingInfoStatusId(1L);
    request.setProcessId("1");
    String correlationId = "1";
    Mockito.when(loadingPlanGrpcServiceImpl.saveLoadingInfoStatus(Mockito.any()))
        .thenReturn(getASR());
    try {
      var reply = this.loadingPlanService.saveLoadingInfoStatus(request, correlationId);
      Assertions.assertEquals("200", reply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.AlgoStatusReply getASR() {
    LoadableStudy.AlgoStatusReply reply =
        LoadableStudy.AlgoStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private LoadableStudy.AlgoStatusReply getASRNS() {
    LoadableStudy.AlgoStatusReply reply =
        LoadableStudy.AlgoStatusReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testSaveLoadingInfoStatusException() {
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setLoadingInfoStatusId(1L);
    request.setProcessId("1");
    String correlationId = "1";
    Mockito.when(loadingPlanGrpcServiceImpl.saveLoadingInfoStatus(Mockito.any()))
        .thenReturn(getASRNS());
    try {
      var reply = this.loadingPlanService.saveLoadingInfoStatus(request, correlationId);
      Assertions.assertEquals("200", reply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //  void testSaveLoadingInformation() throws GenericServiceException {
  //    LoadingInformationRequest request = new LoadingInformationRequest();
  //    String correlationId = "1";
  //    Mockito.when(
  //            this.loadingInformationServiceImpl.saveLoadingInformation(
  //                Mockito.any(), Mockito.anyString()))
  //        .thenReturn(getLIR());
  //    Mockito.when(this.loadingPlanGrpcServiceImpl.getActiveVoyageDetails(Mockito.anyLong()))
  //        .thenReturn(getVR());
  //    Mockito.when(
  //            this.loadingPlanGrpcServiceImpl.fetchLoadingInformation(
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong()))
  //        .thenReturn(getLI());
  //    Mockito.when(
  //            this.loadingInformationServiceImpl.getLoadingDetailsByPortRotationId(
  //                Mockito.any(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong()))
  //        .thenReturn(getLD());
  //    Mockito.when(
  //            this.loadingInformationServiceImpl.getLoadingRateForVessel(
  //                Mockito.any(), Mockito.anyLong()))
  //        .thenReturn(getLR());
  //    Mockito.when(
  //            this.loadingInformationServiceImpl.getMasterBerthDetailsByPortId(Mockito.anyLong()))
  //        .thenReturn(getLBD());
  //    Mockito.when(this.loadingInformationServiceImpl.buildLoadingPlanBerthDetails(Mockito.any()))
  //        .thenReturn(getLBD());
  //    Mockito.when(
  //            this.loadingInformationServiceImpl.getCargoMachinesInUserFromVessel(
  //                Mockito.any(), Mockito.anyLong()))
  //        .thenReturn(getCMU());
  //    Mockito.when(this.loadingInformationServiceImpl.getLoadingStagesAndMasters(Mockito.any()))
  //        .thenReturn(getLSS());
  //    Mockito.when(this.loadingInformationServiceImpl.getToppingOffSequence(Mockito.any()))
  //        .thenReturn(getLTS());
  //    Mockito.when(
  //            this.loadingPlanGrpcServiceImpl.fetchPortWiseCargoDetails(
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.any()))
  //        .thenReturn(getCVTD());
  //    Mockito.doNothing()
  //        .when(loadingInformationServiceImpl)
  //        .setCargoTobeLoadedAndCargoGrade(
  //            Mockito.any(),
  //            Mockito.anyLong(),
  //            Mockito.anyLong(),
  //            Mockito.any(),
  //            Mockito.anyLong(),
  //            Mockito.anyLong(),
  //            Mockito.any(),
  //            Mockito.anyBoolean());
  //    var response = this.loadingPlanService.saveLoadingInformation(request, correlationId);
  //    Assertions.assertEquals(1L, response.getLoadingInformation().getLoadingInfoId());
  //  }

  private CargoVesselTankDetails getCVTD() {
    CargoVesselTankDetails cargoVesselTankDetails = new CargoVesselTankDetails();
    return cargoVesselTankDetails;
  }

  private List<ToppingOffSequence> getLTS() {
    List<ToppingOffSequence> list = new ArrayList<>();
    ToppingOffSequence toppingOffSequence = new ToppingOffSequence();
    list.add(toppingOffSequence);
    return list;
  }

  private LoadingStages getLSS() {
    LoadingStages loadingStages = new LoadingStages();
    return loadingStages;
  }

  private CargoMachineryInUse getCMU() {
    CargoMachineryInUse cargoMachineryInUse = new CargoMachineryInUse();
    return cargoMachineryInUse;
  }

  private List<BerthDetails> getLBD() {
    List<BerthDetails> list = new ArrayList<>();
    BerthDetails berthDetails = new BerthDetails();
    berthDetails.setLoadingInfoId(1L);
    list.add(berthDetails);
    return list;
  }

  private LoadingRates getLR() {
    LoadingRates loadingRates = new LoadingRates();
    return loadingRates;
  }

  private LoadingDetails getLD() {
    LoadingDetails loadingDetails = new LoadingDetails();
    return loadingDetails;
  }

  private LoadingInformationResponse getLIR() {
    LoadingInformationResponse response = new LoadingInformationResponse();
    response.setVesseld(1L);
    response.setPortRotationId(1L);
    return response;
  }

  private LoadingPlanModels.LoadingInformation getLI() {
    List<LoadingPlanModels.LoadingToppingOff> list2 = new ArrayList<>();
    LoadingPlanModels.LoadingToppingOff toppingOff =
        LoadingPlanModels.LoadingToppingOff.newBuilder().build();
    list2.add(toppingOff);
    List<LoadingPlanModels.LoadingMachinesInUse> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder().build();
    list1.add(machinesInUse);
    List<LoadingPlanModels.LoadingBerths> list = new ArrayList<>();
    LoadingPlanModels.LoadingBerths berths = LoadingPlanModels.LoadingBerths.newBuilder().build();
    list.add(berths);
    LoadingPlanModels.LoadingInformation information =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .setLoadingInfoId(1L)
            .setSynopticTableId(1L)
            .setIsLoadingInfoComplete(true)
            .setIsLoadingInfoComplete(true)
            .setIsLoadingPlanGenerated(true)
            .setIsLoadingInstructionsComplete(true)
            .setIsLoadingSequenceGenerated(true)
            .setLoadingInfoStatusId(1L)
            .setLoadingPlanArrStatusId(1L)
            .setLoadingPlanDepStatusId(1L)
            .setLoadingDelays(LoadingPlanModels.LoadingDelay.newBuilder().build())
            .addAllToppingOffSequence(list2)
            .setLoadingStage(LoadingPlanModels.LoadingStages.newBuilder().build())
            .addAllLoadingMachines(list1)
            .addAllLoadingBerths(list)
            .setLoadingRate(LoadingPlanModels.LoadingRates.newBuilder().build())
            .setLoadingDetail(LoadingPlanModels.LoadingDetails.newBuilder().build())
            .build();
    return information;
  }

  @Test
  void testSaveLoadingInformationException() {
    LoadingInformationRequest request = new LoadingInformationRequest();
    String correlationId = "1";
    try {
      Mockito.when(
              this.loadingInformationServiceImpl.saveLoadingInformation(
                  Mockito.any(), Mockito.anyString()))
          .thenReturn(getLIR());
      Mockito.when(this.loadingPlanGrpcServiceImpl.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVRNS());
      var response = this.loadingPlanService.saveLoadingInformation(request, correlationId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private VoyageResponse getVRNS() {
    VoyageResponse voyageResponse = new VoyageResponse();
    voyageResponse.setVoyageNumber("1");
    voyageResponse.setPortRotations(getLPRNS());
    voyageResponse.setId(1L);
    voyageResponse.setPatternId(1L);
    voyageResponse.setActiveLs(getLS());
    return voyageResponse;
  }

  private List<PortRotation> getLPRNS() {
    List<PortRotation> list = new ArrayList<>();
    PortRotation portRotation = new PortRotation();
    portRotation.setId(1L);
    // portRotation.setPortId(1L);
    portRotation.setPortOrder(1L);
    list.add(portRotation);
    return list;
  }

  @Test
  void testGetLoadingPlanRules() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    try {
      Mockito.when(this.loadingPlanGrpcServiceImpl.saveOrGetLoadingPlanRules(Mockito.any()))
          .thenReturn(getRR());
      var response = this.loadingPlanService.getLoadingPlanRules(vesselId, voyageId, loadingInfoId);
      Assertions.assertEquals("1", response.getResponseStatus().getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private RuleResponse getRR() {
    RuleResponse response = new RuleResponse();
    response.setResponseStatus(new CommonSuccessResponse("1", "1"));
    return response;
  }

  @Test
  void testSaveLoadingPlanRules() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    RuleRequest ruleRequest = new RuleRequest();
    try {
      Mockito.when(this.loadingPlanGrpcServiceImpl.saveOrGetLoadingPlanRules(Mockito.any()))
          .thenReturn(getRR());
      var response =
          this.loadingPlanService.saveLoadingPlanRules(
              vesselId, voyageId, loadingInfoId, ruleRequest);
      Assertions.assertEquals("1", response.getResponseStatus().getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetLoadingSequence() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    try {
      Mockito.when(this.loadingPlanGrpcServiceImpl.getLoadingSequence(Mockito.any()))
          .thenReturn(getLSR());
      Mockito.doNothing()
          .when(loadingSequenceService)
          .buildLoadingSequence(Mockito.anyLong(), Mockito.any(), Mockito.any());
      var response = this.loadingPlanService.getLoadingSequence(vesselId, voyageId, infoId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingSequenceReply getLSR() {
    LoadingPlanModels.LoadingSequenceReply reply =
        LoadingPlanModels.LoadingSequenceReply.newBuilder().build();
    return reply;
  }

  @Test
  void testSaveLoadingPlan() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    LoadingPlanAlgoRequest loadingPlanAlgoRequest = new LoadingPlanAlgoRequest();
    loadingPlanAlgoRequest.setProcessId("1");
    String requestJsonString = "1";
    Mockito.when(this.loadingPlanGrpcServiceImpl.saveJson(Mockito.any())).thenReturn(getSR());
    Mockito.doNothing()
        .when(loadingSequenceService)
        .buildLoadingPlanSaveRequest(
            Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    Mockito.when(loadingPlanGrpcServiceImpl.saveLoadingPlan(Mockito.any())).thenReturn(getLPSR());
    ReflectionTestUtils.setField(loadingPlanService, "rootFolder", "D:\\");
    try {
      Mockito.when(
              loadingPlanExcelReportService.generateLoadingPlanExcel(
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyBoolean()))
          .thenReturn(new byte[0]);
      var response =
          this.loadingPlanService.saveLoadingPlan(
              vesselId, voyageId, infoId, loadingPlanAlgoRequest, requestJsonString);
      Assertions.assertEquals("1", response.getProcessId());
      File file = new File(this.rootFolder + "/json");
      deleteFolder(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveLoadingPlanException1() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    LoadingPlanAlgoRequest loadingPlanAlgoRequest = new LoadingPlanAlgoRequest();
    loadingPlanAlgoRequest.setProcessId("1");
    String requestJsonString = "1";
    Mockito.when(this.loadingPlanGrpcServiceImpl.saveJson(Mockito.any())).thenReturn(getSR());
    Mockito.doNothing()
        .when(loadingSequenceService)
        .buildLoadingPlanSaveRequest(
            Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    Mockito.when(loadingPlanGrpcServiceImpl.saveLoadingPlan(Mockito.any())).thenReturn(getLPSR());
    try {
      Mockito.when(
              loadingPlanExcelReportService.generateLoadingPlanExcel(
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyBoolean()))
          .thenReturn(new byte[0]);
      var response =
          this.loadingPlanService.saveLoadingPlan(
              vesselId, voyageId, infoId, loadingPlanAlgoRequest, requestJsonString);
      File file = new File(this.rootFolder + "/json");
      deleteFolder(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveLoadingPlanException2() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    LoadingPlanAlgoRequest loadingPlanAlgoRequest = new LoadingPlanAlgoRequest();
    loadingPlanAlgoRequest.setProcessId("1");
    String requestJsonString = "1";
    Mockito.when(this.loadingPlanGrpcServiceImpl.saveJson(Mockito.any())).thenReturn(getSRNS());
    //
    // Mockito.doNothing().when(loadingSequenceService).buildLoadingPlanSaveRequest(Mockito.any(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any());
    //
    // Mockito.when(loadingPlanGrpcServiceImpl.saveLoadingPlan(Mockito.any())).thenReturn(getLPSR());
    ReflectionTestUtils.setField(loadingPlanService, "rootFolder", "D:\\");
    try {
      Mockito.when(
              loadingPlanExcelReportService.generateLoadingPlanExcel(
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyBoolean()))
          .thenReturn(new byte[0]);
      var response =
          this.loadingPlanService.saveLoadingPlan(
              vesselId, voyageId, infoId, loadingPlanAlgoRequest, requestJsonString);
      Assertions.assertEquals("1", response.getProcessId());
      File file = new File(this.rootFolder + "/json");
      deleteFolder(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveLoadingPlanException3() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    LoadingPlanAlgoRequest loadingPlanAlgoRequest = new LoadingPlanAlgoRequest();
    loadingPlanAlgoRequest.setProcessId("1");
    String requestJsonString = "1";
    Mockito.when(this.loadingPlanGrpcServiceImpl.saveJson(Mockito.any())).thenReturn(getSR());
    Mockito.doNothing()
        .when(loadingSequenceService)
        .buildLoadingPlanSaveRequest(
            Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    Mockito.when(loadingPlanGrpcServiceImpl.saveLoadingPlan(Mockito.any())).thenReturn(getLPSRNS());
    ReflectionTestUtils.setField(loadingPlanService, "rootFolder", "D:\\");
    try {
      Mockito.when(
              loadingPlanExcelReportService.generateLoadingPlanExcel(
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyBoolean()))
          .thenReturn(new byte[0]);
      var response =
          this.loadingPlanService.saveLoadingPlan(
              vesselId, voyageId, infoId, loadingPlanAlgoRequest, requestJsonString);
      File file = new File(this.rootFolder + "/json");
      deleteFolder(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void deleteFolder(File file) {
    for (File subFile : file.listFiles()) {
      if (subFile.isDirectory()) {
        deleteFolder(subFile);
      } else {
        subFile.delete();
      }
    }
    file.delete();
  }

  private LoadingPlanModels.LoadingPlanSaveResponse getLPSR() {
    LoadingPlanModels.LoadingPlanSaveResponse response =
        LoadingPlanModels.LoadingPlanSaveResponse.newBuilder()
            .setPortRotationId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private LoadingPlanModels.LoadingPlanSaveResponse getLPSRNS() {
    LoadingPlanModels.LoadingPlanSaveResponse response =
        LoadingPlanModels.LoadingPlanSaveResponse.newBuilder()
            .setPortRotationId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return response;
  }

  private LoadableStudy.StatusReply getSR() {
    LoadableStudy.StatusReply reply =
        LoadableStudy.StatusReply.newBuilder().setStatus(SUCCESS).build();
    return reply;
  }

  private LoadableStudy.StatusReply getSRNS() {
    LoadableStudy.StatusReply reply =
        LoadableStudy.StatusReply.newBuilder().setStatus(FAILED).build();
    return reply;
  }

  @Test
  void testGetLoadingPlan() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    try {
      Mockito.when(this.loadingPlanGrpcServiceImpl.getActiveVoyageDetails(Mockito.any()))
          .thenReturn(getVR());
      Mockito.when(
              this.loadingPlanGrpcServiceImpl.getLoadingPlan(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong()))
          .thenReturn(getLPRY());
      Mockito.when(
              this.loadingInformationServiceImpl.getLoadingRateForVessel(
                  Mockito.any(), Mockito.anyLong()))
          .thenReturn(getLR());
      Mockito.when(this.loadingInformationServiceImpl.getToppingOffSequence(Mockito.any()))
          .thenReturn(getLTS());
      Mockito.when(this.vesselInfoGrpcService.getVesselDetailForSynopticalTable(Mockito.any()))
          .thenReturn(getVRY());
      Mockito.when(
              this.loadingInformationServiceImpl.getLoadingDetailsByPortRotationId(
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong()))
          .thenReturn(getLD());
      Mockito.when(
              this.loadingInformationServiceImpl.getMasterBerthDetailsByPortId(Mockito.anyLong()))
          .thenReturn(getLBD());
      Mockito.when(this.loadingInformationServiceImpl.buildLoadingPlanBerthDetails(Mockito.any()))
          .thenReturn(getLBD());
      Mockito.when(
              this.loadingInformationServiceImpl.getCargoMachinesInUserFromVessel(
                  Mockito.any(), Mockito.anyLong()))
          .thenReturn(getCMU());
      Mockito.when(this.loadingInformationServiceImpl.getLoadingStagesAndMasters(Mockito.any()))
          .thenReturn(getLSS());
      Mockito.when(
              this.loadingPlanGrpcServiceImpl.fetchPortWiseCargoDetails(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any()))
          .thenReturn(getCVTD());
      Mockito.when(
              this.loadingInformationServiceImpl.getLoadablePlanCargoDetailsByPort(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyBoolean()))
          .thenReturn(getLLQCD());
      Mockito.when(this.loadingInformationServiceImpl.getLoadingSequence(Mockito.any()))
          .thenReturn(getLSSS());
      Mockito.when(loadingPlanBuilderService.buildLoadingPlanBallastFromRpc(Mockito.anyList()))
          .thenReturn(getLLPBD());
      Mockito.when(loadingPlanBuilderService.buildLoadingPlanStowageFromRpc(Mockito.anyList()))
          .thenReturn(getLLPSD());
      Mockito.when(loadingPlanBuilderService.buildLoadingPlanRobFromRpc(Mockito.anyList()))
          .thenReturn(getLLPRD());
      Mockito.when(
              loadingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(Mockito.anyList()))
          .thenReturn(getLLPSP());
      Mockito.when(loadingPlanBuilderService.buildLoadingPlanCommingleFromRpc(Mockito.anyList()))
          .thenReturn(getLLPCD());
      Mockito.when(
              this.loadingInformationServiceImpl.getLoadablePlanCargoDetailsByPortUnfiltered(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyBoolean()))
          .thenReturn(getLLQCD());
      ReflectionTestUtils.setField(
          loadingPlanService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
      var response =
          this.loadingPlanService.getLoadingPlan(vesselId, voyageId, infoId, portRotationId);
      Assertions.assertEquals(1L, response.getCurrentPortCargos().get(0).getCargoId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails> getLLPCD() {
    List<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails> list = new ArrayList<>();
    com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails loadingPlanCommingleDetails =
        new com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails();
    list.add(loadingPlanCommingleDetails);
    return list;
  }

  private List<LoadingPlanStabilityParam> getLLPSP() {
    List<LoadingPlanStabilityParam> list = new ArrayList<>();
    LoadingPlanStabilityParam loadingPlanStabilityParam = new LoadingPlanStabilityParam();
    list.add(loadingPlanStabilityParam);
    return list;
  }

  private List<LoadingPlanRobDetails> getLLPRD() {
    List<LoadingPlanRobDetails> list = new ArrayList<>();
    LoadingPlanRobDetails robDetails = new LoadingPlanRobDetails();
    list.add(robDetails);
    return list;
  }

  private List<LoadingPlanStowageDetails> getLLPSD() {
    List<LoadingPlanStowageDetails> list = new ArrayList<>();
    LoadingPlanStowageDetails loadingPlanStowageDetails = new LoadingPlanStowageDetails();
    loadingPlanStowageDetails.setApi("1");
    list.add(loadingPlanStowageDetails);
    return list;
  }

  private List<LoadingPlanBallastDetails> getLLPBD() {
    List<LoadingPlanBallastDetails> list = new ArrayList<>();
    LoadingPlanBallastDetails loadingPlanBallastDetails = new LoadingPlanBallastDetails();
    loadingPlanBallastDetails.setColorCode("1");
    list.add(loadingPlanBallastDetails);
    return list;
  }

  private LoadingSequences getLSSS() {
    LoadingSequences loadingSequences = new LoadingSequences();
    return loadingSequences;
  }

  private List<LoadableQuantityCargoDetails> getLLQCD() {
    List<LoadableQuantityCargoDetails> list = new ArrayList<>();
    LoadableQuantityCargoDetails loadableQuantityCargoDetails = new LoadableQuantityCargoDetails();
    loadableQuantityCargoDetails.setCargoId(1L);
    list.add(loadableQuantityCargoDetails);
    return list;
  }

  private LoadingPlanModels.LoadingPlanReply getLPRY() {
    List<LoadingPlanModels.LoadingPlanCommingleDetails> list6 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanCommingleDetails details =
        LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder().build();
    list6.add(details);
    List<LoadingPlanModels.LoadingPlanStabilityParameters> list5 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanStabilityParameters planStabilityParameters =
        LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder().build();
    list5.add(planStabilityParameters);
    List<LoadingPlanModels.LoadingPlanTankDetails> list4 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails tankDetails =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder().build();
    list4.add(tankDetails);
    List<LoadingPlanModels.LoadingMachinesInUse> list3 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse loadingMachinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder().build();
    list3.add(loadingMachinesInUse);
    List<LoadingPlanModels.LoadingBerths> list2 = new ArrayList<>();
    LoadingPlanModels.LoadingBerths loadingBerths =
        LoadingPlanModels.LoadingBerths.newBuilder().build();
    list2.add(loadingBerths);
    List<LoadingPlanModels.LoadingToppingOff> list = new ArrayList<>();
    LoadingPlanModels.LoadingToppingOff loadingToppingOff =
        LoadingPlanModels.LoadingToppingOff.newBuilder().build();
    list.add(loadingToppingOff);
    LoadingPlanModels.LoadingPlanReply reply =
        LoadingPlanModels.LoadingPlanReply.newBuilder()
            .addAllPortLoadingPlanCommingleDetails(list6)
            .addAllPortLoadingPlanStabilityParameters(list5)
            .addAllPortLoadingPlanRobDetails(list4)
            .addAllPortLoadingPlanStowageDetails(list4)
            .addAllPortLoadingPlanBallastDetails(list4)
            .setLoadingInformation(
                LoadingPlanModels.LoadingInformation.newBuilder()
                    .setLoadingDelays(LoadingPlanModels.LoadingDelay.newBuilder().build())
                    .setLoadingInfoStatusId(1L)
                    .setLoadingPlanDepStatusId(1L)
                    .setLoadingPlanArrStatusId(1L)
                    .setLoadablePatternId(1L)
                    .setLoadingStage(LoadingPlanModels.LoadingStages.newBuilder().build())
                    .addAllLoadingMachines(list3)
                    .addAllLoadingBerths(list2)
                    .setLoadingDetail(LoadingPlanModels.LoadingDetails.newBuilder().build())
                    .addAllToppingOffSequence(list)
                    .setLoadingRate(LoadingPlanModels.LoadingRates.newBuilder().build())
                    .build())
            .build();
    return reply;
  }

  private VesselInfo.VesselReply getVRY() {
    List<VesselInfo.VesselTankDetail> list = new ArrayList<>();
    VesselInfo.VesselTankDetail vesselTankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankDisplayOrder(1)
            .setTankCategoryId(2L)
            .setTankPositionCategory("FRONT")
            .setTankGroup(1)
            .setFrameNumberTo("1")
            .setFrameNumberTo("1")
            .setShortName("1")
            .setTankCategoryName("1")
            .setTankId(1L)
            .setTankName("1")
            .setDensity("1")
            .setIsSlopTank(true)
            .setFillCapacityCubm("1")
            .setHeightTo("1")
            .setHeightFrom("1")
            .setTankOrder(1)
            .setFullCapacityCubm("1")
            .build();
    list.add(vesselTankDetail);
    VesselInfo.VesselReply reply =
        VesselInfo.VesselReply.newBuilder().setVesselId(1L).addAllVesselTanks(list).build();
    return reply;
  }

  @Test
  void testGetUpdateUllageDetails() {
    Long vesselId = 1L;
    Long patternId = 1L;
    Long portRotationId = 1L;
    String operationType = "1";
    boolean isDischarging = true;
    try {
      Mockito.when(this.loadingPlanGrpcServiceImpl.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.when(loadableStudyServiceBlockingStub.getCargoNominationById(Mockito.any()))
          .thenReturn(getCNR());
      Mockito.when(
              this.dischargePlanServiceBlockingStub.getDischargeUpdateUllageDetails(Mockito.any()))
          .thenReturn(getUUDR());
      Mockito.when(
              this.loadingInformationServiceImpl.getLoadablePlanCargoDetailsByPort(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyBoolean()))
          .thenReturn(getLLQCD());
      Mockito.when(
              this.loadingInformationServiceImpl.getLoadablePlanCargoDetailsByPortUnfiltered(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyBoolean()))
          .thenReturn(getLLQCD());
      Mockito.when(cargoInfoServiceBlockingStub.getCargoInfosByCargoIds(Mockito.any()))
          .thenReturn(getCR());
      Mockito.when(this.vesselInfoGrpcService.getVesselDetailForSynopticalTable(Mockito.any()))
          .thenReturn(getVRY());
      ReflectionTestUtils.setField(
          loadingPlanService,
          "loadableStudyServiceBlockingStub",
          this.loadableStudyServiceBlockingStub);
      ReflectionTestUtils.setField(
          loadingPlanService,
          "dischargePlanServiceBlockingStub",
          this.dischargePlanServiceBlockingStub);
      ReflectionTestUtils.setField(
          loadingPlanService, "cargoInfoServiceBlockingStub", this.cargoInfoServiceBlockingStub);
      ReflectionTestUtils.setField(
          loadingPlanService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
      var response =
          this.loadingPlanService.getUpdateUllageDetails(
              vesselId, patternId, portRotationId, operationType, isDischarging);
      Assertions.assertEquals("200", response.getResponseStatus().getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.LoadableStudy.CargoNominationReply getCNR() {
    com.cpdss.common.generated.LoadableStudy.CargoNominationReply cargoNominationReply =
        LoadableStudy.CargoNominationReply.newBuilder().build();
    return cargoNominationReply;
  }

  private LoadingPlanModels.UpdateUllageDetailsResponse getUUDR() {
    LoadingPlanModels.UpdateUllageDetailsResponse response =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder().build();
    return response;
  }

  private CargoInfo.CargoReply getCR() {
    CargoInfo.CargoReply reply = CargoInfo.CargoReply.newBuilder().build();
    return reply;
  }

  @Test
  void testGetOnHandQuantity() {
    LoadableStudy.OnHandQuantityRequest request =
        LoadableStudy.OnHandQuantityRequest.newBuilder().build();
    Mockito.when(this.loadableStudyServiceBlockingStub.getOnHandQuantity(Mockito.any()))
        .thenReturn(getOHQR());
    ReflectionTestUtils.setField(
        loadingPlanService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response = this.loadingPlanService.getOnHandQuantity(request);
    Assertions.assertEquals(1L, response.getId());
  }

  private LoadableStudy.OnHandQuantityReply getOHQR() {
    LoadableStudy.OnHandQuantityReply reply =
        LoadableStudy.OnHandQuantityReply.newBuilder().setId(1L).build();
    return reply;
  }

  @Test
  void testGetVesselDetailForSynopticalTable() {
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    Mockito.when(this.vesselInfoGrpcService.getVesselDetailForSynopticalTable(Mockito.any()))
        .thenReturn(getVRY());
    ReflectionTestUtils.setField(
        loadingPlanService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    var response = this.loadingPlanService.getVesselDetailForSynopticalTable(request);
    Assertions.assertEquals(1L, response.getVesselId());
  }

  @Test
  void testBuildTankDetail() {
    VesselInfo.VesselTankDetail detail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setFrameNumberTo("1")
            .setFrameNumberFrom("1")
            .setShortName("1")
            .setTankCategoryId(1L)
            .setTankCategoryName("1")
            .setTankName("1")
            .setTankId(1L)
            .setIsSlopTank(true)
            .setDensity("1")
            .setFillCapacityCubm("1")
            .setHeightFrom("1")
            .setHeightTo("1")
            .setTankOrder(1)
            .setTankDisplayOrder(1)
            .setTankGroup(1)
            .setFullCapacityCubm("1")
            .build();
    var response = this.loadingPlanService.buildTankDetail(detail);
    Assertions.assertEquals("1", response.getDensity());
  }

  @Test
  void testGetLoadableStudyShoreTwo1() {
    String correlationID = "1";
    UllageBillRequest inputData = new UllageBillRequest();
    inputData.setIsValidate("1");
    inputData.setUllageUpdList(getLUUD());
    inputData.setBallastUpdateList(getLBDD());
    inputData.setRobUpdateList(getRUL());
    inputData.setBillOfLandingList(getLBL());
    inputData.setBillOfLandingListRemove(getLBL());
    inputData.setCommingleUpdateList(getLCU());
    boolean isDischarging = true;
    Mockito.when(dischargePlanServiceBlockingStub.updateDischargeUllageDetails(Mockito.any()))
        .thenReturn(getUR());
    ReflectionTestUtils.setField(
        loadingPlanService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.loadingPlanService.getLoadableStudyShoreTwo(correlationID, inputData, isDischarging);
      Assertions.assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetLoadableStudyShoreTwo1Exception() {
    String correlationID = "1";
    UllageBillRequest inputData = new UllageBillRequest();
    inputData.setIsValidate("1");
    inputData.setUllageUpdList(getLUUD());
    inputData.setBallastUpdateList(getLBDD());
    inputData.setRobUpdateList(getRUL());
    inputData.setBillOfLandingList(getLBL());
    inputData.setBillOfLandingListRemove(getLBL());
    inputData.setCommingleUpdateList(getLCU());
    boolean isDischarging = true;
    Mockito.when(dischargePlanServiceBlockingStub.updateDischargeUllageDetails(Mockito.any()))
        .thenReturn(getURNS());
    ReflectionTestUtils.setField(
        loadingPlanService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.loadingPlanService.getLoadableStudyShoreTwo(correlationID, inputData, isDischarging);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply getUR() {
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply reply =
        LoadingPlanModels.UllageBillReply.newBuilder()
            .setProcessId("1")
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setStatus(SUCCESS).setCode("200").build())
            .build();
    return reply;
  }

  private com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply getURNS() {
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply reply =
        LoadingPlanModels.UllageBillReply.newBuilder()
            .setProcessId("1")
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setStatus(FAILED).setCode("400").build())
            .build();
    return reply;
  }

  private List<CommingleDetail> getLCU() {
    List<CommingleDetail> list = new ArrayList<>();
    CommingleDetail commingleDetail = new CommingleDetail();
    commingleDetail.setLoadingInformationId(1L);
    commingleDetail.setDischargingInformationId(1L);
    commingleDetail.setTankId(1L);
    commingleDetail.setTemperature(new BigDecimal(1));
    commingleDetail.setQuantityMT(new BigDecimal(1));
    commingleDetail.setFillingPercentage(new BigDecimal(1));
    commingleDetail.setApi(new BigDecimal(1));
    commingleDetail.setCargoNomination1Id(1L);
    commingleDetail.setCargoNomination2Id(1L);
    commingleDetail.setUllage(new BigDecimal(1));
    commingleDetail.setArrival_departutre(1);
    commingleDetail.setActual_planned(1);
    commingleDetail.setIsUpdate(true);
    commingleDetail.setAbbreviation("1");
    commingleDetail.setCargo1Id(1L);
    commingleDetail.setCargo2Id(1L);
    commingleDetail.setQuantityMT(new BigDecimal(1));
    commingleDetail.setQuantity2MT(new BigDecimal(1));
    commingleDetail.setQuantity1M3(new BigDecimal(1));
    commingleDetail.setQuantity2M3(new BigDecimal(1));
    commingleDetail.setUllage1(new BigDecimal(1));
    commingleDetail.setUllage2(new BigDecimal(1));
    commingleDetail.setColorCode("1");
    list.add(commingleDetail);
    return list;
  }

  private List<BillOfLanding> getLBL() {
    List<BillOfLanding> list = new ArrayList<>();
    BillOfLanding billOfLanding = new BillOfLanding();
    billOfLanding.setId(1L);
    billOfLanding.setLoadingId(1L);
    billOfLanding.setDischargingId(1L);
    billOfLanding.setPortId(1L);
    billOfLanding.setCargoId(1L);
    billOfLanding.setBlRefNumber("1");
    billOfLanding.setBblAt60f(new BigDecimal(1));
    billOfLanding.setQuantityLt(new BigDecimal(1));
    billOfLanding.setQuantityMt(new BigDecimal(1));
    billOfLanding.setKlAt15c(new BigDecimal(1));
    billOfLanding.setApi(new BigDecimal(1));
    billOfLanding.setTemperature(new BigDecimal(1));
    billOfLanding.setIsActive(new BigDecimal(1));
    billOfLanding.setVersion(1L);
    billOfLanding.setIsUpdate(true);
    list.add(billOfLanding);
    return list;
  }

  private List<BallastDetail> getLBDD() {
    List<BallastDetail> list = new ArrayList<>();
    BallastDetail ballastDetail = new BallastDetail();
    ballastDetail.setLoadingInformationId(new BigDecimal(1));
    ballastDetail.setDischargingInformationId(new BigDecimal(1));
    ballastDetail.setTankId(new BigDecimal(1));
    ballastDetail.setTemperature(new BigDecimal(1));
    ballastDetail.setCorrectedUllage(new BigDecimal(1));
    ballastDetail.setCorrectionFactor(new BigDecimal(1));
    ballastDetail.setQuantity(new BigDecimal(1));
    ballastDetail.setObservedM3(new BigDecimal(1));
    ballastDetail.setFilling_percentage(new BigDecimal(1));
    ballastDetail.setSounding(new BigDecimal(1));
    ballastDetail.setFilling_percentage(new BigDecimal(1));
    ballastDetail.setArrival_departutre(new BigDecimal(1));
    ballastDetail.setActual_planned(new BigDecimal(1));
    ballastDetail.setColor_code("1");
    ballastDetail.setSg(new BigDecimal(1));
    ballastDetail.setPortXId(1L);
    ballastDetail.setPortRotationXId(1L);
    ballastDetail.setIsUpdate(true);
    list.add(ballastDetail);
    return list;
  }

  private List<RobDetail> getRUL() {
    List<RobDetail> list = new ArrayList<>();
    RobDetail robDetail = new RobDetail();
    robDetail.setLoadingInformationId(new BigDecimal(1));
    robDetail.setDischargingInformationId(new BigDecimal(1));
    robDetail.setTankId(new BigDecimal(1));
    robDetail.setTemperature(new BigDecimal(1));
    robDetail.setQuantity(new BigDecimal(1));
    robDetail.setIsUpdate(true);
    robDetail.setDensity(new BigDecimal(1));
    robDetail.setColour_code("1");
    robDetail.setArrival_departutre(new BigDecimal(1));
    robDetail.setActual_planned(new BigDecimal(1));
    robDetail.setPortXId(1L);
    robDetail.setPortRotationXId(1L);

    list.add(robDetail);
    return list;
  }

  private List<UllageUpdateDetails> getLUUD() {
    List<UllageUpdateDetails> list = new ArrayList<>();
    UllageUpdateDetails ullageUpdateDetails = new UllageUpdateDetails();
    ullageUpdateDetails.setLoadingInformationId(new BigDecimal(1));
    ullageUpdateDetails.setDischargingInformationId(new BigDecimal(1));
    ullageUpdateDetails.setTankId(new BigDecimal(1));
    ullageUpdateDetails.setTemperature(new BigDecimal(1));
    ullageUpdateDetails.setCorrectedUllage(new BigDecimal(1));
    ullageUpdateDetails.setQuantity(new BigDecimal(1));
    ullageUpdateDetails.setFillingPercentage(new BigDecimal(1));
    ullageUpdateDetails.setApi(new BigDecimal(1));
    ullageUpdateDetails.setCargoNominationId(new BigDecimal(1));
    ullageUpdateDetails.setUllage(new BigDecimal(1));
    ullageUpdateDetails.setPort_xid(1L);
    ullageUpdateDetails.setPort_rotation_xid(1L);
    ullageUpdateDetails.setArrival_departutre(1L);
    ullageUpdateDetails.setGrade(1L);
    ullageUpdateDetails.setActual_planned(1L);
    ullageUpdateDetails.setCorrectionFactor(new BigDecimal(1));
    ullageUpdateDetails.setIsUpdate(true);
    ullageUpdateDetails.setColor_code("1");
    ullageUpdateDetails.setAbbreviation("1");
    ullageUpdateDetails.setCargoId(1L);
    list.add(ullageUpdateDetails);
    return list;
  }

  @Test
  void testGetLoadableStudyShoreTwo2() {
    String correlationID = "1";
    UllageBillRequest inputData = new UllageBillRequest();
    inputData.setIsValidate("1");
    inputData.setUllageUpdList(getLUUD());
    inputData.setBallastUpdateList(getLBDD());
    inputData.setRobUpdateList(getRUL());
    inputData.setBillOfLandingList(getLBL());
    inputData.setBillOfLandingListRemove(getLBL());
    inputData.setCommingleUpdateList(getLCU());
    boolean isDischarging = false;
    try {
      Mockito.when(
              loadingPlanGrpcServiceImpl.getLoadableStudyShoreTwo(
                  Mockito.anyString(), Mockito.any()))
          .thenReturn(getUBR());
      var response =
          this.loadingPlanService.getLoadableStudyShoreTwo(correlationID, inputData, isDischarging);
      Assertions.assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private UllageBillReply getUBR() {
    UllageBillReply ullageBillReply = new UllageBillReply();
    ullageBillReply.setResponseStatus(new CommonSuccessResponse(SUCCESS, "1"));
    return ullageBillReply;
  }

  @Test
  void testGetLoadableStudyShoreTwo2Exception() {
    String correlationID = "1";
    UllageBillRequest inputData = new UllageBillRequest();
    inputData.setIsValidate("1");
    inputData.setUllageUpdList(getLUUD());
    inputData.setBallastUpdateList(getLBDD());
    inputData.setRobUpdateList(getRUL());
    inputData.setBillOfLandingList(getLBL());
    inputData.setBillOfLandingListRemove(getLBL());
    inputData.setCommingleUpdateList(getLCU());
    boolean isDischarging = false;
    try {
      Mockito.when(
              loadingPlanGrpcServiceImpl.getLoadableStudyShoreTwo(
                  Mockito.anyString(), Mockito.any()))
          .thenReturn(getUBRNS());
      var response =
          this.loadingPlanService.getLoadableStudyShoreTwo(correlationID, inputData, isDischarging);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private UllageBillReply getUBRNS() {
    UllageBillReply ullageBillReply = new UllageBillReply();
    ullageBillReply.setResponseStatus(new CommonSuccessResponse(FAILED, "1"));
    return ullageBillReply;
  }

  @Test
  void testUploadLoadingTideDetails() throws GenericServiceException, IOException {
    Long loadingId = 1L;
    MultipartFile file =
        new MultipartFile() {
          @Override
          public String getName() {
            return null;
          }

          @Override
          public String getOriginalFilename() {
            return null;
          }

          @Override
          public String getContentType() {
            return null;
          }

          @Override
          public boolean isEmpty() {
            return false;
          }

          @Override
          public long getSize() {
            return 0;
          }

          @Override
          public byte[] getBytes() throws IOException {
            return new byte[0];
          }

          @Override
          public InputStream getInputStream() throws IOException {
            return null;
          }

          @Override
          public void transferTo(File dest) throws IOException, IllegalStateException {}
        };
    String correlationId = "1";
    String portName = "1";
    Long portId = 1L;
    Mockito.when(
            loadingInformationServiceImpl.uploadLoadingTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenReturn(getUTDR());
    var response =
        this.loadingPlanService.uploadLoadingTideDetails(
            loadingId, file, correlationId, portName, portId);
    Assertions.assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  private UploadTideDetailResponse getUTDR() {
    UploadTideDetailResponse response = new UploadTideDetailResponse();
    response.setResponseStatus(new CommonSuccessResponse(SUCCESS, "1"));
    return response;
  }

  @Test
  void testDownloadLoadingPortTideDetails() throws GenericServiceException {
    Long loadingId = 1L;
    Mockito.when(loadingInformationServiceImpl.downloadLoadingPortTideDetails(Mockito.anyLong()))
        .thenReturn(new byte[0]);
    var response = this.loadingPlanService.downloadLoadingPortTideDetails(loadingId);
    Assertions.assertEquals(0, response.length);
  }

  @Test
  void testImportUllageReportFile() throws GenericServiceException, IOException {
    MultipartFile file =
        new MultipartFile() {
          @Override
          public String getName() {
            return null;
          }

          @Override
          public String getOriginalFilename() {
            return null;
          }

          @Override
          public String getContentType() {
            return null;
          }

          @Override
          public boolean isEmpty() {
            return false;
          }

          @Override
          public long getSize() {
            return 0;
          }

          @Override
          public byte[] getBytes() throws IOException {
            return new byte[0];
          }

          @Override
          public InputStream getInputStream() throws IOException {
            return null;
          }

          @Override
          public void transferTo(File dest) throws IOException, IllegalStateException {}
        };
    String tankDetails = "1";
    Long infoId = 1L;
    Long cargoNominationId = 1L;
    String correlationId = "1";
    boolean isLoading = true;
    Long vesselId = 1L;
    Mockito.when(
            ullageReportFileParsingService.importUllageReportFile(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.anyLong()))
        .thenReturn(getLURR());
    var respponse =
        this.loadingPlanService.importUllageReportFile(
            file, tankDetails, infoId, cargoNominationId, correlationId, isLoading, vesselId);
    Assertions.assertEquals(SUCCESS, respponse.getResponseStatus().getStatus());
  }

  private ListOfUllageReportResponse getLURR() {
    ListOfUllageReportResponse response = new ListOfUllageReportResponse();
    response.setResponseStatus(new CommonSuccessResponse(SUCCESS, "1"));
    return response;
  }

  @Test
  void testGetSimulatorJsonDataForLoading()
      throws GenericServiceException, JsonProcessingException {
    Long vesselId = 1L;
    Long infoId = 1L;
    String correlationId = "1";
    Mockito.when(loadableStudyServiceBlockingStub.getLoadingSimulatorJsonData(Mockito.any()))
        .thenReturn(getLSJR());
    ReflectionTestUtils.setField(
        loadingPlanService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response =
        this.loadingPlanService.getSimulatorJsonDataForLoading(vesselId, infoId, correlationId);
    Assertions.assertEquals("200", response.getResponseStatus().getStatus());
  }

  private com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply getLSJR() {
    com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply reply =
        LoadableStudy.LoadingSimulatorJsonReply.newBuilder()
            .setLoadingJson("1")
            .setLoadicatorJson("1")
            .build();
    return reply;
  }

  @Test
  void testPrepareFileName() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long portRotationId = 1L;
    Mockito.when(
            this.loadableStudyServiceBlockingStub.getLoadableStudyPortRotationByPortRotationId(
                Mockito.any()))
        .thenReturn(getDR());
    Mockito.when(this.vesselInfoGrpcService.getVesselInfoByVesselId(Mockito.any()))
        .thenReturn(getVIR());
    Mockito.when(this.loadableStudyServiceBlockingStub.getVoyageByVoyageId(Mockito.any()))
        .thenReturn(getVIRR());
    Mockito.when(this.portInfoGrpcService.getPortInfoByPortIds(Mockito.any())).thenReturn(getPR());
    ReflectionTestUtils.setField(
        loadingPlanService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    ReflectionTestUtils.setField(
        loadingPlanService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        loadingPlanService, "portInfoGrpcService", this.portInfoGrpcService);
    var response = this.loadingPlanService.prepareFileName(vesselId, voyageId, portRotationId);
  }

  private com.cpdss.common.generated.PortInfo.PortReply getPR() {
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail detail = PortInfo.PortDetail.newBuilder().setName("1").build();
    list.add(detail);
    com.cpdss.common.generated.PortInfo.PortReply portReply =
        com.cpdss.common.generated.PortInfo.PortReply.newBuilder().addAllPorts(list).build();
    return portReply;
  }

  private com.cpdss.common.generated.LoadableStudy.VoyageInfoReply getVIRR() {
    com.cpdss.common.generated.LoadableStudy.VoyageInfoReply reply =
        com.cpdss.common.generated.LoadableStudy.VoyageInfoReply.newBuilder()
            .setVoyageDetail(LoadableStudy.VoyageDetail.newBuilder().setVoyageNumber("1").build())
            .build();
    return reply;
  }

  private com.cpdss.common.generated.VesselInfo.VesselIdResponse getVIR() {
    com.cpdss.common.generated.VesselInfo.VesselIdResponse response =
        VesselInfo.VesselIdResponse.newBuilder()
            .setVesselDetail(VesselInfo.VesselDetail.newBuilder().setName("1").build())
            .build();
    return response;
  }

  private com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply getDR() {
    com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply reply =
        com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.newBuilder()
            .setPortRotationDetail(
                LoadableStudy.PortRotationDetail.newBuilder().setPortId(1L).build())
            .build();
    return reply;
  }
}
