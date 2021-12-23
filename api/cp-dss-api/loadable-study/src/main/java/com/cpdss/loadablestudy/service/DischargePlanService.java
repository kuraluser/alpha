/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.loadablestudy.domain.LoadabalePatternDetails;
import com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest;
import com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails;
import com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DischargePlanService {

  @Autowired LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired LoadableStudyPortRotationService loadableStudyPortRotationService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;

  @Autowired
  LoadablePlanCommingleDetailsPortwiseRepository loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired
  LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired LoadablePlanService loadablePlanService;

  public void buildDischargeablePlanPortWiseDetails(
      LoadablePattern loadablePattern,
      LoadabalePatternValidateRequest loadabalePatternValidateRequest) {
    List<LoadableStudyPortRotation> entityList =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            loadablePattern.getLoadableStudy(), true);
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
    VesselInfo.VesselRequest.Builder builder = VesselInfo.VesselRequest.newBuilder();
    builder.setVesselId(loadablePattern.getLoadableStudy().getVesselXId());
    builder.addTankCategories(CARGO_TANK_CATEGORY_ID);
    builder.addTankCategories(BALLAST_TANK_CATEGORY_ID);
    VesselInfo.VesselReply vesselTanksReply = vesselInfoGrpcService.getVesselTanks(builder.build());

    List<com.cpdss.loadablestudy.domain.LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails =
        new ArrayList<LoadablePlanPortWiseDetails>();
    new ArrayList<>(entityList)
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
                  loadablePlanService.addLoadablePatternsStowageDetails(
                      loadablePatternCargoDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                              true),
                      false,
                      loadablePattern.getId()));
              departureCondition.setLoadablePlanStowageDetails(
                  loadablePlanService.addLoadablePatternsStowageDetails(
                      loadablePatternCargoDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                              true),
                      false,
                      loadablePattern.getId()));

              arrivalCondition.setLoadablePlanBallastDetails(
                  loadablePlanService.addLoadablePlanBallastDetails(
                      loadablePlanStowageBallastDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                              true),
                      false,
                      loadablePattern.getId()));
              departureCondition.setLoadablePlanBallastDetails(
                  loadablePlanService.addLoadablePlanBallastDetails(
                      loadablePlanStowageBallastDetailsRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                              true),
                      false,
                      loadablePattern.getId()));

              arrivalCondition.setLoadablePlanCommingleDetails(
                  loadablePlanService.addLoadablePlanCommingleDetails(
                      loadablePlanCommingleDetailsPortwiseRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                              true),
                      false,
                      loadablePattern.getId()));

              departureCondition.setLoadablePlanCommingleDetails(
                  loadablePlanService.addLoadablePlanCommingleDetails(
                      loadablePlanCommingleDetailsPortwiseRepository
                          .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                              loadablePattern.getId(),
                              portRotate.getId(),
                              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                              true),
                      false,
                      loadablePattern.getId()));

              // ALGO needs all ballast tanks in the request.
              addEmptytankstoCondition(arrivalCondition, vesselTanksReply);
              addEmptytankstoCondition(departureCondition, vesselTanksReply);

              portWiseDetails.setArrivalCondition(arrivalCondition);
              portWiseDetails.setDepartureCondition(departureCondition);
              loadablePlanPortWiseDetails.add(portWiseDetails);
            });
    loadabalePatternValidateRequest.setLoadablePlanPortWiseDetails(loadablePlanPortWiseDetails);
  }

  /**
   * Add empty tanks to Arrival/Departure condition of the ALGO request.
   *
   * @param portCondition
   * @param vesselTanksReply
   */
  private void addEmptytankstoCondition(
      LoadabalePatternDetails portCondition, VesselInfo.VesselReply vesselTanksReply) {
    vesselTanksReply
        .getVesselTanksList()
        .forEach(
            vesselTankDetail -> {
              if (vesselTankDetail.getTankCategoryId() == CARGO_TANK_CATEGORY_ID) {
                if (portCondition.getLoadablePlanStowageDetails().stream()
                        .filter(stowage -> stowage.getTankId() == vesselTankDetail.getTankId())
                        .collect(Collectors.toList())
                        .size()
                    == 0) {
                  LoadablePlanStowageDetails details = new LoadablePlanStowageDetails();
                  details.setId(0L);
                  details.setQuantityMT(null);
                  details.setTankId(vesselTankDetail.getTankId());
                  portCondition.getLoadablePlanStowageDetails().add(details);
                }
              } else {
                if (portCondition.getLoadablePlanBallastDetails().stream()
                        .filter(ballast -> ballast.getTankId() == vesselTankDetail.getTankId())
                        .collect(Collectors.toList())
                        .size()
                    == 0) {
                  com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails details =
                      new com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails();
                  details.setId(0L);
                  details.setQuantityMT(BigDecimal.ZERO.toString());
                  details.setTankId(vesselTankDetail.getTankId());
                  details.setColorCode(BALLAST_TANK_COLOR_CODE);
                  details.setSg(BigDecimal.ZERO.toString());
                  portCondition.getLoadablePlanBallastDetails().add(details);
                }
              }
            });
  }
}
