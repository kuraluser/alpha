/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadingPlanStagingService extends StagingService {

  @Autowired
  private PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @Autowired private PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired private LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;

  @Autowired
  private PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;

  @Autowired
  private PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;

  @Autowired
  private PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;

  @Autowired
  private PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;

  @Autowired private LoadingSequenceRepository loadingSequenceRepository;
  @Autowired private LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;

  @Autowired
  private LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;

  @Autowired
  private LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @Autowired private LoadingInformationRepository loadingInformationRepository;
  @Autowired private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired private StageOffsetRepository stageOffsetRepository;
  @Autowired private StageDurationRepository stageDurationRepository;
  @Autowired private LoadingInformationStatusRepository loadingInformationStatusRepository;
  @Autowired private LoadingBerthDetailsRepository loadingBerthDetailsRepository;
  @Autowired private LoadingDelayRepository loadingDelayRepository;
  @Autowired private LoadingMachineryInUseRepository loadingMachineryInUseRepository;

  @Autowired
  private PortLoadingPlanCommingleTempDetailsRepository
      portLoadingPlanCommingleTempDetailsRepository;

  @Autowired
  private PortLoadingPlanCommingleDetailsRepository portLoadingPlanCommingleDetailsRepository;

  @Autowired private BillOfLandingRepository billOfLandingRepository;
  @Autowired private PyUserRepository pyUserRepository;

  public LoadingPlanStagingService(
      @Autowired LoadingPlanStagingRepository loadingPlanStagingRepository) {
    super(loadingPlanStagingRepository);
  }

  LoadingInformation loadingInformation = null;
  List<Long> loadingPlanPortWiseDetailsIds = null;

  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param Id- id
   * @param pyUserId- processId of algoResponse
   * @return JsonArray
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList,
      String processId,
      String processGroupId,
      Long Id,
      String pyUserId) {
    log.info("LoadingPlanStaging Service processidentifier list:" + processIdentifierList);
    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    for (String processIdentifier : processIdentifierList) {
      if (processedList.contains(processIdentifier)) {
        log.info("Table already fetched :" + processIdentifier);
        continue;
      }
      JsonObject jsonObject = new JsonObject();
      List<Object> object = new ArrayList<Object>();
      switch (ProcessIdentifiers.valueOf(processIdentifier)) {
        case loading_information:
          {
            getLoadingInformation(
                array, Id, processIdentifier, processId, processGroupId, processedList, object);
            break;
          }
        case cargo_topping_off_sequence:
          {
            List<CargoToppingOffSequence> cargoToppingOffSequences =
                cargoToppingOffSequenceRepository.findByLoadingInformationId(
                    loadingInformation.getId());
            if (cargoToppingOffSequences != null && !cargoToppingOffSequences.isEmpty()) {
              // set each loading info obj to null
              cargoToppingOffSequences.stream()
                  .forEach(
                      cargoToppingOffSequence ->
                          cargoToppingOffSequence.setLoadingInformation(null));
              object.addAll(cargoToppingOffSequences);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_berth_details:
          {
            List<LoadingBerthDetail> loadingBerthDetails =
                loadingBerthDetailsRepository.findByLoadingInformationId(
                    loadingInformation.getId());
            if (loadingBerthDetails != null && !loadingBerthDetails.isEmpty()) {
              // set each loading info obj to null
              loadingBerthDetails.stream()
                  .forEach(loadingBerthDetail -> loadingBerthDetail.setLoadingInformation(null));
              object.addAll(loadingBerthDetails);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_delay:
          {
            List<LoadingDelay> loadingDelayList =
                loadingDelayRepository.findByLoadingInformationId(loadingInformation.getId());
            if (loadingDelayList != null && !loadingDelayList.isEmpty()) {
              // set each loading info and LoadingDelayReasons to null
              loadingDelayList.stream()
                  .forEach(
                      loadingDelay -> {
                        loadingDelay.setLoadingInformation(null);
                        loadingDelay.setLoadingDelayReasons(null);
                      });
              object.addAll(loadingDelayList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_machinary_in_use:
          {
            List<LoadingMachineryInUse> loadingMachineryInUseList =
                loadingMachineryInUseRepository.findByLoadingInformationId(
                    loadingInformation.getId());
            if (loadingMachineryInUseList != null && !loadingMachineryInUseList.isEmpty()) {
              // set each loading info obj to null
              loadingMachineryInUseList.stream()
                  .forEach(
                      loadingMachineryInUse -> loadingMachineryInUse.setLoadingInformation(null));
              object.addAll(loadingMachineryInUseList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_sequence:
          {
            List<LoadingSequence> loadingSequenceList =
                loadingSequenceRepository.findByLoadingInformationId(Id);
            if (loadingSequenceList != null && !loadingSequenceList.isEmpty()) {
              loadingSequenceList.stream()
                  .forEach(
                      loadingSequence -> {
                        loadingSequence.setLoadingPlanPortWiseDetails(null);
                        loadingSequence.setCargoLoadingRates(null);
                        loadingSequence.setCargoValves(null);
                        loadingSequence.setBallastValves(null);
                        loadingSequence.setDeballastingRates(null);
                        loadingSequence.setBallastOperations(null);
                        loadingSequence.setLoadingInformation(null);
                      });
              object.addAll(loadingSequenceList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_plan_portwise_details:
          {
            getLoadingPlanPortWiseDetails(
                array, Id, processIdentifier, processId, processGroupId, processedList, object);
            break;
          }
        case port_loading_plan_stability_parameters:
          {
            List<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParamList =
                portLoadingPlanStabilityParametersRepository.findByLoadingInformationId(Id);
            if (portLoadingPlanStabilityParamList != null
                && !portLoadingPlanStabilityParamList.isEmpty()) {
              portLoadingPlanStabilityParamList.stream()
                  .forEach(
                      portLoadingPlanStabilityParam ->
                          portLoadingPlanStabilityParam.setLoadingInformation(null));
              object.addAll(portLoadingPlanStabilityParamList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }

        case port_loading_plan_rob_details:
          {
            List<PortLoadingPlanRobDetails> portLoadingPlanRobDetailsList =
                portLoadingPlanRobDetailsRepository.findByLoadingInformation(Id);
            if (portLoadingPlanRobDetailsList != null && !portLoadingPlanRobDetailsList.isEmpty()) {
              object.addAll(portLoadingPlanRobDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_plan_ballast_details:
          {
            List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList =
                loadingPlanBallastDetailsRepository.findByLoadingPlanPortWiseDetailIds(
                    loadingPlanPortWiseDetailsIds);
            if (loadingPlanBallastDetailsList != null && !loadingPlanBallastDetailsList.isEmpty()) {
              loadingPlanBallastDetailsList.stream()
                  .forEach(
                      portLoadingPlanStabilityParam -> {
                        portLoadingPlanStabilityParam.setCommunicationPortWiseId(
                            portLoadingPlanStabilityParam.getLoadingPlanPortWiseDetails().getId());
                        portLoadingPlanStabilityParam.setLoadingPlanPortWiseDetails(null);
                      });
              object.addAll(loadingPlanBallastDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_plan_rob_details:
          {
            List<LoadingPlanRobDetails> loadingPlanRobDetailsList =
                loadingPlanRobDetailsRepository.findByPortWiseDetailIds(
                    loadingPlanPortWiseDetailsIds);
            if (loadingPlanRobDetailsList != null && !loadingPlanRobDetailsList.isEmpty()) {
              loadingPlanRobDetailsList.stream()
                  .forEach(
                      loadingPlanRobDetails -> {
                        loadingPlanRobDetails.setCommunicationPortWiseId(
                            loadingPlanRobDetails.getLoadingPlanPortWiseDetails().getId());
                        loadingPlanRobDetails.setLoadingPlanPortWiseDetails(null);
                      });
              object.addAll(loadingPlanRobDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }

        case port_loading_plan_stowage_ballast_details:
          {
            List<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetailsList =
                portLoadingPlanBallastDetailsRepository.findByLoadingInformationId(Id);
            if (portLoadingPlanBallastDetailsList != null
                && !portLoadingPlanBallastDetailsList.isEmpty()) {
              portLoadingPlanBallastDetailsList.stream()
                  .forEach(
                      portLoadingPlanBallastDetails ->
                          portLoadingPlanBallastDetails.setLoadingInformation(null));
              object.addAll(portLoadingPlanBallastDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case port_loading_plan_stowage_ballast_details_temp:
          {
            List<PortLoadingPlanBallastTempDetails> portLoadingPlanBallastTempDetailsList =
                portLoadingPlanBallastTempDetailsRepository.findByLoadingInformation(Id);
            if (portLoadingPlanBallastTempDetailsList != null
                && !portLoadingPlanBallastTempDetailsList.isEmpty()) {
              object.addAll(portLoadingPlanBallastTempDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case port_loading_plan_stowage_details:
          {
            List<PortLoadingPlanStowageDetails> portLoadingPlanStowageDetailsList =
                portLoadingPlanStowageDetailsRepository.findByLoadingInformationId(Id);
            if (portLoadingPlanStowageDetailsList != null
                && !portLoadingPlanStowageDetailsList.isEmpty()) {
              portLoadingPlanStowageDetailsList.stream()
                  .forEach(
                      portLoadingPlanStowageDetails ->
                          portLoadingPlanStowageDetails.setLoadingInformation(null));
              object.addAll(portLoadingPlanStowageDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case port_loading_plan_stowage_details_temp:
          {
            List<PortLoadingPlanStowageTempDetails> portLoadingPlanStowageTempDetailsList =
                portLoadingPlanStowageTempDetailsRepository.findByLoadingInformation(Id);
            if (portLoadingPlanStowageTempDetailsList != null
                && !portLoadingPlanStowageTempDetailsList.isEmpty()) {
              object.addAll(portLoadingPlanStowageTempDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }

        case loading_plan_stowage_details:
          {
            List<LoadingPlanStowageDetails> loadingPlanStowageDetailsList =
                loadingPlanStowageDetailsRepository.findByPortWiseDetailIds(
                    loadingPlanPortWiseDetailsIds);
            if (loadingPlanStowageDetailsList != null && !loadingPlanStowageDetailsList.isEmpty()) {
              loadingPlanStowageDetailsList.stream()
                  .forEach(
                      loadingPlanStowageDetails -> {
                        loadingPlanStowageDetails.setCommunicationPortWiseId(
                            loadingPlanStowageDetails.getLoadingPlanPortWiseDetails().getId());
                        loadingPlanStowageDetails.setLoadingPlanPortWiseDetails(null);
                      });
              object.addAll(loadingPlanStowageDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_sequence_stability_parameters:
          {
            List<LoadingSequenceStabilityParameters> loadingSequenceStabilityParametersList =
                loadingSequenceStabiltyParametersRepository.findByLoadingInformationId(Id);
            if (loadingSequenceStabilityParametersList != null
                && !loadingSequenceStabilityParametersList.isEmpty()) {
              loadingSequenceStabilityParametersList.stream()
                  .forEach(
                      loadingSequenceStabilityParameters ->
                          loadingSequenceStabilityParameters.setLoadingInformation(null));
              object.addAll(loadingSequenceStabilityParametersList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case loading_plan_stability_parameters:
          {
            List<LoadingPlanStabilityParameters> loadingPlanStabilityParametersList =
                loadingPlanStabilityParametersRepository.findByLoadingPlanPortWiseDetailIds(
                    loadingPlanPortWiseDetailsIds);
            if (loadingPlanStabilityParametersList != null
                && !loadingPlanStabilityParametersList.isEmpty()) {
              loadingPlanStabilityParametersList.stream()
                  .forEach(
                      loadingPlanStabilityParameters -> {
                        loadingPlanStabilityParameters.setCommunicationPortWiseId(
                            loadingPlanStabilityParameters.getLoadingPlanPortWiseDetails().getId());
                        loadingPlanStabilityParameters.setLoadingPlanPortWiseDetails(null);
                      });
              object.addAll(loadingPlanStabilityParametersList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case port_loadable_plan_commingle_details_temp:
          {
            List<PortLoadingPlanCommingleTempDetails> portLoadingPlanCommingleTempDetailsList =
                portLoadingPlanCommingleTempDetailsRepository.findByLoadingInformation(Id);
            if (portLoadingPlanCommingleTempDetailsList != null
                && !portLoadingPlanCommingleTempDetailsList.isEmpty()) {
              object.addAll(portLoadingPlanCommingleTempDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case port_loadable_plan_commingle_details:
          {
            List<PortLoadingPlanCommingleDetails> portLoadingPlanCommingleDetailsList =
                portLoadingPlanCommingleDetailsRepository.findByLoadingInformationId(Id);
            if (portLoadingPlanCommingleDetailsList != null
                && !portLoadingPlanCommingleDetailsList.isEmpty()) {
              // set each loading info obj to null
              portLoadingPlanCommingleDetailsList.stream()
                  .forEach(
                      portLoadingPlanCommingleDetails ->
                          portLoadingPlanCommingleDetails.setLoadingInformation(null));
              object.addAll(portLoadingPlanCommingleDetailsList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case bill_of_ladding:
          {
            List<BillOfLanding> billOfLandingList = billOfLandingRepository.findByLoadingId(Id);
            if (billOfLandingList != null && !billOfLandingList.isEmpty()) {
              object.addAll(billOfLandingList);
              addIntoProcessedList(
                  array, object, processIdentifier, processId, processGroupId, processedList);
            }
            break;
          }
        case pyuser:
          {
            if (pyUserId != null) {
              Optional<PyUser> pyUser = pyUserRepository.findById(pyUserId);
              if (pyUser.isPresent()) {
                object.add(pyUser.get());
                addIntoProcessedList(
                    array, object, processIdentifier, processId, processGroupId, processedList);
              }
            }
            break;
          }
      }
    }
    return array;
  }

  private void addIntoProcessedList(
      JsonArray array,
      List<Object> object,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    array.add(createJsonObject(object, processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }

  private void getLoadingInformation(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object) {
    Optional<LoadingInformation> loadingInformationObj = loadingInformationRepository.findById(id);
    if (!loadingInformationObj.isEmpty()) {

      loadingInformation = loadingInformationObj.get();
      loadingInformation.setLoadingBerthDetails(null);
      loadingInformation.setCargoToppingOfSequences(null);
      loadingInformation.setLoadingDelays(null);
      loadingInformation.setLoadingMachineriesInUse(null);
      if (loadingInformation.getStageOffset() != null) {
        getStageOffset(
            array,
            loadingInformation.getStageOffset().getId(),
            "stages_min_amount",
            processId,
            processGroupId,
            processedList);
        loadingInformation.setStageOffset(null);
      }
      if (loadingInformation.getStageDuration() != null) {
        getStageDuration(
            array,
            loadingInformation.getStageDuration().getId(),
            "stages_duration",
            processId,
            processGroupId,
            processedList);
      }
      loadingInformation.setStageDuration(null);
      if (loadingInformation.getLoadingInformationStatus() != null) {
        getLoadingInformationStatus(
            array,
            loadingInformation.getLoadingInformationStatus().getId(),
            "loading_information_status",
            processId,
            processGroupId,
            processedList);
      }
      loadingInformation.setLoadingInformationStatus(null);
      if (loadingInformation.getArrivalStatus() != null) {
        getArrivalStatus(
            array,
            loadingInformation.getArrivalStatus().getId(),
            "loading_information_arrival_status",
            processId,
            processGroupId,
            processedList);
      }
      loadingInformation.setArrivalStatus(null);
      if (loadingInformation.getDepartureStatus() != null) {
        getDepartureStatus(
            array,
            loadingInformation.getDepartureStatus().getId(),
            "loading_information_departure_status",
            processId,
            processGroupId,
            processedList);
      }
      loadingInformation.setDepartureStatus(null);
      object.addAll(Arrays.asList(loadingInformation));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
    }
  }

  private void getStageOffset(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<StageOffset> stageOffsetObj = stageOffsetRepository.findById(id);
    if (!stageOffsetObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(stageOffsetObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
    }
  }

  private void getStageDuration(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<StageDuration> stageDurationObj = stageDurationRepository.findById(id);
    if (!stageDurationObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(stageDurationObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
    }
  }

  private void getLoadingInformationStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<LoadingInformationStatus> loadingInformationStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!loadingInformationStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(loadingInformationStatusObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
    }
  }

  private void getArrivalStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<LoadingInformationStatus> arrivalStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!arrivalStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(arrivalStatusObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
    }
  }

  private void getDepartureStatus(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList) {
    Optional<LoadingInformationStatus> departureStatusObj =
        loadingInformationStatusRepository.findById(id);
    if (!departureStatusObj.isEmpty()) {
      List<Object> object = new ArrayList<>();
      object.addAll(Arrays.asList(departureStatusObj.get()));
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
    }
  }

  private void getLoadingPlanPortWiseDetails(
      JsonArray array,
      Long id,
      String processIdentifier,
      String processId,
      String processGroupId,
      List<String> processedList,
      List<Object> object) {
    List<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsList =
        loadingPlanPortWiseDetailsRepository.findByLoadingInformationId(id);
    if (loadingPlanPortWiseDetailsList != null && !loadingPlanPortWiseDetailsList.isEmpty()) {
      loadingPlanPortWiseDetailsList.stream()
          .forEach(
              loadingPlanPortWiseDetails -> {
                loadingPlanPortWiseDetails.setLoadingPlanStowageDetails(null);
                loadingPlanPortWiseDetails.setLoadingPlanBallastDetails(null);
                loadingPlanPortWiseDetails.setLoadingPlanRobDetails(null);
                loadingPlanPortWiseDetails.setLoadingPlanStabilityParameters(null);
                loadingPlanPortWiseDetails.setDeballastingRates(null);
                loadingPlanPortWiseDetails.setCommunicationSequenceId(
                    loadingPlanPortWiseDetails.getLoadingSequence().getId());
                loadingPlanPortWiseDetails.setLoadingSequence(null);
              });

      object.addAll(loadingPlanPortWiseDetailsList);
      addIntoProcessedList(
          array, object, processIdentifier, processId, processGroupId, processedList);
      loadingPlanPortWiseDetailsIds =
          loadingPlanPortWiseDetailsList.stream()
              .map(LoadingPlanPortWiseDetails::getId)
              .collect(Collectors.toList());
    }
  }
  /**
   * method for create JsonObject
   *
   * @param list - List
   * @param processIdentifier - processId
   * @param processId - processGroupId
   * @param processGroupId- processGroupId
   * @return JsonObject
   */
  public JsonObject createJsonObject(
      List<Object> list, String processIdentifier, String processId, String processGroupId) {

    JsonObject jsonObject = new JsonObject();
    JsonObject metaData = new JsonObject();
    metaData.addProperty("processIdentifier", processIdentifier);
    metaData.addProperty("processId", processId);
    metaData.addProperty("processGroupId", processGroupId);
    jsonObject.add("meta_data", metaData);
    JsonArray array = new JsonArray();
    for (Object obj : list) {
      array.add(new Gson().toJson(obj));
    }
    jsonObject.add("data", array);
    return jsonObject;
  }
}
