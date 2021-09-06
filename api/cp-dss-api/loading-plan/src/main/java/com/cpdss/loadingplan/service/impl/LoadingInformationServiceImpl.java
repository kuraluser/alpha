/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.*;

import com.cpdss.common.constants.RedisConfigConstants;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequestWithPaging;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DownloadTideDetailRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DownloadTideDetailStatusReply.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UploadTideDetailRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.domain.LoadingInfoResponse;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.CargoToppingOffSequenceService;
import com.cpdss.loadingplan.service.LoadingBerthService;
import com.cpdss.loadingplan.service.LoadingDelayService;
import com.cpdss.loadingplan.service.LoadingInformationBuilderService;
import com.cpdss.loadingplan.service.LoadingInformationService;
import com.cpdss.loadingplan.service.LoadingMachineryInUseService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.google.protobuf.ByteString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class LoadingInformationServiceImpl implements LoadingInformationService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;
  @Autowired LoadingInformationBuilderService informationBuilderService;
  @Autowired LoadingBerthDetailsRepository berthDetailsRepository;
  @Autowired LoadingMachineryInUseRepository loadingMachineryInUserRepository;
  @Autowired LoadingBerthDetailsRepository loadingBerthDetailsRepository;

  @Autowired StageOffsetRepository stageOffsetRepository;

  @Autowired StageDurationRepository stageDurationRepository;

  @Autowired ReasonForDelayRepository reasonForDelayRepository;

  @Autowired LoadingDelayRepository loadingDelayRepository;

  @Autowired LoadingBerthService loadingBerthService;
  @Autowired LoadingDelayService loadingDelayService;
  @Autowired LoadingMachineryInUseService loadingMachineryInUseService;
  @Autowired CargoToppingOffSequenceService toppingOffSequenceService;
  @Autowired PortTideDetailsRepository portTideDetailsRepository;
  @Autowired LoadingInstructionRepository loadingInstructionRepository;
  @Autowired LoadingPlanAlgoService loadingPlanAlgoService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @Override
  public LoadingInformation saveLoadingInformationDetail(
      LoadingInformationDetail loadingInformationDetail, LoadingInformation loadingInformation) {
    deleteLoadingInformationOfVessel(
        loadingInformationDetail.getVesselId(), loadingInformationDetail.getLoadablePatternId());

    Optional.ofNullable(loadingInformationDetail.getLoadablePatternId())
        .ifPresent(loadingInformation::setLoadablePatternXId);
    Optional.ofNullable(loadingInformationDetail.getPortId())
        .ifPresent(loadingInformation::setPortXId);
    Optional.ofNullable(loadingInformationDetail.getSynopticalTableId())
        .ifPresent(loadingInformation::setSynopticalTableXId);
    Optional.ofNullable(loadingInformationDetail.getVesselId())
        .ifPresent(loadingInformation::setVesselXId);
    Optional.ofNullable(loadingInformationDetail.getVoyageId())
        .ifPresent(loadingInformation::setVoyageId);
    Optional.ofNullable(loadingInformationDetail.getPortRotationId())
        .ifPresent(loadingInformation::setPortRotationXId);
    Optional<StageOffset> defaultOffsetOpt =
        stageOffsetRepository.findByStageOffsetValAndIsActiveTrue(DEFAULT_STAGE_OFFSET_VALUE);
    Optional<StageDuration> defaultDurationOpt =
        stageDurationRepository.findByDurationAndIsActiveTrue(DEFAULT_STAGE_DURATION_VALUE);
    if (defaultOffsetOpt.isPresent()) {
      loadingInformation.setStageOffset(defaultOffsetOpt.get());
    }
    if (defaultDurationOpt.isPresent()) {
      loadingInformation.setStageDuration(defaultDurationOpt.get());
    }
    loadingInformation.setIsLoadingInfoComplete(false);
    loadingInformation.setIsActive(true);
    Optional<LoadingInformationStatus> pendingStatusOpt;
    try {
      pendingStatusOpt =
          loadingPlanAlgoService.getLoadingInformationStatus(LOADING_INFORMATION_PENDING_ID);
      pendingStatusOpt.ifPresent(
          status -> {
            loadingInformation.setLoadingInformationStatus(status);
            loadingInformation.setArrivalStatus(status);
            loadingInformation.setDepartureStatus(status);
          });
    } catch (GenericServiceException e) {
      log.info("Failed to fetch status with id {}", LOADING_INFORMATION_PENDING_ID);
    }
    return loadingInformationRepository.save(loadingInformation);
  }

  private void deleteLoadingInformationOfVessel(Long vesselId, Long loadablePatternId) {

    List<LoadingInformation> loadingInformations =
        loadingInformationRepository.findByVesselXIdAndLoadablePatternXIdNotAndIsActive(
            vesselId, loadablePatternId, true);
    loadingInformations.forEach(
        loadingInformation -> {
          deleteLoadablePlanDetails(loadingInformation);
          loadingInformationRepository.deleteByLoadingInformationId(loadingInformation.getId());
        });
  }

  @Override
  public void deleteLoadablePlanDetails(LoadingInformation loadingInformation) {
    cargoToppingOffSequenceRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanBallastDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanCommingleDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanQuantityRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanStowageDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadingBerthDetailsRepository.deleteByLoadingInformationId(loadingInformation.getId());
    loadingDelayRepository.deleteByLoadingInformationId(loadingInformation.getId());
    loadingMachineryInUserRepository.deleteByLoadingInformationId(loadingInformation.getId());
  }

  @Override
  public Optional<LoadingInformation> getLoadingInformation(
      Long id, Long vesselId, Long voyageId, Long patternId, Long portRotationId) {
    Optional<LoadingInformation> information = Optional.empty();
    if (id != 0) {
      information = this.loadingInformationRepository.findById(id);
      log.info("Loading Information found for Id {}", id);
      if (information.isPresent()) return information;
    }
    if (vesselId != 0 && voyageId != 0 && portRotationId != 0) {
      information =
          this.loadingInformationRepository
              .findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue(
                  vesselId, voyageId, portRotationId);
      if (information.isPresent()) {
        log.info(
            "Loading Information found Id {}, for Voyage Id {}, Port Rotation Id {}",
            information.get().getId(),
            voyageId,
            portRotationId);
        return information;
      }
    }
    log.info("No data found for Loading Information");
    return information;
  }

  @Override
  public Optional<LoadingInformation> getLoadingInformation(Long id) {
    return this.getLoadingInformation(id, 0L, 0L, 0L, 0L);
  }

  @Override
  public LoadingPlanModels.LoadingInformation getLoadingInformation(
      LoadingPlanModels.LoadingInformationRequest request,
      LoadingPlanModels.LoadingInformation.Builder builder)
      throws GenericServiceException {

    Common.ResponseStatus.Builder responseStatus = Common.ResponseStatus.newBuilder();
    responseStatus.setStatus(FAILED);

    Optional<LoadingInformation> var1 =
        this.getLoadingInformation(
            request.getLoadingPlanId(),
            request.getVesselId(),
            request.getVoyageId(),
            request.getLoadingPatternId(),
            request.getPortRotationId());
    if (!var1.isPresent()) {
      log.info("No Loading Information found for Id {}", request.getLoadingPlanId());
    }

    // Common fields
    var1.ifPresent(v -> builder.setLoadingInfoId(v.getId()));
    var1.ifPresent(v -> builder.setSynopticTableId(v.getSynopticalTableXId()));
    var1.ifPresent(
        v -> {
          if (v.getIsLoadingInfoComplete() != null) {
            builder.setIsLoadingInfoComplete(v.getIsLoadingInfoComplete());
          }
        });
    List<LoadingInstruction> listLoadingInstruction =
        loadingInstructionRepository.getAllLoadingInstructions(
            request.getVesselId(), var1.get().getId(), request.getPortRotationId());
    if (!listLoadingInstruction.isEmpty()) {
      builder.setIsLoadingInstructionsComplete(true);
    } else {
      builder.setIsLoadingInstructionsComplete(false);
    }
    var1.ifPresent(
        v -> {
          if (v.getIsLoadingSequenceGenerated() != null) {
            builder.setIsLoadingSequenceGenerated(v.getIsLoadingSequenceGenerated());
          }
        });
    var1.ifPresent(
        v -> {
          if (v.getIsLoadingPlanGenerated() != null) {
            builder.setIsLoadingPlanGenerated(v.getIsLoadingPlanGenerated());
          }
        });
    Optional.ofNullable(var1.get().getLoadingInformationStatus())
        .ifPresent(status -> builder.setLoadingInfoStatusId(status.getId()));
    // Loading Details
    LoadingPlanModels.LoadingDetails details =
        this.informationBuilderService.buildLoadingDetailsMessage(var1.orElse(null));

    // Loading Rates
    LoadingPlanModels.LoadingRates rates =
        this.informationBuilderService.buildLoadingRateMessage(var1.orElse(null));

    // Loading Berths
    List<LoadingBerthDetail> list1 =
        this.berthDetailsRepository.findAllByLoadingInformationAndIsActiveTrue(var1.orElse(null));
    List<LoadingPlanModels.LoadingBerths> berths =
        this.informationBuilderService.buildLoadingBerthsMessage(list1);

    // Machines in use
    List<LoadingMachineryInUse> list2 =
        this.loadingMachineryInUserRepository.findAllByLoadingInformationAndIsActiveTrue(
            var1.orElse(null));
    List<LoadingPlanModels.LoadingMachinesInUse> machines =
        this.informationBuilderService.buildLoadingMachineryInUseMessage(list2);

    // Stage Min Amount Master
    List<StageOffset> list3 = this.stageOffsetRepository.findAll();
    // Stage Duration Master
    List<StageDuration> list4 = this.stageDurationRepository.findAll();

    // Staging User data and Master data
    LoadingPlanModels.LoadingStages loadingStages =
        this.informationBuilderService.buildLoadingStageMessage(var1.orElse(null), list3, list4);

    // Loading Delay
    List<ReasonForDelay> list5 = this.reasonForDelayRepository.findAll();
    List<LoadingDelay> list6 =
        this.loadingDelayRepository.findAllByLoadingInformationAndIsActiveTrue(var1.orElse(null));
    LoadingPlanModels.LoadingDelay loadingDelay =
        this.informationBuilderService.buildLoadingDelayMessage(list5, list6);

    List<CargoToppingOffSequence> list8 =
        this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(
            var1.orElse(null));
    List<LoadingPlanModels.LoadingToppingOff> toppingOff =
        this.informationBuilderService.buildToppingOffMessage(list8);

    builder.setLoadingDetail(details);
    builder.setLoadingRate(rates);
    builder.addAllLoadingBerths(berths);
    builder.addAllLoadingMachines(machines);
    builder.setLoadingStage(loadingStages);
    builder.setLoadingDelays(loadingDelay);
    builder.addAllToppingOffSequence(toppingOff);
    builder.setResponseStatus(responseStatus.setStatus(SUCCESS));
    return builder.build();
  }

  @Override
  public LoadingInformation saveLoadingInformation(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request)
      throws Exception {
    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(request.getLoadingDetail().getId());
    if (loadingInformationOpt.isPresent()) {
      LoadingInformation loadingInformation = loadingInformationOpt.get();
      informationBuilderService.buildLoadingInfoFromRpcMessage(request, loadingInformation);
      loadingInformationRepository.save(loadingInformation);
      this.updateIsLoadingInfoCompeteStatus(
          loadingInformationOpt.get().getId(), request.getIsLoadingInfoComplete());
      return loadingInformationOpt.get();
    } else {
      throw new Exception(
          "Cannot find loading information with id " + request.getLoadingDetail().getId());
    }
  }

  @Override
  public LoadingInformation saveLoadingInfoRates(
      LoadingPlanModels.LoadingRates source,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response) {
    Optional<LoadingInformation> information = this.getLoadingInformation(source.getId());
    if (information.isPresent()) {
      LoadingInformation var1 = information.get();
      log.info("Save Loading Info, Set Loading Rates");
      if (!source.getLineContentRemaining().isEmpty())
        var1.setLineContentRemaining(new BigDecimal(source.getLineContentRemaining()));

      if (!source.getMaxDeBallastingRate().isEmpty())
        var1.setMaxDeBallastRate(new BigDecimal(source.getMaxDeBallastingRate()));

      if (!source.getMaxLoadingRate().isEmpty())
        var1.setMaxLoadingRate(new BigDecimal(source.getMaxLoadingRate()));

      if (!source.getMinDeBallastingRate().isEmpty())
        var1.setMinDeBallastRate(new BigDecimal(source.getMinDeBallastingRate()));

      if (!source.getReducedLoadingRate().isEmpty())
        var1.setReducedLoadingRate(new BigDecimal(source.getReducedLoadingRate()));

      if (!source.getMinLoadingRate().isEmpty())
        var1.setMinLoadingRate(new BigDecimal(source.getMinLoadingRate()));

      if (!source.getInitialLoadingRate().isEmpty())
        var1.setInitialLoadingRate(new BigDecimal(source.getInitialLoadingRate()));

      if (!source.getNoticeTimeRateReduction().isEmpty())
        var1.setNoticeTimeForRateReduction(Integer.valueOf(source.getNoticeTimeRateReduction()));

      if (!source.getNoticeTimeStopLoading().isEmpty())
        var1.setNoticeTimeForStopLoading(Integer.valueOf(source.getNoticeTimeStopLoading()));

      if (!source.getShoreLoadingRate().isEmpty())
        var1.setShoreLoadingRate(new BigDecimal(source.getShoreLoadingRate()));

      loadingInformationRepository.save(var1);
      return var1;
    }
    return null;
  }

  @Override
  public LoadingInformation saveLoadingInfoBerths(
      List<LoadingPlanModels.LoadingBerths> berths,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException {
    return null;
  }

  @Override
  public LoadingInformation saveLoadingInfoMachines(
      List<LoadingPlanModels.LoadingMachinesInUse> machines,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException {
    return null;
  }

  @Override
  public LoadingInformation saveLoadingInfoDelays(
      List<LoadingPlanModels.LoadingDelay> loadingDelays,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException {
    return null;
  }

  @Override
  public LoadingInformation saveLoadingInfoStages(
      LoadingPlanModels.LoadingStages loadingStage, LoadingInformation loadingInformation) {

    if (loadingStage != null) {
      loadingInformation.setTrackStartEndStage(loadingStage.getTrackStartEndStage());
      loadingInformation.setTrackGradeSwitch(loadingStage.getTrackGradeSwitch());
      if (Optional.ofNullable(loadingStage.getDuration().getId()).isPresent()
          && loadingStage.getDuration().getId() != 0) {
        Optional<StageDuration> stageDurationOpt =
            stageDurationRepository.findByIdAndIsActiveTrue(loadingStage.getDuration().getId());
        if (stageDurationOpt.isPresent()) {
          loadingInformation.setStageDuration(stageDurationOpt.get());
        } else {
          log.error("Duration not found id {}", loadingStage.getDuration().getId());
        }
      }
      if (Optional.of(loadingStage.getOffset().getId()).isPresent()
          && loadingStage.getOffset().getId() != 0) {
        Optional<StageOffset> stageOffsetOpt =
            stageOffsetRepository.findByIdAndIsActiveTrue(loadingStage.getOffset().getId());
        if (stageOffsetOpt.isPresent()) {
          loadingInformation.setStageOffset(stageOffsetOpt.get());
        } else {
          log.info("Offset Not found Id {}", loadingStage.getOffset().getId());
        }
      }
      loadingInformationRepository.save(loadingInformation);
    }
    return null;
  }

  /**
   * For every save request, this status will pass from front end
   *
   * @param loadingInfoId
   * @param status
   */
  @Override
  public void updateIsLoadingInfoCompeteStatus(Long loadingInfoId, boolean status) {
    this.loadingInformationRepository.updateLoadingInformationCompleteStatus(loadingInfoId, status);
  }

  private void buildLoadingInfoResponse(
      LoadingInformation loadingInformation, LoadingInfoResponse loadingInfoResponse) {
    loadingInfoResponse.setLoadingInfoId(loadingInformation.getId());
    loadingInfoResponse.setVesselId(loadingInformation.getVesselXId());
    loadingInfoResponse.setVoyageId(loadingInformation.getVoyageId());
    loadingInfoResponse.setPortRotationId(loadingInformation.getPortRotationXId());
    loadingInfoResponse.setSynopticalTableId(loadingInformation.getSynopticalTableXId());
  }

  private void buildLoadingInformation(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request,
      LoadingInformation loadingInformation)
      throws Exception {
    if (Optional.ofNullable(request.getLoadingStage().getDuration().getId()).isPresent()
        && request.getLoadingStage().getDuration().getId() != 0) {
      Optional<StageDuration> stageDurationOpt =
          stageDurationRepository.findByIdAndIsActiveTrue(
              request.getLoadingStage().getDuration().getId());
      if (stageDurationOpt.isPresent()) {
        loadingInformation.setStageDuration(stageDurationOpt.get());
      } else {
        throw new Exception("Invalid stage duration");
      }
    }

    if (Optional.ofNullable(request.getLoadingStage().getOffset().getId()).isPresent()
        && request.getLoadingStage().getOffset().getId() != 0) {
      Optional<StageOffset> stageOffsetOpt =
          stageOffsetRepository.findByIdAndIsActiveTrue(
              request.getLoadingStage().getOffset().getId());
      if (stageOffsetOpt.isPresent()) {
        loadingInformation.setStageOffset(stageOffsetOpt.get());
      } else {
        throw new Exception("Invalid stage offset");
      }
    }
    loadingInformation.setStartTime(
        StringUtils.isEmpty(request.getLoadingDetail().getStartTime())
            ? null
            : LocalTime.from(TIME_FORMATTER.parse(request.getLoadingDetail().getStartTime())));
    loadingInformation.setFinalTrim(
        StringUtils.isEmpty(request.getLoadingDetail().getTrimAllowed().getFinalTrim())
            ? null
            : new BigDecimal(request.getLoadingDetail().getTrimAllowed().getFinalTrim()));
    loadingInformation.setInitialTrim(
        StringUtils.isEmpty(request.getLoadingDetail().getTrimAllowed().getInitialTrim())
            ? null
            : new BigDecimal(request.getLoadingDetail().getTrimAllowed().getInitialTrim()));
    loadingInformation.setMaximumTrim(
        StringUtils.isEmpty(request.getLoadingDetail().getTrimAllowed().getMaximumTrim())
            ? null
            : new BigDecimal(request.getLoadingDetail().getTrimAllowed().getMaximumTrim()));
    loadingInformation.setLineContentRemaining(
        StringUtils.isEmpty(request.getLoadingRate().getLineContentRemaining())
            ? null
            : new BigDecimal(request.getLoadingRate().getLineContentRemaining()));
    loadingInformation.setMaxDeBallastRate(
        StringUtils.isEmpty(request.getLoadingRate().getMaxDeBallastingRate())
            ? null
            : new BigDecimal(request.getLoadingRate().getMaxDeBallastingRate()));
    loadingInformation.setMaxLoadingRate(
        StringUtils.isEmpty(request.getLoadingRate().getMaxLoadingRate())
            ? null
            : new BigDecimal(request.getLoadingRate().getMaxLoadingRate()));
    loadingInformation.setMinDeBallastRate(
        StringUtils.isEmpty(request.getLoadingRate().getMinDeBallastingRate())
            ? null
            : new BigDecimal(request.getLoadingRate().getMinDeBallastingRate()));
    loadingInformation.setReducedLoadingRate(
        StringUtils.isEmpty(request.getLoadingRate().getReducedLoadingRate())
            ? null
            : new BigDecimal(request.getLoadingRate().getReducedLoadingRate()));
    loadingInformation.setMinLoadingRate(
        StringUtils.isEmpty(request.getLoadingRate().getMinLoadingRate())
            ? null
            : new BigDecimal(request.getLoadingRate().getMinLoadingRate()));
    loadingInformation.setInitialLoadingRate(
        StringUtils.isEmpty(request.getLoadingRate().getInitialLoadingRate())
            ? null
            : new BigDecimal(request.getLoadingRate().getInitialLoadingRate()));
    loadingInformation.setNoticeTimeForRateReduction(
        StringUtils.isEmpty(request.getLoadingRate().getNoticeTimeRateReduction())
            ? null
            : Integer.valueOf(request.getLoadingRate().getNoticeTimeRateReduction()));
    loadingInformation.setNoticeTimeForStopLoading(
        StringUtils.isEmpty(request.getLoadingRate().getNoticeTimeStopLoading())
            ? null
            : Integer.valueOf(request.getLoadingRate().getNoticeTimeStopLoading()));
    loadingInformation.setTrackGradeSwitch(request.getLoadingStage().getTrackGradeSwitch());
    loadingInformation.setTrackStartEndStage(request.getLoadingStage().getTrackStartEndStage());

    if (request.getLoadingBerthsCount() > 0) {
      loadingInformation.setIsLoadingInfoComplete(true);
    } else {
      loadingInformation.setIsLoadingInfoComplete(false);
    }
  }

  /**
   * upload Port Tide Details to DB.
   *
   * @param request - UploadTideDetailRequest.
   * @throws GenericServiceException - throws GenericServiceException from the method.
   */
  @Override
  public void uploadPortTideDetails(UploadTideDetailRequest request)
      throws GenericServiceException {

    try {
      ByteString tideDetaildata = request.getTideDetaildata();
      Map<Long, String> portDetails = getPortDetailsFromPortService();
      InputStream bin = new ByteArrayInputStream(tideDetaildata.toByteArray());
      Workbook workbook = WorkbookFactory.create(bin);
      Sheet sheetAt = workbook.getSheet(SHEET);
      Iterator<Row> rowIterator = sheetAt.iterator();
      if (rowIterator.hasNext()) {
        rowIterator.next();
      }
      List<PortTideDetail> tideDetails = new ArrayList<>();
      while (rowIterator.hasNext()) {
        PortTideDetail tideDetail = new PortTideDetail();
        tideDetail.setLoadingXid(request.getLoadingId());
        tideDetail.setIsActive(true);
        Row row = rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        for (int rowCell = 0; rowCell <= 3; rowCell++) {
          Cell cell = cellIterator.next();
          CellType cellType = cell.getCellType();
          // fetch String value from excel
          if (rowCell == 0) {
            if (!cellType.equals(CellType.STRING)) {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_PORT_NAME_INVALID);
            }
            Optional<Long> findFirst =
                portDetails.entrySet().stream()
                    .filter(e -> cell.getStringCellValue().equalsIgnoreCase(e.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst();
            if (!findFirst.isPresent()) {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_PORT_NAME_INVALID);
            }
            tideDetail.setPortXid(findFirst.get());
          }
          // fetch Date value from excel
          if (rowCell == 1) {
            if (cellType.equals(CellType.NUMERIC)) {
              double numberValue = cell.getNumericCellValue();
              if (DateUtil.isCellDateFormatted(cell)) {
                tideDetail.setTideDate(DateUtil.getJavaDate(numberValue));
              } else {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_DATE_INVALID);
              }
            } else if (cellType.equals(CellType.STRING)) {
              if (!cell.getStringCellValue().matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_DATE_INVALID);
              }
              tideDetail.setTideDate(
                  new SimpleDateFormat(DATE_FORMAT).parse(cell.getStringCellValue()));
            } else {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_DATE_INVALID);
            }
          }
          // fetch Time value from excel
          if (rowCell == 2) {
            if (cellType.equals(CellType.NUMERIC)) {
              if (DateUtil.isCellDateFormatted(cell)) {
                if (cell.getLocalDateTimeCellValue().toLocalTime().equals(LocalTime.of(0, 0))) {
                  throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
                }
                tideDetail.setTideTime(cell.getLocalDateTimeCellValue().toLocalTime());
              } else {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
              }
            } else if (cellType.equals(CellType.STRING)) {
              if (!cell.getStringCellValue().matches("([0-9]{2}):([0-9]{2})")) {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
              }
              tideDetail.setTideTime(LocalTime.parse(cell.getStringCellValue()));
            } else {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
            }
          }
          // fetch Double value from excel
          if (rowCell == 3) {
            if (!cellType.equals(CellType.NUMERIC)) {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_HEIGHT_INVALID);
            }
            tideDetail.setTideHeight(
                new BigDecimal(cell.getNumericCellValue(), MathContext.DECIMAL64));
          }
        }
        tideDetails.add(tideDetail);
      }
      portTideDetailsRepository.updatePortDetailActiveState(request.getLoadingId());
      portTideDetailsRepository.saveAll(tideDetails);
    } catch (IllegalStateException e) {
      throw new GenericServiceException(e.getMessage(), e.getMessage(), HttpStatusCode.BAD_REQUEST);
    } catch (Exception e) {
      throw new GenericServiceException(
          e.getMessage(),
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * download Port Tide Details template
   *
   * @param workbook - XSSFWorkbook
   * @param request - DownloadTideDetailRequest
   * @param builder - Builder class
   * @throws GenericServiceException - throws GenericServiceException from the method.
   */
  @Override
  public void downloadPortTideDetails(
      XSSFWorkbook workbook, DownloadTideDetailRequest request, Builder builder)
      throws GenericServiceException, IOException {
    try {
      XSSFSheet spreadsheet = workbook.createSheet(SHEET);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      int rowNo = 0;
      XSSFRow titleRow = spreadsheet.createRow(rowNo);
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      XSSFFont font = workbook.createFont();
      font.setFontName(PORT_TITLE_FONT_STYLE);
      font.setFontHeight(PORT_TITLE_FONT_HEIGHT);
      font.setBold(true);
      cellStyle.setFont(font);
      for (int columnNo = 0; columnNo < PORT_EXCEL_TEMPLATE_TITLES.size(); columnNo++) {
        spreadsheet.setColumnWidth(columnNo, 17 * 256);
        XSSFCell titleCell = titleRow.createCell(columnNo);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue(PORT_EXCEL_TEMPLATE_TITLES.get(columnNo));
      }
      long loadingId = request.getLoadingId();
      if (loadingId != 0) {
        List<PortTideDetail> list =
            portTideDetailsRepository.findByLoadingXidAndIsActive(request.getLoadingId(), true);
        if (!list.isEmpty()) {
          Map<Long, String> portsMap = getPortDetailsFromPortService();
          for (rowNo = 0; rowNo < list.size(); rowNo++) {
            XSSFRow row = spreadsheet.createRow(rowNo + 1);
            for (int columnNo = 0; columnNo < PORT_EXCEL_TEMPLATE_TITLES.size(); columnNo++) {
              XSSFCell cell = row.createCell(columnNo);
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(PORT)) {
                cell.setCellType(CellType.STRING);
                cell.setCellValue(portsMap.get(list.get(rowNo).getPortXid()));
              }
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(TIDE_DATE)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(
                    new SimpleDateFormat(DATE_FORMAT).format(list.get(rowNo).getTideDate()));
              }
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(TIDE_TIME)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(list.get(rowNo).getTideTime().toString());
              }
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(TIDE_HEIGHT)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(list.get(rowNo).getTideHeight().doubleValue());
              }
            }
          }
        }
      }
      workbook.write(byteArrayOutputStream);
      byte[] bytes = byteArrayOutputStream.toByteArray();
      builder
          .setData(ByteString.copyFrom(bytes))
          .setSize(bytes.length)
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setStatus(SUCCESS)
                  .setCode(HttpStatusCode.OK.getReasonPhrase())
                  .build())
          .build();
      byteArrayOutputStream.close();
    } catch (Exception e) {
      throw new GenericServiceException(
          e.getMessage(),
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * To fetch port details from port service.
   *
   * @return Map of Key value pair
   */
  private Map<Long, String> getPortDetailsFromPortService() {
    PortRequestWithPaging redisRequest =
        PortRequestWithPaging.newBuilder()
            .setOffset(RedisConfigConstants.OFFSET_VAL)
            .setLimit(RedisConfigConstants.PAGE_COUNT)
            .build();
    PortReply reply = portInfoServiceBlockingStub.getPortInfoByPaging(redisRequest);
    List<PortDetail> portsList = reply.getPortsList();
    Map<Long, String> portsMap = new HashMap<>();
    if (!portsList.isEmpty()) {
      portsMap =
          portsList.stream().collect(Collectors.toMap(map -> map.getId(), map -> map.getName()));
    }
    return portsMap;
  }
}
