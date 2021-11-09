/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.tasks;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CPDSS_BUILD_ENV_SHORE;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.ENV;

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

  public static final String ENABLED = "Enabled";
  public static final String DISABLED = "Disabled";
  public static final String DOWNLOAD_TASK_PREFIX = "DOWNLOAD_RESULT_";
  public static final String STATUS_CHECK_TASK_PREFIX = "STATUS_CHECK_";

  /**
   * Task Listener
   *
   * @param taskName taskName value from scheduler_job_info table
   * @param taskReqParams param map from task_req_param_attributes table
   * @throws GenericServiceException Exception on failure
   */
  @Override
  public void listen(String taskName, Map<String, String> taskReqParams)
      throws GenericServiceException {

    log.info("Communication Status: {}", enableCommunication ? ENABLED : DISABLED);

    if (enableCommunication) {

      log.info("Executing Task: {}, Params: {}", taskName, taskReqParams);

      // Download result task
      if (taskName.startsWith(DOWNLOAD_TASK_PREFIX)) {
        if (taskReqParams.get(ENV).equals(CPDSS_BUILD_ENV_SHIP)) {
          communicationService.getDataFromCommInShipSide(taskReqParams, MessageTypes.ship);
        } else if (taskReqParams.get(ENV).equals(CPDSS_BUILD_ENV_SHORE)) {
          communicationService.getDataFromCommInShoreSide(taskReqParams, MessageTypes.shore);
        } else {
          log.error(
              "Invalid env configured in task_req_param_attributes. Task: {}, Env: {}",
              taskName,
              taskReqParams.get(ENV));
        }
      }
      // Status check task - fallback mechanism on timeout
      else if (taskName.startsWith(STATUS_CHECK_TASK_PREFIX)) {
        communicationService.checkLoadableStudyStatus(taskReqParams);
      }
      // Task configured in DB but not implemented
      else {
        log.warn("Task not implemented. Task: {}, Params: {}", taskName, taskReqParams);
      }
    }
  }
}
