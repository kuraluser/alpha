/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      CargoService.class,
    })
public class CargoServiceTest {
  @Autowired CargoService cargoService;
  @MockBean CommingleColourRepository commingleColourRepository;
  @MockBean CargoHistoryRepository cargoHistoryRepository;
  @MockBean ApiTempHistoryRepository apiTempHistoryRepository;
  @MockBean PurposeOfCommingleRepository purposeOfCommingleRepository;
  @MockBean CommingleCargoRepository commingleCargoRepository;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean LoadableStudyService loadableStudyService;
  @MockBean OnHandQuantityService onHandQuantityService;
  @MockBean VoyageService voyageService;
  @MockBean LoadablePatternService loadablePatternService;
  @MockBean CargoNominationRepository cargoNominationRepository;
  @MockBean VoyageRepository voyageRepository;
  @MockBean LoadablePatternRepository loadablePatternRepository;
  @MockBean VoyageStatusRepository voyageStatusRepository;
  @MockBean LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private FileRepoService fileRepoService;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testGetAllCargoHistory() {
    Map<String, LoadableStudy.FilterSpecification> map = new HashMap<>();
    List<Long> ids = new ArrayList<>();
    ids.add(1l);
    LoadableStudy.CargoHistoryRequest request =
        LoadableStudy.CargoHistoryRequest.newBuilder()
            .setSortBy("1")
            .setOrderBy("asc")
            .setPage(1)
            .setPageSize(1)
            .putAllFilterParams(map)
            .build();

    List<String> values = new ArrayList<>();
    values.add("1986-04-08T12:30");
    values.add("1986-04-08T12:30");
    LoadableStudy.FilterSpecification filterSpecification =
        LoadableStudy.FilterSpecification.newBuilder()
            .setOperation("1")
            .addAllIds(ids)
            .setOperation("LIKE")
            .addAllValues(values)
            .build();
    LoadableStudy.CargoHistoryReply.Builder replyBuilder =
        LoadableStudy.CargoHistoryReply.newBuilder();

    Page<ApiTempHistory> pagedResult = new PageImpl(getApiTempHistoryList());
    Mockito.when(
            apiTempHistoryRepository.findAll(
                Mockito.any(Specification.class), Mockito.any(Pageable.class)))
        .thenReturn(pagedResult);
    Mockito.when(apiTempHistoryRepository.findAll(Mockito.any(Pageable.class)))
        .thenReturn(pagedResult);
    var result = cargoService.getAllCargoHistory(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoHistoryByCargo() {
    LoadableStudy.LatestCargoRequest request =
        LoadableStudy.LatestCargoRequest.newBuilder()
            .setPortId(1l)
            .setCargoId(1l)
            .setVesselId(1l)
            .build();
    LoadableStudy.LatestCargoReply.Builder replyBuilder =
        LoadableStudy.LatestCargoReply.newBuilder();
    List<ApiTempHistory> apiHistories = new ArrayList<>();
    ApiTempHistory apiTempHistory = new ApiTempHistory();
    apiTempHistory.setApi(new BigDecimal(1));
    apiTempHistory.setTemp(new BigDecimal(1));
    apiHistories.add(apiTempHistory);
    Mockito.when(
            apiTempHistoryRepository
                .findByLoadingPortIdAndCargoIdAndLoadedDateNotNullOrderByLoadedDateDesc(
                    Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(apiHistories);
    var result = cargoService.getCargoHistoryByCargo(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetPurposeOfCommingle() {
    LoadableStudy.PurposeOfCommingleRequest request =
        LoadableStudy.PurposeOfCommingleRequest.newBuilder().build();
    LoadableStudy.PurposeOfCommingleReply.Builder reply =
        LoadableStudy.PurposeOfCommingleReply.newBuilder();
    List<PurposeOfCommingle> purposeList = new ArrayList<>();
    PurposeOfCommingle purposeOfCommingle = new PurposeOfCommingle();
    purposeOfCommingle.setPurpose("1");
    purposeOfCommingle.setId(1l);
    purposeList.add(purposeOfCommingle);

    Mockito.when(purposeOfCommingleRepository.findAll()).thenReturn(purposeList);
    var result = cargoService.getPurposeOfCommingle(request, reply);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testBuildCommingleCargoDetails() {
    Long loadableStudyId = 1l;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    ModelMapper modelMapper = new ModelMapper();
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos = new ArrayList<>();
    com.cpdss.loadablestudy.entity.CommingleCargo commingleCargo =
        new com.cpdss.loadablestudy.entity.CommingleCargo();
    commingleCargo.setCargo1Xid(1l);
    commingleCargo.setCargo2Xid(1l);
    commingleCargo.setCargoNomination1Id(1L);
    commingleCargo.setCargoNomination2Id(1L);
    commingleCargo.setCargo1Pct(new BigDecimal(1));
    commingleCargo.setCargo2Pct(new BigDecimal(1));
    commingleCargos.add(commingleCargo);
    Mockito.when(
            commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(commingleCargos);
    cargoService.buildCommingleCargoDetails(loadableStudyId, loadableStudy, modelMapper);
    var result = loadableStudy.getCommingleCargos().get(0).getCargo1Id();
    assertEquals(1l, result);
  }

  @Test
  void testGetCommingleCargo() {
    LoadableStudy.CommingleCargoRequest request =
        LoadableStudy.CommingleCargoRequest.newBuilder().setVesselId(1l).build();
    LoadableStudy.CommingleCargoReply.Builder replyBuilder =
        LoadableStudy.CommingleCargoReply.newBuilder();

    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        Optional.of(loadableStudy);

    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoList = new ArrayList<>();
    com.cpdss.loadablestudy.entity.CommingleCargo commingleCargo =
        new com.cpdss.loadablestudy.entity.CommingleCargo();
    commingleCargo.setId(1l);
    commingleCargo.setPurposeXid(1l);
    commingleCargo.setIsSlopOnly(true);
    commingleCargo.setTankIds("1,1");
    commingleCargo.setCargo1Xid(1l);
    commingleCargo.setCargo2Xid(1l);
    commingleCargo.setCargo1Pct(new BigDecimal(1));
    commingleCargo.setCargo2Pct(new BigDecimal(1));
    commingleCargo.setQuantity(new BigDecimal(1));
    commingleCargo.setCargoNomination1Id(1l);
    commingleCargo.setCargoNomination2Id(1l);
    commingleCargoList.add(commingleCargo);

    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();

    List<LoadableStudy.TankDetail> tankLists = new ArrayList<>();
    LoadableStudy.TankDetail tankDetail =
        LoadableStudy.TankDetail.newBuilder().setTankId(1l).build();
    tankLists.add(tankDetail);
    LoadableStudy.TankList tankList =
        LoadableStudy.TankList.newBuilder().addAllVesselTank(tankLists).build();
    List<LoadableStudy.TankList> tankListList = new ArrayList<>();
    tankListList.add(tankList);

    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadableStudyOpt);
    Mockito.when(
            this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(commingleCargoList);
    Mockito.when(loadableStudyService.getVesselTanks(Mockito.any(VesselInfo.VesselRequest.class)))
        .thenReturn(vesselReply);
    Mockito.when(onHandQuantityService.groupTanks(Mockito.anyList())).thenReturn(tankListList);
    try {
      var result = cargoService.getCommingleCargo(request, replyBuilder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveCommingleCargo() {
    List<LoadableStudy.CommingleCargo> commingleCargoList = new ArrayList<>();
    List<Long> longs = new ArrayList<>();
    longs.add(1l);
    LoadableStudy.CommingleCargo commingleCargo =
        LoadableStudy.CommingleCargo.newBuilder()
            .setId(1l)
            .setCargo1Id(1l)
            .setCargoNomination1Id(1l)
            .setCargoNomination2Id(1l)
            .setPurposeId(1l)
            .addAllPreferredTanks(longs)
            .setCargo1Id(1l)
            .setCargo2Id(1l)
            .setCargo1Pct("1")
            .setCargo2Pct("1")
            .setQuantity("1")
            .setSlopOnly(true)
            .build();
    commingleCargoList.add(commingleCargo);
    LoadableStudy.CommingleCargoRequest request =
        LoadableStudy.CommingleCargoRequest.newBuilder()
            .setLoadableStudyId(1l)
            .addAllCommingleCargo(commingleCargoList)
            .build();
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    voyage.setCaptainXId(1l);
    LoadableStudy.CommingleCargoReply.Builder replyBuilder =
        LoadableStudy.CommingleCargoReply.newBuilder();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setVoyage(voyage);
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        Optional.of(loadableStudy);
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoEntityList =
        new ArrayList<>();
    CommingleCargo commingleCargoEntity = new CommingleCargo();
    commingleCargoEntity.setId(1l);
    commingleCargoEntityList.add(commingleCargoEntity);
    Optional<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoOptional =
        Optional.of(commingleCargoEntity);
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadableStudyOpt);
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(),
                Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
                Mockito.anyBoolean()))
        .thenReturn(generatedPatterns);
    Mockito.when(
            this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(commingleCargoEntityList);
    Mockito.when(
            this.commingleCargoRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(commingleCargoOptional);
    Mockito.when(
            this.commingleColourRepository.findByAbbreviationAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getColour());
    try {
      var result = cargoService.saveCommingleCargo(request, replyBuilder);
      Mockito.verify(commingleCargoRepository).deleteCommingleCargo(Mockito.anyList());
      Mockito.verify(this.commingleCargoRepository).saveAll(Mockito.anyList());
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<CommingleColour> getColour() {
    CommingleColour commingleColour = new CommingleColour();
    commingleColour.setCommingleColour("1");
    return Optional.of(commingleColour);
  }

  @Test
  void testGetCargoApiTempHistory() {
    LoadableStudy.CargoHistoryRequest request =
        LoadableStudy.CargoHistoryRequest.newBuilder().build();
    LoadableStudy.CargoHistoryReply.Builder replyBuilder =
        LoadableStudy.CargoHistoryReply.newBuilder();
    Mockito.when(
            this.apiTempHistoryRepository.findApiTempHistoryWithYearAfter(
                Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(getApiTempHistoryList());
    var result = cargoService.getCargoApiTempHistory(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private List<ApiTempHistory> getApiTempHistoryList() {
    List<ApiTempHistory> apiTempHistoryList = new ArrayList<>();
    ApiTempHistory apiTempHistory = new ApiTempHistory();
    apiTempHistory.setCargoId(1l);
    apiTempHistory.setVesselId(1l);
    apiTempHistory.setLoadingPortId(1l);
    LocalDateTime aDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
    apiTempHistory.setLoadedDate(aDateTime);
    apiTempHistory.setYear(1);
    apiTempHistory.setMonth(1);
    apiTempHistory.setDate(1);
    apiTempHistory.setApi(new BigDecimal(1));
    apiTempHistory.setTemp(new BigDecimal(1));
    apiTempHistoryList.add(apiTempHistory);
    return apiTempHistoryList;
  }
}
