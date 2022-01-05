/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;
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
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    entity.setCargoId(0 == request.getCargoId() ? null : request.getCargoId());
    entity.setTankId(request.getTankId());
    entity.setPortId(request.getPortId());
    entity.setSounding(
        isEmpty(request.getSounding()) ? null : new BigDecimal(request.getSounding()));
    entity.setPlannedArrivalWeight(
        isEmpty(request.getWeight()) ? null : new BigDecimal(request.getWeight()));
    entity.setVolumeInM3(request.getVolume());
    entity.setColorCode(isEmpty(request.getColorCode()) ? null : request.getColorCode());
    entity.setAbbreviation(isEmpty(request.getAbbreviation()) ? null : request.getAbbreviation());
    entity.setDensity(isEmpty(request.getDensity()) ? null : new BigDecimal(request.getDensity()));
    entity.setIsActive(true);
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
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetailsList = null;
    List<LoadingPlanModels.LoadingPlanTankDetails> dischargingPlanStowageBallastDetails = null;
    Collections.sort(
        modifieableList, Comparator.comparing(VesselInfo.VesselTankDetail::getTankDisplayOrder));
    List<OnBoardQuantity> onBoardQuantityEntityList = new ArrayList<>();
    if (obqEntities == null || obqEntities.size() == 0) {
      for (VesselInfo.VesselTankDetail tank : modifieableList) {
        OnBoardQuantity onBoardQuantityEntity = new OnBoardQuantity();

        // if planingType = 1 means save OBQ for loadable study
        if (request.getPlaningType() == Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE) {
          // lazy loading the cargo history
          if (null == cargoDetailsList) {
            // cargoHistories = this.findCargoHistoryForPrvsVoyage(voyage);
            cargoDetailsList = this.findCargoDetailsForPrevVoyage(voyage);
          }
          Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> lpCargo =
              cargoDetailsList.stream()
                  .filter(var -> var.getTankId().equals(tank.getTankId()))
                  .findAny();
          if (lpCargo.isPresent()) {
            Optional.ofNullable(lpCargo.get().getCargoId())
                .ifPresent(onBoardQuantityEntity::setCargoId);
            Optional.ofNullable(lpCargo.get().getColorCode())
                .ifPresent(onBoardQuantityEntity::setColorCode);
            Optional.ofNullable(lpCargo.get().getAbbreviation())
                .ifPresent(onBoardQuantityEntity::setAbbreviation);
            Optional.ofNullable(lpCargo.get().getApi())
                .ifPresentOrElse(
                    onBoardQuantityEntity::setDensity,
                    () -> onBoardQuantityEntity.setDensity(new BigDecimal(0)));
            Optional.ofNullable(lpCargo.get().getActualQuantity())
                .ifPresentOrElse(
                    onBoardQuantityEntity::setPlannedArrivalWeight,
                    () -> onBoardQuantityEntity.setPlannedArrivalWeight(new BigDecimal(0)));
            Optional.ofNullable(lpCargo.get().getActualQuantity())
                .ifPresentOrElse(
                    onBoardQuantityEntity::setPlannedDepartureWeight,
                    () -> onBoardQuantityEntity.setPlannedDepartureWeight(new BigDecimal(0)));
            Optional.ofNullable(lpCargo.get().getTemperature())
                .ifPresentOrElse(
                    onBoardQuantityEntity::setTemperature,
                    () -> onBoardQuantityEntity.setTemperature(new BigDecimal(0)));
          }
        }

        // if planingType = 2 means save OBQ for discharge plan
        if (request.getPlaningType() == Common.PLANNING_TYPE.DISCHARGE_STUDY_VALUE) {
          if (null == dischargingPlanStowageBallastDetails) {
            dischargingPlanStowageBallastDetails =
                this.findPortDischargingPlanStowageBallastDetailForPrevVoyage(voyage);
          }
          Optional<LoadingPlanModels.LoadingPlanTankDetails> dsCargo =
              dischargingPlanStowageBallastDetails.stream()
                  .filter(var -> var.getTankId() == (tank.getTankId()))
                  .findAny();
          if (dsCargo.isPresent()) {

            Optional.ofNullable(dsCargo.get().getColorCode())
                .ifPresent(onBoardQuantityEntity::setColorCode);
            Optional.ofNullable(dsCargo.get().getAbbreviation())
                .ifPresent(onBoardQuantityEntity::setAbbreviation);
            Optional.ofNullable(dsCargo.get().getApi())
                .ifPresentOrElse(
                    api -> onBoardQuantityEntity.setDensity(new BigDecimal(api)),
                    () -> onBoardQuantityEntity.setDensity(new BigDecimal(0)));
            Optional.ofNullable(dsCargo.get().getTemperature())
                .ifPresentOrElse(
                    temp -> onBoardQuantityEntity.setTemperature(new BigDecimal(temp)),
                    () -> onBoardQuantityEntity.setTemperature(new BigDecimal(0)));
            Optional.ofNullable(dsCargo.get().getSounding())
                .ifPresentOrElse(
                    temp -> onBoardQuantityEntity.setSounding(new BigDecimal(temp)),
                    () -> onBoardQuantityEntity.setSounding(new BigDecimal(0)));
            Optional<Long> cargoNomination =
                Optional.ofNullable(dsCargo.get().getCargoNominationId());
            if (cargoNomination.isPresent()) {
              Optional<CargoNomination> cargoNominationEntity =
                  cargoNominationRepository.findByIdAndIsActive(cargoNomination.get(), true);
              if (cargoNominationEntity.isPresent()) {
                Optional.ofNullable(cargoNominationEntity.get().getCargoXId())
                    .ifPresent(onBoardQuantityEntity::setCargoId);
              }
            }
          }
        }

        onBoardQuantityEntity.setIsActive(true);
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
        ofNullable(entity.getVolume()).ifPresent(item -> builder.setVolume(item.toString()));
        ofNullable(entity.getColorCode()).ifPresent(builder::setColorCode);
        ofNullable(entity.getAbbreviation()).ifPresent(builder::setAbbreviation);
        ofNullable(entity.getDensity()).ifPresent(item -> builder.setDensity(item.toString()));
        ofNullable(entity.getTemperature())
            .ifPresent(item -> builder.setTemperature(item.toString()));
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
      findPortDischargingPlanStowageBallastDetailForPrevVoyage(Voyage currentVoyage)
          throws GenericServiceException {
    if (currentVoyage.getVoyageStartDate() != null && currentVoyage.getVoyageEndDate() != null) {
      VoyageStatus voyageStatus = this.voyageStatusRepository.getOne(CLOSE_VOYAGE_STATUS);
      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                  currentVoyage.getVesselXId(), true, voyageStatus);
      if (previousVoyage != null) {
        Optional<LoadableStudyStatus> confirmedDSStatus =
            loadableStudyStatusRepository.findById(LoadableStudiesConstants.LS_STATUS_CONFIRMED);

        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedDS =
            previousVoyage.getLoadableStudies().stream()
                .filter(
                    var ->
                        (var.getLoadableStudyStatus()
                            .getId()
                            .equals(confirmedDSStatus.get().getId())))
                .findFirst();
        Long lastLoadingPort = 0L;
        if (confirmedDS.isPresent()) {
          lastLoadingPort =
              loadableStudyPortRotationService.getLastPort(
                  confirmedDS.get(),
                  this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID));
          if (lastLoadingPort != null && lastLoadingPort != 0L) {
            DischargePlanStowageDetailsRequest.Builder builder =
                DischargePlanStowageDetailsRequest.newBuilder();
            builder.setLastLoadingPortRotationId(lastLoadingPort);
            DischargePlanStowageDetailsResponse dischargePlanStowageBallastResponse =
                dischargePlanServiceBlockingStub.dischargePlanStowageDetails(builder.build());
            if (!SUCCESS.equals(
                dischargePlanStowageBallastResponse.getResponseStatus().getStatus())) {
              throw new GenericServiceException(
                  "Failed to fetch dischargePlanStowageBallastResponse",
                  dischargePlanStowageBallastResponse.getResponseStatus().getCode(),
                  HttpStatusCode.valueOf(
                      Integer.valueOf(
                          dischargePlanStowageBallastResponse.getResponseStatus().getCode())));
            }
            return dischargePlanStowageBallastResponse.getDischargingPlanTankDetailsList();
          }
        }
        log.info(
            "Get On-Board-Quantity, previous voyage, DS id - {}, name - {}",
            confirmedDS.get().getId(),
            confirmedDS.get().getName());
      }
    }
    return new ArrayList<>();
  }
}
