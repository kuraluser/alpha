/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AlgoStatusRequest;
import com.cpdss.gateway.domain.RuleRequest;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.UpdateUllage;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingPlanServiceImpl;
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
  @Autowired LoadingPlanServiceImpl loadingPlanServiceImpl;
  @Autowired private LoadingInstructionService loadingInstructionService;

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
   * loadingInfoStatus API
   *
   * @param headers
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @param request
   * @return
   * @throws CommonRestException LoadingInfoAlgoResponse
   */
  @PostMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-info-status")
  public LoadingInfoAlgoResponse loadingInfoStatus(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @RequestBody AlgoStatusRequest request)
      throws CommonRestException {
    try {
      log.info("update loading info status api for vessel {}", vesselId);
      return loadingPlanServiceImpl.saveLoadingInfoStatus(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in loadingInfoStatus ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in loadingInfoStatus ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
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

  /**
   * To get rule against the Loading Information.
   *
   * @param headers
   * @param vesselId Long Id
   * @param voyageId Long Id
   * @param infoId Long Id
   * @return RuleResponse
   * @throws CommonRestException
   */
  @GetMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/rules")
  public RuleResponse getLoadingPlanRules(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId)
      throws CommonRestException {
    try {
      return loadingPlanService.getLoadingPlanRules(vesselId, voyageId, infoId);
    } catch (GenericServiceException e) {
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
   * Save Rule from Loading Information Page
   *
   * @param headers HttpHeaders
   * @param vesselId Long Id
   * @param voyageId Long Id
   * @param infoId Long Id
   * @param loadingPlanRule RuleRequest
   * @return RuleResponse
   * @throws CommonRestException
   */
  @PostMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/rules")
  public RuleResponse saveLoadingPlanRule(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @RequestBody RuleRequest loadingPlanRule)
      throws CommonRestException {
    try {
      return loadingPlanService.saveLoadingPlanRules(vesselId, voyageId, infoId, loadingPlanRule);
    } catch (GenericServiceException e) {
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
   * Save new Loading Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/new-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInstructionsSaveResponse addLoadingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody LoadingInstructionsSaveRequest request)
      throws CommonRestException {
    try {
      log.info("Adding new Loading instruction");
      return loadingInstructionService.addLoadingInstruction(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Adding new Loading Instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Adding new Loading instruction");
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
   * Update Loading Instruction status
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/update-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInstructionsSaveResponse updateLoadingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody LoadingInstructionsUpdateRequest request)
      throws CommonRestException {
    try {
      log.info("Updating Loading instruction status for {}", vesselId);
      return loadingInstructionService.updateLoadingInstructions(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Updating Loading instruction status for {} failed", vesselId);
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Updating Loading instruction status for {} ", vesselId);
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
   * Delete Loading Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/delete-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInstructionsSaveResponse deleteLoadingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody LoadingInstructionsStatus request)
      throws CommonRestException {
    try {
      log.info("Deleting Loading instruction , id{}", request.getInstructionId());
      return loadingInstructionService.deleteLoadingInstructions(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Deleting Loading instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Deleting Loading instruction");
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
   * Delete Loading Instruction
   *
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      "/edit-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInstructionsSaveResponse editLoadingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId,
      @RequestBody LoadingInstructionsStatus request)
      throws CommonRestException {
    try {
      log.info("Editing Loading instruction , id{}", request.getInstructionId());
      return loadingInstructionService.editLoadingInstructions(
          vesselId, infoId, portRotationId, request);

    } catch (GenericServiceException e) {
      log.error("Editing Loading instruction failed");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Editing Loading instruction");
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
   * Retrieve all loading Instructions
   *
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}")
  public LoadingInstructionResponse getAllLoadingInstructions(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      log.info(
          "Getting all Loading instructions of vesselID: {} on port: {}", vesselId, portRotationId);
      return loadingInstructionService.getLoadingInstructions(vesselId, infoId, portRotationId);

    } catch (GenericServiceException e) {
      log.error("Getting all Loading instructions Failed error");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    } catch (Exception e) {
      log.error("Exception in Getting all Loading instructions");
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
   * Get Loading Sequence API
   *
   * @param headers
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @return
   * @throws CommonRestException
   */
  @GetMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-sequence")
  public LoadingSequenceResponse getLoadingSequence(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId)
      throws CommonRestException {
    try {
      log.info(
          "Get Loading Sequence api for vessel {}, voyage {}, loading information {}",
          vesselId,
          voyageId,
          infoId);
      return loadingPlanService.getLoadingSequence(vesselId, voyageId, infoId);
    } catch (GenericServiceException e) {
      log.error("Exception in Get Loading Sequence API");
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
   * Save Loading Sequence API
   *
   * @param headers
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @return
   * @throws CommonRestException
   */
  @PostMapping("/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/save-loading-plan")
  public LoadingPlanAlgoResponse saveLoadingPlan(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @RequestBody LoadingPlanAlgoRequest loadingPlanAlgoRequest)
      throws CommonRestException {
    try {
      log.info(
          "Save Loading Plan API for vessel {}, voyage {}, loading information {}",
          vesselId,
          voyageId,
          infoId);
      return loadingPlanService.saveLoadingPlan(vesselId, voyageId, infoId, loadingPlanAlgoRequest);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in Save Loading Plan API");
      e.printStackTrace();
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in Save Loading Plan API");
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
   * Get Loading Sequence API
   *
   * @param headers
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-plan/{portRotationId}")
  public LoadingPlanResponse getLoadingPlan(
      @RequestHeader HttpHeaders headers,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long infoId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long portRotationId)
      throws CommonRestException {
    try {
      log.info(
          "Get Loading Plan API for vessel {}, voyage {}, loading information {}",
          vesselId,
          voyageId,
          infoId);
      return loadingPlanService.getLoadingPlan(vesselId, voyageId, infoId, portRotationId);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in Get Loading Plan API");
      e.printStackTrace();
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in Get Loading Plan API");
      e.printStackTrace();
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }
}
