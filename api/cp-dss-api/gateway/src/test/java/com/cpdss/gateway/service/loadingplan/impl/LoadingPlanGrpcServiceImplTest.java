/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static com.cpdss.gateway.common.GatewayConstants.FAILED;
import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.service.LoadableStudyService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingPlanGrpcServiceImpl.class})
public class LoadingPlanGrpcServiceImplTest {

  @Autowired LoadingPlanGrpcServiceImpl loadingPlanGrpcService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  @MockBean
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub;

  @MockBean private LoadableStudyService loadableStudyService;

  @Test
  void testGetActiveVoyageDetails() {
    Long vesselId = 1L;
    Mockito.when(loadableStudyServiceBlockingStub.getActiveVoyagesByVessel(Mockito.any()))
        .thenReturn(getAV());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
      assertEquals(1L, response.getActiveDs().getId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.ActiveVoyage getAV() {
    List<LoadableStudy.PortRotationDetail> list1 = new ArrayList<>();
    LoadableStudy.PortRotationDetail detail = LoadableStudy.PortRotationDetail.newBuilder().build();
    list1.add(detail);
    List<LoadableStudy.PortRotationDetail> list = new ArrayList<>();
    LoadableStudy.PortRotationDetail portRotationDetail =
        LoadableStudy.PortRotationDetail.newBuilder().build();
    list.add(portRotationDetail);
    LoadableStudy.ActiveVoyage voyage =
        LoadableStudy.ActiveVoyage.newBuilder()
            .addAllPortRotation(list)
            .addAllDischargePortRotation(list1)
            .setConfirmedDischargeStudy(
                LoadableStudy.LoadableStudyDetail.newBuilder().setId(1L).build())
            .setConfirmedLoadableStudy(
                LoadableStudy.LoadableStudyDetail.newBuilder().setId(1L).build())
            .setId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return voyage;
  }

  @Test
  void testGetActiveVoyageDetailsException1() {
    Long vesselId = 1L;
    Mockito.when(loadableStudyServiceBlockingStub.getActiveVoyagesByVessel(Mockito.any()))
        .thenReturn(getAVNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.ActiveVoyage getAVNS() {
    LoadableStudy.ActiveVoyage voyage =
        LoadableStudy.ActiveVoyage.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return voyage;
  }

  @Test
  void testGetActiveVoyageDetailsException2() {
    Long vesselId = 1L;
    Mockito.when(loadableStudyServiceBlockingStub.getActiveVoyagesByVessel(Mockito.any()))
        .thenReturn(getAVN());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.ActiveVoyage getAVN() {
    LoadableStudy.ActiveVoyage voyage =
        LoadableStudy.ActiveVoyage.newBuilder()
            .setId(0L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return voyage;
  }

  @Test
  void testGetPortRotationDetailsForActiveVoyage() {
    Long vesselId = 1L;
    var response = this.loadingPlanGrpcService.getPortRotationDetailsForActiveVoyage(vesselId);
  }

  @Test
  void testFetchSynopticRecordForPortRotation() {
    Long portRId = 1L;
    String operationType = "1";
    Mockito.when(this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.fetchSynopticRecordForPortRotation(portRId, operationType);
      assertEquals("1", response.getOperationType());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LoadingPlanCommonResponse getLPCR() {
    List<LoadableStudy.LoadablePlanBallastDetails> list2 = new ArrayList<>();
    LoadableStudy.LoadablePlanBallastDetails detail =
        LoadableStudy.LoadablePlanBallastDetails.newBuilder().setTankId(1L).build();
    list2.add(detail);
    List<LoadableStudy.LoadableQuantityCargoDetails> list1 = new ArrayList<>();
    LoadableStudy.LoadableQuantityCargoDetails details =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder().setPriority(1).build();
    list1.add(details);
    List<LoadableStudy.LoadingSynopticResponse> list = new ArrayList<>();
    LoadableStudy.LoadingSynopticResponse respon =
        LoadableStudy.LoadingSynopticResponse.newBuilder().setOperationType("1").build();
    list.add(respon);
    LoadableStudy.LoadingPlanCommonResponse response =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder()
            .addAllLoadablePlanBallastDetails(list2)
            .addAllLoadableQuantityCargoDetails(list1)
            .addAllSynopticData(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testFetchSynopticRecordForPortRotationException1() {
    Long portRId = 1L;
    String operationType = "1";
    Mockito.when(this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(Mockito.any()))
        .thenReturn(getLPCRNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.fetchSynopticRecordForPortRotation(portRId, operationType);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LoadingPlanCommonResponse getLPCRNS() {
    LoadableStudy.LoadingPlanCommonResponse response =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return response;
  }

  @Test
  void testFetchSynopticRecordForPortRotation1() {
    Long portRId = 1L;
    String operationType = "1";
    Mockito.when(this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(Mockito.any()))
        .thenReturn(getLPCRN());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.fetchSynopticRecordForPortRotation(portRId, operationType);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LoadingPlanCommonResponse getLPCRN() {
    LoadableStudy.LoadingPlanCommonResponse response =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testFetchPortDetailByPortId() {
    Long portId = 1L;
    Mockito.when(this.portInfoServiceBlockingStub.getPortInfoByPortIds(Mockito.any()))
        .thenReturn(getPR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.fetchPortDetailByPortId(portId);
      assertEquals(1L, response.getId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPR() {
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail portDetail = PortInfo.PortDetail.newBuilder().setId(1L).build();
    list.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return portReply;
  }

  @Test
  void testFetchPortDetailByPortId1() {
    Long portId = 1L;
    Mockito.when(this.portInfoServiceBlockingStub.getPortInfoByPortIds(Mockito.any()))
        .thenReturn(getPRN());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.fetchPortDetailByPortId(portId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPRN() {
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return portReply;
  }

  @Test
  void testFetchPortDetailByPortIdException() {
    Long portId = 1L;
    Mockito.when(this.portInfoServiceBlockingStub.getPortInfoByPortIds(Mockito.any()))
        .thenReturn(getPRNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.fetchPortDetailByPortId(portId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPRNS() {
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return portReply;
  }

  @Test
  void testFetchLoadingInformation() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long patternId = 1L;
    Long portRotationId = 1L;
    Mockito.when(loadingInfoServiceBlockingStub.getLoadingInformation(Mockito.any()))
        .thenReturn(getLI());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.fetchLoadingInformation(
              vesselId, voyageId, loadingInfoId, patternId, portRotationId);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInformation getLI() {
    LoadingPlanModels.LoadingInformation information =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .setLoadablePatternId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return information;
  }

  @Test
  void testFetchLoadingInformationException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long patternId = 1L;
    Long portRotationId = 1L;
    Mockito.when(loadingInfoServiceBlockingStub.getLoadingInformation(Mockito.any()))
        .thenReturn(getLINS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.fetchLoadingInformation(
              vesselId, voyageId, loadingInfoId, patternId, portRotationId);
      assertEquals(FAILED, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInformation getLINS() {
    LoadingPlanModels.LoadingInformation information =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .setLoadablePatternId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return information;
  }

  //    @Test
  //      void testFetchPortWiseCargoDetails() {
  //        Long vesselId = 1L;
  //        Long voyageId = 1L;
  //        Long loadableStudyId = 1L;
  //        Long portId = 1L;
  //        Long portOrder = 1L;
  //        Long portRotationId = 1L;
  //        String operationType = "1";
  //        try {
  //
  // Mockito.when(this.loadableStudyService.getVoyageStatus(Mockito.any(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString()))
  //                    .thenReturn(getVSR());
  //
  // ReflectionTestUtils.setField(loadingPlanGrpcService,"loadableStudyServiceBlockingStub",this.loadableStudyServiceBlockingStub);
  //
  // ReflectionTestUtils.setField(loadingPlanGrpcService,"cargoInfoServiceBlockingStub",this.cargoInfoServiceBlockingStub);
  //
  // ReflectionTestUtils.setField(loadingPlanGrpcService,"portInfoServiceBlockingStub",this.portInfoServiceBlockingStub);
  //
  // ReflectionTestUtils.setField(loadingPlanGrpcService,"vesselInfoGrpcService",this.vesselInfoGrpcService);
  //
  // ReflectionTestUtils.setField(loadingPlanGrpcService,"loadingInfoServiceBlockingStub",this.loadingInfoServiceBlockingStub);
  //
  // ReflectionTestUtils.setField(loadingPlanGrpcService,"loadingPlanServiceBlockingStub",this.loadingPlanServiceBlockingStub);
  //            var response =
  // this.loadingPlanGrpcService.fetchPortWiseCargoDetails(vesselId,voyageId,loadableStudyId,portId,portOrder,portRotationId,operationType);
  //            assertEquals(1L,response.getCargoConditions().get(0).getId());
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

  private VoyageStatusResponse getVSR() {
    VoyageStatusResponse voyageStatusResponse = new VoyageStatusResponse();
    voyageStatusResponse.setCargoTanks(getLLVT());
    voyageStatusResponse.setCargoQuantities(getLSTR());
    voyageStatusResponse.setCargoConditions(getLC());
    return voyageStatusResponse;
  }

  private List<List<VesselTank>> getLLVT() {
    List<List<VesselTank>> list = new ArrayList<>();
    List<VesselTank> list1 = new ArrayList<>();
    VesselTank vesselTank = new VesselTank();
    vesselTank.setId(1L);
    VesselTank vesselTank1 = new VesselTank();
    vesselTank1.setId(1L);
    list1.add(vesselTank1);
    list1.add(vesselTank);
    list.add(list1);
    return list;
  }

  private List<SynopticalCargoBallastRecord> getLSTR() {
    List<SynopticalCargoBallastRecord> list = new ArrayList<>();
    SynopticalCargoBallastRecord record = new SynopticalCargoBallastRecord();
    record.setCapacity(new BigDecimal(1));
    list.add(record);
    return list;
  }

  private List<Cargo> getLC() {
    List<Cargo> list = new ArrayList<>();
    Cargo cargo = new Cargo();
    cargo.setId(1L);
    list.add(cargo);
    return list;
  }

  @Test
  void testFetchLoadablePlanCargoDetails() {
    Long patternId = 1L;
    String operationType = "1";
    Long portRotationId = 1L;
    Long portId = 1L;
    Boolean isFilterOn = true;
    Common.PLANNING_TYPE planning_type = Common.PLANNING_TYPE.LOADABLE_STUDY;
    Mockito.when(this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response =
        this.loadingPlanGrpcService.fetchLoadablePlanCargoDetails(
            patternId, operationType, portRotationId, portId, isFilterOn, planning_type);
    assertEquals(1, response.get(0).getPriority());
  }

  @Test
  void testFetchLoadablePlanCargoDetailsReplay() {
    Long patternId = 1L;
    String operationType = "1";
    Long portRotationId = 1L;
    Long portId = 1L;
    Boolean isFilterOn = true;
    Common.PLANNING_TYPE planning_type = Common.PLANNING_TYPE.LOADABLE_STUDY;
    Mockito.when(this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response =
        this.loadingPlanGrpcService.fetchLoadablePlanCargoDetailsReplay(
            patternId, operationType, portRotationId, portId, isFilterOn, planning_type);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  @Test
  void testBuildVoyageRequest() {
    Long vesselId = 1L;
    var response = this.loadingPlanGrpcService.buildVoyageRequest(vesselId);
    assertEquals(1L, response.getVesselId());
  }

  @Test
  void testSaveLoadingInformation() {
    LoadingPlanModels.LoadingInformation loadingInformation =
        LoadingPlanModels.LoadingInformation.newBuilder().build();
    Mockito.when(this.loadingInfoServiceBlockingStub.saveLoadingInformation(Mockito.any()))
        .thenReturn(getLISR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    var response = this.loadingPlanGrpcService.saveLoadingInformation(loadingInformation);
    assertEquals(1L, response.getLoadingInfoId());
  }

  private LoadingPlanModels.LoadingInfoSaveResponse getLISR() {
    LoadingPlanModels.LoadingInfoSaveResponse response =
        LoadingPlanModels.LoadingInfoSaveResponse.newBuilder().setLoadingInfoId(1L).build();
    return response;
  }

  @Test
  void testUpdateUllageAtLoadingPlan() {
    LoadingPlanModels.UpdateUllageLoadingRequest request =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder()
            .setPortRotationId(1L)
            .setVoyageId(1L)
            .setTankId(1L)
            .build();
    Mockito.when(this.loadingInfoServiceBlockingStub.updateUllage(Mockito.any()))
        .thenReturn(getUULR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.updateUllageAtLoadingPlan(request);
      assertEquals(true, response);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.UpdateUllageLoadingReplay getUULR() {
    LoadingPlanModels.UpdateUllageLoadingReplay replay =
        LoadingPlanModels.UpdateUllageLoadingReplay.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return replay;
  }

  @Test
  void testUpdateUllageAtLoadingPlanException() {
    LoadingPlanModels.UpdateUllageLoadingRequest request =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder().build();
    Mockito.when(this.loadingInfoServiceBlockingStub.updateUllage(Mockito.any()))
        .thenReturn(getUULRNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.updateUllageAtLoadingPlan(request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.UpdateUllageLoadingReplay getUULRNS() {
    LoadingPlanModels.UpdateUllageLoadingReplay replay =
        LoadingPlanModels.UpdateUllageLoadingReplay.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setStatus(FAILED).setMessage("400").build())
            .build();
    return replay;
  }

  @Test
  void testSaveLoadingInfoStatus() {
    LoadableStudy.AlgoStatusRequest request = LoadableStudy.AlgoStatusRequest.newBuilder().build();
    Mockito.when(this.loadingInfoServiceBlockingStub.saveAlgoLoadingPlanStatus(Mockito.any()))
        .thenReturn(getASR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    var response = this.loadingPlanGrpcService.saveLoadingInfoStatus(request);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  private LoadableStudy.AlgoStatusReply getASR() {
    LoadableStudy.AlgoStatusReply reply =
        LoadableStudy.AlgoStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGenerateLoadingPlan() {
    Long loadingInfoId = 1L;
    Mockito.when(this.loadingInfoServiceBlockingStub.generateLoadingPlan(Mockito.any()))
        .thenReturn(getLIAR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    var response = this.loadingPlanGrpcService.generateLoadingPlan(loadingInfoId);
    assertEquals(1L, response.getLoadingInfoId());
  }

  private LoadingPlanModels.LoadingInfoAlgoReply getLIAR() {
    LoadingPlanModels.LoadingInfoAlgoReply reply =
        LoadingPlanModels.LoadingInfoAlgoReply.newBuilder().setLoadingInfoId(1L).build();
    return reply;
  }

  @Test
  void testSaveOrGetLoadingPlanRules() {
    LoadingPlanModels.LoadingPlanRuleRequest.Builder builder =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    Mockito.when(this.loadingPlanServiceBlockingStub.getOrSaveRulesForLoadingPlan(Mockito.any()))
        .thenReturn(getLPRR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.saveOrGetLoadingPlanRules(builder);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingPlanRuleReply getLPRR() {
    LoadingPlanModels.LoadingPlanRuleReply reply =
        LoadingPlanModels.LoadingPlanRuleReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testSaveOrGetLoadingPlanRulesException() {
    LoadingPlanModels.LoadingPlanRuleRequest.Builder builder =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    Mockito.when(this.loadingPlanServiceBlockingStub.getOrSaveRulesForLoadingPlan(Mockito.any()))
        .thenReturn(getLPRRNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.saveOrGetLoadingPlanRules(builder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingPlanRuleReply getLPRRNS() {
    LoadingPlanModels.LoadingPlanRuleReply reply =
        LoadingPlanModels.LoadingPlanRuleReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingSequence() {
    LoadingPlanModels.LoadingSequenceRequest.Builder builder =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder();
    Mockito.when(this.loadingPlanServiceBlockingStub.getLoadingSequences(Mockito.any()))
        .thenReturn(getLSR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getLoadingSequence(builder);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingSequenceReply getLSR() {
    LoadingPlanModels.LoadingSequenceReply reply =
        LoadingPlanModels.LoadingSequenceReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingSequenceException() {
    LoadingPlanModels.LoadingSequenceRequest.Builder builder =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder();
    Mockito.when(this.loadingPlanServiceBlockingStub.getLoadingSequences(Mockito.any()))
        .thenReturn(getLSRNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getLoadingSequence(builder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingSequenceReply getLSRNS() {
    LoadingPlanModels.LoadingSequenceReply reply =
        LoadingPlanModels.LoadingSequenceReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testSaveLoadingPlan() {
    LoadingPlanModels.LoadingPlanSaveRequest request =
        LoadingPlanModels.LoadingPlanSaveRequest.newBuilder().build();
    Mockito.when(this.loadingPlanServiceBlockingStub.saveLoadingPlan(Mockito.any()))
        .thenReturn(getLPSR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    var response = this.loadingPlanGrpcService.saveLoadingPlan(request);
    assertEquals(1L, response.getPortRotationId());
  }

  private LoadingPlanModels.LoadingPlanSaveResponse getLPSR() {
    LoadingPlanModels.LoadingPlanSaveResponse response =
        LoadingPlanModels.LoadingPlanSaveResponse.newBuilder().setPortRotationId(1L).build();
    return response;
  }

  @Test
  void testGetLoadingPlan() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long patternId = 1L;
    Long portRotationId = 1L;
    Mockito.when(this.loadingPlanServiceBlockingStub.getLoadingPlan(Mockito.any()))
        .thenReturn(getLPR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.getLoadingPlan(
              vesselId, voyageId, loadingInfoId, patternId, portRotationId);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingPlanReply getLPR() {
    LoadingPlanModels.LoadingPlanReply reply =
        LoadingPlanModels.LoadingPlanReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingPlanException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long patternId = 1L;
    Long portRotationId = 1L;
    Mockito.when(this.loadingPlanServiceBlockingStub.getLoadingPlan(Mockito.any()))
        .thenReturn(getLPRNS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response =
          this.loadingPlanGrpcService.getLoadingPlan(
              vesselId, voyageId, loadingInfoId, patternId, portRotationId);
      assertEquals(FAILED, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingPlanReply getLPRNS() {
    LoadingPlanModels.LoadingPlanReply reply =
        LoadingPlanModels.LoadingPlanReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testGetUpdateUllageDetails() {
    LoadingPlanModels.UpdateUllageDetailsRequest.Builder requestBuilder =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder();
    Mockito.when(loadingPlanServiceBlockingStub.getUpdateUllageDetails(Mockito.any()))
        .thenReturn(getUUDR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getUpdateUllageDetails(requestBuilder);
      assertEquals("200", response.getMessage());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.UpdateUllageDetailsResponse getUUDR() {
    LoadingPlanModels.UpdateUllageDetailsResponse response =
        LoadingPlanModels.UpdateUllageDetailsResponse.newBuilder().setMessage("200").build();
    return response;
  }

  @Test
  void testSaveJson() {
    LoadableStudy.JsonRequest jsonRequest = LoadableStudy.JsonRequest.newBuilder().build();
    Mockito.when(this.loadableStudyServiceBlockingStub.saveJson(Mockito.any())).thenReturn(getSR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response = this.loadingPlanGrpcService.saveJson(jsonRequest);
    assertEquals("200", response.getStatus());
  }

  private LoadableStudy.StatusReply getSR() {
    LoadableStudy.StatusReply reply =
        LoadableStudy.StatusReply.newBuilder().setStatus("200").build();
    return reply;
  }

  @Test
  void testGetLoadingInfoAlgoStatus() {
    LoadingPlanModels.LoadingInfoStatusRequest request =
        LoadingPlanModels.LoadingInfoStatusRequest.newBuilder().build();
    Mockito.when(this.loadingInfoServiceBlockingStub.getLoadingInfoAlgoStatus(Mockito.any()))
        .thenReturn(getLISRR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    var response = this.loadingPlanGrpcService.getLoadingInfoAlgoStatus(request);
    assertEquals(1L, response.getLoadingInfoId());
  }

  private LoadingPlanModels.LoadingInfoStatusReply getLISRR() {
    LoadingPlanModels.LoadingInfoStatusReply reply =
        LoadingPlanModels.LoadingInfoStatusReply.newBuilder().setLoadingInfoId(1L).build();
    return reply;
  }

  @Test
  void testGetLoadingInfoAlgoErrors() {
    LoadableStudy.AlgoErrorRequest request = LoadableStudy.AlgoErrorRequest.newBuilder().build();
    Mockito.when(this.loadingInfoServiceBlockingStub.getLoadingInfoAlgoErrors(Mockito.any()))
        .thenReturn(getAER());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    var response = this.loadingPlanGrpcService.getLoadingInfoAlgoErrors(request);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  private LoadableStudy.AlgoErrorReply getAER() {
    LoadableStudy.AlgoErrorReply reply =
        LoadableStudy.AlgoErrorReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadableStudyShoreTwo() {
    String first = "1";
    LoadingPlanModels.UllageBillRequest.Builder inputData =
        LoadingPlanModels.UllageBillRequest.newBuilder();
    Mockito.when(loadingPlanServiceBlockingStub.getLoadableStudyShoreTwo(Mockito.any()))
        .thenReturn(getUBR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response = this.loadingPlanGrpcService.getLoadableStudyShoreTwo(first, inputData);
      assertEquals("1", response.getProcessId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.UllageBillReply getUBR() {
    LoadingPlanModels.UllageBillReply reply =
        LoadingPlanModels.UllageBillReply.newBuilder()
            .setProcessId("1")
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setStatus(SUCCESS).setCode("200").build())
            .build();
    return reply;
  }

  @Test
  void testFetchLoadablePlanBallastDetails() {
    Long patternId = 1L;
    Long portRotationId = 1L;
    Mockito.when(this.loadableStudyServiceBlockingStub.getSynopticDataForLoadingPlan(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response =
        this.loadingPlanGrpcService.fetchLoadablePlanBallastDetails(patternId, portRotationId);
    assertEquals(1L, response.get(0).getTankId());
  }

  @Test
  void testUpdateDischargeQuantityCargoDetails() {
    List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetails = new ArrayList<>();
    DischargeQuantityCargoDetails details = new DischargeQuantityCargoDetails();
    details.setId(1L);
    details.setProtested(true);
    details.setIsCommingledDischarge(true);
    details.setSlopQuantity("1");
    dischargeQuantityCargoDetails.add(details);
    Mockito.when(
            loadableStudyServiceBlockingStub.updateDischargeQuantityCargoDetails(Mockito.any()))
        .thenReturn(getRS());
    ReflectionTestUtils.setField(
        loadingPlanGrpcService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    var response =
        this.loadingPlanGrpcService.updateDischargeQuantityCargoDetails(
            dischargeQuantityCargoDetails);
    assertEquals("200", response.getCode());
  }

  private Common.ResponseStatus getRS() {
    Common.ResponseStatus status = Common.ResponseStatus.newBuilder().setCode("200").build();
    return status;
  }
}
