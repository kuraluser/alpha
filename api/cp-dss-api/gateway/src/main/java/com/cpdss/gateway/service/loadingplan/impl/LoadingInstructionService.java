/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.loadingplan.*;
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
	private LoadingInstructionServiceBlockingStub loadingInstructionServiceBlockingStub;

	public LoadingInstructionDetails getLoadingInstructions(Long vesselId, Long infoId, Long portRotationId)
			throws GenericServiceException {
		try {
			log.info("Calling getLoadingInstructions in loading-plan microservice via GRPC");
			LoadingInstructionRequest.Builder requestBuilber = LoadingInstructionRequest.newBuilder();
			requestBuilber.setLoadingInfoId(infoId).setPortRotationId(portRotationId).setVesselId(vesselId);
			LoadingInstructionDetails response = loadingInstructionServiceBlockingStub
					.getLoadingInstructions(requestBuilber.build());
			
			if (response.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
				return response;
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

}
