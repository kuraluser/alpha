/* Licensed under Apache-2.0 */
package com.cpdss.loadicatorintegration.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.Loadicator.LoadicatorReply;
import com.cpdss.common.generated.Loadicator.LoadicatorRequest;
import com.cpdss.common.generated.LoadicatorServiceGrpc.LoadicatorServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadicatorintegration.entity.CargoData;
import com.cpdss.loadicatorintegration.entity.OtherTankDetails;
import com.cpdss.loadicatorintegration.entity.StowageDetails;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import com.cpdss.loadicatorintegration.repository.CargoDataRepository;
import com.cpdss.loadicatorintegration.repository.OtherTankDetailsRepository;
import com.cpdss.loadicatorintegration.repository.StowageDetailsRepository;
import com.cpdss.loadicatorintegration.repository.StowagePlanRepository;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Log4j2
@GrpcService
@Transactional
public class LoadicatorService extends LoadicatorServiceImplBase {

  @Autowired private StowagePlanRepository stowagePlanRepository;
  @Autowired private StowageDetailsRepository stowageDetailsRepository;
  @Autowired private CargoDataRepository cargoDataRepository;
  @Autowired private OtherTankDetailsRepository otherTankDetailsRepository;

  private static final String FAILED = "FAILED";
  private static final String SUCCESS = "SUCCESS";

  @Transactional
  @Override
  public void saveLoadicatorInfo(
      LoadicatorRequest request, StreamObserver<LoadicatorReply> responseObserver) {

    CargoData cargoData = new CargoData();
    OtherTankDetails otherTankDetails = new OtherTankDetails();
    StowageDetails stowageDetails = new StowageDetails();
    StowagePlan stowagePlan = new StowagePlan();
    com.cpdss.common.generated.Loadicator.LoadicatorReply.Builder replyBuilder =
        LoadicatorReply.newBuilder();
    try {
      if (request.getCargoInfo() != null) {
        cargoData = this.buildCargoData(cargoData, request);
        this.cargoDataRepository.save(cargoData);
      }

      if (request.getStowagePlanDetails() != null) {
        stowagePlan = this.buildStowagePlan(stowagePlan, request);
        stowagePlan = this.stowagePlanRepository.save(stowagePlan);
      }

      if (request.getStowageDetailsInfo() != null) {
        stowageDetails = this.buildStowageDetails(stowageDetails, request);
        this.stowageDetailsRepository.save(stowageDetails);
      }

      if (request.getOtherTankInfo() != null) {
        otherTankDetails = this.buildOtherTankInfo(otherTankDetails, request);
        this.otherTankDetailsRepository.save(otherTankDetails);
      }
      Long status = this.getStatus(stowagePlan.getBookingListId());

      if (status.equals(3L)) {
        replyBuilder
            .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
            .build();
      }
    } catch (Exception e) {
      log.error("Error saving stowage plan", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving stowage plan")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private OtherTankDetails buildOtherTankInfo(
      OtherTankDetails otherTankDetails, LoadicatorRequest request) {
    otherTankDetails.setTankId(
        StringUtils.isEmpty(request.getOtherTankInfo().getTankId())
            ? null
            : request.getStowageDetailsInfo().getTankId());
    otherTankDetails.setTankName(
        StringUtils.isEmpty(request.getOtherTankInfo().getTankName())
            ? null
            : request.getStowageDetailsInfo().getTankName());
    otherTankDetails.setQuantity(
        StringUtils.isEmpty(request.getOtherTankInfo().getQuantity())
            ? null
            : new BigDecimal(request.getStowageDetailsInfo().getQuantity()));
    otherTankDetails.setShortName(
        StringUtils.isEmpty(request.getOtherTankInfo().getShortName())
            ? null
            : request.getStowageDetailsInfo().getShortName());
    otherTankDetails.setStowagePlanId(
        StringUtils.isEmpty(request.getOtherTankInfo().getStowagePlanId())
            ? null
            : request.getStowageDetailsInfo().getStowagePlanId());
    return otherTankDetails;
  }

  private CargoData buildCargoData(CargoData cargoData, LoadicatorRequest request) {
    cargoData.setApi(
        StringUtils.isEmpty(request.getCargoInfo().getApi())
            ? null
            : new BigDecimal(request.getCargoInfo().getApi()));
    cargoData.setStowagePlanId(
        StringUtils.isEmpty(request.getCargoInfo().getStowagePlanId())
            ? null
            : request.getCargoInfo().getStowagePlanId());
    cargoData.setCargoId(
        StringUtils.isEmpty(request.getCargoInfo().getCargoId())
            ? null
            : request.getCargoInfo().getCargoId());
    cargoData.setCargoName(
        StringUtils.isEmpty(request.getCargoInfo().getCargoName())
            ? null
            : request.getCargoInfo().getCargoName());
    cargoData.setCargoAbbrev(
        StringUtils.isEmpty(request.getCargoInfo().getCargoAbbrev())
            ? null
            : request.getCargoInfo().getCargoAbbrev());
    cargoData.setStandardTemp(
        StringUtils.isEmpty(request.getCargoInfo().getStandardTemp())
            ? null
            : request.getCargoInfo().getStandardTemp());

    cargoData.setGrade(
        StringUtils.isEmpty(request.getCargoInfo().getGrade())
            ? null
            : request.getCargoInfo().getGrade());
    cargoData.setDensity(
        StringUtils.isEmpty(request.getCargoInfo().getDensity())
            ? null
            : new BigDecimal(request.getCargoInfo().getDensity()));
    cargoData.setDegc(
        StringUtils.isEmpty(request.getCargoInfo().getDegc())
            ? null
            : new BigDecimal(request.getCargoInfo().getDegc()));
    cargoData.setDegf(
        StringUtils.isEmpty(request.getCargoInfo().getDegf())
            ? null
            : new BigDecimal(request.getCargoInfo().getDegf()));
    return cargoData;
  }

  private StowagePlan buildStowagePlan(StowagePlan stowagePlan, LoadicatorRequest request) {
    stowagePlan.setVesselXId(
        StringUtils.isEmpty(request.getStowagePlanDetails().getVesselId())
            ? null
            : request.getStowagePlanDetails().getVesselId());
    stowagePlan.setImoNumber(
        StringUtils.isEmpty(request.getStowagePlanDetails().getImoNumber())
            ? null
            : request.getStowagePlanDetails().getImoNumber());
    stowagePlan.setBookingListId(
        StringUtils.isEmpty(request.getStowagePlanDetails().getBookingListId())
            ? null
            : request.getStowagePlanDetails().getBookingListId());
    stowagePlan.setCalCount(
        StringUtils.isEmpty(request.getStowagePlanDetails().getCalCount())
            ? null
            : request.getStowagePlanDetails().getCalCount());
    stowagePlan.setCompanyXId(
        StringUtils.isEmpty(request.getStowagePlanDetails().getCompanyId())
            ? null
            : request.getStowagePlanDetails().getCompanyId());
    stowagePlan.setDamageCal(
        StringUtils.isEmpty(request.getStowagePlanDetails().getDamageCal())
            ? null
            : request.getStowagePlanDetails().getDamageCal());
    stowagePlan.setDataSave(
        StringUtils.isEmpty(request.getStowagePlanDetails().getDataSave())
            ? null
            : request.getStowagePlanDetails().getDataSave());
    stowagePlan.setDeadweightConstant(
        StringUtils.isEmpty(request.getStowagePlanDetails().getDeadweightConstant())
            ? null
            : request.getStowagePlanDetails().getDeadweightConstant());
    stowagePlan.setPortCode(
        StringUtils.isEmpty(request.getStowagePlanDetails().getPortCode())
            ? null
            : request.getStowagePlanDetails().getPortCode());

    stowagePlan.setPortId(
        StringUtils.isEmpty(request.getStowagePlanDetails().getPortId())
            ? null
            : request.getStowagePlanDetails().getPortId());
    stowagePlan.setProvisionalConstant(
        StringUtils.isEmpty(request.getStowagePlanDetails().getProvisionalConstant())
            ? null
            : request.getStowagePlanDetails().getProvisionalConstant());

    stowagePlan.setSaveMessage(
        StringUtils.isEmpty(request.getStowagePlanDetails().getSaveMessage())
            ? null
            : request.getStowagePlanDetails().getSaveMessage());
    stowagePlan.setShipType(
        StringUtils.isEmpty(request.getStowagePlanDetails().getShipType())
            ? null
            : request.getStowagePlanDetails().getShipType());
    stowagePlan.setStatus(
        StringUtils.isEmpty(request.getStowagePlanDetails().getStatus())
            ? null
            : request.getStowagePlanDetails().getStatus());
    stowagePlan.setVesselCode(
        StringUtils.isEmpty(request.getStowagePlanDetails().getVesselCode())
            ? null
            : request.getStowagePlanDetails().getVesselCode());
    return stowagePlan;
  }

  private StowageDetails buildStowageDetails(
      StowageDetails stowageDetails, LoadicatorRequest request) {
    stowageDetails.setCargoBookId(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getCargoBookId())
            ? null
            : request.getStowageDetailsInfo().getCargoBookId());
    stowageDetails.setCargoId(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getCargoId())
            ? null
            : request.getStowageDetailsInfo().getCargoId());
    stowageDetails.setCargoName(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getCargoName())
            ? null
            : request.getStowageDetailsInfo().getCargoName());
    stowageDetails.setQuantity(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getQuantity())
            ? null
            : new BigDecimal(request.getStowageDetailsInfo().getQuantity()));
    stowageDetails.setShortName(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getShortName())
            ? null
            : request.getStowageDetailsInfo().getShortName());
    stowageDetails.setSpecificGravity(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getSpecificGravity())
            ? null
            : new BigDecimal(request.getStowageDetailsInfo().getSpecificGravity()));
    stowageDetails.setStowagePlanId(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getStowagePlanId())
            ? null
            : request.getStowageDetailsInfo().getStowagePlanId());
    stowageDetails.setTankId(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getTankId())
            ? null
            : request.getStowageDetailsInfo().getTankId());
    stowageDetails.setTankName(
        StringUtils.isEmpty(request.getStowageDetailsInfo().getTankName())
            ? null
            : request.getStowageDetailsInfo().getTankName());
    return stowageDetails;
  }

  @Scheduled(fixedRateString = "${loadicator.scheduler.interval}")
  public Long getStatus(Long bookingListId) {
    Long status = null;
    if (!bookingListId.equals(null)) {
      Optional<StowagePlan> stowagePlan =
          this.stowagePlanRepository.findByBookingListId(bookingListId);

      if (stowagePlan.isPresent()) status = stowagePlan.get().getStatus();
    }
    return status;
  }
}
