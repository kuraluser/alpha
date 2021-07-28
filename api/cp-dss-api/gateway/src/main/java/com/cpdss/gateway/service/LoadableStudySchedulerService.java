/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.generated.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadableStudySchedulerService {
  /* @Autowired(required = false)
  ScheduledTaskRequest scheduledTaskRequest;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("taskManagerService")
  private TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerGrpcService;

  @Value("${cpdss.build.env}")
  private String environment;

  @Value("${ro.server.port}")
  private String port;

  private static final String SUCCESS = "SUCCESS";

  //@PostConstruct
  public void init() {
      List<SchedulerRequest> vesselList = getAllVessel();
      List<String> scheduledTasks = getScheduledTasks();
      if (vesselList != null) {
          vesselList.forEach(
                  vesssel -> {
                      new Thread(
                              () -> {
                                  try {
                                      Thread.sleep(15 * 1000);
                                      log.info("Executing Tasks Creation");
                                      LocalDateTime dateTime = LocalDateTime.now();
                                      LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(2));
                                      ScheduledTaskProperties properties = new ScheduledTaskProperties();
                                      properties.setTaskName(
                                              "DOWNLOAD_RESULT_" + environment + "_" + vesssel.getVesselId());
                                      properties.setTaskFrequency(30);
                                      properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                                      properties.setTaskStartDate(dateTime.toLocalDate());
                                      properties.setTaskStartTime(dateTime.toLocalTime());
                                      properties.setTaskEndDate(endDateTime.toLocalDate());
                                      properties.setTaskEndTime(endDateTime.toLocalTime());
                                      properties.setTaskURI("localhost:" + port);
                                      Map<String, String> requestParam = new HashMap<>();
                                      requestParam.put("env", environment);
                                      requestParam.put("ClientId", vesssel.getVesselName());
                                      requestParam.put("ShipId", String.valueOf(vesssel.getShipId()));
                                      properties.setTaskReqParam(requestParam);
                                      if (scheduledTasks != null
                                              && !scheduledTasks.contains(properties.getTaskName()))
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
                                      log.info("Executing Status Tasks Creation");
                                      LocalDateTime dateTime = LocalDateTime.now();
                                      LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(4));
                                      ScheduledTaskProperties properties = new ScheduledTaskProperties();
                                      properties.setTaskName(
                                              "STATUS_CHECK_" + environment + "_" + vesssel.getVesselId());
                                      properties.setTaskFrequency(60);
                                      properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                                      properties.setTaskStartDate(dateTime.toLocalDate());
                                      properties.setTaskStartTime(dateTime.toLocalTime());
                                      properties.setTaskEndDate(endDateTime.toLocalDate());
                                      properties.setTaskEndTime(endDateTime.toLocalTime());
                                      properties.setTaskURI("localhost:" + port);
                                      Map<String, String> requestParam = new HashMap<>();

                                      requestParam.put("ClientId", vesssel.getVesselName());
                                      requestParam.put("ShipId", String.valueOf(vesssel.getShipId()));
                                      properties.setTaskReqParam(requestParam);
                                      if (scheduledTasks != null
                                              && !scheduledTasks.contains(properties.getTaskName()))
                                          scheduledTaskRequest.createScheduledTaskRequest(properties);

                                  } catch (InterruptedException | GenericServiceException e) {
                                      e.printStackTrace();
                                  }
                              })
                              .start();
                  });
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
  }*/
}
