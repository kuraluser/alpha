package com.cpdss.task.manager.grpc;


import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.TaskManager;
import com.cpdss.common.generated.TaskManager.TaskManagerReply;
import com.cpdss.common.generated.TaskManager.ScheduleTaskRequest;
import com.cpdss.common.generated.TaskManagerServiceGrpc.TaskManagerServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.task.manager.domain.SchedulerJobInfoRequest;
import com.cpdss.task.manager.service.SchedulerService;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Grpc Service for TaskScheduler
 */
@Log4j2
@GrpcService
public class TaskManagerService extends TaskManagerServiceImplBase {

    @Autowired
    private SchedulerService schedulerService;

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";

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
            SchedulerJobInfoRequest schedulerJobInfoDTO = new SchedulerJobInfoRequest();
            schedulerJobInfoDTO.setTaskName(taskRequest.getTaskName());
            schedulerJobInfoDTO.setTaskStartDate(getLocalDateTime(taskRequest.getTaskStartDateTime()).toLocalDate());
            schedulerJobInfoDTO.setTaskStartTime(getLocalDateTime(taskRequest.getTaskStartDateTime()).toLocalTime());
            schedulerJobInfoDTO.setTaskEndDate(getLocalDateTime(taskRequest.getTaskEndDateTime()).toLocalDate());
            schedulerJobInfoDTO.setTaskEndTime(getLocalDateTime(taskRequest.getTaskEndDateTime()).toLocalTime());
            schedulerJobInfoDTO.setTaskURI(taskRequest.getTaskURI());
            schedulerJobInfoDTO.setTaskReqParam(taskRequest.getTaskReqParamMap());
            schedulerJobInfoDTO.setTaskFrequency(taskRequest.getTaskFrequency());
            schedulerJobInfoDTO.setCronExpression(taskRequest.getCronExpression());
            schedulerJobInfoDTO.setTaskType(taskRequest.getTaskType());
            schedulerService.scheduleNewJob(schedulerJobInfoDTO);

        } catch (GenericServiceException e) {
            log.error("GenericServiceException when saving scheduled tasks", e);
            replyBuilder.setResponseStatus(
                    Common.ResponseStatus.newBuilder()
                            .setCode(e.getCode())
                            .setMessage("GenericServiceException when saving scheduled tasks")
                            .setStatus(FAILED)
                            .build());
        } catch (Exception e) {
            log.error("Exception when saving scheduled tasks", e);
            replyBuilder.setResponseStatus(
                    Common.ResponseStatus.newBuilder()
                            .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                            .setMessage("Exception when saving scheduled tasks")
                            .setStatus(FAILED)
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
    public void deleteScheduleTask(TaskManager.ScheduleTaskDeleteRequest taskDeleteRequest, StreamObserver responseObserver) {
        TaskManagerReply.Builder replyBuilder = TaskManagerReply.newBuilder();
        try {
            schedulerService.deleteJob(taskDeleteRequest.getTaskName());
        } catch (GenericServiceException e) {
            log.error("GenericServiceException when deleting scheduled tasks", e);
            replyBuilder.setResponseStatus(
                    Common.ResponseStatus.newBuilder()
                            .setCode(e.getCode())
                            .setMessage("GenericServiceException when deleting scheduled tasks")
                            .setStatus(FAILED)
                            .build());
        } catch (Exception e) {
            log.error("Exception when saving scheduled tasks", e);
            replyBuilder.setResponseStatus(
                    Common.ResponseStatus.newBuilder()
                            .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                            .setMessage("Exception when deleting scheduled tasks")
                            .setStatus(FAILED)
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
    private LocalDateTime getLocalDateTime( com.google.protobuf.Timestamp timeStamp){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp.getSeconds(), timeStamp.getNanos()), ZoneId.of("UTC"));
    }

}
