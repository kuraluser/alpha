/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyCargoResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyRequest;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyUpdateResponse;
import com.cpdss.gateway.domain.AlgoPatternResponse;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.OnHandQuantity;
import com.cpdss.gateway.domain.OnHandQuantityResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.PortWiseCargoResponse;
import com.cpdss.gateway.service.DischargeStudyService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** @Author jerin.g */
@Log4j2
@Validated
@RestController
@RequestMapping({ "/api/cloud", "/api/ship" })
public class DischargeStudyController {

	private static final String CORRELATION_ID_HEADER = "correlationId";

	@Autowired
	private DischargeStudyService dischargeStudyService;

	/**
	 * @param vesselId
	 * @param voyageId
	 * @param loadableStudiesId
	 * @param headers
	 * @throws CommonRestException void
	 */
	@PostMapping("/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudiesId}/generate-discharge-patterns")
	public AlgoPatternResponse generateDischargePatterns(
			@PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long vesselId,
			@PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long voyageId,
			@PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long dischargeStudiesId,
			@RequestHeader HttpHeaders headers) throws CommonRestException {
		try {
			log.info("call Discharge study ALGO. correlationId: {} ", headers.getFirst(CORRELATION_ID_HEADER));
			return dischargeStudyService.generateDischargePatterns(vesselId, voyageId, dischargeStudiesId,
					headers.getFirst(CORRELATION_ID_HEADER));
		} catch (GenericServiceException e) {
			log.error("GenericServiceException in calling ALGO ", e);
			throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
		} catch (Exception e) {
			log.error("Error in calling ALGO ", e);
			throw new CommonRestException(CommonErrorCodes.E_GEN_INTERNAL_ERR, headers,
					HttpStatusCode.SERVICE_UNAVAILABLE, e.getMessage(), e);
		}
	}
}
