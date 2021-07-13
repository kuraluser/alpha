/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Master Service for Voyage Related Operations
 *
 * @author Johnsooraj.x
 */
@Slf4j
@Service
public class LoadicatorService {

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("loadicatorService")
  private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorService;

  public void saveLodicatorDataForSynoptical(
      LoadablePattern loadablePattern,
      LoadableStudy.LoadablePlanDetailsReply arrivalCondition,
      LoadableStudy.LoadablePlanDetails lpd,
      String portType,
      Long portRotationId) {

    SynopticalTableLoadicatorData synopticalTableLoadicatorData =
        new SynopticalTableLoadicatorData();

    LoadableStudyPortRotation lsPortRotOption =
        loadableStudyPortRotationRepository.getOne(portRotationId);

    Optional<SynopticalTable> synData =
        synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
            lsPortRotOption.getId(), portType, true);

    if (synData.isPresent()) {
      LoadableStudy.StabilityParameter stabilityParameter =
          arrivalCondition.getStabilityParameter();
      synopticalTableLoadicatorData.setLoadablePatternId(loadablePattern.getId());

      synopticalTableLoadicatorData.setCalculatedDraftFwdPlanned(
          isEmpty(stabilityParameter.getForwardDraft())
              ? null
              : new BigDecimal(stabilityParameter.getForwardDraft()));

      synopticalTableLoadicatorData.setCalculatedDraftMidPlanned(
          isEmpty(stabilityParameter.getMeanDraft())
              ? null
              : new BigDecimal(stabilityParameter.getMeanDraft()));

      synopticalTableLoadicatorData.setCalculatedDraftAftPlanned(
          isEmpty(stabilityParameter.getAfterDraft())
              ? null
              : new BigDecimal(stabilityParameter.getAfterDraft()));

      synopticalTableLoadicatorData.setCalculatedTrimPlanned(
          isEmpty(stabilityParameter.getTrim())
              ? null
              : new BigDecimal(stabilityParameter.getTrim()));
      synopticalTableLoadicatorData.setBendingMoment(
          isEmpty(stabilityParameter.getBendinMoment())
              ? null
              : new BigDecimal(stabilityParameter.getBendinMoment()));
      synopticalTableLoadicatorData.setShearingForce(
          isEmpty(stabilityParameter.getShearForce())
              ? null
              : new BigDecimal(stabilityParameter.getShearForce()));
      synopticalTableLoadicatorData.setActive(true);
      synopticalTableLoadicatorData.setSynopticalTable(synData.get());
      synopticalTableLoadicatorDataRepository.save(synopticalTableLoadicatorData);
    }
  }

  /**
   * Save to loadicator tables
   *
   * @param loadableStudyEntity
   * @param processId
   */
  public void saveLoadicatorInfo(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity,
      String processId,
      Long patternId,
      List<LoadablePattern> loadablePatternsList) {
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    try {
      List<LoadablePattern> loadablePatterns = null;
      if (patternId == 0) {
        loadablePatterns = loadablePatternsList;
        loadicatorRequestBuilder.setIsPattern(false);
        loadicatorRequestBuilder.setLoadableStudyId(loadableStudyEntity.getId());
      } else {
        Optional<LoadablePattern> lpOpt =
            this.loadablePatternRepository.findByIdAndIsActive(patternId, true);
        loadablePatterns =
            lpOpt.isPresent() ? new ArrayList<LoadablePattern>(Arrays.asList(lpOpt.get())) : null;
        loadicatorRequestBuilder.setIsPattern(true);
        loadicatorRequestBuilder.setLoadablePatternId(lpOpt.get().getId());
      }
      if (null == loadablePatterns) {
        throw new GenericServiceException(
            "No loadable patterns found for this loadable study",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      List<Long> loadablePatternIds =
          loadablePatterns.stream().map(LoadablePattern::getId).collect(Collectors.toList());
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails =
          this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdInAndIsActive(
              loadablePatternIds, true);
      List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
          loadablePatternCommingleCargoDetails =
              this.loadablePlanCommingleDetailsPortwiseRepository
                  .findByLoadablePatternIdInAndIsActive(loadablePatternIds, true);
      List<LoadablePlanStowageBallastDetails> loadablePatternBallastList =
          this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdInAndIsActive(
              loadablePatternIds, true);
      List<SynopticalTable> synopticalEntities =
          this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
              loadableStudyEntity.getId(), true);
      List<OnHandQuantity> ohqEntities =
          this.onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyEntity, true);
      CargoInfo.CargoReply cargoReply = this.getCargoInfoForLoadicator(loadableStudyEntity);
      VesselInfo.VesselReply vesselReply = this.getVesselDetailsForLoadicator(loadableStudyEntity);
      PortInfo.PortReply portReply = this.getPortInfoForLoadicator(loadableStudyEntity);
      for (LoadablePattern pattern : loadablePatterns) {
        for (SynopticalTable synopticalEntity : synopticalEntities) {
          loadicatorRequestBuilder.addStowagePlanDetails(
              this.buildLoadicatorStowagePlan(
                  pattern,
                  synopticalEntity,
                  portReply,
                  vesselReply,
                  cargoReply,
                  processId,
                  loadableStudyEntity,
                  loadablePatternCargoDetails,
                  loadablePatternBallastList,
                  ohqEntities,
                  loadablePatternCommingleCargoDetails));
        }
      }
      Loadicator.LoadicatorReply reply = this.saveLoadicatorInfo(loadicatorRequestBuilder.build());

    } catch (GenericServiceException e) {
      log.error("Error in saveLoadicatorInfo ", e);
    } catch (Exception e) {
      log.error("Error saving LoadicatorInfo ", e);
    }
  }

  /**
   * Get cargo deatils
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  private CargoInfo.CargoReply getCargoInfoForLoadicator(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity)
      throws GenericServiceException {
    CargoInfo.CargoRequest cargoRequest =
        CargoInfo.CargoRequest.newBuilder()
            .setVesselId(loadableStudyEntity.getVesselXId())
            .setVoyageId(loadableStudyEntity.getVoyage().getId())
            .setLoadableStudyId(loadableStudyEntity.getId())
            .build();
    CargoInfo.CargoReply cargoReply = this.getCargoInfo(cargoRequest);
    if (!SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
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

  /**
   * get vessel detail for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  private VesselInfo.VesselReply getVesselDetailsForLoadicator(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity)
      throws GenericServiceException {
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(loadableStudyEntity.getVesselXId())
            .setVesselDraftConditionId(loadableStudyEntity.getLoadLineXId())
            .setDraftExtreme(loadableStudyEntity.getDraftMark().toString())
            .build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailByVesselId(replyBuilder);
    if (!SUCCESS.equalsIgnoreCase(vesselReply.getResponseStatus().getStatus())) {
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
   * Get port info for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  private PortInfo.PortReply getPortInfoForLoadicator(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity)
      throws GenericServiceException {
    PortInfo.PortRequest portRequest =
        PortInfo.PortRequest.newBuilder()
            .setVesselId(loadableStudyEntity.getVesselXId())
            .setVoyageId(loadableStudyEntity.getVoyage().getId())
            .setLoadableStudyId(loadableStudyEntity.getId())
            .build();
    PortInfo.PortReply portReply = this.GetPortInfo(portRequest);
    if (!SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
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

  public Loadicator.LoadicatorReply saveLoadicatorInfo(Loadicator.LoadicatorRequest build) {
    return loadicatorService.saveLoadicatorInfo(build);
  }

  /**
   * Build loadicator stowage plan
   *
   * @param pattern
   * @param synopticalEntity
   * @param portReply
   * @param vesselReply
   * @param cargoReply
   * @param processId
   * @param loadableStudyEntity
   * @param loadablePatternCargoDetails
   * @param loadablePatternBallastList
   * @param ohqEntities
   * @return
   */
  private Loadicator.StowagePlan buildLoadicatorStowagePlan(
      LoadablePattern pattern,
      SynopticalTable synopticalEntity,
      PortInfo.PortReply portReply,
      VesselInfo.VesselReply vesselReply,
      CargoInfo.CargoReply cargoReply,
      String processId,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity,
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails,
      List<LoadablePlanStowageBallastDetails> loadablePatternBallastList,
      List<OnHandQuantity> ohqEntities,
      List<LoadablePlanComminglePortwiseDetails> loadablePatternCommingleCargoDetails) {
    Loadicator.StowagePlan.Builder stowagePlanBuilder = Loadicator.StowagePlan.newBuilder();
    this.buildLoadicatorStowagePlan(
        stowagePlanBuilder,
        pattern,
        synopticalEntity,
        portReply,
        vesselReply,
        processId,
        loadableStudyEntity);
    this.buildLoadicatorStowagePlanDetails(
        stowagePlanBuilder,
        loadablePatternCargoDetails,
        vesselReply,
        cargoReply,
        synopticalEntity,
        loadablePatternCommingleCargoDetails);
    this.buildLoadicatorCargoDetails(
        stowagePlanBuilder,
        loadablePatternCargoDetails,
        synopticalEntity,
        cargoReply,
        loadablePatternCommingleCargoDetails);
    this.buildLoadicatorBallastDetails(
        stowagePlanBuilder, vesselReply, synopticalEntity, loadablePatternBallastList);
    this.buildLoadicatorOhqDetails(stowagePlanBuilder, vesselReply, synopticalEntity, ohqEntities);
    return stowagePlanBuilder.build();
  }

  /**
   * Build stowage plan
   *
   * @param stowagePlanBuilder
   * @param pattern
   * @param synopticalEntity
   * @param portReply
   * @param vesselReply
   * @param processId
   * @param loadableStudyEntity
   */
  private void buildLoadicatorStowagePlan(
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      LoadablePattern pattern,
      SynopticalTable synopticalEntity,
      PortInfo.PortReply portReply,
      VesselInfo.VesselReply vesselReply,
      String processId,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity) {
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
    stowagePlanBuilder.setPortId(synopticalEntity.getLoadableStudyPortRotation().getPortXId());
    stowagePlanBuilder.setStowageId(pattern.getId());
    stowagePlanBuilder.setStatus(STOWAGE_STATUS);
    stowagePlanBuilder.setBookingListId(loadableStudyEntity.getId());
    Optional<PortInfo.PortDetail> portDetail =
        portReply.getPortsList().stream()
            .filter(
                port ->
                    Long.valueOf(port.getId())
                        .equals(synopticalEntity.getLoadableStudyPortRotation().getPortXId()))
            .findAny();
    if (portDetail.isPresent()) {
      Optional.ofNullable(portDetail.get().getCode()).ifPresent(stowagePlanBuilder::setPortCode);
    }
    Optional.ofNullable(synopticalEntity.getLoadableStudyPortRotation().getSeaWaterDensity())
        .ifPresent(density -> stowagePlanBuilder.setSeaWaterDensity(valueOf(density)));
    stowagePlanBuilder.setSynopticalId(synopticalEntity.getId());
  }

  /**
   * Build loadicator OHQ data
   *
   * @param stowagePlanBuilder
   * @param vesselReply
   * @param synopticalEntity
   * @param ohqEntities
   */
  private void buildLoadicatorOhqDetails(
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      VesselInfo.VesselReply vesselReply,
      SynopticalTable synopticalEntity,
      List<OnHandQuantity> ohqEntities) {
    List<OnHandQuantity> synopticalWiseList =
        ohqEntities.stream()
            .filter(
                ohq ->
                    ohq.getPortRotation()
                        .getId()
                        .equals(synopticalEntity.getLoadableStudyPortRotation().getId()))
            .collect(Collectors.toList());
    synopticalWiseList.forEach(
        ohq -> {
          Loadicator.OtherTankInfo.Builder otherTankBuilder = Loadicator.OtherTankInfo.newBuilder();
          otherTankBuilder.setTankId(ohq.getTankXId());
          Optional<VesselInfo.VesselTankDetail> tankDetail =
              vesselReply.getVesselTanksList().stream()
                  .filter(tank -> Long.valueOf(tank.getTankId()).equals(ohq.getTankXId()))
                  .findAny();
          if (tankDetail.isPresent()) {
            Optional.ofNullable(tankDetail.get().getTankName())
                .ifPresent(otherTankBuilder::setTankName);
            Optional.ofNullable(tankDetail.get().getShortName())
                .ifPresent(otherTankBuilder::setShortName);
          }
          if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(synopticalEntity.getOperationType())) {
            Optional.ofNullable(ohq.getArrivalQuantity())
                .ifPresent(qty -> otherTankBuilder.setQuantity(valueOf(qty)));
          } else {
            Optional.ofNullable(ohq.getDepartureQuantity())
                .ifPresent(qty -> otherTankBuilder.setQuantity(valueOf(qty)));
          }
          stowagePlanBuilder.addOtherTankInfo(otherTankBuilder.build());
        });
  }

  /**
   * Build ballast details
   *
   * @param stowagePlanBuilder
   * @param vesselReply
   * @param synopticalEntity
   * @param loadablePatternBallastList
   */
  private void buildLoadicatorBallastDetails(
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      VesselInfo.VesselReply vesselReply,
      SynopticalTable synopticalEntity,
      List<LoadablePlanStowageBallastDetails> loadablePatternBallastList) {
    List<LoadablePlanStowageBallastDetails> synopticalWiseList =
        loadablePatternBallastList.stream()
            .filter(
                ballast ->
                    synopticalEntity
                            .getLoadableStudyPortRotation()
                            .getId()
                            .equals(ballast.getPortRotationId())
                        && synopticalEntity.getOperationType().equals(ballast.getOperationType()))
            .collect(Collectors.toList());
    synopticalWiseList.forEach(
        patternBallast -> {
          if (stowagePlanBuilder.getStowageId() == patternBallast.getLoadablePatternId()) {
            this.buildLoadicatorBallastDetails(patternBallast, stowagePlanBuilder, vesselReply);
          }
        });
  }

  /**
   * Build ballast details
   *
   * @param ballast
   * @param stowagePlanBuilder
   * @param vesselReply
   */
  private void buildLoadicatorBallastDetails(
      LoadablePlanStowageBallastDetails ballast,
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      VesselInfo.VesselReply vesselReply) {

    Loadicator.BallastInfo.Builder ballastBuilder = Loadicator.BallastInfo.newBuilder();
    Optional.ofNullable(String.valueOf(ballast.getQuantity()))
        .ifPresent(ballastBuilder::setQuantity);
    Optional.ofNullable(ballast.getLoadablePatternId()).ifPresent(ballastBuilder::setStowageId);
    Optional.ofNullable(ballast.getTankXId()).ifPresent(ballastBuilder::setTankId);
    Optional.ofNullable(ballast.getPortXId()).ifPresent(ballastBuilder::setPortId);
    Optional<VesselInfo.VesselTankDetail> tankDetail =
        vesselReply.getVesselTanksList().stream()
            .filter(tank -> Long.valueOf(tank.getTankId()).equals(ballast.getTankXId()))
            .findAny();
    if (tankDetail.isPresent()) {
      Optional.ofNullable(tankDetail.get().getTankName()).ifPresent(ballastBuilder::setTankName);
      Optional.ofNullable(tankDetail.get().getShortName()).ifPresent(ballastBuilder::setShortName);
    }
    stowagePlanBuilder.addBallastInfo(ballastBuilder.build());
  }

  /**
   * Build cargo details
   *
   * @param stowagePlanBuilder
   * @param loadablePatternCargoDetails
   * @param synopticalEntity
   * @param cargoReply
   */
  @SuppressWarnings("unchecked")
  private void buildLoadicatorCargoDetails(
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails,
      SynopticalTable synopticalEntity,
      CargoInfo.CargoReply cargoReply,
      List<LoadablePlanComminglePortwiseDetails> loadablePatternCommingleCargoDetails) {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> synopticalWiseList =
        loadablePatternCargoDetails.stream()
            .filter(
                cargo ->
                    synopticalEntity
                            .getLoadableStudyPortRotation()
                            .getId()
                            .equals(cargo.getPortRotationId())
                        && synopticalEntity.getOperationType().equals(cargo.getOperationType()))
            .collect(Collectors.toList());
    synopticalWiseList =
        synopticalWiseList.stream()
            .filter(
                cargo ->
                    synopticalEntity
                        .getLoadableStudyPortRotation()
                        .getId()
                        .equals(cargo.getPortRotationId()))
            .filter(
                distinctByKeys(
                    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails::getAbbreviation))
            .collect(Collectors.toList());
    synopticalWiseList.forEach(
        patternCargo ->
            stowagePlanBuilder.addCargoInfo(
                this.buildLoadicatorCargoDetails(patternCargo, cargoReply)));

    List<LoadablePlanComminglePortwiseDetails> synopticalWiseCommingleList =
        loadablePatternCommingleCargoDetails.stream()
            .filter(
                cargo ->
                    synopticalEntity
                            .getLoadableStudyPortRotation()
                            .getId()
                            .equals(cargo.getPortRotationXid())
                        && synopticalEntity.getOperationType().equals(cargo.getOperationType()))
            .filter(
                distinctByKeys(
                    com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails
                        ::getCargo1Abbreviation,
                    com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails
                        ::getCargo2Abbreviation))
            .collect(Collectors.toList());
    AtomicInteger commingleCount = new AtomicInteger(0);
    synopticalWiseCommingleList.forEach(
        commingleCargo ->
            stowagePlanBuilder.addAllCargoInfo(
                this.buildLoadicatorCargoDetails(commingleCargo, cargoReply, commingleCount)));
  }

  /**
   * Build stowage plan details
   *
   * @param stowagePlanBuilder
   * @param loadablePatternCargoDetails
   * @param vesselReply
   * @param cargoReply
   * @param synopticalEntity
   */
  private void buildLoadicatorStowagePlanDetails(
      Loadicator.StowagePlan.Builder stowagePlanBuilder,
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails,
      VesselInfo.VesselReply vesselReply,
      CargoInfo.CargoReply cargoReply,
      SynopticalTable synopticalEntity,
      List<LoadablePlanComminglePortwiseDetails> loadablePatternCommingleCargoDetails) {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> synopticalWiseList =
        loadablePatternCargoDetails.stream()
            .filter(
                cargo ->
                    synopticalEntity
                            .getLoadableStudyPortRotation()
                            .getId()
                            .equals(cargo.getPortRotationId())
                        && synopticalEntity.getOperationType().equals(cargo.getOperationType()))
            .collect(Collectors.toList());
    synopticalWiseList.forEach(
        patternCargo -> {
          if (stowagePlanBuilder.getStowageId() == patternCargo.getLoadablePatternId()) {
            this.buildLoadicatorStowagePlanDetails(
                patternCargo, stowagePlanBuilder, cargoReply, vesselReply);
          }
        });
    AtomicInteger commingleCount = new AtomicInteger(0);
    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        synopticalWiseCommingleList =
            loadablePatternCommingleCargoDetails.stream()
                .filter(
                    cargo ->
                        synopticalEntity
                                .getLoadableStudyPortRotation()
                                .getId()
                                .equals(cargo.getPortRotationXid())
                            && synopticalEntity.getOperationType().equals(cargo.getOperationType()))
                .collect(Collectors.toList());
    synopticalWiseCommingleList.forEach(
        patternCmomingleCargo -> {
          if (stowagePlanBuilder.getStowageId()
              == patternCmomingleCargo.getLoadablePattern().getId()) {
            this.buildLoadicatorStowagePlanDetailsForCommingleCargo(
                patternCmomingleCargo, stowagePlanBuilder, cargoReply, vesselReply, commingleCount);
          }
        });
  }

  private void buildLoadicatorStowagePlanDetailsForCommingleCargo(
      LoadablePlanComminglePortwiseDetails patternCmomingleCargo,
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      CargoInfo.CargoReply cargoReply,
      VesselInfo.VesselReply vesselReply,
      AtomicInteger commingleCount) {
    Loadicator.StowageDetails.Builder stowageDetailsBuilder =
        Loadicator.StowageDetails.newBuilder();
    Optional.ofNullable(patternCmomingleCargo.getTankId())
        .ifPresent(stowageDetailsBuilder::setTankId);

    BigDecimal cargo1Mt = null;
    BigDecimal cargo2Mt = null;

    if (!isEmpty(patternCmomingleCargo.getCargo1Mt())) {
      cargo1Mt = new BigDecimal(patternCmomingleCargo.getCargo1Mt());
    }

    if (!isEmpty(patternCmomingleCargo.getCargo2Mt())) {
      cargo2Mt = new BigDecimal(patternCmomingleCargo.getCargo2Mt());
    }

    BigDecimal plannedQuantity = cargo1Mt.add(cargo2Mt);

    Optional.ofNullable(plannedQuantity)
        .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));

    Optional.ofNullable(patternCmomingleCargo.getPortId())
        .ifPresent(stowageDetailsBuilder::setPortId);
    Optional.ofNullable(patternCmomingleCargo.getLoadablePattern())
        .ifPresent(item -> stowageDetailsBuilder.setStowageId(item.getId()));
    Optional<VesselInfo.VesselTankDetail> tankDetail =
        vesselReply.getVesselTanksList().stream()
            .filter(
                tank -> Long.valueOf(tank.getTankId()).equals(patternCmomingleCargo.getTankId()))
            .findAny();
    if (tankDetail.isPresent()) {
      Optional.ofNullable(tankDetail.get().getTankName())
          .ifPresent(stowageDetailsBuilder::setTankName);
      Optional.ofNullable(tankDetail.get().getShortName())
          .ifPresent(stowageDetailsBuilder::setShortName);
    }
    //    Optional<CargoDetail> cargoDetail =
    //        cargoReply.getCargosList().stream()
    //            .filter(c -> Long.valueOf(c.getId()).equals(patternCmomingleCargo.getId()))
    //            .findAny();
    //    if (cargoDetail.isPresent()) {
    //      Optional.ofNullable(cargoDetail.get().getCrudeType())
    //          .ifPresent(stowageDetailsBuilder::setCargoName);
    //    }
    stowageDetailsBuilder.setCargoName("COM" + commingleCount.incrementAndGet());
    stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
  }

  /**
   * Build commingle cargo details
   *
   * @param commingleCargo
   * @param cargoReply
   * @return
   */
  private List<Loadicator.CargoInfo> buildLoadicatorCargoDetails(
      LoadablePlanComminglePortwiseDetails commingleCargo,
      CargoInfo.CargoReply cargoReply,
      AtomicInteger commingleCount) {
    List<Loadicator.CargoInfo> list = new ArrayList<>();
    Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
    cargoBuilder.setCargoAbbrev("COM" + commingleCount.incrementAndGet());
    Optional.ofNullable(String.valueOf(commingleCargo.getApi())).ifPresent(cargoBuilder::setApi);
    Optional.ofNullable(String.valueOf(commingleCargo.getTemperature()))
        .ifPresent(cargoBuilder::setStandardTemp);
    Optional.ofNullable(commingleCargo.getPortId()).ifPresent(cargoBuilder::setPortId);
    if (null != commingleCargo.getLoadablePattern()) {
      cargoBuilder.setStowageId(commingleCargo.getLoadablePattern().getId());
    }
    //    list.add(cargoBuilder.build());
    //    cargoBuilder = CargoInfo.newBuilder();
    //    Optional.ofNullable(String.valueOf(commingleCargo.getCargo2Abbreviation()))
    //        .ifPresent(cargoBuilder::setCargoAbbrev);
    //
    // Optional.ofNullable(String.valueOf(commingleCargo.getApi())).ifPresent(cargoBuilder::setApi);
    //    Optional.ofNullable(String.valueOf(commingleCargo.getTemperature()))
    //        .ifPresent(cargoBuilder::setStandardTemp);
    //    Optional.ofNullable(commingleCargo.getPortId()).ifPresent(cargoBuilder::setPortId);
    //    if (null != commingleCargo.getLoadablePattern()) {
    //      cargoBuilder.setStowageId(commingleCargo.getLoadablePattern().getId());
    //    }
    list.add(cargoBuilder.build());
    return list;
  }

  /**
   * Build cargo
   *
   * @param cargo
   * @return
   */
  private Loadicator.CargoInfo buildLoadicatorCargoDetails(
      com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargo,
      CargoInfo.CargoReply cargoReply) {
    Loadicator.CargoInfo.Builder cargoBuilder = Loadicator.CargoInfo.newBuilder();
    Optional.ofNullable(String.valueOf(cargo.getAbbreviation()))
        .ifPresent(cargoBuilder::setCargoAbbrev);
    Optional.ofNullable(String.valueOf(cargo.getApi())).ifPresent(cargoBuilder::setApi);
    Optional.ofNullable(String.valueOf(cargo.getTemperature()))
        .ifPresent(cargoBuilder::setStandardTemp);
    Optional.ofNullable(cargo.getCargoId()).ifPresent(cargoBuilder::setCargoId);
    Optional.ofNullable(cargo.getPortId()).ifPresent(cargoBuilder::setPortId);
    Optional.ofNullable(cargo.getLoadablePatternId()).ifPresent(cargoBuilder::setStowageId);
    return cargoBuilder.build();
  }

  /**
   * Build stowage plan details
   *
   * @param patternCargo
   * @param stowagePlanBuilder
   * @param cargoReply
   * @param vesselReply
   */
  private void buildLoadicatorStowagePlanDetails(
      com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails patternCargo,
      com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder,
      CargoInfo.CargoReply cargoReply,
      VesselInfo.VesselReply vesselReply) {
    Loadicator.StowageDetails.Builder stowageDetailsBuilder =
        Loadicator.StowageDetails.newBuilder();
    Optional.ofNullable(patternCargo.getCargoId()).ifPresent(stowageDetailsBuilder::setCargoId);
    Optional.ofNullable(patternCargo.getTankId()).ifPresent(stowageDetailsBuilder::setTankId);
    Optional.ofNullable(patternCargo.getPlannedQuantity())
        .ifPresent(quantity -> stowageDetailsBuilder.setQuantity(String.valueOf(quantity)));
    Optional.ofNullable(patternCargo.getAbbreviation())
        .ifPresent(stowageDetailsBuilder::setCargoName);
    Optional.ofNullable(patternCargo.getPortId()).ifPresent(stowageDetailsBuilder::setPortId);
    Optional.ofNullable(patternCargo.getLoadablePatternId())
        .ifPresent(stowageDetailsBuilder::setStowageId);
    Optional<VesselInfo.VesselTankDetail> tankDetail =
        vesselReply.getVesselTanksList().stream()
            .filter(tank -> Long.valueOf(tank.getTankId()).equals(patternCargo.getTankId()))
            .findAny();
    if (tankDetail.isPresent()) {
      Optional.ofNullable(tankDetail.get().getTankName())
          .ifPresent(stowageDetailsBuilder::setTankName);
      Optional.ofNullable(tankDetail.get().getShortName())
          .ifPresent(stowageDetailsBuilder::setShortName);
    }
    Optional<CargoInfo.CargoDetail> cargoDetail =
        cargoReply.getCargosList().stream()
            .filter(c -> Long.valueOf(c.getId()).equals(patternCargo.getId()))
            .findAny();
    if (cargoDetail.isPresent()) {
      Optional.ofNullable(cargoDetail.get().getCrudeType())
          .ifPresent(stowageDetailsBuilder::setCargoName);
    }

    stowagePlanBuilder.addStowageDetails(stowageDetailsBuilder.build());
  }

  private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
    final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

    return t -> {
      final List<?> keys =
          Arrays.stream(keyExtractors).map(ke -> ke.apply(t)).collect(Collectors.toList());

      return seen.putIfAbsent(keys, Boolean.TRUE) == null;
    };
  }

  public LoadableStudy.AlgoReply.Builder saveLoadicatorResults(
      LoadableStudy.LoadicatorResultsRequest request,
      LoadableStudy.AlgoReply.Builder replyBuilder) {
    log.info("saveLoadicatorResults - loadable study micro service");
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudy.isPresent()) {
      log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(INVALID_LOADABLE_STUDY_ID)
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
    } else {
      saveLoadicatorResults(request);
      updateloadablestudystatus(request);
      replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    }
    return replyBuilder;
  }

  /** @param request void */
  private void updateloadablestudystatus(LoadableStudy.LoadicatorResultsRequest request) {
    loadableStudyRepository.updateLoadableStudyStatus(
        LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, request.getLoadableStudyId());
    loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
        LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, request.getProcessId(), true);
  }

  /** @param request void */
  private void saveLoadicatorResults(LoadableStudy.LoadicatorResultsRequest request) {
    request
        .getLoadicatorPatternDetailsResultsList()
        .forEach(
            lrr -> {
              lrr.getLodicatorResultDetailsList()
                  .forEach(
                      lrdl -> {
                        SynopticalTableLoadicatorData loadicatorData =
                            new SynopticalTableLoadicatorData();
                        loadicatorData.setActive(true);
                        loadicatorData.setBlindSector(
                            !StringUtils.isEmpty(lrdl.getBlindSector())
                                ? new BigDecimal(lrdl.getBlindSector())
                                : null);
                        loadicatorData.setCalculatedDraftAftPlanned(
                            !StringUtils.isEmpty(lrdl.getCalculatedDraftAftPlanned())
                                ? new BigDecimal(lrdl.getCalculatedDraftAftPlanned())
                                : null);
                        loadicatorData.setCalculatedDraftFwdPlanned(
                            !StringUtils.isEmpty(lrdl.getCalculatedDraftFwdPlanned())
                                ? new BigDecimal(lrdl.getCalculatedDraftFwdPlanned())
                                : null);
                        loadicatorData.setCalculatedDraftMidPlanned(
                            !StringUtils.isEmpty(lrdl.getCalculatedDraftMidPlanned())
                                ? new BigDecimal(lrdl.getCalculatedDraftMidPlanned())
                                : null);
                        loadicatorData.setCalculatedTrimPlanned(
                            !StringUtils.isEmpty(lrdl.getCalculatedTrimPlanned())
                                ? new BigDecimal(lrdl.getCalculatedTrimPlanned())
                                : null);
                        loadicatorData.setDeflection(
                            !StringUtils.isEmpty(lrdl.getDeflection())
                                ? new BigDecimal(lrdl.getDeflection())
                                : null);
                        loadicatorData.setList(
                            !StringUtils.isEmpty(lrdl.getList())
                                ? new BigDecimal(lrdl.getList())
                                : null);
                        loadicatorData.setPortId(lrdl.getPortId());
                        loadicatorData.setOperationId(lrdl.getOperationId());
                        loadicatorData.setLoadablePatternId(lrr.getLoadablePatternId());
                        synopticalTableLoadicatorDataRepository.save(loadicatorData);
                      });
            });
  }
}
