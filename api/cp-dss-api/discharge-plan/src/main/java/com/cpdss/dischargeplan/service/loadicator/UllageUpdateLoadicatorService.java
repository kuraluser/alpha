/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.loadicator;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudy.SynopticalBallastRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalOhqRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.algo.*;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import com.cpdss.dischargeplan.service.DischargePlanAlgoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/** @author pranav.k */
@Slf4j
@Service
@Transactional
public class UllageUpdateLoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired RestTemplate restTemplate;

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired
  PortDischargingPlanStowageDetailsRepository portDischargingPlanStowageDetailsRepository;

  @Autowired
  PortDischargingPlanBallastDetailsRepository portDischargingPlanBallastDetailsRepository;

  @Autowired
  PortDischargingPlanCommingleDetailsRepository portDischargingPlanCommingleDetailsRepository;

  @Autowired PortDischargingPlanRobDetailsRepository portDischargingPlanRobDetailsRepository;

  @Autowired
  PortDischargingPlanStowageTempDetailsRepository portDischargingPlanStowageTempDetailsRepository;

  @Autowired
  PortDischargingPlanBallastTempDetailsRepository portDischargingPlanBallastDetailsTempRepository;

  @Autowired
  PortDischargingPlanCommingleTempDetailsRepository
      portDischargingPlanCommingleTempDetailsRepository;

  @Autowired DischargePlanAlgoService dischargingPlanAlgoService;

  @Autowired LoadicatorService loadicatorService;

  @Autowired DischargeInformationStatusRepository dsInfoStatusRepository;

  @Autowired DischargeInformationService dischargeInformationService;

  @Autowired AlgoErrorHeadingRepository algoErrorHeadingRepository;

  @Autowired AlgoErrorsRepository algoErrorsRepository;

  @Autowired PortDischargingPlanStabilityParametersRepository portDisStabilityParamRepository;

  @GrpcClient("loadableStudyService")
  private SynopticalOperationServiceGrpc.SynopticalOperationServiceBlockingStub
      synopticalOperationServiceBlockingStub;

  private static final int VALUE_TYPE = 1;

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
    Long dsichargingInfoId = request.getUpdateUllage(0).getDischargingInfoId();
    Optional<DischargeInformation> dischargingInfoOpt =
        dischargeInformationRepository.findByIdAndIsActiveTrue(dsichargingInfoId);
    if (dischargingInfoOpt.isEmpty()) {
      log.info("Cannot find dsicharging information with id {}", dsichargingInfoId);
      throw new GenericServiceException(
          "Could not find loading information " + dsichargingInfoId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    String processId = UUID.randomUUID().toString();
    this.updateDischargePlanStatus(
  		  dischargingInfoOpt.get(),
            DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_STARTED_ID,
            processId,
            request.getUpdateUllage(0).getArrivalDepartutre());
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
      
      this.updateDischargePlanStatus(
    		  dischargingInfoOpt.get(),
              DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID,
              processId,
              request.getUpdateUllage(0).getArrivalDepartutre());
      saveUpdatedDischargingPlanDetails(
          dischargingInfoOpt.get(), request.getUpdateUllage(0).getArrivalDepartutre());
      log.info(
          "Saved updated loading plan details of loading information {}",
          dischargingInfoOpt.get().getId());
      return processId;
    }
    List<PortDischargingPlanStowageTempDetails> tempStowageDetails =
        portDischargingPlanStowageTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargingInfoOpt.get().getId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
    List<PortDischargingPlanBallastTempDetails> tempBallastDetails =
        portDischargingPlanBallastDetailsTempRepository
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
    List<PortDischargingPlanCommingleTempDetails> tempCommingleDetails = new ArrayList<>();
    if (!request.getCommingleUpdateList().isEmpty()) {
      tempCommingleDetails =
          portDischargingPlanCommingleTempDetailsRepository
              .findByDischargingInformationAndConditionTypeAndIsActive(
                  dischargingInfoOpt.get().getId(),
                  request.getCommingleUpdate(0).getArrivalDeparture(),
                  true);
    }
    Set<Long> cargoNominationIds = new LinkedHashSet<>();

    cargoNominationIds.addAll(
        tempStowageDetails.stream()
            .map(PortDischargingPlanStowageTempDetails::getCargoNominationXId)
            .collect(Collectors.toList()));
    Map<Long, CargoNominationDetail> cargoNomDetails =
        loadicatorService.getCargoNominationDetails(cargoNominationIds);
    CargoInfo.CargoReply cargoReply =
        loadicatorService.getCargoInfoForLoadicator(dischargingInfoOpt.get());
    PortInfo.PortReply portReply =
        loadicatorService.getPortInfoForLoadicator(dischargingInfoOpt.get());

    loadicatorRequestBuilder.setTypeId(DischargePlanConstants.LOADICATOR_TYPE_ID);
    loadicatorRequestBuilder.setIsUllageUpdate(true);
    loadicatorRequestBuilder.setConditionType(request.getUpdateUllage(0).getArrivalDepartutre());
    StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
    loadicatorService.buildStowagePlan(
        dischargingInfoOpt.get(),
        0,
        processId,
        cargoReply,
        vesselReply,
        portReply,
        stowagePlanBuilder);
    buildStowagePlanDetails(
        dischargingInfoOpt.get(),
        tempStowageDetails,
        tempCommingleDetails,
        cargoNomDetails,
        vesselReply,
        cargoReply,
        stowagePlanBuilder);
    buildCargoDetails(
        dischargingInfoOpt.get(),
        cargoNomDetails,
        tempStowageDetails,
        tempCommingleDetails,
        cargoReply,
        stowagePlanBuilder);
    buildBallastDetails(
        dischargingInfoOpt.get(), tempBallastDetails, vesselReply, stowagePlanBuilder);
    buildRobDetails(dischargingInfoOpt.get(), robDetails, vesselReply, stowagePlanBuilder);
    loadicatorRequestBuilder.addStowagePlanDetails(stowagePlanBuilder.build());
    Loadicator.LoadicatorReply reply =
        loadicatorService.saveLoadicatorInfo(loadicatorRequestBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
    	  this.updateDischargePlanStatus(
        		  dischargingInfoOpt.get(),
                  DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_FAILED_ID,
                  processId,
                  request.getUpdateUllage(0).getArrivalDepartutre());
      throw new GenericServiceException(
          "Failed to send Stowage plans to Loadicator",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    
    }

    Optional<DischargingInformationStatus> dischargingInfoStatusOpt =
        dischargingPlanAlgoService.getDischargingInformationStatus(
            DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_STARTED_ID);
    if (DischargePlanConstants.ARRIVAL_CONDITION_VALUE
        == request.getUpdateUllage(0).getArrivalDepartutre()) {
      dischargeInformationRepository.updateDischargeInformationArrivalStatus(
          dischargingInfoStatusOpt.get().getId(), dsichargingInfoId);
    } else if (DischargePlanConstants.DEPARTURE_CONDITION_VALUE
        == request.getUpdateUllage(0).getArrivalDepartutre()) {
      dischargeInformationRepository.updateDischargeInformationDepartureStatus(
          dischargingInfoStatusOpt.get().getId(), dsichargingInfoId);
    }
    dischargingPlanAlgoService.createDischargingInformationAlgoStatus(
        dischargingInfoOpt.get(),
        processId,
        dischargingInfoStatusOpt.get(),
        request.getUpdateUllage(0).getArrivalDepartutre());
    return processId;
  }

  public void saveUpdatedDischargingPlanDetails(
      DischargeInformation dischargeInformation, Integer conditionType)
      throws IllegalAccessException, InvocationTargetException, NumberFormatException,
          GenericServiceException {
    // Method used for Updating port discharging plan stowage and ballast details from  temp table
    // to
    // permanent after loadicator done
    log.info(
        "Updating Loading Plan Details of Loading Information {}", dischargeInformation.getId());
    List<PortDischargingPlanStowageTempDetails> tempStowageList =
        portDischargingPlanStowageTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), conditionType, true);
    List<PortDischargingPlanStowageDetails> stowageEntityList = new ArrayList<>();
    if (!tempStowageList.isEmpty()) {
      log.info("Copying stowage details from temporary tables");
      for (PortDischargingPlanStowageTempDetails tempStowageEntity : tempStowageList) {
        PortDischargingPlanStowageDetails stowageEntity = new PortDischargingPlanStowageDetails();
        BeanUtils.copyProperties(tempStowageEntity, stowageEntity);
        stowageEntity.setId(null);
        stowageEntity.setCreatedBy(null);
        stowageEntity.setCreatedDate(null);
        stowageEntity.setCreatedDateTime(null);
        stowageEntity.setLastModifiedBy(null);
        stowageEntity.setLastModifiedDate(null);
        stowageEntity.setLastModifiedDateTime(null);
        stowageEntity.setIsActive(true);
        stowageEntity.setDischargingInformation(dischargeInformation);
        stowageEntityList.add(stowageEntity);
      }
      // Deleting existing entry from actual table before pushing new records
      portDischargingPlanStowageDetailsRepository
          .deleteExistingByDischargingInfoAndConditionTypeAndValueType(
              dischargeInformation.getId(),
              conditionType,
              DischargePlanConstants.ACTUAL_TYPE_VALUE);
      portDischargingPlanStowageDetailsRepository.saveAll(stowageEntityList);
    }

    List<PortDischargingPlanBallastTempDetails> tempBallastList =
        portDischargingPlanBallastDetailsTempRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), conditionType, true);
    List<PortDischargingPlanBallastDetails> ballastEntityList = new ArrayList<>();
    if (!tempBallastList.isEmpty()) {
      log.info("Copying ballast details from temporary tables");
      for (PortDischargingPlanBallastTempDetails tempBallastEntity : tempBallastList) {
        PortDischargingPlanBallastDetails ballastEntity = new PortDischargingPlanBallastDetails();
        BeanUtils.copyProperties(tempBallastEntity, ballastEntity);
        ballastEntity.setId(null);
        ballastEntity.setCreatedBy(null);
        ballastEntity.setCreatedDate(null);
        ballastEntity.setCreatedDateTime(null);
        ballastEntity.setLastModifiedBy(null);
        ballastEntity.setLastModifiedDate(null);
        ballastEntity.setLastModifiedDateTime(null);
        ballastEntity.setIsActive(true);
        ballastEntity.setDischargingInformation(dischargeInformation);
        ballastEntityList.add(ballastEntity);
      }
      // Deleting existing entry from actual table before pushing new records
      portDischargingPlanBallastDetailsRepository
          .deleteExistingByDischargingInfoAndConditionTypeAndValueType(
              dischargeInformation.getId(),
              conditionType,
              DischargePlanConstants.ACTUAL_TYPE_VALUE);
      portDischargingPlanBallastDetailsRepository.saveAll(ballastEntityList);
    }

    List<PortDischargingPlanCommingleTempDetails> tempCommingleList =
        portDischargingPlanCommingleTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), conditionType, true);

    List<PortDischargingPlanCommingleDetails> commingleEntityList = new ArrayList<>();
    if (!tempCommingleList.isEmpty()) {
      log.info("Copying commingle details from temporary tables");
      for (PortDischargingPlanCommingleTempDetails tempCommingleEntity : tempCommingleList) {
        PortDischargingPlanCommingleDetails commingleEntity =
            new PortDischargingPlanCommingleDetails();
        BeanUtils.copyProperties(tempCommingleEntity, commingleEntity);
        commingleEntity.setId(null);
        commingleEntity.setCreatedBy(null);
        commingleEntity.setCreatedDate(null);
        commingleEntity.setCreatedDateTime(null);
        commingleEntity.setLastModifiedBy(null);
        commingleEntity.setLastModifiedDate(null);
        commingleEntity.setLastModifiedDateTime(null);
        commingleEntity.setIsActive(true);
        commingleEntity.setDischargingInformation(dischargeInformation);
        commingleEntityList.add(commingleEntity);
      }
      // Deleting existing entry from actual table before pushing new records
      portDischargingPlanCommingleDetailsRepository
          .deleteExistingByDischargingInformationAndConditionTypeAndValueType(
              dischargeInformation.getId(),
              conditionType,
              DischargePlanConstants.ACTUAL_TYPE_VALUE);
      portDischargingPlanCommingleDetailsRepository.saveAll(commingleEntityList);
    }
    /**
     * copying data to synoptical table. stowage quantity as cargo rob quantity as ohq ballas as
     * ballast condition type is used to determine which records are need to be updated(arrival/
     * departure) port id and port rotation id is also needed
     */
    SynopticalTableRequest.Builder request = SynopticalTableRequest.newBuilder();
    request.setLoadablePatternId(dischargeInformation.getDischargingPatternXid());
    if (conditionType.equals(1)) {
      request.setOperationType(DischargePlanConstants.ARRIVAL_CONDITION_TYPE);
    } else {
      request.setOperationType(DischargePlanConstants.DEPARTURE_CONDITION_TYPE);
    }
    request.setVesselId(dischargeInformation.getVesselXid());
    SynopticalRecord.Builder synopticalData = SynopticalRecord.newBuilder();
    synopticalData.setPortId(dischargeInformation.getPortXid());
    synopticalData.setPortRotationId(dischargeInformation.getPortRotationXid());
    stowageEntityList.stream()
        .forEach(
            stowage -> {
              SynopticalCargoRecord.Builder cargo = SynopticalCargoRecord.newBuilder();
              cargo.setActualWeight(stowage.getQuantity().toString());
              cargo.setTankId(stowage.getTankXId());
              synopticalData.addCargo(cargo);
            });
    ballastEntityList.stream()
        .forEach(
            ballast -> {
              SynopticalBallastRecord.Builder ballastRecord = SynopticalBallastRecord.newBuilder();
              ballastRecord.setActualWeight(ballast.getQuantity().toString());
              ballastRecord.setTankId(ballast.getTankXId());
              synopticalData.addBallast(ballastRecord);
            });
    List<PortDischargingPlanRobDetails> robDetails =
        portDischargingPlanRobDetailsRepository
            .findByDischargingInformationAndConditionTypeAndValueTypeAndIsActive(
                dischargeInformation.getId(), conditionType, VALUE_TYPE, true);
    robDetails.stream()
        .forEach(
            rob -> {
              SynopticalOhqRecord.Builder ohq = SynopticalOhqRecord.newBuilder();
              ohq.setActualWeight(rob.getQuantity().toString());
              ohq.setTankId(rob.getTankXId());
              synopticalData.addOhq(ohq);
            });
    commingleEntityList.stream()
        .forEach(
            commingle -> {
              LoadableStudy.SynopticalCommingleRecord.Builder commingleRecord =
                  LoadableStudy.SynopticalCommingleRecord.newBuilder();
              commingleRecord.setActualWeight(commingle.getQuantity());
              commingleRecord.setTankId(commingle.getTankId());
              synopticalData.addCommingle(commingleRecord);
            });
    request.addSynopticalRecord(synopticalData);
    ResponseStatus response =
        synopticalOperationServiceBlockingStub.updateSynopticalTable(request.build());
    if (!DischargePlanConstants.SUCCESS.equals(response.getStatus())) {
      throw new GenericServiceException(
          "Failed to update actuals",
          response.getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(response.getCode())));
    }
    // Deleting existing entry from temp tables
    portDischargingPlanStowageTempDetailsRepository.deleteExistingByDischargingInfoAndConditionType(
        dischargeInformation.getId(), conditionType);
    portDischargingPlanBallastDetailsTempRepository.deleteExistingByDischargingInfoAndConditionType(
        dischargeInformation.getId(), conditionType);
    portDischargingPlanCommingleTempDetailsRepository
        .deleteExistingByDischargingInfoAndConditionType(
            dischargeInformation.getId(), conditionType);
  }

  /**
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
          Optional.ofNullable(dischargeInformation.getPortXid())
              .ifPresent(ballastBuilder::setPortId);
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
   * @param tempCommingleDetails
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildCargoDetails(
      DischargeInformation dischargeInformation,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      List<PortDischargingPlanStowageTempDetails> tempStowageDetails,
      List<PortDischargingPlanCommingleTempDetails> tempCommingleDetails,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempStowageDetails.stream()
        .map(PortDischargingPlanStowageTempDetails::getCargoNominationXId)
        .filter(cargoNominationId -> cargoNominationId.longValue() != 0)
        .collect(Collectors.toSet())
        .forEach(
            cargoNominationId -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              Optional.ofNullable(cargoNomDetails.get(cargoNominationId))
                  .ifPresent(
                      cargoNominationDetail -> {
                        Optional.ofNullable(cargoNominationDetail.getAbbreviation())
                            .ifPresent(cargoBuilder::setCargoAbbrev);
                        Optional.ofNullable(cargoNominationDetail.getCargoId())
                            .ifPresent(cargoBuilder::setCargoId);
                      });
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
              Optional.ofNullable(dischargeInformation.getPortXid())
                  .ifPresent(cargoBuilder::setPortId);
              Optional.ofNullable(stowagePlanBuilder.getStowageId())
                  .ifPresent(cargoBuilder::setStowageId);
              stowagePlanBuilder.addCargoInfo(cargoBuilder.build());
            });
    buildLoadicatorCargoDetailsForCommingleCargo(
        tempCommingleDetails, dischargeInformation, cargoReply, stowagePlanBuilder);
  }

  /**
   * Build Loadicator Cargo Details For Commingle Cargo
   *
   * @param tempCommingleDetails
   * @param dischargeInformation
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildLoadicatorCargoDetailsForCommingleCargo(
      List<PortDischargingPlanCommingleTempDetails> tempCommingleDetails,
      DischargeInformation dischargeInformation,
      CargoReply cargoReply,
      Builder stowagePlanBuilder) {
    tempCommingleDetails.stream()
        .filter(
            distinctByKeys(
                PortDischargingPlanCommingleTempDetails::getCargoNomination1XId,
                PortDischargingPlanCommingleTempDetails::getCargoNomination2XId))
        .forEach(
            commingle -> {
              Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
              cargoBuilder.setCargoAbbrev(commingle.getGrade());
              Optional.ofNullable(String.valueOf(commingle.getApi()))
                  .ifPresent(cargoBuilder::setApi);
              Optional.ofNullable(String.valueOf(commingle.getTemperature()))
                  .ifPresent(cargoBuilder::setStandardTemp);
              Optional.ofNullable(dischargeInformation.getPortXid())
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

  /**
   * Builds stowage plan details
   *
   * @param dischargeInformation
   * @param tempStowageDetails
   * @param tempCommingleDetails
   * @param cargoNomDetails
   * @param vesselReply
   * @param cargoReply
   * @param stowagePlanBuilder
   */
  private void buildStowagePlanDetails(
      DischargeInformation dischargeInformation,
      List<PortDischargingPlanStowageTempDetails> tempStowageDetails,
      List<PortDischargingPlanCommingleTempDetails> tempCommingleDetails,
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
              Optional.ofNullable(cargoNomDetails.get(stowage.getCargoNominationXId()))
                  .ifPresent(
                      cargoNominationDetail -> {
                        Optional.ofNullable(cargoNominationDetail.getAbbreviation())
                            .ifPresent(stowageDetailsBuilder::setCargoName);
                        Optional.ofNullable(cargoNominationDetail.getCargoId())
                            .ifPresent(stowageDetailsBuilder::setCargoId);
                      });
              Optional.ofNullable(stowage.getTankXId()).ifPresent(stowageDetailsBuilder::setTankId);
              Optional.ofNullable(stowage.getQuantity())
                  .ifPresent(
                      quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));

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
    buildLoadicatorStowagePlanDetailsForCommingleCargo(
        tempCommingleDetails, dischargeInformation, cargoReply, vesselReply, stowagePlanBuilder);
  }

  /**
   * Build Loadicator Stowage Plan Details For Commingle Cargo
   *
   * @param tempCommingleDetails
   * @param dischargeInformation
   * @param cargoReply
   * @param vesselReply
   * @param stowagePlanBuilder
   */
  private void buildLoadicatorStowagePlanDetailsForCommingleCargo(
      List<PortDischargingPlanCommingleTempDetails> tempCommingleDetails,
      DischargeInformation dischargeInformation,
      CargoReply cargoReply,
      VesselReply vesselReply,
      Builder stowagePlanBuilder) {

    tempCommingleDetails.forEach(
        commingle -> {
          Loadicator.StowageDetails.Builder stowageDetailsBuilder =
              Loadicator.StowageDetails.newBuilder();
          Optional.ofNullable(commingle.getTankId()).ifPresent(stowageDetailsBuilder::setTankId);

          Optional.ofNullable(commingle.getQuantity())
              .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));

          Optional.ofNullable(dischargeInformation.getPortXid())
              .ifPresent(stowageDetailsBuilder::setPortId);
          Optional.ofNullable(stowageDetailsBuilder.getStowageId())
              .ifPresent(stowageDetailsBuilder::setStowageId);
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(commingle.getTankId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(stowageDetailsBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(stowageDetailsBuilder::setShortName);
          }

          stowageDetailsBuilder.setCargoName(commingle.getGrade());
          stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
        });
  }

  /**
   * @param algoRequest
   * @param loadingInfoId
   * @throws GenericServiceException
   */
  private void saveUllageEditLoadicatorRequestJson(
      UllageEditLoadicatorAlgoRequest algoRequest, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(DischargePlanConstants.UPDATE_ULLAGE_LOADICATOR_REQUEST_JSON_TYPE_ID);
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
   * @param lar
   * @param loadingInfoId
   * @throws GenericServiceException
   */
  private void saveUllageEditLoadicatorResponseJson(LoadicatorAlgoResponse lar, Long loadingInfoId)
      throws GenericServiceException {
    log.info("Saving Loadicator request to Loadable study DB");
    JsonRequest.Builder jsonBuilder = JsonRequest.newBuilder();
    jsonBuilder.setReferenceId(loadingInfoId);
    jsonBuilder.setJsonTypeId(
        DischargePlanConstants.UPDATE_ULLAGE_LOADICATOR_RESPONSE_JSON_TYPE_ID);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadingPlanEditLoadicatorResponse_"
                  + loadingInfoId
                  + ".json"),
          lar);
      jsonBuilder.setJson(mapper.writeValueAsString(lar));
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
   * @param dischargeInformation
   * @param request
   * @param algoRequest
   */
  private void buildUllageEditLoadicatorAlgoRequest(
      DischargeInformation dischargeInformation,
      LoadingInfoLoadicatorDataRequest request,
      UllageEditLoadicatorAlgoRequest algoRequest) {
    algoRequest.setDischargingInformationId(dischargeInformation.getId());
    algoRequest.setDischargeStudyProcessId(dischargeInformation.getDischargeStudyProcessId());
    algoRequest.setProcessId(request.getProcessId());
    algoRequest.setVesselId(dischargeInformation.getVesselXid());
    algoRequest.setPortId(dischargeInformation.getPortXid());
    algoRequest.setPortRotationId(dischargeInformation.getPortRotationXid());
    // TODO ask pranav about the process id. there is no process id for DS
    // algoRequest.setLoadableStudyProcessId(dischargeInformation.get);
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

    DischargingPlanLoadicatorDetails loadingPlanLoadicatorDetails =
        new DischargingPlanLoadicatorDetails();

    List<PortDischargingPlanStowageTempDetails> tempStowageDetails =
        portDischargingPlanStowageTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);
    List<PortDischargingPlanBallastTempDetails> tempBallastDetails =
        portDischargingPlanBallastDetailsTempRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);
    List<PortDischargingPlanRobDetails> robDetails =
        portDischargingPlanRobDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);

    List<LoadicatorStowageDetails> loadicatorStowageDetails = new ArrayList<>();
    tempStowageDetails.forEach(
        stowage -> {
          LoadicatorStowageDetails stowageDetails = new LoadicatorStowageDetails();
          BeanUtils.copyProperties(stowage, stowageDetails);
          loadicatorStowageDetails.add(stowageDetails);
        });
    loadingPlanLoadicatorDetails.setStowageDetails(loadicatorStowageDetails);
    List<LoadicatorBallastDetails> loadicatorBallastDetails = new ArrayList<>();
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

  /**
   * @param request
   * @param dischargeInformation
   * @throws GenericServiceException
   */
  public void getLoadicatorData(
      DischargingInfoLoadicatorDataRequest request, DischargeInformation dischargeInformation)
      throws GenericServiceException {
    log.info("Update Ullage Loadicator Data Received - Process Id {}", request.getProcessId());
    UllageEditLoadicatorAlgoRequest algoRequest = new UllageEditLoadicatorAlgoRequest();

    // Build Algo Request
    buildUllageEditLoadicatorAlgoRequest(dischargeInformation, request, algoRequest);

    // Save Algo Request in File
    saveUllageEditLoadicatorRequestJson(algoRequest, dischargeInformation.getId());

    // Send Payload to Algo
    LoadicatorAlgoResponse lar =
        restTemplate.postForObject(loadicatorUrl, algoRequest, LoadicatorAlgoResponse.class);

    // Save Algo Response in File
    saveUllageEditLoadicatorResponseJson(lar, dischargeInformation.getId());

    lar.getLoadicatorResults().get(0).getErrorDetails().removeIf(error -> error.isEmpty());
    if (lar.getLoadicatorResults().get(0).getErrorDetails().size() > 0) {
      // If there is error
      this.updateDischargePlanStatus(
          dischargeInformation,
          DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_FAILED_ID,
          request.getProcessId(),
          request.getConditionType());
      this.saveLoadingPlanLoadicatorErrors(
          lar.getLoadicatorResults().get(0).getErrorDetails(),
          dischargeInformation,
          request.getConditionType());
    } else {
      this.updateDischargePlanStatus(
          dischargeInformation,
          DischargePlanConstants.UPDATE_ULLAGE_VALIDATION_SUCCESS_ID,
          request.getProcessId(),
          request.getConditionType());
      this.saveDischargePlanStabilityParameters(
          dischargeInformation,
          lar,
          request.getConditionType(),
          DischargePlanConstants.DISCHARGE_PLAN_ACTUAL_TYPE_VALUE);
    }
  }

  public void saveDischargePlanStabilityParameters(
      DischargeInformation dsInfo,
      LoadicatorAlgoResponse algoResponse,
      int conditionType,
      int valueType) {
    portDisStabilityParamRepository.deleteByDischargingInformationIdAndConditionTypeAndValueType(
        dsInfo.getId(), conditionType, valueType);
    PortDischargingPlanStabilityParameters stabilityParameters =
        new PortDischargingPlanStabilityParameters();
    buildDischargePlanStabilityParams(
        algoResponse, dsInfo, conditionType, valueType, stabilityParameters);
    portDisStabilityParamRepository.save(stabilityParameters);
  }

  private void buildDischargePlanStabilityParams(
      LoadicatorAlgoResponse algoResponse,
      DischargeInformation dsInfo,
      int conditionType,
      int valueType,
      PortDischargingPlanStabilityParameters stabilityParameters) {
    LoadicatorResult result = algoResponse.getLoadicatorResults().get(0);
    loadicatorService.buildPortDischargePlanStabilityParams(
        dsInfo, result, stabilityParameters, conditionType, valueType, Optional.empty());
    portDisStabilityParamRepository.save(stabilityParameters);
  }

  public void updateDischargePlanStatus(
      DischargeInformation dsInfo, Long statusId, String processId, int conditionType) {

    Optional<DischargingInformationStatus> dis =
        dsInfoStatusRepository.findByIdAndIsActive(statusId, true);
    if (dis.isPresent()) {
      dischargeInformationService.updateDischargePlanStatus(dsInfo, dis.get(), conditionType);
      dischargingPlanAlgoService.updateDischargingInfoAlgoStatus(dsInfo, processId, dis.get());
    }
  }

  private void saveLoadingPlanLoadicatorErrors(
      List<String> errorDetails, DischargeInformation loadingInformation, int conditionType) {
    algoErrorHeadingRepository.deleteByDischargingInformationAndConditionType(
        loadingInformation, conditionType);
    algoErrorsRepository.deleteByDischargingInformationAndConditionType(
        loadingInformation, conditionType);

    AlgoErrorHeading algoErrorHeading = new AlgoErrorHeading();
    algoErrorHeading.setErrorHeading("Loadicator Errors");
    algoErrorHeading.setDischargingInformation(loadingInformation);
    algoErrorHeading.setConditionType(conditionType);
    algoErrorHeading.setIsActive(true);
    algoErrorHeadingRepository.save(algoErrorHeading);
    errorDetails.forEach(
        error -> {
          AlgoErrors algoErrors = new AlgoErrors();
          algoErrors.setAlgoErrorHeading(algoErrorHeading);
          algoErrors.setErrorMessage(error);
          algoErrors.setIsActive(true);
          algoErrorsRepository.save(algoErrors);
        });
  }

  /**
   * @param dischargeInformation
   * @param request
   * @param algoRequest
   */
  private void buildUllageEditLoadicatorAlgoRequest(
      DischargeInformation dischargeInformation,
      DischargingInfoLoadicatorDataRequest request,
      UllageEditLoadicatorAlgoRequest algoRequest) {
    algoRequest.setDischargingInformationId(dischargeInformation.getId());
    algoRequest.setDischargeStudyProcessId(dischargeInformation.getDischargeStudyProcessId());
    algoRequest.setProcessId(request.getProcessId());
    algoRequest.setVesselId(dischargeInformation.getVesselXid());
    algoRequest.setPortId(dischargeInformation.getPortXid());
    algoRequest.setPortRotationId(dischargeInformation.getPortRotationXid());
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

    DischargingPlanLoadicatorDetails loadingPlanLoadicatorDetails =
        new DischargingPlanLoadicatorDetails();

    List<PortDischargingPlanStowageTempDetails> tempStowageDetails =
        portDischargingPlanStowageTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);
    List<PortDischargingPlanBallastTempDetails> tempBallastDetails =
        portDischargingPlanBallastDetailsTempRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);
    List<PortDischargingPlanRobDetails> robDetails =
        portDischargingPlanRobDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                dischargeInformation.getId(), request.getConditionType(), true);

    List<LoadicatorStowageDetails> loadicatorStowageDetails = new ArrayList<>();
    tempStowageDetails.forEach(
        stowage -> {
          LoadicatorStowageDetails stowageDetails = new LoadicatorStowageDetails();
          BeanUtils.copyProperties(stowage, stowageDetails);
          loadicatorStowageDetails.add(stowageDetails);
        });
    loadingPlanLoadicatorDetails.setStowageDetails(loadicatorStowageDetails);
    List<LoadicatorBallastDetails> loadicatorBallastDetails = new ArrayList<>();
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
