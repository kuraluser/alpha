/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.AlgoErrors;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.PumpType;
import com.cpdss.common.generated.VesselInfo.VesselIdRequest;
import com.cpdss.common.generated.VesselInfo.VesselPump;
import com.cpdss.common.generated.VesselInfo.VesselPumpsResponse;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.DischargePlanPortWiseDetails;
import com.cpdss.common.generated.discharge_plan.DischargeSequenceReply;
import com.cpdss.common.generated.discharge_plan.DischargingPlanSaveRequest.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingRate;
import com.cpdss.common.generated.discharge_plan.DischargingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.EductorOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.AlgoError;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanAlgoRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanPortWiseDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.Ballast;
import com.cpdss.gateway.domain.loadingplan.sequence.BallastPump;
import com.cpdss.gateway.domain.loadingplan.sequence.Cargo;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoLoadingRate;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoStage;
import com.cpdss.gateway.domain.loadingplan.sequence.EductionOperation;
import com.cpdss.gateway.domain.loadingplan.sequence.Event;
import com.cpdss.gateway.domain.loadingplan.sequence.FlowRate;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceStabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.Pump;
import com.cpdss.gateway.domain.loadingplan.sequence.PumpCategory;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategory;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingSequenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** @author sanalkumar.k */
@Slf4j
@Service
public class DischargingSequenceService {

  private final Long cargoPumpType = 1L;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoGrpcService;

  @Autowired LoadingSequenceService loadingSequenceService;

  @Autowired private LoadingPlanGrpcService loadingPlanGrpcService;

  /**
   * @param dischargingPlanAlgoRequest
   * @param vesselId
   * @param loadingInfoId
   * @param builder
   */
  public void buildDischargingPlanSaveRequest(
      DischargingPlanAlgoRequest dischargingPlanAlgoRequest,
      Long vesselId,
      Long loadingInfoId,
      Builder builder) {
    builder.setDischargingInfoId(loadingInfoId);
    builder.setHasLoadicator(dischargingPlanAlgoRequest.getHasLoadicator());
    AtomicInteger sequenceNumber = new AtomicInteger(0);
    VesselIdRequest.Builder vesselReqBuilder = VesselIdRequest.newBuilder();
    vesselReqBuilder.setVesselId(vesselId);
    VesselPumpsResponse pumpsResponse =
        vesselInfoGrpcService.getVesselPumpsByVesselId(vesselReqBuilder.build());
    Optional.ofNullable(dischargingPlanAlgoRequest.getProcessId()).ifPresent(builder::setProcessId);
    if (!pumpsResponse.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      log.error("Failed to fetch Vessel Pumps from Vessel Info MS of vessel {}", vesselId);
    }
    if (dischargingPlanAlgoRequest.getEvents() != null) {
      dischargingPlanAlgoRequest
          .getEvents()
          .forEach(
              event -> {
                this.buildSequences(
                    event, sequenceNumber, pumpsResponse.getVesselPumpList(), builder);
              });
    }

    if (dischargingPlanAlgoRequest.getPlans() != null) {
      dischargingPlanAlgoRequest
          .getPlans()
          .entrySet()
          .forEach(
              entry -> {
                this.buildDischargingPlan(entry, builder);
              });
    }

    if (dischargingPlanAlgoRequest.getStages() != null) {
      dischargingPlanAlgoRequest
          .getStages()
          .forEach(
              stage -> {
                this.buildDischargingSequenceStabilityParam(stage, builder);
              });
    }

    if (dischargingPlanAlgoRequest.getErrors() != null
        && !dischargingPlanAlgoRequest.getErrors().isEmpty()) {
      this.buildAlgoErrors(dischargingPlanAlgoRequest.getErrors(), builder);
    }

    if (dischargingPlanAlgoRequest.getDischargingInformation() != null) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        builder.setDischargingPlanDetailsFromAlgo(
            mapper.writeValueAsString(dischargingPlanAlgoRequest.getDischargingInformation()));
      } catch (JsonProcessingException e) {
        log.error("Could not parse Dischgarging Plan Details from ALGO");
      }
    }
  }

  private void buildAlgoErrors(List<AlgoError> errors, Builder builder) {
    errors.forEach(
        error -> {
          AlgoErrors.Builder errorBuilder = AlgoErrors.newBuilder();
          errorBuilder.setErrorHeading(error.getErrorHeading());
          errorBuilder.addAllErrorMessages(error.getErrorDetails());
          builder.addAlgoErrors(errorBuilder.build());
        });
  }

  private void buildDischargingSequenceStabilityParam(
      LoadingSequenceStabilityParam stage, Builder builder) {
    LoadingPlanStabilityParameters.Builder paramBuilder =
        LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(stage.getAfterDraft())
        .ifPresent(aftDraft -> paramBuilder.setAftDraft(String.valueOf(aftDraft)));
    Optional.ofNullable(stage.getBendinMoment())
        .ifPresent(bm -> paramBuilder.setBm(String.valueOf(bm)));
    Optional.ofNullable(stage.getForeDraft())
        .ifPresent(foreDraft -> paramBuilder.setForeDraft(String.valueOf(foreDraft)));
    Optional.ofNullable(stage.getShearForce())
        .ifPresent(sf -> paramBuilder.setSf(String.valueOf(sf)));
    Optional.ofNullable(stage.getTime())
        .ifPresent(time -> paramBuilder.setTime((new BigDecimal(time)).intValue()));
    Optional.ofNullable(stage.getMeanDraft())
        .ifPresent(meanDraft -> paramBuilder.setMeanDraft(String.valueOf(meanDraft)));
    Optional.ofNullable(stage.getTrim())
        .ifPresent(trim -> paramBuilder.setTrim(String.valueOf(trim)));
    Optional.ofNullable(stage.getList())
        .ifPresent(list -> paramBuilder.setList(String.valueOf(list)));
    builder.addDischargingSequenceStabilityParameters(paramBuilder.build());
  }

  private void buildDischargingPlan(Entry<String, DischargingPlan> entry, Builder builder) {
    Integer conditionType = 0;
    if (entry.getKey().equals("arrival")) {
      conditionType = 1;
    } else {
      conditionType = 2;
    }

    if (entry.getValue() != null) {
      this.buildPortStowage(entry.getValue(), conditionType, builder);
      this.buildPortBallast(entry.getValue(), conditionType, builder);
      this.buildPortRob(entry.getValue(), conditionType, builder);
      this.buildPortStability(entry.getValue(), conditionType, builder);
      this.buildPortCommingle(entry.getValue(), conditionType, builder);
    }
  }

  /**
   * @param value
   * @param conditionType
   * @param builder
   */
  private void buildPortCommingle(DischargingPlan value, Integer conditionType, Builder builder) {
    if (value.getDischargeQuantityCommingleCargoDetails() != null) {
      value
          .getDischargeQuantityCommingleCargoDetails()
          .forEach(
              commingle -> {
                LoadingPlanCommingleDetails.Builder commingleBuilder =
                    LoadingPlanCommingleDetails.newBuilder();
                Optional.ofNullable(commingle.getAbbreviation())
                    .ifPresent(commingleBuilder::setAbbreviation);
                Optional.ofNullable(commingle.getApi()).ifPresent(commingleBuilder::setApi);
                Optional.ofNullable(commingle.getCargo1Id())
                    .ifPresent(commingleBuilder::setCargo1Id);
                Optional.ofNullable(commingle.getCargo2Id())
                    .ifPresent(commingleBuilder::setCargo2Id);
                Optional.ofNullable(commingle.getCargoNomination1Id())
                    .ifPresent(commingleBuilder::setCargoNomination1Id);
                Optional.ofNullable(commingle.getCargoNomination2Id())
                    .ifPresent(commingleBuilder::setCargoNomination2Id);
                Optional.ofNullable(commingle.getColorCode())
                    .ifPresent(commingleBuilder::setColorCode);
                Optional.ofNullable(commingle.getQuantityM3())
                    .ifPresent(commingleBuilder::setQuantityM3);
                Optional.ofNullable(commingle.getQuantityMT())
                    .ifPresent(commingleBuilder::setQuantityMT);
                Optional.ofNullable(commingle.getTankId()).ifPresent(commingleBuilder::setTankId);
                Optional.ofNullable(commingle.getTankName())
                    .ifPresent(commingleBuilder::setTankName);
                Optional.ofNullable(commingle.getTemperature())
                    .ifPresent(commingleBuilder::setTemperature);
                Optional.ofNullable(commingle.getUllage()).ifPresent(commingleBuilder::setUllage);
                commingleBuilder.setConditionType(conditionType);
                builder.addPortDischargingPlanCommingleDetails(commingleBuilder.build());
              });
    }
  }

  private void buildPortStability(DischargingPlan value, Integer conditionType, Builder builder) {
    LoadingPlanStabilityParameters.Builder paramBuilder =
        LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(value.getBendinMoment()).ifPresent(paramBuilder::setBm);
    Optional.ofNullable(value.getShearForce()).ifPresent(paramBuilder::setSf);
    Optional.ofNullable(value.getForeDraft()).ifPresent(paramBuilder::setForeDraft);
    Optional.ofNullable(value.getAfterDraft()).ifPresent(paramBuilder::setAftDraft);
    Optional.ofNullable(value.getMeanDraft()).ifPresent(paramBuilder::setMeanDraft);
    Optional.ofNullable(value.getTrim()).ifPresent(paramBuilder::setTrim);
    Optional.ofNullable(value.getList()).ifPresent(paramBuilder::setList);
    Optional.ofNullable(value.getFreeboard()).ifPresent(paramBuilder::setFreeboard);
    Optional.ofNullable(value.getManifoldHeight()).ifPresent(paramBuilder::setManifoldHeight);
    paramBuilder.setConditionType(conditionType);
    builder.addPortDischargingPlanStabilityParameters(paramBuilder.build());
  }

  private void buildPortRob(DischargingPlan value, Integer conditionType, Builder builder) {
    if (value.getDischargePlanRoBDetails() != null) {
      value
          .getDischargePlanRoBDetails()
          .forEach(
              rob -> {
                LoadingPlanTankDetails.Builder robBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(rob.getQuantityM3()).ifPresent(robBuilder::setQuantityM3);
                Optional.ofNullable(rob.getQuantityMT()).ifPresent(robBuilder::setQuantity);
                Optional.ofNullable(rob.getTankId()).ifPresent(robBuilder::setTankId);
                Optional.ofNullable(rob.getColorCode()).ifPresent(robBuilder::setColorCode);
                Optional.ofNullable(rob.getDensity())
                    .ifPresent(density -> robBuilder.setDensity(density.toString()));
                robBuilder.setConditionType(conditionType);
                builder.addPortDischargingPlanRobDetails(robBuilder.build());
              });
    }
  }

  private void buildPortBallast(DischargingPlan value, Integer conditionType, Builder builder) {
    if (value.getDischargePlanBallastDetails() != null) {
      value
          .getDischargePlanBallastDetails()
          .forEach(
              ballast -> {
                LoadingPlanTankDetails.Builder ballastBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(ballast.getQuantityM3())
                    .ifPresent(ballastBuilder::setQuantityM3);
                Optional.ofNullable(ballast.getQuantityMT()).ifPresent(ballastBuilder::setQuantity);
                Optional.ofNullable(ballast.getSounding()).ifPresent(ballastBuilder::setSounding);
                Optional.ofNullable(ballast.getTankId()).ifPresent(ballastBuilder::setTankId);
                Optional.ofNullable(ballast.getColorCode()).ifPresent(ballastBuilder::setColorCode);
                Optional.ofNullable(ballast.getSg()).ifPresent(ballastBuilder::setSg);
                ballastBuilder.setConditionType(conditionType);
                builder.addPortDischargingPlanBallastDetails(ballastBuilder.build());
              });
    }
  }

  private void buildPortStowage(DischargingPlan value, Integer conditionType, Builder builder) {
    if (value.getDischargePlanStowageDetails() != null) {
      value
          .getDischargePlanStowageDetails()
          .forEach(
              stowage -> {
                LoadingPlanTankDetails.Builder stowageBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(stowage.getApi()).ifPresent(stowageBuilder::setApi);
                Optional.ofNullable(stowage.getCargoNominationId())
                    .ifPresent(stowageBuilder::setCargoNominationId);
                Optional.ofNullable(stowage.getQuantityM3())
                    .ifPresent(stowageBuilder::setQuantityM3);
                Optional.ofNullable(stowage.getQuantityMT()).ifPresent(stowageBuilder::setQuantity);
                Optional.ofNullable(stowage.getTankId()).ifPresent(stowageBuilder::setTankId);
                Optional.ofNullable(stowage.getTemperature())
                    .ifPresent(stowageBuilder::setTemperature);
                Optional.ofNullable(stowage.getUllage()).ifPresent(stowageBuilder::setUllage);
                Optional.ofNullable(stowage.getColorCode()).ifPresent(stowageBuilder::setColorCode);
                Optional.ofNullable(stowage.getAbbreviation())
                    .ifPresent(stowageBuilder::setAbbreviation);
                Optional.ofNullable(stowage.getCargoId()).ifPresent(stowageBuilder::setCargoId);
                stowageBuilder.setConditionType(conditionType);
                builder.addPortDischargingPlanStowageDetails(stowageBuilder.build());
              });
    }
  }

  private void buildSequences(
      Event event, AtomicInteger sequenceNumber, List<VesselPump> pumps, Builder builder) {
    event
        .getSequence()
        .forEach(
            sequence -> {
              DischargingSequence.Builder sequenceBuilder = DischargingSequence.newBuilder();
              sequenceBuilder.setCargoNominationId(event.getCargoNominationId());
              if (sequence.getStageWiseCargoDischargingRates().size() > 0)
                Optional.ofNullable(sequence.getStageWiseCargoDischargingRates().get("0"))
                    .ifPresent(
                        rate1 -> {
                          if (rate1.equalsIgnoreCase("None")) {
                            sequenceBuilder.setCargoDischargingRate1("0");
                          } else sequenceBuilder.setCargoDischargingRate1(rate1);
                        });
              if (sequence.getStageWiseCargoDischargingRates().size() > 1)
                Optional.ofNullable(sequence.getStageWiseCargoDischargingRates().get("1"))
                    .ifPresent(
                        rate2 -> {
                          if (rate2.equalsIgnoreCase("None")) {
                            sequenceBuilder.setCargoDischargingRate2("0");
                          } else sequenceBuilder.setCargoDischargingRate2(rate2);
                        });
              this.buildBallastOperations(sequence.getBallast(), pumps, sequenceBuilder);
              this.buildDeballastingRates(sequence.getDeballastingRates(), sequenceBuilder);
              this.buildDischargingRates(
                  sequence.getTankWiseCargoDischargingRates(), sequenceBuilder);
              if (sequence.getDischargePlanPortWiseDetails() != null) {
                this.buildDischargingPlanPortWiseDetails(
                    sequence.getDischargePlanPortWiseDetails(), sequenceBuilder);
              }
              Optional.ofNullable(sequence.getStage()).ifPresent(sequenceBuilder::setStageName);
              sequenceBuilder.setSequenceNumber(sequenceNumber.incrementAndGet());
              Optional.ofNullable(sequence.getTimeEnd())
                  .ifPresent(
                      timeEnd -> sequenceBuilder.setEndTime((new BigDecimal(timeEnd)).intValue()));
              Optional.ofNullable(sequence.getTimeStart())
                  .ifPresent(
                      timeStart ->
                          sequenceBuilder.setStartTime((new BigDecimal(timeStart)).intValue()));
              Optional.ofNullable(sequence.getToLoadicator())
                  .ifPresent(sequenceBuilder::setToLoadicator);
              builder.addDischargingSequences(sequenceBuilder.build());
            });
  }

  private void buildDischargingPlanPortWiseDetails(
      List<DischargingPlanPortWiseDetails> dischargePlanPortWiseDetails,
      DischargingSequence.Builder sequenceBuilder) {
    dischargePlanPortWiseDetails.forEach(
        portWiseDetails -> {
          LoadingPlanPortWiseDetails.Builder builder = LoadingPlanPortWiseDetails.newBuilder();
          this.buildDeballastingRates(portWiseDetails, builder);
          this.buildLoadingPlanBallastDetails(portWiseDetails, builder);
          this.buildDischargingPlanRobDetails(portWiseDetails, builder);
          this.buildStabilityParams(portWiseDetails, builder);
          this.buildDischargingPlanStowageDetails(portWiseDetails, builder);
          this.buildLoadingPlanCommingleDetails(portWiseDetails, builder);
          Optional.ofNullable(portWiseDetails.getTime())
              .ifPresent(time -> builder.setTime((new BigDecimal(time)).intValue()));
          sequenceBuilder.addDischargingPlanPortWiseDetails(builder.build());
        });
  }

  /**
   * @param portWiseDetails
   * @param builder
   */
  private void buildLoadingPlanCommingleDetails(
      DischargingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getDischargeQuantityCommingleCargoDetails() != null) {
      portWiseDetails
          .getDischargeQuantityCommingleCargoDetails()
          .forEach(
              commingle -> {
                LoadingPlanCommingleDetails.Builder commingleBuilder =
                    LoadingPlanCommingleDetails.newBuilder();
                Optional.ofNullable(commingle.getAbbreviation())
                    .ifPresent(commingleBuilder::setAbbreviation);
                Optional.ofNullable(commingle.getApi()).ifPresent(commingleBuilder::setApi);
                Optional.ofNullable(commingle.getCargo1Id())
                    .ifPresent(commingleBuilder::setCargo1Id);
                Optional.ofNullable(commingle.getCargo2Id())
                    .ifPresent(commingleBuilder::setCargo2Id);
                Optional.ofNullable(commingle.getCargoNomination1Id())
                    .ifPresent(commingleBuilder::setCargoNomination1Id);
                Optional.ofNullable(commingle.getCargoNomination2Id())
                    .ifPresent(commingleBuilder::setCargoNomination2Id);
                Optional.ofNullable(commingle.getColorCode())
                    .ifPresent(commingleBuilder::setColorCode);
                Optional.ofNullable(commingle.getQuantityM3())
                    .ifPresent(commingleBuilder::setQuantityM3);
                Optional.ofNullable(commingle.getQuantityMT())
                    .ifPresent(commingleBuilder::setQuantityMT);
                Optional.ofNullable(commingle.getTankId()).ifPresent(commingleBuilder::setTankId);
                Optional.ofNullable(commingle.getTankName())
                    .ifPresent(commingleBuilder::setTankName);
                Optional.ofNullable(commingle.getTemperature())
                    .ifPresent(commingleBuilder::setTemperature);
                Optional.ofNullable(commingle.getUllage()).ifPresent(commingleBuilder::setUllage);
                builder.addLoadingPlanCommingleDetails(commingleBuilder.build());
              });
    }
  }

  private void buildDischargingPlanStowageDetails(
      DischargingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getDischargePlanStowageDetails() != null) {
      portWiseDetails
          .getDischargePlanStowageDetails()
          .forEach(
              stowage -> {
                LoadingPlanTankDetails.Builder stowageBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(stowage.getApi()).ifPresent(stowageBuilder::setApi);
                Optional.ofNullable(stowage.getCargoNominationId())
                    .ifPresent(stowageBuilder::setCargoNominationId);
                Optional.ofNullable(stowage.getQuantityM3())
                    .ifPresent(stowageBuilder::setQuantityM3);
                Optional.ofNullable(stowage.getQuantityMT()).ifPresent(stowageBuilder::setQuantity);
                Optional.ofNullable(stowage.getTankId()).ifPresent(stowageBuilder::setTankId);
                Optional.ofNullable(stowage.getTemperature())
                    .ifPresent(stowageBuilder::setTemperature);
                Optional.ofNullable(stowage.getUllage()).ifPresent(stowageBuilder::setUllage);
                Optional.ofNullable(stowage.getAbbreviation())
                    .ifPresent(stowageBuilder::setAbbreviation);
                Optional.ofNullable(stowage.getColorCode()).ifPresent(stowageBuilder::setColorCode);
                builder.addLoadingPlanStowageDetails(stowageBuilder.build());
              });
    }
  }

  private void buildStabilityParams(
      DischargingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    LoadingPlanStabilityParameters.Builder paramBuilder =
        LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(portWiseDetails.getBendinMoment()).ifPresent(paramBuilder::setBm);
    Optional.ofNullable(portWiseDetails.getShearForce()).ifPresent(paramBuilder::setSf);
    Optional.ofNullable(portWiseDetails.getForeDraft()).ifPresent(paramBuilder::setForeDraft);
    Optional.ofNullable(portWiseDetails.getAfterDraft()).ifPresent(paramBuilder::setAftDraft);
    Optional.ofNullable(portWiseDetails.getMeanDraft()).ifPresent(paramBuilder::setMeanDraft);
    Optional.ofNullable(portWiseDetails.getTrim()).ifPresent(paramBuilder::setTrim);
    Optional.ofNullable(portWiseDetails.getList()).ifPresent(paramBuilder::setList);
    builder.setLoadingPlanStabilityParameters(paramBuilder.build());
  }

  private void buildDischargingPlanRobDetails(
      DischargingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getDischargePlanRoBDetails() != null) {
      portWiseDetails
          .getDischargePlanRoBDetails()
          .forEach(
              rob -> {
                LoadingPlanTankDetails.Builder robBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(rob.getQuantityM3()).ifPresent(robBuilder::setQuantityM3);
                Optional.ofNullable(rob.getQuantityMT()).ifPresent(robBuilder::setQuantity);
                Optional.ofNullable(rob.getTankId()).ifPresent(robBuilder::setTankId);
                Optional.ofNullable(rob.getColorCode()).ifPresent(robBuilder::setColorCode);
                builder.addLoadingPlanRobDetails(robBuilder.build());
              });
    }
  }

  private void buildLoadingPlanBallastDetails(
      DischargingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getDischargePlanBallastDetails() != null) {
      portWiseDetails
          .getDischargePlanBallastDetails()
          .forEach(
              ballast -> {
                LoadingPlanTankDetails.Builder ballastBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(ballast.getQuantityM3())
                    .ifPresent(ballastBuilder::setQuantityM3);
                Optional.ofNullable(ballast.getQuantityMT()).ifPresent(ballastBuilder::setQuantity);
                Optional.ofNullable(ballast.getSounding()).ifPresent(ballastBuilder::setSounding);
                Optional.ofNullable(ballast.getTankId()).ifPresent(ballastBuilder::setTankId);
                Optional.ofNullable(ballast.getColorCode()).ifPresent(ballastBuilder::setColorCode);
                Optional.ofNullable(ballast.getSg()).ifPresent(ballastBuilder::setSg);
                builder.addLoadingPlanBallastDetails(ballastBuilder.build());
              });
    }
  }

  private void buildDeballastingRates(
      DischargingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getDeballastingRates() != null) {
      portWiseDetails
          .getDeballastingRates()
          .entrySet()
          .forEach(
              entry -> {
                DeBallastingRate.Builder rateBuilder = DeBallastingRate.newBuilder();
                Optional.ofNullable(entry.getKey())
                    .ifPresent(tankId -> rateBuilder.setTankId(Long.valueOf(tankId)));
                Optional.ofNullable(entry.getValue()).ifPresent(rateBuilder::setDeBallastingRate);
                Optional.ofNullable(portWiseDetails.getTime())
                    .ifPresent(time -> rateBuilder.setTime((new BigDecimal(time)).intValue()));
                builder.addDeballastingRates(rateBuilder.build());
              });
    }
  }

  private void buildDischargingRates(
      List<Map<String, String>> loadingRates, DischargingSequence.Builder sequenceBuilder) {
    if (loadingRates != null) {
      loadingRates.forEach(
          section -> {
            section
                .entrySet()
                .forEach(
                    entry -> {
                      DischargingRate.Builder builder = DischargingRate.newBuilder();
                      Optional.ofNullable(entry.getKey())
                          .ifPresent(tankId -> builder.setTankId(Long.valueOf(tankId)));
                      Optional.ofNullable(entry.getValue())
                          .ifPresent(rate -> builder.setDischargingRate(rate));
                      sequenceBuilder.addDischargingRates(builder.build());
                    });
          });
    }
  }

  private void buildDeballastingRates(
      Map<String, String> deballastingRates, DischargingSequence.Builder sequenceBuilder) {
    if (deballastingRates != null) {
      deballastingRates
          .entrySet()
          .forEach(
              entry -> {
                DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
                Optional.ofNullable(entry.getKey())
                    .ifPresent(tankId -> builder.setTankId(Long.valueOf(tankId)));
                Optional.ofNullable(entry.getValue()).ifPresent(builder::setDeBallastingRate);
                sequenceBuilder.addDeBallastingRates(builder.build());
              });
    }
  }

  private void buildBallastOperations(
      Map<String, List<Pump>> ballasts,
      List<VesselPump> pumps,
      DischargingSequence.Builder sequenceBuilder) {
    if (ballasts != null) {
      for (Entry<String, List<Pump>> entry : ballasts.entrySet()) {
        long pumpId = 0;
        if (entry.getKey().equalsIgnoreCase("Gravity")) {
          pumpId = 0;
        } else {
          pumpId = Long.valueOf(entry.getKey());
        }

        if (entry.getValue() != null) {
          for (Pump ballastOperation : entry.getValue()) {
            PumpOperation.Builder builder = PumpOperation.newBuilder();
            Optional.ofNullable(ballastOperation.getQuantityM3())
                .ifPresent(quantityM3 -> builder.setQuantityM3(quantityM3));
            Optional.ofNullable(ballastOperation.getRate())
                .ifPresent(rate -> builder.setRate(rate));
            Optional.ofNullable(ballastOperation.getTimeEnd())
                .ifPresent(timeEnd -> builder.setEndTime((new BigDecimal(timeEnd)).intValue()));
            Optional.ofNullable(ballastOperation.getTimeStart())
                .ifPresent(
                    timeStart -> builder.setStartTime((new BigDecimal(timeStart)).intValue()));
            builder.setPumpXId(pumpId);
            Optional<VesselPump> vesselPumpOpt =
                pumps.stream().filter(pump -> pump.getId() == builder.getPumpXId()).findFirst();
            vesselPumpOpt.ifPresent(vesselPump -> builder.setPumpName(vesselPump.getPumpName()));
            sequenceBuilder.addBallastOperations(builder.build());
          }
        }
      }
    }
  }

  public void buildDischargingSequence(
      Long vesselId, DischargeSequenceReply reply, LoadingSequenceResponse response)
      throws GenericServiceException {
    Map<Long, VesselTankDetail> vesselTankMap = this.getVesselTanks(vesselId);
    Set<Long> cargoNominationIds =
        reply.getDischargeSequencesList().stream()
            .map(sequence -> sequence.getCargoNominationId())
            .collect(Collectors.toSet());
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);

    // Get ballast color details
    List<LoadablePlanBallastDetails> ballastDetails = new ArrayList<>();
    ballastDetails.addAll(
        loadingPlanGrpcService.fetchLoadablePlanBallastDetails(
            reply.getDischargePatternId(), reply.getPortRotationId()));

    List<Cargo> cargos = new ArrayList<Cargo>();
    List<Ballast> ballasts = new ArrayList<Ballast>();
    List<BallastPump> allPumps = new ArrayList<BallastPump>();
    List<BallastPump> gravityList = new ArrayList<BallastPump>();
    BallastPump gravity = new BallastPump();
    List<CargoLoadingRate> cargoDischargeRates = new ArrayList<>();
    List<DischargingRate> dischargeRates = new ArrayList<DischargingRate>();
    Set<Long> stageTickPositions = new LinkedHashSet<Long>();
    List<StabilityParam> stabilityParams = new ArrayList<StabilityParam>();
    Set<TankCategory> cargoTankCategories = new LinkedHashSet<TankCategory>();
    Set<TankCategory> ballastTankCategories = new LinkedHashSet<TankCategory>();
    List<CargoStage> cargoStages = new ArrayList<CargoStage>();
    List<EductionOperation> ballastEduction = new ArrayList<EductionOperation>();
    inititalizeStabilityParams(stabilityParams);

    PortDetail portDetail = getPortInfo(reply.getPortId());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    try {
      response.setMinXAxisValue(
          StringUtils.isEmpty(reply.getStartDate())
              ? new Date().toInstant().toEpochMilli()
              : sdf.parse(reply.getStartDate()).toInstant().toEpochMilli());
      if (!StringUtils.isEmpty(portDetail.getTimezoneOffsetVal())) {
        response.setMinXAxisValue(
            response.getMinXAxisValue()
                + (long)
                    (Float.valueOf(portDetail.getTimezoneOffsetVal()).floatValue()
                        * 60
                        * 60
                        * 1000));
      }
    } catch (ParseException e) {
      e.printStackTrace();
      response.setMinXAxisValue(new Date().toInstant().toEpochMilli());
    }

    Long portEta = response.getMinXAxisValue();
    Integer start = 0;
    Integer temp = 0;
    Long currentCargoNomId = 0L;
    AtomicInteger stageNumber = new AtomicInteger();

    log.info("Populating Discharging Sequences");
    for (DischargingSequence dischargeSeq : reply.getDischargeSequencesList()) {
      if (dischargeSeq.getStageName().equalsIgnoreCase("initialCondition")) {
        start = dischargeSeq.getStartTime();
      }

      if (!currentCargoNomId.equals(dischargeSeq.getCargoNominationId())) {
        stageNumber = new AtomicInteger();
      }

      currentCargoNomId = dischargeSeq.getCargoNominationId();
      buildEduction(dischargeSeq, portEta, ballastEduction);
      // TODO cargo eduction
      for (DischargePlanPortWiseDetails portWiseDetails :
          dischargeSeq.getDischargePlanPortWiseDetailsList()) {
        List<LoadingPlanTankDetails> filteredStowage =
            portWiseDetails.getDischargingPlanStowageDetailsList().stream()
                .filter(
                    stowage ->
                        (stowage.getCargoNominationId() == dischargeSeq.getCargoNominationId())
                            || (stowage.getCargoNominationId() == 0))
                .collect(Collectors.toList());
        for (LoadingPlanTankDetails stowage : filteredStowage) {
          // Adding cargos
          temp =
              this.buildCargoSequence(
                  stowage,
                  vesselTankMap,
                  cargoNomDetails,
                  portEta,
                  start,
                  portWiseDetails,
                  cargos,
                  cargoTankCategories);
        }

        List<LoadingPlanCommingleDetails> filteredComingleDetails =
            portWiseDetails.getDischargingPlanCommingleDetailsList().stream()
                .filter(
                    commingle ->
                        (commingle.getCargoNomination1Id() == dischargeSeq.getCargoNominationId())
                            || (commingle.getCargoNomination2Id()
                                == dischargeSeq.getCargoNominationId()))
                .collect(Collectors.toList());

        for (LoadingPlanCommingleDetails commingle : filteredComingleDetails) {
          // Adding commingle cargos
          temp =
              this.buildCommingleSequence(
                  commingle,
                  currentCargoNomId,
                  vesselTankMap,
                  cargoNomDetails,
                  portEta,
                  start,
                  portWiseDetails,
                  cargos,
                  cargoTankCategories);
        }

        for (LoadingPlanTankDetails ballast :
            portWiseDetails.getDischargingPlanBallastDetailsList()) {
          // Adding ballasts
          temp =
              this.buildBallastSequence(
                  ballast,
                  vesselTankMap,
                  portEta,
                  start,
                  portWiseDetails,
                  ballastDetails,
                  ballasts,
                  ballastTankCategories,
                  dischargeSeq.getStageName(),
                  ballastEduction);
        }

        addCargoStage(
            portWiseDetails,
            cargoNomDetails,
            dischargeSeq.getCargoNominationId(),
            stageNumber,
            portEta,
            start,
            temp,
            cargoStages);

        addCommingleCargoStage(
            portWiseDetails,
            cargoNomDetails,
            dischargeSeq.getCargoNominationId(),
            stageNumber,
            portEta,
            start,
            temp,
            cargoStages);

        start = temp;
        stageTickPositions.add(portEta + (temp * 60 * 1000));
      }

      Integer loadEnd = temp - (temp % (reply.getInterval() * 60)) + (reply.getInterval() * 60);
      response.setMaxXAxisValue(portEta + (loadEnd * 60 * 1000));
      response.setInterval(reply.getInterval());
      // Adding cargo loading rates TODO
      this.buildCargoDischargeRates(dischargeSeq, portEta, stageTickPositions, cargoDischargeRates);

      // Getting all types of pump details in ballast pump list
      dischargeSeq
          .getBallastOperationsList()
          .forEach(
              operation -> {
                BallastPump pump = new BallastPump();
                buildBallastPump(operation, portEta, pump);
                if (pump.getPumpId() == 0L) {
                  gravityList.add(pump);
                } else {
                  allPumps.add(pump);
                }
              });

      dischargeRates.addAll(dischargeSeq.getDischargingRatesList());
    }

    if (gravityList.size() > 0) {
      gravity.setPumpId(0L);
      gravity.setQuantityM3(null);
      gravity.setRate(null);
      gravity.setStart(gravityList.get(0).getStart());
      gravity.setEnd(gravityList.get(gravityList.size() - 1).getEnd());
    }

    // Re using some methods in loading sequence here
    loadingSequenceService.updateCargoLoadingRateIntervals(cargoDischargeRates, stageTickPositions);
    this.buildStabilityParamSequence(reply, portEta, stabilityParams);
    this.buildFlowRates(dischargeRates, vesselTankMap, portEta, response);
    // Building cargo and ballast pumps details
    this.buildPumpDetails(vesselId, response, allPumps);
    loadingSequenceService.removeEmptyBallasts(ballasts, ballastTankCategories, ballastEduction);
    loadingSequenceService.removeEmptyCargos(cargos, cargoTankCategories);
    // loadingSequenceService.removeEmptyBallasts(ballasts, ballastTankCategories);
    //    loadingSequenceService.removeEmptyCargos(cargos, cargoTankCategories);

    response.setCargos(cargos);
    response.setBallasts(ballasts);
    response.setGravity(gravity);
    response.setCargoDischargingRates(cargoDischargeRates);
    response.setStageTickPositions(stageTickPositions);
    response.setStabilityParams(stabilityParams);
    response.setCargoTankCategories(
        cargoTankCategories.stream()
            .sorted(Comparator.comparing(TankCategory::getDisplayOrder))
            .collect(Collectors.toList()));
    List<VesselTankDetail> vesselTankDetails = new ArrayList<>(vesselTankMap.values());
    response.setBallastTankCategories(
        ballastTankCategories.stream()
            .sorted(
                Comparator.comparing(
                    ballast -> vesselTankDetails.indexOf(vesselTankMap.get(ballast.getId()))))
            .collect(Collectors.toList()));
    response.setCargoStages(cargoStages);
  }

  /**
   * @param loadingSequence
   * @param portEta
   * @param ballastEduction
   */
  private void buildEduction(
      DischargingSequence dischargeSeq, Long portEta, List<EductionOperation> ballastEductions) {
    EductorOperation eductorOperation = dischargeSeq.getEductorOperation();
    if (eductorOperation.getEndTime() != 0) {
      EductionOperation ballastEduction = new EductionOperation();
      if (!eductorOperation.getEductorPumpsUsed().isEmpty()) {
        ballastEduction.setPumpSelected(
            List.of(eductorOperation.getEductorPumpsUsed().split(",")).stream()
                .map(pumpId -> Long.valueOf(pumpId))
                .collect(Collectors.toList()));
      }
      if (!eductorOperation.getTanksUsed().isEmpty()) {
        ballastEduction.setTanks(
            List.of(eductorOperation.getTanksUsed().split(",")).stream()
                .map(tankId -> Long.valueOf(tankId))
                .collect(Collectors.toList()));
      }
      ballastEduction.setTimeEnd(portEta + (eductorOperation.getEndTime() * 60 * 1000));
      ballastEduction.setTimeStart(portEta + (eductorOperation.getStartTime() * 60 * 1000));
      ballastEductions.add(ballastEduction);
    }
  }

  /**
   * Method to segregate different pumps and categories
   *
   * @param vesselId
   * @param response
   * @param ballastPumps
   */
  private void buildPumpDetails(
      Long vesselId, LoadingSequenceResponse response, List<BallastPump> ballastPumps) {
    List<PumpCategory> ballastPumpCategories = new ArrayList<>();
    List<PumpCategory> cargoPumpCategories = new ArrayList<>();
    log.info("Populating ballast pumps and categories");
    VesselIdRequest.Builder builder = VesselIdRequest.newBuilder();
    builder.setVesselId(vesselId);
    Set<Long> usedPumpIds =
        ballastPumps.stream().map(pump -> pump.getPumpId()).collect(Collectors.toSet());
    VesselPumpsResponse pumpsResponse =
        vesselInfoGrpcService.getVesselPumpsByVesselId(builder.build());
    if (pumpsResponse.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      pumpsResponse.getVesselPumpList().stream()
          .filter(vesselPump -> usedPumpIds.contains(vesselPump.getId()))
          .forEach(
              vesselPump -> {
                PumpCategory pumpCategory = new PumpCategory();
                pumpCategory.setId(vesselPump.getId());
                pumpCategory.setPumpName(vesselPump.getPumpCode());
                Optional<PumpType> pumpTypeOpt =
                    pumpsResponse.getPumpTypeList().stream()
                        .filter(pumpType -> pumpType.getId() == vesselPump.getId())
                        .findAny();
                pumpTypeOpt.ifPresent(pumpType -> pumpCategory.setPumpType(pumpType.getName()));
                pumpTypeOpt.ifPresent(pumpType -> pumpCategory.setId(pumpType.getId()));
                if (pumpCategory.getId().equals(cargoPumpType)) {
                  cargoPumpCategories.add(pumpCategory);
                } else {
                  ballastPumpCategories.add(pumpCategory);
                }
              });
    }
    response.setBallastPumpCategories(ballastPumpCategories);
    response.setCargoPumpCategories(cargoPumpCategories);
    response.setBallastPumps(
        ballastPumps.stream()
            .filter(pump -> !pump.getPumpId().equals(cargoPumpType))
            .collect(Collectors.toList()));
    response.setCargoPumps(
        ballastPumps.stream()
            .filter(pump -> pump.getPumpId().equals(cargoPumpType))
            .collect(Collectors.toList()));
  }

  private void buildFlowRates(
      List<DischargingRate> dischargingRate,
      Map<Long, VesselTankDetail> vesselTankMap,
      Long portEta,
      LoadingSequenceResponse response) {
    log.info("Populating flow rates");
    List<FlowRate> flowRates = new ArrayList<FlowRate>();
    Set<Long> tankIdList =
        dischargingRate.stream().map(rate -> rate.getTankId()).collect(Collectors.toSet());
    tankIdList.forEach(
        tankId -> {
          FlowRate flowRate = new FlowRate();
          Optional<VesselTankDetail> tankDetailOpt = Optional.ofNullable(vesselTankMap.get(tankId));
          tankDetailOpt.ifPresent(tank -> flowRate.setTankName(tank.getShortName()));
          flowRate.setData(
              dischargingRate.stream()
                  .filter(rate -> rate.getTankId() == tankId)
                  .map(
                      item ->
                          Arrays.asList(
                              portEta + (item.getStartTime() * 60 * 1000),
                              StringUtils.isEmpty(item.getDischargingRate())
                                  ? null
                                  : new BigDecimal(item.getDischargingRate())))
                  .collect(Collectors.toList()));
          Optional<DischargingRate> rateOpt =
              dischargingRate.stream()
                  .filter(rate -> rate.getTankId() == tankId)
                  .sorted(Comparator.comparing(DischargingRate::getEndTime).reversed())
                  .findFirst();

          rateOpt.ifPresent(
              rate ->
                  flowRate
                      .getData()
                      .add(
                          Arrays.asList(
                              portEta + (rate.getEndTime() * 60 * 1000),
                              StringUtils.isEmpty(rate.getDischargingRate())
                                  ? null
                                  : new BigDecimal(rate.getDischargingRate()))));

          flowRates.add(flowRate);
        });
    response.setFlowRates(flowRates);
  }

  private void buildStabilityParamSequence(
      DischargeSequenceReply reply, Long portEta, List<StabilityParam> stabilityParams) {
    List<LoadingPlanStabilityParameters> params =
        reply.getDischargeSequenceStabilityParametersList();
    log.info("Populating stability parameters");
    params.forEach(
        param -> {
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("fore_draft"))
              .forEach(
                  foreDraft ->
                      foreDraft
                          .getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getForeDraft())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("aft_draft"))
              .forEach(
                  aftDraft ->
                      aftDraft
                          .getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getAftDraft())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("bm"))
              .forEach(
                  bm ->
                      bm.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getBm())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("sf"))
              .forEach(
                  sf ->
                      sf.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getSf())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("trim"))
              .forEach(
                  sf ->
                      sf.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getTrim())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("gomValue"))
              .forEach(
                  gom ->
                      gom.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getGomValue())));
        });
  }

  private void buildCargoDischargeRates(
      DischargingSequence dischargingSequence,
      Long portEta,
      Set<Long> stageTickPositions,
      List<CargoLoadingRate> cargoDischargeRates) {
    log.info("Adding cargo loading rate");
    CargoLoadingRate cargoDischargeRate = new CargoLoadingRate();
    cargoDischargeRate.setStartTime(portEta + (dischargingSequence.getStartTime() * 60 * 1000));
    cargoDischargeRate.setEndTime(portEta + (dischargingSequence.getEndTime() * 60 * 1000));
    List<BigDecimal> rateList = new ArrayList<BigDecimal>();
    if (!StringUtils.isEmpty(dischargingSequence.getCargoDischargingRate1())) {
      rateList.add(new BigDecimal(dischargingSequence.getCargoDischargingRate1()));
    }
    if (!StringUtils.isEmpty(dischargingSequence.getCargoDischargingRate2())) {
      rateList.add(new BigDecimal(dischargingSequence.getCargoDischargingRate2()));
    }
    cargoDischargeRate.setDischargingRates(rateList);
    cargoDischargeRates.add(cargoDischargeRate);
  }

  private void addCommingleCargoStage(
      DischargePlanPortWiseDetails portWiseDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      long cargoNominationId,
      AtomicInteger stageNumber,
      Long portEta,
      Integer start,
      Integer end,
      List<CargoStage> cargoStages) {
    CargoStage cargoStage = new CargoStage();
    if (portWiseDetails.getDischargingPlanCommingleDetailsCount() > 0) {
      List<Cargo> cargos = new ArrayList<Cargo>();
      Cargo cargo = new Cargo();
      CargoNominationDetail cargoNomination = cargoNomDetails.get(cargoNominationId);
      if (cargoNomination != null) {
        cargo.setName(cargoNomination.getCargoName());
        cargo.setCargoId(cargoNomination.getCargoId());
        cargo.setAbbreviation(cargoNomination.getAbbreviation());
        cargo.setCargoNominationId(cargoNomination.getId());
        cargo.setColor(cargoNomination.getColor());
      }
      cargo.setApi(
          StringUtils.isEmpty(cargoNomination.getApi())
              ? null
              : new BigDecimal(cargoNomination.getApi()));
      BigDecimal total =
          portWiseDetails.getDischargingPlanCommingleDetailsList().stream()
              .filter(
                  commingle ->
                      ((commingle.getCargoNomination1Id() == cargoNominationId)
                              || (commingle.getCargoNomination2Id() == cargoNominationId))
                          && !StringUtils.isEmpty(commingle.getQuantityMT()))
              .map(commingle -> new BigDecimal(commingle.getQuantityMT()))
              .reduce(
                  new BigDecimal(0),
                  (val1, val2) -> {
                    return val1.add(val2);
                  });
      cargo.setQuantity(total);
      cargos.add(cargo);
      cargoStage.setName("Stage " + stageNumber.incrementAndGet());
      cargoStage.setStart(portEta + (start * 60 * 1000));
      cargoStage.setEnd(portEta + (end * 60 * 1000));
      cargoStage.setCargos(cargos);
      cargoStages.add(cargoStage);
    }
  }

  private void addCargoStage(
      DischargePlanPortWiseDetails portWiseDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long cargoNomId,
      AtomicInteger stageNumber,
      Long portEta,
      Integer start,
      Integer end,
      List<CargoStage> cargoStages) {
    CargoStage cargoStage = new CargoStage();
    if (portWiseDetails.getDischargingPlanStowageDetailsCount() > 0) {
      List<Cargo> cargos = new ArrayList<Cargo>();
      Cargo cargo = new Cargo();
      CargoNominationDetail cargoNomination = cargoNomDetails.get(cargoNomId);
      if (cargoNomination != null) {
        cargo.setName(cargoNomination.getCargoName());
        cargo.setCargoId(cargoNomination.getCargoId());
        cargo.setAbbreviation(cargoNomination.getAbbreviation());
        cargo.setCargoNominationId(cargoNomination.getId());
        cargo.setColor(cargoNomination.getColor());
        cargo.setApi(
            StringUtils.isEmpty(cargoNomination.getApi())
                ? null
                : new BigDecimal(cargoNomination.getApi()));
      }

      BigDecimal total =
          portWiseDetails.getDischargingPlanStowageDetailsList().stream()
              .filter(
                  stowage ->
                      (stowage.getCargoNominationId() == cargoNomId)
                          && !StringUtils.isEmpty(stowage.getQuantity()))
              .map(stowage -> new BigDecimal(stowage.getQuantity()))
              .reduce(
                  new BigDecimal(0),
                  (val1, val2) -> {
                    return val1.add(val2);
                  });
      cargo.setQuantity(total);
      cargos.add(cargo);
      cargoStage.setName("Stage " + stageNumber.incrementAndGet());
      cargoStage.setStart(portEta + (start * 60 * 1000));
      cargoStage.setEnd(portEta + (end * 60 * 1000));
      cargoStage.setCargos(cargos);
      cargoStages.add(cargoStage);
    }
  }

  private Integer buildCargoSequence(
      LoadingPlanTankDetails stowage,
      Map<Long, VesselTankDetail> vesselTankMap,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long portEta,
      Integer start,
      DischargePlanPortWiseDetails portWiseDetails,
      List<Cargo> cargos,
      Set<TankCategory> cargoTankCategories) {
    Cargo cargo = new Cargo();
    Optional<VesselTankDetail> tankDetailOpt =
        Optional.ofNullable(vesselTankMap.get(stowage.getTankId()));
    CargoNominationDetail cargoNomination = cargoNomDetails.get(stowage.getCargoNominationId());
    Integer end =
        buildCargo(stowage, cargo, cargoNomination, portEta, start, portWiseDetails.getTime());
    buildCargoTankCategory(stowage, tankDetailOpt, cargoTankCategories);
    tankDetailOpt.ifPresent(tank -> cargo.setTankName(tank.getShortName()));
    cargos.add(cargo);
    return end;
  }

  private void buildCargoTankCategory(
      LoadingPlanTankDetails stowage,
      Optional<VesselTankDetail> tankDetailOpt,
      Set<TankCategory> cargoTankCategories) {
    TankCategory tankCategory = new TankCategory();
    tankDetailOpt.ifPresent(
        tank -> {
          tankCategory.setTankName(tank.getShortName());
          tankCategory.setDisplayOrder(tank.getTankDisplayOrder());
        });
    if (cargoTankCategories.stream().anyMatch(cargo -> cargo.getId().equals(stowage.getTankId()))) {
      cargoTankCategories.removeIf(cargo -> cargo.getId().equals(stowage.getTankId()));
    }
    tankCategory.setId(stowage.getTankId());
    tankCategory.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    tankCategory.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    cargoTankCategories.add(tankCategory);
  }

  private Integer buildCommingleSequence(
      LoadingPlanCommingleDetails commingle,
      Long currentCargoNominationId,
      Map<Long, VesselTankDetail> vesselTankMap,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long portEta,
      Integer start,
      DischargePlanPortWiseDetails portWiseDetails,
      List<Cargo> cargos,
      Set<TankCategory> cargoTankCategories) {
    Cargo cargo = new Cargo();
    Optional<VesselTankDetail> tankDetailOpt =
        Optional.ofNullable(vesselTankMap.get(commingle.getTankId()));
    Integer end =
        buildCommingleCargo(
            commingle,
            currentCargoNominationId,
            cargoNomDetails,
            cargo,
            portEta,
            start,
            portWiseDetails.getTime());
    buildCommingleCargoTankCategory(commingle, tankDetailOpt, cargoTankCategories);
    tankDetailOpt.ifPresent(tank -> cargo.setTankName(tank.getShortName()));
    cargos.add(cargo);
    return end;
  }

  /**
   * @param commingle
   * @param cargoNomDetails
   * @param currentCargoNominationId
   * @param cargo
   * @param portEta
   * @param start
   * @param time
   * @return
   */
  private Integer buildCommingleCargo(
      LoadingPlanCommingleDetails commingle,
      Long currentCargoNominationId,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Cargo cargo,
      Long portEta,
      Integer start,
      int end) {
    CargoNominationDetail cargoNomination = null;
    if (currentCargoNominationId.equals(commingle.getCargoNomination1Id())
        || currentCargoNominationId.equals(commingle.getCargoNomination2Id())) {
      cargo.setCargoNominationId(currentCargoNominationId);
      cargoNomination = cargoNomDetails.get(currentCargoNominationId);
    }

    cargo.setQuantity(
        StringUtils.isEmpty(commingle.getQuantityMT())
            ? null
            : new BigDecimal(commingle.getQuantityMT()));
    cargo.setTankId(commingle.getTankId());
    cargo.setUllage(
        StringUtils.isEmpty(commingle.getUllage()) ? null : new BigDecimal(commingle.getUllage()));
    if (cargoNomination != null) {
      cargo.setCargoId(cargoNomination.getCargoId());
      cargo.setColor(cargoNomination.getColor());
      cargo.setName(cargoNomination.getCargoName());
      cargo.setAbbreviation(cargoNomination.getAbbreviation());
    }
    cargo.setStart(portEta + (start * 60 * 1000));
    cargo.setEnd(portEta + (end * 60 * 1000));
    cargo.setApi(
        StringUtils.isEmpty(commingle.getApi()) ? null : new BigDecimal(commingle.getApi()));
    cargo.setIsCommingle(true);
    return end;
  }

  /**
   * @param commingle
   * @param tankDetailOpt
   * @param cargoTankCategories
   */
  private void buildCommingleCargoTankCategory(
      LoadingPlanCommingleDetails commingle,
      Optional<VesselTankDetail> tankDetailOpt,
      Set<TankCategory> cargoTankCategories) {
    TankCategory tankCategory = new TankCategory();
    tankDetailOpt.ifPresent(
        tank -> {
          tankCategory.setTankName(tank.getShortName());
          tankCategory.setDisplayOrder(tank.getTankDisplayOrder());
        });
    if (cargoTankCategories.stream()
        .anyMatch(cargo -> cargo.getId().equals(commingle.getTankId()))) {
      cargoTankCategories.removeIf(cargo -> cargo.getId().equals(commingle.getTankId()));
    }
    tankCategory.setId(commingle.getTankId());
    tankCategory.setQuantity(
        StringUtils.isEmpty(commingle.getQuantityMT())
            ? null
            : new BigDecimal(commingle.getQuantityMT()));
    tankCategory.setUllage(
        StringUtils.isEmpty(commingle.getUllage()) ? null : new BigDecimal(commingle.getUllage()));
    cargoTankCategories.add(tankCategory);
  }

  private Integer buildBallastSequence(
      LoadingPlanTankDetails ballast,
      Map<Long, VesselTankDetail> vesselTankMap,
      Long portEta,
      Integer start,
      DischargePlanPortWiseDetails portWiseDetails,
      List<LoadablePlanBallastDetails> ballastDetails,
      List<Ballast> ballasts,
      Set<TankCategory> ballastTankCategories,
      String stageName,
      List<EductionOperation> ballastEduction) {
    Ballast ballastDto = new Ballast();
    Optional<VesselTankDetail> tankDetailOpt =
        Optional.ofNullable(vesselTankMap.get(ballast.getTankId()));
    Optional<LoadablePlanBallastDetails> ballastDetailsOpt =
        ballastDetails.stream()
            .filter(
                details ->
                    (details.getTankId() == ballast.getTankId())
                        && !StringUtils.isEmpty(details.getColorCode()))
            .findFirst();
    Integer end =
        buildBallast(
            ballast,
            ballastDto,
            portEta,
            start,
            portWiseDetails.getTime(),
            stageName,
            ballastEduction);
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(ballast.getTankId());
    tankDetailOpt.ifPresent(
        tank -> {
          ballastDto.setTankName(tank.getShortName());
          tankCategory.setTankName(tank.getShortName());
          tankCategory.setDisplayOrder(tank.getTankDisplayOrder());
        });
    ballastDetailsOpt.ifPresent(details -> ballastDto.setColor(details.getColorCode()));
    ballastTankCategories.add(tankCategory);
    ballasts.add(ballastDto);
    return end;
  }

  /** @param stabilityParams */
  private void inititalizeStabilityParams(List<StabilityParam> stabilityParams) {
    StabilityParam foreDraft = new StabilityParam();
    foreDraft.setName("fore_draft");
    foreDraft.setData(new ArrayList<>());
    StabilityParam aftDraft = new StabilityParam();
    aftDraft.setName("aft_draft");
    aftDraft.setData(new ArrayList<>());
    StabilityParam trim = new StabilityParam();
    trim.setName("trim");
    trim.setData(new ArrayList<>());
    StabilityParam ukc = new StabilityParam();
    ukc.setName("ukc");
    ukc.setData(new ArrayList<>());
    StabilityParam gm = new StabilityParam();
    gm.setName("gm");
    gm.setData(new ArrayList<>());
    StabilityParam bm = new StabilityParam();
    bm.setName("bm");
    bm.setData(new ArrayList<>());
    StabilityParam sf = new StabilityParam();
    sf.setName("sf");
    sf.setData(new ArrayList<>());
    StabilityParam gom = new StabilityParam();
    gom.setName("gomValue");
    gom.setData(new ArrayList<>());
    stabilityParams.addAll(Arrays.asList(foreDraft, aftDraft, trim, ukc, gm, sf, bm, gom));
  }

  private Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds)
      throws GenericServiceException {
    Map<Long, CargoNominationDetail> details = new HashMap<Long, CargoNominationDetail>();
    cargoNominationIds.forEach(
        id -> {
          CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
          builder.setCargoNominationId(id);
          CargoNominationDetailReply reply =
              loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
          if (reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
            log.info("Fetched details of cargo nomination with id {}", id);
            details.put(id, reply.getCargoNominationdetail());
          }
        });

    return details;
  }

  private Map<Long, VesselTankDetail> getVesselTanks(Long vesselId) throws GenericServiceException {
    Map<Long, VesselTankDetail> vesselTankMap = new LinkedHashMap<Long, VesselTankDetail>();
    VesselRequest.Builder builder = VesselRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.addTankCategories(1L);
    builder.addTankCategories(9L);
    builder.addTankCategories(2L);
    VesselReply reply = vesselInfoGrpcService.getVesselTanks(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get vessel tanks",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched vessel tank details of vessel {}", vesselId);
    reply
        .getVesselTanksList()
        .forEach(vesselTank -> vesselTankMap.put(vesselTank.getTankId(), vesselTank));
    return vesselTankMap;
  }

  private PortDetail getPortInfo(Long portId) throws GenericServiceException {
    GetPortInfoByPortIdsRequest.Builder builder = GetPortInfoByPortIdsRequest.newBuilder();
    builder.addId(portId);
    PortReply reply = portInfoGrpcService.getPortInfoByPortIds(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get vessel tanks",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched port details of port {}", portId);
    return reply.getPorts(0);
  }

  private void buildBallastPump(PumpOperation operation, Long portEta, BallastPump ballastPump) {
    ballastPump.setPumpId(operation.getPumpXId());
    ballastPump.setRate(
        StringUtils.isEmpty(operation.getRate()) ? null : new BigDecimal(operation.getRate()));
    ballastPump.setEnd(portEta + (operation.getEndTime() * 60 * 1000));
    ballastPump.setStart(portEta + (operation.getStartTime() * 60 * 1000));
    ballastPump.setQuantityM3(
        StringUtils.isEmpty(operation.getQuantityM3())
            ? null
            : new BigDecimal(operation.getQuantityM3()));
  }

  private Integer buildBallast(
      LoadingPlanTankDetails ballast,
      Ballast ballastDto,
      Long portEta,
      Integer start,
      Integer end,
      String stageName,
      List<EductionOperation> ballastEduction) {
    ballastDto.setQuantity(
        StringUtils.isEmpty(ballast.getQuantity())
            ? BigDecimal.ZERO
            : new BigDecimal(ballast.getQuantity()));
    ballastDto.setSounding(
        StringUtils.isEmpty(ballast.getSounding())
            ? BigDecimal.ZERO
            : new BigDecimal(ballast.getSounding()));
    ballastDto.setStart(portEta + (start * 60 * 1000));
    ballastDto.setEnd(portEta + (end * 60 * 1000));
    ballastDto.setTankId(ballast.getTankId());
    ballastEduction.forEach(
        eduction -> {
          if (stageName.equalsIgnoreCase("dischargingAtMaxRate")
              && eduction.getTanks().contains(ballastDto.getTankId())
              && (ballastDto.getEnd() > eduction.getTimeEnd())) {
            ballastDto.setEnd(eduction.getTimeEnd());
          }
        });
    return end;
  }

  private Integer buildCargo(
      LoadingPlanTankDetails stowage,
      Cargo cargo,
      CargoNominationDetail cargoNomination,
      Long portEta,
      Integer start,
      Integer end) {
    cargo.setCargoNominationId(stowage.getCargoNominationId());
    cargo.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    cargo.setTankId(stowage.getTankId());
    cargo.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    if (cargoNomination != null) {
      cargo.setCargoId(cargoNomination.getCargoId());
      cargo.setColor(cargoNomination.getColor());
      cargo.setName(cargoNomination.getCargoName());
      cargo.setAbbreviation(cargoNomination.getAbbreviation());
    }
    cargo.setStart(portEta + (start * 60 * 1000));
    cargo.setEnd(portEta + (end * 60 * 1000));
    cargo.setApi(StringUtils.isEmpty(stowage.getApi()) ? null : new BigDecimal(stowage.getApi()));
    cargo.setIsCommingle(false);
    return end;
  }
}
