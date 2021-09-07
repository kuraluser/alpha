/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailStatusReply.Builder;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class DischargeInformationRPCService
    extends DischargeInformationServiceGrpc.DischargeInformationServiceImplBase {

  @Autowired DischargeInformationService dischargeInformationService;

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
}
