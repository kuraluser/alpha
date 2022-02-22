/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargePlanStowageDetailsRequest;
import com.cpdss.common.generated.discharge_plan.DischargePlanStowageDetailsResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class OnBoardQuantityService {

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;

  @Autowired private VoyageService voyageService;

  @Autowired private LoadablePatternService loadablePatternService;

  @Autowired private VoyageRepository voyageRepository;

  @Autowired private OnHandQuantityService onHandQuantityService;

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired private VoyageStatusRepository voyageStatusRepository;

  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;

  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("dischargeInformationService")
  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub
      dischargePlanServiceBlockingStub;

  public LoadableStudy.OnBoardQuantityReply.Builder saveOnBoardQuantity(
      LoadableStudy.OnBoardQuantityDetail request,
      LoadableStudy.OnBoardQuantityReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    OnBoardQuantity entity = null;
    if (request.getId() == 0) {
      entity = new OnBoardQuantity();
      entity.setLoadableStudy(loadableStudyOpt.get());
    } else {
      entity = this.onBoardQuantityRepository.findByIdAndIsActive(request.getId(), true);
      if (null == entity) {
        throw new GenericServiceException(
            "On hand quantity does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    }
    this.voyageService.checkIfVoyageClosed(entity.getLoadableStudy().getVoyage().getId());
    if (loadableStudyOpt.get().getPlanningTypeXId().equals(PLANNING_TYPE_LOADING)) {
      this.voyageService.checkIfDischargingStarted(
          loadableStudyOpt.get().getVesselXId(), loadableStudyOpt.get().getVoyage().getId());
    }
    loadablePatternService.isPatternGeneratedOrConfirmed(entity.getLoadableStudy());

    this.buildOnBoardQuantityEntity(entity, request);
    entity = this.onBoardQuantityRepository.save(entity);
    loadableStudyOpt.get().setIsObqComplete(request.getIsObqComplete());
    loadableStudyOpt.get().setLoadOnTop(request.getLoadOnTop());
    this.loadableStudyRepository.save(loadableStudyOpt.get());
    replyBuilder.setId(entity.getId());
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Build on board quantity entity
   *
   * @param entity
   * @param request
   */
  private void buildOnBoardQuantityEntity(
      OnBoardQuantity entity, LoadableStudy.OnBoardQuantityDetail request) {
    entity.setTankId(request.getTankId());
    entity.setPortId(request.getPortId());
    entity.setSounding(
        isEmpty(request.getSounding()) ? null : new BigDecimal(request.getSounding()));
    entity.setColorCode(isEmpty(request.getColorCode()) ? null : request.getColorCode());
    entity.setAbbreviation(isEmpty(request.getAbbreviation()) ? null : request.getAbbreviation());
    entity.setIsActive(true);
    if (!request.getIsSlopTank()) {
      entity.setCargoId(0 == request.getCargoId() ? null : request.getCargoId());
      entity.setTankId(request.getTankId());
      entity.setPortId(request.getPortId());
      entity.setPlannedArrivalWeight(
          isEmpty(request.getWeight()) ? null : new BigDecimal(request.getWeight()));
      entity.setVolumeInM3(request.getVolume());
      entity.setDensity(
          isEmpty(request.getDensity()) ? null : new BigDecimal(request.getDensity()));
      // DSS 5450
      entity.setTemperature(
          isEmpty(request.getTemperature()) ? null : new BigDecimal(request.getTemperature()));
      entity.setIsSlopTank(false);
    } else {
      entity.setIsSlopTank(true);
      entity.setSlopQuantity(
          !hasLength(request.getSlopQuantity()) ? null : new BigDecimal(request.getSlopQuantity()));
      entity.setSlopCargoId(request.getSlopCargoId());
      entity.setSlopDensity(
          !hasLength(request.getSlopDensity()) ? null : new BigDecimal(request.getSlopDensity()));
      entity.setSlopTemperature(
          !hasLength(request.getSlopTemperature())
              ? null
              : new BigDecimal(request.getSlopTemperature()));
      entity.setSlopVolume(
          !hasLength(request.getSlopVolume()) ? null : new BigDecimal(request.getSlopVolume()));
    }
  }

  /**
   * @param loadableStudy
   * @param loadableStudyEntity
   * @param modelMapper void
   */
  public void buildOnBoardQuantityDetails(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    loadableStudy.setOnBoardQuantity(new ArrayList<>());
    List<OnBoardQuantity> onBoardQuantities =
        onBoardQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyEntity, true);
    onBoardQuantities.forEach(
        onBoardQuantity -> {
          com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantityDto =
              new com.cpdss.loadablestudy.domain.OnBoardQuantity();
          onBoardQuantityDto =
              modelMapper.map(
                  onBoardQuantity, com.cpdss.loadablestudy.domain.OnBoardQuantity.class);
          onBoardQuantityDto.setApi(
              null != onBoardQuantity.getDensity()
                  ? String.valueOf(onBoardQuantity.getDensity())
                  : "");
          onBoardQuantityDto.setTemperature(
              null != onBoardQuantity.getTemperature()
                  ? String.valueOf(onBoardQuantity.getTemperature())
                  : BigDecimal.ZERO.toString());
          // DSS-5450
          if (onBoardQuantity.getIsSlopTank()) {
            onBoardQuantityDto.setApi(
                null != onBoardQuantity.getSlopDensity()
                    ? String.valueOf(onBoardQuantity.getSlopDensity())
                    : "");
            onBoardQuantityDto.setTemperature(
                null != onBoardQuantity.getSlopTemperature()
                    ? String.valueOf(onBoardQuantity.getSlopTemperature())
                    : BigDecimal.ZERO.toString());
            onBoardQuantityDto.setVolume(
                null != onBoardQuantity.getSlopVolume()
                    ? String.valueOf(onBoardQuantity.getSlopVolume())
                    : BigDecimal.ZERO.toString());
            onBoardQuantityDto.setCargoId(onBoardQuantity.getSlopCargoId());
            onBoardQuantityDto.setPlannedArrivalWeight(
                null != onBoardQuantity.getSlopQuantity()
                    ? String.valueOf(onBoardQuantity.getSlopQuantity())
                    : BigDecimal.ZERO.toString());
          }
          loadableStudy.getOnBoardQuantity().add(onBoardQuantityDto);
        });
  }

  public LoadableStudy.OnBoardQuantityReply.Builder getOnBoardQuantity(
      LoadableStudy.OnBoardQuantityRequest request,
      LoadableStudy.OnBoardQuantityReply.Builder replyBuilder)
      throws GenericServiceException {
    Voyage voyage = this.voyageRepository.findByIdAndIsActive(request.getVoyageId(), true);
    if (null == voyage) {
      throw new GenericServiceException(
          "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    VesselInfo.VesselReply vesselReply = this.getObqTanks(request);
    replyBuilder.addAllOnBoardQuantity(
        this.buildOnBoardQuantity(
            request, loadableStudyOpt.get(), voyage, vesselReply.getVesselTanksList()));
    replyBuilder.addAllTanks(onHandQuantityService.groupTanks(vesselReply.getVesselTanksList()));
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Fetch vessel fuel and fresh water tanks
   *
   * @param request
   * @return
   * @throws GenericServiceException
   */
  private VesselInfo.VesselReply getObqTanks(LoadableStudy.OnBoardQuantityRequest request)
      throws GenericServiceException {
    VesselInfo.VesselRequest.Builder vesselGrpcRequest = VesselInfo.VesselRequest.newBuilder();
    vesselGrpcRequest.setCompanyId(request.getCompanyId());
    vesselGrpcRequest.setVesselId(request.getVesselId());
    vesselGrpcRequest.addAllTankCategories(CARGO_TANK_CATEGORIES);
    VesselInfo.VesselReply vesselReply = this.getVesselTanks(vesselGrpcRequest.build());
    if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs",
          vesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
    }
    return vesselReply;
  }

  /**
   * Get vessel fuel tanks from vessel micro service
   *
   * @param request
   * @return
   */
  public VesselInfo.VesselReply getVesselTanks(VesselInfo.VesselRequest request) {
    return this.vesselInfoGrpcService.getVesselTanks(request);
  }

  /**
   * Build obq detail objects
   *
   * @param request
   * @param loadableStudy
   * @param vesselTanksList
   * @param voyage
   */
  private List<LoadableStudy.OnBoardQuantityDetail> buildOnBoardQuantity(
      LoadableStudy.OnBoardQuantityRequest request,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      Voyage voyage,
      List<VesselInfo.VesselTankDetail> vesselTanksList)
      throws GenericServiceException {
    List<OnBoardQuantity> obqEntities =
        this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            loadableStudy, request.getPortId(), true);
    // List<CargoHistory> cargoHistories = null;
    List<LoadableStudy.OnBoardQuantityDetail> obqDetailList = new ArrayList<>();
    List<VesselInfo.VesselTankDetail> modifieableList = new ArrayList<>(vesselTanksList);
    List<LoadingPlanModels.LoadingPlanTankDetails> dischargingPlanStowageDetails =
        this.findPortDischargingPlanStowageDetailsForPrevVoyage(voyage);
    Collections.sort(
        modifieableList, Comparator.comparing(VesselInfo.VesselTankDetail::getTankDisplayOrder));
    Set<Long> cargoNominationIds =
        dischargingPlanStowageDetails.stream()
            .map(LoadingPlanModels.LoadingPlanTankDetails::getCargoNominationId)
            .collect(Collectors.toSet());
    Map<Long, CargoNomination> cargoNominationMap =
        cargoNominationIds.stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    id -> {
                      Optional<CargoNomination> cargoNominationOpt =
                          this.cargoNominationRepository.findByIdAndIsActive(id, true);
                      return cargoNominationOpt.isEmpty()
                          ? new CargoNomination()
                          : cargoNominationOpt.get();
                    }));
    List<OnBoardQuantity> onBoardQuantityEntityList = new ArrayList<>();
    if (obqEntities == null || obqEntities.size() == 0) {
      for (VesselInfo.VesselTankDetail tank : modifieableList) {
        OnBoardQuantity onBoardQuantityEntity = new OnBoardQuantity();
        Optional<LoadingPlanModels.LoadingPlanTankDetails> dsCargo =
            dischargingPlanStowageDetails.stream()
                .filter(var -> var.getTankId() == (tank.getTankId()))
                .findAny();
        if (dsCargo.isPresent()) {
          onBoardQuantityEntity.setColorCode(dsCargo.get().getColorCode());
          onBoardQuantityEntity.setAbbreviation(dsCargo.get().getAbbreviation());
          onBoardQuantityEntity.setDensity(
              StringUtils.hasLength(dsCargo.get().getApi())
                  ? new BigDecimal(dsCargo.get().getApi())
                  : BigDecimal.ZERO);
          onBoardQuantityEntity.setTemperature(
              StringUtils.hasLength(dsCargo.get().getTemperature())
                  ? new BigDecimal(dsCargo.get().getTemperature())
                  : BigDecimal.ZERO);
          onBoardQuantityEntity.setActualArrivalWeight(
              StringUtils.hasLength(dsCargo.get().getQuantity())
                  ? new BigDecimal(dsCargo.get().getQuantity())
                  : BigDecimal.ZERO);
          onBoardQuantityEntity.setActualDepartureWeight(
              StringUtils.hasLength(dsCargo.get().getQuantity())
                  ? new BigDecimal(dsCargo.get().getQuantity())
                  : BigDecimal.ZERO);
          // DSS 5450 getting volume of cargo
          onBoardQuantityEntity.setVolume(
              StringUtils.hasLength(dsCargo.get().getQuantityM3())
                  ? new BigDecimal(dsCargo.get().getQuantityM3())
                  : BigDecimal.ZERO);
          CargoNomination cargoNominationEntity =
              cargoNominationMap.get(dsCargo.get().getCargoNominationId());
          if (cargoNominationEntity != null) {
            Optional.ofNullable(cargoNominationEntity.getCargoXId())
                .ifPresent(onBoardQuantityEntity::setCargoId);
          }
        }
        onBoardQuantityEntity.setIsActive(true);
        onBoardQuantityEntity.setIsSlopTank(false);
        onBoardQuantityEntity.setLoadableStudy(loadableStudy);
        onBoardQuantityEntity.setPortId(request.getPortId());
        Optional.ofNullable(tank.getTankId()).ifPresent(onBoardQuantityEntity::setTankId);
        onBoardQuantityEntityList.add(onBoardQuantityEntity);
        /*
        Optional<CargoHistory> cargoHistoryOpt =
            cargoHistories.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
        if (cargoHistoryOpt.isPresent()) {
          CargoHistory dto = cargoHistoryOpt.get();
          ofNullable(dto.getCargoId()).ifPresent(builder::setCargoId);
          ofNullable(dto.getCargoColor()).ifPresent(builder::setColorCode);
          ofNullable(dto.getAbbreviation()).ifPresent(builder::setAbbreviation);
          ofNullable(dto.getQuantity()).ifPresent(item -> builder.setWeight(valueOf(item)));
          ofNullable(dto.getApi()).ifPresent(item -> builder.setDensity(valueOf(item)));
        }*/
      }
    }
    if (onBoardQuantityEntityList.size() > 0) {
      List<OnBoardQuantity> onBoardQuantityList =
          onBoardQuantityRepository.saveAll(onBoardQuantityEntityList);
      log.info("Onboard quantity saved successfully : " + onBoardQuantityList.size());
      loadableStudyRepository.updateOBQStatusById(true, loadableStudy.getId());
    }
    // fetch saved OBQ from OBQ table for return response to front end
    obqEntities =
        this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            loadableStudy, request.getPortId(), true);
    for (VesselInfo.VesselTankDetail tank : modifieableList) {
      LoadableStudy.OnBoardQuantityDetail.Builder builder =
          LoadableStudy.OnBoardQuantityDetail.newBuilder();
      ofNullable(tank.getShortName()).ifPresent(builder::setTankName);
      Optional<OnBoardQuantity> entityOpt =
          obqEntities.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
      if (entityOpt.isPresent()) {
        OnBoardQuantity entity = entityOpt.get();
        builder.setId(entity.getId());
        ofNullable(entity.getTankId()).ifPresent(builder::setTankId);
        ofNullable(entity.getCargoId()).ifPresent(builder::setCargoId);
        ofNullable(entity.getSounding()).ifPresent(item -> builder.setSounding(item.toString()));
        ofNullable(entity.getPlannedArrivalWeight())
            .ifPresent(item -> builder.setWeight(item.toString()));
        ofNullable(entity.getActualArrivalWeight())
            .ifPresent(item -> builder.setActualWeight(item.toString()));
        ofNullable(entity.getVolumeInM3()).ifPresent(item -> builder.setVolume(item.toString()));
        ofNullable(entity.getColorCode()).ifPresent(builder::setColorCode);
        ofNullable(entity.getAbbreviation()).ifPresent(builder::setAbbreviation);
        ofNullable(entity.getDensity()).ifPresent(item -> builder.setDensity(item.toString()));
        ofNullable(entity.getTemperature())
            .ifPresent(item -> builder.setTemperature(item.toString()));
        // DSS 5450 additional fields for tanks which can be changed to SLOP tank
        ofNullable(entity.getIsSlopTank()).ifPresent(builder::setIsSlopTank);
        ofNullable(entity.getSlopQuantity())
            .ifPresent(item -> builder.setSlopQuantity(item.toString()));
        ofNullable(entity.getSlopCargoId()).ifPresent(builder::setSlopCargoId);
        ofNullable(entity.getSlopDensity())
            .ifPresent(item -> builder.setSlopDensity(item.toString()));
        ofNullable(entity.getSlopTemperature())
            .ifPresent(item -> builder.setSlopTemperature(item.toString()));
        ofNullable(entity.getSlopVolume())
            .ifPresent(item -> builder.setSlopVolume(item.toString()));
        obqDetailList.add(builder.build());
      }
    }
    return obqDetailList;
  }

  /**
   * Cargo Deatils of Previous closed voyage's confirmed loadable study's pattern's details,
   *
   * @return
   */
  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
      findCargoDetailsForPrevVoyage(Voyage currentVoyage) {

    if (currentVoyage.getVoyageStartDate() != null && currentVoyage.getVoyageEndDate() != null) {
      VoyageStatus voyageStatus = this.voyageStatusRepository.getOne(CLOSE_VOYAGE_STATUS);
      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                  currentVoyage.getVesselXId(), true, voyageStatus);
      if (previousVoyage != null) {
        Optional<LoadableStudyStatus> confimredLSStatus =
            loadableStudyStatusRepository.findById(LoadableStudiesConstants.LS_STATUS_CONFIRMED);
        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedLS =
            previousVoyage.getLoadableStudies().stream()
                .filter(
                    var ->
                        var.getLoadableStudyStatus()
                            .getId()
                            .equals(confimredLSStatus.get().getId()))
                .findFirst();
        if (confirmedLS.isPresent()) {
          log.info(
              "Get On-Board-Quantity, previous voyage, LS id - {}, name - {}",
              confirmedLS.get().getId(),
              confirmedLS.get().getName());
          Optional<LoadablePattern> confirmedLP =
              loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
                  confirmedLS.get(), confimredLSStatus.get().getId(), true);
          if (confirmedLP.isPresent()) {
            List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> lpCargos =
                loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
                    confirmedLP.get().getId(), true);
            log.info(
                "Get On-Board-Quantity, Cargo Deatils for LP - {}, size - {}",
                confirmedLP.get().getId(),
                lpCargos.size());
            List dep =
                lpCargos.stream()
                    .filter(
                        var ->
                            var.getOperationType()
                                .equals(LoadableStudiesConstants.OPERATION_TYPE_DEP))
                    .collect(Collectors.toList());
            log.info(
                "Get On-Board-Quantity, Cargo Deatils DEP Condition for LP - {}, size - {}",
                confirmedLP.get().getId(),
                dep.size());
            return dep;
          }
        }
      }
    }
    return new ArrayList<>();
  }

  /**
   * StowageBallastDetail of Previous closed voyage's confirmed discharge study's
   * 'PortDischargingPlanBallastDetails' details
   *
   * @param currentVoyage
   * @return
   * @throws GenericServiceException
   */
  private List<LoadingPlanModels.LoadingPlanTankDetails>
      findPortDischargingPlanStowageDetailsForPrevVoyage(Voyage currentVoyage)
          throws GenericServiceException {
    if (currentVoyage.getVoyageStartDate() != null && currentVoyage.getVoyageEndDate() != null) {
      // getting previous voyage - closed or active DSS 5450
      VoyageStatus voyageStatus = this.voyageStatusRepository.getById(CLOSE_VOYAGE_STATUS);
      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateTimeDesc(
                  currentVoyage.getVesselXId(), true, voyageStatus);
      if (previousVoyage != null) {
        log.info("Last closed/Active voyage: {}", previousVoyage.getVoyageNo());
        Optional<LoadableStudyStatus> confirmedDSStatus =
            loadableStudyStatusRepository.findById(LoadableStudiesConstants.LS_STATUS_CONFIRMED);

        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedDS =
            previousVoyage.getLoadableStudies().stream()
                .filter(
                    var ->
                        (var.getLoadableStudyStatus()
                                .getId()
                                .equals(confirmedDSStatus.get().getId())
                            && var.getPlanningTypeXId() == PLANNING_TYPE_DISCHARGE))
                .findFirst();
        Long lastLoadingPort = 0L;
        if (confirmedDS.isPresent()) {
          lastLoadingPort =
              loadableStudyPortRotationService.getLastPortRotationId(
                  confirmedDS.get(),
                  this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID));
          if (lastLoadingPort != null && lastLoadingPort != 0L) {
            log.info(
                "Fetching Discharging Plan Stowage Details of Port Rotation {}", lastLoadingPort);
            DischargePlanStowageDetailsRequest.Builder builder =
                DischargePlanStowageDetailsRequest.newBuilder();
            builder.setLastLoadingPortRotationId(lastLoadingPort);
            DischargePlanStowageDetailsResponse dischargePlanStowageBallastResponse =
                dischargePlanServiceBlockingStub.dischargePlanStowageDetails(builder.build());
            if (!SUCCESS.equals(
                dischargePlanStowageBallastResponse.getResponseStatus().getStatus())) {
              throw new GenericServiceException(
                  "Failed to fetch dischargePlanStowageResponse",
                  dischargePlanStowageBallastResponse.getResponseStatus().getCode(),
                  HttpStatusCode.valueOf(
                      Integer.valueOf(
                          dischargePlanStowageBallastResponse.getResponseStatus().getCode())));
            }
            return dischargePlanStowageBallastResponse.getDischargingPlanTankDetailsList();
          }
          log.info(
              "Get On-Board-Quantity, previous voyage, DS id - {}, name - {}",
              confirmedDS.get().getId(),
              confirmedDS.get().getName());
        }
      }
    }
    return new ArrayList<>();
  }

  /**
   * Populates OBQ for a loadable Study
   *
   * @param loadableStudy
   * @return
   * @throws GenericServiceException
   */
  public List<OnBoardQuantity> populateOnBoardQuantities(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy) throws GenericServiceException {
    LoadableStudy.OnBoardQuantityRequest.Builder obqRequestBuilder =
        LoadableStudy.OnBoardQuantityRequest.newBuilder();
    obqRequestBuilder.setCompanyId(loadableStudy.getVoyage().getCompanyXId());
    obqRequestBuilder.setVesselId(loadableStudy.getVoyage().getVesselXId());
    VesselInfo.VesselReply reply = this.getObqTanks(obqRequestBuilder.build());
    List<LoadingPlanModels.LoadingPlanTankDetails> dischargingPlanStowageDetails =
        this.findPortDischargingPlanStowageDetailsForPrevVoyage(loadableStudy.getVoyage());
    Set<Long> cargoNominationIds =
        dischargingPlanStowageDetails.stream()
            .map(LoadingPlanModels.LoadingPlanTankDetails::getCargoNominationId)
            .collect(Collectors.toSet());
    Map<Long, CargoNomination> cargoNominationMap =
        cargoNominationIds.stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    id -> {
                      Optional<CargoNomination> cargoNominationOpt =
                          this.cargoNominationRepository.findByIdAndIsActive(id, true);
                      return cargoNominationOpt.isEmpty()
                          ? new CargoNomination()
                          : cargoNominationOpt.get();
                    }));
    List<OnBoardQuantity> onBoardQuantityEntityList = new ArrayList<>();
    for (VesselInfo.VesselTankDetail tank : reply.getVesselTanksList()) {
      OnBoardQuantity onBoardQuantityEntity = new OnBoardQuantity();
      Optional<LoadingPlanModels.LoadingPlanTankDetails> dsCargo =
          dischargingPlanStowageDetails.stream()
              .filter(var -> var.getTankId() == (tank.getTankId()))
              .findAny();
      if (dsCargo.isPresent()) {
        onBoardQuantityEntity.setColorCode(dsCargo.get().getColorCode());
        onBoardQuantityEntity.setAbbreviation(dsCargo.get().getAbbreviation());
        onBoardQuantityEntity.setDensity(
            StringUtils.hasLength(dsCargo.get().getApi())
                ? new BigDecimal(dsCargo.get().getApi())
                : BigDecimal.ZERO);
        onBoardQuantityEntity.setTemperature(
            StringUtils.hasLength(dsCargo.get().getTemperature())
                ? new BigDecimal(dsCargo.get().getTemperature())
                : BigDecimal.ZERO);
        onBoardQuantityEntity.setActualArrivalWeight(
            StringUtils.hasLength(dsCargo.get().getQuantity())
                ? new BigDecimal(dsCargo.get().getQuantity())
                : BigDecimal.ZERO);
        onBoardQuantityEntity.setActualDepartureWeight(
            StringUtils.hasLength(dsCargo.get().getQuantity())
                ? new BigDecimal(dsCargo.get().getQuantity())
                : BigDecimal.ZERO);
        CargoNomination cargoNominationEntity =
            cargoNominationMap.get(dsCargo.get().getCargoNominationId());
        if (cargoNominationEntity != null) {
          Optional.ofNullable(cargoNominationEntity.getCargoXId())
              .ifPresent(onBoardQuantityEntity::setCargoId);
        }
      }
      onBoardQuantityEntity.setIsActive(true);
      onBoardQuantityEntity.setIsSlopTank(false);
      onBoardQuantityEntity.setLoadableStudy(loadableStudy);
      if (loadableStudy.getPortRotations() != null) {
        Optional<LoadableStudyPortRotation> loadableStudyPortRotationOpt =
            loadableStudy.getPortRotations().stream()
                .filter(LoadableStudyPortRotation::isActive)
                .min(
                    Comparator.comparing(
                        com.cpdss.loadablestudy.entity.LoadableStudyPortRotation::getPortOrder));
        loadableStudyPortRotationOpt.ifPresent(
            loadableStudyPortRotation ->
                onBoardQuantityEntity.setPortId(loadableStudyPortRotation.getPortXId()));
      }
      onBoardQuantityEntity.setTankId(tank.getTankId());
      onBoardQuantityEntityList.add(onBoardQuantityEntity);
    }
    return onBoardQuantityEntityList;
  }
}
