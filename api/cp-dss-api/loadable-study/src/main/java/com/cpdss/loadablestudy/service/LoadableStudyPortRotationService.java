/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.CargoOperationRepository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
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
 * @author vinothkumar m @Since 07-07-2021
 */
@Slf4j
@Service
public class LoadableStudyPortRotationService {

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableQuantityRepository loadableQuantityRepository;

  @Autowired private CargoOperationRepository cargoOperationRepository;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

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

    for (LoadableStudyPortRotation portRotation : loadableStudyPortRotations) {
      if (portRotation.getOperation().getId() == 1L) {
        Integer loc = loadableStudyPortRotations.indexOf(portRotation);
        for (int index = 0; index <= loc; index++) {
          LoadableStudyPortRotation portAbove = loadableStudyPortRotations.get(index);
          if (portAbove.getOperation().getId().equals(2L)) {
            loadableStudyPortRotations.remove(portRotation);
            loadableStudyPortRotations.add(index, portRotation);
          }
        }
      }
    }

    Optional<LoadableStudyPortRotation> lastPortRotationOpt =
        loadableStudyPortRotations.stream()
            .sorted(Comparator.comparingLong(LoadableStudyPortRotation::getPortOrder).reversed())
            .findFirst();

    if (lastPortRotationOpt.isPresent()
        && !lastPortRotationOpt.get().getOperation().getId().equals(2L)) {
      Optional<LoadableStudyPortRotation> lastDischargePortOpt =
          loadableStudyPortRotations.stream()
              .filter(portRotation -> portRotation.getOperation().getId().equals(2L))
              .sorted(Comparator.comparingLong(LoadableStudyPortRotation::getPortOrder).reversed())
              .findFirst();
      if (lastDischargePortOpt.isPresent()) {
        Integer index = loadableStudyPortRotations.indexOf(lastPortRotationOpt.get());
        loadableStudyPortRotations.remove(lastDischargePortOpt.get());
        loadableStudyPortRotations.add(index, lastDischargePortOpt.get());
      }
    }

    AtomicLong newPortOrder = new AtomicLong(0);
    loadableStudyPortRotations.forEach(
        portRotation -> {
          portRotation.setPortOrder(newPortOrder.incrementAndGet());
        });

    this.loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotations);
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
    Optional<LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist in database", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }

    List<LoadableStudyPortRotation> entityList =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            loadableStudyOpt.get(), true);
    for (LoadableStudyPortRotation entity : entityList) {
      replyBuilder.addPorts(
          this.createPortDetail(
              entity,
              DateTimeFormatter.ofPattern(ETA_ETD_FORMAT),
              DateTimeFormatter.ofPattern(LAY_CAN_FORMAT)));
    }
    List<CargoOperation> operationEntityList =
        this.cargoOperationRepository.findByIsActiveOrderById(true);
    for (CargoOperation entity : operationEntityList) {
      replyBuilder.addOperations(this.createOperationDetail(entity));
    }

    // Last modified port rotation
    if (loadableStudyOpt.isPresent()) {
      Optional<LoadableQuantity> lq =
          loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
              loadableStudyOpt.get());
      if (lq.isPresent()) {
        replyBuilder.setLastModifiedPort(lq.get().getLoadableStudyPortRotation().getId());
      }
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
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
}
