/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.domain;

import com.cpdss.task.manager.validate.ValidatorGroups;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** SchedulerJobInfoRequest Class */
@Getter
@Setter
@ToString
public class SchedulerJobInfoRequest {

  @Null(
      groups = {ValidatorGroups.Post.class},
      message = "{id.should.be.null}")
  private Long id;

  @NotBlank(
      groups = {ValidatorGroups.Post.class},
      message = "{dto.taskname.must.not.blank}")
  private String taskName;

  @NotBlank(
      groups = {ValidatorGroups.Post.class},
      message = "{dto.task.uri.must.not.blank}")
  private String taskURI;

  private Map<String, String> taskReqParam;

  private String cronExpression;

  private String taskType;

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
