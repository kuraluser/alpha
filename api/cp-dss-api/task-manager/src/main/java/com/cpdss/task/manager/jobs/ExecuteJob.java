/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.jobs;

import static com.cpdss.task.manager.commons.TaskManagerConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.TaskManager;
import com.cpdss.common.generated.TaskManagerServiceGrpc;
import com.cpdss.common.scheduler.ScheduledTaskProperties;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.cpdss.task.manager.commons.TaskManagerConstants;
import com.cpdss.task.manager.entity.SchedulerJobInfo;
import com.cpdss.task.manager.repository.SchedulerRepository;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/** Job Class to Execute The Job */
@Log4j2
@Component
public class ExecuteJob extends QuartzJobBean {

  @Autowired private SchedulerRepository schedulerRepository;

  private Gson gson = new Gson();

  /**
   * executeInternal call the target job url using restTemplate. In case in error it try to refire
   * the job.
   *
   * @param context - JobExecutionContext.
   * @throws JobExecutionException - JobExecutionException
   */
  @Override
  protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
    ManagedChannel channel = null;
    JobDetail detail = null;
    try {
      detail = context.getJobDetail();
      log.info(JOB_STARTED + detail.getKey());
      channel =
          ManagedChannelBuilder.forTarget(detail.getJobDataMap().getString(TASK_URL))
              .usePlaintext()
              .build();
      log.info(JOB_STARTED+" invoking Target " + channel);
      Optional<SchedulerJobInfo> schedulerJobInfoOptional =
          schedulerRepository.findByTaskName(detail.getKey().getName());
      if (!schedulerJobInfoOptional.isPresent()) {
        throw new JobExecutionException("Job not found with the name " + detail.getKey().getName());
      }
      Map<String, String> requestParams =
          gson.fromJson(detail.getJobDataMap().getString(REQUEST_BODY), Map.class);
      TaskManager.ExecuteTaskRequest.Builder builder = TaskManager.ExecuteTaskRequest.newBuilder();
      builder.setTaskName(detail.getKey().getName());
      builder.putAllTaskReqParam(requestParams);
      ScheduledTaskProperties.TaskTypeEnum taskTypeEnum =
          ScheduledTaskProperties.TaskTypeEnum.valueOf(
              schedulerJobInfoOptional.get().getTaskType());
      switch (taskTypeEnum) {
        case SYNC:
          TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerBlockingService =
              TaskManagerServiceGrpc.newBlockingStub(channel);
          TaskManager.ExecuteTaskReply executeTaskReply =
              taskManagerBlockingService.executeTask(builder.build());
          Common.ResponseStatus responseStatus = executeTaskReply.getResponseStatus();
          if (responseStatus.getStatus().contentEquals(Utils.STATUS.FAILED.name())) {
            log.error("Task Execution has failed with message" + responseStatus.getMessage());
            throw new GenericServiceException(
                responseStatus.getMessage(),
                responseStatus.getCode(),
                HttpStatusCode.INTERNAL_SERVER_ERROR);
          }
          log.info(JOB_COMPLETED + detail.getKey());
          break;
        case ASYNC:
          final JobKey asycJobKey = detail.getKey();
          TaskManagerServiceGrpc.TaskManagerServiceFutureStub taskManagerNonBlockingService =
              TaskManagerServiceGrpc.newFutureStub(channel);
          ListenableFuture<TaskManager.ExecuteTaskReply> executeTaskReplyListener =
              taskManagerNonBlockingService.executeTask(builder.build());
          executeTaskReplyListener.addListener(
              () -> {
                try {
                  if (!executeTaskReplyListener.isDone()) {
                    return;
                  }
                  Common.ResponseStatus asyncResponseStatus =
                      executeTaskReplyListener.get().getResponseStatus();
                  if (asyncResponseStatus.getStatus().contentEquals(Utils.STATUS.FAILED.name())) {
                    log.error(
                        "Task Execution has failed with message"
                            + asyncResponseStatus.getMessage());
                    throw new GenericServiceException(
                        asyncResponseStatus.getMessage(),
                        asyncResponseStatus.getCode(),
                        HttpStatusCode.INTERNAL_SERVER_ERROR);
                  }
                  log.info(JOB_COMPLETED + asycJobKey);
                } catch (InterruptedException | ExecutionException | GenericServiceException e) {
                  log.error(asycJobKey + JOB_INTERRUPTED + e.getMessage());
                }
              },
              MoreExecutors.directExecutor());

          break;
        default:
          throw new JobExecutionException(
              "Unknown task type for the task with name " + detail.getKey().getName());
      }
    } catch (Exception e) {
      log.error(detail.getKey() + JOB_INTERRUPTED + e.getMessage());
      // In case any error during the call, the job will refire immediately
      //	if the time interval is greater or equal to one hour.
      final JobExecutionException jobException = new JobExecutionException(e);
      if (detail.getJobDataMap().getInt(TIME_FREQUENCY) >= TaskManagerConstants.JOB_REFIRE_VALUE) {
        log.info(JOB_REFIRED + detail.getKey());
        jobException.setRefireImmediately(true);
      }
    } finally {
      if (channel != null) {
        channel.shutdown();
      }
    }
  }
}
