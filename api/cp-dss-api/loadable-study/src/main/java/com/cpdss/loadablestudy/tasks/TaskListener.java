/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.tasks;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.service.LoadableStudyService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Task Listener service for executing scheduled tasks */
@Service
public class TaskListener implements ExecuteTaskListener {
  @Autowired private LoadableStudyService loadableStudyService;
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
    System.out.println("Executing task " + taskName);
    if (taskName.equals("DOWNLOAD_RESULT1")) {
      if (taskReqParams.get("messageType").equals(String.valueOf(MessageTypes.LOADABLESTUDY)))
        loadableStudyService.saveLoadableStudyShore(taskReqParams);
      else if (taskReqParams.get("messageType").equals(String.valueOf(MessageTypes.LOADABLESTUDY)))
        loadableStudyService.saveAlgoPatternFromShore(taskReqParams);
    }
  }
}
