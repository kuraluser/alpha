/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.scheduler.ScheduledTaskProperties;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.SchedulerRequest;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadableStudySchedulerService {
  @Autowired ScheduledTaskRequest scheduledTaskRequest;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("taskManagerService")
  private TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerGrpcService;

  @Value("${cpdss.build.env}")
  private String environment;

  @Value("${ro.grpc.server.port}")
  private String port;

  private static final String SUCCESS = "SUCCESS";

  @PostConstruct
  public void init() {
    List<SchedulerRequest> vesselList = getAllVessel();
    List<String> scheduledTasks = getScheduledTasks();
    for (SchedulerRequest vessel : vesselList) {
      final String taskName = "_" + environment + "_" + vessel.getVesselId();
      new Thread(
              () -> {
                try {
                  Thread.sleep(15 * 1000);
                  System.out.println("EXECUTING");
                  LocalDateTime dateTime = LocalDateTime.now();
                  LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                  ScheduledTaskProperties properties = new ScheduledTaskProperties();
                  properties.setTaskName("LOADABLE_STUDY_DOWNLOAD_RESULT" + taskName);
                  properties.setTaskFrequency(30);
                  properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                  properties.setTaskStartDate(dateTime.toLocalDate());
                  properties.setTaskStartTime(dateTime.toLocalTime());
                  properties.setTaskEndDate(endDateTime.toLocalDate());
                  properties.setTaskEndTime(endDateTime.toLocalTime());
                  properties.setTaskURI("loadable-study-service:" + port);
                  Map<String, String> requestParam = new HashMap<>();
                  requestParam.put("env", environment);
                  requestParam.put("ClientId", vessel.getVesselName());
                  requestParam.put("ShipId", String.valueOf(vessel.getShipId()));
                  properties.setTaskReqParam(requestParam);
                  if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                    scheduledTaskRequest.createScheduledTaskRequest(properties);

                } catch (InterruptedException | GenericServiceException e) {
                  e.printStackTrace();
                }
              })
          .start();
      new Thread(
              () -> {
                try {
                  Thread.sleep(15 * 1000);
                  System.out.println("EXECUTING STATUS");
                  LocalDateTime dateTime = LocalDateTime.now();
                  LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                  ScheduledTaskProperties properties = new ScheduledTaskProperties();
                  properties.setTaskName("STATUS_CHECK" + taskName);
                  properties.setTaskFrequency(60);
                  properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                  properties.setTaskStartDate(dateTime.toLocalDate());
                  properties.setTaskStartTime(dateTime.toLocalTime());
                  properties.setTaskEndDate(endDateTime.toLocalDate());
                  properties.setTaskEndTime(endDateTime.toLocalTime());
                  properties.setTaskURI("loadable-study-service:" + port);
                  Map<String, String> requestParam = new HashMap<>();

                  requestParam.put("ClientId", vessel.getVesselName());
                  requestParam.put("ShipId", String.valueOf(vessel.getShipId()));
                  requestParam.put("env", environment);
                  properties.setTaskReqParam(requestParam);
                  if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                    scheduledTaskRequest.createScheduledTaskRequest(properties);

                } catch (InterruptedException | GenericServiceException e) {
                  e.printStackTrace();
                }
              })
          .start();
      new Thread(
              () -> {
                try {
                  Thread.sleep(15 * 1000);
                  System.out.println("EXECUTING");
                  LocalDateTime dateTime = LocalDateTime.now();
                  LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                  ScheduledTaskProperties properties = new ScheduledTaskProperties();
                  properties.setTaskName("DISCHARGE_STUDY_DOWNLOAD_RESULT" + taskName);
                  properties.setTaskFrequency(30);
                  properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                  properties.setTaskStartDate(dateTime.toLocalDate());
                  properties.setTaskStartTime(dateTime.toLocalTime());
                  properties.setTaskEndDate(endDateTime.toLocalDate());
                  properties.setTaskEndTime(endDateTime.toLocalTime());
                  properties.setTaskURI("loadable-study-service:" + port);
                  Map<String, String> requestParam = new HashMap<>();
                  requestParam.put("env", environment);
                  requestParam.put("ClientId", vessel.getVesselName());
                  requestParam.put("ShipId", String.valueOf(vessel.getShipId()));
                  properties.setTaskReqParam(requestParam);
                  if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                    scheduledTaskRequest.createScheduledTaskRequest(properties);

                } catch (InterruptedException | GenericServiceException e) {
                  e.printStackTrace();
                }
              })
          .start();
      new Thread(
              () -> {
                try {
                  Thread.sleep(15 * 1000);
                  System.out.println("EXECUTING");
                  LocalDateTime dateTime = LocalDateTime.now();
                  LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                  ScheduledTaskProperties properties = new ScheduledTaskProperties();
                  properties.setTaskName(
                      LoadableStudiesConstants.FILE_SHARE_STAGE_DOWNLOAD_TASK + taskName);
                  properties.setTaskFrequency(30);
                  properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                  properties.setTaskStartDate(dateTime.toLocalDate());
                  properties.setTaskStartTime(dateTime.toLocalTime());
                  properties.setTaskEndDate(endDateTime.toLocalDate());
                  properties.setTaskEndTime(endDateTime.toLocalTime());
                  properties.setTaskURI("loadable-study-service:" + port);
                  Map<String, String> requestParam = new HashMap<>();
                  requestParam.put("env", environment);
                  requestParam.put("ClientId", vessel.getVesselName());
                  requestParam.put("ShipId", String.valueOf(vessel.getShipId()));
                  properties.setTaskReqParam(requestParam);
                  if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                    scheduledTaskRequest.createScheduledTaskRequest(properties);

                } catch (InterruptedException | GenericServiceException e) {
                  e.printStackTrace();
                }
              })
          .start();
    }
    new Thread(
            () -> {
              try {
                Thread.sleep(15 * 1000);
                System.out.println("EXECUTING");
                LocalDateTime dateTime = LocalDateTime.now();
                LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                ScheduledTaskProperties properties = new ScheduledTaskProperties();
                properties.setTaskName("LOADABLE_STUDY_DATA_UPDATE_" + environment);
                properties.setTaskFrequency(30);
                properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                properties.setTaskStartDate(dateTime.toLocalDate());
                properties.setTaskStartTime(dateTime.toLocalTime());
                properties.setTaskEndDate(endDateTime.toLocalDate());
                properties.setTaskEndTime(endDateTime.toLocalTime());
                properties.setTaskURI("loadable-study-service:" + port);
                Map<String, String> requestParam = new HashMap<>();
                requestParam.put("env", environment);
                properties.setTaskReqParam(requestParam);
                if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                  scheduledTaskRequest.createScheduledTaskRequest(properties);

              } catch (InterruptedException | GenericServiceException e) {
                e.printStackTrace();
              }
            })
        .start();
    new Thread(
            () -> {
              try {
                Thread.sleep(15 * 1000);
                System.out.println("EXECUTING");
                LocalDateTime dateTime = LocalDateTime.now();
                LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                ScheduledTaskProperties properties = new ScheduledTaskProperties();
                properties.setTaskName("STOWAGE_DATA_UPDATE_" + environment);
                properties.setTaskFrequency(30);
                properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                properties.setTaskStartDate(dateTime.toLocalDate());
                properties.setTaskStartTime(dateTime.toLocalTime());
                properties.setTaskEndDate(endDateTime.toLocalDate());
                properties.setTaskEndTime(endDateTime.toLocalTime());
                properties.setTaskURI("loadable-study-service:" + port);
                Map<String, String> requestParam = new HashMap<>();
                requestParam.put("env", environment);
                properties.setTaskReqParam(requestParam);
                if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                  scheduledTaskRequest.createScheduledTaskRequest(properties);

              } catch (InterruptedException | GenericServiceException e) {
                e.printStackTrace();
              }
            })
        .start();
    new Thread(
            () -> {
              try {
                Thread.sleep(15 * 1000);
                System.out.println("EXECUTING");
                LocalDateTime dateTime = LocalDateTime.now();
                LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                ScheduledTaskProperties properties = new ScheduledTaskProperties();
                properties.setTaskName("DISCHARGE_STUDY_DATA_UPDATE_" + environment);
                properties.setTaskFrequency(30);
                properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                properties.setTaskStartDate(dateTime.toLocalDate());
                properties.setTaskStartTime(dateTime.toLocalTime());
                properties.setTaskEndDate(endDateTime.toLocalDate());
                properties.setTaskEndTime(endDateTime.toLocalTime());
                properties.setTaskURI("loadable-study-service:" + port);
                Map<String, String> requestParam = new HashMap<>();
                requestParam.put("env", environment);
                properties.setTaskReqParam(requestParam);
                if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                  scheduledTaskRequest.createScheduledTaskRequest(properties);

              } catch (InterruptedException | GenericServiceException e) {
                e.printStackTrace();
              }
            })
        .start();
    new Thread(
            () -> {
              try {
                Thread.sleep(15 * 1000);
                System.out.println("EXECUTING");
                LocalDateTime dateTime = LocalDateTime.now();
                LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(100));
                ScheduledTaskProperties properties = new ScheduledTaskProperties();
                properties.setTaskName(
                    LoadableStudiesConstants.FILE_SHARE_DATA_UPDATE_TASK + "_" + environment);
                properties.setTaskFrequency(30);
                properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                properties.setTaskStartDate(dateTime.toLocalDate());
                properties.setTaskStartTime(dateTime.toLocalTime());
                properties.setTaskEndDate(endDateTime.toLocalDate());
                properties.setTaskEndTime(endDateTime.toLocalTime());
                properties.setTaskURI("loadable-study-service:" + port);
                Map<String, String> requestParam = new HashMap<>();
                requestParam.put("env", environment);
                properties.setTaskReqParam(requestParam);
                if (scheduledTasks != null && !scheduledTasks.contains(properties.getTaskName()))
                  scheduledTaskRequest.createScheduledTaskRequest(properties);

              } catch (InterruptedException | GenericServiceException e) {
                e.printStackTrace();
              }
            })
        .start();
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
      log.error("GenericServiceException when generating pattern", e);
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
