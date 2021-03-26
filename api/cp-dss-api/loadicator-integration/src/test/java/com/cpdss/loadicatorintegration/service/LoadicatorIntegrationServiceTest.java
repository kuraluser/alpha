/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest;
import com.cpdss.common.generated.Loadicator.BallastInfo;
import com.cpdss.common.generated.Loadicator.CargoInfo;
import com.cpdss.common.generated.Loadicator.LoadicatorReply;
import com.cpdss.common.generated.Loadicator.LoadicatorRequest;
import com.cpdss.common.generated.Loadicator.OtherTankInfo;
import com.cpdss.common.generated.Loadicator.StowageDetails;
import com.cpdss.loadicatorintegration.domain.StowagePlanDetail;
import com.cpdss.loadicatorintegration.entity.IntactStability;
import com.cpdss.loadicatorintegration.entity.LoadicatorStrength;
import com.cpdss.loadicatorintegration.entity.LoadicatorTrim;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import com.cpdss.loadicatorintegration.repository.LoadicatorIntactStabilityRepository;
import com.cpdss.loadicatorintegration.repository.LoadicatorStrengthRepository;
import com.cpdss.loadicatorintegration.repository.LoadicatorTrimRepository;
import com.cpdss.loadicatorintegration.repository.StowagePlanRepository;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** @Author jerin.g */
@SpringJUnitConfig(classes = {LoadicatorService.class})
public class LoadicatorIntegrationServiceTest {

  @Autowired private LoadicatorService loadicatorService;
  @MockBean private StowagePlanRepository stowagePlanRepository;
  @MockBean private LoadicatorTrimRepository loadicatorTrimRepository;
  @MockBean private LoadicatorStrengthRepository loadicatorStrengthRepository;
  @MockBean private LoadicatorIntactStabilityRepository loadicatorIntactStabilityRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String DUMMY_STRING = "DUMMY_STRING";
  private static final Long DUMMY_LONG = 1L;

  @Test
  void testSaveLoadicatorInfo() {
    List<StowagePlan> stowagePlanList = new ArrayList<>();
    StowagePlan stowagePlan = new StowagePlan();
    stowagePlan.setStowageId(DUMMY_LONG);
    stowagePlan.setBookingListId(DUMMY_LONG);
    stowagePlan.setProcessId(DUMMY_STRING);
    stowagePlanList.add(stowagePlan);
    when(this.stowagePlanRepository.saveAll(any())).thenReturn(stowagePlanList);

    LoadicatorService spyService = Mockito.spy(this.loadicatorService);
    when(this.loadicatorIntactStabilityRepository.findByStowagePlanIdIn(any()))
        .thenReturn(createIntactStabilityList());
    when(this.stowagePlanRepository.findPortForStability(any()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorStrengthRepository.findByStowagePlanIdIn(any()))
        .thenReturn(createLoadicatorStrengthDetails());
    when(this.stowagePlanRepository.findPortForStrength(any()))
        .thenReturn(createStowagePlanDetails());
    when(this.loadicatorTrimRepository.findByStowagePlanIdIn(any()))
        .thenReturn(createLoadicatorTrimDetails());
    when(this.stowagePlanRepository.findPortForTrim(any())).thenReturn(createStowagePlanDetails());

    Mockito.doReturn(
            LoadicatorDataReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build())
        .when(spyService)
        .getLoadicatorDatas(any(LoadicatorDataRequest.class));

    StreamRecorder<LoadicatorReply> responseObserver = StreamRecorder.create();
    spyService.saveLoadicatorInfo(createLoadicatorRequest(), responseObserver);
    List<LoadicatorReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  /** @return List<LoadicatorTrim> */
  private List<LoadicatorTrim> createLoadicatorTrimDetails() {
    List<LoadicatorTrim> loadicatorTrims = new ArrayList<LoadicatorTrim>();
    LoadicatorTrim loadicatorTrim = new LoadicatorTrim();
    loadicatorTrim.setId(DUMMY_LONG);
    loadicatorTrims.add(loadicatorTrim);
    return loadicatorTrims;
  }

  /** @return List<LoadicatorStrength> */
  private List<LoadicatorStrength> createLoadicatorStrengthDetails() {
    List<LoadicatorStrength> loadicatorStrengths = new ArrayList<LoadicatorStrength>();
    LoadicatorStrength loadicatorStrength = new LoadicatorStrength();
    loadicatorStrength.setId(DUMMY_LONG);
    loadicatorStrengths.add(loadicatorStrength);
    return loadicatorStrengths;
  }

  /** @return StowagePlanDetail */
  private StowagePlanDetail createStowagePlanDetails() {
    StowagePlanDetail stowagePlanDetail = new StowagePlanDetail();
    stowagePlanDetail.setPortId(DUMMY_LONG);
    stowagePlanDetail.setSynopticalId(DUMMY_LONG);
    return stowagePlanDetail;
  }

  /** @return List<IntactStability> */
  private List<IntactStability> createIntactStabilityList() {
    List<IntactStability> intactStabilities = new ArrayList<IntactStability>();
    IntactStability intactStability = new IntactStability();
    intactStability.setId(DUMMY_LONG);
    intactStabilities.add(intactStability);
    return intactStabilities;
  }

  /** @return LoadicatorRequest */
  private LoadicatorRequest createLoadicatorRequest() {
    LoadicatorRequest.Builder loadicatorRequestBuilder = LoadicatorRequest.newBuilder();
    loadicatorRequestBuilder.addStowagePlanDetails(buildStowagePlanDetails());
    return loadicatorRequestBuilder.build();
  }

  /** @return com.cpdss.common.generated.Loadicator.StowagePlan */
  private com.cpdss.common.generated.Loadicator.StowagePlan buildStowagePlanDetails() {
    com.cpdss.common.generated.Loadicator.StowagePlan.Builder stowagePlanBuilder =
        com.cpdss.common.generated.Loadicator.StowagePlan.newBuilder();
    stowagePlanBuilder.addStowageDetails(buildStowageDetails());
    stowagePlanBuilder.addBallastInfo(buildBallastInfo());
    stowagePlanBuilder.addOtherTankInfo(buildOtherTankInfo());
    stowagePlanBuilder.addCargoInfo(buildCargoInfo());
    return stowagePlanBuilder.build();
  }

  /** @return CargoInfo */
  private CargoInfo buildCargoInfo() {
    CargoInfo.Builder builder = CargoInfo.newBuilder();
    return builder.build();
  }

  /** @return OtherTankInfo */
  private OtherTankInfo buildOtherTankInfo() {
    OtherTankInfo.Builder builder = OtherTankInfo.newBuilder();
    return builder.build();
  }

  /** @return BallastInfo */
  private BallastInfo buildBallastInfo() {
    BallastInfo.Builder builder = BallastInfo.newBuilder();
    return builder.build();
  }

  /** @return StowageDetails */
  private StowageDetails buildStowageDetails() {
    StowageDetails.Builder builder = StowageDetails.newBuilder();
    return builder.build();
  }
}
