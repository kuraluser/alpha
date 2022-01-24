/* Licensed at AlphaOri Technologies */
package com.cpdss.common.domain;

import java.util.Map;
import lombok.Data;

@Data
public class TaskExecutionRequest {
  private Map<String, String> taskReqParam;
}
