/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.utility;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingBerths;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelays;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRates;
import com.cpdss.loadingplan.domain.algo.BerthDetails;
import com.cpdss.loadingplan.domain.algo.LoadingSequences;
import com.cpdss.loadingplan.domain.algo.ReasonForDelay;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

/** @author pranav.k */
@Log4j2
public class LoadingPlanUtility {

  /**
   * @param berthDetails
   * @return
   */
  public static List<LoadingBerths> buildBerthDetails(List<BerthDetails> berthDetails) {
    log.info("Building berth details of loading plan");
    List<LoadingBerths> berths = new ArrayList<LoadingBerths>();
    if (berthDetails != null) {
      for (BerthDetails berth : berthDetails) {
        LoadingBerths.Builder builder = LoadingBerths.newBuilder();
        Optional.ofNullable(berth.getLoadingBerthId()).ifPresent(builder::setId);
        Optional.ofNullable(berth.getLoadingInfoId()).ifPresent(builder::setLoadingInfoId);
        Optional.ofNullable(berth.getId()).ifPresent(builder::setBerthId);
        Optional.ofNullable(berth.getMaxShipDepth()).ifPresent(v -> builder.setDepth(v.toString()));
        Optional.ofNullable(berth.getSeaDraftLimitation())
            .ifPresent(v -> builder.setSeaDraftLimitation(v.toString()));
        Optional.ofNullable(berth.getAirDraftLimitation())
            .ifPresent(v -> builder.setAirDraftLimitation(v.toString()));
        Optional.ofNullable(berth.getMaxManifoldHeight())
            .ifPresent(v -> builder.setMaxManifoldHeight(v.toString()));
        Optional.ofNullable(berth.getRegulationAndRestriction())
            .ifPresent(v -> builder.setSpecialRegulationRestriction(v.toString()));
        Optional.ofNullable(berth.getItemsToBeAgreedWith())
            .ifPresent(v -> builder.setItemsToBeAgreedWith(v));
        Optional.ofNullable(berth.getHoseConnections())
            .ifPresent(v -> builder.setHoseConnections(v));
        Optional.ofNullable(berth.getLineDisplacement())
            .ifPresent(v -> builder.setLineDisplacement(v.toString()));
        berths.add(builder.build());
      }
    }
    return berths;
  }

  /**
   * @param loadingSequences
   * @return
   */
  public static LoadingDelay buildLoadingDelays(LoadingSequences loadingSequences) {
    log.info("Building loading sequences of loading plan");
    LoadingDelay.Builder builder = LoadingDelay.newBuilder();
    if (loadingSequences != null) {
      if (loadingSequences.getReasonForDelays() != null) {
        for (ReasonForDelay reason : loadingSequences.getReasonForDelays()) {
          DelayReasons.Builder reasonBuilder = DelayReasons.newBuilder();
          reasonBuilder.setId(reason.getId());
          reasonBuilder.setReason(reason.getReason());
          builder.addReasons(reasonBuilder);
        }
      }
      if (loadingSequences.getLoadingDelays() != null) {
        for (com.cpdss.loadingplan.domain.algo.LoadingDelays delay :
            loadingSequences.getLoadingDelays()) {
          LoadingDelays.Builder delayBuilder = LoadingDelays.newBuilder();
          delayBuilder.setId(delay.getId());
          Optional.ofNullable(delay.getLoadingInfoId()).ifPresent(delayBuilder::setLoadingInfoId);
          Optional.ofNullable(delay.getReasonForDelayIds())
              .ifPresent(v -> v.forEach(s -> delayBuilder.addReasonForDelayIds(s)));
          Optional.ofNullable(delay.getDuration())
              .ifPresent(value -> delayBuilder.setDuration(value.toString()));
          Optional.ofNullable(delay.getCargoId()).ifPresent(delayBuilder::setCargoId);
          Optional.ofNullable(delay.getQuantity())
              .ifPresent(value -> delayBuilder.setQuantity(value.toString()));
          Optional.ofNullable(delay.getCargoNominationId())
              .ifPresent(delayBuilder::setCargoNominationId);
          builder.addDelays(delayBuilder);
        }
      }
    }
    return builder.build();
  }

  /**
   * @param loadingRates
   * @return
   */
  public static LoadingRates buildLoadingRates(
      com.cpdss.loadingplan.domain.algo.LoadingRates loadingRates) {
    log.info("Building rates of loading plan");
    LoadingRates.Builder builder = LoadingRates.newBuilder();
    if (loadingRates != null) {
      Optional.ofNullable(loadingRates.getId()).ifPresent(builder::setId);
      Optional.ofNullable(loadingRates.getMaxLoadingRate())
          .ifPresent(v -> builder.setMaxLoadingRate(v.toString()));
      Optional.ofNullable(loadingRates.getReducedLoadingRate())
          .ifPresent(v -> builder.setReducedLoadingRate(v.toString()));
      Optional.ofNullable(loadingRates.getMinDeBallastingRate())
          .ifPresent(v -> builder.setMinDeBallastingRate(v.toString()));
      Optional.ofNullable(loadingRates.getMaxDeBallastingRate())
          .ifPresent(v -> builder.setMaxDeBallastingRate(v.toString()));
      Optional.ofNullable(loadingRates.getNoticeTimeRateReduction())
          .ifPresent(v -> builder.setNoticeTimeRateReduction(v.toString()));
      Optional.ofNullable(loadingRates.getNoticeTimeStopLoading())
          .ifPresent(v -> builder.setNoticeTimeStopLoading(v.toString()));
      Optional.ofNullable(loadingRates.getLineContentRemaining())
          .ifPresent(v -> builder.setLineContentRemaining(v.toString()));
      Optional.ofNullable(loadingRates.getMinLoadingRate())
          .ifPresent(v -> builder.setMinLoadingRate(v.toString()));
      Optional.ofNullable(loadingRates.getShoreLoadingRate())
          .ifPresent(v -> builder.setShoreLoadingRate(v.toString()));
    }
    return builder.build();
  }

  public static String calculateCommingleCargoPercentage(
      String cargoQuantity, String totalQuantity) {
    if (StringUtils.hasLength(cargoQuantity) && StringUtils.hasLength(totalQuantity)) {
      BigDecimal cargoPercentage =
          (new BigDecimal(cargoQuantity))
              .divide(new BigDecimal(totalQuantity), RoundingMode.UNNECESSARY)
              .multiply(new BigDecimal(100));
      return cargoPercentage.toString();
    } else {
      return BigDecimal.ZERO.toString();
    }
  }
}
