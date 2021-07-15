/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.FAILED;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceImplBase;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionGroup;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionSubHeader;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructions;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInstruction;
import com.cpdss.loadingplan.entity.LoadingInstructionHeader;
import com.cpdss.loadingplan.entity.LoadingInstructionTemplate;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionHeaderRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionRepository;
import com.cpdss.loadingplan.repository.LoadingInstructionTemplateRepository;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class LoadingInstructionService extends LoadingInstructionServiceImplBase {

	@Autowired
	LoadingInstructionRepository loadingInstructionRepository;

	@Autowired
	LoadingInstructionTemplateRepository loadingInstructionTemplateRepository;

	@Autowired
	LoadingInformationRepository loadingInformationRepository;

	@Autowired
	LoadingInstructionHeaderRepository loadingInstructionHeaderRepository;

	@Autowired
	LoadingInformationServiceImpl loadingInformationServiceImpl;

	@Override
	public void getLoadingInstructions(LoadingInstructionRequest request,
			StreamObserver<LoadingInstructionDetails> responseObserver) {
		log.info("Inside getLoadingInstructions");

		
		LoadingInstructionDetails.Builder response = LoadingInstructionDetails.newBuilder();
		response.setResponseStatus(Common.ResponseStatus.newBuilder().setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
				.setStatus(FAILED).build());

		Optional<LoadingInformation> loadingInformation = loadingInformationServiceImpl.getLoadingInformation(
				request.getLoadingInfoId(), request.getVesselId(), 0L, 0L, request.getPortRotationId());
		if (!loadingInformation.isEmpty()) {
			log.info("Found Suitable Loading information of port {}",loadingInformation.get().getPortXId());
			final Long PortXId = loadingInformation.get().getPortXId();
			List<LoadingInstructionHeader> groupNameList = loadingInstructionHeaderRepository
					.findAllByIsActiveTrue();
			log.info("Found instructionGroup {}",groupNameList.toString());
			List<LoadingInstructionGroup> groupBuilderlist = groupNameList.stream().map(item -> LoadingInstructionGroup
					.newBuilder().setGroupId(item.getId()).setGroupName(item.getHeaderName()).build())
					.collect(Collectors.toList());
			response.addAllLoadingInstructionGroupList(groupBuilderlist);
			
			if (!loadingInstructionRepository.findAny(request.getLoadingInfoId())) {
				log.info("First time intruction fetch - fetching data from template master");
				List<LoadingInstructionTemplate> templateList = loadingInstructionTemplateRepository
						.findALLByLoadingInsructionTypeIdAndReferenceId(1L,PortXId);
				log.info("templateList {}",templateList.toString());
				if (templateList != null && !templateList.isEmpty()) {
					List<LoadingInstruction> instructionList = templateList.stream()
							.map(template -> new LoadingInstruction(template.getLoading_instruction(),
									template.getParentInstructionXId(),
									template.getLoadingInsructionType().getId(),
									template.getLoadingInstructionHeaderXId().getId(), true, template.getReferenceXId(),
									template.getIsActive(), template.getId(), request.getLoadingInfoId(),
									template.getIsHeaderInstruction(),null))
							.collect(Collectors.toList());
					log.info("Saving general instruction for first time");
					loadingInstructionRepository.saveAll(instructionList);
					//loadingInstructionRepository.updateInstructionParentChildMapping();
					response = getAllLoadingInstructions(request, response);
				}
			} else {
				log.info("Fetching existing data from instruction table");
				response = getAllLoadingInstructions(request, response);
			}
		}
		log.info("Exiting getLoadingInstructions");
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}

	private Builder getAllLoadingInstructions(LoadingInstructionRequest request, Builder response) {
		log.info("Fetching data from instruction ");
		List<LoadingInstruction> listLoadingInstruction = loadingInstructionRepository.getAllLoadingInstructions(
				request.getVesselId(), request.getLoadingInfoId(), request.getPortRotationId());
		log.info("listLoadingInstruction {}",listLoadingInstruction.toString());
		if (listLoadingInstruction != null && !listLoadingInstruction.isEmpty()) {
			log.info("Found list listLoadingInstruction");
			response = buildLoadingInstructionDetails(listLoadingInstruction,response);
			response.setResponseStatus(
					ResponseStatus.newBuilder().setMessage("Successfull").setStatus(SUCCESS).build());
		}
		return response;
	}

	private Builder buildLoadingInstructionDetails(List<LoadingInstruction> listLoadingInstruction, Builder responseBuilder) {

		List<LoadingInstruction> subHeaderList = listLoadingInstruction.stream()
				.filter(item -> item.getParentInstructionXId() == null).collect(Collectors.toList());

		List<LoadingInstruction> instructionList = listLoadingInstruction.stream()
				.filter(item -> item.getParentInstructionXId() != null).collect(Collectors.toList());

			List<LoadingInstructionSubHeader> subHeaderBuilderList = new ArrayList<>();
			for (LoadingInstruction headerInstruction : subHeaderList) {
				log.info("inside  header loop");
				LoadingInstructionSubHeader.Builder subHeaderBuilder = LoadingInstructionSubHeader.newBuilder();
				subHeaderBuilder.setSubHeaderId(headerInstruction.getId());
				subHeaderBuilder.setSubHeaderName(headerInstruction.getLoadingInstruction());
				subHeaderBuilder.setIsChecked(headerInstruction.getIsChecked());
				subHeaderBuilder.setInstructionHeaderId(headerInstruction.getLoadingInstructionHeaderXId());
				subHeaderBuilder.setInstructionTypeId(headerInstruction.getLoadingTypeXId());

				List<LoadingInstructions> instructionsBuilderList = new ArrayList<>();

				instructionsBuilderList = instructionList.stream()
						.filter(item -> item.getParentInstructionXId() == headerInstruction.getId())
						.map(item -> LoadingInstructions.newBuilder().setInstructionId(item.getId())
								.setInstructionHeaderId(item.getLoadingInstructionHeaderXId())
								.setInstructionTypeId(item.getLoadingTypeXId())
								.setInstruction(item.getLoadingInstruction()).setIsChecked(item.getIsChecked()).build())
						.collect(Collectors.toList());
				subHeaderBuilder.addAllLoadingInstructionsList(instructionsBuilderList);
				subHeaderBuilderList.add(subHeaderBuilder.build());
			}
			responseBuilder.addAllLoadingInstructionSubHeader(subHeaderBuilderList);
		

		return responseBuilder;
	}
}
