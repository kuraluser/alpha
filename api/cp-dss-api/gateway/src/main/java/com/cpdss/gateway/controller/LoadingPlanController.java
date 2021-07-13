/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.UpdateUllage;
import com.cpdss.gateway.domain.loadingplan.LoadingInfoAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInformation;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionHeader;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructions;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class LoadingPlanController {

  @Autowired LoadingPlanService loadingPlanService;
  @Autowired LoadingInformationService loadingInformationService;
  @Autowired LoadingInstructionService loadingInstructionService;

  private static final String CORRELATION_ID_HEADER = "correlationId";

  /**
   * Get API to collect the port rotation details of active Voyage
   *
   * @param vesselId Long
   * @param id Long: Always 0.
   * @return
   */
  @GetMapping("/vessels/{vesselId}/loading-info/{id}")
  public Object getPortRotationDetails(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id)
      throws CommonRestException {
    try {
      return loadingPlanService.getLoadingPortRotationDetails(vesselId, id);
    } catch (GenericServiceException e) {
      log.error("Custom exception in Loading Plan Port Rotation");
      e.printStackTrace();
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception in Loading Plan Port Rotation");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Loading Information Get API,
   *
   * <p>Some data collect from Loadable study service and others in Loading plan DB
   *
   * @param vesselId Long
   * @param infoId Long
   * @param voyageId Long
   * @param portRotationId Long
   * @return LoadingInformation
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInformation getLoadingInformation(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      log.info("Get Loading Info, api for vessel {}, Port Rotation {}", vesselId, portRotationId);
      LoadingInformation var1 =
          this.loadingPlanService.getLoadingInformationByPortRotation(
              vesselId, infoId, portRotationId);
      return var1;
    } catch (GenericServiceException e) {
      e.printStackTrace();
      log.error("Custom exception in Get Loading Information API - {}", e.getMessage());
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception in Get Loading Information API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Save Loading Information API
   *
   * @param request
   * @param headers
   * @param vesselId
   * @param voyageId
   * @return
   * @throws CommonRestException
   */
  @PostMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info")
  public LoadingInformationResponse saveLoadingInformation(
      @RequestBody @Valid LoadingInformationRequest request,
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId)
      throws CommonRestException {
    try {
      log.info("Save Loading Info, api for vessel {}, voyage {}", vesselId, voyageId);
      return this.loadingPlanService.saveLoadingInformation(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("Exception in Save Loading Information API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @PostMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/update-ullage/{portRotationId}")
  public UpdateUllage updateUllage(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody UpdateUllage updateUllageRequest)
      throws CommonRestException {
    try {
      return loadingInformationService.processUpdateUllage(
          vesselId, voyageId, infoId, portRotationId, updateUllageRequest, "");
    } catch (GenericServiceException e) {
      log.error("Failed to update ullage for loading info!");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Generate Loading Plan API
   *
   * @param headers
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @return
   * @throws CommonRestException
   */
  @PostMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/generate-loading-plan")
  public LoadingInfoAlgoResponse generateLoadingPlan(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId)
      throws CommonRestException {
    try {
      log.info("Generate Loading Plan, api for vessel {}", vesselId);
      return loadingInformationService.generateLoadingPlan(infoId);
    } catch (GenericServiceException e) {
      log.error("Exception in Generate Loading Plan API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  
//  /**
//   * Save new Loading Instructions
//   * @return
//   * @throws CommonRestException
//   */
//  @PostMapping("/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
//  public LoadingInstruction updateLoadingInstructions(
//      @RequestHeader HttpHeaders headers,
//      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
//      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
//      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portRotationId,
//      @RequestBody LoadingInstructionRequest request
//      )
//      throws CommonRestException {
//	  
	  
//    try {
//      log.info("Getting all Loading instructions of vesselID: {} on port: {}", vesselId,portRotationId);
//      return loadingInstructionService.getLoadingInstructions(vesselId,infoId,portRotationId);
//     
//    } catch (GenericServiceException e) {
//      log.error("Getting all Loading instructions");
//      e.printStackTrace();
//      throw new CommonRestException(
//          CommonErrorCodes.E_GEN_INTERNAL_ERR,
//          headers,
//          HttpStatusCode.INTERNAL_SERVER_ERROR,
//          e.getMessage(),
//          e);
//    }
//  }
  
  /**
   * Retrieve all loading Instructions
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInstructionDetails getAllLoadingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portRotationId)
      throws CommonRestException {
	  
	  LoadingInstructionResponse mockreturn = new LoadingInstructionResponse();
//	  mockreturn.setInstructionType("Preparation for entering and cargo loading");
//	  mockreturn.setInstructionTypeId(1L);
//	  List<LoadingInstructionHeader> hlist = new ArrayList<>();
//	  LoadingInstructionHeader header = new LoadingInstructionHeader();
//	  header.setHeaderName("Oil spill Equipment - 1");
//	  header.setIsHeaderChecked(true);
//	  LoadingInstructions instruction = new LoadingInstructions();
//	  instruction.setInstruction("Oil spill Equipment to be displayed and ready for use before cargo operation start-1");
//	  instruction.setIsChecked(true);
//	  LoadingInstructions instruction2 = new LoadingInstructions();
//	  instruction2.setInstruction("Oil spill Equipment to be displayed and ready for use");
//	  instruction2.setIsChecked(false);
//	  List<LoadingInstructions> ilist = new ArrayList<>();
//	  ilist.add(instruction);
//	  ilist.add(instruction2);
//	  header.setLoadingInstructionlist(ilist);
//	  hlist.add(header);
//	  mockreturn.setLoadingInstructionList(hlist);
//	  return mockreturn;
    try {
      log.info("Getting all Loading instructions of vesselID: {} on port: {}", vesselId,portRotationId);
      return loadingInstructionService.getLoadingInstructions(vesselId,infoId,portRotationId);
     
    } catch (GenericServiceException e) {
      log.error("Getting all Loading instructions");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  
}
