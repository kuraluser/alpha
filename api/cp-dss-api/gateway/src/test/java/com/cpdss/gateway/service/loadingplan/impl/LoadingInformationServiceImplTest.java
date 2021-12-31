/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.discharge_plan.PostDischargeStageTime;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.RulePlans;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.UpdateUllage;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.PortInfoService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@SpringJUnitConfig(classes = {LoadingInformationServiceImpl.class})
public class LoadingInformationServiceImplTest {

  @Autowired LoadingInformationServiceImpl loadingInformationService;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @MockBean VesselInfoService vesselInfoService;

  @MockBean PortInfoService portInfoService;

  @MockBean LoadingPlanGrpcService loadingPlanGrpcService;

  @MockBean LoadingInformationBuilderService loadingInfoBuilderService;

  @MockBean LoadableStudyService loadableStudyService;

  @MockBean
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Test
  void testGetLoadingDetailsByPortRotationIdException() {
    LoadingPlanModels.LoadingDetails var1 =
        LoadingPlanModels.LoadingDetails.newBuilder()
            .setStartTime("1")
            .setTimeOfSunrise("1")
            .setTimeOfSunset("2")
            .setTrimAllowed(
                LoadingPlanModels.TrimAllowed.newBuilder()
                    .setInitialTrim("1")
                    .setMaximumTrim("1")
                    .setFinalTrim("1")
                    .build())
            .build();
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long portRId = 1L;
    Long portId = 1L;
    try {
      Mockito.when(
              vesselInfoService.getRulesByVesselIdAndSectionId(
                  Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
          .thenReturn(getRR());
      Mockito.when(
              this.loadingPlanGrpcService.fetchSynopticRecordForPortRotation(
                  Mockito.anyLong(), Mockito.any()))
          .thenReturn(getLSR());
      Mockito.when(this.loadingPlanGrpcService.fetchPortDetailByPortId(Mockito.anyLong()))
          .thenReturn(getPD());
      var response =
          this.loadingInformationService.getLoadingDetailsByPortRotationId(
              var1, vesselId, voyageId, portRId, portId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  //    @Test
  //    void testGetLoadingDetailsByPortRotationId() {
  //        LoadingPlanModels.LoadingDetails var1 =
  // LoadingPlanModels.LoadingDetails.newBuilder().setStartTime("1").setTimeOfSunrise("1").setTimeOfSunset("2")
  //                .setTrimAllowed(LoadingPlanModels.TrimAllowed.newBuilder()
  //
  // .setInitialTrim("1").setMaximumTrim("1").setFinalTrim("1").build()).build();
  //        Long vesselId = 1L;
  //        Long voyageId = 1L;
  //        Long portRId = 1L;
  //        Long portId = 1L;
  //        try {
  //
  // Mockito.when(vesselInfoService.getRulesByVesselIdAndSectionId(Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.anyString())).thenReturn(getRR());
  //
  // Mockito.when(this.loadingPlanGrpcService.fetchSynopticRecordForPortRotation(Mockito.anyLong(),Mockito.any())).thenReturn(getLSR());
  //
  // Mockito.when(this.loadingPlanGrpcService.fetchPortDetailByPortId(Mockito.anyLong())).thenReturn(getPD());
  //
  //
  // ReflectionTestUtils.setField(vesselInfoService,"vesselInfoGrpcService",this.vesselInfoGrpcService);
  //
  // ReflectionTestUtils.setField(loadingInformationService,"vesselInfoService",this.vesselInfoService);
  //            var response =
  // this.loadingInformationService.getLoadingDetailsByPortRotationId(var1,vesselId,voyageId,portRId,portId);
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

  private RuleResponse getRR() {
    RuleResponse response = new RuleResponse();
    response.setPlan(getLRP());
    return response;
  }

  private List<RulePlans> getLRP() {
    List<RulePlans> list = new ArrayList<>();
    RulePlans rulePlans = new RulePlans();
    rulePlans.setHeader("1");
    list.add(rulePlans);
    return list;
  }

  private PortInfo.PortDetail getPD() {
    PortInfo.PortDetail detail =
        PortInfo.PortDetail.newBuilder().setSunsetTime("1").setSunriseTime("1").build();
    return detail;
  }

  private LoadableStudy.LoadingSynopticResponse getLSR() {
    LoadableStudy.LoadingSynopticResponse response =
        LoadableStudy.LoadingSynopticResponse.newBuilder()
            .setTimeOfSunset("1")
            .setTimeOfSunrise("1")
            .build();
    return response;
  }

  @Test
  void testGetMasterBerthDetailsByPortId() {
    Long portId = 1L;
    Mockito.when(this.portInfoService.getBerthInfoByPortId(Mockito.anyLong())).thenReturn(getBIR());
    var response = this.loadingInformationService.getMasterBerthDetailsByPortId(portId);
    assertEquals("1", response.get(0).getHoseConnections());
  }

  private PortInfo.BerthInfoResponse getBIR() {
    List<PortInfo.BerthDetail> list = new ArrayList<>();
    PortInfo.BerthDetail detail =
        PortInfo.BerthDetail.newBuilder()
            .setId(1L)
            .setPortId(1L)
            .setMaxShipChannel("1")
            .setBerthName("1")
            .setMaxShipDepth("1")
            .setSeaDraftLimitation("1")
            .setAirDraftLimitation("1")
            .setMaxManifoldHeight("1")
            .setRegulationAndRestriction("1")
            .setMaxLoa("1")
            .setLineDisplacement("1")
            .setHoseConnection("1")
            .build();
    list.add(detail);
    PortInfo.BerthInfoResponse response =
        PortInfo.BerthInfoResponse.newBuilder().addAllBerths(list).build();
    return response;
  }

  @Test
  void testBuildLoadingPlanBerthDetails() {
    List<LoadingPlanModels.LoadingBerths> var1 = new ArrayList<>();
    LoadingPlanModels.LoadingBerths berths =
        LoadingPlanModels.LoadingBerths.newBuilder()
            .setLoadingInfoId(1L)
            .setId(1L)
            .setBerthId(1L)
            .setDepth("1")
            .setSeaDraftLimitation("1")
            .setAirDraftLimitation("1")
            .setMaxManifoldHeight("1")
            .setSpecialRegulationRestriction("1")
            .setItemsToBeAgreedWith("1")
            .setHoseConnections("1")
            .setLineDisplacement("1")
            .build();
    var1.add(berths);
    var response = this.loadingInformationService.buildLoadingPlanBerthDetails(var1);
    assertEquals("1", response.get(0).getHoseConnections());
  }

  @Test
  void testGetCargoMachinesInUserFromVessel() {
    List<LoadingPlanModels.LoadingMachinesInUse> var1 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder()
            .setId(1L)
            .setMachineId(1L)
            .setLoadingInfoId(1L)
            .setMachineType(Common.MachineType.EMPTY)
            .setCapacity("1")
            .setIsUsing(true)
            .build();
    var1.add(machinesInUse);
    Long vesselId = 1L;
    Mockito.when(vesselInfoService.getVesselPumpsFromVesselInfo(Mockito.anyLong()))
        .thenReturn(getVPR());
    var response = this.loadingInformationService.getCargoMachinesInUserFromVessel(var1, vesselId);
    assertEquals(new BigDecimal(1), response.getLoadingMachinesInUses().get(0).getCapacity());
  }

  private VesselInfo.VesselPumpsResponse getVPR() {
    List<VesselInfo.PumpType> list = new ArrayList<>();
    VesselInfo.PumpType pumpType = VesselInfo.PumpType.newBuilder().build();
    list.add(pumpType);
    List<VesselInfo.VesselPump> list1 = new ArrayList<>();
    VesselInfo.VesselPump pump =
        VesselInfo.VesselPump.newBuilder().setPumpTypeId(2L).setPumpCapacity("1").build();
    list1.add(pump);
    List<VesselInfo.VesselComponent> list2 = new ArrayList<>();
    VesselInfo.VesselComponent component =
        VesselInfo.VesselComponent.newBuilder()
            .setId(1L)
            .setComponentCode("1")
            .setComponentName("1")
            .setVesselId(1L)
            .setComponentType(1L)
            .build();
    list2.add(component);
    List<VesselInfo.TankType> list3 = new ArrayList<>();
    VesselInfo.TankType tankType =
        VesselInfo.TankType.newBuilder().setId(1L).setTypeName("1").build();
    list3.add(tankType);
    VesselInfo.VesselPumpsResponse response =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .addAllVesselManifold(list2)
            .addAllVesselPump(list1)
            .addAllVesselBottomLine(list2)
            .addAllPumpType(list)
            .addAllTankType(list3)
            .build();
    return response;
  }

  @Test
  void testGetLoadingStagesAndMasters() {
    List<LoadingPlanModels.StageOffsets> list = new ArrayList<>();
    LoadingPlanModels.StageOffsets stageOffsets =
        LoadingPlanModels.StageOffsets.newBuilder().setId(1L).setStageOffsetVal(1L).build();
    list.add(stageOffsets);
    List<LoadingPlanModels.StageDuration> list1 = new ArrayList<>();
    LoadingPlanModels.StageDuration stageDuration =
        LoadingPlanModels.StageDuration.newBuilder().setId(1L).setDuration(1L).build();
    list1.add(stageDuration);
    LoadingPlanModels.LoadingStages var1 =
        LoadingPlanModels.LoadingStages.newBuilder()
            .setId(1L)
            .addAllStageOffsets(list)
            .setTrackGradeSwitch(true)
            .setTrackStartEndStage(true)
            .setStageOffset(1)
            .setStageDuration(1)
            .addAllStageDurations(list1)
            .build();
    var response = this.loadingInformationService.getLoadingStagesAndMasters(var1);
    assertEquals(1L, response.getId());
  }

  @Test
  void testGetToppingOffSequence() {
    List<LoadingPlanModels.LoadingToppingOff> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingToppingOff loadingToppingOff =
        LoadingPlanModels.LoadingToppingOff.newBuilder()
            .setId(1L)
            .setLoadingInfoId(1L)
            .setOrderNumber(1)
            .setTankId(1L)
            .setCargoId(1L)
            .setCargoName("1")
            .setAbbreviation("1")
            .setColourCode("1")
            .setRemark("1")
            .setUllage("1")
            .setQuantity("1")
            .setFillingRatio("1")
            .setApi("1")
            .setTemperature("1")
            .setDisplayOrder(1)
            .setCargoNominationId(1L)
            .build();
    list1.add(loadingToppingOff);
    var response = this.loadingInformationService.getToppingOffSequence(list1);
    assertEquals("1", response.get(0).getCargoAbbreviation());
  }

  @Test
  void testGetLoadablePlanCargoDetailsByPort() {
    Long vesselId = 1L;
    Long patternId = 1L;
    String operationType = "1";
    Long portRotationId = 1L;
    Long portId = 1L;
    Common.PLANNING_TYPE planningType = Common.PLANNING_TYPE.LOADABLE_STUDY;
    boolean isDischarging = true;
    Mockito.when(
            this.loadingPlanGrpcService.fetchLoadablePlanCargoDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any()))
        .thenReturn(getLLQCD());
    var response =
        this.loadingInformationService.getLoadablePlanCargoDetailsByPort(
            vesselId,
            patternId,
            operationType,
            portRotationId,
            portId,
            planningType,
            isDischarging);
    assertEquals("1", response.get(0).getColorCode());
  }

  private List<LoadableStudy.LoadableQuantityCargoDetails> getLLQCD() {
    List<LoadableStudy.LoadingPortDetail> list1 = new ArrayList<>();
    LoadableStudy.LoadingPortDetail detail =
        LoadableStudy.LoadingPortDetail.newBuilder().setName("1").build();
    list1.add(detail);
    List<LoadableStudy.LoadableQuantityCargoDetails> list = new ArrayList<>();
    LoadableStudy.LoadableQuantityCargoDetails details =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder()
            .setDifferencePercentage("1")
            .setDifferenceColor("1")
            .setGrade("1")
            .setEstimatedAPI("1")
            .setEstimatedTemp("1")
            .setId(1L)
            .setLoadableBblsdbs("1")
            .setLoadableBbls60F("1")
            .setLoadableKL("1")
            .setLoadableLT("1")
            .setLoadableMT("1")
            .setMaxTolerence("1")
            .setMinTolerence("1")
            .setOrderBblsdbs("1")
            .setOrderBbls60F("1")
            .setOrderedQuantity("1")
            .setSlopQuantity("1")
            .setTimeRequiredForLoading("1")
            .setCargoNominationTemperature("1")
            .setCargoId(1L)
            .setCargoAbbreviation("1")
            .setColorCode("1")
            .setLoadingOrder(1)
            .setPriority(1)
            .setOrderedQuantity("1")
            .setCargoNominationQuantity("1")
            .setDscargoNominationId(1L)
            .setLoadingRateM3Hr("1")
            .addAllLoadingPorts(list1)
            .setDischargeMT("1")
            .build();
    list.add(details);
    return list;
  }

  @Test
  void testGetDischargePlanCargoDetailsByPort() {
    Long vesselId = 1L;
    Long patternId = 1L;
    String operationType = "1";
    Long portRotationId = 1L;
    Long portId = 1L;
    Mockito.when(
            this.loadingPlanGrpcService.fetchLoadablePlanCargoDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any()))
        .thenReturn(getLLQCD());
    var response =
        this.loadingInformationService.getDischargePlanCargoDetailsByPort(
            vesselId, patternId, operationType, portRotationId, portId);
    assertEquals("1", response.get(0).getCargoAbbreviation());
  }

  @Test
  void testGetLoadingSequence() {
    List<LoadingPlanModels.DelayReasons> list = new ArrayList<>();
    LoadingPlanModels.DelayReasons delayReasons =
        LoadingPlanModels.DelayReasons.newBuilder().build();
    list.add(delayReasons);
    List<Long> list2 = new ArrayList<>();
    list2.add(1L);
    List<LoadingPlanModels.LoadingDelays> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingDelays delays =
        LoadingPlanModels.LoadingDelays.newBuilder()
            .setDuration("1")
            .setQuantity("1")
            .addAllReasonForDelayIds(list2)
            .build();
    list1.add(delays);
    LoadingPlanModels.LoadingDelay loadingDelay =
        LoadingPlanModels.LoadingDelay.newBuilder().addAllDelays(list1).addAllReasons(list).build();
    var response = this.loadingInformationService.getLoadingSequence(loadingDelay);
    assertEquals(new BigDecimal(1), response.getLoadingDelays().get(0).getDuration());
  }

  @Test
  void testGetLoadingInfoCargoDetailsByPattern() {
    Long patternId = 1L;
    Mockito.when(loadingInfoServiceBlockingStub.getLoadigInformationByVoyage(Mockito.any()))
        .thenReturn(getLISR());
    ReflectionTestUtils.setField(
        loadingInformationService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingInformationService.getLoadingInfoCargoDetailsByPattern(patternId);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInformationSynopticalReply getLISR() {
    LoadingPlanModels.LoadingInformationSynopticalReply reply =
        LoadingPlanModels.LoadingInformationSynopticalReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingInfoCargoDetailsByPatternException() {
    Long patternId = 1L;
    Mockito.when(loadingInfoServiceBlockingStub.getLoadigInformationByVoyage(Mockito.any()))
        .thenReturn(getLISRNS());
    ReflectionTestUtils.setField(
        loadingInformationService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response = this.loadingInformationService.getLoadingInfoCargoDetailsByPattern(patternId);
      assertEquals(FAILED, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInformationSynopticalReply getLISRNS() {
    LoadingPlanModels.LoadingInformationSynopticalReply reply =
        LoadingPlanModels.LoadingInformationSynopticalReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setStatus(FAILED).setCode("400").build())
            .build();
    return reply;
  }

  @Test
  void testSaveLoadingInformation() {
    LoadingInformationRequest request = new LoadingInformationRequest();
    request.setLoadingDetails(getLD());
    request.setSynopticalTableId(1L);
    String correlationId = "1";
    try {
      Mockito.when(loadingInfoBuilderService.saveDataAsync(Mockito.any())).thenReturn(getLISRR());
      Mockito.doNothing()
          .when(loadableStudyService)
          .saveLoadingInfoToSynopticalTable(Mockito.anyLong(), Mockito.any(), Mockito.any());
      var response = this.loadingInformationService.saveLoadingInformation(request, correlationId);
      assertEquals(1L, response.getLoadingInfoId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private LoadingDetails getLD() {
    LoadingDetails loadingDetails = new LoadingDetails();
    loadingDetails.setTimeOfSunrise("1");
    loadingDetails.setTimeOfSunset("2");
    return loadingDetails;
  }

  private LoadingPlanModels.LoadingInfoSaveResponse getLISRR() {
    LoadingPlanModels.LoadingInfoSaveResponse response =
        LoadingPlanModels.LoadingInfoSaveResponse.newBuilder()
            .setLoadingInfoId(1L)
            .setPortRotationId(1L)
            .setSynopticalTableId(1L)
            .setVesselId(1L)
            .setVoyageId(1L)
            .build();
    return response;
  }

  @Test
  void testSaveLoadingInformationException() {
    LoadingInformationRequest request = new LoadingInformationRequest();
    request.setLoadingDetails(getLD());
    request.setSynopticalTableId(1L);
    request.setLoadingInfoId(1L);
    String correlationId = "1";
    try {
      Mockito.when(loadingInfoBuilderService.saveDataAsync(Mockito.any())).thenReturn(null);
      Mockito.doNothing()
          .when(loadableStudyService)
          .saveLoadingInfoToSynopticalTable(Mockito.anyLong(), Mockito.any(), Mockito.any());
      var response = this.loadingInformationService.saveLoadingInformation(request, correlationId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInfoSaveResponse getLISRRNS() {
    LoadingPlanModels.LoadingInfoSaveResponse response =
        LoadingPlanModels.LoadingInfoSaveResponse.newBuilder().build();
    return response;
  }

  @Test
  void testProcessUpdateUllage() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long portRotationId = 1L;
    UpdateUllage updateUllage = new UpdateUllage();
    updateUllage.setId(1L);
    updateUllage.setTankId(1L);

    String correlationId = "1";
    try {
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.doNothing()
          .when(loadableStudyService)
          .buildUpdateUllageRequest(
              Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
      Mockito.when(this.loadableStudyService.updateUllage(Mockito.any())).thenReturn(getUUR());
      Mockito.when(this.loadingPlanGrpcService.updateUllageAtLoadingPlan(Mockito.any()))
          .thenReturn(true);
      Mockito.when(
              this.loadableStudyService.buildeUpdateUllageResponse(
                  Mockito.any(), Mockito.anyString()))
          .thenReturn(getUU());
      var response =
          this.loadingInformationService.processUpdateUllage(
              vesselId, voyageId, loadingInfoId, portRotationId, updateUllage, correlationId);
      assertEquals("1", response.getApi());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private UpdateUllage getUU() {
    UpdateUllage updateUllage = new UpdateUllage();
    updateUllage.setApi("1");
    return updateUllage;
  }

  private LoadableStudy.UpdateUllageReply getUUR() {
    LoadableStudy.UpdateUllageReply reply =
        LoadableStudy.UpdateUllageReply.newBuilder()
            .setLoadablePlanStowageDetails(
                LoadableStudy.LoadablePlanStowageDetails.newBuilder()
                    .setCargoNominationId(1L)
                    .setFillingRatio("1")
                    .setCorrectionFactor("1")
                    .setCorrectedUllage("1")
                    .setWeight("1")
                    .build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private VoyageResponse getVR() {
    VoyageResponse voyageResponse = new VoyageResponse();
    voyageResponse.setId(1L);
    voyageResponse.setPatternId(1L);
    return voyageResponse;
  }

  @Test
  void testProcessUpdateUllageException1() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long portRotationId = 1L;
    UpdateUllage updateUllage = new UpdateUllage();
    String correlationId = "1";
    try {
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(null);
      var response =
          this.loadingInformationService.processUpdateUllage(
              vesselId, voyageId, loadingInfoId, portRotationId, updateUllage, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testProcessUpdateUllageException2() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long portRotationId = 1L;
    UpdateUllage updateUllage = new UpdateUllage();
    String correlationId = "1";
    try {
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.doNothing()
          .when(loadableStudyService)
          .buildUpdateUllageRequest(
              Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
      Mockito.when(this.loadableStudyService.updateUllage(Mockito.any())).thenReturn(getUURNS());
      var response =
          this.loadingInformationService.processUpdateUllage(
              vesselId, voyageId, loadingInfoId, portRotationId, updateUllage, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.UpdateUllageReply getUURNS() {
    LoadableStudy.UpdateUllageReply reply =
        LoadableStudy.UpdateUllageReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder()
                    .setStatus(FAILED)
                    .setCode("400")
                    .setHttpStatusCode(400)
                    .build())
            .build();
    return reply;
  }

  @Test
  void testProcessUpdateUllageException3() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long loadingInfoId = 1L;
    Long portRotationId = 1L;
    UpdateUllage updateUllage = new UpdateUllage();
    updateUllage.setId(1L);
    updateUllage.setTankId(1L);

    String correlationId = "1";
    try {
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.doNothing()
          .when(loadableStudyService)
          .buildUpdateUllageRequest(
              Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
      Mockito.when(this.loadableStudyService.updateUllage(Mockito.any())).thenReturn(getUUR());
      Mockito.when(this.loadingPlanGrpcService.updateUllageAtLoadingPlan(Mockito.any()))
          .thenReturn(false);
      var response =
          this.loadingInformationService.processUpdateUllage(
              vesselId, voyageId, loadingInfoId, portRotationId, updateUllage, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGenerateLoadingPlan() {
    Long infoId = 1L;
    Mockito.when(this.loadingPlanGrpcService.generateLoadingPlan(Mockito.anyLong()))
        .thenReturn(getLIAR());
    try {
      var response = this.loadingInformationService.generateLoadingPlan(infoId);
      assertEquals("1", response.getProcessId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInfoAlgoReply getLIAR() {
    LoadingPlanModels.LoadingInfoAlgoReply reply =
        LoadingPlanModels.LoadingInfoAlgoReply.newBuilder()
            .setProcessId("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGenerateLoadingPlanException() {
    Long infoId = 1L;
    Mockito.when(this.loadingPlanGrpcService.generateLoadingPlan(Mockito.anyLong()))
        .thenReturn(getLIARNS());
    try {
      var response = this.loadingInformationService.generateLoadingPlan(infoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInfoAlgoReply getLIARNS() {
    LoadingPlanModels.LoadingInfoAlgoReply reply =
        LoadingPlanModels.LoadingInfoAlgoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingInfoAlgoStatus() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    String processId = "1";
    Integer conditionType = 1;
    Mockito.when(this.loadingPlanGrpcService.getLoadingInfoAlgoStatus(Mockito.any()))
        .thenReturn(getLISRRR());
    try {
      var response =
          this.loadingInformationService.getLoadingInfoAlgoStatus(
              vesselId, voyageId, infoId, processId, conditionType);
      assertEquals(1L, response.getLoadingInfoStatusId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInfoStatusReply getLISRRR() {
    LoadingPlanModels.LoadingInfoStatusReply reply =
        LoadingPlanModels.LoadingInfoStatusReply.newBuilder()
            .setLoadingInfoStatusId(1L)
            .setLoadingInfoStatusLastModifiedTime("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingInfoAlgoStatusException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    String processId = "1";
    Integer conditionType = 1;
    Mockito.when(this.loadingPlanGrpcService.getLoadingInfoAlgoStatus(Mockito.any()))
        .thenReturn(getLISRRRNS());
    try {
      var response =
          this.loadingInformationService.getLoadingInfoAlgoStatus(
              vesselId, voyageId, infoId, processId, conditionType);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInfoStatusReply getLISRRRNS() {
    LoadingPlanModels.LoadingInfoStatusReply reply =
        LoadingPlanModels.LoadingInfoStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingInfoAlgoErrors() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Integer conditionType = 1;
    Mockito.when(this.loadingPlanGrpcService.getLoadingInfoAlgoErrors(Mockito.any()))
        .thenReturn(getAER());
    try {
      var response =
          this.loadingInformationService.getLoadingInfoAlgoErrors(
              vesselId, voyageId, infoId, conditionType);
      assertEquals(SUCCESS, response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.AlgoErrorReply getAER() {
    List<String> list1 = new ArrayList<>();
    list1.add("1");
    List<LoadableStudy.AlgoErrors> list = new ArrayList<>();
    LoadableStudy.AlgoErrors errors =
        LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .addAllErrorMessages(list1)
            .build();
    list.add(errors);
    LoadableStudy.AlgoErrorReply reply =
        LoadableStudy.AlgoErrorReply.newBuilder()
            .addAllAlgoErrors(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetLoadingInfoAlgoErrorsException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Integer conditionType = 1;
    Mockito.when(this.loadingPlanGrpcService.getLoadingInfoAlgoErrors(Mockito.any()))
        .thenReturn(getAERNS());
    try {
      var response =
          this.loadingInformationService.getLoadingInfoAlgoErrors(
              vesselId, voyageId, infoId, conditionType);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.AlgoErrorReply getAERNS() {
    LoadableStudy.AlgoErrorReply reply =
        LoadableStudy.AlgoErrorReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testUploadLoadingTideDetails() {
    Long loadingId = 1L;
    MultipartFile file =
        new MultipartFile() {
          @Override
          public String getName() {
            return null;
          }

          @Override
          public String getOriginalFilename() {
            return "xlsx";
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
    Mockito.when(loadingInfoServiceBlockingStub.uploadPortTideDetails(Mockito.any()))
        .thenReturn(getUTDSR());
    ReflectionTestUtils.setField(
        loadingInformationService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response =
          this.loadingInformationService.uploadLoadingTideDetails(
              loadingId, file, correlationId, portName, portId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.UploadTideDetailStatusReply getUTDSR() {
    LoadingPlanModels.UploadTideDetailStatusReply reply =
        LoadingPlanModels.UploadTideDetailStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testUploadLoadingTideDetailsException1() {
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
    Mockito.when(loadingInfoServiceBlockingStub.uploadPortTideDetails(Mockito.any()))
        .thenReturn(getUTDSR());
    ReflectionTestUtils.setField(
        loadingInformationService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response =
          this.loadingInformationService.uploadLoadingTideDetails(
              loadingId, file, correlationId, portName, portId);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testUploadLoadingTideDetailsException2() {
    Long loadingId = 1L;
    MultipartFile file =
        new MultipartFile() {
          @Override
          public String getName() {
            return null;
          }

          @Override
          public String getOriginalFilename() {
            return "xlsx";
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
    Mockito.when(loadingInfoServiceBlockingStub.uploadPortTideDetails(Mockito.any()))
        .thenReturn(getUTDSRNS());
    ReflectionTestUtils.setField(
        loadingInformationService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response =
          this.loadingInformationService.uploadLoadingTideDetails(
              loadingId, file, correlationId, portName, portId);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.UploadTideDetailStatusReply getUTDSRNS() {
    LoadingPlanModels.UploadTideDetailStatusReply reply =
        LoadingPlanModels.UploadTideDetailStatusReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder()
                    .setStatus(FAILED)
                    .setMessage("400")
                    .setCode("400")
                    .setHttpStatusCode(400)
                    .build())
            .build();
    return reply;
  }

  @Test
  void testGetPostDischargeStage() {
    PostDischargeStageTime postDischargeStageTime =
        PostDischargeStageTime.newBuilder()
            .setFinalStripping("1")
            .setSlopDischarging("1")
            .setFreshOilWashing("1")
            .setTimeForDryCheck("1")
            .build();
    var response = this.loadingInformationService.getPostDischargeStage(postDischargeStageTime);
    assertEquals(new BigDecimal(1), response.getDryCheckTime());
  }
}
