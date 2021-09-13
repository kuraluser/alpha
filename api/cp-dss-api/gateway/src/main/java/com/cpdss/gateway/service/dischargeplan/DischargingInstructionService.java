/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionServiceGrpc.DischargingInstructionServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionGroup;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionSubHeader;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructions;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsStatus;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsUpdateRequest;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargingInstructionService {

  @GrpcClient("dischargeInformationService")
  private DischargingInstructionServiceBlockingStub dischargingInstructionServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  public DischargingInstructionsSaveResponse addDischargingInstruction(
      Long vesselId, Long infoId, Long portRotationId, DischargingInstructionsSaveRequest request)
      throws GenericServiceException {

    try {
      log.info("Calling addDischargingInstruction in Discharging-plan microservice via GRPC");
      DischargingInstructionsSave.Builder requestBuilder = DischargingInstructionsSave.newBuilder();
      Optional.ofNullable(request.getInstructionHeaderId()).ifPresent(requestBuilder::setHeaderId);
      Optional.ofNullable(request.getInstruction()).ifPresent(requestBuilder::setInstruction);
      Optional.ofNullable(request.getInstructionTypeId())
          .ifPresent(requestBuilder::setInstructionTypeId);
      Optional.ofNullable(request.getIsChecked()).ifPresent(requestBuilder::setIsChecked);
      Optional.ofNullable(request.getIsSingleHeader()).ifPresent(requestBuilder::setIsSingleHeader);
      Optional.ofNullable(request.getIsSubHeader()).ifPresent(requestBuilder::setIsSubHeader);
      Optional.ofNullable(request.getSubHeaderId()).ifPresent(requestBuilder::setSubHeaderId);
      Optional.ofNullable(vesselId).ifPresent(requestBuilder::setVesselId);
      Optional.ofNullable(infoId).ifPresent(requestBuilder::setDischargingInfoId);
      Optional.ofNullable(portRotationId).ifPresent(requestBuilder::setPortRotationId);

      ResponseStatus grpcResponse =
          this.dischargingInstructionServiceBlockingStub.addDischargingInstruction(
              requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, ""));
      } else {
        log.error("Failed to save new instruction");
        throw new GenericServiceException(
            "Failed to add new instruction",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to save new instruction {} ", e);
      throw new GenericServiceException(
          "Failed to add new instruction",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public DischargingInstructionsSaveResponse updateDischargingInstructions(
      Long vesselId, Long infoId, Long portRotationId, DischargingInstructionsUpdateRequest request)
      throws GenericServiceException {
    try {
      log.info("Calling updateDischargingInstructions in discharging-plan microservice via GRPC");
      DischargingInstructionsUpdate.Builder requestBuilder =
          DischargingInstructionsUpdate.newBuilder();
      Optional.ofNullable(request.getInstructionList())
          .ifPresent(
              instructionList ->
                  requestBuilder.addAllInstructionList(
                      instructionList.stream()
                          .map(
                              item ->
                                  DischargingInstructionStatus.newBuilder()
                                      .setInstructionId(item.getInstructionId())
                                      .setIsChecked(item.getIsChecked())
                                      .build())
                          .collect(Collectors.toList())));

      ResponseStatus grpcResponse =
          this.dischargingInstructionServiceBlockingStub.updateDischargingInstructions(
              requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, ""));
      } else {
        log.error("Failed to update instruction status - GRPC failed");
        throw new GenericServiceException(
            "Failed to update instruction status",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to update instruction status {} ", e);
      throw new GenericServiceException(
          "Failed to update instruction status",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public DischargingInstructionsSaveResponse editDischargingInstructions(
      Long vesselId, Long infoId, Long portRotationId, DischargingInstructionsStatus request)
      throws GenericServiceException {
    try {
      log.info("Calling editDischargingInstructions in discharging-plan microservice via GRPC");
      DischargingInstructionStatus.Builder requestBuilder =
          DischargingInstructionStatus.newBuilder();
      Optional.ofNullable(request.getInstructionId()).ifPresent(requestBuilder::setInstructionId);
      Optional.ofNullable(request.getInstruction()).ifPresent(requestBuilder::setInstruction);

      ResponseStatus grpcResponse =
          this.dischargingInstructionServiceBlockingStub.editInstructions(requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new DischargingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", ""));
      } else {
        log.error("Failed to edit instruction - GRPC failed");
        throw new GenericServiceException(
            "Failed to edit instruction",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to edit instruction {} ", e);
      throw new GenericServiceException(
          "Failed to edit instruction",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public DischargingInstructionResponse getDischargingInstructions(
      Long vesselId, Long infoId, Long portRotationId) throws GenericServiceException {
    try {
      log.info("Calling getDischargingInstructions in discharging-plan microservice via GRPC");
      DischargingInstructionRequest.Builder requestBuilder =
          DischargingInstructionRequest.newBuilder();
      requestBuilder
          .setDischargingInfoId(infoId)
          .setPortRotationId(portRotationId)
          .setVesselId(vesselId);
      DischargingInstructionDetails grpcResponse =
          dischargingInstructionServiceBlockingStub.getDischargingInstructions(
              requestBuilder.build());

      if (grpcResponse.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return this.buildResponseModel(grpcResponse);
      } else {
        log.error(
            "Failed to retrieve discharging instructions of  vesselID: {} on port: {}",
            vesselId,
            portRotationId);
        throw new GenericServiceException(
            "Failed to retrieve discharging instructions",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error(
          "Failed to retrieve discharging instructions of  vesselID: {} on port: {}",
          vesselId,
          portRotationId);
      throw new GenericServiceException(
          "Failed to retrieve discharging instructions",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private DischargingInstructionResponse buildResponseModel(
      DischargingInstructionDetails grpcResponse) {
    DischargingInstructionResponse responseModel = new DischargingInstructionResponse();
    responseModel.setDischargingInstructionGroupList(
        grpcResponse.getDischargingInstructionGroupListList().stream()
            .map(item -> new DischargingInstructionGroup(item.getGroupId(), item.getGroupName()))
            .collect(Collectors.toList()));
    responseModel.setDischargingInstructionSubHeader(
        grpcResponse.getDischargingInstructionSubHeaderList().stream()
            .map(
                item ->
                    new DischargingInstructionSubHeader(
                        item.getInstructionTypeId(),
                        item.getInstructionHeaderId(),
                        item.getSubHeaderId(),
                        item.getSubHeaderName(),
                        item.getIsChecked(),
                        item.getIsEditable(),
                        item.getIsHeaderInstruction(),
                        item.getDischargingInstructionsListList().stream()
                            .map(
                                subItem ->
                                    new DischargingInstructions(
                                        subItem.getInstructionTypeId(),
                                        subItem.getInstructionHeaderId(),
                                        subItem.getInstructionId(),
                                        subItem.getInstruction(),
                                        subItem.getIsChecked(),
                                        subItem.getIsEditable()))
                            .collect(Collectors.toList())))
            .collect(Collectors.toList()));
    return responseModel;
  }

  public DischargingInstructionsSaveResponse deleteDischargingInstructions(
      Long vesselId, Long infoId, Long portRotationId, DischargingInstructionsStatus request)
      throws GenericServiceException {
    try {
      log.info("Calling deleteDischargingInstructions in discharging-plan microservice via GRPC");
      DischargingInstructionStatus.Builder requestBuilder =
          DischargingInstructionStatus.newBuilder();
      Optional.ofNullable(request.getInstructionId()).ifPresent(requestBuilder::setInstructionId);

      ResponseStatus grpcResponse =
          this.dischargingInstructionServiceBlockingStub.deleteDischargingInstructions(
              requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new DischargingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", ""));
      } else {
        log.error("Failed to delete instruction - GRPC failed");
        throw new GenericServiceException(
            "Failed to delete instruction",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("Failed to delete instruction {} ", e);
      throw new GenericServiceException(
          "Failed to delete instruction",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }
}
