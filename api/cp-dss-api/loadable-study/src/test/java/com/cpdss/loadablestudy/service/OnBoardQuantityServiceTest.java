/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudyStatus;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import com.cpdss.loadablestudy.entity.Voyage;
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
      OnBoardQuantityService.class,
    })
public class OnBoardQuantityServiceTest {
  @Autowired OnBoardQuantityService onBoardQuantityService;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean VoyageService voyageService;
  @MockBean CargoOperationRepository cargoOperationRepository;
  @MockBean LoadablePatternService loadablePatternService;
  @MockBean VoyageRepository voyageRepository;
  @MockBean OnHandQuantityService onHandQuantityService;
  @MockBean LoadablePatternRepository loadablePatternRepository;
  @MockBean LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean VoyageStatusRepository voyageStatusRepository;
  @MockBean LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean CargoNominationRepository cargoNominationRepository;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub
      dischargePlanServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testSaveOnBoardQuantity() {
    LoadableStudy.OnBoardQuantityDetail request =
        LoadableStudy.OnBoardQuantityDetail.newBuilder()
            .setId(1l)
            .setCargoId(1l)
            .setTankId(1l)
            .setPortId(1l)
            .setSounding("1")
            .setWeight("1")
            .setVolume("1")
            .setColorCode("1")
            .setAbbreviation("1")
            .setDensity("1")
            .setIsObqComplete(true)
            .setLoadOnTop(true)
            .build();
    LoadableStudy.OnBoardQuantityReply.Builder replyBuilder =
        LoadableStudy.OnBoardQuantityReply.newBuilder();
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    when(loadablePatternRepository.findLoadablePatterns(
            Mockito.anyLong(),
            Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            Mockito.anyBoolean()))
        .thenReturn(generatedPatterns);
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudy()));
    when(this.onBoardQuantityRepository.findByIdAndIsActive(
            Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOnBoardQuantity());
    when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadableStudy().getVoyage());
    when(this.onBoardQuantityRepository.save(Mockito.any(OnBoardQuantity.class)))
        .thenReturn(getOnBoardQuantity());
    try {
      var result = onBoardQuantityService.saveOnBoardQuantity(request, replyBuilder);
      Mockito.verify(this.loadableStudyRepository)
          .save(Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildOnBoardQuantityDetails() {
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    List<com.cpdss.loadablestudy.domain.OnBoardQuantity> onBoardQuantityList = new ArrayList<>();
    com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantity =
        new com.cpdss.loadablestudy.domain.OnBoardQuantity();
    onBoardQuantityList.add(onBoardQuantity);
    loadableStudy.setOnBoardQuantity(onBoardQuantityList);
    ModelMapper modelMapper = new ModelMapper();
    List<OnBoardQuantity> onBoardQuantities = new ArrayList<>();
    onBoardQuantities.add(getOnBoardQuantity());
    when(onBoardQuantityRepository.findByLoadableStudyAndIsActive(
            Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class), Mockito.anyBoolean()))
        .thenReturn(onBoardQuantities);
    onBoardQuantityService.buildOnBoardQuantityDetails(
        getLoadableStudy(), loadableStudy, modelMapper);
    assertEquals("1", loadableStudy.getOnBoardQuantity().get(0).getApi());
  }

  @Test
  void testGetOnBoardQuantity() throws GenericServiceException {
    OnBoardQuantityService spyService = spy(OnBoardQuantityService.class);

    LoadableStudy.OnBoardQuantityRequest request =
        LoadableStudy.OnBoardQuantityRequest.newBuilder().setCompanyId(1l).setVesselId(1l).build();
    LoadableStudy.OnBoardQuantityReply.Builder replyBuilder =
        LoadableStudy.OnBoardQuantityReply.newBuilder();
    when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadableStudy().getVoyage());
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudy()));
    List<OnBoardQuantity> obqEntities = new ArrayList<>();
    obqEntities.add(getOnBoardQuantity());
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            Mockito.anyLong(),
            Mockito.anyBoolean()))
        .thenReturn(obqEntities);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    List<LoadableStudy.TankList> tankLists = new ArrayList<>();
    when(onHandQuantityService.groupTanks(anyList())).thenReturn(tankLists);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "onBoardQuantityRepository", onBoardQuantityRepository);
    ReflectionTestUtils.setField(spyService, "onHandQuantityService", onHandQuantityService);

    spyService.getOnBoardQuantity(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanks() {
    OnBoardQuantityService spyService = new OnBoardQuantityService();
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder().setVesselId(1l).build();
    when(vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    var result = spyService.getVesselTanks(request);
    assertEquals(1l, result.getVesselId());
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLoadableStudy() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setName("1");
    loadableStudy.setId(1l);
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    loadableStudy.setActive(true);
    loadableStudy.setVoyage(voyage);
    loadableStudy.setPlanningTypeXId(1);
    loadableStudy.setConfirmedLoadableStudyId(1l);
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("1");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    return loadableStudy;
  }

  private OnBoardQuantity getOnBoardQuantity() {
    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    onBoardQuantity.setId(1l);
    onBoardQuantity.setLoadableStudy(getLoadableStudy());
    onBoardQuantity.setCargoId(1l);
    onBoardQuantity.setTankId(1l);
    onBoardQuantity.setPortId(1l);
    onBoardQuantity.setColorCode("1");
    onBoardQuantity.setAbbreviation("1");
    onBoardQuantity.setDensity(new BigDecimal(1));
    return onBoardQuantity;
  }
}
