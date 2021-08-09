/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.LDIntactStability;
import com.cpdss.common.generated.LoadableStudy.LDStrength;
import com.cpdss.common.generated.LoadableStudy.LDtrim;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest;
import com.cpdss.common.generated.LoadableStudy.LoadicatorPatternDetails;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.Loadicator.BallastInfo;
import com.cpdss.common.generated.Loadicator.CargoInfo;
import com.cpdss.common.generated.Loadicator.LoadicatorReply;
import com.cpdss.common.generated.Loadicator.LoadicatorRequest;
import com.cpdss.common.generated.Loadicator.OtherTankInfo;
import com.cpdss.common.generated.LoadicatorServiceGrpc.LoadicatorServiceImplBase;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDetail;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadicatorintegration.domain.StowagePlanDetail;
import com.cpdss.loadicatorintegration.entity.CargoData;
import com.cpdss.loadicatorintegration.entity.IntactStability;
import com.cpdss.loadicatorintegration.entity.LoadicatorStrength;
import com.cpdss.loadicatorintegration.entity.LoadicatorTrim;
import com.cpdss.loadicatorintegration.entity.OtherTankDetails;
import com.cpdss.loadicatorintegration.entity.StowageDetails;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import com.cpdss.loadicatorintegration.repository.CargoDataRepository;
import com.cpdss.loadicatorintegration.repository.LoadicatorIntactStabilityRepository;
import com.cpdss.loadicatorintegration.repository.LoadicatorStrengthRepository;
import com.cpdss.loadicatorintegration.repository.LoadicatorTrimRepository;
import com.cpdss.loadicatorintegration.repository.StowageDetailsRepository;
import com.cpdss.loadicatorintegration.repository.StowagePlanRepository;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Log4j2
@GrpcService
public class LoadicatorService extends LoadicatorServiceImplBase {

  @Autowired private StowagePlanRepository stowagePlanRepository;
  @Autowired private LoadicatorTrimRepository loadicatorTrimRepository;
  @Autowired private LoadicatorStrengthRepository loadicatorStrengthRepository;
  @Autowired private LoadicatorIntactStabilityRepository loadicatorIntactStabilityRepository;
  @Autowired private CargoDataRepository cargoDataRepository;
  @Autowired private StowageDetailsRepository stowageDetailsRepository;

  @Autowired private SimpleAsyncTaskExecutor taskExecutor;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyService;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceBlockingStub loadingPlanService;

  private static final String FAILED = "FAILED";
  private static final String SUCCESS = "SUCCESS";

  @Override
  public void saveLoadicatorInfo(
      LoadicatorRequest request, StreamObserver<LoadicatorReply> responseObserver) {
    com.cpdss.common.generated.Loadicator.LoadicatorReply.Builder replyBuilder =
        LoadicatorReply.newBuilder();
    try {
      log.info("inside saveLoadicatorInfo");
      AtomicLong cargoId = new AtomicLong(0L);
      List<StowagePlan> stowagePlanList = new ArrayList<>();
      for (com.cpdss.common.generated.Loadicator.StowagePlan plan :
          request.getStowagePlanDetailsList()) {
        StowagePlan entity = this.buildStowagePlan(plan);
        if (!plan.getStowageDetailsList().isEmpty()) {
          entity.setStowageDetails(new HashSet<>());
          entity.getStowageDetails().addAll(this.buildStowageDetailSet(plan, entity));
        }
        entity.setOtherTankDetails(new HashSet<>());
        if (!plan.getBallastInfoList().isEmpty()) {
          entity.getOtherTankDetails().addAll(this.buildBallastDetailSet(plan, entity));
        }
        if (!plan.getOtherTankInfoList().isEmpty()) {
          entity.getOtherTankDetails().addAll(this.buildOtherTankDetailSet(plan, entity));
        }
        if (!plan.getCargoInfoList().isEmpty()) {
          entity.setCargoData(new HashSet<>());
          entity.getCargoData().addAll(this.buildCargoDataSet(plan, entity, cargoId));
        }
        stowagePlanList.add(entity);
      }

      this.stowagePlanRepository.saveAll(stowagePlanList);

      this.updateStowageDetails(stowagePlanList);

      this.taskExecutor.execute(
          () -> {
            try {
              this.getStatus(stowagePlanList, request);
            } catch (InterruptedException e) {
              log.error("Encounted error while checking loadicator status");
              replyBuilder.setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Encounted error while checking loadicator status")
                      .setStatus(FAILED)
                      .build());
            }
          });

      replyBuilder
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
          .build();
    } catch (Exception e) {
      log.error("Error saving stowage plan", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving stowage plan")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  private void updateStowageDetails(List<StowagePlan> stowagePlanList) {
    stowagePlanList.forEach(
        stowagePlan -> {
          List<CargoData> cargoDatas = this.cargoDataRepository.findByStowagePlan(stowagePlan);
          cargoDatas.forEach(
              cargoData -> {
                this.stowageDetailsRepository.updateCargoIdInStowageDetailsByStowagePlan(
                    stowagePlan, cargoData.getCargoId(), cargoData.getCargoAbbrev());
              });
        });
  }

  public LoadicatorDataReply getLoadicatorDatas(LoadicatorDataRequest loadableStudyrequest) {
    try {
      return Context.current()
          .fork()
          .call(() -> this.loadableStudyService.getLoadicatorData(loadableStudyrequest));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return this.loadableStudyService.getLoadicatorData(loadableStudyrequest);
    }
  }

  private Set<CargoData> buildCargoDataSet(
      com.cpdss.common.generated.Loadicator.StowagePlan plan,
      StowagePlan entity,
      AtomicLong cargoId) {
    Set<CargoData> set = new HashSet<>();
    for (CargoInfo cargo : plan.getCargoInfoList()) {
      CargoData cargoData = new CargoData();
      cargoData.setApi(StringUtils.isEmpty(cargo.getApi()) ? null : new BigDecimal(cargo.getApi()));
      cargoData.setCargoId(cargoId.incrementAndGet());
      cargoData.setCargoName(
          StringUtils.isEmpty(cargo.getCargoName()) ? null : cargo.getCargoName());
      cargoData.setCargoAbbrev(
          StringUtils.isEmpty(cargo.getCargoAbbrev()) ? null : cargo.getCargoAbbrev());
      cargoData.setDegf(
          StringUtils.isEmpty(cargo.getStandardTemp())
              ? null
              : new BigDecimal(cargo.getStandardTemp()));
      cargoData.setGrade(StringUtils.isEmpty(cargo.getGrade()) ? null : cargo.getGrade());
      cargoData.setDensity(
          StringUtils.isEmpty(cargo.getDensity()) ? null : new BigDecimal(cargo.getDensity()));
      cargoData.setDegc(
          StringUtils.isEmpty(cargo.getDegc()) ? null : new BigDecimal(cargo.getDegc()));
      cargoData.setStowagePlan(entity);
      set.add(cargoData);
    }
    return set;
  }

  private Set<OtherTankDetails> buildOtherTankDetailSet(
      com.cpdss.common.generated.Loadicator.StowagePlan plan, StowagePlan entity) {
    Set<OtherTankDetails> set = new HashSet<>();
    for (OtherTankInfo otherTank : plan.getOtherTankInfoList()) {
      OtherTankDetails otherTankDetails = new OtherTankDetails();
      otherTankDetails.setTankId(
          StringUtils.isEmpty(otherTank.getTankId()) ? null : otherTank.getTankId());
      otherTankDetails.setTankName(
          StringUtils.isEmpty(otherTank.getTankName()) ? null : otherTank.getTankName());
      otherTankDetails.setQuantity(
          StringUtils.isEmpty(otherTank.getQuantity())
              ? null
              : new BigDecimal(otherTank.getQuantity()));
      otherTankDetails.setShortName(
          StringUtils.isEmpty(otherTank.getShortName()) ? null : otherTank.getShortName());
      otherTankDetails.setStowagePlan(entity);
      set.add(otherTankDetails);
    }
    return set;
  }

  private Set<OtherTankDetails> buildBallastDetailSet(
      com.cpdss.common.generated.Loadicator.StowagePlan plan, StowagePlan entity) {
    Set<OtherTankDetails> set = new HashSet<>();
    for (BallastInfo ballast : plan.getBallastInfoList()) {
      OtherTankDetails otherTankDetails = new OtherTankDetails();
      otherTankDetails.setTankId(
          StringUtils.isEmpty(ballast.getTankId()) ? null : ballast.getTankId());
      otherTankDetails.setTankName(
          StringUtils.isEmpty(ballast.getTankName()) ? null : ballast.getTankName());
      otherTankDetails.setQuantity(
          StringUtils.isEmpty(ballast.getQuantity())
              ? null
              : new BigDecimal(ballast.getQuantity()));
      otherTankDetails.setShortName(
          StringUtils.isEmpty(ballast.getShortName()) ? null : ballast.getShortName());
      otherTankDetails.setStowagePlan(entity);
      set.add(otherTankDetails);
    }
    return set;
  }

  private Set<StowageDetails> buildStowageDetailSet(
      com.cpdss.common.generated.Loadicator.StowagePlan plan, StowagePlan entity) {
    Set<StowageDetails> set = new HashSet<>();
    for (com.cpdss.common.generated.Loadicator.StowageDetails stowageInfo :
        plan.getStowageDetailsList()) {
      StowageDetails stowageDetails = new StowageDetails();
      stowageDetails.setCargoBookId(
          StringUtils.isEmpty(stowageInfo.getCargoBookId()) ? null : stowageInfo.getCargoBookId());
      stowageDetails.setCargoId(
          StringUtils.isEmpty(stowageInfo.getCargoId()) ? null : stowageInfo.getCargoId());
      stowageDetails.setCargoName(
          StringUtils.isEmpty(stowageInfo.getCargoName()) ? null : stowageInfo.getCargoName());
      stowageDetails.setQuantity(
          StringUtils.isEmpty(stowageInfo.getQuantity())
              ? null
              : new BigDecimal(stowageInfo.getQuantity()));
      stowageDetails.setShortName(
          StringUtils.isEmpty(stowageInfo.getShortName()) ? null : stowageInfo.getShortName());
      stowageDetails.setSpecificGravity(
          StringUtils.isEmpty(stowageInfo.getSpecificGravity())
              ? null
              : new BigDecimal(stowageInfo.getSpecificGravity()));
      stowageDetails.setTankId(
          StringUtils.isEmpty(stowageInfo.getTankId()) ? null : stowageInfo.getTankId());
      stowageDetails.setTankName(
          StringUtils.isEmpty(stowageInfo.getTankName()) ? null : stowageInfo.getTankName());
      stowageDetails.setStowagePlan(entity);
      set.add(stowageDetails);
    }
    return set;
  }

  private StowagePlan buildStowagePlan(
      com.cpdss.common.generated.Loadicator.StowagePlan stowagePlanInfo) {
    StowagePlan stowagePlan = new StowagePlan();
    stowagePlan.setVesselXId(
        StringUtils.isEmpty(stowagePlanInfo.getVesselId()) ? null : stowagePlanInfo.getVesselId());
    stowagePlan.setBookingListId(
        StringUtils.isEmpty(stowagePlanInfo.getBookingListId())
            ? null
            : stowagePlanInfo.getBookingListId());
    stowagePlan.setCalCount(
        StringUtils.isEmpty(stowagePlanInfo.getCalCount()) ? null : stowagePlanInfo.getCalCount());
    stowagePlan.setCompanyXId(
        StringUtils.isEmpty(stowagePlanInfo.getCompanyId())
            ? null
            : stowagePlanInfo.getCompanyId());
    stowagePlan.setDamageCal(
        StringUtils.isEmpty(stowagePlanInfo.getDamageCal())
            ? null
            : stowagePlanInfo.getDamageCal());
    stowagePlan.setDataSave(
        StringUtils.isEmpty(stowagePlanInfo.getDataSave()) ? null : stowagePlanInfo.getDataSave());
    stowagePlan.setDeadweightConstant(
        StringUtils.isEmpty(stowagePlanInfo.getDeadweightConstant())
            ? null
            : (long) (Double.parseDouble(stowagePlanInfo.getDeadweightConstant())));
    stowagePlan.setPortCode(
        StringUtils.isEmpty(stowagePlanInfo.getPortCode()) ? null : stowagePlanInfo.getPortCode());

    stowagePlan.setPortId(
        StringUtils.isEmpty(stowagePlanInfo.getPortId()) ? null : stowagePlanInfo.getPortId());
    stowagePlan.setSynopticalId(
        StringUtils.isEmpty(stowagePlanInfo.getSynopticalId())
            ? null
            : stowagePlanInfo.getSynopticalId());
    stowagePlan.setProvisionalConstant(
        StringUtils.isEmpty(stowagePlanInfo.getProvisionalConstant())
            ? null
            : (long) (Double.parseDouble(stowagePlanInfo.getProvisionalConstant())));

    stowagePlan.setSaveMessage(
        StringUtils.isEmpty(stowagePlanInfo.getSaveMessage())
            ? null
            : stowagePlanInfo.getSaveMessage());
    stowagePlan.setStowageId(
        StringUtils.isEmpty(stowagePlanInfo.getStowageId())
            ? null
            : stowagePlanInfo.getStowageId());
    stowagePlan.setBookingListId(
        StringUtils.isEmpty(stowagePlanInfo.getBookingListId())
            ? null
            : stowagePlanInfo.getBookingListId());
    stowagePlan.setStatus(
        StringUtils.isEmpty(stowagePlanInfo.getStatus()) ? null : stowagePlanInfo.getStatus());
    stowagePlan.setVesselCode(
        StringUtils.isEmpty(stowagePlanInfo.getVesselCode())
            ? null
            : stowagePlanInfo.getVesselCode());
    stowagePlan.setProcessId(stowagePlanInfo.getProcessId());
    stowagePlan.setSeaWater(
        StringUtils.isEmpty(stowagePlanInfo.getSeaWaterDensity())
            ? null
            : new BigDecimal(stowagePlanInfo.getSeaWaterDensity()));
    return stowagePlan;
  }

  public void getStatus(List<StowagePlan> stowagePlans, LoadicatorRequest request)
      throws InterruptedException {
    boolean status = false;
    do {
      if (request.getTypeId() == 1) {
        log.info(
            "Checking loadicator status of "
                + (!request.getIsPattern()
                    ? ("Loadable Study " + request.getStowagePlanDetails(0).getBookingListId())
                    : ("Loadable Pattern " + request.getStowagePlanDetails(0).getStowageId())));
      } else if (request.getTypeId() == 2) {
        log.info(
            "Checking loadicator status of Loading Information "
                + request.getStowagePlanDetails(0).getBookingListId());
      }

      Thread.sleep(10000);
      List<Long> stowagePlanIds =
          stowagePlans.stream().map(StowagePlan::getId).collect(Collectors.toList());
      List<StowagePlan> stowagePlanList = this.stowagePlanRepository.findByIdIn(stowagePlanIds);
      Long statusCount =
          stowagePlanList.stream().filter(plan -> plan.getStatus().equals(3L)).count();
      if (statusCount.equals(stowagePlanList.stream().count())) {
        status = true;
        if (request.getTypeId() == 1) {
          LoadicatorDataRequest loadableStudyrequest =
              this.sendLoadicatorData(stowagePlanList, request.getIsPattern());
          log.info(
              "Loadicator check completed for "
                  + (!request.getIsPattern()
                      ? ("Loadable Study " + request.getStowagePlanDetails(0).getBookingListId())
                      : ("Loadable Pattern " + request.getStowagePlanDetails(0).getStowageId())));

          this.getLoadicatorDatas(loadableStudyrequest);
        } else if (request.getTypeId() == 2) {
          log.info(
              "Loadicator check completed for loading information {}",
              request.getStowagePlanDetails(0).getBookingListId());
          LoadingInfoLoadicatorDataRequest loadingInformationRequest =
              this.buildLoadingInfoLoadicatorData(stowagePlanList);
          this.getLoadingInfoLoadicatorData(loadingInformationRequest);
        }
      }
    } while (!status);
  }

  private void getLoadingInfoLoadicatorData(
      LoadingInfoLoadicatorDataRequest loadingInformationRequest) {
    this.loadingPlanService.getLoadicatorData(loadingInformationRequest);
  }

  private LoadingInfoLoadicatorDataRequest buildLoadingInfoLoadicatorData(
      List<StowagePlan> stowagePlanList) {
    LoadingInfoLoadicatorDataRequest.Builder builder =
        LoadingInfoLoadicatorDataRequest.newBuilder();
    builder.setLoadingInformationId(stowagePlanList.get(0).getBookingListId());
    builder.setProcessId(stowagePlanList.get(0).getProcessId());
    stowagePlanList.forEach(
        stowagePlan -> {
          LoadingInfoLoadicatorDetail.Builder detailBuilder =
              LoadingInfoLoadicatorDetail.newBuilder();
          this.buildLoadingInfoLoadicatorDetails(stowagePlan, detailBuilder);
          builder.addLoadingInfoLoadicatorDetails(detailBuilder.build());
        });
    return builder.build();
  }

  private void buildLoadingInfoLoadicatorDetails(
      StowagePlan stowagePlan,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDetail.Builder
          detailBuilder) {

    detailBuilder.setTime(stowagePlan.getStowageId().intValue());

    LoadicatorTrim loadicatorTrim =
        loadicatorTrimRepository.findByStowagePlanId(stowagePlan.getId());
    LDtrim.Builder trimBuilder = this.buildLoadicatorTrimDetails(loadicatorTrim);
    detailBuilder.setLDtrim(trimBuilder.build());

    LoadicatorStrength loadicatorStrength =
        loadicatorStrengthRepository.findByStowagePlanId(stowagePlan.getId());
    LDStrength.Builder strengthBuilder = this.buildStrengthDetails(loadicatorStrength);
    detailBuilder.setLDStrength(strengthBuilder.build());

    IntactStability intactStability =
        loadicatorIntactStabilityRepository.findByStowagePlanId(stowagePlan.getId());
    LDIntactStability.Builder intactStabilityBuilder = this.buildStabilityDetails(intactStability);
    detailBuilder.setLDIntactStability(intactStabilityBuilder.build());
  }

  /**
   * Build request
   *
   * @param stowagePlanList
   * @return
   */
  public LoadicatorDataRequest sendLoadicatorData(
      List<StowagePlan> stowagePlanList, Boolean isPattern) {
    LoadicatorDataRequest.Builder request = LoadicatorDataRequest.newBuilder();
    if (null != stowagePlanList) {
      Map<Long, List<StowagePlan>> stowagePlanMap = this.buildStowagePlanMap(stowagePlanList);
      for (Map.Entry<Long, List<StowagePlan>> entry : stowagePlanMap.entrySet()) {
        LoadicatorPatternDetails.Builder builder = LoadicatorPatternDetails.newBuilder();
        builder.setLoadablePatternId(entry.getKey());
        List<Long> planIds =
            entry.getValue().stream().map(StowagePlan::getId).collect(Collectors.toList());
        this.buildLoadicatorLdTrimData(builder, planIds);
        this.buildLoadicatorStrengthData(builder, planIds);
        this.buildLoadicatorIntactStabilityData(builder, planIds);
        request.addLoadicatorPatternDetails(builder.build());
      }
    }
    request.setProcessId(stowagePlanList != null ? stowagePlanList.get(0).getProcessId() : "");
    request.setLoadableStudyId(
        stowagePlanList != null ? stowagePlanList.get(0).getBookingListId() : 0);
    request.setIsPattern(isPattern);
    return request.build();
  }

  private void buildLoadicatorIntactStabilityData(
      LoadicatorPatternDetails.Builder builder, List<Long> planIds) {
    List<IntactStability> stabilityValues =
        this.loadicatorIntactStabilityRepository.findByStowagePlanIdIn(planIds);
    stabilityValues.forEach(
        stability -> {
          LDIntactStability.Builder ldstability = this.buildStabilityDetails(stability);
          builder.addLDIntactStability(ldstability);
        });
  }

  private void buildLoadicatorStrengthData(
      LoadicatorPatternDetails.Builder builder, List<Long> planIds) {
    List<LoadicatorStrength> ldStrengthValues =
        this.loadicatorStrengthRepository.findByStowagePlanIdIn(planIds);
    ldStrengthValues.forEach(
        strength -> {
          LDStrength.Builder ldStrength = this.buildStrengthDetails(strength);
          builder.addLDStrength(ldStrength);
        });
  }

  private void buildLoadicatorLdTrimData(
      LoadicatorPatternDetails.Builder builder, List<Long> planIds) {
    List<LoadicatorTrim> trimValues = this.loadicatorTrimRepository.findByStowagePlanIdIn(planIds);
    trimValues.forEach(
        trim -> {
          LDtrim.Builder ldTrim = this.buildLoadicatorTrimDetails(trim);
          builder.addLDtrim(ldTrim);
        });
  }

  private Map<Long, List<StowagePlan>> buildStowagePlanMap(List<StowagePlan> stowagePlanList) {
    Map<Long, List<StowagePlan>> map = new HashMap<>();
    for (StowagePlan plan : stowagePlanList) {
      if (null == map.get(plan.getStowageId())) {
        List<StowagePlan> planList = new ArrayList<>();
        planList.add(plan);
        map.put(plan.getStowageId(), planList);
      } else {
        map.get(plan.getStowageId()).add(plan);
      }
    }
    return map;
  }

  private LDIntactStability.Builder buildStabilityDetails(IntactStability stability) {
    LDIntactStability.Builder ldStability = LDIntactStability.newBuilder();

    StowagePlanDetail stowageDetail =
        this.stowagePlanRepository.findPortForStability(stability.getStowagePlanId());
    ldStability.setPortId(stowageDetail.getPortId());
    ldStability.setId(stability.getId());
    ldStability.setSynopticalId(stowageDetail.getSynopticalId());
    Optional.ofNullable(stability.getStowagePlanId()).ifPresent(ldStability::setStowagePlanId);
    Optional.ofNullable(stability.getBigintialGomvalue())
        .ifPresent(item -> ldStability.setBigintialGomValue(String.valueOf(item)));
    Optional.ofNullable(stability.getBigintialGomjudgement())
        .ifPresent(item -> ldStability.setBigIntialGomJudgement(String.valueOf(item)));
    Optional.ofNullable(stability.getMaximumRightingLeverValue())
        .ifPresent(item -> ldStability.setMaximumRightingLeverValue(String.valueOf(item)));
    Optional.ofNullable(stability.getMaximumRightingLeverJudgement())
        .ifPresent(item -> ldStability.setMaximumRightingLeverJudgement(String.valueOf(item)));
    Optional.ofNullable(stability.getAngleatMaxrLeverValue())
        .ifPresent(item -> ldStability.setAngleatmaxrleverValue(String.valueOf(item)));
    Optional.ofNullable(stability.getAngleatMaxrLeverJudgement())
        .ifPresent(item -> ldStability.setAngleatmaxrleverJudgement(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfStability_0_30_Value())
        .ifPresent(item -> ldStability.setAreaofStability030Value(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfstability_0_30_Judgement())
        .ifPresent(item -> ldStability.setAreaofStability030Judgement(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfStability_0_40_Value())
        .ifPresent(item -> ldStability.setAreaofStability040Value(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfStability_0_40_Judgement())
        .ifPresent(item -> ldStability.setAreaofStability040Judgement(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfStability_30_40_Value())
        .ifPresent(item -> ldStability.setAreaofStability3040Value(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfStability_30_40_Judgement())
        .ifPresent(item -> ldStability.setAreaofStability3040Judgement(String.valueOf(item)));
    Optional.ofNullable(stability.getHeelBySteadyWindValue())
        .ifPresent(item -> ldStability.setHeelBySteadyWindValue(String.valueOf(item)));
    Optional.ofNullable(stability.getHeelBySteadyWindJudgement())
        .ifPresent(item -> ldStability.setHeelBySteadyWindJudgement(String.valueOf(item)));
    Optional.ofNullable(stability.getAreaOfStability_30_40_Judgement())
        .ifPresent(item -> ldStability.setAreaofStability3040Judgement(String.valueOf(item)));
    Optional.ofNullable(stability.getStabilityAreaBaValue())
        .ifPresent(item -> ldStability.setStabilityAreaBaValue(String.valueOf(item)));
    Optional.ofNullable(stability.getStabilityAreaBaJudgement())
        .ifPresent(item -> ldStability.setStabilityAreaBaJudgement(String.valueOf(item)));
    Optional.ofNullable(stability.getGmAllowableCurveCheckValue())
        .ifPresent(item -> ldStability.setGmAllowableCurveCheckValue(String.valueOf(item)));
    Optional.ofNullable(stability.getGm_allowableCurveCheckJudgement())
        .ifPresent(item -> ldStability.setGmAllowableCurveCheckJudgement(String.valueOf(item)));
    Optional.ofNullable(stability.getErrorStatus())
        .ifPresent(item -> ldStability.setErrorStatus(item));
    Optional.ofNullable(stability.getErrorDetails())
        .ifPresent(item -> ldStability.setErrorDetails(String.valueOf(item)));
    Optional.ofNullable(stability.getMessageText())
        .ifPresent(item -> ldStability.setMessageText(String.valueOf(item)));
    return ldStability;
  }

  LDStrength.Builder buildStrengthDetails(LoadicatorStrength strength) {
    LDStrength.Builder ldStrength = LDStrength.newBuilder();

    StowagePlanDetail stowageDetail =
        this.stowagePlanRepository.findPortForStrength(strength.getStowagePlanId());
    ldStrength.setPortId(stowageDetail.getPortId());
    ldStrength.setSynopticalId(stowageDetail.getSynopticalId());
    ldStrength.setId(strength.getId());
    Optional.ofNullable(strength.getStowagePlanId()).ifPresent(ldStrength::setStowagePlanId);
    Optional.ofNullable(strength.getShearingForcePresentValue())
        .ifPresent(item -> ldStrength.setShearingForcePersentValue(String.valueOf(item)));
    Optional.ofNullable(strength.getShearingForceJudgement())
        .ifPresent(item -> ldStrength.setShearingForceJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getSfFrameNumber())
        .ifPresent(item -> ldStrength.setSfFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getSfSideShellValue())
        .ifPresent(item -> ldStrength.setSfSideShellValue(String.valueOf(item)));
    Optional.ofNullable(strength.getSfSideShellJudgement())
        .ifPresent(item -> ldStrength.setSfSideShellJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getSfSideShellFrameNumber())
        .ifPresent(item -> ldStrength.setSfSideShellFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getSfHopperValue())
        .ifPresent(item -> ldStrength.setSfHopperValue(String.valueOf(item)));
    Optional.ofNullable(strength.getSfHopperJudgement())
        .ifPresent(item -> ldStrength.setSfHopperJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getSfHopperJudgement())
        .ifPresent(item -> ldStrength.setSfHopperJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getSfHopperFrameNumber())
        .ifPresent(item -> ldStrength.setSfHopperFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getOuterLongiBhdFrameNumber())
        .ifPresent(item -> ldStrength.setOuterLongiBhdFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getOuterLongiBhdValue())
        .ifPresent(item -> ldStrength.setOuterLongiBhdValue(String.valueOf(item)));
    Optional.ofNullable(strength.getInnerLongiBhdFrameNumber())
        .ifPresent(item -> ldStrength.setInnerLongiBhdFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getInnerLongiBhdJudgement())
        .ifPresent(item -> ldStrength.setInnerLongiBhdJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getInnerLongiBhdValue())
        .ifPresent(item -> ldStrength.setInnerLongiBhdValue(String.valueOf(item)));
    Optional.ofNullable(strength.getInnerLongiBhdJudgement())
        .ifPresent(item -> ldStrength.setInnerLongiBhdJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getBendingMomentPersentValue())
        .ifPresent(item -> ldStrength.setBendingMomentPersentValue(String.valueOf(item)));
    Optional.ofNullable(strength.getBendingMomentPersentJudgement())
        .ifPresent(item -> ldStrength.setBendingMomentPersentJudgement(String.valueOf(item)));
    Optional.ofNullable(strength.getBendingMomentPersentFrameNumber())
        .ifPresent(item -> ldStrength.setBendingMomentPersentFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getBendingMomentPersentFrameNumber())
        .ifPresent(item -> ldStrength.setBendingMomentPersentFrameNumber(String.valueOf(item)));
    Optional.ofNullable(strength.getErrorStatus())
        .ifPresent(item -> ldStrength.setErrorStatus(item));
    Optional.ofNullable(strength.getErrorDetails())
        .ifPresent(item -> ldStrength.setErrorDetails(String.valueOf(item)));
    Optional.ofNullable(strength.getMessageText())
        .ifPresent(item -> ldStrength.setMessageText(String.valueOf(item)));
    return ldStrength;
  }

  LDtrim.Builder buildLoadicatorTrimDetails(LoadicatorTrim trim) {
    LDtrim.Builder ldTrim = LDtrim.newBuilder();

    StowagePlanDetail stowageDetail =
        this.stowagePlanRepository.findPortForTrim(trim.getStowagePlanId());
    ldTrim.setPortId(stowageDetail.getPortId());
    ldTrim.setSynopticalId(stowageDetail.getSynopticalId());
    ldTrim.setId(trim.getId());
    Optional.ofNullable(trim.getStowagePlanId()).ifPresent(ldTrim::setStowagePlanId);
    Optional.ofNullable(trim.getAftDraft())
        .ifPresent(item -> ldTrim.setAftDraftValue(String.valueOf(item)));
    Optional.ofNullable(trim.getForeDraft())
        .ifPresent(item -> ldTrim.setForeDraftValue(String.valueOf(item)));
    Optional.ofNullable(trim.getTrim())
        .ifPresent(item -> ldTrim.setTrimValue(String.valueOf(item)));
    Optional.ofNullable(trim.getHeel())
        .ifPresent(item -> ldTrim.setHeelValue(String.valueOf(item)));
    Optional.ofNullable(trim.getMeanDraft())
        .ifPresent(item -> ldTrim.setMeanDraftValue(String.valueOf(item)));
    Optional.ofNullable(trim.getMeanDraftJudgement())
        .ifPresent(item -> ldTrim.setMeanDraftJudgement(String.valueOf(item)));
    Optional.ofNullable(trim.getDisplacementJudgement())
        .ifPresent(item -> ldTrim.setDisplacementJudgement(String.valueOf(item)));
    Optional.ofNullable(trim.getDisplacementValue())
        .ifPresent(item -> ldTrim.setDisplacementValue(String.valueOf(item)));
    Optional.ofNullable(trim.getMaximumDraft())
        .ifPresent(item -> ldTrim.setMaximumDraftValue(String.valueOf(item)));
    Optional.ofNullable(trim.getMaximumDraftJudgement())
        .ifPresent(item -> ldTrim.setMaximumDraftJudgement(String.valueOf(item)));
    Optional.ofNullable(trim.getAirDraft())
        .ifPresent(item -> ldTrim.setAirDraftValue(String.valueOf(item)));
    Optional.ofNullable(trim.getAirDraftJudgement())
        .ifPresent(item -> ldTrim.setAirDraftJudgement(String.valueOf(item)));
    Optional.ofNullable(trim.getMinimumForeDraftInRoughWeatherValue())
        .ifPresent(item -> ldTrim.setMinimumForeDraftInRoughWeatherValue(String.valueOf(item)));
    Optional.ofNullable(trim.getMinimumForeDraftInRoughWeatherValueJudgement())
        .ifPresent(item -> ldTrim.setMinimumForeDraftInRoughWeatherJudgement(String.valueOf(item)));
    Optional.ofNullable(trim.getMaximumAllowableVisibility())
        .ifPresent(item -> ldTrim.setMaximumAllowableVisibility(String.valueOf(item)));
    Optional.ofNullable(trim.getMaximumAllowableJudgement())
        .ifPresent(item -> ldTrim.setMaximumAllowableJudement(String.valueOf(item)));
    Optional.ofNullable(trim.getErrorStatus()).ifPresent(item -> ldTrim.setErrorStatus(item));
    Optional.ofNullable(trim.getErrorDetails())
        .ifPresent(item -> ldTrim.setErrorDetails(String.valueOf(item)));
    Optional.ofNullable(trim.getMessageText())
        .ifPresent(item -> ldTrim.setMessageText(String.valueOf(item)));
    Optional.ofNullable(trim.getDeflection())
        .ifPresent(item -> ldTrim.setDeflection(String.valueOf(item)));
    return ldTrim;
  }

  public LoadicatorDataReply getLoadicatorData(LoadicatorDataRequest build) {
    return loadableStudyService.getLoadicatorData(build);
  }
}
