/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** Master Service for Loadable Plans */
@Slf4j
@Service
public class LoadablePlanService {

  @Autowired CargoNominationRepository cargoNominationRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired private SynopticService synopticService;

  @Autowired private LoadableQuantityRepository loadableQuantityRepository;

  @Autowired private LoadablePlanCommentsRepository loadablePlanCommentsRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;

  @Autowired private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @Autowired private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;

  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired LoadableStudyStatusRepository loadableStudyStatusRepository;

  @Autowired private RestTemplate restTemplate;

  @Autowired private LoadableStudyService loadableStudyService;

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private JsonDataService jsonDataService;
  @Autowired private CommunicationService communicationService;

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Value("${algo.loadablestudy.api.url}")
  private String loadableStudyUrl;

  @Value("${algo.stowage.edit.api.url}")
  private String algoUpdateUllageUrl;

  public void buildLoadablePlanQuantity(
      List<LoadablePlanQuantity> loadablePlanQuantities,
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder) {
    loadablePlanQuantities.forEach(
        lpq -> {
          LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
              LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
          Optional.ofNullable(lpq.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpq.getDifferenceColor()).ifPresent(builder::setDifferenceColor);
          Optional.ofNullable(lpq.getDifferencePercentage())
              .ifPresent(diffPercentage -> builder.setDifferencePercentage(diffPercentage));
          Optional.ofNullable(lpq.getEstimatedApi())
              .ifPresent(estimatedApi -> builder.setEstimatedAPI(String.valueOf(estimatedApi)));
          Optional.ofNullable(lpq.getCargoNominationTemperature())
              .ifPresent(
                  estimatedTemperature ->
                      builder.setEstimatedTemp(String.valueOf(estimatedTemperature)));
          Optional.ofNullable(lpq.getGrade()).ifPresent(builder::setGrade);
          Optional.ofNullable(lpq.getLoadableBbls60f()).ifPresent(builder::setLoadableBbls60F);
          Optional.ofNullable(lpq.getLoadableBblsDbs()).ifPresent(builder::setLoadableBblsdbs);
          Optional.ofNullable(lpq.getLoadableKl()).ifPresent(builder::setLoadableKL);
          Optional.ofNullable(lpq.getLoadableLt()).ifPresent(builder::setLoadableLT);
          Optional.ofNullable(lpq.getLoadableMt()).ifPresent(builder::setLoadableMT);
          Optional.ofNullable(lpq.getMaxTolerence()).ifPresent(builder::setMaxTolerence);
          Optional.ofNullable(lpq.getMinTolerence()).ifPresent(builder::setMinTolerence);
          Optional.ofNullable(lpq.getOrderBbls60f()).ifPresent(builder::setOrderBbls60F);
          Optional.ofNullable(lpq.getOrderBblsDbs()).ifPresent(builder::setOrderBblsdbs);
          Optional.ofNullable(lpq.getCargoXId()).ifPresent(builder::setCargoId);
          Optional.ofNullable(lpq.getOrderQuantity())
              .ifPresent(orderQuantity -> builder.setOrderedMT(String.valueOf(orderQuantity)));
          Optional.ofNullable(lpq.getSlopQuantity()).ifPresent(builder::setSlopQuantity);
          Optional.ofNullable(lpq.getTimeRequiredForLoading())
              .ifPresent(builder::setTimeRequiredForLoading);

          Optional.ofNullable(lpq.getCargoNominationId()).ifPresent(builder::setCargoNominationId);

          if (lpq.getCargoXId() != null) {
            log.info("Loadable Plan Quantity, Cargo Id {}", lpq.getCargoXId());
          }
          try { // collect port names - for each LQ
            this.setLoadingPortNameFromCargoOperation(lpq, builder);
          } catch (Exception e) {
            log.info("Loadable Pattern Details, Failed to fetch Port Info ", e);
          }
          replyBuilder.addLoadableQuantityCargoDetails(builder);
        });
  }

  public void buildLoadablePlanCommingleDetails(
      List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder) {
    loadablePlanCommingleDetails.forEach(
        lpcd -> {
          LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder builder =
              LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder();
          Optional.ofNullable(lpcd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpcd.getApi()).ifPresent(builder::setApi);
          Optional.ofNullable(lpcd.getCargo1Abbreviation())
              .ifPresent(builder::setCargo1Abbreviation);
          Optional.ofNullable(lpcd.getCargo1Bbls60f()).ifPresent(builder::setCargo1Bbls60F);
          Optional.ofNullable(lpcd.getCargo1BblsDbs()).ifPresent(builder::setCargo1Bblsdbs);
          Optional.ofNullable(lpcd.getCargo1Kl()).ifPresent(builder::setCargo1KL);
          Optional.ofNullable(lpcd.getCargo1Lt()).ifPresent(builder::setCargo1LT);
          Optional.ofNullable(lpcd.getCargo1Mt()).ifPresent(builder::setCargo1MT);
          Optional.ofNullable(lpcd.getCargo1Percentage()).ifPresent(builder::setCargo1Percentage);
          Optional.ofNullable(lpcd.getCargo2Abbreviation())
              .ifPresent(builder::setCargo2Abbreviation);
          Optional.ofNullable(lpcd.getCargo2Bbls60f()).ifPresent(builder::setCargo2Bbls60F);
          Optional.ofNullable(lpcd.getCargo2BblsDbs()).ifPresent(builder::setCargo2Bblsdbs);
          Optional.ofNullable(lpcd.getCargo2Kl()).ifPresent(builder::setCargo2KL);
          Optional.ofNullable(lpcd.getCargo2Lt()).ifPresent(builder::setCargo2LT);
          Optional.ofNullable(lpcd.getCargo2Mt()).ifPresent(builder::setCargo2MT);
          Optional.ofNullable(lpcd.getCargo2Percentage()).ifPresent(builder::setCargo2Percentage);
          Optional.ofNullable(lpcd.getGrade()).ifPresent(builder::setGrade);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(builder::setQuantity);
          Optional.ofNullable(lpcd.getTankName()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpcd.getTemperature()).ifPresent(builder::setTemp);
          Optional.ofNullable(lpcd.getSlopQuantity()).ifPresent(builder::setSlopQuantity);
          Optional.ofNullable(lpcd.getTankShortName()).ifPresent(builder::setTankShortName);
          replyBuilder.addLoadableQuantityCommingleCargoDetails(builder);

          com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder
              stowageBuilder =
                  com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
          Optional.ofNullable(lpcd.getId()).ifPresent(stowageBuilder::setId);
          Optional.ofNullable(lpcd.getGrade()).ifPresent(stowageBuilder::setCargoAbbreviation);
          Optional.ofNullable(lpcd.getApi()).ifPresent(stowageBuilder::setApi);
          Optional.ofNullable(lpcd.getCorrectedUllage())
              .ifPresent(stowageBuilder::setCorrectedUllage);
          Optional.ofNullable(lpcd.getCorrectionFactor())
              .ifPresent(stowageBuilder::setCorrectionFactor);
          Optional.ofNullable(lpcd.getFillingRatio()).ifPresent(stowageBuilder::setFillingRatio);

          Optional.ofNullable(lpcd.getRdgUllage()).ifPresent(stowageBuilder::setRdgUllage);
          Optional.ofNullable(lpcd.getTankName()).ifPresent(stowageBuilder::setTankName);
          Optional.ofNullable(lpcd.getTankShortName()).ifPresent(stowageBuilder::setTankShortName);
          Optional.ofNullable(lpcd.getTankId()).ifPresent(stowageBuilder::setTankId);
          Optional.ofNullable(lpcd.getTemperature()).ifPresent(stowageBuilder::setTemperature);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(stowageBuilder::setWeight);
          stowageBuilder.setIsCommingle(true);
          replyBuilder.addLoadablePlanStowageDetails(stowageBuilder);
        });
  }

  public void buildBallastGridDetails(
      List<LoadablePlanBallastDetails> loadablePlanBallastDetails,
      List<LoadablePlanStowageDetailsTemp> ballstTempList,
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder) {
    loadablePlanBallastDetails.forEach(
        lpbd -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder();
          Optional.ofNullable(lpbd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpbd.getCorrectedLevel()).ifPresent(builder::setCorrectedLevel);
          Optional.ofNullable(lpbd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
          Optional.ofNullable(lpbd.getCubicMeter()).ifPresent(builder::setCubicMeter);
          Optional.ofNullable(lpbd.getInertia()).ifPresent(builder::setInertia);
          Optional.ofNullable(lpbd.getLcg()).ifPresent(builder::setLcg);
          Optional.ofNullable(lpbd.getMetricTon()).ifPresent(builder::setMetricTon);
          Optional.ofNullable(lpbd.getPercentage()).ifPresent(builder::setPercentage);
          Optional.ofNullable(lpbd.getRdgLevel()).ifPresent(builder::setRdgLevel);
          Optional.ofNullable(lpbd.getSg()).ifPresent(builder::setSg);
          Optional.ofNullable(lpbd.getTankId()).ifPresent(builder::setTankId);
          Optional.ofNullable(lpbd.getTcg()).ifPresent(builder::setTcg);
          Optional.ofNullable(lpbd.getVcg()).ifPresent(builder::setVcg);
          Optional.ofNullable(lpbd.getTankName()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpbd.getColorCode()).ifPresent(builder::setColorCode);
          setTempBallastDetails(lpbd, ballstTempList, builder);
          replyBuilder.addLoadablePlanBallastDetails(builder);
        });
  }

  public void setTempBallastDetails(
      LoadablePlanBallastDetails lpbd,
      List<LoadablePlanStowageDetailsTemp> ballstTempList,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder) {
    Optional<LoadablePlanStowageDetailsTemp> tempOpt =
        ballstTempList.stream()
            .filter(b -> b.getLoadablePlanBallastDetails().getId().equals(lpbd.getId()))
            .findAny();
    if (tempOpt.isPresent()) {
      LoadablePlanStowageDetailsTemp temp = tempOpt.get();
      Optional.ofNullable(temp.getRdgUllage())
          .ifPresent(item -> builder.setRdgLevel(valueOf(item)));
      Optional.ofNullable(temp.getCorrectedUllage())
          .ifPresent(item -> builder.setCorrectedLevel(valueOf(item)));
      Optional.ofNullable(temp.getCorrectionFactor())
          .ifPresent(item -> builder.setCorrectionFactor(valueOf(item)));
      Optional.ofNullable(temp.getQuantity())
          .ifPresent(item -> builder.setMetricTon(valueOf(item)));
      Optional.ofNullable(temp.getFillingRatio())
          .ifPresent(item -> builder.setPercentage(valueOf(item)));
    }
  }

  boolean setLoadingPortNameFromCargoOperation(
      LoadablePlanQuantity lpQuantity, LoadableStudy.LoadableQuantityCargoDetails.Builder builder) {
    Optional<CargoNomination> cN =
        cargoNominationRepository.findByIdAndIsActive(lpQuantity.getCargoNominationId(), true);
    if (cN.isPresent()) {
      if (cN.get().getCargoXId().equals(lpQuantity.getCargoXId())) {
        Set<CargoNominationPortDetails> cnPD = cN.get().getCargoNominationPortDetails();
        if (!cnPD.isEmpty()) {
          for (CargoNominationPortDetails var1 : cnPD) {
            if (var1.getPortId() != null) {
              LoadableStudy.LoadingPortDetail.Builder a =
                  this.fetchPortNameFromPortService(var1.getPortId());
              builder.addLoadingPorts(a);
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * As we are getting duplicate Port Name, this logic not Using.
   *
   * <p>Reason for duplicate, every time vessel reach and leave from port, a record will add to
   * LPCD. So each record is consider as, arrival and departure condition. We cannot find the
   * loading ports. So, we user cargo nomination for the Find Loading ports. See above:
   * setLoadingPortNameFromCargoOperation(vl1, vl2)
   *
   * @param lpQuantity
   * @param builder
   * @return
   */
  boolean setLoadingPortForLoadableQuantityCargoDetails(
      LoadablePlanQuantity lpQuantity, LoadableStudy.LoadableQuantityCargoDetails.Builder builder) {
    LoadableStudy.LoadingPortDetail.Builder portsBuilder =
        LoadableStudy.LoadingPortDetail.newBuilder();
    if (lpQuantity == null || lpQuantity.getCargoNominationId() == null) {
      return false;
    }
    List<Long> lpPortRotationIds =
        loadablePatternCargoDetailsRepository.findAllPortRotationIdByCargoNomination(
            lpQuantity.getCargoNominationId());
    log.info("Port Rotation Ids From Loadable Plan Quantity, Size {}", lpPortRotationIds.size());
    if (!lpPortRotationIds.isEmpty()) { // all unique, because it fetch by distinct query
      for (Long id : lpPortRotationIds) {
        LoadableStudyPortRotation lsPR =
            loadableStudyPortRotationRepository.findByIdAndIsActive(id, true);
        if (lsPR != null) {
          if (lsPR.getOperation().getId().equals(LOADING_OPERATION_ID)) {
            LoadableStudy.LoadingPortDetail.Builder a =
                this.fetchPortNameFromPortService(lsPR.getPortXId());
            builder.addLoadingPorts(a);
          }
        }
      }
    }
    return false;
  }

  public LoadableStudy.LoadingPortDetail.Builder fetchPortNameFromPortService(Long portId) {
    PortInfo.GetPortInfoByPortIdsRequest.Builder builder =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
    builder.addAllId(Arrays.asList(portId));
    PortInfo.PortReply grpcReplay = portInfoGrpcService.getPortInfoByPortIds(builder.build());
    if (grpcReplay.getResponseStatus().getStatus().equals(SUCCESS)) {
      Optional<PortInfo.PortDetail> portInfo = grpcReplay.getPortsList().stream().findFirst();
      if (portInfo.isPresent()) {
        LoadableStudy.LoadingPortDetail.Builder portsBuilder =
            LoadableStudy.LoadingPortDetail.newBuilder();
        log.info("Port Info Service, port id {} and name {}", portId, portInfo.get().getName());
        portsBuilder.setName(portInfo.get().getName()).setPortId(portInfo.get().getId()).build();
        return portsBuilder;
      }
    }
    return null;
  }

  /**
   * @param loadablePattern
   * @param loadabalePatternValidateRequest void
   */
  public void buildLoadablePlanPortWiseDetails(
      LoadablePattern loadablePattern,
      LoadabalePatternValidateRequest loadabalePatternValidateRequest) {
    List<LoadableStudyPortRotation> entityList =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            loadablePattern.getLoadableStudy(), true);
    Long lastLoadingRotationId =
        loadableStudyPortRotationService.getLastPortRotationId(
            loadablePattern.getLoadableStudy(),
            this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));

    Long lastLoadingPortId =
        loadableStudyPortRotationService.getLastPort(
            loadablePattern.getLoadableStudy(),
            this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));

    PortInfo.GetPortInfoByPortIdsRequest.Builder reqBuilder =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
    entityList.stream()
        .map(LoadableStudyPortRotation::getPortXId)
        .collect(Collectors.toList())
        .forEach(
            port -> {
              reqBuilder.addId(port);
            });
    PortInfo.PortReply portReply = portInfoGrpcService.getPortInfoByPortIds(reqBuilder.build());

    List<com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails =
        new ArrayList<LoadablePlanPortWiseDetails>();
    entityList.stream()
        .filter(portRotation -> !portRotation.getId().equals(lastLoadingRotationId))
        .collect(Collectors.toList())
        .forEach(
            portRotate -> {
              com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails portWiseDetails =
                  new com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails();
              portWiseDetails.setPortId(portRotate.getPortXId());
              portWiseDetails.setPortRotationId(portRotate.getId());
              portWiseDetails.setPortCode(
                  portReply.getPortsList().stream()
                      .filter(
                          portDetail -> Objects.equals(portRotate.getPortXId(), portDetail.getId()))
                      .findAny()
                      .get()
                      .getCode());
              LoadabalePatternDetails arrivalCondition = new LoadabalePatternDetails();
              LoadabalePatternDetails departureCondition = new LoadabalePatternDetails();

              arrivalCondition.setLoadablePlanStowageDetails(
                  addLoadablePatternsStowageDetails(
                      loadablePatternCargoDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                              true),
                      false,
                      loadablePattern.getId()));
              departureCondition.setLoadablePlanStowageDetails(
                  addLoadablePatternsStowageDetails(
                      loadablePatternCargoDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                              true),
                      false,
                      loadablePattern.getId()));

              arrivalCondition.setLoadablePlanBallastDetails(
                  addLoadablePlanBallastDetails(
                      loadablePlanStowageBallastDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                              true),
                      false,
                      loadablePattern.getId()));
              departureCondition.setLoadablePlanBallastDetails(
                  addLoadablePlanBallastDetails(
                      loadablePlanStowageBallastDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                              true),
                      false,
                      loadablePattern.getId()));

              arrivalCondition.setLoadablePlanCommingleDetails(
                  addLoadablePlanCommingleDetails(
                      loadablePlanCommingleDetailsPortwiseRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                              true),
                      false,
                      loadablePattern.getId()));

              departureCondition.setLoadablePlanCommingleDetails(
                  addLoadablePlanCommingleDetails(
                      loadablePlanCommingleDetailsPortwiseRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                              true),
                      false,
                      loadablePattern.getId()));

              portWiseDetails.setArrivalCondition(arrivalCondition);
              portWiseDetails.setDepartureCondition(departureCondition);
              loadablePlanPortWiseDetails.add(portWiseDetails);
            });

    com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails portWiseDetails =
        new com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails();

    portWiseDetails.setPortId(lastLoadingPortId);
    portWiseDetails.setPortRotationId(lastLoadingRotationId);
    portWiseDetails.setPortCode(
        portReply.getPortsList().stream()
            .filter(portDetail -> Objects.equals(lastLoadingPortId, portDetail.getId()))
            .findAny()
            .get()
            .getCode());
    LoadabalePatternDetails arrivalCondition = new LoadabalePatternDetails();
    LoadabalePatternDetails departureCondition = new LoadabalePatternDetails();

    arrivalCondition.setLoadablePlanStowageDetails(
        addLoadablePatternsStowageDetails(
            loadablePatternCargoDetailsRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    loadablePattern.getId(),
                    lastLoadingRotationId,
                    SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                    true),
            false,
            loadablePattern.getId()));
    departureCondition.setLoadablePlanStowageDetails(
        addLoadablePatternsStowageDetails(
            loadablePatternCargoDetailsRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    loadablePattern.getId(),
                    lastLoadingRotationId,
                    SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                    true),
            true,
            loadablePattern.getId()));
    arrivalCondition.setLoadablePlanBallastDetails(
        addLoadablePlanBallastDetails(
            loadablePlanStowageBallastDetailsRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    loadablePattern.getId(),
                    lastLoadingRotationId,
                    SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                    true),
            false,
            loadablePattern.getId()));
    departureCondition.setLoadablePlanBallastDetails(
        addLoadablePlanBallastDetails(
            loadablePlanStowageBallastDetailsRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    loadablePattern.getId(),
                    lastLoadingRotationId,
                    SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                    true),
            true,
            loadablePattern.getId()));

    arrivalCondition.setLoadablePlanCommingleDetails(
        addLoadablePlanCommingleDetails(
            loadablePlanCommingleDetailsPortwiseRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    loadablePattern.getId(),
                    lastLoadingRotationId,
                    SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                    true),
            false,
            loadablePattern.getId()));
    departureCondition.setLoadablePlanCommingleDetails(
        addLoadablePlanCommingleDetails(
            loadablePlanCommingleDetailsPortwiseRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    loadablePattern.getId(),
                    lastLoadingRotationId,
                    SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                    true),
            true,
            loadablePattern.getId()));

    portWiseDetails.setArrivalCondition(arrivalCondition);
    portWiseDetails.setDepartureCondition(departureCondition);
    loadablePlanPortWiseDetails.add(portWiseDetails);

    loadabalePatternValidateRequest.setLoadablePlanPortWiseDetails(loadablePlanPortWiseDetails);
  }

  /**
   * @param isLastPortDeparture
   * @param loadablePatternCargoDetails
   * @return List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails>
   */
  private List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails>
      addLoadablePatternsStowageDetails(
          List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
              loadablePatternCargoDetails,
          Boolean isLastPortDeparture,
          Long loadablePatternId) {
    List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails> stowageDetails =
        new ArrayList<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails>();
    if (isLastPortDeparture) {
      stowageDetailsTempRepository
          .findByLoadablePlanStowageTempDetailsAndIsActive(loadablePatternId, true)
          .forEach(
              lpsd -> {
                com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails details =
                    new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();
                Object[] obA = (Object[]) lpsd;
                details.setId((Long) obA[0]);
                details.setCargoNominationId((Long) obA[1]);
                Optional<CargoNomination> cargoNomOpt =
                    this.cargoNominationRepository.findByIdAndIsActive(
                        details.getCargoNominationId(), true);
                cargoNomOpt.ifPresent(
                    cargoNomination -> details.setCargoId(cargoNomination.getCargoXId()));
                details.setTankId((Long) obA[2]);
                details.setQuantityMT(String.valueOf(obA[3]));
                Optional.ofNullable(obA[4])
                    .ifPresent(colorCode -> details.setColorCode(String.valueOf(colorCode)));
                Optional.ofNullable(obA[5])
                    .ifPresent(abbr -> details.setAbbreviation(String.valueOf(abbr)));
                stowageDetails.add(details);
              });
    } else {

      loadablePatternCargoDetails.forEach(
          lpsd -> {
            com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails details =
                new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();
            details.setId(lpsd.getId());
            details.setCargoNominationId(lpsd.getCargoNominationId());
            details.setTankId(lpsd.getTankId());
            details.setQuantityMT(String.valueOf(lpsd.getPlannedQuantity()));
            details.setColorCode(lpsd.getColorCode());
            details.setAbbreviation(lpsd.getAbbreviation());
            Optional<CargoNomination> cargoNomOpt =
                this.cargoNominationRepository.findByIdAndIsActive(
                    details.getCargoNominationId(), true);
            cargoNomOpt.ifPresent(
                cargoNomination -> details.setCargoId(cargoNomination.getCargoXId()));
            stowageDetails.add(details);
          });
    }
    return stowageDetails;
  }

  /**
   * @param isLastPortDeparture
   * @param loadablePatternId
   * @return List<com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails>
   */
  private List<com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails>
      addLoadablePlanBallastDetails(
          List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetails,
          Boolean isLastPortDeparture,
          Long loadablePatternId) {
    List<com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails> ballastDetails =
        new ArrayList<com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails>();

    if (isLastPortDeparture) {
      stowageDetailsTempRepository
          .findByLoadablePlanBallastTempDetailsAndIsActive(loadablePatternId, true)
          .forEach(
              lpsd -> {
                com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails details =
                    new com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails();
                Object[] obA = (Object[]) lpsd;
                details.setId((Long) obA[0]);
                details.setTankId((Long) obA[1]);
                details.setQuantityMT(String.valueOf(obA[2]));
                Optional.ofNullable(obA[3])
                    .ifPresent(colorCode -> details.setColorCode(String.valueOf(colorCode)));
                Optional.ofNullable(obA[4]).ifPresent(sg -> details.setSg(String.valueOf(sg)));
                ballastDetails.add(details);
              });
    } else {
      loadablePlanStowageBallastDetails.forEach(
          lpsd -> {
            com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails details =
                new com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails();

            details.setId(lpsd.getId());
            details.setQuantityMT(String.valueOf(lpsd.getQuantity()));
            details.setTankId(lpsd.getTankXId());
            details.setColorCode(lpsd.getColorCode());
            details.setSg(lpsd.getSg());
            ballastDetails.add(details);
          });
    }
    return ballastDetails;
  }

  /**
   * @param loadablePatternCommingleDetails
   * @param isLastPortDeparture
   * @param loadablePatternId
   * @return
   */
  private List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails>
      addLoadablePlanCommingleDetails(
          List<LoadablePlanComminglePortwiseDetails> loadablePatternCommingleDetails,
          boolean isLastPortDeparture,
          Long loadablePatternId) {
    List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails> stowageDetails =
        new ArrayList<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails>();

    // commenting the below code since commingle is not editing for last loading port as of now
    /*
     * if (isLastPortDeparture) { stowageDetailsTempRepository
     * .findByLoadablePlanCommingleTempDetailsAndIsActive(loadablePatternId, true)
     * .forEach( lpsd -> { com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails
     * details = new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();
     * Object[] obA = (Object[]) lpsd; details.setId((Long) obA[0]); //
     * details.setCargoNominationId((Long) obA[1]); details.setTankId((Long)
     * obA[1]); details.setQuantityMT(String.valueOf(obA[2]));
     * stowageDetails.add(details); }); } else {
     */

    loadablePatternCommingleDetails.forEach(
        lpsd -> {
          com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails details =
              new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();
          details.setId(lpsd.getId());
          // details.setCargoNominationId(lpsd.getCargoNominationId());
          details.setTankId(lpsd.getTankId());
          details.setQuantityMT(String.valueOf(lpsd.getQuantity()));
          details.setCargo1QuantityMT(lpsd.getCargo1Mt());
          details.setCargo2QuantityMT(lpsd.getCargo2Mt());
          details.setCargo1NominationId(lpsd.getCargo1NominationId());
          details.setCargo2NominationId(lpsd.getCargo2NominationId());
          stowageDetails.add(details);
        });
    // }
    return stowageDetails;
  }

  public void getLoadablePlanReport(
      XSSFWorkbook workbook,
      LoadableStudy.LoadablePlanReportRequest request,
      LoadableStudy.LoadablePlanReportReply.Builder dataChunkBuilder)
      throws IOException, GenericServiceException {
    XSSFSheet spreadsheet = workbook.createSheet(LOADABLE_PLAN_REPORT_BEFORE_LOADING_SHEET_NAME);
    spreadsheet.setDefaultColumnWidth(LOADABLE_PLAN_REPORT_DEFAULT_COLUMN_WIDTH);

    //    Create vesselPlanTable
    VesselPlanTable vesselPlanTable =
        buildVesselPlanTableData(request.getVesselId(), request.getLoadablePatternId());
    SheetCoordinates vesselPlanTableCoordinates =
        drawVesselPlanTable(
            spreadsheet,
            vesselPlanTable,
            LOADABLE_PLAN_REPORT_START_ROW,
            LOADABLE_PLAN_REPORT_START_COLUMN);

    //    Create cargoDetailsTable
    CargoDetailsTable cargoDetailsTable =
        buildCargoDetailsTable(request.getLoadableStudyId(), request.getLoadablePatternId());
    SheetCoordinates cargoDetailsTableCoordinates =
        drawCargoDetailsTable(
            spreadsheet,
            cargoDetailsTable,
            vesselPlanTableCoordinates.getRow() + LOADABLE_PLAN_REPORT_TABLE_SPACER,
            LOADABLE_PLAN_REPORT_START_COLUMN);

    //    Create port operations table
    PortOperationTable portOperationTable =
        synopticService.buildPortOperationsTable(
            request.getLoadableStudyId(), request.getLoadablePatternId());
    drawPortOperationTable(
        spreadsheet,
        portOperationTable,
        cargoDetailsTableCoordinates.getRow() + LOADABLE_PLAN_REPORT_TABLE_SPACER,
        LOADABLE_PLAN_REPORT_START_COLUMN);

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    workbook.write(byteArrayOutputStream);

    byte[] bytes = byteArrayOutputStream.toByteArray();
    dataChunkBuilder
        .setData(ByteString.copyFrom(bytes))
        .setSize(bytes.length)
        .setResponseStatus(
            LoadableStudy.StatusReply.newBuilder()
                .setStatus(SUCCESS)
                .setCode(HttpStatusCode.OK.getReasonPhrase())
                .build())
        .build();

    byteArrayOutputStream.close();
  }

  /**
   * Method to set merged style for cells
   *
   * @param xssfSheet XSSFSheet spreadsheet object
   * @param cellBorder CellBorder type
   * @param cellAddresses Cell address range
   * @param style XSSFCellStyle object
   */
  private void setMergedStyle(
      XSSFSheet xssfSheet,
      CellBorder cellBorder,
      CellRangeAddress cellAddresses,
      XSSFCellStyle style) {
    switch (cellBorder) {
      case CLOSED:
        RegionUtil.setBorderTop(style.getBorderTop(), cellAddresses, xssfSheet);
        RegionUtil.setBorderBottom(style.getBorderBottom(), cellAddresses, xssfSheet);
        RegionUtil.setBorderLeft(style.getBorderLeft(), cellAddresses, xssfSheet);
        RegionUtil.setBorderRight(style.getBorderRight(), cellAddresses, xssfSheet);
        break;
      case OPEN_TOP:
        RegionUtil.setBorderBottom(style.getBorderBottom(), cellAddresses, xssfSheet);
        RegionUtil.setBorderLeft(style.getBorderLeft(), cellAddresses, xssfSheet);
        RegionUtil.setBorderRight(style.getBorderRight(), cellAddresses, xssfSheet);
        break;
      case OPEN_BOTTOM:
        RegionUtil.setBorderTop(style.getBorderTop(), cellAddresses, xssfSheet);
        RegionUtil.setBorderLeft(style.getBorderLeft(), cellAddresses, xssfSheet);
        RegionUtil.setBorderRight(style.getBorderRight(), cellAddresses, xssfSheet);
        break;
      case OPEN_TOP_AND_BOTTOM:
        RegionUtil.setBorderLeft(style.getBorderLeft(), cellAddresses, xssfSheet);
        RegionUtil.setBorderRight(style.getBorderRight(), cellAddresses, xssfSheet);
        break;
      case OPEN:
        break;
    }
  }

  /**
   * Method to convert other units to Bbls
   *
   * @param value value to be converted
   * @param api api value
   * @param temperature temperature value
   * @param conversionUnit Unit in which the value is provided
   * @return Bbls value
   */
  public float convertToBbls(
      float value, float api, float temperature, ConversionUnit conversionUnit) {
    float conversionConstant = getConversionConstant(conversionUnit, api, temperature);
    switch (conversionUnit) {
      case OBSBBLS:
        return value * conversionConstant;
      case MT:
      case KL15C:
      case LT:
        return value / conversionConstant;
      default:
        throw new IllegalStateException("Unexpected value: " + conversionUnit);
    }
  }

  /**
   * Method to get the conversion constant for conversions
   *
   * @param conversionUnit conversion unit value
   * @param api api value
   * @param temperature temperature value
   * @return conversion constant value
   */
  public float getConversionConstant(ConversionUnit conversionUnit, float api, float temperature) {
    switch (conversionUnit) {
      case MT:
        return (float) (((535.1911 / (api + 131.5)) - 0.0046189) * 0.42) / 10;
      case OBSBBLS:
        return (float)
            Math.exp(
                -(341.0957 / Math.pow((141360.198 / (api + 131.5)), 2))
                    * (temperature - 60)
                    * (1
                        + (0.8
                            * (341.0957 / Math.pow((141360.198 / (api + 131.5)), 2))
                            * (temperature - 60))));
      case KL15C:
        return (float) 0.15899;
      case LT:
        return (float) ((float) ((589.943 / (api + 131.5)) - 0.0050789) * 0.0375);
      default:
        throw new IllegalStateException("Unexpected value: " + conversionUnit);
    }
  }

  /**
   * Method to get cell style
   *
   * @param spreadsheet XSSFSheet spreadsheet object
   * @param tableCellStyle table cell style value
   * @param backgroundColor optional color for cell
   * @param dataFormat optional data format for cell
   * @return XSSFCellStyle object
   */
  private XSSFCellStyle getCellStyle(
      XSSFSheet spreadsheet,
      TableCellStyle tableCellStyle,
      Optional<Color> backgroundColor,
      Optional<String> dataFormat) {
    XSSFWorkbook workbook = spreadsheet.getWorkbook();
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    DataFormat format = workbook.createDataFormat();

    //    Set default font style
    XSSFFont font = workbook.createFont();
    font.setFontName(LOADABLE_PLAN_REPORT_DEFAULT_FONT);
    font.setFontHeight(LOADABLE_PLAN_REPORT_DEFAULT_FONT_HEIGHT);

    //    Set default cell background color
    Color bgColor = backgroundColor.orElse(Color.WHITE);
    cellStyle.setFillForegroundColor(new XSSFColor(bgColor, new DefaultIndexedColorMap()));
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    switch (tableCellStyle) {
      case STOWAGE_PLAN_TITLE:
        font.setBold(true);
        font.setFontHeight(20);
        break;
      case FPT_CELL_STYLE:
        setBorderStyle(cellStyle, CellBorder.OPEN);
        break;
      case VESSEL_TANK_DESCRIPTION:
        break;
      case VESSEL_TANK_TANK_NO:
      case VESSEL_TANK_CARGO_CODE:
      case VESSEL_TANK_DETAILS:
        setBorderStyle(cellStyle, CellBorder.CLOSED);
        break;
      case VESSEL_TANK_ULLAGE:
        setBorderStyle(cellStyle, CellBorder.OPEN_BOTTOM);
        break;
      case VESSEL_TANK_LOADED_PERCENTAGE:
      case VESSEL_TANK_SHIPS_NBBLS:
      case VESSEL_TANK_SHIPS_MT:
        setBorderStyle(cellStyle, CellBorder.OPEN_TOP_AND_BOTTOM);
        break;
      case VESSEL_TANK_SHIPS_KL_15C:
        setBorderStyle(cellStyle, CellBorder.OPEN_TOP);
        break;
      case CARGO_TITLES:
      case CARGO_CARGO_CODE:
      case CARGO_LOADING_PORT:
      case CARGO_API:
      case CARGO_TEMP:
      case CARGO_CARGO_NOMINATION:
      case CARGO_TOLERANCE:
      case CARGO_NBBLS:
      case CARGO_MT:
      case CARGO_KL15C:
      case CARGO_LT:
      case CARGO_DIFF_BBLS:
      case CARGO_DIFF_DIFF_PERCENTAGE:
      case CARGO_TOTAL:
      case CLOSED_CELL_STYLE:
      case PORT_OPERATIONS_TITLES:
        font.setFontHeight(10);
        setBorderStyle(cellStyle, CellBorder.CLOSED);
        break;
      case PORT_OPERATIONS_VALUES:
        setBorderStyle(cellStyle, CellBorder.CLOSED);
        break;
    }
    //    Set font color based on background color
    font.setColor(new XSSFColor(getContrastColor(bgColor), new DefaultIndexedColorMap()));

    //    Set value
    dataFormat.ifPresent(df -> cellStyle.setDataFormat(format.getFormat(df)));
    cellStyle.setFont(font);
    cellStyle.setWrapText(true);
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    return cellStyle;
  }

  /**
   * Method to set border style for cells
   *
   * @param xssfCellStyle XSSFCellStyle object
   * @param cellBorder Cell border type
   */
  private void setBorderStyle(XSSFCellStyle xssfCellStyle, CellBorder cellBorder) {

    switch (cellBorder) {
      case CLOSED:
        xssfCellStyle.setBorderTop(BorderStyle.THIN);
        xssfCellStyle.setBorderBottom(BorderStyle.THIN);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        break;
      case OPEN_TOP:
        xssfCellStyle.setBorderTop(BorderStyle.NONE);
        xssfCellStyle.setBorderBottom(BorderStyle.THIN);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        break;
      case OPEN_BOTTOM:
        xssfCellStyle.setBorderTop(BorderStyle.THIN);
        xssfCellStyle.setBorderBottom(BorderStyle.NONE);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        break;
      case OPEN_TOP_AND_BOTTOM:
        xssfCellStyle.setBorderTop(BorderStyle.NONE);
        xssfCellStyle.setBorderBottom(BorderStyle.NONE);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        break;
      case OPEN:
        xssfCellStyle.setBorderTop(BorderStyle.NONE);
        xssfCellStyle.setBorderBottom(BorderStyle.NONE);
        xssfCellStyle.setBorderLeft(BorderStyle.NONE);
        xssfCellStyle.setBorderRight(BorderStyle.NONE);
        break;
    }
  }

  /**
   * Method to build VesselPlanTable data
   *
   * @param vesselId vesselId value
   * @param loadablePatternId loadablePatternId value
   * @return VesselPlanTable object
   */
  public VesselPlanTable buildVesselPlanTableData(long vesselId, long loadablePatternId)
      throws GenericServiceException {

    //    Get vessel details
    VesselInfo.VesselRequest vesselRequest =
        VesselInfo.VesselRequest.newBuilder().setVesselId(vesselId).build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailByVesselId(vesselRequest);

    VesselInfo.VesselDetail vesselDetail =
        vesselReply.getVesselsList().stream()
            .findFirst()
            .orElseThrow(
                () ->
                    new GenericServiceException(
                        String.format(
                            "Vessel details not found for VesselId: %d, LoadablePatterId: %d",
                            vesselId, loadablePatternId),
                        CommonErrorCodes.E_HTTP_BAD_REQUEST,
                        HttpStatusCode.BAD_REQUEST));

    //    Get loadable pattern details
    Optional<LoadablePattern> loadablePatternDetails =
        this.loadablePatternRepository.findByIdAndIsActive(loadablePatternId, true);

    //    Get loadable plan details
    LoadableStudy.LoadablePlanDetailsReply.Builder loadablePlanDetailsBuilder =
        LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    buildLoadablePlanDetails(loadablePatternDetails, loadablePlanDetailsBuilder);
    LoadableStudy.LoadablePlanDetailsReply loadablePlanDetails = loadablePlanDetailsBuilder.build();

    //    Get vessel tank details
    List<VesselInfo.VesselTankDetail> vesselTankDetailList =
        vesselReply.getVesselTanksList().stream()
            .filter(vessel -> CARGO_TANK_CATEGORY_ID == vessel.getTankCategoryId())
            .sorted(Comparator.comparing(VesselInfo.VesselTankDetail::getFrameNumberFrom))
            .collect(Collectors.toList());

    //    Get stowage plan details
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails>
        loadablePlanStowageDetails = loadablePlanDetails.getLoadablePlanStowageDetailsList();

    List<VesselTanksTable> vesselTanksTableList = new ArrayList<>();
    List<Float> frameFromCells = new ArrayList<>();
    List<Float> frameToCells = new ArrayList<>();

    vesselTankDetailList.forEach(
        vesselTankDetail -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails stowageDetails =
              loadablePlanStowageDetails.stream()
                  .filter(
                      stowageDetail -> vesselTankDetail.getTankId() == stowageDetail.getTankId())
                  .findFirst()
                  .orElse(null);

          VesselTanksTable.VesselTanksTableBuilder vesselTanksTableBuilder =
              VesselTanksTable.builder();

          if (null != stowageDetails) {

            //          Build VesselTanksTable
            float obsBbsValue =
                convertToBbls(
                    Float.parseFloat(stowageDetails.getWeight()),
                    Float.parseFloat(stowageDetails.getApi()),
                    stowageDetails.getTemperature().isEmpty()
                        ? 0
                        : Float.parseFloat(stowageDetails.getTemperature()),
                    ConversionUnit.MT);
            float klValue = convertFromBbls(obsBbsValue, 0F, 0F, ConversionUnit.KL15C);
            float fillingPercentage = Float.parseFloat(stowageDetails.getFillingRatioOrginal());
            // TODO Remove check if not necessary
            String colorCode =
                stowageDetails.getColorCode().isEmpty()
                    ? WHITE_COLOR_CODE
                    : stowageDetails.getColorCode();
            vesselTanksTableBuilder
                .colorCode(
                    stowageDetails.getIsCommingle() ? COMMINGLE_DEFAULT_COLOR_CODE : colorCode)
                .cargoCode(stowageDetails.getCargoAbbreviation())
                // TODO ullage for commingle is empty check and set value
                .ullage(
                    Double.parseDouble(
                        stowageDetails.getRdgUllage().isEmpty()
                            ? "0.0"
                            : stowageDetails.getRdgUllage()))
                .loadedPercentage((Math.round(fillingPercentage * 100.0) / 100.0))
                .shipsNBbls(Double.parseDouble(Float.toString(obsBbsValue)))
                .shipsMt(Double.parseDouble(stowageDetails.getWeight()))
                .shipsKlAt15C(Double.parseDouble(Float.toString(klValue)));
          } else {
            //            Set default color to white if no stowage details found
            vesselTanksTableBuilder.colorCode(WHITE_COLOR_CODE);
          }
          if (!frameFromCells.contains(Float.parseFloat(vesselTankDetail.getFrameNumberFrom()))) {
            //            TODO Check padding
            frameFromCells.add(-10F);
            frameFromCells.add(Float.parseFloat(vesselTankDetail.getFrameNumberFrom()));
          }
          if (!frameToCells.contains(Float.parseFloat(vesselTankDetail.getFrameNumberTo()))) {
            frameToCells.add(-10F);
            frameToCells.add(Float.parseFloat(vesselTankDetail.getFrameNumberTo()));
          }

          vesselTanksTableBuilder
              .frameNoFrom(Float.parseFloat(vesselTankDetail.getFrameNumberFrom()))
              .frameNoTo(Float.parseFloat(vesselTankDetail.getFrameNumberTo()))
              .tankNo(vesselTankDetail.getShortName());

          vesselTanksTableList.add(vesselTanksTableBuilder.build());
        });

    return VesselPlanTable.builder()
        .vesselName(vesselDetail.getName())
        .voyageNo(loadablePlanDetails.getVoyageNumber())
        .date(loadablePlanDetails.getDate())
        .frameFromCellsList(frameFromCells)
        .frameToCellsList(frameToCells)
        .vesselTanksTableList(vesselTanksTableList)
        .build();
  }

  /**
   * Method to draw vessel plan table
   *
   * @param spreadsheet XSSFSheet spreadsheet object
   * @param vesselPlanTable VesselPlanTable object
   * @param startRow Table start row
   * @param starColumn Table start column
   * @return SheetCoordinates with final row and column numbers
   */
  public SheetCoordinates drawVesselPlanTable(
      XSSFSheet spreadsheet, VesselPlanTable vesselPlanTable, int startRow, int starColumn) {
    //    Set value for iterable values
    int rowNo = startRow;
    int columnNo = starColumn;

    //    Set title rows >> START
    XSSFRow titleRow = spreadsheet.createRow(rowNo);

    for (VesselPlanTableTitles vesselPlanTableTitle : VesselPlanTableTitles.values()) {

      XSSFCell titleCell = titleRow.createCell(columnNo);
      titleCell.setCellStyle(
          getCellStyle(
              spreadsheet, TableCellStyle.STOWAGE_PLAN_TITLE, Optional.empty(), Optional.empty()));

      //      Set values
      switch (vesselPlanTableTitle) {
        case STOWAGE_PLAN:
          titleCell.setCellValue(vesselPlanTableTitle.getColumnName());
          break;
        case VESSEL_NAME:
          titleCell.setCellValue(
              vesselPlanTableTitle.getColumnName() + vesselPlanTable.getVesselName());
          break;
        case VOYAGE_NO:
          titleCell.setCellValue(
              vesselPlanTableTitle.getColumnName() + vesselPlanTable.getVoyageNo());
          break;
        case DATE:
          titleCell.setCellValue(vesselPlanTableTitle.getColumnName() + vesselPlanTable.getDate());
          break;
      }

      //      Merge columns
      spreadsheet.addMergedRegion(
          new CellRangeAddress(
              rowNo, rowNo, columnNo, columnNo + LOADABLE_PLAN_REPORT_TITLE_WIDTH - 1));
      columnNo += LOADABLE_PLAN_REPORT_TITLE_WIDTH;
    }

    //    Add table spacer
    rowNo += LOADABLE_PLAN_REPORT_TABLE_SPACER;
    //    Reset columnNo
    columnNo = starColumn;

    //    Set title rows >> END

    //    Add vessel tanks >> START
    int vesselTanksTableStartRow = rowNo;

    //    TODO get dynamic row endings
    List<String> rowEndings = Arrays.asList("P", "C", "S");
    int midRowIndex = rowEndings.size() / 2;
    String midRow = rowEndings.get(midRowIndex);

    for (String rowEnding : rowEndings) {
      for (StowagePlanTableTitles stowagePlanTableTitle : StowagePlanTableTitles.values()) {
        XSSFRow stowagePlanDetailsRow = spreadsheet.createRow(rowNo);

        //        Add FPT value to Ship's head
        if (stowagePlanTableTitle.equals(Arrays.asList(StowagePlanTableTitles.values()).get(0))
            && rowEnding.equals(rowEndings.get(0))) {
          XSSFCell fptCell =
              stowagePlanDetailsRow.createCell(vesselPlanTable.getFrameFromCellsList().size() + 1);
          fptCell.setCellValue(LOADABLE_PLAN_REPORT_FPT_VALUE);
          fptCell.setCellStyle(
              getCellStyle(
                  spreadsheet, TableCellStyle.FPT_CELL_STYLE, Optional.empty(), Optional.empty()));
        }

        for (VesselTanksTable vesselTankDetail : vesselPlanTable.getVesselTanksTableList()) {
          if (rowEnding.charAt(0)
              == vesselTankDetail.getTankNo().charAt(vesselTankDetail.getTankNo().length() - 1)) {

            //            Add tank description column values
            if (rowEnding.charAt(0) == midRow.charAt(0)) {
              XSSFCell descriptionCell = stowagePlanDetailsRow.createCell(starColumn - 1);
              descriptionCell.setCellValue(stowagePlanTableTitle.getColumnName());
              descriptionCell.setCellStyle(
                  getCellStyle(
                      spreadsheet,
                      TableCellStyle.VESSEL_TANK_DESCRIPTION,
                      Optional.empty(),
                      Optional.empty()));
            }

            //            TODO Change merge logic
            //            Merge cells
            CellRangeAddress mergeRange =
                new CellRangeAddress(
                    rowNo,
                    rowNo,
                    vesselPlanTable
                        .getFrameFromCellsList()
                        .indexOf(vesselTankDetail.getFrameNoFrom()),
                    vesselPlanTable.getFrameToCellsList().indexOf(vesselTankDetail.getFrameNoTo())
                        + 1);
            spreadsheet.addMergedRegion(mergeRange);

            //            Set values
            XSSFCell stowagePlanDetailsCell =
                stowagePlanDetailsRow.createCell(
                    vesselPlanTable
                        .getFrameFromCellsList()
                        .indexOf(vesselTankDetail.getFrameNoFrom()));

            XSSFCellStyle cellStyle = spreadsheet.getWorkbook().createCellStyle();
            switch (stowagePlanTableTitle) {
              case TANK_NO:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getTankNo());
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_TANK_NO,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.of(stowagePlanTableTitle.getFormat()));
                setMergedStyle(spreadsheet, CellBorder.CLOSED, mergeRange, cellStyle);
                break;
              case CARGO_CODE:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getCargoCode());
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_CARGO_CODE,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.empty());
                setMergedStyle(spreadsheet, CellBorder.CLOSED, mergeRange, cellStyle);
                break;
              case ULLAGE:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getUllage());
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_ULLAGE,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.of(stowagePlanTableTitle.getFormat()));
                setMergedStyle(spreadsheet, CellBorder.OPEN_BOTTOM, mergeRange, cellStyle);
                break;
              case LOADED_PERCENTAGE:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getLoadedPercentage() + "%");
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_LOADED_PERCENTAGE,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.of(stowagePlanTableTitle.getFormat()));
                setMergedStyle(spreadsheet, CellBorder.OPEN_TOP_AND_BOTTOM, mergeRange, cellStyle);
                break;
              case SHIPS_NBBLS:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getShipsNBbls());
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_SHIPS_NBBLS,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.of(stowagePlanTableTitle.getFormat()));
                setMergedStyle(spreadsheet, CellBorder.OPEN_TOP_AND_BOTTOM, mergeRange, cellStyle);
                break;
              case SHIPS_MT:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getShipsMt());
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_SHIPS_MT,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.of(stowagePlanTableTitle.getFormat()));
                setMergedStyle(spreadsheet, CellBorder.OPEN_TOP_AND_BOTTOM, mergeRange, cellStyle);
                break;
              case SHIPS_KL_15C:
                stowagePlanDetailsCell.setCellValue(vesselTankDetail.getShipsKlAt15C());
                cellStyle =
                    getCellStyle(
                        spreadsheet,
                        TableCellStyle.VESSEL_TANK_SHIPS_KL_15C,
                        Optional.of(getColour(vesselTankDetail.getColorCode())),
                        Optional.of(stowagePlanTableTitle.getFormat()));
                setMergedStyle(spreadsheet, CellBorder.OPEN_TOP, mergeRange, cellStyle);
                break;
            }

            //            Set styling
            stowagePlanDetailsCell.setCellStyle(cellStyle);
          }
        }
        //        Update row counter
        rowNo++;
      }
    }

    //            Merge FPT cells
    CellRangeAddress mergeRange =
        new CellRangeAddress(
            vesselTanksTableStartRow,
            rowNo - 1,
            vesselPlanTable.getFrameFromCellsList().size() + 1,
            vesselPlanTable.getFrameFromCellsList().size() + 1);
    spreadsheet.addMergedRegion(mergeRange);

    //    Add vessel tanks >> END

    //    Draw ship's head >> START

    //    Draw downward diagonal
    XSSFDrawing drawing = spreadsheet.createDrawingPatriarch();
    XSSFClientAnchor anchor =
        new XSSFClientAnchor(
            0,
            0,
            0,
            0,
            vesselPlanTable.getFrameFromCellsList().size() + 1,
            vesselTanksTableStartRow,
            vesselPlanTable.getFrameFromCellsList().size() + 2,
            startRow + StowagePlanTableTitles.values().length + vesselTanksTableStartRow);
    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
    XSSFSimpleShape diagonalDown = drawing.createSimpleShape(anchor);
    diagonalDown.setShapeType(ShapeTypes.LINE);
    diagonalDown.setFillColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
    diagonalDown.setLineStyleColor(
        Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());

    //    Draw side line
    XSSFDrawing drawing2 = spreadsheet.createDrawingPatriarch();
    XSSFClientAnchor anchor2 =
        new XSSFClientAnchor(
            0,
            0,
            0,
            0,
            vesselPlanTable.getFrameFromCellsList().size() + 2,
            vesselTanksTableStartRow + StowagePlanTableTitles.values().length,
            vesselPlanTable.getFrameFromCellsList().size() + 2,
            ((midRowIndex + 1) * StowagePlanTableTitles.values().length)
                + vesselTanksTableStartRow);
    XSSFSimpleShape straightLine = drawing2.createSimpleShape(anchor2);
    straightLine.setShapeType(ShapeTypes.LINE);
    straightLine.setLineStyleColor(
        Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());

    //    Draw upward diagonal
    XSSFDrawing drawing3 = spreadsheet.createDrawingPatriarch();
    XSSFClientAnchor anchor3 =
        new XSSFClientAnchor(
            0,
            0,
            0,
            0,
            vesselPlanTable.getFrameFromCellsList().size() + 1,
            ((midRowIndex + 1) * StowagePlanTableTitles.values().length) + vesselTanksTableStartRow,
            vesselPlanTable.getFrameFromCellsList().size() + 2,
            rowEndings.size() * StowagePlanTableTitles.values().length + vesselTanksTableStartRow);
    XSSFSimpleShape diagonalUp = drawing3.createSimpleShape(anchor3);
    diagonalUp.setShapeType(ShapeTypes.LINE);
    diagonalUp.setLineStyleColor(
        Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
    diagonalUp.getCTShape().getSpPr().getXfrm().setFlipH(true);
    //    Draw ship's head >> END

    return new SheetCoordinates(rowNo, columnNo);
  }

  /**
   * Method to convert hexColorCode to Color object
   *
   * @param hexColorCode hex color code string
   * @return Color object
   */
  private Color getColour(String hexColorCode) {
    return Color.decode(hexColorCode);
  }

  /**
   * Method to convert to other values from Bbls value
   *
   * @param value value to be converted
   * @param api api value
   * @param temperature temperature value
   * @param conversionUnit unit to be converted to
   * @return value in the conversionUnit
   */
  public float convertFromBbls(
      float value, float api, float temperature, ConversionUnit conversionUnit) {
    float conversionConstant = getConversionConstant(conversionUnit, api, temperature);
    switch (conversionUnit) {
      case OBSBBLS:
        return value / conversionConstant;
      case MT:
      case KL15C:
      case LT:
        return value * conversionConstant;
      default:
        throw new IllegalStateException("Unexpected value: " + conversionUnit);
    }
  }

  public VesselInfo.VesselReply getVesselDetailByVesselId(VesselInfo.VesselRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselDetailByVesselId(replyBuilder);
  }

  /**
   * Method to get contrast color for a given background color
   *
   * @param backgroundColor Color value of background
   * @return Contrast Color object
   */
  private Color getContrastColor(Color backgroundColor) {
    double lumaValue =
        ((0.299 * backgroundColor.getRed())
                + (0.587 * backgroundColor.getGreen())
                + (0.114 * backgroundColor.getBlue()))
            / 255;
    //    Threshold set to 0.5 for lumaValue
    return lumaValue > 0.5 ? Color.BLACK : Color.WHITE;
  }

  /**
   * @param spreadsheet XSSF spreadsheet object
   * @param portOperationTable PortOperationTable object
   * @param startRow Table start row
   * @param starColumn Table start column
   * @return SheetCoordinates object
   */
  public SheetCoordinates drawPortOperationTable(
      XSSFSheet spreadsheet, PortOperationTable portOperationTable, int startRow, int starColumn) {
    int rowNo = startRow;
    int columnNo = starColumn;

    for (PortOperationsTableTitles portOperationsTableTitle : PortOperationsTableTitles.values()) {
      columnNo = starColumn;

      //      Write titles
      XSSFRow operationsValueRow = spreadsheet.createRow(rowNo);
      XSSFCell titleCell = operationsValueRow.createCell(columnNo);
      titleCell.setCellValue(portOperationsTableTitle.getColumnName());
      titleCell.setCellStyle(
          getCellStyle(
              spreadsheet,
              TableCellStyle.PORT_OPERATIONS_TITLES,
              Optional.empty(),
              Optional.empty()));
      columnNo++;

      //      Write table values
      int portColumnIndex = columnNo;
      for (OperationsTable portOperationDetails : portOperationTable.getOperationsTableList()) {
        XSSFCell operationsValueCell = operationsValueRow.createCell(portColumnIndex);
        switch (portOperationsTableTitle) {
          case OPERATION:
            operationsValueCell.setCellValue(portOperationDetails.getOperation());
            break;
          case PORT_NAME:
            operationsValueCell.setCellValue(portOperationDetails.getPortName());
            break;
          case COUNTRY:
            operationsValueCell.setCellValue(portOperationDetails.getCountry());
            break;
          case LAYCAN_RANGE:
            operationsValueCell.setCellValue(portOperationDetails.getLaycanRange());
            break;
          case ETA:
            operationsValueCell.setCellValue(portOperationDetails.getEta());
            break;
          case ETD:
            operationsValueCell.setCellValue(portOperationDetails.getEtd());
            break;
          case ARR_FWD_DRAFT:
            operationsValueCell.setCellValue(portOperationDetails.getArrFwdDraft());
            break;
          case ARR_AFT_DRAFT:
            operationsValueCell.setCellValue(portOperationDetails.getArrAftDraft());
            break;
          case ARR_DISPLACEMENT:
            operationsValueCell.setCellValue(portOperationDetails.getArrDisplacement());
            break;
          case DEP_FWD_DRAFT:
            operationsValueCell.setCellValue(portOperationDetails.getDepFwdDraft());
            break;
          case DEP_AFT_DRAFT:
            operationsValueCell.setCellValue(portOperationDetails.getDepAftDraft());
            break;
          case DEP_DISP:
            operationsValueCell.setCellValue(portOperationDetails.getDepDisp());
            break;
        }

        XSSFCellStyle cellStyle =
            getCellStyle(
                spreadsheet,
                TableCellStyle.PORT_OPERATIONS_VALUES,
                Optional.empty(),
                Optional.of(portOperationsTableTitle.getFormat()));
        operationsValueCell.setCellStyle(cellStyle);
        portColumnIndex++;
      }
      rowNo++;
    }
    return new SheetCoordinates(rowNo, columnNo);
  }

  /**
   * Method to draw cargo details table
   *
   * @param spreadsheet XSSFSheet spreadsheet object
   * @param cargoDetailsTable CargoDetailsTable object
   * @param startRow Table start row
   * @param starColumn Table start column
   * @return
   */
  public SheetCoordinates drawCargoDetailsTable(
      XSSFSheet spreadsheet, CargoDetailsTable cargoDetailsTable, int startRow, int starColumn) {
    int rowNo = startRow;
    int columnNo = starColumn;

    //    Set cargo rows >> START
    for (CargoDetailsTableTitles cargoTableColumnDetails : CargoDetailsTableTitles.values()) {
      columnNo = starColumn;
      XSSFRow cargoRow = spreadsheet.createRow(rowNo);

      //      Set cargo table titles
      XSSFCell cargoTitleCell = cargoRow.createCell(columnNo);
      cargoTitleCell.setCellValue(cargoTableColumnDetails.getColumnName());
      cargoTitleCell.setCellStyle(
          getCellStyle(
              spreadsheet, TableCellStyle.CARGO_TITLES, Optional.empty(), Optional.empty()));

      //      Merge title rows
      if (cargoTableColumnDetails.isXMerge()) {
        //      Merge columns
        spreadsheet.addMergedRegion(
            new CellRangeAddress(
                rowNo, rowNo, starColumn, starColumn + LOADABLE_PLAN_REPORT_CARGO_TITLE_WIDTH - 1));
      }
      columnNo += LOADABLE_PLAN_REPORT_CARGO_TITLE_WIDTH - 1;

      //      Set cargo table sub columns
      XSSFCell cargoSubTitleCell = cargoRow.createCell(columnNo);
      cargoSubTitleCell.setCellValue(cargoTableColumnDetails.getSubColumnName());
      cargoSubTitleCell.setCellStyle(
          getCellStyle(
              spreadsheet, TableCellStyle.CARGO_TITLES, Optional.empty(), Optional.empty()));
      columnNo++;

      int cargoDetailsColumn = columnNo;
      //        Set values
      for (CargosTable cargoDetails : cargoDetailsTable.getCargosTableList()) {
        XSSFCell cargoValueCell = cargoRow.createCell(cargoDetailsColumn);
        XSSFCell totalValueCell =
            cargoRow.createCell(columnNo + cargoDetailsTable.getCargosTableList().size());

        XSSFCellStyle cellStyle = spreadsheet.getWorkbook().createCellStyle();
        XSSFCellStyle totalCellStyle = spreadsheet.getWorkbook().createCellStyle();
        switch (cargoTableColumnDetails) {
          case CARGO_CODE:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_CARGO_CODE,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.empty());
            totalCellStyle =
                getCellStyle(
                    spreadsheet, TableCellStyle.CARGO_TOTAL, Optional.empty(), Optional.empty());

            totalValueCell.setCellValue(LOADABLE_PLAN_REPORT_TOTAL_VALUE);
            cargoValueCell.setCellValue(cargoDetails.getCargoCode());
            break;
          case LOADING_PORT:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_LOADING_PORT,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.empty());
            totalCellStyle =
                getCellStyle(
                    spreadsheet, TableCellStyle.CARGO_TOTAL, Optional.empty(), Optional.empty());

            cargoValueCell.setCellValue(cargoDetails.getLoadingPort());
            break;
          case API:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_API,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet, TableCellStyle.CARGO_TOTAL, Optional.empty(), Optional.empty());

            cargoValueCell.setCellValue(cargoDetails.getApi());
            break;
          case TEMP:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TEMP,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet, TableCellStyle.CARGO_TOTAL, Optional.empty(), Optional.empty());

            cargoValueCell.setCellValue(cargoDetails.getTemp());
            break;
          case CARGO_NOMINATION:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_CARGO_NOMINATION,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getCargoNominationTotal());
            cargoValueCell.setCellValue(cargoDetails.getCargoNomination());
            break;
          case TOLERANCE:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOLERANCE,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.empty());
            totalCellStyle =
                getCellStyle(
                    spreadsheet, TableCellStyle.CARGO_TOTAL, Optional.empty(), Optional.empty());

            cargoValueCell.setCellValue(cargoDetails.getTolerance());
            break;
          case NBBLS:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_NBBLS,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getNBblsTotal());
            cargoValueCell.setCellValue(cargoDetails.getNBbls());
            break;
          case MT:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_MT,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getMtTotal());
            cargoValueCell.setCellValue(cargoDetails.getMt());
            break;
          case KL15C:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_KL15C,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getKl15CTotal());
            cargoValueCell.setCellValue(cargoDetails.getKl15C());
            break;
          case LT:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_LT,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getLtTotal());
            cargoValueCell.setCellValue(cargoDetails.getLt());
            break;
          case DIFF_BBLS:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_DIFF_BBLS,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getDiffBblsTotal());
            cargoValueCell.setCellValue(cargoDetails.getDiffBbls());
            break;
          case DIFF_PERCENTAGE:
            cellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_DIFF_DIFF_PERCENTAGE,
                    Optional.of(getColour(cargoDetails.getColorCode())),
                    Optional.of(cargoTableColumnDetails.getFormat()));
            totalCellStyle =
                getCellStyle(
                    spreadsheet,
                    TableCellStyle.CARGO_TOTAL,
                    Optional.empty(),
                    Optional.of(cargoTableColumnDetails.getTotalFormat()));

            totalValueCell.setCellValue(cargoDetailsTable.getDiffPercentageTotal());
            cargoValueCell.setCellValue(cargoDetails.getDiffPercentage());
            break;
        }

        cargoValueCell.setCellStyle(cellStyle);
        totalValueCell.setCellStyle(totalCellStyle);

        cargoDetailsColumn++;
      }
      rowNo++;
    }
    //    Set cargo rows >> END
    return new SheetCoordinates(rowNo, columnNo);
  }

  /**
   * Method to build cargo details table
   *
   * @param loadableStudyId loadable study id value
   * @param loadablePatternId loadable pattern id value
   * @return CargoDetailsTable object
   */
  public CargoDetailsTable buildCargoDetailsTable(long loadableStudyId, long loadablePatternId)
      throws GenericServiceException {

    float cargoNominationTotal = 0;
    float nBblsTotal = 0F;
    float mtTotal = 0F;
    float kl15CTotal = 0F;
    float ltTotal = 0F;
    float diffBblsTotal = 0F;
    double diffPercentageTotal = 0;

    //    Get cargo nominations
    List<CargosTable> cargosTableList = new ArrayList<>();
    List<CargoNomination> cargoNominationList =
        this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
            loadableStudyId, true);

    //    Get loadable pattern
    Optional<LoadablePattern> loadablePatternDetails =
        this.loadablePatternRepository.findByIdAndIsActive(loadablePatternId, true);

    //    Get loadable plan details
    LoadableStudy.LoadablePlanDetailsReply.Builder loadablePlanDetailsBuilder =
        LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    buildLoadablePlanDetails(loadablePatternDetails, loadablePlanDetailsBuilder);
    LoadableStudy.LoadablePlanDetailsReply loadablePlanDetails = loadablePlanDetailsBuilder.build();

    //    Get loadable quantity cargo details
    List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList =
        loadablePlanDetails.getLoadableQuantityCargoDetailsList();

    //    Get loadable plan stowage details
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails>
        loadablePlanStowageDetails = loadablePlanDetails.getLoadablePlanStowageDetailsList();

    //    Build cargo details
    for (LoadableStudy.LoadableQuantityCargoDetails loadableQuantityCargoDetails :
        loadableQuantityCargoDetailsList) {
      Optional<CargoNomination> cargoNominationDetails =
          cargoNominationList.stream()
              .filter(
                  cargoNomination ->
                      cargoNomination
                          .getAbbreviation()
                          .equalsIgnoreCase(loadableQuantityCargoDetails.getCargoAbbreviation()))
              .findFirst();

      float shipsFigureMtTotal =
          (float)
              loadablePlanStowageDetails.stream()
                  .filter(
                      loadablePlanStowageDetails1 ->
                          loadableQuantityCargoDetails
                              .getCargoAbbreviation()
                              .equalsIgnoreCase(loadablePlanStowageDetails1.getCargoAbbreviation()))
                  .mapToDouble(detail -> Double.parseDouble(detail.getWeight()))
                  .sum();
      float nBblsValue =
          convertToBbls(
              shipsFigureMtTotal,
              Float.parseFloat(loadableQuantityCargoDetails.getEstimatedAPI()),
              loadableQuantityCargoDetails.getEstimatedTemp().isEmpty()
                  ? 0
                  : Float.parseFloat(loadableQuantityCargoDetails.getEstimatedTemp()),
              ConversionUnit.MT);
      float cargoNominationValue =
          cargoNominationDetails
              .map(
                  cargoNomination ->
                      convertToBbls(
                          cargoNomination.getQuantity().floatValue(),
                          cargoNomination.getApi().floatValue(),
                          cargoNomination.getTemperature().floatValue(),
                          ConversionUnit.MT))
              .orElseThrow(
                  () ->
                      new GenericServiceException(
                          String.format(
                              "Invalid quantity in cargo nomination. LoadableStudyId: %d, LoadablePatterId: %d",
                              loadableStudyId, loadablePatternId),
                          CommonErrorCodes.E_HTTP_BAD_REQUEST,
                          HttpStatusCode.BAD_REQUEST));

      float diffBbls = nBblsValue - cargoNominationValue;
      float kl15CValue = convertFromBbls(nBblsValue, 0F, 0F, ConversionUnit.KL15C);
      float ltValue =
          convertFromBbls(
              nBblsValue,
              Float.parseFloat(loadableQuantityCargoDetails.getEstimatedAPI()),
              0F,
              ConversionUnit.LT);
      double diffPercentage =
          Double.parseDouble(Float.toString(diffBbls))
              / Double.parseDouble(Float.toString(cargoNominationValue));

      //      Calculate totals
      cargoNominationTotal += cargoNominationValue;
      nBblsTotal += nBblsValue;
      mtTotal += shipsFigureMtTotal;
      kl15CTotal += kl15CValue;
      ltTotal += ltValue;
      diffBblsTotal += diffBbls;
      diffPercentageTotal += diffPercentage;
      Long portId =
          cargoNominationDetails.get().getCargoNominationPortDetails().stream()
              .findFirst()
              .get()
              .getPortId();
      PortInfo.GetPortInfoByPortIdsRequest request =
          PortInfo.GetPortInfoByPortIdsRequest.newBuilder().addId(portId).build();
      PortInfo.PortDetail portReply =
          getPortInfo(request).getPortsList().stream()
              .findFirst()
              .orElse(PortInfo.PortDetail.getDefaultInstance());

      CargosTable cargosTable =
          CargosTable.builder()
              .cargoCode(loadableQuantityCargoDetails.getCargoAbbreviation())
              .loadingPort(portReply.getName())
              .api(Double.parseDouble(loadableQuantityCargoDetails.getEstimatedAPI()))
              .temp(
                  loadableQuantityCargoDetails.getEstimatedTemp().isEmpty()
                      ? 0
                      : Float.parseFloat(loadableQuantityCargoDetails.getEstimatedTemp()))
              .cargoNomination(Double.parseDouble(Float.toString(cargoNominationValue)))
              .tolerance(
                  String.format(
                      "+%s %% / -%s %%",
                      loadableQuantityCargoDetails.getMaxTolerence().isEmpty()
                          ? 0.00
                          : loadableQuantityCargoDetails.getMaxTolerence(),
                      loadableQuantityCargoDetails.getMinTolerence().isEmpty()
                          ? 0.00
                          : loadableQuantityCargoDetails.getMinTolerence()))
              .nBbls(Double.parseDouble(Float.toString(nBblsValue)))
              .mt(Double.parseDouble(Float.toString(shipsFigureMtTotal)))
              .kl15C(Double.parseDouble(Float.toString(kl15CValue)))
              .lt(Double.parseDouble(Float.toString(ltValue)))
              .colorCode(loadableQuantityCargoDetails.getColorCode())
              .diffBbls(Double.parseDouble(Float.toString(diffBbls)))
              .diffPercentage(diffPercentage)
              .build();

      cargosTableList.add(cargosTable);
    }
    return CargoDetailsTable.builder()
        .cargosTableList(cargosTableList)
        .cargoNominationTotal(Double.parseDouble(Float.toString(cargoNominationTotal)))
        .nBblsTotal(Double.parseDouble(Float.toString(nBblsTotal)))
        .mtTotal(Double.parseDouble(Float.toString(mtTotal)))
        .kl15CTotal(Double.parseDouble(Float.toString(kl15CTotal)))
        .ltTotal(Double.parseDouble(Float.toString(ltTotal)))
        .diffBblsTotal(Double.parseDouble(Float.toString(diffBblsTotal)))
        .diffPercentageTotal(diffPercentageTotal)
        .build();
  }

  /**
   * @param build
   * @return PortReply
   */
  public PortInfo.PortReply getPortInfo(PortInfo.GetPortInfoByPortIdsRequest build) {
    return portInfoGrpcService.getPortInfoByPortIds(build);
  }

  /**
   * void
   *
   * @param replyBuilder
   * @param loadablePatternOpt
   */
  public void buildLoadablePlanDetails(
      Optional<LoadablePattern> loadablePatternOpt,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder)
      throws GenericServiceException {

    VesselInfo.VesselReply vesselReplyCargoBallastTanks =
        this.getTanks(
            loadablePatternOpt.get().getLoadableStudy().getVesselXId(),
            CARGO_BALLAST_TANK_CATEGORIES);
    if (!SUCCESS.equals(vesselReplyCargoBallastTanks.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs for cargo tanks",
          vesselReplyCargoBallastTanks.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(vesselReplyCargoBallastTanks.getResponseStatus().getCode())));
    }

    List<LoadablePlanQuantity> loadablePlanQuantities =
        loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    buildLoadablePlanQuantity(loadablePlanQuantities, replyBuilder);

    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
        loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    List<Long> tankIdsCommingle =
        loadablePlanCommingleDetails.stream()
            .map(LoadablePlanCommingleDetails::getTankId)
            .collect(Collectors.toList());
    VesselInfo.VesselTankRequest replyTankCommingleBuilder =
        VesselInfo.VesselTankRequest.newBuilder().addAllTankIds(tankIdsCommingle).build();
    VesselInfo.VesselTankResponse vesselReplyCommingle =
        this.getVesselTankDetailsByTankIds(replyTankCommingleBuilder);
    buildLoadablePlanCommingleDetails(
        loadablePlanCommingleDetails, replyBuilder, vesselReplyCommingle);

    List<LoadablePlanStowageDetails> loadablePlanStowageDetails =
        loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    List<Long> tankIds =
        loadablePlanStowageDetails.stream()
            .map(LoadablePlanStowageDetails::getTankId)
            .collect(Collectors.toList());
    VesselInfo.VesselTankRequest replyTankBuilder =
        VesselInfo.VesselTankRequest.newBuilder().addAllTankIds(tankIds).build();
    VesselInfo.VesselTankResponse vesselReply =
        this.getVesselTankDetailsByTankIds(replyTankBuilder);

    buildLoadablePlanStowageCargoDetails(loadablePlanStowageDetails, replyBuilder, vesselReply);
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails> modifieableList =
        new ArrayList<>(replyBuilder.getLoadablePlanStowageDetailsList());
    Collections.sort(
        modifieableList,
        Comparator.comparing(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails
                ::getTankDisplayOrder));
    replyBuilder.clearLoadablePlanStowageDetails();
    replyBuilder.addAllLoadablePlanStowageDetails(modifieableList);
    replyBuilder.addAllTanks(
        this.groupTanks(
            vesselReplyCargoBallastTanks.getVesselTanksList().stream()
                .filter(tankList -> CARGO_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
                .collect(Collectors.toList())));

    buildBallastTankLayout(
        vesselReplyCargoBallastTanks.getVesselTanksList().stream()
            .filter(tankList -> BALLAST_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
            .collect(Collectors.toList()),
        replyBuilder);
    List<LoadablePlanBallastDetails> loadablePlanBallastDetails =
        loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    List<Long> ballanstTankIds =
        loadablePlanBallastDetails.stream()
            .map(LoadablePlanBallastDetails::getTankId)
            .collect(Collectors.toList());
    VesselInfo.VesselTankRequest replyBallastTankBuilder =
        VesselInfo.VesselTankRequest.newBuilder().addAllTankIds(ballanstTankIds).build();
    VesselInfo.VesselTankResponse vesselBallastReply =
        this.getVesselTankDetailsByTankIds(replyBallastTankBuilder);
    buildBallastGridDetails(loadablePlanBallastDetails, replyBuilder, vesselBallastReply);
    List<com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails>
        modifieableBallastList = new ArrayList<>(replyBuilder.getLoadablePlanBallastDetailsList());
    Collections.sort(
        modifieableBallastList,
        Comparator.comparing(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails
                ::getTankDisplayOrder));
    replyBuilder.clearLoadablePlanBallastDetails();
    replyBuilder.addAllLoadablePlanBallastDetails(modifieableBallastList);
    List<LoadablePlanComments> loadablePlanComments =
        loadablePlanCommentsRepository.findByLoadablePatternAndIsActiveOrderByIdDesc(
            loadablePatternOpt.get(), true);

    buildCommentDetails(loadablePlanComments, replyBuilder);

    buildLoadablePlanDetails(replyBuilder, loadablePatternOpt.get());

    Optional<LoadableQuantity> lq =
        loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
            loadablePatternOpt.get().getLoadableStudy());
    if (lq.isPresent()) {
      ofNullable(lq.get().getTotalQuantity())
          .ifPresent(
              totalQuantity -> replyBuilder.setTotalLoadableQuantity(totalQuantity.toString()));
      ofNullable(lq.get().getLoadableStudyPortRotation().getId())
          .ifPresent(replyBuilder::setLastModifiedPort);
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * @param replyBuilder
   * @param loadablePattern void
   */
  private void buildLoadablePlanDetails(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder,
      LoadablePattern loadablePattern) {
    replyBuilder.setId(loadablePattern.getId());
    replyBuilder.setCaseNumber(loadablePattern.getCaseNumber().toString());
    replyBuilder.setDate(
        DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT).format(loadablePattern.getCreatedDate()));
    replyBuilder.setVoyageNumber(loadablePattern.getLoadableStudy().getVoyage().getVoyageNo());
    try {
      Optional.ofNullable(loadablePattern.getLoadableStudy().getVoyage().getVoyageStatus().getId())
          .ifPresent(replyBuilder::setVoyageStatusId);
    } catch (Exception e) {
      log.info("voyage status not found");
    }

    List<LoadablePatternAlgoStatus> status =
        loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(loadablePattern, true);
    if (!status.isEmpty()) {
      replyBuilder.setLoadablePatternStatusId(
          status.get(status.size() - 1).getLoadableStudyStatus().getId());
    }
    replyBuilder.setLoadableStudyStatusId(loadablePattern.getLoadableStudyStatus());
    //    if (stowageDetailsTempRepository
    //        .findByLoadablePatternAndIsActive(loadablePattern, true)
    //        .isEmpty()) replyBuilder.setValidated(true);
    Optional<LoadablePattern> pattern =
        loadablePatternRepository.findByIdAndIsActive(loadablePattern.getId(), true);
    if (!status.isEmpty()) {
      if (stowageDetailsTempRepository
              .findByLoadablePatternAndIsActive(pattern.get(), true)
              .isEmpty()
          || VALIDATED_CONDITIONS.contains(replyBuilder.getLoadablePatternStatusId())) {
        replyBuilder.setValidated(true);
      }
    } else {
      if (stowageDetailsTempRepository
          .findByLoadablePatternAndIsActive(pattern.get(), true)
          .isEmpty()) {
        replyBuilder.setValidated(true);
      }
    }
  }

  /**
   * @param loadablePlanComments
   * @param replyBuilder void
   */
  private void buildCommentDetails(
      List<LoadablePlanComments> loadablePlanComments,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {

    loadablePlanComments.forEach(
        lpc -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanComments.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanComments.newBuilder();
          Optional.ofNullable(lpc.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpc.getComments()).ifPresent(builder::setComment);
          Optional.ofNullable(lpc.getCreatedBy()).ifPresent(builder::setCreatedBy);
          Optional.ofNullable(
                  DateTimeFormatter.ofPattern(DATE_FORMAT).format(lpc.getCreatedDateTime()))
              .ifPresent(builder::setDataAndTime);
          // Username set at gateway as Keycloack is at gateway layer
          builder.setUserName(
              DEFAULT_USER_NAME); // ToDo - replace it with the value taken from cache
          replyBuilder.addLoadablePlanComments(builder);
        });
  }

  /**
   * @param loadablePlanBallastDetails
   * @param replyBuilder void
   * @param vesselBallastReply
   */
  private void buildBallastGridDetails(
      List<LoadablePlanBallastDetails> loadablePlanBallastDetails,
      LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder,
      VesselInfo.VesselTankResponse vesselBallastReply) {
    List<LoadablePlanStowageDetailsTemp> ballstTempList =
        this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsInAndIsActive(
            loadablePlanBallastDetails, true);
    loadablePlanBallastDetails.forEach(
        lpbd -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder();
          ofNullable(lpbd.getId()).ifPresent(builder::setId);
          ofNullable(lpbd.getCorrectedLevel()).ifPresent(builder::setCorrectedLevel);
          ofNullable(lpbd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
          ofNullable(lpbd.getCubicMeter()).ifPresent(builder::setCubicMeter);
          ofNullable(lpbd.getInertia()).ifPresent(builder::setInertia);
          ofNullable(lpbd.getLcg()).ifPresent(builder::setLcg);
          ofNullable(lpbd.getMetricTon()).ifPresent(builder::setMetricTon);
          ofNullable(lpbd.getPercentage()).ifPresent(builder::setPercentage);
          ofNullable(lpbd.getRdgLevel()).ifPresent(builder::setRdgLevel);
          ofNullable(lpbd.getSg()).ifPresent(builder::setSg);
          ofNullable(lpbd.getTankId()).ifPresent(builder::setTankId);
          ofNullable(lpbd.getTcg()).ifPresent(builder::setTcg);
          ofNullable(lpbd.getVcg()).ifPresent(builder::setVcg);
          ofNullable(lpbd.getTankName()).ifPresent(builder::setTankName);
          ofNullable(lpbd.getColorCode()).ifPresent(builder::setColorCode);
          VesselInfo.VesselTankOrder vesselTankOrder =
              vesselBallastReply.getVesselTankOrderList().stream()
                  .filter(tankData -> (tankData.getTankId() == lpbd.getTankId()))
                  .findFirst()
                  .get();
          Optional.ofNullable(vesselTankOrder.getShortName()).ifPresent(builder::setTankShortName);
          Optional.ofNullable(vesselTankOrder.getTankDisplayOrder())
              .ifPresent(builder::setTankDisplayOrder);

          ofNullable(lpbd.getCorrectedLevel()).ifPresent(builder::setCorrectedLevelOrginal);
          ofNullable(lpbd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactorOrginal);
          ofNullable(lpbd.getMetricTon()).ifPresent(builder::setMetricTonOrginal);
          ofNullable(lpbd.getPercentage()).ifPresent(builder::setPercentageOrginal);
          ofNullable(lpbd.getRdgLevel()).ifPresent(builder::setRdgLevelOrginal);

          setTempBallastDetails(lpbd, ballstTempList, builder);
          replyBuilder.addLoadablePlanBallastDetails(builder);
        });
  }

  /**
   * Ballast Tank category builder
   *
   * @param vesselTankDetails - List<VesselTankDetail>
   * @param replyBuilder - LoadablePatternReply.Builder
   */
  private void buildBallastTankLayout(
      List<VesselInfo.VesselTankDetail> vesselTankDetails,
      LoadableStudy.LoadablePatternReply.Builder replyBuilder) {

    List<VesselInfo.VesselTankDetail> frontBallastTanks = new ArrayList<>();
    List<VesselInfo.VesselTankDetail> centerBallestTanks = new ArrayList<>();
    List<VesselInfo.VesselTankDetail> rearBallastTanks = new ArrayList<>();
    frontBallastTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_FRONT_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));
    centerBallestTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_CENTER_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));

    rearBallastTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_REAR_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));

    replyBuilder.addAllBallastFrontTanks(this.groupTanks(frontBallastTanks));
    replyBuilder.addAllBallastCenterTanks(this.groupTanks(centerBallestTanks));
    replyBuilder.addAllBallastRearTanks(this.groupTanks(rearBallastTanks));
  }

  /**
   * Group tanks based on tank group
   *
   * @param tankDetailList
   * @return
   */
  private List<LoadableStudy.TankList> groupTanks(
      List<VesselInfo.VesselTankDetail> tankDetailList) {
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

  private VesselInfo.VesselReply getTanks(Long vesselId, List<Long> tankCategory)
      throws GenericServiceException {
    VesselInfo.VesselRequest.Builder vesselGrpcRequest = VesselInfo.VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(vesselId);
    vesselGrpcRequest.addAllTankCategories(tankCategory);
    return this.getVesselTanks(vesselGrpcRequest.build());
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

  public VesselInfo.VesselReply getVesselTanks(VesselInfo.VesselRequest request) {
    return this.vesselInfoGrpcService.getVesselTanks(request);
  }

  public VesselInfo.VesselTankResponse getVesselTankDetailsByTankIds(
      VesselInfo.VesselTankRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselInfoBytankIds(replyBuilder);
  }

  /**
   * @param loadablePlanStowageDetails
   * @param replyBuilder void
   */
  public void buildLoadablePlanStowageCargoDetails(
      List<LoadablePlanStowageDetails> loadablePlanStowageDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder,
      VesselInfo.VesselTankResponse vesselTankData) {
    List<LoadablePlanStowageDetailsTemp> tempStowageDetails =
        this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsInAndIsActive(
            loadablePlanStowageDetails, true);
    loadablePlanStowageDetails.forEach(
        lpsd -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
          Optional.ofNullable(lpsd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpsd.getAbbreviation()).ifPresent(builder::setCargoAbbreviation);
          Optional.ofNullable(lpsd.getApi()).ifPresent(builder::setApi);
          Optional.ofNullable(lpsd.getCorrectedUllage()).ifPresent(builder::setCorrectedUllage);
          Optional.ofNullable(lpsd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
          Optional.ofNullable(lpsd.getFillingPercentage()).ifPresent(builder::setFillingRatio);
          Optional.ofNullable(lpsd.getObservedBarrels()).ifPresent(builder::setObservedBarrels);
          Optional.ofNullable(lpsd.getObservedBarrelsAt60())
              .ifPresent(builder::setObservedBarrelsAt60);
          Optional.ofNullable(lpsd.getObservedM3()).ifPresent(builder::setObservedM3);
          Optional.ofNullable(lpsd.getRdgUllage()).ifPresent(builder::setRdgUllage);
          Optional.ofNullable(lpsd.getTankname()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpsd.getTankId()).ifPresent(builder::setTankId);
          Optional.ofNullable(lpsd.getCargoNominationTemperature())
              .ifPresent(temp -> builder.setTemperature(valueOf(temp)));
          Optional.ofNullable(lpsd.getWeight()).ifPresent(builder::setWeight);
          Optional.ofNullable(lpsd.getColorCode()).ifPresent(builder::setColorCode);
          Optional.ofNullable(lpsd.getCorrectedUllage())
              .ifPresent(builder::setCorrectedUllageOrginal);
          Optional.ofNullable(lpsd.getCorrectionFactor())
              .ifPresent(builder::setCorrectionFactorOrginal);
          Optional.ofNullable(lpsd.getFillingPercentage())
              .ifPresent(builder::setFillingRatioOrginal);
          Optional.ofNullable(lpsd.getWeight()).ifPresent(builder::setWeightOrginal);
          Optional.ofNullable(lpsd.getRdgUllage()).ifPresent(builder::setRdgUllageOrginal);

          Optional.ofNullable(lpsd.getCargoNominationId()).ifPresent(builder::setCargoNominationId);

          this.setTempStowageDetails(lpsd, tempStowageDetails, builder);
          builder.setIsCommingle(false);
          addTankShortName(vesselTankData, lpsd.getTankId(), builder);
          replyBuilder.addLoadablePlanStowageDetails(builder);
        });
  }

  /**
   * Check if data present in temporary table
   *
   * @param lpsd
   * @param tempStowageDetails
   * @param builder
   */
  private void setTempStowageDetails(
      LoadablePlanStowageDetails lpsd,
      List<LoadablePlanStowageDetailsTemp> tempStowageDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder) {
    Optional<LoadablePlanStowageDetailsTemp> tempStowageOpt =
        tempStowageDetails.stream()
            .filter(temp -> temp.getLoadablePlanStowageDetails().getId().equals(lpsd.getId()))
            .findAny();
    if (tempStowageOpt.isPresent()) {
      LoadablePlanStowageDetailsTemp tempStowage = tempStowageOpt.get();
      Optional.ofNullable(tempStowage.getCorrectedUllage())
          .ifPresent(item -> builder.setCorrectedUllage(valueOf(item)));
      Optional.ofNullable(tempStowage.getCorrectionFactor())
          .ifPresent(item -> builder.setCorrectionFactor(valueOf(item)));
      Optional.ofNullable(tempStowage.getFillingRatio())
          .ifPresent(item -> builder.setFillingRatio(valueOf(item)));
      Optional.ofNullable(tempStowage.getQuantity())
          .ifPresent(item -> builder.setWeight(valueOf(item)));
      Optional.ofNullable(tempStowage.getRdgUllage())
          .ifPresent(item -> builder.setRdgUllage(valueOf(item)));
    }
  }

  private void addTankShortName(
      VesselInfo.VesselTankResponse vesselReplyCommingle,
      Long tankId,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder stowageBuilder) {
    VesselInfo.VesselTankOrder vesselTankOrder =
        vesselReplyCommingle.getVesselTankOrderList().stream()
            .filter(tankData -> (tankData.getTankId() == tankId))
            .findFirst()
            .get();
    Optional.ofNullable(vesselTankOrder.getShortName()).ifPresent(stowageBuilder::setTankShortName);
    Optional.ofNullable(vesselTankOrder.getTankDisplayOrder())
        .ifPresent(stowageBuilder::setTankDisplayOrder);
  }

  /**
   * @param loadablePlanCommingleDetails
   * @param replyBuilder void
   * @param vesselReplyCommingle
   */
  private void buildLoadablePlanCommingleDetails(
      List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails,
      LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder,
      VesselInfo.VesselTankResponse vesselReplyCommingle) {
    loadablePlanCommingleDetails.forEach(
        lpcd -> {
          LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder builder =
              getCommingleCargoBuilder(lpcd);
          replyBuilder.addLoadableQuantityCommingleCargoDetails(builder);

          com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder
              stowageBuilder =
                  com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
          Optional.ofNullable(lpcd.getId()).ifPresent(stowageBuilder::setId);
          Optional.ofNullable(lpcd.getGrade()).ifPresent(stowageBuilder::setCargoAbbreviation);
          Optional.ofNullable(lpcd.getApi()).ifPresent(stowageBuilder::setApi);
          Optional.ofNullable(lpcd.getCorrectedUllage())
              .ifPresent(stowageBuilder::setCorrectedUllage);
          Optional.ofNullable(lpcd.getCorrectionFactor())
              .ifPresent(stowageBuilder::setCorrectionFactor);
          Optional.ofNullable(lpcd.getFillingRatio()).ifPresent(stowageBuilder::setFillingRatio);

          Optional.ofNullable(lpcd.getRdgUllage()).ifPresent(stowageBuilder::setRdgUllage);
          Optional.ofNullable(lpcd.getTankName()).ifPresent(stowageBuilder::setTankName);
          Optional.ofNullable(lpcd.getTankShortName()).ifPresent(stowageBuilder::setTankShortName);
          Optional.ofNullable(lpcd.getTankId()).ifPresent(stowageBuilder::setTankId);
          Optional.ofNullable(lpcd.getTemperature()).ifPresent(stowageBuilder::setTemperature);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(stowageBuilder::setWeight);
          stowageBuilder.setIsCommingle(true);

          Optional.ofNullable(lpcd.getCorrectedUllage())
              .ifPresent(stowageBuilder::setCorrectedUllageOrginal);
          Optional.ofNullable(lpcd.getCorrectionFactor())
              .ifPresent(stowageBuilder::setCorrectionFactorOrginal);
          Optional.ofNullable(lpcd.getFillingRatio())
              .ifPresent(stowageBuilder::setFillingRatioOrginal);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(stowageBuilder::setWeightOrginal);
          Optional.ofNullable(lpcd.getRdgUllage()).ifPresent(stowageBuilder::setRdgUllageOrginal);

          addTankShortName(vesselReplyCommingle, lpcd.getTankId(), stowageBuilder);
          replyBuilder.addLoadablePlanStowageDetails(stowageBuilder);
        });
  }

  private LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder getCommingleCargoBuilder(
      LoadablePlanCommingleDetails lpcd) {
    LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder builder =
        LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder();
    ofNullable(lpcd.getId()).ifPresent(builder::setId);
    ofNullable(lpcd.getApi()).ifPresent(builder::setApi);
    ofNullable(lpcd.getCargo1Abbreviation()).ifPresent(builder::setCargo1Abbreviation);
    ofNullable(lpcd.getCargo1Bbls60f()).ifPresent(builder::setCargo1Bbls60F);
    ofNullable(lpcd.getCargo1BblsDbs()).ifPresent(builder::setCargo1Bblsdbs);
    ofNullable(lpcd.getCargo1Kl()).ifPresent(builder::setCargo1KL);
    ofNullable(lpcd.getCargo1Lt()).ifPresent(builder::setCargo1LT);
    ofNullable(lpcd.getCargo1Mt()).ifPresent(builder::setCargo1MT);
    ofNullable(lpcd.getCargo1Percentage()).ifPresent(builder::setCargo1Percentage);
    ofNullable(lpcd.getCargo2Abbreviation()).ifPresent(builder::setCargo2Abbreviation);
    ofNullable(lpcd.getCargo2Bbls60f()).ifPresent(builder::setCargo2Bbls60F);
    ofNullable(lpcd.getCargo2BblsDbs()).ifPresent(builder::setCargo2Bblsdbs);
    ofNullable(lpcd.getCargo2Kl()).ifPresent(builder::setCargo2KL);
    ofNullable(lpcd.getCargo2Lt()).ifPresent(builder::setCargo2LT);
    ofNullable(lpcd.getCargo2Mt()).ifPresent(builder::setCargo2MT);
    ofNullable(lpcd.getCargo2Percentage()).ifPresent(builder::setCargo2Percentage);
    ofNullable(lpcd.getGrade()).ifPresent(builder::setGrade);
    ofNullable(lpcd.getQuantity()).ifPresent(builder::setQuantity);
    ofNullable(lpcd.getTankName()).ifPresent(builder::setTankName);
    ofNullable(lpcd.getTemperature()).ifPresent(builder::setTemp);
    ofNullable(lpcd.getTankShortName()).ifPresent(builder::setTankShortName);
    return builder;
  }

  /**
   * @param vesselTankDetails
   * @param replyBuilder void
   */
  public void buildBallastTankLayout(
      List<VesselInfo.VesselTankDetail> vesselTankDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {

    List<VesselInfo.VesselTankDetail> frontBallastTanks = new ArrayList<>();
    List<VesselInfo.VesselTankDetail> centerBallestTanks = new ArrayList<>();
    List<VesselInfo.VesselTankDetail> rearBallastTanks = new ArrayList<>();
    frontBallastTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_FRONT_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));
    centerBallestTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_CENTER_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));

    rearBallastTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_REAR_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));

    replyBuilder.addAllBallastFrontTanks(this.groupTanks(frontBallastTanks));
    replyBuilder.addAllBallastCenterTanks(this.groupTanks(centerBallestTanks));
    replyBuilder.addAllBallastRearTanks(this.groupTanks(rearBallastTanks));
  }

  /**
   * @param loadablePlanQuantities
   * @param replyBuilder void
   */
  private void buildLoadablePlanQuantity(
      List<LoadablePlanQuantity> loadablePlanQuantities,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {
    loadablePlanQuantities.forEach(
        lpq -> {
          LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
              getLoadablePlanQtyBuilder(lpq);
          replyBuilder.addLoadableQuantityCargoDetails(builder);
        });
  }

  private LoadableStudy.LoadableQuantityCargoDetails.Builder getLoadablePlanQtyBuilder(
      LoadablePlanQuantity lpq) {
    LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
    ofNullable(lpq.getId()).ifPresent(builder::setId);
    ofNullable(lpq.getDifferenceColor()).ifPresent(builder::setDifferenceColor);
    ofNullable(lpq.getDifferencePercentage())
        .ifPresent(
            diffPercentage -> builder.setDifferencePercentage(String.valueOf(diffPercentage)));
    ofNullable(lpq.getEstimatedApi())
        .ifPresent(estimatedApi -> builder.setEstimatedAPI(String.valueOf(estimatedApi)));
    ofNullable(lpq.getCargoNominationTemperature())
        .ifPresent(
            estimatedTemperature -> builder.setEstimatedTemp(String.valueOf(estimatedTemperature)));
    ofNullable(lpq.getGrade()).ifPresent(builder::setGrade);
    ofNullable(lpq.getLoadableBbls60f()).ifPresent(builder::setLoadableBbls60F);
    ofNullable(lpq.getLoadableBblsDbs()).ifPresent(builder::setLoadableBblsdbs);
    ofNullable(lpq.getLoadableKl()).ifPresent(builder::setLoadableKL);
    ofNullable(lpq.getLoadableLt()).ifPresent(builder::setLoadableLT);
    ofNullable(lpq.getLoadableMt()).ifPresent(builder::setLoadableMT);
    ofNullable(lpq.getMaxTolerence()).ifPresent(builder::setMaxTolerence);
    ofNullable(lpq.getMinTolerence()).ifPresent(builder::setMinTolerence);
    ofNullable(lpq.getOrderBbls60f()).ifPresent(builder::setOrderBbls60F);
    ofNullable(lpq.getOrderBblsDbs()).ifPresent(builder::setOrderBblsdbs);
    ofNullable(lpq.getCargoXId()).ifPresent(builder::setCargoId);
    ofNullable(lpq.getOrderQuantity())
        .ifPresent(orderQuantity -> builder.setOrderedMT(String.valueOf(orderQuantity)));
    Optional.of(lpq.getCargoColor()).ifPresent(builder::setColorCode);
    Optional.of(lpq.getCargoAbbreviation()).ifPresent(builder::setCargoAbbreviation);
    Optional.ofNullable(lpq.getTimeRequiredForLoading())
        .ifPresent(builder::setTimeRequiredForLoading);
    Optional.of(lpq.getCargoNominationId()).ifPresent(builder::setCargoNominationId);
    return builder;
  }

  public LoadableStudy.AlgoReply.Builder validateLoadablePlan(
      LoadableStudy.LoadablePlanDetailsRequest request,
      LoadableStudy.AlgoReply.Builder replyBuilder)
      throws IOException, GenericServiceException {
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (!loadablePatternOpt.isPresent()) {
      log.info(INVALID_LOADABLE_PATTERN_ID, request.getLoadablePatternId());
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(INVALID_LOADABLE_PATTERN_ID)
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));

    } else {
      LoadabalePatternValidateRequest loadabalePatternValidateRequest =
          new LoadabalePatternValidateRequest();
      log.info("------- Started preparing request");
      ModelMapper modelMapper = new ModelMapper();
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
          new com.cpdss.loadablestudy.domain.LoadableStudy();

      loadableStudyService.buildLoadableStudy(
          loadablePatternOpt.get().getLoadableStudy().getId(),
          loadablePatternOpt.get().getLoadableStudy(),
          loadableStudy,
          modelMapper);
      log.info("------- Started preparing buildLoadablePlanPortWiseDetails");
      buildLoadablePlanPortWiseDetails(loadablePatternOpt.get(), loadabalePatternValidateRequest);

      loadabalePatternValidateRequest.setLoadableStudy(loadableStudy);

      loadabalePatternValidateRequest.setBallastEdited(
          stowageDetailsTempRepository.isBallastEdited(request.getLoadablePatternId(), true));
      loadabalePatternValidateRequest.setLoadablePatternId(request.getLoadablePatternId());
      loadabalePatternValidateRequest.setCaseNumber(loadablePatternOpt.get().getCaseNumber());
      log.info("------- Before save payload into db");
      ObjectMapper objectMapper = new ObjectMapper();
      jsonDataService.saveJsonToDatabase(
          request.getLoadablePatternId(),
          LOADABLE_PATTERN_EDIT_REQUEST,
          objectMapper.writeValueAsString(loadabalePatternValidateRequest));
      log.info(
          "------- After save payload into db : "
              + this.rootFolder
              + "/json/loadablePattern_request_"
              + request.getLoadablePatternId()
              + ".json");
      objectMapper.writeValue(
          new File(
              this.rootFolder
                  + "/json/loadablePattern_request_"
                  + request.getLoadablePatternId()
                  + ".json"),
          loadabalePatternValidateRequest);
      log.info("------- Payload has successfully saved in file");
      LoadabalePatternValidateRequest communicationServiceRequest = loadabalePatternValidateRequest;
      buildCommunicationServiceRequest(communicationServiceRequest, loadablePatternOpt.get());
      log.info("------- Before envoy writer calling");
      EnvoyWriter.WriterReply ewReply =
          communicationService.passRequestPayloadToEnvoyWriter(
              objectMapper.writeValueAsString(communicationServiceRequest),
              loadableStudy.getVesselId(),
              MessageTypes.VALIDATEPLAN.getMessageType());
      log.info("------- After envoy writer calling");
      if (SUCCESS.equals(ewReply.getResponseStatus().getStatus())) {
        log.info("------- Envoy writer has called successfully : " + ewReply.toString());
        LoadableStudyCommunicationStatus lsCommunicationStatus =
            new LoadableStudyCommunicationStatus();
        if (ewReply.getMessageId() != null) {
          lsCommunicationStatus.setMessageUUID(ewReply.getMessageId());
          lsCommunicationStatus.setCommunicationStatus(
              CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId());
        }
        lsCommunicationStatus.setReferenceId(request.getLoadablePatternId());
        lsCommunicationStatus.setMessageType(String.valueOf(MessageTypes.VALIDATEPLAN));
        lsCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
        this.loadableStudyCommunicationStatusRepository.save(lsCommunicationStatus);
      } else {
        log.info("------- Envoy writer calling has failed : " + ewReply.getStatusCode());
        //        AlgoResponse algoResponse =
        //            restTemplate.postForObject(
        //                loadableStudyUrl, loadabalePatternValidateRequest, AlgoResponse.class);
        //
        //        updateProcessIdForLoadablePattern(
        //            algoResponse.getProcessId(),
        //            loadablePatternOpt.get(),
        //            LOADABLE_PATTERN_VALIDATION_STARTED_ID);
        //        replyBuilder
        //            .setProcesssId(algoResponse.getProcessId())
        //            .setResponseStatus(
        //
        // Common.ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());
      }
    }
    return replyBuilder;
  }

  private void buildCommunicationServiceRequest(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      LoadablePattern loadablePattern) {
    Long patternId = loadablePattern.getId();
    ModelMapper modelMapper = new ModelMapper();
    buldLoadablPlanstowageTempDetails(loadabalePatternValidateRequest, patternId, modelMapper);
    buldLoadablPlanStowageDetails(loadabalePatternValidateRequest, patternId, modelMapper);
    buldLoadablPlanBallastDetails(loadabalePatternValidateRequest, patternId, modelMapper);
    buldLoadablPlanStabilityDetails(loadabalePatternValidateRequest, patternId, modelMapper);
    buldLoadablPlanQuantityCargoDetails(
        loadabalePatternValidateRequest, loadablePattern, modelMapper);
  }

  private void buldLoadablPlanQuantityCargoDetails(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      LoadablePattern pattern,
      ModelMapper modelMapper) {
    List<LoadablePlanQuantity> loadablePlanQuantities =
        loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(pattern, true);

    List<LoadablePatternCargoToppingOffSequence> loadablePatternCargoToppingOffSequences =
        toppingOffSequenceRepository.findByLoadablePatternAndIsActive(pattern, true);
    List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetails =
        new ArrayList<>();
    for (LoadablePlanQuantity loableqty : loadablePlanQuantities) {
      LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
          getLoadablePlanQtyBuilder(loableqty);
      for (LoadablePatternCargoToppingOffSequence toppingOff :
          loadablePatternCargoToppingOffSequences) {
        LoadableStudy.CargoToppingOffSequenceDetails.Builder toppingOffBuilder =
            LoadableStudy.CargoToppingOffSequenceDetails.newBuilder();
        toppingOffBuilder.setTankId(toppingOff.getTankXId());
        toppingOffBuilder.setOrderNumber(toppingOff.getOrderNumber());
        builder.addToppingOffSequences(toppingOffBuilder);
      }
      loadableQuantityCargoDetails.add(builder.build());
    }
    loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails().stream()
        .forEach(
            port -> {
              port.getArrivalCondition()
                  .setLoadableQuantityCargoDetails(loadableQuantityCargoDetails);
              port.getDepartureCondition()
                  .setLoadableQuantityCargoDetails(loadableQuantityCargoDetails);
            });
  }

  private void buldLoadablPlanStabilityDetails(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      Long patternId,
      ModelMapper modelMapper) {
    loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails().stream()
        .forEach(
            port -> {
              port.getArrivalCondition().setStabilityParameter(new StabilityParameter());
              SynopticalTableLoadicatorData synopticalTableLoadicatorDataArr =
                  synopticalTableLoadicatorDataRepository
                      .findByLoadablePatternIdAndPortIdAndOperationId(
                          patternId, port.getPortId(), LOADING_OPERATION_ID);
              SynopticalTableLoadicatorData synopticalTableLoadicatorDataDep =
                  synopticalTableLoadicatorDataRepository
                      .findByLoadablePatternIdAndPortIdAndOperationId(
                          patternId, port.getPortId(), DISCHARGING_OPERATION_ID);
              StabilityParameter stabilityParameter =
                  modelMapper.map(
                      synopticalTableLoadicatorDataArr,
                      com.cpdss.loadablestudy.domain.StabilityParameter.class);
              port.getArrivalCondition().setStabilityParameter(stabilityParameter);
              StabilityParameter stabilityParameterDep =
                  modelMapper.map(
                      synopticalTableLoadicatorDataDep,
                      com.cpdss.loadablestudy.domain.StabilityParameter.class);
              port.getDepartureCondition().setStabilityParameter(stabilityParameterDep);
            });
  }

  private void buldLoadablPlanBallastDetails(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      Long patternId,
      ModelMapper modelMapper) {
    loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails().stream()
        .forEach(
            port -> {
              port.getArrivalCondition().setLoadablePlanBallastDetails(new ArrayList<>());
              Optional<LoadablePattern> pattern =
                  loadablePatternRepository.findByIdAndIsActive(patternId, true);
              List<LoadablePlanStowageBallastDetails> ballastDetailsList =
                  loadablePlanStowageBallastDetailsRepository.findAllByLoadablePatternIdAndPortXId(
                      patternId, port.getPortId());
              List<LoadablePlanStowageBallastDetails> ballastDetailsListPortwiseArr =
                  ballastDetailsList.stream()
                      .filter(var -> var.getOperationType().equals(OPERATION_TYPE_ARR))
                      .collect(Collectors.toList());
              List<LoadablePlanStowageBallastDetails> ballastDetailsListPortwiseDep =
                  ballastDetailsList.stream()
                      .filter(var -> var.getOperationType().equals(OPERATION_TYPE_DEP))
                      .collect(Collectors.toList());
              ballastDetailsListPortwiseArr.stream()
                  .forEach(
                      lpcd -> {
                        com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails ballastDetails =
                            new com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails();

                        ballastDetails =
                            modelMapper.map(
                                lpcd,
                                com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails.class);
                        port.getArrivalCondition()
                            .getLoadablePlanBallastDetails()
                            .add(ballastDetails);
                      });
              ballastDetailsListPortwiseDep.stream()
                  .forEach(
                      lpcd -> {
                        com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails ballastDetails =
                            new com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails();

                        ballastDetails =
                            modelMapper.map(
                                lpcd,
                                com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails.class);
                        port.getDepartureCondition()
                            .getLoadablePlanBallastDetails()
                            .add(ballastDetails);
                      });
            });
  }

  private void buldLoadablPlanStowageDetails(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      Long patternId,
      ModelMapper modelMapper) {
    loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails().stream()
        .forEach(
            port -> {
              port.getArrivalCondition().setLoadablePlanStowageDetails(new ArrayList<>());
              List<LoadablePatternCargoDetails> stowageDetailsListPortwise =
                  loadablePatternCargoDetailsRepository.findAllByPatternIdAndPortId(
                      patternId, port.getPortId());
              List<LoadablePatternCargoDetails> stowageDetailsListPortwiseArr =
                  stowageDetailsListPortwise.stream()
                      .filter(var -> var.getOperationType().equals(OPERATION_TYPE_ARR))
                      .collect(Collectors.toList());
              List<LoadablePatternCargoDetails> stowageDetailsListPortwiseDep =
                  stowageDetailsListPortwise.stream()
                      .filter(var1 -> var1.getOperationType().equals(OPERATION_TYPE_DEP))
                      .collect(Collectors.toList());
              stowageDetailsListPortwiseArr.stream()
                  .forEach(
                      lpcd -> {
                        com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails stowageDetails =
                            new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();

                        stowageDetails =
                            modelMapper.map(
                                lpcd,
                                com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails.class);
                        port.getArrivalCondition()
                            .getLoadablePlanStowageDetails()
                            .add(stowageDetails);
                      });
              stowageDetailsListPortwiseDep.stream()
                  .forEach(
                      lpcd -> {
                        com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails stowageDetails =
                            new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();

                        stowageDetails =
                            modelMapper.map(
                                lpcd,
                                com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails.class);
                        port.getDepartureCondition()
                            .getLoadablePlanStowageDetails()
                            .add(stowageDetails);
                      });
            });
  }

  private void buldLoadablPlanstowageTempDetails(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      Long patternId,
      ModelMapper modelMapper) {
    loadabalePatternValidateRequest.setLoadablePlanStowageTempDetails(new ArrayList<>());
    Optional<LoadablePattern> pattern =
        loadablePatternRepository.findByIdAndIsActive(patternId, true);
    List<LoadablePlanStowageDetailsTemp> stowageTempList =
        stowageDetailsTempRepository.findByLoadablePatternAndIsActive(pattern.get(), true);
    stowageTempList.stream()
        .forEach(
            stowageTemp -> {
              com.cpdss.loadablestudy.domain.LoadablePlanStowageTempDetails
                  loadablePlanStowageTempDetails =
                      new com.cpdss.loadablestudy.domain.LoadablePlanStowageTempDetails();
              loadablePlanStowageTempDetails =
                  modelMapper.map(
                      stowageTemp,
                      com.cpdss.loadablestudy.domain.LoadablePlanStowageTempDetails.class);
              loadabalePatternValidateRequest
                  .getLoadablePlanStowageTempDetails()
                  .add(loadablePlanStowageTempDetails);
            });
  }

  /**
   * @param processId
   * @param loadablePattern
   * @param loadablePatternProcessingStartedId void
   */
  private void updateProcessIdForLoadablePattern(
      String processId, LoadablePattern loadablePattern, Long loadablePatternProcessingStartedId) {
    LoadablePatternAlgoStatus status = new LoadablePatternAlgoStatus();
    status.setLoadablePattern(loadablePattern);
    status.setIsActive(true);
    status.setLoadableStudyStatus(
        loadableStudyStatusRepository.getOne(loadablePatternProcessingStartedId));
    status.setProcessId(processId);
    status.setVesselxid(loadablePattern.getLoadableStudy().getVesselXId());
    loadablePatternAlgoStatusRepository.save(status);
  }

  public LoadableStudy.UpdateUllageReply.Builder updateUllage(
      LoadableStudy.UpdateUllageRequest request,
      LoadableStudy.UpdateUllageReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (!loadablePatternOpt.isPresent()) {
      throw new GenericServiceException(
          INVALID_LOADABLE_PATTERN_ID,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    UllageUpdateResponse algoResponse =
        this.callAlgoUllageUpdateApi(
            this.prepareUllageUpdateRequest(request, loadablePatternOpt.get()));
    if (!request.getUpdateUllageForLoadingPlan() && !algoResponse.getFillingRatio().equals("")) {
      this.saveUllageUpdateResponse(algoResponse, request, loadablePatternOpt.get());
    }
    replyBuilder.setLoadablePlanStowageDetails(this.buildUpdateUllageReply(algoResponse, request));
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Build upadate ullage reply
   *
   * @param algoResponse
   * @param request
   * @return
   */
  private com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails
      buildUpdateUllageReply(
          UllageUpdateResponse algoResponse, LoadableStudy.UpdateUllageRequest request) {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
    Optional.ofNullable(algoResponse.getCorrectedUllage()).ifPresent(builder::setCorrectedUllage);
    Optional.ofNullable(algoResponse.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
    Optional.ofNullable(algoResponse.getQuantityMt()).ifPresent(builder::setWeight);
    Optional.ofNullable(request.getLoadablePlanStowageDetails().getIsBallast())
        .ifPresent(builder::setIsBallast);
    Optional.ofNullable(algoResponse.getFillingRatio()).ifPresent(builder::setFillingRatio);
    return builder.build();
  }

  /**
   * Save corrected ullage to the temp table
   *
   * @param algoResponse
   * @param loadablePattern
   */
  private void saveUllageUpdateResponse(
      UllageUpdateResponse algoResponse,
      LoadableStudy.UpdateUllageRequest request,
      LoadablePattern loadablePattern) {
    LoadablePlanStowageDetailsTemp stowageTemp = null;
    if (request.getLoadablePlanStowageDetails().getIsBallast()) {
      LoadablePlanBallastDetails ballastDetails =
          this.loadablePlanBallastDetailsRepository.getOne(algoResponse.getId());
      stowageTemp =
          this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsAndIsActive(
              ballastDetails, true);
      if (null == stowageTemp) {
        stowageTemp = new LoadablePlanStowageDetailsTemp();
        stowageTemp.setLoadablePlanBallastDetails(ballastDetails);
        stowageTemp.setIsActive(true);
      }
    } else if (request.getLoadablePlanStowageDetails().getIsCommingle()) {
      LoadablePlanCommingleDetails commingleDetails =
          this.loadablePlanCommingleDetailsRepository.getOne(algoResponse.getId());
      stowageTemp =
          this.stowageDetailsTempRepository.findByLoadablePlanCommingleDetailsAndIsActive(
              commingleDetails, true);
      if (null == stowageTemp) {
        stowageTemp = new LoadablePlanStowageDetailsTemp();
        stowageTemp.setLoadablePlanCommingleDetails(commingleDetails);
        stowageTemp.setIsActive(true);
      }
    } else {
      LoadablePlanStowageDetails stowageDetails =
          this.loadablePlanStowageDetailsRespository.getOne(algoResponse.getId());
      stowageTemp =
          this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsAndIsActive(
              stowageDetails, true);
      if (null == stowageTemp) {
        stowageTemp = new LoadablePlanStowageDetailsTemp();
        stowageTemp.setLoadablePlanStowageDetails(stowageDetails);
        stowageTemp.setIsActive(true);
      }
    }

    stowageTemp.setCorrectedUllage(
        isEmpty(algoResponse.getCorrectedUllage())
            ? null
            : new BigDecimal(algoResponse.getCorrectedUllage()));
    stowageTemp.setCorrectionFactor(
        isEmpty(algoResponse.getCorrectionFactor())
            ? null
            : new BigDecimal(algoResponse.getCorrectionFactor()));

    stowageTemp.setQuantity(
        isEmpty(algoResponse.getQuantityMt())
            ? null
            : new BigDecimal(algoResponse.getQuantityMt()));
    stowageTemp.setRdgUllage(
        isEmpty(request.getLoadablePlanStowageDetails().getCorrectedUllage())
            ? null
            : new BigDecimal(request.getLoadablePlanStowageDetails().getCorrectedUllage()));
    stowageTemp.setIsBallast(request.getLoadablePlanStowageDetails().getIsBallast());
    stowageTemp.setIsCommingle(request.getLoadablePlanStowageDetails().getIsCommingle());
    stowageTemp.setLoadablePattern(loadablePattern);
    stowageTemp.setFillingRatio(
        isEmpty(algoResponse.getFillingRatio())
            ? null
            : new BigDecimal(algoResponse.getFillingRatio()));
    this.stowageDetailsTempRepository.save(stowageTemp);
  }

  /**
   * Call algo - ullage update api and validate the resonse
   *
   * @param algoRequest
   * @return
   * @throws GenericServiceException
   */
  private UllageUpdateResponse callAlgoUllageUpdateApi(UllageUpdateRequest algoRequest)
      throws GenericServiceException {
    ResponseEntity<UllageUpdateResponse> responseEntity =
        this.restTemplate.postForEntity(
            this.algoUpdateUllageUrl, algoRequest, UllageUpdateResponse.class);
    if (HttpStatusCode.OK.value() != responseEntity.getStatusCodeValue()) {
      throw new GenericServiceException(
          "Error calling algo: invalid status received",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return responseEntity.getBody();
  }

  /**
   * Prepare ullage upate request for algo
   *
   * @param request
   * @param loadablePattern
   * @return
   */
  private UllageUpdateRequest prepareUllageUpdateRequest(
      LoadableStudy.UpdateUllageRequest request, LoadablePattern loadablePattern) {
    UllageUpdateRequest algoRequest = new UllageUpdateRequest();
    algoRequest.setRdgUllage(request.getLoadablePlanStowageDetails().getCorrectedUllage());
    algoRequest.setId(request.getLoadablePlanStowageDetails().getId());
    algoRequest.setTankId(request.getLoadablePlanStowageDetails().getTankId());
    algoRequest.setApi(request.getLoadablePlanStowageDetails().getApi());
    algoRequest.setTemp(request.getLoadablePlanStowageDetails().getTemperature());
    Optional<SynopticalTable> synopticalTableOpt =
        synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
            loadableStudyPortRotationService.getLastPortRotationId(
                loadablePattern.getLoadableStudy(),
                this.cargoOperationRepository.getOne(LOADING_OPERATION_ID)),
            SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
            true);
    if (synopticalTableOpt.isPresent()) {
      algoRequest.setTrim(
          String.valueOf(
              synopticalTableLoadicatorDataRepository
                  .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                      synopticalTableOpt.get(), loadablePattern.getId(), true)
                  .getCalculatedTrimPlanned()));
    }
    algoRequest.setSg(request.getLoadablePlanStowageDetails().getSg());
    return algoRequest;
  }

  public LoadableStudy.LoadablePlanDetailsReply.Builder getLoadablePlanDetails(
      LoadableStudy.LoadablePlanDetailsRequest request,
      LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (!loadablePatternOpt.isPresent()) {
      log.info(INVALID_LOADABLE_PATTERN_ID, request.getLoadablePatternId());
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(INVALID_LOADABLE_PATTERN_ID)
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
    } else {
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> ls =
          loadableStudyRepository.findByIdAndIsActive(
              loadablePatternOpt.get().getLoadableStudy().getId(), true);
      boolean status = this.validateLoadableStudyForConfimPlan(ls.get());
      replyBuilder.setConfirmPlanEligibility(status);
      buildLoadablePlanDetails(loadablePatternOpt, replyBuilder);
    }
    return replyBuilder;
  }

  /**
   * This validation based on DSS-1860 If the ETA/ETD/Lay are empty for a LS, then this retunr value
   * must false.
   *
   * @return
   */
  public boolean validateLoadableStudyForConfimPlan(
      com.cpdss.loadablestudy.entity.LoadableStudy ls) {
    boolean status = true;
    Map<Long, Boolean> validationStack = new HashMap<>();
    if (ls.getPortRotations() != null && !ls.getPortRotations().isEmpty()) {
      for (LoadableStudyPortRotation pr : ls.getPortRotations()) {
        if (pr.getId() > 0) {
          if (pr.getEta() == null || pr.getEtd() == null || !isLayCanValid(pr)) {
            validationStack.put(pr.getId(), false);
          }
        }
      }
    }
    log.info(
        "Loadable Study, Validate Plan, status - {}, LS Id - {}",
        validationStack.isEmpty(),
        ls.getId());
    if (!validationStack.isEmpty()) {
      log.info(
          "Loadable Study, Validate Plan, Invalid Port Rotaion Ids - {}", validationStack.keySet());
    }
    return validationStack.isEmpty();
  }

  private boolean isLayCanValid(LoadableStudyPortRotation lsPr) {
    List ids =
        Arrays.asList(LOADING_OPERATION_ID, STS_LOADING_OPERATION_ID, STS_DISCHARGING_OPERATION_ID);
    if (ids.contains(lsPr.getOperation().getId())) {
      if (lsPr.getLayCanTo() == null || lsPr.getLayCanFrom() == null) return false;
      else return true;
    }
    return true;
  }

  public LoadableStudy.SaveCommentReply.Builder saveComment(
      LoadableStudy.SaveCommentRequest request,
      LoadableStudy.SaveCommentReply.Builder replyBuilder) {
    LoadablePlanComments entity = new LoadablePlanComments();
    entity.setComments(request.getComment());
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (loadablePatternOpt.isPresent()) {
      entity.setLoadablePattern(loadablePatternOpt.get());
    }
    entity.setCreatedBy(Long.toString(request.getUser()));

    entity.setIsActive(true);
    this.loadablePlanCommentsRepository.save(entity);
    if (entity.getId() != null) {
      LoadableStudy.SaveCommentRequest.Builder comment =
          LoadableStudy.SaveCommentRequest.newBuilder();
      comment.setComment(entity.getComments());
      comment.setCommentId(entity.getId());
      comment.setCreateDate(
          DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(entity.getCreatedDateTime()));
      comment.setUpdateDate(
          DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(entity.getLastModifiedDateTime()));
      try {
        comment.setUser(Long.valueOf(entity.getCreatedBy()));
      } catch (Exception e) {
        log.error("Failed to parse user id {}, error - {}", entity.getCreatedBy(), e.getMessage());
      }
      comment.build();
      replyBuilder.setComment(comment);
      log.info(
          "Save Comment, saved for Pattern id {}, Comment {}",
          request.getLoadablePatternId(),
          entity.getComments());
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  public LoadableStudy.UpdateUllageReply.Builder getUllage(
      LoadableStudy.UpdateUllageRequest request,
      LoadableStudy.UpdateUllageReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (!loadablePatternOpt.isPresent()) {
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setStatus(SUCCESS)
              .setCode(CommonErrorCodes.E_HTTP_ILLEGAL_URL_PARAM)
              .setMessage(INVALID_LOADABLE_PATTERN_ID)
              .build());
      return replyBuilder;
    }
    UllageUpdateResponse algoResponse =
        this.callAlgoUllageUpdateApi(
            this.prepareUllageUpdateRequest(request, loadablePatternOpt.get()));
    replyBuilder.setLoadablePlanStowageDetails(this.buildUpdateUllageReply(algoResponse, request));
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }
}
