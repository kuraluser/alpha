/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselTank;
import com.cpdss.vesselinfo.repository.TankCategoryRepository;
import com.cpdss.vesselinfo.repository.VesselChartererMappingRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import com.cpdss.vesselinfo.repository.VesselTankRepository;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
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

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final Long TEST_ID = 1L;

  @Test
  void testGetVesselFuelTanks() {
    Vessel vessel = new Vessel();
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(vessel);
    when(this.tankCategoryRepository.getOne(anyLong())).thenReturn(new TankCategory());
    when(this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
            any(Vessel.class), anyList(), anyBoolean()))
        .thenReturn(this.prepareVesselTankEntities());
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselFuelTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselFuelTanksInvalidVessel() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselFuelTanks(
        VesselRequest.newBuilder().setCompanyId(TEST_ID).setVesselId(TEST_ID).build(),
        responseObserver);
    List<VesselReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselFuelTanksRunTimeException() {
    when(this.vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VesselReply> responseObserver = StreamRecorder.create();
    this.vesselInfoService.getVesselFuelTanks(
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
              tanks.add(tank);
            });
    return tanks;
  }
}
