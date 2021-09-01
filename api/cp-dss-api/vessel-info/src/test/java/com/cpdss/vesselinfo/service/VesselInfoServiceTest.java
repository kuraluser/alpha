/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VesselInfoServiceTest {

  @InjectMocks private VesselInfoService vesselInfoService;

  @Mock private VesselRepository vesselRepository;
  @Mock private VesselTankRepository vesselTankRepository;
  @Mock private HydrostaticTableRepository hydrostaticTableRepository;
  @Mock private VesselDraftConditionRepository vesselDraftConditionRepository;
  @Mock private VesselTankTcgRepository vesselTankTcgRepository;
  @Mock private CalculationSheetRepository calculationSheetRepository;
  @Mock private CalculationSheetTankgroupRepository calculationSheetTankgroupRepository;
  @Mock private MinMaxValuesForBmsfRepository minMaxValuesForBmsfRepository;
  @Mock private StationValuesRepository stationValuesRepository;
  @Mock private InnerBulkHeadValuesRepository innerBulkHeadValuesRepository;
  @Mock private UllageTableDataRepository ullageTableDataRepository;
  @Mock private VesselFlowRateRepository vesselFlowRateRepository;
  @Mock private VesselPumpService vesselPumpService;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final Long TEST_ID = 1L;

  @Test
  void testGetVesselTanks() {
    Vessel vessel = new Vessel();
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(vessel);
    when(this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
            any(Vessel.class), anyList(), anyBoolean()))
        .thenReturn(this.prepareVesselTankEntities());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanksInvalidVessel() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanksRunTimeException() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<VesselTank> prepareVesselTankEntities() {
    List<VesselTank> tanks = new ArrayList<>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              VesselTank tank = new VesselTank();
              tank.setId(Long.valueOf(i));
              tank.setShortName("tank-" + i);
              tank.setFrameNumberFrom("frm-from" + i);
              tank.setFrameNumberTo("frame-to" + i);
              TankCategory tankCategory = new TankCategory();
              tankCategory.setId(Long.valueOf(i));
              tankCategory.setName("tankCategory-" + i);
              tank.setTankCategory(tankCategory);
              tank.setTankName("tank-name-" + i);
              tank.setHeightFrom(String.valueOf(i));
              tank.setHeightTo(String.valueOf(i));
              tank.setFillCapacityCubm(BigDecimal.TEN);
              tank.setFillCapacityCubm(BigDecimal.ONE);
              tank.setTankGroup(i);
              tank.setTankOrder(i);
              tank.setIsSlopTank(false);
              tanks.add(tank);
            });
    return tanks;
  }

  @Test
  void testGetVesselDetailsForAlgo() throws GenericServiceException {
    Vessel vessel = new Vessel();
    this.setUllageTrimCorrections(vessel);
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(vessel);
    when(this.vesselDraftConditionRepository.findByVesselAndIsActive(
            any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(new VesselDraftCondition()));
    when(this.vesselTankRepository.findByVesselAndIsActive(any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(new VesselTank()));
    when(this.hydrostaticTableRepository.findByVesselAndIsActive(any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(new HydrostaticTable()));
    when(this.vesselTankTcgRepository.findByVesselIdAndIsActive(any(), anyBoolean()))
        .thenReturn(Arrays.asList(new VesselTankTcg()));
    when(this.calculationSheetRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new CalculationSheet()));
    when(this.calculationSheetTankgroupRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new CalculationSheetTankgroup()));
    when(this.minMaxValuesForBmsfRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new MinMaxValuesForBmsf()));
    when(this.stationValuesRepository.findByVesselId(any()))
        .thenReturn(Arrays.asList(new StationValues()));
    when(this.innerBulkHeadValuesRepository.findByVesselId((any())))
        .thenReturn(Arrays.asList(new InnerBulkHeadValues()));
    when(this.ullageTableDataRepository.findByVesselOrderByVesselTankAscUllageDepthAsc(
            (any(Vessel.class))))
        .thenReturn(Arrays.asList(new UllageTableData()));
    when(this.vesselFlowRateRepository.findByVessel((any(Vessel.class))))
        .thenReturn(Arrays.asList(new VesselFlowRate()));
    VesselInfo.VesselPumpsResponse.Builder vesselPumpResponseBuilder =
        VesselInfo.VesselPumpsResponse.newBuilder();
    lenient()
        .when(
            this.vesselPumpService.getVesselPumpsAndTypes(
                vesselPumpResponseBuilder, vessel.getId()))
        .thenReturn(vesselPumpResponseBuilder.build());

    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private void setUllageTrimCorrections(Vessel vessel) {
    vessel.setUllageTrimCorrections(new HashSet<>());
    UllageTrimCorrection correction = new UllageTrimCorrection();
    correction.setId(TEST_ID);
    correction.setTrim0(BigDecimal.ONE);
    correction.setTrim1(BigDecimal.ONE);
    correction.setTrim2(BigDecimal.ONE);
    correction.setTrim3(BigDecimal.ONE);
    correction.setTrim4(BigDecimal.ONE);
    correction.setTrim5(BigDecimal.ONE);
    correction.setTrim6(BigDecimal.ONE);
    correction.setTrimM1(BigDecimal.ONE);
    correction.setTrimM2(BigDecimal.ONE);
    correction.setTrimM3(BigDecimal.ONE);
    correction.setTrimM4(BigDecimal.ONE);
    correction.setTrimM5(BigDecimal.ONE);
    correction.setUllageDepth(BigDecimal.ONE);
    correction.setIsActive(true);
    vessel.getUllageTrimCorrections().add(correction);
  }

  @Test
  void testGetVesselDetailsForAlgoInvalidVessel() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsForAlgoRunTimeException() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }
}
