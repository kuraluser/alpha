/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.loadingplan.entity.CargoLoadingRate;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class LoadingSequenceService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired LoadingSequenceRepository loadingSequenceRepository;

  /**
   * Get Loading Sequences
   *
   * @param request
   * @param builder
   * @throws Exception
   */
  public void getLoadingSequences(LoadingSequenceRequest request, Builder builder)
      throws Exception {

    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findById(request.getLoadingInfoId());
    if (loadingInfoOpt.isEmpty()) {
      log.info("Cannot find loading information with id {}", request.getLoadingInfoId());
      throw new Exception("Cannot find loading information with id " + request.getLoadingInfoId());
    }

    List<LoadingSequence> loadingSequences =
        loadingSequenceRepository.findByLoadingInformationAndIsActive(loadingInfoOpt.get(), true);
    buildLoadingSequences(loadingSequences, builder);
  }

  private void buildLoadingSequences(List<LoadingSequence> loadingSequences, Builder builder) {
    loadingSequences.forEach(
        loadingSequence -> {
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
              sequenceBuilder =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence
                      .newBuilder();
          Optional.ofNullable(loadingSequence.getCargoLoadingRate1())
              .ifPresent(rate1 -> sequenceBuilder.setCargoLoadingRate1(String.valueOf(rate1)));
          Optional.ofNullable(loadingSequence.getCargoLoadingRate2())
              .ifPresent(rate2 -> sequenceBuilder.setCargoLoadingRate1(String.valueOf(rate2)));
          Optional.ofNullable(loadingSequence.getCargoNominationXId())
              .ifPresent(sequenceBuilder::setCargoNominationId);
          Optional.ofNullable(loadingSequence.getEndTime()).ifPresent(sequenceBuilder::setEndTime);
          Optional.ofNullable(loadingSequence.getPortXId()).ifPresent(sequenceBuilder::setPortId);
          Optional.ofNullable(loadingSequence.getSequenceNumber())
              .ifPresent(sequenceBuilder::setSequenceNumber);
          Optional.ofNullable(loadingSequence.getStageName())
              .ifPresent(sequenceBuilder::setStageName);
          Optional.ofNullable(loadingSequence.getStartTime())
              .ifPresent(sequenceBuilder::setStartTime);
          Optional.ofNullable(loadingSequence.getToLoadicator())
              .ifPresent(sequenceBuilder::setToLoadicator);
          buildDeBallastingRates(sequenceBuilder, loadingSequence.getDeballastingRates());
          buildCargoLoadingRates(sequenceBuilder, loadingSequence.getCargoLoadingRates());
          buildLoadingPlanPortWiseDetails(
              sequenceBuilder, loadingSequence.getLoadingPlanPortWiseDetails());
          builder.addLoadingSequences(sequenceBuilder.build());
        });
  }

  private void buildLoadingPlanPortWiseDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      Set<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails) {
    loadingPlanPortWiseDetails.stream()
        .filter(portWiseDetails -> portWiseDetails.getIsActive())
        .forEach(
            portWiseDetails -> {
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                      .Builder
                  portWiseDetailsBuilder =
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingPlanPortWiseDetails.newBuilder();
              Optional.ofNullable(portWiseDetails.getTime())
                  .ifPresent(portWiseDetailsBuilder::setTime);
              buildDeBallastingRates(
                  portWiseDetailsBuilder, portWiseDetails.getDeballastingRates());
              buildBallastDetails(
                  portWiseDetailsBuilder, portWiseDetails.getLoadingPlanBallastDetails());
              buildRobDetails(portWiseDetailsBuilder, portWiseDetails.getLoadingPlanRobDetails());
              buildStabilityParams(
                  portWiseDetailsBuilder, portWiseDetails.getLoadingPlanStabilityParameters());
              buildStowageDetails(
                  portWiseDetailsBuilder, portWiseDetails.getLoadingPlanStowageDetails());
              sequenceBuilder.addLoadingPlanPortWiseDetails(portWiseDetailsBuilder.build());
            });
  }

  private void buildStowageDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      Set<LoadingPlanStowageDetails> loadingPlanStowageDetails) {
    loadingPlanStowageDetails.stream()
        .filter(stowage -> stowage.getIsActive())
        .forEach(
            stowage -> {
              LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(stowage.getQuantity())
                  .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(stowage.getQuantityM3())
                  .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
              Optional.ofNullable(stowage.getTankXId()).ifPresent(builder::setTankId);
              Optional.ofNullable(stowage.getApi())
                  .ifPresent(api -> builder.setApi(String.valueOf(api)));
              Optional.ofNullable(stowage.getCargoNominationId())
                  .ifPresent(builder::setCargoNominationId);
              Optional.ofNullable(stowage.getTemperature())
                  .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));
              Optional.ofNullable(stowage.getUllage())
                  .ifPresent(ullage -> builder.setUllage(String.valueOf(ullage)));
              portWiseDetailsBuilder.addLoadingPlanBallastDetails(builder.build());
            });
  }

  private void buildStabilityParams(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      Set<LoadingPlanStabilityParameters> loadingPlanStabilityParameters) {
    Optional<LoadingPlanStabilityParameters> paramsOpt =
        loadingPlanStabilityParameters.stream().filter(params -> params.getIsActive()).findAny();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters.Builder
        builder =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
                .newBuilder();
    Optional.ofNullable(paramsOpt.get().getBm()).ifPresent(bm -> builder.setBm(String.valueOf(bm)));
    Optional.ofNullable(paramsOpt.get().getDraft())
        .ifPresent(draft -> builder.setBm(String.valueOf(draft)));
    Optional.ofNullable(paramsOpt.get().getSf()).ifPresent(sf -> builder.setBm(String.valueOf(sf)));
    portWiseDetailsBuilder.setLoadingPlanStabilityParameters(builder.build());
  }

  private void buildRobDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      Set<LoadingPlanRobDetails> loadingPlanRobDetails) {
    loadingPlanRobDetails.stream()
        .filter(rob -> rob.getIsActive())
        .forEach(
            rob -> {
              LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(rob.getQuantity())
                  .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(rob.getQuantityM3())
                  .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
              Optional.ofNullable(rob.getTankXId()).ifPresent(builder::setTankId);
              portWiseDetailsBuilder.addLoadingPlanBallastDetails(builder.build());
            });
  }

  private void buildBallastDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      Set<LoadingPlanBallastDetails> loadingPlanBallastDetails) {
    loadingPlanBallastDetails.stream()
        .filter(ballast -> ballast.getIsActive())
        .forEach(
            ballast -> {
              LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(ballast.getQuantity())
                  .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(ballast.getQuantityM3())
                  .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
              Optional.ofNullable(ballast.getSounding())
                  .ifPresent(sounding -> builder.setQuantity(String.valueOf(sounding)));
              Optional.ofNullable(ballast.getTankXId()).ifPresent(builder::setTankId);
              portWiseDetailsBuilder.addLoadingPlanBallastDetails(builder.build());
            });
  }

  private void buildDeBallastingRates(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      Set<DeballastingRate> deballastingRates) {
    deballastingRates.stream()
        .filter(rate -> rate.getIsActive())
        .forEach(
            rate -> {
              DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
              Optional.ofNullable(rate.getDeBallastingRate())
                  .ifPresent(
                      deBallastingRate ->
                          builder.setDeBallastingRate(String.valueOf(deBallastingRate)));
              Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
              Optional.ofNullable(rate.getTime()).ifPresent(builder::setTime);
              portWiseDetailsBuilder.addDeballastingRates(builder.build());
            });
  }

  private void buildCargoLoadingRates(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      Set<CargoLoadingRate> cargoLoadingRates) {
    cargoLoadingRates.stream()
        .filter(rate -> rate.getIsActive())
        .forEach(
            rate -> {
              LoadingRate.Builder builder = LoadingRate.newBuilder();
              Optional.ofNullable(rate.getLoadingRate())
                  .ifPresent(loadingRate -> builder.setLoadingRate(String.valueOf(loadingRate)));
              Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
              sequenceBuilder.addLoadingRates(builder.build());
            });
  }

  private void buildDeBallastingRates(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      Set<DeballastingRate> deballastingRates) {
    deballastingRates.stream()
        .filter(rate -> rate.getIsActive())
        .forEach(
            rate -> {
              DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
              Optional.ofNullable(rate.getDeBallastingRate())
                  .ifPresent(
                      deBallastingRate ->
                          builder.setDeBallastingRate(String.valueOf(deBallastingRate)));
              Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
              Optional.ofNullable(rate.getTime()).ifPresent(builder::setTime);
              sequenceBuilder.addDeBallastingRates(builder.build());
            });
  }
}
