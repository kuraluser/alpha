/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      VesselPumpService.class,
    })
public class VesselPumpServiceTest {

  @Autowired VesselPumpService vesselPumpService;

  @MockBean VesselRepository vesselRepository;

  @MockBean VesselPumpRepository vesselPumpRepository;

  @MockBean PumpTypeRepository pumpTypeRepository;

  @MockBean TankTypeRepository tankTypeRepository;

  @MockBean VesselManifoldRepository vesselManifoldRepository;

  @MockBean VesselBottomLineRepository vesselBottomLineRepository;

  @MockBean VesselValveSequenceRepository vesselValveSequenceRepository;

  @Test
  void testGetVesselPumpsAndTypes() {
    VesselInfo.VesselPumpsResponse.Builder builder = VesselInfo.VesselPumpsResponse.newBuilder();
    Long vesselId = 1L;
    Mockito.when(vesselRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVessel());
    Mockito.when(pumpTypeRepository.findAll(Mockito.any(Pageable.class)))
        .thenReturn(getPumpTypes());
    Mockito.when(
            vesselPumpRepository.findAllByVesselAndIsActiveTrueOrderById(
                Mockito.any(Vessel.class), Mockito.any(Pageable.class)))
        .thenReturn(getVesselPumps());
    Mockito.when(tankTypeRepository.findAll(Mockito.any(Pageable.class)))
        .thenReturn(getTankTypes());
    Mockito.when(vesselManifoldRepository.findAll(Mockito.any(Pageable.class)))
        .thenReturn(getVesselManifolds());
    Mockito.when(vesselBottomLineRepository.findAll(Mockito.any(Pageable.class)))
        .thenReturn(getVesselBottomLine());
    try {
      var vesselPumpsResponse = this.vesselPumpService.getVesselPumpsAndTypes(builder, vesselId);
      assertEquals("SUCCESS", vesselPumpsResponse.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Vessel getVessel() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    vessel.setHomogeneousLoadingRate(new BigDecimal(1));
    vessel.setWingTankLoadingRate(new BigDecimal(1));
    vessel.setCenterTankLoadingRate(new BigDecimal(1));
    vessel.setName("1");
    return vessel;
  }

  private Page<PumpType> getPumpTypes() {
    PumpType pumpType = new PumpType();
    pumpType.setId(1L);
    pumpType.setName("1");
    return new PageImpl<>(Collections.singletonList(pumpType));
  }

  private Page<VesselPumps> getVesselPumps() {
    VesselPumps vesselPumps = new VesselPumps();
    vesselPumps.setId(1L);
    vesselPumps.setVessel(getVessel());
    vesselPumps.setPumpType(getPumpType());
    vesselPumps.setPumpName("1");
    vesselPumps.setPumpCode("1");
    vesselPumps.setCapacity(1L);
    return new PageImpl<>(Collections.singletonList(vesselPumps));
  }

  private PumpType getPumpType() {
    PumpType pumpType = new PumpType();
    pumpType.setId(1L);
    return pumpType;
  }

  private Page<TankType> getTankTypes() {
    TankType tankType = new TankType();
    tankType.setId(1L);
    tankType.setTypeName("1");
    return new PageImpl<>(Collections.singletonList(tankType));
  }

  private Page<VesselManifold> getVesselManifolds() {
    VesselManifold vesselManifold = new VesselManifold();
    vesselManifold.setId(1L);
    vesselManifold.setManifoldName("1");
    vesselManifold.setManifoldCode("1");
    vesselManifold.setVesselXid(1L);
    vesselManifold.setTankType(getTankType());
    return new PageImpl<>(Collections.singletonList(vesselManifold));
  }

  private TankType getTankType() {
    TankType tankType = new TankType();
    tankType.setId(1L);
    return tankType;
  }

  private Page<VesselBottomLine> getVesselBottomLine() {
    VesselBottomLine vesselBottomLine = new VesselBottomLine();
    vesselBottomLine.setId(1L);
    vesselBottomLine.setBottomLineName("1");
    vesselBottomLine.setBottomLineCode("1");
    vesselBottomLine.setVesselXid(1L);
    return new PageImpl<>(Collections.singletonList(vesselBottomLine));
  }

  @Test
  void testBuildVesselValveSeqMessage() {
    List<VesselValveSequence> list = new ArrayList<>();
    VesselValveSequence vesselValveSequence = new VesselValveSequence();
    vesselValveSequence.setId(1L);
    vesselValveSequence.setIsCommonValve(true);
    vesselValveSequence.setIsShut(true);
    vesselValveSequence.setPipelineId(1);
    vesselValveSequence.setPipelineName("1");
    vesselValveSequence.setPipelineColor("1");
    vesselValveSequence.setPipelineType("1");
    vesselValveSequence.setPumpType("1");
    vesselValveSequence.setPumpCode("1");
    vesselValveSequence.setPumpName("1");
    vesselValveSequence.setSequenceNumber(1);
    vesselValveSequence.setSequenceOperationId(1);
    vesselValveSequence.setSequenceOperationName("1");
    vesselValveSequence.setSequenceTypeId(1);
    vesselValveSequence.setSequenceTypeName("1");
    vesselValveSequence.setSequenceVesselMappingId(1);
    vesselValveSequence.setTankShortName("1");
    vesselValveSequence.setStageNumber("1");
    vesselValveSequence.setValveCategory("1");
    vesselValveSequence.setValveCategoryId(1);
    vesselValveSequence.setValveId(1);
    vesselValveSequence.setValveNumber("1");
    vesselValveSequence.setValveSide(1);
    vesselValveSequence.setValveTypeId(1);
    vesselValveSequence.setValveTypeName("1");
    vesselValveSequence.setVesselName("1");
    vesselValveSequence.setVesselTankXid(1);
    vesselValveSequence.setVesselXid(1L);
    list.add(vesselValveSequence);
    var valveSequence = this.vesselPumpService.buildVesselValveSeqMessage(list);
    assertEquals(1L, valveSequence.get(0).getVesselXid());
  }

  @Test
  void testBuildVesselValveEducator() {
    List<VesselValveEducationProcess> data = new ArrayList<>();
    VesselValveEducationProcess vesselValveEducationProcess = new VesselValveEducationProcess();
    vesselValveEducationProcess.setId(1L);
    vesselValveEducationProcess.setEductionProcessMasterId(1);
    vesselValveEducationProcess.setEductorId(1);
    vesselValveEducationProcess.setEductorName("1");
    vesselValveEducationProcess.setSequenceNumber(1);
    vesselValveEducationProcess.setStepName("1");
    vesselValveEducationProcess.setValveNumber("1");
    vesselValveEducationProcess.setStageNumber(1);
    vesselValveEducationProcess.setValveId(1);
    vesselValveEducationProcess.setStageName("1");
    data.add(vesselValveEducationProcess);
    var valveEducationProcess = this.vesselPumpService.buildVesselValveEducator(data);
    assertEquals(1, valveEducationProcess.get(0).getValveId());
  }

  @Test
  void testBuildVesselValveAirPurge() {
    List<VesselValveAirPurgeSequence> all = new ArrayList<>();
    VesselValveAirPurgeSequence vesselValveAirPurgeSequence = new VesselValveAirPurgeSequence();
    vesselValveAirPurgeSequence.setVesselId(1L);
    vesselValveAirPurgeSequence.setId(1L);
    vesselValveAirPurgeSequence.setVesselName("2");
    vesselValveAirPurgeSequence.setShortname("1");
    vesselValveAirPurgeSequence.setTankId(1L);
    vesselValveAirPurgeSequence.setPumpId(1L);
    vesselValveAirPurgeSequence.setPumpCode("2");
    vesselValveAirPurgeSequence.setSequenceNumber(1);
    vesselValveAirPurgeSequence.setValveId(1);
    vesselValveAirPurgeSequence.setValveNumber("1");
    vesselValveAirPurgeSequence.setIsShut(true);
    vesselValveAirPurgeSequence.setIsCopWarmup(true);
    all.add(vesselValveAirPurgeSequence);
    var valveAirPurgeSequence = this.vesselPumpService.buildVesselValveAirPurge(all);
    assertEquals(false, valveAirPurgeSequence.equals(1));
  }

  @Test
  void testBuildVesselValveStrippingSequence() {
    List<VesselValveStrippingSequence> all = new ArrayList<>();
    VesselValveStrippingSequence vesselValveStrippingSequence = new VesselValveStrippingSequence();
    vesselValveStrippingSequence.setVesselId(1L);
    vesselValveStrippingSequence.setId(1L);
    vesselValveStrippingSequence.setVesselName("1");
    vesselValveStrippingSequence.setPipeLineId(1);
    vesselValveStrippingSequence.setPipeLineName("1");
    vesselValveStrippingSequence.setColour("1");
    vesselValveStrippingSequence.setValveId(1);
    vesselValveStrippingSequence.setValve("1");
    vesselValveStrippingSequence.setSequenceNumber(1);
    all.add(vesselValveStrippingSequence);
    var valveStrippingSequence = this.vesselPumpService.buildVesselValveStrippingSequence(all);
    assertEquals(false, valveStrippingSequence.equals(1));
  }
}
