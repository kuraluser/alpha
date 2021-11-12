/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionGroup;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionServiceGrpc.DischargingInstructionServiceImplBase;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionSubHeader;
import com.cpdss.common.generated.discharge_plan.DischargingInstructions;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingInstruction;
import com.cpdss.dischargeplan.entity.DischargingInstructionHeader;
import com.cpdss.dischargeplan.entity.DischargingInstructionTemplate;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingInstructionHeaderRepository;
import com.cpdss.dischargeplan.repository.DischargingInstructionRepository;
import com.cpdss.dischargeplan.repository.DischargingInstructionTemplateRepository;
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
public class DischargingInstructionService extends DischargingInstructionServiceImplBase {

  @Autowired DischargingInstructionRepository dischargingInstructionRepository;

  @Autowired DischargingInstructionTemplateRepository dischargingInstructionTemplateRepository;

  @Autowired DischargeInformationRepository dischargingInformationRepository;

  @Autowired DischargingInstructionHeaderRepository dischargingInstructionHeaderRepository;

  @Autowired DischargeInformationService dischargeInformationService;

  @Override
  public void addDischargingInstruction(
      DischargingInstructionsSave request, StreamObserver<ResponseStatus> responseObserver) {
    log.info("inside addDischargingInstruction");
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(DischargePlanConstants.FAILED);
    try {
      log.info("Add new instruction request receieved");
      DischargingInstruction newInstruction = new DischargingInstruction();
      final Long PortXId = getPortIDFromPortRotation(request.getDischargingInfoId());

      if (request.getIsSubHeader()) {
        log.info("New Sub header instruction request receieved");
        newInstruction.setDischargingInstruction(request.getInstruction());
        newInstruction.setDischargingInstructionHeaderXId(request.getHeaderId());
        newInstruction.setIsChecked(request.getIsChecked());
        // Reference ID changes w.r.t TypeID, here only considered typeId1 which is PORT
        newInstruction.setReferenceXId(PortXId);
        newInstruction.setIsActive(true);
        newInstruction.setDischargingXId(request.getDischargingInfoId());
        newInstruction.setIsHeaderInstruction(request.getIsSingleHeader());
        newInstruction.setTemplateParentXId(null);

      } else {
        log.info("New child instruction request receieved");
        newInstruction.setDischargingInstruction(request.getInstruction());
        newInstruction.setParentInstructionXId(request.getSubHeaderId());
        newInstruction.setDischargingInstructionHeaderXId(request.getHeaderId());
        newInstruction.setIsChecked(request.getIsChecked());
        // Reference ID changes w.r.t TypeID, here only considered typeId1 which is PORT
        newInstruction.setReferenceXId(PortXId);
        newInstruction.setIsActive(true);
        newInstruction.setDischargingXId(request.getDischargingInfoId());
        newInstruction.setTemplateParentXId(null);
      }

      if (dischargingInstructionRepository.save(newInstruction).getId() != null) {
        log.info("New instruction added with id ");
        response.setStatus(DischargePlanConstants.SUCCESS);
        response.setMessage(DischargePlanConstants.SUCCESS);
        // Update Discharge Information Table
        this.dischargeInformationService.updateDischargeInstructionStatusTrue(
            newInstruction.getDischargingXId());
      }
    } catch (GenericServiceException e) {
      log.info("ADD new instruction request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(DischargePlanConstants.FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  private Long getPortIDFromPortRotation(Long dischargingInfoId) throws GenericServiceException {
    DischargeInformation dischargingInformation =
        dischargeInformationService.getDischargeInformation(dischargingInfoId);

    if (dischargingInformation == null) {
      log.info("Invalid Port rotation ID");
      throw new GenericServiceException(
          "Error occured while fetching portId using PortRotationID",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return dischargingInformation.getPortXid();
  }

  @Override
  @Transactional
  public void updateDischargingInstructions(
      DischargingInstructionsUpdate request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(DischargePlanConstants.FAILED);
    try {
      log.info(
          "Update Instruction status request received for  : {}",
          request.getInstructionListCount());
      for (DischargingInstructionStatus item : request.getInstructionListList()) {

        dischargingInstructionRepository.updateInstructionStatus(
            item.getInstructionId(), item.getIsChecked());
      }
      log.info("Update instruction Succesfully");
      response.setStatus(DischargePlanConstants.SUCCESS);
      response.setMessage(DischargePlanConstants.SUCCESS);
    } catch (Exception e) {
      log.info("Update instruction status request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(DischargePlanConstants.FAILED)
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
      DischargingInstructionStatus request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(DischargePlanConstants.FAILED);
    try {
      log.info("Edit instruction request receieved for id : {}", request.getInstructionId());
      dischargingInstructionRepository.editInstruction(
          request.getInstructionId(), request.getInstruction());
      log.info("Edited instruction Succesfully");
      response.setStatus(DischargePlanConstants.SUCCESS);
      response.setMessage(DischargePlanConstants.SUCCESS);
    } catch (Exception e) {
      log.info("Edit instruction request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(DischargePlanConstants.FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getDischargingInstructions(
      DischargingInstructionRequest request,
      StreamObserver<DischargingInstructionDetails> responseObserver) {
    log.info("Inside getDischargingInstructions");
    DischargingInstructionDetails.Builder response = DischargingInstructionDetails.newBuilder();
    response.setResponseStatus(
        Common.ResponseStatus.newBuilder()
            .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
            .setStatus(DischargePlanConstants.FAILED)
            .build());
    try {
      final Long portXId = getPortIDFromPortRotation(request.getDischargingInfoId());
      log.info("Found Suitable Discharging information of port {}", portXId);
      List<DischargingInstructionHeader> groupNameList =
          dischargingInstructionHeaderRepository.findAllByIsActiveTrue();
      log.info("Found instructionGroup {}", groupNameList.toString());
      List<DischargingInstructionGroup> groupBuilderlist =
          groupNameList.stream()
              .map(
                  item ->
                      DischargingInstructionGroup.newBuilder()
                          .setGroupId(item.getId())
                          .setGroupName(item.getHeaderName())
                          .build())
              .collect(Collectors.toList());
      response.addAllDischargingInstructionGroupList(groupBuilderlist);

      if (!dischargingInstructionRepository.findAny(request.getDischargingInfoId())) {
        log.info("First time intruction fetch - fetching data from template master");
        Long dischargingInstructionId = 5L;
        List<DischargingInstructionTemplate> templateList =
            dischargingInstructionTemplateRepository
                .findALLByDischargingInsructionTypeIdAndReferenceId(
                    1L, portXId, dischargingInstructionId);
        log.info("templateList {}", templateList.toString());
        if (templateList != null && !templateList.isEmpty()) {
          List<DischargingInstruction> instructionList =
              templateList.stream()
                  .map(
                      template ->
                          new DischargingInstruction(
                              template.getDischarging_instruction(),
                              null,
                              template.getDischargingInsructionType().getId(),
                              template.getDischargingInstructionHeaderXId().getId(),
                              true,
                              template.getReferenceXId(),
                              template.getIsActive(),
                              template.getId(),
                              request.getDischargingInfoId(),
                              template.getIsHeaderInstruction(),
                              template.getParentInstructionXId()))
                  .collect(Collectors.toList());
          log.info("Saving general instruction for first time");
          dischargingInstructionRepository.saveAll(instructionList);
          this.updateInstructionParentChildMapping(instructionList);
          response = getAllDischargingInstructions(request, response);
        } else {
          response.setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("No Discharging Instructions Available")
                  .setStatus(DischargePlanConstants.SUCCESS)
                  .build());
        }
      } else {
        log.info("Fetching existing data from instruction table");
        response = getAllDischargingInstructions(request, response);
      }
    } catch (GenericServiceException e) {
      log.info("Get discharging instruction failed");
      e.printStackTrace();
      response.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      log.info("Exiting getDischargingInstructions");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Transactional
  public void updateInstructionParentChildMapping(List<DischargingInstruction> instructionList) {
    List<DischargingInstruction> updateList = new ArrayList<>();
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
                                i.getDischargingInstructionTemplateXId()
                                    .equals(item.getParentInstructionXId()))
                        .findFirst()
                        .get()
                        .getId()));

    dischargingInstructionRepository.saveAll(updateList);
  }

  private Builder getAllDischargingInstructions(
      DischargingInstructionRequest request, Builder response) {
    log.info("Fetching data from instruction ");
    List<DischargingInstruction> listDischargingInstruction =
        dischargingInstructionRepository.getAllDischargingInstructions(
            request.getVesselId(), request.getDischargingInfoId(), request.getPortRotationId());
    log.info("listDischargingInstruction {}", listDischargingInstruction.toString());
    if (listDischargingInstruction != null && !listDischargingInstruction.isEmpty()) {
      log.info("Found list listDischargingInstruction");
      response = buildDischargingInstructionDetails(listDischargingInstruction, response);
      response.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("SUCCESS")
              .setStatus(DischargePlanConstants.SUCCESS)
              .build());
    }
    return response;
  }

  private Builder buildDischargingInstructionDetails(
      List<DischargingInstruction> listDischargingInstruction, Builder responseBuilder) {

    List<DischargingInstruction> subHeaderList =
        listDischargingInstruction.stream()
            .filter(item -> item.getParentInstructionXId() == null)
            .sorted(Comparator.comparing(DischargingInstruction::getCreatedDateTime))
            .collect(Collectors.toList());

    List<DischargingInstruction> instructionList =
        listDischargingInstruction.stream()
            .filter(item -> item.getParentInstructionXId() != null)
            .sorted(Comparator.comparing(DischargingInstruction::getCreatedDateTime))
            .collect(Collectors.toList());

    List<DischargingInstructionSubHeader> subHeaderBuilderList = new ArrayList<>();
    for (DischargingInstruction headerInstruction : subHeaderList) {
      DischargingInstructionSubHeader.Builder subHeaderBuilder =
          DischargingInstructionSubHeader.newBuilder();
      Optional.ofNullable(headerInstruction.getId()).ifPresent(subHeaderBuilder::setSubHeaderId);
      Optional.ofNullable(headerInstruction.getDischargingInstruction())
          .ifPresent(subHeaderBuilder::setSubHeaderName);
      Optional.ofNullable(headerInstruction.getIsChecked())
          .ifPresent(subHeaderBuilder::setIsChecked);
      Optional.ofNullable(headerInstruction.getDischargingInstructionHeaderXId())
          .ifPresent(subHeaderBuilder::setInstructionHeaderId);
      Optional.ofNullable(headerInstruction.getDischargingTypeXId())
          .ifPresent(subHeaderBuilder::setInstructionTypeId);
      Optional.ofNullable(headerInstruction.getIsHeaderInstruction())
          .ifPresent(subHeaderBuilder::setIsHeaderInstruction);
      subHeaderBuilder.setIsEditable(
          headerInstruction.getDischargingInstructionTemplateXId() == null ? true : false);

      List<DischargingInstructions> instructionsBuilderList = new ArrayList<>();

      for (DischargingInstruction item : instructionList) {
        if (item.getParentInstructionXId().equals(headerInstruction.getId())) {
          DischargingInstructions.Builder instructionBuilder = DischargingInstructions.newBuilder();
          Optional.ofNullable(item.getId()).ifPresent(instructionBuilder::setInstructionId);
          Optional.ofNullable(item.getDischargingInstructionHeaderXId())
              .ifPresent(instructionBuilder::setInstructionHeaderId);
          Optional.ofNullable(item.getDischargingInstruction())
              .ifPresent(instructionBuilder::setInstruction);
          Optional.ofNullable(item.getIsChecked()).ifPresent(instructionBuilder::setIsChecked);
          Optional.ofNullable(item.getDischargingTypeXId())
              .ifPresent(instructionBuilder::setInstructionTypeId);
          instructionBuilder.setIsEditable(
              item.getDischargingInstructionTemplateXId() == null ? true : false);
          instructionsBuilderList.add(instructionBuilder.build());
        }
      }
      subHeaderBuilder.addAllDischargingInstructionsList(instructionsBuilderList);
      subHeaderBuilderList.add(subHeaderBuilder.build());
    }
    responseBuilder.addAllDischargingInstructionSubHeader(subHeaderBuilderList);

    return responseBuilder;
  }

  @Override
  @Transactional
  public void deleteDischargingInstructions(
      DischargingInstructionStatus request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    response.setStatus(DischargePlanConstants.FAILED);
    try {
      log.info("Delete instruction request receieved for id : {}", request.getInstructionId());
      dischargingInstructionRepository.deleteInstruction(request.getInstructionId());
      log.info("Deleted instruction Succesfully");
      response.setStatus(DischargePlanConstants.SUCCESS);
      response.setMessage(DischargePlanConstants.SUCCESS);
    } catch (Exception e) {
      log.info("Delete instruction request failed");
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(DischargePlanConstants.FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }
}
