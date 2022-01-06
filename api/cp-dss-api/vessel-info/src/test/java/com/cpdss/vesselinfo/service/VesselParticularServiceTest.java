/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      VesselParticularService.class,
    })
public class VesselParticularServiceTest {
  @Autowired VesselParticularService vesselParticularService;

  @MockBean VesselRepository vesselRepository;

  @MockBean VesselTankRepository vesselTankRepository;

  @MockBean VesselPumpRepository vesselPumpRepository;

  @MockBean PumpTypeRepository pumpTypeRepository;

  @MockBean TankTypeRepository tankTypeRepository;

  @MockBean VesselManifoldRepository vesselManifoldRepository;

  @MockBean VesselFlowRateRepository vesselFlowRateRepository;

  @MockBean VesselDraftConditionRepository vesselDraftConditionRepository;

  @MockBean VesselVendingCapacityRepository vesselVentingCapacityRepository;

  @MockBean VesselPumpService vesselPumpService;

  @ParameterizedTest
  @ValueSource(longs = {1l, 4l})
  void testGetVesselParticulars(Long l) throws GenericServiceException {
    VesselInfo.VesselParticulars.Builder builder = VesselInfo.VesselParticulars.newBuilder();
    VesselInfo.LoadingInfoRulesRequest request =
        VesselInfo.LoadingInfoRulesRequest.newBuilder().setVesselId(1l).build();
    VesselDraftCondition condition = getDraftCondition();
    DraftCondition draftCondition = new DraftCondition();
    draftCondition.setId(l);
    condition.setDraftCondition(draftCondition);
    when(vesselRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getVesl());
    when(vesselFlowRateRepository.findByVessel(any(Vessel.class))).thenReturn(getFlowRates());
    when(vesselPumpRepository.findAllByVesselAndIsActiveTrue(
            any(Vessel.class), any(Pageable.class)))
        .thenReturn(new PageImpl<>(getPumpList()));
    when(vesselManifoldRepository.findByVesselXidAndIsActiveTrue(anyLong()))
        .thenReturn(Arrays.asList(new VesselManifold()));
    when(vesselDraftConditionRepository.findByVesselAndIsActive(any(Vessel.class), anyBoolean()))
        .thenReturn(Arrays.asList(condition));
    when(vesselTankRepository.findByVesselAndIsActive(any(Vessel.class), anyBoolean()))
        .thenReturn(getListVesselTank());
    when(vesselVentingCapacityRepository.findByVesselIdAndIsActiveTrue(anyLong()))
        .thenReturn(getCapacity());

    vesselParticularService.getVesselParticulars(builder, request);
    assertEquals("1", builder.getPvBreakerVentingVaccum());
  }

  @Test
  void testGetVesselParticularsWithException() {
    VesselInfo.VesselParticulars.Builder builder = VesselInfo.VesselParticulars.newBuilder();
    VesselInfo.LoadingInfoRulesRequest request =
        VesselInfo.LoadingInfoRulesRequest.newBuilder().setVesselId(1l).build();

    when(this.vesselTankRepository.findByIdInAndIsActive(anyList(), anyBoolean())).thenReturn(null);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> vesselParticularService.getVesselParticulars(builder, request));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetVesselTanksByTankIds() {
    VesselInfo.VesselTankRequest request =
        VesselInfo.VesselTankRequest.newBuilder().addAllTankIds(Arrays.asList(1l)).build();
    VesselInfo.VesselTankReply.Builder builder = VesselInfo.VesselTankReply.newBuilder();

    when(this.vesselTankRepository.findByIdInAndIsActive(anyList(), anyBoolean()))
        .thenReturn(getListVesselTank());

    vesselParticularService.getVesselTanksByTankIds(request, builder);
    assertEquals(1l, builder.getVesselTankInfo(0).getTankId());
  }

  private Vessel getVesl() {
    Vessel vessel = new Vessel();
    vessel.setId(1L);
    vessel.setVesselTypeXid(1);
    vessel.setName("1");
    vessel.setBuilder("1");
    vessel.setImoNumber("1");
    vessel.setPortOfRegistry("1");
    vessel.setOfficialNumber("1");
    vessel.setSignalLetter("1");
    vessel.setNavigationAreaId(1);
    vessel.setTypeOfShip("1");
    vessel.setDateOfLaunching(new Date());
    vessel.setDateOfDelivery(new Date());
    vessel.setDateKeelLaid(new Date());
    vessel.setClass1("1");
    vessel.setNavigationArea("1");
    vessel.setBm_sf_model_type(1);
    vessel.setBreadthMolded(new BigDecimal(1));
    vessel.setRegisterLength(new BigDecimal(1));
    vessel.setLengthOverall(new BigDecimal(1));
    vessel.setLengthBetweenPerpendiculars(new BigDecimal(1));
    vessel.setDepthMolded(new BigDecimal(1));
    vessel.setDesignedLoaddraft(new BigDecimal(1));
    vessel.setDraftFullLoadSummer(new BigDecimal(1));
    vessel.setThicknessOfUpperDeckStringerPlate(new BigDecimal(1));
    vessel.setThicknessOfKeelplate(new BigDecimal(1));
    vessel.setDeadweight(new BigDecimal(1));
    vessel.setLightweight(new BigDecimal(1));
    vessel.setLcg(new BigDecimal(1));
    vessel.setKeelToMastHeight(new BigDecimal(1));
    vessel.setDeadweightConstant(new BigDecimal(1));
    vessel.setProvisionalConstant(new BigDecimal(1));
    vessel.setDeadweightConstantLcg(new BigDecimal(1));
    vessel.setProvisionalConstantLcg(new BigDecimal(1));
    vessel.setGrossTonnage(new BigDecimal(1));
    vessel.setNetTonnage(new BigDecimal(1));
    vessel.setDeadweightConstantTcg(new BigDecimal(1));
    vessel.setHasLoadicator(true);
    vessel.setMaxLoadRate(new BigDecimal(1));
    vessel.setMastRiser(new BigDecimal(1));
    vessel.setHeightOfManifoldAboveDeck(new BigDecimal(1));
    // vessel.setUllageTrimCorrections(getUTC());
    return vessel;
  }

  private List<VesselFlowRate> getFlowRates() {
    List<VesselFlowRate> flowRates = new ArrayList<>();
    VesselFlowRate flowRate = new VesselFlowRate();
    flowRate.setFlowRateSix(new BigDecimal(1));
    flowRate.setFlowRateParameter(getParameter());
    flowRates.add(flowRate);
    return flowRates;
  }

  private FlowRateParameter getParameter() {
    FlowRateParameter parameter = new FlowRateParameter();
    parameter.setId(1l);
    return parameter;
  }

  private VesselVentingCapacity getCapacity() {
    VesselVentingCapacity capacity = new VesselVentingCapacity();
    capacity.setHighVelocityPressure(new BigDecimal(1));
    capacity.setHighVelocityVaccum(new BigDecimal(1));
    capacity.setPvBreakerPressure(new BigDecimal(1));
    capacity.setPvBreakerVaccum(new BigDecimal(1));
    return capacity;
  }

  private List<VesselTank> getListVesselTank() {
    List<VesselTank> vesselTanks = new ArrayList<VesselTank>();
    VesselTank vessel = new VesselTank();
    vessel.setId(1L);
    vessel.setTankCategory(getTankCategory());
    vessel.setShortName("1");
    vessel.setFrameNumberFrom("1");
    vessel.setFrameNumberTo("1");
    vessel.setTankName("1");
    vessel.setHeightFrom("1");
    vessel.setHeightTo("1");
    vessel.setFillCapacityCubm(new BigDecimal(1));
    vessel.setDensity(new BigDecimal(1));
    vessel.setTankGroup(1);
    vessel.setTankOrder(1);
    vessel.setIsSlopTank(true);
    vessel.setFullCapacityCubm(new BigDecimal(1));
    vessel.setTankDisplayOrder(1);
    vessel.setShowInOhqObq(true);
    vessel.setTankPositionCategory("1");
    vesselTanks.add(vessel);
    return vesselTanks;
  }

  private TankCategory getTankCategory() {
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(1L);
    tankCategory.setName("1");
    tankCategory.setShortName("1");
    tankCategory.setColorCode("1");
    return tankCategory;
  }

  private List<VesselPumps> getPumpList() {
    List<VesselPumps> pumpList = new ArrayList<>();
    VesselPumps pumps = new VesselPumps();
    PumpType type = new PumpType();
    type.setId(2l);
    pumps.setPumpType(type);
    pumps.setCapacity(1l);
    pumpList.add(pumps);
    return pumpList;
  }

  private VesselDraftCondition getDraftCondition() {
    VesselDraftCondition condition = new VesselDraftCondition();
    DraftCondition draftCondition = new DraftCondition();
    draftCondition.setId(1l);
    condition.setDraftCondition(draftCondition);
    condition.setDraftExtreme(new BigDecimal(1));
    condition.setDisplacement(new BigDecimal(1));
    condition.setDeadweight(new BigDecimal(1));
    condition.setDraftExtreme(new BigDecimal(1));
    condition.setDraftExtreme(new BigDecimal(1));
    condition.setDraftExtreme(new BigDecimal(1));

    condition.setId(1l);
    return condition;
  }
}
