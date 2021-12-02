/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.EnvoyWriter;
import com.cpdss.common.generated.LoadableStudy.SynopticalBallastRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalCommingleRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalOhqRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.SynopticalOperationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.CommunicationStatus;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.cpdss.loadingplan.service.loadicator.UllageUpdateLoadicatorService;
import com.cpdss.loadingplan.utility.LoadingPlanUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Transactional
@Service
public class LoadingPlanService {

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Value("${cpdss.build.env}")
  private String env;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String ARR = "ARR";
  private static final String DEP = "DEP";
  private static final int VALUE_TYPE = 1;
  @Autowired LoadingInformationService loadingInformationService;
  @Autowired CargoToppingOffSequenceService cargoToppingOffSequenceService;
  @Autowired LoadablePlanBallastDetailsService loadablePlanBallastDetailsService;
  @Autowired LoadablePlanCommingleDetailsService loadablePlanCommingleDetailsService;
  @Autowired LoadablePlanQuantityService loadablePlanQuantityService;
  @Autowired LoadablePlanStowageDetailsService loadablePlanStowageDetailsService;
  @Autowired PortLoadingPlanBallastDetailsRepository plpBallastDetailsRepository;
  @Autowired PortLoadingPlanStowageDetailsRepository plpStowageDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository plpRobDetailsRepository;
  @Autowired PortLoadingPlanStabilityParametersRepository plpStabilityParametersRepository;
  @Autowired PortLoadingPlanCommingleDetailsRepository plpCommingleDetailsRepository;
  @Autowired LoadingInformationBuilderService informationBuilderService;
  @Autowired LoadingBerthDetailsRepository berthDetailsRepository;
  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;

  @Autowired BillOfLaddingRepository billOfLaddingRepo;
  @Autowired PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @Autowired PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;

  @Autowired
  PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @Autowired
  PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @Autowired PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @Autowired
  PortLoadingPlanCommingleTempDetailsRepository portLoadingPlanCommingleTempDetailsRepository;

  @Autowired BillOfLandingRepository billOfLandingRepository;

  @Autowired PortLoadingPlanStowageTempDetailsRepository loadingPlanStowageDetailsTempRepository;
  @Autowired PortLoadingPlanBallastTempDetailsRepository loadingPlanBallastDetailsTempRepository;
  @Autowired PortLoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @Autowired PortLoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepositoryTemp loadingPlanRobDetailsTempRepository;

  @Autowired StageOffsetRepository stageOffsetRepository;
  @Autowired StageDurationRepository stageDurationRepository;
  @Autowired ReasonForDelayRepository reasonForDelayRepository;
  @Autowired LoadingDelayRepository loadingDelayRepository;
  @Autowired LoadingSequenceRepository loadingSequenceRepository;
  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Autowired private UllageUpdateLoadicatorService ullageUpdateLoadicatorService;
  @Autowired private LoadingPlanAlgoService loadingPlanAlgoService;
  @Autowired private LoadingPlanRuleService loadingPlanRuleService;
  @Autowired private LoadingPlanCommunicationService loadingPlancommunicationService;
  @Autowired private LoadingPlanStagingService loadingPlanStagingService;

  @Autowired
  private LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;

  @Autowired LoadingInformationStatusRepository loadingInfoStatusRepository;

  @GrpcClient("loadableStudyService")
  private SynopticalOperationServiceGrpc.SynopticalOperationServiceBlockingStub
      synopticalOperationServiceBlockingStub;
  /**
   * @param request
   * @param builder
   */
  public void loadingPlanSynchronization(
      LoadingPlanSyncDetails request, LoadingPlanSyncReply.Builder builder) {
    try {
      LoadingInformation loadingInformation = new LoadingInformation();
      LoadingInformation savedLoadingInformation =
          loadingInformationService.saveLoadingInformationDetail(
              request.getLoadingInformationDetail(), loadingInformation);
      cargoToppingOffSequenceService.saveCargoToppingOffSequenceList(
          request.getCargoToppingOffSequencesList(), savedLoadingInformation);
      loadablePlanBallastDetailsService.saveLoadablePlanBallastDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanBallastDetailsList(),
          savedLoadingInformation);
      loadablePlanCommingleDetailsService.saveLoadablePlanCommingleDetailsList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCommingleCargoDetailsList(),
          savedLoadingInformation);
      loadablePlanQuantityService.saveLoadablePlanQuantyList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCargoDetailsList(),
          savedLoadingInformation);
      loadablePlanStowageDetailsService.saveLoadablePlanStowageDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanStowageDetailsList(),
          savedLoadingInformation);
      loadingPlanRuleService.saveRulesAgainstLoadingInformation(savedLoadingInformation);
      log.info(" Saved Loading Information Id : ", savedLoadingInformation.getId());
      log.info(
          "Saved LoadingInformation on port "
              + request.getLoadingInformationDetail().getPortId()
              + " of Loadable pattern "
              + request.getLoadingInformationDetail().getLoadablePatternId());
      log.info("Communication Started for LoadingInfo when voyage activated");
      if (enableCommunication && env.equals("ship")) {
        String processId = UUID.randomUUID().toString();
        JsonArray jsonArray =
            loadingPlanStagingService.getCommunicationData(
                Arrays.asList("loading_information"),
                processId,
                MessageTypes.LOADINGPLAN_SAVE.getMessageType(),
                savedLoadingInformation.getId(),
                null);

        log.info("Json Array in Loading plan service: " + jsonArray.toString());
        EnvoyWriter.WriterReply ewReply =
            loadingPlancommunicationService.passRequestPayloadToEnvoyWriter(
                jsonArray.toString(),
                savedLoadingInformation.getVesselXId(),
                MessageTypes.LOADINGPLAN_SAVE.getMessageType());

        if (LoadingPlanConstants.SUCCESS.equals(ewReply.getResponseStatus().getStatus())) {
          log.info("------- Envoy writer has called successfully : " + ewReply.toString());
          LoadingPlanCommunicationStatus loadingPlanCommunicationStatus =
              new LoadingPlanCommunicationStatus();
          if (ewReply.getMessageId() != null) {
            loadingPlanCommunicationStatus.setMessageUUID(ewReply.getMessageId());
            loadingPlanCommunicationStatus.setCommunicationStatus(
                CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId());
          }
          loadingPlanCommunicationStatus.setReferenceId(savedLoadingInformation.getId());
          loadingPlanCommunicationStatus.setMessageType(
              MessageTypes.LOADINGPLAN_SAVE.getMessageType());
          loadingPlanCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
          LoadingPlanCommunicationStatus loadingPlanCommunicationStat =
              this.loadingPlanCommunicationStatusRepository.save(loadingPlanCommunicationStatus);
          log.info("Communication table updated : " + loadingPlanCommunicationStat.getId());
          // Set Loading Status
          Optional<LoadingInformationStatus> loadingInfoStatusOpt =
              loadingInfoStatusRepository.findByIdAndIsActive(
                  LoadingPlanConstants.LOADING_INFORMATION_COMMUNICATED_TO_SHORE, true);
          loadingInformationRepository.updateLoadingInfoWithInfoStatus(
              loadingInfoStatusOpt.get(), false, false, savedLoadingInformation.getId());
        }
      }
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Successfully saved loading information in database")
              .setStatus(LoadingPlanConstants.SUCCESS)
              .build());
    } catch (Exception e) {
      log.info(
          "Failed to save LoadingInformation on port "
              + request.getLoadingInformationDetail().getPortId()
              + " of Loadable pattern "
              + request.getLoadingInformationDetail().getLoadablePatternId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Error occured while saving loading information in database")
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setStatus(LoadingPlanConstants.SUCCESS)
              .build());
      e.printStackTrace();
    }
  }

  public void getLoadingPlan(
      LoadingPlanModels.LoadingInformationRequest request,
      LoadingPlanModels.LoadingPlanReply.Builder masterBuilder)
      throws GenericServiceException {

    Optional<LoadingInformation> var1;
    if (request.getLoadingPlanId() > 0) {
      var1 = loadingInformationService.getLoadingInformation(request.getLoadingPlanId());
    } else {
      var1 =
          loadingInformationService.getLoadingInformation(
              0L,
              request.getVesselId(),
              request.getVoyageId(),
              request.getLoadingPatternId(),
              request.getPortRotationId());
    }

    if (var1.isPresent()) {

      // <---Loading Information Start-->
      LoadingPlanModels.LoadingInformation.Builder loadingInformation =
          LoadingPlanModels.LoadingInformation.newBuilder();

      // Loading Rate From Loading Info
      //      LoadingPlanModels.LoadingRates rates =
      //          this.informationBuilderService.buildLoadingRateMessage(var1.get());
      //      loadingInformation.setLoadingRate(rates);

      // Set Saved Berth Data
      //      List<LoadingBerthDetail> list1 =
      //
      // this.berthDetailsRepository.findAllByLoadingInformationAndIsActiveTrue(var1.orElse(null));
      //      List<LoadingPlanModels.LoadingBerths> berths =
      //          this.informationBuilderService.buildLoadingBerthsMessage(list1);

      ObjectMapper mapper = new ObjectMapper();
      if (var1.get().getLoadingPlanDetailsFromAlgo() != null) {
        com.cpdss.loadingplan.domain.algo.LoadingInformation loadingInfoFromAlgo;
        try {
          loadingInfoFromAlgo =
              mapper.readValue(
                  var1.get().getLoadingPlanDetailsFromAlgo(),
                  com.cpdss.loadingplan.domain.algo.LoadingInformation.class);

          LoadingPlanModels.LoadingRates rates =
              LoadingPlanUtility.buildLoadingRates(loadingInfoFromAlgo.getLoadingRates());
          loadingInformation.setLoadingRate(rates);

          List<LoadingPlanModels.LoadingBerths> berths =
              LoadingPlanUtility.buildBerthDetails(loadingInfoFromAlgo.getBerthDetails());
          loadingInformation.addAllLoadingBerths(berths);

          LoadingPlanModels.LoadingDelay loadingDelay =
              LoadingPlanUtility.buildLoadingDelays(loadingInfoFromAlgo.getLoadingSequences());
          loadingInformation.setLoadingDelays(loadingDelay);

        } catch (JsonProcessingException e) {
          log.error("Could not process loading plan details from ALGO");
        }
      } else {
        throw new GenericServiceException(
            "Cannot find loading plan details obtained from ALGO",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }

      // Topping Off Sequence
      List<CargoToppingOffSequence> list2 =
          this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(
              var1.orElse(null));

      // Loading Sequences
      // Stage Min Amount Master
      List<StageOffset> list3 = this.stageOffsetRepository.findAll();
      // Stage Duration Master
      List<StageDuration> list4 = this.stageDurationRepository.findAll();

      // Staging User data and Master data
      LoadingPlanModels.LoadingStages loadingStages =
          this.informationBuilderService.buildLoadingStageMessage(var1.orElse(null), list3, list4);
      loadingInformation.setLoadingStage(loadingStages);

      //      // Loading Delay
      //      List<ReasonForDelay> list5 = this.reasonForDelayRepository.findAll();
      //      List<LoadingDelay> list6 =
      //          this.loadingDelayRepository.findAllByLoadingInformationAndIsActiveTrueOrderById(
      //              var1.orElse(null));
      //      LoadingPlanModels.LoadingDelay loadingDelay =
      //          this.informationBuilderService.buildLoadingDelayMessage(list5, list6);

      Optional.ofNullable(var1.get().getLoadingInformationStatus())
          .ifPresent(status -> loadingInformation.setLoadingInfoStatusId(status.getId()));
      Optional.ofNullable(var1.get().getArrivalStatus())
          .ifPresent(status -> loadingInformation.setLoadingPlanArrStatusId(status.getId()));
      Optional.ofNullable(var1.get().getDepartureStatus())
          .ifPresent(status -> loadingInformation.setLoadingPlanDepStatusId(status.getId()));
      Optional.ofNullable(var1.get().getLoadablePatternXId())
          .ifPresent(loadingInformation::setLoadablePatternId);

      // <---Loading Information End-->

      // <---Cargo Details Start-->
      List<PortLoadingPlanBallastDetails> plpBallastList =
          plpBallastDetailsRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      List<PortLoadingPlanStowageDetails> plpStowageList =
          plpStowageDetailsRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      List<PortLoadingPlanRobDetails> plpRobList =
          plpRobDetailsRepository.findByLoadingInformationAndIsActive(var1.get().getId(), true);
      List<PortLoadingPlanStabilityParameters> plpStabilityList =
          plpStabilityParametersRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      List<PortLoadingPlanCommingleDetails> plpCommingleList =
          plpCommingleDetailsRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      // Removing tanks that were loaded from previous port.
      List<Long> toBeLoadedCargoNominationIds =
          loadingSequenceRepository.findToBeLoadedCargoNominationIdByLoadingInformationAndIsActive(
              var1.get(), true);
      List<Long> toBeLoadedTanks =
          plpStowageList.stream()
              .filter(
                  stowage -> toBeLoadedCargoNominationIds.contains(stowage.getCargoNominationXId()))
              .map(stowage -> stowage.getTankXId())
              .collect(Collectors.toList());
      list2.removeIf(toppingOff -> !toBeLoadedTanks.contains(toppingOff.getTankXId()));
      List<LoadingPlanModels.LoadingToppingOff> toppingOff =
          this.informationBuilderService.buildToppingOffMessage(list2);
      loadingInformation.addAllToppingOffSequence(toppingOff);

      masterBuilder.setLoadingInformation(loadingInformation.build());
      masterBuilder.addAllPortLoadingPlanBallastDetails(
          this.informationBuilderService.buildLoadingPlanTankBallastMessage(plpBallastList));
      masterBuilder.addAllPortLoadingPlanStowageDetails(
          this.informationBuilderService.buildLoadingPlanTankStowageMessage(plpStowageList));
      masterBuilder.addAllPortLoadingPlanRobDetails(
          this.informationBuilderService.buildLoadingPlanTankRobMessage(plpRobList));
      masterBuilder.addAllPortLoadingPlanStabilityParameters(
          this.informationBuilderService.buildLoadingPlanTankStabilityMessage(plpStabilityList));
      masterBuilder.addAllPortLoadingPlanCommingleDetails(
          this.informationBuilderService.buildLoadingPlanCommingleMessage(plpCommingleList));
      // <---Loading Information End-->
    } else {
      log.error("Failed to fetch Loading Plan, Loading info Id is 0");
      throw new GenericServiceException(
          "Loading Info Id Is 0", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    masterBuilder.setResponseStatus(
        ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    masterBuilder.build();
  }

  public void getBillOfLaddingDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {

    List<BillOfLadding> billOfLaddingDetails =
        this.billOfLaddingRepo.findByLoadablePatternXIdAndPortIdAndIsActive(
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
    return;
  }

  public void getPortWiseStowageDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    List<PortLoadingPlanStowageDetails> portWiseStowageDetails =
        portLoadingPlanStowageDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortLoadingPlanStowageDetails portWiseStowageDetail : portWiseStowageDetails) {
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
      builder.addPortLoadablePlanStowageDetails(newBuilder);
    }
  }

  public void getPortWiseBallastDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    List<PortLoadingPlanBallastDetails> portWiseStowageDetails =
        portLoadingPlanBallastDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortLoadingPlanBallastDetails portWiseBallastDetail : portWiseStowageDetails) {
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
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    List<PortLoadingPlanRobDetails> portWiseRobDetails =
        portLoadingPlanRobDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortLoadingPlanRobDetails portWiseRobDetail : portWiseRobDetails) {
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

  public void getLoadableStudyShoreTwo(
      LoadingPlanModels.UllageBillRequest request,
      LoadingPlanModels.UllageBillReply.Builder builder) {

    String processId = "";
    try {

      request
          .getBillOfLandingList()
          .forEach(
              billOfLanding -> {
                if (billOfLanding.getIsUpdate()) {
                  billOfLandingRepository.updateBillOfLandingRepository(
                      billOfLanding.getBlRefNumber(),
                      StringUtils.isEmpty(billOfLanding.getBblAt60F())
                          ? null
                          : new BigDecimal(billOfLanding.getBblAt60F()),
                      StringUtils.isEmpty(billOfLanding.getQuantityLt())
                          ? null
                          : new BigDecimal(billOfLanding.getQuantityLt()),
                      StringUtils.isEmpty(billOfLanding.getQuantityMt())
                          ? null
                          : new BigDecimal(billOfLanding.getQuantityMt()),
                      StringUtils.isEmpty(billOfLanding.getKlAt15C())
                          ? null
                          : new BigDecimal(billOfLanding.getKlAt15C()),
                      StringUtils.isEmpty(billOfLanding.getApi())
                          ? null
                          : new BigDecimal(billOfLanding.getApi()),
                      StringUtils.isEmpty(billOfLanding.getTemperature())
                          ? null
                          : new BigDecimal(billOfLanding.getTemperature()),
                      Long.valueOf(billOfLanding.getId() + ""));
                } else if (billOfLanding.getId() == 0) {
                  BillOfLanding landing = new BillOfLanding();
                  landing.setLoadingId(billOfLanding.getLoadingId());
                  landing.setPortId(billOfLanding.getPortId());
                  landing.setCargoId(billOfLanding.getCargoId());
                  landing.setBlRefNumber(billOfLanding.getBlRefNumber());
                  landing.setBblAt60f(
                      StringUtils.isEmpty(billOfLanding.getBblAt60F())
                          ? null
                          : new BigDecimal(billOfLanding.getBblAt60F()));
                  landing.setKlAt15c(
                      StringUtils.isEmpty(billOfLanding.getKlAt15C())
                          ? null
                          : new BigDecimal(billOfLanding.getKlAt15C()));
                  landing.setQuantityLt(
                      StringUtils.isEmpty(billOfLanding.getQuantityLt())
                          ? null
                          : new BigDecimal(billOfLanding.getQuantityLt()));
                  landing.setQuantityMt(
                      StringUtils.isEmpty(billOfLanding.getQuantityMt())
                          ? null
                          : new BigDecimal(billOfLanding.getQuantityMt()));
                  landing.setApi(
                      StringUtils.isEmpty(billOfLanding.getApi())
                          ? null
                          : new BigDecimal(billOfLanding.getApi()));
                  landing.setTemperature(
                      StringUtils.isEmpty(billOfLanding.getTemperature())
                          ? null
                          : new BigDecimal(billOfLanding.getTemperature()));
                  landing.setIsActive(true);
                  landing.setVersion(billOfLanding.getVersion());
                  billOfLandingRepository.save(landing);
                }
              });

      request
          .getBillOfLandingRemoveList()
          .forEach(
              billOfLanding -> {
                billOfLandingRepository.deleteBillOfLandingRepository(
                    Long.valueOf(billOfLanding.getId() + ""));
              });

      Integer tempBallastCount =
          portLoadingPlanBallastTempDetailsRepository
              .findByLoadingInformationAndConditionTypeAndIsActive(
                  request.getUpdateUllage(0).getLoadingInformationId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  true)
              .size();
      request
          .getBallastUpdateList()
          .forEach(
              ullageInsert -> {
                if ((ullageInsert.getIsUpdate() && tempBallastCount > 0) || tempBallastCount > 0) {
                  loadingPlanBallastDetailsTempRepository.updateLoadingPlanBallastDetailsRepository(
                      StringUtils.isEmpty(ullageInsert.getSg())
                          ? null
                          : new BigDecimal(ullageInsert.getSg()),
                      StringUtils.isEmpty(ullageInsert.getCorrectedUllage())
                          ? null
                          : new BigDecimal(ullageInsert.getCorrectedUllage()),
                      ullageInsert.getColorCode(),
                      new BigDecimal(ullageInsert.getQuantity()),
                      new BigDecimal(ullageInsert.getSounding()),
                      new BigDecimal(ullageInsert.getQuantity()),
                      new BigDecimal(ullageInsert.getFillingPercentage()),
                      new BigDecimal(ullageInsert.getCorrectionFactor()),
                      Long.valueOf(ullageInsert.getTankId()),
                      Long.valueOf(ullageInsert.getLoadingInformationId()),
                      Long.valueOf(ullageInsert.getArrivalDepartutre()));
                } else {
                  PortLoadingPlanBallastTempDetails details =
                      new PortLoadingPlanBallastTempDetails();
                  details.setLoadingInformation(ullageInsert.getLoadingInformationId());
                  details.setPortRotationXId(ullageInsert.getPortRotationXid());
                  details.setPortXId(ullageInsert.getPortXid());
                  details.setTankXId(ullageInsert.getTankId());
                  details.setTemperature(
                      StringUtils.isEmpty(ullageInsert.getTemperature())
                          ? null
                          : new BigDecimal(ullageInsert.getTemperature()));
                  details.setCorrectedUllage(
                      StringUtils.isEmpty(ullageInsert.getCorrectedUllage())
                          ? null
                          : new BigDecimal(ullageInsert.getCorrectedUllage()));
                  details.setQuantity(
                      StringUtils.isEmpty(ullageInsert.getQuantity())
                          ? null
                          : new BigDecimal(ullageInsert.getQuantity()));
                  details.setObservedM3(
                      StringUtils.isEmpty(ullageInsert.getObservedM3())
                          ? null
                          : new BigDecimal(ullageInsert.getObservedM3()));
                  details.setFillingPercentage(
                      StringUtils.isEmpty(ullageInsert.getFillingPercentage())
                          ? null
                          : new BigDecimal(ullageInsert.getFillingPercentage()));
                  details.setSounding(
                      StringUtils.isEmpty(ullageInsert.getSounding())
                          ? null
                          : new BigDecimal(ullageInsert.getSounding()));
                  details.setValueType(ullageInsert.getActualPlanned());
                  details.setConditionType(ullageInsert.getArrivalDepartutre());
                  details.setIsActive(true);
                  details.setColorCode(ullageInsert.getColorCode());
                  details.setSg(
                      StringUtils.isEmpty(ullageInsert.getSg())
                          ? null
                          : new BigDecimal(ullageInsert.getSg()));
                  loadingPlanBallastDetailsTempRepository.save(details);
                }
              });

      Integer tempStowageCount =
          portLoadingPlanStowageTempDetailsRepository
              .findByLoadingInformationAndConditionTypeAndIsActive(
                  request.getUpdateUllage(0).getLoadingInformationId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  true)
              .size();

      request
          .getUpdateUllageList()
          .forEach(
              ullageInsert -> {
                if ((ullageInsert.getIsUpdate() && tempStowageCount > 0) || tempStowageCount > 0) {
                  loadingPlanStowageDetailsTempRepository
                      .updatePortLoadingPlanStowageDetailsRepository(
                          new BigDecimal(ullageInsert.getQuantity()),
                          new BigDecimal(ullageInsert.getUllage()),
                          new BigDecimal(ullageInsert.getQuantity()),
                          new BigDecimal(ullageInsert.getApi()),
                          new BigDecimal(ullageInsert.getTemperature()),
                          new BigDecimal(ullageInsert.getCorrectedUllage()),
                          new BigDecimal(ullageInsert.getFillingPercentage()),
                          new BigDecimal(ullageInsert.getCorrectionFactor()),
                          Long.valueOf(ullageInsert.getTankId()),
                          Long.valueOf(ullageInsert.getLoadingInformationId()),
                          Long.valueOf(ullageInsert.getArrivalDepartutre()));
                } else {
                  PortLoadingPlanStowageTempDetails tempData =
                      new PortLoadingPlanStowageTempDetails();
                  tempData.setLoadingInformation(ullageInsert.getLoadingInformationId());
                  tempData.setTankXId(ullageInsert.getTankId());
                  tempData.setTemperature(
                      StringUtils.isEmpty(ullageInsert.getTemperature())
                          ? null
                          : new BigDecimal(ullageInsert.getTemperature()));
                  tempData.setCorrectedUllage(
                      StringUtils.isEmpty(ullageInsert.getCorrectedUllage())
                          ? null
                          : new BigDecimal(ullageInsert.getCorrectedUllage()));
                  tempData.setQuantity(new BigDecimal(ullageInsert.getQuantity()));
                  tempData.setFillingPercentage(
                      StringUtils.isEmpty(ullageInsert.getFillingPercentage())
                          ? null
                          : new BigDecimal(ullageInsert.getFillingPercentage()));
                  tempData.setApi(
                      StringUtils.isEmpty(ullageInsert.getApi())
                          ? null
                          : new BigDecimal(ullageInsert.getApi()));
                  tempData.setCargoNominationXId(ullageInsert.getCargoNominationXid());
                  tempData.setPortXId(ullageInsert.getPortXid());
                  tempData.setPortRotationXId(ullageInsert.getPortRotationXid());
                  tempData.setValueType(ullageInsert.getActualPlanned());
                  tempData.setConditionType(ullageInsert.getArrivalDepartutre());
                  tempData.setCorrectionFactor(
                      StringUtils.isEmpty(ullageInsert.getCorrectionFactor())
                          ? null
                          : new BigDecimal(ullageInsert.getCorrectionFactor()));
                  tempData.setIsActive(true);
                  tempData.setUllage(
                      StringUtils.isEmpty(ullageInsert.getUllage())
                          ? null
                          : new BigDecimal(ullageInsert.getUllage()));
                  tempData.setColorCode(ullageInsert.getColorCode());
                  tempData.setAbbreviation(ullageInsert.getAbbreviation());
                  tempData.setCargoXId(ullageInsert.getCargoId());
                  loadingPlanStowageDetailsTempRepository.save(tempData);
                }
              });

      Integer robActualsCount =
          portLoadingPlanRobDetailsRepository
              .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                  request.getUpdateUllage(0).getLoadingInformationId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE,
                  true)
              .size();

      request
          .getRobUpdateList()
          .forEach(
              ullageInsert -> {
                if (ullageInsert.getIsUpdate() || robActualsCount > 0) {
                  loadingPlanRobDetailsRepository.updatePortLoadingPlanRobDetailsRepository(
                      StringUtils.isEmpty(ullageInsert.getQuantity())
                          ? null
                          : new BigDecimal(ullageInsert.getQuantity()),
                      StringUtils.isEmpty(ullageInsert.getQuantity())
                          ? null
                          : new BigDecimal(ullageInsert.getQuantity()),
                      Long.valueOf(ullageInsert.getTankId()),
                      ullageInsert.getLoadingInformationId(),
                      ullageInsert.getArrivalDepartutre(),
                      ullageInsert.getActualPlanned());
                } else {
                  PortLoadingPlanRobDetails robDet = new PortLoadingPlanRobDetails();
                  robDet.setLoadingInformation(ullageInsert.getLoadingInformationId());
                  robDet.setTankXId(Long.valueOf(ullageInsert.getTankId()));
                  robDet.setQuantity(
                      StringUtils.isEmpty(ullageInsert.getQuantity())
                          ? null
                          : new BigDecimal(ullageInsert.getQuantity()));
                  robDet.setPortXId(Long.valueOf(ullageInsert.getPortXid()));
                  robDet.setPortRotationXId(Long.valueOf(ullageInsert.getPortRotationXid()));
                  robDet.setConditionType(ullageInsert.getArrivalDepartutre());
                  robDet.setValueType(LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
                  robDet.setIsActive(true);
                  robDet.setColorCode(ullageInsert.getColourCode());
                  robDet.setDensity(
                      StringUtils.isEmpty(ullageInsert.getDensity())
                          ? null
                          : new BigDecimal(ullageInsert.getDensity()));
                  loadingPlanRobDetailsRepository.save(robDet);
                }
              });
      Integer tempCommingleCount =
          portLoadingPlanCommingleTempDetailsRepository
              .findByLoadingInformationAndConditionTypeAndIsActive(
                  request.getUpdateUllage(0).getLoadingInformationId(),
                  request.getUpdateUllage(0).getArrivalDepartutre(),
                  true)
              .size();

      request
          .getCommingleUpdateList()
          .forEach(
              ullageInsert -> {
                if ((ullageInsert.getIsUpdate() && tempCommingleCount > 0)
                    || tempCommingleCount > 0) {
                  portLoadingPlanCommingleTempDetailsRepository.updateLoadingPlanCommingleDetails(
                      ullageInsert.getApi(),
                      ullageInsert.getFillingPercentage(),
                      ullageInsert.getQuantityMT(),
                      ullageInsert.getQuantityM3(),
                      ullageInsert.getTemperature(),
                      ullageInsert.getQuantity1MT(),
                      ullageInsert.getQuantity2MT(),
                      ullageInsert.getQuantity1M3(),
                      ullageInsert.getQuantity2M3(),
                      ullageInsert.getUllage1(),
                      ullageInsert.getUllage2(),
                      ullageInsert.getUllage(),
                      ullageInsert.getColorCode(),
                      ullageInsert.getTankId(),
                      ullageInsert.getLoadingInformationId(),
                      ullageInsert.getArrivalDeparture());
                } else {
                  PortLoadingPlanCommingleTempDetails tempData =
                      new PortLoadingPlanCommingleTempDetails();
                  tempData.setLoadingInformation(ullageInsert.getLoadingInformationId());
                  tempData.setTankId(ullageInsert.getTankId());
                  tempData.setTemperature(ullageInsert.getTemperature());
                  tempData.setQuantity(ullageInsert.getQuantityMT());
                  tempData.setFillingRatio(ullageInsert.getFillingPercentage());
                  tempData.setApi(ullageInsert.getApi());
                  tempData.setCargoNomination1XId(ullageInsert.getCargoNomination1Id());
                  tempData.setCargoNomination2XId(ullageInsert.getCargoNomination2Id());
                  tempData.setCargo1XId(ullageInsert.getCargo1Id());
                  tempData.setCargo2XId(ullageInsert.getCargo2Id());
                  tempData.setValueType(ullageInsert.getActualPlanned());
                  tempData.setConditionType(ullageInsert.getArrivalDeparture());
                  tempData.setIsActive(true);
                  tempData.setUllage(ullageInsert.getUllage());
                  tempData.setQuantity1MT(ullageInsert.getQuantity1MT());
                  tempData.setQuantity2MT(ullageInsert.getQuantity2MT());
                  tempData.setQuantity1M3(ullageInsert.getQuantity1M3());
                  tempData.setQuantity2M3(ullageInsert.getQuantity2M3());
                  tempData.setUllage1(ullageInsert.getUllage1());
                  tempData.setUllage2(ullageInsert.getUllage2());
                  tempData.setGrade(ullageInsert.getAbbreviation());
                  tempData.setColorCode(ullageInsert.getColorCode());
                  portLoadingPlanCommingleTempDetailsRepository.save(tempData);
                }
              });
      if (request.getIsValidate() != null && request.getIsValidate().equals("true")) {
        processId = validateAndSaveData(request);
      } else {
        updateLoadingPlanStatusForUllageUpdate(
            request.getUpdateUllage(0).getLoadingInformationId(),
            request.getUpdateUllage(0).getArrivalDepartutre(),
            LoadingPlanConstants.UPDATE_ULLAGE_VALIDATION_PENDING_ID);
      }
      builder.setProcessId(processId);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    }
    // log.info("getLoadableStudyShoreTwo ", request);
  }

  /**
   * Updates loading information status based on the condition i.e. Arrival / Departure.
   *
   * @param loadingInformationId
   * @param arrivalDepartutre
   * @param updateUllageValidationPendingId
   * @throws GenericServiceException
   */
  private void updateLoadingPlanStatusForUllageUpdate(
      long loadingInformationId, int arrivalDeparture, Long statusId)
      throws GenericServiceException {
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(loadingInformationId);
    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Cannot find Loading Information: " + loadingInformationId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    Optional<LoadingInformationStatus> loadingInfoStatusOpt =
        loadingPlanAlgoService.getLoadingInformationStatus(statusId);

    if (loadingInfoOpt.isEmpty()) {
      throw new GenericServiceException(
          "Cannot find Loading Information Status: " + statusId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    updateLoadingPlanStatus(loadingInfoOpt.get(), loadingInfoStatusOpt.get(), arrivalDeparture);
  }

  public void updateLoadingPlanStatus(
      LoadingInformation loadingInformation,
      LoadingInformationStatus loadingInfoStatus,
      int conditionType) {
    if (LoadingPlanConstants.LOADING_PLAN_ARRIVAL_CONDITION_VALUE == conditionType) {
      loadingInformationRepository.updateLoadingInformationArrivalStatus(
          loadingInfoStatus, loadingInformation.getId());
    } else if (LoadingPlanConstants.LOADING_PLAN_DEPARTURE_CONDITION_VALUE == conditionType) {
      loadingInformationRepository.updateLoadingInformationDepartureStatus(
          loadingInfoStatus, loadingInformation.getId());
    }
  }

  private String validateAndSaveData(LoadingPlanModels.UllageBillRequest request)
      throws GenericServiceException, IllegalAccessException, InvocationTargetException {
    return ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(request);
  }

  public void saveUpdatedLoadingPlanDetails(
      LoadingInformation loadingInformation, Integer conditionType)
      throws IllegalAccessException, InvocationTargetException, NumberFormatException,
          GenericServiceException {
    // Method used for Updating port loading plan stowage and ballast details from  temp table to
    // permanent after loadicator done
    log.info("Updating Loading Plan Details of Loading Information {}", loadingInformation.getId());
    List<PortLoadingPlanStowageTempDetails> tempStowageList =
        portLoadingPlanStowageTempDetailsRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInformation.getId(), conditionType, true);
    List<PortLoadingPlanStowageDetails> stowageEntityList = new ArrayList<>();
    if (!tempStowageList.isEmpty()) {
      log.info("Copying stowage details from temporary tables");
      for (PortLoadingPlanStowageTempDetails tempStowageEntity : tempStowageList) {
        PortLoadingPlanStowageDetails stowageEntity = new PortLoadingPlanStowageDetails();
        BeanUtils.copyProperties(tempStowageEntity, stowageEntity);
        stowageEntity.setId(null);
        stowageEntity.setCreatedBy(null);
        stowageEntity.setCreatedDate(null);
        stowageEntity.setCreatedDateTime(null);
        stowageEntity.setLastModifiedBy(null);
        stowageEntity.setLastModifiedDate(null);
        stowageEntity.setLastModifiedDateTime(null);
        stowageEntity.setIsActive(true);
        stowageEntity.setLoadingInformation(loadingInformation);
        stowageEntityList.add(stowageEntity);
      }
      // Deleting existing entry from actual table before pushing new records
      portLoadingPlanStowageDetailsRepository
          .deleteExistingByLoadingInfoAndConditionTypeAndValueType(
              loadingInformation.getId(),
              conditionType,
              LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
      portLoadingPlanStowageDetailsRepository.saveAll(stowageEntityList);
    }

    List<PortLoadingPlanBallastTempDetails> tempBallastList =
        portLoadingPlanBallastTempDetailsRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInformation.getId(), conditionType, true);
    List<PortLoadingPlanBallastDetails> ballastEntityList = new ArrayList<>();
    if (!tempBallastList.isEmpty()) {
      log.info("Copying ballast details from temporary tables");
      for (PortLoadingPlanBallastTempDetails tempBallastEntity : tempBallastList) {
        PortLoadingPlanBallastDetails ballastEntity = new PortLoadingPlanBallastDetails();
        BeanUtils.copyProperties(tempBallastEntity, ballastEntity);
        ballastEntity.setId(null);
        ballastEntity.setCreatedBy(null);
        ballastEntity.setCreatedDate(null);
        ballastEntity.setCreatedDateTime(null);
        ballastEntity.setLastModifiedBy(null);
        ballastEntity.setLastModifiedDate(null);
        ballastEntity.setLastModifiedDateTime(null);
        ballastEntity.setIsActive(true);
        ballastEntity.setLoadingInformation(loadingInformation);
        ballastEntityList.add(ballastEntity);
      }
      // Deleting existing entry from actual table before pushing new records
      portLoadingPlanBallastDetailsRepository
          .deleteExistingByLoadingInfoAndConditionTypeAndValueType(
              loadingInformation.getId(),
              conditionType,
              LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
      portLoadingPlanBallastDetailsRepository.saveAll(ballastEntityList);
    }

    List<PortLoadingPlanCommingleTempDetails> tempCommingleList =
        portLoadingPlanCommingleTempDetailsRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(
                loadingInformation.getId(), conditionType, true);

    List<PortLoadingPlanCommingleDetails> commingleEntityList =
        new ArrayList<PortLoadingPlanCommingleDetails>();
    if (!tempCommingleList.isEmpty()) {
      log.info("Copying commingle details from temporary tables");
      for (PortLoadingPlanCommingleTempDetails tempCommingleEntity : tempCommingleList) {
        PortLoadingPlanCommingleDetails commingleEntity = new PortLoadingPlanCommingleDetails();
        BeanUtils.copyProperties(tempCommingleEntity, commingleEntity);
        commingleEntity.setId(null);
        commingleEntity.setCreatedBy(null);
        commingleEntity.setCreatedDate(null);
        commingleEntity.setCreatedDateTime(null);
        commingleEntity.setLastModifiedBy(null);
        commingleEntity.setLastModifiedDate(null);
        commingleEntity.setLastModifiedDateTime(null);
        commingleEntity.setIsActive(true);
        commingleEntity.setLoadingInformation(loadingInformation);
        commingleEntityList.add(commingleEntity);
      }
      // Deleting existing entry from actual table before pushing new records
      portLoadingPlanCommingleDetailsRepository
          .deleteExistingByLoadingInfoAndConditionTypeAndValueType(
              loadingInformation.getId(),
              conditionType,
              LoadingPlanConstants.LOADING_PLAN_ACTUAL_TYPE_VALUE);
      portLoadingPlanCommingleDetailsRepository.saveAll(commingleEntityList);
    }
    /**
     * copying data to synoptical table. stowage quantity as cargo rob quantity as ohq ballas as
     * ballast condition type is used to determine which records are need to be updated(arrival/
     * departure) port id and port rotation id is also needed
     */
    SynopticalTableRequest.Builder request = SynopticalTableRequest.newBuilder();
    request.setLoadablePatternId(loadingInformation.getLoadablePatternXId());
    if (conditionType.equals(1)) {
      request.setOperationType(ARR);
    } else {
      request.setOperationType(DEP);
    }
    request.setVesselId(loadingInformation.getVesselXId());
    SynopticalRecord.Builder synopticalData = SynopticalRecord.newBuilder();
    synopticalData.setPortId(loadingInformation.getPortXId());
    synopticalData.setPortRotationId(loadingInformation.getPortRotationXId());
    stowageEntityList.stream()
        .forEach(
            stowage -> {
              SynopticalCargoRecord.Builder cargo = SynopticalCargoRecord.newBuilder();
              cargo.setActualWeight(stowage.getQuantity().toString());
              cargo.setTankId(stowage.getTankXId());
              cargo.setActualApi(stowage.getApi().toString());
              cargo.setActualTemperature(stowage.getTemperature().toString());
              cargo.setUllage(stowage.getUllage().toString());
              synopticalData.addCargo(cargo);
            });
    ballastEntityList.stream()
        .forEach(
            ballast -> {
              SynopticalBallastRecord.Builder ballastRecord = SynopticalBallastRecord.newBuilder();
              ballastRecord.setActualWeight(ballast.getQuantity().toString());
              ballastRecord.setTankId(ballast.getTankXId());
              synopticalData.addBallast(ballastRecord);
            });
    List<PortLoadingPlanRobDetails> robDetails =
        portLoadingPlanRobDetailsRepository
            .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                loadingInformation.getId(), conditionType, VALUE_TYPE, true);
    robDetails.stream()
        .forEach(
            rob -> {
              SynopticalOhqRecord.Builder ohq = SynopticalOhqRecord.newBuilder();
              ohq.setActualWeight(rob.getQuantity().toString());
              ohq.setTankId(rob.getTankXId());
              synopticalData.addOhq(ohq);
            });

    commingleEntityList.stream()
        .forEach(
            commingle -> {
              SynopticalCommingleRecord.Builder commingleRecord =
                  SynopticalCommingleRecord.newBuilder();
              commingleRecord.setActualWeight(commingle.getQuantity());
              commingleRecord.setTankId(commingle.getTankId());
              synopticalData.addCommingle(commingleRecord);
            });
    request.addSynopticalRecord(synopticalData);
    ResponseStatus response =
        synopticalOperationServiceBlockingStub.updateSynopticalTable(request.build());
    if (!SUCCESS.equals(response.getStatus())) {
      throw new GenericServiceException(
          "Failed to update actuals",
          response.getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(response.getCode())));
    }
    // Deleting existing entry from temp tables
    portLoadingPlanStowageTempDetailsRepository.deleteExistingByLoadingInfoAndConditionType(
        loadingInformation.getId(), conditionType);
    portLoadingPlanBallastTempDetailsRepository.deleteExistingByLoadingInfoAndConditionType(
        loadingInformation.getId(), conditionType);
    portLoadingPlanCommingleTempDetailsRepository.deleteExistingByLoadingInfoAndConditionType(
        loadingInformation.getId(), conditionType);
  }

  public void getPortWiseStowageTempDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    List<PortLoadingPlanStowageTempDetails> portWiseStowageTempDetails =
        portLoadingPlanStowageTempDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortLoadingPlanStowageTempDetails portWiseStowageDetail : portWiseStowageTempDetails) {
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
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    List<PortLoadingPlanBallastTempDetails> portWiseBallastTempDetails =
        portLoadingPlanBallastTempDetailsRepository.findByPatternIdAndPortRotationIdAndIsActive(
            request.getPatternId(), request.getPortRotationId(), true);
    for (PortLoadingPlanBallastTempDetails portWiseBallastDetail : portWiseBallastTempDetails) {
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
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    Optional<LoadingInformation> loadingInfo =
        this.loadingInformationRepository
            .findByVesselXIdAndLoadablePatternXIdAndPortRotationXIdAndIsActiveTrue(
                request.getVesselId(), request.getPatternId(), request.getPortRotationId());
    List<PortLoadingPlanCommingleDetails> portWiseRobDetails =
        portLoadingPlanCommingleDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfo.get(), true);
    for (PortLoadingPlanCommingleDetails portWiseCommingleDetail : portWiseRobDetails) {
      builder.addLoadablePlanCommingleDetails(
          this.buildPortWiseCommingleDetails(request, portWiseCommingleDetail, loadingInfo));
    }
  }

  public void getPortWiseCommingleTempDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      LoadingPlanModels.UpdateUllageDetailsResponse.Builder builder) {
    Optional<LoadingInformation> loadingInfo =
        this.loadingInformationRepository
            .findByVesselXIdAndLoadablePatternXIdAndPortRotationXIdAndIsActiveTrue(
                request.getVesselId(), request.getPatternId(), request.getPortRotationId());
    List<PortLoadingPlanCommingleTempDetails> portWiseRobDetails =
        portLoadingPlanCommingleTempDetailsRepository.findByLoadingInformationAndIsActive(
            loadingInfo.get().getId(), true);
    for (PortLoadingPlanCommingleTempDetails portWiseCommingleDetail : portWiseRobDetails) {
      builder.addLoadablePlanCommingleTempDetails(
          this.buildPortWiseCommingleDetails(request, portWiseCommingleDetail, loadingInfo));
    }
  }

  private LoadingPlanModels.LoadablePlanCommingleDetails.Builder buildPortWiseCommingleDetails(
      LoadingPlanModels.UpdateUllageDetailsRequest request,
      PortLoadingPlanCommingleEntityDoc portWiseCommingleDetail,
      Optional<LoadingInformation> loadingInfo) {
    LoadingPlanModels.LoadablePlanCommingleDetails.Builder newBuilder =
        LoadingPlanModels.LoadablePlanCommingleDetails.newBuilder();

    newBuilder.setLoadablePatternId(request.getPatternId());
    newBuilder.setId(portWiseCommingleDetail.getId());
    newBuilder.setLoadingInformationId(loadingInfo.get().getId());
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
        portWiseCommingleDetail.getLoadingOrder() == null
            ? Long.valueOf(0)
            : portWiseCommingleDetail.getLoadingOrder().longValue());
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
        portWiseCommingleDetail.getTimeRequiredForLoading() == null
            ? ""
            : portWiseCommingleDetail.getTimeRequiredForLoading());
    newBuilder.setQuantity1MT(
        portWiseCommingleDetail.getQuantity1MT() == null
            ? ""
            : portWiseCommingleDetail.getQuantity1MT());
    newBuilder.setQuantity2MT(
        portWiseCommingleDetail.getQuantity2MT() == null
            ? ""
            : portWiseCommingleDetail.getQuantity2MT());
    newBuilder.setQuantity1M3(
        portWiseCommingleDetail.getQuantity1M3() == null
            ? ""
            : portWiseCommingleDetail.getQuantity1M3());
    newBuilder.setQuantity2M3(
        portWiseCommingleDetail.getQuantity2M3() == null
            ? ""
            : portWiseCommingleDetail.getQuantity2M3());
    newBuilder.setUllage1(
        portWiseCommingleDetail.getUllage1() == null ? "" : portWiseCommingleDetail.getUllage1());
    newBuilder.setUllage2(
        portWiseCommingleDetail.getUllage2() == null ? "" : portWiseCommingleDetail.getUllage2());
    newBuilder.setActualPlanned(
        portWiseCommingleDetail.getValueType() == null
            ? ""
            : portWiseCommingleDetail.getValueType().toString());
    newBuilder.setArrivalDeparture(
        portWiseCommingleDetail.getConditionType() == null
            ? ""
            : portWiseCommingleDetail.getConditionType().toString());
    newBuilder.setUllage(
        portWiseCommingleDetail.getUllage() == null ? "" : portWiseCommingleDetail.getUllage());
    return newBuilder;
  }
}
