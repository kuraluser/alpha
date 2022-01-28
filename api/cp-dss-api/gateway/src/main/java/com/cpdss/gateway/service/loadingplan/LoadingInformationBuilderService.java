/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
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
import com.cpdss.gateway.domain.loadingplan.CargoApiTempDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.ToppingOffSequence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingInformationBuilderService {

  @GrpcClient("loadingInformationService")
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  /**
   * Multi Thread save implemented for Save Loading Info
   *
   * <p>If Any issue found during this API, need to change to Synchronized Save Operation (save one
   * by one).
   *
   * @param request
   * @return
   * @throws Exception
   */
  public LoadingPlanModels.LoadingInfoSaveResponse saveDataAsync(LoadingInformationRequest request)
      throws Exception {

    LoadingInformation.Builder builder = LoadingInformation.newBuilder();
    List<Callable<LoadingPlanModels.LoadingInfoSaveResponse>> callableTasks = new ArrayList<>();
    builder.setLoadingInfoId(request.getLoadingInfoId());
    builder.setSynopticTableId(request.getSynopticalTableId());
    builder.setIsLoadingInfoComplete(request.getIsLoadingInfoComplete());

    // Loading Info Case 1 - Details
    if (request.getLoadingDetails() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t1 =
          () -> {
            builder.setLoadingDetail(
                buildLoadingDetails(request.getLoadingDetails(), request.getLoadingInfoId()));
            return loadingInfoServiceBlockingStub.saveLoadingInformation(builder.build());
          };
      callableTasks.add(t1);
    }

    // Loading Info Case 2 - Stages
    if (request.getLoadingStages() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t2 =
          () -> {
            builder.setLoadingStage(buildLoadingStage(request));
            return loadingInfoServiceBlockingStub.saveLoadingInfoStages(builder.build());
          };
      callableTasks.add(t2);
    }

    // Loading Info Case 3 - Rates
    if (request.getLoadingRates() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t3 =
          () -> {
            builder.setLoadingRate(
                buildLoadingRates(request.getLoadingRates(), request.getLoadingInfoId()));
            return loadingInfoServiceBlockingStub.saveLoadingInfoRates(builder.build());
          };
      callableTasks.add(t3);
    }

    // Loading Info Case 4 - Berths
    if (request.getLoadingBerths() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t4 =
          () -> {
            builder.addAllLoadingBerths(
                buildLoadingBerths(request.getLoadingBerths(), request.getLoadingInfoId()));
            return loadingInfoServiceBlockingStub.saveLoadingInfoBerths(builder.build());
          };
      callableTasks.add(t4);
    }

    // Loading Info Case 5 - Delays
    if (request.getLoadingDelays() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t5 =
          () -> {
            LoadingDelay.Builder loadingDelayBuilder = LoadingDelay.newBuilder();
            loadingDelayBuilder.addAllDelays(
                buildLoadingDelays(request.getLoadingDelays(), request.getLoadingInfoId()));
            builder.setLoadingDelays(loadingDelayBuilder.build());
            return loadingInfoServiceBlockingStub.saveLoadingInfoDelays(builder.build());
          };
      callableTasks.add(t5);
    }

    // Loading Info Case 6 - Machines
    if (request.getLoadingMachineries() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t6 =
          () -> {
            builder.addAllLoadingMachines(
                buildLoadingMachineries(
                    request.getLoadingMachineries(), request.getLoadingInfoId()));
            return loadingInfoServiceBlockingStub.saveLoadingInfoMachinery(builder.build());
          };
      callableTasks.add(t6);
    }

    // Loading Info Case 7 - Cargo To Be Loaded
    if (request.getCargoToBeLoaded() != null) {
      Callable<LoadingPlanModels.LoadingInfoSaveResponse> t7 =
          () -> {
            buildCargoToBeLoaded(request.getCargoToBeLoaded(), builder);
            return loadingInfoServiceBlockingStub.saveCargoToBeLoaded(builder.build());
          };
      callableTasks.add(t7);
    }

    // builder.addAllToppingOffSequence(buildToppingOffSequences(request.getToppingOffSequence()));

    ExecutorService executorService =
        new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    List<Future<LoadingPlanModels.LoadingInfoSaveResponse>> futures =
        executorService.invokeAll(callableTasks);

    List<Future<LoadingPlanModels.LoadingInfoSaveResponse>> data =
        futures.stream()
            .filter(
                v -> {
                  try {
                    if (v.get().getLoadingInfoId() > 0) {
                      return true;
                    }
                    log.error("Failed to save Thread {}", v.get());
                  } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                  }
                  return false;
                })
            .collect(Collectors.toList());
    log.info(
        "Save Loading info, Save Request Count - {}, Response Count {}",
        callableTasks.size(),
        data.size());
    return data.isEmpty() ? null : data.stream().findFirst().get().get();
  }

  /**
   * Method to build Cargo to be loaded
   *
   * @param cargoToBeLoaded CargoApiTempDetails input
   * @param builder LoadingInformation.Builder grpc model object builder
   */
  public void buildCargoToBeLoaded(
      CargoApiTempDetails cargoToBeLoaded, LoadingInformation.Builder builder) {

    log.info("Inside buildCargoToBeLoaded method!");

    cargoToBeLoaded
        .getLoadableQuantityCargoDetails()
        .forEach(
            loadableQuantityCargoDetails -> {
              LoadableStudy.LoadableQuantityCargoDetails.Builder cargoDetailBuilder =
                  LoadableStudy.LoadableQuantityCargoDetails.newBuilder();

              cargoDetailBuilder.setEstimatedAPI(loadableQuantityCargoDetails.getEstimatedAPI());
              cargoDetailBuilder.setEstimatedTemp(loadableQuantityCargoDetails.getEstimatedTemp());
              cargoDetailBuilder.setMaxLoadingRate(
                  loadableQuantityCargoDetails.getMaxLoadingRate());
              cargoDetailBuilder.setCargoNominationId(
                  loadableQuantityCargoDetails.getCargoNominationId());

              builder.addLoadableQuantityCargoDetails(cargoDetailBuilder.build());
            });
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
    Optional.ofNullable(loadingDetails.getCommonDate())
        .ifPresent(commonDate -> builder.setCommonDate(String.valueOf(commonDate)));
    Optional.ofNullable(loadingDetails.getSlopQuantity())
        .ifPresent(slopQuantity -> builder.setSlopQuantity(slopQuantity.toString()));
    return builder.build();
  }

  public LoadingRates buildLoadingRates(
      com.cpdss.gateway.domain.loadingplan.LoadingRates loadingRates, Long loadingInfoId) {
    LoadingRates.Builder builder = LoadingRates.newBuilder();
    Optional.ofNullable(loadingInfoId).ifPresent(builder::setId);
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
    Optional.ofNullable(loadingRates.getShoreLoadingRate())
        .ifPresent(v -> builder.setShoreLoadingRate(v.toString()));
    return builder.build();
  }

  public List<LoadingBerths> buildLoadingBerths(
      List<BerthDetails> berthDetailsList, Long loadingInfoId) {
    List<LoadingBerths> berthList = new ArrayList<LoadingBerths>();
    berthDetailsList.forEach(
        berth -> {
          LoadingBerths.Builder builder = LoadingBerths.newBuilder();
          Optional.ofNullable(berth.getAirDraftLimitation())
              .ifPresent(airDraft -> builder.setAirDraftLimitation(String.valueOf(airDraft)));
          Optional.ofNullable(berth.getHoseConnections())
              .ifPresent(hoseConnection -> builder.setHoseConnections(hoseConnection));
          Optional.ofNullable(berth.getBerthId()).ifPresent(builder::setBerthId);
          Optional.ofNullable(berth.getLoadingBerthId()).ifPresent(builder::setId);
          Optional.ofNullable(loadingInfoId).ifPresent(builder::setLoadingInfoId);
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
          Optional.ofNullable(berth.getLineDisplacement())
              .ifPresent(v -> builder.setLineDisplacement(v));
          Optional.ofNullable(berth.getDisplacement())
              .ifPresent(displacement -> builder.setDisplacement(String.valueOf(displacement)));
          berthList.add(builder.build());
        });
    return berthList;
  }

  public List<LoadingDelays> buildLoadingDelays(
      List<com.cpdss.gateway.domain.loadingplan.LoadingDelays> loadingDelayList,
      Long loadingInfoId) {
    List<LoadingDelays> delayList = new ArrayList<LoadingDelays>();
    loadingDelayList.forEach(
        delay -> {
          LoadingDelays.Builder builder = LoadingDelays.newBuilder();
          Optional.ofNullable(delay.getCargoId()).ifPresent(builder::setCargoId);
          Optional.ofNullable(delay.getDuration())
              .ifPresent(duration -> builder.setDuration(String.valueOf(duration)));
          Optional.ofNullable(delay.getId()).ifPresent(builder::setId);
          Optional.ofNullable(loadingInfoId).ifPresent(builder::setLoadingInfoId);
          Optional.ofNullable(delay.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(delay.getReasonForDelayIds())
              .ifPresent(v -> v.forEach(builder::addReasonForDelayIds));
          Optional.ofNullable(delay.getCargoNominationId())
              .ifPresent(builder::setCargoNominationId);
          Optional.ofNullable(delay.getLoadingRate())
              .ifPresent(loadingRate -> builder.setLoadingRate(loadingRate.toString()));
          Optional.ofNullable(delay.getSequenceNo()).ifPresent(builder::setSequenceNo);
          delayList.add(builder.build());
        });
    return delayList;
  }

  public List<LoadingMachinesInUse> buildLoadingMachineries(
      List<com.cpdss.gateway.domain.loadingplan.LoadingMachinesInUse> loadingMachineryList,
      Long loadingInfoId) {
    List<LoadingMachinesInUse> machineries = new ArrayList<LoadingMachinesInUse>();
    loadingMachineryList.forEach(
        machine -> {
          LoadingMachinesInUse.Builder builder = LoadingMachinesInUse.newBuilder();
          Optional.ofNullable(machine.getCapacity())
              .ifPresent(capacity -> builder.setCapacity(String.valueOf(capacity)));
          Optional.ofNullable(machine.getId()).ifPresent(builder::setId);
          Optional.ofNullable(loadingInfoId).ifPresent(builder::setLoadingInfoId);
          Optional.ofNullable(machine.getMachineId()).ifPresent(builder::setMachineId);
          Optional.ofNullable(machine.getMachineTypeId())
              .ifPresent(v -> builder.setMachineTypeValue(v));
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
          Optional.ofNullable(topOff.getDisplayOrder()).ifPresent(builder::setDisplayOrder);
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

    if (request.getLoadingStages() != null) {
      if (request.getLoadingStages().getStageDuration() != null) {
        Optional.ofNullable(request.getLoadingStages().getStageDuration().getId())
            .ifPresent(durationBuilder::setId);
        Optional.ofNullable(request.getLoadingStages().getStageDuration().getDuration())
            .ifPresent(durationBuilder::setDuration);
      }

      if (request.getLoadingStages().getStageOffset() != null) {
        Optional.ofNullable(request.getLoadingStages().getStageOffset().getId())
            .ifPresent(offsetBuilder::setId);
        Optional.ofNullable(request.getLoadingStages().getStageOffset().getStageOffsetVal())
            .ifPresent(offsetBuilder::setStageOffsetVal);
      }

      Optional.ofNullable(request.getLoadingStages().getTrackGradeSwitch())
          .ifPresent(builder::setTrackGradeSwitch);
      Optional.ofNullable(request.getLoadingStages().getTrackStartEndStage())
          .ifPresent(builder::setTrackStartEndStage);
      Optional.ofNullable(request.getLoadingStages().getIsStageDurationUsed())
          .ifPresent(builder::setIsStageDurationUsed);
      Optional.ofNullable(request.getLoadingStages().getIsStageOffsetUsed())
          .ifPresent(builder::setIsStageOffsetUsed);
    }
    builder.setDuration(durationBuilder.build());
    builder.setOffset(offsetBuilder.build());
    return builder.build();
  }
}
