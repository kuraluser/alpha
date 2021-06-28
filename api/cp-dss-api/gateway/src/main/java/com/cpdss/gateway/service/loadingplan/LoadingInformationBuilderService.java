/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingBerths;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelays;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRates;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingToppingOff;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.StageOffsets;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.TrimAllowed;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.ToppingOffSequence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoadingInformationBuilderService {

  public LoadingInformation buildLoadingInformation(LoadingInformationRequest request)
      throws Exception {
    LoadingInformation.Builder builder = LoadingInformation.newBuilder();
    builder.setLoadingDetail(
        buildLoadingDetails(request.getLoadingDetails(), request.getLoadingInfoId()));
    builder.setLoadingRate(
        buildLoadingRates(request.getLoadingRates(), request.getLoadingInfoId()));
    builder.addAllLoadingBerths(buildLoadingBerths(request.getLoadingBerths()));
    LoadingDelay.Builder loadingDelayBuilder = LoadingDelay.newBuilder();
    loadingDelayBuilder.addAllDelays(buildLoadingDelays(request.getLoadingDelays()));
    builder.setLoadingDelays(loadingDelayBuilder.build());
    builder.addAllLoadingMachines(buildLoadingMachineries(request.getLoadingMachineries()));
    builder.addAllToppingOffSequence(buildToppingOffSequences(request.getToppingOffSequence()));
    builder.setLoadingStage(buildLoadingStage(request));
    return builder.build();
  }

  public LoadingDetails buildLoadingDetails(
      com.cpdss.gateway.domain.loadingplan.LoadingDetails loadingDetails, Long loadingInfoId) {
    LoadingDetails.Builder builder = LoadingDetails.newBuilder();
    Optional.ofNullable(loadingInfoId).ifPresent(builder::setId);
    Optional.ofNullable(loadingDetails.getStartTime()).ifPresent(builder::setStartTime);
    Optional.ofNullable(loadingDetails.getTimeOfSunrise()).ifPresent(builder::setTimeOfSunrise);
    Optional.ofNullable(loadingDetails.getTimeOfSunset()).ifPresent(builder::setTimeOfSunset);
    TrimAllowed.Builder trimBuilder = TrimAllowed.newBuilder();
    if (!Optional.ofNullable(loadingDetails.getTrimAllowed()).isEmpty()) {
      Optional.ofNullable(loadingDetails.getTrimAllowed().getFinalTrim())
          .ifPresent(finalTrim -> trimBuilder.setFinalTrim(String.valueOf(finalTrim)));
      Optional.ofNullable(loadingDetails.getTrimAllowed().getInitialTrim())
          .ifPresent(initialTrim -> trimBuilder.setInitialTrim(String.valueOf(initialTrim)));
      Optional.ofNullable(loadingDetails.getTrimAllowed().getMaximumTrim())
          .ifPresent(maxTrim -> trimBuilder.setMaximumTrim(String.valueOf(maxTrim)));
    }
    builder.setTrimAllowed(trimBuilder.build());
    return builder.build();
  }

  public LoadingRates buildLoadingRates(
      com.cpdss.gateway.domain.loadingplan.LoadingRates loadingRates, Long loadingInfoId) {
    LoadingRates.Builder builder = LoadingRates.newBuilder();
    Optional.ofNullable(loadingInfoId).ifPresent(builder::setId);
    Optional.ofNullable(loadingRates.getInitialLoadingRate())
        .ifPresent(initLoadRate -> builder.setInitialLoadingRate(String.valueOf(initLoadRate)));
    Optional.ofNullable(loadingRates.getLineContentRemaining())
        .ifPresent(lineContent -> builder.setLineContentRemaining(String.valueOf(lineContent)));
    Optional.ofNullable(loadingRates.getMaxDeBallastingRate())
        .ifPresent(maxDeBallast -> builder.setMaxDeBallastingRate(String.valueOf(maxDeBallast)));
    Optional.ofNullable(loadingRates.getMaxLoadingRate())
        .ifPresent(maxLoadingRate -> builder.setMaxLoadingRate(String.valueOf(maxLoadingRate)));
    Optional.ofNullable(loadingRates.getMinDeBallastingRate())
        .ifPresent(minDeBallast -> builder.setMinDeBallastingRate(String.valueOf(minDeBallast)));
    Optional.ofNullable(loadingRates.getMinLoadingRate())
        .ifPresent(minLoadingRate -> builder.setMinLoadingRate(String.valueOf(minLoadingRate)));
    Optional.ofNullable(loadingRates.getNoticeTimeRateReduction())
        .ifPresent(
            noticeTimeRate -> builder.setNoticeTimeRateReduction(String.valueOf(noticeTimeRate)));
    Optional.ofNullable(loadingRates.getNoticeTimeStopLoading())
        .ifPresent(
            noticeTimeStop -> builder.setNoticeTimeStopLoading(String.valueOf(noticeTimeStop)));
    Optional.ofNullable(loadingRates.getReducedLoadingRate())
        .ifPresent(reducedRate -> builder.setReducedLoadingRate(String.valueOf(reducedRate)));
    return builder.build();
  }

  public List<LoadingBerths> buildLoadingBerths(List<BerthDetails> berthDetailsList) {
    List<LoadingBerths> berthList = new ArrayList<LoadingBerths>();
    berthDetailsList.forEach(
        berth -> {
          LoadingBerths.Builder builder = LoadingBerths.newBuilder();
          Optional.ofNullable(berth.getAirDraftLimitation())
              .ifPresent(airDraft -> builder.setAirDraftLimitation(String.valueOf(airDraft)));
          Optional.ofNullable(berth.getHoseConnections())
              .ifPresent(hoseConnection -> builder.setHoseConnections(hoseConnection));
          Optional.ofNullable(berth.getId()).ifPresent(builder::setId);
          Optional.ofNullable(berth.getLoadingBerthId()).ifPresent(builder::setBerthId);
          Optional.ofNullable(berth.getLoadingInfoId()).ifPresent(builder::setLoadingInfoId);
          // missing depth, itemsToBeAgreedWith added to domain
          Optional.ofNullable(berth.getMaxManifoldHeight())
              .ifPresent(
                  maxManifoldHeight ->
                      builder.setMaxManifoldHeight(String.valueOf(maxManifoldHeight)));
          Optional.ofNullable(berth.getRegulationAndRestriction())
              .ifPresent(
                  restriction ->
                      builder.setSpecialRegulationRestriction(String.valueOf(restriction)));
          Optional.ofNullable(berth.getSeaDraftLimitation())
              .ifPresent(seaDraft -> builder.setSeaDraftLimitation(String.valueOf(seaDraft)));
          Optional.ofNullable(berth.getItemsToBeAgreedWith())
              .ifPresent(builder::setItemsToBeAgreedWith);
          // maxShipDepth is taken as depth in LoadingBerthDetails table
          Optional.ofNullable(berth.getMaxShipDepth())
              .ifPresent(depth -> builder.setDepth(String.valueOf(depth)));
          berthList.add(builder.build());
        });
    return berthList;
  }

  public List<LoadingDelays> buildLoadingDelays(
      List<com.cpdss.gateway.domain.loadingplan.LoadingDelays> loadingDelayList) {
    List<LoadingDelays> delayList = new ArrayList<LoadingDelays>();
    loadingDelayList.forEach(
        delay -> {
          LoadingDelays.Builder builder = LoadingDelays.newBuilder();
          Optional.ofNullable(delay.getCargoId()).ifPresent(builder::setCargoId);
          Optional.ofNullable(delay.getDuration())
              .ifPresent(duration -> builder.setDuration(String.valueOf(duration)));
          Optional.ofNullable(delay.getId()).ifPresent(builder::setId);
          Optional.ofNullable(delay.getLoadingInfoId()).ifPresent(builder::setLoadingInfoId);
          Optional.ofNullable(delay.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(delay.getReasonForDelayId()).ifPresent(builder::setReasonForDelayId);
          delayList.add(builder.build());
        });
    return delayList;
  }

  public List<LoadingMachinesInUse> buildLoadingMachineries(
      List<com.cpdss.gateway.domain.loadingplan.LoadingMachinesInUse> loadingMachineryList) {
    List<LoadingMachinesInUse> machineries = new ArrayList<LoadingMachinesInUse>();
    loadingMachineryList.forEach(
        machine -> {
          LoadingMachinesInUse.Builder builder = LoadingMachinesInUse.newBuilder();
          Optional.ofNullable(machine.getCapacity())
              .ifPresent(capacity -> builder.setCapacity(String.valueOf(capacity)));
          Optional.ofNullable(machine.getId()).ifPresent(builder::setId);
          Optional.ofNullable(machine.getLoadingInfoId()).ifPresent(builder::setLoadingInfoId);
          Optional.ofNullable(machine.getPumpId()).ifPresent(builder::setPumpId);
          // isUsing missing added to domain
          Optional.ofNullable(machine.getIsUsing()).ifPresent(builder::setIsUsing);
          machineries.add(builder.build());
        });
    return machineries;
  }

  public List<LoadingToppingOff> buildToppingOffSequences(List<ToppingOffSequence> toppingOffList) {
    List<LoadingToppingOff> cargoToppingOffList = new ArrayList<LoadingToppingOff>();
    toppingOffList.forEach(
        topOff -> {
          LoadingToppingOff.Builder builder = LoadingToppingOff.newBuilder();
          Optional.ofNullable(topOff.getCargoAbbreviation())
              .ifPresent(builder::setCargoAbbreviation);
          Optional.ofNullable(topOff.getCargoId()).ifPresent(builder::setCargoId);
          Optional.ofNullable(topOff.getCargoName()).ifPresent(builder::setCargoName);
          Optional.ofNullable(topOff.getColourCode()).ifPresent(builder::setColourCode);
          Optional.ofNullable(topOff.getFillingRatio())
              .ifPresent(fillingRatio -> builder.setFillingRatio(String.valueOf(fillingRatio)));
          Optional.ofNullable(topOff.getId()).ifPresent(builder::setId);
          Optional.ofNullable(topOff.getLoadingInfoId()).ifPresent(builder::setLoadingInfoId);
          Optional.ofNullable(topOff.getOrderNumber()).ifPresent(builder::setOrderNumber);
          Optional.ofNullable(topOff.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(topOff.getRemark()).ifPresent(builder::setRemark);
          Optional.ofNullable(topOff.getTankId()).ifPresent(builder::setTankId);
          Optional.ofNullable(topOff.getUllage())
              .ifPresent(ullage -> builder.setUllage(String.valueOf(ullage)));
          cargoToppingOffList.add(builder.build());
        });
    return cargoToppingOffList;
  }

  public LoadingStages buildLoadingStage(LoadingInformationRequest request) {
    LoadingStages.Builder builder = LoadingStages.newBuilder();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.StageDuration.Builder
        durationBuilder =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.StageDuration.newBuilder();
    StageOffsets.Builder offsetBuilder = StageOffsets.newBuilder();
    if (Optional.ofNullable(request.getStageDuration()).isPresent()) {
      Optional.ofNullable(request.getStageDuration().getId()).ifPresent(durationBuilder::setId);
      Optional.ofNullable(request.getStageDuration().getDuration())
          .ifPresent(durationBuilder::setDuration);
    }
    if (Optional.ofNullable(request.getStageOffset()).isPresent()) {
      Optional.ofNullable(request.getStageOffset().getId()).ifPresent(offsetBuilder::setId);
      Optional.ofNullable(request.getStageOffset().getStageOffsetVal())
          .ifPresent(offsetBuilder::setStageOffsetVal);
    }
    Optional.ofNullable(request.getTrackGradeSwitch()).ifPresent(builder::setTrackGradeSwitch);
    Optional.ofNullable(request.getTrackStartEndStage()).ifPresent(builder::setTrackStartEndStage);
    builder.setDuration(durationBuilder.build());
    builder.setOffset(offsetBuilder.build());
    return builder.build();
  }
}
