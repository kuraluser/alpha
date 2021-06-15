/* Licensed at AlphaOri Technologies */
package com.cpdss.common.scheduler;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.TaskManager;
import com.cpdss.common.generated.TaskManagerServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.google.protobuf.Timestamp;
import java.time.*;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Class to create scheduled task
 *
 * @param <T>
 */
@Service
@Log4j2
public final class ScheduledTaskRequest<T extends ScheduledTaskProperties> {

  // @GrpcClient("taskManagerService")
  private TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerServiceBlockingStub;

  /**
   * Scheduling a new job trigger
   *
   * @param scheduledTaskProperties
   */
  public void createScheduledTaskRequest(T scheduledTaskProperties) throws GenericServiceException {
    try {
      TaskManager.ScheduleTaskRequest.Builder builder =
          TaskManager.ScheduleTaskRequest.newBuilder();
      builder.setTaskName(scheduledTaskProperties.getTaskName());
      Optional.ofNullable(scheduledTaskProperties.getTaskFrequency())
          .ifPresent(builder::setTaskFrequency);
      Optional.ofNullable(scheduledTaskProperties.getCronExpression())
          .ifPresent(builder::setCronExpression);
      builder.setTaskType(scheduledTaskProperties.getTaskType().name());
      builder.setTaskStartDateTime(
          getTimestamp(
              scheduledTaskProperties.getTaskStartDate(),
              scheduledTaskProperties.getTaskStartTime()));
      builder.setTaskEndDateTime(
          getTimestamp(
              scheduledTaskProperties.getTaskEndDate(), scheduledTaskProperties.getTaskEndTime()));
      builder.setTaskURI(scheduledTaskProperties.getTaskURI());
      builder.putAllTaskReqParam(scheduledTaskProperties.getTaskReqParam());
      TaskManager.TaskManagerReply taskManagerReply =
          this.taskManagerServiceBlockingStub.scheduleTask(builder.build());
      if (taskManagerReply
          .getResponseStatus()
          .getStatus()
          .contentEquals(Utils.STATUS.FAILED.name())) {
        if (taskManagerReply
            .getResponseStatus()
            .getCode()
            .contentEquals(CommonErrorCodes.E_HTTP_CONFLICT)) {
          log.warn(
              String.format(
                  "Task with name %s is already scheduled", scheduledTaskProperties.getTaskName()));
          return;
        }
        throw new GenericServiceException(
            taskManagerReply.getResponseStatus().getMessage(),
            taskManagerReply.getResponseStatus().getCode(),
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      log.info("Task scheduled successfully " + scheduledTaskProperties.getTaskName());
    } catch (Exception e) {
      log.error("Exception when creating scheduled tasks", e);
      throw new GenericServiceException(
          e.getMessage(),
          CommonErrorCodes.E_CPDSS_TASK_SCHEDULE_CREATE_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e);
    }
  }

  /**
   * Method to get timestamp from LocalDate and LocalTime
   *
   * @param date
   * @param time
   * @return
   */
  private Timestamp getTimestamp(LocalDate date, LocalTime time) {
    Instant instant = date.atTime(time).toInstant(ZoneOffset.UTC);
    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano())
        .build();
  }
}
