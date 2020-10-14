/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gateway controller for loadable study related operations
 * @author suhail.k
 *
 */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class LoadableStudyController {

  @Autowired private LoadableStudyService loadableStudyService;

  private static final String CORRELATION_ID_HEADER = "correlationId";

  /**
   * Get list of loadable studies baed on vessel and voyage
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
}
