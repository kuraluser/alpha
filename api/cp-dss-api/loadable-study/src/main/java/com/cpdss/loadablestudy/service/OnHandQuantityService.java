/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.VoyageStatus;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.VoyageStatusRepository;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
public class OnHandQuantityService {

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private LoadablePatternService loadablePatternService;

  @Autowired private VoyageService voyageService;

  @Autowired private VoyageStatusRepository voyageStatusRepository;

  @Autowired private SynopticService synopticService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  public LoadableStudy.OnHandQuantityReply.Builder saveOnHandQuantity(
      LoadableStudy.OnHandQuantityDetail request,
      LoadableStudy.OnHandQuantityReply.Builder replyBuilder)
      throws GenericServiceException {
    OnHandQuantity entity = null;
    if (request.getId() != 0) {
      entity = this.onHandQuantityRepository.findByIdAndIsActive(request.getId(), true);
      if (null == entity) {
        throw new GenericServiceException(
            "On hand quantity with given id does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }

    } else {
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudyPortRotation portRotation =
          this.loadableStudyPortRotationRepository.findByIdAndIsActive(
              request.getPortRotationId(), true);
      if (null == portRotation) {
        throw new GenericServiceException(
            "Port rotation does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      entity = new OnHandQuantity();
      entity.setLoadableStudy(loadableStudyOpt.get());
      entity.setPortRotation(portRotation);
      entity.setPortXId(portRotation.getPortXId());
    }
    this.voyageService.checkIfVoyageClosed(entity.getLoadableStudy().getVoyage().getId());
    loadablePatternService.isPatternGeneratedOrConfirmed(entity.getLoadableStudy());

    entity = this.buildOnHandQuantityEntity(entity, request);

    // save obq level status in port rotation table
    if (null != entity.getPortRotation()) {
      entity.getPortRotation().setIsPortRotationOhqComplete(request.getIsPortRotationOhqComplete());
      this.loadableStudyPortRotationRepository.save(entity.getPortRotation());
    }
    // save obq level status in loadable study table
    this.saveOhqLevelStatus(request);

    entity = this.onHandQuantityRepository.save(entity);
    replyBuilder
        .setId(entity.getId())
        .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Build on hand quantity entity from request
   *
   * @param entity
   * @param request
   * @return
   */
  private OnHandQuantity buildOnHandQuantityEntity(
      OnHandQuantity entity, LoadableStudy.OnHandQuantityDetail request) {
    entity.setIsActive(true);
    entity.setFuelTypeXId(0 == request.getFuelTypeId() ? null : request.getFuelTypeId());
    entity.setTankXId(0 == request.getTankId() ? null : request.getTankId());
    entity.setArrivalQuantity(
        isEmpty(request.getArrivalQuantity())
            ? null
            : new BigDecimal(request.getArrivalQuantity()));
    entity.setArrivalVolume(
        isEmpty(request.getArrivalVolume()) ? null : new BigDecimal(request.getArrivalVolume()));
    entity.setDepartureQuantity(
        isEmpty(request.getDepartureQuantity())
            ? null
            : new BigDecimal(request.getDepartureQuantity()));
    entity.setDepartureVolume(
        isEmpty(request.getDepartureVolume())
            ? null
            : new BigDecimal(request.getDepartureVolume()));
    entity.setDensity(isEmpty(request.getDensity()) ? null : new BigDecimal(request.getDensity()));
    return entity;
  }

  private void saveOhqLevelStatus(LoadableStudy.OnHandQuantityDetail request)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    List<LoadableStudyPortRotation> portRotations =
        this.loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
            loadableStudyOpt.get().getId(), true);

    if (!portRotations.isEmpty()) {
      Boolean status = true;

      for (LoadableStudyPortRotation port : portRotations) {
        Boolean ohqPortRotationStatus = port.getIsPortRotationOhqComplete();
        if (null == ohqPortRotationStatus) {
          ohqPortRotationStatus = false;
        }
        status = status && ohqPortRotationStatus;
      }
      loadableStudyOpt.get().setIsOhqComplete(status);
      this.loadableStudyRepository.save(loadableStudyOpt.get());
    }
  }

  public LoadableStudy.OnHandQuantityReply.Builder getOnHandQuantity(
      LoadableStudy.OnHandQuantityRequest request,
      LoadableStudy.OnHandQuantityReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    VoyageStatus voyageStatus = this.voyageStatusRepository.getOne(CLOSE_VOYAGE_STATUS);

    LoadableStudyPortRotation portRotation =
        this.loadableStudyPortRotationRepository.findByIdAndIsActive(
            request.getPortRotationId(), true);
    if (null == portRotation) {
      throw new GenericServiceException(
          "Port rotation does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    List<OnHandQuantity> onHandQuantities =
        this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            loadableStudyOpt.get(), portRotation, true);
    VesselInfo.VesselReply vesselReply = this.getOhqTanks(request);
    if (onHandQuantities.isEmpty()) {
      synopticService.populateOnHandQuantityData(loadableStudyOpt, portRotation);
    }
    onHandQuantities =
        this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            loadableStudyOpt.get(), portRotation, true);

    for (VesselInfo.VesselTankDetail tankDetail : vesselReply.getVesselTanksList()) {
      if (!tankDetail.getShowInOhqObq()
          || OHQ_VOID_TANK_CATEGORIES.contains(tankDetail.getTankCategoryId())) {
        continue;
      }
      LoadableStudy.OnHandQuantityDetail.Builder detailBuilder =
          LoadableStudy.OnHandQuantityDetail.newBuilder();
      detailBuilder.setFuelType(tankDetail.getTankCategoryName());
      detailBuilder.setFuelTypeShortName(tankDetail.getTankCategoryShortName());
      detailBuilder.setFuelTypeId(tankDetail.getTankCategoryId());
      detailBuilder.setTankId(tankDetail.getTankId());
      detailBuilder.setTankName(tankDetail.getShortName());
      detailBuilder.setColorCode(tankDetail.getColourCode());
      detailBuilder.setPortRotationId(portRotation.getId());
      detailBuilder.setPortId(portRotation.getPortXId());
      Optional<OnHandQuantity> qtyOpt =
          onHandQuantities.stream()
              .filter(
                  entity ->
                      entity.getFuelTypeXId().equals(tankDetail.getTankCategoryId())
                          && entity.getTankXId().equals(tankDetail.getTankId()))
              .findAny();
      if (qtyOpt.isPresent()) {
        OnHandQuantity qty = qtyOpt.get();
        detailBuilder.setId(qty.getId());
        ofNullable(qty.getArrivalQuantity())
            .ifPresent(item -> detailBuilder.setArrivalQuantity(valueOf(item)));
        ofNullable(qty.getActualArrivalQuantity())
            .ifPresent(item -> detailBuilder.setActualArrivalQuantity(valueOf(item)));
        ofNullable(qty.getArrivalVolume())
            .ifPresent(item -> detailBuilder.setArrivalVolume(valueOf(item)));
        ofNullable(qty.getDepartureQuantity())
            .ifPresent(item -> detailBuilder.setDepartureQuantity(valueOf(item)));
        ofNullable(qty.getActualDepartureQuantity())
            .ifPresent(item -> detailBuilder.setActualDepartureQuantity(valueOf(item)));
        ofNullable(qty.getDepartureVolume())
            .ifPresent(item -> detailBuilder.setDepartureVolume(valueOf(item)));
        Optional.ofNullable(qty.getDensity())
            .ifPresent(item -> detailBuilder.setDensity(valueOf(item)));
        Optional.ofNullable(qty.getPortRotation())
            .ifPresent(item -> detailBuilder.setPortRotationId(item.getId()));
        Optional.ofNullable(qty.getPortRotation())
            .ifPresent(item -> detailBuilder.setPortId(item.getPortXId()));
      } else {
        if (onHandQuantities != null && !onHandQuantities.isEmpty()) {
          Optional<OnHandQuantity> ohqQtyOpt =
              onHandQuantities.stream()
                  .filter(
                      entity ->
                          entity.getFuelTypeXId().equals(tankDetail.getTankCategoryId())
                              && entity.getTankXId().equals(tankDetail.getTankId()))
                  .findAny();
          if (ohqQtyOpt.isPresent()) {
            OnHandQuantity ohqQty = ohqQtyOpt.get();
            detailBuilder.setId(ohqQty.getId());
            ofNullable(ohqQty.getArrivalQuantity())
                .ifPresent(item -> detailBuilder.setArrivalQuantity(valueOf(item)));
            ofNullable(ohqQty.getArrivalVolume())
                .ifPresent(item -> detailBuilder.setArrivalVolume(valueOf(item)));
            ofNullable(ohqQty.getDepartureQuantity())
                .ifPresent(item -> detailBuilder.setDepartureQuantity(valueOf(item)));
            ofNullable(ohqQty.getDepartureVolume())
                .ifPresent(item -> detailBuilder.setDepartureVolume(valueOf(item)));
            ofNullable(ohqQty.getDensity())
                .ifPresent(item -> detailBuilder.setDensity(valueOf(item)));
          }
        }
      }
      replyBuilder.addOnHandQuantity(detailBuilder.build());
    }
    this.createOhqVesselTankLayoutArray(vesselReply, replyBuilder);
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Get On hand quantity tanks
   *
   * @param request
   * @return
   * @throws NumberFormatException
   * @throws GenericServiceException
   */
  private VesselInfo.VesselReply getOhqTanks(LoadableStudy.OnHandQuantityRequest request)
      throws GenericServiceException {
    VesselInfo.VesselRequest.Builder vesselGrpcRequest = VesselInfo.VesselRequest.newBuilder();
    vesselGrpcRequest.setCompanyId(request.getCompanyId());
    vesselGrpcRequest.setVesselId(request.getVesselId());
    vesselGrpcRequest.addAllTankCategories(OHQ_TANK_CATEGORIES);
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
   * Group tanks by tank group
   *
   * @param vesselReply
   * @return
   */
  private void createOhqVesselTankLayoutArray(
      VesselInfo.VesselReply vesselReply, LoadableStudy.OnHandQuantityReply.Builder replyBuilder) {
    List<VesselInfo.VesselTankDetail> rearTanks = new ArrayList<>();
    List<VesselInfo.VesselTankDetail> centerTanks = new ArrayList<>();
    rearTanks.addAll(
        vesselReply.getVesselTanksList().stream()
            .filter(
                tank ->
                    OHQ_REAR_TANK_CATEGORIES.contains(tank.getTankCategoryId())
                        && tank.getShowInOhqObq())
            .collect(Collectors.toList()));
    centerTanks.addAll(
        vesselReply.getVesselTanksList().stream()
            .filter(
                tank ->
                    OHQ_CENTER_TANK_CATEGORIES.contains(tank.getTankCategoryId())
                        && tank.getShowInOhqObq())
            .collect(Collectors.toList()));
    replyBuilder.addAllTanks(this.groupTanks(centerTanks));
    replyBuilder.addAllRearTanks(this.groupTanks(rearTanks));
  }

  /**
   * Group tanks based on tank group
   *
   * @param tankDetailList
   * @return
   */
  public List<LoadableStudy.TankList> groupTanks(List<VesselInfo.VesselTankDetail> tankDetailList) {
    Map<Integer, List<VesselInfo.VesselTankDetail>> vesselTankMap = new HashMap<>();
    for (VesselInfo.VesselTankDetail tank : tankDetailList) {
      Integer tankGroup = tank.getTankGroup();
      List<VesselInfo.VesselTankDetail> list = null;
      if (null == vesselTankMap.get(tankGroup)) {
        list = new ArrayList<>();
      } else {
        list = vesselTankMap.get(tankGroup);
      }
      list.add(tank);
      vesselTankMap.put(tankGroup, list);
    }
    List<LoadableStudy.TankList> tankList = new ArrayList<>();
    List<LoadableStudy.TankDetail> tankGroup = null;
    for (Map.Entry<Integer, List<VesselInfo.VesselTankDetail>> entry : vesselTankMap.entrySet()) {
      tankGroup = entry.getValue().stream().map(this::buildTankDetail).collect(Collectors.toList());
      Collections.sort(tankGroup, Comparator.comparing(LoadableStudy.TankDetail::getTankOrder));
      tankList.add(LoadableStudy.TankList.newBuilder().addAllVesselTank(tankGroup).build());
    }
    return tankList;
  }

  /**
   * create tank detail
   *
   * @param detail
   * @return
   */
  public LoadableStudy.TankDetail buildTankDetail(VesselInfo.VesselTankDetail detail) {
    LoadableStudy.TankDetail.Builder builder = LoadableStudy.TankDetail.newBuilder();
    builder.setFrameNumberFrom(detail.getFrameNumberFrom());
    builder.setFrameNumberTo(detail.getFrameNumberTo());
    builder.setShortName(detail.getShortName());
    builder.setTankCategoryId(detail.getTankCategoryId());
    builder.setTankCategoryName(detail.getTankCategoryName());
    builder.setTankId(detail.getTankId());
    builder.setTankName(detail.getTankName());
    builder.setIsSlopTank(detail.getIsSlopTank());
    builder.setDensity(detail.getDensity());
    builder.setFillCapacityCubm(detail.getFillCapacityCubm());
    builder.setHeightFrom(detail.getHeightFrom());
    builder.setHeightTo(detail.getHeightTo());
    builder.setTankOrder(detail.getTankOrder());
    builder.setTankDisplayOrder(detail.getTankDisplayOrder());
    builder.setTankGroup(detail.getTankGroup());
    builder.setFullCapacityCubm(detail.getFullCapacityCubm());
    return builder.build();
  }

  /**
   * @param loadableStudy
   * @param loadableStudyEntity
   * @param modelMapper void
   */
  public void buildOnHandQuantityDetails(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    loadableStudy.setOnHandQuantity(new ArrayList<>());
    List<OnHandQuantity> onHandQuantities =
        onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyEntity, true);
    onHandQuantities.forEach(
        onHandQuantity -> {
          com.cpdss.loadablestudy.domain.OnHandQuantity onHandQuantityDto =
              new com.cpdss.loadablestudy.domain.OnHandQuantity();
          modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
          onHandQuantityDto =
              modelMapper.map(onHandQuantity, com.cpdss.loadablestudy.domain.OnHandQuantity.class);
          onHandQuantityDto.setFueltypeId(onHandQuantity.getFuelTypeXId());
          onHandQuantityDto.setPortId(onHandQuantity.getPortXId());
          onHandQuantityDto.setTankId(onHandQuantity.getTankXId());
          loadableStudy.getOnHandQuantity().add(onHandQuantityDto);
        });
  }

  public void deletePortRotationDetails(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      LoadableStudyPortRotation entity) {
    List<OnHandQuantity> ohqs =
        onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            loadableStudy, entity, true);
    if (!ohqs.isEmpty()) {
      ohqs.parallelStream()
          .forEach(
              ohq -> {
                ohq.setIsActive(false);
              });
      onHandQuantityRepository.saveAll(ohqs);
    }
  }
}
