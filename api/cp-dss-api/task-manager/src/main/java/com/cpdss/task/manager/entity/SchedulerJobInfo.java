/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.entity;

import com.cpdss.common.utils.EntityDoc;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** SchedulerJobInfo Entity Class */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scheduler_job_info")
public class SchedulerJobInfo extends EntityDoc {

  @Column(name = "task_name", unique = true)
  private String taskName;

  @Column(name = "job_group")
  private String jobGroup;

  @Column(name = "task_uri")
  private String taskURI;

  @Column(name = "task_frequency")
  private int taskFrequency;

  @Column(name = "cron_expression")
  private String cronExpression;

  @Column(name = "task_type")
  private String taskType;

  @Column(name = "task_start_date")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate taskStartDate;

  @Column(name = "task_end_date")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate taskEndDate;

  @Column(name = "task_start_time")
  @JsonFormat(pattern = "HH:mm:ss")
  private LocalTime taskStartTime;

  @Column(name = "task_end_time")
  @JsonFormat(pattern = "HH:mm:ss")
  private LocalTime taskEndTime;

  @ElementCollection
  @MapKeyColumn(name = "name")
  @Column(name = "value")
  @CollectionTable(
      name = "task_req_param_attributes",
      joinColumns = @JoinColumn(name = "task_req_param_id"))
  private Map<String, String> taskReqParam;
}
