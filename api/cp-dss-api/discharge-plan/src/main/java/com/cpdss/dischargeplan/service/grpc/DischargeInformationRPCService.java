/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.DischargeInformation;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeRuleReply;
import com.cpdss.common.generated.discharge_plan.DischargeRuleRequest;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailRequest;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailStatusReply;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailStatusReply.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingPlanReply;
import com.cpdss.common.generated.discharge_plan.DischargingUploadTideDetailRequest;
import com.cpdss.common.generated.discharge_plan.DischargingUploadTideDetailStatusReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeStageDurationRepository;
import com.cpdss.dischargeplan.repository.DischargeStageMinAmountRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayReasonRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanBallastDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanRobDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStabilityParametersRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageDetailsRepository;
import com.cpdss.dischargeplan.service.DischargeInformationBuilderService;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import io.grpc.stub.StreamObserver;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class DischargeInformationRPCService
    extends DischargeInformationServiceGrpc.DischargeInformationServiceImplBase {

  @Autowired DischargeInformationService dischargeInformationService;
  @Autowired DischargeInformationBuilderService informationBuilderService;
  @Autowired DischargeBerthDetailRepository dischargeBerthDetailRepository;
  @Autowired DischargeStageMinAmountRepository stageMinAmountRepository;
  @Autowired DischargeStageDurationRepository dischargeStageDurationRepository;
  @Autowired DischargingDelayReasonRepository dischargingDelayReasonRepository;
  @Autowired DischargingDelayRepository dischargingDelayRepository;
  @Autowired PortDischargingPlanBallastDetailsRepository pdpBallastDetailsRepository;
  @Autowired PortDischargingPlanStowageDetailsRepository pdpStowageDetailsRepository;
  @Autowired PortDischargingPlanRobDetailsRepository pdpRobDetailsRepository;
  @Autowired PortDischargingPlanStabilityParametersRepository pdpStabilityParametersRepository;

  @Override
  public void getDischargeInformation(
      DischargeInformationRequest request, StreamObserver<DischargeInformation> responseObserver) {
    DischargeInformation.Builder builder = DischargeInformation.newBuilder();
    try {
      log.info("Get Discharge Info Request Payload \n{}", Utils.toJson(request));
      this.dischargeInformationService.getDischargeInformation(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getOrSaveRulesForDischarging(
      DischargeRuleRequest request, StreamObserver<DischargeRuleReply> responseObserver) {
    DischargeRuleReply.Builder builder = DischargeRuleReply.newBuilder();
    try {
      // log.info("Get or dave Discharge rule \n{}", Utils.toJson(request));
      this.dischargeInformationService.getOrSaveRulesForDischarge(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void dischargingUploadPortTideDetails(
      DischargingUploadTideDetailRequest request,
      StreamObserver<DischargingUploadTideDetailStatusReply> responseObserver) {
    DischargingUploadTideDetailStatusReply.Builder uploadTideDetailStatusReplyBuilder =
        DischargingUploadTideDetailStatusReply.newBuilder();
    try {
      this.dischargeInformationService.uploadPortTideDetails(request);
      uploadTideDetailStatusReplyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS));
    } catch (GenericServiceException e) {
      uploadTideDetailStatusReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(DischargePlanConstants.FAILED)
              .setMessage(e.getMessage())
              .setCode(e.getCode())
              .setHttpStatusCode(e.getStatus().value()));
    } catch (Exception e) {
      uploadTideDetailStatusReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.FAILED));
    } finally {
      responseObserver.onNext(uploadTideDetailStatusReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void dischargingDownloadPortTideDetails(
      DischargingDownloadTideDetailRequest request,
      StreamObserver<DischargingDownloadTideDetailStatusReply> responseObserver) {
    Builder builder = DischargingDownloadTideDetailStatusReply.newBuilder();
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      this.dischargeInformationService.downloadPortTideDetails(workbook, request, builder);
    } catch (Exception e) {
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(DischargePlanConstants.FAILED)
              .setMessage(e.getMessage())
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getDischargingPlan(
      DischargeInformationRequest request, StreamObserver<DischargingPlanReply> responseObserver) {
    DischargingPlanReply.Builder builder = DischargingPlanReply.newBuilder();
    try {

      com.cpdss.dischargeplan.entity.DischargeInformation disEntity;
      if (request.getDischargeInfoId() > 0) {
        disEntity =
            dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      } else {
        disEntity =
            dischargeInformationService.getDischargeInformation(
                request.getVesselId(), request.getVoyageId(), request.getPortRotationId());
      }

      if (disEntity != null) {

        // <---discharging Information Start-->
        DischargeInformation.Builder dischargingInformation = DischargeInformation.newBuilder();
        dischargingInformation.setDischargeInfoId(disEntity.getId());
        dischargingInformation.setSynopticTableId(disEntity.getSynopticTableXid());
        // Set Discharge Rates
        this.informationBuilderService.buildDischargeRateMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Discharge berth
        List<DischargingBerthDetail> listVarB =
            this.dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(
                disEntity.getId());
        this.informationBuilderService.buildDischargeBerthMessageFromEntity(
            disEntity, listVarB, dischargingInformation);

        // Set Stages
        this.informationBuilderService.buildDischargeStageMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Delay
        this.informationBuilderService.buildDischargeDelaysMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Post Discharge stage
        this.informationBuilderService.buildPostDischargeStageMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Cow Details
        this.informationBuilderService.buildCowPlanMessageFromEntity(
            disEntity, dischargingInformation);
        builder.setDischargingInformation(dischargingInformation.build());

        // <---Cargo Details Start-->
        List<PortDischargingPlanBallastDetails> pdpBallastList =
            pdpBallastDetailsRepository.findByDischargingInformationAndIsActive(disEntity, true);
        List<PortDischargingPlanStowageDetails> pdpStowageList =
            pdpStowageDetailsRepository.findByDischargingInformationAndIsActive(disEntity, true);
        List<PortDischargingPlanRobDetails> pdpRobList =
            pdpRobDetailsRepository.findByDischargingInformationAndIsActive(
                disEntity.getId(), true);
        List<PortDischargingPlanStabilityParameters> pdpStabilityList =
            pdpStabilityParametersRepository.findByDischargingInformationAndIsActive(
                disEntity, true);

        builder.addAllPortDischargingPlanBallastDetails(
            this.informationBuilderService.buildDischargingPlanTankBallastMessage(pdpBallastList));
        builder.addAllPortDischargingPlanStowageDetails(
            this.informationBuilderService.buildDischargingPlanTankStowageMessage(pdpStowageList));
        builder.addAllPortDischargingPlanRobDetails(
            this.informationBuilderService.buildDischargingPlanTankRobMessage(pdpRobList));
        builder.addAllPortDischargingPlanStabilityParameters(
            this.informationBuilderService.buildDischargingPlanTankStabilityMessage(
                pdpStabilityList));
      } else {
        log.error("Failed to fetch Loading Plan, Loading info Id is 0");
        throw new GenericServiceException(
            "Loading Info Id Is 0",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
      builder.build();

    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
