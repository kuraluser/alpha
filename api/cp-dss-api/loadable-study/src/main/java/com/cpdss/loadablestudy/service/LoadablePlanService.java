/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADING_OPERATION_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static java.lang.String.valueOf;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternCargoDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
          Optional.ofNullable(lpq.getEstimatedTemperature())
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
}
