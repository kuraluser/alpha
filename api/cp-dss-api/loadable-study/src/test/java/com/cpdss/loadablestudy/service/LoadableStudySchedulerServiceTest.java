/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.mockito.Mockito.*;

import com.cpdss.common.generated.*;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadableStudySchedulerService.class,
    })
public class LoadableStudySchedulerServiceTest {
  @Autowired LoadableStudySchedulerService loadableStudySchedulerService;
  @MockBean ScheduledTaskRequest scheduledTaskRequest;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean private TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerGrpcService;
  private static final String SUCCESS = "SUCCESS";

  //    @Test
  //    void testInit() throws GenericServiceException {
  //        LoadableStudySchedulerService spyService = spy(LoadableStudySchedulerService.class);
  //        List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
  //        VesselInfo.VesselDetail vesselDetail =
  //
  // VesselInfo.VesselDetail.newBuilder().setName("1").setId(1l).setImoNumber("1").build();
  //        vesselDetailList.add(vesselDetail);
  //        VesselInfo.VesselReply vesselReply =
  //                VesselInfo.VesselReply.newBuilder()
  //
  // .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //                        .addAllVessels(vesselDetailList)
  //                        .build();
  //        List<String> strings = new ArrayList<>();
  //        strings.add("1");
  //        TaskManager.SchedulededTaskReply taskReply =
  //                TaskManager.SchedulededTaskReply.newBuilder()
  //
  // .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //                        .addAllTaskName(strings)
  //                        .build();
  //        doCallRealMethod().when(spyService).init();
  //        when(this.taskManagerGrpcService.getScheduledTasks(
  //                any(TaskManager.SchedulededTaskRequest.class)))
  //                .thenReturn(taskReply);
  //
  // when(this.vesselInfoGrpcService.getAllVesselsByCompany(any(VesselInfo.VesselRequest.class)))
  //                .thenReturn(vesselReply);
  //        doNothing()
  //                .when(scheduledTaskRequest)
  //                .createScheduledTaskRequest(any(ScheduledTaskProperties.class));
  //        ReflectionTestUtils.setField(spyService, "taskManagerGrpcService",
  // taskManagerGrpcService);
  //        ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService",
  // vesselInfoGrpcService);
  //        ReflectionTestUtils.setField(spyService, "scheduledTaskRequest", scheduledTaskRequest);
  //        ReflectionTestUtils.setField(spyService, "environment", "environment");
  //        ReflectionTestUtils.setField(spyService, "port", "port");
  //
  //        spyService.init();
  //    }
}
