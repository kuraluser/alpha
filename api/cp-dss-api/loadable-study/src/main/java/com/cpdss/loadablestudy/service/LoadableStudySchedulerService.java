/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.scheduler.ScheduledTaskProperties;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.SchedulerRequest;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
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
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadableStudySchedulerService {
  @Autowired ScheduledTaskRequest scheduledTaskRequest;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Value("${cpdss.build.env}")
  private String environment;

  public void init() {
    List<SchedulerRequest> vesselList = getAllVessel();
    vesselList.forEach(
        vesssel -> {
          new Thread(
                  () -> {
                    try {
                      Thread.sleep(15 * 1000);
                      System.out.println("EXECUTING");
                      LocalDateTime dateTime = LocalDateTime.now();
                      LocalDateTime endDateTime = dateTime.plus(Duration.ofDays(2));
                      ScheduledTaskProperties properties = new ScheduledTaskProperties();
                      properties.setTaskName("DOWNLOAD_RESULT" + vesssel.getVesselId());
                      properties.setTaskFrequency(1);
                      properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
                      properties.setTaskStartDate(dateTime.toLocalDate());
                      properties.setTaskStartTime(dateTime.toLocalTime());
                      properties.setTaskEndDate(endDateTime.toLocalDate());
                      properties.setTaskEndTime(endDateTime.toLocalTime());
                      properties.setTaskURI("localhost:9090");
                      Map<String, String> requestParam = new HashMap<>();
                      if (environment.equals("shore"))
                        requestParam.put("messageType", String.valueOf(MessageTypes.LOADABLESTUDY));
                      else if (environment.equals("ship"))
                        requestParam.put("messageType", String.valueOf(MessageTypes.ALGORESULT));
                      requestParam.put("ClientId", vesssel.getVesselName());
                      requestParam.put("ShipId", String.valueOf(vesssel.getShipId()));
                      properties.setTaskReqParam(requestParam);
                      scheduledTaskRequest.createScheduledTaskRequest(properties);

                    } catch (InterruptedException | GenericServiceException e) {
                      e.printStackTrace();
                    }
                  })
              .start();
        });
  }

  private List<SchedulerRequest> getAllVessel() {
    List<SchedulerRequest> requestList = null;
    VesselInfo.VesselRequest.Builder request = VesselInfo.VesselRequest.newBuilder();
    request.setCompanyId(1L);
    try {
      VesselInfo.VesselReply VesselReply =
          this.vesselInfoGrpcService.getAllVesselsByCompany(request.build());
      if (!LoadableStudiesConstants.SUCCESS.equalsIgnoreCase(
          VesselReply.getResponseStatus().getStatus())) {

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
      e.printStackTrace();
    }
    return requestList;
  }
}
