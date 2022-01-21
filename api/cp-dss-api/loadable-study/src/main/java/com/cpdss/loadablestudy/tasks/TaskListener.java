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
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
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
  public static final String LOADABLE_STUDY_DOWNLOAD_TASK_PREFIX = "LOADABLE_STUDY_DOWNLOAD_RESULT";
  public static final String STATUS_CHECK_TASK_PREFIX = "STATUS_CHECK";
  public static final String LOADABLE_STUDY_DATA_UPDATE_TASK_PREFIX = "LOADABLE_STUDY_DATA_UPDATE";
  public static final String STOWAGE_DATA_UPDATE_TASK_PREFIX = "STOWAGE_DATA_UPDATE";
  public static final String DISCHARGE_STUDY_DOWNLOAD_TASK_PREFIX =
      "DISCHARGE_STUDY_DOWNLOAD_RESULT";
  public static final String DISCHARGE_STUDY_DATA_UPDATE_TASK_PREFIX =
      "DISCHARGE_STUDY_DATA_UPDATE";

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
      if (taskName.startsWith(LOADABLE_STUDY_DOWNLOAD_TASK_PREFIX)) {
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
      else if (taskName.startsWith(LOADABLE_STUDY_DATA_UPDATE_TASK_PREFIX)) {
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
      // Download result task for Discharge Study
      else if (taskName.startsWith(DISCHARGE_STUDY_DOWNLOAD_TASK_PREFIX)) {
        if (CPDSS_BUILD_ENV_SHORE.equals(taskReqParams.get(ENV))) {
          communicationService.getDataFromCommInShoreSide(
              taskName, taskReqParams, MessageTypes.dischargeStudyShore);
        } else {
          log.error(
              "Invalid env configured in task_req_param_attributes. Task: {}, Env: {}",
              taskName,
              taskReqParams.get(ENV));
        }
      }
      // Save data from staging table to corresponding tables
      else if (taskName.startsWith(DISCHARGE_STUDY_DATA_UPDATE_TASK_PREFIX)) {
        loadableStudyCommunicationService.getDischargeStudyStagingData(
            StagingStatus.READY_TO_PROCESS.getStatus(), taskReqParams.get(ENV), taskName);
        loadableStudyCommunicationService.getDischargeStudyStagingData(
            StagingStatus.RETRY.getStatus(), taskReqParams.get(ENV), taskName);
        loadableStudyCommunicationService.getDischargeStudyStagingData(
            StagingStatus.IN_PROGRESS.getStatus(), taskReqParams.get(ENV), taskName);
      } else if (taskName.startsWith(LoadableStudiesConstants.FILE_SHARE_STAGE_DOWNLOAD_TASK)) {
        loadableStudyCommunicationService.callSaveToStageInGateWay(taskName, taskReqParams);
      } else if (taskName.startsWith(LoadableStudiesConstants.FILE_SHARE_DATA_UPDATE_TASK)) {
        loadableStudyCommunicationService.callSaveToFileRepoInGateWay(taskName, taskReqParams);
      }
      // Task configured in DB but not implemented
      else {
        log.warn("Task not implemented. Task: {}, Params: {}", taskName, taskReqParams);
      }
    }

    // Status check task - fallback mechanism on timeout
    if (taskName.startsWith(STATUS_CHECK_TASK_PREFIX)) {
      communicationService.checkCommunicationStatus(taskReqParams, MessageTypes.LOADABLESTUDY);
      communicationService.checkCommunicationStatus(taskReqParams, MessageTypes.VALIDATEPLAN);
    }
  }
}
