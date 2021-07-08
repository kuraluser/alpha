/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceImplBase;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingInstructionService extends LoadingInstructionServiceImplBase {

	@Autowired LoadingIR
	@Override
	public void getLoadingInstructions(LoadingInstructionRequest request,
			StreamObserver<LoadingInstructionDetails> responseObserver) {
		
		LoadingInstructionDetails.Builder responseBuilder = LoadingInstructionDetails.newBuilder();
		
		//Fetch data from DB
		
		responseObserver.onNext(responseBuilder.build());
		responseObserver.onCompleted();
	}

}
