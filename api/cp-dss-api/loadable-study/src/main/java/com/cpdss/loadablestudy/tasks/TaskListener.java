/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.tasks;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.scheduler.ExecuteTaskListener;
import java.util.Map;
import org.springframework.stereotype.Service;

/** Task Listener service for executing scheduled tasks */
@Service
public class TaskListener implements ExecuteTaskListener {

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
  }
}
