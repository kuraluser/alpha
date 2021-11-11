/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      OnHandQuantityService.class,
    })
public class OnHandQuantityServiceTest {
  @Autowired OnHandQuantityService onHandQuantityService;

  @MockBean OnHandQuantityRepository onHandQuantityRepository;

  @MockBean LoadableStudyRepository loadableStudyRepository;

  @MockBean LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @MockBean LoadablePatternService loadablePatternService;

  @MockBean VoyageService voyageService;

  @MockBean VoyageRepository voyageRepository;

  @MockBean LoadablePatternRepository loadablePatternRepository;

  @MockBean VoyageStatusRepository voyageStatusRepository;

  @MockBean SynopticService synopticService;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testSaveOnHandQuantity() {
    LoadableStudy.OnHandQuantityDetail request =
        LoadableStudy.OnHandQuantityDetail.newBuilder()
            .setIsPortRotationOhqComplete(true)
            .setTankId(1L)
            .setFuelTypeId(1L)
            .setArrivalQuantity("1")
            .setArrivalVolume("1")
            .setDepartureQuantity("1")
            .setDepartureVolume("1")
            .setDensity("1")
            .setLoadableStudyId(1L)
            .build();

    LoadableStudy.OnHandQuantityReply.Builder replyBuilder =
        LoadableStudy.OnHandQuantityReply.newBuilder();

    Voyage voyage = new Voyage();
    VoyageStatus voyageStatus = new VoyageStatus();
    voyageStatus.setId(1L);
    voyage.setVoyageStatus(voyageStatus);
    voyage.setId(1L);

    List<LoadablePattern> loadablePatternList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setCaseNumber(1);
    loadablePattern.setConstraints("1");
    loadablePatternList.add(loadablePattern);

    List<LoadableStudyPortRotation> portRotations = new ArrayList<>();
    portRotations.add(getPortRotation());

    Mockito.when(
            onHandQuantityRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOnHandQuantity());
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadableStudyOpt());
    Mockito.when(
            loadableStudyPortRotationRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getPortRotation());
    Mockito.when(voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(),
                Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
                Mockito.anyBoolean()))
        .thenReturn(loadablePatternList);
    Mockito.when(
            loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(portRotations);
    Mockito.when(
            onHandQuantityRepository.save(
                Mockito.any(com.cpdss.loadablestudy.entity.OnHandQuantity.class)))
        .thenReturn(getOnHandQuantity());
    try {
      var entity = onHandQuantityService.saveOnHandQuantity(request, replyBuilder);
      Mockito.verify(loadableStudyPortRotationRepository)
          .save(Mockito.any(LoadableStudyPortRotation.class));
      Mockito.verify(onHandQuantityRepository).save(Mockito.any(OnHandQuantity.class));
      assertEquals(SUCCESS, entity.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyPortRotation getPortRotation() {
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setPortRotationType("1");
    portRotation.setPortXId(1L);
    portRotation.setId(1L);
    portRotation.setIsPortRotationOhqComplete(true);
    return portRotation;
  }

  private OnHandQuantity getOnHandQuantity() {
    OnHandQuantity entity = new OnHandQuantity();
    entity.setId(1L);
    entity.setArrivalQuantity(new BigDecimal(1));
    entity.setDepartureQuantity(new BigDecimal(1));
    entity.setDensity(new BigDecimal(1));
    entity.setArrivalVolume(new BigDecimal(1));
    entity.setDensity(new BigDecimal(1));
    entity.setDepartureVolume(new BigDecimal(1));
    entity.setFuelTypeXId(1L);
    entity.setPortXId(1L);
    entity.setTankXId(1L);
    entity.setFuelTypeXId(1L);
    entity.setActualArrivalQuantity(new BigDecimal(1));
    entity.setActualDepartureQuantity(new BigDecimal(1));
    entity.setIsActive(true);
    entity.setPortRotation(getPortRotation());
    return entity;
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getLoadableStudyOpt() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setDetails("1");
    loadableStudy.setCharterer("1");
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    loadableStudy.setVoyage(voyage);
    return Optional.of(loadableStudy);
  }

  @Test
  void testGroupTanks() {
    List<VesselInfo.VesselTankDetail> tankDetailList = new ArrayList<>();
    VesselInfo.VesselTankDetail vesselTankDetail =
        VesselInfo.VesselTankDetail.newBuilder().setTankGroup(1).build();
    tankDetailList.add(vesselTankDetail);
    var result = onHandQuantityService.groupTanks(tankDetailList);
    assertEquals(1, result.get(0).getVesselTankList().get(0).getTankGroup());
  }

  @Test
  void testBuildTankDetail() {
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankCategoryName("1")
            .setTankCategoryShortName("1")
            .setShortName("1")
            .setTankCategoryId(1L)
            .setColourCode("1")
            .setTankId(1L)
            .setFrameNumberFrom("1")
            .setFrameNumberTo("1")
            .setTankName("1")
            .setIsSlopTank(true)
            .setDensity("1")
            .setFillCapacityCubm("1")
            .setHeightFrom("1")
            .setHeightTo("1")
            .setTankDisplayOrder(1)
            .setTankOrder(1)
            .setTankGroup(1)
            .setFullCapacityCubm("1")
            .setShowInOhqObq(true)
            .build();
    var result = onHandQuantityService.buildTankDetail(tankDetail);
    assertEquals("1", result.getTankName());
  }

  @Test
  void testBuildOnHandQuantityDetails() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudyEntity.setDetails("1");
    loadableStudyEntity.setCharterer("1");
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    loadableStudyEntity.setVoyage(voyage);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    ModelMapper modelMapper = new ModelMapper();
    List<OnHandQuantity> onHandQuantities = new ArrayList<>();
    onHandQuantities.add(getOnHandQuantity());
    Mockito.when(
            onHandQuantityRepository.findByLoadableStudyAndIsActive(
                Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
                Mockito.anyBoolean()))
        .thenReturn(onHandQuantities);
    this.onHandQuantityService.buildOnHandQuantityDetails(
        loadableStudyEntity, loadableStudy, modelMapper);
    assertEquals(1L, loadableStudy.getOnHandQuantity().get(0).getPortId());
  }

  @Test
  void testDeletePortRotationDetails() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    List<OnHandQuantity> onHandQuantities = new ArrayList<>();
    onHandQuantities.add(getOnHandQuantity());
    Mockito.when(
            onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
                Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
                Mockito.any(LoadableStudyPortRotation.class),
                Mockito.anyBoolean()))
        .thenReturn(onHandQuantities);
    this.onHandQuantityService.deletePortRotationDetails(loadableStudy, entity);
    Mockito.verify(onHandQuantityRepository).saveAll(Mockito.anyList());
  }
}
