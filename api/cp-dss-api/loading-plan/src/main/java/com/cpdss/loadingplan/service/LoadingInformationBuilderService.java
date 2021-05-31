/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.TIME_FORMATTER;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.entity.CargoToppingOffSequence;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoadingInformationBuilderService {

  public LoadingDetails buildLoadingDetailsMessage(LoadingInformation var1) {
    LoadingDetails.Builder builder = LoadingDetails.newBuilder();
    Optional.of(var1.getId()).ifPresent(builder::setId);
    Optional.ofNullable(var1.getSunriseTime())
        .ifPresent(v -> builder.setTimeOfSunrise(TIME_FORMATTER.format(v)));
    Optional.ofNullable(var1.getSunsetTime())
        .ifPresent(v -> builder.setTimeOfSunset(TIME_FORMATTER.format(v)));
    Optional.ofNullable(var1.getStartTime())
        .ifPresent(v -> builder.setStartTime(TIME_FORMATTER.format(v)));

    TrimAllowed.Builder builder1 = TrimAllowed.newBuilder();
    Optional.ofNullable(var1.getInitialTrim())
        .ifPresent(v -> builder1.setInitialTrim(v.toString()));
    Optional.ofNullable(var1.getMaximumTrim())
        .ifPresent(v -> builder1.setMaximumTrim(v.toString()));
    Optional.ofNullable(var1.getFinalTrim()).ifPresent(v -> builder1.setFinalTrim(v.toString()));

    builder.setTrimAllowed(builder1.build());
    return builder.build();
  }

  public LoadingRates buildLoadingRateMessage(LoadingInformation var1) {
    LoadingRates.Builder builder = LoadingRates.newBuilder();
    Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
    Optional.ofNullable(var1.getInitialLoadingRate())
        .ifPresent(v -> builder.setInitialLoadingRate(v.toString()));
    Optional.ofNullable(var1.getMaxLoadingRate())
        .ifPresent(v -> builder.setMaxLoadingRate(v.toString()));
    Optional.ofNullable(var1.getReducedLoadingRate())
        .ifPresent(v -> builder.setReducedLoadingRate(v.toString()));
    Optional.ofNullable(var1.getMinDeBallastRate())
        .ifPresent(v -> builder.setMinDeBallastingRate(v.toString()));
    Optional.ofNullable(var1.getMaxDeBallastRate())
        .ifPresent(v -> builder.setMinDeBallastingRate(v.toString()));
    Optional.ofNullable(var1.getNoticeTimeForRateReduction())
        .ifPresent(v -> builder.setNoticeTimeRateReduction(v.toString()));
    Optional.ofNullable(var1.getNoticeTimeForStopLoading())
        .ifPresent(v -> builder.setNoticeTimeStopLoading(v.toString()));
    Optional.ofNullable(var1.getLineContentRemaining())
        .ifPresent(v -> builder.setLineContentRemaining(v.toString()));
    return builder.build();
  }

  public List<LoadingBerths> buildLoadingBerthsMessage(List<LoadingBerthDetail> list) {
    List<LoadingBerths> berths = new ArrayList<>();
    for (LoadingBerthDetail var1 : list) {
      LoadingBerths.Builder builder = LoadingBerths.newBuilder();
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getLoadingInformation().getId())
          .ifPresent(builder::setLoadingInfoId);
      Optional.ofNullable(var1.getBerthXId()).ifPresent(builder::setBerthId);
      Optional.ofNullable(var1.getDepth()).ifPresent(v -> builder.setDepth(v.toString()));
      Optional.ofNullable(var1.getSeaDraftLimitation())
          .ifPresent(v -> builder.setSeaDraftLimitation(v.toString()));
      Optional.ofNullable(var1.getAirDraftLimitation())
          .ifPresent(v -> builder.setAirDraftLimitation(v.toString()));
      Optional.ofNullable(var1.getMaxManifoldHeight())
          .ifPresent(v -> builder.setMaxManifoldHeight(v.toString()));
      Optional.ofNullable(var1.getSpecialRegulationRestriction())
          .ifPresent(v -> builder.setSpecialRegulationRestriction(v.toString()));
      berths.add(builder.build());
    }
    return berths;
  }

  public List<LoadingMachinesInUse> buildLoadingMachineryInUseMessage(
      List<LoadingMachineryInUse> list) {
    List<LoadingMachinesInUse> machinery = new ArrayList<>();
    for (LoadingMachineryInUse var1 : list) {
      LoadingMachinesInUse.Builder builder = LoadingMachinesInUse.newBuilder();
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getLoadingInformation().getId())
          .ifPresent(builder::setLoadingInfoId);
      Optional.ofNullable(var1.getPumpXId()).ifPresent(builder::setPumpId);
      Optional.ofNullable(var1.getCapacity())
          .ifPresent(value -> builder.setCapacity(value.toString()));
      machinery.add(builder.build());
    }
    return machinery;
  }

  public LoadingStages buildLoadingStageMessage(
      LoadingInformation var1,
      List<StageOffset> list3,
      List<com.cpdss.loadingplan.entity.StageDuration> list4) {
    LoadingStages.Builder builder = LoadingStages.newBuilder();
    Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
    Optional.ofNullable(var1.getStageOffset().getId())
        .ifPresent(value -> builder.setStageOffset(value.intValue()));
    Optional.ofNullable(var1.getStageDuration().getId())
        .ifPresent(value -> builder.setStageDuration(value.intValue()));
    Optional.ofNullable(var1.getTrackStartEndStage()).ifPresent(builder::setTrackStartEndStage);
    Optional.ofNullable(var1.getTrackGradeSwitch()).ifPresent(builder::setTrackGradeSwitch);

    // Set Offset Master
    builder.addAllStageOffsets(this.buildStageOffsetMasterMessage(list3));
    builder.addAllStageDurations(this.buildStageDurationMasterMessage(list4));
    return builder.build();
  }

  public List<LoadingPlanModels.StageDuration> buildStageDurationMasterMessage(
      List<com.cpdss.loadingplan.entity.StageDuration> list) {
    List<LoadingPlanModels.StageDuration> durations = new ArrayList<>();
    for (com.cpdss.loadingplan.entity.StageDuration dr : list) {
      LoadingPlanModels.StageDuration.Builder builder =
          LoadingPlanModels.StageDuration.newBuilder();
      builder.setId(dr.getId());
      builder.setDuration(dr.getDuration());
      durations.add(builder.build());
    }
    return durations;
  }

  public List<StageOffsets> buildStageOffsetMasterMessage(List<StageOffset> list) {
    List<StageOffsets> offsets = new ArrayList<>();
    for (StageOffset offset : list) {
      StageOffsets.Builder builder = StageOffsets.newBuilder();
      builder.setId(offset.getId());
      builder.setStageOffsetVal(offset.getStageOffsetVal());
      offsets.add(builder.build());
    }
    return offsets;
  }

  public LoadingDelay buildLoadingDelayMessage(
      List<ReasonForDelay> list, List<com.cpdss.loadingplan.entity.LoadingDelay> list6) {
    LoadingDelay.Builder builder = LoadingDelay.newBuilder();
    for (ReasonForDelay var : list) {
      DelayReasons.Builder builder1 = DelayReasons.newBuilder();
      builder1.setId(var.getId());
      builder1.setReason(var.getReason());
      builder.addReasons(builder1);
    }
    for (com.cpdss.loadingplan.entity.LoadingDelay var : list6) {
      LoadingDelays.Builder builder1 = LoadingDelays.newBuilder();
      builder1.setId(var.getId());
      Optional.ofNullable(var.getLoadingInformation().getId())
          .ifPresent(builder1::setLoadingInfoId);
      Optional.ofNullable(var.getReasonForDelay().getId()).ifPresent(builder1::setReasonForDelayId);
      Optional.ofNullable(var.getDuration())
          .ifPresent(value -> builder1.setDuration(value.toString()));
      Optional.ofNullable(var.getCargoXId()).ifPresent(builder1::setCargoId);
      Optional.ofNullable(var.getQuantity())
          .ifPresent(value -> builder1.setDuration(value.toString()));
      builder.addDelays(builder1);
    }
    // User Entry Delay TO DO
    // Cargo List for drop down TO DO
    return builder.build();
  }

  public List<LoadingToppingOff> buildToppingOffMessage(List<CargoToppingOffSequence> list) {
    List<LoadingToppingOff> toppingOffs = new ArrayList<>();
    for (CargoToppingOffSequence var1 : list) {
      LoadingToppingOff.Builder builder = LoadingToppingOff.newBuilder();
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getLoadingInformation().getId())
          .ifPresent(builder::setLoadingInfoId);
      Optional.ofNullable(var1.getOrderNumber()).ifPresent(builder::setOrderNumber);
      Optional.ofNullable(var1.getTankXId()).ifPresent(builder::setTankId);
      Optional.ofNullable(var1.getCargoXId()).ifPresent(builder::setCargoId);
      Optional.ofNullable(var1.getUllage()).ifPresent(v -> builder.setUllage(v.toString()));
      Optional.ofNullable(var1.getQuantity())
          .ifPresent(value -> builder.setQuantity(value.toString()));
      Optional.ofNullable(var1.getFillingRatio())
          .ifPresent(value -> builder.setFillingRatio(value.toString()));
      Optional.ofNullable(var1.getRemarks()).ifPresent(builder::setRemark);
      // cargo name, short name, colour need to add
    }
    return toppingOffs;
  }
}
