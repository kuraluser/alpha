package com.cpdss.common.scheduler;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.TaskManager;
import com.cpdss.common.generated.TaskManagerServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.Utils;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to execute scheduled task
 */
@GrpcService
@Log4j2
public class ExecuteTask extends TaskManagerServiceGrpc.TaskManagerServiceImplBase {

    @Autowired
    private ExecuteTaskListener listener;

    /**
     * Method to execute a new job
     *
     * @param executeTaskRequest
     * @param responseObserver
     */
    @Override
    public void executeTask(TaskManager.ExecuteTaskRequest executeTaskRequest, StreamObserver responseObserver) {
        TaskManager.ExecuteTaskReply.Builder replyBuilder = TaskManager.ExecuteTaskReply.newBuilder();
        try {
            listener.listen(executeTaskRequest.getTaskName(), executeTaskRequest.getTaskReqParamMap());
        } catch (GenericServiceException e) {
            log.error(String.format("GenericServiceException when executing scheduled task %s", executeTaskRequest.getTaskName()), e);
            replyBuilder.setResponseStatus(
                    Common.ResponseStatus.newBuilder()
                            .setCode(e.getCode())
                            .setMessage("GenericServiceException when executing scheduled tasks")
                            .setStatus(Utils.STATUS.FAILED.name())
                            .build());
        } catch (Exception e) {
            log.error(String.format("Exception when executing scheduled task %s", executeTaskRequest.getTaskName()), e);
            replyBuilder.setResponseStatus(
                    Common.ResponseStatus.newBuilder()
                            .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                            .setMessage("Exception when executing scheduled tasks")
                            .setStatus(Utils.STATUS.FAILED.name())
                            .build());
        } finally {
            responseObserver.onNext(replyBuilder.build());
            responseObserver.onCompleted();
        }
    }


}
