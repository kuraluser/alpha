/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.ACTIVE_VOYAGE_STATUS;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_REQUEST;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy.ActiveVoyage;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.VesselTankRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankResponse;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.domain.ArrivalConditionJson;
import com.cpdss.loadablestudy.domain.CargoNomination;
import com.cpdss.loadablestudy.domain.CargoNominationOperationDetails;
import com.cpdss.loadablestudy.domain.CowDetail;
import com.cpdss.loadablestudy.domain.DischargeStudyAlgoJson;
import com.cpdss.loadablestudy.domain.DischargeStudyPortInstructionDetailsJson;
import com.cpdss.loadablestudy.domain.DischargeStudyPortRotationJson;
import com.cpdss.loadablestudy.domain.LoadablePlanStowageDetailsJson;
import com.cpdss.loadablestudy.domain.LoadableStudyInstruction;
import com.cpdss.loadablestudy.domain.OnHandQuantity;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.domain.StabilityParameter;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.DischargeStudyCowDetail;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import com.cpdss.loadablestudy.entity.PortInstruction;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyStatusRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.PortInstructionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Component
public class GenerateDischargeStudyJson {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final String INVALID_DISCHARGE_STUDY_ID = "INVALID_DISCHARGE_STUDY_ID";
  public static final String INVALID_DISCHARGE_QUANTITY = "INVALID_DISCHARGE_QUANTITY";

  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @Value("${algo.dischargestudy.api.url}")
  private String dischargeStudyUrl;

  @Autowired private PortInstructionRepository portInstructionRepository;

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private CowDetailService cowDetailService;

  @Autowired private RestTemplate restTemplate;

  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;

  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;

  @Autowired private LoadableStudyService loadableStudyService;

  @Autowired VoyageService voyageService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceBlockingStub loadingPlanGrpcService;

  public AlgoReply.Builder generateDischargePatterns(
      AlgoRequest request, AlgoReply.Builder replyBuilder)
      throws GenericServiceException, Exception {
    // Checking requested discharge Id validity
    Optional<LoadableStudy> loadableStudyOpt =
        loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (loadableStudyOpt.isPresent()) {

      DischargeStudyAlgoJson AlgoJsonPayload =
          this.generateDischargeStudyJson(request.getLoadableStudyId(), loadableStudyOpt.get());

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(
          new File(
              this.rootFolder + "/json/dischargeStudy_" + request.getLoadableStudyId() + ".json"),
          AlgoJsonPayload);
      loadableStudyService.saveJsonToDatabase(
          request.getLoadableStudyId(),
          LOADABLE_STUDY_REQUEST, // TODO
          objectMapper.writeValueAsString(AlgoJsonPayload));

      // Calling Algo Service
      AlgoResponse algoResponse =
          restTemplate.postForObject(dischargeStudyUrl, AlgoJsonPayload, AlgoResponse.class);
      updateProcessIdForDischargeStudy(
          algoResponse.getProcessId(),
          loadableStudyOpt.get(),
          LOADABLE_STUDY_PROCESSING_STARTED_ID); // TODO

      loadableStudyRepository.updateLoadableStudyStatus(
          LOADABLE_STUDY_PROCESSING_STARTED_ID, loadableStudyOpt.get().getId());

      replyBuilder
          .setProcesssId(algoResponse.getProcessId())
          .setResponseStatus(
              Common.ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());

    } else {
      log.info("INVALID_DISCHARGE_STUDY {} - ", request.getLoadableStudyId());
      throw new GenericServiceException(
          INVALID_DISCHARGE_STUDY_ID,
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return replyBuilder;
  }

  public void updateProcessIdForDischargeStudy(
      String processId, LoadableStudy loadableStudy, Long loadableStudyStatus) {
    LoadableStudyAlgoStatus status = new LoadableStudyAlgoStatus();
    status.setLoadableStudy(loadableStudy);
    status.setIsActive(true);
    status.setLoadableStudyStatus(loadableStudyStatusRepository.getOne(loadableStudyStatus));
    status.setProcessId(processId);
    status.setVesselxid(loadableStudy.getVesselXId());
    loadableStudyAlgoStatusRepository.save(status);
  }

  public DischargeStudyAlgoJson generateDischargeStudyJson(
      Long dischargeStudyId, LoadableStudy loadableStudy) throws GenericServiceException {
    log.info("Generating Discharge study request Json for  :{}", dischargeStudyId);
    DischargeStudyAlgoJson dischargeStudyAlgoJson = new DischargeStudyAlgoJson();

    dischargeStudyAlgoJson.setId(dischargeStudyId);
    dischargeStudyAlgoJson.setVoyageId(loadableStudy.getVoyage().getId());
    dischargeStudyAlgoJson.setVoyageNo(loadableStudy.getVoyage().getVoyageNo());
    dischargeStudyAlgoJson.setVesselId(loadableStudy.getVesselXId());
    dischargeStudyAlgoJson.setName(loadableStudy.getName()); // TODO discharge name

    List<LoadableStudyInstruction> instructionsDetails = getAllLoadableStudyInstruction();
    dischargeStudyAlgoJson.setInstructionMaster(instructionsDetails);

    dischargeStudyAlgoJson.setCommingleCargos(
        new ArrayList<>()); // reserved for future.Keeping empty for now

    List<DischargeStudyPortRotationJson> portRotationList =
        getDischargeStudyPortRotation(dischargeStudyId, instructionsDetails);
    dischargeStudyAlgoJson.setDischargeStudyPortRotation(portRotationList);

    List<CargoNomination> cargoNominations = getCargoNomination(dischargeStudyId);
    dischargeStudyAlgoJson.setCargoNomination(cargoNominations);
    dischargeStudyAlgoJson.setCargoNominationOperationDetails(
        getCargoNominationOperationDetails(dischargeStudyId, cargoNominations));

    dischargeStudyAlgoJson.setOnHandQuantity(getOnHandQuantity(dischargeStudyId));

    dischargeStudyAlgoJson.setPortDetails(getPortDetails(portRotationList));

    dischargeStudyAlgoJson.setCowHistory(
        new ArrayList<>()); // reserved for future.Keeping empty for now

    dischargeStudyAlgoJson.setLoadablePlanPortWiseDetails(
        getLoadablePlanPortWiseDetails(
            dischargeStudyId,
            loadableStudy.getVesselXId(),
            loadableStudy.getVoyage().getVoyageNo()));

    return dischargeStudyAlgoJson;
  }

  private ArrivalConditionJson getLoadablePlanPortWiseDetails(
      Long dischargeStudyId, Long vesselId, String voyageNo) throws GenericServiceException {
    log.info("Fetching LoadablePlan PortWise Details for discharge id {}", dischargeStudyId);
    ActiveVoyage.Builder builder = ActiveVoyage.newBuilder();
    try {
      this.voyageService.fetchActiveVoyageByVesselId(builder, vesselId, ACTIVE_VOYAGE_STATUS);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to fetch active voyage for, Vessel Id {}", vesselId);
      throw new GenericServiceException(
          "NO active voyage Found",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    ActiveVoyage activeVoyage = builder.build();
    if (!activeVoyage.getVoyageNumber().equals(voyageNo)) {
      log.error(
          "Active voyage number and request voyage number does not match for DischargeStudyID {}",
          dischargeStudyId);
      throw new GenericServiceException(
          "Active voyage number and request voyage number does not match ",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } else {
      log.info("Found active voyage {} ", voyageNo);
      Optional<PortRotationDetail> optionalPortRotationDetail =
          activeVoyage.getPortRotationList().stream()
              .filter(item -> item.getOperationId() == 1L)
              .max(Comparator.comparing(PortRotationDetail::getPortOrder));
      if (optionalPortRotationDetail.isEmpty()) {
        log.error("No port rotation details found for voyage  {} ", voyageNo);
        throw new GenericServiceException(
            "No port rotation details found for voyage",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      } else {
        log.info(
            "Requesting loading plan for port rotation id {}",
            optionalPortRotationDetail.get().getId());
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest.Builder
            LoadingInformationRequest =
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
                    .newBuilder();
        LoadingInformationRequest.setCompanyId(1L);
        LoadingInformationRequest.setVesselId(vesselId);
        LoadingInformationRequest.setVoyageId(activeVoyage.getId());
        LoadingInformationRequest.setLoadingPatternId(activeVoyage.getPatternId());
        LoadingInformationRequest.setPortRotationId(optionalPortRotationDetail.get().getId());
        LoadingPlanReply loadingPlanReply =
            loadingPlanGrpcService.getLoadingPlan(LoadingInformationRequest.build());
        if (SUCCESS != loadingPlanReply.getResponseStatus().getStatus()) {
          log.error(
              "No Loading plan found for port rotaion id {} ",
              optionalPortRotationDetail.get().getId());
          throw new GenericServiceException(
              "No Loading plan found for port rotaion",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        } else {
          return buildArrivalCondition(loadingPlanReply);
        }
      }
    }
  }

  private ArrivalConditionJson buildArrivalCondition(LoadingPlanReply loadingPlanReply) {
    ArrivalConditionJson arrivalCondition = new ArrivalConditionJson();

    arrivalCondition.setLoadableQuantityCargoDetails(new ArrayList<>()); // For future
    arrivalCondition.setLoadableQuantityCommingleCargoDetails(new ArrayList<>()); // For future

    ofNullable(loadingPlanReply.getPortLoadingPlanStabilityParametersList())
        .ifPresent(
            stabilityParameterFromloadingPlanReply ->
                arrivalCondition.setStabilityParameters(
                    getStabilityParameters(stabilityParameterFromloadingPlanReply)));

    ofNullable(loadingPlanReply.getPortLoadingPlanStowageDetailsList())
        .ifPresent(
            loadablePlanStowageFromloadingPlanReply ->
                arrivalCondition.setLoadablePlanStowageDetails(
                    getLoadablePlanStowageDetails(loadablePlanStowageFromloadingPlanReply)));

    ofNullable(loadingPlanReply.getPortLoadingPlanBallastDetailsList())
        .ifPresent(
            loadablePlanBallastFromloadingPlanReply ->
                arrivalCondition.setLoadablePlanBallastDetails(
                    getLoadablePlanStowageDetails(loadablePlanBallastFromloadingPlanReply)));

    return arrivalCondition;
  }

  //	private ArrivalConditionJson getLoadablePlanPortWiseDetails(Long dischargeStudyId) throws
  // GenericServiceException {
  //		log.info("Fetching LoadablePlan PortWise Details for discharge id {}", dischargeStudyId);
  //		LoadablePatternRequest.Builder request = LoadablePatternRequest.newBuilder();
  //		ArrivalConditionJson arrivalCondition = new ArrivalConditionJson();
  //		request.setLoadableStudyId(dischargeStudyId);
  //
  //		LoadablePatternReply loadablePatternReply = loadablePatternService
  //				.getLoadablePatternDetails(request.build(), LoadablePatternReply.newBuilder()).build();
  //
  //		if (loadablePatternReply.getResponseStatus().getStatus() != SUCCESS) {
  //
  //		} else {
  //			ofNullable(loadablePatternReply.getConfirmPlanEligibility())
  //					.ifPresent(arrivalCondition::setConfirmPlanEligibility);
  //
  //			// Fetch arrival condition details form list of loading pattern details.
  //			LoadablePattern loadablePattern = loadablePatternReply.getLoadablePatternList().get(0);
  //
  //
  //	ofNullable(loadablePattern.getLoadablePatternId()).ifPresent(arrivalCondition::setLoadablePatternId);
  //			ofNullable(loadablePattern.getLoadableStudyStatusId())
  //					.ifPresent(arrivalCondition::setLoadableStudyStatusId);
  //			ofNullable(loadablePattern.getCaseNumber()).ifPresent(arrivalCondition::setCaseNumber);
  //
  //			ofNullable(loadablePattern.getLoadableQuantityCargoDetailsList())
  //					.ifPresent(loadableQuantityCargoDetails ->
  // arrivalCondition.setLoadableQuantityCargoDetails(
  //							getLoadableQuantityCargoDetails(loadableQuantityCargoDetails)));
  //
  //			ofNullable(loadablePattern.getLoadableQuantityCommingleCargoDetailsList())
  //					.ifPresent(loadableQuantityCommingleCargoDetails -> arrivalCondition
  //							.setLoadableQuantityCommingleCargoDetails(new ArrayList<>())); // For future
  //
  //			ofNullable(loadablePattern.getLoadablePlanStowageDetailsList())
  //					.ifPresent(loadablePlanStowageDetails -> arrivalCondition
  //
  //	.setLoadablePlanStowageDetails(getLoadablePlanStowageDetails(loadablePlanStowageDetails)));
  //
  //			ofNullable(loadablePattern.getLoadablePlanBallastDetailsList())
  //					.ifPresent(loadablePlanBallastDetails -> arrivalCondition
  //
  //	.setLoadablePlanBallastDetails(getLoadablePlanBallastDetails(loadablePlanBallastDetails)));
  //
  //			ofNullable(loadablePattern.getStabilityParameters())
  //					.ifPresent(item -> arrivalCondition.setStabilityParameters(getStabilityParameters(item)));
  //		}
  //
  //		return null;
  //	}

  //	private List<LoadablePlanBallastDetails> getLoadablePlanBallastDetails(
  //			List<LoadingPlanTankDetails> loadablePlanBallastFromloadingPlanReply) {
  //
  //		List<LoadablePlanBallastDetails> ballastDetailsList = new ArrayList<>();
  //		loadablePlanBallastFromloadingPlanReply = loadablePlanBallastFromloadingPlanReply.stream()
  //			.filter(item -> item.getConditionType() == 1L && item.getValueType() ==
  // 1L).collect(Collectors.toList());
  //
  //		loadablePlanBallastFromloadingPlanReply.forEach(item -> {
  //			LoadablePlanBallastDetails ballastDetails = new LoadablePlanBallastDetails();
  //			ofNullable(item.getId()).ifPresent(ballastDetails::setId);
  //			ofNullable(item.getTankId()).ifPresent(ballastDetails::setTankId);
  //			ofNullable(item.getCorrectionFactor()).ifPresent(ballastDetails::setCorrectionFactor);
  //			ofNullable(item.getCorrectedLevel()).ifPresent(ballastDetails::setCorrectedUllage);
  //			ofNullable(item.getSg()).ifPresent(ballastDetails::setSg);
  //			ofNullable(item.getTankName()).ifPresent(ballastDetails::setTankName);
  //			ofNullable(item.getMetricTon()).ifPresent(ballastDetails::setQuantityMT);
  //			// ofNullable(item.get).ifPresent(ballastDetails::setFillingRatio);
  //			ofNullable(item.getTankShortName()).ifPresent(ballastDetails::setTankShortName);
  //
  //			ballastDetailsList.add(ballastDetails);
  //		});
  //		return ballastDetailsList;
  //	}

  private List<LoadablePlanStowageDetailsJson> getLoadablePlanStowageDetails(
      List<LoadingPlanTankDetails> loadablePlanStowageFromloadingPlanReply) {

    List<LoadablePlanStowageDetailsJson> stowageDetailsList = new ArrayList<>();
    loadablePlanStowageFromloadingPlanReply =
        loadablePlanStowageFromloadingPlanReply.stream()
            .filter(item -> item.getConditionType() == 1L && item.getValueType() == 1L)
            .collect(Collectors.toList());

    loadablePlanStowageFromloadingPlanReply.forEach(
        item -> {
          LoadablePlanStowageDetailsJson stowageDetails = new LoadablePlanStowageDetailsJson();
          ofNullable(item.getApi()).ifPresent(stowageDetails::setApi);
          ofNullable(item.getCargoNominationId()).ifPresent(stowageDetails::setCargoNominationId);
          ofNullable(item.getQuantity()).ifPresent(stowageDetails::setQuantity);
          ofNullable(item.getTankId()).ifPresent(stowageDetails::setTankId);
          ofNullable(item.getTemperature()).ifPresent(stowageDetails::setTemperature);
          ofNullable(item.getUllage()).ifPresent(stowageDetails::setUllage);
          ofNullable(item.getQuantityM3()).ifPresent(stowageDetails::setQuantityM3);
          ofNullable(item.getSounding()).ifPresent(stowageDetails::setSounding);
          stowageDetailsList.add(stowageDetails);
        });
    return stowageDetailsList;
  }

  //	private List<LoadableQuantityCargoDetails> getLoadableQuantityCargoDetails(
  //			List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails>
  // loadableQuantityCargoDetails) {
  //		List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList = new ArrayList<>();
  //		loadableQuantityCargoDetails.forEach(item -> {
  //			LoadableQuantityCargoDetails quantityCargoDetail = new LoadableQuantityCargoDetails();
  //			ofNullable(item.getId()).ifPresent(quantityCargoDetail::setId);
  //			ofNullable(item.getEstimatedAPI()).ifPresent(quantityCargoDetail::setEstimatedAPI);
  //			ofNullable(item.getEstimatedTemp()).ifPresent(quantityCargoDetail::setEstimatedTemp);
  //			ofNullable(item.getMinTolerence()).ifPresent(quantityCargoDetail::setMinTolerence);
  //			ofNullable(item.getMaxTolerence()).ifPresent(quantityCargoDetail::setMaxTolerence);
  //			ofNullable(item.getLoadableMT()).ifPresent(quantityCargoDetail::setLoadableMT);
  //
  //	ofNullable(item.getDifferencePercentage()).ifPresent(quantityCargoDetail::setDifferencePercentage);
  //			ofNullable(item.getCargoId()).ifPresent(quantityCargoDetail::setCargoId);
  //			ofNullable(item.getOrderQuantity()).ifPresent(quantityCargoDetail::setOrderedQuantity);
  //			ofNullable(item.getCargoAbbreviation()).ifPresent(quantityCargoDetail::setCargoAbbreviation);
  //			ofNullable(item.getColorCode()).ifPresent(quantityCargoDetail::setColorCode);
  //			ofNullable(item.getPriority()).ifPresent(quantityCargoDetail::setPriority);
  //			ofNullable(item.getLoadingOrder()).ifPresent(quantityCargoDetail::setLoadingOrder);
  //			ofNullable(item.getCargoNominationId()).ifPresent(quantityCargoDetail::setCargoNominationId);
  //			ofNullable(item.getSlopQuantity()).ifPresent(quantityCargoDetail::setSlopQuantity);
  //
  //	ofNullable(item.getTimeRequiredForLoading()).ifPresent(quantityCargoDetail::setTimeRequiredForLoading);
  //
  //			loadableQuantityCargoDetailsList.add(quantityCargoDetail);
  //		});
  //		return loadableQuantityCargoDetailsList;
  //	}

  private StabilityParameter getStabilityParameters(
      List<LoadingPlanStabilityParameters> loadingPlanStabilityParametersList) {

    Optional<LoadingPlanStabilityParameters> optionalItem =
        loadingPlanStabilityParametersList.stream()
            .filter(item -> item.getConditionType() == 1L && item.getValueType() == 1L)
            .findAny();
    StabilityParameter parameter = new StabilityParameter();
    if (optionalItem.isPresent()) {
      ofNullable(optionalItem.get().getDraft()).ifPresent(parameter::setDraft);
      ofNullable(optionalItem.get().getTrim()).ifPresent(parameter::setTrim);
      ofNullable(optionalItem.get().getBm()).ifPresent(parameter::setBm);
      ofNullable(optionalItem.get().getSf()).ifPresent(parameter::setSf);
    }
    return parameter;
  }

  private List<PortDetails> getPortDetails(List<DischargeStudyPortRotationJson> portRotationList) {
    log.info("Fetching Port details for {} ports", portRotationList.size());
    if (!CollectionUtils.isEmpty(portRotationList)) {
      List<PortDetails> portDetailsList = new ArrayList<>();
      List<Long> portIds =
          portRotationList.stream().map(item -> item.getPortId()).collect(Collectors.toList());
      PortInfo.PortReply portReply = getPortInfo(portIds);
      if (portReply.getResponseStatus().getStatus() == SUCCESS) {
        portReply
            .getPortsList()
            .forEach(
                item -> {
                  PortDetails portdetail = new PortDetails();
                  ofNullable(item.getId()).ifPresent(portdetail::setId);
                  ofNullable(item.getName()).ifPresent(portdetail::setName);
                  ofNullable(item.getCode()).ifPresent(portdetail::setCode);
                  ofNullable(item.getAverageTideHeight())
                      .ifPresent(portdetail::setAverageTideHeight);
                  ofNullable(item.getTideHeight()).ifPresent(portdetail::setTideHeight);
                  ofNullable(item.getSerializedSize())
                      .ifPresent(i -> portdetail.setDensitySeaWater(i.toString()));
                  ofNullable(item.getCountryName()).ifPresent(portdetail::setCountryName);
                  portDetailsList.add(portdetail);
                });
        log.info("Found {} items", portDetailsList.size());
        return portDetailsList;
      }
    }
    log.info("No Port details  found ");
    return null;
  }

  /**
   * @param build
   * @return PortReply
   */
  public PortInfo.PortReply getPortInfo(List<Long> portIds) {
    PortInfo.GetPortInfoByPortIdsRequest request =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder().addAllId(portIds).build();
    return portInfoGrpcService.getPortInfoByPortIds(request);
  }

  private List<OnHandQuantity> getOnHandQuantity(Long dischargeStudyId) {
    log.info("Fetching OnHand quantity details for discharge id {}", dischargeStudyId);
    List<com.cpdss.loadablestudy.entity.OnHandQuantity> ohqList =
        onHandQuantityRepository.findByDischargeStudyIdAndActive(dischargeStudyId);
    if (!CollectionUtils.isEmpty(ohqList)) {
      List<OnHandQuantity> onHandQuantityList = new ArrayList<>();
      ohqList.forEach(
          item -> {
            OnHandQuantity onHandQuantity = new OnHandQuantity();
            ofNullable(item.getId()).ifPresent(onHandQuantity::setId);
            ofNullable(item.getPortXId()).ifPresent(onHandQuantity::setPortId);
            ofNullable(item.getFuelTypeXId()).ifPresent(onHandQuantity::setFueltypeId);
            ofNullable(item.getTankXId()).ifPresent(onHandQuantity::setTankId);
            ofNullable(item.getArrivalVolume())
                .ifPresent(i -> onHandQuantity.setArrivalVolume(i.toString()));
            ofNullable(item.getArrivalQuantity())
                .ifPresent(i -> onHandQuantity.setArrivalQuantity(i.toString()));
            ofNullable(item.getDepartureVolume())
                .ifPresent(i -> onHandQuantity.setDepartureVolume(i.toString()));
            ofNullable(item.getDepartureQuantity())
                .ifPresent(i -> onHandQuantity.setDepartureQuantity(i.toString()));
            onHandQuantityList.add(onHandQuantity);
          });
      log.info("Found {} items", onHandQuantityList.size());
      return onHandQuantityList;
    }
    log.info("No  OnHand quantity details found ");
    return null;
  }

  private List<CargoNominationOperationDetails> getCargoNominationOperationDetails(
      Long dischargeStudyId, List<CargoNomination> cargoNominations) {
    log.info("Fetching CargoNomination Operation details for discharge id {}", dischargeStudyId);
    List<com.cpdss.loadablestudy.entity.CargoNomination> cargoNominationReply =
        getCargoNominationforDischargeID(dischargeStudyId);
    if (!CollectionUtils.isEmpty(cargoNominationReply)) {
      List<CargoNominationOperationDetails> cargoNominationOperationDetailsList = new ArrayList<>();
      cargoNominationReply.forEach(
          item -> {
            if (!CollectionUtils.isEmpty(item.getCargoNominationPortDetails())) {
              Iterator<CargoNominationPortDetails> iterator =
                  item.getCargoNominationPortDetails().iterator();
              while (iterator.hasNext()) {
                CargoNominationPortDetails iteratorItem = iterator.next();
                CargoNominationOperationDetails cargoNominationOperation =
                    new CargoNominationOperationDetails();
                ofNullable(iteratorItem.getId()).ifPresent(cargoNominationOperation::setId);
                ofNullable(item.getId()).ifPresent(cargoNominationOperation::setCargoNominationId);
                ofNullable(iteratorItem.getPortId()).ifPresent(cargoNominationOperation::setPortId);
                ofNullable(iteratorItem.getQuantity())
                    .ifPresent(i -> cargoNominationOperation.setQuantity(i.toString()));
                cargoNominationOperationDetailsList.add(cargoNominationOperation);
              }
            }
          });
      log.info("Found {} items", cargoNominationOperationDetailsList.size());
      return cargoNominationOperationDetailsList;
    }
    log.info("No CargoNomination operation details found ");
    return null;
  }

  private List<com.cpdss.loadablestudy.entity.CargoNomination> getCargoNominationforDischargeID(
      Long dischargeStudyId) {
    return this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
        dischargeStudyId, true);
  }

  private List<CargoNomination> getCargoNomination(Long dischargeStudyId) {
    log.info("Fetching CargoNomination details for discharge id {}", dischargeStudyId);
    List<CargoNomination> cargoNominationList = new ArrayList<>();
    List<com.cpdss.loadablestudy.entity.CargoNomination> cargoNominationReply =
        getCargoNominationforDischargeID(dischargeStudyId);
    if (!CollectionUtils.isEmpty(cargoNominationReply)) {
      cargoNominationReply.forEach(
          item -> {
            CargoNomination nomination = new CargoNomination();
            ofNullable(item.getId()).ifPresent(nomination::setId);
            ofNullable(item.getLoadableStudyXId()).ifPresent(nomination::setLoadableStudyId);
            ofNullable(item.getPriority()).ifPresent(nomination::setPriority);
            ofNullable(item.getColor()).ifPresent(nomination::setColor);
            ofNullable(item.getCargoXId()).ifPresent(nomination::setCargoId);
            ofNullable(item.getAbbreviation()).ifPresent(nomination::setAbbreviation);
            ofNullable(item.getQuantity()).ifPresent(nomination::setQuantity);
            ofNullable(item.getMaxTolerance()).ifPresent(nomination::setMaxTolerance);
            ofNullable(item.getMinTolerance()).ifPresent(nomination::setMinTolerance);
            ofNullable(item.getApi()).ifPresent(nomination::setApi);
            ofNullable(item.getTemperature()).ifPresent(nomination::setTemperature);
            ofNullable(item.getSegregationXId()).ifPresent(nomination::setSegregationId);
            nomination.setIsCondensateCargo(null); // for future
            nomination.setIsHrvpCargo(null); // for future
            cargoNominationList.add(nomination);
          });
      log.info("Found {} items", cargoNominationList.size());
      return cargoNominationList;
    }
    log.info("No CargoNomination details found ");
    return null;
  }

  private List<DischargeStudyPortRotationJson> getDischargeStudyPortRotation(
      Long dischargeStudyId, List<LoadableStudyInstruction> instructionsDetails) {
    log.info("Fetching Port Roataion details ");
    List<DischargeStudyPortRotationJson> portRotationList = new ArrayList<>();
    PortRotationRequest.Builder request = PortRotationRequest.newBuilder();
    request.setLoadableStudyId(dischargeStudyId);
    PortRotationReply reply =
        loadableStudyPortRotationService
            .getPortRotationByLoadableStudyId(request.build(), PortRotationReply.newBuilder())
            .build();
    if (reply.getResponseStatus().getStatus() != SUCCESS) {
      log.info("No port rotaion details found for Discharge ID  :{}", dischargeStudyId);
      return null;
    } else {
      reply
          .getPortsList()
          .forEach(
              port -> {
                DischargeStudyPortRotationJson portRotation = new DischargeStudyPortRotationJson();
                portRotation.setId(port.getId());
                portRotation.setDischargeStudyId(port.getLoadableStudyId());
                portRotation.setPortId(port.getPortId());
                portRotation.setBerthId(port.getBerthId());
                portRotation.setOperationId(port.getOperationId());
                portRotation.setSeaWaterDensity(port.getSeaWaterDensity());
                portRotation.setDistanceBetweenPorts(port.getDistanceBetweenPorts());
                portRotation.setTimeOfStay(port.getTimeOfStay());
                portRotation.setMaxDraft(port.getMaxDraft());
                portRotation.setMaxAirDraft(port.getMaxDraft());
                portRotation.setEta(port.getEta());
                portRotation.setEtd(port.getEtd());
                portRotation.setPortOrder(port.getPortOrder());
                portRotation.setCowDetails(getCowDetails(dischargeStudyId, port.getPortId()));
                portRotation.setInstructions(getPortInstructions(port, instructionsDetails));

                portRotationList.add(portRotation);
              });
      log.info("Found {} items", portRotationList.size());
      return portRotationList;
    }
  }

  private CowDetail getCowDetails(Long dischargeStudyId, Long portId) {
    log.info("Getting Cow details for {}", portId);
    DischargeStudyCowDetail reply =
        cowDetailService.getCowDetailForOnePort(dischargeStudyId, portId);
    if (reply != null) {

      CowDetail cowDetail = new CowDetail();
      cowDetail.setId(reply.getId());
      cowDetail.setType(reply.getCowType());
      cowDetail.setPercentage(reply.getPercentage());

      List<Long> tankIdList =
          Stream.of(reply.getTankIds().split(","))
              .map(Long::parseLong)
              .collect(Collectors.toList());
      VesselTankRequest.Builder tankRequest = VesselTankRequest.newBuilder();
      tankRequest.addAllTankIds(tankIdList);
      VesselTankResponse replyBuilder =
          this.vesselInfoGrpcService.getVesselInfoBytankIds(tankRequest.build());
      if (replyBuilder.getVesselTankOrderList() != null) {
        log.info("No Tank Sort name found for Discharge ID  :{}", dischargeStudyId);
        cowDetail.setTanks(
            replyBuilder.getVesselTankOrderList().stream()
                .map(item -> item.getShortName())
                .collect(Collectors.joining(",")));
      }
      return cowDetail;
    }
    log.info("No COW Details found for port ID  :{}", portId);
    return null;
  }

  private List<DischargeStudyPortInstructionDetailsJson> getPortInstructions(
      PortRotationDetail port, List<LoadableStudyInstruction> instructionsDetails) {
    log.info("Getting Instruction details for port {}", port.getPortId());
    List<DischargeStudyPortInstructionDetailsJson> instructionList =
        new ArrayList<DischargeStudyPortInstructionDetailsJson>();
    port.getInstructionIdList()
        .forEach(
            instructionId -> {
              instructionsDetails.forEach(
                  instructionMasterItem -> {
                    if (instructionId == instructionMasterItem.getId()) {
                      DischargeStudyPortInstructionDetailsJson portInstruction =
                          new DischargeStudyPortInstructionDetailsJson();
                      portInstruction.setId(instructionId);
                      portInstruction.setPortInstruction(instructionMasterItem.getInstruction());
                      instructionList.add(portInstruction);
                    }
                  });
            });
    log.info("Found {} items", instructionList.size());
    return instructionList;
  }

  // Getting all instructions from master table PortInstruction
  private List<LoadableStudyInstruction> getAllLoadableStudyInstruction() {

    List<PortInstruction> instructionsDetails = portInstructionRepository.findByIsActive(true);
    return instructionsDetails.stream()
        .map(item -> new LoadableStudyInstruction(item.getId(), item.getPortInstruction()))
        .collect(Collectors.toList());
  }
}
