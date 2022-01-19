/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.communication;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.TaskManager;
import com.cpdss.common.generated.TaskManagerServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.scheduler.ScheduledTaskProperties;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.SchedulerRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
public class FileSchedulerService {

  @Value("${cpdss.build.env}")
  private String environment;

  @Value("${ro.grpc.server.port}")
  private String port;

  @Autowired private ScheduledTaskRequest scheduledTaskRequest;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("taskManagerService")
  private TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerGrpcService;

  private static final String SERVICE_NAME = "gateway-service:";

  private static final String SUCCESS = "SUCCESS";

  @EventListener(ApplicationStartedEvent.class)
  public void scheduleTasks() throws GenericServiceException {
    List<SchedulerRequest> vesselList = getAllVessel();
    List<String> scheduledTasks = getScheduledTasks();
    for (SchedulerRequest vessel : vesselList) {
      createStageSaveJob(vessel, scheduledTasks);
      createFileCommunicationJob(scheduledTasks);
    }
  }

  private void createStageSaveJob(SchedulerRequest vessel, List<String> scheduledTasks)
      throws GenericServiceException {
    LocalDateTime dateTime = LocalDateTime.now();
    LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
    ScheduledTaskProperties properties = new ScheduledTaskProperties();
    properties.setTaskName(
        "FILE_SHARE_STAGE_DOWNLOAD" + "_" + environment + "_" + vessel.getVesselId());
    properties.setTaskFrequency(30);
    properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
    properties.setTaskStartDate(dateTime.toLocalDate());
    properties.setTaskStartTime(dateTime.toLocalTime());
    properties.setTaskEndDate(endDateTime.toLocalDate());
    properties.setTaskEndTime(endDateTime.toLocalTime());
    properties.setTaskURI(SERVICE_NAME + port);
    Map<String, String> requestParam = new HashMap<>();
    requestParam.put("env", environment);
    requestParam.put("ClientId", vessel.getVesselName());
    requestParam.put("ShipId", String.valueOf(vessel.getShipId()));
    properties.setTaskReqParam(requestParam);
    if (!CollectionUtils.isEmpty(scheduledTasks)
        && !scheduledTasks.contains(properties.getTaskName())) {
      scheduledTaskRequest.createScheduledTaskRequest(properties);
    }
  }

  private void createFileCommunicationJob(List<String> scheduledTasks)
      throws GenericServiceException {
    LocalDateTime dateTime = LocalDateTime.now();
    LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
    ScheduledTaskProperties properties = new ScheduledTaskProperties();
    properties.setTaskName("FILE_SHARE_DATA_UPDATE" + "_" + environment);
    properties.setTaskFrequency(30);
    properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
    properties.setTaskStartDate(dateTime.toLocalDate());
    properties.setTaskStartTime(dateTime.toLocalTime());
    properties.setTaskEndDate(endDateTime.toLocalDate());
    properties.setTaskEndTime(endDateTime.toLocalTime());
    properties.setTaskURI(SERVICE_NAME + port);
    Map<String, String> requestParam = new HashMap<>();
    requestParam.put("env", environment);
    properties.setTaskReqParam(requestParam);
    if (!CollectionUtils.isEmpty(scheduledTasks)
        && !scheduledTasks.contains(properties.getTaskName())) {
      scheduledTaskRequest.createScheduledTaskRequest(properties);
    }
  }

  private List<SchedulerRequest> getAllVessel() {
    List<SchedulerRequest> requestList = null;
    VesselInfo.VesselRequest.Builder request = VesselInfo.VesselRequest.newBuilder();
    request.setCompanyId(1L);
    try {
      VesselInfo.VesselReply VesselReply =
          this.vesselInfoGrpcService.getAllVesselsByCompany(request.build());
      if (!SUCCESS.equalsIgnoreCase(VesselReply.getResponseStatus().getStatus())) {

        throw new GenericServiceException(
            "Error in calling vessel service",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      List<VesselInfo.VesselDetail> vesselsList = VesselReply.getVesselsList();
      requestList =
          vesselsList.stream()
              .map(
                  vessel ->
                      new SchedulerRequest(vessel.getName(), vessel.getId(), vessel.getImoNumber()))
              .collect(Collectors.toList());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when getAllVessel", e);
    }
    return requestList;
  }

  private List<String> getScheduledTasks() {
    List<String> scheduledTasks = null;
    TaskManager.SchedulededTaskRequest.Builder request =
        TaskManager.SchedulededTaskRequest.newBuilder();
    request.setJobGroup("CPDSS");
    try {
      TaskManager.SchedulededTaskReply taskReply =
          this.taskManagerGrpcService.getScheduledTasks(request.build());
      if (!SUCCESS.equalsIgnoreCase(taskReply.getResponseStatus().getStatus())) {

        throw new GenericServiceException(
            "Error in calling task Manager service",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      scheduledTasks = taskReply.getTaskNameList();

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when generating pattern", e);
    }
    return scheduledTasks;
  }
}
