/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.tasks;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.service.CommunicationService;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Task Listener service for executing scheduled tasks */
@Log4j2
@Service
public class TaskListener implements ExecuteTaskListener {
  @Autowired private CommunicationService communicationService;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;
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
    // To-Do Task Execution
    log.info("Communication Enabled " + enableCommunication);
    if (enableCommunication) {
      if (taskName.contains("DOWNLOAD_RESULT_")) {
        log.info("inside TaskNmae " + taskName);
        if (taskReqParams.get("env").equals("ship")) {
          communicationService.getDataFromCommInShipSide(taskReqParams, MessageTypes.ship);
        } else {
          log.info("inside taskReqParams " + taskReqParams.get("env"));
          communicationService.getDataFromCommInShoreSide(taskReqParams, MessageTypes.shore);
        }

      } else if (taskName.contains("STATUS_CHECK_")) {
        // communicationService.checkLoadableStudyStatus(taskReqParams);
      }
    }
  }
}
