/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AlgoStatusRequest;
import com.cpdss.gateway.domain.AlgoStatusResponse;
import com.cpdss.gateway.domain.CargoNomination;
import com.cpdss.gateway.domain.CargoNominationResponse;
import com.cpdss.gateway.domain.CommingleCargo;
import com.cpdss.gateway.domain.CommingleCargoResponse;
import com.cpdss.gateway.domain.DischargingPortRequest;
import com.cpdss.gateway.domain.LoadablePatternDetailsResponse;
import com.cpdss.gateway.domain.LoadablePatternResponse;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.OnBoardQuantity;
import com.cpdss.gateway.domain.OnBoardQuantityResponse;
import com.cpdss.gateway.domain.OnHandQuantity;
import com.cpdss.gateway.domain.OnHandQuantityResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * Gateway controller for loadable study related operations
 *
 * @author suhail.k
 */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class LoadableStudyController {

  @Autowired private LoadableStudyService loadableStudyService;

  private static final String CORRELATION_ID_HEADER = "correlationId";

  /**
   * API for save voyage
   *
   * @param voyage
   * @param vesselId
   * @param headers
   * @return CommonSuccessResponse
   * @throws CommonRestException CommonSuccessResponse
   */
  @PostMapping("/vessels/{vesselId}/voyages")
  public VoyageResponse saveVoyage(
      @RequestBody @Valid Voyage voyage,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("save voyage API. correlationId: {}", headers.getFirst(CORRELATION_ID_HEADER));
      log.info("saveVoyage: {}", getClientIp());
      Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
      return loadableStudyService.saveVoyage(
          voyage, companyId, vesselId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in save voyage", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in save voyage ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * API for save loadable Quantity
   *
   * @param loadableQuantity - has the details of loadable quantity
   * @param vesselId
   * @param loadableStudiesId
   * @param headers
   * @return
   * @throws CommonRestException CommonSuccessResponse
   */
  @PostMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-quantity")
  public LoadableQuantityResponse saveLoadableQuantity(
      @RequestBody @Valid LoadableQuantity loadableQuantity,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudiesId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info(
          "save loadable quantity API. correlationId: {} ",
          headers.getFirst(CORRELATION_ID_HEADER));
      log.info("saveLoadableQuantity: {}", getClientIp());
      return loadableStudyService.saveLoadableQuantity(
          loadableQuantity, loadableStudiesId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in save loadable quantity ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in save loadable quantity ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Get list of loadable studies baed on vessel and voyage
   *
   * @param vesselId - the vessel id
   * @param voyageId - the voyage id
   * @param headers - http request headers
   * @return {@link LoadableStudyResponse}
   * @throws CommonRestException
   */
  @GetMapping("/vessels/{vesselId}/voyages/{voyageId}/loadable-studies")
  public LoadableStudyResponse getLoadableStudyByVoyage(
      @PathVariable @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getLoadableStudyByVoyage: {}", getClientIp());
      Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
      return this.loadableStudyService.getLoadableStudies(
          companyId, vesselId, voyageId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error fetching loadable study", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Save loadable study
   *
   * @param vesselId - the vessel id for which loadable study is created
   * @param voyageId - the voyage id for which loadable study is created
   * @param request - the request body {@link LoadableStudy}
   * @param headers - the http request header
   * @return {@link LoadableStudyResponse}
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public LoadableStudyResponse saveLoadableStudy(
      @PathVariable Long vesselId,
      @PathVariable Long voyageId,
      @Valid final LoadableStudy request,
      @Size(max = 5, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          @RequestParam(name = "files", required = false)
          MultipartFile[] files,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("saveLoadableStudy: {}", getClientIp());
      request.setVesselId(vesselId);
      request.setVoyageId(voyageId);
      request.setCompanyId(1L);
      return this.loadableStudyService.saveLoadableStudy(
          request, headers.getFirst(CORRELATION_ID_HEADER), files);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error when saving loadable study", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Retrieves cargo nominations set for the loadable study Retrieves port info details from master
   * Retrieves cargo info details from master
   *
   * @param vesselId
   * @param voyageId
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations")
  public CargoNominationResponse getCargoNomination(
      @PathVariable Long vesselId,
      @PathVariable Long voyageId,
      @PathVariable Long loadableStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    CargoNominationResponse response = null;
    try {
      log.info("getCargoNomination: {}", getClientIp());
      response = loadableStudyService.getCargoNomination(loadableStudyId, headers);
    } catch (Exception e) {
      log.error("Error in getCargoNomination ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }

  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CargoNominationResponse saveCargoNomination(
      @PathVariable Long vesselId,
      @PathVariable Long voyageId,
      @PathVariable Long loadableStudyId,
      @PathVariable Long id,
      @RequestBody CargoNomination cargoNomination,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    CargoNominationResponse response = null;
    try {
      log.info("saveCargoNomination: {}", getClientIp());
      response =
          loadableStudyService.saveCargoNomination(
              vesselId, voyageId, loadableStudyId, cargoNomination, headers);
    } catch (Exception e) {
      log.error("Error in getCargoNomination ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }

  /**
   * Get port list for loadable study
   *
   * @param vesselId
   * @param voyageId
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-quantity")
  public LoadableQuantityResponse getLoadableQuantity(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudiesId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getLoadableQuantity: {}", getClientIp());
      log.info(
          "get loadable quantity API. correlationId: {}", headers.getFirst(CORRELATION_ID_HEADER));
      return loadableStudyService.getLoadableQuantity(
          loadableStudiesId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in save loadable quantity ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in save loadable quantity ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  /**
   * Get voyages by vessel
   *
   * @param vesselId
   * @return
   * @throws CommonRestException
   */
  @GetMapping(value = "/vessels/{vesselId}/voyages")
  public VoyageResponse getVoyageListByVessel(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getVoyageListByVessel: {}", getClientIp());
      return this.loadableStudyService.getVoyageListByVessel(
          vesselId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when listing voyages", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when listing voyages", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Get loadable study port rotations
   *
   * @param vesselId
   * @param voyageId
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      value = "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PortRotationResponse loadableStudyPortList(
      @PathVariable Long vesselId,
      @PathVariable Long voyageId,
      @PathVariable Long loadableStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("loadableStudyPortList: {}", getClientIp());
      return this.loadableStudyService.getLoadableStudyPortRotationList(
          vesselId, voyageId, loadableStudyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when list loadable study - ports", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when list loadable study - ports", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Save loadable study port rotation
   *
   * @param request
   * @param loadableStudyId
   * @param id
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PortRotationResponse savePortRotation(
      @RequestBody @Valid PortRotation request,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("savePortRotation: {}", getClientIp());
      request.setId(id);
      request.setLoadableStudyId(loadableStudyId);
      return this.loadableStudyService.savePortRotation(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port rotation", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving loadable study - port rotation", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @DeleteMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}")
  public CargoNominationResponse deleteCargoNomination(
      @PathVariable Long vesselId,
      @PathVariable Long voyageId,
      @PathVariable Long loadableStudyId,
      @PathVariable Long id,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    CargoNominationResponse response = null;
    try {
      log.info("deleteCargoNomination: {}", getClientIp());
      response = loadableStudyService.deleteCargoNomination(id, headers);
    } catch (Exception e) {
      log.error("Error in deleteCargoNomination ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
    return response;
  }

  /**
   * Save discharging ports to port rotation
   *
   * @param request {@link DischargingPortRequest}
   * @param loadableStudyId - the loadable study id
   * @param headers {@link HttpHeaders}
   * @return {@link PortRotationResponse}
   * @throws CommonRestException
   */
  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/discharging-ports",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PortRotationResponse saveDischargingPorts(
      @RequestBody @Valid DischargingPortRequest request,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("saveDischargingPorts: {}", getClientIp());
      request.setLoadableStudyId(loadableStudyId);
      return this.loadableStudyService.saveDischargingPorts(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving discharging ports", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving discharging ports", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @DeleteMapping(
      value = "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public LoadableStudyResponse deleteLoadableStudy(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("deleteLoadableStudy: {}", getClientIp());
      return this.loadableStudyService.deleteLoadableStudy(
          loadableStudyId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting loadable study", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when deleting loadable study", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * @param vesselId
   * @param voyageId
   * @param loadableStudyId
   * @param headers
   * @return
   * @throws CommonRestException PortRotationResponse
   */
  @GetMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/port-rotation")
  public PortRotationResponse getPortRotation(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getPortRotation: {}", getClientIp());
      log.info(
          "Inside getPortRotation gateway controller with correlationId : "
              + headers.getFirst(CORRELATION_ID_HEADER));
      return this.loadableStudyService.getPortRotation(
          loadableStudyId, headers.getFirst(CORRELATION_ID_HEADER));

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when get port rotation", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error when saving loadable study", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Delete port rotation by id
   *
   * @param id
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @DeleteMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PortRotationResponse deletePortRotation(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("deletePortRotation: {}", getClientIp());
      return this.loadableStudyService.deletePortRotation(
          loadableStudyId, id, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting port rotation", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when deleting port rotation", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * @param vesselId
   * @param voyageId
   * @param loadableStudiesId
   * @param headers
   * @return
   * @throws CommonRestException LoadablePatternResponse
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-patterns")
  public LoadablePatternResponse getLoadablePatterns(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudiesId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("get loadable-patterns : {}", getClientIp());
      log.info(
          "get loadable pattern API. correlationId: {} ", headers.getFirst(CORRELATION_ID_HEADER));
      return loadableStudyService.getLoadablePatterns(
          loadableStudiesId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in get loadable patterns ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in get loadable patterns ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * @param vesselId
   * @param voyageId
   * @param loadableStudiesId
   * @param loadablePatternId
   * @param loadablePatternDetailsId
   * @param headers
   * @return
   * @throws CommonRestException LoadablePatternDetailsResponse
   */
  @GetMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-patterns/{loadablePatternId}/loadable-pattern-commingle-details/{loadablePatternCommingleDetailsId}")
  public LoadablePatternDetailsResponse getLoadablePatternCommingleDetails(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudiesId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadablePatternId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadablePatternCommingleDetailsId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("get loadable-patterns : {}", getClientIp());
      log.info(
          "get loadable pattern API. correlationId: {} ", headers.getFirst(CORRELATION_ID_HEADER));
      return loadableStudyService.getLoadablePatternCommingleDetails(
          loadablePatternCommingleDetailsId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in get loadable pattern details ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in get loadable pattern details ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Get on hand quantity
   *
   * @param vesselId
   * @param loadableStudyId
   * @param portId
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{portId}/on-hand-quantities")
  public OnHandQuantityResponse getOnHandQuantity(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getOnHandQuantity: {}", getClientIp());
      // TODO
      final Long companyId = 1L;
      return this.loadableStudyService.getOnHandQuantity(
          companyId, vesselId, loadableStudyId, portId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching on hand quantities", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching on hand quantities", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}"
              + "/ports/{portId}/on-hand-quantities/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public OnHandQuantityResponse saveOnHandQuantity(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestBody @Valid OnHandQuantity request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      request.setId(id);
      request.setPortId(portId);
      request.setLoadableStudyId(loadableStudyId);
      return this.loadableStudyService.saveOnHandQuantity(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on hand quantities", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving on hand quantities", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * Get commingle cargo
   *
   * @param vesselId
   * @param loadableStudyId
   * @param
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @GetMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/commingle-cargo")
  public CommingleCargoResponse getCommingleCargo(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("getCommingleCargo: {}", getClientIp());
      return this.loadableStudyService.getCommingleCargo(loadableStudyId, vesselId);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException getCommingleCargo", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception getCommingleCargo", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Save commingle cargo
   *
   * @param vesselId
   * @param loadableStudyId
   * @param
   * @param headers
   * @return
   * @throws CommonRestException
   */
  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/commingle-cargo")
  public CommingleCargoResponse saveCommingleCargo(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @RequestBody CommingleCargo commingleCargo,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("saveCommingleCargo: {}", getClientIp());
      return this.loadableStudyService.saveCommingleCargo(loadableStudyId, commingleCargo);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException saveCommingleCargo", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception saveCommingleCargo", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  /**
   * @param vesselId
   * @param voyageId
   * @param loadableStudiesId
   * @param headers
   * @throws CommonRestException void
   */
  @PostMapping(
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/generate-loadable-patterns")
  public void generateLoadablePatterns(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudiesId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("call ALGO. correlationId: {} ", headers.getFirst(CORRELATION_ID_HEADER));
      loadableStudyService.generateLoadablePatterns(
          loadableStudiesId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in calling ALGO ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in calling ALGO ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.SERVICE_UNAVAILABLE,
          e.getMessage(),
          e);
    }
  }

  @GetMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{portId}/on-board-quantities")
  public OnBoardQuantityResponse getOnBoardQuantites(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info(
          "getOnBoardQuantites: {}, correlationId: {}",
          getClientIp(),
          headers.getFirst(CORRELATION_ID_HEADER));
      log.debug(
          "getOnBoardQuantites, vesselId:{}, loadableStudyId:{}, portId:{}",
          vesselId,
          loadableStudyId,
          portId);
      return this.loadableStudyService.getOnBoardQuantites(
          vesselId, voyageId, loadableStudyId, portId, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching on board quantities", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching on board quantities", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{portId}/on-board-quantities/{id}")
  public OnBoardQuantityResponse saveOnBoardQuantites(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long portId,
      @PathVariable @Min(value = 0, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long id,
      @RequestBody @Valid OnBoardQuantity request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      request.setId(id);
      request.setLoadableStudyId(loadableStudyId);
      request.setPortId(portId);
      log.info(
          "saveOnBoardQuantites: {}, correlationId: {}",
          getClientIp(),
          headers.getFirst(CORRELATION_ID_HEADER));
      log.debug(
          "saveOnBoardQuantites, loadableStudyId:{}, portId:{}, id: {}",
          loadableStudyId,
          portId,
          id);
      return this.loadableStudyService.saveOnBoardQuantites(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on board quantities", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when fetching on saving quantities", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @PostMapping(
      value =
          "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-study-status/{loadableStudystatusId}")
  public AlgoStatusResponse updateLoadableStudyStatus(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudyId,
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
          Long loadableStudystatusId,
      @RequestBody AlgoStatusRequest request,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      request.setLoadableStudystatusId(loadableStudystatusId);
      return this.loadableStudyService.saveAlgoLoadableStudyStatus(
          request, headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when updating loadable Study status", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when updating loadable Study status", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Returns the caller ip for debugging
   *
   * @return
   */
  private static String getClientIp() {
    HttpServletRequest curRequest =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String remoteAddr = "";
    remoteAddr = curRequest.getHeader("X-FORWARDED-FOR");
    if (remoteAddr == null || "".equals(remoteAddr)) {
      remoteAddr = curRequest.getRemoteAddr();
    }
    return remoteAddr;
  }
}
