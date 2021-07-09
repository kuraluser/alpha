/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Master Service for Loadable Studies
 *
 * @author johnsooraj.x
 * @since 08-04-2021
 */
@Slf4j
@Service
public class LoadableQuantityService {

  @Autowired LoadableQuantityRepository loadableQuantityRepository;

  @Autowired LoadableStudyRepository loadableStudyRepository;

  @Autowired OnHandQuantityRepository onHandQuantityRepository;

  @Autowired PortRotationService portRotationService;

  @Autowired private VoyageService voyageService;

  @Autowired private LoadablePatternService loadablePatternService;

  @Autowired LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  /**
   * Fetch Loadable quantity for LS Id and Port Rotation Id
   *
   * @param builder - GRPC Builder
   * @param loadableStudyId - Long
   * @param portRotationId - Long
   * @return LoadableQuantityResponse
   * @throws GenericServiceException - CP DSS Exception
   */
  public LoadableQuantityResponse.Builder loadableQuantityByPortId(
      LoadableQuantityResponse.Builder builder, Long loadableStudyId, Long portRotationId)
      throws GenericServiceException {

    Optional<LoadableQuantity> loadableQuantity;
    loadableQuantity =
        loadableQuantityRepository.findByLSIdAndPortRotationId(
            loadableStudyId, portRotationId, true);

    // If portRotationId is -1 then it will fetch value for synoptical table page
    // otherwise fetch value for cargo nomination page
    if (!loadableQuantity.isPresent() && portRotationId == -1) {
      List<LoadableQuantity> lQuantity =
          loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
      if (!lQuantity.isEmpty()) {
        loadableQuantity = lQuantity.stream().findFirst();
        portRotationId = loadableQuantity.get().getLoadableStudyPortRotation().getId();
        log.info("Get Loadable Quantity for Synoptic Table, PR Id {}", portRotationId);
      }
    }

    // make final for lambda expressions
    final Long portRotationFinalVal = portRotationId;

    Optional<LoadableStudy> loadableStudy =
        loadableStudyRepository.findByIdAndIsActive(loadableStudyId, true);
    if (!loadableStudy.isPresent()) {
      log.info(INVALID_LOADABLE_STUDY_ID, loadableStudyId);
      throw new GenericServiceException(
          INVALID_LOADABLE_QUANTITY,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Loadable Quantity, LS Id {}, Port Rotation Id {}", loadableStudyId, portRotationId);

    Map<Long, Long> portRIds = portRotationService.getPortRotationIdAndPortIds(loadableStudy.get());
    BigDecimal foOnboard = BigDecimal.ZERO;
    BigDecimal doOnboard = BigDecimal.ZERO;
    BigDecimal freshWaterOnBoard = BigDecimal.ZERO;
    BigDecimal boileWaterOnBoard = BigDecimal.ZERO;
    String draftRestriction1 = "";
    String seaWaterDensity = "";
    String dwtValue = "";

    List<BigDecimal> minDraftValue = new ArrayList<>();

    if (portRotationId == 0) {
      // GRPC call to Vessel Info
      dwtValue =
          String.valueOf(
              this.getDWTByVesselId(
                  loadableStudy.get().getVesselXId(), loadableStudy.get().getDraftMark()));
    }

    if (!portRIds.isEmpty() && portRIds.get(portRotationId) != null) {
      boolean isLoadingPort = true;
      LoadableStudyPortRotation portRotation =
          portRotationService.findLoadableStudyPortRotationById(portRotationId);
      if (portRotation != null) {
        // If Operation Type 1 (Loading port) ?, then isLoadingPort is already TRUE.
        if (portRotation.getOperation().getId().equals(DISCHARGING_OPERATION_ID)
            || portRotation.getOperation().getId().equals(TRANSIT_OPERATION_ID)) {
          isLoadingPort = false;
        }
        log.info(
            "Loadable Quantity, Port Rotation Operation Type - {}",
            portRotation.getOperation().getId());
        minDraftValue.add(portRotation.getMaxDraft());
        minDraftValue.add(loadableStudy.get().getDraftMark());
        Optional<BigDecimal> minVal =
            minDraftValue.stream().min(Comparator.comparing(BigDecimal::doubleValue));
        log.info("Minimum draft value among 2 {}", minVal.get());
        draftRestriction1 = minVal.isPresent() ? String.valueOf(minVal.get()) : "";
        seaWaterDensity =
            portRotation.getSeaWaterDensity() != null
                ? String.valueOf(portRotation.getSeaWaterDensity())
                : "";
        // GRPC call to Vessel Info
        dwtValue =
            String.valueOf(
                this.getDWTByVesselId(
                    loadableStudy.get().getVesselXId(),
                    portRotation.getMaxDraft() != null
                        ? portRotation.getMaxDraft()
                        : BigDecimal.ZERO));
      }

      List<OnHandQuantity> onHandQuantityList =
          this.onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudy.get(), true);
      if (!onHandQuantityList.isEmpty()) {
        foOnboard =
            onHandQuantityList.stream()
                .filter(
                    ohq ->
                        null != ohq.getFuelTypeXId()
                            && null != ohq.getPortRotation()
                            && ohq.getFuelTypeXId().equals(FUEL_OIL_TANK_CATEGORY_ID)
                            && ohq.getPortRotation().getId().equals(portRotationFinalVal)
                            && ohq.getIsActive())
                .map(
                    isLoadingPort
                        ? OnHandQuantity::getDepartureQuantity
                        : OnHandQuantity::getArrivalQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        doOnboard =
            onHandQuantityList.stream()
                .filter(
                    ohq ->
                        null != ohq.getFuelTypeXId()
                            && null != ohq.getPortRotation()
                            && ohq.getFuelTypeXId().equals(DIESEL_OIL_TANK_CATEGORY_ID)
                            && ohq.getPortRotation().getId().equals(portRotationFinalVal)
                            && ohq.getIsActive())
                .map(
                    isLoadingPort
                        ? OnHandQuantity::getDepartureQuantity
                        : OnHandQuantity::getArrivalQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        freshWaterOnBoard =
            onHandQuantityList.stream()
                .filter(
                    ohq ->
                        null != ohq.getFuelTypeXId()
                            && null != ohq.getPortRotation()
                            && ohq.getFuelTypeXId().equals(FRESH_WATER_TANK_CATEGORY_ID)
                            && ohq.getPortRotation().getId().equals(portRotationFinalVal)
                            && ohq.getIsActive())
                .map(
                    isLoadingPort
                        ? OnHandQuantity::getDepartureQuantity
                        : OnHandQuantity::getArrivalQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        boileWaterOnBoard =
            onHandQuantityList.stream()
                .filter(
                    ohq ->
                        ohq.getFuelTypeXId().equals(FRESH_WATER_TANK_CATEGORY_ID)
                            && null != ohq.getPortRotation()
                            && ohq.getPortRotation().getId().equals(portRotationFinalVal)
                            && ohq.getIsActive())
                .map(
                    isLoadingPort
                        ? OnHandQuantity::getDepartureQuantity
                        : OnHandQuantity::getArrivalQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info(
            "Loadable Quantity, values of FO/DO/FW/BW - {}/{}/{}/{}",
            foOnboard,
            doOnboard,
            freshWaterOnBoard,
            boileWaterOnBoard);
      }
    }

    // GRPC call to Vessel Info
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(loadableStudy.get().getVesselXId())
            .setVesselDraftConditionId(loadableStudy.get().getLoadLineXId())
            .setDraftExtreme(loadableStudy.get().getDraftMark().toString())
            .build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailsById(replyBuilder);
    String selectedZone = "";
    if (vesselReply.getVesselLoadableQuantityDetails().getDraftConditionName() != null) {
      String[] array =
          vesselReply.getVesselLoadableQuantityDetails().getDraftConditionName().split(" ");
      selectedZone = array[array.length - 1];
    }
    builder.setCaseNo(loadableStudy.get().getCaseNo());
    builder.setSelectedZone(selectedZone);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    String lastUpdatedTime;
    List<LocalDateTime> lastUpdateTimeList = new ArrayList<>();
    loadableQuantity.ifPresent(
        quantity -> lastUpdateTimeList.add(quantity.getLastModifiedDateTime()));
    loadableStudy.ifPresent(study -> lastUpdateTimeList.add(study.getLastModifiedDateTime()));
    LocalDateTime maxOne = Collections.max(lastUpdateTimeList);
    lastUpdatedTime = formatter.format(maxOne);

    BigDecimal displacement = BigDecimal.ZERO;
    if (!dwtValue.isEmpty() && !dwtValue.isBlank()) {
      String stringVal1 = vesselReply.getVesselLoadableQuantityDetails().getVesselLightWeight();
      log.info("Vessel Light weight is {}", stringVal1);
      BigDecimal lWeight = stringVal1.isEmpty() ? BigDecimal.ZERO : new BigDecimal(stringVal1);
      displacement = lWeight.add(new BigDecimal(dwtValue));
    }

    if (!loadableQuantity.isPresent()) {
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest loadableQuantityRequest =
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.newBuilder()
              .setDisplacmentDraftRestriction(displacement.toString())
              .setVesselLightWeight(
                  vesselReply.getVesselLoadableQuantityDetails().getVesselLightWeight())
              .setConstant(vesselReply.getVesselLoadableQuantityDetails().getConstant())
              .setTpc(vesselReply.getVesselLoadableQuantityDetails().getTpc())
              .setDraftRestriction(draftRestriction1)
              .setDwt(dwtValue)
              .setEstSeaDensity(seaWaterDensity)
              .setEstFOOnBoard(String.valueOf(foOnboard))
              .setEstDOOnBoard(String.valueOf(doOnboard))
              .setEstFreshWaterOnBoard(String.valueOf(freshWaterOnBoard))
              .setBoilerWaterOnBoard(String.valueOf(boileWaterOnBoard))
              .setLastUpdatedTime(lastUpdatedTime)
              .setPortRotationId(portRotationId)
              .build();
      builder.setLoadableQuantityRequest(loadableQuantityRequest);
      log.info(
          "Loadable Quantity, LQ Not found for LS id {}, Port Rotation Id {}",
          loadableStudyId,
          portRotationId);
    } else {
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.Builder
          loadableQuantityRequest =
              com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.newBuilder();
      loadableQuantityRequest.setLastUpdatedTime(lastUpdatedTime);
      loadableQuantityRequest.setPortRotationId(portRotationId);
      loadableQuantityRequest.setId(loadableQuantity.get().getId());
      loadableQuantityRequest.setDisplacmentDraftRestriction(displacement.toString());
      Optional.ofNullable(loadableQuantity.get().getConstant())
          .ifPresent(cons -> loadableQuantityRequest.setConstant(cons.toString()));
      Optional.ofNullable(loadableQuantity.get().getDistanceFromLastPort())
          .ifPresent(dist -> loadableQuantityRequest.setDistanceFromLastPort(dist.toString()));

      loadableQuantityRequest.setEstFOOnBoard(String.valueOf(foOnboard));
      loadableQuantityRequest.setEstDOOnBoard(String.valueOf(doOnboard));
      loadableQuantityRequest.setEstFreshWaterOnBoard(String.valueOf(freshWaterOnBoard));
      loadableQuantityRequest.setBoilerWaterOnBoard(String.valueOf(boileWaterOnBoard));

      Optional.ofNullable(loadableQuantity.get().getEstimatedSagging())
          .ifPresent(estSagging -> loadableQuantityRequest.setEstSagging(estSagging.toString()));

      // DSS-2608
      loadableQuantityRequest.setEstSeaDensity(seaWaterDensity);
      loadableQuantityRequest.setDwt(dwtValue);

      Optional.ofNullable(loadableQuantity.get().getOtherIfAny())
          .ifPresent(otherIfAny -> loadableQuantityRequest.setOtherIfAny(otherIfAny.toString()));
      Optional.ofNullable(loadableQuantity.get().getSaggingDeduction())
          .ifPresent(
              saggingDeduction ->
                  loadableQuantityRequest.setSaggingDeduction(saggingDeduction.toString()));
      Optional.ofNullable(loadableQuantity.get().getSgCorrection())
          .ifPresent(
              sgCorrection -> loadableQuantityRequest.setSgCorrection(sgCorrection.toString()));
      Optional.ofNullable(loadableQuantity.get().getTotalQuantity())
          .ifPresent(
              totalQuantity -> loadableQuantityRequest.setTotalQuantity(totalQuantity.toString()));
      Optional.ofNullable(loadableQuantity.get().getTpcatDraft())
          .ifPresent(tpc -> loadableQuantityRequest.setTpc(tpc.toString()));
      Optional.ofNullable(loadableQuantity.get().getVesselAverageSpeed())
          .ifPresent(
              vesselAverageSpeed ->
                  loadableQuantityRequest.setVesselAverageSpeed(vesselAverageSpeed.toString()));
      if (Optional.ofNullable(loadableQuantity.get().getLightWeight()).isPresent()) {
        loadableQuantityRequest.setVesselLightWeight(
            loadableQuantity.get().getLightWeight().toString());
      } else {
        loadableQuantityRequest.setVesselLightWeight(
            vesselReply.getVesselLoadableQuantityDetails().getVesselLightWeight());
      }
      Optional.ofNullable(loadableQuantity.get().getLastModifiedDateTime())
          .ifPresent(
              updateDateAndTime ->
                  loadableQuantityRequest.setUpdateDateAndTime(
                      DateTimeFormatter.ofPattern(DATE_FORMAT).format(updateDateAndTime)));
      Optional.ofNullable(loadableQuantity.get().getPortId())
          .ifPresent(portId -> loadableQuantityRequest.setPortId(portId.longValue()));

      Optional.ofNullable(loadableQuantity.get().getBallast())
          .ifPresent(ballast -> loadableQuantityRequest.setBallast(ballast.toString()));
      Optional.ofNullable(loadableQuantity.get().getRunningHours())
          .ifPresent(
              runningHours -> loadableQuantityRequest.setRunningHours(runningHours.toString()));
      Optional.ofNullable(loadableQuantity.get().getRunningDays())
          .ifPresent(runningDays -> loadableQuantityRequest.setRunningDays(runningDays.toString()));
      Optional.ofNullable(loadableQuantity.get().getFoConsumptionInSZ())
          .ifPresent(
              foConsumptionInSZ ->
                  loadableQuantityRequest.setFoConInSZ(foConsumptionInSZ.toString()));
      loadableQuantityRequest.setDraftRestriction(draftRestriction1);
      Optional.ofNullable(loadableQuantity.get().getSubTotal())
          .ifPresent(subTotal -> loadableQuantityRequest.setSubTotal(subTotal.toString()));
      Optional.ofNullable(loadableQuantity.get().getFoConsumptionPerDay())
          .ifPresent(
              foConsumptionPerDay ->
                  loadableQuantityRequest.setFoConsumptionPerDay(foConsumptionPerDay.toString()));
      builder.setLoadableQuantityRequest(loadableQuantityRequest);
      log.info(
          "Loadable Quantity, LQ Id {}, LS Id {}, Port Rotation Id {}",
          loadableQuantity.get().getId(),
          loadableStudyId,
          portRotationId);
    }
    builder.setResponseStatus(
        com.cpdss.common.generated.LoadableStudy.StatusReply.newBuilder()
            .setStatus(SUCCESS)
            .setMessage(SUCCESS));
    return builder;
  }

  public VesselInfo.VesselReply getVesselDetailsById(VesselInfo.VesselRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselDetailsById(replyBuilder);
  }

  /**
   * Fetch From Vessel Service, DWT = displacement - lightWeight
   *
   * @return
   */
  private BigDecimal getDWTByVesselId(Long vesselId, BigDecimal draftMark) {
    VesselInfo.VesselDWTRequest request =
        VesselInfo.VesselDWTRequest.newBuilder()
            .setVesselId(vesselId)
            .setDraftValue(String.valueOf(draftMark))
            .build();
    VesselInfo.VesselDWTResponse response =
        vesselInfoGrpcService.getDWTFromVesselByVesselId(request);
    if (!response.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.info(
          "Failed to fetch DWT for vessel, Error - {}", response.getResponseStatus().getMessage());
      return BigDecimal.ZERO;
    } else {
      log.info("Loadable Quantity, DWT value from vessel-info {}", response.getDwtResult());
    }
    return response.getDwtResult().length() > 0
        ? new BigDecimal(response.getDwtResult())
        : BigDecimal.ZERO;
  }

  /**
   * save Loadable Quantity
   *
   * @param loadableQuantityRequest
   * @param loadableQuantityReply
   * @return
   * @throws GenericServiceException
   */
  com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.Builder saveLoadableQuantity(
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest loadableQuantityRequest,
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.Builder loadableQuantityReply)
      throws GenericServiceException {
    LoadableQuantity loadableQuantity = null;
    Optional<LoadableStudy> loadableStudy =
        loadableStudyRepository.findById((Long) loadableQuantityRequest.getLoadableStudyId());
    if (loadableStudy.isPresent()) {
      this.voyageService.checkIfVoyageClosed(loadableStudy.get().getVoyage().getId());
      // One Loadable Quantity Record For One LS
      List<LoadableQuantity> lqs =
          loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
              loadableStudy.get().getId(), true);
      if (lqs.isEmpty()) {
        loadableQuantity = new LoadableQuantity();
      } else {
        loadableQuantity = lqs.stream().findFirst().get();
      }
      // Code for Find By Id, removed.
      log.info("Save Loadable Quantity, for Id {}", loadableQuantity.getId());

      loadableQuantity.setLoadableStudyXId(loadableStudy.get());
      loadablePatternService.isPatternGeneratedOrConfirmed(loadableQuantity.getLoadableStudyXId());
      this.copyRequestLQToEntity(loadableQuantityRequest, loadableQuantity);
      loadableQuantity = loadableQuantityRepository.save(loadableQuantity);
      // when Db save is complete we return to client a success message
      loadableQuantityReply
          .setResponseStatus(
              com.cpdss.common.generated.LoadableStudy.StatusReply.newBuilder()
                  .setStatus(SUCCESS)
                  .setMessage(SUCCESS))
          .setLoadableQuantityId(loadableQuantity.getId())
          .build();
    } else {
      log.info("INVALID_LOADABLE_STUDY {} - ", loadableQuantityRequest.getLoadableStudyId());
      loadableQuantityReply
          .setResponseStatus(
              com.cpdss.common.generated.LoadableStudy.StatusReply.newBuilder()
                  .setStatus(FAILED)
                  .setMessage(INVALID_LOADABLE_QUANTITY)
                  .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                  .setStatusCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
          .build();
    }
    return loadableQuantityReply;
  }

  private void copyRequestLQToEntity(
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest loadableQuantityRequest,
      LoadableQuantity loadableQuantity) {
    loadableQuantity.setConstant(
        StringUtils.isEmpty(loadableQuantityRequest.getConstant())
            ? null
            : new BigDecimal(loadableQuantityRequest.getConstant()));
    loadableQuantity.setDeadWeight(
        StringUtils.isEmpty(loadableQuantityRequest.getDwt())
            ? null
            : new BigDecimal(loadableQuantityRequest.getDwt()));

    loadableQuantity.setDisplacementAtDraftRestriction(
        StringUtils.isEmpty(loadableQuantityRequest.getDisplacmentDraftRestriction())
            ? null
            : new BigDecimal(loadableQuantityRequest.getDisplacmentDraftRestriction()));
    loadableQuantity.setDistanceFromLastPort(
        StringUtils.isEmpty(loadableQuantityRequest.getDistanceFromLastPort())
            ? null
            : new BigDecimal(loadableQuantityRequest.getDistanceFromLastPort()));

    loadableQuantity.setEstimatedDOOnBoard(
        StringUtils.isEmpty(loadableQuantityRequest.getEstDOOnBoard())
            ? null
            : new BigDecimal(loadableQuantityRequest.getEstDOOnBoard()));

    loadableQuantity.setEstimatedFOOnBoard(
        StringUtils.isEmpty(loadableQuantityRequest.getEstFOOnBoard())
            ? null
            : new BigDecimal(loadableQuantityRequest.getEstFOOnBoard()));
    loadableQuantity.setEstimatedFWOnBoard(
        StringUtils.isEmpty(loadableQuantityRequest.getEstFreshWaterOnBoard())
            ? null
            : new BigDecimal(loadableQuantityRequest.getEstFreshWaterOnBoard()));
    loadableQuantity.setEstimatedSagging(
        StringUtils.isEmpty(loadableQuantityRequest.getEstSagging())
            ? null
            : new BigDecimal(loadableQuantityRequest.getEstSagging()));

    loadableQuantity.setEstimatedSeaDensity(
        StringUtils.isEmpty(loadableQuantityRequest.getEstSeaDensity())
            ? null
            : new BigDecimal(loadableQuantityRequest.getEstSeaDensity()));

    loadableQuantity.setLightWeight(
        StringUtils.isEmpty(loadableQuantityRequest.getVesselLightWeight())
            ? null
            : new BigDecimal(loadableQuantityRequest.getVesselLightWeight()));

    loadableQuantity.setOtherIfAny(
        StringUtils.isEmpty(loadableQuantityRequest.getOtherIfAny())
            ? null
            : new BigDecimal(loadableQuantityRequest.getOtherIfAny()));
    loadableQuantity.setSaggingDeduction(
        StringUtils.isEmpty(loadableQuantityRequest.getSaggingDeduction())
            ? null
            : new BigDecimal(loadableQuantityRequest.getSaggingDeduction()));

    loadableQuantity.setSgCorrection(
        StringUtils.isEmpty(loadableQuantityRequest.getSgCorrection())
            ? null
            : new BigDecimal(loadableQuantityRequest.getSgCorrection()));

    loadableQuantity.setTotalQuantity(
        StringUtils.isEmpty(loadableQuantityRequest.getTotalQuantity())
            ? null
            : new BigDecimal(loadableQuantityRequest.getTotalQuantity()));
    loadableQuantity.setTpcatDraft(
        StringUtils.isEmpty(loadableQuantityRequest.getTpc())
            ? null
            : new BigDecimal(loadableQuantityRequest.getTpc()));

    loadableQuantity.setVesselAverageSpeed(
        StringUtils.isEmpty(loadableQuantityRequest.getVesselAverageSpeed())
            ? null
            : new BigDecimal(loadableQuantityRequest.getVesselAverageSpeed()));

    loadableQuantity.setPortId(
        StringUtils.isEmpty(loadableQuantityRequest.getPortId())
            ? null
            : new BigDecimal(loadableQuantityRequest.getPortId()));
    loadableQuantity.setBoilerWaterOnBoard(
        StringUtils.isEmpty(loadableQuantityRequest.getBoilerWaterOnBoard())
            ? null
            : new BigDecimal(loadableQuantityRequest.getBoilerWaterOnBoard()));
    loadableQuantity.setBallast(
        StringUtils.isEmpty(loadableQuantityRequest.getBallast())
            ? null
            : new BigDecimal(loadableQuantityRequest.getBallast()));
    loadableQuantity.setRunningHours(
        StringUtils.isEmpty(loadableQuantityRequest.getRunningHours())
            ? null
            : new BigDecimal(loadableQuantityRequest.getRunningHours()));
    loadableQuantity.setRunningDays(
        StringUtils.isEmpty(loadableQuantityRequest.getRunningDays())
            ? null
            : new BigDecimal(loadableQuantityRequest.getRunningDays()));
    loadableQuantity.setFoConsumptionInSZ(
        StringUtils.isEmpty(loadableQuantityRequest.getFoConInSZ())
            ? null
            : new BigDecimal(loadableQuantityRequest.getFoConInSZ()));
    loadableQuantity.setDraftRestriction(
        StringUtils.isEmpty(loadableQuantityRequest.getDraftRestriction())
            ? null
            : new BigDecimal(loadableQuantityRequest.getDraftRestriction()));

    loadableQuantity.setSubTotal(
        StringUtils.isEmpty(loadableQuantityRequest.getSubTotal())
            ? null
            : new BigDecimal(loadableQuantityRequest.getSubTotal()));
    loadableQuantity.setFoConsumptionPerDay(
        StringUtils.isEmpty(loadableQuantityRequest.getFoConsumptionPerDay())
            ? null
            : new BigDecimal(loadableQuantityRequest.getFoConsumptionPerDay()));
    loadableQuantity.setIsActive(true);

    if (loadableQuantityRequest.getPortRotationId() > 0) {
      log.info(
          "Save Loadable Quantity, port rotation id : {}",
          loadableQuantityRequest.getPortRotationId());
      long id = loadableQuantityRequest.getPortRotationId();
      LoadableStudyPortRotation lsPortRot =
          loadableStudyPortRotationRepository.findByIdAndIsActive(id, true);
      if (lsPortRot != null) {
        loadableQuantity.setLoadableStudyPortRotation(lsPortRot);
      }
    }
  }

  void saveLQuantity(
      com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
      Optional<LoadableStudy> loadableStudy) {
    List<LoadableQuantity> loadableQuantity =
        this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
            loadableStudy.get().getId(), true);
    if (null != loadableQuantity && !loadableQuantity.isEmpty()) {
      loadableQuantity
          .get(0)
          .setDraftRestriction(
              StringUtils.isEmpty(request.getDraftMark())
                  ? null
                  : new BigDecimal(request.getDraftMark()));
      this.loadableQuantityRepository.save(loadableQuantity.get(0));
    }
  }
}
