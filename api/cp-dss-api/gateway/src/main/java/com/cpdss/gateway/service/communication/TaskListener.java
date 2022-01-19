/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.communication;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Task Listener service for executing scheduled tasks */
@Log4j2
@Service
public class TaskListener implements ExecuteTaskListener {

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Autowired private FileSharingStagingAndDownloaderService fileSharingStagingAndDownloaderService;
  /**
   * Task Listener
   *
   * @param taskName
   * @param taskReqParams
   * @throws GenericServiceException
   */
  @Override
  public void listen(String taskName, Map<String, String> taskReqParams)
      throws GenericServiceException {

    log.info("Communication Enabled : " + enableCommunication);
    log.info("Task Name :" + taskName);
    if (enableCommunication) {
      if (taskName.startsWith("FILE_SHARE_STAGE_DOWNLOAD")) {
        fileSharingStagingAndDownloaderService.saveToStage(taskReqParams);
      } else if (taskName.startsWith("FILE_SHARE_DATA_UPDATE")) {
        fileSharingStagingAndDownloaderService.saveToFileRepo();
      }
    }
  }
}
