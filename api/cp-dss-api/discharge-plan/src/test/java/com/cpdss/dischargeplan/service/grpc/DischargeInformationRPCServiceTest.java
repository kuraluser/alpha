/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.*;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {DischargeInformationRPCService.class})
public class DischargeInformationRPCServiceTest {

  @Autowired DischargeInformationRPCService dischargeInformationRPCService;

  @MockBean
  PortDischargingPlanCommingleDetailsRepository portDischargingPlanCommingleDetailsRepository;

  @MockBean DischargeInformationService dischargeInformationService;
  @MockBean DischargeInformationBuilderService informationBuilderService;
  @MockBean DischargeBerthDetailRepository dischargeBerthDetailRepository;
  @MockBean DischargeStageMinAmountRepository stageMinAmountRepository;
  @Autowired DischargeStageDurationRepository dischargeStageDurationRepository;
  @MockBean DischargingDelayReasonRepository dischargingDelayReasonRepository;
  @MockBean DischargingDelayRepository dischargingDelayRepository;
  @MockBean PortDischargingPlanBallastDetailsRepository pdpBallastDetailsRepository;
  @MockBean PortDischargingPlanStowageDetailsRepository pdpStowageDetailsRepository;
  @MockBean PortDischargingPlanRobDetailsRepository pdpRobDetailsRepository;
  @MockBean PortDischargingPlanStabilityParametersRepository pdpStabilityParametersRepository;
  @MockBean DischargingBerthService dischargingBerthService;
  @MockBean DischargingDelayService dischargingDelayService;
  @MockBean DischargingMachineryInUseService dischargingMachineryInUseService;
  @MockBean DischargeStageDurationRepository stageDurationRepository;
  @Autowired DischargeStageMinAmountRepository minAmountRepository;
  @MockBean CowPlanDetailRepository cowPlanDetailRepository;
  @MockBean CowWithDifferentCargoRepository cowWithDifferentCargoRepository;
  @MockBean CowTankDetailRepository cowTankDetailRepository;
  @MockBean DischargeInformationRepository dischargeInformationRepository;
  @Autowired DischargeInformationBuilderService dischargeInformationBuilderService;
  @Autowired DischargeStageMinAmountRepository dischargeStageMinAmountRepository;
  @MockBean ReasonForDelayRepository reasonForDelayRepository;
  @MockBean CowPlanService cowPlanService;

  @Test
  void testGetDischargeInformation() throws GenericServiceException {
    DischargeInformationRequest request =
        DischargeInformationRequest.newBuilder()
            .setVoyageId(1L)
            .setVesselId(1L)
            .setPortRotationId(1L)
            .build();
    StreamObserver<com.cpdss.common.generated.discharge_plan.DischargeInformation>
        responseObserver = StreamRecorder.create();
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    Mockito.when(
            this.dischargeInformationRepository
                .findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getODI());
    Mockito.when(
            this.dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(
                Mockito.anyLong()))
        .thenReturn(getLDBD());
    Mockito.when(dischargeStageDurationRepository.findAllByIsActiveTrue()).thenReturn(getLDSD());
    Mockito.when(dischargeStageMinAmountRepository.findAllByIsActiveTrue()).thenReturn(getLDSMA());
    Mockito.when(this.reasonForDelayRepository.findAllByIsActiveTrue()).thenReturn(getRD());
    Mockito.when(
            this.dischargingDelayRepository.findAllByDischargingInformation_IdAndIsActiveOrderById(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLDD());
    Mockito.when(this.cowPlanDetailRepository.findByDischargingId(Mockito.anyLong()))
        .thenReturn(getOCPD());

    this.dischargeInformationService.getDischargeInformation(request, builder);
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargeInformationRepository",
        this.dischargeInformationRepository);
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargeBerthDetailRepository",
        this.dischargeBerthDetailRepository);
    ReflectionTestUtils.setField(
        dischargeInformationBuilderService,
        "dischargeStageMinAmountRepository",
        this.dischargeStageMinAmountRepository);
    ReflectionTestUtils.setField(
        dischargeInformationBuilderService,
        "reasonForDelayRepository",
        this.reasonForDelayRepository);
    ReflectionTestUtils.setField(
        dischargeInformationBuilderService,
        "dischargingDelayRepository",
        this.dischargingDelayRepository);
    this.dischargeInformationRPCService.getDischargeInformation(request, responseObserver);
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setSynopticTableXid(1L);
    dischargeInformation.setSunriseTime(Timestamp.valueOf("2021-03-12 11:23:11"));
    dischargeInformation.setStartTime(LocalTime.NOON);
    dischargeInformation.setSunsetTime(Timestamp.valueOf("2021-03-12 11:23:11"));
    dischargeInformation.setVoyageXid(1L);
    dischargeInformation.setInitialTrim(new BigDecimal(1));
    dischargeInformation.setMaximumTrim(new BigDecimal(1));
    dischargeInformation.setFinalTrim(new BigDecimal(1));
    dischargeInformation.setInitialDischargingRate(new BigDecimal(1));
    dischargeInformation.setMaxDischargingRate(new BigDecimal(1));
    dischargeInformation.setMinBallastRate(new BigDecimal(1));
    dischargeInformation.setMaxBallastRate(new BigDecimal(1));
    dischargeInformation.setDischargingInformationStatus(getDIS());
    dischargeInformation.setDischargingStagesMinAmount(getDSMA());
    dischargeInformation.setDischargingStagesDuration(getDSD());
    dischargeInformation.setIsTrackStartEndStage(true);
    dischargeInformation.setIsTrackGradeSwitching(true);
    dischargeInformation.setTimeForDryCheck(new BigDecimal(1));
    dischargeInformation.setTimeForSlopDischarging(new BigDecimal(1));
    dischargeInformation.setTimeForFinalStripping(new BigDecimal(1));
    dischargeInformation.setFreshOilWashing(new BigDecimal(1));
    //  dischargeInformation.setDischargingMachineryInUses(getSDMU());
    dischargeInformation.setIsDischargeInformationComplete(true);
    dischargeInformation.setDischargeSlopTankFirst(true);
    dischargeInformation.setDischargeCommingleCargoSeparately(true);
    return Optional.of(dischargeInformation);
  }

  private Optional<CowPlanDetail> getOCPD() {
    CowPlanDetail cowPlanDetail = new CowPlanDetail();
    cowPlanDetail.setCowOperationType(1);
    cowPlanDetail.setCowPercentage(new BigDecimal(1));
    cowPlanDetail.setCowStartTime(new BigDecimal(1));
    cowPlanDetail.setCowEndTime(new BigDecimal(1));
    cowPlanDetail.setEstimatedCowDuration(new BigDecimal(1));
    cowPlanDetail.setCowMaxTrim(new BigDecimal(1));
    cowPlanDetail.setCowMinTrim(new BigDecimal(1));
    cowPlanDetail.setNeedFreshCrudeStorage(true);
    cowPlanDetail.setNeedFlushingOil(true);
    cowPlanDetail.setWashTankWithDifferentCargo(true);
    cowPlanDetail.setCowTankDetails(getCTD());
    cowPlanDetail.setCowWithDifferentCargos(getCD());
    return Optional.of(cowPlanDetail);
  }

  private Set<CowWithDifferentCargo> getCD() {
    Set<CowWithDifferentCargo> set = new HashSet<>();
    CowWithDifferentCargo cowWithDifferentCargo = new CowWithDifferentCargo();
    cowWithDifferentCargo.setIsActive(true);
    cowWithDifferentCargo.setCargoXid(1L);
    cowWithDifferentCargo.setCargoNominationXid(1L);
    cowWithDifferentCargo.setWashingCargoXid(1L);
    cowWithDifferentCargo.setWashingCargoNominationXid(1L);
    cowWithDifferentCargo.setTankXid(1L);
    set.add(cowWithDifferentCargo);
    return set;
  }

  private Set<CowTankDetail> getCTD() {
    Set<CowTankDetail> set = new HashSet<>();
    CowTankDetail cowTankDetail = new CowTankDetail();
    cowTankDetail.setCowTypeXid(1);
    cowTankDetail.setTankXid(1L);
    set.add(cowTankDetail);
    return set;
  }

  private Set<DischargingMachineryInUse> getSDMU() {
    Set<DischargingMachineryInUse> set = new HashSet<>();
    DischargingMachineryInUse dischargingMachineryInUse = new DischargingMachineryInUse();
    dischargingMachineryInUse.setId(1L);
    dischargingMachineryInUse.setMachineXid(1L);
    dischargingMachineryInUse.setMachineTypeXid(1);
    dischargingMachineryInUse.setCapacity(new BigDecimal(1));
    dischargingMachineryInUse.setIsUsing(true);
    set.add(dischargingMachineryInUse);
    return set;
  }

  private List<DischargingDelay> getLDD() {
    List<DischargingDelay> list = new ArrayList<>();
    DischargingDelay dischargingDelay = new DischargingDelay();
    dischargingDelay.setId(1L);
    dischargingDelay.setDuration(new BigDecimal(1));
    dischargingDelay.setQuantity(new BigDecimal(1));
    dischargingDelay.setCargoXid(1L);
    dischargingDelay.setCargoNominationXid(1L);
    dischargingDelay.setDischargingDelayReasons(getLDDR());
    list.add(dischargingDelay);
    return list;
  }

  private List<DischargingDelayReason> getLDDR() {
    List<DischargingDelayReason> list = new ArrayList<>();
    DischargingDelayReason dischargingDelayReason = new DischargingDelayReason();
    dischargingDelayReason.setId(1L);
    list.add(dischargingDelayReason);
    return list;
  }

  private List<ReasonForDelay> getRD() {
    List<ReasonForDelay> list = new ArrayList<>();
    ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setId(1L);
    reasonForDelay.setReason("1");
    list.add(reasonForDelay);
    return list;
  }

  private List<DischargingStagesMinAmount> getLDSMA() {
    List<DischargingStagesMinAmount> list = new ArrayList<>();
    DischargingStagesMinAmount amount = new DischargingStagesMinAmount();
    amount.setMinAmount(1);
    amount.setId(1L);
    list.add(amount);
    return list;
  }

  private List<DischargingStagesDuration> getLDSD() {
    List<DischargingStagesDuration> list = new ArrayList<>();
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    dischargingStagesDuration.setDuration(1);
    list.add(dischargingStagesDuration);
    return list;
  }

  private DischargingStagesDuration getDSD() {
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    return dischargingStagesDuration;
  }

  private DischargingStagesMinAmount getDSMA() {
    DischargingStagesMinAmount amount = new DischargingStagesMinAmount();
    amount.setId(1L);
    return amount;
  }

  private DischargingInformationStatus getDIS() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    dischargingInformationStatus.setId(1L);
    return dischargingInformationStatus;
  }

  private List<DischargingBerthDetail> getLDBD() {
    List<DischargingBerthDetail> list = new ArrayList<>();
    DischargingBerthDetail dischargingBerthDetail = new DischargingBerthDetail();
    dischargingBerthDetail.setId(1L);
    dischargingBerthDetail.setBerthXid(1L);
    dischargingBerthDetail.setDepth(new BigDecimal(1));
    dischargingBerthDetail.setMaxManifoldHeight(new BigDecimal(1));
    dischargingBerthDetail.setMaxManifoldPressure(new BigDecimal(1));
    dischargingBerthDetail.setHoseConnections("1");
    dischargingBerthDetail.setSeaDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setAirDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setIsAirPurge(true);
    dischargingBerthDetail.setIsCargoCirculation(true);
    dischargingBerthDetail.setLineContentDisplacement(new BigDecimal(1));
    dischargingBerthDetail.setSpecialRegulationRestriction("1");
    dischargingBerthDetail.setItemToBeAgreed("1");
    list.add(dischargingBerthDetail);
    return list;
  }
}
