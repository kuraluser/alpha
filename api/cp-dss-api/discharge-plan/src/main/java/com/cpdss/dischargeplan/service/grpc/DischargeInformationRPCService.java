/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.FAILED;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.TIME_FORMATTER;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
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
import com.cpdss.common.generated.discharge_plan.PostDischargeStageTime;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import com.cpdss.dischargeplan.entity.DischargingStagesDuration;
import com.cpdss.dischargeplan.entity.DischargingStagesMinAmount;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import com.cpdss.dischargeplan.repository.CowPlanDetailRepository;
import com.cpdss.dischargeplan.repository.CowTankDetailRepository;
import com.cpdss.dischargeplan.repository.CowWithDifferentCargoRepository;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargeStageDurationRepository;
import com.cpdss.dischargeplan.repository.DischargeStageMinAmountRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayReasonRepository;
import com.cpdss.dischargeplan.repository.DischargingDelayRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanBallastDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanRobDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStabilityParametersRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageDetailsRepository;
import com.cpdss.dischargeplan.service.*;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
  @Autowired CowWithDifferentCargoRepository cowWithDifferentCargoRepository;
  @Autowired CowTankDetailRepository cowTankDetailRepository;
  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired CowPlanService cowPlanService;

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
        Optional.ofNullable(disEntity.getDischargingInformationStatus())
            .ifPresent(status -> dischargingInformation.setDischargingInfoStatusId(status.getId()));
        Optional.ofNullable(disEntity.getArrivalStatusId())
            .ifPresent(dischargingInformation::setDischargingPlanArrStatusId);
        Optional.ofNullable(disEntity.getDepartureStatusId())
            .ifPresent(dischargingInformation::setDischargingPlanDepStatusId);
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
        log.error("Failed to fetch Discharging Plan, Discharging info Id is 0");
        throw new GenericServiceException(
            "Discharging Info Id Is 0",
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
      log.info("Save Discharging Info, Berths Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        dischargingBerthService.saveDischargingBerthList(
            request.getBerthDetailsList(), dischargingInformation);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Discharging information Berths")
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
      log.info("Save Discharging Info, Delays Id {}", request.getDischargeInfoId());
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
                  .setMessage("Successfully saved Discharging information Delays")
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
      log.info("Save Discharging Info, Machines Id {}", request.getDischargeInfoId());
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
                  .setMessage("Successfully saved Discharging information Machinery")
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
      log.info("Save Discharging Info, Rates Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        saveDischargingInfoRates(request.getDischargeRate(), builder);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Discharging information Rates")
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
      log.info("Save Discharging Info, Rates Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        cowPlanService.saveCowPlanDetails(request.getCowPlan());
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Discharging information Rates")
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
  public void savePostDischargeStage(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save post discharge rate, Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        savePostDischargeRate(request.getPostDischargeStageTime(), dischargingInformation);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Discharging information Rates")
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

  private void savePostDischargeRate(
      PostDischargeStageTime postDischargeStageTime,
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation) {
    dischargeInformationRepository
        .updateFinalStrippingAndFreshOilWashAndSlopDischargingAndTimeForDryCheck(
            new BigDecimal(postDischargeStageTime.getFinalStripping()),
            new BigDecimal(postDischargeStageTime.getFreshOilWashing()),
            new BigDecimal(postDischargeStageTime.getSlopDischarging()),
            new BigDecimal(postDischargeStageTime.getTimeForDryCheck()),
            dischargingInformation.getId());
  }

  private void saveDischargingInfoRates(
      DischargeRates source, DischargingInfoSaveResponse.Builder response) {
    com.cpdss.dischargeplan.entity.DischargeInformation var1 =
        dischargeInformationService.getDischargeInformation(source.getId());
    if (var1 != null) {
      log.info("Save Discharging Info, Set Discharging Rates");

      if (!source.getMaxDischargeRate().isEmpty())
        var1.setMaxDischargingRate(new BigDecimal(source.getMaxDischargeRate()));

      if (!source.getInitialDischargeRate().isEmpty())
        var1.setInitialDischargingRate(new BigDecimal(source.getInitialDischargeRate()));

      if (!source.getMaxBallastRate().isEmpty())
        var1.setMaxBallastRate(new BigDecimal(source.getMaxBallastRate()));

      if (!source.getMinBallastRate().isEmpty())
        var1.setMinBallastRate(new BigDecimal(source.getMinBallastRate()));
      dischargeInformationRepository.save(var1);
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
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Successfully saved Discharging information")
              .setStatus(SUCCESS)
              .build());
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
        dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
    if (dischargeInformation != null) {
      log.info("updating discharging info, update discharging Details");
      updateDischargingInfoFromRpcMessage(request, dischargeInformation);
      Optional.ofNullable(request.getDischargeCommingledCargoSeparately())
          .ifPresent(dischargeInformation::setDischargeCommingleCargoSeparately);
      Optional.ofNullable(request.getDischargeSlopTanksFirst())
          .ifPresent(dischargeInformation::setDischargeSlopTankFirst);
      dischargeInformationRepository.updateCommingledCargoAndSlopTankFirst(
          dischargeInformation.getDischargeCommingleCargoSeparately(),
          dischargeInformation.getDischargeSlopTankFirst(),
          dischargeInformation.getId());
      dischargeInformationService.updateIsDischargingInfoCompeteStatus(
          dischargeInformation.getId(), request.getIsDischargingInfoComplete());
      return dischargeInformation;
    } else {
      throw new Exception(
          "Cannot find discharging information with id " + request.getDischargeDetails().getId());
    }
  }

  public void updateDischargingInfoFromRpcMessage(
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
      dischargeInformationRepository.updateStartTimeAndFinalTrim(
          target.getStartTime(), target.getFinalTrim(), target.getId());
      if (!source.getDischargeDetails().getTrimAllowed().getInitialTrim().isEmpty())
        target.setInitialTrim(
            new BigDecimal(source.getDischargeDetails().getTrimAllowed().getInitialTrim()));

      if (!source.getDischargeDetails().getTrimAllowed().getMaximumTrim().isEmpty())
        target.setMaximumTrim(
            new BigDecimal(source.getDischargeDetails().getTrimAllowed().getMaximumTrim()));
      dischargeInformationRepository.updateInitialTrimAndMaximumTrim(
          target.getInitialTrim(), target.getMaximumTrim(), target.getId());
    }
  }

  @Override
  public void saveDischargingInfoStages(
      DischargeInformation request, StreamObserver<DischargingInfoSaveResponse> responseObserver) {
    DischargingInfoSaveResponse.Builder builder = DischargingInfoSaveResponse.newBuilder();
    try {
      log.info("Request payload {}", Utils.toJson(request));
      com.cpdss.dischargeplan.entity.DischargeInformation dischargingInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      log.info("Save Discharging Info, Stages Id {}", request.getDischargeInfoId());
      if (dischargingInformation != null) {
        saveDischargingStages(request.getDischargeStage(), dischargingInformation);
        this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
            dischargingInformation.getId(), request.getIsDischargingInfoComplete());
      }
      buildDischargingInfoSaveResponse(builder, dischargingInformation);
      builder
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setMessage("Successfully saved Discharging information Stages")
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
      dischargeInformationRepository.updateIsTrackStartEndAndTrackGradeSwitching(
          dischargeStage.getTrackStartEndStage(),
          dischargeStage.getTrackGradeSwitch(),
          dischargingInformation.getId());
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
      dischargeInformationRepository.save(dischargingInformation);
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

  @Override
  public void getOrSaveRulesForDischargingPlan(
      DischargeRuleRequest request, StreamObserver<DischargeRuleReply> responseObserver) {
    DischargeRuleReply.Builder builder = DischargeRuleReply.newBuilder();
    try {
      dischargeInformationService.getOrSaveRulesForDischargingPlan(request, builder);
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
}
