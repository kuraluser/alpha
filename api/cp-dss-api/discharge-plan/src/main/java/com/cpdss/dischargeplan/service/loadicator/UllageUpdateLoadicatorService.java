/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.loadicator;

import static java.lang.String.valueOf;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.Loadicator;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.discharge_plan.DischargePlanService;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PortLoadingPlanRobDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.algo.DischargingPlanLoadicatorDetails;
import com.cpdss.dischargeplan.domain.algo.LoadicatorBallastDetails;
import com.cpdss.dischargeplan.domain.algo.LoadicatorRobDetails;
import com.cpdss.dischargeplan.domain.algo.LoadicatorStage;
import com.cpdss.dischargeplan.domain.algo.LoadicatorStowageDetails;
import com.cpdss.dischargeplan.domain.algo.UllageEditLoadicatorAlgoRequest;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastTempDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageTempDetails;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanBallastDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanBallastTempDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanRobDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageTempDetailsRepository;
import com.cpdss.dischargeplan.service.DischargePlanAlgoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/** @author pranav.k */
@Slf4j
@Service
@Transactional
public class UllageUpdateLoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired DischargeInformationRepository dischargeInformationRepository;
  @Autowired PortDischargingPlanStowageDetailsRepository portDischargingPlanStowageDetailsRepository;
  @Autowired PortDischargingPlanBallastDetailsRepository portDischargingPlanBallastDetailsRepository;
  @Autowired PortDischargingPlanRobDetailsRepository portDischargingPlanRobDetailsRepository;

  @Autowired
  PortDischargingPlanStowageTempDetailsRepository portLoadingPlanStowageDetailsTempRepository;

  @Autowired
  PortDischargingPlanBallastTempDetailsRepository portLoadingPlanBallastDetailsTempRepository;

  @Autowired DischargePlanAlgoService dischargingPlanAlgoService;
  @Autowired LoadicatorService loadicatorService;
  @Autowired DischargePlanService loadingPlanService;

  /**
   * Sends StowagePlans to loadicator-integration MS for Loadicator processing.
   *
   * @param request
   * @throws GenericServiceException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public String saveLoadicatorInfoForUllageUpdate(UllageBillRequest request)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    Long loadingInfoId = request.getUpdateUllage(0).getLoadingInformationId();
    Optional<DischargeInformation> dischargingInfoOpt =
    		dischargeInformationRepository.findByIdAndIsActiveTrue(loadingInfoId);
    if (dischargingInfoOpt.isEmpty()) {
      log.info("Cannot find loading information with id {}", loadingInfoId);
      throw new GenericServiceException(
          "Could not find loading information " + loadingInfoId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    String processId = UUID.randomUUID().toString();
    VesselInfo.VesselReply vesselReply =
        loadicatorService.getVesselDetailsForLoadicator(dischargingInfoOpt.get());
    if (!vesselReply.getVesselsList().get(0).getHasLoadicator()) {
      log.info("Vessel has no loadicator");
      UllageEditLoadicatorAlgoRequest algoRequest = new UllageEditLoadicatorAlgoRequest();
      LoadingInfoLoadicatorDataRequest.Builder loadicatorDataRequestBuilder =
          LoadingInfoLoadicatorDataRequest.newBuilder();
      loadicatorDataRequestBuilder.setProcessId(processId);
      buildUllageEditLoadicatorAlgoRequest(
          dischargingInfoOpt.get(), loadicatorDataRequestBuilder.build(), algoRequest);
      if (algoRequest.getStages().isEmpty()) {
        algoRequest.setStages(null);
      }
      saveUllageEditLoadicatorRequestJson(algoRequest, dischargingInfoOpt.get().getId());
      Optional<DischargingInformationStatus> dischargingInfoStatusOpt =
    		  dischargingPlanAlgoService.getDischargingInformationStatus(
              DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
      if (DischargePlanConstants.ARRIVAL_CONDITION_VALUE
          == request.getUpdateUllage(0).getArrivalDepartutre()) {
          DischargeInformation info = dischargeInformationRepository.findById(dischargingInfoStatusOpt.get().getId()).orElse(null);
          if(info!=null) {
        	  info.setArrivalStatusId(dischargingInfoStatusOpt.get().getId());
          }
          dischargeInformationRepository.save(info); 
       } else if (DischargePlanConstants.DEPARTURE_CONDITION_VALUE
          == request.getUpdateUllage(0).getArrivalDepartutre()) {
    	   DischargeInformation info = dischargeInformationRepository.findById(dischargingInfoStatusOpt.get().getId()).orElse(null);
           if(info!=null) {
         	  info.setDepartureStatusId(dischargingInfoStatusOpt.get().getId());
           }
           dischargeInformationRepository.save(info);
      }
      dischargingPlanAlgoService.createDischargingInformationAlgoStatus(
          dischargingInfoOpt.get(),
          processId,
          dischargingInfoStatusOpt.get(),
          request.getUpdateUllage(0).getArrivalDepartutre());
      loadingPlanService.saveUpdatedLoadingPlanDetails(
          dischargingInfoOpt.get(), request.getUpdateUllage(0).getArrivalDepartutre());
      log.info(
          "Saved updated loading plan details of loading information {}",
          dischargingInfoOpt.get().getId());
      return processId;
    }
    List<PortDischargingPlanStowageTempDetails> tempStowageDetails =
    		portLoadingPlanStowageDetailsTempRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
    List<PortDischargingPlanBallastTempDetails> tempBallastDetails =
        portLoadingPlanBallastDetailsTempRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
    List<PortDischargingPlanRobDetails> robDetails =
        portDischargingPlanRobDetailsRepository
            .findByDischargingInformationAndConditionTypeAndValueTypeAndIsActive(
                dischargingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                DischargePlanConstants.ACTUAL_TYPE_VALUE,
                true);
    Set<Long> cargoNominationIds = new LinkedHashSet<>();

    cargoNominationIds.addAll(
        tempStowageDetails.stream()
            .map(PortDischargingPlanStowageTempDetails::getCargoNominationXId)
            .collect(Collectors.toList()));
    Map<Long, CargoNominationDetail> cargoNomDetails =
        loadicatorService.getCargoNominationDetails(cargoNominationIds);
    CargoInfo.CargoReply cargoReply =
        loadicatorService.getCargoInfoForLoadicator(dischargingInfoOpt.get());
    PortInfo.PortReply portReply = loadicatorService.getPortInfoForLoadicator(dischargingInfoOpt.get());

    loadicatorRequestBuilder.setTypeId(DischargePlanConstants.LOADICATOR_TYPE_ID);
    loadicatorRequestBuilder.setIsUllageUpdate(true);
    loadicatorRequestBuilder.setConditionType(request.getUpdateUllage(0).getArrivalDepartutre());
    StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
    loadicatorService.buildStowagePlan(
        dischargingInfoOpt.get(), 0, processId, cargoReply, vesselReply, portReply, stowagePlanBuilder);
    buildStowagePlanDetails(
        dischargingInfoOpt.get(),
        tempStowageDetails,
        cargoNomDetails,
        vesselReply,
        cargoReply,
        stowagePlanBuilder);
    buildCargoDetails(
        dischargingInfoOpt.get(), cargoNomDetails, tempStowageDetails, cargoReply, stowagePlanBuilder);
    buildBallastDetails(dischargingInfoOpt.get(), tempBallastDetails, vesselReply, stowagePlanBuilder);
    buildRobDetails(dischargingInfoOpt.get(), robDetails, vesselReply, stowagePlanBuilder);
    loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
    Loadicator.LoadicatorReply reply =
        loadicatorService.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(LoadingPlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to send Stowage plans to Loadicator",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    Optional<DischargingInformationStatus> dischargingInfoStatusOpt =
        loadingPlanAlgoService.getLoadingInformationStatus(
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_STARTED_ID);
    if (LoadingPlanConstants.LOADING_PLAN_ARRIVAL_CONDITION_VALUE
        == request.getUpdateUllage(0).getArrivalDepartutre()) {
      dischargeInformationRepository.updateLoadingInformationArrivalStatus(
          dischargingInfoStatusOpt.get(), loadingInfoId);
    } else if (LoadingPlanConstants.LOADING_PLAN_DEPARTURE_CONDITION_VALUE
        == request.getUpdateUllage(0).getArrivalDepartutre()) {
      dischargeInformationRepository.updateLoadingInformationDepartureStatus(
          dischargingInfoStatusOpt.get(), loadingInfoId);
    }
    loadingPlanAlgoService.createLoadingInformationAlgoStatus(
        dischargingInfoOpt.get(),
        processId,
        dischargingInfoStatusOpt.get(),
        request.getUpdateUllage(0).getArrivalDepartutre());
    return processId;
  }

  /**
   * Build ROB details for Stowage Plan
   *
   * @param dischargeInformation
   * @param robDetails
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildRobDetails(
      DischargeInformation dischargeInformation,
      List<PortDischargingPlanRobDetails> robDetails,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {
    robDetails.forEach(
        rob -> {
          Loadicator.OtherTankInfo.Builder otherTankBuilder = Loadicator.OtherTankInfo.newBuilder();
          otherTankBuilder.setTankId(rob.getTankXId());
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(rob.getTankXId())
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
   * @param dischargeInformation
   * @param tempBallastDetails
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildBallastDetails(
      DischargeInformation dischargeInformation,
      List<PortDischargingPlanBallastTempDetails> tempBallastDetails,
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
          Optional.ofNullable(dischargeInformation.getPortXid()).ifPresent(ballastBuilder::setPortId);
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
   * @param dischargeInformation
   * @param cargoNomDetails
   * @param tempStowageDetails
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildCargoDetails(
      DischargeInformation dischargeInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<PortDischargingPlanStowageTempDetails> tempStowageDetails,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.stream()
        .map(PortDischargingPlanStowageTempDetails::getCargoNominationXId)
        .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(
                      String.valueOf(cargoNomDetails.get(cargoNominationId).getAbbreviation()))
                  .ifPresent(cargoBuilder::setCargoAbbrev);
              Optional<PortDischargingPlanStowageTempDetails> stowageOpt =
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
              Optional.ofNullable(dischargeInformation.getPortXid())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
  }

  /**
   * Builds stowage plan details
   *
   * @param dischargeInformation
   * @param tempStowageDetails
   * @param cargoNomDetails
   * @param vesselReply
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildStowagePlanDetails(
      DischargeInformation dischargeInformation,
      List<PortDischargingPlanStowageTempDetails> tempStowageDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      VesselReply vesselReply,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.stream()
        .filter(stowage -> stowage.getCargoNominationXId().longValue() != 0)
        .forEach(
            stowage -> {
              Loadicator.StowageDetails.Builder stowageDetailsBuilder =
                  Loadicator.StowageDetails.newBuilder();
              CargoNominationDetail cargoNomDetail =
                  cargoNomDetails.get(stowage.getCargoNominationXId());
              Optional.ofNullable(cargoNomDetail.getCargoId())
                  .ifPresent(stowageDetailsBuilder::setCargoId);
              Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
              Optional.ofNullable(stowage.getQuantity())
                  .ifPresent(
                      quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
              Optional.ofNullable(cargoNomDetail.getAbbreviation())
                  .ifPresent(stowageDetailsBuilder::setCargoName);
              Optional.ofNullable(dischargeInformation.getPortXid())
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

  /**
   * @param request
   * @throws GenericServiceException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public void getLoadicatorData(LoadingInfoLoadicatorDataRequest request)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {
    log.info(
        "Recieved stability parameters of Loading Plam of Loading Information {} from Loadicator",
        request.getLoadingInformationId());
    Optional<LoadingInformation> dischargingInfoOpt =
        dischargeInformationRepository.findByIdAndIsActiveTrue(request.getLoadingInformationId());
    if (dischargingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Could not find loading information " + request.getLoadingInformationId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    UllageEditLoadicatorAlgoRequest algoRequest = new UllageEditLoadicatorAlgoRequest();
    buildUllageEditLoadicatorAlgoRequest(dischargingInfoOpt.get(), request, algoRequest);
    saveUllageEditLoadicatorRequestJson(algoRequest, dischargingInfoOpt.get().getId());

    //    	    LoadicatorAlgoResponse algoResponse =
    //    	        restTemplate.postForObject(loadicatorUrl, algoRequest,
    // LoadicatorAlgoResponse.class);
    //    	    saveLoadicatorResponseJson(algoResponse, dischargingInfoOpt.get().getId());
    //
    //    	    saveLoadingSequenceStabilityParameters(dischargingInfoOpt.get(), algoResponse);

    Optional<LoadingInformationStatus> dischargingInfoStatusOpt =
        loadingPlanAlgoService.getLoadingInformationStatus(
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID);
    if (LoadingPlanConstants.LOADING_PLAN_ARRIVAL_CONDITION_VALUE == request.getConditionType()) {
      dischargeInformationRepository.updateLoadingInformationArrivalStatus(
          dischargingInfoStatusOpt.get(), dischargingInfoOpt.get().getId());
    } else if (LoadingPlanConstants.LOADING_PLAN_DEPARTURE_CONDITION_VALUE
        == request.getConditionType()) {
      dischargeInformationRepository.updateLoadingInformationDepartureStatus(
          dischargingInfoStatusOpt.get(), dischargingInfoOpt.get().getId());
    }
    loadingPlanAlgoService.updateLoadingInfoAlgoStatus(
        dischargingInfoOpt.get(), request.getProcessId(), dischargingInfoStatusOpt.get());
    loadingPlanService.saveUpdatedLoadingPlanDetails(
        dischargingInfoOpt.get(), request.getConditionType());
  }

  /**
   * @param algoRequest
   * @param id
   * @throws GenericServiceException
   */
  private void saveUllageEditLoadicatorRequestJson(
      UllageEditLoadicatorAlgoRequest algoRequest, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(LoadingPlanConstants.UPDATE_ULLAGE_LOADICATOR_REQUEST_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadingPlanEditLoadicatorRequest_"
                  + loadingInfoId
                  + ".json"),
          algoRequest);
      jsonBuilder.setJson(mapper.writeValueAsString(algoRequest));
      loadicatorService.saveJson(jsonBuilder);
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
  * 
  * @param dischargeInformation
  * @param request
  * @param algoRequest
  */
  private void buildUllageEditLoadicatorAlgoRequest(
      DischargeInformation dischargeInformation,
      LoadingInfoLoadicatorDataRequest request,
      UllageEditLoadicatorAlgoRequest algoRequest) {
    algoRequest.setLoadingInformationId(dischargeInformation.getId());
    algoRequest.setProcessId(request.getProcessId());
    algoRequest.setVesselId(dischargeInformation.getVesselXid());
    algoRequest.setPortId(dischargeInformation.getPortXid());
    //TODO ask pranav about the process id. there is no process id for DS
    //algoRequest.setLoadableStudyProcessId(dischargeInformation.get);
    List<LoadicatorStage> stages = new ArrayList<LoadicatorStage>();
    request
        .getLoadingInfoLoadicatorDetailsList()
        .forEach(
            loadicatorDetails -> {
              LoadicatorStage loadicatorStage = new LoadicatorStage();
              loadicatorService.buildLdTrim(loadicatorDetails.getLDtrim(), loadicatorStage);
              loadicatorService.buildLdIntactStability(
                  loadicatorDetails.getLDIntactStability(), loadicatorStage);
              loadicatorService.buildLdStrength(loadicatorDetails.getLDStrength(), loadicatorStage);
              stages.add(loadicatorStage);
            });
    algoRequest.setStages(stages);

    DischargingPlanLoadicatorDetails loadingPlanLoadicatorDetails = new DischargingPlanLoadicatorDetails();

    List<PortDischargingPlanStowageTempDetails> tempStowageDetails =
        portLoadingPlanStowageDetailsTempRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);
    List<PortDischargingPlanBallastTempDetails> tempBallastDetails =
        portDischargingPlanBallastDetailsRepository.findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);
    List<PortDischargingPlanRobDetails> robDetails =
        portDischargingPlanRobDetailsRepository.findByDischargingInformationAndConditionTypeAndIsActive(
            dischargeInformation.getId(), request.getConditionType(), true);

    List<LoadicatorStowageDetails> loadicatorStowageDetails =
        new ArrayList<>();
    tempStowageDetails.forEach(
        stowage -> {
          LoadicatorStowageDetails stowageDetails = new LoadicatorStowageDetails();
          BeanUtils.copyProperties(stowage, stowageDetails);
          loadicatorStowageDetails.add(stowageDetails);
        });
    loadingPlanLoadicatorDetails.setStowageDetails(loadicatorStowageDetails);
    List<LoadicatorBallastDetails> loadicatorBallastDetails =
        new ArrayList<>();
    tempBallastDetails.forEach(
        ballast -> {
          LoadicatorBallastDetails ballastDetails = new LoadicatorBallastDetails();
          BeanUtils.copyProperties(ballast, ballastDetails);
          loadicatorBallastDetails.add(ballastDetails);
        });

    loadingPlanLoadicatorDetails.setBallastDetails(loadicatorBallastDetails);
    List<LoadicatorRobDetails> loadicatorRobDetails = new ArrayList<>();
    robDetails.forEach(
        rob -> {
          LoadicatorRobDetails robDetail = new LoadicatorRobDetails();
          BeanUtils.copyProperties(rob, robDetail);
          loadicatorRobDetails.add(robDetail);
        });
    loadingPlanLoadicatorDetails.setRobDetails(loadicatorRobDetails);
    algoRequest.setPlanDetails(loadingPlanLoadicatorDetails);
  }

}
