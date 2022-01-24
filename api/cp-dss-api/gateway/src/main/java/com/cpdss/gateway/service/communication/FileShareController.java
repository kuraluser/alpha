/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.communication;

import com.cpdss.common.domain.TaskExecutionRequest;
import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class FileShareController {

  @Autowired private FileSharingStagingAndDownloaderService fileSharingStagingAndDownloaderService;
  private static final String CORRELATION_ID_HEADER = "correlationId";

  @PostMapping("/file-stage")
  public CommonSuccessResponse stageSave(
      @RequestHeader HttpHeaders headers, @RequestBody TaskExecutionRequest taskExecutionRequest)
      throws CommonRestException {
    try {
      return fileSharingStagingAndDownloaderService.saveToStage(
          taskExecutionRequest.getTaskReqParam(), headers.getFirst(CORRELATION_ID_HEADER));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving to staging table and download file", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving to staging table and download file", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @PostMapping("/file-communication")
  public CommonSuccessResponse fileCommunication(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      return fileSharingStagingAndDownloaderService.saveToFileRepo(
          headers.getFirst(CORRELATION_ID_HEADER));
    } catch (Exception e) {
      log.error("Exception when saving to file repo table", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
