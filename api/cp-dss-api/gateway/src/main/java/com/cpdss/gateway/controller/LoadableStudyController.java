/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.CargoNomination;
import com.cpdss.gateway.domain.CargoNominationResponse;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;

import lombok.extern.log4j.Log4j2;

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
      @PathVariable long vesselId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
      return loadableStudyService.saveVoyage(voyage, companyId, vesselId);
    } catch (Exception e) {
      log.error("Error in save voyage ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatus.SERVICE_UNAVAILABLE,
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
   * @throws CommonRestException
   * CommonSuccessResponse
   */
  @PostMapping("/vessels/{vesselId}/voyage/{voyageId}/loadable-studies/{loadableStudiesId}")
  public LoadableQuantityResponse saveLoadableQuantity(
      @RequestBody @Valid LoadableQuantity loadableQuantity,
      @PathVariable long loadableStudiesId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
    	return
    			loadableStudyService.saveLoadableQuantity(loadableQuantity, loadableStudiesId);
    } catch (Exception e) {
      log.error("Error in save loadable quantity ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatus.SERVICE_UNAVAILABLE,
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
					HttpStatus.INTERNAL_SERVER_ERROR,
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
			request.setVesselId(vesselId);
			request.setVoyageId(voyageId);
			request.setCompanyId(1L);
			return this.loadableStudyService.saveLoadableStudy(
					request, headers.getFirst(CORRELATION_ID_HEADER), files);
		} catch (GenericServiceException e) {
			log.error("GenericServiceException when fetching loadable study", e);
			throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
		} catch (Exception e) {
			log.error("Error fetching loadable study", e);
			throw new CommonRestException(
					CommonErrorCodes.E_GEN_INTERNAL_ERR,
					headers,
					HttpStatus.INTERNAL_SERVER_ERROR,
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
          HttpStatus.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
  
	/**
	 * Retrieves cargo nominations set for the loadable study
	 * Retrieves port info details from master
	 * Retrieves cargo info details from master
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
			@RequestHeader HttpHeaders headers) throws CommonRestException {
		CargoNominationResponse response = null;
		try {
			Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
			response = loadableStudyService.getCargoNomination(loadableStudyId, headers);
		} catch (Exception e) {
			log.error("Error in getCargoNomination ", e);
			throw new CommonRestException(
					CommonErrorCodes.E_GEN_INTERNAL_ERR,
					headers,
					HttpStatus.SERVICE_UNAVAILABLE,
					e.getMessage(),
					e);
		}
		return response;
	}
	
	@PostMapping(value = "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CargoNominationResponse saveCargoNomination(
			@PathVariable Long vesselId,
			@PathVariable Long voyageId,
			@PathVariable Long loadableStudyId,
			@PathVariable Long id,
			@RequestBody CargoNomination cargoNomination,
			@RequestHeader HttpHeaders headers) throws CommonRestException {
		CargoNominationResponse response = null;
		try {
			Long companyId = 1L; // TODO get the companyId from userContext in keycloak token
			response = loadableStudyService.saveCargoNomination(vesselId, voyageId, loadableStudyId, cargoNomination, headers);
		} catch (Exception e) {
			log.error("Error in getCargoNomination ", e);
			throw new CommonRestException(
					CommonErrorCodes.E_GEN_INTERNAL_ERR,
					headers,
					HttpStatus.SERVICE_UNAVAILABLE,
					e.getMessage(),
					e);
		}
		return response;
	}
}
