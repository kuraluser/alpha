/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static com.cpdss.gateway.common.GatewayConstants.LOADING_RULE_MASTER_ID;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import com.cpdss.gateway.service.loadingplan.LoadingSequenceService;
import com.cpdss.gateway.utility.RuleUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingPlanServiceImpl implements LoadingPlanService {

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;

  @Autowired LoadingPlanGrpcServiceImpl loadingPlanGrpcServiceImpl;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @GrpcClient("cargoInfoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";
  @Autowired VesselInfoService vesselInfoService;

  @Autowired LoadingSequenceService loadingSequenceService;

  @Autowired LoadingPlanBuilderService loadingPlanBuilderService;

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  /**
   * Port Rotation From Loading Plan DB
   *
   * @param vesselId Numeric Long
   * @param portRId Numeric Long
   * @return Not decided
   * @throws GenericServiceException
   */
  @Override
  public Object getLoadingPortRotationDetails(Long vesselId, Long portRId)
      throws GenericServiceException {
    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info("Active Voyage {} For Vessel Id {}", activeVoyage.getVoyageNumber(), vesselId);
    // TO DO
    return activeVoyage.getPortRotations();
  }

  /**
   * @param request
   * @param correlationId
   * @return LoadingInfoAlgoResponse
   */
  public LoadingInfoAlgoResponse saveLoadingInfoStatus(
      AlgoStatusRequest request, String correlationId) throws GenericServiceException {
    log.info("update loading info status api");
    LoadingInfoAlgoResponse loadingInfoAlgoResponse = new LoadingInfoAlgoResponse();
    com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.Builder requestBuilder =
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.newBuilder();
    requestBuilder.setLoadableStudystatusId(request.getLoadingInfoStatusId());
    requestBuilder.setProcesssId(request.getProcessId());
    AlgoStatusReply reply =
        loadingPlanGrpcServiceImpl.saveLoadingInfoStatus(requestBuilder.build());
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to saveLoadingInfoStatus",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    loadingInfoAlgoResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return loadingInfoAlgoResponse;
  }

  /**
   * 1. Loading Details from LS 2. loading rate
   *
   * @param vesselId
   * @param planId
   * @param portRId
   * @return
   */
  @Override
  public LoadingInformation getLoadingInformationByPortRotation(
      Long vesselId, Long planId, Long portRId) throws GenericServiceException {
    LoadingInformation var1 = new LoadingInformation();

    final String OPERATION_TYPE = "DEP";

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get Loading Info, Active Voyage Number and Id {} ",
        activeVoyage.getVoyageNumber(),
        activeVoyage.getId());
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream().filter(v -> v.getId().equals(portRId)).findFirst();

    if (!portRotation.isPresent() || portRotation.get().getPortId() == null) {
      log.error("Port Rotation Id cannot be empty");
      throw new GenericServiceException(
          "Port Rotation Id Cannot be empty",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    LoadingPlanModels.LoadingInformation loadingInfo =
        this.loadingPlanGrpcService.fetchLoadingInformation(
            vesselId,
            activeVoyage.getId(),
            planId,
            activeVoyage.getPatternId(),
            portRotation.get().getId());

    log.info(
        "Get Loading Info, Port rotation id is available in Active Voyage, Port Id is {}",
        portRotation.get().getPortId());

    // call to synoptic/port master table for sunrise/sunset data
    LoadingDetails loadingDetails =
        this.loadingInformationService.getLoadingDetailsByPortRotationId(
            loadingInfo.getLoadingDetail(),
            vesselId,
            activeVoyage.getId(),
            portRId,
            portRotation.get().getPortId());

    // from loading info table, loading plan service
    LoadingRates loadingRates =
        this.loadingInformationService.getLoadingRateForVessel(
            loadingInfo.getLoadingRate(), vesselId);

    // Berth data from master, call to port Info service
    List<BerthDetails> masterBerthDetails =
        this.loadingInformationService.getMasterBerthDetailsByPortId(
            portRotation.get().getPortId());
    List<BerthDetails> loadingBerthDetails =
        this.loadingInformationService.buildLoadingPlanBerthDetails(
            loadingInfo.getLoadingBerthsList());

    // Call to vessel and set value from loading plan
    CargoMachineryInUse machineryInUse =
        this.loadingInformationService.getCargoMachinesInUserFromVessel(
            loadingInfo.getLoadingMachinesList(), vesselId);

    // from loading plan, user data + master data
    LoadingStages loadingStages =
        this.loadingInformationService.getLoadingStagesAndMasters(loadingInfo.getLoadingStage());

    // Topping Off Sequence
    List<ToppingOffSequence> toppingSequence =
        this.loadingInformationService.getToppingOffSequence(
            loadingInfo.getToppingOffSequenceList());

    // Call No. 1 To synoptic table api (voyage-status)
    CargoVesselTankDetails vesselTankDetails =
        this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
            vesselId,
            activeVoyage.getId(),
            activeVoyage.getActiveLs().getId(),
            portRotation.get().getPortId(),
            portRotation.get().getPortOrder(),
            portRotation.get().getId(),
            OPERATION_TYPE);
    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setLoadableQuantityCargoDetails(
        this.loadingInformationService.getLoadablePlanCargoDetailsByPort(
            vesselId,
            activeVoyage.getPatternId(),
            OPERATION_TYPE,
            portRotation.get().getId(),
            portRotation.get().getPortId()));

    LoadingSequences loadingSequences =
        this.loadingInformationService.getLoadingSequence(loadingInfo.getLoadingDelays());

    var1.setLoadingInfoId(loadingInfo.getLoadingInfoId());
    var1.setSynopticTableId(loadingInfo.getSynopticTableId());
    var1.setIsLoadingInfoComplete(loadingInfo.getIsLoadingInfoComplete());
    var1.setLoadingDetails(loadingDetails);
    var1.setLoadingRates(loadingRates);
    var1.setBerthDetails(new LoadingBerthDetails(masterBerthDetails, loadingBerthDetails));
    var1.setMachineryInUses(machineryInUse);
    var1.setLoadingStages(loadingStages);
    var1.setToppingOffSequence(toppingSequence);
    var1.setCargoVesselTankDetails(vesselTankDetails);
    var1.setLoadingSequences(loadingSequences);
    var1.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null));
    return var1;
  }

  @Override
  public LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException {
    LoadingInformationResponse response =
        this.loadingInformationService.saveLoadingInformation(request, correlationId);
    response.setLoadingInformation(
        this.getLoadingInformationByPortRotation(
            response.getVesseld(), 0L, response.getPortRotationId()));
    return response;
  }

  @Override
  public RuleResponse getLoadingPlanRules(Long vesselId, Long voyageId, Long loadingInfoId)
      throws GenericServiceException {
    LoadingPlanModels.LoadingPlanRuleRequest.Builder builder =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setSectionId(LOADING_RULE_MASTER_ID);
    builder.setLoadingInfoId(loadingInfoId);
    RuleResponse ruleResponse = this.loadingPlanGrpcService.saveOrGetLoadingPlanRules(builder);
    log.info("Loading Info Rule Fetch for Vessel Id {}, info Id {}", vesselId, loadingInfoId);
    return ruleResponse;
  }

  @Override
  public RuleResponse saveLoadingPlanRules(
      Long vesselId, Long voyageId, Long loadingInfoId, RuleRequest ruleRequest)
      throws GenericServiceException {
    LoadingPlanModels.LoadingPlanRuleRequest.Builder builder =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.setSectionId(LOADING_RULE_MASTER_ID);
    builder.setLoadingInfoId(loadingInfoId);
    RuleUtility.buildRuleListForSave(ruleRequest, null, null, builder, false, true);
    RuleResponse ruleResponse = this.loadingPlanGrpcService.saveOrGetLoadingPlanRules(builder);
    return ruleResponse;
  }


  @Override
  public LoadingSequenceResponse getLoadingSequence(Long vesselId, Long voyageId, Long infoId)
      throws GenericServiceException {
    LoadingSequenceRequest.Builder builder = LoadingSequenceRequest.newBuilder();
    builder.setLoadingInfoId(infoId);
    LoadingSequenceReply reply = this.loadingPlanGrpcService.getLoadingSequence(builder);
    LoadingSequenceResponse response = new LoadingSequenceResponse();
    loadingSequenceService.buildLoadingSequence(vesselId, reply, response);
    return response;
  }

  @Override
  public LoadingPlanAlgoResponse saveLoadingPlan(
      Long vesselId, Long voyageId, Long infoId, LoadingPlanAlgoRequest loadingPlanAlgoRequest)
      throws GenericServiceException {
    LoadingPlanAlgoResponse algoResponse = new LoadingPlanAlgoResponse();
    LoadingPlanSaveRequest.Builder builder = LoadingPlanSaveRequest.newBuilder();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.writeValue(
          new File(this.rootFolder + "/json/loadingInformationResult_" + infoId + ".json"),
          loadingPlanAlgoRequest);
    } catch (IOException e) {
      log.error("Exception encountered when saving Loading Information Response JSON");
    }
    try {
      log.info("Saving Loading Information Response JSON");
      StatusReply reply =
          this.saveJson(
              infoId,
              GatewayConstants.LOADING_INFORMATION_RESPONSE_JSON_ID,
              objectMapper.writeValueAsString(loadingPlanAlgoRequest));
      if (!GatewayConstants.SUCCESS.equals(reply.getStatus())) {
        log.error("Error occured  in gateway while writing JSON to database.");
      }
    } catch (JsonProcessingException e) {
      log.error("Exception encountered when processing Loading Information Response JSON");
    }
    loadingSequenceService.buildLoadingPlanSaveRequest(loadingPlanAlgoRequest, infoId, builder);
    LoadingPlanSaveResponse response = loadingPlanGrpcService.saveLoadingPlan(builder.build());
    if (!response.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error("Exception occured when saving loading plan");
      throw new GenericServiceException(
          "Unable to save loading plan for loading information " + infoId,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    algoResponse.setResponseStatus(new CommonSuccessResponse(SUCCESS, ""));
    return algoResponse;
  }

  @Override
  public LoadingPlanResponse getLoadingPlan(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException {

    LoadingPlanResponse loadingPlanResponse = new LoadingPlanResponse();

    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info(
        "Get Loading Plan, Active Voyage Number and Id {} ",
        activeVoyage.getVoyageNumber(),
        activeVoyage.getId());
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream()
            .filter(v -> v.getId().equals(portRotationId))
            .findFirst();

    LoadingPlanModels.LoadingPlanReply planReply =
        this.loadingPlanGrpcService.getLoadingPlan(
            vesselId, voyageId, infoId, activeVoyage.getPatternId(), portRotation.get().getId());

    LoadingInformation loadingInformation = new LoadingInformation();
    // from loading info table, loading plan service
    LoadingRates loadingRates =
        this.loadingInformationService.getLoadingRateForVessel(
            planReply.getLoadingInformation().getLoadingRate(), vesselId);

    // Topping Off Sequence
    List<ToppingOffSequence> toppingSequence =
        this.loadingInformationService.getToppingOffSequence(
            planReply.getLoadingInformation().getToppingOffSequenceList());
    loadingInformation.setLoadingRates(loadingRates);
    loadingInformation.setToppingOffSequence(toppingSequence);

    loadingPlanResponse.setLoadingInformation(loadingInformation);

    loadingPlanResponse.setPlanBallastDetails(
        loadingPlanBuilderService.buildLoadingPlanBallastFromRpc(
            planReply.getPortLoadingPlanBallastDetailsList()));
    loadingPlanResponse.setPlanStowageDetails(
        loadingPlanBuilderService.buildLoadingPlanStowageFromRpc(
            planReply.getPortLoadingPlanStowageDetailsList()));
    loadingPlanResponse.setPlanRobDetails(
        loadingPlanBuilderService.buildLoadingPlanRobFromRpc(
            planReply.getPortLoadingPlanRobDetailsList()));
    loadingPlanResponse.setPlanStabilityParams(
        loadingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(
            planReply.getPortLoadingPlanStabilityParametersList()));

    return loadingPlanResponse;
  }

  @Override
  public LoadingUpdateUllageResponse getUpdateUllageDetails(Long vesselId, Long patternId, Long portRotationId)
      throws GenericServiceException {
    LoadingPlanModels.UpdateUllageDetailsRequest.Builder requestBuilder =
        LoadingPlanModels.UpdateUllageDetailsRequest.newBuilder();
    requestBuilder.setPatternId(patternId).setPortRotationId(portRotationId).setVesselId(vesselId);
    //    try {
    // getting active voyage details
    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    log.info("Active Voyage {} For Vessel Id {}", activeVoyage.getVoyageNumber(), vesselId);

    activeVoyage.getPortRotations().stream().forEach( portRotation -> System.out.println(portRotation.getId()));

    Optional<PortRotation> portRotation =
            activeVoyage.getPortRotations().stream().filter(v -> v.getId().doubleValue() == portRotationId.doubleValue()).findFirst();

    Long loadableStudyId = activeVoyage.getActiveLs().getId();

    // Retrieve cargo information from cargo master
    CargoInfo.CargoRequest cargoRequest =
        CargoInfo.CargoRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
    CargoInfo.CargoReply cargoReply = cargoInfoServiceBlockingStub.getCargoInfo(cargoRequest);

    // Retrieve cargo Nominations from cargo nomination table
    com.cpdss.common.generated.LoadableStudy.CargoNominationRequest cargoNominationRequest =
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.newBuilder()
            .setLoadableStudyId(loadableStudyId)
            .build();
    com.cpdss.common.generated.LoadableStudy.CargoNominationReply cargoNominationReply =
        loadableStudyServiceBlockingStub.getCargoNominationById(cargoNominationRequest);

    // Get Update Ullage Data
    LoadingPlanModels.UpdateUllageDetailsResponse response =
            this.loadingPlanGrpcService.getUpdateUllageDetails(requestBuilder);

    LoadingUpdateUllageResponse outResponse = new LoadingUpdateUllageResponse();

    // Call No. 1 To synoptic table api (voyage-status)
    final String OPERATION_TYPE = "DEP";
    CargoVesselTankDetails vesselTankDetails =
            this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
                    vesselId,
                    activeVoyage.getId(),
                    activeVoyage.getActiveLs().getId(),
                    portRotation.get().getPortId(),
                    portRotation.get().getPortOrder(),
                    portRotation.get().getId(),
                    OPERATION_TYPE);
    // Call No. 2 To synoptic data for loading (same as port rotation in above code)
    vesselTankDetails.setLoadableQuantityCargoDetails(
            this.loadingInformationService.getLoadablePlanCargoDetailsByPort(
                    vesselId,
                    activeVoyage.getPatternId(),
                    OPERATION_TYPE,
                    portRotation.get().getId(),
                    portRotation.get().getPortId()));
    outResponse.setVesselTankDetails(vesselTankDetails);

    // group cargo nomination ids
    List<Long> cargoNominationIds =
            cargoNominationReply.getCargoNominationsList().stream()
            .map(cargoNomination -> cargoNomination.getId())
            .collect(Collectors.toList());
    cargoNominationIds = removeDuplicates(cargoNominationIds);
    System.out.println(cargoNominationIds);

    List<PortLoadablePlanStowageDetails> portLoadablePlanStowageDetails = this.buildPortLoadablePlanStowageDetails(response);
    outResponse.setPortLoadablePlanStowageDetails(portLoadablePlanStowageDetails);
    List<PortLoadablePlanBallastDetails> portLoadablePlanBallastDetails = this.buildPortLoadablePlanBallastDetails(response);
    outResponse.setPortLoadablePlanBallastDetails(portLoadablePlanBallastDetails);
    List<PortLoadablePlanRobDetails> portLoadablePlanRobDetails = this.buildPortLoadablePlanRobDetails(response);
    outResponse.setPortLoadablePlanRobDetails(portLoadablePlanRobDetails);
    this.buildUpdateUllageDetails(response,outResponse, cargoNominationIds, cargoNominationReply, portLoadablePlanStowageDetails);

    outResponse.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null));
    return outResponse;
    //    } catch (Exception e) {
    //      log.error(
    //          "Failed to retrieve update ullage details of  vesselID: {} on port: {}",
    //          vesselId,
    //          portId);
    //      throw new GenericServiceException(
    //          "Failed to retrieve update ullage details",
    //          CommonErrorCodes.E_HTTP_BAD_REQUEST,
    //          HttpStatusCode.BAD_REQUEST);
    //    }
  }


  private LoadingUpdateUllageResponse buildUpdateUllageDetails(
          LoadingPlanModels.UpdateUllageDetailsResponse response, LoadingUpdateUllageResponse outResponse, List<Long> cargoNominationIds, LoadableStudy.CargoNominationReply cargoNominationReply, List<PortLoadablePlanStowageDetails> portLoadablePlanStowageDetails) {
    List<CargoBillOfLadding> billOfLaddingList = new ArrayList<CargoBillOfLadding>();
    List<UpdateUllageCargoQuantityDetail> cargoQuantityDetailList = new ArrayList<UpdateUllageCargoQuantityDetail>();
    for (Long cargoNominationId : cargoNominationIds) {
      CargoBillOfLadding cargoBillOfLadding = new CargoBillOfLadding();
      UpdateUllageCargoQuantityDetail cargoQuantityDetail = new UpdateUllageCargoQuantityDetail();

      // get cargonomination details
      LoadableStudy.CargoNominationDetail cargoNomination =
              cargoNominationReply.getCargoNominationsList().stream()
                      .filter(
                              cargoNominationDetail ->
                                      cargoNominationDetail.getId() == cargoNominationId.longValue())
                      .findFirst()
                      .get();

      // get the bill of laddings for the cargo nomination
      List<Common.BillOfLadding> cargoBills =
              response.getBillOfLaddingList().stream()
                      .filter(billOfLadding -> billOfLadding.getCargoNominationId() == cargoNominationId.longValue())
                      .collect(Collectors.toList());
      List<BillOfLadding> billOfLaddings = new ArrayList<BillOfLadding>();
      if(cargoBills.size() > 0) {
        billOfLaddings =
                cargoBills.stream()
                        .<BillOfLadding>map(cargoBill -> this.buildBillOfLadding(cargoBill))
                        .collect(Collectors.toList());
      }
      cargoBillOfLadding.setCargoId(cargoNomination.getCargoId());
      cargoBillOfLadding.setCargoColor(cargoNomination.getColor());
      cargoBillOfLadding.setCargoName(cargoNomination.getCargoName());
      cargoBillOfLadding.setCargoAbbrevation(cargoNomination.getAbbreviation());
      cargoBillOfLadding.setBillOfLaddings(billOfLaddings);
      billOfLaddingList.add(cargoBillOfLadding);

      //Setting Cargo Quantity Table values
      cargoQuantityDetail.setCargoId(cargoNomination.getCargoId());
      cargoQuantityDetail.setCargoColor(cargoNomination.getColor());
      cargoQuantityDetail.setCargoName(cargoNomination.getCargoName());
      cargoQuantityDetail.setCargoAbbrevation(cargoNomination.getAbbreviation());
      cargoQuantityDetail.setApi(cargoNomination.getApi());
      cargoQuantityDetail.setTemperature(cargoNomination.getTemperature());
      Double nominationQuantity = cargoNomination.getQuantity() != null ? Double.parseDouble(cargoNomination.getQuantity()) : null;
      cargoQuantityDetail.setNominationTotal(nominationQuantity);
      Double minTolerance = cargoNomination.getMinTolerance() != null ? Double.parseDouble(cargoNomination.getMinTolerance()) : null;
      cargoQuantityDetail.setMinTolerance(minTolerance);
      Double maxTolerance = cargoNomination.getMaxTolerance() != null ? Double.parseDouble(cargoNomination.getMaxTolerance()) : null;
      cargoQuantityDetail.setMaxTolerance(maxTolerance);
      Double minQuantity = nominationQuantity * (100 + minTolerance) / 100 ;
      cargoQuantityDetail.setMinQuantity(minQuantity);
      Double maxQuantity = nominationQuantity * (100 + maxTolerance) / 100 ;
      cargoQuantityDetail.setMaxQuantity(maxQuantity);
      Double actualQuantityTotal = portLoadablePlanStowageDetails.stream()
              .filter(stowage -> stowage.getCargoNominationId().doubleValue() == cargoNominationId && stowage.getActualPlanned().equalsIgnoreCase("1"))
              .mapToDouble(stowage -> Double.parseDouble(stowage.getQuantity()))
              .reduce(0, (subtotal, element) -> subtotal + element);
      cargoQuantityDetail.setActualQuantityTotal(actualQuantityTotal);
      Double plannedQuantityTotal = portLoadablePlanStowageDetails.stream()
              .filter(stowage -> stowage.getCargoNominationId().doubleValue() == cargoNominationId && stowage.getActualPlanned().equalsIgnoreCase("2"))
              .mapToDouble(stowage -> Double.parseDouble(stowage.getQuantity()))
              .reduce(0, (subtotal, element) -> subtotal + element);
      cargoQuantityDetail.setPlannedQuantityTotal(plannedQuantityTotal);
      Double blQuantityTotal = billOfLaddings.stream()
              .mapToDouble(billOfLadding -> billOfLadding.getQuantityMt().doubleValue())
              .reduce(0, (subtotal, element) -> subtotal + element);
      cargoQuantityDetail.setBlQuantityTotal(blQuantityTotal);
      Double difference = actualQuantityTotal.doubleValue() - blQuantityTotal.doubleValue();
      cargoQuantityDetail.setDifference(difference);
      cargoQuantityDetailList.add(cargoQuantityDetail);
    }
    outResponse.setBillOfLaddingList(billOfLaddingList);
    outResponse.setCargoQuantityDetails(cargoQuantityDetailList);
    return outResponse;
  }

  private BillOfLadding buildBillOfLadding(Common.BillOfLadding cargoBill) {
    BillOfLadding billOfLadding = new BillOfLadding();
    Optional.ofNullable(cargoBill.getId()).ifPresent(id -> billOfLadding.setId(id));
    Optional.ofNullable(cargoBill.getPortId()).ifPresent(id -> billOfLadding.setPortId(id));
    Optional.ofNullable(cargoBill.getCargoNominationId())
        .ifPresent(id -> billOfLadding.setCargoNominationId(id));
    Optional.ofNullable(cargoBill.getApi())
        .ifPresent(api -> billOfLadding.setApi(new BigDecimal(api)));
    Optional.ofNullable(cargoBill.getTemperature())
        .ifPresent(temp -> billOfLadding.setTemperature(new BigDecimal(temp)));
    Optional.ofNullable(cargoBill.getQuantityMt())
        .ifPresent(quantity -> billOfLadding.setQuantityMt(new BigDecimal(quantity)));
//    Optional.ofNullable(cargoBill.getQuantityBbls())
//        .ifPresent(quantity -> billOfLadding.setQuantityBbls(new BigDecimal(quantity)));
//    Optional.ofNullable(cargoBill.getQuantityKl())
//        .ifPresent(quantity -> billOfLadding.setQuantityKl(new BigDecimal(quantity)));
    //    Optional.ofNullable(cargoBill.getBL()).ifPresent(ref -> billOfLadding.setBlRefNo(ref));
    return billOfLadding;
  }

  private List<PortLoadablePlanStowageDetails> buildPortLoadablePlanStowageDetails(LoadingPlanModels.UpdateUllageDetailsResponse response) {
    List<PortLoadablePlanStowageDetails> portLoadablePlanStowageDetailsList = new ArrayList<PortLoadablePlanStowageDetails>();
    if(response.getPortLoadablePlanStowageDetailsCount() > 0){
      response.getPortLoadablePlanStowageDetailsList().stream().forEach( portWiseStowageDetail -> {
        PortLoadablePlanStowageDetails stowageDetail = new PortLoadablePlanStowageDetails();
        stowageDetail.setAbbreviation(portWiseStowageDetail.getAbbreviation());
        stowageDetail.setApi(portWiseStowageDetail.getApi());
        stowageDetail.setCargoNominationId(portWiseStowageDetail.getCargoNominationId());
        stowageDetail.setCargoId(portWiseStowageDetail.getCargoId());
        stowageDetail.setColorCode(portWiseStowageDetail.getColorCode());
        stowageDetail.setCorrectedUllage(portWiseStowageDetail.getCorrectedUllage());
        stowageDetail.setCorrectionFactor(portWiseStowageDetail.getCorrectionFactor());
        stowageDetail.setFillingPercentage(portWiseStowageDetail.getFillingPercentage());
        stowageDetail.setId(portWiseStowageDetail.getId());
        stowageDetail.setLoadablePatternId(portWiseStowageDetail.getLoadablePatternId());
        stowageDetail.setRdgUllage(portWiseStowageDetail.getRdgUllage());
        stowageDetail.setTankId(portWiseStowageDetail.getTankId());
        stowageDetail.setTemperature(portWiseStowageDetail.getTemperature());
        stowageDetail.setWeight(portWiseStowageDetail.getWeight());
        stowageDetail.setQuantity(portWiseStowageDetail.getQuantity());
        stowageDetail.setArrivalDeparture(portWiseStowageDetail.getArrivalDeparture());
        stowageDetail.setActualPlanned(portWiseStowageDetail.getActualPlanned());
        stowageDetail.setUllage(portWiseStowageDetail.getUllage());
        portLoadablePlanStowageDetailsList.add(stowageDetail);
      });
    }
    return portLoadablePlanStowageDetailsList;
  }

  private List<PortLoadablePlanBallastDetails> buildPortLoadablePlanBallastDetails(LoadingPlanModels.UpdateUllageDetailsResponse response) {
    List<PortLoadablePlanBallastDetails> portLoadablePlanBallastDetailsList = new ArrayList<PortLoadablePlanBallastDetails>();
    if(response.getPortLoadingPlanBallastDetailsCount() > 0){
      response.getPortLoadingPlanBallastDetailsList().stream().forEach(portWiseBallastDetail -> {
        PortLoadablePlanBallastDetails ballastDetails = new PortLoadablePlanBallastDetails();
        ballastDetails.setCargoId(portWiseBallastDetail.getCargoId());
        ballastDetails.setColorCode(portWiseBallastDetail.getColorCode());
        ballastDetails.setCorrectedUllage(portWiseBallastDetail.getCorrectedUllage());
        ballastDetails.setCorrectionFactor(portWiseBallastDetail.getCorrectionFactor());
        ballastDetails.setFillingPercentage(portWiseBallastDetail.getFillingPercentage());
        ballastDetails.setId(portWiseBallastDetail.getId());
        ballastDetails.setLoadablePatternId(portWiseBallastDetail.getLoadablePatternId());
        ballastDetails.setTankId(portWiseBallastDetail.getTankId());
        ballastDetails.setTemperature(portWiseBallastDetail.getTemperature());
        ballastDetails.setQuantity(portWiseBallastDetail.getQuantity());
        ballastDetails.setArrivalDeparture(portWiseBallastDetail.getArrivalDeparture());
        ballastDetails.setActualPlanned(portWiseBallastDetail.getActualPlanned());
        ballastDetails.setSounding(portWiseBallastDetail.getSounding());
        portLoadablePlanBallastDetailsList.add(ballastDetails);
      });
    }
    return portLoadablePlanBallastDetailsList;
  }

  private List<PortLoadablePlanRobDetails> buildPortLoadablePlanRobDetails(LoadingPlanModels.UpdateUllageDetailsResponse response) {
    List<PortLoadablePlanRobDetails> portLoadablePlanRobDetailsList = new ArrayList<PortLoadablePlanRobDetails>();
    if(response.getPortLoadingPlanRobDetailsCount() > 0){
      response.getPortLoadingPlanRobDetailsList().stream().forEach(portWiseRobDetail -> {
        PortLoadablePlanRobDetails robDetail = new PortLoadablePlanRobDetails();
        robDetail.setId(portWiseRobDetail.getId());
        robDetail.setLoadablePatternId(portWiseRobDetail.getLoadablePatternId());
        robDetail.setTankId(portWiseRobDetail.getTankId());
        robDetail.setQuantity(portWiseRobDetail.getQuantity());
        robDetail.setArrivalDeparture(portWiseRobDetail.getArrivalDeparture());
        robDetail.setActualPlanned(portWiseRobDetail.getActualPlanned());
        portLoadablePlanRobDetailsList.add(robDetail);
      });
    }
    return portLoadablePlanRobDetailsList;
  }

  // Function to remove duplicates from an ArrayList
  private <T> List<T> removeDuplicates(List<T> list) {
    // Create a new LinkedHashSet
    Set<T> set = new LinkedHashSet<>();

    // Add the elements to set
    set.addAll(list);

    // Clear the list
    list.clear();

    // add the elements of set
    // with no duplicates to the list
    list.addAll(set);

    // return the list
    return list;
  }


  /**
   * @param referenceId
   * @return StatusReply
   */
  public StatusReply saveJson(Long referenceId, Long jsonTypeId, String json) {
    JsonRequest jsonRequest =
        JsonRequest.newBuilder()
            .setReferenceId(referenceId)
            .setJsonTypeId(jsonTypeId)
            .setJson(json)
            .build();
    return this.loadingPlanGrpcService.saveJson(jsonRequest);
  }
}
