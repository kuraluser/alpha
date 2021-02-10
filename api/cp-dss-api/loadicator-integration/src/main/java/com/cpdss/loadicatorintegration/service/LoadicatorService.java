/* Licensed under Apache-2.0 */
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
import com.cpdss.common.generated.Loadicator.StowageDetailsInfo;
import com.cpdss.common.generated.LoadicatorServiceGrpc.LoadicatorServiceImplBase;
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
import com.cpdss.loadicatorintegration.repository.OtherTankDetailsRepository;
import com.cpdss.loadicatorintegration.repository.StowageDetailsRepository;
import com.cpdss.loadicatorintegration.repository.StowagePlanRepository;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Log4j2
@Transactional
@GrpcService
public class LoadicatorService extends LoadicatorServiceImplBase {

  @Autowired private StowagePlanRepository stowagePlanRepository;
  @Autowired private StowageDetailsRepository stowageDetailsRepository;
  @Autowired private CargoDataRepository cargoDataRepository;
  @Autowired private OtherTankDetailsRepository otherTankDetailsRepository;
  @Autowired private LoadicatorTrimRepository loadicatorTrimRepository;
  @Autowired private LoadicatorStrengthRepository loadicatorStrengthRepository;
  @Autowired private LoadicatorIntactStabilityRepository loadicatorIntactStabilityRepository;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyService;

  private static final String FAILED = "FAILED";
  private static final String SUCCESS = "SUCCESS";

  @Transactional
  @Override
  public void saveLoadicatorInfo(
      LoadicatorRequest request, StreamObserver<LoadicatorReply> responseObserver) {
    com.cpdss.common.generated.Loadicator.LoadicatorReply.Builder replyBuilder =
        LoadicatorReply.newBuilder();
    try {
      List<StowagePlan> stowagePlan = new ArrayList<StowagePlan>();
      List<StowagePlan> stowagePlanEntityList = new ArrayList<StowagePlan>();
      if (null != request.getStowagePlanDetailsList()) {
        stowagePlan = this.buildStowagePlan(request);
      }
      for (StowagePlan stowage : stowagePlan) {
        StowagePlan newStowage = this.stowagePlanRepository.save(stowage);
        stowagePlanEntityList.add(newStowage);

        if (request.getStowageDetailsInfoList() != null) {

          request
              .getStowageDetailsInfoList()
              .forEach(
                  details -> {
                    if (Long.valueOf(details.getPortId()).equals(newStowage.getPortId())
                        && Long.valueOf(details.getStowageId()).equals(newStowage.getStowageId())) {
                      StowageDetails stowageDetail =
                          this.buildStowageDetails(details, newStowage.getId());
                      stowageDetail = this.stowageDetailsRepository.save(stowageDetail);
                    }
                  });
        }

        if (null != request.getCargoInfoList()) {
          request
              .getCargoInfoList()
              .forEach(
                  cargoInfo -> {
                    if (Long.valueOf(cargoInfo.getPortId()).equals(newStowage.getPortId())
                        && Long.valueOf(cargoInfo.getStowageId())
                            .equals(newStowage.getStowageId())) {
                      CargoData cargoData = this.buildCargoData(cargoInfo, newStowage.getId());
                      this.cargoDataRepository.save(cargoData);
                    }
                  });
        }

        if (null != request.getOtherTankInfoList()) {
          request
              .getOtherTankInfoList()
              .forEach(
                  otherTanks -> {
                    if (Long.valueOf(otherTanks.getPortId()).equals(newStowage.getPortId())
                        && Long.valueOf(otherTanks.getLoadableStudyId())
                            .equals(newStowage.getBookingListId())) {
                      OtherTankDetails otherTankDetails =
                          this.buildOtherTankInfo(otherTanks, newStowage.getId());
                      this.otherTankDetailsRepository.save(otherTankDetails);
                    }
                  });
        }
        if (null != request.getBallastInfoList()) {
          request
              .getBallastInfoList()
              .forEach(
                  ballast -> {
                    if (Long.valueOf(ballast.getPortId()).equals(newStowage.getPortId())
                        && Long.valueOf(ballast.getStowageId()).equals(newStowage.getStowageId())) {
                      OtherTankDetails ballastInfo = this.buildBallast(ballast, newStowage.getId());
                      this.otherTankDetailsRepository.save(ballastInfo);
                    }
                  });
        }
      }

      Boolean status = this.getStatus(stowagePlan);
      if (status) {
        replyBuilder
            .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
            .build();

        LoadicatorDataRequest.Builder loadableStudyrequest =
            this.sendLoadicatorData(stowagePlanEntityList);
        LoadicatorDataReply loadicatorDataReply =
            loadableStudyService.getLoadicatorData(loadableStudyrequest.build());
      }

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

  private OtherTankDetails buildOtherTankInfo(OtherTankInfo otherTank, Long id) {

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
    otherTankDetails.setStowagePlanId(id);

    return otherTankDetails;
  }

  private CargoData buildCargoData(CargoInfo cargo, Long id) {
    CargoData cargoData = new CargoData();
    cargoData.setApi(StringUtils.isEmpty(cargo.getApi()) ? null : new BigDecimal(cargo.getApi()));
    // cargoData.setCargoId(StringUtils.isEmpty(cargo.getCargoId()) ? null : cargo.getCargoId());
    cargoData.setCargoName(StringUtils.isEmpty(cargo.getCargoName()) ? null : cargo.getCargoName());
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

    cargoData.setStowagePlanId(id);
    return cargoData;
  }

  private List<StowagePlan> buildStowagePlan(LoadicatorRequest request) {
    List<StowagePlan> stowagePlanList = new ArrayList<StowagePlan>();
    request
        .getStowagePlanDetailsList()
        .forEach(
            stowagePlanInfo -> {
              StowagePlan stowagePlan = new StowagePlan();
              stowagePlan.setVesselXId(
                  StringUtils.isEmpty(stowagePlanInfo.getVesselId())
                      ? null
                      : stowagePlanInfo.getVesselId());
              stowagePlan.setBookingListId(
                  StringUtils.isEmpty(stowagePlanInfo.getBookingListId())
                      ? null
                      : stowagePlanInfo.getBookingListId());
              stowagePlan.setCalCount(
                  StringUtils.isEmpty(stowagePlanInfo.getCalCount())
                      ? null
                      : stowagePlanInfo.getCalCount());
              stowagePlan.setCompanyXId(
                  StringUtils.isEmpty(stowagePlanInfo.getCompanyId())
                      ? null
                      : stowagePlanInfo.getCompanyId());
              stowagePlan.setDamageCal(
                  StringUtils.isEmpty(stowagePlanInfo.getDamageCal())
                      ? null
                      : stowagePlanInfo.getDamageCal());
              stowagePlan.setDataSave(
                  StringUtils.isEmpty(stowagePlanInfo.getDataSave())
                      ? null
                      : stowagePlanInfo.getDataSave());
              stowagePlan.setDeadweightConstant(
                  StringUtils.isEmpty(stowagePlanInfo.getDeadweightConstant())
                      ? null
                      : (long) (Double.parseDouble(stowagePlanInfo.getDeadweightConstant())));
              stowagePlan.setPortCode(
                  StringUtils.isEmpty(stowagePlanInfo.getPortCode())
                      ? null
                      : stowagePlanInfo.getPortCode());

              stowagePlan.setPortId(
                  StringUtils.isEmpty(stowagePlanInfo.getPortId())
                      ? null
                      : stowagePlanInfo.getPortId());
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
                  StringUtils.isEmpty(stowagePlanInfo.getStatus())
                      ? null
                      : stowagePlanInfo.getStatus());
              stowagePlan.setVesselCode(
                  StringUtils.isEmpty(stowagePlanInfo.getVesselCode())
                      ? null
                      : stowagePlanInfo.getVesselCode());
              stowagePlan.setProcessId(stowagePlanInfo.getProcessId());
              stowagePlanList.add(stowagePlan);
            });

    return stowagePlanList;
  }

  private StowageDetails buildStowageDetails(StowageDetailsInfo stowageInfo, Long id) {
    StowageDetails stowageDetails = null;

    stowageDetails = new StowageDetails();
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
    stowageDetails.setStowagePlanId(id);
    stowageDetails.setTankId(
        StringUtils.isEmpty(stowageInfo.getTankId()) ? null : stowageInfo.getTankId());
    stowageDetails.setTankName(
        StringUtils.isEmpty(stowageInfo.getTankName()) ? null : stowageInfo.getTankName());
    return stowageDetails;
  }

  private OtherTankDetails buildBallast(BallastInfo otherTank, Long id) {

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
    otherTankDetails.setStowagePlanId(id);

    return otherTankDetails;
  }

  public Boolean getStatus(List<StowagePlan> stowagePlans) throws InterruptedException {
    Boolean status = false;
    do {
      Thread.sleep(10000);
      List<Long> stowagePlanIds =
          stowagePlans.stream().map(StowagePlan::getId).collect(Collectors.toList());
      List<StowagePlan> stowagePlanList = this.stowagePlanRepository.findByIdIn(stowagePlanIds);
      Long statusCount =
          stowagePlanList.stream().filter(plan -> plan.getStatus().equals(3L)).count();
      if (statusCount.equals(stowagePlanList.stream().count())) {
        status = true;
      }
    } while (!status);
    return status;
  }

  public LoadicatorDataRequest.Builder sendLoadicatorData(List<StowagePlan> stowagePlanEntityList) {
    LoadicatorDataRequest.Builder request = LoadicatorDataRequest.newBuilder();
    List<Long> stowagePlanIds =
        stowagePlanEntityList.stream().map(StowagePlan::getId).collect(Collectors.toList());
    List<StowagePlan> stowagePlanList = this.stowagePlanRepository.findByIdIn(stowagePlanIds);

	for ( StowagePlan stowage : stowagePlanList) {
		if (null != stowagePlanList) {
			List<LoadicatorTrim> loadicatorTrimList = this.loadicatorTrimRepository
					.findByStowagePlanIdIn(stowagePlanIds);
			LoadicatorPatternDetails.Builder loadicatorPatternDetails = LoadicatorPatternDetails.newBuilder();

			if (loadicatorTrimList != null) {
				loadicatorTrimList.forEach(trim -> {
					LDtrim.Builder ldTrim = this.buildLoadicatorTrimDetails(trim);
					loadicatorPatternDetails.addLDtrim(ldTrim);
				});
			}
			List<LoadicatorStrength> loadicatorStrengthList = this.loadicatorStrengthRepository
					.findByStowagePlanIdIn(stowagePlanIds);
			if (loadicatorStrengthList != null) {
				loadicatorStrengthList.forEach(strength -> {
					LDStrength.Builder ldStrength = this.buildStrengthDetails(strength);
					loadicatorPatternDetails.addLDStrength(ldStrength);
				});
			}
			if (loadicatorStrengthList != null) {
				List<IntactStability> intactStabilityList = this.loadicatorIntactStabilityRepository
						.findByStowagePlanIdIn(stowagePlanIds);
				intactStabilityList.forEach(stability -> {
					LDIntactStability.Builder ldStability = this.buildStabilityDetails(stability);
					loadicatorPatternDetails.addLDIntactStability(ldStability);
				});
			}
			loadicatorPatternDetails.setLoadablePatternId(stowage.getStowageId());
			request.addLoadicatorPatternDetails(loadicatorPatternDetails);

		}
      request.setProcessId(stowagePlanList.get(0).getProcessId());
      request.setLoadableStudyId(stowagePlanList.get(0).getBookingListId());
    }
    return request;
  }

  private LDIntactStability.Builder buildStabilityDetails(IntactStability stability) {
    LDIntactStability.Builder ldStability = LDIntactStability.newBuilder();
    
    StowagePlanDetail stowageDetail = this.stowagePlanRepository.findPortForStability(stability.getStowagePlanId());
    ldStability.setPortId(stowageDetail.getPortId());
    ldStability.setId(stability.getId());
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
    
    StowagePlanDetail stowageDetail = this.stowagePlanRepository.findPortForStrength(strength.getStowagePlanId());
    ldStrength.setPortId(stowageDetail.getPortId());
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
    
	StowagePlanDetail stowageDetail = this.stowagePlanRepository.findPortForTrim(trim.getStowagePlanId());
	ldTrim.setPortId(stowageDetail.getPortId());
	 
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
    return ldTrim;
  }

  public LoadicatorDataReply getLoadicatorData(LoadicatorDataRequest build) {
    return loadableStudyService.getLoadicatorData(build);
  }
}
