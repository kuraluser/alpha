/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.AlgoErrors;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.PumpType;
import com.cpdss.common.generated.VesselInfo.VesselIdRequest;
import com.cpdss.common.generated.VesselInfo.VesselPump;
import com.cpdss.common.generated.VesselInfo.VesselPumpsResponse;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.EductorOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.AlgoError;
import com.cpdss.gateway.domain.loadingplan.sequence.Ballast;
import com.cpdss.gateway.domain.loadingplan.sequence.BallastPump;
import com.cpdss.gateway.domain.loadingplan.sequence.Cargo;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoLoadingRate;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoStage;
import com.cpdss.gateway.domain.loadingplan.sequence.Eduction;
import com.cpdss.gateway.domain.loadingplan.sequence.EductionOperation;
import com.cpdss.gateway.domain.loadingplan.sequence.Event;
import com.cpdss.gateway.domain.loadingplan.sequence.FlowRate;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlan;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceStabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.Pump;
import com.cpdss.gateway.domain.loadingplan.sequence.PumpCategory;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LoadingSequenceService {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoGrpcService;

  @Autowired private LoadingPlanGrpcService loadingPlanGrpcService;

  public void buildLoadingSequence(
      Long vesselId, LoadingSequenceReply reply, LoadingSequenceResponse response)
      throws GenericServiceException {
    Map<Long, VesselTankDetail> vesselTankMap = this.getVesselTanks(vesselId);
    Set<Long> cargoNominationIds =
        reply.getLoadingSequencesList().stream()
            .map(sequence -> sequence.getCargoNominationId())
            .collect(Collectors.toSet());
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);

    List<LoadablePlanBallastDetails> ballastDetails = new ArrayList<>();
    ballastDetails.addAll(
        loadingPlanGrpcService.fetchLoadablePlanBallastDetails(
            reply.getLoadablePatternId(), reply.getPortRotationId()));
    List<Cargo> cargos = new ArrayList<Cargo>();
    List<Ballast> ballasts = new ArrayList<Ballast>();
    List<BallastPump> ballastPumps = new ArrayList<BallastPump>();
    List<BallastPump> gravityList = new ArrayList<BallastPump>();
    BallastPump gravity = new BallastPump();
    List<CargoLoadingRate> cargoLoadingRates = new ArrayList<>();
    List<LoadingRate> loadingRates = new ArrayList<LoadingRate>();
    Set<Long> stageTickPositions = new LinkedHashSet<Long>();
    List<StabilityParam> stabilityParams = new ArrayList<StabilityParam>();
    Set<TankCategory> cargoTankCategories = new LinkedHashSet<TankCategory>();
    Set<TankCategory> ballastTankCategories = new LinkedHashSet<TankCategory>();
    List<CargoStage> cargoStages = new ArrayList<CargoStage>();
    List<EductionOperation> ballastEduction = new ArrayList<EductionOperation>();
    inititalizeStabilityParams(stabilityParams);

    PortDetail portDetail = getPortInfo(reply.getPortId());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    try {
      response.setMinXAxisValue(
          StringUtils.isEmpty(reply.getStartDate())
              ? new Date().toInstant().toEpochMilli()
              : sdf.parse(reply.getStartDate()).toInstant().toEpochMilli());
      if (!StringUtils.isEmpty(portDetail.getTimezoneOffsetVal())) {
        response.setMinXAxisValue(
            response.getMinXAxisValue()
                + (long)
                    (Float.valueOf(portDetail.getTimezoneOffsetVal()).floatValue()
                        * 60
                        * 60
                        * 1000));
      }
    } catch (ParseException e) {
      e.printStackTrace();
      response.setMinXAxisValue(new Date().toInstant().toEpochMilli());
    }

    Long portEta = response.getMinXAxisValue();
    Integer start = 0;
    Integer temp = 0;
    Long currentCargoNomId = 0L;
    AtomicInteger stageNumber = new AtomicInteger();

    log.info("Populating Loading Sequences");
    for (LoadingSequence loadingSequence : reply.getLoadingSequencesList()) {
      if (loadingSequence.getStageName().equalsIgnoreCase("initialCondition")) {
        start = loadingSequence.getStartTime();
      }

      if (!currentCargoNomId.equals(loadingSequence.getCargoNominationId())) {
        stageNumber = new AtomicInteger();
      }

      currentCargoNomId = loadingSequence.getCargoNominationId();
      for (LoadingPlanPortWiseDetails portWiseDetails :
          loadingSequence.getLoadingPlanPortWiseDetailsList()) {
        List<LoadingPlanTankDetails> filteredStowage =
            portWiseDetails.getLoadingPlanStowageDetailsList().stream()
                .filter(
                    stowage ->
                        (stowage.getCargoNominationId() == loadingSequence.getCargoNominationId())
                            || (stowage.getCargoNominationId() == 0))
                .collect(Collectors.toList());
        for (LoadingPlanTankDetails stowage : filteredStowage) {
          // Adding cargos
          temp =
              this.buildCargoSequence(
                  stowage,
                  vesselTankMap,
                  cargoNomDetails,
                  portEta,
                  start,
                  portWiseDetails,
                  cargos,
                  cargoTankCategories);
        }

        List<LoadingPlanCommingleDetails> filteredComingleDetails =
            portWiseDetails.getLoadingPlanCommingleDetailsList().stream()
                .filter(
                    commingle ->
                        (commingle.getCargoNomination1Id()
                                == loadingSequence.getCargoNominationId())
                            || (commingle.getCargoNomination2Id()
                                == loadingSequence.getCargoNominationId()))
                .collect(Collectors.toList());

        for (LoadingPlanCommingleDetails commingle : filteredComingleDetails) {
          // Adding commingle cargos
          temp =
              this.buildCommingleSequence(
                  commingle,
                  currentCargoNomId,
                  vesselTankMap,
                  cargoNomDetails,
                  portEta,
                  start,
                  portWiseDetails,
                  cargos,
                  cargoTankCategories);
        }

        for (LoadingPlanTankDetails ballast : portWiseDetails.getLoadingPlanBallastDetailsList()) {
          // Adding ballasts
          temp =
              this.buildBallastSequence(
                  ballast,
                  vesselTankMap,
                  portEta,
                  start,
                  portWiseDetails,
                  ballastDetails,
                  ballasts,
                  ballastTankCategories);
        }

        addCargoStage(
            portWiseDetails,
            cargoNomDetails,
            loadingSequence.getCargoNominationId(),
            stageNumber,
            portEta,
            start,
            temp,
            cargoStages);

        addCommingleCargoStage(
            portWiseDetails,
            cargoNomDetails,
            loadingSequence.getCargoNominationId(),
            stageNumber,
            portEta,
            start,
            temp,
            cargoStages);

        start = temp;
        stageTickPositions.add(portEta + (temp * 60 * 1000));
      }

      Integer loadEnd = temp - (temp % (reply.getInterval() * 60)) + (reply.getInterval() * 60);
      response.setMaxXAxisValue(portEta + (loadEnd * 60 * 1000));
      response.setInterval(reply.getInterval());
      // Adding cargo loading rates
      this.buildCargoLoadingRates(loadingSequence, portEta, stageTickPositions, cargoLoadingRates);

      // Adding ballast pumps
      loadingSequence
          .getBallastOperationsList()
          .forEach(
              operation -> {
                BallastPump ballastPump = new BallastPump();
                buildBallastPump(operation, portEta, ballastPump);
                if (ballastPump.getPumpId() == 0L) {
                  gravityList.add(ballastPump);
                } else {
                  ballastPumps.add(ballastPump);
                }
              });
      loadingRates.addAll(loadingSequence.getLoadingRatesList());

      buildEduction(loadingSequence, portEta, ballastEduction);
    }

    if (gravityList.size() > 0) {
      gravity.setPumpId(0L);
      gravity.setQuantityM3(null);
      gravity.setRate(null);
      gravity.setStart(gravityList.get(0).getStart());
      gravity.setEnd(gravityList.get(gravityList.size() - 1).getEnd());
    }

    this.updateCargoLoadingRateIntervals(cargoLoadingRates, stageTickPositions);
    this.buildStabilityParamSequence(reply, portEta, stabilityParams);
    this.buildFlowRates(loadingRates, vesselTankMap, portEta, response);
    this.buildBallastPumpCategories(vesselId, response, ballastPumps);
    this.removeEmptyBallasts(ballasts, ballastTankCategories);
    this.removeEmptyCargos(cargos, cargoTankCategories);

    response.setCargos(cargos);
    response.setBallasts(ballasts);
    response.setBallastPumps(ballastPumps);
    response.setGravity(gravity);
    response.setCargoLoadingRates(cargoLoadingRates);
    response.setStageTickPositions(stageTickPositions);
    response.setStabilityParams(stabilityParams);
    response.setCargoTankCategories(
        cargoTankCategories.stream()
            .sorted(Comparator.comparing(TankCategory::getDisplayOrder))
            .collect(Collectors.toList()));
    List<VesselTankDetail> vesselTankDetails = new ArrayList<>(vesselTankMap.values());
    response.setBallastTankCategories(
        ballastTankCategories.stream()
            .sorted(
                Comparator.comparing(
                    ballast -> vesselTankDetails.indexOf(vesselTankMap.get(ballast.getId()))))
            .collect(Collectors.toList()));
    response.setCargoStages(cargoStages);
    response.setBallastEduction(ballastEduction);
  }

  /**
   * @param loadingSequence
   * @param portEta
   * @param ballastEduction
   */
  private void buildEduction(
      LoadingSequence loadingSequence, Long portEta, List<EductionOperation> ballastEductions) {
    EductorOperation eductorOperation = loadingSequence.getEductorOperation();
    if (eductorOperation.getEndTime() != 0) {
      EductionOperation ballastEduction = new EductionOperation();
      if (!eductorOperation.getPumpsUsed().isEmpty()) {
        ballastEduction.setPumpSelected(
            List.of(eductorOperation.getPumpsUsed().split(",")).stream()
                .map(pumpId -> Long.valueOf(pumpId))
                .collect(Collectors.toList()));
      }
      if (!eductorOperation.getTanksUsed().isEmpty()) {
        ballastEduction.setTanks(
            List.of(eductorOperation.getTanksUsed().split(",")).stream()
                .map(tankId -> Long.valueOf(tankId))
                .collect(Collectors.toList()));
      }
      ballastEduction.setTimeEnd(portEta + (eductorOperation.getEndTime() * 60 * 1000));
      ballastEduction.setTimeStart(portEta + (eductorOperation.getStartTime() * 60 * 1000));
      ballastEductions.add(ballastEduction);
    }
  }

  /**
   * @param portWiseDetails
   * @param cargoNomDetails
   * @param cargoNominationId
   * @param stageNumber
   * @param portEta
   * @param start
   * @param temp
   * @param cargoStages
   */
  private void addCommingleCargoStage(
      LoadingPlanPortWiseDetails portWiseDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      long cargoNominationId,
      AtomicInteger stageNumber,
      Long portEta,
      Integer start,
      Integer end,
      List<CargoStage> cargoStages) {
    CargoStage cargoStage = new CargoStage();
    if (portWiseDetails.getLoadingPlanCommingleDetailsCount() > 0) {
      List<Cargo> cargos = new ArrayList<Cargo>();
      Cargo cargo = new Cargo();
      CargoNominationDetail cargoNomination = cargoNomDetails.get(cargoNominationId);
      if (cargoNomination != null) {
        cargo.setName(cargoNomination.getCargoName());
        cargo.setCargoId(cargoNomination.getCargoId());
        cargo.setAbbreviation(cargoNomination.getAbbreviation());
        cargo.setCargoNominationId(cargoNomination.getId());
        cargo.setColor(cargoNomination.getColor());
      }
      cargo.setApi(
          StringUtils.isEmpty(cargoNomination.getApi())
              ? null
              : new BigDecimal(cargoNomination.getApi()));
      BigDecimal total =
          portWiseDetails.getLoadingPlanCommingleDetailsList().stream()
              .filter(
                  commingle ->
                      ((commingle.getCargoNomination1Id() == cargoNominationId)
                              || (commingle.getCargoNomination2Id() == cargoNominationId))
                          && !StringUtils.isEmpty(commingle.getQuantityMT()))
              .map(commingle -> new BigDecimal(commingle.getQuantityMT()))
              .reduce(
                  new BigDecimal(0),
                  (val1, val2) -> {
                    return val1.add(val2);
                  });
      cargo.setQuantity(total);
      cargos.add(cargo);
      cargoStage.setName("Stage " + stageNumber.incrementAndGet());
      cargoStage.setStart(portEta + (start * 60 * 1000));
      cargoStage.setEnd(portEta + (end * 60 * 1000));
      cargoStage.setCargos(cargos);
      cargoStages.add(cargoStage);
    }
  }

  /**
   * @param commingle
   * @param currentCargoNominationId
   * @param vesselTankMap
   * @param cargoNomDetails
   * @param portEta
   * @param start
   * @param portWiseDetails
   * @param cargos
   * @param cargoTankCategories
   * @return
   */
  private Integer buildCommingleSequence(
      LoadingPlanCommingleDetails commingle,
      Long currentCargoNominationId,
      Map<Long, VesselTankDetail> vesselTankMap,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long portEta,
      Integer start,
      LoadingPlanPortWiseDetails portWiseDetails,
      List<Cargo> cargos,
      Set<TankCategory> cargoTankCategories) {
    Cargo cargo = new Cargo();
    Optional<VesselTankDetail> tankDetailOpt =
        Optional.ofNullable(vesselTankMap.get(commingle.getTankId()));
    Integer end =
        buildCommingleCargo(
            commingle,
            currentCargoNominationId,
            cargoNomDetails,
            cargo,
            portEta,
            start,
            portWiseDetails.getTime());
    buildCommingleCargoTankCategory(commingle, tankDetailOpt, cargoTankCategories);
    tankDetailOpt.ifPresent(tank -> cargo.setTankName(tank.getShortName()));
    cargos.add(cargo);
    return end;
  }

  /**
   * @param commingle
   * @param cargoNomDetails
   * @param currentCargoNominationId
   * @param cargo
   * @param portEta
   * @param start
   * @param time
   * @return
   */
  private Integer buildCommingleCargo(
      LoadingPlanCommingleDetails commingle,
      Long currentCargoNominationId,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Cargo cargo,
      Long portEta,
      Integer start,
      int end) {
    CargoNominationDetail cargoNomination = null;
    if (currentCargoNominationId.equals(commingle.getCargoNomination1Id())
        || currentCargoNominationId.equals(commingle.getCargoNomination2Id())) {
      cargo.setCargoNominationId(currentCargoNominationId);
      cargoNomination = cargoNomDetails.get(currentCargoNominationId);
    }

    cargo.setQuantity(
        StringUtils.isEmpty(commingle.getQuantityMT())
            ? null
            : new BigDecimal(commingle.getQuantityMT()));
    cargo.setTankId(commingle.getTankId());
    cargo.setUllage(
        StringUtils.isEmpty(commingle.getUllage()) ? null : new BigDecimal(commingle.getUllage()));
    if (cargoNomination != null) {
      cargo.setCargoId(cargoNomination.getCargoId());
      cargo.setColor(cargoNomination.getColor());
      cargo.setName(cargoNomination.getCargoName());
      cargo.setAbbreviation(cargoNomination.getAbbreviation());
    }
    cargo.setStart(portEta + (start * 60 * 1000));
    cargo.setEnd(portEta + (end * 60 * 1000));
    cargo.setApi(
        StringUtils.isEmpty(commingle.getApi()) ? null : new BigDecimal(commingle.getApi()));
    cargo.setIsCommingle(true);
    return end;
  }

  /**
   * @param commingle
   * @param tankDetailOpt
   * @param cargoTankCategories
   */
  private void buildCommingleCargoTankCategory(
      LoadingPlanCommingleDetails commingle,
      Optional<VesselTankDetail> tankDetailOpt,
      Set<TankCategory> cargoTankCategories) {
    TankCategory tankCategory = new TankCategory();
    tankDetailOpt.ifPresent(
        tank -> {
          tankCategory.setTankName(tank.getShortName());
          tankCategory.setDisplayOrder(tank.getTankDisplayOrder());
        });
    if (cargoTankCategories.stream()
        .anyMatch(cargo -> cargo.getId().equals(commingle.getTankId()))) {
      cargoTankCategories.removeIf(cargo -> cargo.getId().equals(commingle.getTankId()));
    }
    tankCategory.setId(commingle.getTankId());
    tankCategory.setQuantity(
        StringUtils.isEmpty(commingle.getQuantityMT())
            ? null
            : new BigDecimal(commingle.getQuantityMT()));
    tankCategory.setUllage(
        StringUtils.isEmpty(commingle.getUllage()) ? null : new BigDecimal(commingle.getUllage()));
    cargoTankCategories.add(tankCategory);
  }

  /**
   * @param cargos
   * @param cargoTankCategories
   */
  private void removeEmptyCargos(List<Cargo> cargos, Set<TankCategory> cargoTankCategories) {
    Set<Long> tankIds = cargos.stream().map(Cargo::getTankId).collect(Collectors.toSet());
    Optional<Long> cargoStartOpt =
        cargos.stream().map(cargo -> cargo.getStart()).sorted().findFirst();
    tankIds.forEach(
        tankId -> {
          List<Cargo> tankWiseCargos =
              cargos.stream()
                  .filter(cargo -> cargo.getTankId().equals(tankId))
                  .collect(Collectors.toList());
          if (tankWiseCargos.size() < 2) {
            cargos.removeIf(cargo -> cargo.getTankId().equals(tankId));
            cargoTankCategories.removeIf(category -> category.getId().equals(tankId));
          } else if ((tankWiseCargos.get(0).getQuantity().compareTo(BigDecimal.ZERO) == 0)
              && (tankWiseCargos.get(1).getQuantity().compareTo(BigDecimal.ZERO) == 0)) {
            cargos.removeIf(
                cargo ->
                    cargo.getTankId().equals(tankId)
                        && (cargo.getQuantity().compareTo(BigDecimal.ZERO) == 0)
                        && !cargo.getStart().equals(cargoStartOpt.get()));
            if (tankWiseCargos.get(1).getStart().equals(cargoStartOpt.get())) {
              cargos.remove(tankWiseCargos.get(1));
            }
          }

          tankWiseCargos =
              cargos.stream()
                  .filter(cargo -> cargo.getTankId().equals(tankId))
                  .collect(Collectors.toList());
          if (tankWiseCargos.size() < 2) {
            cargos.removeIf(cargo -> cargo.getTankId().equals(tankId));
            cargoTankCategories.removeIf(category -> category.getId().equals(tankId));
          }
        });
  }

  /**
   * @param ballasts
   * @param ballastTankCategories
   */
  private void removeEmptyBallasts(
      List<Ballast> ballasts, Set<TankCategory> ballastTankCategories) {
    Set<Long> tankIds = ballasts.stream().map(Ballast::getTankId).collect(Collectors.toSet());
    tankIds.forEach(
        tankId -> {
          if (ballasts.stream()
              .filter(ballast -> ballast.getTankId().equals(tankId))
              .allMatch(ballast -> ballast.getSounding().compareTo(BigDecimal.ZERO) == 0)) {
            ballasts.removeIf(ballast -> ballast.getTankId().equals(tankId));
            ballastTankCategories.removeIf(category -> category.getId().equals(tankId));
          }
        });
  }

  /** @param stabilityParams */
  private void inititalizeStabilityParams(List<StabilityParam> stabilityParams) {
    StabilityParam foreDraft = new StabilityParam();
    foreDraft.setName("fore_draft");
    foreDraft.setData(new ArrayList<>());
    StabilityParam aftDraft = new StabilityParam();
    aftDraft.setName("aft_draft");
    aftDraft.setData(new ArrayList<>());
    StabilityParam trim = new StabilityParam();
    trim.setName("trim");
    trim.setData(new ArrayList<>());
    StabilityParam ukc = new StabilityParam();
    ukc.setName("ukc");
    ukc.setData(new ArrayList<>());
    StabilityParam gm = new StabilityParam();
    gm.setName("gm");
    gm.setData(new ArrayList<>());
    StabilityParam bm = new StabilityParam();
    bm.setName("bm");
    bm.setData(new ArrayList<>());
    StabilityParam sf = new StabilityParam();
    sf.setName("sf");
    sf.setData(new ArrayList<>());
    StabilityParam gom = new StabilityParam();
    gom.setName("gomValue");
    gom.setData(new ArrayList<>());
    stabilityParams.addAll(Arrays.asList(foreDraft, aftDraft, trim, ukc, gm, sf, bm, gom));
  }

  private void addCargoStage(
      LoadingPlanPortWiseDetails portWiseDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long cargoNomId,
      AtomicInteger stageNumber,
      Long portEta,
      Integer start,
      Integer end,
      List<CargoStage> cargoStages) {
    CargoStage cargoStage = new CargoStage();
    if (portWiseDetails.getLoadingPlanStowageDetailsCount() > 0) {
      List<Cargo> cargos = new ArrayList<Cargo>();
      Cargo cargo = new Cargo();
      CargoNominationDetail cargoNomination = cargoNomDetails.get(cargoNomId);
      if (cargoNomination != null) {
        cargo.setName(cargoNomination.getCargoName());
        cargo.setCargoId(cargoNomination.getCargoId());
        cargo.setAbbreviation(cargoNomination.getAbbreviation());
        cargo.setCargoNominationId(cargoNomination.getId());
        cargo.setColor(cargoNomination.getColor());
      }
      cargo.setApi(
          StringUtils.isEmpty(cargoNomination.getApi())
              ? null
              : new BigDecimal(cargoNomination.getApi()));
      BigDecimal total =
          portWiseDetails.getLoadingPlanStowageDetailsList().stream()
              .filter(
                  stowage ->
                      (stowage.getCargoNominationId() == cargoNomId)
                          && !StringUtils.isEmpty(stowage.getQuantity()))
              .map(stowage -> new BigDecimal(stowage.getQuantity()))
              .reduce(
                  new BigDecimal(0),
                  (val1, val2) -> {
                    return val1.add(val2);
                  });
      cargo.setQuantity(total);
      cargos.add(cargo);
      cargoStage.setName("Stage " + stageNumber.incrementAndGet());
      cargoStage.setStart(portEta + (start * 60 * 1000));
      cargoStage.setEnd(portEta + (end * 60 * 1000));
      cargoStage.setCargos(cargos);
      cargoStages.add(cargoStage);
    }
  }

  /**
   * Adjusts interval of cargo loading rate based on stage tick positions.
   *
   * @param cargoLoadingRates
   * @param stageTickPositions
   */
  private void updateCargoLoadingRateIntervals(
      List<CargoLoadingRate> cargoLoadingRates, Set<Long> stageTickPositions) {
    cargoLoadingRates.forEach(
        cargoLoadingRate -> {
          Optional<Long> startOpt =
              stageTickPositions.stream()
                  .filter(pos -> pos < cargoLoadingRate.getStartTime())
                  .sorted(Comparator.reverseOrder())
                  .findFirst();
          Optional<Long> endOpt =
              stageTickPositions.stream()
                  .filter(pos -> pos > cargoLoadingRate.getEndTime())
                  .findFirst();
          startOpt.ifPresent(cargoLoadingRate::setStartTime);
          endOpt.ifPresent(cargoLoadingRate::setEndTime);
        });
  }

  private void buildStabilityParamSequence(
      LoadingSequenceReply reply, Long portEta, List<StabilityParam> stabilityParams) {
    List<LoadingPlanStabilityParameters> params = reply.getLoadingSequenceStabilityParametersList();
    log.info("Populating stability parameters");
    params.forEach(
        param -> {
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("fore_draft"))
              .forEach(
                  foreDraft ->
                      foreDraft
                          .getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getForeDraft())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("aft_draft"))
              .forEach(
                  aftDraft ->
                      aftDraft
                          .getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getAftDraft())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("bm"))
              .forEach(
                  bm ->
                      bm.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getBm())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("sf"))
              .forEach(
                  sf ->
                      sf.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getSf())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("trim"))
              .forEach(
                  sf ->
                      sf.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getTrim())));
          stabilityParams.stream()
              .filter(stabilityParam -> stabilityParam.getName().equals("gomValue"))
              .forEach(
                  gom ->
                      gom.getData()
                          .add(
                              Arrays.asList(
                                  portEta + (param.getTime() * 60 * 1000), param.getGomValue())));
        });
  }

  private Integer buildBallastSequence(
      LoadingPlanTankDetails ballast,
      Map<Long, VesselTankDetail> vesselTankMap,
      Long portEta,
      Integer start,
      LoadingPlanPortWiseDetails portWiseDetails,
      List<LoadablePlanBallastDetails> ballastDetails,
      List<Ballast> ballasts,
      Set<TankCategory> ballastTankCategories) {
    Ballast ballastDto = new Ballast();
    Optional<VesselTankDetail> tankDetailOpt =
        Optional.ofNullable(vesselTankMap.get(ballast.getTankId()));
    Optional<LoadablePlanBallastDetails> ballastDetailsOpt =
        ballastDetails.stream()
            .filter(
                details ->
                    (details.getTankId() == ballast.getTankId())
                        && !StringUtils.isEmpty(details.getColorCode()))
            .findFirst();
    Integer end = buildBallast(ballast, ballastDto, portEta, start, portWiseDetails.getTime());
    TankCategory tankCategory = new TankCategory();
    tankCategory.setId(ballast.getTankId());
    tankDetailOpt.ifPresent(
        tank -> {
          ballastDto.setTankName(tank.getShortName());
          tankCategory.setTankName(tank.getShortName());
          tankCategory.setDisplayOrder(tank.getTankDisplayOrder());
        });
    ballastDetailsOpt.ifPresent(details -> ballastDto.setColor(details.getColorCode()));
    ballastTankCategories.add(tankCategory);
    ballasts.add(ballastDto);
    return end;
  }

  private Integer buildCargoSequence(
      LoadingPlanTankDetails stowage,
      Map<Long, VesselTankDetail> vesselTankMap,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long portEta,
      Integer start,
      LoadingPlanPortWiseDetails portWiseDetails,
      List<Cargo> cargos,
      Set<TankCategory> cargoTankCategories) {
    Cargo cargo = new Cargo();
    Optional<VesselTankDetail> tankDetailOpt =
        Optional.ofNullable(vesselTankMap.get(stowage.getTankId()));
    CargoNominationDetail cargoNomination = cargoNomDetails.get(stowage.getCargoNominationId());
    Integer end =
        buildCargo(stowage, cargo, cargoNomination, portEta, start, portWiseDetails.getTime());
    buildCargoTankCategory(stowage, tankDetailOpt, cargoTankCategories);
    tankDetailOpt.ifPresent(tank -> cargo.setTankName(tank.getShortName()));
    cargos.add(cargo);
    return end;
  }

  private void buildCargoLoadingRates(
      LoadingSequence loadingSequence,
      Long portEta,
      Set<Long> stageTickPositions,
      List<CargoLoadingRate> cargoLoadingRates) {
    log.info("Adding cargo loading rate");
    CargoLoadingRate cargoLoadingRate = new CargoLoadingRate();
    cargoLoadingRate.setStartTime(portEta + (loadingSequence.getStartTime() * 60 * 1000));
    cargoLoadingRate.setEndTime(portEta + (loadingSequence.getEndTime() * 60 * 1000));
    List<BigDecimal> rateList = new ArrayList<BigDecimal>();
    if (!StringUtils.isEmpty(loadingSequence.getCargoLoadingRate1())) {
      rateList.add(new BigDecimal(loadingSequence.getCargoLoadingRate1()));
    }
    if (!StringUtils.isEmpty(loadingSequence.getCargoLoadingRate2())) {
      rateList.add(new BigDecimal(loadingSequence.getCargoLoadingRate2()));
    }
    cargoLoadingRate.setLoadingRates(rateList);
    cargoLoadingRates.add(cargoLoadingRate);
  }

  private void buildBallastPumpCategories(
      Long vesselId, LoadingSequenceResponse response, List<BallastPump> ballastPumps) {
    List<PumpCategory> ballastPumpCategories = new ArrayList<>();
    log.info("Populating ballast pump categories");
    VesselIdRequest.Builder builder = VesselIdRequest.newBuilder();
    builder.setVesselId(vesselId);
    Set<Long> usedPumpIds =
        ballastPumps.stream().map(pump -> pump.getPumpId()).collect(Collectors.toSet());
    VesselPumpsResponse pumpsResponse =
        vesselInfoGrpcService.getVesselPumpsByVesselId(builder.build());
    if (pumpsResponse.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      pumpsResponse.getVesselPumpList().stream()
          .filter(vesselPump -> usedPumpIds.contains(vesselPump.getId()))
          .forEach(
              vesselPump -> {
                PumpCategory pumpCategory = new PumpCategory();
                pumpCategory.setId(vesselPump.getId());
                pumpCategory.setPumpName(vesselPump.getPumpCode());
                Optional<PumpType> pumpTypeOpt =
                    pumpsResponse.getPumpTypeList().stream()
                        .filter(pumpType -> pumpType.getId() == vesselPump.getId())
                        .findAny();
                pumpTypeOpt.ifPresent(pumpType -> pumpCategory.setPumpType(pumpType.getName()));
                ballastPumpCategories.add(pumpCategory);
              });
    }
    response.setBallastPumpCategories(ballastPumpCategories);
  }

  private void buildCargoTankCategory(
      LoadingPlanTankDetails stowage,
      Optional<VesselTankDetail> tankDetailOpt,
      Set<TankCategory> cargoTankCategories) {
    TankCategory tankCategory = new TankCategory();
    tankDetailOpt.ifPresent(
        tank -> {
          tankCategory.setTankName(tank.getShortName());
          tankCategory.setDisplayOrder(tank.getTankDisplayOrder());
        });
    if (cargoTankCategories.stream().anyMatch(cargo -> cargo.getId().equals(stowage.getTankId()))) {
      cargoTankCategories.removeIf(cargo -> cargo.getId().equals(stowage.getTankId()));
    }
    tankCategory.setId(stowage.getTankId());
    tankCategory.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    tankCategory.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    cargoTankCategories.add(tankCategory);
  }

  private void buildFlowRates(
      List<LoadingRate> loadingRates,
      Map<Long, VesselTankDetail> vesselTankMap,
      Long portEta,
      LoadingSequenceResponse response) {
    log.info("Populating flow rates");
    List<FlowRate> flowRates = new ArrayList<FlowRate>();
    Set<Long> tankIdList =
        loadingRates.stream().map(rate -> rate.getTankId()).collect(Collectors.toSet());
    tankIdList.forEach(
        tankId -> {
          FlowRate flowRate = new FlowRate();
          Optional<VesselTankDetail> tankDetailOpt = Optional.ofNullable(vesselTankMap.get(tankId));
          tankDetailOpt.ifPresent(tank -> flowRate.setTankName(tank.getShortName()));
          flowRate.setData(
              loadingRates.stream()
                  .filter(loadingRate -> loadingRate.getTankId() == tankId)
                  .map(
                      loadingRate ->
                          Arrays.asList(
                              portEta + (loadingRate.getStartTime() * 60 * 1000),
                              StringUtils.isEmpty(loadingRate.getLoadingRate())
                                  ? null
                                  : new BigDecimal(loadingRate.getLoadingRate())))
                  .collect(Collectors.toList()));
          Optional<LoadingRate> rateOpt =
              loadingRates.stream()
                  .filter(loadingRate -> loadingRate.getTankId() == tankId)
                  .sorted(Comparator.comparing(LoadingRate::getEndTime).reversed())
                  .findFirst();

          rateOpt.ifPresent(
              loadingRate ->
                  flowRate
                      .getData()
                      .add(
                          Arrays.asList(
                              portEta + (loadingRate.getEndTime() * 60 * 1000),
                              StringUtils.isEmpty(loadingRate.getLoadingRate())
                                  ? null
                                  : new BigDecimal(loadingRate.getLoadingRate()))));

          flowRates.add(flowRate);
        });
    response.setFlowRates(flowRates);
  }

  private Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds)
      throws GenericServiceException {
    Map<Long, CargoNominationDetail> details = new HashMap<Long, CargoNominationDetail>();
    cargoNominationIds.forEach(
        id -> {
          CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
          builder.setCargoNominationId(id);
          CargoNominationDetailReply reply =
              loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
          if (reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
            log.info("Fetched details of cargo nomination with id {}", id);
            details.put(id, reply.getCargoNominationdetail());
          }
        });

    return details;
  }

  private Map<Long, VesselTankDetail> getVesselTanks(Long vesselId) throws GenericServiceException {
    Map<Long, VesselTankDetail> vesselTankMap = new LinkedHashMap<Long, VesselTankDetail>();
    VesselRequest.Builder builder = VesselRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.addTankCategories(1L);
    builder.addTankCategories(9L);
    builder.addTankCategories(2L);
    VesselReply reply = vesselInfoGrpcService.getVesselTanks(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get vessel tanks",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched vessel tank details of vessel {}", vesselId);
    reply
        .getVesselTanksList()
        .forEach(vesselTank -> vesselTankMap.put(vesselTank.getTankId(), vesselTank));
    return vesselTankMap;
  }

  private PortDetail getPortInfo(Long portId) throws GenericServiceException {
    GetPortInfoByPortIdsRequest.Builder builder = GetPortInfoByPortIdsRequest.newBuilder();
    builder.addId(portId);
    PortReply reply = portInfoGrpcService.getPortInfoByPortIds(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get vessel tanks",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched port details of port {}", portId);
    return reply.getPorts(0);
  }

  private void buildBallastPump(PumpOperation operation, Long portEta, BallastPump ballastPump) {
    ballastPump.setPumpId(operation.getPumpXId());
    ballastPump.setRate(
        StringUtils.isEmpty(operation.getRate()) ? null : new BigDecimal(operation.getRate()));
    ballastPump.setEnd(portEta + (operation.getEndTime() * 60 * 1000));
    ballastPump.setStart(portEta + (operation.getStartTime() * 60 * 1000));
    ballastPump.setQuantityM3(
        StringUtils.isEmpty(operation.getQuantityM3())
            ? null
            : new BigDecimal(operation.getQuantityM3()));
  }

  private Integer buildBallast(
      LoadingPlanTankDetails ballast,
      Ballast ballastDto,
      Long portEta,
      Integer start,
      Integer end) {
    ballastDto.setQuantity(
        StringUtils.isEmpty(ballast.getQuantity()) ? null : new BigDecimal(ballast.getQuantity()));
    ballastDto.setSounding(
        StringUtils.isEmpty(ballast.getSounding()) ? null : new BigDecimal(ballast.getSounding()));
    ballastDto.setStart(portEta + (start * 60 * 1000));
    ballastDto.setEnd(portEta + (end * 60 * 1000));
    ballastDto.setTankId(ballast.getTankId());
    return end;
  }

  private Integer buildCargo(
      LoadingPlanTankDetails stowage,
      Cargo cargo,
      CargoNominationDetail cargoNomination,
      Long portEta,
      Integer start,
      Integer end) {
    cargo.setCargoNominationId(stowage.getCargoNominationId());
    cargo.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    cargo.setTankId(stowage.getTankId());
    cargo.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    if (cargoNomination != null) {
      cargo.setCargoId(cargoNomination.getCargoId());
      cargo.setColor(cargoNomination.getColor());
      cargo.setName(cargoNomination.getCargoName());
      cargo.setAbbreviation(cargoNomination.getAbbreviation());
    }
    cargo.setStart(portEta + (start * 60 * 1000));
    cargo.setEnd(portEta + (end * 60 * 1000));
    cargo.setApi(StringUtils.isEmpty(stowage.getApi()) ? null : new BigDecimal(stowage.getApi()));
    cargo.setIsCommingle(false);
    return end;
  }

  public void buildLoadingPlanSaveRequest(
      LoadingPlanAlgoRequest loadingPlanAlgoRequest,
      Long vesselId,
      Long loadingInfoId,
      Builder builder) {
    builder.setLoadingInfoId(loadingInfoId);
    builder.setHasLoadicator(loadingPlanAlgoRequest.getHasLoadicator());
    AtomicInteger sequenceNumber = new AtomicInteger(0);
    VesselIdRequest.Builder vesselReqBuilder = VesselIdRequest.newBuilder();
    vesselReqBuilder.setVesselId(vesselId);
    VesselPumpsResponse pumpsResponse =
        vesselInfoGrpcService.getVesselPumpsByVesselId(vesselReqBuilder.build());
    Optional.ofNullable(loadingPlanAlgoRequest.getProcessId()).ifPresent(builder::setProcessId);
    if (!pumpsResponse.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      log.error("Failed to fetch Vessel Pumps from Vessel Info MS of vessel {}", vesselId);
    }
    if (loadingPlanAlgoRequest.getEvents() != null) {
      loadingPlanAlgoRequest
          .getEvents()
          .forEach(
              event -> {
                this.buildSequences(
                    event, sequenceNumber, pumpsResponse.getVesselPumpList(), builder);
              });
    }

    if (loadingPlanAlgoRequest.getPlans() != null) {
      loadingPlanAlgoRequest
          .getPlans()
          .entrySet()
          .forEach(
              entry -> {
                this.buildLoadingPlan(entry, builder);
              });
    }

    if (loadingPlanAlgoRequest.getStages() != null) {
      loadingPlanAlgoRequest
          .getStages()
          .forEach(
              stage -> {
                this.buildLoadingSequenceStabilityParam(stage, builder);
              });
    }

    if (loadingPlanAlgoRequest.getErrors() != null
        && !loadingPlanAlgoRequest.getErrors().isEmpty()) {
      this.buildAlgoErrors(loadingPlanAlgoRequest.getErrors(), builder);
    }

    if (loadingPlanAlgoRequest.getLoadingInformation() != null) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        builder.setLoadingPlanDetailsFromAlgo(
            mapper.writeValueAsString(loadingPlanAlgoRequest.getLoadingInformation()));
      } catch (JsonProcessingException e) {
        log.error("Could not parse Loading Plan Details from ALGO");
      }
    }
  }

  private void buildAlgoErrors(List<AlgoError> errors, Builder builder) {
    errors.forEach(
        error -> {
          AlgoErrors.Builder errorBuilder = AlgoErrors.newBuilder();
          errorBuilder.setErrorHeading(error.getErrorHeading());
          errorBuilder.addAllErrorMessages(error.getErrorDetails());
          builder.addAlgoErrors(errorBuilder.build());
        });
  }

  private void buildLoadingSequenceStabilityParam(
      LoadingSequenceStabilityParam stage, Builder builder) {
    LoadingPlanStabilityParameters.Builder paramBuilder =
        LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(stage.getAfterDraft())
        .ifPresent(aftDraft -> paramBuilder.setAftDraft(String.valueOf(aftDraft)));
    Optional.ofNullable(stage.getBendinMoment())
        .ifPresent(bm -> paramBuilder.setBm(String.valueOf(bm)));
    Optional.ofNullable(stage.getForeDraft())
        .ifPresent(foreDraft -> paramBuilder.setForeDraft(String.valueOf(foreDraft)));
    Optional.ofNullable(stage.getShearForce())
        .ifPresent(sf -> paramBuilder.setSf(String.valueOf(sf)));
    Optional.ofNullable(stage.getTime())
        .ifPresent(time -> paramBuilder.setTime((new BigDecimal(time)).intValue()));
    Optional.ofNullable(stage.getMeanDraft())
        .ifPresent(meanDraft -> paramBuilder.setMeanDraft(String.valueOf(meanDraft)));
    Optional.ofNullable(stage.getTrim())
        .ifPresent(trim -> paramBuilder.setTrim(String.valueOf(trim)));
    Optional.ofNullable(stage.getList())
        .ifPresent(list -> paramBuilder.setList(String.valueOf(list)));
    builder.addLoadingSequenceStabilityParameters(paramBuilder.build());
  }

  private void buildLoadingPlan(Entry<String, LoadingPlan> entry, Builder builder) {
    Integer conditionType = 0;
    if (entry.getKey().equals("arrival")) {
      conditionType = 1;
    } else {
      conditionType = 2;
    }

    if (entry.getValue() != null) {
      this.buildPortStowage(entry.getValue(), conditionType, builder);
      this.buildPortBallast(entry.getValue(), conditionType, builder);
      this.buildPortRob(entry.getValue(), conditionType, builder);
      this.buildPortStability(entry.getValue(), conditionType, builder);
      this.buildPortCommingle(entry.getValue(), conditionType, builder);
    }
  }

  /**
   * @param value
   * @param conditionType
   * @param builder
   */
  private void buildPortCommingle(LoadingPlan value, Integer conditionType, Builder builder) {
    if (value.getLoadableQuantityCommingleCargoDetails() != null) {
      value
          .getLoadableQuantityCommingleCargoDetails()
          .forEach(
              commingle -> {
                LoadingPlanCommingleDetails.Builder commingleBuilder =
                    LoadingPlanCommingleDetails.newBuilder();
                Optional.ofNullable(commingle.getAbbreviation())
                    .ifPresent(commingleBuilder::setAbbreviation);
                Optional.ofNullable(commingle.getApi()).ifPresent(commingleBuilder::setApi);
                Optional.ofNullable(commingle.getCargo1Id())
                    .ifPresent(commingleBuilder::setCargo1Id);
                Optional.ofNullable(commingle.getCargo2Id())
                    .ifPresent(commingleBuilder::setCargo2Id);
                Optional.ofNullable(commingle.getCargoNomination1Id())
                    .ifPresent(commingleBuilder::setCargoNomination1Id);
                Optional.ofNullable(commingle.getCargoNomination2Id())
                    .ifPresent(commingleBuilder::setCargoNomination2Id);
                Optional.ofNullable(commingle.getColorCode())
                    .ifPresent(commingleBuilder::setColorCode);
                Optional.ofNullable(commingle.getQuantityM3())
                    .ifPresent(commingleBuilder::setQuantityM3);
                Optional.ofNullable(commingle.getQuantityMT())
                    .ifPresent(commingleBuilder::setQuantityMT);
                Optional.ofNullable(commingle.getTankId()).ifPresent(commingleBuilder::setTankId);
                Optional.ofNullable(commingle.getTankName())
                    .ifPresent(commingleBuilder::setTankName);
                Optional.ofNullable(commingle.getTemperature())
                    .ifPresent(commingleBuilder::setTemperature);
                Optional.ofNullable(commingle.getUllage()).ifPresent(commingleBuilder::setUllage);
                Optional.ofNullable(commingle.getQuantity1MT())
                    .ifPresent(commingleBuilder::setQuantity1MT);
                Optional.ofNullable(commingle.getQuantity2MT())
                    .ifPresent(commingleBuilder::setQuantity2MT);
                Optional.ofNullable(commingle.getQuantity1M3())
                    .ifPresent(commingleBuilder::setQuantity1M3);
                Optional.ofNullable(commingle.getQuantity2M3())
                    .ifPresent(commingleBuilder::setQuantity2M3);
                Optional.ofNullable(commingle.getUllage1()).ifPresent(commingleBuilder::setUllage1);
                Optional.ofNullable(commingle.getUllage2()).ifPresent(commingleBuilder::setUllage2);
                commingleBuilder.setConditionType(conditionType);
                builder.addPortLoadingPlanCommingleDetails(commingleBuilder.build());
              });
    }
  }

  private void buildPortStability(LoadingPlan value, Integer conditionType, Builder builder) {
    LoadingPlanStabilityParameters.Builder paramBuilder =
        LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(value.getBendinMoment()).ifPresent(paramBuilder::setBm);
    Optional.ofNullable(value.getShearForce()).ifPresent(paramBuilder::setSf);
    Optional.ofNullable(value.getForeDraft()).ifPresent(paramBuilder::setForeDraft);
    Optional.ofNullable(value.getAfterDraft()).ifPresent(paramBuilder::setAftDraft);
    Optional.ofNullable(value.getMeanDraft()).ifPresent(paramBuilder::setMeanDraft);
    Optional.ofNullable(value.getTrim()).ifPresent(paramBuilder::setTrim);
    Optional.ofNullable(value.getList()).ifPresent(paramBuilder::setList);
    paramBuilder.setConditionType(conditionType);
    builder.addPortLoadingPlanStabilityParameters(paramBuilder.build());
  }

  private void buildPortRob(LoadingPlan value, Integer conditionType, Builder builder) {
    if (value.getLoadablePlanRoBDetails() != null) {
      value
          .getLoadablePlanRoBDetails()
          .forEach(
              rob -> {
                LoadingPlanTankDetails.Builder robBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(rob.getQuantityM3()).ifPresent(robBuilder::setQuantityM3);
                Optional.ofNullable(rob.getQuantityMT()).ifPresent(robBuilder::setQuantity);
                Optional.ofNullable(rob.getTankId()).ifPresent(robBuilder::setTankId);
                Optional.ofNullable(rob.getColorCode()).ifPresent(robBuilder::setColorCode);
                Optional.ofNullable(rob.getDensity())
                    .ifPresent(density -> robBuilder.setDensity(density.toString()));
                robBuilder.setConditionType(conditionType);
                builder.addPortLoadingPlanRobDetails(robBuilder.build());
              });
    }
  }

  private void buildPortBallast(LoadingPlan value, Integer conditionType, Builder builder) {
    if (value.getLoadablePlanBallastDetails() != null) {
      value
          .getLoadablePlanBallastDetails()
          .forEach(
              ballast -> {
                LoadingPlanTankDetails.Builder ballastBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(ballast.getQuantityM3())
                    .ifPresent(ballastBuilder::setQuantityM3);
                Optional.ofNullable(ballast.getQuantityMT()).ifPresent(ballastBuilder::setQuantity);
                Optional.ofNullable(ballast.getSounding()).ifPresent(ballastBuilder::setSounding);
                Optional.ofNullable(ballast.getTankId()).ifPresent(ballastBuilder::setTankId);
                Optional.ofNullable(ballast.getColorCode()).ifPresent(ballastBuilder::setColorCode);
                Optional.ofNullable(ballast.getSg()).ifPresent(ballastBuilder::setSg);
                ballastBuilder.setConditionType(conditionType);
                builder.addPortLoadingPlanBallastDetails(ballastBuilder.build());
              });
    }
  }

  private void buildPortStowage(LoadingPlan value, Integer conditionType, Builder builder) {
    if (value.getLoadablePlanStowageDetails() != null) {
      value
          .getLoadablePlanStowageDetails()
          .forEach(
              stowage -> {
                LoadingPlanTankDetails.Builder stowageBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(stowage.getApi()).ifPresent(stowageBuilder::setApi);
                Optional.ofNullable(stowage.getCargoNominationId())
                    .ifPresent(stowageBuilder::setCargoNominationId);
                Optional.ofNullable(stowage.getQuantityM3())
                    .ifPresent(stowageBuilder::setQuantityM3);
                Optional.ofNullable(stowage.getQuantityMT()).ifPresent(stowageBuilder::setQuantity);
                Optional.ofNullable(stowage.getTankId()).ifPresent(stowageBuilder::setTankId);
                Optional.ofNullable(stowage.getTemperature())
                    .ifPresent(stowageBuilder::setTemperature);
                Optional.ofNullable(stowage.getUllage()).ifPresent(stowageBuilder::setUllage);
                Optional.ofNullable(stowage.getColorCode()).ifPresent(stowageBuilder::setColorCode);
                Optional.ofNullable(stowage.getAbbreviation())
                    .ifPresent(stowageBuilder::setAbbreviation);
                Optional.ofNullable(stowage.getCargoId()).ifPresent(stowageBuilder::setCargoId);
                stowageBuilder.setConditionType(conditionType);
                builder.addPortLoadingPlanStowageDetails(stowageBuilder.build());
              });
    }
  }

  private void buildSequences(
      Event event, AtomicInteger sequenceNumber, List<VesselPump> pumps, Builder builder) {
    event
        .getSequence()
        .forEach(
            sequence -> {
              LoadingSequence.Builder sequenceBuilder = LoadingSequence.newBuilder();
              sequenceBuilder.setCargoNominationId(event.getCargoNominationId());
              if (sequence.getStageWiseCargoLoadingRates().size() > 0)
                Optional.ofNullable(sequence.getStageWiseCargoLoadingRates().get("0"))
                    .ifPresent(
                        rate1 -> {
                          if (rate1.equalsIgnoreCase("None")) {
                            sequenceBuilder.setCargoLoadingRate1("0");
                          } else sequenceBuilder.setCargoLoadingRate1(rate1);
                        });
              if (sequence.getStageWiseCargoLoadingRates().size() > 1)
                Optional.ofNullable(sequence.getStageWiseCargoLoadingRates().get("1"))
                    .ifPresent(
                        rate2 -> {
                          if (rate2.equalsIgnoreCase("None")) {
                            sequenceBuilder.setCargoLoadingRate2("0");
                          } else sequenceBuilder.setCargoLoadingRate2(rate2);
                        });
              this.buildBallastOperations(sequence.getBallast(), pumps, sequenceBuilder);
              this.buildDeballastingRates(sequence.getDeballastingRates(), sequenceBuilder);
              this.buildLoadingRates(sequence.getTankWiseCargoLoadingRates(), sequenceBuilder);
              this.buildEductorOperations(sequence.getEduction(), sequenceBuilder);
              if (sequence.getLoadablePlanPortWiseDetails() != null) {
                this.buildLoadingPlanPortWiseDetails(
                    sequence.getLoadablePlanPortWiseDetails(), sequenceBuilder);
              }
              Optional.ofNullable(sequence.getStage()).ifPresent(sequenceBuilder::setStageName);
              sequenceBuilder.setSequenceNumber(sequenceNumber.incrementAndGet());
              Optional.ofNullable(sequence.getTimeEnd())
                  .ifPresent(
                      timeEnd -> sequenceBuilder.setEndTime((new BigDecimal(timeEnd)).intValue()));
              Optional.ofNullable(sequence.getTimeStart())
                  .ifPresent(
                      timeStart ->
                          sequenceBuilder.setStartTime((new BigDecimal(timeStart)).intValue()));
              Optional.ofNullable(sequence.getToLoadicator())
                  .ifPresent(sequenceBuilder::setToLoadicator);
              builder.addLoadingSequences(sequenceBuilder.build());
            });
  }

  /**
   * @param eduction
   * @param sequenceBuilder
   */
  private void buildEductorOperations(
      Eduction eduction,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder) {
    if ((eduction != null) && (eduction.getTimeStart() != null)) {
      EductorOperation.Builder eductorBuilder = EductorOperation.newBuilder();
      eductorBuilder.setEndTime(
          StringUtils.isEmpty(eduction.getTimeEnd()) ? 0 : Integer.valueOf(eduction.getTimeEnd()));
      if (eduction.getPumpSelected() != null) {
        eductorBuilder.setPumpsUsed(
            eduction.getPumpSelected().keySet().stream().collect(Collectors.joining(",")));
      }
      if (eduction.getTank() != null) {
        eductorBuilder.setTanksUsed(
            eduction.getTank().keySet().stream().collect(Collectors.joining(",")));
      }
      eductorBuilder.setStartTime(
          StringUtils.isEmpty(eduction.getTimeStart())
              ? 0
              : Integer.valueOf(eduction.getTimeStart()));
      sequenceBuilder.setEductorOperation(eductorBuilder.build());
    }
  }

  private void buildLoadingPlanPortWiseDetails(
      List<com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails>
          loadablePlanPortWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder) {
    loadablePlanPortWiseDetails.forEach(
        portWiseDetails -> {
          LoadingPlanPortWiseDetails.Builder builder = LoadingPlanPortWiseDetails.newBuilder();
          this.buildDeballastingRates(portWiseDetails, builder);
          this.buildLoadingPlanBallastDetails(portWiseDetails, builder);
          this.buildLoadingPlanRobDetails(portWiseDetails, builder);
          this.buildStabilityParams(portWiseDetails, builder);
          this.buildLoadingPlanStowageDetails(portWiseDetails, builder);
          this.buildLoadingPlanCommingleDetails(portWiseDetails, builder);
          Optional.ofNullable(portWiseDetails.getTime())
              .ifPresent(time -> builder.setTime((new BigDecimal(time)).intValue()));
          sequenceBuilder.addLoadingPlanPortWiseDetails(builder.build());
        });
  }

  /**
   * @param portWiseDetails
   * @param builder
   */
  private void buildLoadingPlanCommingleDetails(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    // TODO Auto-generated method stub
    if (portWiseDetails.getLoadableQuantityCommingleCargoDetails() != null) {
      portWiseDetails
          .getLoadableQuantityCommingleCargoDetails()
          .forEach(
              commingle -> {
                LoadingPlanCommingleDetails.Builder commingleBuilder =
                    LoadingPlanCommingleDetails.newBuilder();
                Optional.ofNullable(commingle.getAbbreviation())
                    .ifPresent(commingleBuilder::setAbbreviation);
                Optional.ofNullable(commingle.getApi()).ifPresent(commingleBuilder::setApi);
                Optional.ofNullable(commingle.getCargo1Id())
                    .ifPresent(commingleBuilder::setCargo1Id);
                Optional.ofNullable(commingle.getCargo2Id())
                    .ifPresent(commingleBuilder::setCargo2Id);
                Optional.ofNullable(commingle.getCargoNomination1Id())
                    .ifPresent(commingleBuilder::setCargoNomination1Id);
                Optional.ofNullable(commingle.getCargoNomination2Id())
                    .ifPresent(commingleBuilder::setCargoNomination2Id);
                Optional.ofNullable(commingle.getColorCode())
                    .ifPresent(commingleBuilder::setColorCode);
                Optional.ofNullable(commingle.getQuantityM3())
                    .ifPresent(commingleBuilder::setQuantityM3);
                Optional.ofNullable(commingle.getQuantityMT())
                    .ifPresent(commingleBuilder::setQuantityMT);
                Optional.ofNullable(commingle.getTankId()).ifPresent(commingleBuilder::setTankId);
                Optional.ofNullable(commingle.getTankName())
                    .ifPresent(commingleBuilder::setTankName);
                Optional.ofNullable(commingle.getTemperature())
                    .ifPresent(commingleBuilder::setTemperature);
                Optional.ofNullable(commingle.getUllage()).ifPresent(commingleBuilder::setUllage);
                Optional.ofNullable(commingle.getQuantity1MT())
                    .ifPresent(commingleBuilder::setQuantity1MT);
                Optional.ofNullable(commingle.getQuantity2MT())
                    .ifPresent(commingleBuilder::setQuantity2MT);
                Optional.ofNullable(commingle.getQuantity1M3())
                    .ifPresent(commingleBuilder::setQuantity1M3);
                Optional.ofNullable(commingle.getQuantity2M3())
                    .ifPresent(commingleBuilder::setQuantity2M3);
                Optional.ofNullable(commingle.getUllage1()).ifPresent(commingleBuilder::setUllage1);
                Optional.ofNullable(commingle.getUllage2()).ifPresent(commingleBuilder::setUllage2);
                builder.addLoadingPlanCommingleDetails(commingleBuilder.build());
              });
    }
  }

  private void buildLoadingPlanStowageDetails(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getLoadablePlanStowageDetails() != null) {
      portWiseDetails
          .getLoadablePlanStowageDetails()
          .forEach(
              stowage -> {
                LoadingPlanTankDetails.Builder stowageBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(stowage.getApi()).ifPresent(stowageBuilder::setApi);
                Optional.ofNullable(stowage.getCargoNominationId())
                    .ifPresent(stowageBuilder::setCargoNominationId);
                Optional.ofNullable(stowage.getQuantityM3())
                    .ifPresent(stowageBuilder::setQuantityM3);
                Optional.ofNullable(stowage.getQuantityMT()).ifPresent(stowageBuilder::setQuantity);
                Optional.ofNullable(stowage.getTankId()).ifPresent(stowageBuilder::setTankId);
                Optional.ofNullable(stowage.getTemperature())
                    .ifPresent(stowageBuilder::setTemperature);
                Optional.ofNullable(stowage.getUllage()).ifPresent(stowageBuilder::setUllage);
                Optional.ofNullable(stowage.getAbbreviation())
                    .ifPresent(stowageBuilder::setAbbreviation);
                Optional.ofNullable(stowage.getColorCode()).ifPresent(stowageBuilder::setColorCode);
                builder.addLoadingPlanStowageDetails(stowageBuilder.build());
              });
    }
  }

  private void buildStabilityParams(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    LoadingPlanStabilityParameters.Builder paramBuilder =
        LoadingPlanStabilityParameters.newBuilder();
    Optional.ofNullable(portWiseDetails.getBendinMoment()).ifPresent(paramBuilder::setBm);
    Optional.ofNullable(portWiseDetails.getShearForce()).ifPresent(paramBuilder::setSf);
    Optional.ofNullable(portWiseDetails.getForeDraft()).ifPresent(paramBuilder::setForeDraft);
    Optional.ofNullable(portWiseDetails.getAfterDraft()).ifPresent(paramBuilder::setAftDraft);
    Optional.ofNullable(portWiseDetails.getMeanDraft()).ifPresent(paramBuilder::setMeanDraft);
    Optional.ofNullable(portWiseDetails.getTrim()).ifPresent(paramBuilder::setTrim);
    Optional.ofNullable(portWiseDetails.getList()).ifPresent(paramBuilder::setList);
    builder.setLoadingPlanStabilityParameters(paramBuilder.build());
  }

  private void buildLoadingPlanRobDetails(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getLoadablePlanRoBDetails() != null) {
      portWiseDetails
          .getLoadablePlanRoBDetails()
          .forEach(
              rob -> {
                LoadingPlanTankDetails.Builder robBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(rob.getQuantityM3()).ifPresent(robBuilder::setQuantityM3);
                Optional.ofNullable(rob.getQuantityMT()).ifPresent(robBuilder::setQuantity);
                Optional.ofNullable(rob.getTankId()).ifPresent(robBuilder::setTankId);
                Optional.ofNullable(rob.getColorCode()).ifPresent(robBuilder::setColorCode);
                builder.addLoadingPlanRobDetails(robBuilder.build());
              });
    }
  }

  private void buildLoadingPlanBallastDetails(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getLoadablePlanBallastDetails() != null) {
      portWiseDetails
          .getLoadablePlanBallastDetails()
          .forEach(
              ballast -> {
                LoadingPlanTankDetails.Builder ballastBuilder = LoadingPlanTankDetails.newBuilder();
                Optional.ofNullable(ballast.getQuantityM3())
                    .ifPresent(ballastBuilder::setQuantityM3);
                Optional.ofNullable(ballast.getQuantityMT()).ifPresent(ballastBuilder::setQuantity);
                Optional.ofNullable(ballast.getSounding()).ifPresent(ballastBuilder::setSounding);
                Optional.ofNullable(ballast.getTankId()).ifPresent(ballastBuilder::setTankId);
                Optional.ofNullable(ballast.getColorCode()).ifPresent(ballastBuilder::setColorCode);
                Optional.ofNullable(ballast.getSg()).ifPresent(ballastBuilder::setSg);
                builder.addLoadingPlanBallastDetails(ballastBuilder.build());
              });
    }
  }

  private void buildDeballastingRates(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    if (portWiseDetails.getDeballastingRates() != null) {
      portWiseDetails
          .getDeballastingRates()
          .entrySet()
          .forEach(
              entry -> {
                DeBallastingRate.Builder rateBuilder = DeBallastingRate.newBuilder();
                Optional.ofNullable(entry.getKey())
                    .ifPresent(tankId -> rateBuilder.setTankId(Long.valueOf(tankId)));
                Optional.ofNullable(entry.getValue()).ifPresent(rateBuilder::setDeBallastingRate);
                Optional.ofNullable(portWiseDetails.getTime())
                    .ifPresent(time -> rateBuilder.setTime((new BigDecimal(time)).intValue()));
                builder.addDeballastingRates(rateBuilder.build());
              });
    }
  }

  private void buildLoadingRates(
      List<Map<String, String>> loadingRates,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder) {
    if (loadingRates != null) {
      loadingRates.forEach(
          section -> {
            section
                .entrySet()
                .forEach(
                    entry -> {
                      LoadingRate.Builder builder = LoadingRate.newBuilder();
                      Optional.ofNullable(entry.getKey())
                          .ifPresent(tankId -> builder.setTankId(Long.valueOf(tankId)));
                      Optional.ofNullable(entry.getValue())
                          .ifPresent(rate -> builder.setLoadingRate(rate));
                      sequenceBuilder.addLoadingRates(builder.build());
                    });
          });
    }
  }

  private void buildDeballastingRates(
      Map<String, String> deballastingRates,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder) {
    if (deballastingRates != null) {
      deballastingRates
          .entrySet()
          .forEach(
              entry -> {
                DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
                Optional.ofNullable(entry.getKey())
                    .ifPresent(tankId -> builder.setTankId(Long.valueOf(tankId)));
                Optional.ofNullable(entry.getValue()).ifPresent(builder::setDeBallastingRate);
                sequenceBuilder.addDeBallastingRates(builder.build());
              });
    }
  }

  private void buildBallastOperations(
      Map<String, List<Pump>> ballasts,
      List<VesselPump> pumps,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder) {
    if (ballasts != null) {
      for (Entry<String, List<Pump>> entry : ballasts.entrySet()) {
        long pumpId = 0;
        if (entry.getKey().equalsIgnoreCase("Gravity")) {
          pumpId = 0;
        } else {
          pumpId = Long.valueOf(entry.getKey());
        }

        if (entry.getValue() != null) {
          for (Pump ballastOperation : entry.getValue()) {
            PumpOperation.Builder builder = PumpOperation.newBuilder();
            Optional.ofNullable(ballastOperation.getQuantityM3())
                .ifPresent(quantityM3 -> builder.setQuantityM3(quantityM3));
            Optional.ofNullable(ballastOperation.getRate())
                .ifPresent(rate -> builder.setRate(rate));
            Optional.ofNullable(ballastOperation.getTimeEnd())
                .ifPresent(timeEnd -> builder.setEndTime((new BigDecimal(timeEnd)).intValue()));
            Optional.ofNullable(ballastOperation.getTimeStart())
                .ifPresent(
                    timeStart -> builder.setStartTime((new BigDecimal(timeStart)).intValue()));
            builder.setPumpXId(pumpId);
            Optional<VesselPump> vesselPumpOpt =
                pumps.stream().filter(pump -> pump.getId() == builder.getPumpXId()).findFirst();
            vesselPumpOpt.ifPresent(vesselPump -> builder.setPumpName(vesselPump.getPumpName()));
            sequenceBuilder.addBallastOperations(builder.build());
          }
        }
      }
    }
  }
}
