/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.CargoToppingOffSequenceService;
import com.cpdss.loadingplan.service.LoadingBerthService;
import com.cpdss.loadingplan.service.LoadingDelayService;
import com.cpdss.loadingplan.service.LoadingInformationBuilderService;
import com.cpdss.loadingplan.service.LoadingMachineryInUseService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      LoadingInformationServiceImpl.class,
    })
class LoadingInformationServiceImplTest {

  @Autowired LoadingInformationServiceImpl loadingInformationServiceImpl;

  @MockBean LoadingInformationRepository loadingInformationRepository;

  @MockBean CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;

  @MockBean LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @MockBean LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @MockBean LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean CargoToppingOffSequenceService cargoToppingOffSequenceService;
  @MockBean LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;
  @MockBean LoadingMachineryInUseService loadingMachineryInUseService;
  @MockBean LoadingInformationBuilderService loadingInformationBuilderService;
  @MockBean LoadingDelayService loadingDelayService;
  @MockBean LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @MockBean LoadingBerthService loadingBerthService;
  @MockBean LoadingMachineryInUseRepository loadingMachineryInUseRepository;
  @MockBean ReasonForDelayRepository reasonForDelayRepository;
  @MockBean LoadingDelayRepository loadingDelayRepository;
  @MockBean StageOffsetRepository stageOffsetRepository;
  @MockBean StageDurationRepository stageDurationRepository;
  @MockBean LoadingInformation loadingInformation;
  @MockBean PortTideDetailsRepository portTideDetailsRepository;
  @MockBean LoadingInstructionRepository loadingInstructionRepository;
  @MockBean LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  // getLoadingInformation(Long id)

  @Test
  void getLoadingInformationTest() {
    Mockito.when(this.loadingInformationServiceImpl.getLoadingInformation(1L, 0L, 0L, 0L, 0L))
        .thenReturn(getLoad());
    var loadinginfo = loadingInformationServiceImpl.getLoadingInformation(1L).get();
    assertEquals(getLoad().get().getId(), loadinginfo.getId());
    assertEquals(
        getLoad().get().getCargoToppingOfSequences(), loadinginfo.getCargoToppingOfSequences());
    assertEquals(getLoad().get().getTrackStartEndStage(), loadinginfo.getTrackGradeSwitch());
  }

  private Optional<LoadingInformation> getLoad() {
    LoadingInformation load = new LoadingInformation();
    return Optional.of(load);
  }

  // getLoadingInformation(Long id, Long vesselId, Long voyageId, Long patternId, Long
  // portRotationId))

  @Test
  void getLoadingInformationTest2() {
    Mockito.when(this.loadingInformationServiceImpl.loadingInformationRepository.findById(1L))
        .thenReturn(findById());
    Mockito.when(
            this.loadingInformationServiceImpl.loadingInformationRepository
                .findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue(1L, 1L, 1L))
        .thenReturn(findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue());
    var loadinginfo = loadingInformationServiceImpl.getLoadingInformation(1L, 1L, 1L, 1L, 1L).get();
    assertEquals(
        findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue().get().getId(),
        loadinginfo.getId());
    assertEquals(findById().get().getId(), loadinginfo.getId());
  }

  private Optional<LoadingInformation> findById() {
    LoadingInformation information = new LoadingInformation();
    return Optional.of(information);
  }

  private Optional<LoadingInformation>
      findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue() {
    LoadingInformation information = new LoadingInformation();
    return Optional.of(information);
  }

  // getLoadingInformation( LoadingPlanModels.LoadingInformationRequest
  // request,LoadingPlanModels.LoadingInformation.Builder builder)

  @Test
  void getLoadingInformationTest3() {
    LoadingPlanModels.LoadingInformationRequest request =
        LoadingPlanModels.LoadingInformationRequest.newBuilder()
            .setLoadingPlanId(1L)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setLoadingPatternId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingPlanModels.LoadingInformation.Builder builder =
        LoadingPlanModels.LoadingInformation.newBuilder();

    Mockito.when(this.loadingInformationServiceImpl.getLoadingInformation(1L, 1L, 1L, 1L, 1L))
        .thenReturn(getLoading());

    Optional<LoadingInformation> var1 = getLoading();
    var loading_information = Mockito.any(LoadingInformation.class);

    List<StageOffset> list3 = getStageMin();
    List<StageDuration> list4 = getStageDuration();
    List<ReasonForDelay> list5 = getLoadingDelay();
    List<LoadingDelay> list6 = getLoadingDel();
    List<CargoToppingOffSequence> list7 = getCargo();

    // Loading Details
    Mockito.when(
            this.loadingInformationBuilderService.buildLoadingDetailsMessage(loading_information))
        .thenReturn(getLoadingDetails());

    // Loading Rates
    Mockito.when(
            this.loadingInformationBuilderService.buildLoadingRateMessage(
                Mockito.any(LoadingInformation.class)))
        .thenReturn(getLoadingRates());

    // Loading Berths
    Mockito.when(
            this.loadingInformationServiceImpl.berthDetailsRepository
                .findAllByLoadingInformationAndIsActiveTrue(loading_information))
        .thenReturn(getLoadingBerthDetails());

    List<LoadingBerthDetail> list1 = getLoadingBerthDetails();
    Mockito.when(this.loadingInformationBuilderService.buildLoadingBerthsMessage(list1))
        .thenReturn(getLoadingPlanBerths());

    // Machines in use
    Mockito.when(
            this.loadingInformationServiceImpl.loadingMachineryInUserRepository
                .findAllByLoadingInformationAndIsActiveTrue(loading_information))
        .thenReturn(getLoadingMachineryInUse());

    List<LoadingMachineryInUse> list2 = getLoadingMachineryInUse();
    Mockito.when(this.loadingInformationBuilderService.buildLoadingMachineryInUseMessage(list2))
        .thenReturn(getLoadingModelMachineryInUse());

    // Stage Min Amount Master
    Mockito.when(this.stageOffsetRepository.findAll()).thenReturn(list3);

    // Stage Duration Master
    Mockito.when(this.stageDurationRepository.findAll()).thenReturn(list4);

    // Staging User data and Master data

    Mockito.when(
            this.loadingInformationBuilderService.buildLoadingStageMessage(
                Mockito.any(LoadingInformation.class),
                Mockito.any(ArrayList.class),
                Mockito.any(ArrayList.class)))
        .thenReturn(getLoadingStages());

    // Loading Delay

    Mockito.when(this.reasonForDelayRepository.findAll()).thenReturn(list5);

    Mockito.when(
            this.loadingDelayRepository.findAllByLoadingInformationAndIsActiveTrue(
                loading_information))
        .thenReturn(list6);

    Mockito.when(this.loadingInformationBuilderService.buildLoadingDelayMessage(list5, list6))
        .thenReturn(getLoadDel());

    // cargotoppingoffsequence

    Mockito.when(
            this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(
                Mockito.any(LoadingInformation.class)))
        .thenReturn(list7);

    Mockito.when(this.loadingInformationBuilderService.buildToppingOffMessage(list7))
        .thenReturn(getToppingOff());

    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation abc;
    try {
      abc = this.loadingInformationServiceImpl.getLoadingInformation(request, builder);
      assertEquals(1L, abc.getLoadingInfoId());
      assertEquals("abc", abc.getLoadingDetail().getTimeOfSunrise());
      assertEquals("mnb", abc.getLoadingRate().getInitialLoadingRate());

    } catch (GenericServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private Optional<LoadingInformation> getLoading() {

    LoadingInformation var1 = new LoadingInformation();
    var1.setId(1L);
    var1.setSynopticalTableXId(1L);
    var1.setIsLoadingInfoComplete(false);
    var1.setNoticeTimeForRateReduction(2);

    return Optional.of(var1);
  }

  private LoadingPlanModels.LoadingDetails getLoadingDetails() {
    LoadingPlanModels.LoadingDetails details =
        LoadingPlanModels.LoadingDetails.newBuilder()
            .setTimeOfSunrise("abc")
            .setTimeOfSunset("fed")
            .setStartTime("ds")
            .build();
    return details;
  }

  private LoadingPlanModels.LoadingRates getLoadingRates() {
    LoadingPlanModels.LoadingRates rates =
        LoadingPlanModels.LoadingRates.newBuilder()
            .setNoticeTimeRateReduction("2")
            .setInitialLoadingRate("mnb")
            .setShoreLoadingRate("jkl")
            .build();
    return rates;
  }

  private List<LoadingBerthDetail> getLoadingBerthDetails() {
    List<LoadingBerthDetail> list = new ArrayList<LoadingBerthDetail>();
    return list;
  }

  private List<LoadingPlanModels.LoadingBerths> getLoadingPlanBerths() {
    List<LoadingPlanModels.LoadingBerths> berths = new ArrayList<LoadingPlanModels.LoadingBerths>();
    return berths;
  }

  private List<LoadingMachineryInUse> getLoadingMachineryInUse() {
    List<LoadingMachineryInUse> list20 = new ArrayList<LoadingMachineryInUse>();
    return list20;
  }

  private List<LoadingPlanModels.LoadingMachinesInUse> getLoadingModelMachineryInUse() {
    List<LoadingPlanModels.LoadingMachinesInUse> machines =
        new ArrayList<LoadingPlanModels.LoadingMachinesInUse>();
    return machines;
  }

  private List<StageOffset> getStageMin() {
    List<StageOffset> list11 = new ArrayList<StageOffset>();
    return list11;
  }

  private List<StageDuration> getStageDuration() {
    List<StageDuration> list12 = new ArrayList<StageDuration>();
    return list12;
  }

  private LoadingPlanModels.LoadingStages getLoadingStages() {
    LoadingPlanModels.LoadingStages loadingStages =
        LoadingPlanModels.LoadingStages.newBuilder().setStageOffset(1).setStageDuration(1).build();

    return loadingStages;
  }

  private List<ReasonForDelay> getLoadingDelay() {
    List<ReasonForDelay> list13 = new ArrayList<ReasonForDelay>();
    return list13;
  }

  private List<LoadingDelay> getLoadingDel() {
    List<LoadingDelay> list14 = new ArrayList<LoadingDelay>();
    return list14;
  }

  private LoadingPlanModels.LoadingDelay getLoadDel() {
    LoadingPlanModels.LoadingDelay loadingDelay =
        LoadingPlanModels.LoadingDelay.newBuilder().build();
    return loadingDelay;
  }

  private List<CargoToppingOffSequence> getCargo() {
    List<CargoToppingOffSequence> list15 = new ArrayList<CargoToppingOffSequence>();
    return list15;
  }

  private List<LoadingPlanModels.LoadingToppingOff> getToppingOff() {
    List<LoadingPlanModels.LoadingToppingOff> toppingOff =
        new ArrayList<LoadingPlanModels.LoadingToppingOff>();
    return toppingOff;
  }

  @Test
  void saveLoadingInformationDetailTest() {
    final Integer DEFAULT_STAGE_OFFSET_VALUE = 4;
    final Integer DEFAULT_STAGE_DURATION_VALUE = 4;
    LoadingPlanModels.LoadingInformationDetail loadingInformationDetail =
        LoadingPlanModels.LoadingInformationDetail.newBuilder()
            .setVesselId(1L)
            .setLoadablePatternId(1L)
            .setPortId(1L)
            .setSynopticalTableId(1L)
            .setVoyageId(1L)
            .setPortRotationId(1L)
            .build();
    LoadingInformation loading = new LoadingInformation();

    Mockito.when(
            this.stageOffsetRepository.findByStageOffsetValAndIsActiveTrue(
                DEFAULT_STAGE_OFFSET_VALUE))
        .thenReturn(getOffSetVal());

    Mockito.when(
            this.stageDurationRepository.findByDurationAndIsActiveTrue(
                DEFAULT_STAGE_DURATION_VALUE))
        .thenReturn(getDurationValue());

    Mockito.when(this.loadingInformationRepository.save(loading)).thenReturn(getLoadinginf());

    var loadinginfo =
        this.loadingInformationServiceImpl.saveLoadingInformationDetail(
            loadingInformationDetail, loading);
    assertEquals(getLoadinginf().getId(), loadinginfo.getId());
    assertEquals(getLoadinginf().getLoadablePatternXId(), loadinginfo.getLoadablePatternXId());
    assertEquals(getLoadinginf().getPortXId(), loadinginfo.getPortXId());
  }

  private Optional<StageOffset> getOffSetVal() {
    StageOffset defaultOffsetOpt = new StageOffset();
    return Optional.of(defaultOffsetOpt);
  }

  private Optional<StageDuration> getDurationValue() {
    StageDuration defaultDurationOpt = new StageDuration();
    return Optional.of(defaultDurationOpt);
  }

  private LoadingInformation getLoadinginf() {
    LoadingInformation loading = new LoadingInformation();
    loading.setVesselXId(1L);
    loading.setLoadablePatternXId(1L);
    loading.setPortXId(1L);
    loading.setSynopticalTableXId(1L);
    loading.setIsActive(true);
    loading.setTrackStartEndStage(true);
    loading.setTrackGradeSwitch(true);
    loading.setVoyageId(1L);
    loading.setPortRotationXId(1L);
    loading.setIsLoadingInfoComplete(false);
    return loading;
  }

  @Test
  void testDeleteLoadablePlanDetails() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    this.loadingInformationServiceImpl.deleteLoadablePlanDetails(loadingInformation);
  }

  @Test
  void testgetAllLoadingInfoByVoyageId() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Mockito.when(
            this.loadingInformationRepository.findAllByVesselXIdAndVoyageIdAndIsActiveTrue(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLLI());
    var reply = this.loadingInformationServiceImpl.getAllLoadingInfoByVoyageId(vesselId, voyageId);
    assertEquals(1L, reply.get(0).getId());
  }

  private List<LoadingInformation> getLLI() {
    List<LoadingInformation> list = new ArrayList<>();
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    list.add(loadingInformation);
    return list;
  }

  @Test
  void testSaveLoadingInformation() {
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request =
        LoadingPlanModels.LoadingInformation.newBuilder()
            .setIsLoadingInfoComplete(true)
            .setLoadingDetail(
                LoadingPlanModels.LoadingDetails.newBuilder()
                    .setStartTime("11:23")
                    .setTrimAllowed(
                        LoadingPlanModels.TrimAllowed.newBuilder()
                            .setMaximumTrim("1")
                            .setInitialTrim("1")
                            .setFinalTrim("1")
                            .build())
                    .setId(1L)
                    .build())
            .build();
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getLoading());
    try {
      var loading = this.loadingInformationServiceImpl.saveLoadingInformation(request);
      assertEquals(1L, loading.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveLoadingInfoRates() {
    LoadingPlanModels.LoadingRates source =
        LoadingPlanModels.LoadingRates.newBuilder()
            .setLineContentRemaining("1")
            .setMaxDeBallastingRate("1")
            .setMaxLoadingRate("1")
            .setMinDeBallastingRate("1")
            .setReducedLoadingRate("1")
            .setMinLoadingRate("1")
            .setInitialLoadingRate("1")
            .setNoticeTimeRateReduction("1")
            .setNoticeTimeStopLoading("1")
            .setShoreLoadingRate("1")
            .setId(1L)
            .build();
    LoadingInformation loadingInformation = new LoadingInformation();
    LoadingPlanModels.LoadingInfoSaveResponse.Builder response =
        LoadingPlanModels.LoadingInfoSaveResponse.newBuilder();
    Mockito.when(this.loadingInformationServiceImpl.loadingInformationRepository.findById(1L))
        .thenReturn(findById());
    Mockito.when(
            this.loadingInformationServiceImpl.loadingInformationRepository
                .findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue(1L, 1L, 1L))
        .thenReturn(findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue());
    this.loadingInformationServiceImpl.saveLoadingInfoRates(source, loadingInformation, response);
    Mockito.verify(loadingInformationRepository).save(Mockito.any());
  }

  @Test
  void testSaveLoadingInfoStages() {
    LoadingPlanModels.LoadingStages loadingStage =
        LoadingPlanModels.LoadingStages.newBuilder()
            .setOffset(LoadingPlanModels.StageOffsets.newBuilder().setId(1L).build())
            .setTrackStartEndStage(true)
            .setTrackGradeSwitch(true)
            .setDuration(LoadingPlanModels.StageDuration.newBuilder().setId(1L).build())
            .build();
    LoadingInformation loadingInformation = new LoadingInformation();
    Mockito.when(stageDurationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOSD());
    Mockito.when(stageOffsetRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOSO());
    this.loadingInformationServiceImpl.saveLoadingInfoStages(loadingStage, loadingInformation);
    Mockito.verify(loadingInformationRepository).save(Mockito.any());
  }

  private Optional<StageDuration> getOSD() {
    StageDuration defaultDurationOpt = new StageDuration();
    defaultDurationOpt.setId(1L);
    return Optional.of(defaultDurationOpt);
  }

  private Optional<StageOffset> getOSO() {
    StageOffset defaultOffsetOpt = new StageOffset();
    defaultOffsetOpt.setId(1L);
    return Optional.of(defaultOffsetOpt);
  }

  @Test
  void testUpdateIsLoadingInfoCompeteStatus() {
    Long loadingInfoId = 1L;
    boolean status = true;
    this.loadingInformationServiceImpl.updateIsLoadingInfoCompeteStatus(loadingInfoId, status);
  }

  //  @Test
  //  void testUploadPortTideDetails() {
  //    LoadingPlanModels.UploadTideDetailRequest request =
  // LoadingPlanModels.UploadTideDetailRequest.newBuilder().setLoadingId(1L).setTideDetaildata(ByteString.EMPTY).build();
  //    try {
  //      this.loadingInformationServiceImpl.uploadPortTideDetails(request);
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //  }

  @Test
  void testUploadPortTideDetailsFailed() {
    LoadingPlanModels.UploadTideDetailRequest request =
        LoadingPlanModels.UploadTideDetailRequest.newBuilder()
            .setLoadingId(1L)
            .setTideDetaildata(ByteString.EMPTY)
            .build();
    try {
      this.loadingInformationServiceImpl.uploadPortTideDetails(request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDownloadPortTideDetails() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    LoadingPlanModels.DownloadTideDetailRequest request =
        LoadingPlanModels.DownloadTideDetailRequest.newBuilder().setLoadingId(1L).build();
    LoadingPlanModels.DownloadTideDetailStatusReply.Builder builder =
        LoadingPlanModels.DownloadTideDetailStatusReply.newBuilder();
    Mockito.when(
            portTideDetailsRepository.findByLoadingXidAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLPTD());
    Mockito.when(portInfoServiceBlockingStub.getPortInfoByPaging(Mockito.any()))
        .thenReturn(getPR());
    ReflectionTestUtils.setField(
        loadingInformationServiceImpl,
        "portInfoServiceBlockingStub",
        this.portInfoServiceBlockingStub);
    try {
      this.loadingInformationServiceImpl.downloadPortTideDetails(workbook, request, builder);
      assertEquals("SUCCESS", builder.getResponseStatus().getStatus());

    } catch (GenericServiceException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<PortTideDetail> getLPTD() {
    List<PortTideDetail> list = new ArrayList<>();
    PortTideDetail portTideDetail = new PortTideDetail();
    portTideDetail.setIsActive(true);
    portTideDetail.setPortXid(1L);
    portTideDetail.setTideDate(new Date(34));
    portTideDetail.setTideTime(LocalTime.MAX);
    portTideDetail.setTideHeight(new BigDecimal(1));
    list.add(portTideDetail);
    return list;
  }

  private PortInfo.PortReply getPR() {
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setId(1L).setName("1").build();
    list.add(portDetail);
    PortInfo.PortReply portReply = PortInfo.PortReply.newBuilder().addAllPorts(list).build();
    return portReply;
  }

  @Test
  void testDownloadPortTideDetailsFailed() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    LoadingPlanModels.DownloadTideDetailRequest request =
        LoadingPlanModels.DownloadTideDetailRequest.newBuilder().setLoadingId(1L).build();
    LoadingPlanModels.DownloadTideDetailStatusReply.Builder builder =
        LoadingPlanModels.DownloadTideDetailStatusReply.newBuilder();
    Mockito.when(
            portTideDetailsRepository.findByLoadingXidAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenThrow(new RuntimeException("server error"));
    try {
      this.loadingInformationServiceImpl.downloadPortTideDetails(workbook, request, builder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
