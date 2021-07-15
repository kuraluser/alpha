/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionGroup;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionSubHeader;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructions;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveResponse;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.stream.Collectors;

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

	@GrpcClient("loadingInstructionService")
	private LoadingInstructionServiceBlockingStub loadingInstructionServiceBlockingStub;

	public LoadingInstructionResponse getLoadingInstructions(Long vesselId, Long infoId, Long portRotationId)
			throws GenericServiceException {
		try {
			log.info("Calling getLoadingInstructions in loading-plan microservice via GRPC");
			LoadingInstructionRequest.Builder requestBuilber = LoadingInstructionRequest.newBuilder();
			requestBuilber.setLoadingInfoId(infoId).setPortRotationId(portRotationId).setVesselId(vesselId);
			LoadingInstructionDetails grpcResponse = loadingInstructionServiceBlockingStub
					.getLoadingInstructions(requestBuilber.build());

			if (grpcResponse.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
				log.info("GRPC call successfull");
				return this.buildResponseModel(grpcResponse);
			} else {
				log.error("Failed to retrieve loading instructions of  vesselID: {} on port: {}", vesselId,
						portRotationId);
				throw new GenericServiceException("Failed to retrieve loading instructions",
						CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Failed to retrieve loading instructions of  vesselID: {} on port: {}", vesselId, portRotationId);
			throw new GenericServiceException("Failed to retrieve loading instructions",
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		}
	}

	private LoadingInstructionResponse buildResponseModel(LoadingInstructionDetails grpcResponse) {
		LoadingInstructionResponse responseModel = new LoadingInstructionResponse();
		responseModel.setLoadingInstructionGroupList(grpcResponse.getLoadingInstructionGroupListList().stream()
				.map(item -> new LoadingInstructionGroup(item.getGroupId(), item.getGroupName()))
				.collect(Collectors.toList()));
		responseModel.setLoadingInstructionSubHeader(grpcResponse.getLoadingInstructionSubHeaderList().stream()
				.map(item -> new LoadingInstructionSubHeader(item.getInstructionTypeId(), item.getInstructionHeaderId(),
						item.getSubHeaderId(), item.getSubHeaderName(), item.getIsChecked(),
						item.getLoadingInstructionsListList().stream()
								.map(subItem -> new LoadingInstructions(subItem.getInstructionTypeId(),
										subItem.getInstructionHeaderId(), subItem.getInstructionId(),
										subItem.getInstruction(), subItem.getIsChecked()))
								.collect(Collectors.toList())))
				.collect(Collectors.toList()));
		return responseModel;
	}

	public LoadingInstructionsSaveResponse addLoadingInstructionsSubHeader(LoadingInstructionsSaveRequest request) {
		
		try {
			log.info("Calling addLoadingInstructionsSubHeader in loading-plan microservice via GRPC");
			LoadingInstructionRequest.Builder requestBuilber = LoadingInstructionRequest.newBuilder();
			requestBuilber.setLoadingInfoId(infoId).setPortRotationId(portRotationId).setVesselId(vesselId);
			LoadingInstructionDetails grpcResponse = loadingInstructionServiceBlockingStub
					.getLoadingInstructions(requestBuilber.build());

			if (grpcResponse.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
				log.info("GRPC call successfull");
				return new LoadingInstructionsSaveResponse();;
			} else {
				log.error("Failed to save Sub header instruction");
				throw new GenericServiceException("Failed to retrieve loading instructions",
						CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Failed to retrieve loading instructions of  vesselID: {} on port: {}", vesselId, portRotationId);
			throw new GenericServiceException("Failed to retrieve loading instructions",
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		}
	}

}
