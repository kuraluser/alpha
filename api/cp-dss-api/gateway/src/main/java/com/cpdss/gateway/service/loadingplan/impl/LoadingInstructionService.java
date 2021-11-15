/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionGroup;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionSubHeader;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructions;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsStatus;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsUpdateRequest;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * Loading Instructions related services
 *
 * @author Sanal
 * @since 07-07-2021
 */
@Slf4j
@Service
public class LoadingInstructionService {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @GrpcClient("loadingInformationService")
  public LoadingInstructionServiceBlockingStub loadingInstructionServiceBlockingStub;

  public LoadingInstructionResponse getLoadingInstructions(
      Long vesselId, Long infoId, Long portRotationId) throws GenericServiceException {
    try {
      log.info("Calling getLoadingInstructions in loading-plan microservice via GRPC");
      LoadingInstructionRequest.Builder requestBuilder = LoadingInstructionRequest.newBuilder();
      requestBuilder
          .setLoadingInfoId(infoId)
          .setPortRotationId(portRotationId)
          .setVesselId(vesselId);
      LoadingInstructionDetails grpcResponse =
          loadingInstructionServiceBlockingStub.getLoadingInstructions(requestBuilder.build());

      if (grpcResponse.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return this.buildResponseModel(grpcResponse);
      } else {
        log.error(
            "Failed to retrieve loading instructions of  vesselID: {} on port: {}",
            vesselId,
            portRotationId);
        throw new GenericServiceException(
            "Failed to retrieve loading instructions",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error(
          "Failed to retrieve loading instructions of  vesselID: {} on port: {}",
          vesselId,
          portRotationId);
      throw new GenericServiceException(
          "Failed to retrieve loading instructions",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private LoadingInstructionResponse buildResponseModel(LoadingInstructionDetails grpcResponse) {
    LoadingInstructionResponse responseModel = new LoadingInstructionResponse();
    responseModel.setLoadingInstructionGroupList(
        grpcResponse.getLoadingInstructionGroupListList().stream()
            .map(item -> new LoadingInstructionGroup(item.getGroupId(), item.getGroupName()))
            .collect(Collectors.toList()));
    responseModel.setLoadingInstructionSubHeader(
        grpcResponse.getLoadingInstructionSubHeaderList().stream()
            .map(
                item ->
                    new LoadingInstructionSubHeader(
                        item.getInstructionTypeId(),
                        item.getInstructionHeaderId(),
                        item.getSubHeaderId(),
                        item.getSubHeaderName(),
                        item.getIsChecked(),
                        item.getIsEditable(),
                        item.getIsHeaderInstruction(),
                        item.getLoadingInstructionsListList().stream()
                            .map(
                                subItem ->
                                    new LoadingInstructions(
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

  public LoadingInstructionsSaveResponse addLoadingInstruction(
      Long vesselId, Long infoId, Long portRotationId, LoadingInstructionsSaveRequest request)
      throws GenericServiceException {

    try {
      log.info("Calling addLoadingInstruction in loading-plan microservice via GRPC");
      LoadingInstructionsSave.Builder requestBuilder = LoadingInstructionsSave.newBuilder();
      Optional.ofNullable(request.getInstructionHeaderId()).ifPresent(requestBuilder::setHeaderId);
      Optional.ofNullable(request.getInstruction()).ifPresent(requestBuilder::setInstruction);
      Optional.ofNullable(request.getInstructionTypeId())
          .ifPresent(requestBuilder::setInstructionTypeId);
      Optional.ofNullable(request.getIsChecked()).ifPresent(requestBuilder::setIsChecked);
      Optional.ofNullable(request.getIsSingleHeader()).ifPresent(requestBuilder::setIsSingleHeader);
      Optional.ofNullable(request.getIsSubHeader()).ifPresent(requestBuilder::setIsSubHeader);
      Optional.ofNullable(request.getSubHeaderId()).ifPresent(requestBuilder::setSubHeaderId);
      Optional.ofNullable(vesselId).ifPresent(requestBuilder::setVesselId);
      Optional.ofNullable(infoId).ifPresent(requestBuilder::setLoadingInfoId);
      Optional.ofNullable(portRotationId).ifPresent(requestBuilder::setPortRotationId);

      ResponseStatus grpcResponse =
          this.loadingInstructionServiceBlockingStub.addLoadingInstruction(requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", ""));
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

  public LoadingInstructionsSaveResponse updateLoadingInstructions(
      Long vesselId, Long infoId, Long portRotationId, LoadingInstructionsUpdateRequest request)
      throws GenericServiceException {
    try {
      log.info("Calling updateLoadingInstructions in loading-plan microservice via GRPC");
      LoadingInstructionsUpdate.Builder requestBuilder = LoadingInstructionsUpdate.newBuilder();
      Optional.ofNullable(request.getInstructionList())
          .ifPresent(
              instructionList ->
                  requestBuilder.addAllInstructionList(
                      instructionList.stream()
                          .map(
                              item ->
                                  LoadingInstructionStatus.newBuilder()
                                      .setInstructionId(item.getInstructionId())
                                      .setIsChecked(item.getIsChecked())
                                      .build())
                          .collect(Collectors.toList())));

      ResponseStatus grpcResponse =
          this.loadingInstructionServiceBlockingStub.updateLoadingInstructions(
              requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", ""));
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

  public LoadingInstructionsSaveResponse deleteLoadingInstructions(
      Long vesselId, Long infoId, Long portRotationId, LoadingInstructionsStatus request)
      throws GenericServiceException {
    try {
      log.info("Calling deleteLoadingInstructions in loading-plan microservice via GRPC");
      LoadingInstructionStatus.Builder requestBuilder = LoadingInstructionStatus.newBuilder();
      Optional.ofNullable(request.getInstructionId()).ifPresent(requestBuilder::setInstructionId);

      ResponseStatus grpcResponse =
          this.loadingInstructionServiceBlockingStub.deleteLoadingInstructions(
              requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", ""));
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

  public LoadingInstructionsSaveResponse editLoadingInstructions(
      Long vesselId, Long infoId, Long portRotationId, LoadingInstructionsStatus request)
      throws GenericServiceException {
    try {
      log.info("Calling editLoadingInstructions in loading-plan microservice via GRPC");
      LoadingInstructionStatus.Builder requestBuilder = LoadingInstructionStatus.newBuilder();
      Optional.ofNullable(request.getInstructionId()).ifPresent(requestBuilder::setInstructionId);
      Optional.ofNullable(request.getInstruction()).ifPresent(requestBuilder::setInstruction);

      ResponseStatus grpcResponse =
          this.loadingInstructionServiceBlockingStub.editInstructions(requestBuilder.build());
      if (grpcResponse.getStatus().equalsIgnoreCase(SUCCESS)) {
        log.info("GRPC call successfull");
        return new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", ""));
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
}
