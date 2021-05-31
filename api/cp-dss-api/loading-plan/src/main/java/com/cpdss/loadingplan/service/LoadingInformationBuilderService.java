/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.*;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.*;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
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
    // Initial loading rate missing
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
}
