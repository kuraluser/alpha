/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.communication;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.service.communication.models.TaskExecutionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileShareController {

  @Autowired private FileSharingStagingAndDownloaderService fileSharingStagingAndDownloaderService;

  @PostMapping("/filestage")
  public ResponseEntity<String> stageSave(@RequestBody TaskExecutionRequest taskExecutionRequest)
      throws GenericServiceException {
    fileSharingStagingAndDownloaderService.saveToStage(taskExecutionRequest);
    return ResponseEntity.ok("Success");
  }

  @PostMapping("/filecommunication")
  public ResponseEntity<String> fileCommunication() {
    fileSharingStagingAndDownloaderService.saveToFileRepo();
    return ResponseEntity.ok("Success");
  }
}
