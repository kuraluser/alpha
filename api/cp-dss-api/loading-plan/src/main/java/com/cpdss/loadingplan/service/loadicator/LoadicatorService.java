/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.constants.AlgoErrorHeaderConstants;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudy.LDIntactStability;
import com.cpdss.common.generated.LoadableStudy.LDStrength;
import com.cpdss.common.generated.LoadableStudy.LDtrim;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.CommunicationStatus;
import com.cpdss.loadingplan.domain.algo.LDTrim;
import com.cpdss.loadingplan.domain.algo.LoadicatorAlgoRequest;
import com.cpdss.loadingplan.domain.algo.LoadicatorAlgoResponse;
import com.cpdss.loadingplan.domain.algo.LoadicatorResult;
import com.cpdss.loadingplan.domain.algo.LoadicatorStage;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
public class LoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Value("${cpdss.build.env}")
  private String env;

  @Value("${cpdss.judgement.enable}")
  private boolean judgementEnabled;

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired LoadingSequenceRepository loadingSequenceRepository;
  @Autowired LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @Autowired LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;

  @Autowired
  LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @Autowired
  PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @Autowired LoadingPlanCommingleDetailsRepository loadingPlanCommingleDetailsRepository;

  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired LoadingPlanService loadingPlanService;
  @Autowired UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @Autowired RestTemplate restTemplate;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("loadicatorService")
  private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @Autowired LoadingPlanStagingService loadingPlanStagingService;
  @Autowired LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;
  @Autowired private LoadingPlanCommunicationService communicationService;
  @Autowired LoadingPlanCommunicationService loadingPlancommunicationService;

  @Autowired AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired AlgoErrorsRepository algoErrorsRepository;

  public void saveLoadicatorInfo(LoadingInformation loadingInformation, String processId)
      throws GenericServiceException {
    log.info(
        "Sending stowage plans of loading information {} to loadicator",
        loadingInformation.getId());
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    Set<Long> cargoNominationIds = new LinkedHashSet<Long>();
    List<LoadingSequence> loadingSequences =
        loadingSequenceRepository.findByLoadingInformationAndIsActiveOrderBySequenceNumber(
            loadingInformation, true);
    List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails =
        loadingPlanPortWiseDetailsRepository.findByLoadingInformationIdAndToLoadicatorAndIsActive(
            loadingInformation.getId(), true, true);
    List<Long> portWiseDetailIds =
        loadingPlanPortWiseDetails.stream()
            .map(LoadingPlanPortWiseDetails::getId)
            .collect(Collectors.toList());
    List<LoadingPlanStowageDetails> loadingPlanStowageDetails =
        loadingPlanStowageDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    cargoNominationIds.addAll(
        loadingPlanStowageDetails.stream()
            .map(LoadingPlanStowageDetails::getCargoNominationId)
            .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
            .collect(Collectors.toList()));
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);
    List<LoadingPlanBallastDetails> loadingPlanBallastDetails =
        loadingPlanBallastDetailsRepository.findByLoadingPlanPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);
    BigDecimal sg = null;
    List<BigDecimal> specificGravities =
        loadingPlanBallastDetails.stream()
            .filter(ballast -> (ballast.getSg() != null) && (ballast.getSg() != BigDecimal.ZERO))
            .map(ballast -> ballast.getSg())
            .collect(Collectors.toList());

    if (!specificGravities.isEmpty()) {
      sg = specificGravities.get(0);
    }
    List<LoadingPlanRobDetails> loadingPlanRobDetails =
        loadingPlanRobDetailsRepository.findByPortWiseDetailIdsAndIsActive(portWiseDetailIds, true);
    List<LoadingPlanCommingleDetails> loadingPlanCommingleDetails =
        loadingPlanCommingleDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            portWiseDetailIds, true);

    Set<Integer> loadingTimes = new LinkedHashSet<Integer>();
    loadingTimes.addAll(
        loadingPlanPortWiseDetails.stream()
            .map(LoadingPlanPortWiseDetails::getTime)
            .sorted()
            .collect(Collectors.toList()));
    Map<Integer, List<LoadingPlanStowageDetails>> stowageMap =
        new HashMap<Integer, List<LoadingPlanStowageDetails>>();
    Map<Integer, List<LoadingPlanBallastDetails>> ballastMap =
        new HashMap<Integer, List<LoadingPlanBallastDetails>>();
    Map<Integer, List<LoadingPlanRobDetails>> robMap =
        new HashMap<Integer, List<LoadingPlanRobDetails>>();
    Map<Integer, List<LoadingPlanCommingleDetails>> commingleMap =
        new HashMap<Integer, List<LoadingPlanCommingleDetails>>();

    loadingTimes.forEach(
        time -> {
          Boolean isGradeSwitchWithoutDelay =
              isGradeSwitchTimeWithoutDelay(loadingPlanPortWiseDetails, time);
          Long portWiseDetailsId =
              getPortWiseDetailsIdForGradeSwitchWithoutDelay(
                  loadingSequences, loadingPlanPortWiseDetails, time);
          stowageMap.put(
              time,
              loadingPlanStowageDetails.stream()
                  .filter(stowage -> stowage.getLoadingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      stowage ->
                          isGradeSwitchWithoutDelay
                              ? stowage
                                  .getLoadingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
          ballastMap.put(
              time,
              loadingPlanBallastDetails.stream()
                  .filter(ballast -> ballast.getLoadingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      ballast ->
                          isGradeSwitchWithoutDelay
                              ? ballast
                                  .getLoadingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
          robMap.put(
              time,
              loadingPlanRobDetails.stream()
                  .filter(rob -> rob.getLoadingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      rob ->
                          isGradeSwitchWithoutDelay
                              ? rob.getLoadingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
          commingleMap.put(
              time,
              loadingPlanCommingleDetails.stream()
                  .filter(
                      commingle -> commingle.getLoadingPlanPortWiseDetails().getTime().equals(time))
                  .filter(
                      commingle ->
                          isGradeSwitchWithoutDelay
                              ? commingle
                                  .getLoadingPlanPortWiseDetails()
                                  .getId()
                                  .equals(portWiseDetailsId)
                              : true)
                  .collect(Collectors.toList()));
        });

    CargoInfo.CargoReply cargoReply = getCargoInfoForLoadicator(loadingInformation);
    VesselInfo.VesselReply vesselReply = getVesselDetailsForLoadicator(loadingInformation);
    PortInfo.PortReply portReply = getPortInfoForLoadicator(loadingInformation);

    loadicatorRequestBuilder.setTypeId(LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_TYPE_ID);
    loadicatorRequestBuilder.setIsUllageUpdate(false);
    for (Integer time : loadingTimes) {
      StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
      buildStowagePlan(
          loadingInformation,
          time,
          processId,
          cargoReply,
          vesselReply,
          portReply,
          stowagePlanBuilder,
          sg);
      buildLoadicatorStowagePlanDetails(
          loadingInformation,
          stowageMap.get(time),
          commingleMap.get(time),
          cargoNomDetails,
          vesselReply,
          cargoReply,
          stowagePlanBuilder);
      buildLoadicatorCargoDetails(
          loadingInformation,
          cargoNomDetails,
          stowageMap.get(time),
          commingleMap.get(time),
          cargoReply,
          stowagePlanBuilder);
      buildLoadicatorBallastDetails(
          loadingInformation, ballastMap.get(time), vesselReply, stowagePlanBuilder);
      buildLoadicatorRobDetails(
          loadingInformation, robMap.get(time), vesselReply, stowagePlanBuilder);
      loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
    }

    Loadicator.LoadicatorReply reply = this.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to send Stowage plans to Loadicator",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * @param loadingSequences
   * @param loadingPlanPortWiseDetails
   * @param time
   * @return
   */
  private Long getPortWiseDetailsIdForGradeSwitchWithoutDelay(
      List<LoadingSequence> loadingSequences,
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails,
      Integer time) {
    List<LoadingPlanPortWiseDetails> filteredPortDetails =
        loadingPlanPortWiseDetails.stream()
            .filter(portWiseDetails -> portWiseDetails.getTime().equals(time))
            .collect(Collectors.toList());
    if (filteredPortDetails.size() > 1) {
      log.info("Found grade switch without delay stage at time {}", time);
      return filteredPortDetails.get(0).getLoadingSequence().getSequenceNumber()
              > filteredPortDetails.get(1).getLoadingSequence().getSequenceNumber()
          ? filteredPortDetails.get(0).getId()
          : filteredPortDetails.get(1).getId();
    }
    return null;
  }

  /**
   * Returns whether there is a grade switch without delay at the given time. If there are multiple
   * portWiseDetails for the same time, then it is actually due to a grade switch without any delay
   *
   * @param loadingPlanPortWiseDetails
   * @param time
   * @return
   */
  private Boolean isGradeSwitchTimeWithoutDelay(
      List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails, Integer time) {
    if (loadingPlanPortWiseDetails.stream()
            .filter(portWiseDetails -> portWiseDetails.getTime().equals(time))
            .collect(Collectors.toList())
            .size()
        > 1) {
      return true;
    }
    return false;
  }

  public Loadicator.LoadicatorReply saveLoadicatorInfo(
      Loadicator.LoadicatorRequest loadicatorRequest) {
    return loadicatorGrpcService.saveLoadicatorInfo(loadicatorRequest);
  }

  private void buildLoadicatorRobDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanRobDetails> robDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    robDetails.forEach(
        rob -> {
          Loadicator.OtherTankInfo.Builder otherTankBuilder = Loadicator.OtherTankInfo.newBuilder();
          otherTankBuilder.setTankId(rob.getTankXId());
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(rob.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(otherTankBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(otherTankBuilder::setShortName);
          }
          Optional.ofNullable(rob.getQuantity())
              .ifPresent(qty -> otherTankBuilder.setQuantity(valueOf(qty)));
          stowagePlanBuilder.addOtherTankInfo(otherTankBuilder.build());
        });
  }

  private void buildLoadicatorBallastDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanBallastDetails> ballastDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    ballastDetails.forEach(
        ballast -> {
          Loadicator.BallastInfo.Builder ballastBuilder = Loadicator.BallastInfo.newBuilder();
          Optional.ofNullable(String.valueOf(ballast.getQuantity()))
              .ifPresent(ballastBuilder::setQuantity);
          Optional.ofNullable(stowagePlanBuilder.getStowageId())
              .ifPresent(ballastBuilder::setStowageId);
          Optional.ofNullable(ballast.getTankXId()).ifPresent(ballastBuilder::setTankId);
          Optional.ofNullable(loadingInformation.getPortXId()).ifPresent(ballastBuilder::setPortId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(ballast.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(ballastBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(ballastBuilder::setShortName);
          }
          stowagePlanBuilder.addBallastInfo(ballastBuilder.build());
        });
  }

  private void buildLoadicatorCargoDetails(
      LoadingInformation loadingInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<LoadingPlanStowageDetails> stowageDetails,
      List<LoadingPlanCommingleDetails> commingleDetails,
      CargoInfo.CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    stowageDetails.stream()
        .map(stowage -> stowage.getCargoNominationId())
        .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getAbbreviation()))
                  .ifPresent(cargoBuilder::setCargoAbbrev);
              Optional<LoadingPlanStowageDetails> stowageOpt =
                  stowageDetails.stream()
                      .filter(stwg -> stwg.getCargoNominationId().equals(cargoNominationId))
                      .findAny();
              stowageOpt.ifPresent(
                  stwg -> {
                    Optional.ofNullable(String.valueOf(stwg.getApi()))
                        .ifPresent(cargoBuilder::setApi);
                    Optional.ofNullable(String.valueOf(stwg.getTemperature()))
                        .ifPresent(cargoBuilder::setStandardTemp);
                  });
              Optional.ofNullable(cargoNomDetails.get(cargoNominationId).getCargoId())
                  .ifPresent(cargoBuilder::setCargoId);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
    buildLoadicatorCargoDetailsForCommingleCargo(
        commingleDetails, loadingInformation, cargoReply, stowagePlanBuilder);
  }

  /**
   * @param commingleDetails
   * @param loadingInformation
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  @SuppressWarnings("unchecked")
  private void buildLoadicatorCargoDetailsForCommingleCargo(
      List<LoadingPlanCommingleDetails> commingleDetails,
      LoadingInformation loadingInformation,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    commingleDetails.stream()
        .filter(
            distinctByKeys(
                LoadingPlanCommingleDetails::getCargoNomination1XId,
                LoadingPlanCommingleDetails::getCargoNomination2XId))
        .forEach(
            commingle -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              cargoBuilder.setCargoAbbrev(commingle.getAbbreviation());
              Optional.ofNullable(String.valueOf(commingle.getApi()))
                  .ifPresent(cargoBuilder::setApi);
              Optional.ofNullable(String.valueOf(commingle.getTemperature()))
                  .ifPresent(cargoBuilder::setStandardTemp);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
  }

  @SuppressWarnings("unchecked")
  private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
    final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

    return t -> {
      final List<?> keys =
          Arrays.stream(keyExtractors).map(ke -> ke.apply(t)).collect(Collectors.toList());

      return seen.putIfAbsent(keys, Boolean.TRUE) == null;
    };
  }

  public Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds)
      throws GenericServiceException {
    Map<Long, CargoNominationDetail> details = new HashMap<Long, CargoNominationDetail>();
    cargoNominationIds.stream()
        .filter(cargoNomId -> cargoNomId != 0)
        .forEach(
            id -> {
              CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
              builder.setCargoNominationId(id);
              CargoNominationDetailReply reply =
                  loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
              if (reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
                log.info("Fetched details of cargo nomination with id {}", id);
                details.put(id, reply.getCargoNominationdetail());
              }
            });

    return details;
  }

  private void buildLoadicatorStowagePlanDetails(
      LoadingInformation loadingInformation,
      List<LoadingPlanStowageDetails> stowageDetails,
      List<LoadingPlanCommingleDetails> commingleDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      VesselInfo.VesselReply vesselReply,
      CargoInfo.CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    stowageDetails.stream()
        .filter(stowage -> stowage.getCargoNominationId().longValue() != 0)
        .forEach(
            stowage -> {
              Loadicator.StowageDetails.Builder stowageDetailsBuilder =
                  Loadicator.StowageDetails.newBuilder();
              CargoNominationDetail cargoNomDetail =
                  cargoNomDetails.get(stowage.getCargoNominationId());
              Optional.ofNullable(cargoNomDetail.getCargoId())
                  .ifPresent(stowageDetailsBuilder::setCargoId);
              Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
              Optional.ofNullable(stowage.getQuantity())
                  .ifPresent(
                      quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(cargoNomDetail.getAbbreviation())
                  .ifPresent(stowageDetailsBuilder::setCargoName);
              Optional.ofNullable(loadingInformation.getPortXId())
                  .ifPresent(stowageDetailsBuilder::setPortId);
              Optional.ofNullable(stowageDetailsBuilder.getStowageId())
                  .ifPresent(stowageDetailsBuilder::setStowageId);
              Optional<VesselInfo.VesselTankDetail> tankDetail =
                  vesselReply.getVesselTanksList().stream()
                      .filter(tank -> Long.valueOf(tank.getTankId()).equals(stowage.getTankXId()))
                      .findAny();
              if (tankDetail.isPresent()) {
                Optional.ofNullable(tankDetail.get().getTankName())
                    .ifPresent(stowageDetailsBuilder::setTankName);
                Optional.ofNullable(tankDetail.get().getShortName())
                    .ifPresent(stowageDetailsBuilder::setShortName);
              }
              stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
            });
    buildLoadicatorStowagePlanDetailsForCommingleCargo(
        commingleDetails, loadingInformation, cargoReply, vesselReply, stowagePlanBuilder);
  }

  /**
   * @param commingleDetails
   * @param loadingInformation
   * @param cargoReply
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildLoadicatorStowagePlanDetailsForCommingleCargo(
      List<LoadingPlanCommingleDetails> commingleDetails,
      LoadingInformation loadingInformation,
      CargoReply cargoReply,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    commingleDetails.forEach(
        commingle -> {
          Loadicator.StowageDetails.Builder stowageDetailsBuilder =
              Loadicator.StowageDetails.newBuilder();
          Optional.ofNullable(commingle.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);

          Optional.ofNullable(commingle.getQuantity())
              .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));

          Optional.ofNullable(loadingInformation.getPortXId())
              .ifPresent(stowageDetailsBuilder::setPortId);
          Optional.ofNullable(stowageDetailsBuilder.getStowageId())
              .ifPresent(stowageDetailsBuilder::setStowageId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(commingle.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(stowageDetailsBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(stowageDetailsBuilder::setShortName);
          }

          stowageDetailsBuilder.setCargoName(commingle.getAbbreviation());
          stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
        });
  }

  public void buildStowagePlan(
      LoadingInformation loadingInformation,
      Integer time,
      String processId,
      CargoReply cargoReply,
      VesselReply vesselReply,
      PortReply portReply,
      Builder stowagePlanBuilder,
      BigDecimal sg) {
    stowagePlanBuilder.setProcessId(processId);
    VesselInfo.VesselDetail vessel = vesselReply.getVesselsList().get(0);
    Optional.ofNullable(vessel.getId()).ifPresent(stowagePlanBuilder::setVesselId);
    Optional.ofNullable(vessel.getImoNumber()).ifPresent(stowagePlanBuilder::setImoNumber);
    Optional.ofNullable(vessel.getTypeOfShip()).ifPresent(stowagePlanBuilder::setShipType);
    Optional.ofNullable(vessel.getCode()).ifPresent(stowagePlanBuilder::setVesselCode);
    Optional.ofNullable(vessel.getProvisionalConstant())
        .ifPresent(stowagePlanBuilder::setProvisionalConstant);
    Optional.ofNullable(vessel.getDeadweightConstant())
        .ifPresent(stowagePlanBuilder::setDeadweightConstant);
    stowagePlanBuilder.setPortId(loadingInformation.getPortXId());
    stowagePlanBuilder.setStowageId(time);
    stowagePlanBuilder.setStatus(1);
    stowagePlanBuilder.setBookingListId(loadingInformation.getId());
    Optional<PortInfo.PortDetail> portDetail =
        portReply.getPortsList().stream()
            .filter(port -> Long.valueOf(port.getId()).equals(loadingInformation.getPortXId()))
            .findAny();
    if (portDetail.isPresent()) {
      Optional.ofNullable(portDetail.get().getCode()).ifPresent(stowagePlanBuilder::setPortCode);
      Optional.ofNullable(portDetail.get().getWaterDensity())
          .ifPresent(density -> stowagePlanBuilder.setSeaWaterDensity(valueOf(density)));
    }
    if (sg != null) {
      stowagePlanBuilder.setSeaWaterDensity(sg.toString());
    }
    stowagePlanBuilder.setSynopticalId(loadingInformation.getSynopticalTableXId());
    stowagePlanBuilder.setPortRotationId(loadingInformation.getPortRotationXId());
  }

  /**
   * Get port info for loadicator
   *
   * @param loadingInformation
   * @return
   * @throws GenericServiceException
   */
  public PortInfo.PortReply getPortInfoForLoadicator(LoadingInformation loadingInformation)
      throws GenericServiceException {
    PortInfo.PortRequest portRequest =
        PortInfo.PortRequest.newBuilder()
            .setVesselId(loadingInformation.getVesselXId())
            .setVoyageId(loadingInformation.getVoyageId())
            .build();
    PortInfo.PortReply portReply = this.GetPortInfo(portRequest);
    if (!LoadingPlanConstants.SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portReply;
  }

  public PortInfo.PortReply GetPortInfo(PortInfo.PortRequest build) {
    return portInfoGrpcService.getPortInfo(build);
  }

  /**
   * get vessel detail for loadicator
   *
   * @param loadingInformation
   * @return
   * @throws GenericServiceException
   */
  public VesselInfo.VesselReply getVesselDetailsForLoadicator(LoadingInformation loadingInformation)
      throws GenericServiceException {
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(loadingInformation.getVesselXId())
            .build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailByVesselId(replyBuilder);
    if (!LoadingPlanConstants.SUCCESS.equalsIgnoreCase(
        vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return vesselReply;
  }

  public VesselInfo.VesselReply getVesselDetailByVesselId(VesselInfo.VesselRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselDetailByVesselId(replyBuilder);
  }

  /**
   * Get cargo details
   *
   * @param loadingInformation
   * @return
   * @throws GenericServiceException
   */
  public CargoInfo.CargoReply getCargoInfoForLoadicator(LoadingInformation loadingInformation)
      throws GenericServiceException {
    CargoInfo.CargoRequest cargoRequest =
        CargoInfo.CargoRequest.newBuilder()
            .setVesselId(loadingInformation.getVesselXId())
            .setVoyageId(loadingInformation.getVoyageId())
            .build();
    CargoInfo.CargoReply cargoReply = this.getCargoInfo(cargoRequest);
    if (!LoadingPlanConstants.SUCCESS.equalsIgnoreCase(
        cargoReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoReply;
  }

  public CargoInfo.CargoReply getCargoInfo(CargoInfo.CargoRequest build) {
    return cargoInfoGrpcService.getCargoInfo(build);
  }

  public void getLoadicatorData(
      LoadingInfoLoadicatorDataRequest request,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply
              .Builder
          reply)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {

    Boolean isValid = true;
    Long statusId;
    List<String> judgements = new ArrayList<>();

    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInformationId());
    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInformationId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    if (!request.getIsUllageUpdate()) {
      log.info(
          "Recieved stability parameters of Loading Information {} from Loadicator",
          request.getLoadingInformationId());
      LoadicatorAlgoRequest algoRequest = new LoadicatorAlgoRequest();
      buildLoadicatorAlgoRequest(loadingInfoOpt.get(), request, algoRequest);
      saveLoadicatorRequestJson(algoRequest, loadingInfoOpt.get().getId());
      try {
        LoadicatorAlgoResponse algoResponse =
            restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);

        saveLoadicatorResponseJson(algoResponse, loadingInfoOpt.get().getId());
        saveLoadingSequenceStabilityParameters(loadingInfoOpt.get(), algoResponse);

        log.info(
            "=============== Judgement Check {} ===============",
            judgementEnabled ? "enabled" : "disabled");
        if (judgementEnabled
            && algoResponse.getLoadicatorResults() != null
            && !algoResponse.getLoadicatorResults().isEmpty()) {
          for (LoadicatorResult loadicatorResult : algoResponse.getLoadicatorResults()) {
            if (loadicatorResult.getJudgement() != null
                && !loadicatorResult.getJudgement().isEmpty()) {
              log.error("Judgement check failed for time {}", loadicatorResult.getTime());
              isValid = false;
              loadicatorResult
                  .getJudgement()
                  .forEach(
                      judgement -> {
                        judgements.add(
                            String.format("Time %d : %s", loadicatorResult.getTime(), judgement));
                      });
            }
          }
        }

        if (isValid) {
          statusId = LoadingPlanConstants.LOADING_INFORMATION_PLAN_GENERATED_ID;
          loadingInformationRepository.updateIsLoadingSequenceGeneratedStatus(
              loadingInfoOpt.get().getId(), true);
          loadingInformationRepository.updateIsLoadingPlanGeneratedStatus(
              loadingInfoOpt.get().getId(), true);
          loadingPlanCommunicationStatusRepository.updateCommunicationStatus(
              CommunicationStatus.COMPLETED.getId(), false, loadingInfoOpt.get().getId());

        } else {
          statusId = LoadingPlanConstants.LOADING_INFORMATION_ERROR_OCCURRED_ID;
          saveJudgements(loadingInfoOpt.get(), judgements);
        }

        Optional<LoadingInformationStatus> loadingInfoStatusOpt =
            loadingPlanAlgoService.getLoadingInformationStatus(statusId);
        loadingInformationRepository.updateLoadingInformationStatuses(
            loadingInfoStatusOpt.get(),
            loadingInfoStatusOpt.get(),
            loadingInfoStatusOpt.get(),
            loadingInfoOpt.get().getId());
        loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
            loadingInfoOpt.get(), request.getProcessId(), loadingInfoStatusOpt.get());
        try {
          log.info("Communication side started for loadicator on");
          if (enableCommunication && !env.equals("ship")) {
            loadingPlanCommunication(
                com.cpdss.loadingplan.utility.LoadingPlanConstants.LOADING_PLAN_SHORE_TO_SHIP,
                loadingInfoOpt.get());
          }
        } catch (Exception e) {
          log.error(
              "Error occured when communicate loadingplan with loadicator on", e.getMessage());
        }
      } catch (HttpStatusCodeException e) {
        log.error("Error occured in ALGO side while calling loadicator_results API");
        Optional<LoadingInformationStatus> errorOccurredStatusOpt =
            loadingPlanAlgoService.getLoadingInformationStatus(
                LoadingPlanConstants.LOADING_INFORMATION_ERROR_OCCURRED_ID);
        loadingInformationRepository.updateLoadingInformationStatus(
            errorOccurredStatusOpt.get(), loadingInfoOpt.get().getId());
        loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
            loadingInfoOpt.get(), request.getProcessId(), errorOccurredStatusOpt.get());
        loadingPlanAlgoService.saveAlgoInternalError(
            loadingInfoOpt.get(),
            null,
            Lists.newArrayList(e.getResponseBodyAsString(), request.getProcessId()));
        try {
          log.info("Communication side started for loadicator on With Algo Errors");
          if (enableCommunication && !env.equals("ship")) {
            loadingPlanCommunication(
                com.cpdss.loadingplan.utility.LoadingPlanConstants
                    .LOADING_PLAN_ALGO_ERRORS_SHORE_TO_SHIP,
                loadingInfoOpt.get());
          }
        } catch (Exception ex) {
          log.error("Error occured when communicate algo errors", ex.getMessage());
        }
      }
    } else {
      ullageUpdateLoadicatorService.getLoadicatorData(request);
    }
  }

  /**
   * Saves judgements to DB
   *
   * @param loadingInformation
   * @param judgements
   */
  private void saveJudgements(LoadingInformation loadingInformation, List<String> judgements) {
    log.info("Saving judgements for loading information {}", loadingInformation.getId());
    algoErrorHeadingRepository.deleteByLoadingInformation(loadingInformation);
    algoErrorsRepository.deleteByLoadingInformation(loadingInformation);
    loadingPlanAlgoService.saveAlgoErrorEntity(
        loadingInformation, AlgoErrorHeaderConstants.ALGO_STABILITY_ERRORS, null, judgements);
  }

  /**
   * Saves the Loadicator Response JSON to DB.
   *
   * @param algoResponse
   * @param loadingInfoId
   * @throws GenericServiceException
   */
  private void saveLoadicatorResponseJson(LoadicatorAlgoResponse algoResponse, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator response to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(
        LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_RESPONSE_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadingInformationLoadicatorResult_"
                  + loadingInfoId
                  + ".json"),
          algoResponse);
      jsonBuilder.setJson(mapper.writeValueAsString(algoResponse));
      saveJson(jsonBuilder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save request JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (IOException e) {
      throw new GenericServiceException(
          "Could not save request JSON to Filesystem",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * Saves the Loadicator Request JSON to DB.
   *
   * @param algoRequest
   * @param loadingInfoId
   * @throws GenericServiceException
   */
  private void saveLoadicatorRequestJson(LoadicatorAlgoRequest algoRequest, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(
        LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_REQUEST_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadingInformationLoadicatorRequest_"
                  + loadingInfoId
                  + ".json"),
          algoRequest);
      jsonBuilder.setJson(mapper.writeValueAsString(algoRequest));
      saveJson(jsonBuilder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Could not save request JSON to DB",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } catch (IOException e) {
      throw new GenericServiceException(
          "Could not save request JSON to Filesystem",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public void saveJson(com.cpdss.common.generated.LoadableStudy.JsonRequest.Builder jsonBuilder) {
    this.loadableStudyGrpcService.saveJson(jsonBuilder.build());
  }

  private void buildLoadicatorAlgoRequest(
      LoadingInformation loadingInformation,
      LoadingInfoLoadicatorDataRequest request,
      LoadicatorAlgoRequest algoRequest) {
    algoRequest.setLoadingInformationId(loadingInformation.getId());
    algoRequest.setProcessId(request.getProcessId());
    algoRequest.setVesselId(loadingInformation.getVesselXId());
    algoRequest.setPortId(loadingInformation.getPortXId());
    algoRequest.setPortRotationId(loadingInformation.getPortRotationXId());
    List<LoadicatorStage> stages = new ArrayList<LoadicatorStage>();
    request
        .getLoadingInfoLoadicatorDetailsList()
        .forEach(
            loadicatorDetails -> {
              LoadicatorStage loadicatorStage = new LoadicatorStage();
              loadicatorStage.setTime(loadicatorDetails.getTime());
              buildLdTrim(loadicatorDetails.getLDtrim(), loadicatorStage);
              buildLdIntactStability(loadicatorDetails.getLDIntactStability(), loadicatorStage);
              buildLdStrength(loadicatorDetails.getLDStrength(), loadicatorStage);
              stages.add(loadicatorStage);
            });
    algoRequest.setStages(stages);
  }

  public void buildLdStrength(LDStrength ldStrength, LoadicatorStage loadicatorStage) {
    com.cpdss.loadingplan.domain.algo.LDStrength strength =
        new com.cpdss.loadingplan.domain.algo.LDStrength();
    strength.setBendingMomentPersentFrameNumber(ldStrength.getBendingMomentPersentFrameNumber());
    strength.setBendingMomentPersentJudgement(ldStrength.getBendingMomentPersentJudgement());
    strength.setBendingMomentPersentValue(ldStrength.getBendingMomentPersentValue());
    strength.setErrorDetails(ldStrength.getErrorDetails());
    strength.setId(ldStrength.getId());
    strength.setInnerLongiBhdFrameNumber(ldStrength.getInnerLongiBhdFrameNumber());
    strength.setInnerLongiBhdJudgement(ldStrength.getInnerLongiBhdJudgement());
    strength.setInnerLongiBhdValue(ldStrength.getInnerLongiBhdValue());
    strength.setMessageText(ldStrength.getMessageText());
    strength.setOuterLongiBhdFrameNumber(ldStrength.getOuterLongiBhdFrameNumber());
    strength.setOuterLongiBhdJudgement(ldStrength.getOuterLongiBhdJudgement());
    strength.setOuterLongiBhdValue(ldStrength.getOuterLongiBhdValue());
    strength.setSfFrameNumber(ldStrength.getSfFrameNumber());
    strength.setSfHopperFrameNumber(ldStrength.getSfHopperFrameNumber());
    strength.setSfHopperJudgement(ldStrength.getSfHopperJudgement());
    strength.setSfHopperValue(ldStrength.getSfHopperValue());
    strength.setSfSideShellFrameNumber(ldStrength.getSfSideShellFrameNumber());
    strength.setSfSideShellJudgement(ldStrength.getSfSideShellJudgement());
    strength.setSfSideShellValue(ldStrength.getSfSideShellValue());
    strength.setShearingForceJudgement(ldStrength.getShearingForceJudgement());
    strength.setShearingForcePersentValue(ldStrength.getShearingForcePersentValue());
    loadicatorStage.setLdStrength(strength);
  }

  public void buildLdIntactStability(
      LDIntactStability lDIntactStability, LoadicatorStage loadicatorStage) {
    com.cpdss.loadingplan.domain.algo.LDIntactStability intactStability =
        new com.cpdss.loadingplan.domain.algo.LDIntactStability();
    intactStability.setAngleatmaxrleverJudgement(lDIntactStability.getAngleatmaxrleverJudgement());
    intactStability.setAngleatmaxrleverValue(lDIntactStability.getAngleatmaxrleverValue());
    intactStability.setAreaofStability030Judgement(
        lDIntactStability.getAreaofStability030Judgement());
    intactStability.setAreaofStability030Value(lDIntactStability.getAreaofStability030Value());
    intactStability.setAreaofStability040Judgement(
        lDIntactStability.getAreaofStability040Judgement());
    intactStability.setAreaofStability040Value(lDIntactStability.getAreaofStability040Value());
    intactStability.setAreaofStability3040Judgement(
        lDIntactStability.getAreaofStability3040Judgement());
    intactStability.setAreaofStability3040Value(lDIntactStability.getAreaofStability3040Value());
    intactStability.setBigIntialGomJudgement(lDIntactStability.getBigIntialGomJudgement());
    intactStability.setBigintialGomValue(lDIntactStability.getBigintialGomValue());
    intactStability.setErrorDetails(lDIntactStability.getErrorDetails());
    intactStability.setErrorStatus(lDIntactStability.getErrorStatus());
    intactStability.setGmAllowableCurveCheckJudgement(
        lDIntactStability.getGmAllowableCurveCheckJudgement());
    intactStability.setGmAllowableCurveCheckValue(
        lDIntactStability.getGmAllowableCurveCheckValue());
    intactStability.setHeelBySteadyWindJudgement(lDIntactStability.getHeelBySteadyWindJudgement());
    intactStability.setHeelBySteadyWindValue(lDIntactStability.getHeelBySteadyWindValue());
    intactStability.setId(lDIntactStability.getId());
    intactStability.setMaximumRightingLeverJudgement(
        lDIntactStability.getMaximumRightingLeverJudgement());
    intactStability.setMaximumRightingLeverValue(lDIntactStability.getMaximumRightingLeverValue());
    intactStability.setMessageText(lDIntactStability.getMessageText());
    intactStability.setStabilityAreaBaJudgement(lDIntactStability.getStabilityAreaBaJudgement());
    intactStability.setStabilityAreaBaValue(lDIntactStability.getStabilityAreaBaValue());
    loadicatorStage.setLdIntactStability(intactStability);
  }

  public void buildLdTrim(LDtrim ldTrim, LoadicatorStage loadicatorStage) {
    LDTrim trim = new LDTrim();
    trim.setAftDraftValue(ldTrim.getAftDraftValue());
    trim.setAirDraftJudgement(ldTrim.getAirDraftJudgement());
    trim.setAirDraftValue(ldTrim.getAirDraftValue());
    trim.setDisplacementJudgement(ldTrim.getDisplacementJudgement());
    trim.setDisplacementValue(ldTrim.getDisplacementValue());
    trim.setErrorDetails(ldTrim.getErrorDetails());
    trim.setErrorStatus(ldTrim.getErrorStatus());
    trim.setForeDraftValue(ldTrim.getForeDraftValue());
    trim.setHeelValue(ldTrim.getHeelValue());
    trim.setId(ldTrim.getId());
    trim.setMaximumAllowableJudement(ldTrim.getMaximumAllowableJudement());
    trim.setMaximumAllowableVisibility(ldTrim.getMaximumAllowableVisibility());
    trim.setMaximumDraftJudgement(ldTrim.getMaximumDraftJudgement());
    trim.setMeanDraftValue(ldTrim.getMaximumDraftValue());
    trim.setMaximumDraftValue(ldTrim.getMaximumDraftValue());
    trim.setMeanDraftJudgement(ldTrim.getMeanDraftJudgement());
    trim.setMeanDraftValue(ldTrim.getMeanDraftValue());
    trim.setMessageText(ldTrim.getMessageText());
    trim.setMinimumForeDraftInRoughWeatherJudgement(
        ldTrim.getMinimumForeDraftInRoughWeatherJudgement());
    trim.setMinimumForeDraftInRoughWeatherValue(ldTrim.getMinimumForeDraftInRoughWeatherValue());
    trim.setTrimValue(ldTrim.getTrimValue());
    trim.setDeflection(ldTrim.getDeflection());
    loadicatorStage.setLdTrim(trim);
  }

  private void saveLoadingSequenceStabilityParameters(
      LoadingInformation loadingInformation, LoadicatorAlgoResponse algoResponse) {
    List<LoadingSequenceStabilityParameters> loadingSequenceStabilityParameters =
        new ArrayList<LoadingSequenceStabilityParameters>();
    deleteLoadingSequenceStabilityParameters(loadingInformation);
    log.info(
        "Saving loading sequence stability parameters of loading information {}",
        loadingInformation.getId());
    algoResponse
        .getLoadicatorResults()
        .forEach(
            result -> {
              LoadingSequenceStabilityParameters stabilityParameters =
                  new LoadingSequenceStabilityParameters();
              buildLoadingSequenceStabilityParameters(
                  loadingInformation, result, stabilityParameters);
              loadingSequenceStabilityParameters.add(stabilityParameters);
            });
    loadingSequenceStabiltyParametersRepository.saveAll(loadingSequenceStabilityParameters);
    savePortStabilityParams(loadingInformation, algoResponse);
  }

  /**
   * Saves Loadicator generated Stability Parameters of the Loading Plan of a Port.
   *
   * @param loadingInformation
   * @param algoResponse
   */
  private void savePortStabilityParams(
      LoadingInformation loadingInformation, LoadicatorAlgoResponse algoResponse) {
    Optional<PortLoadingPlanStabilityParameters> oldArrStabilityOpt =
        portLoadingPlanStabilityParametersRepository
            .findByLoadingInformationIdAndConditionTypeAndValueTypeAndIsActiveTrue(
                loadingInformation.getId(),
                LoadingPlanConstants.LOADING_PLAN_ARRIVAL_CONDITION_VALUE,
                LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE);

    Optional<PortLoadingPlanStabilityParameters> oldDepStabilityOpt =
        portLoadingPlanStabilityParametersRepository
            .findByLoadingInformationIdAndConditionTypeAndValueTypeAndIsActiveTrue(
                loadingInformation.getId(),
                LoadingPlanConstants.LOADING_PLAN_DEPARTURE_CONDITION_VALUE,
                LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE);

    portLoadingPlanStabilityParametersRepository.deleteByLoadingInformationId(
        loadingInformation.getId());
    LoadicatorResult arrivalStabilityParameters = algoResponse.getLoadicatorResults().get(0);
    LoadicatorResult departureStabilityParameters =
        algoResponse.getLoadicatorResults().get(algoResponse.getLoadicatorResults().size() - 1);

    PortLoadingPlanStabilityParameters portArrStability = new PortLoadingPlanStabilityParameters();
    buildPortStabilityParams(
        loadingInformation,
        arrivalStabilityParameters,
        portArrStability,
        LoadingPlanConstants.LOADING_PLAN_ARRIVAL_CONDITION_VALUE,
        LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE,
        oldArrStabilityOpt);
    portLoadingPlanStabilityParametersRepository.save(portArrStability);

    PortLoadingPlanStabilityParameters portDepStability = new PortLoadingPlanStabilityParameters();
    buildPortStabilityParams(
        loadingInformation,
        departureStabilityParameters,
        portDepStability,
        LoadingPlanConstants.LOADING_PLAN_DEPARTURE_CONDITION_VALUE,
        LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE,
        oldDepStabilityOpt);
    portLoadingPlanStabilityParametersRepository.save(portDepStability);
  }

  /**
   * @param loadingInformation
   * @param result
   * @param portStabilityParameters
   * @param conditionType
   * @param oldStabilityOpt
   */
  public void buildPortStabilityParams(
      LoadingInformation loadingInformation,
      LoadicatorResult result,
      PortLoadingPlanStabilityParameters portStabilityParameters,
      Integer conditionType,
      Integer valueType,
      Optional<PortLoadingPlanStabilityParameters> oldStabilityOpt) {
    portStabilityParameters.setAftDraft(
        StringUtils.isEmpty(result.getCalculatedDraftAftPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftAftPlanned()));
    portStabilityParameters.setBendingMoment(
        StringUtils.isEmpty(result.getBendingMoment())
            ? null
            : new BigDecimal(result.getBendingMoment()));
    portStabilityParameters.setForeDraft(
        StringUtils.isEmpty(result.getCalculatedDraftFwdPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftFwdPlanned()));
    portStabilityParameters.setIsActive(true);
    portStabilityParameters.setList(
        StringUtils.isEmpty(result.getList()) ? null : new BigDecimal(result.getList()));
    portStabilityParameters.setLoadingInformation(loadingInformation);
    portStabilityParameters.setMeanDraft(
        StringUtils.isEmpty(result.getCalculatedDraftMidPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftMidPlanned()));
    portStabilityParameters.setPortXId(loadingInformation.getPortXId());
    portStabilityParameters.setShearingForce(
        StringUtils.isEmpty(result.getShearingForce())
            ? null
            : new BigDecimal(result.getShearingForce()));
    portStabilityParameters.setTrim(
        StringUtils.isEmpty(result.getCalculatedTrimPlanned())
            ? null
            : new BigDecimal(result.getCalculatedTrimPlanned()));
    portStabilityParameters.setConditionType(conditionType);
    portStabilityParameters.setPortRotationXId(loadingInformation.getPortRotationXId());
    portStabilityParameters.setValueType(valueType);
    oldStabilityOpt.ifPresent(
        stability -> {
          portStabilityParameters.setFreeboard(stability.getFreeboard());
          portStabilityParameters.setManifoldHeight(stability.getManifoldHeight());
        });
  }

  private void deleteLoadingSequenceStabilityParameters(LoadingInformation loadingInformation) {
    log.info(
        "Deleting old stability parameters of Loading Information {}", loadingInformation.getId());
    loadingSequenceStabiltyParametersRepository.deleteByLoadingInformationId(
        loadingInformation.getId());
  }

  public void buildLoadingSequenceStabilityParameters(
      LoadingInformation loadingInformation,
      LoadicatorResult result,
      LoadingSequenceStabilityParameters stabilityParameters) {
    stabilityParameters.setAftDraft(
        StringUtils.isEmpty(result.getCalculatedDraftAftPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftAftPlanned()));
    stabilityParameters.setBendingMoment(
        StringUtils.isEmpty(result.getBendingMoment())
            ? null
            : new BigDecimal(result.getBendingMoment()));
    stabilityParameters.setForeDraft(
        StringUtils.isEmpty(result.getCalculatedDraftFwdPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftFwdPlanned()));
    stabilityParameters.setIsActive(true);
    stabilityParameters.setList(
        StringUtils.isEmpty(result.getList()) ? null : new BigDecimal(result.getList()));
    stabilityParameters.setLoadingInformation(loadingInformation);
    stabilityParameters.setMeanDraft(
        StringUtils.isEmpty(result.getCalculatedDraftMidPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftMidPlanned()));
    stabilityParameters.setPortXId(loadingInformation.getPortXId());
    stabilityParameters.setShearingForce(
        StringUtils.isEmpty(result.getShearingForce())
            ? null
            : new BigDecimal(result.getShearingForce()));
    stabilityParameters.setTime(result.getTime());
    stabilityParameters.setTrim(
        StringUtils.isEmpty(result.getCalculatedTrimPlanned())
            ? null
            : new BigDecimal(result.getCalculatedTrimPlanned()));
    stabilityParameters.setGomValue(
        StringUtils.isEmpty(result.getGomValue()) ? null : new BigDecimal(result.getGomValue()));
    stabilityParameters.setSfFrameNumber(
        StringUtils.isEmpty(result.getSfFrameNumber())
            ? null
            : new BigDecimal(result.getSfFrameNumber()));
    stabilityParameters.setBmFrameNumber(
        StringUtils.isEmpty(result.getBmFrameNumber())
            ? null
            : new BigDecimal(result.getBmFrameNumber()));
  }

  private void loadingPlanCommunication(
      List<String> loadingPlanCommunicationList, LoadingInformation loadingInformation)
      throws GenericServiceException {
    JsonArray jsonArray =
        loadingPlanStagingService.getCommunicationData(
            loadingPlanCommunicationList,
            UUID.randomUUID().toString(),
            MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType(),
            loadingInformation.getId(),
            StringUtils.hasLength(loadingInformation.getLoadableStudyProcessId())
                ? loadingInformation.getLoadableStudyProcessId()
                : null);
    log.info("Json Array in After Algo call: " + jsonArray.toString());
    EnvoyWriter.WriterReply ewReply =
        loadingPlancommunicationService.passRequestPayloadToEnvoyWriter(
            jsonArray.toString(),
            loadingInformation.getVesselXId(),
            MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType());
    log.info("------- Envoy writer has called successfully in shore: " + ewReply.toString());
    LoadingPlanCommunicationStatus loadingPlanCommunicationStatus =
        new LoadingPlanCommunicationStatus();
    if (ewReply.getMessageId() != null) {
      loadingPlanCommunicationStatus.setMessageUUID(ewReply.getMessageId());
      loadingPlanCommunicationStatus.setCommunicationStatus(
          CommunicationStatus.RECEIVED_WITH_HASH_VERIFIED.getId());
    }
    loadingPlanCommunicationStatus.setReferenceId(loadingInformation.getId());
    loadingPlanCommunicationStatus.setMessageType(
        MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType());
    loadingPlanCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
    loadingPlanCommunicationStatus.setActive(true);
    LoadingPlanCommunicationStatus loadableStudyCommunicationStatus =
        this.loadingPlanCommunicationStatusRepository.save(loadingPlanCommunicationStatus);
    log.info("Communication table update : " + loadingPlanCommunicationStatus.getId());
  }
}
