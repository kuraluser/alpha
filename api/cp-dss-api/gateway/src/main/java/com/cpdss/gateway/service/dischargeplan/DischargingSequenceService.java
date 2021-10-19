/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.generated.LoadableStudy.AlgoErrors;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.VesselIdRequest;
import com.cpdss.common.generated.VesselInfo.VesselPump;
import com.cpdss.common.generated.VesselInfo.VesselPumpsResponse;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.DischargingPlanSaveRequest.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingRate;
import com.cpdss.common.generated.discharge_plan.DischargingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.AlgoError;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlan;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanAlgoRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanPortWiseDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.Event;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceStabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.Pump;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargingSequenceService {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoGrpcService;

  /**
   * FYI:: MOST OF THE METHODS ARE USING THE SAME CLASSES USED FOR LOADING PLAN API. MOST OF THE
   * FIELDS IN THAT CLASSES ARE SAME
   */

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
              if (sequence.getLoadablePlanPortWiseDetails() != null) {
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
}
