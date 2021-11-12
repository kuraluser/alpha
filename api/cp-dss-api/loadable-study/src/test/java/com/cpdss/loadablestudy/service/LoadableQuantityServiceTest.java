/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.CargoToppingOffSequenceDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadablePatternCargoToppingOffSequenceRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadableQuantityService.class,
    })
class LoadableQuantityServiceTest {
  @Autowired LoadableQuantityService loadableQuantityService;

  @MockBean LoadableQuantityRepository loadableQuantityRepository;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean OnHandQuantityRepository onHandQuantityRepository;
  @MockBean PortRotationService portRotationService;
  @MockBean VoyageService voyageService;
  @MockBean LoadablePatternService loadablePatternService;
  @MockBean LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;
  @MockBean LoadablePlanQuantityRepository loadablePlanQuantityRepository;

  @Test
  void testSaveLoadableQuantity() {
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setLoadableStudyXId(getLoadableStudyXid());
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest loadableQuantityRequest =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.newBuilder()
            .setLoadableStudyId(1L)
            .setEstFreshWaterOnBoard("1")
            .setEstSagging("1")
            .setEstSeaDensity("1")
            .setVesselLightWeight("1")
            .setOtherIfAny("1")
            .setSaggingDeduction("1")
            .setSgCorrection("1")
            .setTotalQuantity("1")
            .setTpc("1")
            .setVesselAverageSpeed("1")
            .setPortId(1L)
            .setBoilerWaterOnBoard("1")
            .setBallast("1")
            .setRunningHours("1")
            .setRunningDays("1")
            .setFoConInSZ("1")
            .setDraftRestriction("1")
            .setSubTotal("1")
            .setFoConsumptionPerDay("1")
            .setPortRotationId(1L)
            .setEstDOOnBoard("1")
            .setEstFOOnBoard("1")
            .setDistanceFromLastPort("1")
            .setDisplacmentDraftRestriction("1")
            .setDwt("1")
            .setConstant("1")
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.Builder loadableQuantityReply =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.newBuilder();
    Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong()))
        .thenReturn(getLoadableStudy());
    Mockito.when(
            this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getListQuantity());

    Mockito.when(
            this.loadableStudyPortRotationRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadablePortROtation());
    Mockito.when(this.loadableQuantityRepository.save(Mockito.any(LoadableQuantity.class)))
        .thenReturn(getLoadablequnt());
    try {
      this.loadableQuantityService.saveLoadableQuantity(
          loadableQuantityRequest, loadableQuantityReply);
      assertEquals("SUCCESS", loadableQuantityReply.getResponseStatus().getStatus());
      assertEquals("SUCCESS", loadableQuantityReply.getResponseStatus().getMessage());
    } catch (GenericServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private Optional<LoadableStudy> getLoadableStudy() {
    LoadableStudy load = new LoadableStudy();
    load.setId(1L);
    load.setVesselXId(1L);
    load.setVoyage(getvoyage());
    load.setDraftMark(new BigDecimal(1));
    return Optional.of(load);
  }

  private Voyage getvoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    return voyage;
  }

  private LoadableStudy getLoadableStudyXid() {
    LoadableStudy load = new LoadableStudy();
    load.setLoadLineXId(1L);
    load.setId(1L);
    return load;
  }

  private List<LoadableQuantity> getListQuantity() {
    List<LoadableQuantity> lqs = new ArrayList<LoadableQuantity>();
    LoadableQuantity lo = new LoadableQuantity();
    lo.setBallast(new BigDecimal(1));
    lo.setConstant(new BigDecimal(1));
    lo.setLoadableStudyPortRotation(getLoadablePortROtation());
    lqs.add(lo);
    return lqs;
  }

  private LoadableStudyPortRotation getLoadablePortROtation() {
    LoadableStudyPortRotation lsPortRot = new LoadableStudyPortRotation();
    lsPortRot.setBerthXId(1L);
    lsPortRot.setId(2L);
    lsPortRot.setMaxDraft(new BigDecimal(1));
    lsPortRot.setSeaWaterDensity(new BigDecimal(1));
    return lsPortRot;
  }

  private LoadableQuantity getLoadablequnt() {
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId(1L);
    return loadableQuantity;
  }

  @Test
  void testSaveLQuantity() {
    com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.newBuilder()
            .setDraftMark("1")
            .build();
    LoadableStudy ls = new LoadableStudy();
    ls.setId(1L);
    Optional<LoadableStudy> loadableStudy = Optional.ofNullable(ls);

    Mockito.when(
            this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadQuantity());
    assertEquals(new BigDecimal(1), getLoadQuantity().get(0).getConstant());
    this.loadableQuantityService.saveLQuantity(request, loadableStudy);
    Mockito.when(this.loadableQuantityRepository.save(getLoadQuantity().get(0)))
        .thenReturn(getloading());
    assertEquals(new BigDecimal(1), getloading().getDraftRestriction());
  }

  private List<LoadableQuantity> getLoadQuantity() {
    List<LoadableQuantity> loadable = new ArrayList<LoadableQuantity>();
    LoadableQuantity load = new LoadableQuantity();
    load.setBallast(new BigDecimal(1));
    load.setConstant(new BigDecimal(1));
    loadable.add(load);
    return loadable;
  }

  private LoadableQuantity getloading() {
    LoadableQuantity lo = new LoadableQuantity();
    lo.setDraftRestriction(new BigDecimal(1));
    return lo;
  }

  @Test
  void testSaveLoadableQuantity2Loadable() {
    LoadableQuantityCargoDetails lo =
        LoadableQuantityCargoDetails.newBuilder()
            .setTimeRequiredForLoading("1")
            .setCargoNominationTemperature("1")
            .setCargoNominationId(1L)
            .setSlopQuantity("1")
            .setMaxTolerence("1")
            .setMinTolerence("1")
            .setLoadingOrder(1)
            .setPriority(1)
            .setColorCode("1")
            .setCargoAbbreviation("1")
            .setOrderedMT("1")
            .setLoadableMT("1")
            .setCargoId(1L)
            .setEstimatedTemp("1")
            .setEstimatedAPI("1")
            .setDifferencePercentage("1")
            .build();
    LoadablePlanDetailsReply loadablePlanDetailsReply =
        LoadablePlanDetailsReply.newBuilder()
            .addAllLoadableQuantityCargoDetails(Collections.singletonList(lo))
            .build();

    com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails
        loadablePlanPortWiseDetails =
            com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails.newBuilder()
                .setDepartureCondition(loadablePlanDetailsReply)
                .build();
    LoadablePattern loadablePattern = new LoadablePattern();
    List<LoadableQuantityCargoDetails> load = new ArrayList<LoadableQuantityCargoDetails>();

    List<CargoToppingOffSequenceDetails> top = new ArrayList<CargoToppingOffSequenceDetails>();
    CargoToppingOffSequenceDetails cargo =
        CargoToppingOffSequenceDetails.newBuilder().setOrderNumber(1).setTankId(1L).build();
    top.add(cargo);
    this.loadableQuantityService.saveLoadableQuantity(loadablePlanPortWiseDetails, loadablePattern);
    Mockito.verify(loadablePlanQuantityRepository).save(Mockito.any(LoadablePlanQuantity.class));
  }

  @Test
  void testBuildLoadableQuantityDetails() {
    long loadableStudyId = 1L;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    var loan = getLoadableQuantity();
    Mockito.when(
            this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(loan);

    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy1 =
        new com.cpdss.loadablestudy.domain.LoadableStudy();

    this.loadableQuantityService.buildLoadableQuantityDetails(loadableStudyId, loadableStudy);
    assertEquals("1", loadableStudy.getLoadableQuantity().getBallast());
    assertEquals("1", loadableStudy.getLoadableQuantity().getConstant());
    assertEquals("1", loadableStudy.getLoadableQuantity().getOtherIfAny());
  }

  private List<LoadableQuantity> getLoadableQuantity() {
    List<LoadableQuantity> loadableQuantity = new ArrayList<LoadableQuantity>();
    LoadableQuantity load = new LoadableQuantity();
    load.setId(1L);
    load.setBallast(new BigDecimal(1));
    load.setBoilerWaterOnBoard(new BigDecimal(1));
    load.setConstant(new BigDecimal(1));
    load.setDeadWeight(new BigDecimal(1));
    load.setDistanceFromLastPort(new BigDecimal(1));
    load.setDraftRestriction(new BigDecimal(1));
    load.setEstimatedDOOnBoard(new BigDecimal(1));
    load.setEstimatedFOOnBoard(new BigDecimal(1));
    load.setEstimatedFWOnBoard(new BigDecimal(1));
    load.setEstimatedSagging(new BigDecimal(1));
    load.setFoConsumptionInSZ(new BigDecimal(1));
    load.setOtherIfAny(new BigDecimal(1));
    load.setPortId(new BigDecimal(1));
    load.setRunningDays(new BigDecimal(1));
    load.setRunningHours(new BigDecimal(1));
    load.setSaggingDeduction(new BigDecimal(1));
    load.setSgCorrection(new BigDecimal(1));
    load.setTotalFoConsumption(new BigDecimal(1));
    load.setTotalQuantity(new BigDecimal(1));
    load.setTpcatDraft(new BigDecimal(1));
    load.setVesselAverageSpeed(new BigDecimal(1));
    loadableQuantity.add(load);
    return loadableQuantity;
  }

  @Test
  void testValidateLoadableStudyWithLQ() {
    LoadableStudy ls = new LoadableStudy();
    Mockito.when(
            this.loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getPortsid());
    Mockito.when(
            this.loadableQuantityRepository.findByLSIdAndPortRotationId(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadableQuant());
    try {
      this.loadableQuantityService.validateLoadableStudyWithLQ(ls);
      assertEquals(1L, ls.getId());
    } catch (GenericServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private List<PortRotationIdAndPortId> getPortsid() {
    List<PortRotationIdAndPortId> po = new ArrayList<PortRotationIdAndPortId>();
    PortRotationIdAndPortId ports = new PortRotation();
    po.add(ports);
    return po;
  }

  private Optional<LoadableQuantity> getLoadableQuant() {
    LoadableQuantity load = new LoadableQuantity();
    load.setBallast(new BigDecimal(1));
    return Optional.of(load);
  }

  private class PortRotation implements PortRotationIdAndPortId {

    @Override
    public Long getId() {
      // TODO Auto-generated method stub
      return 1L;
    }

    @Override
    public Long getPortId() {
      // TODO Auto-generated method stub
      return 1L;
    }
  }
}
