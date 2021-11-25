/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.FAILED;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingSequenceService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingSequenceService.class})
public class LoadingSequenceServiceTest {

  @Autowired LoadingSequenceService loadingSequenceService;

  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean LoadingSequenceRepository loadingSequenceRepository;
  @MockBean DeballastingRateRepository deBallastingRateRepository;
  @MockBean CargoLoadingRateRepository cargoLoadingRateRepository;
  @MockBean BallastOperationRepository ballastOperationRepository;
  @MockBean LoadingPlanPortWiseDetailsRepository portWiseDetailsRepository;
  @MockBean LoadingPlanBallastDetailsRepository ballastDetailsRepository;
  @MockBean LoadingPlanRobDetailsRepository robDetailsRepository;
  @MockBean LoadingPlanStabilityParametersRepository stabilityParametersRepository;
  @MockBean LoadingPlanStowageDetailsRepository stowageDetailsRepository;
  @MockBean LoadingSequenceStabiltyParametersRepository loadingSequenceStabilityParamsRepository;
  @MockBean LoadingPlanCommingleDetailsRepository commingleDetailsRepository;
  @MockBean EductionOperationRepository eductionOperationRepository;
  @MockBean LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @Test
  void testGetLoadingSequences() {
    LoadingPlanModels.LoadingSequenceRequest request =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder().setLoadingInfoId(1L).build();
    LoadingPlanModels.LoadingSequenceReply.Builder builder =
        LoadingPlanModels.LoadingSequenceReply.newBuilder();
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(
            loadableStudyGrpcService.getLoadableStudyPortRotationByPortRotationId(Mockito.any()))
        .thenReturn(getPRDR());
    Mockito.when(
            loadingSequenceRepository.findByLoadingInformationAndIsActiveOrderBySequenceNumber(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLS());
    Mockito.when(
            deBallastingRateRepository.findByLoadingSequenceAndIsActiveTrueOrderById(Mockito.any()))
        .thenReturn(getLDR());
    Mockito.when(
            cargoLoadingRateRepository.findByLoadingSequenceAndIsActiveTrueOrderById(Mockito.any()))
        .thenReturn(getLCLR());
    Mockito.when(
            ballastOperationRepository.findByLoadingSequenceAndIsActiveTrueOrderById(Mockito.any()))
        .thenReturn(getLBO());
    Mockito.when(
            portWiseDetailsRepository.findByLoadingSequenceAndIsActiveTrueOrderById(Mockito.any()))
        .thenReturn(getLPPWD());
    Mockito.when(
            deBallastingRateRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLDR());
    Mockito.when(
            ballastDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLPBD());
    Mockito.when(
            robDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLPRD());
    Mockito.when(
            stabilityParametersRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrue(
                Mockito.any()))
        .thenReturn(getOLPSP());
    Mockito.when(
            commingleDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLLPCD());
    Mockito.when(
            stowageDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                Mockito.any()))
        .thenReturn(getLLPSD());
    Mockito.when(eductionOperationRepository.findByLoadingSequenceAndIsActiveTrue(Mockito.any()))
        .thenReturn(getEO());
    Mockito.when(
            loadingSequenceStabilityParamsRepository.findByLoadingInformationAndIsActiveOrderByTime(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLSP());
    ReflectionTestUtils.setField(
        loadingSequenceService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    try {
      this.loadingSequenceService.getLoadingSequences(request, builder);
      assertEquals("1", builder.getLoadingSequenceStabilityParameters(0).getAftDraft());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<LoadingSequenceStabilityParameters> getLLSP() {
    List<LoadingSequenceStabilityParameters> list = new ArrayList<>();
    LoadingSequenceStabilityParameters parameters = new LoadingSequenceStabilityParameters();
    parameters.setAftDraft(new BigDecimal(1));
    parameters.setBendingMoment(new BigDecimal(1));
    parameters.setForeDraft(new BigDecimal(1));
    parameters.setShearingForce(new BigDecimal(1));
    parameters.setTrim(new BigDecimal(1));
    parameters.setList(new BigDecimal(1));
    parameters.setTime(1);
    parameters.setGomValue(new BigDecimal(1));
    list.add(parameters);
    return list;
  }

  private EductionOperation getEO() {
    EductionOperation eductionOperation = new EductionOperation();
    eductionOperation.setTanksUsed("1");
    eductionOperation.setEductorsUsed("1");
    eductionOperation.setId(1L);
    eductionOperation.setEndTime(1);
    eductionOperation.setStartTime(1);
    return eductionOperation;
  }

  private List<LoadingPlanStowageDetails> getLLPSD() {
    List<LoadingPlanStowageDetails> list = new ArrayList<>();
    LoadingPlanStowageDetails details = new LoadingPlanStowageDetails();
    details.setQuantity(new BigDecimal(1));
    details.setQuantityM3(new BigDecimal(1));
    details.setTankXId(1L);
    details.setApi(new BigDecimal(1));
    details.setCargoNominationId(1L);
    details.setTemperature(new BigDecimal(1));
    details.setUllage(new BigDecimal(1));
    details.setAbbreviation("1");
    details.setColorCode("1");

    list.add(details);
    return list;
  }

  private List<LoadingPlanCommingleDetails> getLLPCD() {
    List<LoadingPlanCommingleDetails> list = new ArrayList<>();
    LoadingPlanCommingleDetails details = new LoadingPlanCommingleDetails();
    details.setAbbreviation("1");
    details.setCargo1XId(1L);
    details.setCargo2XId(1L);
    details.setApi(new BigDecimal(1));
    details.setCargoNomination1XId(1L);
    details.setCargoNomination2XId(1L);
    details.setId(1L);
    details.setQuantity(new BigDecimal(1));
    details.setQuantity1M3(new BigDecimal(1));
    details.setTankXId(1L);
    details.setTemperature(new BigDecimal(1));
    details.setUllage(new BigDecimal(1));
    details.setColorCode("1");
    list.add(details);
    return list;
  }

  private Optional<LoadingPlanStabilityParameters> getOLPSP() {
    LoadingPlanStabilityParameters parameters = new LoadingPlanStabilityParameters();
    parameters.setBm(new BigDecimal(1));
    parameters.setSf(new BigDecimal(1));
    parameters.setDraft(new BigDecimal(1));
    return Optional.of(parameters);
  }

  private List<LoadingPlanRobDetails> getLPRD() {
    List<LoadingPlanRobDetails> list = new ArrayList<>();
    LoadingPlanRobDetails details = new LoadingPlanRobDetails();
    details.setTankXId(1L);
    details.setQuantity(new BigDecimal(1));
    details.setQuantityM3(new BigDecimal(1));
    list.add(details);
    return list;
  }

  private List<LoadingPlanBallastDetails> getLPBD() {
    List<LoadingPlanBallastDetails> list = new ArrayList<>();
    LoadingPlanBallastDetails details = new LoadingPlanBallastDetails();
    details.setQuantity(new BigDecimal(1));
    details.setQuantityM3(new BigDecimal(1));
    details.setSounding(new BigDecimal(1));
    details.setColorCode("1");
    details.setTankXId(1L);
    list.add(details);
    return list;
  }

  private List<LoadingPlanPortWiseDetails> getLPPWD() {
    List<LoadingPlanPortWiseDetails> list = new ArrayList<>();
    LoadingPlanPortWiseDetails details = new LoadingPlanPortWiseDetails();
    details.setTime(1);
    list.add(details);
    return list;
  }

  private List<BallastOperation> getLBO() {
    List<BallastOperation> list = new ArrayList<>();
    BallastOperation ballastOperation = new BallastOperation();
    ballastOperation.setEndTime(1);
    ballastOperation.setPumpName("1");
    ballastOperation.setPumpXId(1L);
    ballastOperation.setQuantityM3(new BigDecimal(1));
    ballastOperation.setRate(new BigDecimal(1));
    ballastOperation.setStartTime(1);
    list.add(ballastOperation);
    return list;
  }

  private List<CargoLoadingRate> getLCLR() {
    List<CargoLoadingRate> list = new ArrayList<>();
    CargoLoadingRate cargoLoadingRate = new CargoLoadingRate();
    cargoLoadingRate.setLoadingRate(new BigDecimal(1));
    cargoLoadingRate.setTankXId(1L);
    list.add(cargoLoadingRate);
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

  private List<LoadingSequence> getLLS() {
    List<LoadingSequence> list = new ArrayList<>();
    LoadingSequence loadingSequence = new LoadingSequence();
    loadingSequence.setCargoLoadingRate1(new BigDecimal(1));
    loadingSequence.setCargoLoadingRate2(new BigDecimal(1));
    loadingSequence.setCargoNominationXId(1L);
    loadingSequence.setEndTime(1);
    loadingSequence.setPortXId(1L);
    loadingSequence.setSequenceNumber(1);
    loadingSequence.setStageName("1");
    loadingSequence.setStartTime(1);
    loadingSequence.setToLoadicator(true);
    list.add(loadingSequence);
    return list;
  }

  private Optional<LoadingInformation> getOLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setPortRotationXId(1L);
    loadingInformation.setVesselXId(1L);
    loadingInformation.setVoyageId(1L);
    loadingInformation.setPortXId(1L);
    loadingInformation.setLoadablePatternXId(1L);
    loadingInformation.setStageDuration(getSD());
    return Optional.of(loadingInformation);
  }

  private StageDuration getSD() {
    StageDuration stageDuration = new StageDuration();
    stageDuration.setDuration(2);
    return stageDuration;
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

  @Test
  void testGetLoadingSequencesFailed1() {
    LoadingPlanModels.LoadingSequenceRequest request =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder().setLoadingInfoId(1L).build();
    LoadingPlanModels.LoadingSequenceReply.Builder builder =
        LoadingPlanModels.LoadingSequenceReply.newBuilder();
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(Optional.empty());
    try {
      this.loadingSequenceService.getLoadingSequences(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetLoadingSequencesFailed2() {
    LoadingPlanModels.LoadingSequenceRequest request =
        LoadingPlanModels.LoadingSequenceRequest.newBuilder().setLoadingInfoId(1L).build();
    LoadingPlanModels.LoadingSequenceReply.Builder builder =
        LoadingPlanModels.LoadingSequenceReply.newBuilder();
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(
            loadableStudyGrpcService.getLoadableStudyPortRotationByPortRotationId(Mockito.any()))
        .thenReturn(getPRDRF());
    ReflectionTestUtils.setField(
        loadingSequenceService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    try {
      this.loadingSequenceService.getLoadingSequences(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.PortRotationDetailReply getPRDRF() {
    LoadableStudy.PortRotationDetailReply reply =
        LoadableStudy.PortRotationDetailReply.newBuilder()
            .setPortRotationDetail(
                LoadableStudy.PortRotationDetail.newBuilder().setEta("1").build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }
}
