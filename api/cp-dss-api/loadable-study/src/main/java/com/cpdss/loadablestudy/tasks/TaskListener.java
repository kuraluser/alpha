/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.tasks;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CPDSS_BUILD_ENV_SHORE;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.ENV;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.common.utils.StagingStatus;
import com.cpdss.loadablestudy.service.CommunicationService;
import com.cpdss.loadablestudy.service.LoadableStudyCommunicationService;
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
  @Autowired private LoadableStudyCommunicationService loadableStudyCommunicationService;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  public static final String ENABLED = "Enabled";
  public static final String DISABLED = "Disabled";
  public static final String DOWNLOAD_TASK_PREFIX = "DOWNLOAD_RESULT_";
  public static final String STATUS_CHECK_TASK_PREFIX = "STATUS_CHECK_";
  public static final String LOADABLE_DATA_UPDATE_TASK_PREFIX = "LOADABLE_DATA_UPDATE";
  public static final String STOWAGE_DATA_UPDATE_TASK_PREFIX = "STOWAGE_DATA_UPDATE";
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
        if (CPDSS_BUILD_ENV_SHIP.equals(taskReqParams.get(ENV))) {
          communicationService.getDataFromCommInShipSide(
              taskName, taskReqParams, MessageTypes.loadableShip);
        } else if (CPDSS_BUILD_ENV_SHORE.equals(taskReqParams.get(ENV))) {
          communicationService.getDataFromCommInShoreSide(
              taskName, taskReqParams, MessageTypes.loadableShore);
        } else {
          log.error(
              "Invalid env configured in task_req_param_attributes. Task: {}, Env: {}",
              taskName,
              taskReqParams.get(ENV));
        }
      }
      // Save LoadableStudy data from staging table to corresponding tables
      else if (taskName.startsWith(LOADABLE_DATA_UPDATE_TASK_PREFIX)) {
        loadableStudyCommunicationService.getLoadableStudyStagingData(
            StagingStatus.READY_TO_PROCESS.getStatus(), taskReqParams.get(ENV), taskName);
        loadableStudyCommunicationService.getLoadableStudyStagingData(
            StagingStatus.RETRY.getStatus(), taskReqParams.get(ENV), taskName);
        loadableStudyCommunicationService.getLoadableStudyStagingData(
            StagingStatus.IN_PROGRESS.getStatus(), taskReqParams.get(ENV), taskName);
      }
      // Save stowage data from staging table to corresponding tables
      else if (taskName.startsWith(STOWAGE_DATA_UPDATE_TASK_PREFIX)) {
        loadableStudyCommunicationService.getStowageStagingData(
            StagingStatus.READY_TO_PROCESS.getStatus(), taskReqParams.get(ENV), taskName);
        loadableStudyCommunicationService.getStowageStagingData(
            StagingStatus.RETRY.getStatus(), taskReqParams.get(ENV), taskName);
        loadableStudyCommunicationService.getStowageStagingData(
            StagingStatus.IN_PROGRESS.getStatus(), taskReqParams.get(ENV), taskName);
      }
      // Status check task - fallback mechanism on timeout
      else if (taskName.startsWith(STATUS_CHECK_TASK_PREFIX)) {
        // communicationService.checkLoadableStudyStatus(taskReqParams);
      }
      // Task configured in DB but not implemented
      else {
        log.warn("Task not implemented. Task: {}, Params: {}", taskName, taskReqParams);
      }
    }
  }
}
