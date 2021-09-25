/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.EntityDoc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.domain.VoyagePorts;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Master Service for Voyage Related Operations
 *
 * @author vinothkumar m @Since 07-07-2021 ravi.r
 */
@Slf4j
@Service
public class LoadableStudyPortRotationService {

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableQuantityRepository loadableQuantityRepository;

  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired private VoyageService voyageService;

  @Autowired private SynopticService synopticService;

  @Autowired private LoadableStudyService studyService;

  @Autowired private LoadablePatternService loadablePatternService;

  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired private BackLoadingService backLoadingService;

  @Autowired private PortInstructionService portInstructionService;
  @Autowired private CowDetailService cowDetailService;
  @Autowired private OnHandQuantityService onHandQuantityService;
  @Autowired private EntityManager entityManager;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  /**
   * Get max port order for a LS
   *
   * @param loadableStudy
   * @return
   */
  public Long findMaxPortOrderForLoadableStudy(LoadableStudy loadableStudy) {
    Long maxPortOrder = 0L;
    LoadableStudyPortRotation maxPortOrderEntity =
        this.loadableStudyPortRotationRepository
            .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(loadableStudy, true);
    if (maxPortOrderEntity != null) {
      maxPortOrder = maxPortOrderEntity.getPortOrder();
    }
    return maxPortOrder;
  }

  public void setPortOrdering(LoadableStudy loadableStudy) {
    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            loadableStudy, true);

    List<LoadableStudyPortRotation> newPortRotations = new ArrayList<>();
    for (LoadableStudyPortRotation portRotation : loadableStudyPortRotations) {
      if (portRotation.getOperation().getId() == 1L) {
        newPortRotations.add(portRotation);
        int loc = newPortRotations.indexOf(portRotation);
        for (int index = 0; index <= loc; index++) {
          LoadableStudyPortRotation portAbove = newPortRotations.get(index);
          if (portAbove.getOperation().getId().equals(2L)) {
            newPortRotations.remove(portRotation);
            newPortRotations.add(index, portRotation);
          }
        }
      } else {
        newPortRotations.add(portRotation);
      }
    }

    Optional<LoadableStudyPortRotation> lastPortRotationOpt =
        newPortRotations.stream()
            .sorted(
                Comparator.comparing(portRotation -> newPortRotations.indexOf(portRotation))
                    .reversed())
            .findFirst();

    if (lastPortRotationOpt.isPresent()
        && !lastPortRotationOpt.get().getOperation().getId().equals(2L)) {
      Optional<LoadableStudyPortRotation> lastDischargePortOpt =
          newPortRotations.stream()
              .filter(portRotation -> portRotation.getOperation().getId().equals(2L))
              .sorted(
                  Comparator.comparing(portRotation -> newPortRotations.indexOf(portRotation))
                      .reversed())
              .findFirst();
      if (lastDischargePortOpt.isPresent()) {
        Integer index = newPortRotations.indexOf(lastPortRotationOpt.get());
        newPortRotations.remove(lastDischargePortOpt.get());
        newPortRotations.add(index, lastDischargePortOpt.get());
      }
    }

    AtomicLong newPortOrder = new AtomicLong(0);
    newPortRotations.forEach(
        portRotation -> {
          portRotation.setPortOrder(newPortOrder.incrementAndGet());
        });
    this.loadableStudyPortRotationRepository.saveAll(newPortRotations);
  }

  /**
   * Fetch transit ports for the specific loadableStudy if available in port rotation so that they
   * are not added as loading ports
   *
   * @param loadableStudy
   * @param requestedPortIds
   * @throws GenericServiceException
   */
  public void validateTransitPorts(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy, List<Long> requestedPortIds)
      throws GenericServiceException {
    List<Long> transitPorts =
        this.loadableStudyPortRotationRepository.getTransitPorts(loadableStudy, requestedPortIds);
    if (!CollectionUtils.isEmpty(transitPorts)) {
      throw new GenericServiceException(
          "Ports exist as transit ports "
              + StringUtils.collectionToCommaDelimitedString(transitPorts),
          CommonErrorCodes.E_CPDSS_TRANSIT_PORT_EXISTS,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder
      getLoadableStudyPortRotation(
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder)
          throws GenericServiceException {
    Optional<LoadableStudy> studyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!studyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist in database", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    List<LoadableStudyPortRotation> entityList = new ArrayList<>();
    // Checking if request came from landing page / Operation - then fetching both DS and LS
    if (request.getIsLandingPage()) {
      // Checking if Discharge confirmed or not
      Optional<List<LoadableStudy>> dischargeStudyEntries =
          synopticService.checkDischargeStarted(request.getVesselId(), request.getVoyageId());
      if (dischargeStudyEntries.isPresent()
          && studyOpt.get().getConfirmedLoadableStudyId() != null
          && studyOpt.get().getLoadableStudyStatus().getName().equalsIgnoreCase(STATUS_CONFIRMED)) {
        // Fetching respective LS of DS
        Optional<LoadableStudy> loadableStudyOpt =
            this.loadableStudyRepository.findById(studyOpt.get().getConfirmedLoadableStudyId());
        entityList = getPortRotationList(loadableStudyOpt.get(), true, LOADING_PORT);
        // Removing first discharge port added when LS created
        entityList.removeIf(item -> item.getOperation().getId().equals(DISCHARGING_OPERATION_ID));
        // Appending all DS ports
        entityList.addAll(getPortRotationList(studyOpt.get(), true, DISCHARGE_PORT));
      } else {
        entityList = getPortRotationList(studyOpt.get(), true, LOADING_PORT);
      }
    } else {
      entityList = getPortRotationList(studyOpt.get(), true, null);
    }

    for (LoadableStudyPortRotation entity : entityList) {
      replyBuilder.addPorts(
          this.createPortDetail(
              entity,
              DateTimeFormatter.ofPattern(ETA_ETD_FORMAT),
              DateTimeFormatter.ofPattern(LAY_CAN_FORMAT)));
    }
    // Last modified port rotation
    if (studyOpt.isPresent()) {
      Optional<LoadableQuantity> lq =
          loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
              studyOpt.get());
      if (lq.isPresent()) {
        replyBuilder.setLastModifiedPort(lq.get().getLoadableStudyPortRotation().getId());
      }
    }
    List<CargoOperation> operationEntityList =
        this.cargoOperationRepository.findByIsActiveOrderById(true);
    for (CargoOperation entity : operationEntityList) {
      replyBuilder.addOperations(this.createOperationDetail(entity));
    }

    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  private List<LoadableStudyPortRotation> getPortRotationList(
      LoadableStudy loadableStudy, Boolean isActive, String portType) {
    List<LoadableStudyPortRotation> entityList =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            loadableStudy, isActive);
    if (portType != null) {
      entityList.stream()
          .filter(item -> item.getPortRotationType() == null)
          .forEach(item -> item.setPortRotationType(portType));
    }

    return entityList;
  }

  /**
   * Create {@link com.cpdss.common.generated.LoadableStudy.Operation} from {@link CargoOperation}
   *
   * @param entity - {@link CargoOperation}
   * @return
   */
  private com.cpdss.common.generated.LoadableStudy.Operation createOperationDetail(
      CargoOperation entity) {
    return com.cpdss.common.generated.LoadableStudy.Operation.newBuilder()
        .setId(entity.getId())
        .setOperationName(entity.getName())
        .build();
  }

  /**
   * Create {@link PortInfo.PortDetail} from {@link LoadableStudyPortRotation}
   *
   * @param entity {@link LoadableStudyPortRotation}
   * @return {@link PortInfo.PortDetail}
   */
  private com.cpdss.common.generated.LoadableStudy.PortRotationDetail createPortDetail(
      LoadableStudyPortRotation entity,
      DateTimeFormatter etaEtdFormatter,
      DateTimeFormatter layCanFormatter) {
    com.cpdss.common.generated.LoadableStudy.PortRotationDetail.Builder builder =
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail.newBuilder();
    builder.setId(entity.getId());
    builder.setLoadableStudyId(entity.getLoadableStudy().getId());
    ofNullable(entity.getPortXId()).ifPresent(builder::setPortId);
    ofNullable(entity.getOperation()).ifPresent(op -> builder.setOperationId(op.getId()));
    ofNullable(entity.getBerthXId()).ifPresent(builder::setBerthId);
    ofNullable(entity.getSeaWaterDensity())
        .ifPresent(density -> builder.setSeaWaterDensity(valueOf(density)));
    ofNullable(entity.getDistanceBetweenPorts())
        .ifPresent(distance -> builder.setDistanceBetweenPorts(valueOf(distance)));
    ofNullable(entity.getTimeOfStay())
        .ifPresent(timeOfStay -> builder.setTimeOfStay(valueOf(timeOfStay)));
    ofNullable(entity.getMaxDraft()).ifPresent(maxDraft -> builder.setMaxDraft(valueOf(maxDraft)));
    ofNullable(entity.getAirDraftRestriction())
        .ifPresent(airDraft -> builder.setMaxAirDraft(valueOf(airDraft)));
    ofNullable(entity.getEta()).ifPresent(eta -> builder.setEta(etaEtdFormatter.format(eta)));
    ofNullable(entity.getEtd()).ifPresent(etd -> builder.setEtd(etaEtdFormatter.format(etd)));
    ofNullable(entity.getLayCanFrom())
        .ifPresent(layCanFrom -> builder.setLayCanFrom(layCanFormatter.format(layCanFrom)));
    ofNullable(entity.getLayCanTo())
        .ifPresent(layCanTo -> builder.setLayCanTo(layCanFormatter.format(layCanTo)));
    ofNullable(entity.getPortOrder()).ifPresent(builder::setPortOrder);
    ofNullable(entity.getPortRotationType()).ifPresent(builder::setPortRotationType);
    if (entity.getPortXId() != null && entity.getPortXId() > 0) {
      this.setPortTimezoneId(entity.getPortXId(), builder);
    }
    return builder.build();
  }

  private void setPortTimezoneId(
      Long portId, com.cpdss.common.generated.LoadableStudy.PortRotationDetail.Builder builder) {
    PortInfo.PortReply reply =
        portInfoGrpcService.getPortInfoByPortIds(
            PortInfo.GetPortInfoByPortIdsRequest.newBuilder().addId(portId).build());
    if (!reply.getPortsList().isEmpty()) { // Expect single entry as response
      builder.setPortTimezoneId(reply.getPortsList().get(0).getTimezoneId());
    }
  }

  /**
   * @param loadableStudy
   * @param loading
   * @return Long - id
   */
  public Long getLastPortRotationId(LoadableStudy loadableStudy, CargoOperation loading) {
    Object[] ob = getLastPortRotationData(loadableStudy, loading, true);
    return (long) ob[1];
  }

  public Object[] getLastPortRotationData(
      LoadableStudy loadableStudy, CargoOperation loading, boolean status) {
    Object ob = loadableStudyPortRotationRepository.findLastPort(loadableStudy, loading, status);
    Object[] obA = (Object[]) ob;
    return obA;
  }

  /**
   * @param loadableStudy
   * @param loading
   * @return
   */
  public Long getLastPort(LoadableStudy loadableStudy, CargoOperation loading) {
    Object[] ob = getLastPortRotationData(loadableStudy, loading, true);
    return (long) ob[0];
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy
   * @param modelMapper void
   */
  public void buildLoadableStudyPortRotationDetails(
      long loadableStudyId,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(loadableStudyId, true);

    loadableStudy.setLoadableStudyPortRotation(new ArrayList<>());
    if (!loadableStudyPortRotations.isEmpty()) {
      loadableStudyPortRotations.forEach(
          loadableStudyPortRotation -> {
            com.cpdss.loadablestudy.domain.LoadableStudyPortRotation loadableStudyPortRotationDto =
                new com.cpdss.loadablestudy.domain.LoadableStudyPortRotation();
            loadableStudyPortRotationDto =
                modelMapper.map(
                    loadableStudyPortRotation,
                    com.cpdss.loadablestudy.domain.LoadableStudyPortRotation.class);
            loadableStudyPortRotationDto.setMaxAirDraft(
                loadableStudyPortRotation.getAirDraftRestriction());
            loadableStudy.getLoadableStudyPortRotation().add(loadableStudyPortRotationDto);
          });
    }
  }

  /**
   * @param loadableStudy
   * @param loadableStudyEntity void
   */
  public void buildportRotationDetails(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
    PortInfo.GetPortInfoByPortIdsRequest.Builder portsBuilder =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
    List<Long> portIds = this.getPortRoationPortIds(loadableStudyEntity);
    portIds.forEach(
        portId -> {
          portsBuilder.addId(portId);
        });

    loadableStudy.setPortDetails(new ArrayList<PortDetails>());
    PortInfo.PortReply portReply = getPortInfo(portsBuilder.build());
    portReply
        .getPortsList()
        .forEach(
            portList -> {
              PortDetails portDetails = new PortDetails();
              portDetails.setAverageTideHeight(portList.getAverageTideHeight());
              portDetails.setCode(portList.getCode());
              portDetails.setDensitySeaWater(portList.getWaterDensity());
              portDetails.setId(portList.getId());
              portDetails.setName(portList.getName());
              portDetails.setTideHeight(portList.getTideHeight());
              portDetails.setCountryName(portList.getCountryName());
              portDetails.setTimezoneId(portList.getTimezoneId());
              portDetails.setOffset(portList.getTimezoneOffsetVal());
              portDetails.setTimezoneAbbr(portList.getTimezoneAbbreviation());
              loadableStudy.getPortDetails().add(portDetails);
            });
  }

  public List<Long> getPortRoationPortIds(LoadableStudy loadableStudy) {
    List<Long> portIds =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            loadableStudy, true);
    return portIds.stream().distinct().collect(Collectors.toList());
  }

  public PortInfo.PortReply getPortInfo(PortInfo.GetPortInfoByPortIdsRequest build) {
    return portInfoGrpcService.getPortInfoByPortIds(build);
  }

  public com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder
      saveLoadableStudyPortRotationList(
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder)
          throws GenericServiceException {
    Optional<LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());
    // validates the input port rotation list for valid ids
    List<LoadableStudyPortRotation> existingPortRotationList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(request.getPortRotationDetailsList())) {
      for (com.cpdss.common.generated.LoadableStudy.PortRotationDetail requestPortRotation :
          request.getPortRotationDetailsList()) {
        Optional<LoadableStudyPortRotation> portRotation =
            this.loadableStudyPortRotationRepository.findById(requestPortRotation.getId());
        if (!portRotation.isPresent()) {
          throw new GenericServiceException(
              "Port rotation does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        existingPortRotationList.add(portRotation.get());
      }
    }
    createPortRotationEntityList(existingPortRotationList, request);
    if (!CollectionUtils.isEmpty(existingPortRotationList)) {
      this.loadableStudyPortRotationRepository.saveAll(existingPortRotationList);
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /*
   * builds list of port rotation entities for bulk save
   */
  private List<LoadableStudyPortRotation> createPortRotationEntityList(
      List<LoadableStudyPortRotation> existingPortRotationList,
      com.cpdss.common.generated.LoadableStudy.PortRotationRequest portRotationRequest) {
    if (!CollectionUtils.isEmpty(existingPortRotationList)
        && !CollectionUtils.isEmpty(portRotationRequest.getPortRotationDetailsList())) {
      existingPortRotationList.forEach(
          entity ->
              portRotationRequest.getPortRotationDetailsList().stream()
                  .filter(requestPort -> requestPort.getId() == entity.getId().longValue())
                  .forEach(request -> buildLoadableStudyPortRotationEntity(entity, request)));
    }
    return existingPortRotationList;
  }

  /*
   * builds single entity from the request
   */
  public void buildLoadableStudyPortRotationEntity(
      LoadableStudyPortRotation entity,
      com.cpdss.common.generated.LoadableStudy.PortRotationDetail request) {
    entity.setAirDraftRestriction(
        isEmpty(request.getMaxAirDraft()) ? null : new BigDecimal(request.getMaxAirDraft()));
    entity.setBerthXId(0 == request.getBerthId() ? null : request.getBerthId());
    entity.setPortXId(0 == request.getPortId() ? null : request.getPortId());
    entity.setDistanceBetweenPorts(
        isEmpty(request.getDistanceBetweenPorts())
            ? null
            : new BigDecimal(request.getDistanceBetweenPorts()));
    entity.setMaxDraft(
        isEmpty(request.getMaxDraft()) ? null : new BigDecimal(request.getMaxDraft()));
    entity.setSeaWaterDensity(
        isEmpty(request.getSeaWaterDensity())
            ? null
            : new BigDecimal(request.getSeaWaterDensity()));
    entity.setTimeOfStay(
        isEmpty(request.getTimeOfStay()) ? null : new BigDecimal(request.getTimeOfStay()));
    entity.setEta(
        isEmpty(request.getEta())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(request.getEta())));
    entity.setEtd(
        isEmpty(request.getEtd())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(request.getEtd())));
    entity.setLayCanFrom(
        isEmpty(request.getLayCanFrom())
            ? null
            : LocalDate.from(
                DateTimeFormatter.ofPattern(LAY_CAN_FORMAT).parse(request.getLayCanFrom())));
    entity.setLayCanTo(
        isEmpty(request.getLayCanTo())
            ? null
            : LocalDate.from(
                DateTimeFormatter.ofPattern(LAY_CAN_FORMAT).parse(request.getLayCanTo())));
    entity.setOperation(this.cargoOperationRepository.getOne(request.getOperationId()));
    entity.setPortOrder(0 == request.getPortOrder() ? null : request.getPortOrder());
    // update distance, etaActual, etdActual values in synoptical
    if (!CollectionUtils.isEmpty(entity.getSynopticalTable())) {
      entity
          .getSynopticalTable()
          .forEach(
              record -> {
                record.setDistance(
                    !StringUtils.isEmpty(request.getDistanceBetweenPorts())
                        ? new BigDecimal(request.getDistanceBetweenPorts())
                        : null);
                String operationType = SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL;
                if (TYPE_DEPARTURE.equals(request.getOperationType())) {
                  operationType = SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE;
                }
                if (record.getOperationType().equals(operationType)) {
                  if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(record.getOperationType())) {
                    record.setEtaActual(
                        isEmpty(request.getEtaActual())
                            ? null
                            : LocalDateTime.from(
                                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT)
                                    .parse(request.getEtaActual())));
                  } else {
                    record.setEtdActual(
                        isEmpty(request.getEtdActual())
                            ? null
                            : LocalDateTime.from(
                                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT)
                                    .parse(request.getEtdActual())));
                  }
                }
              });
    }
  }

  public com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder
      saveLoadableStudyPortRotation(
          com.cpdss.common.generated.LoadableStudy.PortRotationDetail request,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder)
          throws GenericServiceException {
    Optional<LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (!request.getIsLandingPage()) {
      this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());
    }
    LoadableStudyPortRotation entity = null;
    boolean portEdited = false;
    if (request.getId() == 0) {
      entity = new LoadableStudyPortRotation();
      entity.setLoadableStudy(loadableStudyOpt.get());
      // Add ports to synoptical table
      synopticService.buildPortsInfoSynopticalTable(
          entity, request.getOperationId(), request.getPortId());
    } else {
      Optional<LoadableStudyPortRotation> portRoationOpt =
          this.loadableStudyPortRotationRepository.findById(request.getId());
      if (!portRoationOpt.isPresent()) {
        throw new GenericServiceException(
            "Port rotation does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      entity = portRoationOpt.get();
      if (!entity.getPortXId().equals(request.getPortId())) {
        portEdited = true;
      }
    }
    if (!request.getIsLandingPage()) {
      loadablePatternService.isPatternGeneratedOrConfirmed(entity.getLoadableStudy());
    }
    entity =
        this.loadableStudyPortRotationRepository.save(
            this.createPortRotationEntity(entity, request));
    if (portEdited) {
      this.synopticalTableRepository.deleteByPortRotationId(entity.getId());
      this.loadableQuantityRepository.deleteByPortRotationId(entity.getId());
      this.loadableStudyPortRotationRepository.updateIsOhqCompleteByIdAndIsActiveTrue(
          entity.getId(), false);
      synopticService.buildPortsInfoSynopticalTable(
          entity, request.getOperationId(), request.getPortId());
    }
    this.loadableStudyRepository.updateLoadableStudyIsPortsComplete(
        loadableStudyOpt.get().getId(), request.getIsPortsComplete());

    // set port order after update
    // loadableStudyPortRotationService.setPortOrdering(loadableStudyOpt.get());

    replyBuilder.setPortRotationId(entity.getId());
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Create entity class from request
   *
   * @param entity
   * @param request
   * @return
   */
  private LoadableStudyPortRotation createPortRotationEntity(
      LoadableStudyPortRotation entity,
      com.cpdss.common.generated.LoadableStudy.PortRotationDetail request) {
    buildLoadableStudyPortRotationEntity(entity, request);
    return entity;
  }

  public com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder
      getPortRotationByLoadableStudyId(
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder
              portRotationReplyBuilder) {
    Optional<LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);

    if (!loadableStudy.isPresent()) {
      log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
      portRotationReplyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(INVALID_LOADABLE_STUDY_ID)
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
    } else {
      // loadable quantity logic
      if (loadableStudy.get().getConfirmedLoadableStudyId() != null) {
        List<LoadableQuantity> quantities =
            loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                loadableStudy.get().getConfirmedLoadableStudyId(), true);
        BigDecimal sum =
            quantities.stream()
                .map(LoadableQuantity::getTotalQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        portRotationReplyBuilder.setLoadableQuantity(sum != null ? sum.toString() : "0");
      }
      List<LoadableStudyPortRotation> ports =
          this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
              loadableStudy.get(), true);
      if (ports.isEmpty()) {
        log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
        portRotationReplyBuilder.setResponseStatus(
            Common.ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_STUDY_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {
        List<Long> lsprIds =
            ports.stream().map(LoadableStudyPortRotation::getId).collect(Collectors.toList());
        Map<Long, List<BackLoading>> backloadingDataByportIds =
            backLoadingService.getBackloadingDataByportIds(request.getLoadableStudyId(), lsprIds);
        Map<Long, List<DischargeStudyPortInstruction>> instructionsForThePort =
            portInstructionService.getPortWiseInstructions(request.getLoadableStudyId(), lsprIds);
        Map<Long, DischargeStudyCowDetail> cowDetails =
            cowDetailService.getCowDetailForThePort(request.getLoadableStudyId(), lsprIds);

        ports.forEach(
            port -> {
              com.cpdss.common.generated.LoadableStudy.PortRotationDetail.Builder builder =
                  com.cpdss.common.generated.LoadableStudy.PortRotationDetail.newBuilder();
              builder.setPortId(port.getPortXId());
              builder.setId(port.getId());
              builder.setMaxDraft(String.valueOf(port.getMaxDraft()));
              builder.setOperationId(port.getOperation().getId());
              builder.setSeaWaterDensity(String.valueOf(port.getSeaWaterDensity()));
              builder.setEta(String.valueOf(port.getEta()));
              builder.setEtd(String.valueOf(port.getEtd()));
              builder.setPortOrder(port.getPortOrder());

              if (port.getIsbackloadingEnabled() != null) {
                builder.setIsBackLoadingEnabled(port.getIsbackloadingEnabled());
                if (backloadingDataByportIds.get(port.getId()) != null) {
                  backloadingDataByportIds
                      .get(port.getId())
                      .forEach(
                          backLoading -> {
                            builder.addBackLoading(buildBackloading(backLoading));
                          });
                }
              }
              if (instructionsForThePort.get(port.getId()) != null) {
                builder.addAllInstructionId(
                    instructionsForThePort.get(port.getId()).stream()
                        .map(DischargeStudyPortInstruction::getPortInstructionId)
                        .collect(Collectors.toList()));
              }
              if (cowDetails.get(port.getId()) != null) {
                DischargeStudyCowDetail cow = cowDetails.get(port.getId());
                builder.setCowId(cow.getCowType());
                if (cow.getPercentage() != null) {
                  builder.setPercentage(cow.getPercentage());
                }
                if (cow.getTankIds() != null && !cow.getTankIds().isEmpty()) {
                  List<String> tanks = Arrays.asList(cow.getTankIds().split(","));
                  builder.addAllTanks(
                      tanks.stream()
                          .map(tank -> Long.parseLong(tank))
                          .collect(Collectors.toList()));
                }
              }

              portRotationReplyBuilder.addPorts(builder);
            });

        portRotationReplyBuilder
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
            .build();
      }
    }
    return portRotationReplyBuilder;
  }

  private com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading buildBackloading(
      BackLoading backLoading) {

    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.Builder
        backLoadingBuilder =
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.newBuilder();
    backLoadingBuilder.setAbbreviation(backLoading.getAbbreviation());
    backLoadingBuilder.setApi(backLoading.getApi().toString());
    backLoadingBuilder.setCargoId(backLoading.getCargoId());
    backLoadingBuilder.setColour(backLoading.getColour());
    backLoadingBuilder.setId(backLoading.getId());
    backLoadingBuilder.setQuantity(backLoading.getQuantity().toString());
    backLoadingBuilder.setTemperature(backLoading.getTemperature().toString());

    return backLoadingBuilder.build();
  }

  public com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder deletePortRotation(
      com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
      com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent() || !loadableStudyOpt.get().isActive()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());
    loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudyOpt.get());
    LoadableStudy loadableStudy = loadableStudyOpt.get();
    if (null != loadableStudy.getLoadableStudyStatus()
        && LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID.equals(
            loadableStudy.getLoadableStudyStatus().getId())) {
      throw new GenericServiceException(
          "Cannot delete ports for loadable study with status - plan generated",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<LoadableStudyPortRotation> entityOpt =
        this.loadableStudyPortRotationRepository.findById(request.getId());
    if (!entityOpt.isPresent()) {
      throw new GenericServiceException(
          "port rotation does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    LoadableStudyPortRotation entity = entityOpt.get();
    if (loadableStudy.getPlanningTypeXId() != null
        && loadableStudy.getPlanningTypeXId().equals(Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE)) {
      if (null != entity.getOperation()
          && (LOADING_OPERATION_ID.equals(entity.getOperation().getId())
              || DISCHARGING_OPERATION_ID.equals(entity.getOperation().getId()))) {
        throw new GenericServiceException(
            "Cannot delete loading/discharging ports",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    }
    entity.setActive(false);
    // delete ports from synoptical table
    if (!CollectionUtils.isEmpty(entity.getSynopticalTable())) {
      entity.getSynopticalTable().forEach(portRecord -> portRecord.setIsActive(false));
    }
    this.loadableStudyPortRotationRepository.save(entity);
    if (loadableStudy.getPlanningTypeXId() != null
        && loadableStudy.getPlanningTypeXId().equals(2)) {
      onHandQuantityService.deletePortRotationDetails(loadableStudy, entity);
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  public void getLoadableStudyShore(
      com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse.Builder builder) {

    try {
      VesselInfo.VesselRequest vesselAlgoRequest = VesselInfo.VesselRequest.newBuilder().build();
      VesselInfo.VesselReply replyBuilder =
          vesselInfoGrpcService.getAllVesselsByCompany(vesselAlgoRequest);

      replyBuilder
          .getVesselsList()
          .forEach(
              vesselDetail -> {
                com.cpdss.common.generated.LoadableStudy.LoadableStudyShore.Builder shoreBuilder =
                    com.cpdss.common.generated.LoadableStudy.LoadableStudyShore.newBuilder();
                shoreBuilder.setId(vesselDetail.getId());
                shoreBuilder.setVesselName(vesselDetail.getName());
                shoreBuilder.setImoNo(Long.parseLong(vesselDetail.getImoNumber()));
                shoreBuilder.setFlagName(vesselDetail.getFlag());

                com.cpdss.common.generated.LoadableStudy.VoyageRequest requests =
                    com.cpdss.common.generated.LoadableStudy.VoyageRequest.newBuilder()
                        .setVesselId(vesselDetail.getId())
                        .build();
                com.cpdss.common.generated.LoadableStudy.VoyageListReply.Builder builders =
                    com.cpdss.common.generated.LoadableStudy.VoyageListReply.newBuilder();
                List<com.cpdss.common.generated.LoadableStudy.VoyageDetail> de =
                    voyageService.getVoyagesByVessel(requests, builders).getVoyagesList().stream()
                        .filter(list -> list.getStatus().trim().equals("Active"))
                        .collect(Collectors.toList());
                if (de != null && !de.isEmpty()) {
                  getVoyageData(vesselDetail, shoreBuilder, de);
                }
                builder.addShoreList(shoreBuilder);
              });
    } catch (Exception e) {
      log.info("Here is my setResponseStatus :" + e.getMessage());
    }
    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  private void getVoyageData(
      VesselDetail vesselDetail,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyShore.Builder shoreBuilder,
      List<com.cpdss.common.generated.LoadableStudy.VoyageDetail> de) {
    shoreBuilder.setVoyageId(de.get(0).getId());
    // Gettting Lodablestudy detaild from Vessel
    Set<Long> distinctLodableStudyId =
        loadableStudyRepository.findByVesselXId(vesselDetail.getId()).stream()
            .filter(
                det ->
                    det.getLoadableStudyStatus() != null
                        && det.getLoadableStudyStatus().getName() != null
                        && det.getVesselXId() != null
                        && det.getLoadableStudyStatus().getName().trim().equals("Confirmed")
                        && det.getVesselXId() == vesselDetail.getId()
                        && det.getVoyage() != null
                        && det.getVoyage().getId() == de.get(0).getId())
            .map(EntityDoc::getId)
            .collect(Collectors.toSet());
    /* Voyage voy = new Voyage();
    voy.setId(de.get(0).getId());
    voy.setVesselXId(vesselDetail.getId());
    List<LoadableStudy> study = loadableStudyRepository.findByVesselXIdAndVoyageAndIsActiveAndLoadableStudyStatus_id(
            request.getVesselId(), voy, true, CONFIRMED_STATUS_ID);*/

    List<VoyagePorts> dataMap = new ArrayList<>();
    Set<Long> portId = new HashSet<>();

    // Getting all the port id from Lodable study
    distinctLodableStudyId.forEach(
        detail -> {
          List<LoadableStudyPortRotation> loadableStudyPortRotations =
              loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(detail, true);

          if (!loadableStudyPortRotations.isEmpty()) {
            loadableStudyPortRotations.forEach(
                loadableStudyPortRotation -> {
                  if (!portId.contains(loadableStudyPortRotation.getPortXId())) {

                    // Getting port name and lat and long detials
                    PortInfo.PortReply reply =
                        portInfoGrpcService.getPortInfoByPortIds(
                            PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
                                .addId(loadableStudyPortRotation.getPortXId())
                                .build());

                    dataMap.add(
                        new VoyagePorts(
                            String.valueOf(loadableStudyPortRotation.getPortXId()),
                            String.valueOf(loadableStudyPortRotation.getEta()),
                            String.valueOf(loadableStudyPortRotation.getEtd()),
                            String.valueOf(loadableStudyPortRotation.getPortOrder()),
                            String.valueOf(loadableStudyPortRotation.getOperation().getName()),
                            null,
                            null,
                            String.valueOf(
                                (loadableStudyPortRotation.getSynopticalTable() != null
                                        && loadableStudyPortRotation.getSynopticalTable().size()
                                            > 0)
                                    ? loadableStudyPortRotation
                                                .getSynopticalTable()
                                                .get(0)
                                                .getEtaActual()
                                            == null
                                        ? ""
                                        : loadableStudyPortRotation
                                            .getSynopticalTable()
                                            .get(0)
                                            .getEtaActual()
                                    : ""),
                            String.valueOf(
                                (loadableStudyPortRotation.getSynopticalTable() != null
                                        && loadableStudyPortRotation.getSynopticalTable().size()
                                            > 0)
                                    ? loadableStudyPortRotation
                                                .getSynopticalTable()
                                                .get(1)
                                                .getEtdActual()
                                            == null
                                        ? ""
                                        : loadableStudyPortRotation
                                            .getSynopticalTable()
                                            .get(1)
                                            .getEtdActual()
                                    : ""),
                            reply.getPortsCount() > 0 ? reply.getPorts(0).getLat() : "",
                            reply.getPortsCount() > 0 ? reply.getPorts(0).getLon() : "",
                            reply.getPortsCount() > 0 ? reply.getPorts(0).getName() : ""));
                  }
                  portId.add(loadableStudyPortRotation.getPortXId());
                });
          }
        });

    // Building final Map
    String vyogeName = "";
    for (int i = 0; i < dataMap.size(); i++) {
      shoreBuilder
          .addVoyagePortsBuilder()
          .setAnchorage(dataMap.get(i).getAnchorage() == null ? "" : dataMap.get(i).getAnchorage())
          .setPortName(dataMap.get(i).getPortName() == null ? "" : dataMap.get(i).getPortName())
          .setEta(dataMap.get(i).getEta() == null ? "" : dateFormat(dataMap.get(i).getEta()))
          .setEtd(dataMap.get(i).getEtd() == null ? "" : dateFormat(dataMap.get(i).getEtd()))
          .setPortType(dataMap.get(i).getPortType() == null ? "" : dataMap.get(i).getPortType())
          .setAta(dataMap.get(i).getAta() == null ? "" : dateFormat(dataMap.get(i).getAta()))
          .setAtd(dataMap.get(i).getAtd() == null ? "" : dateFormat(dataMap.get(i).getAtd()))
          .setLat(dataMap.get(i).getLat() == null ? "" : dataMap.get(i).getLat())
          .setLon(dataMap.get(i).getLon() == null ? "" : dataMap.get(i).getLon())
          .setPortOrder(dataMap.get(i).getPortOrder() == null ? "" : dataMap.get(i).getPortOrder())
          .build();
      if (i == 0) {
        shoreBuilder.setAtd(
            dataMap.get(i).getAtd() == null ? "" : dateFormat(dataMap.get(i).getAtd()));
        vyogeName = dataMap.get(i).getPortName() == null ? "" : dataMap.get(i).getPortName();
      }
      if (i == dataMap.size() - 1) {
        shoreBuilder.setEta(
            dataMap.get(i).getEta() == null ? "" : dateFormat(dataMap.get(i).getEta()));
        vyogeName =
            dataMap.get(i).getPortName() == null
                ? ""
                : vyogeName + " - " + dataMap.get(i).getPortName();
      }
    }

    shoreBuilder.setVoyageName(vyogeName);
  }

  private String dateFormat(String date) {
    if (date.length() > 0)
      return date.substring(8, 10)
          + "-"
          + date.substring(5, 7)
          + "-"
          + date.substring(0, 4)
          + " "
          + date.substring(11, 16);
    else return "";
  }

  public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply getLoadableStudyList(
      com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
    return this.loadableStudyServiceBlockingStub.findLoadableStudiesByVesselAndVoyage(request);
  }

  public void getPortRotationByPortRotationId(
      PortRotationRequest request,
      com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.Builder builder)
      throws Exception {
    LoadableStudyPortRotation portRotation =
        this.loadableStudyPortRotationRepository.findByIdAndIsActive(request.getId(), true);
    PortRotationDetail.Builder portDetailBuilder = PortRotationDetail.newBuilder();
    if (portRotation == null) {
      throw new Exception("Could not find port rotation with id " + request.getId());
    }

    Optional.ofNullable(portRotation.getPortXId()).ifPresent(portDetailBuilder::setPortId);
    Optional.ofNullable(portRotation.getOperation())
        .ifPresent(operation -> portDetailBuilder.setOperationId(operation.getId()));
    Optional.of(portRotation.getEta())
        .ifPresent(eta -> portDetailBuilder.setEta(portRotation.getEta().toString()));
    Optional.of(portRotation.getEtd())
        .ifPresent(etd -> portDetailBuilder.setEtd(portRotation.getEtd().toString()));
    builder.setPortRotationDetail(portDetailBuilder.build());
  }
}
