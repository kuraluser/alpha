/* Licensed at AlphaOri Technologies */
package com.cpdss.common.scheduler;

import com.cpdss.common.exception.GenericServiceException;
import java.util.Map;

/** Execute task listener */
public interface ExecuteTaskListener {

  public void listen(String taskName, Map<String, String> taskReqParams)
      throws GenericServiceException;
}
