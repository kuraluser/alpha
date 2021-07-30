/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.tasks;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.service.CommunicationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Task Listener service for executing scheduled tasks */
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
    System.out.println("Communication Enabled " + enableCommunication);
    if (enableCommunication) {
      if (taskName.contains("DOWNLOAD_RESULT_")) {
        if (taskReqParams.get("env").equals("ship")) {
          communicationService.getDataFromCommInShipSide(taskReqParams, MessageTypes.ship);
        } else {
          communicationService.getDataFromCommInShoreSide(taskReqParams, MessageTypes.shore);
        }

      } else if (taskName.contains("STATUS_CHECK_")) {
        communicationService.checkLoadableStudyStatus(taskReqParams);
      }
    }
  }
}
