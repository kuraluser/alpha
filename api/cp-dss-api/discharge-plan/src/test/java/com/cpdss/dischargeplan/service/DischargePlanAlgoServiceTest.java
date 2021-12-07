/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;
import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.domain.DischargeInformationAlgoRequest;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.loadicator.LoadicatorService;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {DischargePlanAlgoService.class})
public class DischargePlanAlgoServiceTest {

  @Autowired DischargePlanAlgoService dischargePlanAlgoService;

  @MockBean DischargeInformationService dischargeInformationService;

  @MockBean DischargeBerthDetailRepository dischargeBerthDetailRepository;

  @MockBean DischargingDelayRepository dischargingDelayRepository;

  @MockBean DischargingMachineryInUseRepository dischargingMachineryInUseRepository;

  @MockBean ReasonForDelayRepository reasonForDelayRepository;

  @MockBean CowPlanDetailRepository cowPlanDetailRepository;

  @MockBean private DischargeInformationStatusRepository dischargeInformationStatusRepository;
  @MockBean private DischargingPlanBuilderService dischargingPlanBuilderService;

  @MockBean
  private DischargingInformationAlgoStatusRepository dischargingInformationAlgoStatusRepository;

  @MockBean private DischargingSequenceRepository dischargingSequenceRepository;
  @MockBean private LoadicatorService loadicatorService;
  @MockBean BallastOperationRepository ballastOperationRepository;
  @MockBean CargoDischargingRateRepository cargoDischargingRateRepository;
  @MockBean DeballastingRateRepository deballastingRateRepository;

  @MockBean
  private DischargingSequenceStabiltyParametersRepository
      dischargingSequenceStabiltyParametersRepository;

  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private BallastValveRepository ballastValveRepository;
  @MockBean private CargoValveRepository cargoValveRepository;
  @MockBean private CowTankDetailRepository cowTankDetailRepository;

  @MockBean
  private DischargingPlanCommingleDetailsRepository dischargingPlanCommingleDetailsRepository;

  @Autowired
  private DischargingPlanStabilityParametersRepository dischargingPlanStabilityParametersRepository;

  @MockBean private DischargingPlanRobDetailsRepository dischargingPlanRobDetailsRepository;

  @MockBean private DischargingPlanBallastDetailsRepository dischargingPlanBallastDetailsRepository;

  @MockBean private DischargingPlanStowageDetailsRepository dischargingPlanStowageDetailsRepository;

  @MockBean
  private DischargingPlanPortWiseDetailsRepository dischargingPlanPortWiseDetailsRepository;

  @MockBean private PortDischargingPlanRobDetailsRepository portRobDetailsRepository;
  @MockBean private PortDischargingPlanStabilityParametersRepository portStabilityParamsRepository;
  @MockBean private PortDischargingPlanStowageDetailsRepository portStowageDetailsRepository;

  @MockBean
  private PortDischargingPlanCommingleDetailsRepository
      portDischargingPlanCommingleDetailsRepository;

  @MockBean
  PortDischargingPlanStowageTempDetailsRepository portDischargingPlanStowageTempDetailsRepository;

  @MockBean DischargeInformationRepository dischargeInformationRepository;
  @MockBean DischargingPlanStabilityParametersRepository PlanStabilityParametersRepository;

  @MockBean
  PortDischargingPlanBallastTempDetailsRepository portDischargingPlanBallastTempDetailsRepository;

  @MockBean
  private PortDischargingPlanBallastDetailsRepository portDischargingPlanBallastDetailsRepository;

  @MockBean private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyService;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoService;

  @MockBean
  private DischargeStudyOperationServiceGrpc.DischargeStudyOperationServiceBlockingStub
      dischargeStudyOperationServiceBlockingStub;

  @Value("${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @MockBean private EductionOperationRepository eductionOperationRepository;

  @MockBean private DischargingDriveTankRepository dischargingDriveTankRepository;

  @MockBean PortTideDetailsRepository portTideDetailsRepository;

  // doubt at 594 List<LoadablePlanPortWiseDetails>

  //    @Test
  //     void testBuildDischargeInformation() {
  //        DischargeInformationRequest request =
  // DischargeInformationRequest.newBuilder().setDischargeInfoId(1L).build();
  //        com.cpdss.dischargeplan.domain.DischargeInformationAlgoRequest algoRequest = new
  // DischargeInformationAlgoRequest();
  //
  // Mockito.when(this.dischargeInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong())).thenReturn(getODI());
  //
  // Mockito.when(this.loadableStudyService.getSynopticDataForLoadingPlan(Mockito.any())).thenReturn(getLPCR());
  //
  // Mockito.when(dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(Mockito.anyLong())).thenReturn(getLDBD());
  //
  // Mockito.when(portInfoServiceBlockingStub.getBerthDetailsByPortId(Mockito.any())).thenReturn(getBIR());
  //
  // Mockito.when(this.vesselInfoService.getVesselPumpsByVesselId(Mockito.any())).thenReturn(getVPR());
  //
  // Mockito.when(this.reasonForDelayRepository.findAllByIsActiveTrue()).thenReturn(getLRD());
  //
  // Mockito.when(this.cowPlanDetailRepository.findByDischargingId(Mockito.anyLong())).thenReturn(getCPD());
  //
  // Mockito.when(dischargeStudyOperationServiceBlockingStub.getCowHistoryByVesselId(Mockito.any())).thenReturn(getCHR());
  //
  // Mockito.when(this.loadableStudyService.getLoadablePatternDetailsJson(Mockito.any())).thenReturn(getLPDJ());
  //
  //    }

  private LoadableStudy.LoadablePatternPortWiseDetailsJson getLPDJ() {
    LoadableStudy.LoadablePatternPortWiseDetailsJson json =
        LoadableStudy.LoadablePatternPortWiseDetailsJson.newBuilder()
            .setLoadablePatternDetails("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return json;
  }

  private LoadableStudy.CowHistoryReply getCHR() {
    List<LoadableStudy.CowHistory> list = new ArrayList<>();
    LoadableStudy.CowHistory cowHistory =
        LoadableStudy.CowHistory.newBuilder()
            .setId(1L)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setTankId(1L)
            .setPortId(1L)
            .setVoyageEndDate("1")
            .setCowOptionType(Common.COW_OPTION_TYPE.AUTO)
            .build();
    list.add(cowHistory);
    LoadableStudy.CowHistoryReply cowHistoryReply =
        LoadableStudy.CowHistoryReply.newBuilder()
            .addAllCowHistory(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return cowHistoryReply;
  }

  private Optional<CowPlanDetail> getCPD() {
    CowPlanDetail cowPlanDetail = new CowPlanDetail();
    cowPlanDetail.setCowOperationType(1);
    cowPlanDetail.setCowTankDetails(getSCTD());
    cowPlanDetail.setCowWithDifferentCargos(getSCDC());
    return Optional.of(cowPlanDetail);
  }

  private Set<CowWithDifferentCargo> getSCDC() {
    Set<CowWithDifferentCargo> set = new HashSet<>();
    CowWithDifferentCargo cowWithDifferentCargo = new CowWithDifferentCargo();
    cowWithDifferentCargo.setCargoXid(1L);
    cowWithDifferentCargo.setCargoNominationXid(1L);
    cowWithDifferentCargo.setWashingCargoXid(1L);
    cowWithDifferentCargo.setWashingCargoNominationXid(1L);
    cowWithDifferentCargo.setTankXid(1L);
    set.add(cowWithDifferentCargo);
    return set;
  }

  private Set<CowTankDetail> getSCTD() {
    Set<CowTankDetail> set = new HashSet<>();
    CowTankDetail cowTankDetail = new CowTankDetail();
    cowTankDetail.setCowTypeXid(1);
    cowTankDetail.setTankXid(1L);
    set.add(cowTankDetail);
    return set;
  }

  private List<com.cpdss.dischargeplan.entity.ReasonForDelay> getLRD() {
    List<com.cpdss.dischargeplan.entity.ReasonForDelay> list = new ArrayList<>();
    com.cpdss.dischargeplan.entity.ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setId(1L);
    reasonForDelay.setReason("1");
    list.add(reasonForDelay);
    return list;
  }

  private VesselInfo.VesselPumpsResponse getVPR() {
    List<VesselInfo.VesselComponent> list4 = new ArrayList<>();
    VesselInfo.VesselComponent veselComponent =
        VesselInfo.VesselComponent.newBuilder()
            .setId(1L)
            .setComponentCode("1")
            .setComponentName("1")
            .build();
    list4.add(veselComponent);
    List<VesselInfo.TankType> list3 = new ArrayList<>();
    VesselInfo.TankType tankType = VesselInfo.TankType.newBuilder().setId(1L).build();
    list3.add(tankType);
    List<VesselInfo.VesselComponent> list2 = new ArrayList<>();
    VesselInfo.VesselComponent vesselComponent =
        VesselInfo.VesselComponent.newBuilder()
            .setId(1L)
            .setComponentCode("1")
            .setComponentName("1")
            .setTankTypeId(1L)
            .build();
    list2.add(vesselComponent);
    List<VesselInfo.VesselPump> list1 = new ArrayList<>();
    VesselInfo.VesselPump vesselPump =
        VesselInfo.VesselPump.newBuilder().setId(1L).setPumpName("1").build();
    list1.add(vesselPump);
    List<VesselInfo.PumpType> list = new ArrayList<>();
    VesselInfo.PumpType pumpType = VesselInfo.PumpType.newBuilder().setId(1L).build();
    list.add(pumpType);
    VesselInfo.VesselPumpsResponse response =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .addAllVesselBottomLine(list4)
            .addAllTankType(list3)
            .addAllVesselManifold(list2)
            .addAllVesselPump(list1)
            .addAllPumpType(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private PortInfo.BerthInfoResponse getBIR() {
    List<PortInfo.BerthDetail> list = new ArrayList<>();
    PortInfo.BerthDetail berthDetail =
        PortInfo.BerthDetail.newBuilder().setId(1L).setBerthName("1").setUkc("1").build();
    list.add(berthDetail);
    PortInfo.BerthInfoResponse response =
        PortInfo.BerthInfoResponse.newBuilder()
            .addAllBerths(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private List<DischargingBerthDetail> getLDBD() {
    List<DischargingBerthDetail> list = new ArrayList<>();
    DischargingBerthDetail dischargingBerthDetail = new DischargingBerthDetail();
    dischargingBerthDetail.setId(1L);
    dischargingBerthDetail.setDischargingInformation(getDI());
    dischargingBerthDetail.setBerthXid(1L);
    dischargingBerthDetail.setDepth(new BigDecimal(1));
    dischargingBerthDetail.setMaxManifoldHeight(new BigDecimal(1));
    dischargingBerthDetail.setMaxManifoldPressure(new BigDecimal(1));
    dischargingBerthDetail.setSeaDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setAirDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setIsCargoCirculation(true);
    dischargingBerthDetail.setIsAirPurge(true);
    dischargingBerthDetail.setLineContentDisplacement(new BigDecimal(1));

    list.add(dischargingBerthDetail);
    return list;
  }

  private DischargeInformation getDI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setPortXid(1L);
    return dischargeInformation;
  }

  private Set<DischargingMachineryInUse> getSDMIU() {
    Set<DischargingMachineryInUse> set = new HashSet<>();
    DischargingMachineryInUse dischargingMachineryInUse = new DischargingMachineryInUse();
    dischargingMachineryInUse.setCapacity(new BigDecimal(1));
    dischargingMachineryInUse.setId(1L);
    dischargingMachineryInUse.setMachineXid(1L);
    dischargingMachineryInUse.setMachineTypeXid(1);
    set.add(dischargingMachineryInUse);
    return set;
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setVesselXid(1L);
    //  dischargeInformation.setDischargingMachineryInUses(getSDMIU());
    dischargeInformation.setPortXid(1L);
    dischargeInformation.setVoyageXid(1L);
    dischargeInformation.setId(1L);
    //   dischargeInformation.setDischargingDelays(getSDD());
    dischargeInformation.setSynopticTableXid(1L);
    dischargeInformation.setStartTime(LocalTime.now());
    dischargeInformation.setPortRotationXid(1L);
    dischargeInformation.setInitialTrim(new BigDecimal(1));
    dischargeInformation.setMaximumTrim(new BigDecimal(1));
    dischargeInformation.setFinalTrim(new BigDecimal(1));
    dischargeInformation.setDischargingStagesDuration(getDSD());
    dischargeInformation.setDischargingStagesMinAmount(getDSMA());
    dischargeInformation.setIsTrackGradeSwitching(true);
    dischargeInformation.setIsTrackStartEndStage(true);
    dischargeInformation.setDischargingPatternXid(1L);
    return Optional.of(dischargeInformation);
  }

  private Set<DischargingDelay> getSDD() {
    Set<DischargingDelay> set = new HashSet<>();
    DischargingDelay dischargingDelay = new DischargingDelay();
    dischargingDelay.setCargoXid(1L);
    dischargingDelay.setCargoNominationXid(1L);
    dischargingDelay.setDuration(new BigDecimal(1));
    dischargingDelay.setId(1L);
    dischargingDelay.setQuantity(new BigDecimal(1));
    dischargingDelay.setDischargingInformation(getDI());
    dischargingDelay.setDischargingDelayReasons(getLDDR());
    set.add(dischargingDelay);
    return set;
  }

  private List<DischargingDelayReason> getLDDR() {
    List<DischargingDelayReason> list = new ArrayList<>();
    DischargingDelayReason dischargingDelayReason = new DischargingDelayReason();
    dischargingDelayReason.setReasonForDelay(getRD());
    list.add(dischargingDelayReason);
    return list;
  }

  private ReasonForDelay getRD() {
    ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setId(1L);
    return reasonForDelay;
  }

  private DischargingStagesMinAmount getDSMA() {
    DischargingStagesMinAmount dischargingStagesMinAmount = new DischargingStagesMinAmount();
    dischargingStagesMinAmount.setMinAmount(1);
    return dischargingStagesMinAmount;
  }

  private DischargingStagesDuration getDSD() {
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setDuration(1);
    return dischargingStagesDuration;
  }

  private LoadableStudy.LoadingPlanCommonResponse getLPCR() {
    List<LoadableStudy.LoadingSynopticResponse> list = new ArrayList<>();
    LoadableStudy.LoadingSynopticResponse loadingSynopticResponse =
        LoadableStudy.LoadingSynopticResponse.newBuilder()
            .setTimeOfSunrise("1")
            .setTimeOfSunset("1")
            .setOperationType("ARR")
            .build();
    list.add(loadingSynopticResponse);
    LoadableStudy.LoadingPlanCommonResponse response =
        LoadableStudy.LoadingPlanCommonResponse.newBuilder()
            .addAllSynopticData(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testGetDischargingInformationStatus() {
    Long dischargingInformationStatusId = 1L;
    Mockito.when(
            dischargeInformationStatusRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getODIS());
    try {
      var dischargingInfoStatusOpt =
          this.dischargePlanAlgoService.getDischargingInformationStatus(
              dischargingInformationStatusId);
      assertEquals(Optional.of(1L), Optional.ofNullable(dischargingInfoStatusOpt.get().getId()));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<DischargingInformationStatus> getODIS() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    dischargingInformationStatus.setId(1L);
    return Optional.of(dischargingInformationStatus);
  }

  @Test
  void testCreateDischargingInformationAlgoStatus() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setVesselXid(1L);
    String processId = "1";
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    Integer arrivalDepartutre = 1;
    this.dischargePlanAlgoService.createDischargingInformationAlgoStatus(
        dischargeInformation, processId, dischargingInformationStatus, arrivalDepartutre);
    Mockito.verify(dischargingInformationAlgoStatusRepository)
        .save(Mockito.any(DischargingInformationAlgoStatus.class));
  }

  @Test
  void testSaveDischargingInformationRequestJson() {
    DischargeInformationAlgoRequest algoRequest = new DischargeInformationAlgoRequest();
    Long dischargingInfoId = 1L;

    Mockito.when(this.loadableStudyService.saveJson(Mockito.any())).thenReturn(getSR());
    try {
      this.dischargePlanAlgoService.saveDischargingInformationRequestJson(
          algoRequest, dischargingInfoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.LoadableStudy.StatusReply getSR() {
    com.cpdss.common.generated.LoadableStudy.StatusReply statusReply =
        LoadableStudy.StatusReply.newBuilder().build();
    return statusReply;
  }

  //    @Test
  //      void testSaveDischargingSequenceAndPlan() {
  //        List<String> list2 = new ArrayList<>();
  //        list2.add("1");
  //        Iterable<? extends LoadableStudy.AlgoErrors> list1 = new ArrayList<>();
  //        LoadableStudy.AlgoErrors algoErrors =
  // LoadableStudy.AlgoErrors.newBuilder().setErrorHeading("1").addAllErrorMessages(list2).build();
  //
  //        List list = new ArrayList<>();
  //        DischargingSequence dischargingSequence = new DischargingSequence();
  //        dischargingSequence.setId(1L);
  //        DischargingPlanSaveRequest request =
  // DischargingPlanSaveRequest.newBuilder().addAllAlgoErrors(list1).setProcessId("1").addAllDischargingSequences().setDischargingInfoId(1L).setDischargingPlanDetailsFromAlgo("1").build();
  //
  // Mockito.when(this.dischargeInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong())).thenReturn(getODI());
  //
  // Mockito.when(dischargeInformationStatusRepository.findByIdAndIsActive(Mockito.anyLong(),Mockito.anyBoolean())).thenReturn(getODIS());
  //
  // Mockito.when(dischargeInformationService.getDischargeInformation(Mockito.anyLong())).thenCallRealMethod();
  //
  // ReflectionTestUtils.setField(dischargeInformationService,"dischargeInformationRepository",this.dischargeInformationRepository);
  //        try {
  //            this.dischargePlanAlgoService.saveDischargingSequenceAndPlan(request);
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //
  //    }

  @Test
  void testUpdateDischargingInfoAlgoStatus() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setId(1L);
    String processId = "1";
    Integer conditionType = 1;
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    dischargingInformationStatus.setId(1L);
    this.dischargePlanAlgoService.updateDischargingInfoAlgoStatus(
        dischargingInformation, processId, dischargingInformationStatus, conditionType);
  }

  @Test
  void testBuildBallastValve() {
    DischargingSequence dischargingSequence = new DischargingSequence();
    BallastValve ballastValve = new BallastValve();
    LoadingPlanModels.Valve valve =
        LoadingPlanModels.Valve.newBuilder()
            .setOperation("1")
            .setTime(1)
            .setValveCode("1")
            .setValveType("1")
            .setValveId(1L)
            .build();
    this.dischargePlanAlgoService.buildBallastValve(dischargingSequence, ballastValve, valve);
    assertEquals("1", ballastValve.getValveType());
  }

  @Test
  void testSaveDischargingInfoAlgoStatus() {
    LoadableStudy.AlgoStatusRequest request =
        LoadableStudy.AlgoStatusRequest.newBuilder()
            .setLoadableStudystatusId(1L)
            .setProcesssId("1")
            .build();
    Mockito.when(
            dischargingInformationAlgoStatusRepository.findByProcessIdAndIsActiveTrue(
                Mockito.any()))
        .thenReturn(getODIAS());
    try {
      this.dischargePlanAlgoService.saveDischargingInfoAlgoStatus(request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<DischargingInformationAlgoStatus> getODIAS() {
    DischargingInformationAlgoStatus dischargingInformationAlgoStatus =
        new DischargingInformationAlgoStatus();
    dischargingInformationAlgoStatus.setId(1L);
    return Optional.of(dischargingInformationAlgoStatus);
  }
}
