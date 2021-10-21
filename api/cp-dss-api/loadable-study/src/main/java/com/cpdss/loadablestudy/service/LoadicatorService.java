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
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

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

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  @Autowired private LoadablePatternService loadablePatternService;

  @Autowired private RestTemplate restTemplate;

  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;

  @Autowired private LoadableStudyService loadableStudyService;
  @Autowired private JsonDataService jsonDataService;
  @Autowired private CommunicationService communicationService;
  @Autowired private JsonDataRepository jsonDataRepository;
  @Autowired private JsonTypeRepository jsonTypeRepository;

  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @Value("${algo.loadicator.api.url}")
  private String loadicatorUrl;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Value("${cpdss.build.env}")
  private String env;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("loadicatorService")
  private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorService;

  public void saveLodicatorDataForSynoptical(
      com.cpdss.loadablestudy.entity.LoadablePattern loadablePattern,
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
      loadicatorRequestBuilder.setTypeId(
          LoadableStudiesConstants.LOADABLE_STUDY_LOADICATOR_TYPE_ID);
      List<LoadablePattern> loadablePatterns = null;
      if (patternId == 0) {
        loadablePatterns = loadablePatternsList;
        loadicatorRequestBuilder.setIsPattern(false);
      } else {
        Optional<LoadablePattern> lpOpt =
            this.loadablePatternRepository.findByIdAndIsActive(patternId, true);
        loadablePatterns =
            lpOpt.isPresent() ? new ArrayList<LoadablePattern>(Arrays.asList(lpOpt.get())) : null;
        loadicatorRequestBuilder.setIsPattern(true);
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
  public void saveLoadicatorResults(LoadableStudy.LoadicatorResultsRequest request) {
    request
        .getLoadicatorResultsPatternWiseList()
        .forEach(
            lrr -> {
              lrr.getLoadicatorResultDetailsList()
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
                        loadicatorData.setShearingForce(
                            !StringUtils.isEmpty(lrdl.getSF())
                                ? new BigDecimal(lrdl.getSF())
                                : null);
                        loadicatorData.setBendingMoment(
                            !StringUtils.isEmpty(lrdl.getBM())
                                ? new BigDecimal(lrdl.getBM())
                                : null);
                        loadicatorData.setPortId(lrdl.getPortId());
                        loadicatorData.setOperationId(lrdl.getOperationId());
                        loadicatorData.setLoadablePatternId(lrr.getLoadablePatternId());

                        // Set synoptical table id
                        SynopticalTable synopticalTable =
                            synopticalTableRepository
                                .findByIdAndIsActive(lrdl.getSynopticalId(), true)
                                .orElse(new SynopticalTable());
                        loadicatorData.setSynopticalTable(synopticalTable);
                        synopticalTableLoadicatorDataRepository.save(loadicatorData);
                      });
            });
  }

  public LoadableStudy.LoadicatorDataReply.Builder getLoadicatorData(
      LoadableStudy.LoadicatorDataRequest request,
      LoadableStudy.LoadicatorDataReply.Builder replyBuilder)
      throws Exception {
    LoadicatorAlgoRequest loadicator = new LoadicatorAlgoRequest();
    this.buildLoadicatorUrlRequest(request, loadicator);
    ObjectMapper objectMapper = new ObjectMapper();
    this.saveLoadicatorAlgoRequest(request, loadicator, objectMapper);
    LoadicatorAlgoResponse algoResponse =
        restTemplate.postForObject(loadicatorUrl, loadicator, LoadicatorAlgoResponse.class);
    this.saveLoadicatorAlgoResponse(request, algoResponse, objectMapper);
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    Optional<LoadableStudyCommunicationStatus> loadableStudyCommunicationStatus =
        this.loadableStudyCommunicationStatusRepository.findByReferenceIdAndMessageType(
            request.getLoadableStudyId(), MessageTypes.LOADABLESTUDY.getMessageType());
    if (algoResponse.getFeedbackLoop() != null) {
      if (!request.getIsPattern()) {
        if (algoResponse.getFeedbackLoop()) {
          log.info(
              "I2R Algorithm has started feedback loop for loadable study "
                  + request.getLoadableStudyId());
          this.updateFeedbackLoopParameters(
              request.getLoadableStudyId(),
              false,
              true,
              algoResponse.getFeedbackLoopCount(),
              LOADABLE_STUDY_STATUS_FEEDBACK_LOOP_STARTED);
          if (loadableStudyOpt.isPresent()) {
            log.info("Deleting existing patterns");
            this.loadablePatternRepository
                .findByLoadableStudyAndIsActive(loadableStudyOpt.get(), true)
                .forEach(
                    loadablePattern -> {
                      log.info("Deleting loadable pattern " + loadablePattern.getId());
                      this.loadablePatternRepository.deleteLoadablePattern(loadablePattern.getId());
                      loadablePatternService.deleteExistingPlanDetails(loadablePattern);
                    });
          } else {
            log.error("Loadable Study not found in database");
            throw new Exception("Loadable Study not found in database");
          }
        } else {
          log.info("Feedback Loop ended for loadable study " + request.getLoadableStudyId());
          this.updateFeedbackLoopParameters(
              request.getLoadableStudyId(),
              false,
              false,
              algoResponse.getFeedbackLoopCount(),
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
          this.saveloadicatorDataForSynopticalTable(algoResponse, request.getIsPattern());
          loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, algoResponse.getProcessId(), true);
          if (enableCommunication && !env.equals("ship")) {
            passResultToCommunication(
                objectMapper, algoResponse, loadableStudyOpt, loadableStudyCommunicationStatus);
          }
        }
      } else {
        if (algoResponse.getFeedbackLoop()) {
          log.info(
              "I2R Algorithm has started feedback loop for loadable pattern "
                  + request.getLoadicatorPatternDetails(0).getLoadablePatternId());
          this.updateFeedbackLoopParameters(
              request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
              true,
              true,
              algoResponse.getFeedbackLoopCount(),
              LOADABLE_PATTERN_VALIDATION_FEEDBACK_LOOP_STARTED);
        } else {
          log.info(
              "Feedback loop ended for loadable pattern "
                  + request.getLoadicatorPatternDetails(0).getLoadablePatternId());
          this.updateFeedbackLoopParameters(
              request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
              true,
              false,
              algoResponse.getFeedbackLoopCount(),
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
          List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataList =
              this.saveloadicatorDataForSynopticalTable(algoResponse, request.getIsPattern());
          loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
              LOADABLE_PATTERN_VALIDATION_SUCCESS_ID, algoResponse.getProcessId(), true);
          log.info("LoadablePattern algo status process id: " + algoResponse.getProcessId());
          if (enableCommunication && !env.equals("ship")) {
            passPatternWithLodicatorToEnvoyWriter(
                request,
                request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
                loadableStudyOpt,
                synopticalTableLoadicatorDataList,
                algoResponse.getProcessId(),
                algoResponse.getFeedbackLoopCount());
          }
        }
      }
    } else {
      this.saveloadicatorDataForSynopticalTable(algoResponse, request.getIsPattern());
      loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
          LOADABLE_PATTERN_VALIDATION_SUCCESS_ID, algoResponse.getProcessId(), true);

      if (enableCommunication && !env.equals("ship")) {
        passResultToCommunication(
            objectMapper, algoResponse, loadableStudyOpt, loadableStudyCommunicationStatus);
      }
    }
    replyBuilder =
        LoadableStudy.LoadicatorDataReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * To prepare payload and send payload to ship side
   *
   * @param request
   * @param loadablePatternId
   * @param loadableStudyOpt
   * @param synopticalTableLoadicatorDataList
   * @param processId
   * @param feedbackLoopCount
   */
  private void passPatternWithLodicatorToEnvoyWriter(
      LoadableStudy.LoadicatorDataRequest request,
      long loadablePatternId,
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt,
      List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataList,
      String processId,
      Integer feedbackLoopCount)
      throws JsonProcessingException, GenericServiceException {
    ObjectMapper objectMapper = new ObjectMapper();
    Optional<JsonType> type =
        this.jsonTypeRepository.findByIdAndIsActive(
            LoadableStudiesConstants.LOADABLE_PATTERN_VALIDATE_RESULT_JSON_ID, true);
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(loadablePatternId, true);
    if (type.isPresent() && loadableStudyOpt.isPresent() && loadablePatternOpt.isPresent()) {
      JsonData jsonData =
          jsonDataRepository.findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc(
              loadablePatternId, type.get());
      if (jsonData != null && jsonData.getJsonData() != null) {
        Optional<LoadableStudyCommunicationStatus> patternValidateCommunicationStatus =
            this.loadableStudyCommunicationStatusRepository.findByReferenceIdAndMessageType(
                loadablePatternId, MessageTypes.VALIDATEPLAN.getMessageType());
        PatternValidateResultRequest patternValidateResultRequest =
            new Gson().fromJson(jsonData.getJsonData(), PatternValidateResultRequest.class);
        LoadablePatternAlgoRequest loadablePatternAlgoRequest = new LoadablePatternAlgoRequest();
        Optional.ofNullable(patternValidateResultRequest.getValidated())
            .ifPresent(loadablePatternAlgoRequest::setValidated);
        Optional.ofNullable(loadableStudyOpt.get().getId())
            .ifPresent(loadablePatternAlgoRequest::setLoadableStudyId);
        Optional.ofNullable(patternValidateResultRequest.getHasLoadicator())
            .ifPresent(loadablePatternAlgoRequest::setHasLoadicator);
        Optional.ofNullable(processId).ifPresent(loadablePatternAlgoRequest::setProcessId);
        Optional.ofNullable(loadablePatternId)
            .ifPresent(loadablePatternAlgoRequest::setLoadablePatternId);
        loadablePatternAlgoRequest.setMessageId(
            patternValidateCommunicationStatus.get().getMessageUUID());
        PatternDetails patternDetails = new PatternDetails();
        loadablePatternService.fetchSavedPatternFromDB(patternDetails, loadablePatternOpt.get());
        loadablePatternAlgoRequest.setPatternDetails(patternDetails);
        if (synopticalTableLoadicatorDataList != null) {
          ModelMapper modelMapper = new ModelMapper();
          List<SynopticalTableLoadicatorDataDto> synopticalTableLoadicatorDataDtoList =
              Arrays.asList(
                  modelMapper.map(
                      synopticalTableLoadicatorDataList, SynopticalTableLoadicatorDataDto[].class));
          patternDetails.setSynopticalTableLoadicatorData(synopticalTableLoadicatorDataDtoList);
        }
        EnvoyWriter.WriterReply ewReply =
            communicationService.passRequestPayloadToEnvoyWriter(
                objectMapper.writeValueAsString(loadablePatternAlgoRequest),
                loadablePatternOpt.get().getLoadableStudy().getVesselXId(),
                MessageTypes.PATTERNDETAIL.getMessageType());
        if (SUCCESS.equals(ewReply.getResponseStatus().getStatus())) {
          log.info(
              "------- Envoy writer has called successfully in Lodicator(Stowage Edit ): "
                  + ewReply);
          LoadableStudyCommunicationStatus lsCommunicationStatus =
              new LoadableStudyCommunicationStatus();
          if (ewReply.getMessageId() != null) {
            lsCommunicationStatus.setMessageUUID(ewReply.getMessageId());
            lsCommunicationStatus.setCommunicationStatus(
                CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId());
          }
          lsCommunicationStatus.setReferenceId(loadablePatternOpt.get().getId());
          lsCommunicationStatus.setMessageType(MessageTypes.PATTERNDETAIL.getMessageType());
          lsCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
          LoadableStudyCommunicationStatus loadableStudyCommunicationStatus =
              this.loadableStudyCommunicationStatusRepository.save(lsCommunicationStatus);
          log.info(
              "Communication table update in Lodicator(true) for stowage Edit : "
                  + loadableStudyCommunicationStatus.getId());
        }
      }
    }
  }

  private void passResultToCommunication(
      ObjectMapper objectMapper,
      LoadicatorAlgoResponse algoResponse,
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt,
      Optional<LoadableStudyCommunicationStatus> loadableStudyCommunicationStatus)
      throws InvalidProtocolBufferException, JsonProcessingException, GenericServiceException {
    if (loadableStudyCommunicationStatus.get().getMessageUUID() != null) {
      LoadableStudy.AlgoResponseCommunication.Builder algoRespComm =
          LoadableStudy.AlgoResponseCommunication.newBuilder();
      algoRespComm.setMessageId(loadableStudyCommunicationStatus.get().getMessageUUID());
      LoadableStudy.LoadicatorResultsRequest.Builder loadicatorResultsRequest =
          LoadableStudy.LoadicatorResultsRequest.newBuilder();
      JsonFormat.parser()
          .ignoringUnknownFields()
          .merge(objectMapper.writeValueAsString(algoResponse), loadicatorResultsRequest);
      algoRespComm.setLoadicatorResultsRequest(loadicatorResultsRequest.build());
      JsonData patternJson =
          this.jsonDataService.getJsonData(
              loadableStudyOpt.get().getId(),
              LoadableStudiesConstants.LOADABLE_STUDY_RESULT_JSON_ID);
      if (patternJson != null) {
        LoadableStudy.LoadablePatternAlgoRequest.Builder loadablePatternAlgoRequest =
            LoadableStudy.LoadablePatternAlgoRequest.newBuilder();
        JsonFormat.parser()
            .ignoringUnknownFields()
            .merge(patternJson.getJsonData(), loadablePatternAlgoRequest);
        loadablePatternAlgoRequest.setHasLodicator(true);
        algoRespComm.setLoadablePatternAlgoRequest(loadablePatternAlgoRequest.build());
      }
      communicationService.passResultPayloadToEnvoyWriter(algoRespComm, loadableStudyOpt.get());
    }
  }

  /**
   * @param request
   * @param loadicator void
   * @throws GenericServiceException
   */
  private void buildLoadicatorUrlRequest(
      LoadableStudy.LoadicatorDataRequest request, LoadicatorAlgoRequest loadicator)
      throws GenericServiceException {
    loadicator.setProcessId(request.getProcessId());
    loadicator.setLoadicatorPatternDetails(new ArrayList<>());

    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (loadableStudyOpt.isPresent()) {
      ModelMapper modelMapper = new ModelMapper();
      loadableStudyService.buildLoadableStudy(
          request.getLoadableStudyId(), loadableStudyOpt.get(), loadableStudy, modelMapper);
    }

    if (request.getIsPattern()) {
      com.cpdss.common.generated.LoadableStudy.LoadicatorPatternDetails patternDetails =
          request.getLoadicatorPatternDetails(0);
      LoadicatorPatternDetails pattern = new LoadicatorPatternDetails();
      pattern.setLoadablePatternId(patternDetails.getLoadablePatternId());
      pattern.setLdTrim(this.createLdTrim(patternDetails.getLDtrimList()));
      pattern.setLdStrength(this.createLdStrength(patternDetails.getLDStrengthList()));
      pattern.setLdIntactStability(
          this.createLdIntactStability(patternDetails.getLDIntactStabilityList()));
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(
              patternDetails.getLoadablePatternId(), true);
      if (loadablePatternOpt.isPresent()) {
        loadableStudy.setFeedbackLoop(
            Optional.ofNullable(loadablePatternOpt.get().getFeedbackLoop()).isPresent()
                ? loadablePatternOpt.get().getFeedbackLoop()
                : false);
        loadableStudy.setFeedbackLoopCount(
            Optional.ofNullable(loadablePatternOpt.get().getFeedbackLoopCount()).isPresent()
                ? loadablePatternOpt.get().getFeedbackLoopCount()
                : 0);
      }
      loadicator.setLoadicatorPatternDetail(pattern);
    } else {
      request
          .getLoadicatorPatternDetailsList()
          .forEach(
              patternDetails -> {
                LoadicatorPatternDetails patterns = new LoadicatorPatternDetails();
                patterns.setLoadablePatternId(patternDetails.getLoadablePatternId());
                patterns.setLdTrim(this.createLdTrim(patternDetails.getLDtrimList()));
                patterns.setLdStrength(this.createLdStrength(patternDetails.getLDStrengthList()));
                patterns.setLdIntactStability(
                    this.createLdIntactStability(patternDetails.getLDIntactStabilityList()));
                loadicator.getLoadicatorPatternDetails().add(patterns);
              });
    }

    loadicator.setLoadableStudy(loadableStudy);
  }

  /**
   * Save Loadicator ALGO request
   *
   * @param request
   * @param loadicator
   * @param objectMapper
   */
  private void saveLoadicatorAlgoRequest(
      LoadableStudy.LoadicatorDataRequest request,
      LoadicatorAlgoRequest loadicator,
      ObjectMapper objectMapper) {
    log.info("Saving Loadicator ALGO request to database");
    try {
      if (request.getIsPattern()) {
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadicator_pattern_"
                    + request.getLoadicatorPatternDetails(0).getLoadablePatternId()
                    + ".json"),
            loadicator);
        jsonDataService.saveJsonToDatabase(
            request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
            LOADABLE_PATTERN_EDIT_LOADICATOR_REQUEST,
            objectMapper.writeValueAsString(loadicator));
      } else {
        objectMapper.writeValue(
            new File(
                this.rootFolder + "/json/loadicator_" + request.getLoadableStudyId() + ".json"),
            loadicator);
        jsonDataService.saveJsonToDatabase(
            request.getLoadableStudyId(),
            LOADABLE_STUDY_LOADICATOR_REQUEST,
            objectMapper.writeValueAsString(loadicator));
      }
    } catch (Exception e) {
      log.error("Encountered error while saving loadicator request json");
    }
  }

  /**
   * Save Loadicator ALGO reponse
   *
   * @param request
   * @param response
   * @param objectMapper
   */
  private void saveLoadicatorAlgoResponse(
      LoadableStudy.LoadicatorDataRequest request,
      LoadicatorAlgoResponse response,
      ObjectMapper objectMapper) {
    log.info("Saving Loadicator ALGO response to database");
    try {
      if (request.getIsPattern()) {
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadicator_algo_response_pattern_"
                    + request.getLoadicatorPatternDetails(0).getLoadablePatternId()
                    + ".json"),
            response);
        jsonDataService.saveJsonToDatabase(
            request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
            LOADABLE_PATTERN_EDIT_LOADICATOR_RESPONSE,
            objectMapper.writeValueAsString(response));
      } else {
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadicator_algo_response_"
                    + request.getLoadableStudyId()
                    + ".json"),
            response);
        jsonDataService.saveJsonToDatabase(
            request.getLoadableStudyId(),
            LOADABLE_STUDY_LOADICATOR_RESPONSE,
            objectMapper.writeValueAsString(response));
      }
    } catch (Exception e) {
      log.error("Encountered error while saving loadicator response json");
    }
  }

  /**
   * Update Feedback Loop parameters for loadable study/ loadable pattern
   *
   * @param id
   * @param isPattern
   * @param feedbackLoop
   * @param feedbackLoopCount
   * @param status
   */
  public void updateFeedbackLoopParameters(
      Long id, Boolean isPattern, Boolean feedbackLoop, Integer feedbackLoopCount, Long status) {
    if (isPattern) {
      this.loadablePatternRepository.updateLoadablePatternStatus(status, id);
      this.loadablePatternRepository.updateLoadablePatternFeedbackLoopAndFeedbackLoopCount(
          feedbackLoop, feedbackLoopCount, id);
    } else {
      this.loadableStudyRepository.updateLoadableStudyStatus(status, id);
      this.loadableStudyRepository.updateLoadableStudyFeedbackLoopAndFeedbackLoopCount(
          feedbackLoop, feedbackLoopCount, id);
    }
  }

  /**
   * Save data for synoptical table
   *
   * @param algoResponse
   */
  private List<SynopticalTableLoadicatorData> saveloadicatorDataForSynopticalTable(
      LoadicatorAlgoResponse algoResponse, Boolean isPattern) {
    List<SynopticalTableLoadicatorData> entities = new ArrayList<>();
    if (isPattern) {
      algoResponse
          .getLoadicatorResults()
          .getLoadicatorResultDetails()
          .forEach(
              result -> {
                this.synopticalTableLoadicatorDataRepository
                    .deleteBySynopticalTableAndLoadablePatternId(
                        this.synopticalTableRepository.getOne(result.getSynopticalId()),
                        algoResponse.getLoadicatorResults().getLoadablePatternId());
                entities.add(
                    this.createSynopticalTableLoadicatorDataEntity(
                        algoResponse.getLoadicatorResults(), result));
              });
    } else {
      for (LoadicatorPatternDetailsResults patternDetails :
          algoResponse.getLoadicatorResultsPatternWise()) {
        patternDetails
            .getLoadicatorResultDetails()
            .forEach(
                result -> {
                  entities.add(
                      this.createSynopticalTableLoadicatorDataEntity(patternDetails, result));
                  LoadablePattern loadablePattern =
                      loadablePatternRepository.getOne(patternDetails.getLoadablePatternId());
                  loadablePattern.setIsActive(true);
                  loadablePatternRepository.save(loadablePattern);
                });
      }
    }
    List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataList =
        synopticalTableLoadicatorDataRepository.saveAll(entities);
    return synopticalTableLoadicatorDataList;
  }

  /**
   * @param list
   * @return LDTrim
   */
  private List<LDTrim> createLdTrim(List<LoadableStudy.LDtrim> list) {
    List<LDTrim> ldTrims = new ArrayList<LDTrim>();
    list.forEach(
        ldTrim -> {
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
          trim.setMinimumForeDraftInRoughWeatherValue(
              ldTrim.getMinimumForeDraftInRoughWeatherValue());
          trim.setTrimValue(ldTrim.getTrimValue());
          trim.setPortId(ldTrim.getPortId());
          trim.setSynopticalId(ldTrim.getSynopticalId());
          trim.setDeflection(ldTrim.getDeflection());
          ldTrims.add(trim);
        });

    return ldTrims;
  }

  /**
   * @param list
   * @return LDIntactStability
   */
  private List<LDIntactStability> createLdIntactStability(
      List<com.cpdss.common.generated.LoadableStudy.LDIntactStability> list) {
    List<LDIntactStability> ldIntactStabilities = new ArrayList<LDIntactStability>();
    list.forEach(
        lDIntactStability -> {
          LDIntactStability intactStability = new LDIntactStability();
          intactStability.setAngleatmaxrleverJudgement(
              lDIntactStability.getAngleatmaxrleverJudgement());
          intactStability.setAngleatmaxrleverValue(lDIntactStability.getAngleatmaxrleverValue());
          intactStability.setAreaofStability030Judgement(
              lDIntactStability.getAreaofStability030Judgement());
          intactStability.setAreaofStability030Value(
              lDIntactStability.getAreaofStability030Value());
          intactStability.setAreaofStability040Judgement(
              lDIntactStability.getAreaofStability040Judgement());
          intactStability.setAreaofStability040Value(
              lDIntactStability.getAreaofStability040Value());
          intactStability.setAreaofStability3040Judgement(
              lDIntactStability.getAreaofStability3040Judgement());
          intactStability.setAreaofStability3040Value(
              lDIntactStability.getAreaofStability3040Value());
          intactStability.setBigIntialGomJudgement(lDIntactStability.getBigIntialGomJudgement());
          intactStability.setBigintialGomValue(lDIntactStability.getBigintialGomValue());
          intactStability.setErrorDetails(lDIntactStability.getErrorDetails());
          intactStability.setErrorStatus(lDIntactStability.getErrorStatus());
          intactStability.setGmAllowableCurveCheckJudgement(
              lDIntactStability.getGmAllowableCurveCheckJudgement());
          intactStability.setGmAllowableCurveCheckValue(
              lDIntactStability.getGmAllowableCurveCheckValue());
          intactStability.setHeelBySteadyWindJudgement(
              lDIntactStability.getHeelBySteadyWindJudgement());
          intactStability.setHeelBySteadyWindValue(lDIntactStability.getHeelBySteadyWindValue());
          intactStability.setId(lDIntactStability.getId());
          intactStability.setMaximumRightingLeverJudgement(
              lDIntactStability.getMaximumRightingLeverJudgement());
          intactStability.setMaximumRightingLeverValue(
              lDIntactStability.getMaximumRightingLeverValue());
          intactStability.setMessageText(lDIntactStability.getMessageText());
          intactStability.setStabilityAreaBaJudgement(
              lDIntactStability.getStabilityAreaBaJudgement());
          intactStability.setStabilityAreaBaValue(lDIntactStability.getStabilityAreaBaValue());
          intactStability.setPortId(lDIntactStability.getPortId());
          intactStability.setSynopticalId(lDIntactStability.getSynopticalId());
          ldIntactStabilities.add(intactStability);
        });

    return ldIntactStabilities;
  }

  /**
   * @param list
   * @return LDStrength
   */
  private List<LDStrength> createLdStrength(
      List<com.cpdss.common.generated.LoadableStudy.LDStrength> list) {
    List<LDStrength> ldStrengths = new ArrayList<LDStrength>();
    list.forEach(
        ldStrength -> {
          LDStrength strength = new LDStrength();
          strength.setBendingMomentPersentFrameNumber(
              ldStrength.getBendingMomentPersentFrameNumber());
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
          strength.setPortId(ldStrength.getPortId());
          strength.setSynopticalId(ldStrength.getSynopticalId());
          ldStrengths.add(strength);
        });

    return ldStrengths;
  }

  /**
   * Create SynopticalTableLoadicatorData entities
   *
   * @param patternDetails
   * @param result
   * @return SynopticalTableLoadicatorData
   */
  private SynopticalTableLoadicatorData createSynopticalTableLoadicatorDataEntity(
      LoadicatorPatternDetailsResults patternDetails, LoadicatorResultDetails result) {
    SynopticalTableLoadicatorData entity = new SynopticalTableLoadicatorData();
    entity.setLoadablePatternId(patternDetails.getLoadablePatternId());
    entity.setSynopticalTable(this.synopticalTableRepository.getOne(result.getSynopticalId()));
    entity.setActive(true);
    entity.setBlindSector(
        isEmpty(result.getDeflection()) ? null : new BigDecimal(result.getDeflection()));
    entity.setCalculatedDraftAftPlanned(
        isEmpty(result.getCalculatedDraftAftPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftAftPlanned()));
    entity.setCalculatedDraftFwdPlanned(
        isEmpty(result.getCalculatedDraftFwdPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftFwdPlanned()));
    entity.setCalculatedDraftMidPlanned(
        isEmpty(result.getCalculatedDraftMidPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftMidPlanned()));
    entity.setCalculatedTrimPlanned(
        isEmpty(result.getCalculatedTrimPlanned())
            ? null
            : new BigDecimal(result.getCalculatedTrimPlanned()));
    entity.setList(isEmpty(result.getList()) ? null : new BigDecimal(result.getList()));
    entity.setBendingMoment(isEmpty(result.getBm()) ? null : new BigDecimal(result.getBm()));
    entity.setShearingForce(isEmpty(result.getSf()) ? null : new BigDecimal(result.getSf()));
    entity.setDeflection(
        isEmpty(result.getDeflection()) ? null : new BigDecimal(result.getDeflection()));
    return entity;
  }
}
