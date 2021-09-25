/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.ListOfUllageReportResponse;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class CommonController {

  private static final String CORRELATION_ID_HEADER = "correlationId";
  @Autowired LoadingPlanService loadingPlanService;

  /**
   * To import ullage report excel file
   *
   * @param headers
   * @param file
   * @return UploadTideDetailResponse
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/import/ullage-report-file",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ListOfUllageReportResponse importUllageReportFile(
      @RequestHeader HttpHeaders headers,
      @RequestParam(name = "file", required = true) MultipartFile file,
      @RequestParam(name = "infoId", required = true) Long infoId,
      @RequestParam(name = "isLoading", required = true) boolean isLoading,
      @RequestParam(name = "cargoNominationId", required = true) Long cargoNominationId,
      @RequestParam(name = "vesselId", required = true) Long vesselId,
      @RequestParam(name = "tanks", required = true) Object tanks)
      throws CommonRestException {
    try {
      return loadingPlanService.importUllageReportFile(
          file,
          tanks.toString(),
          infoId,
          cargoNominationId,
          headers.getFirst(CORRELATION_ID_HEADER),
          isLoading,
          vesselId);

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when upload ullage report details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when upload ullage report details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
