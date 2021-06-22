package com.cpdss.loadingplan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class LoadingInformationAlgoService {

	@GrpcClient("loadingInformationService")
	private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
	      loadingInfoServiceBlockingStub;
	
	@GrpcClient("loadableStudyService")
	  private LoadableStudyServiceBlockingStub loadableStudyService;
	
	@Autowired LoadingInformationRepository loadingInformationRepository;
	
	public void createAlgoRequest(Long loadingInformationId) {
		Optional<com.cpdss.loadingplan.entity.LoadingInformation> loadingInfoOpt = this.loadingInformationRepository.findByIdAndIsActiveTrue(loadingInformationId);
		if (loadingInfoOpt.isPresent()) {
			try {
				getLoadingInformation(loadingInfoOpt.get().getVesselXId(), loadingInfoOpt.get().getVoyageId(), loadingInformationId, loadingInfoOpt.get().getLoadablePatternXId(), loadingInfoOpt.get().getPortRotationXId());
				getLoadablePatternPortWiseDetails(loadingInfoOpt.get().getLoadablePatternXId());
			} catch (GenericServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private LoadingInformation getLoadingInformation(Long vesselId, Long voyageId, Long loadingInfoId, Long patternId, Long portRotationId) throws GenericServiceException {
		LoadingPlanModels.LoadingInformationRequest.Builder builder =
		        LoadingPlanModels.LoadingInformationRequest.newBuilder();
		    builder.setVesselId(vesselId);
		    builder.setVoyageId(voyageId);
		    builder.setLoadingPlanId(loadingInfoId);
		    if (patternId != null) builder.setLoadingPatternId(patternId);
		    if (portRotationId != null) builder.setPortRotationId(portRotationId);
		    LoadingPlanModels.LoadingInformation reply =
		        loadingInfoServiceBlockingStub.getLoadingInformation(builder.build());
		    System.out.println();
		    if (!reply.getResponseStatus().getStatus().equals("SUCCESS")) {
		      throw new GenericServiceException(
		          "Failed to get Loading Information",
		          CommonErrorCodes.E_HTTP_BAD_REQUEST,
		          HttpStatusCode.BAD_REQUEST);
		    }
		    
		    return reply;
	}
	
	private void getLoadablePatternPortWiseDetails(Long loadablePatternId) throws GenericServiceException {
		LoadablePlanDetailsRequest.Builder requestBuilder = LoadablePlanDetailsRequest.newBuilder();
		requestBuilder.setLoadablePatternId(loadablePatternId);
		LoadablePatternPortWiseDetailsJson response = this.loadableStudyService.getLoadablePatternDetailsJson(requestBuilder.build());
		if (!response.getResponseStatus().getStatus().equals("SUCCESS")) {
		      throw new GenericServiceException(
		          "Failed to get Loading Information",
		          CommonErrorCodes.E_HTTP_BAD_REQUEST,
		          HttpStatusCode.BAD_REQUEST);
		}
		System.out.println(response.getLoadablePatternDetails());
	}
}
