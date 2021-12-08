/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.grpc;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.ACTUAL_TYPE_VALUE;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.DEPARTURE_CONDITION_VALUE;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.FAILED;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.AlgoErrorReply;
import com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply;
import com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargePlanStowageDetailsRequest;
import com.cpdss.common.generated.discharge_plan.DischargePlanStowageDetailsResponse;
import com.cpdss.common.generated.discharge_plan.DischargeSequenceReply;
import com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest;
import com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataReply;
import com.cpdss.common.generated.discharge_plan.DischargingInfoLoadicatorDataRequest;
import com.cpdss.common.generated.discharge_plan.DischargingPlanSaveRequest;
import com.cpdss.common.generated.discharge_plan.DischargingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetails.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.algo.DischargingInformationAlgoResponse;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.*;
import com.cpdss.dischargeplan.service.loadicator.LoadicatorService;
import com.cpdss.dischargeplan.service.loadicator.UllageUpdateLoadicatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@GrpcService
@Transactional
public class DischargePlanRPCService extends DischargePlanServiceGrpc.DischargePlanServiceImplBase {

  public static final Integer CONDITION_TYPE_DEP = 2;
  public static final Integer VALUE_TYPE_ACTUALS = 1;
  public static final Long ULLAGE_UPDATE_VALIDATED_TRUE = 13L;
  @Autowired DischargePlanSynchronizeService dischargePlanSynchronizeService;

  @Autowired DischargePlanAlgoService dischargePlanAlgoService;
  @Autowired PortDischargingPlanBallastDetailsRepository pdpBallastDetailsRepository;
  @Autowired PortDischargingPlanStowageDetailsRepository pdpStowageDetailsRepository;
  @Autowired PortDischargingPlanRobDetailsRepository pdpRobDetailsRepository;
  @Autowired PortDischargingPlanStabilityParametersRepository pdpStabilityParametersRepository;
  @Autowired BillOfLaddingRepository billOfLaddingRepo;
  @Autowired DischargeInformationService dischargeInformationService;

  @Autowired
  PortDischargingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @Autowired
  PortDischargingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @Autowired
  PortDischargingPlanCommingleTempDetailsRepository
      portDischargingPlanCommingleTempDetailsRepository;

  @Autowired DischargePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @Autowired DischargeInfoStatusCheckService dischargeInfoStatusCheckService;

  @Autowired
  private PortDischargingPlanStowageDetailsRepository portDischargingPlanStowageDetailsRepository;

  @Autowired private DischargeInformationBuilderService dischargeInformationBuilderService;

  @Autowired RestTemplate restTemplate;
  @Autowired DischargeInformationRepository dischargeInformationRepository;
  @Autowired DischargingSequenceService dischargeSequenceService;

  @Autowired LoadicatorService loadicatorService;

  @Autowired DischargeCargoHistoryService cargoHistoryService;

  @Autowired
  PortDischargingPlanCommingleDetailsRepository portDischargingPlanCommingleDetailsRepository;

  @Value(value = "${algo.planGenerationUrl}")
  private String planGenerationUrl;

  @Override
  public void dischargePlanSynchronization(
      DischargeStudyDataTransferRequest request, StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder response = ResponseStatus.newBuilder();
    try {
      this.dischargePlanSynchronizeService.saveDischargeInformation(
          request); // Create new Discharge Information
      if (!request.getPortDataList().isEmpty()) {
        this.dischargePlanSynchronizeService.saveCowDetailsForDischargeStudy(
            request); // Save Cow Plan
        // per/discharge-info
      }
      response.setStatus(SUCCESS);
      response.setMessage(SUCCESS);
    } catch (Exception e) {

      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      e.printStackTrace();
      e.printStackTrace();
      response
          .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
          .setMessage(e.getMessage())
          .setStatus(FAILED)
          .build();
    } finally {
      log.info("Exiting GRPC method");
      responseObserver.onNext(response.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void generateDischargePlan(
      DischargeInformationRequest request,
      StreamObserver<com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest>
          responseObserver) {
    com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.newBuilder();
    com.cpdss.dischargeplan.domain.DischargeInformationAlgoRequest algoRequest =
        new com.cpdss.dischargeplan.domain.DischargeInformationAlgoRequest();
    try {
      log.info("Generate Discharge Plan RPC: Payload", Utils.toJson(request));

      // build DTO object
      this.dischargePlanAlgoService.buildDischargeInformation(request, algoRequest);

      // Save Above JSON In LS json data Table
      dischargePlanAlgoService.saveDischargingInformationRequestJson(
          algoRequest, request.getDischargeInfoId());
      ObjectMapper objectMapper = new ObjectMapper();
      String ss = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(algoRequest);
      builder.setRequestAsJsonString(ss);
      log.info("algo request payload - {}", ss);
      com.cpdss.dischargeplan.entity.DischargeInformation dischargeInformation =
          dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
      if (dischargeInformation == null) {
        throw new GenericServiceException(
            "Could not find discharge information " + request.getDischargeInfoId(),
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      // Call To Algo End Point for Loading
      try {
        DischargingInformationAlgoResponse response =
            restTemplate.postForObject(
                planGenerationUrl, algoRequest, DischargingInformationAlgoResponse.class);
        // Set Loading Status
        Optional<DischargingInformationStatus> dischargingInfoStatusOpt =
            dischargePlanAlgoService.getDischargingInformationStatus(
                DischargePlanConstants.DISCHARGING_INFORMATION_PROCESSING_STARTED_ID);
        dischargeInformation.setDischargingInformationStatus(dischargingInfoStatusOpt.get());
        dischargeInformation.setIsDischargingPlanGenerated(false);
        dischargeInformation.setIsDischargingSequenceGenerated(false);
        dischargeInformationRepository.save(dischargeInformation);
        dischargePlanAlgoService.createDischargingInformationAlgoStatus(
            dischargeInformation, response.getProcessId(), dischargingInfoStatusOpt.get(), null);
        builder.setProcessId(response.getProcessId());
        builder.setResponseStatus(
            Common.ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
      } catch (HttpStatusCodeException e) {
        log.error("Error occured in ALGO side while calling new_loadable API");
        Optional<DischargingInformationStatus> errorOccurredStatusOpt =
            dischargePlanAlgoService.getDischargingInformationStatus(
                DischargePlanConstants.DISCHARGING_INFORMATION_ERROR_OCCURRED_ID);
        dischargeInformationService.updateDischargingInformationStatus(
            errorOccurredStatusOpt.get(), dischargeInformation.getId());
        dischargePlanAlgoService.saveAlgoInternalError(
            dischargeInformation, null, Lists.newArrayList(e.getResponseBodyAsString()));
        builder.setResponseStatus(
            Common.ResponseStatus.newBuilder()
                .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                .setMessage(e.getMessage())
                .setStatus(DischargePlanConstants.FAILED)
                .build());
      }
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
  public void getDischargeUpdateUllageDetails(
      UpdateUllageDetailsRequest request,
      StreamObserver<UpdateUllageDetailsResponse> responseObserver) {
    log.info("Inside get Update Ullage details");
    UpdateUllageDetailsResponse.Builder builder = UpdateUllageDetailsResponse.newBuilder();
    try {
      getBillOfLaddingDetails(request, builder);
      getPortWiseStowageDetails(request, builder);
      getPortWiseBallastDetails(request, builder);
      getPortWiseRobDetails(request, builder);
      getPortWiseStowageTempDetails(request, builder);
      getPortWiseBallastTempDetails(request, builder);
      getPortWiseCommingleDetails(request, builder);
    } catch (Exception e) {
      log.error("Exception when saveLoadingPlan microservice is called", e);

    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  public void getBillOfLaddingDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {

    List<BillOfLadding> billOfLaddingDetails =
        this.billOfLaddingRepo.findByDischargePatternXIdAndPortIdAndIsActive(
            request.getPatternId(), request.getPortId(), true);
    billOfLaddingDetails.stream()
        .forEach(
            bill -> {
              Common.BillOfLadding.Builder blBuilder = Common.BillOfLadding.newBuilder();
              blBuilder.setId(bill.getId());
              blBuilder.setBlRefNo(bill.getBlRefNo());
              blBuilder.setApi(bill.getApi() != null ? bill.getApi().toString() : "");
              blBuilder.setTemperature(
                  bill.getTemperature() != null ? bill.getTemperature().toString() : "");
              blBuilder.setCargoNominationId(bill.getCargoNominationId());
              blBuilder.setPortId(bill.getPortId());
              blBuilder.setQuantityMt(
                  bill.getQuantityMt() != null ? bill.getQuantityMt().toString() : "");
              blBuilder.setQuantityBbls(
                  bill.getQuantityMt() != null ? bill.getQuantityBbls().toString() : "");
              blBuilder.setQuantityKl(
                  bill.getQuantityMt() != null ? bill.getQuantityKl().toString() : "");
              blBuilder.setQuantityLT(
                  bill.getQuantityLT() != null ? bill.getQuantityLT().toString() : "");
              blBuilder.setCargoNominationId(bill.getCargoNominationId());
              builder.addBillOfLadding(blBuilder);
            });
  }

  public void getPortWiseStowageDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {
    List<PortDischargingPlanStowageDetails> portWiseStowageDetails =
        pdpStowageDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortDischargingPlanStowageDetails portWiseStowageDetail : portWiseStowageDetails) {
      LoadingPlanModels.PortLoadablePlanStowageDetail.Builder newBuilder =
          LoadingPlanModels.PortLoadablePlanStowageDetail.newBuilder();
      newBuilder.setAbbreviation(
          portWiseStowageDetail.getAbbreviation() != null
              ? portWiseStowageDetail.getAbbreviation()
              : "");
      newBuilder.setApi(
          portWiseStowageDetail.getApi() != null ? portWiseStowageDetail.getApi().toString() : "");
      newBuilder.setCargoNominationId(portWiseStowageDetail.getCargoNominationXId());
      newBuilder.setColorCode(
          portWiseStowageDetail.getColorCode() != null ? portWiseStowageDetail.getColorCode() : "");
      newBuilder.setCorrectedUllage(
          portWiseStowageDetail.getCorrectedUllage() != null
              ? portWiseStowageDetail.getCorrectedUllage().toString()
              : "");
      newBuilder.setCorrectionFactor(
          portWiseStowageDetail.getCorrectionFactor() != null
              ? portWiseStowageDetail.getCorrectionFactor().toString()
              : "");
      newBuilder.setFillingPercentage(
          portWiseStowageDetail.getFillingPercentage() != null
              ? portWiseStowageDetail.getFillingPercentage().toString()
              : "");
      newBuilder.setId(portWiseStowageDetail.getId());
      newBuilder.setLoadablePatternId(request.getPatternId());
      newBuilder.setRdgUllage(
          portWiseStowageDetail.getRdgUllage() != null
              ? portWiseStowageDetail.getRdgUllage().toString()
              : "");
      newBuilder.setTankId(portWiseStowageDetail.getTankXId());
      newBuilder.setTemperature(
          portWiseStowageDetail.getTemperature() != null
              ? portWiseStowageDetail.getTemperature().toString()
              : "");
      newBuilder.setWeight(
          portWiseStowageDetail.getWeight() != null
              ? portWiseStowageDetail.getWeight().toString()
              : "");
      newBuilder.setQuantity(
          portWiseStowageDetail.getQuantity() != null
              ? portWiseStowageDetail.getQuantity().toString()
              : "");
      newBuilder.setActualPlanned(
          portWiseStowageDetail.getValueType() != null
              ? portWiseStowageDetail.getValueType().toString()
              : "");
      newBuilder.setArrivalDeparture(
          portWiseStowageDetail.getConditionType() != null
              ? portWiseStowageDetail.getConditionType().toString()
              : "");
      newBuilder.setUllage(
          portWiseStowageDetail.getUllage() != null
              ? portWiseStowageDetail.getUllage().toString()
              : "");
      builder.addPortLoadablePlanStowageDetails(newBuilder);
    }
  }

  public void getPortWiseBallastDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {
    List<PortDischargingPlanBallastDetails> portWiseStowageDetails =
        pdpBallastDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortDischargingPlanBallastDetails portWiseBallastDetail : portWiseStowageDetails) {
      LoadingPlanModels.PortLoadingPlanBallastDetails.Builder newBuilder =
          LoadingPlanModels.PortLoadingPlanBallastDetails.newBuilder();

      newBuilder.setColorCode(
          portWiseBallastDetail.getColorCode() != null
              ? portWiseBallastDetail.getColorCode().toString()
              : "");
      newBuilder.setCorrectedUllage(
          portWiseBallastDetail.getCorrectedUllage() != null
              ? portWiseBallastDetail.getCorrectedUllage().toString()
              : "");
      newBuilder.setCorrectionFactor(
          portWiseBallastDetail.getCorrectionFactor() != null
              ? portWiseBallastDetail.getCorrectionFactor().toString()
              : "");
      newBuilder.setFillingPercentage(
          portWiseBallastDetail.getFillingPercentage() != null
              ? portWiseBallastDetail.getFillingPercentage().toString()
              : "");
      newBuilder.setId(portWiseBallastDetail.getId());
      newBuilder.setLoadablePatternId(request.getPatternId());
      newBuilder.setTankId(portWiseBallastDetail.getTankXId());
      newBuilder.setTemperature(
          portWiseBallastDetail.getTemperature() != null
              ? portWiseBallastDetail.getTemperature().toString()
              : "");
      newBuilder.setQuantity(
          portWiseBallastDetail.getQuantity() != null
              ? portWiseBallastDetail.getQuantity().toString()
              : "");
      newBuilder.setActualPlanned(
          portWiseBallastDetail.getValueType() != null
              ? portWiseBallastDetail.getValueType().toString()
              : "");
      newBuilder.setArrivalDeparture(
          portWiseBallastDetail.getConditionType() != null
              ? portWiseBallastDetail.getConditionType().toString()
              : "");
      newBuilder.setSounding(
          portWiseBallastDetail.getSounding() != null
              ? portWiseBallastDetail.getSounding().toString()
              : "");
      newBuilder.setSg(
          portWiseBallastDetail.getSg() != null ? portWiseBallastDetail.getSg().toString() : "");
      builder.addPortLoadingPlanBallastDetails(newBuilder);
    }
  }

  public void getPortWiseRobDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {
    List<PortDischargingPlanRobDetails> portWiseRobDetails =
        pdpRobDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortDischargingPlanRobDetails portWiseRobDetail : portWiseRobDetails) {
      LoadingPlanModels.PortLoadingPlanRobDetails.Builder newBuilder =
          LoadingPlanModels.PortLoadingPlanRobDetails.newBuilder();

      newBuilder.setId(portWiseRobDetail.getId());
      newBuilder.setLoadablePatternId(request.getPatternId());
      newBuilder.setTankId(portWiseRobDetail.getTankXId());
      newBuilder.setQuantity(
          portWiseRobDetail.getQuantity() != null
              ? portWiseRobDetail.getQuantity().toString()
              : "");
      newBuilder.setActualPlanned(
          portWiseRobDetail.getValueType() != null
              ? portWiseRobDetail.getValueType().toString()
              : "");
      newBuilder.setArrivalDeparture(
          portWiseRobDetail.getConditionType() != null
              ? portWiseRobDetail.getConditionType().toString()
              : "");
      newBuilder.setDensity(
          portWiseRobDetail.getDensity() != null ? portWiseRobDetail.getDensity().toString() : "");
      newBuilder.setColorCode(
          portWiseRobDetail.getColorCode() != null ? portWiseRobDetail.getColorCode() : "");
      builder.addPortLoadingPlanRobDetails(newBuilder);
    }
  }

  public void getPortWiseStowageTempDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {
    List<PortDischargingPlanStowageTempDetails> portWiseStowageTempDetails =
        portLoadingPlanStowageTempDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortDischargingPlanStowageTempDetails portWiseStowageDetail : portWiseStowageTempDetails) {
      LoadingPlanModels.PortLoadablePlanStowageDetail.Builder newBuilder =
          LoadingPlanModels.PortLoadablePlanStowageDetail.newBuilder();
      newBuilder.setAbbreviation(
          portWiseStowageDetail.getAbbreviation() != null
              ? portWiseStowageDetail.getAbbreviation()
              : "");
      newBuilder.setApi(
          portWiseStowageDetail.getApi() != null ? portWiseStowageDetail.getApi().toString() : "");
      newBuilder.setCargoNominationId(portWiseStowageDetail.getCargoNominationXId());
      newBuilder.setColorCode(
          portWiseStowageDetail.getColorCode() != null
              ? portWiseStowageDetail.getColorCode().toString()
              : "");
      newBuilder.setCorrectedUllage(
          portWiseStowageDetail.getCorrectedUllage() != null
              ? portWiseStowageDetail.getCorrectedUllage().toString()
              : "");
      newBuilder.setCorrectionFactor(
          portWiseStowageDetail.getCorrectionFactor() != null
              ? portWiseStowageDetail.getCorrectionFactor().toString()
              : "");
      newBuilder.setFillingPercentage(
          portWiseStowageDetail.getFillingPercentage() != null
              ? portWiseStowageDetail.getFillingPercentage().toString()
              : "");
      newBuilder.setId(portWiseStowageDetail.getId());
      newBuilder.setLoadablePatternId(request.getPatternId());
      newBuilder.setRdgUllage(
          portWiseStowageDetail.getRdgUllage() != null
              ? portWiseStowageDetail.getRdgUllage().toString()
              : "");
      newBuilder.setTankId(portWiseStowageDetail.getTankXId());
      newBuilder.setTemperature(
          portWiseStowageDetail.getTemperature() != null
              ? portWiseStowageDetail.getTemperature().toString()
              : "");
      newBuilder.setWeight(
          portWiseStowageDetail.getWeight() != null
              ? portWiseStowageDetail.getWeight().toString()
              : "");
      newBuilder.setQuantity(
          portWiseStowageDetail.getQuantity() != null
              ? portWiseStowageDetail.getQuantity().toString()
              : "");
      newBuilder.setActualPlanned(
          portWiseStowageDetail.getValueType() != null
              ? portWiseStowageDetail.getValueType().toString()
              : "");
      newBuilder.setArrivalDeparture(
          portWiseStowageDetail.getConditionType() != null
              ? portWiseStowageDetail.getConditionType().toString()
              : "");
      newBuilder.setUllage(
          portWiseStowageDetail.getUllage() != null
              ? portWiseStowageDetail.getUllage().toString()
              : "");
      builder.addPortLoadablePlanStowageTempDetails(newBuilder);
    }
  }

  public void getPortWiseBallastTempDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {
    List<PortDischargingPlanBallastTempDetails> portWiseBallastTempDetails =
        portLoadingPlanBallastTempDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortDischargingPlanBallastTempDetails portWiseBallastDetail : portWiseBallastTempDetails) {
      LoadingPlanModels.PortLoadingPlanBallastDetails.Builder newBuilder =
          LoadingPlanModels.PortLoadingPlanBallastDetails.newBuilder();

      newBuilder.setColorCode(
          portWiseBallastDetail.getColorCode() != null
              ? portWiseBallastDetail.getColorCode().toString()
              : "");
      newBuilder.setCorrectedUllage(
          portWiseBallastDetail.getCorrectedUllage() != null
              ? portWiseBallastDetail.getCorrectedUllage().toString()
              : "");
      newBuilder.setCorrectionFactor(
          portWiseBallastDetail.getCorrectionFactor() != null
              ? portWiseBallastDetail.getCorrectionFactor().toString()
              : "");
      newBuilder.setFillingPercentage(
          portWiseBallastDetail.getFillingPercentage() != null
              ? portWiseBallastDetail.getFillingPercentage().toString()
              : "");
      newBuilder.setId(portWiseBallastDetail.getId());
      newBuilder.setLoadablePatternId(request.getPatternId());
      newBuilder.setTankId(portWiseBallastDetail.getTankXId());
      newBuilder.setTemperature(
          portWiseBallastDetail.getTemperature() != null
              ? portWiseBallastDetail.getTemperature().toString()
              : "");
      newBuilder.setQuantity(
          portWiseBallastDetail.getQuantity() != null
              ? portWiseBallastDetail.getQuantity().toString()
              : "");
      newBuilder.setActualPlanned(
          portWiseBallastDetail.getValueType() != null
              ? portWiseBallastDetail.getValueType().toString()
              : "");
      newBuilder.setArrivalDeparture(
          portWiseBallastDetail.getConditionType() != null
              ? portWiseBallastDetail.getConditionType().toString()
              : "");
      newBuilder.setSounding(
          portWiseBallastDetail.getSounding() != null
              ? portWiseBallastDetail.getSounding().toString()
              : "");
      newBuilder.setSg(
          portWiseBallastDetail.getSg() != null ? portWiseBallastDetail.getSg().toString() : "");
      builder.addPortLoadingPlanBallastTempDetails(newBuilder);
    }
  }

  public void getPortWiseCommingleDetails(
      UpdateUllageDetailsRequest request, UpdateUllageDetailsResponse.Builder builder) {
    Optional<com.cpdss.dischargeplan.entity.DischargeInformation> dischargingInfo =
        this.dischargeInformationRepository
            .findByVesselXidAndDischargingPatternXidAndPortRotationXidAndIsActive(
                request.getVesselId(), request.getPatternId(), request.getPortRotationId(), true);
    List<PortDischargingPlanCommingleDetails> portWiseRobDetails =
        portDischargingPlanCommingleDetailsRepository.findByDischargingInformationAndIsActive(
            dischargingInfo.get(), true);
    for (PortDischargingPlanCommingleDetails portWiseCommingleDetail : portWiseRobDetails) {
      builder.addLoadablePlanCommingleDetails(
          buildPortWiseCommingleDetails(request, portWiseCommingleDetail, dischargingInfo));
    }
  }

  public void getPortWiseCommingleTempDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    Optional<com.cpdss.dischargeplan.entity.DischargeInformation> dischargingInfo =
        this.dischargeInformationRepository
            .findByVesselXidAndDischargingPatternXidAndPortRotationXidAndIsActive(
                request.getVesselId(), request.getPatternId(), request.getPortRotationId(), true);
    List<PortDischargingPlanCommingleTempDetails> portWiseRobDetails =
        portDischargingPlanCommingleTempDetailsRepository.findByDischargingInformationAndIsActive(
            dischargingInfo.get().getId(), true);
    for (PortDischargingPlanCommingleTempDetails portWiseCommingleDetail : portWiseRobDetails) {
      builder.addLoadablePlanCommingleTempDetails(
          this.buildPortWiseCommingleDetails(request, portWiseCommingleDetail, dischargingInfo));
    }
  }

  private Builder buildPortWiseCommingleDetails(
      UpdateUllageDetailsRequest request,
      PortDischargingPlanCommingleEntityDoc portWiseCommingleDetail,
      Optional<DischargeInformation> dischargingInfo) {
    LoadingPlanModels.LoadablePlanCommingleDetails.Builder newBuilder =
        LoadingPlanModels.LoadablePlanCommingleDetails.newBuilder();

    newBuilder.setLoadablePatternId(request.getPatternId());
    newBuilder.setId(portWiseCommingleDetail.getId());
    newBuilder.setLoadingInformationId(dischargingInfo.get().getId());
    newBuilder.setLoadablePlanId(portWiseCommingleDetail.getLoadablePatternId());
    newBuilder.setCargoNomination1Id(portWiseCommingleDetail.getCargoNomination1XId());
    newBuilder.setCargoNomination2Id(portWiseCommingleDetail.getCargoNomination2XId());
    newBuilder.setCargo1Id(portWiseCommingleDetail.getCargo1XId());
    newBuilder.setCargo2Id(portWiseCommingleDetail.getCargo2XId());
    newBuilder.setGrade(
        portWiseCommingleDetail.getGrade() == null ? "" : portWiseCommingleDetail.getGrade());
    newBuilder.setColorCode(
        portWiseCommingleDetail.getColorCode() == null
            ? ""
            : portWiseCommingleDetail.getColorCode());
    newBuilder.setTankName(
        portWiseCommingleDetail.getTankName() == null ? "" : portWiseCommingleDetail.getTankName());
    newBuilder.setQuantity(
        portWiseCommingleDetail.getQuantity() == null ? "" : portWiseCommingleDetail.getQuantity());
    newBuilder.setApi(
        portWiseCommingleDetail.getApi() == null ? "" : portWiseCommingleDetail.getApi());
    newBuilder.setTemperature(
        portWiseCommingleDetail.getTemperature() == null
            ? ""
            : portWiseCommingleDetail.getTemperature());
    newBuilder.setCargo1Abbreviation(
        portWiseCommingleDetail.getCargo1Abbreviation() == null
            ? ""
            : portWiseCommingleDetail.getCargo1Abbreviation());
    newBuilder.setCargo2Abbreviation(
        portWiseCommingleDetail.getCargo2Abbreviation() == null
            ? ""
            : portWiseCommingleDetail.getCargo2Abbreviation());
    newBuilder.setCargo1Percentage(
        portWiseCommingleDetail.getCargo1Percentage() == null
            ? ""
            : portWiseCommingleDetail.getCargo1Percentage());
    newBuilder.setCargo2Percentage(
        portWiseCommingleDetail.getCargo2Percentage() == null
            ? ""
            : portWiseCommingleDetail.getCargo2Percentage());
    newBuilder.setCargo1BblsDbs(
        portWiseCommingleDetail.getCargo1BblsDbs() == null
            ? ""
            : portWiseCommingleDetail.getCargo1BblsDbs());
    newBuilder.setCargo2BblsDbs(
        portWiseCommingleDetail.getCargo2BblsDbs() == null
            ? ""
            : portWiseCommingleDetail.getCargo2BblsDbs());
    newBuilder.setCargo1Bbls60F(
        portWiseCommingleDetail.getCargo1Bbls60f() == null
            ? ""
            : portWiseCommingleDetail.getCargo1Bbls60f());
    newBuilder.setCargo2Bbls60F(
        portWiseCommingleDetail.getCargo2Bbls60f() == null
            ? ""
            : portWiseCommingleDetail.getCargo2Bbls60f());
    newBuilder.setCargo1Lt(
        portWiseCommingleDetail.getCargo1Lt() == null ? "" : portWiseCommingleDetail.getCargo1Lt());
    newBuilder.setCargo2Lt(
        portWiseCommingleDetail.getCargo2Lt() == null ? "" : portWiseCommingleDetail.getCargo2Lt());
    newBuilder.setCargo1Mt(
        portWiseCommingleDetail.getCargo1Mt() == null ? "" : portWiseCommingleDetail.getCargo1Mt());
    newBuilder.setCargo2Mt(
        portWiseCommingleDetail.getCargo2Mt() == null ? "" : portWiseCommingleDetail.getCargo2Mt());
    newBuilder.setCargo1Kl(
        portWiseCommingleDetail.getCargo1Kl() == null ? "" : portWiseCommingleDetail.getCargo1Kl());
    newBuilder.setCargo2Kl(
        portWiseCommingleDetail.getCargo2Kl() == null ? "" : portWiseCommingleDetail.getCargo2Kl());
    newBuilder.setIsActive(portWiseCommingleDetail.getIsActive());
    newBuilder.setPriority(
        portWiseCommingleDetail.getPriority() == null
            ? Long.valueOf(0)
            : portWiseCommingleDetail.getPriority().longValue());
    newBuilder.setOrderQuantity(
        portWiseCommingleDetail.getOrderQuantity() == null
            ? ""
            : portWiseCommingleDetail.getOrderQuantity());
    newBuilder.setLoadingOrder(
        portWiseCommingleDetail.getDischargingOrder() == null
            ? Long.valueOf(0)
            : portWiseCommingleDetail.getDischargingOrder().longValue());
    newBuilder.setTankId(
        portWiseCommingleDetail.getTankId() == null
            ? Long.valueOf(0)
            : portWiseCommingleDetail.getTankId().longValue());
    newBuilder.setFillingRatio(
        portWiseCommingleDetail.getFillingRatio() == null
            ? ""
            : portWiseCommingleDetail.getFillingRatio());
    newBuilder.setCorrectedUllage(
        portWiseCommingleDetail.getCorrectedUllage() == null
            ? ""
            : portWiseCommingleDetail.getCorrectedUllage().toString());
    newBuilder.setCorrectionFactor(
        portWiseCommingleDetail.getCorrectionFactor() == null
            ? ""
            : portWiseCommingleDetail.getCorrectionFactor());
    newBuilder.setRdgUllage(
        portWiseCommingleDetail.getRdgUllage() == null
            ? ""
            : portWiseCommingleDetail.getRdgUllage());
    newBuilder.setSlopQuantity(
        portWiseCommingleDetail.getSlopQuantity() == null
            ? ""
            : portWiseCommingleDetail.getSlopQuantity());
    newBuilder.setTimeRequiredForLoading(
        portWiseCommingleDetail.getTimeRequiredForDischarging() == null
            ? ""
            : portWiseCommingleDetail.getTimeRequiredForDischarging());
    newBuilder.setQuantity1M3(
        portWiseCommingleDetail.getQuantityM3() == null
            ? ""
            : portWiseCommingleDetail.getQuantityM3());
    newBuilder.setUllage1(
        portWiseCommingleDetail.getUllage() == null ? "" : portWiseCommingleDetail.getUllage());
    newBuilder.setArrivalDeparture(
        portWiseCommingleDetail.getConditionType() == null
            ? ""
            : portWiseCommingleDetail.getConditionType().toString());
    newBuilder.setActualPlanned(
        portWiseCommingleDetail.getValueType() == null
            ? ""
            : portWiseCommingleDetail.getValueType().toString());
    return newBuilder;
  }

  @Override
  public void updateDischargeUllageDetails(
      UllageBillRequest request, StreamObserver<UllageBillReply> responseObserver) {
    LoadingPlanModels.UllageBillReply.Builder builder =
        LoadingPlanModels.UllageBillReply.newBuilder();
    String processId = "";
    try {

      // update and save ballast
      List<PortDischargingPlanBallastTempDetails> tempBallast =
          portLoadingPlanBallastTempDetailsRepository
              .findByDischargingInformationAndConditionTypeAndIsActive(
                  request.getUpdateUllage(0).getDischargingInfoId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  true);
      List<PortDischargingPlanBallastTempDetails> updatedBallast =
          DischargeUllageServiceUtils.updateBallast(request, tempBallast);
      portLoadingPlanBallastTempDetailsRepository.saveAll(updatedBallast);

      // update and save stowage
      List<PortDischargingPlanStowageTempDetails> tempStowage =
          portLoadingPlanStowageTempDetailsRepository
              .findByDischargingInformationAndConditionTypeAndIsActive(
                  request.getUpdateUllage(0).getDischargingInfoId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  true);
      List<PortDischargingPlanStowageTempDetails> updatedStowage =
          DischargeUllageServiceUtils.updateStowage(request, tempStowage);
      portLoadingPlanStowageTempDetailsRepository.saveAll(updatedStowage);

      // update and save ROB
      List<PortDischargingPlanRobDetails> tempRob =
          pdpRobDetailsRepository.findByDischargingInformationAndConditionTypeAndIsActive(
              request.getRobUpdate(0).getDischargingInformationId(),
              request.getRobUpdate(0).getArrivalDepartutre(),
              true);
      List<PortDischargingPlanRobDetails> updatedRob =
          DischargeUllageServiceUtils.updateRob(request, tempRob);
      pdpRobDetailsRepository.saveAll(updatedRob);

      // update and save bill of ladding
      List<BillOfLadding> updatedBillOfLadding =
          DischargeUllageServiceUtils.updateBillOfLadding(
              request, billOfLaddingRepo, dischargeInformationService);
      billOfLaddingRepo.saveAll(updatedBillOfLadding);

      if (!request.getCommingleUpdateList().isEmpty()) {
        List<PortDischargingPlanCommingleTempDetails> tempCommingle =
            portDischargingPlanCommingleTempDetailsRepository
                .findByDischargingInformationAndConditionTypeAndIsActive(
                    request.getCommingleUpdate(0).getDischargingInformationId(),
                    request.getCommingleUpdate(0).getArrivalDeparture(),
                    true);
        List<PortDischargingPlanCommingleTempDetails> updatedCommingle =
            DischargeUllageServiceUtils.updateCommingle(request, tempCommingle);
        portDischargingPlanCommingleTempDetailsRepository.saveAll(updatedCommingle);
      }

      if (request.getIsValidate() != null && request.getIsValidate().equals("true")) {
        processId = validateAndSaveData(request);
      }
      builder.setProcessId(processId);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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

  private String validateAndSaveData(UllageBillRequest request)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {
    return ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(request);
  }

  @Override
  public void saveDischargingPlan(
      DischargingPlanSaveRequest request,
      StreamObserver<DischargingPlanSaveResponse> responseObserver) {
    log.info("Inside save Discharging Plan");
    DischargingPlanSaveResponse.Builder builder = DischargingPlanSaveResponse.newBuilder();
    try {
      dischargePlanAlgoService.saveDischargingSequenceAndPlan(request);
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when saveLoadingPlan microservice is called", e);
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
  public void dischargeInfoStatusCheck(
      DischargeInfoStatusRequest request,
      StreamObserver<DischargeInfoStatusReply> responseObserver) {
    DischargeInfoStatusReply.Builder builder = DischargeInfoStatusReply.newBuilder();
    try {
      dischargeInfoStatusCheckService.checkStatus(request, builder);
      builder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when get status of discharge info", e);
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
  public void saveDischargingPlanAlgoStatus(
      AlgoStatusRequest request, StreamObserver<AlgoStatusReply> responseObserver) {
    AlgoStatusReply.Builder builder = AlgoStatusReply.newBuilder();
    try {
      dischargePlanAlgoService.saveDischargingInfoAlgoStatus(request);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Successfully updated ALGO status")
              .setStatus(DischargePlanConstants.SUCCESS)
              .build());
    } catch (GenericServiceException e) {
      log.info("GenericServiceException in saveDischargePlanAlgoStatus at DP MS ", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(e.getMessage())
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
              .build());
    } catch (Exception e) {
      e.printStackTrace();
      log.info("Exception in in saveDischargePlanAlgoStatus at DP MS ", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(e.getMessage())
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Previous closed voyage's confirmed discharge study's 'PortDischargingPlanBallastDetails'
   * details
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void dischargePlanStowageDetails(
      DischargePlanStowageDetailsRequest request,
      StreamObserver<DischargePlanStowageDetailsResponse> responseObserver) {
    List<PortDischargingPlanStowageDetails> portDischargingPlanStowageDetails =
        portDischargingPlanStowageDetailsRepository
            .findByPortRotationXIdAndConditionTypeAndValueTypeAndIsActive(
                request.getLastLoadingPortRotationId(),
                DEPARTURE_CONDITION_VALUE,
                ACTUAL_TYPE_VALUE,
                true);
    DischargePlanStowageDetailsResponse.Builder builder =
        DischargePlanStowageDetailsResponse.newBuilder();
    try {
      log.info("dischargePlanStowageDetails method called");
      List<LoadingPlanModels.LoadingPlanTankDetails> dischargePlanStowageBallastDetails =
          dischargeInformationBuilderService.buildDischargingPlanTankStowageMessage(
              portDischargingPlanStowageDetails);
      builder.addAllDischargingPlanTankDetails(dischargePlanStowageBallastDetails);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
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
  public void getDischargingSequences(
      LoadingSequenceRequest request, StreamObserver<DischargeSequenceReply> responseObserver) {

    log.info("Inside getDischargingSequences RPC ");
    DischargeSequenceReply.Builder reply = DischargeSequenceReply.newBuilder();
    try {
      dischargeSequenceService.getDischargingSequences(request, reply);
      reply.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getDischargingSequences is called", e);
      e.printStackTrace();
      reply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadicatorData(
      DischargingInfoLoadicatorDataRequest request,
      StreamObserver<DischargingInfoLoadicatorDataReply> responseObserver) {
    DischargingInfoLoadicatorDataReply.Builder reply =
        DischargingInfoLoadicatorDataReply.newBuilder();
    try {
      loadicatorService.getLoadicatorData(request, reply);
      reply.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when getting data from Loadicator", e);
      reply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(DischargePlanConstants.FAILED)
              .build());
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getDischargingInfoAlgoErrors(
      AlgoErrorRequest request, StreamObserver<AlgoErrorReply> responseObserver) {
    log.info("Inside getDischargingInfoAlgoErrors in DP MS");
    AlgoErrorReply.Builder builder = AlgoErrorReply.newBuilder();
    try {
      this.dischargePlanAlgoService.getDischargingInfoAlgoErrors(request, builder);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build()).build();
    } catch (GenericServiceException e) {
      log.info("GenericServiceException in getDischargingInfoAlgoErrors at DP MS ", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(e.getMessage())
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
              .build());
    } catch (Exception e) {
      e.printStackTrace();
      log.info("Exception in getDischargingInfoAlgoErrors at DP MS ", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(e.getMessage())
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getDischargePlanCargoHistory(
      Common.CargoHistoryOpsRequest request,
      StreamObserver<Common.CargoHistoryResponse> responseObserver) {
    Common.CargoHistoryResponse.Builder builder = Common.CargoHistoryResponse.newBuilder();
    try {
      log.info("Get cargo history for voyage id - {}", request.getVoyageId());
      cargoHistoryService.buildCargoDetailsFromStowageData(request, builder);
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

  /**
   * Validation of stowage and bill of ladding
   * @param request
   * @param responseObserver
   */
  @Override
  public void validateStowageAndBillOfLadding(
      LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request,
      StreamObserver<ResponseStatus> responseObserver) {

    ResponseStatus.Builder builder = ResponseStatus.newBuilder();
    try {
      List<LoadingPlanModels.PortWiseCargo> portWiseCargosList = request.getPortWiseCargosList();
      List<Long> portRotationIds =
          portWiseCargosList.stream()
              .map(LoadingPlanModels.PortWiseCargo::getPortRotationId)
              .collect(Collectors.toList());
      List<Long> portIds =
          portWiseCargosList.stream()
              .map(LoadingPlanModels.PortWiseCargo::getPortId)
              .collect(Collectors.toList());
      List<Long> cargoIds =
          portWiseCargosList.stream()
              .flatMap(port -> port.getCargoIdsList().stream())
              .distinct()
              .collect(Collectors.toList());
      List<PortDischargingPlanStowageDetails> stowageDetails =
          portDischargingPlanStowageDetailsRepository
              .findByPortRotationXIdAndConditionTypeAndValueTypeAndIsActive(
                  portRotationIds.get(portRotationIds.size() - 1),
                  CONDITION_TYPE_DEP,
                  VALUE_TYPE_ACTUALS, true);
      List<BillOfLadding> blList =
          billOfLaddingRepo.findByCargoNominationIdInAndIsActive(cargoIds, true);
      Map<Long, List<BillOfLadding>> portWiseBL =
          blList.stream().collect(Collectors.groupingBy(BillOfLadding::getPortId));

      if (!blList.isEmpty()
          && blList.stream()
              .allMatch(
                  bl ->
                      bl.getDischargeInformation()
                          .getDepartureStatusId()
                          .equals(ULLAGE_UPDATE_VALIDATED_TRUE))) {
        if (stowageDetails == null
            || stowageDetails.isEmpty()
            || !portWiseBL.keySet().containsAll(portIds)) {
          builder.setStatus(DischargePlanConstants.FAILED);
          return;
        }
        Map<Long, List<PortDischargingPlanStowageDetails>> cargoWiseStowage =
            stowageDetails.stream()
                .filter(v -> v.getCargoNominationXId() != 0)
                .collect(
                    Collectors.groupingBy(
                        PortDischargingPlanStowageDetails::getCargoNominationXId));
        cargoWiseStowage.forEach(
            (key, values) -> {
              if (values.stream()
                  .noneMatch(
                      v ->
                          v.getQuantity() != null
                              || v.getQuantity().compareTo(BigDecimal.ZERO) > 0)) {
                builder.setStatus(DischargePlanConstants.FAILED);
              }
            });

        portWiseCargosList.stream()
            .forEach(
                port -> {
                  try {
                    List<BillOfLadding> bLValues = portWiseBL.get(port.getPortId());
                    List<Long> dbCargos =
                        stowageDetails.stream()
                            .map(PortDischargingPlanStowageDetails::getCargoNominationXId)
                            .distinct()
                            .collect(Collectors.toList());
                    List<Long> dbBLCargos =
                        bLValues.stream()
                            .map(BillOfLadding::getCargoNominationId)
                            .distinct()
                            .collect(Collectors.toList());
                    // Checking if stowage details and bill of lading entries exists and quantity
                    // parameters are greater than zero
                    // Bug fix DSS 4458
                    // Issue fix : for commingle cargos - cargo nomination id will not be present in
                    // port loadable stowage details
                    // so removing !dbCargos.containsAll(port.getCargoIdsList()) check since this
                    // will
                    // always fail.
                    if (!dbBLCargos.containsAll(port.getCargoIdsList())
                        || (bLValues.stream()
                            .anyMatch(
                                bl ->
                                    bl.getQuantityMt() == null
                                        || bl.getQuantityMt().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getQuantityKl() == null
                                        || bl.getQuantityKl().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getQuantityBbls() == null
                                        || bl.getQuantityBbls().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getQuantityLT() == null
                                        || bl.getQuantityLT().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getApi() == null
                                        || bl.getApi().compareTo(BigDecimal.ZERO) < 0
                                        || bl.getTemperature() == null
                                        || bl.getTemperature().compareTo(BigDecimal.ZERO) < 0))) {
                      builder.setStatus(DischargePlanConstants.FAILED);
                      throw new GenericServiceException(
                          "LS actuals or BL values are missing",
                          "",
                          HttpStatusCode.SERVICE_UNAVAILABLE);
                    } else {
                      builder.setStatus(DischargePlanConstants.SUCCESS);
                    }
                    // Add check for Zero and null values
                  } catch (Exception e) {
                    builder.setStatus(DischargePlanConstants.FAILED);
                  }
                });
      } else {
        // One or more ports have its ullage update validation not successful
        builder.setStatus(DischargePlanConstants.FAILED);
        throw new GenericServiceException(
            "Ullage updated validation Pending", "", HttpStatusCode.SERVICE_UNAVAILABLE);
      }

    } catch (Exception e) {
      e.printStackTrace();
      builder.setStatus(DischargePlanConstants.FAILED);
      builder.setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value());
      builder.setMessage(e.getMessage());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
