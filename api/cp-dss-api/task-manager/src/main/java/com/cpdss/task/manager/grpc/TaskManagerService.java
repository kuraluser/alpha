/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.grpc;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.TaskManager;
import com.cpdss.common.generated.TaskManager.ScheduleTaskRequest;
import com.cpdss.common.generated.TaskManager.TaskManagerReply;
import com.cpdss.common.generated.TaskManagerServiceGrpc.TaskManagerServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.scheduler.ScheduledTaskProperties;
import com.cpdss.common.utils.Utils;
import com.cpdss.task.manager.service.SchedulerService;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Grpc Service for TaskScheduler */
@Log4j2
@GrpcService
public class TaskManagerService extends TaskManagerServiceImplBase {

  @Autowired private SchedulerService schedulerService;

  /**
   * Method to schedule a new job
   *
   * @param taskRequest
   * @param responseObserver
   */
  @Override
  public void scheduleTask(ScheduleTaskRequest taskRequest, StreamObserver responseObserver) {
    TaskManagerReply.Builder replyBuilder = TaskManagerReply.newBuilder();
    try {
      ScheduledTaskProperties schedulerJobInfoDTO = new ScheduledTaskProperties();
      schedulerJobInfoDTO.setTaskName(taskRequest.getTaskName());
      schedulerJobInfoDTO.setTaskStartDate(
          getLocalDateTime(taskRequest.getTaskStartDateTime()).toLocalDate());
      schedulerJobInfoDTO.setTaskStartTime(
          getLocalDateTime(taskRequest.getTaskStartDateTime()).toLocalTime());
      schedulerJobInfoDTO.setTaskEndDate(
          getLocalDateTime(taskRequest.getTaskEndDateTime()).toLocalDate());
      schedulerJobInfoDTO.setTaskEndTime(
          getLocalDateTime(taskRequest.getTaskEndDateTime()).toLocalTime());
      schedulerJobInfoDTO.setTaskURI(taskRequest.getTaskURI());
      schedulerJobInfoDTO.setTaskReqParam(taskRequest.getTaskReqParamMap());
      switch (taskRequest.getTaskIntervalCase()) {
        case CRONEXPRESSION:
          schedulerJobInfoDTO.setCronExpression(taskRequest.getCronExpression());
          break;
        case TASKFREQUENCY:
          schedulerJobInfoDTO.setTaskFrequency(taskRequest.getTaskFrequency());
          break;
      }
      schedulerJobInfoDTO.setTaskType(
          ScheduledTaskProperties.TaskTypeEnum.valueOf(taskRequest.getTaskType()));
      schedulerService.scheduleNewJob(schedulerJobInfoDTO);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setMessage("New Task schedule has saved successfully")
              .setStatus(Utils.STATUS.SUCCESS.name())
              .build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving scheduled tasks", e);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when saving scheduled tasks")
              .setStatus(Utils.STATUS.FAILED.name())
              .build());
    } catch (Exception e) {
      log.error("Exception when saving scheduled tasks", e);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving scheduled tasks")
              .setStatus(Utils.STATUS.FAILED.name())
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Method to delete the tasks
   *
   * @param taskDeleteRequest
   * @param responseObserver
   */
  @Override
  public void deleteScheduleTask(
      TaskManager.ScheduleTaskDeleteRequest taskDeleteRequest, StreamObserver responseObserver) {
    TaskManagerReply.Builder replyBuilder = TaskManagerReply.newBuilder();
    try {
      schedulerService.deleteJob(taskDeleteRequest.getTaskName());
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setMessage("Task schedule has deleted successfully")
              .setStatus(Utils.STATUS.SUCCESS.name())
              .build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting scheduled tasks", e);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when deleting scheduled tasks")
              .setStatus(Utils.STATUS.FAILED.name())
              .build());
    } catch (Exception e) {
      log.error("Exception when saving scheduled tasks", e);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when deleting scheduled tasks")
              .setStatus(Utils.STATUS.FAILED.name())
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Method to get local date time from timestamp
   *
   * @param timeStamp
   * @return
   */
  private LocalDateTime getLocalDateTime(com.google.protobuf.Timestamp timeStamp) {
    return LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timeStamp.getSeconds(), timeStamp.getNanos()), ZoneId.of("UTC"));
  }
  /**
   * Method to get schedule Tasks
   *
   * @param request
   * @return
   */
  @Override
  public void getScheduledTasks(
      TaskManager.SchedulededTaskRequest request,
      StreamObserver<TaskManager.SchedulededTaskReply> responseObserver) {
    TaskManager.SchedulededTaskReply.Builder replyBuilder =
        TaskManager.SchedulededTaskReply.newBuilder();
    try {
      final Page<ScheduledTaskProperties> jobInfoList =
          schedulerService.findAll(Pageable.unpaged());
      List<String> taskNameList =
          jobInfoList
              .get()
              .map(
                  jobInfo -> {
                    return jobInfo.getTaskName();
                  })
              .collect(Collectors.toList());

      replyBuilder.addAllTaskName(taskNameList);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setMessage("TaskName Listed successfully")
              .setStatus(Utils.STATUS.SUCCESS.name())
              .build());
    } catch (Exception e) {
      log.error("Exception when getting scheduled tasks", e);
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when getting scheduled tasks")
              .setStatus(Utils.STATUS.FAILED.name())
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
