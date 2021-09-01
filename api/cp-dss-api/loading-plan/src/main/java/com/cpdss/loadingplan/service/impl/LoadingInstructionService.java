/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.FAILED;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceImplBase;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionGroup;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionSubHeader;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructions;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@GrpcService
public class LoadingInstructionService extends LoadingInstructionServiceImplBase {

  @Autowired LoadingInstructionRepository loadingInstructionRepository;

  @Autowired LoadingInstructionTemplateRepository loadingInstructionTemplateRepository;

  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Autowired LoadingInstructionHeaderRepository loadingInstructionHeaderRepository;

  @Autowired LoadingInformationServiceImpl loadingInformationServiceImpl;

  @Override
  @Transactional
  public void updateLoadingInstructions(
      LoadingInstructionsUpdate request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(FAILED);
    try {
      log.info(
          "Update Instruction status request received for  : {}",
          request.getInstructionListCount());
      for (LoadingInstructionStatus item : request.getInstructionListList()) {

        loadingInstructionRepository.updateInstructionStatus(
            item.getInstructionId(), item.getIsChecked());
      }
      log.info("Deleted instruction Succesfully");
      response.setStatus(SUCCESS);
      response.setMessage(SUCCESS);
    } catch (Exception e) {
      log.info("Update instruction status request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  @Transactional
  public void deleteLoadingInstructions(
      LoadingInstructionStatus request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(FAILED);
    try {
      log.info("Delete instruction request receieved for id : {}", request.getInstructionId());
      loadingInstructionRepository.deleteInstruction(request.getInstructionId());
      log.info("Deleted instruction Succesfully");
      response.setStatus(SUCCESS);
      response.setMessage(SUCCESS);
    } catch (Exception e) {
      log.info("Delete instruction request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  @Transactional
  public void editInstructions(
      LoadingInstructionStatus request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(FAILED);
    try {
      log.info("Edit instruction request receieved for id : {}", request.getInstructionId());
      loadingInstructionRepository.editInstruction(
          request.getInstructionId(), request.getInstruction());
      log.info("Edited instruction Succesfully");
      response.setStatus(SUCCESS);
      response.setMessage(SUCCESS);
    } catch (Exception e) {
      log.info("Edit instruction request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void addLoadingInstruction(
      LoadingInstructionsSave request, StreamObserver<ResponseStatus> responseObserver) {
    log.info("inside addLoadingInstruction");
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(FAILED);
    try {
      log.info("Add new instruction request receieved");
      LoadingInstruction newInstruction = new LoadingInstruction();
      final Long PortXId =
          getPortIDFromPortRotation(
              request.getLoadingInfoId(), request.getVesselId(), request.getPortRotationId());

      if (request.getIsSubHeader()) {
        log.info("New Sub header instruction request receieved");
        newInstruction.setLoadingInstruction(request.getInstruction());
        newInstruction.setLoadingInstructionHeaderXId(request.getHeaderId());
        newInstruction.setIsChecked(request.getIsChecked());
        // Reference ID changes w.r.t TypeID, here only considered typeId1 which is PORT
        newInstruction.setReferenceXId(PortXId);
        newInstruction.setIsActive(true);
        newInstruction.setLoadingXId(request.getLoadingInfoId());
        newInstruction.setIsHeaderInstruction(request.getIsSingleHeader());
        newInstruction.setTemplateParentXId(null);

      } else {
        log.info("New child instruction request receieved");
        newInstruction.setLoadingInstruction(request.getInstruction());
        newInstruction.setParentInstructionXId(request.getSubHeaderId());
        newInstruction.setLoadingInstructionHeaderXId(request.getHeaderId());
        newInstruction.setIsChecked(request.getIsChecked());
        // Reference ID changes w.r.t TypeID, here only considered typeId1 which is PORT
        newInstruction.setReferenceXId(PortXId);
        newInstruction.setIsActive(true);
        newInstruction.setLoadingXId(request.getLoadingInfoId());
        newInstruction.setTemplateParentXId(null);
      }

      if (loadingInstructionRepository.save(newInstruction).getId() != null) {
        log.info("New instruction added with id ");
        response.setStatus(SUCCESS);
        response.setMessage(SUCCESS);
      }
    } catch (GenericServiceException e) {
      log.info("ADD new instruction request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  private Long getPortIDFromPortRotation(Long loadingInfoId, Long vesselId, Long portRotationId)
      throws GenericServiceException {
    Optional<LoadingInformation> loadingInformation =
        loadingInformationServiceImpl.getLoadingInformation(
            loadingInfoId, vesselId, 0L, 0L, portRotationId);
    if (loadingInformation.isEmpty()) {
      log.info("Invalid Port rotation ID");
      throw new GenericServiceException(
          "Error occured while fetching portId using PortRotationID",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return loadingInformation.get().getPortXId();
  }

  @Override
  public void getLoadingInstructions(
      LoadingInstructionRequest request,
      StreamObserver<LoadingInstructionDetails> responseObserver) {
    log.info("Inside getLoadingInstructions");
    LoadingInstructionDetails.Builder response = LoadingInstructionDetails.newBuilder();
    response.setResponseStatus(
        Common.ResponseStatus.newBuilder()
            .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
            .setStatus(FAILED)
            .build());
    try {
      final Long portXId =
          getPortIDFromPortRotation(
              request.getLoadingInfoId(), request.getVesselId(), request.getPortRotationId());
      log.info("Found Suitable Loading information of port {}", portXId);
      List<LoadingInstructionHeader> groupNameList =
          loadingInstructionHeaderRepository.findAllByIsActiveTrue();
      log.info("Found instructionGroup {}", groupNameList.toString());
      List<LoadingInstructionGroup> groupBuilderlist =
          groupNameList.stream()
              .map(
                  item ->
                      LoadingInstructionGroup.newBuilder()
                          .setGroupId(item.getId())
                          .setGroupName(item.getHeaderName())
                          .build())
              .collect(Collectors.toList());
      response.addAllLoadingInstructionGroupList(groupBuilderlist);

      if (!loadingInstructionRepository.findAny(request.getLoadingInfoId())) {
        log.info("First time intruction fetch - fetching data from template master");
        List<LoadingInstructionTemplate> templateList =
            loadingInstructionTemplateRepository.findALLByLoadingInsructionTypeIdAndReferenceId(
                1L, portXId);
        log.info("templateList {}", templateList.toString());
        if (templateList != null && !templateList.isEmpty()) {
          List<LoadingInstruction> instructionList =
              templateList.stream()
                  .map(
                      template ->
                          new LoadingInstruction(
                              template.getLoading_instruction(),
                              null,
                              template.getLoadingInsructionType().getId(),
                              template.getLoadingInstructionHeaderXId().getId(),
                              true,
                              template.getReferenceXId(),
                              template.getIsActive(),
                              template.getId(),
                              request.getLoadingInfoId(),
                              template.getIsHeaderInstruction(),
                              template.getParentInstructionXId()))
                  .collect(Collectors.toList());
          log.info("Saving general instruction for first time");
          loadingInstructionRepository.saveAll(instructionList);
          this.updateInstructionParentChildMapping(instructionList);
          response = getAllLoadingInstructions(request, response);
        } else {
          response.setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("No Loading Instructions Available")
                  .setStatus(SUCCESS)
                  .build());
        }
      } else {
        log.info("Fetching existing data from instruction table");
        response = getAllLoadingInstructions(request, response);
      }
    } catch (GenericServiceException e) {
      log.info("Get loading instruction failed");
      e.printStackTrace();
      response.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } finally {
      log.info("Exiting getLoadingInstructions");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Transactional
  private void updateInstructionParentChildMapping(List<LoadingInstruction> instructionList) {
    List<LoadingInstruction> updateList = new ArrayList<>();
    updateList =
        instructionList.stream()
            .filter(
                item ->
                    item.getParentInstructionXId().equals(null)
                        && item.getTemplateParentXId() != null)
            .collect(Collectors.toList());

    updateList.stream()
        .forEach(
            item ->
                item.setParentInstructionXId(
                    instructionList.stream()
                        .filter(
                            i ->
                                i.getLoadingInstructionTemplateXId()
                                    .equals(item.getParentInstructionXId()))
                        .findFirst()
                        .get()
                        .getId()));

    loadingInstructionRepository.saveAll(updateList);
  }

  private Builder getAllLoadingInstructions(LoadingInstructionRequest request, Builder response) {
    log.info("Fetching data from instruction ");
    List<LoadingInstruction> listLoadingInstruction =
        loadingInstructionRepository.getAllLoadingInstructions(
            request.getVesselId(), request.getLoadingInfoId(), request.getPortRotationId());
    log.info("listLoadingInstruction {}", listLoadingInstruction.toString());
    if (listLoadingInstruction != null && !listLoadingInstruction.isEmpty()) {
      log.info("Found list listLoadingInstruction");
      response = buildLoadingInstructionDetails(listLoadingInstruction, response);
      response.setResponseStatus(
          ResponseStatus.newBuilder().setMessage("SUCCESS").setStatus(SUCCESS).build());
    }
    return response;
  }

  private Builder buildLoadingInstructionDetails(
      List<LoadingInstruction> listLoadingInstruction, Builder responseBuilder) {

    List<LoadingInstruction> subHeaderList =
        listLoadingInstruction.stream()
            .filter(item -> item.getParentInstructionXId() == null)
            .sorted(Comparator.comparing(LoadingInstruction::getCreatedDateTime))
            .collect(Collectors.toList());

    List<LoadingInstruction> instructionList =
        listLoadingInstruction.stream()
            .filter(item -> item.getParentInstructionXId() != null)
            .sorted(Comparator.comparing(LoadingInstruction::getCreatedDateTime))
            .collect(Collectors.toList());

    List<LoadingInstructionSubHeader> subHeaderBuilderList = new ArrayList<>();
    for (LoadingInstruction headerInstruction : subHeaderList) {
      LoadingInstructionSubHeader.Builder subHeaderBuilder =
          LoadingInstructionSubHeader.newBuilder();
      Optional.ofNullable(headerInstruction.getId()).ifPresent(subHeaderBuilder::setSubHeaderId);
      Optional.ofNullable(headerInstruction.getLoadingInstruction())
          .ifPresent(subHeaderBuilder::setSubHeaderName);
      Optional.ofNullable(headerInstruction.getIsChecked())
          .ifPresent(subHeaderBuilder::setIsChecked);
      Optional.ofNullable(headerInstruction.getLoadingInstructionHeaderXId())
          .ifPresent(subHeaderBuilder::setInstructionHeaderId);
      Optional.ofNullable(headerInstruction.getLoadingTypeXId())
          .ifPresent(subHeaderBuilder::setInstructionTypeId);
      Optional.ofNullable(headerInstruction.getIsHeaderInstruction())
          .ifPresent(subHeaderBuilder::setIsHeaderInstruction);
      subHeaderBuilder.setIsEditable(
          headerInstruction.getLoadingInstructionTemplateXId() == null ? true : false);

      List<LoadingInstructions> instructionsBuilderList = new ArrayList<>();

      for (LoadingInstruction item : instructionList) {
        if (item.getParentInstructionXId().equals(headerInstruction.getId())) {
          LoadingInstructions.Builder instructionBuilder = LoadingInstructions.newBuilder();
          Optional.ofNullable(item.getId()).ifPresent(instructionBuilder::setInstructionId);
          Optional.ofNullable(item.getLoadingInstructionHeaderXId())
              .ifPresent(instructionBuilder::setInstructionHeaderId);
          Optional.ofNullable(item.getLoadingInstruction())
              .ifPresent(instructionBuilder::setInstruction);
          Optional.ofNullable(item.getIsChecked()).ifPresent(instructionBuilder::setIsChecked);
          Optional.ofNullable(item.getLoadingTypeXId())
              .ifPresent(instructionBuilder::setInstructionTypeId);
          instructionBuilder.setIsEditable(
              item.getLoadingInstructionTemplateXId() == null ? true : false);
          instructionsBuilderList.add(instructionBuilder.build());
        }
      }
      subHeaderBuilder.addAllLoadingInstructionsList(instructionsBuilderList);
      subHeaderBuilderList.add(subHeaderBuilder.build());
    }
    responseBuilder.addAllLoadingInstructionSubHeader(subHeaderBuilderList);

    return responseBuilder;
  }
}
