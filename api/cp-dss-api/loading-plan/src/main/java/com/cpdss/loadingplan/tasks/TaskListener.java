/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.tasks;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Task Listener service for executing scheduled tasks */
@Log4j2
@Service
public class TaskListener implements ExecuteTaskListener {
  @Autowired private LoadingPlanCommunicationService communicationService;

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
    log.info("Task Name :" + taskName);
    if (enableCommunication) {
      if (taskName.contains("LOADING_PLAN_DOWNLOAD_RESULT")) {
        log.info("inside TaskName " + taskName);
        if (taskReqParams.get("env").equals("ship")) {
          communicationService.getDataFromCommunication(
              taskReqParams, MessageTypes.LOADINGPLAN_ALGORESULT.getMessageType());
        } else {
          log.info("inside taskReqParams " + taskReqParams.get("env"));
          communicationService.getDataFromCommunication(
              taskReqParams, MessageTypes.LOADINGPLAN.getMessageType());
        }

      } else if (taskName.contains("LOADING_DATA_UPDATE")) {
        log.info("inside TaskName " + taskName);
        communicationService.getStagingData(
            StagingStatus.READY_TO_PROCESS.getStatus(), taskReqParams.get("env"));
        communicationService.getStagingData(
            StagingStatus.RETRY.getStatus(), taskReqParams.get("env"));
        communicationService.getStagingData(
            StagingStatus.IN_PROGRESS.getStatus(), taskReqParams.get("env"));
      } else if (taskName.contains("LOADING_PLAN_STATUS_CHECK")) {
        // communicationService.checkLoadableStudyStatus(taskReqParams);
      }
    }
  }
}
