/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.OperationsTable;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.domain.PortOperationTable;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Master Service For Synoptic Related Operations
 *
 * @author Johnsooraj.X
 * @since 27-05-2021
 */
@Slf4j
@Service
public class SynopticService {

  @Autowired SynopticalTableRepository synopticalTableRepository;

  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;

  @Autowired CargoNominationRepository cargoNominationRepository;

  @Autowired LoadableStudyRepository loadableStudyRepository;

  @Autowired CargoOperationRepository cargoOperationRepository;

  @Autowired LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired OnHandQuantityService onHandQuantityService;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @Autowired private VoyageService voyageService;

  @Autowired private EntityManager entityManager;

  @Autowired private VoyageRepository voyageRepository;

  @Autowired private VoyageStatusRepository voyageStatusRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  public void fetchLoadingInformationSynopticDetails(
      LoadableStudy.LoadingPlanIdRequest request,
      LoadableStudy.LoadingPlanCommonResponse.Builder builder,
      Common.ResponseStatus.Builder repBuilder)
      throws GenericServiceException {
    Long id = request.getId();
    switch (request.getIdType()) {
      case "PORT_ROTATION":
        this.buildPortRotationResponse(id, builder, repBuilder);
        break;
      default:
        log.info("Synoptic Data for Loading Plan Default Case");
        break;
    }
    // Not passing operation type and portId when calling for getting ballast details
    if (!StringUtils.isEmpty(request.getOperationType())) {
      // Cargo details based on port, and operation type
      this.buildCargoToBeLoadedForPort(request, builder, repBuilder);
    }
    // Ballast details based on port rotation
    this.buildBallastDetailsBasedOnPort(request, builder, repBuilder);
  }

  /**
   * Populates ballast Details based on Loadable Pattern Id and Port Rotation
   *
   * @param request
   * @param builder
   * @param repBuilder
   */
  private void buildBallastDetailsBasedOnPort(
      LoadingPlanIdRequest request,
      Builder builder,
      com.cpdss.common.generated.Common.ResponseStatus.Builder repBuilder) {

    List<LoadablePlanStowageBallastDetails> ballastDetails =
        this.loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndIsActive(
                request.getPatternId(), request.getId(), true);
    if (!ballastDetails.isEmpty()) {
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    }
    ballastDetails.forEach(
        ballast -> {
          LoadablePlanBallastDetails.Builder ballastBuilder =
              LoadablePlanBallastDetails.newBuilder();
          Optional.ofNullable(ballast.getColorCode()).ifPresent(ballastBuilder::setColorCode);
          Optional.ofNullable(ballast.getCorrectionFactor())
              .ifPresent(ballastBuilder::setCorrectionFactor);
          Optional.ofNullable(ballast.getFillingPercentage())
              .ifPresent(ballastBuilder::setPercentage);
          Optional.ofNullable(ballast.getTankXId()).ifPresent(ballastBuilder::setTankId);
          Optional.ofNullable(ballast.getOperationType()).ifPresent(ballast::setOperationType);
          builder.addLoadablePlanBallastDetails(ballastBuilder.build());
        });
  }

  // Single Entry with the Operation Type - ARR
  private void buildPortRotationResponse(
      Long id,
      LoadableStudy.LoadingPlanCommonResponse.Builder builder,
      Common.ResponseStatus.Builder repBuilder)
      throws GenericServiceException {
    List<SynopticalTable> list = synopticalTableRepository.findAllByPortRotationId(id);
    if (list.isEmpty()) {
      log.error(
          "Synoptic Data Not Found for Port Rotation Id {}, Operation Type {}",
          id,
          OPERATION_TYPE_ARR);
      throw new GenericServiceException(
          "Synoptic Data Not Found for Port Rotation Id " + id + ", Type ARR",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info(
        "Synoptic Record Found for Port Rotation Id {}, Ids {}",
        id,
        list.stream().map(SynopticalTable::getId).collect(Collectors.toList()));
    try {
      for (SynopticalTable data : list) {
        LoadableStudy.LoadingSynopticResponse.Builder builder1 =
            LoadableStudy.LoadingSynopticResponse.newBuilder();
        Optional.ofNullable(data.getId()).ifPresent(builder1::setSynopticTableId);
        Optional.ofNullable(data.getLoadableStudyXId()).ifPresent(builder1::setLoadableStudyId);
        if (data.getLoadableStudyPortRotation() != null) {
          Optional.ofNullable(data.getLoadableStudyPortRotation().getId())
              .ifPresent(builder1::setPortRotationId);
          Optional.ofNullable(data.getPortXid()).ifPresent(builder1::setPortId);
        }
        Optional.ofNullable(data.getOperationType()).ifPresent(builder1::setOperationType);
        Optional.ofNullable(data.getTimeOfSunrise())
            .ifPresent(v -> builder1.setTimeOfSunrise(TIME_FORMATTER.format(v)));
        Optional.ofNullable(data.getTimeOfSunSet())
            .ifPresent(v -> builder1.setTimeOfSunset(TIME_FORMATTER.format(v)));
        builder.addSynopticData(builder1.build());
        builder.setResponseStatus(repBuilder.setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      builder.setResponseStatus(repBuilder.setStatus(FAILED).setMessage(e.getMessage()).build());
      e.printStackTrace();
      log.info("Failed to build synoptic data, {}", e.getMessage());
      throw new GenericServiceException(
          "Synoptic Data Builder Exception",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private void buildCargoToBeLoadedForPort(
      LoadableStudy.LoadingPlanIdRequest request,
      LoadableStudy.LoadingPlanCommonResponse.Builder builder,
      Common.ResponseStatus.Builder repBuilder) {

    List<LoadablePlanQuantity> list =
        this.loadablePlanQuantityRepository.PORT_WISE_CARGO_DETAILS(
            request.getPatternId(),
            request.getOperationType(),
            request.getPortRotationId(),
            request.getPortId());

    if (!list.isEmpty()) {
      for (LoadablePlanQuantity var1 : list) {
        LoadableStudy.LoadableQuantityCargoDetails.Builder builder1 =
            LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
        Optional.ofNullable(var1.getId()).ifPresent(builder1::setId);
        Optional.ofNullable(var1.getGrade()).ifPresent(builder1::setGrade);
        Optional.ofNullable(var1.getEstimatedApi())
            .ifPresent(v -> builder1.setEstimatedAPI(v.toString()));
        Optional.ofNullable(var1.getEstimatedTemperature())
            .ifPresent(v -> builder1.setEstimatedTemp(v.toString()));
        Optional.ofNullable(var1.getCargoNominationTemperature())
            .ifPresent(v -> builder1.setCargoNominationTemperature(v.toString()));

        Optional.ofNullable(var1.getOrderBblsDbs()).ifPresent(builder1::setOrderBblsdbs);
        Optional.ofNullable(var1.getOrderBbls60f()).ifPresent(builder1::setOrderBbls60F);
        Optional.ofNullable(var1.getMinTolerence()).ifPresent(builder1::setMinTolerence);
        Optional.ofNullable(var1.getMaxTolerence()).ifPresent(builder1::setMaxTolerence);
        Optional.ofNullable(var1.getLoadableBblsDbs()).ifPresent(builder1::setLoadableBblsdbs);
        Optional.ofNullable(var1.getLoadableBbls60f()).ifPresent(builder1::setLoadableBbls60F);

        Optional.ofNullable(var1.getLoadableLt()).ifPresent(builder1::setLoadableLT);
        Optional.ofNullable(var1.getLoadableMt()).ifPresent(builder1::setLoadableMT);
        Optional.ofNullable(var1.getLoadableKl()).ifPresent(builder1::setLoadableKL);
        Optional.ofNullable(var1.getDifferencePercentage())
            .ifPresent(builder1::setDifferencePercentage);
        Optional.ofNullable(var1.getDifferenceColor()).ifPresent(builder1::setDifferenceColor);

        Optional.ofNullable(var1.getCargoXId()).ifPresent(builder1::setCargoId);
        Optional.ofNullable(var1.getCargoAbbreviation()).ifPresent(builder1::setCargoAbbreviation);
        Optional.ofNullable(var1.getCargoColor()).ifPresent(builder1::setColorCode);
        Optional.ofNullable(var1.getPriority()).ifPresent(builder1::setPriority);
        Optional.ofNullable(var1.getLoadingOrder()).ifPresent(builder1::setLoadingOrder);
        Optional.ofNullable(var1.getSlopQuantity()).ifPresent(builder1::setSlopQuantity);

        Optional.ofNullable(var1.getOrderQuantity())
            .ifPresent(
                v -> {
                  builder1.setOrderQuantity(v.toString());
                });
        Optional.ofNullable(this.getCargoNominationQuantity(var1.getCargoNominationId()))
            .ifPresent(
                v -> {
                  builder1.setCargoNominationQuantity(v.toString());
                });

        Optional.ofNullable(var1.getCargoNominationId()).ifPresent(builder1::setCargoNominationId);
        Optional.ofNullable(var1.getTimeRequiredForLoading())
            .ifPresent(builder1::setTimeRequiredForLoading);
        // loading port details no need
        // cargo topping off no need
        builder.addLoadableQuantityCargoDetails(builder1.build());
      }
    }
    builder.setResponseStatus(repBuilder.setStatus(SUCCESS).build());
  }

  private BigDecimal getCargoNominationQuantity(Long cargoNominationId) {
    try {
      Optional<CargoNomination> var1 =
          cargoNominationRepository.findByIdAndIsActive(cargoNominationId, true);
      if (var1.isPresent()) {
        if (var1.get().getQuantity() != null) {
          return var1.get().getQuantity();
        }
      }
    } catch (Exception e) {
      log.error("Failed to get cargo nomination quantity");
    }
    return BigDecimal.ZERO;
  }

  /**
   * Save data from loading information like timeOfSunrise, timeOfSunset etc to SynopticalTable
   *
   * @param request
   * @param builder
   * @throws Exception
   * @return
   */
  public ResponseStatus.Builder saveLoadingInformationToSynopticalTable(
      LoadableStudy.LoadingInfoSynopticalUpdateRequest request, ResponseStatus.Builder builder)
      throws Exception {
    Optional<SynopticalTable> synopticalOpt =
        this.synopticalTableRepository.findByIdAndIsActive(request.getSynopticalTableId(), true);
    if (synopticalOpt.isPresent()) {
      SynopticalTable table = synopticalOpt.get();
      if (!StringUtils.isEmpty(request.getTimeOfSunrise())) {
        table.setTimeOfSunrise(
            LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(request.getTimeOfSunrise())));
      }
      if (!StringUtils.isEmpty(request.getTimeOfSunset())) {
        table.setTimeOfSunSet(
            LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(request.getTimeOfSunset())));
      }
      this.synopticalTableRepository.save(table);
    } else
      throw new Exception("Cannot find synoptical table with id " + request.getSynopticalTableId());
    builder.setStatus(SUCCESS);
    return builder;
  }

  /**
   * Builds the port info in synoptical table
   *
   * @param entity
   * @param requestedOperationId
   * @param requestedPortId
   */
  public void buildPortsInfoSynopticalTable(
      LoadableStudyPortRotation entity, Long requestedOperationId, Long requestedPortId) {
    // build ports information to update synoptical table
    if (requestedOperationId != 0
        && !StringUtils.isEmpty(
            com.cpdss.loadablestudy.domain.CargoOperation.getOperation(requestedOperationId))) {
      List<SynopticalTable> synopticalTableEntityList = new ArrayList<>();
      if (CARGO_OPERATION_ARR_DEP_SYNOPTICAL.contains(requestedOperationId)) {
        buildSynopticalTableRecord(
            requestedPortId, entity, synopticalTableEntityList, SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL);
        buildSynopticalTableRecord(
            requestedPortId, entity, synopticalTableEntityList, SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE);
      }
      if (!CollectionUtils.isEmpty(entity.getSynopticalTable())) {
        entity.getSynopticalTable().addAll(synopticalTableEntityList);
      } else {
        entity.setSynopticalTable(synopticalTableEntityList);
      }
    }
  }

  /**
   * Builds the synoptical table records
   *
   * @param portId
   * @param entity
   * @param synopticalTableList
   * @param portStage
   */
  private void buildSynopticalTableRecord(
      Long portId,
      LoadableStudyPortRotation entity,
      List<SynopticalTable> synopticalTableList,
      String portStage) {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setLoadableStudyPortRotation(entity);
    synopticalTable.setLoadableStudyXId(entity.getLoadableStudy().getId());
    synopticalTable.setOperationType(portStage);
    synopticalTable.setPortXid(0 == portId ? null : portId);
    synopticalTable.setIsActive(true);
    synopticalTableList.add(synopticalTable);
  }

  public LoadableStudy.SynopticalTableReply.Builder getSynopticDataByLoadableStudyId(
      LoadableStudy.SynopticalTableRequest request,
      LoadableStudy.SynopticalTableReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    CargoOperation cOp = this.cargoOperationRepository.getOne(LOADING_OPERATION_ID);
    Long portRotationId =
        loadableStudyPortRotationService.getLastPortRotationId(loadableStudy.get(), cOp);
    LoadableStudyPortRotation lsPr = loadableStudyPortRotationRepository.getOne(portRotationId);
    Pageable pageable = PageRequest.of(0, 10);
    Page<SynopticalTable> synData =
        synopticalTableRepository.findByloadableStudyPortRotation(lsPr, pageable);
    Optional<SynopticalTable> synopticDEP =
        synData.stream()
            .filter(var1 -> (var1.getIsActive() && var1.getOperationType().equals("DEP")))
            .findFirst();
    log.info(
        "Synoptic Table data id {}, for port rotation id {}",
        synopticDEP.get().getId(),
        lsPr.getId());
    VesselInfo.VesselReply vesselReply =
        this.getSynopticalTableVesselData(request, loadableStudy.get());
    List<VesselInfo.VesselTankDetail> sortedTankList =
        new ArrayList<>(vesselReply.getVesselTanksList());
    buildSynopticalTableReply(
        request,
        Arrays.asList(synopticDEP.get()),
        this.getSynopticalTablePortDetails(Arrays.asList(synopticDEP.get())),
        this.getSynopticalTablePortRotations(loadableStudy.get()),
        loadableStudy.get(),
        sortedTankList,
        vesselReply.getVesselLoadableQuantityDetails(),
        replyBuilder);
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }

  /**
   * Build Synoptical records for synoptical table
   *
   * @param request
   * @param synopticalTableList
   * @param portReply
   * @param vesselLoadableQuantityDetails
   * @param loadableStudy
   * @param portRotations
   * @param replyBuilder
   */
  public void buildSynopticalTableReply(
      LoadableStudy.SynopticalTableRequest request,
      List<SynopticalTable> synopticalTableList,
      PortInfo.PortReply portReply,
      List<LoadableStudyPortRotation> portRotations,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      List<VesselInfo.VesselTankDetail> sortedTankList,
      VesselInfo.VesselLoadableQuantityDetails vesselLoadableQuantityDetails,
      LoadableStudy.SynopticalTableReply.Builder replyBuilder) {
    if (!CollectionUtils.isEmpty(synopticalTableList) && !CollectionUtils.isEmpty(portRotations)) {
      Long firstPortId = portRotations.get(0).getPortXId();
      // first port arrival condition data will be same as the data in obq
      List<OnBoardQuantity> obqEntities =
          this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
              loadableStudy, firstPortId, true);
      // populating ohq data if its empty
      portRotations.forEach(
          portRotation -> {
            List<OnHandQuantity> ohqPortEntities =
                this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
                    loadableStudy, portRotation, true);
            if (ohqPortEntities.isEmpty()) {
              this.populateOnHandQuantityData(Optional.of(loadableStudy), portRotation);
            }
          });
      // fething entire ohq entities based on loadable study
      List<OnHandQuantity> ohqEntities =
          this.onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudy, true);
      List<LoadableStudy.SynopticalRecord> records = new ArrayList<>();
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetails = null;
      List<LoadablePlanStowageBallastDetails> ballastDetails = new ArrayList<>();
      List<LoadablePlanComminglePortwiseDetails> commingleDetails = null;
      // if there is loadable pattern selected, fetch corresponding loadicator and
      // ballast data of
      // all ports
      if (request.getLoadablePatternId() > 0) {
        cargoDetails =
            this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
                request.getLoadablePatternId(), true);
        ballastDetails.addAll(
            this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
                request.getLoadablePatternId(), true));
        commingleDetails =
            this.loadablePlanCommingleDetailsPortwiseRepository.findByLoadablePatternIdAndIsActive(
                request.getLoadablePatternId(), true);
      }
      for (SynopticalTable synopticalEntity : synopticalTableList) {
        LoadableStudy.SynopticalRecord.Builder builder =
            LoadableStudy.SynopticalRecord.newBuilder();
        this.buildSynopticalRecord(synopticalEntity, builder, portReply);
        // set eta/etd estamted values from port rotation table
        this.setSynopticalEtaEtdEstimated(synopticalEntity, builder, portRotations);
        this.setSynopticalCargoDetails(
            request,
            cargoDetails,
            obqEntities,
            synopticalEntity,
            builder,
            sortedTankList,
            firstPortId,
            loadableStudy.getVoyage(),
            commingleDetails);
        this.setSynopticalOhqData(ohqEntities, synopticalEntity, builder, sortedTankList);
        this.setSynopticalTableVesselParticulars(
            synopticalEntity, builder, vesselLoadableQuantityDetails);
        if (request.getLoadablePatternId() > 0) {
          this.setSynopticalTableLoadicatorData(
              synopticalEntity, request.getLoadablePatternId(), builder);
          this.setBallastDetails(
              request, synopticalEntity, builder, ballastDetails, sortedTankList);
        }
        if (synopticalEntity.getPortXid() != null && synopticalEntity.getPortXid() > 0) {
          this.setPortDetailForSynoptics(synopticalEntity, builder);
        }
        records.add(builder.build());
      }
      replyBuilder.addAllSynopticalRecords(records);
    }
  }

  /**
   * Fetch port details for synoptical table
   *
   * @param synopticalTableList
   * @return
   * @throws GenericServiceException
   */
  public PortInfo.PortReply getSynopticalTablePortDetails(List<SynopticalTable> synopticalTableList)
      throws GenericServiceException {
    PortInfo.GetPortInfoByPortIdsRequest.Builder portReqBuilder =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
    buildPortIdsRequestSynoptical(portReqBuilder, synopticalTableList);
    PortInfo.PortReply portReply = this.getPortInfo(portReqBuilder.build());
    if (portReply != null
        && portReply.getResponseStatus() != null
        && !SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling port service within getSynopticalTable",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portReply;
  }

  /**
   * Get port rotation details for synoptical table
   *
   * @param loadableStudy
   * @return
   */
  public List<LoadableStudyPortRotation> getSynopticalTablePortRotations(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy) {
    return this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
        loadableStudy, true);
  }

  /**
   * * Get vessel data for synoptical table
   *
   * @param request
   * @param loadableStudy
   * @return
   * @throws GenericServiceException
   */
  public VesselInfo.VesselReply getSynopticalTableVesselData(
      LoadableStudy.SynopticalTableRequest request,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy)
      throws GenericServiceException {
    VesselInfo.VesselRequest.Builder vesselGrpcRequest = VesselInfo.VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(request.getVesselId());
    ofNullable(loadableStudy.getLoadLineXId())
        .ifPresent(vesselGrpcRequest::setVesselDraftConditionId);
    ofNullable(loadableStudy.getDraftMark())
        .ifPresent(item -> vesselGrpcRequest.setDraftExtreme(valueOf(item)));
    vesselGrpcRequest.addAllTankCategories(SYNOPTICAL_TABLE_TANK_CATEGORIES);
    VesselInfo.VesselReply vesselReply =
        this.getVesselDetailForSynopticalTable(vesselGrpcRequest.build());
    if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs",
          vesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
    }
    return vesselReply;
  }

  public void populateOnHandQuantityData(
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt,
      LoadableStudyPortRotation portRotation) {
    VoyageStatus voyageStatus = this.voyageStatusRepository.getOne(CLOSE_VOYAGE_STATUS);
    Voyage previousVoyage =
        this.voyageRepository
            .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                loadableStudyOpt.get().getVoyage().getVesselXId(), true, voyageStatus);

    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> confirmedLoadableStudyOpt =
        this.loadableStudyRepository
            .findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
                previousVoyage,
                CONFIRMED_STATUS_ID,
                true,
                Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE);

    List<OnHandQuantity> onHandQuantityList = null;
    if (confirmedLoadableStudyOpt.isPresent()) {

      LoadableStudyPortRotation lastDischargingPortPortRotation =
          this.loadableStudyPortRotationRepository
              .findFirstByLoadableStudyAndOperationAndIsActiveOrderByPortOrderDesc(
                  confirmedLoadableStudyOpt.get(),
                  this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID),
                  true);

      onHandQuantityList =
          this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
              confirmedLoadableStudyOpt.get(), lastDischargingPortPortRotation, true);

      if (!onHandQuantityList.isEmpty()) {

        Long portOrder = portRotation.getPortOrder();
        List<OnHandQuantity> OnHandQuantities = new ArrayList<OnHandQuantity>();

        List<LoadableStudyPortRotation> portRotationList =
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                loadableStudyOpt.get().getId(), true);
        if (null != portRotationList && !portRotationList.isEmpty()) {
          int index =
              IntStream.range(0, portRotationList.size())
                  .filter(i -> portRotationList.get(i).getId().equals(portRotation.getId()))
                  .findFirst()
                  .orElse(-1);
          if (portOrder.equals(portRotationList.get(0).getPortOrder())) {
            boolean ohqComplete = true;
            List<Long> fuelTypes = new ArrayList<Long>();
            for (OnHandQuantity onHandQuantity : onHandQuantityList) {
              if (ohqComplete && !fuelTypes.contains(onHandQuantity.getFuelTypeXId())) {
                fuelTypes.add(onHandQuantity.getFuelTypeXId());
                BigDecimal total = new BigDecimal(0);
                for (OnHandQuantity ohq : onHandQuantityList) {
                  if (ohq.getFuelTypeXId() == onHandQuantity.getFuelTypeXId()) {
                    total = total.add(ohq.getDepartureQuantity());
                  }
                }
                if (total.compareTo(new BigDecimal(0)) <= 0) {
                  ohqComplete = false;
                }
              }
              entityManager.detach(onHandQuantity);
              onHandQuantity.setId(null);
              onHandQuantity.setLoadableStudy(loadableStudyOpt.get());
              onHandQuantity.setActualArrivalQuantity(null);
              onHandQuantity.setActualDepartureQuantity(null);
              onHandQuantity.setArrivalQuantity(onHandQuantity.getDepartureQuantity());
              onHandQuantity.setPortXId(portRotation.getPortXId());
              onHandQuantity.setPortRotation(portRotation);
              OnHandQuantities.add(onHandQuantity);
            }
            portRotation.setIsPortRotationOhqComplete(ohqComplete);
          } else {

            LoadableStudyPortRotation previousPortPortRotation = portRotationList.get(index - 1);
            portRotation.setIsPortRotationOhqComplete(
                previousPortPortRotation.getIsPortRotationOhqComplete());
            this.loadableStudyPortRotationRepository.save(portRotation);
            onHandQuantityList =
                this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
                    loadableStudyOpt.get(), previousPortPortRotation, true);
            onHandQuantityList.forEach(
                onHandQuantity -> {
                  entityManager.detach(onHandQuantity);
                  onHandQuantity.setId(null);
                  onHandQuantity.setLoadableStudy(loadableStudyOpt.get());
                  onHandQuantity.setActualArrivalQuantity(null);
                  onHandQuantity.setActualDepartureQuantity(null);
                  onHandQuantity.setArrivalQuantity(onHandQuantity.getDepartureQuantity());
                  onHandQuantity.setPortXId(portRotation.getPortXId());
                  onHandQuantity.setPortRotation(portRotation);
                  OnHandQuantities.add(onHandQuantity);
                });
          }
          this.onHandQuantityRepository.saveAll(OnHandQuantities);
        }
      }
    }
  }

  /**
   * Build synoptical table record
   *
   * @param synopticalEntity
   * @param builder
   * @param portReply
   */
  public void buildSynopticalRecord(
      SynopticalTable synopticalEntity,
      LoadableStudy.SynopticalRecord.Builder builder,
      PortInfo.PortReply portReply) {
    ofNullable(synopticalEntity.getId()).ifPresent(builder::setId);
    ofNullable(synopticalEntity.getPortXid()).ifPresent(builder::setPortId);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    if (portReply != null) {
      portReply.getPortsList().stream()
          .filter(p -> synopticalEntity.getPortXid().equals(p.getId()))
          .findAny()
          .ifPresent(
              port -> {
                this.setSynopticalPortValues(port, builder);
              });
    }
    ofNullable(synopticalEntity.getOperationType()).ifPresent(builder::setOperationType);
    ofNullable(synopticalEntity.getDistance())
        .ifPresent(distance -> builder.setDistance(String.valueOf(distance)));
    ofNullable(synopticalEntity.getSpeed())
        .ifPresent(speed -> builder.setSpeed(String.valueOf(speed)));
    ofNullable(synopticalEntity.getRunningHours())
        .ifPresent(runningHours -> builder.setRunningHours(String.valueOf(runningHours)));
    ofNullable(synopticalEntity.getInPortHours())
        .ifPresent(inPortHours -> builder.setInPortHours(String.valueOf(inPortHours)));
    ofNullable(synopticalEntity.getTimeOfSunrise())
        .ifPresent(time -> builder.setTimeOfSunrise(timeFormatter.format(time)));
    ofNullable(synopticalEntity.getTimeOfSunSet())
        .ifPresent(time -> builder.setTimeOfSunset(timeFormatter.format(time)));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    ofNullable(synopticalEntity.getHwTideFrom())
        .ifPresent(hwTideFrom -> builder.setHwTideFrom(String.valueOf(hwTideFrom)));
    ofNullable(synopticalEntity.getHwTideTimeFrom())
        .ifPresent(
            hwTideTimeFrom -> builder.setHwTideTimeFrom(timeFormatter.format(hwTideTimeFrom)));
    ofNullable(synopticalEntity.getHwTideTo())
        .ifPresent(hwTideTo -> builder.setHwTideTo(String.valueOf(hwTideTo)));
    ofNullable(synopticalEntity.getHwTideTimeTo())
        .ifPresent(hwTideTimeTo -> builder.setHwTideTimeTo(timeFormatter.format(hwTideTimeTo)));
    ofNullable(synopticalEntity.getLwTideFrom())
        .ifPresent(lwTideFrom -> builder.setLwTideFrom(String.valueOf(lwTideFrom)));
    ofNullable(synopticalEntity.getLwTideTimeFrom())
        .ifPresent(
            lwTideTimeFrom -> builder.setLwTideTimeFrom(timeFormatter.format(lwTideTimeFrom)));
    ofNullable(synopticalEntity.getLwTideTo())
        .ifPresent(lwTideTo -> builder.setLwTideTo(String.valueOf(lwTideTo)));
    ofNullable(synopticalEntity.getLwTideTimeTo())
        .ifPresent(lwTideTimeTo -> builder.setLwTideTimeTo(timeFormatter.format(lwTideTimeTo)));
    Optional.ofNullable(synopticalEntity.getLoadableStudyPortRotation())
        .ifPresent(portRotation -> builder.setPortRotationId(portRotation.getId()));
    if (null != synopticalEntity.getEtaActual()) {
      builder.setEtaEtdActual(formatter.format(synopticalEntity.getEtaActual()));
    } else if (null != synopticalEntity.getEtdActual()) {
      builder.setEtaEtdActual(formatter.format(synopticalEntity.getEtdActual()));
    }
  }

  /**
   * Set eta and etd estimated values
   *
   * @param synopticalEntity
   * @param builder
   * @param portRotations
   */
  public void setSynopticalEtaEtdEstimated(
      SynopticalTable synopticalEntity,
      LoadableStudy.SynopticalRecord.Builder builder,
      List<LoadableStudyPortRotation> portRotations) {
    /*
     * Optional<LoadableStudyPortRotationService> portRotation = portRotations .stream()
     * .filter( pr ->
     * pr.getId().equals(synopticalEntity.getLoadableStudyPortRotation().getId()))
     * .findFirst();
     */
    Optional<LoadableStudyPortRotation> portRotation =
        Optional.of(synopticalEntity.getLoadableStudyPortRotation());
    if (portRotation.isPresent()) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(synopticalEntity.getOperationType())) {
        builder.setEtaEtdEstimated(
            null == portRotation.get().getEta()
                ? ""
                : formatter.format(portRotation.get().getEta()));
      } else {
        builder.setEtaEtdEstimated(
            null == portRotation.get().getEtd()
                ? ""
                : formatter.format(portRotation.get().getEtd()));
      }
      if (null != portRotation.get().getPortOrder()) {
        builder.setPortOrder(portRotation.get().getPortOrder());
      }
      if (null != portRotation.get().getSeaWaterDensity()) {
        builder.setSpecificGravity(portRotation.get().getSeaWaterDensity().toString());
      }
      builder.setPortRotationId(portRotation.get().getId());
    }
  }

  /**
   * Set cargo details on synoptical record
   *
   * @param request
   * @param obqEntities
   * @param synopticalEntity
   * @param builder
   * @param firstPortId
   * @param voyage
   * @param cargoDetails
   * @param commingleCargoDetails
   */
  public void setSynopticalCargoDetails(
      LoadableStudy.SynopticalTableRequest request,
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetails,
      List<OnBoardQuantity> obqEntities,
      SynopticalTable synopticalEntity,
      LoadableStudy.SynopticalRecord.Builder builder,
      List<VesselInfo.VesselTankDetail> sortedTankList,
      Long firstPortId,
      Voyage voyage,
      List<LoadablePlanComminglePortwiseDetails> commingleCargoDetails) {
    BigDecimal cargoActualTotal = BigDecimal.ZERO;
    BigDecimal cargoPlannedTotal = BigDecimal.ZERO;
    List<com.cpdss.loadablestudy.domain.CargoHistory> cargoHistories = null;
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> portSpecificCargoDetails =
        new ArrayList<>();
    List<LoadablePlanComminglePortwiseDetails> portSpecificCommingleCargoDetails =
        new ArrayList<>();
    if (null != cargoDetails) {
      portSpecificCargoDetails.addAll(
          cargoDetails.stream()
              .filter(
                  det ->
                      det.getPortRotationId()
                              .equals(synopticalEntity.getLoadableStudyPortRotation().getId())
                          && det.getOperationType().equals(synopticalEntity.getOperationType()))
              .collect(Collectors.toList()));
    }

    if (null != commingleCargoDetails) {
      portSpecificCommingleCargoDetails.addAll(
          commingleCargoDetails.stream()
              .filter(
                  det ->
                      det.getPortRotationXid()
                              .equals(synopticalEntity.getLoadableStudyPortRotation().getId())
                          && det.getOperationType().equals(synopticalEntity.getOperationType()))
              .collect(Collectors.toList()));
    }

    for (VesselInfo.VesselTankDetail tank : sortedTankList) {
      if (!CARGO_TANK_CATEGORIES.contains(tank.getTankCategoryId())) {
        continue;
      }
      LoadableStudy.SynopticalCargoRecord.Builder cargoBuilder =
          LoadableStudy.SynopticalCargoRecord.newBuilder();
      cargoBuilder.setTankId(tank.getTankId());
      cargoBuilder.setTankName(tank.getShortName());
      cargoBuilder.setCapacity(tank.getFullCapacityCubm());
      // first port arrival data will be same as obq data
      // if no obq data is found, previos voyage data has to be fetched
      if (synopticalEntity.getPortXid().equals(firstPortId)
          && synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)) {
        this.buildObqDataForSynopticalTable(
            tank, cargoHistories, obqEntities, cargoBuilder, voyage);
      } else if (request.getLoadablePatternId() > 0) {
        Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> tankDataOpt =
            portSpecificCargoDetails.stream()
                .filter(cargo -> cargo.getTankId().equals(tank.getTankId()))
                .findAny();
        if (tankDataOpt.isPresent()) {
          ofNullable(tankDataOpt.get().getCargoNominationId())
              .ifPresent(v -> cargoBuilder.setCargoNominationId(v));
          ofNullable(tankDataOpt.get().getId()).ifPresent(v -> cargoBuilder.setLpCargoDetailId(v));
          ofNullable(tankDataOpt.get().getPlannedQuantity())
              .ifPresent(item -> cargoBuilder.setPlannedWeight(valueOf(item)));
          ofNullable(tankDataOpt.get().getActualQuantity())
              .ifPresent(item -> cargoBuilder.setActualWeight(valueOf(item)));
          // attributes for landing page
          ofNullable(tankDataOpt.get().getCargoId()).ifPresent(cargoBuilder::setCargoId);
          ofNullable(tankDataOpt.get().getAbbreviation())
              .ifPresent(cargoBuilder::setCargoAbbreviation);
          ofNullable(tankDataOpt.get().getColorCode()).ifPresent(cargoBuilder::setColorCode);
          ofNullable(tankDataOpt.get().getCorrectedUllage())
              .ifPresent(ullage -> cargoBuilder.setCorrectedUllage(valueOf(ullage)));
          ofNullable(tankDataOpt.get().getApi())
              .ifPresent(api -> cargoBuilder.setApi(valueOf(api)));
          Optional.ofNullable(tankDataOpt.get().getTemperature())
              .ifPresent(temp -> cargoBuilder.setTemperature(valueOf(temp)));
          ofNullable(tankDataOpt.get().getFillingRatio()).ifPresent(cargoBuilder::setFillingRatio);
        }

        Optional<LoadablePlanComminglePortwiseDetails> commingleTankDataOpt =
            portSpecificCommingleCargoDetails.stream()
                .filter(cargo -> cargo.getTankId().equals(tank.getTankId()))
                .findAny();
        if (commingleTankDataOpt.isPresent()) {
          cargoBuilder.setIsCommingleCargo(true);
          BigDecimal cargo1Mt = null;
          BigDecimal cargo2Mt = null;

          if (!isEmpty(commingleTankDataOpt.get().getCargo1Mt())) {
            cargo1Mt = new BigDecimal(commingleTankDataOpt.get().getCargo1Mt());
          }

          if (!isEmpty(commingleTankDataOpt.get().getCargo2Mt())) {
            cargo2Mt = new BigDecimal(commingleTankDataOpt.get().getCargo2Mt());
          }

          BigDecimal plannedQuantity = cargo1Mt.add(cargo2Mt);

          Optional.ofNullable(plannedQuantity)
              .ifPresent(item -> cargoBuilder.setPlannedWeight(valueOf(item)));

          Optional.ofNullable(commingleTankDataOpt.get().getActualQuantity())
              .ifPresent(item -> cargoBuilder.setActualWeight(valueOf(item)));

          Optional.ofNullable(commingleTankDataOpt.get().getCorrectedUllage())
              .ifPresent(ullage -> cargoBuilder.setCorrectedUllage(valueOf(ullage)));
          Optional.ofNullable(commingleTankDataOpt.get().getApi())
              .ifPresent(api -> cargoBuilder.setApi(valueOf(api)));
          ofNullable(commingleTankDataOpt.get().getFillingRatio())
              .ifPresent(cargoBuilder::setFillingRatio);

          List<LoadablePlanCommingleDetails> commingleDetails =
              this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
                  commingleTankDataOpt.get().getLoadablePattern(), true);

          Optional<LoadablePlanCommingleDetails> lpcd =
              commingleDetails.stream()
                  .filter(
                      comDetail ->
                          comDetail.getTankId().equals(commingleTankDataOpt.get().getTankId()))
                  .findAny();

          if (lpcd.isPresent()) {
            Optional.ofNullable(lpcd.get().getGrade())
                .ifPresent(grade -> cargoBuilder.setCargoAbbreviation(grade));
          }
        }
      }
      if (!isEmpty(cargoBuilder.getActualWeight())) {
        cargoActualTotal = cargoActualTotal.add(new BigDecimal(cargoBuilder.getActualWeight()));
      }
      if (!isEmpty(cargoBuilder.getPlannedWeight())) {
        cargoPlannedTotal = cargoPlannedTotal.add(new BigDecimal(cargoBuilder.getPlannedWeight()));
      }

      builder.addCargo(cargoBuilder.build());
    }
    builder.setCargoActualTotal(valueOf(cargoActualTotal));
    builder.setCargoPlannedTotal(valueOf(cargoPlannedTotal));
  }

  public void buildObqDataForSynopticalTable(
      VesselInfo.VesselTankDetail tank,
      List<com.cpdss.loadablestudy.domain.CargoHistory> cargoHistories,
      List<OnBoardQuantity> obqEntities,
      LoadableStudy.SynopticalCargoRecord.Builder cargoBuilder,
      Voyage voyage) {

    Optional<OnBoardQuantity> obqOpt =
        obqEntities.stream().filter(obq -> obq.getTankId().equals(tank.getTankId())).findAny();
    if (obqOpt.isPresent()) {
      OnBoardQuantity obqEntity = obqOpt.get();
      if (null != obqEntity.getActualArrivalWeight()) {
        cargoBuilder.setActualWeight(valueOf(obqEntity.getActualArrivalWeight()));
      }
      if (null != obqEntity.getPlannedArrivalWeight()) {
        cargoBuilder.setPlannedWeight(valueOf(obqEntity.getPlannedArrivalWeight()));
      }
      if (null != obqEntity.getDensity()) {
        cargoBuilder.setApi(valueOf(obqEntity.getDensity()));
      }
      if (null != obqEntity.getAbbreviation()) {
        cargoBuilder.setCargoAbbreviation(obqEntity.getAbbreviation());
      }
    } else {
      // data has to be populated from previous voyage - cargo history table
      // lazy loading the cargo history
      if (null == cargoHistories) {
        cargoHistories = voyageService.findCargoHistoryForPrvsVoyage(voyage);
      }
      Optional<com.cpdss.loadablestudy.domain.CargoHistory> cargoHistoryOpt =
          cargoHistories.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
      if (cargoHistoryOpt.isPresent()) {
        com.cpdss.loadablestudy.domain.CargoHistory dto = cargoHistoryOpt.get();
        ofNullable(dto.getQuantity())
            .ifPresent(item -> cargoBuilder.setPlannedWeight(valueOf(item)));
      }
    }
  }

  /**
   * Set ohq data
   *
   * @param ohqEntities
   * @param synopticalEntity
   * @param builder
   * @param sortedTankList
   */
  public void setSynopticalOhqData(
      List<OnHandQuantity> ohqEntities,
      SynopticalTable synopticalEntity,
      LoadableStudy.SynopticalRecord.Builder builder,
      List<VesselInfo.VesselTankDetail> sortedTankList) {
    List<OnHandQuantity> portSpecificEntities =
        ohqEntities.stream()
            .filter(
                entity ->
                    null != entity.getPortRotation()
                        && entity
                            .getPortRotation()
                            .getId()
                            .equals(synopticalEntity.getLoadableStudyPortRotation().getId()))
            .collect(Collectors.toList());
    for (VesselInfo.VesselTankDetail tank : sortedTankList) {
      if (!OHQ_TANK_CATEGORIES.contains(tank.getTankCategoryId()) || !tank.getShowInOhqObq()) {
        continue;
      }
      LoadableStudy.SynopticalOhqRecord.Builder ohqBuilder =
          LoadableStudy.SynopticalOhqRecord.newBuilder();
      ohqBuilder.setTankId(tank.getTankId());
      ohqBuilder.setTankName(tank.getShortName());
      ohqBuilder.setFuelTypeId(tank.getTankCategoryId());
      ohqBuilder.setFuelType(tank.getTankCategoryShortName());
      ohqBuilder.setCapacity(tank.getFullCapacityCubm());
      ohqBuilder.setPortRotationId(synopticalEntity.getLoadableStudyPortRotation().getId());
      Optional<OnHandQuantity> ohqOpt =
          portSpecificEntities.stream()
              .filter(ohq -> ohq.getTankXId().equals(tank.getTankId()))
              .findAny();
      if (ohqOpt.isPresent()) {
        OnHandQuantity ohq = ohqOpt.get();
        if (null != ohq.getDensity()) {
          ohqBuilder.setDensity(valueOf(ohq.getDensity()));
        }
        if (synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)) {
          if (null != ohq.getArrivalQuantity()) {
            ohqBuilder.setPlannedWeight(valueOf(ohq.getArrivalQuantity()));
          }
          if (null != ohq.getActualArrivalQuantity()) {
            ohqBuilder.setActualWeight(valueOf(ohq.getActualArrivalQuantity()));
          }
        } else if (synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE)) {
          if (null != ohq.getDepartureQuantity()) {
            ohqBuilder.setPlannedWeight(valueOf(ohq.getDepartureQuantity()));
          }
          if (null != ohq.getActualDepartureQuantity()) {
            ohqBuilder.setActualWeight(valueOf(ohq.getActualDepartureQuantity()));
          }
        }
      }
      builder.addOhq(ohqBuilder.build());
    }
  }

  /**
   * Set vessel particular data
   *
   * @param synopticalEntity
   * @param builder
   * @param vesselLoadableQuantityDetails
   */
  public void setSynopticalTableVesselParticulars(
      SynopticalTable synopticalEntity,
      LoadableStudy.SynopticalRecord.Builder builder,
      VesselInfo.VesselLoadableQuantityDetails vesselLoadableQuantityDetails) {
    ofNullable(synopticalEntity.getOthersPlanned())
        .ifPresent(item -> builder.setOthersPlanned(valueOf(item)));
    ofNullable(synopticalEntity.getOthersActual())
        .ifPresent(item -> builder.setOthersActual(valueOf(item)));
    builder.setConstantPlanned(vesselLoadableQuantityDetails.getConstant());
    ofNullable(synopticalEntity.getConstantPlanned())
        .ifPresent(item -> builder.setConstantPlanned(valueOf(item)));
    ofNullable(synopticalEntity.getConstantActual())
        .ifPresent(item -> builder.setConstantActual(valueOf(item)));
    builder.setTotalDwtPlanned(vesselLoadableQuantityDetails.getDwt());
    ofNullable(synopticalEntity.getDeadWeightPlanned())
        .ifPresent(item -> builder.setTotalDwtPlanned(valueOf(item)));
    ofNullable(synopticalEntity.getDeadWeightActual())
        .ifPresent(item -> builder.setTotalDwtActual(valueOf(item)));
    builder.setDisplacementPlanned(vesselLoadableQuantityDetails.getDisplacmentDraftRestriction());
    ofNullable(synopticalEntity.getDisplacementPlanned())
        .ifPresent(item -> builder.setDisplacementPlanned(valueOf(item)));
    ofNullable(synopticalEntity.getDisplacementActual())
        .ifPresent(item -> builder.setDisplacementActual(valueOf(item)));
    ofNullable(vesselLoadableQuantityDetails.getHasLoadicator())
        .ifPresent(item -> builder.setHasLoadicator(item));
  }

  /**
   * * Set port values to synoptical response
   *
   * @param port
   * @param builder
   */
  public void setSynopticalPortValues(
      PortInfo.PortDetail port,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder) {
    builder.setPortName(port.getName());
    builder.setSpecificGravity(port.getWaterDensity());
    builder.setHwTideFrom(port.getHwTideFrom());
    builder.setHwTideTo(port.getHwTideTo());
    builder.setLwTideFrom(port.getLwTideFrom());
    builder.setLwTideTo(port.getLwTideTo());
    if (!isEmpty(port.getHwTideTimeFrom())) {
      builder.setHwTideTimeFrom(port.getHwTideTimeFrom());
    }
    if (!isEmpty(port.getHwTideTimeTo())) {
      builder.setHwTideTimeTo(port.getHwTideTimeTo());
    }
    if (!isEmpty(port.getLwTideTimeFrom())) {
      builder.setLwTideTimeFrom(port.getLwTideTimeFrom());
    }
    if (!isEmpty(port.getLwTideTimeTo())) {
      builder.setLwTideTimeTo(port.getLwTideTimeTo());
    }
    if (!isEmpty(port.getSunriseTime())) {
      builder.setTimeOfSunrise(port.getSunriseTime());
    }
    if (!isEmpty(port.getSunsetTime())) {
      builder.setTimeOfSunset(port.getSunsetTime());
    }
  }

  /**
   * Call vessel info grpc service for synoptical table data
   *
   * @param request
   * @return
   */
  public VesselInfo.VesselReply getVesselDetailForSynopticalTable(
      VesselInfo.VesselRequest request) {
    return this.vesselInfoGrpcService.getVesselDetailForSynopticalTable(request);
  }

  /**
   * Build port request to fetch port related fields from port master
   *
   * @param portReqBuilder
   * @param synopticalTableList
   */
  public void buildPortIdsRequestSynoptical(
      com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.Builder portReqBuilder,
      List<SynopticalTable> synopticalTableList) {
    // build fetch port details request object
    if (!CollectionUtils.isEmpty(synopticalTableList)) {
      synopticalTableList.forEach(
          synopticalRecord ->
              ofNullable(synopticalRecord.getPortXid()).ifPresent(portReqBuilder::addId));
    }
  }

  /**
   * @param build
   * @return PortReply
   */
  public PortInfo.PortReply getPortInfo(PortInfo.GetPortInfoByPortIdsRequest build) {
    return portInfoGrpcService.getPortInfoByPortIds(build);
  }

  /**
   * Set loadicator data in synoptical table record
   *
   * @param synopticalEntity
   * @param builder
   */
  public void setSynopticalTableLoadicatorData(
      SynopticalTable synopticalEntity,
      Long loadablePatternId,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder) {
    SynopticalTableLoadicatorData loadicatorData =
        this.synopticalTableLoadicatorDataRepository
            .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                synopticalEntity, loadablePatternId, true);
    if (null != loadicatorData) {
      com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder =
          com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.newBuilder();
      ofNullable(loadicatorData.getBlindSector())
          .ifPresent(item -> dataBuilder.setBlindSector(valueOf(item)));
      ofNullable(loadicatorData.getDeflection())
          .ifPresent(item -> dataBuilder.setDeflection(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedDraftAftActual())
          .ifPresent(item -> dataBuilder.setCalculatedDraftAftActual(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedDraftAftPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedDraftAftPlanned(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedDraftFwdActual())
          .ifPresent(item -> dataBuilder.setCalculatedDraftFwdActual(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedDraftFwdPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedDraftFwdPlanned(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedDraftMidActual())
          .ifPresent(item -> dataBuilder.setCalculatedDraftMidActual(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedDraftMidPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedDraftMidPlanned(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedTrimActual())
          .ifPresent(item -> dataBuilder.setCalculatedTrimActual(valueOf(item)));
      ofNullable(loadicatorData.getCalculatedTrimPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedTrimPlanned(valueOf(item)));
      this.setFinalDraftValues(dataBuilder, loadicatorData);
      ofNullable(loadicatorData.getList()).ifPresent(list -> dataBuilder.setList(valueOf(list)));
      ofNullable(loadicatorData.getBendingMoment())
          .ifPresent(bm -> dataBuilder.setBendingMoment(valueOf(bm)));
      ofNullable(loadicatorData.getShearingForce())
          .ifPresent(sf -> dataBuilder.setShearingForce(valueOf(sf)));
      builder.setLoadicatorData(dataBuilder.build());
      ofNullable(loadicatorData.getBallastActual())
          .ifPresent(item -> builder.setBallastActual(valueOf(item)));
    } else {
      log.info("loadicator data does not exist for loadable patter with id {}", loadablePatternId);
    }
  }

  /**
   * Set ballast details
   *
   * @param synopticalEntity
   * @param builder
   * @param ballastDetails
   * @param sortedTankList
   * @param request
   */
  private void setBallastDetails(
      LoadableStudy.SynopticalTableRequest request,
      SynopticalTable synopticalEntity,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder,
      List<LoadablePlanStowageBallastDetails> ballastDetails,
      List<VesselInfo.VesselTankDetail> sortedTankList) {
    List<LoadablePlanStowageBallastDetails> portBallastList = new ArrayList<>();
    portBallastList.addAll(
        ballastDetails.stream()
            .filter(
                bd ->
                    synopticalEntity.getPortXid().equals(bd.getPortXId())
                        && synopticalEntity.getOperationType().equals(bd.getOperationType()))
            .collect(Collectors.toList()));

    for (VesselInfo.VesselTankDetail tank : sortedTankList) {
      if (!BALLAST_TANK_CATEGORY_ID.equals(tank.getTankCategoryId())) {
        continue;
      }
      LoadableStudy.SynopticalBallastRecord.Builder ballastBuilder =
          LoadableStudy.SynopticalBallastRecord.newBuilder();
      ballastBuilder.setTankId(tank.getTankId());
      ballastBuilder.setTankName(tank.getShortName());
      ballastBuilder.setCapacity(tank.getFullCapacityCubm());
      Optional<LoadablePlanStowageBallastDetails> tankBallastDetail =
          portBallastList.stream()
              .filter(b -> b.getTankXId().longValue() == tank.getTankId())
              .findAny();
      if (tankBallastDetail.isPresent()) {
        LoadablePlanStowageBallastDetails ballast = tankBallastDetail.get();
        Optional.ofNullable(ballast.getQuantity())
            .ifPresent(item -> ballastBuilder.setPlannedWeight(valueOf(item)));
        Optional.ofNullable(ballast.getActualQuantity())
            .ifPresent(item -> ballastBuilder.setActualWeight(valueOf(item)));
        Optional.ofNullable(ballast.getCorrectedUllage())
            .ifPresent(ullage -> ballastBuilder.setCorrectedUllage(ullage));
        Optional.ofNullable(ballast.getSg()).ifPresent(sg -> ballastBuilder.setSpGravity(sg));
        Optional.ofNullable(ballast.getColorCode())
            .ifPresent(colorCode -> ballastBuilder.setColorCode(colorCode));
        Optional.ofNullable(ballast.getFillingPercentage())
            .ifPresent(fillingRatio -> ballastBuilder.setFillingRatio(fillingRatio));

      } else {
        log.info(
            "Ballast details not available for the tank: {}, pattern: {}",
            tank.getTankId(),
            request.getLoadablePatternId());
      }
      builder.addBallast(ballastBuilder.build());
    }
  }

  /**
   * Fetch Single Port Details
   *
   * @param synoptics
   * @param builder
   */
  private void setPortDetailForSynoptics(
      SynopticalTable synoptics, LoadableStudy.SynopticalRecord.Builder builder) {
    PortInfo.PortReply reply =
        portInfoGrpcService.getPortInfoByPortIds(
            PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
                .addId(synoptics.getPortXid())
                .build());
    if (!reply.getPortsList().isEmpty()) { // Expect single entry as response
      builder.setPortTimezoneId(reply.getPortsList().get(0).getTimezoneId());
    }
  }

  /**
   * Set final draft values
   *
   * @param dataBuilder
   * @param loadicatorData
   */
  public void setFinalDraftValues(
      com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder,
      SynopticalTableLoadicatorData loadicatorData) {
    BigDecimal deflection = BigDecimal.ZERO;
    if (null != loadicatorData.getDeflection()) {
      deflection = loadicatorData.getDeflection();
    }
    BigDecimal calculatedDraftFwd = BigDecimal.ZERO;
    if (null != loadicatorData.getCalculatedDraftFwdActual()) {
      calculatedDraftFwd = loadicatorData.getCalculatedDraftFwdActual();
    } else if (null != loadicatorData.getCalculatedDraftFwdPlanned()) {
      calculatedDraftFwd = loadicatorData.getCalculatedDraftFwdPlanned();
    }
    BigDecimal calculatedDraftAft = BigDecimal.ZERO;
    if (null != loadicatorData.getCalculatedDraftAftActual()) {
      calculatedDraftAft = loadicatorData.getCalculatedDraftAftActual();
    } else if (null != loadicatorData.getCalculatedDraftAftPlanned()) {
      calculatedDraftAft = loadicatorData.getCalculatedDraftAftPlanned();
    }
    BigDecimal calculatedDraftMid = BigDecimal.ZERO;
    if (null != loadicatorData.getCalculatedDraftMidActual()) {
      calculatedDraftMid = loadicatorData.getCalculatedDraftMidActual();
    } else if (null != loadicatorData.getCalculatedDraftMidPlanned()) {
      calculatedDraftMid = loadicatorData.getCalculatedDraftMidPlanned();
    }
    dataBuilder.setFinalDraftAft(valueOf(deflection.add(calculatedDraftAft)));
    dataBuilder.setFinalDraftFwd(valueOf(deflection.add(calculatedDraftFwd)));
    dataBuilder.setFinalDraftMid(valueOf(deflection.add(calculatedDraftMid)));
  }

  /**
   * Method to build port operations table
   *
   * @param loadableStudyId loadable study id value
   * @param loadablePatterId loadable pattern id value
   * @return PortOperationTable object
   */
  public PortOperationTable buildPortOperationsTable(long loadableStudyId, long loadablePatterId)
      throws GenericServiceException {

    //    Get loadable study port rotation details
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    ModelMapper modelMapper = new ModelMapper();
    loadableStudyPortRotationService.buildLoadableStudyPortRotationDetails(
        loadableStudyId, loadableStudy, modelMapper);

    //    Get loadable study details
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyDetails =
        loadableStudyRepository
            .findByIdAndIsActive(loadableStudyId, true)
            .orElseThrow(
                () ->
                    new GenericServiceException(
                        String.format(
                            "Loadable study details not found for LoadableStudyId: %d",
                            loadableStudyId),
                        CommonErrorCodes.E_HTTP_BAD_REQUEST,
                        HttpStatusCode.BAD_REQUEST));

    // GRPC call to Vessel Info
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(loadableStudyDetails.getVesselXId())
            .setVesselDraftConditionId(loadableStudyDetails.getLoadLineXId())
            .setDraftExtreme(loadableStudyDetails.getDraftMark().toString())
            .build();
    VesselInfo.VesselReply vesselReply =
        this.vesselInfoGrpcService.getVesselDetailByVesselId(replyBuilder);
    double vesselLwt =
        Double.parseDouble(vesselReply.getVesselLoadableQuantityDetails().getVesselLightWeight());

    //    Get port rotation details
    loadableStudyPortRotationService.buildportRotationDetails(loadableStudyDetails, loadableStudy);

    // Get cargo details
    List<LoadablePatternCargoDetails> cargoes =
        loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
            loadablePatterId, true);
    // Get on hand quantities
    List<OnHandQuantity> onHandQuantities =
        onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyDetails, true);
    // Get ballast details
    List<LoadablePlanStowageBallastDetails> ballastDetails =
        loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
            loadablePatterId, true);
    //    Set OperationsTable details
    List<OperationsTable> operationsTableList = new ArrayList<>();
    for (com.cpdss.loadablestudy.domain.LoadableStudyPortRotation portDetails :
        loadableStudy.getLoadableStudyPortRotation()) {

      //      Get port rotations
      LoadableStudyPortRotation loadableStudyPortRotation =
          loadableStudyDetails.getPortRotations().stream()
              .filter(rotation -> rotation.getPortXId().equals(portDetails.getPortId()))
              .findFirst()
              .orElse(new LoadableStudyPortRotation());

      // Get timezone
      Optional<PortDetails> portMasterDetails =
          loadableStudy.getPortDetails().stream()
              .filter(port -> port.getId().longValue() == portDetails.getPortId().longValue())
              .findFirst();
      System.out.println(portMasterDetails.get().getOffset());
      Optional<SynopticalTable> arrSynopticRecord =
          this.synopticalTableRepository
              .findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
                  loadableStudyId, loadableStudyPortRotation.getId(), OPERATION_TYPE_ARR, true);
      SynopticalTableLoadicatorData arrSynopticalTableLoadicatorData =
          this.synopticalTableLoadicatorDataRepository
              .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                  arrSynopticRecord.get(), loadablePatterId, true);
      Optional<SynopticalTable> depSynopticRecord =
          this.synopticalTableRepository
              .findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
                  loadableStudyId, loadableStudyPortRotation.getId(), OPERATION_TYPE_DEP, true);
      SynopticalTableLoadicatorData depSynopticalTableLoadicatorData =
          this.synopticalTableLoadicatorDataRepository
              .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                  depSynopticRecord.get(), loadablePatterId, true);
      // Calculating displacement
      // Get the port-wise cargo total
      double cargoArrTotal =
          cargoes.stream()
              .filter(
                  cargo ->
                      cargo.getPortRotationId().doubleValue()
                              == loadableStudyPortRotation.getId().doubleValue()
                          && cargo.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ARR))
              .mapToDouble(
                  cargo ->
                      cargo.getPlannedQuantity() != null
                          ? cargo.getPlannedQuantity().doubleValue()
                          : 0)
              .sum();
      double cargoDepTotal =
          cargoes.stream()
              .filter(
                  cargo ->
                      cargo.getPortRotationId().doubleValue()
                              == loadableStudyPortRotation.getId().doubleValue()
                          && cargo.getOperationType().equalsIgnoreCase(OPERATION_TYPE_DEP))
              .mapToDouble(
                  cargo ->
                      cargo.getPlannedQuantity() != null
                          ? cargo.getPlannedQuantity().doubleValue()
                          : 0)
              .sum();
      // Get portwise ohq totals
      double foArrTotal =
          onHandQuantities.stream()
              .filter(
                  ohq ->
                      ohq.getPortRotation().getId() == loadableStudyPortRotation.getId()
                          && ohq.getFuelTypeXId() == FUEL_OIL_TANK_CATEGORY_ID)
              .mapToDouble(ohq -> ohq.getArrivalQuantity().doubleValue())
              .sum();
      double foDepTotal =
          onHandQuantities.stream()
              .filter(
                  ohq ->
                      ohq.getPortRotation().getId() == loadableStudyPortRotation.getId()
                          && ohq.getFuelTypeXId() == FUEL_OIL_TANK_CATEGORY_ID)
              .mapToDouble(ohq -> ohq.getDepartureQuantity().doubleValue())
              .sum();
      double doArrTotal =
          onHandQuantities.stream()
              .filter(
                  ohq ->
                      ohq.getPortRotation().getId() == loadableStudyPortRotation.getId()
                          && ohq.getFuelTypeXId() == DIESEL_OIL_TANK_CATEGORY_ID)
              .mapToDouble(ohq -> ohq.getArrivalQuantity().doubleValue())
              .sum();
      double doDepTotal =
          onHandQuantities.stream()
              .filter(
                  ohq ->
                      ohq.getPortRotation().getId() == loadableStudyPortRotation.getId()
                          && ohq.getFuelTypeXId() == DIESEL_OIL_TANK_CATEGORY_ID)
              .mapToDouble(ohq -> ohq.getDepartureQuantity().doubleValue())
              .sum();
      double fwArrTotal =
          onHandQuantities.stream()
              .filter(
                  ohq ->
                      ohq.getPortRotation().getId() == loadableStudyPortRotation.getId()
                          && ohq.getFuelTypeXId() == FRESH_WATER_TANK_CATEGORY_ID)
              .mapToDouble(ohq -> ohq.getArrivalQuantity().doubleValue())
              .sum();
      double fwDepTotal =
          onHandQuantities.stream()
              .filter(
                  ohq ->
                      ohq.getPortRotation().getId() == loadableStudyPortRotation.getId()
                          && ohq.getFuelTypeXId() == FRESH_WATER_TANK_CATEGORY_ID)
              .mapToDouble(ohq -> ohq.getDepartureQuantity().doubleValue())
              .sum();
      // Get ballast total
      double ballastArrTotal =
          ballastDetails.stream()
              .filter(
                  ballast ->
                      ballast.getPortRotationId().doubleValue()
                              == loadableStudyPortRotation.getId().doubleValue()
                          && ballast.getOperationType().equalsIgnoreCase(OPERATION_TYPE_ARR))
              .mapToDouble(
                  ballast ->
                      ballast.getQuantity() != null ? ballast.getQuantity().doubleValue() : 0)
              .sum();
      double ballastDepTotal =
          ballastDetails.stream()
              .filter(
                  ballast ->
                      ballast.getPortRotationId().doubleValue()
                              == loadableStudyPortRotation.getId().doubleValue()
                          && ballast.getOperationType().equalsIgnoreCase(OPERATION_TYPE_DEP))
              .mapToDouble(
                  ballast ->
                      ballast.getQuantity() != null ? ballast.getQuantity().doubleValue() : 0)
              .sum();
      double arrDisplacement =
          cargoArrTotal
              + (null != arrSynopticRecord.get().getConstantPlanned()
                  ? arrSynopticRecord.get().getConstantPlanned().doubleValue()
                  : vesselReply.getVesselLoadableQuantityDetails().getDeadWeightConstant())
              + (null != arrSynopticRecord.get().getOthersPlanned()
                  ? arrSynopticRecord.get().getOthersPlanned().doubleValue()
                  : 0)
              + foArrTotal
              + doArrTotal
              + fwArrTotal
              + ballastArrTotal
              + vesselLwt;
      double depDisplacement =
          cargoDepTotal
              + (null != depSynopticRecord.get().getConstantPlanned()
                  ? depSynopticRecord.get().getConstantPlanned().doubleValue()
                  : vesselReply.getVesselLoadableQuantityDetails().getDeadWeightConstant())
              + (null != depSynopticRecord.get().getOthersPlanned()
                  ? depSynopticRecord.get().getOthersPlanned().doubleValue()
                  : 0)
              + foDepTotal
              + doDepTotal
              + fwDepTotal
              + ballastDepTotal
              + vesselLwt;
      BigDecimal depDeflection =
          depSynopticalTableLoadicatorData.getDeflection() != null
              ? depSynopticalTableLoadicatorData.getDeflection()
              : BigDecimal.ZERO;
      BigDecimal arrDeflection =
          arrSynopticalTableLoadicatorData.getDeflection() != null
              ? arrSynopticalTableLoadicatorData.getDeflection()
              : BigDecimal.ZERO;
      BigDecimal depAft =
          depSynopticalTableLoadicatorData.getCalculatedDraftAftActual() != null
              ? depSynopticalTableLoadicatorData.getCalculatedDraftAftActual()
              : (depSynopticalTableLoadicatorData.getCalculatedDraftAftPlanned() != null
                  ? depSynopticalTableLoadicatorData.getCalculatedDraftAftPlanned()
                  : BigDecimal.ZERO);
      BigDecimal arrAft =
          arrSynopticalTableLoadicatorData.getCalculatedDraftAftActual() != null
              ? arrSynopticalTableLoadicatorData.getCalculatedDraftAftActual()
              : (arrSynopticalTableLoadicatorData.getCalculatedDraftAftPlanned() != null
                  ? arrSynopticalTableLoadicatorData.getCalculatedDraftAftPlanned()
                  : BigDecimal.ZERO);
      BigDecimal depFwd =
          depSynopticalTableLoadicatorData.getCalculatedDraftFwdActual() != null
              ? depSynopticalTableLoadicatorData.getCalculatedDraftFwdActual()
              : (depSynopticalTableLoadicatorData.getCalculatedDraftFwdPlanned() != null
                  ? depSynopticalTableLoadicatorData.getCalculatedDraftFwdPlanned()
                  : BigDecimal.ZERO);
      BigDecimal arrFwd =
          arrSynopticalTableLoadicatorData.getCalculatedDraftAftActual() != null
              ? arrSynopticalTableLoadicatorData.getCalculatedDraftFwdActual()
              : (arrSynopticalTableLoadicatorData.getCalculatedDraftFwdPlanned() != null
                  ? arrSynopticalTableLoadicatorData.getCalculatedDraftFwdPlanned()
                  : BigDecimal.ZERO);

      // Setting timezone converted dates
      String eta =
          this.getTimezoneConvertedString(
              loadableStudyPortRotation.getEta(),
              Double.parseDouble(portMasterDetails.get().getOffset()),
              portMasterDetails.get().getTimezoneAbbr());
      String etd =
          this.getTimezoneConvertedString(
              loadableStudyPortRotation.getEtd(),
              Double.parseDouble(portMasterDetails.get().getOffset()),
              portMasterDetails.get().getTimezoneAbbr());
      LocalDate laycanFrom =
          this.getTimezoneConvertedDate(
              loadableStudyPortRotation.getLayCanFrom(),
              Double.parseDouble(portMasterDetails.get().getOffset()),
              true);
      LocalDate laycanTo =
          this.getTimezoneConvertedDate(
              loadableStudyPortRotation.getLayCanFrom(),
              Double.parseDouble(portMasterDetails.get().getOffset()),
              false);

      OperationsTable operationsTableData =
          OperationsTable.builder()
              .operation(loadableStudyPortRotation.getOperation().getName())
              .portName(
                  loadableStudy.getPortDetails().stream()
                      .filter(rotationObj -> rotationObj.getId().equals(portDetails.getPortId()))
                      .findFirst()
                      .orElse(new PortDetails())
                      .getName())
              .eta(eta)
              .etd(etd)
              .country(
                  loadableStudy.getPortDetails().stream()
                      .filter(rotationObj -> rotationObj.getId().equals(portDetails.getPortId()))
                      .findFirst()
                      .orElse(new PortDetails())
                      .getCountryName())
              .laycanRange(
                  null != laycanFrom && null != laycanTo
                      ? DateTimeFormatter.ofPattern(LAY_CAN_FORMAT).format(laycanFrom)
                          + "/"
                          + DateTimeFormatter.ofPattern(LAY_CAN_FORMAT).format(laycanTo)
                      : "")
              .arrFwdDraft(arrFwd.doubleValue() + arrDeflection.doubleValue())
              .depFwdDraft(depFwd.doubleValue() + depDeflection.doubleValue())
              .arrAftDraft(arrAft.doubleValue() + arrDeflection.doubleValue())
              .depAftDraft(depAft.doubleValue() + depDeflection.doubleValue())
              .arrDisplacement(arrDisplacement)
              .depDisp(depDisplacement)
              .build();
      operationsTableList.add(operationsTableData);
    }
    return PortOperationTable.builder().operationsTableList(operationsTableList).build();
  }

  private LocalDate getTimezoneConvertedDate(LocalDate date, double offset, boolean from) {
    if (date == null) {
      return null;
    }
    LocalDateTime dateTime = from ? date.atTime(0, 0, 0) : date.atTime(23, 59, 59);
    return getTimezoneConvertedDate(dateTime, offset).toLocalDate();
  }

  private LocalDateTime getTimezoneConvertedDate(LocalDateTime dateTime, double offset) {
    long hours = (long) offset;
    long minutes = (long) ((offset * 10) % 10) * 6;
    LocalDateTime newDateTime = dateTime.plusHours(hours);
    newDateTime = newDateTime.plusMinutes(minutes);
    return newDateTime;
  }

  private String getTimezoneConvertedString(LocalDateTime dateTime, double offset, String abbr) {
    if (dateTime == null) {
      return "";
    }
    LocalDateTime newDateTime = this.getTimezoneConvertedDate(dateTime, offset);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
    String formattedDate = newDateTime.format(format);
    if (offset == 0) {
      formattedDate.concat(" UTC");
    } else {
      int hours = (int) Math.abs(offset);
      int minutes = (int) Math.abs(((offset * 10) % 10) * 6);
      String formattedOffset = String.format("%02d", hours) + ":" + String.format("%02d", minutes);
      formattedDate =
          formattedDate.concat(
              " " + abbr + " (UTC " + (offset > 0 ? "+" : "-") + formattedOffset + ")");
    }

    return formattedDate;
  }

  public void getSynopticalTable(
      LoadableStudy.SynopticalTableRequest request,
      LoadableStudy.SynopticalTableReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    List<SynopticalTable> synopticalTableList =
        this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveOrderByPortOrder(
            request.getLoadableStudyId(), true);
    if (!synopticalTableList.isEmpty()) {
      VesselInfo.VesselReply vesselReply =
          this.getSynopticalTableVesselData(request, loadableStudyOpt.get());
      List<VesselInfo.VesselTankDetail> sortedTankList =
          new ArrayList<>(vesselReply.getVesselTanksList());
      Collections.sort(
          sortedTankList, Comparator.comparing(VesselInfo.VesselTankDetail::getTankDisplayOrder));
      buildSynopticalTableReply(
          request,
          synopticalTableList,
          this.getSynopticalTablePortDetails(synopticalTableList),
          this.getSynopticalTablePortRotations(loadableStudyOpt.get()),
          loadableStudyOpt.get(),
          sortedTankList,
          vesselReply.getVesselLoadableQuantityDetails(),
          replyBuilder);
    }
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
  }

  public void getSynopticalPortDataByPortId(
      LoadableStudy.SynopticalTableRequest request,
      LoadableStudy.SynopticalTableReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    List<SynopticalTable> synopticalTableList =
        this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
            request.getLoadableStudyId(), true, request.getPortId());
    if (!synopticalTableList.isEmpty()) {
      buildSynopticalPortDataReplyByPortId(synopticalTableList, replyBuilder);
    }
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
  }

  private void buildSynopticalPortDataReplyByPortId(
      List<SynopticalTable> synopticalTableList,
      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.Builder replyBuilder) {
    if (!CollectionUtils.isEmpty(synopticalTableList)) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      synopticalTableList.forEach(
          synopticalRecord -> {
            LoadableStudy.SynopticalRecord.Builder recordBuilder =
                LoadableStudy.SynopticalRecord.newBuilder();
            ofNullable(synopticalRecord.getOperationType())
                .ifPresent(recordBuilder::setOperationType);
            ofNullable(synopticalRecord.getDistance())
                .ifPresent(distance -> recordBuilder.setDistance(String.valueOf(distance)));
            ofNullable(synopticalRecord.getEtaActual())
                .ifPresent(
                    etaActual ->
                        recordBuilder.setEtaEtdActual(
                            formatter.format(synopticalRecord.getEtaActual())));
            ofNullable(synopticalRecord.getEtdActual())
                .ifPresent(
                    etdActual ->
                        recordBuilder.setEtaEtdActual(
                            formatter.format(synopticalRecord.getEtdActual())));
            replyBuilder.addSynopticalRecords(recordBuilder);
          });
    }
  }

  public LoadableStudy.SynopticalTableReply.Builder saveSynopticalTable(
      LoadableStudy.SynopticalTableRequest request,
      LoadableStudy.SynopticalTableReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist with given id",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());

    Optional<LoadablePattern> patternOpt = Optional.empty();
    if (request.getLoadablePatternId() > 0) {
      patternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
      if (!patternOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable pattern does not exist with given id",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    }
    for (LoadableStudy.SynopticalRecord record : request.getSynopticalRecordList()) {
      Optional<SynopticalTable> entityOpt =
          this.synopticalTableRepository.findByIdAndIsActive(record.getId(), true);
      if (!entityOpt.isPresent()) {
        throw new GenericServiceException(
            "Synoptical record does not exist with given id",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      SynopticalTable entity = entityOpt.get();
      entity = this.buildSynopticalTableEntity(entity, record);
      entity = this.synopticalTableRepository.save(entity);
      this.saveSynopticalEtaEtdEstimates(entity, record);
      if (request.getLoadablePatternId() > 0) {
        this.saveSynopticalLoadicatorData(entity, request.getLoadablePatternId(), record);
        this.saveSynopticalBallastData(request.getLoadablePatternId(), record, entity);
      }
      this.saveSynopticalCargoData(request, loadableStudyOpt.get(), entity, record);
      this.saveSynopticalOhqData(loadableStudyOpt.get(), entity, record);
    }
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Populate synoptical entity fields
   *
   * @param entity
   * @param record
   * @return
   * @throws GenericServiceException
   */
  public SynopticalTable buildSynopticalTableEntity(
      SynopticalTable entity, LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    entity.setDistance(isEmpty(record.getDistance()) ? null : new BigDecimal(record.getDistance()));
    entity.setSpeed(isEmpty(record.getSpeed()) ? null : new BigDecimal(record.getSpeed()));
    entity.setRunningHours(
        isEmpty(record.getRunningHours()) ? null : new BigDecimal(record.getRunningHours()));
    entity.setInPortHours(
        isEmpty(record.getInPortHours()) ? null : new BigDecimal(record.getInPortHours()));
    entity.setTimeOfSunrise(
        isEmpty(record.getTimeOfSunrise())
            ? null
            : LocalTime.from(dtf.parse(record.getTimeOfSunrise())));
    entity.setTimeOfSunSet(
        isEmpty(record.getTimeOfSunset())
            ? null
            : LocalTime.from(dtf.parse(record.getTimeOfSunset())));
    entity.setHwTideFrom(
        isEmpty(record.getHwTideFrom()) ? null : new BigDecimal(record.getHwTideFrom()));
    entity.setHwTideTo(isEmpty(record.getHwTideTo()) ? null : new BigDecimal(record.getHwTideTo()));
    entity.setLwTideFrom(
        isEmpty(record.getLwTideFrom()) ? null : new BigDecimal(record.getLwTideFrom()));
    entity.setLwTideTo(isEmpty(record.getLwTideTo()) ? null : new BigDecimal(record.getLwTideTo()));
    entity.setHwTideTimeFrom(
        isEmpty(record.getHwTideTimeFrom())
            ? null
            : LocalTime.from(dtf.parse(record.getHwTideTimeFrom())));
    entity.setHwTideTimeTo(
        isEmpty(record.getHwTideTimeTo())
            ? null
            : LocalTime.from(dtf.parse(record.getHwTideTimeTo())));
    entity.setLwTideTimeFrom(
        isEmpty(record.getLwTideTimeFrom())
            ? null
            : LocalTime.from(dtf.parse(record.getLwTideTimeFrom())));
    entity.setLwTideTimeTo(
        isEmpty(record.getLwTideTimeTo())
            ? null
            : LocalTime.from(dtf.parse(record.getLwTideTimeTo())));
    this.buidlSynopticalTableVesselData(entity, record);
    this.buildSynopticalTableEtaEtdActuals(entity, record);
    return entity;
  }

  /**
   * Update estimated values to port rotation table
   *
   * @param entity
   * @param record
   * @throws GenericServiceException
   */
  public void saveSynopticalEtaEtdEstimates(
      SynopticalTable entity, LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {

    LoadableStudyPortRotation prEntity = entity.getLoadableStudyPortRotation();
    if (null == prEntity) {
      throw new GenericServiceException(
          "Port rotation does not exist for given synoptical record",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(entity.getLoadableStudyXId(), true);
    if (!loadableStudy.isPresent()) {
      throw new GenericServiceException(
          "Loadable study with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    this.validateSaveSynopticalEtaEtdEstimates(entity, record, loadableStudy.get(), prEntity);

    LocalDateTime etaEtdEstimated =
        isEmpty(record.getEtaEtdEstimated())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(record.getEtaEtdEstimated()));
    if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
      prEntity.setEta(etaEtdEstimated);
    } else {
      prEntity.setEtd(etaEtdEstimated);
    }
    if (record.getSpecificGravity() != null && !isEmpty(record.getSpecificGravity())) {
      prEntity.setSeaWaterDensity(new BigDecimal(record.getSpecificGravity()));
    }
    this.loadableStudyPortRotationRepository.save(prEntity);
  }

  private void validateSaveSynopticalEtaEtdEstimates(
      SynopticalTable entity,
      LoadableStudy.SynopticalRecord record,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      LoadableStudyPortRotation prEntity)
      throws GenericServiceException {

    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);

    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && ((SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())
                && prEntity.getEta() != null
                && prEntity.getEta().toString().equals(record.getEtaEtdEstimated()))
            || (SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE.equals(entity.getOperationType())
                && prEntity.getEtd() != null
                && prEntity.getEtd().toString().equals(record.getEtaEtdEstimated())))) {

      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * Save synoptical table loadicator data
   *
   * @param entity
   * @param entity
   * @param loadablepatternId
   * @throws GenericServiceException
   */
  public void saveSynopticalLoadicatorData(
      SynopticalTable entity, Long loadablepatternId, LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData data =
        record.getLoadicatorData();
    SynopticalTableLoadicatorData ldEntity =
        this.synopticalTableLoadicatorDataRepository
            .findBySynopticalTableAndLoadablePatternIdAndIsActive(entity, loadablepatternId, true);
    if (null == ldEntity) {
      log.info(
          "Loadicator data does not exist for given synoptical record with id {}", record.getId());
    } else {
      ldEntity.setDeflection(
          isEmpty(data.getDeflection()) ? null : new BigDecimal(data.getDeflection()));
      ldEntity.setCalculatedDraftFwdActual(
          isEmpty(data.getCalculatedDraftFwdActual())
              ? null
              : new BigDecimal(data.getCalculatedDraftFwdActual()));
      ldEntity.setCalculatedDraftAftActual(
          isEmpty(data.getCalculatedDraftAftActual())
              ? null
              : new BigDecimal(data.getCalculatedDraftAftActual()));
      ldEntity.setCalculatedDraftMidActual(
          isEmpty(data.getCalculatedDraftMidActual())
              ? null
              : new BigDecimal(data.getCalculatedDraftMidActual()));
      ldEntity.setCalculatedTrimActual(
          isEmpty(data.getCalculatedTrimActual())
              ? null
              : new BigDecimal(data.getCalculatedTrimActual()));
      ldEntity.setBlindSector(
          isEmpty(data.getBlindSector()) ? null : new BigDecimal(data.getBlindSector()));
      ldEntity.setBallastActual(
          isEmpty(record.getBallastActual()) ? null : new BigDecimal(record.getBallastActual()));
      this.synopticalTableLoadicatorDataRepository.save(ldEntity);
    }
  }

  /**
   * Set vessel particular data in synoptical table
   *
   * @param entity
   * @param record
   * @throws GenericServiceException
   */
  private void buidlSynopticalTableVesselData(
      SynopticalTable entity, LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {

    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(entity.getLoadableStudyXId(), true);
    if (!loadableStudy.isPresent()) {
      throw new GenericServiceException(
          "Loadable study with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    this.validateSynopticalVesselData(loadableStudy.get(), entity, record);

    entity.setOthersPlanned(
        isEmpty(record.getOthersPlanned()) ? null : new BigDecimal(record.getOthersPlanned()));
    entity.setOthersActual(
        isEmpty(record.getOthersActual()) ? null : new BigDecimal(record.getOthersActual()));
    entity.setConstantPlanned(
        isEmpty(record.getConstantPlanned()) ? null : new BigDecimal(record.getConstantPlanned()));
    entity.setConstantActual(
        isEmpty(record.getConstantActual()) ? null : new BigDecimal(record.getConstantActual()));
    entity.setDeadWeightPlanned(
        isEmpty(record.getTotalDwtPlanned()) ? null : new BigDecimal(record.getTotalDwtPlanned()));
    entity.setDeadWeightActual(
        isEmpty(record.getTotalDwtActual()) ? null : new BigDecimal(record.getTotalDwtActual()));
    entity.setDisplacementPlanned(
        isEmpty(record.getDisplacementPlanned())
            ? null
            : new BigDecimal(record.getDisplacementPlanned()));
    entity.setDisplacementActual(
        isEmpty(record.getDisplacementActual())
            ? null
            : new BigDecimal(record.getDisplacementActual()));
  }

  /**
   * build eta etd actuals
   *
   * @param entity
   * @param record
   */
  public void buildSynopticalTableEtaEtdActuals(
      SynopticalTable entity, LoadableStudy.SynopticalRecord record) {
    LocalDateTime etaEtdActual =
        isEmpty(record.getEtaEtdActual())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(record.getEtaEtdActual()));
    if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
      entity.setEtaActual(etaEtdActual);
    } else {
      entity.setEtdActual(etaEtdActual);
    }
  }

  /**
   * Save synoptical ballast data
   *
   * @param loadablePatternId
   * @param record
   * @param entity
   */
  private void saveSynopticalBallastData(
      long loadablePatternId, LoadableStudy.SynopticalRecord record, SynopticalTable entity) {
    List<LoadablePlanStowageBallastDetails> ballastEntities =
        this.loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                loadablePatternId,
                entity.getLoadableStudyPortRotation().getId(),
                entity.getOperationType(),
                true);
    List<LoadablePlanStowageBallastDetails> toBeSaved = new ArrayList<>();
    if (!record.getBallastList().isEmpty()) {
      for (LoadableStudy.SynopticalBallastRecord rec : record.getBallastList()) {
        Optional<LoadablePlanStowageBallastDetails> entityOpt =
            ballastEntities.stream()
                .filter(b -> b.getTankXId().longValue() == rec.getTankId())
                .findAny();
        if (entityOpt.isPresent()) {
          LoadablePlanStowageBallastDetails ent = entityOpt.get();
          ent.setActualQuantity(
              isEmpty(rec.getActualWeight()) ? null : new BigDecimal(rec.getActualWeight()));
          toBeSaved.add(ent);
        } else {
          LoadablePlanStowageBallastDetails ent = new LoadablePlanStowageBallastDetails();
          ent.setTankXId(rec.getTankId());
          ent.setIsActive(true);
          ent.setLoadablePatternId(loadablePatternId);
          ent.setPortXId(entity.getPortXid());
          ent.setOperationType(entity.getOperationType());
          ent.setPortRotationId(entity.getLoadableStudyPortRotation().getId());
          ent.setActualQuantity(
              isEmpty(rec.getActualWeight()) ? null : new BigDecimal(rec.getActualWeight()));
          toBeSaved.add(ent);
        }
      }
      this.loadablePlanStowageBallastDetailsRepository.saveAll(toBeSaved);
    }
  }

  /**
   * Save cargo data
   *
   * @param request
   * @param loadableStudy
   * @param entity
   * @param request
   * @throws GenericServiceException
   */
  private void saveSynopticalCargoData(
      LoadableStudy.SynopticalTableRequest request,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      SynopticalTable entity,
      LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {
    List<Long> portIds = loadableStudyPortRotationService.getPortRoationPortIds(loadableStudy);
    if (null == portIds || portIds.isEmpty()) {
      throw new GenericServiceException(
          "No ports added for loadable study with id: " + loadableStudy.getId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Long firstPortId = portIds.get(0);
    if (entity.getPortXid().equals(firstPortId)
        && SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
      this.saveSynopticalObqData(loadableStudy, record);
    } else if (request.getLoadablePatternId() > 0) {
      this.saveSynopticalCargoByLoadablePattern(request, entity, record);
    }
  }

  private void saveSynopticalOhqData(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      SynopticalTable entity,
      LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {
    List<OnHandQuantity> ohqEntities =
        this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            loadableStudy, entity.getLoadableStudyPortRotation(), true);
    List<OnHandQuantity> toBeSavedList = new ArrayList<>();

    for (LoadableStudy.SynopticalOhqRecord ohqRecord : record.getOhqList()) {
      OnHandQuantity ohqEntity = null;
      Optional<OnHandQuantity> ohqEntityOpt =
          ohqEntities.stream()
              .filter(ohq -> ohq.getTankXId().equals(ohqRecord.getTankId()))
              .findAny();
      if (ohqEntityOpt.isPresent()) {
        ohqEntity = ohqEntityOpt.get();
      } else {

        ohqEntity = new OnHandQuantity();
        ohqEntity.setTankXId(ohqRecord.getTankId());
        ohqEntity.setPortXId(record.getPortId());
        ohqEntity.setFuelTypeXId(ohqRecord.getFuelTypeId());
        ohqEntity.setLoadableStudy(loadableStudy);
        ohqEntity.setPortRotation(entity.getLoadableStudyPortRotation());
        ohqEntity.setIsActive(true);
      }
      this.validateSaveSynopticalOhqData(ohqEntity, entity, ohqRecord, loadableStudy);

      if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
        ohqEntity.setArrivalQuantity(
            isEmpty(ohqRecord.getPlannedWeight())
                ? null
                : new BigDecimal(ohqRecord.getPlannedWeight()));
        ohqEntity.setActualArrivalQuantity(
            isEmpty(ohqRecord.getActualWeight())
                ? null
                : new BigDecimal(ohqRecord.getActualWeight()));
      } else {
        ohqEntity.setDepartureQuantity(
            isEmpty(ohqRecord.getPlannedWeight())
                ? null
                : new BigDecimal(ohqRecord.getPlannedWeight()));
        ohqEntity.setActualDepartureQuantity(
            isEmpty(ohqRecord.getActualWeight())
                ? null
                : new BigDecimal(ohqRecord.getActualWeight()));
      }
      toBeSavedList.add(ohqEntity);
    }
    if (!toBeSavedList.isEmpty()) {
      this.onHandQuantityRepository.saveAll(toBeSavedList);
    }
  }

  void validateSynopticalVesselData(
      com.cpdss.loadablestudy.entity.LoadableStudy loadablestudy,
      SynopticalTable entity,
      LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {
    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadablestudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadablestudy, true);
    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && ((null != entity.getOthersPlanned()
                && entity.getOthersPlanned().doubleValue()
                    != Double.parseDouble(record.getOthersPlanned()))
            || (null != entity.getConstantPlanned()
                && entity.getConstantPlanned().doubleValue()
                    != Double.parseDouble(record.getConstantPlanned()))
        //            || (null != entity.getDeadWeightPlanned()
        //            && entity.getDeadWeightPlanned().doubleValue() !=
        //            Double.parseDouble(record.getTotalDwtPlanned()))
        //            || (null != entity.getDisplacementPlanned()
        //            && entity.getDisplacementPlanned().doubleValue() !=
        //            Double.parseDouble(record.getDisplacementPlanned()))
        )) {
      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * Save obq data from synoptical table
   *
   * @param loadableStudy
   * @param record
   * @throws GenericServiceException
   */
  private void saveSynopticalObqData(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      LoadableStudy.SynopticalRecord record)
      throws GenericServiceException {
    List<OnBoardQuantity> obqEntities =
        this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            loadableStudy, record.getPortId(), true);
    List<OnBoardQuantity> toBeSavedList = new ArrayList<>();

    for (LoadableStudy.SynopticalCargoRecord cargoRecord : record.getCargoList()) {

      OnBoardQuantity obqEntity = null;
      Optional<OnBoardQuantity> obqEntityOpt =
          obqEntities.stream()
              .filter(obq -> obq.getTankId().equals(cargoRecord.getTankId()))
              .findAny();
      if (obqEntityOpt.isPresent()) {

        obqEntity = obqEntityOpt.get();

      } else {
        obqEntity = new OnBoardQuantity();
        obqEntity.setTankId(cargoRecord.getTankId());
        obqEntity.setPortId(record.getPortId());
        obqEntity.setLoadableStudy(loadableStudy);
        obqEntity.setIsActive(true);
      }

      this.validateSaveSynopticalObqData(obqEntity, cargoRecord, loadableStudy);

      obqEntity.setPlannedArrivalWeight(
          isEmpty(cargoRecord.getPlannedWeight())
              ? null
              : new BigDecimal(cargoRecord.getPlannedWeight()));
      obqEntity.setActualArrivalWeight(
          isEmpty(cargoRecord.getActualWeight())
              ? null
              : new BigDecimal(cargoRecord.getActualWeight()));
      toBeSavedList.add(obqEntity);
    }
    if (!toBeSavedList.isEmpty()) {
      this.onBoardQuantityRepository.saveAll(toBeSavedList);
    }
  }

  /**
   * Save synoptical cargo data by loadable pattern id
   *
   * @param request
   * @param entity
   * @param record
   */
  private void saveSynopticalCargoByLoadablePattern(
      LoadableStudy.SynopticalTableRequest request,
      SynopticalTable entity,
      LoadableStudy.SynopticalRecord record) {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoEntities =
        this.loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                request.getLoadablePatternId(),
                entity.getLoadableStudyPortRotation().getId(),
                entity.getOperationType(),
                true);

    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        commingleCargoEntities =
            this.loadablePlanCommingleDetailsPortwiseRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    request.getLoadablePatternId(),
                    entity.getLoadableStudyPortRotation().getId(),
                    entity.getOperationType(),
                    true);
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> toBeSavedCargoList =
        new ArrayList<>();

    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        toBeSavedCommingleCargoList = new ArrayList<>();

    for (LoadableStudy.SynopticalCargoRecord cargoRecord : record.getCargoList()) {
      if (!cargoRecord.getIsCommingleCargo()) {
        Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> entityOpt =
            cargoEntities.stream()
                .filter(cargo -> cargo.getTankId().equals(cargoRecord.getTankId()))
                .findAny();
        if (entityOpt.isPresent()) {
          com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoEntity = entityOpt.get();
          cargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));
          toBeSavedCargoList.add(cargoEntity);
        } else {
          com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoEntity =
              new com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails();
          cargoEntity.setLoadablePatternId(request.getLoadablePatternId());
          cargoEntity.setPortId(entity.getPortXid());
          cargoEntity.setTankId(cargoRecord.getTankId());
          cargoEntity.setIsActive(true);
          cargoEntity.setOperationType(entity.getOperationType());
          cargoEntity.setPortRotationId(entity.getLoadableStudyPortRotation().getId());
          cargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));
          toBeSavedCargoList.add(cargoEntity);
        }
      } else {
        Optional<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
            comminleEntityOpt =
                commingleCargoEntities.stream()
                    .filter(
                        commingleCargo ->
                            commingleCargo.getTankId().equals(commingleCargo.getTankId()))
                    .findAny();
        if (comminleEntityOpt.isPresent()) {
          com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails commingleCargoEntity =
              comminleEntityOpt.get();

          commingleCargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));

          toBeSavedCommingleCargoList.add(commingleCargoEntity);
        } else {
          com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails commingleCargoEntity =
              new com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails();

          LoadablePattern loadablePattern =
              this.loadablePatternRepository.getOne(request.getLoadablePatternId());
          commingleCargoEntity.setLoadablePattern(loadablePattern);
          commingleCargoEntity.setPortId(entity.getPortXid());
          commingleCargoEntity.setTankId(cargoRecord.getTankId());
          commingleCargoEntity.setIsActive(true);
          commingleCargoEntity.setOperationType(entity.getOperationType());
          commingleCargoEntity.setPortRotationXid(entity.getLoadableStudyPortRotation().getId());
          commingleCargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));

          toBeSavedCommingleCargoList.add(commingleCargoEntity);
        }
      }
    }
    if (!toBeSavedCargoList.isEmpty()) {
      this.loadablePatternCargoDetailsRepository.saveAll(toBeSavedCargoList);
    }

    if (!toBeSavedCommingleCargoList.isEmpty()) {
      this.loadablePlanCommingleDetailsPortwiseRepository.saveAll(toBeSavedCommingleCargoList);
    }
  }

  private void validateSaveSynopticalOhqData(
      OnHandQuantity ohqEntity,
      SynopticalTable entity,
      LoadableStudy.SynopticalOhqRecord ohqRecord,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy)
      throws GenericServiceException {

    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);

    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && ((SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())
                && ohqEntity.getArrivalQuantity() != null
                && !Integer.toString(ohqEntity.getArrivalQuantity().intValue())
                    .equals(ohqRecord.getPlannedWeight()))
            || (SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE.equals(entity.getOperationType())
                && ohqEntity.getDepartureQuantity() != null
                && !Integer.toString(ohqEntity.getDepartureQuantity().intValue())
                    .equals(ohqRecord.getPlannedWeight())))) {

      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private void validateSaveSynopticalObqData(
      OnBoardQuantity obqEntity,
      LoadableStudy.SynopticalCargoRecord cargoRecord,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy)
      throws GenericServiceException {

    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);

    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && null != obqEntity.getPlannedArrivalWeight()
        && !cargoRecord
            .getPlannedWeight()
            .equals(Integer.toString(obqEntity.getPlannedArrivalWeight().intValue()))) {
      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public LoadableStudy.SynopticalTableReply.Builder getSynopticalDataByPortId(
      LoadableStudy.SynopticalTableRequest request,
      LoadableStudy.SynopticalTableReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    /**
     * updated the request object with confirmed loadable pattern id which is needed only for voyage
     * status initiated flow. In case this method is reused for any other flow then the loadable
     * pattern id should be updated accordingly using probably a flag to track initiated flow
     */
    List<LoadablePattern> confirmedLoadablePatternList =
        loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
            request.getVoyageId(), CONFIRMED_STATUS_ID, true);
    Optional<LoadablePattern> confirmedLoadablePattern =
        confirmedLoadablePatternList.stream().findFirst();
    if (!confirmedLoadablePattern.isPresent()) {
      throw new GenericServiceException(
          "Confirmed loadable pattern does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    Long confirmedLoadablePatternId = confirmedLoadablePattern.get().getId();
    LoadableStudy.SynopticalTableRequest.Builder requestBuilder = request.toBuilder();
    requestBuilder.setLoadablePatternId(confirmedLoadablePatternId);
    // fetching synoptical entity list
    List<SynopticalTable> synopticalTableList =
        this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
            request.getLoadableStudyId(), true, request.getPortId());
    if (!synopticalTableList.isEmpty()) {
      // fetching vessel tanks including ohq and cargo tanks
      VesselInfo.VesselReply vesselReply =
          getSynopticalTableVesselData(request, loadableStudyOpt.get());
      // sorting the tanks based on tank display order from vessel tank table
      List<VesselInfo.VesselTankDetail> sortedTankList =
          new ArrayList<>(vesselReply.getVesselTanksList());
      Collections.sort(
          sortedTankList, Comparator.comparing(VesselInfo.VesselTankDetail::getTankDisplayOrder));
      buildSynopticalTableReply(
          requestBuilder.build(),
          synopticalTableList,
          getSynopticalTablePortDetails(synopticalTableList),
          getSynopticalTablePortRotations(loadableStudyOpt.get()),
          loadableStudyOpt.get(),
          sortedTankList,
          vesselReply.getVesselLoadableQuantityDetails(),
          replyBuilder);
      // build ballast tank details not available in synoptical
      List<VesselInfo.VesselTankDetail> ballastTankList =
          sortedTankList.stream()
              .filter(tankList -> BALLAST_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
              .collect(Collectors.toList());
      List<VesselInfo.VesselTankDetail> frontBallastTanks = new ArrayList<>();
      List<VesselInfo.VesselTankDetail> centerBallastTanks = new ArrayList<>();
      List<VesselInfo.VesselTankDetail> rearBallastTanks = new ArrayList<>();
      frontBallastTanks.addAll(
          ballastTankList.stream()
              .filter(tank -> BALLAST_FRONT_TANK.equals(tank.getTankPositionCategory()))
              .collect(Collectors.toList()));
      centerBallastTanks.addAll(
          ballastTankList.stream()
              .filter(tank -> BALLAST_CENTER_TANK.equals(tank.getTankPositionCategory()))
              .collect(Collectors.toList()));
      rearBallastTanks.addAll(
          ballastTankList.stream()
              .filter(tank -> BALLAST_REAR_TANK.equals(tank.getTankPositionCategory()))
              .collect(Collectors.toList()));
      replyBuilder.addAllBallastFrontTanks(onHandQuantityService.groupTanks(frontBallastTanks));
      replyBuilder.addAllBallastCenterTanks(onHandQuantityService.groupTanks(centerBallastTanks));
      replyBuilder.addAllBallastRearTanks(onHandQuantityService.groupTanks(rearBallastTanks));
      // build cargo layout tanks not available in synoptical
      replyBuilder.addAllCargoTanks(
          onHandQuantityService.groupTanks(
              sortedTankList.stream()
                  .filter(tankList -> CARGO_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
                  .collect(Collectors.toList())));
    }
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }
}
