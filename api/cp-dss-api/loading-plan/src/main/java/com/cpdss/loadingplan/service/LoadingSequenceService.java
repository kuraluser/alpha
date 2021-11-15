/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.EductorOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.BallastOperation;
import com.cpdss.loadingplan.entity.CargoLoadingRate;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.EductionOperation;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import com.cpdss.loadingplan.entity.LoadingSequenceStabilityParameters;
import com.cpdss.loadingplan.repository.BallastOperationRepository;
import com.cpdss.loadingplan.repository.CargoLoadingRateRepository;
import com.cpdss.loadingplan.repository.DeballastingRateRepository;
import com.cpdss.loadingplan.repository.EductionOperationRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanCommingleDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanPortWiseDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingPlanStabilityParametersRepository;
import com.cpdss.loadingplan.repository.LoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceRepository;
import com.cpdss.loadingplan.repository.LoadingSequenceStabiltyParametersRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class LoadingSequenceService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired LoadingSequenceRepository loadingSequenceRepository;
  @Autowired DeballastingRateRepository deBallastingRateRepository;
  @Autowired CargoLoadingRateRepository cargoLoadingRateRepository;
  @Autowired BallastOperationRepository ballastOperationRepository;
  @Autowired LoadingPlanPortWiseDetailsRepository portWiseDetailsRepository;
  @Autowired LoadingPlanBallastDetailsRepository ballastDetailsRepository;
  @Autowired LoadingPlanRobDetailsRepository robDetailsRepository;
  @Autowired LoadingPlanStabilityParametersRepository stabilityParametersRepository;
  @Autowired LoadingPlanStowageDetailsRepository stowageDetailsRepository;
  @Autowired LoadingSequenceStabiltyParametersRepository loadingSequenceStabilityParamsRepository;
  @Autowired LoadingPlanCommingleDetailsRepository commingleDetailsRepository;
  @Autowired EductionOperationRepository eductionOperationRepository;

  @GrpcClient("loadableStudyService")
  LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  /**
   * Get Loading Sequences
   *
   * @param request
   * @param builder
   * @throws Exception
   */
  public void getLoadingSequences(LoadingSequenceRequest request, Builder builder)
      throws Exception {
    log.info("Fetching loading sequences of loading information {}", request.getLoadingInfoId());
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInfoId());
    if (loadingInfoOpt.isEmpty()) {
      log.info("Cannot find loading information with id {}", request.getLoadingInfoId());
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    String startDate = this.getStartDate(loadingInfoOpt.get().getPortRotationXId());
    builder.setStartDate(startDate);
    builder.setInterval(loadingInfoOpt.get().getStageDuration().getDuration());
    builder.setVesselId(loadingInfoOpt.get().getVesselXId());
    builder.setVoyageId(loadingInfoOpt.get().getVoyageId());
    builder.setPortId(loadingInfoOpt.get().getPortXId());
    builder.setLoadablePatternId(loadingInfoOpt.get().getLoadablePatternXId());
    builder.setPortRotationId(loadingInfoOpt.get().getPortRotationXId());
    List<LoadingSequence> loadingSequences =
        loadingSequenceRepository.findByLoadingInformationAndIsActiveOrderBySequenceNumber(
            loadingInfoOpt.get(), true);
    buildLoadingSequences(loadingSequences, builder);

    List<LoadingSequenceStabilityParameters> stabilityParameters =
        loadingSequenceStabilityParamsRepository.findByLoadingInformationAndIsActiveOrderByTime(
            loadingInfoOpt.get(), true);
    buildLoadingSequenceStabilityParameters(stabilityParameters, builder);
  }

  private void buildLoadingSequenceStabilityParameters(
      List<LoadingSequenceStabilityParameters> stabilityParameters, Builder builder) {
    log.info("Populating Loading sequence stability parameters");
    stabilityParameters.forEach(
        param -> {
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
                  .Builder
              paramBuilder =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingPlanStabilityParameters.newBuilder();
          Optional.ofNullable(param.getAftDraft())
              .ifPresent(aftDraft -> paramBuilder.setAftDraft(String.valueOf(aftDraft)));
          Optional.ofNullable(param.getBendingMoment())
              .ifPresent(bm -> paramBuilder.setBm(String.valueOf(bm)));
          Optional.ofNullable(param.getForeDraft())
              .ifPresent(foreDraft -> paramBuilder.setForeDraft(String.valueOf(foreDraft)));
          Optional.ofNullable(param.getShearingForce())
              .ifPresent(sf -> paramBuilder.setSf(String.valueOf(sf)));
          Optional.ofNullable(param.getTrim())
              .ifPresent(trim -> paramBuilder.setTrim(String.valueOf(trim)));
          Optional.ofNullable(param.getList())
              .ifPresent(list -> paramBuilder.setList(String.valueOf(list)));
          Optional.ofNullable(param.getTime()).ifPresent(paramBuilder::setTime);
          Optional.ofNullable(param.getGomValue())
              .ifPresent(value -> paramBuilder.setGomValue(value.toString()));
          builder.addLoadingSequenceStabilityParameters(paramBuilder.build());
        });
  }

  private String getStartDate(Long portRotationXId) throws Exception {
    log.info("Fetching port rotation details with id {} from loadable study MS", portRotationXId);
    PortRotationRequest.Builder builder = PortRotationRequest.newBuilder();
    builder.setId(portRotationXId);
    PortRotationDetailReply reply =
        loadableStudyGrpcService.getLoadableStudyPortRotationByPortRotationId(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new Exception("Could not find port rotation with id " + portRotationXId);
    }
    return reply.getPortRotationDetail().getEta();
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
              .ifPresent(rate2 -> sequenceBuilder.setCargoLoadingRate2(String.valueOf(rate2)));
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

          List<DeballastingRate> deBallastingRates =
              deBallastingRateRepository.findByLoadingSequenceAndIsActiveTrueOrderById(
                  loadingSequence);
          buildDeBallastingRates(sequenceBuilder, deBallastingRates);

          List<CargoLoadingRate> cargoLoadingRates =
              cargoLoadingRateRepository.findByLoadingSequenceAndIsActiveTrueOrderById(
                  loadingSequence);
          buildCargoLoadingRates(sequenceBuilder, cargoLoadingRates);

          List<BallastOperation> ballastOperations =
              ballastOperationRepository.findByLoadingSequenceAndIsActiveTrueOrderById(
                  loadingSequence);
          buildBallastOperations(sequenceBuilder, ballastOperations);

          List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails =
              portWiseDetailsRepository.findByLoadingSequenceAndIsActiveTrueOrderById(
                  loadingSequence);
          buildLoadingPlanPortWiseDetails(sequenceBuilder, loadingPlanPortWiseDetails);
          EductionOperation eductionOperation =
              eductionOperationRepository.findByLoadingSequenceAndIsActiveTrue(loadingSequence);
          if (eductionOperation != null) {
            buildEductionOperations(sequenceBuilder, eductionOperation);
          }
          builder.addLoadingSequences(sequenceBuilder.build());
        });
  }

  /**
   * @param sequenceBuilder
   * @param eductionOperation
   */
  private void buildEductionOperations(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      EductionOperation eductionOperation) {
    log.info(
        "Populating eduction operation, tanks: {}, pumps: {}",
        eductionOperation.getTanksUsed(),
        eductionOperation.getEductorsUsed());
    EductorOperation.Builder builder = EductorOperation.newBuilder();
    Optional.ofNullable(eductionOperation.getEductorsUsed())
        .ifPresent(builder::setEductorPumpsUsed);
    Optional.ofNullable(eductionOperation.getBallastPumpsUsed())
        .ifPresent(builder::setBallastPumpsUsed);
    Optional.ofNullable(eductionOperation.getEndTime()).ifPresent(builder::setEndTime);
    Optional.ofNullable(eductionOperation.getId()).ifPresent(builder::setId);
    Optional.ofNullable(eductionOperation.getStartTime()).ifPresent(builder::setStartTime);
    Optional.ofNullable(eductionOperation.getTanksUsed()).ifPresent(builder::setTanksUsed);
    sequenceBuilder.setEductorOperation(builder.build());
  }

  private void buildBallastOperations(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      List<BallastOperation> ballastOperations) {
    log.info("Populating Ballast Operations");
    ballastOperations.forEach(
        ballastOp -> {
          PumpOperation.Builder builder = PumpOperation.newBuilder();
          Optional.ofNullable(ballastOp.getEndTime()).ifPresent(builder::setEndTime);
          Optional.ofNullable(ballastOp.getPumpName()).ifPresent(builder::setPumpName);
          Optional.ofNullable(ballastOp.getPumpXId()).ifPresent(builder::setPumpXId);
          Optional.ofNullable(ballastOp.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(ballastOp.getRate())
              .ifPresent(rate -> builder.setRate(String.valueOf(rate)));
          Optional.ofNullable(ballastOp.getStartTime()).ifPresent(builder::setStartTime);
          sequenceBuilder.addBallastOperations(builder.build());
        });
  }

  private void buildLoadingPlanPortWiseDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails) {
    log.info("Populating Portwise details");
    loadingPlanPortWiseDetails.forEach(
        portWiseDetails -> {
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                  .Builder
              portWiseDetailsBuilder =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingPlanPortWiseDetails.newBuilder();
          Optional.ofNullable(portWiseDetails.getTime()).ifPresent(portWiseDetailsBuilder::setTime);

          List<DeballastingRate> deBallastingRates =
              deBallastingRateRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                  portWiseDetails);
          buildDeBallastingRates(portWiseDetailsBuilder, deBallastingRates);

          List<LoadingPlanBallastDetails> ballastDetails =
              ballastDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                  portWiseDetails);
          buildBallastDetails(portWiseDetailsBuilder, ballastDetails);

          List<LoadingPlanRobDetails> robDetails =
              robDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                  portWiseDetails);
          buildRobDetails(portWiseDetailsBuilder, robDetails);

          Optional<LoadingPlanStabilityParameters> stabilityParametersOpt =
              stabilityParametersRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrue(
                  portWiseDetails);
          buildStabilityParams(portWiseDetailsBuilder, stabilityParametersOpt);

          List<LoadingPlanCommingleDetails> commingleDetails =
              commingleDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                  portWiseDetails);
          buildCommingleDetails(portWiseDetailsBuilder, commingleDetails);

          List<LoadingPlanStowageDetails> stowageDetails =
              stowageDetailsRepository.findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
                  portWiseDetails);
          buildStowageDetails(portWiseDetailsBuilder, stowageDetails);
          sequenceBuilder.addLoadingPlanPortWiseDetails(portWiseDetailsBuilder.build());
        });
  }

  private void buildCommingleDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      List<LoadingPlanCommingleDetails> commingleDetails) {
    commingleDetails.forEach(
        commingle -> {
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
                  .Builder
              builder =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingPlanCommingleDetails.newBuilder();
          Optional.ofNullable(commingle.getAbbreviation()).ifPresent(builder::setAbbreviation);
          Optional.ofNullable(commingle.getApi()).ifPresent(api -> builder.setApi(api.toString()));
          Optional.ofNullable(commingle.getCargo1XId()).ifPresent(builder::setCargo1Id);
          Optional.ofNullable(commingle.getCargo2XId()).ifPresent(builder::setCargo2Id);
          Optional.ofNullable(commingle.getCargoNomination1XId())
              .ifPresent(builder::setCargoNomination1Id);
          Optional.ofNullable(commingle.getCargoNomination2XId())
              .ifPresent(builder::setCargoNomination2Id);
          Optional.ofNullable(commingle.getId()).ifPresent(builder::setId);
          Optional.ofNullable(commingle.getQuantity())
              .ifPresent(quantity -> builder.setQuantityMT(quantity.toString()));
          Optional.ofNullable(commingle.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(quantityM3.toString()));
          Optional.ofNullable(commingle.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(commingle.getTemperature())
              .ifPresent(temperature -> builder.setTemperature(temperature.toString()));
          Optional.ofNullable(commingle.getUllage())
              .ifPresent(ullage -> builder.setUllage(ullage.toString()));
          Optional.ofNullable(commingle.getColorCode()).ifPresent(builder::setColorCode);
          portWiseDetailsBuilder.addLoadingPlanCommingleDetails(builder.build());
        });
  }

  private void buildStowageDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      List<LoadingPlanStowageDetails> loadingPlanStowageDetails) {
    loadingPlanStowageDetails.forEach(
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
          Optional.ofNullable(stowage.getAbbreviation()).ifPresent(builder::setAbbreviation);
          Optional.ofNullable(stowage.getColorCode()).ifPresent(builder::setColorCode);
          portWiseDetailsBuilder.addLoadingPlanStowageDetails(builder.build());
        });
  }

  private void buildStabilityParams(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      Optional<LoadingPlanStabilityParameters> loadingPlanStabilityParameters) {
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters.Builder
        builder =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
                .newBuilder();
    Optional.ofNullable(loadingPlanStabilityParameters.get().getBm())
        .ifPresent(bm -> builder.setBm(String.valueOf(bm)));
    Optional.ofNullable(loadingPlanStabilityParameters.get().getDraft())
        .ifPresent(draft -> builder.setDraft(String.valueOf(draft)));
    Optional.ofNullable(loadingPlanStabilityParameters.get().getSf())
        .ifPresent(sf -> builder.setSf(String.valueOf(sf)));
    portWiseDetailsBuilder.setLoadingPlanStabilityParameters(builder.build());
  }

  private void buildRobDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      List<LoadingPlanRobDetails> robDetails) {
    robDetails.forEach(
        rob -> {
          LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
          Optional.ofNullable(rob.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(rob.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(rob.getTankXId()).ifPresent(builder::setTankId);
          portWiseDetailsBuilder.addLoadingPlanRobDetails(builder.build());
        });
  }

  private void buildBallastDetails(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      List<LoadingPlanBallastDetails> ballastDetails) {
    ballastDetails.forEach(
        ballast -> {
          LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
          Optional.ofNullable(ballast.getQuantity())
              .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(ballast.getQuantityM3())
              .ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
          Optional.ofNullable(ballast.getSounding())
              .ifPresent(sounding -> builder.setSounding(String.valueOf(sounding)));
          Optional.ofNullable(ballast.getTankXId()).ifPresent(builder::setTankId);
          Optional.ofNullable(ballast.getColorCode()).ifPresent(builder::setColorCode);
          portWiseDetailsBuilder.addLoadingPlanBallastDetails(builder.build());
        });
  }

  private void buildDeBallastingRates(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          portWiseDetailsBuilder,
      List<DeballastingRate> deballastingRates) {
    deballastingRates.forEach(
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
      List<CargoLoadingRate> cargoLoadingRates) {
    cargoLoadingRates.forEach(
        rate -> {
          LoadingRate.Builder builder = LoadingRate.newBuilder();
          Optional.ofNullable(rate.getLoadingRate())
              .ifPresent(loadingRate -> builder.setLoadingRate(String.valueOf(loadingRate)));
          Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
          builder.setEndTime(sequenceBuilder.getEndTime());
          builder.setStartTime(sequenceBuilder.getStartTime());
          sequenceBuilder.addLoadingRates(builder.build());
        });
  }

  private void buildDeBallastingRates(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder,
      List<DeballastingRate> deballastingRates) {
    deballastingRates.forEach(
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
