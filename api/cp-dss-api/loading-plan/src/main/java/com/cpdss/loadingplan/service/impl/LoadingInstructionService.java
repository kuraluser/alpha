/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceImplBase;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionSubHeader;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructions;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInstruction;
import com.cpdss.loadingplan.entity.LoadingInstructionTemplate;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionTemplateRepository;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.FAILED;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingInstructionService extends LoadingInstructionServiceImplBase {

	@Autowired
	LoadingInstructionRepository loadingInstructionRepository;

	@Autowired
	LoadingInstructionTemplateRepository loadingInstructionTemplateRepository;

	@Autowired
	LoadingInformationRepository loadingInformationRepository;

	@Autowired
	LoadingInformationServiceImpl loadingInformationServiceImpl;

	@Override
	public void getLoadingInstructions(LoadingInstructionRequest request,
			StreamObserver<LoadingInstructionDetails> responseObserver) {

		LoadingInstructionDetails.Builder response = LoadingInstructionDetails.newBuilder();
		Common.ResponseStatus.Builder responseStatus = Common.ResponseStatus.newBuilder();
		responseStatus.setStatus(FAILED);

		Optional<LoadingInformation> loadingInformation = loadingInformationServiceImpl.getLoadingInformation(
				request.getLoadingInfoId(), request.getVesselId(), null, null, request.getPortRotationId());

		if (!loadingInformation.isPresent()) {
			response.setResponseStatus(responseStatus.build());
		}

		final Long PortXId = loadingInformation.get().getPortXId();

		if (!loadingInstructionRepository.findAny(request.getLoadingInfoId())) {
			List<LoadingInstructionTemplate> templateList = loadingInstructionTemplateRepository
					.getCommonInstructionTemplate(PortXId);
			List<LoadingInstruction> instructionList = templateList.stream()
					.map(template -> new LoadingInstruction(template.getLoading_instruction(),
							template.getParentInstructionXId(), template.getLoading_insruction_typexid().getId(),
							template.getLoadingInstructionHeaderXId().getId(), true, template.getReferenceXId(),
							template.getIsActive(), template.getId(), request.getLoadingInfoId()))
					.collect(Collectors.toList());
			loadingInstructionRepository.saveAll(instructionList);
		}

		List<LoadingInstruction> listLoadingInstruction = loadingInstructionRepository.getAllLoadingInstructions(
				request.getVesselId(), request.getLoadingInfoId(), request.getPortRotationId());

		response = buildLoadingInstructionDetails(listLoadingInstruction);
		response.setResponseStatus(responseStatus.setStatus(SUCCESS));
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}

	private Builder buildLoadingInstructionDetails(List<LoadingInstruction> listLoadingInstruction) {

		List<LoadingInstruction> subHeaderList = listLoadingInstruction.stream()
				.filter(item -> item.getParentInstructionXId() == null).collect(Collectors.toList());

		List<LoadingInstruction> instructionList = listLoadingInstruction.stream()
				.filter(item -> item.getParentInstructionXId() != null).collect(Collectors.toList());

		LoadingInstructionDetails.Builder responseBuilder = LoadingInstructionDetails.newBuilder();
		for (LoadingInstruction instruction : subHeaderList) {
			responseBuilder.setInstructionHeaderId(instruction.getLoadingInstructionHeaderXId());
			// responseBuilder.setInstructionHeaderName(instruction.getLoadingInstructionHeaderName());
			responseBuilder.setInstructionTypeId(instruction.getLoadingTypeXId());

			List<LoadingInstructionSubHeader> subHeaderBuilderList = new ArrayList<>();
			for (LoadingInstruction headerInstruction : subHeaderList) {
				LoadingInstructionSubHeader.Builder subHeaderBuilder = LoadingInstructionSubHeader.newBuilder();
				subHeaderBuilder.setHeaderId(headerInstruction.getId());
				subHeaderBuilder.setHeaderName(headerInstruction.getLoadingInstruction());
				subHeaderBuilder.setIsChecked(headerInstruction.getIsChecked());

				List<LoadingInstructions> instructionsBuilderList = new ArrayList<>();

				instructionsBuilderList = instructionList.stream()
						.filter(item -> item.getParentInstructionXId() == headerInstruction.getId())
						.map(item -> LoadingInstructions.newBuilder().setInstructionId(item.getId())
								.setInstruction(item.getLoadingInstruction()).setIsChecked(item.getIsChecked()).build())
						.collect(Collectors.toList());
				subHeaderBuilder.addAllLoadingInstructionsList(instructionsBuilderList);
				subHeaderBuilderList.add(subHeaderBuilder.build());
			}
			responseBuilder.addAllLoadingInstructionSubHeader(subHeaderBuilderList);
		}

		return responseBuilder;
	}

}
