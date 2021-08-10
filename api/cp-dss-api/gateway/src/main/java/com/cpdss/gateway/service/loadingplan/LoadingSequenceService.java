/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.AlgoErrors;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.SynopticalBallastRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.PumpType;
import com.cpdss.common.generated.VesselInfo.VesselIdRequest;
import com.cpdss.common.generated.VesselInfo.VesselPump;
import com.cpdss.common.generated.VesselInfo.VesselPumpsResponse;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LoadingSequenceService {

  @GrpcClient("vesselInfoService")
  VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  public void buildLoadingSequence(
      Long vesselId, LoadingSequenceReply reply, LoadingSequenceResponse response)
      throws GenericServiceException {
    List<VesselTankDetail> vesselTanks = this.getVesselTanks(vesselId);
    Set<Long> cargoNominationIds =
        reply.getLoadingSequencesList().stream()
            .map(sequence -> sequence.getCargoNominationId())
            .collect(Collectors.toSet());
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);

    List<SynopticalBallastRecord> ballastDetails = new ArrayList<>();
    SynopticalTableReply synopticalReply =
        this.getSynopticalTableDetails(
            reply.getVesselId(), reply.getVoyageId(), reply.getLoadablePatternId());
    synopticalReply
        .getSynopticalRecordsList()
        .forEach(
            record -> {
              ballastDetails.addAll(record.getBallastList());
            });
    List<Cargo> cargos = new ArrayList<Cargo>();
    List<Ballast> ballasts = new ArrayList<Ballast>();
    List<BallastPump> ballastPumps = new ArrayList<BallastPump>();
    List<BallastPump> gravityList = new ArrayList<BallastPump>();
    BallastPump gravity = new BallastPump();
    List<CargoLoadingRate> cargoLoadingRates = new ArrayList<>();
    List<LoadingRate> loadingRates = new ArrayList<LoadingRate>();
    SortedSet<Long> stageTickPositions = new TreeSet<Long>();
    List<StabilityParam> stabilityParams = new ArrayList<StabilityParam>();

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
    stabilityParams.addAll(Arrays.asList(foreDraft, aftDraft, trim, ukc, gm, sf, bm));

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm");
    try {
      response.setMinXAxisValue(
          StringUtils.isEmpty(reply.getStartDate()) ? new Date() : sdf.parse(reply.getStartDate()));
    } catch (ParseException e) {
      e.printStackTrace();
      response.setMinXAxisValue(new Date());
    }

    Long portEta = response.getMinXAxisValue().toInstant().toEpochMilli();
    Integer start = 0;
    Integer temp = 0;

    log.info("Populating Loading Sequences");
    for (LoadingSequence loadingSequence : reply.getLoadingSequencesList()) {
      log.info(loadingSequence.getStageName());
      for (LoadingPlanPortWiseDetails portWiseDetails :
          loadingSequence.getLoadingPlanPortWiseDetailsList()) {
        List<LoadingPlanTankDetails> filteredStowage =
            portWiseDetails.getLoadingPlanStowageDetailsList().stream()
                .filter(
                    stowage ->
                        stowage.getCargoNominationId() == loadingSequence.getCargoNominationId())
                .collect(Collectors.toList());
        for (LoadingPlanTankDetails stowage : filteredStowage) {
          // Adding cargos
          temp =
              this.buildCargoSequence(
                  stowage, vesselTanks, cargoNomDetails, portEta, start, portWiseDetails, cargos);
        }
        for (LoadingPlanTankDetails ballast : portWiseDetails.getLoadingPlanBallastDetailsList()) {
          // Adding ballasts
          this.buildBallastSequence(
              ballast, vesselTanks, portEta, start, portWiseDetails, ballastDetails, ballasts);
        }

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
    this.buildFlowRates(loadingRates, vesselTanks, portEta, response);
    this.buildCargoTankCategories(reply, vesselTanks, response);
    this.buildBallastTankCategories(reply, vesselTanks, response);
    this.buildBallastPumpCategories(vesselId, response);
    this.buildCargoStages(reply, cargoNomDetails, portEta, response);

    response.setCargos(cargos);
    response.setBallasts(ballasts);
    response.setBallastPumps(ballastPumps);
    response.setGravity(gravity);
    response.setCargoLoadingRates(cargoLoadingRates);
    response.setStageTickPositions(stageTickPositions);
    response.setStabilityParams(stabilityParams);
  }

  /**
   * @param reply
   * @param cargoNomDetails
   * @param response
   */
  private void buildCargoStages(
      LoadingSequenceReply reply,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long portEta,
      LoadingSequenceResponse response) {
    Integer start = 0;
    Integer temp = 0;
    List<CargoStage> cargoStages = new ArrayList<>();
    Set<Long> cargoNominationIds =
        reply.getLoadingSequencesList().stream()
            .map(sequence -> sequence.getCargoNominationId())
            .collect(Collectors.toSet());
    for (Long cargoNominationId : cargoNominationIds.stream().sorted().toList()) {
      AtomicInteger stageNumber = new AtomicInteger();
      for (LoadingSequence sequence :
          reply.getLoadingSequencesList().stream()
              .filter(sequence -> sequence.getCargoNominationId() == cargoNominationId)
              .collect(Collectors.toList())) {
        for (LoadingPlanPortWiseDetails portWiseDetails :
            sequence.getLoadingPlanPortWiseDetailsList()) {
          temp = portWiseDetails.getTime();
          addCargoStage(
              portWiseDetails, cargoNomDetails, stageNumber, portEta, start, temp, cargoStages);
          start = temp;
        }
      }
    }
    response.setCargoStages(cargoStages);
  }

  private void addCargoStage(
      LoadingPlanPortWiseDetails portWiseDetails,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      AtomicInteger stageNumber,
      Long portEta,
      Integer start,
      Integer end,
      List<CargoStage> cargoStages) {
    CargoStage cargoStage = new CargoStage();
    if (portWiseDetails.getLoadingPlanStowageDetailsCount() > 0) {
      List<Cargo> cargos = new ArrayList<Cargo>();
      Set<Long> cargoNomIds =
          portWiseDetails.getLoadingPlanStowageDetailsList().stream()
              .map(stowage -> stowage.getCargoNominationId())
              .collect(Collectors.toSet());
      cargoNomIds.forEach(
          cargoNominationId -> {
            Cargo cargo = new Cargo();
            CargoNominationDetail cargoNomination = cargoNomDetails.get(cargoNominationId);
            cargo.setName(cargoNomination.getCargoName());
            cargo.setCargoId(cargoNomination.getCargoId());
            cargo.setAbbreviation(cargoNomination.getAbbreviation());
            cargo.setCargoNominationId(cargoNomination.getId());
            cargo.setColor(cargoNomination.getColor());
            BigDecimal total =
                portWiseDetails.getLoadingPlanStowageDetailsList().stream()
                    .filter(
                        stowage ->
                            (stowage.getCargoNominationId() == cargoNominationId)
                                && !StringUtils.isEmpty(stowage.getQuantity()))
                    .map(stowage -> new BigDecimal(stowage.getQuantity()))
                    .reduce(
                        new BigDecimal(0),
                        (val1, val2) -> {
                          return val1.add(val2);
                        });
            cargo.setQuantity(total);
            cargos.add(cargo);
          });
      cargoStage.setName("Stage " + stageNumber.incrementAndGet());
      cargoStage.setStart(portEta + (start * 60 * 1000));
      cargoStage.setEnd(portEta + (end * 60 * 1000));
      cargoStage.setCargos(cargos);
      cargoStages.add(cargoStage);
    }
  }

  private void updateCargoLoadingRateIntervals(
      List<CargoLoadingRate> cargoLoadingRates, SortedSet<Long> stageTickPositions) {
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
        });
  }

  private void buildBallastSequence(
      LoadingPlanTankDetails ballast,
      List<VesselTankDetail> vesselTanks,
      Long portEta,
      Integer start,
      LoadingPlanPortWiseDetails portWiseDetails,
      List<SynopticalBallastRecord> ballastDetails,
      List<Ballast> ballasts) {
    Ballast ballastDto = new Ballast();
    Optional<VesselTankDetail> tankDetailOpt =
        vesselTanks.stream().filter(tank -> tank.getTankId() == ballast.getTankId()).findAny();
    Optional<SynopticalBallastRecord> ballastDetailsOpt =
        ballastDetails.stream()
            .filter(details -> details.getTankId() == ballast.getTankId())
            .findAny();
    buildBallast(ballast, ballastDto, portEta, start, portWiseDetails.getTime());
    tankDetailOpt.ifPresent(tank -> ballastDto.setTankName(tank.getShortName()));
    ballastDetailsOpt.ifPresent(details -> ballastDto.setColor(details.getColorCode()));
    ballasts.add(ballastDto);
  }

  private Integer buildCargoSequence(
      LoadingPlanTankDetails stowage,
      List<VesselTankDetail> vesselTanks,
      Map<Long, CargoNominationDetail> cargoNomDetails,
      Long portEta,
      Integer start,
      LoadingPlanPortWiseDetails portWiseDetails,
      List<Cargo> cargos) {
    Cargo cargo = new Cargo();
    Optional<VesselTankDetail> tankDetailOpt =
        vesselTanks.stream().filter(tank -> tank.getTankId() == stowage.getTankId()).findAny();
    CargoNominationDetail cargoNomination = cargoNomDetails.get(stowage.getCargoNominationId());
    Integer end =
        buildCargo(stowage, cargo, cargoNomination, portEta, start, portWiseDetails.getTime());
    tankDetailOpt.ifPresent(tank -> cargo.setTankName(tank.getShortName()));
    cargos.add(cargo);
    return end;
  }

  private void buildCargoLoadingRates(
      LoadingSequence loadingSequence,
      Long portEta,
      SortedSet<Long> stageTickPositions,
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

  private void buildBallastPumpCategories(Long vesselId, LoadingSequenceResponse response) {
    List<PumpCategory> ballastPumpCategories = new ArrayList<>();
    log.info("Populating ballast pump categories");
    VesselIdRequest.Builder builder = VesselIdRequest.newBuilder();
    builder.setVesselId(vesselId);
    VesselPumpsResponse pumpsResponse =
        vesselInfoGrpcService.getVesselPumpsByVesselId(builder.build());
    if (pumpsResponse.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      pumpsResponse
          .getVesselPumpList()
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

  private void buildBallastTankCategories(
      LoadingSequenceReply reply,
      List<VesselTankDetail> vesselTanks,
      LoadingSequenceResponse response) {
    log.info("Populating ballast tank categories");
    List<TankCategory> ballastTankCategories = new ArrayList<>();
    if (reply.getLoadingSequencesCount() > 0) {
      LoadingSequence initialSequence = reply.getLoadingSequences(0);
      if (initialSequence.getLoadingPlanPortWiseDetailsCount() > 0) {
        LoadingPlanPortWiseDetails portWiseDetails =
            initialSequence.getLoadingPlanPortWiseDetails(0);
        portWiseDetails
            .getLoadingPlanBallastDetailsList()
            .forEach(
                ballast -> {
                  TankCategory tankCategory = new TankCategory();
                  tankCategory.setId(ballast.getTankId());
                  Optional<VesselTankDetail> tankDetailOpt =
                      vesselTanks.stream()
                          .filter(tank -> tank.getTankId() == ballast.getTankId())
                          .findAny();
                  tankDetailOpt.ifPresent(tank -> tankCategory.setTankName(tank.getShortName()));
                  ballastTankCategories.add(tankCategory);
                });
      }
    }
    response.setBallastTankCategories(ballastTankCategories);
  }

  private void buildCargoTankCategories(
      LoadingSequenceReply reply,
      List<VesselTankDetail> vesselTanks,
      LoadingSequenceResponse response) {
    log.info("Populating cargo tank categories");
    List<TankCategory> cargoTankCategories = new ArrayList<>();
    if (reply.getLoadingSequencesCount() > 0) {
      LoadingSequence finalSequence =
          reply.getLoadingSequences(reply.getLoadingSequencesCount() - 1);
      if (finalSequence.getLoadingPlanPortWiseDetailsCount() > 0) {
        LoadingPlanPortWiseDetails portWiseDetails =
            finalSequence.getLoadingPlanPortWiseDetails(
                finalSequence.getLoadingPlanPortWiseDetailsCount() - 1);
        portWiseDetails
            .getLoadingPlanStowageDetailsList()
            .forEach(
                stowage -> {
                  TankCategory tankCategory = new TankCategory();
                  Optional<VesselTankDetail> tankDetailOpt =
                      vesselTanks.stream()
                          .filter(tank -> tank.getTankId() == stowage.getTankId())
                          .findAny();
                  tankDetailOpt.ifPresent(tank -> tankCategory.setTankName(tank.getShortName()));
                  tankCategory.setId(stowage.getTankId());
                  tankCategory.setQuantity(
                      StringUtils.isEmpty(stowage.getQuantity())
                          ? null
                          : new BigDecimal(stowage.getQuantity()));
                  tankCategory.setUllage(
                      StringUtils.isEmpty(stowage.getUllage())
                          ? null
                          : new BigDecimal(stowage.getUllage()));
                  cargoTankCategories.add(tankCategory);
                });
      }
    }
    response.setCargoTankCategories(cargoTankCategories);
  }

  private void buildFlowRates(
      List<LoadingRate> loadingRates,
      List<VesselTankDetail> vesselTanks,
      Long portEta,
      LoadingSequenceResponse response) {
    log.info("Populating flow rates");
    List<FlowRate> flowRates = new ArrayList<FlowRate>();
    Set<Long> tankIdList =
        loadingRates.stream().map(rate -> rate.getTankId()).collect(Collectors.toSet());
    tankIdList.forEach(
        tankId -> {
          FlowRate flowRate = new FlowRate();
          Optional<VesselTankDetail> tankDetailOpt =
              vesselTanks.stream().filter(tank -> tank.getTankId() == tankId).findAny();
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

  private SynopticalTableReply getSynopticalTableDetails(
      Long vesselId, Long voyageId, long loadablePatternId) throws GenericServiceException {
    LoadableStudyRequest.Builder builder = LoadableStudyRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setVoyageId(voyageId);
    LoadablePatternConfirmedReply confirmedReply =
        loadableStudyGrpcService.getLoadablePatternByVoyageAndStatus(builder.build());
    LoadablePatternRequest.Builder patternReqBuilder = LoadablePatternRequest.newBuilder();
    patternReqBuilder.setLoadableStudyId(confirmedReply.getLoadableStudyId());
    SynopticalTableRequest.Builder synopticalBuilder = SynopticalTableRequest.newBuilder();
    synopticalBuilder.setLoadableStudyId(confirmedReply.getLoadableStudyId());
    synopticalBuilder.setOperationType("ARR");
    synopticalBuilder.setPortId(504);
    synopticalBuilder.setVesselId(vesselId);
    synopticalBuilder.setVoyageId(voyageId);
    SynopticalTableReply reply =
        loadableStudyGrpcService.getSynopticalDataByPortId(synopticalBuilder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get synoptical table ",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched Synoptical Table {}", reply.getId());
    return reply;
  }

  private List<VesselTankDetail> getVesselTanks(Long vesselId) throws GenericServiceException {
    VesselRequest.Builder builder = VesselRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.addTankCategories(1L);
    builder.addTankCategories(2L);
    VesselReply reply = vesselInfoGrpcService.getVesselTanks(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get vessel tanks",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched vessel tank details of vessel {}", vesselId);
    return reply.getVesselTanksList();
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

  private void buildBallast(
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
  }

  private Integer buildCargo(
      LoadingPlanTankDetails stowage,
      Cargo cargo,
      CargoNominationDetail cargoNomination,
      Long portEta,
      Integer start,
      Integer end) {
    cargo.setCargoNominationId(stowage.getCargoNominationId());
    cargo.setCargoId(cargoNomination.getCargoId());
    cargo.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    cargo.setTankId(stowage.getTankId());
    cargo.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    cargo.setColor(cargoNomination.getColor());
    cargo.setName(cargoNomination.getCargoName());
    cargo.setAbbreviation(cargoNomination.getAbbreviation());
    cargo.setStart(portEta + (start * 60 * 1000));
    cargo.setEnd(portEta + (end * 60 * 1000));
    return end;
  }

  public void buildLoadingPlanSaveRequest(
      LoadingPlanAlgoRequest loadingPlanAlgoRequest,
      Long vesselId,
      Long loadingInfoId,
      Builder builder) {
    builder.setLoadingInfoId(loadingInfoId);
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
        .ifPresent(time -> paramBuilder.setTime(Integer.valueOf(time)));
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

    this.buildPortStowage(entry.getValue(), conditionType, builder);
    this.buildPortBallast(entry.getValue(), conditionType, builder);
    this.buildPortRob(entry.getValue(), conditionType, builder);
    this.buildPortStability(entry.getValue(), conditionType, builder);
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
    value
        .getLoadablePlanRoBDetails()
        .forEach(
            rob -> {
              LoadingPlanTankDetails.Builder robBuilder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(rob.getQuantityM3()).ifPresent(robBuilder::setQuantityM3);
              Optional.ofNullable(rob.getQuantityMT()).ifPresent(robBuilder::setQuantity);
              Optional.ofNullable(rob.getTankId()).ifPresent(robBuilder::setTankId);
              robBuilder.setConditionType(conditionType);
              builder.addPortLoadingPlanRobDetails(robBuilder.build());
            });
  }

  private void buildPortBallast(LoadingPlan value, Integer conditionType, Builder builder) {
    value
        .getLoadablePlanBallastDetails()
        .forEach(
            ballast -> {
              LoadingPlanTankDetails.Builder ballastBuilder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(ballast.getQuantityM3()).ifPresent(ballastBuilder::setQuantityM3);
              Optional.ofNullable(ballast.getQuantityMT()).ifPresent(ballastBuilder::setQuantity);
              Optional.ofNullable(ballast.getSounding()).ifPresent(ballastBuilder::setSounding);
              Optional.ofNullable(ballast.getTankId()).ifPresent(ballastBuilder::setTankId);
              ballastBuilder.setConditionType(conditionType);
              builder.addPortLoadingPlanBallastDetails(ballastBuilder.build());
            });
  }

  private void buildPortStowage(LoadingPlan value, Integer conditionType, Builder builder) {
    value
        .getLoadablePlanStowageDetails()
        .forEach(
            stowage -> {
              LoadingPlanTankDetails.Builder stowageBuilder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(stowage.getApi()).ifPresent(stowageBuilder::setApi);
              Optional.ofNullable(stowage.getCargoNominationId())
                  .ifPresent(stowageBuilder::setCargoNominationId);
              Optional.ofNullable(stowage.getQuantityM3()).ifPresent(stowageBuilder::setQuantityM3);
              Optional.ofNullable(stowage.getQuantityMT()).ifPresent(stowageBuilder::setQuantity);
              Optional.ofNullable(stowage.getTankId()).ifPresent(stowageBuilder::setTankId);
              Optional.ofNullable(stowage.getTemperature())
                  .ifPresent(stowageBuilder::setTemperature);
              Optional.ofNullable(stowage.getUllage()).ifPresent(stowageBuilder::setUllage);
              stowageBuilder.setConditionType(conditionType);
              builder.addPortLoadingPlanStowageDetails(stowageBuilder.build());
            });
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
                    .ifPresent(sequenceBuilder::setCargoLoadingRate1);
              if (sequence.getStageWiseCargoLoadingRates().size() > 1)
                Optional.ofNullable(sequence.getStageWiseCargoLoadingRates().get("1"))
                    .ifPresent(sequenceBuilder::setCargoLoadingRate2);
              this.buildBallastOperations(sequence.getBallast(), pumps, sequenceBuilder);
              this.buildDeballastingRates(sequence.getDeballastingRates(), sequenceBuilder);
              this.buildLoadingRates(sequence.getTankWiseCargoLoadingRates(), sequenceBuilder);
              this.buildLoadingPlanPortWiseDetails(
                  sequence.getLoadablePlanPortWiseDetails(), sequenceBuilder);
              Optional.ofNullable(sequence.getStage()).ifPresent(sequenceBuilder::setStageName);
              sequenceBuilder.setSequenceNumber(sequenceNumber.incrementAndGet());
              Optional.ofNullable(sequence.getTimeEnd())
                  .ifPresent(timeEnd -> sequenceBuilder.setEndTime(Integer.valueOf(timeEnd)));
              Optional.ofNullable(sequence.getTimeStart())
                  .ifPresent(timeStart -> sequenceBuilder.setStartTime(Integer.valueOf(timeStart)));
              Optional.ofNullable(sequence.getToLoadicator())
                  .ifPresent(sequenceBuilder::setToLoadicator);
              builder.addLoadingSequences(sequenceBuilder.build());
            });
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
          Optional.ofNullable(portWiseDetails.getTime())
              .ifPresent(time -> builder.setTime(Integer.valueOf(time)));
          sequenceBuilder.addLoadingPlanPortWiseDetails(builder.build());
        });
  }

  private void buildLoadingPlanStowageDetails(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    portWiseDetails
        .getLoadablePlanStowageDetails()
        .forEach(
            stowage -> {
              LoadingPlanTankDetails.Builder stowageBuilder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(stowage.getApi()).ifPresent(stowageBuilder::setApi);
              Optional.ofNullable(stowage.getCargoNominationId())
                  .ifPresent(stowageBuilder::setCargoNominationId);
              Optional.ofNullable(stowage.getQuantityM3()).ifPresent(stowageBuilder::setQuantityM3);
              Optional.ofNullable(stowage.getQuantityMT()).ifPresent(stowageBuilder::setQuantity);
              Optional.ofNullable(stowage.getTankId()).ifPresent(stowageBuilder::setTankId);
              Optional.ofNullable(stowage.getTemperature())
                  .ifPresent(stowageBuilder::setTemperature);
              Optional.ofNullable(stowage.getUllage()).ifPresent(stowageBuilder::setUllage);
              builder.addLoadingPlanStowageDetails(stowageBuilder.build());
            });
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
    portWiseDetails
        .getLoadablePlanRoBDetails()
        .forEach(
            rob -> {
              LoadingPlanTankDetails.Builder robBuilder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(rob.getQuantityM3()).ifPresent(robBuilder::setQuantityM3);
              Optional.ofNullable(rob.getQuantityMT()).ifPresent(robBuilder::setQuantity);
              Optional.ofNullable(rob.getTankId()).ifPresent(robBuilder::setTankId);
              builder.addLoadingPlanRobDetails(robBuilder.build());
            });
  }

  private void buildLoadingPlanBallastDetails(
      com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanPortWiseDetails portWiseDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
          builder) {
    portWiseDetails
        .getLoadablePlanBallastDetails()
        .forEach(
            ballast -> {
              LoadingPlanTankDetails.Builder ballastBuilder = LoadingPlanTankDetails.newBuilder();
              Optional.ofNullable(ballast.getQuantityM3()).ifPresent(ballastBuilder::setQuantityM3);
              Optional.ofNullable(ballast.getQuantityMT()).ifPresent(ballastBuilder::setQuantity);
              Optional.ofNullable(ballast.getSounding()).ifPresent(ballastBuilder::setSounding);
              Optional.ofNullable(ballast.getTankId()).ifPresent(ballastBuilder::setTankId);
              builder.addLoadingPlanBallastDetails(ballastBuilder.build());
            });
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
                    .ifPresent(time -> rateBuilder.setTime(Integer.valueOf(time)));
                builder.addDeballastingRates(rateBuilder.build());
              });
    }
  }

  private void buildLoadingRates(
      Map<String, String> tankWiseCargoLoadingRates,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence.Builder
          sequenceBuilder) {
    if (tankWiseCargoLoadingRates != null) {
      tankWiseCargoLoadingRates
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
                .ifPresent(timeEnd -> builder.setEndTime(Integer.valueOf(timeEnd)));
            Optional.ofNullable(ballastOperation.getTimeStart())
                .ifPresent(timeStart -> builder.setStartTime(Integer.valueOf(timeStart)));
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
