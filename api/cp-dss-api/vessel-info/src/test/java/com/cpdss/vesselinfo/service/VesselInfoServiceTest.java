/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.vesselinfo.entity.BendingMoment;
import com.cpdss.vesselinfo.entity.CalculationSheet;
import com.cpdss.vesselinfo.entity.CalculationSheetTankgroup;
import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.InnerBulkHeadValues;
import com.cpdss.vesselinfo.entity.MinMaxValuesForBmsf;
import com.cpdss.vesselinfo.entity.ShearingForce;
import com.cpdss.vesselinfo.entity.StationValues;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.UllageTableData;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselDraftCondition;
import com.cpdss.vesselinfo.entity.VesselTank;
import com.cpdss.vesselinfo.entity.VesselTankTcg;
import com.cpdss.vesselinfo.repository.BendingMomentRepository;
import com.cpdss.vesselinfo.repository.CalculationSheetRepository;
import com.cpdss.vesselinfo.repository.CalculationSheetTankgroupRepository;
import com.cpdss.vesselinfo.repository.HydrostaticTableRepository;
import com.cpdss.vesselinfo.repository.InnerBulkHeadValuesRepository;
import com.cpdss.vesselinfo.repository.MinMaxValuesForBmsfRepository;
import com.cpdss.vesselinfo.repository.ShearingForceRepository;
import com.cpdss.vesselinfo.repository.StationValuesRepository;
import com.cpdss.vesselinfo.repository.TankCategoryRepository;
import com.cpdss.vesselinfo.repository.UllageTableDataRepository;
import com.cpdss.vesselinfo.repository.VesselChartererMappingRepository;
import com.cpdss.vesselinfo.repository.VesselDraftConditionRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import com.cpdss.vesselinfo.repository.VesselTankRepository;
import com.cpdss.vesselinfo.repository.VesselTankTcgRepository;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {VesselInfoService.class})
class VesselInfoServiceTest {

  @Autowired private VesselInfoService vesselInfoService;

  @MockBean private VesselRepository vesselRepository;
  @MockBean private VesselChartererMappingRepository chartererMappingRepository;
  @MockBean private TankCategoryRepository tankCategoryRepository;
  @MockBean private VesselTankRepository vesselTankRepository;
  @MockBean private HydrostaticTableRepository hydrostaticTableRepository;
  @MockBean private VesselDraftConditionRepository vesselDraftConditionRepository;
  @MockBean private VesselTankTcgRepository vesselTankTcgRepository;
  @MockBean private BendingMomentRepository bendingMomentRepository;
  @MockBean private ShearingForceRepository shearingForceRepository;
  @MockBean private CalculationSheetRepository calculationSheetRepository;
  @MockBean private CalculationSheetTankgroupRepository calculationSheetTankgroupRepository;
  @MockBean private MinMaxValuesForBmsfRepository minMaxValuesForBmsfRepository;
  @MockBean private StationValuesRepository stationValuesRepository;
  @MockBean private InnerBulkHeadValuesRepository innerBulkHeadValuesRepository;
  @MockBean private UllageTableDataRepository ullageTableDataRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final Long TEST_ID = 1L;

  @Test
  void testGetVesselTanks() {
    Vessel vessel = new Vessel();
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(vessel);
    when(this.tankCategoryRepository.getOne(anyLong())).thenReturn(new TankCategory());
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
  void testGetVesselDetailsForAlgo() {
    Vessel vessel = new Vessel();
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
    when(this.bendingMomentRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new BendingMoment()));
    when(this.shearingForceRepository.findByVessel(any(Vessel.class)))
        .thenReturn(Arrays.asList(new ShearingForce()));
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

    StreamRecorder<VesselAlgoReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselDetailsForAlgo(
        VesselAlgoRequest.newBuilder().setVesselId(TEST_ID).build(), responseObserver);
    List<VesselAlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
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
