/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      DischargePlanService.class,
    })
public class DischargePlanServiceTest {
  @Autowired DischargePlanService dischargePlanService;

  @MockBean LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;

  @MockBean
  LoadablePlanCommingleDetailsPortwiseRepository loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;
  @MockBean LoadablePlanService loadablePlanService;

  @Test
  void testBuildDischargeablePlanPortWiseDetails() {
    DischargePlanService spyService = spy(DischargePlanService.class);
    LoadablePattern loadablePattern = new LoadablePattern();
    LoadableStudy loadableStudy = new LoadableStudy();
    loadablePattern.setLoadableStudy(loadableStudy);
    loadablePattern.setId(1l);
    LoadabalePatternValidateRequest request = new LoadabalePatternValidateRequest();
    List<LoadableStudyPortRotation> entityList = new ArrayList<>();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setPortXId(1l);
    portRotation.setId(1l);
    entityList.add(portRotation);
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setId(1l).setCode("1").build();
    portDetailList.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder().addAllPorts(portDetailList).build();

    List<LoadablePatternCargoDetails> cargoDetailsList = new ArrayList<>();
    List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails> stowageDetailsList =
        new ArrayList<>();
    com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails stowageDetails =
        new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();
    stowageDetails.setId(1l);
    stowageDetailsList.add(stowageDetails);
    List<LoadablePlanStowageBallastDetails> ballastDetailsList = new ArrayList<>();
    List<com.cpdss.loadablestudy.domain.LoadablePlanBallastDetails> planBallastDetailsList =
        new ArrayList<>();
    List<LoadablePlanComminglePortwiseDetails> portwiseDetailsList = new ArrayList<>();

    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(portReply);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(entityList);
    when(loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(cargoDetailsList);
    when(loadablePlanService.addLoadablePatternsStowageDetails(anyList(), anyBoolean(), anyLong()))
        .thenReturn(stowageDetailsList);
    when(loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(ballastDetailsList);
    when(loadablePlanService.addLoadablePlanBallastDetails(anyList(), anyBoolean(), anyLong()))
        .thenReturn(planBallastDetailsList);
    when(loadablePlanCommingleDetailsPortwiseRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(portwiseDetailsList);
    when(loadablePlanService.addLoadablePlanCommingleDetails(anyList(), anyBoolean(), anyLong()))
        .thenReturn(stowageDetailsList);

    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(spyService, "loadablePlanService", loadablePlanService);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanStowageBallastDetailsRepository",
        loadablePlanStowageBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanCommingleDetailsPortwiseRepository",
        loadablePlanCommingleDetailsPortwiseRepository);

    spyService.buildDischargeablePlanPortWiseDetails(loadablePattern, request);
    assertEquals(
        1l,
        request
            .getLoadablePlanPortWiseDetails()
            .get(0)
            .getDepartureCondition()
            .getLoadablePlanCommingleDetails()
            .get(0)
            .getId());
  }
}
