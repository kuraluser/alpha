/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.*;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import com.cpdss.loadingplan.service.LoadingPlanSchedulerService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingPlanSchedulerService.class})
public class LoadingPlanSchedulerServiceTest {

  @Autowired LoadingPlanSchedulerService loadingPlanSchedulerService;

  @MockBean ScheduledTaskRequest scheduledTaskRequest;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean private TaskManagerServiceGrpc.TaskManagerServiceBlockingStub taskManagerGrpcService;

  private static final String SUCCESS = "SUCCESS";

  //    @Test
  //      void testInit() {
  //
  // Mockito.when(this.vesselInfoGrpcService.getAllVesselsByCompany(Mockito.any())).thenReturn(getVR());
  //
  // Mockito.when(this.taskManagerGrpcService.getScheduledTasks(Mockito.any())).thenReturn(getSTR());
  //
  // ReflectionTestUtils.setField(loadingPlanSchedulerService,"vesselInfoGrpcService",this.vesselInfoGrpcService);
  //
  // ReflectionTestUtils.setField(loadingPlanSchedulerService,"taskManagerGrpcService",this.taskManagerGrpcService);
  //        this.loadingPlanSchedulerService.init();
  //    }

  private VesselInfo.VesselReply getVR() {
    List<VesselInfo.VesselDetail> list = new ArrayList<>();
    VesselInfo.VesselDetail detail =
        VesselInfo.VesselDetail.newBuilder().setId(1L).setName("1").setImoNumber("1").build();
    list.add(detail);
    VesselInfo.VesselReply reply =
        VesselInfo.VesselReply.newBuilder()
            .addAllVessels(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private TaskManager.SchedulededTaskReply getSTR() {
    List<String> list = new ArrayList<>();
    list.add("1");
    TaskManager.SchedulededTaskReply reply =
        TaskManager.SchedulededTaskReply.newBuilder()
            .addAllTaskName(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }
}
