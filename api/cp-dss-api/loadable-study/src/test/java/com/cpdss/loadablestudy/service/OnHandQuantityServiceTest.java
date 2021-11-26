/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
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
import org.springframework.test.util.ReflectionTestUtils;

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

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

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

    when(onHandQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getOnHandQuantity());
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLoadableStudyOpt());
    when(loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getPortRotation());
    when(voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    when(loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternList);
    when(loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(portRotations);
    when(onHandQuantityRepository.save(any(com.cpdss.loadablestudy.entity.OnHandQuantity.class)))
        .thenReturn(getOnHandQuantity());
    try {
      var entity = onHandQuantityService.saveOnHandQuantity(request, replyBuilder);
      Mockito.verify(loadableStudyPortRotationRepository)
          .save(any(LoadableStudyPortRotation.class));
      Mockito.verify(onHandQuantityRepository).save(any(OnHandQuantity.class));
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
    when(onHandQuantityRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
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
    when(onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(LoadableStudyPortRotation.class),
            anyBoolean()))
        .thenReturn(onHandQuantities);
    this.onHandQuantityService.deletePortRotationDetails(loadableStudy, entity);
    Mockito.verify(onHandQuantityRepository).saveAll(Mockito.anyList());
  }

  @Test
  void testGetOnHandQuantity() throws GenericServiceException {
    OnHandQuantityService spyService = new OnHandQuantityService();
    LoadableStudy.OnHandQuantityRequest request =
        LoadableStudy.OnHandQuantityRequest.newBuilder().build();
    LoadableStudy.OnHandQuantityReply.Builder replyBuilder =
        LoadableStudy.OnHandQuantityReply.newBuilder();
    VoyageStatus voyageStatus = new VoyageStatus();

    List<OnHandQuantity> onHandQuantities = new ArrayList<>();
    onHandQuantities.add(getOnHandQuantity());

    List<VesselInfo.VesselTankDetail> tankDetailList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setShowInOhqObq(true)
            .setTankCategoryId(1l)
            .setTankCategoryName("1")
            .setTankCategoryShortName("1")
            .setTankId(1l)
            .setShortName("1")
            .setColourCode("1")
            .build();
    tankDetailList.add(tankDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVesselTanks(tankDetailList)
            .build();
    when(vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    doNothing()
        .when(synopticService)
        .populateOnHandQuantityData(any(Optional.class), any(LoadableStudyPortRotation.class));
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortXIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(onHandQuantities);
    when(onHandQuantityRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(onHandQuantities);
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(LoadableStudyPortRotation.class),
            anyBoolean()))
        .thenReturn(onHandQuantities);
    when(this.voyageStatusRepository.getOne(anyLong())).thenReturn(voyageStatus);
    when(loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getPortRotation());
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLoadableStudyOpt());

    ReflectionTestUtils.setField(spyService, "onHandQuantityRepository", onHandQuantityRepository);
    ReflectionTestUtils.setField(spyService, "voyageStatusRepository", voyageStatusRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(spyService, "synopticService", synopticService);

    var result = spyService.getOnHandQuantity(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanks() {
    OnHandQuantityService spyService = new OnHandQuantityService();
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder().setVesselId(1l).build();
    when(vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    var result = spyService.getVesselTanks(request);
    assertEquals(1l, result.getVesselId());
  }
}
