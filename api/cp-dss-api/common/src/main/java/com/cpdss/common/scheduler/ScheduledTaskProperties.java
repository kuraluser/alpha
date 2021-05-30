/* Licensed at AlphaOri Technologies */
package com.cpdss.common.scheduler;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** SchedulerJobInfoRequest Class */
@Getter
@Setter
@ToString
public class ScheduledTaskProperties {

  @NotBlank(message = "{dto.taskname.must.not.blank}")
  private String taskName;

  @NotBlank(message = "{dto.task.uri.must.not.blank}")
  private String taskURI;

  private Map<String, String> taskReqParam;

  private String cronExpression;

  private TaskTypeEnum taskType;

  public enum TaskTypeEnum {
    SYNC,
    ASYNC
  };

  private Integer taskFrequency;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate taskStartDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate taskEndDate;

  @JsonFormat(pattern = "HH:mm:ss")
  private LocalTime taskStartTime;

  @JsonFormat(pattern = "HH:mm:ss")
  private LocalTime taskEndTime;
}
