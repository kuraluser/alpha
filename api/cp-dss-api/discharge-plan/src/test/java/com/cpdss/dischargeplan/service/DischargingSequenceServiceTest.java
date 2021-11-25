/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      DischargingSequenceService.class,
    })
public class DischargingSequenceServiceTest {

  @Autowired DischargingSequenceService dischargingSequenceService;
  @MockBean DischargeInformationRepository dischargingInformationRepository;
  @MockBean DischargingSequenceRepository dischargingSequenceRepository;
  @MockBean DeballastingRateRepository deBallastingRateRepository;
  @MockBean EductionOperationRepository eductionOperationRepository;
  @MockBean CargoDischargingRateRepository cargoLoadingRateRepository;
  @MockBean BallastOperationRepository ballastOperationRepository;
  @MockBean DischargingPlanPortWiseDetailsRepository portWiseDetailsRepository;
  @MockBean DischargingPlanBallastDetailsRepository ballastDetailsRepository;
  @MockBean DischargingPlanRobDetailsRepository robDetailsRepository;
  @MockBean DischargingPlanStabilityParametersRepository stabilityParametersRepository;
  @MockBean DischargingPlanStowageDetailsRepository stowageDetailsRepository;

  @MockBean
  DischargingSequenceStabiltyParametersRepository dischargingSequenceStabilityParamsRepository;

  @MockBean DischargingPlanCommingleDetailsRepository commingleDetailsRepository;

  @MockBean LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @Test
  void testGetDischargingSequences() {
    LoadingPlanModels.LoadingSequenceRequest request =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder().setLoadingInfoId(1L).build();
    DischargeSequenceReply.Builder builder = DischargeSequenceReply.newBuilder();
    Mockito.when(dischargingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getODI());
    Mockito.when(
            loadableStudyGrpcService.getLoadableStudyPortRotationByPortRotationId(Mockito.any()))
        .thenReturn(getPRDR());
    Mockito.when(
            dischargingSequenceRepository
                .findByDischargeInformationAndIsActiveOrderBySequenceNumber(
                    Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDS());
    Mockito.when(
            deBallastingRateRepository.findByDischargingSequenceAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLDR());

    Mockito.when(
            dischargingSequenceStabilityParamsRepository
                .findByDischargingInformationAndIsActiveOrderByTime(
                    Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDSSP());
    Mockito.when(
            ballastDetailsRepository.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLDPBD());
    Mockito.when(
            robDetailsRepository.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLDPRD());
    Mockito.when(
            stabilityParametersRepository.findByDischargingPlanPortWiseDetailsAndIsActiveTrue(
                Mockito.any()))
        .thenReturn(getODPS());
    Mockito.when(
            commingleDetailsRepository.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLDPCD());
    Mockito.when(
            stowageDetailsRepository.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLDPSD());
    try {
      this.dischargingSequenceService.getDischargingSequences(request, builder);
      assertEquals(1L, builder.getPortId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<DischargingPlanStowageDetails> getLDPSD() {
    List<DischargingPlanStowageDetails> list = new ArrayList<>();
    DischargingPlanStowageDetails dischargingPlanStowageDetails =
        new DischargingPlanStowageDetails();
    dischargingPlanStowageDetails.setQuantity(new BigDecimal(1));
    dischargingPlanStowageDetails.setQuantityM3(new BigDecimal(1));
    dischargingPlanStowageDetails.setTankXId(1L);
    dischargingPlanStowageDetails.setApi(new BigDecimal(1));
    dischargingPlanStowageDetails.setCargoNominationId(1L);
    dischargingPlanStowageDetails.setTemperature(new BigDecimal(1));
    dischargingPlanStowageDetails.setUllage(new BigDecimal(1));
    dischargingPlanStowageDetails.setAbbreviation("1");
    dischargingPlanStowageDetails.setColorCode("1");
    list.add(dischargingPlanStowageDetails);
    return list;
  }

  private List<DischargingPlanCommingleDetails> getLDPCD() {
    List<DischargingPlanCommingleDetails> list = new ArrayList<>();
    DischargingPlanCommingleDetails dischargingPlanCommingleDetails =
        new DischargingPlanCommingleDetails();
    dischargingPlanCommingleDetails.setAbbreviation("1");
    dischargingPlanCommingleDetails.setApi(new BigDecimal(1));
    dischargingPlanCommingleDetails.setCargo1XId(1L);
    dischargingPlanCommingleDetails.setCargo2XId(1L);
    dischargingPlanCommingleDetails.setCargoNomination1XId(1L);
    dischargingPlanCommingleDetails.setCargoNomination2XId(1L);
    dischargingPlanCommingleDetails.setId(1L);
    dischargingPlanCommingleDetails.setQuantity(new BigDecimal(1));
    dischargingPlanCommingleDetails.setQuantityM3(new BigDecimal(1));
    dischargingPlanCommingleDetails.setTankXId(1L);
    dischargingPlanCommingleDetails.setTemperature(new BigDecimal(1));
    dischargingPlanCommingleDetails.setUllage(new BigDecimal(1));
    list.add(dischargingPlanCommingleDetails);
    return list;
  }

  private Optional<DischargingPlanStabilityParameters> getODPS() {
    DischargingPlanStabilityParameters dischargingPlanStabilityParameters =
        new DischargingPlanStabilityParameters();
    dischargingPlanStabilityParameters.setBm(new BigDecimal(1));
    dischargingPlanStabilityParameters.setDraft(new BigDecimal(1));
    dischargingPlanStabilityParameters.setSf(new BigDecimal(1));
    return Optional.of(dischargingPlanStabilityParameters);
  }

  private List<DischargingPlanRobDetails> getLDPRD() {
    List<DischargingPlanRobDetails> list = new ArrayList<>();
    DischargingPlanRobDetails dischargingPlanRobDetails = new DischargingPlanRobDetails();
    dischargingPlanRobDetails.setQuantity(new BigDecimal(1));
    dischargingPlanRobDetails.setQuantityM3(new BigDecimal(1));
    dischargingPlanRobDetails.setTankXId(1L);
    list.add(dischargingPlanRobDetails);
    return list;
  }

  private List<DischargingPlanBallastDetails> getLDPBD() {
    List<DischargingPlanBallastDetails> list = new ArrayList<>();
    DischargingPlanBallastDetails dischargingPlanBallastDetails =
        new DischargingPlanBallastDetails();
    dischargingPlanBallastDetails.setQuantity(new BigDecimal(1));
    dischargingPlanBallastDetails.setQuantityM3(new BigDecimal(1));
    dischargingPlanBallastDetails.setSounding(new BigDecimal(1));
    dischargingPlanBallastDetails.setTankXId(1L);
    dischargingPlanBallastDetails.setColorCode("1");
    list.add(dischargingPlanBallastDetails);
    return list;
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setPortRotationXid(1L);
    dischargeInformation.setDischargingStagesDuration(getDSD());
    dischargeInformation.setVesselXid(1L);
    dischargeInformation.setVoyageXid(1L);
    dischargeInformation.setPortXid(1L);
    dischargeInformation.setDischargingPatternXid(1L);

    return Optional.of(dischargeInformation);
  }

  private LoadableStudy.PortRotationDetailReply getPRDR() {
    LoadableStudy.PortRotationDetailReply reply =
        LoadableStudy.PortRotationDetailReply.newBuilder()
            .setPortRotationDetail(
                LoadableStudy.PortRotationDetail.newBuilder().setEta("1").build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private DischargingStagesDuration getDSD() {
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setDuration(1);
    return dischargingStagesDuration;
  }

  private List<DischargingSequence> getLDS() {
    List<com.cpdss.dischargeplan.entity.DischargingSequence> list = new ArrayList<>();
    com.cpdss.dischargeplan.entity.DischargingSequence dischargingSequence =
        new DischargingSequence();
    dischargingSequence.setCargoDischargingRate1(new BigDecimal(1));
    dischargingSequence.setCargoDischargingRate2(new BigDecimal(1));
    dischargingSequence.setCargoNominationXId(1L);
    dischargingSequence.setEndTime(1);
    dischargingSequence.setPortXId(1L);
    dischargingSequence.setSequenceNumber(1);
    dischargingSequence.setStageName("1");
    dischargingSequence.setStartTime(1);
    dischargingSequence.setToLoadicator(true);
    list.add(dischargingSequence);
    return list;
  }

  private List<DischargingSequenceStabilityParameters> getLDSSP() {
    List<DischargingSequenceStabilityParameters> list = new ArrayList<>();
    DischargingSequenceStabilityParameters dischargingSequenceStabilityParameters =
        new DischargingSequenceStabilityParameters();
    dischargingSequenceStabilityParameters.setAftDraft(new BigDecimal(1));
    dischargingSequenceStabilityParameters.setBendingMoment(new BigDecimal(1));
    dischargingSequenceStabilityParameters.setForeDraft(new BigDecimal(1));
    dischargingSequenceStabilityParameters.setShearingForce(new BigDecimal(1));
    dischargingSequenceStabilityParameters.setTrim(new BigDecimal(1));
    dischargingSequenceStabilityParameters.setList(new BigDecimal(1));
    dischargingSequenceStabilityParameters.setTime(1);
    list.add(dischargingSequenceStabilityParameters);
    return list;
  }

  private List<DeballastingRate> getLDR() {
    List<DeballastingRate> list = new ArrayList<>();
    DeballastingRate deballastingRate = new DeballastingRate();
    deballastingRate.setDeBallastingRate(new BigDecimal(1));
    deballastingRate.setTankXId(1L);
    deballastingRate.setTime(1);
    list.add(deballastingRate);
    return list;
  }
}
