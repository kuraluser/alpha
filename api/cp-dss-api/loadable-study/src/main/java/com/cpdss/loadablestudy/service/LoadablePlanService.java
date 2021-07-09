/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.loadablestudy.domain.LoadabalePatternDetails;
import com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest;
import com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Master Service for Loadable Plans */
@Slf4j
@Service
public class LoadablePlanService {

  @Autowired CargoNominationRepository cargoNominationRepository;

  @Autowired LoadablePatternCargoDetailsRepository lpCargoDetailsRepository;

  @Autowired LoadableStudyPortRotationRepository portRotationRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

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
        lpCargoDetailsRepository.findAllPortRotationIdByCargoNomination(
            lpQuantity.getCargoNominationId());
    log.info("Port Rotation Ids From Loadable Plan Quantity, Size {}", lpPortRotationIds.size());
    if (!lpPortRotationIds.isEmpty()) { // all unique, because it fetch by distinct query
      for (Long id : lpPortRotationIds) {
        LoadableStudyPortRotation lsPR = portRotationRepository.findByIdAndIsActive(id, true);
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
                details.setTankId((Long) obA[2]);
                details.setQuantityMT(String.valueOf(obA[3]));
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
}
