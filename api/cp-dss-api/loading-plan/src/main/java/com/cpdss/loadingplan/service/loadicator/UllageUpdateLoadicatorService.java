/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.Loadicator;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastTempDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageTempDetails;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInformationStatusRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanBallastTempDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanRobDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageTempDetailsRepository;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author pranav.k */
@Slf4j
@Service
@Transactional
public class UllageUpdateLoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @Autowired PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;

  @Autowired
  PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageDetailsTempRepository;

  @Autowired
  PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastDetailsTempRepository;

  @Autowired LoadingInformationStatusRepository loadingInformationStatusRepository;

  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired LoadicatorService loadicatorService;

  /**
   * Sends StowagePlans to loadicator-integration MS for Loadicator processing.
   *
   * @param request
   * @throws GenericServiceException
   */
  public String saveLoadicatorInfoForUllageUpdate(UllageBillRequest request)
      throws GenericServiceException {
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    Long loadingInfoId = request.getUpdateUllage(0).getLoadingInformationId();
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(loadingInfoId);
    if (loadingInfoOpt.isEmpty()) {
      log.info("Cannot find loading information with id {}", loadingInfoId);
      throw new GenericServiceException(
          "Could not find loading information " + loadingInfoId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    String processId = UUID.randomUUID().toString();

    List<PortLoadingPlanStowageDetails> stowageDetails =
        portLoadingPlanStowageDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanBallastDetails> ballastDetails =
        portLoadingPlanBallastDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanStowageTempDetails> tempStowageDetails =
        portLoadingPlanStowageDetailsTempRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanBallastTempDetails> tempBallastDetails =
        portLoadingPlanBallastDetailsTempRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    List<PortLoadingPlanRobDetails> robDetails =
        portLoadingPlanRobDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfoOpt.get(), true);
    Set<Long> cargoNominationIds = new LinkedHashSet<Long>();

    cargoNominationIds.addAll(
        tempStowageDetails.stream()
            .map(PortLoadingPlanStowageTempDetails::getCargoNominationXId)
            .collect(Collectors.toList()));
    Map<Long, CargoNominationDetail> cargoNomDetails =
        loadicatorService.getCargoNominationDetails(cargoNominationIds);
    CargoInfo.CargoReply cargoReply =
        loadicatorService.getCargoInfoForLoadicator(loadingInfoOpt.get());
    VesselInfo.VesselReply vesselReply =
        loadicatorService.getVesselDetailsForLoadicator(loadingInfoOpt.get());
    PortInfo.PortReply portReply = loadicatorService.getPortInfoForLoadicator(loadingInfoOpt.get());

    loadicatorRequestBuilder.setTypeId(LoadingPlanConstants.LOADING_INFORMATION_LOADICATOR_TYPE_ID);
    loadicatorRequestBuilder.setIsUllageUpdate(true);
    StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
    loadicatorService.buildStowagePlan(
        loadingInfoOpt.get(), 0, processId, cargoReply, vesselReply, portReply, stowagePlanBuilder);
    buildStowagePlanDetails(
        loadingInfoOpt.get(),
        tempStowageDetails,
        cargoNomDetails,
        vesselReply,
        cargoReply,
        stowagePlanBuilder);
    buildCargoDetails(
        loadingInfoOpt.get(), cargoNomDetails, tempStowageDetails, cargoReply, stowagePlanBuilder);
    buildBallastDetails(loadingInfoOpt.get(), tempBallastDetails, vesselReply, stowagePlanBuilder);
    buildRobDetails(loadingInfoOpt.get(), robDetails, vesselReply, stowagePlanBuilder);
    loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
    Loadicator.LoadicatorReply reply =
        loadicatorService.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to send Stowage plans to Loadicator",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    Optional<LoadingInformationStatus> loadingInfoStatusOpt =
        loadingInformationStatusRepository.findByIdAndIsActive(
            LoadingPlanConstants.LOADING_INFORMATION_PLAN_GENERATED_ID, true);
    if (loadingInfoStatusOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information status with id "
              + LoadingPlanConstants.LOADING_INFORMATION_PLAN_GENERATED_ID,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    return processId;
  }

  /**
   * Build ballastDetails for Stowage Plan
   *
   * @param loadingInformation
   * @param robDetails
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildRobDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanRobDetails> robDetails,
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

  /**
   * Builds ballast details for Stowage Plan
   *
   * @param loadingInformation
   * @param tempBallastDetails
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildBallastDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanBallastTempDetails> tempBallastDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    tempBallastDetails.forEach(
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

  /**
   * Builds Cargo Details for Stowage Plan
   *
   * @param loadingInformation
   * @param cargoNomDetails
   * @param tempStowageDetails
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildCargoDetails(
      LoadingInformation loadingInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<PortLoadingPlanStowageTempDetails> tempStowageDetails,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.stream()
        .map(stowage -> stowage.getCargoNominationXId())
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getAbbreviation()))
                  .ifPresent(cargoBuilder::setCargoAbbrev);
              Optional<PortLoadingPlanStowageTempDetails> stowageOpt =
                  tempStowageDetails.stream()
                      .filter(stwg -> stwg.getCargoNominationXId().equals(cargoNominationId))
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
  }

  /**
   * Builds stowage plan details
   *
   * @param loadingInformation
   * @param tempStowageDetails
   * @param cargoNomDetails
   * @param vesselReply
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildStowagePlanDetails(
      LoadingInformation loadingInformation,
      List<PortLoadingPlanStowageTempDetails> tempStowageDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      VesselReply vesselReply,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.forEach(
        stowage -> {
          Loadicator.StowageDetails.Builder stowageDetailsBuilder =
              Loadicator.StowageDetails.newBuilder();
          CargoNominationDetail cargoNomDetail =
              cargoNomDetails.get(stowage.getCargoNominationXId());
          Optional.ofNullable(cargoNomDetail.getCargoId())
              .ifPresent(stowageDetailsBuilder::setCargoId);
          Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
          Optional.ofNullable(stowage.getQuantity())
              .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
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
  }
}
