/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.FAILED;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.TIME_FORMATTER;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.COW_TYPE;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.CargoForCow;
import com.cpdss.common.generated.discharge_plan.CowPlan;
import com.cpdss.common.generated.discharge_plan.DischargeInformation;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeRates;
import com.cpdss.common.generated.discharge_plan.DischargeRuleReply;
import com.cpdss.common.generated.discharge_plan.DischargeRuleRequest;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailRequest;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailStatusReply;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailStatusReply.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse;
import com.cpdss.common.generated.discharge_plan.DischargingPlanReply;
import com.cpdss.common.generated.discharge_plan.DischargingUploadTideDetailRequest;
import com.cpdss.common.generated.discharge_plan.DischargingUploadTideDetailStatusReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowTankDetail;
import com.cpdss.dischargeplan.entity.CowWithDifferentCargo;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import com.cpdss.dischargeplan.entity.DischargingStagesDuration;
import com.cpdss.dischargeplan.entity.DischargingStagesMinAmount;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import com.cpdss.dischargeplan.repository.CowPlanDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeStageDurationRepository;
import com.cpdss.dischargeplan.repository.DischargeStageMinAmountRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayReasonRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanBallastDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanRobDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStabilityParametersRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageDetailsRepository;
import com.cpdss.dischargeplan.service.DischargeInformationBuilderService;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import com.cpdss.dischargeplan.service.DischargingBerthService;
import com.cpdss.dischargeplan.service.DischargingDelayService;
import com.cpdss.dischargeplan.service.DischargingMachineryInUseService;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class DischargeInformationRPCService
    extends DischargeInformationServiceGrpc.DischargeInformationServiceImplBase {

  @Autowired DischargeInformationService dischargeInformationService;
  @Autowired DischargeInformationBuilderService informationBuilderService;
  @Autowired DischargeBerthDetailRepository dischargeBerthDetailRepository;
  @Autowired DischargeStageMinAmountRepository stageMinAmountRepository;
  @Autowired DischargeStageDurationRepository dischargeStageDurationRepository;
  @Autowired DischargingDelayReasonRepository dischargingDelayReasonRepository;
  @Autowired DischargingDelayRepository dischargingDelayRepository;
  @Autowired PortDischargingPlanBallastDetailsRepository pdpBallastDetailsRepository;
  @Autowired PortDischargingPlanStowageDetailsRepository pdpStowageDetailsRepository;
  @Autowired PortDischargingPlanRobDetailsRepository pdpRobDetailsRepository;
  @Autowired PortDischargingPlanStabilityParametersRepository pdpStabilityParametersRepository;
  @Autowired DischargingBerthService dischargingBerthService;
  @Autowired DischargingDelayService dischargingDelayService;
  @Autowired DischargingMachineryInUseService dischargingMachineryInUseService;
  @Autowired DischargeStageDurationRepository stageDurationRepository;
  @Autowired DischargeStageMinAmountRepository minAmountRepository;
  @Autowired CowPlanDetailRepository cowPlanDetailRepository;

  @Override
  public void getDischargeInformation(
      DischargeInformationRequest request, StreamObserver<DischargeInformation> responseObserver) {
    DischargeInformation.Builder builder = DischargeInformation.newBuilder();
    try {
      log.info("Get Discharge Info Request Payload \n{}", Utils.toJson(request));
      this.dischargeInformationService.getDischargeInformation(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getOrSaveRulesForDischarging(
      DischargeRuleRequest request, StreamObserver<DischargeRuleReply> responseObserver) {
    DischargeRuleReply.Builder builder = DischargeRuleReply.newBuilder();
    try {
      // log.info("Get or dave Discharge rule \n{}", Utils.toJson(request));
      this.dischargeInformationService.getOrSaveRulesForDischarge(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void dischargingUploadPortTideDetails(
      DischargingUploadTideDetailRequest request,
      StreamObserver<DischargingUploadTideDetailStatusReply> responseObserver) {
    DischargingUploadTideDetailStatusReply.Builder uploadTideDetailStatusReplyBuilder =
        DischargingUploadTideDetailStatusReply.newBuilder();
    try {
      this.dischargeInformationService.uploadPortTideDetails(request);
      uploadTideDetailStatusReplyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS));
    } catch (GenericServiceException e) {
      uploadTideDetailStatusReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(DischargePlanConstants.FAILED)
              .setMessage(e.getMessage())
              .setCode(e.getCode())
              .setHttpStatusCode(e.getStatus().value()));
    } catch (Exception e) {
      uploadTideDetailStatusReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.FAILED));
    } finally {
      responseObserver.onNext(uploadTideDetailStatusReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void dischargingDownloadPortTideDetails(
      DischargingDownloadTideDetailRequest request,
      StreamObserver<DischargingDownloadTideDetailStatusReply> responseObserver) {
    Builder builder = DischargingDownloadTideDetailStatusReply.newBuilder();
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      this.dischargeInformationService.downloadPortTideDetails(workbook, request, builder);
    } catch (Exception e) {
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(DischargePlanConstants.FAILED)
              .setMessage(e.getMessage())
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getDischargingPlan(
      DischargeInformationRequest request, StreamObserver<DischargingPlanReply> responseObserver) {
    DischargingPlanReply.Builder builder = DischargingPlanReply.newBuilder();
    try {

      com.cpdss.dischargeplan.entity.DischargeInformation disEntity;
      if (request.getDischargeInfoId() > 0) {
        disEntity =
            dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      } else {
        disEntity =
            dischargeInformationService.getDischargeInformation(
                request.getVesselId(), request.getVoyageId(), request.getPortRotationId());
      }

      if (disEntity != null) {

        // <---discharging Information Start-->
        DischargeInformation.Builder dischargingInformation = DischargeInformation.newBuilder();
        dischargingInformation.setDischargeInfoId(disEntity.getId());
        dischargingInformation.setSynopticTableId(disEntity.getSynopticTableXid());
        // Set Discharge Rates
        this.informationBuilderService.buildDischargeRateMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Discharge berth
        List<DischargingBerthDetail> listVarB =
            this.dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(
                disEntity.getId());
        this.informationBuilderService.buildDischargeBerthMessageFromEntity(
            disEntity, listVarB, dischargingInformation);

        // Set Stages
        this.informationBuilderService.buildDischargeStageMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Delay
        this.informationBuilderService.buildDischargeDelaysMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Post Discharge stage
        this.informationBuilderService.buildPostDischargeStageMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Discharge Details
        this.informationBuilderService.buildDischargeDetailsMessageFromEntity(
            disEntity, dischargingInformation);

        // set Pump and Machine Details
        this.informationBuilderService.buildMachineInUseMessageFromEntity(
            disEntity, dischargingInformation);

        // Set Cow Details
        this.informationBuilderService.buildCowPlanMessageFromEntity(
            disEntity, dischargingInformation);
        builder.setDischargingInformation(dischargingInformation.build());

        // <---Cargo Details Start-->
        List<PortDischargingPlanBallastDetails> pdpBallastList =
            pdpBallastDetailsRepository.findByDischargingInformationAndIsActive(disEntity, true);
        List<PortDischargingPlanStowageDetails> pdpStowageList =
            pdpStowageDetailsRepository.findByDischargingInformationAndIsActive(disEntity, true);
        List<PortDischargingPlanRobDetails> pdpRobList =
            pdpRobDetailsRepository.findByDischargingInformationAndIsActive(
                disEntity.getId(), true);
        List<PortDischargingPlanStabilityParameters> pdpStabilityList =
            pdpStabilityParametersRepository.findByDischargingInformationAndIsActive(
                disEntity, true);

        builder.addAllPortDischargingPlanBallastDetails(
            this.informationBuilderService.buildDischargingPlanTankBallastMessage(pdpBallastList));
        builder.addAllPortDischargingPlanStowageDetails(
            this.informationBuilderService.buildDischargingPlanTankStowageMessage(pdpStowageList));
        builder.addAllPortDischargingPlanRobDetails(
            this.informationBuilderService.buildDischargingPlanTankRobMessage(pdpRobList));
        builder.addAllPortDischargingPlanStabilityParameters(
            this.informationBuilderService.buildDischargingPlanTankStabilityMessage(
                pdpStabilityList));
      } else {
        log.error("Failed to fetch Loading Plan, Loading info Id is 0");
        throw new GenericServiceException(
            "Loading Info Id Is 0",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
      builder.build();

    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveDischargingInfoBerths(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Loading Info, Berths Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        dischargingBerthService.saveDischargingBerthList(
            request.getBerthDetailsList(), dischargingInformation);
        // TODO confirm the field
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Loading information Berths")
                  .setStatus(SUCCESS)
                  .build())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED).build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveDischargingInfoDelays(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Loading Info, Delays Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        dischargingDelayService.saveDischargingDelayList(
            request.getDischargeDelay(), dischargingInformation);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Loading information Delays")
                  .setStatus(SUCCESS)
                  .build())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED).build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveDischargingInfoMachinery(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Loading Info, Machines Id {}", request.getDischargeInfoId());
      log.info("Request payload {}", Utils.toJson(request));
      if (dischargingInformation != null) {
        dischargingMachineryInUseService.saveDischargingMachineryList(
            request.getMachineInUseList(), dischargingInformation);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Loading information Machinery")
                  .setStatus(SUCCESS)
                  .build())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED).build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveDischargingInfoRates(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Loading Info, Rates Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        saveDischargingInfoRates(request.getDischargeRate(), builder);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Loading information Rates")
                  .setStatus(SUCCESS)
                  .build())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED).build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveCowPlan(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Loading Info, Rates Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        saveCowPlanDetails(request.getCowPlan());
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Loading information Rates")
                  .setStatus(SUCCESS)
                  .build())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED).build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  private void saveCowPlanDetails(CowPlan cowPlan) {
    Optional<CowPlanDetail> var1 =
        cowPlanDetailRepository.findByDischargingId(cowPlan.getDischargingInfoId());
    if (var1.isPresent()) {
      log.info("Save cow plan, Set values");
      CowPlanDetail cowPlanDetail = var1.get();

      if (!cowPlan.getCowEndTime().isEmpty()) {
        cowPlanDetail.setCowEndTime(new BigDecimal(cowPlan.getCowEndTime()));
      }
      if (!cowPlan.getCowStartTime().isEmpty()) {
        cowPlanDetail.setCowStartTime(new BigDecimal(cowPlan.getCowStartTime()));
      }
      if (!cowPlan.getCowTankPercent().isEmpty()) {
        cowPlanDetail.setCowPercentage(new BigDecimal(cowPlan.getCowTankPercent()));
      }
      if (!cowPlan.getCowWithCargoEnable()) {
        cowPlanDetail.setWashTankWithDifferentCargo(cowPlan.getCowWithCargoEnable());
      }

      if (!cowPlan.getEstCowDuration().isEmpty()) {
        cowPlanDetail.setEstimatedCowDuration(new BigDecimal(cowPlan.getEstCowDuration()));
      }

      if (!cowPlan.getNeedFlushingOil()) {
        cowPlanDetail.setNeedFlushingOil(cowPlan.getNeedFlushingOil());
      }
      if (!cowPlan.getNeedFreshCrudeStorage()) {
        cowPlanDetail.setNeedFreshCrudeStorage(cowPlan.getNeedFreshCrudeStorage());
      }
      if (!cowPlan.getTrimCowMax().isEmpty()) {
        cowPlanDetail.setCowMaxTrim(new BigDecimal(cowPlan.getTrimCowMax()));
      }
      if (!cowPlan.getTrimCowMin().isEmpty()) {
        cowPlanDetail.setCowMinTrim(new BigDecimal(cowPlan.getTrimCowMin()));
      }
      if (!cowPlan.getCowTankDetailsList().isEmpty()) {
        cowPlan.getCowTankDetailsList().stream()
            .forEach(
                plan -> {
                  if (COW_TYPE.CARGO.equals(plan.getCowType())) {
                    List<CargoForCow> cargoForCowList = plan.getCargoForCowList();
                    List<Long> tanksIdsToSave =
                        cargoForCowList.stream()
                            .flatMap(cargo -> cargo.getTankIdsList().stream())
                            .collect(Collectors.toList());
                    Set<CowWithDifferentCargo> cowWithDifferentCargos =
                        cowPlanDetail.getCowWithDifferentCargos();
                    // method to create new cargo Wash
                    createCaroCowWash(cargoForCowList, cowPlanDetail);
                    // to disable existing cargo wash
                    if (!cowWithDifferentCargos.isEmpty()) {
                      cowWithDifferentCargos.forEach(
                          cargoCow -> {
                            if (!tanksIdsToSave.contains(cargoCow.getTankXid())) {
                              cargoCow.setIsActive(false);
                            }
                          });
                    }

                  } else {
                    List<Long> tanksIdsToSave = plan.getTankIdsList();
                    List<CowTankDetail> existingTanks =
                        cowPlanDetail.getCowTankDetails().stream()
                            .filter(
                                tank -> tank.getCowTypeXid().equals(plan.getCowType().getNumber()))
                            .collect(Collectors.toList());
                    // if the values are not in the current list set it as false
                    existingTanks.stream()
                        .forEach(
                            tank -> {
                              if (!tanksIdsToSave.contains(tank.getTankXid())) {
                                tank.setIsActive(false);
                              }
                            });
                    // adding new objects
                    List<Long> existingTankIds =
                        cowPlanDetail.getCowTankDetails().stream()
                            .map(CowTankDetail::getTankXid)
                            .collect(Collectors.toList());
                    tanksIdsToSave.removeAll(existingTankIds);
                    if (tanksIdsToSave != null || !tanksIdsToSave.isEmpty()) {
                      tanksIdsToSave.forEach(
                          newCowTankId -> {
                            CowTankDetail newTank = new CowTankDetail();
                            newTank.setCowPlanDetail(cowPlanDetail);
                            newTank.setCowTypeXid(plan.getCowType().getNumber());
                            newTank.setDischargingXid(cowPlan.getDischargingInfoId());
                            newTank.setIsActive(true);
                            newTank.setTankXid(newCowTankId);
                            if (cowPlanDetail.getCowTankDetails() == null) {
                              cowPlanDetail.setCowTankDetails(
                                  new HashSet<>(Arrays.asList(newTank)));
                            } else {
                              cowPlanDetail.getCowTankDetails().add(newTank);
                            }
                          });
                    }
                  }
                });
      }
    }
  }

  private void createCaroCowWash(List<CargoForCow> cargoForCowList, CowPlanDetail cowPlanDetail) {
	  //if the list is empty create all the values in the request list
    if (cowPlanDetail.getCowWithDifferentCargos().isEmpty()) {
      cargoForCowList.forEach(
          newCargoWash ->
              updateCargoCowWash(cowPlanDetail, newCargoWash, newCargoWash.getTankIdsList()));
    } else {
    	//groups the CowWithDifferentCargo by cargo nomination ids
      Map<Long, List<CowWithDifferentCargo>> cargoNominationWiseWashCargos =
          cowPlanDetail.getCowWithDifferentCargos().stream()
              .collect(Collectors.groupingBy(CowWithDifferentCargo::getCargoNominationXid));
      cargoForCowList.forEach(
          newCargoWash -> {
        	  //get the equivalent tanks for the cargo nomination ids
        	  List<CowWithDifferentCargo> nominationWiseWashCargoEntities =
                cargoNominationWiseWashCargos.get(newCargoWash.getCargoNominationId());
            //finds the tanks ids
        	  List<Long> savedTanks =
                nominationWiseWashCargoEntities.stream()
                    .map(CowWithDifferentCargo::getTankXid)
                    .collect(Collectors.toList());
        	  //remove the already existing ids in the db
            List<Long> tankIdsToSave = newCargoWash.getTankIdsList();
            tankIdsToSave.removeAll(savedTanks);
            //create the new ones
            updateCargoCowWash(cowPlanDetail, newCargoWash, tankIdsToSave);
          });
    }
  }

  private void updateCargoCowWash(
      CowPlanDetail cowPlanDetail, CargoForCow newCargoWash, List<Long> tankIdsToSave) {
    tankIdsToSave.forEach(
        tankId -> {
          CowWithDifferentCargo cowWithDifferentCargo = new CowWithDifferentCargo();
          cowWithDifferentCargo.setCargoNominationXid(newCargoWash.getCargoNominationId());
          cowWithDifferentCargo.setCargoXid(newCargoWash.getCargoId());
          cowWithDifferentCargo.setCowPlanDetail(cowPlanDetail);
          cowWithDifferentCargo.setDischargingXid(cowPlanDetail.getDischargeInformation().getId());
          cowWithDifferentCargo.setIsActive(true);
          cowWithDifferentCargo.setWashingCargoXid(newCargoWash.getWashingCargoId());
          cowWithDifferentCargo.setWashingCargoNominationXid(
              newCargoWash.getWashingCargoNominationId());
          cowWithDifferentCargo.setTankXid(tankId);
          if (cowPlanDetail.getCowWithDifferentCargos() == null) {
            cowPlanDetail.setCowWithDifferentCargos(
                new HashSet<>(Arrays.asList(cowWithDifferentCargo)));
          } else {
            cowPlanDetail.getCowWithDifferentCargos().add(cowWithDifferentCargo);
          }
        });
  }

  private void saveDischargingInfoRates(
      DischargeRates source, DischargingInfoSaveResponse.Builder response) {
    com.cpdss.dischargeplan.entity.DischargeInformation var1 =
        dischargeInformationService.getDischargeInformation(source.getId());
    if (var1 != null) {
      log.info("Save Loading Info, Set Loading Rates");

      if (!source.getMaxDischargeRate().isEmpty())
        var1.setMaxDischargingRate(new BigDecimal(source.getMaxDischargeRate()));

      if (!source.getInitialDischargeRate().isEmpty())
        var1.setInitialDischargingRate(new BigDecimal(source.getInitialDischargeRate()));

      if (!source.getMaxBallastRate().isEmpty())
        var1.setMaxBallastRate(new BigDecimal(source.getMaxBallastRate()));

      if (!source.getMinBallastRate().isEmpty())
        var1.setMinBallastRate(new BigDecimal(source.getMinBallastRate()));
      dischargeInformationService.save(var1);
    }
  }

  @Override
  public void saveDischargingInformation(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation response =
          saveDischargingInformation(request);
      buildDischargingInfoSaveResponse(builder, response);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Discharging information")
                  .setStatus(SUCCESS)
                  .build())
          .build();
      log.info("Save Discharging Info, Details Id {}", request.getDischargeInfoId());
    } catch (Exception e) {
      log.error(
          "Exception occured while saving Discharging Information for id {}",
          request.getDischargeDetails().getId());
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                  .setMessage(e.getMessage())
                  .setStatus(FAILED)
                  .build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  public com.cpdss.dischargeplan.entity.DischargeInformation saveDischargingInformation(
      DischargeInformation request) throws Exception {
    com.cpdss.dischargeplan.entity.DischargeInformation dischargeInformation =
        dischargeInformationService.getDischargeInformation(request.getDischargeDetails().getId());
    if (dischargeInformation != null) {
      buildDischargingInfoFromRpcMessage(request, dischargeInformation);
      dischargeInformationService.save(dischargeInformation);
      dischargeInformationService.updateIsDischargingInfoCompeteStatus(
          dischargeInformation.getId(), request.getIsDischargingInfoComplete());
      return dischargeInformation;
    } else {
      throw new Exception(
          "Cannot find discharging information with id " + request.getDischargeDetails().getId());
    }
  }

  public com.cpdss.dischargeplan.entity.DischargeInformation buildDischargingInfoFromRpcMessage(
      DischargeInformation source, com.cpdss.dischargeplan.entity.DischargeInformation target) {
    // Set discharging Details
    if (source.getDischargeDetails() != null) {
      log.info("Save discharging info, Set discharging Details");
      if (!source.getDischargeDetails().getStartTime().isEmpty())
        target.setStartTime(
            LocalTime.from(TIME_FORMATTER.parse(source.getDischargeDetails().getStartTime())));

      if (!source.getDischargeDetails().getTrimAllowed().getFinalTrim().isEmpty())
        target.setFinalTrim(
            new BigDecimal(source.getDischargeDetails().getTrimAllowed().getFinalTrim()));

      if (!source.getDischargeDetails().getTrimAllowed().getInitialTrim().isEmpty())
        target.setInitialTrim(
            new BigDecimal(source.getDischargeDetails().getTrimAllowed().getInitialTrim()));

      if (!source.getDischargeDetails().getTrimAllowed().getMaximumTrim().isEmpty())
        target.setMaximumTrim(
            new BigDecimal(source.getDischargeDetails().getTrimAllowed().getMaximumTrim()));
    }
    return target;
  }

  @Override
  public void saveDischargingInfoStages(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Loading Info, Stages Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        saveDischargingStages(request.getDischargeStage(), dischargingInformation);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Loading information Stages")
                  .setStatus(SUCCESS)
                  .build())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED).build())
          .build();
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  private void saveDischargingStages(
      LoadingStages dischargeStage,
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation) {

    if (dischargeStage != null) {
      dischargingInformation.setIsTrackStartEndStage(dischargeStage.getTrackStartEndStage());
      dischargingInformation.setIsTrackGradeSwitching(dischargeStage.getTrackGradeSwitch());
      if (Optional.ofNullable(dischargeStage.getDuration().getId()).isPresent()
          && dischargeStage.getDuration().getId() != 0) {
        Optional<DischargingStagesDuration> stageDurationOpt =
            stageDurationRepository.findByIdAndIsActiveTrue(dischargeStage.getDuration().getId());
        if (stageDurationOpt.isPresent()) {
          dischargingInformation.setDischargingStagesDuration(stageDurationOpt.get());
        } else {
          log.error("Duration not found id {}", dischargeStage.getDuration().getId());
        }
      }
      if (Optional.of(dischargeStage.getOffset().getId()).isPresent()
          && dischargeStage.getOffset().getId() != 0) {
        Optional<DischargingStagesMinAmount> stageOffsetOpt =
            minAmountRepository.findByIdAndIsActiveTrue(dischargeStage.getOffset().getId());
        if (stageOffsetOpt.isPresent()) {
          dischargingInformation.setDischargingStagesMinAmount(stageOffsetOpt.get());
        } else {
          log.info("Offset Not found Id {}", dischargeStage.getOffset().getId());
        }
      }
      dischargeInformationService.save(dischargingInformation);
    }
  }

  private void buildDischargingInfoSaveResponse(
      com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse.Builder builder,
      com.cpdss.dischargeplan.entity.DischargeInformation response) {
    Optional.ofNullable(response.getId()).ifPresent(builder::setDischargingInfoId);
    Optional.ofNullable(response.getPortRotationXid()).ifPresent(builder::setPortRotationId);
    Optional.ofNullable(response.getSynopticTableXid()).ifPresent(builder::setSynopticalTableId);
    Optional.ofNullable(response.getVesselXid()).ifPresent(builder::setVesselId);
    Optional.ofNullable(response.getVoyageXid()).ifPresent(builder::setVoyageId);
  }
}
