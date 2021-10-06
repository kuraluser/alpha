/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.loadingplan.utility.ProcessIdentifiers;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadingPlanStagingService extends StagingService {

  @Autowired private PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;
  @Autowired private PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @Autowired private LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @Autowired private LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @Autowired private PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;
  @Autowired private PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastTempDetailsRepository;
  @Autowired private PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @Autowired private PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageTempDetailsRepository;
  @Autowired private LoadingSequenceRepository loadingSequenceRepository;
  @Autowired private LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @Autowired private LoadingPlanStabilityParametersRepository loadingPlanStabilityParametersRepository;
  @Autowired private LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;
  @Autowired private LoadingInformationRepository loadingInformationRepository;
  @Autowired private CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired private StageOffsetRepository stageOffsetRepository;
  @Autowired private StageDurationRepository stageDurationRepository;
  @Autowired private LoadingInformationStatusRepository loadingInformationStatusRepository;

  public LoadingPlanStagingService(@Autowired LoadingPlanStagingRepository loadingPlanStagingRepository) {
    super(loadingPlanStagingRepository);
  }

  /**
   * getCommunicationData method for get JsonArray from processIdentifierList
   *
   * @param processIdentifierList - list of processIdentifier
   * @param processId - processId
   * @param processGroupId - processGroupId
   * @param Id- id
   * @return JsonArray
   */
  public JsonArray getCommunicationData(
      List<String> processIdentifierList, String processId, String processGroupId, Long Id) throws GenericServiceException {
    JsonArray array = new JsonArray();
    List<String> processedList = new ArrayList<>();
    for (String processIdentifier : processIdentifierList) {
      if(processedList.contains(processIdentifier)){
        log.info("Table already fetched :"+ processIdentifier);
        break;
      }
      JsonObject jsonObject = new JsonObject();
      switch (ProcessIdentifiers.valueOf(processIdentifier)) {
        case loading_information:
        {
          getLoadingInformation(array, Id,processIdentifier, processId, processGroupId, processedList);
          break;
        }
        case cargo_topping_off_sequence:
        {
          Optional<CargoToppingOffSequence> cargoToppingOffSequence= cargoToppingOffSequenceRepository.findById(Id);
          if(cargoToppingOffSequence.isEmpty()){
            throw new GenericServiceException(
                    "CargoToppingOffSequence with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          CargoToppingOffSequence getObj=cargoToppingOffSequence.get();
          getObj.setLoadingInformation(null);
         // getLoadingInformation(array, getObj.getLoadingInformation().getId(),"loading_information", processId, processGroupId, processedList);
          array.add(
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case port_loading_plan_stability_parameters:
        {
          Optional<PortLoadingPlanStabilityParameters> portLoadingPlanStabilityParameters = portLoadingPlanStabilityParametersRepository.findById(Long.valueOf(33));
         if(portLoadingPlanStabilityParameters.isEmpty()){
           throw new GenericServiceException(
                   "PortLoadingPlanStabilityParameters with given id does not exist",
                   CommonErrorCodes.E_HTTP_BAD_REQUEST,
                   HttpStatusCode.BAD_REQUEST);
        }
        PortLoadingPlanStabilityParameters getObj= portLoadingPlanStabilityParameters.get();
          getObj.setLoadingInformation(null);
        //getLoadingInformation(array, getObj.getLoadingInformation().getId(),"loading_information", processId, processGroupId, processedList);
          jsonObject =
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId);
          array.add(jsonObject);
          processedList.add(processIdentifier);
          break;}

        case port_loading_plan_rob_details:
        {
        Optional <PortLoadingPlanRobDetails> portLoadingPlanRobDetails= portLoadingPlanRobDetailsRepository.findById(Id);
          if(portLoadingPlanRobDetails.isEmpty()){
            throw new GenericServiceException(
                    "PortLoadingPlanRobDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          jsonObject =
                  createJsonObject(
                          new Gson().toJson(portLoadingPlanRobDetails.get()), processIdentifier, processId, processGroupId);
          array.add(jsonObject);
          processedList.add(processIdentifier);
          break;
        }
        case loading_plan_ballast_details:
        {
          Optional<LoadingPlanBallastDetails> loadingPlanBallastDetails= loadingPlanBallastDetailsRepository.findById(Id);
          if(loadingPlanBallastDetails.isEmpty()){
            throw new GenericServiceException(
                    "LoadingPlanBallastDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          LoadingPlanBallastDetails getObj=loadingPlanBallastDetails.get();
          getObj.setLoadingPlanPortWiseDetails(null);
          //getLoadingPlanPortWiseDetails(array, getObj.getLoadingPlanPortWiseDetails().getId(),"loading_plan_portwise_details", processId, processGroupId,processedList);
          jsonObject =
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId);
          array.add(jsonObject);
          processedList.add(processIdentifier);
          break;
        }
        case loading_plan_rob_details:
        {
          Optional<LoadingPlanRobDetails> loadingPlanRobDetails= loadingPlanRobDetailsRepository.findById(Id);
          if(loadingPlanRobDetails.isEmpty()){
            throw new GenericServiceException(
                    "LoadingPlanRobDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          LoadingPlanRobDetails getObj=loadingPlanRobDetails.get();
          getObj.setLoadingPlanPortWiseDetails(null);
          //getLoadingPlanPortWiseDetails(array, getObj.getLoadingPlanPortWiseDetails().getId(),"loading_plan_portwise_details", processId, processGroupId, processedList);
          jsonObject =
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId);
          array.add(jsonObject);
          processedList.add(processIdentifier);
          break;
        }
        case loading_plan_portwise_details:
        {
          getLoadingPlanPortWiseDetails(array, Id,processIdentifier, processId, processGroupId, processedList);
          break;
        }
        case port_loading_plan_stowage_ballast_details:
        {
          Optional<PortLoadingPlanBallastDetails> portLoadingPlanBallastDetails= portLoadingPlanBallastDetailsRepository.findById(Id);
          if(portLoadingPlanBallastDetails.isEmpty()){
            throw new GenericServiceException(
                    "PortLoadingPlanBallastDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          PortLoadingPlanBallastDetails getObj=portLoadingPlanBallastDetails.get();
          getObj.setLoadingInformation(null);
          //getLoadingInformation(array, getObj.getLoadingInformation().getId(),"loading_information", processId, processGroupId, processedList);
          array.add(createJsonObject(
                  new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case port_loading_plan_stowage_ballast_details_temp:
        {
          Optional<PortLoadingPlanBallastTempDetails> portLoadingPlanBallastTempDetails= portLoadingPlanBallastTempDetailsRepository.findById(Id);
          if(portLoadingPlanBallastTempDetails.isEmpty()){
            throw new GenericServiceException(
                    "PortLoadingPlanBallastTempDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          array.add(createJsonObject(
                  new Gson().toJson(portLoadingPlanBallastTempDetails.get()), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case port_loading_plan_stowage_details:
        {
          Optional<PortLoadingPlanStowageDetails> portLoadingPlanStowageDetails= portLoadingPlanStowageDetailsRepository.findById(Id);
          if(portLoadingPlanStowageDetails.isEmpty()){
            throw new GenericServiceException(
                    "PortLoadingPlanStowageDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          PortLoadingPlanStowageDetails getObj=portLoadingPlanStowageDetails.get();
          getObj.setLoadingInformation(null);
          //getLoadingInformation(array, getObj.getLoadingInformation().getId(),"loading_information", processId, processGroupId, processedList);
          array.add(
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case port_loading_plan_stowage_details_temp:
        {
          Optional<PortLoadingPlanStowageTempDetails> portLoadingPlanStowageTempDetails= portLoadingPlanStowageTempDetailsRepository.findById(Id);
          if(portLoadingPlanStowageTempDetails.isEmpty()){
            throw new GenericServiceException(
                    "PortLoadingPlanStowageTempDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          array.add(
                  createJsonObject(
                          new Gson().toJson(portLoadingPlanStowageTempDetails.get()), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case loading_sequence:
        {
          getLoadingSequence(array,Id,processIdentifier,processId,processGroupId,processedList);
          break;
        }
        case loading_plan_stowage_details:
        {
          Optional<LoadingPlanStowageDetails> loadingPlanStowageDetails= loadingPlanStowageDetailsRepository.findById(Id);
          if(loadingPlanStowageDetails.isEmpty()){
            throw new GenericServiceException(
                    "LoadingPlanStowageDetails with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          LoadingPlanStowageDetails getObj=loadingPlanStowageDetails.get();
          getObj.setLoadingPlanPortWiseDetails(null);
          //getLoadingPlanPortWiseDetails(array, getObj.getLoadingPlanPortWiseDetails().getId(),"loading_plan_portwise_details", processId, processGroupId, processedList);
          array.add(
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case loading_sequence_stability_parameters:
        {
          Optional<LoadingSequenceStabilityParameters> loadingSequenceStabilityParameters= loadingSequenceStabiltyParametersRepository.findById(Id);
          if(loadingSequenceStabilityParameters.isEmpty()){
            throw new GenericServiceException(
                    "LoadingSequenceStabilityParameters with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          LoadingSequenceStabilityParameters getObj=loadingSequenceStabilityParameters.get();
          getObj.setLoadingInformation(null);
          //getLoadingInformation(array, getObj.getLoadingInformation().getId(),"loading_information", processId, processGroupId, processedList);
          array.add(
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
        case loading_plan_stability_parameters:
        {
          Optional<LoadingPlanStabilityParameters> loadingPlanStabilityParameters= loadingPlanStabilityParametersRepository.findById(Id);
          if(loadingPlanStabilityParameters.isEmpty()){
            throw new GenericServiceException(
                    "LoadingPlanStabilityParameters with given id does not exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
          }
          LoadingPlanStabilityParameters getObj=loadingPlanStabilityParameters.get();
          getObj.setLoadingPlanPortWiseDetails(null);
          //getLoadingPlanPortWiseDetails(array, getObj.getLoadingPlanPortWiseDetails().getId(),"loading_plan_portwise_details", processId, processGroupId, processedList);
          array.add(
                  createJsonObject(
                          new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
          processedList.add(processIdentifier);
          break;
        }
      }
    }
    return array;
  }

   private void getLoadingInformation(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<LoadingInformation> loadingInformationObj= loadingInformationRepository.findById(id);
    if(loadingInformationObj.isEmpty()){
      throw new GenericServiceException(
              "LoadingInformation with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    LoadingInformation loadingInformation=loadingInformationObj.get();
     loadingInformation.setLoadingBerthDetails(null);
     loadingInformation.setCargoToppingOfSequences(null);
     loadingInformation.setLoadingDelays(null);
     loadingInformation.setLoadingMachineriesInUse(null);
     getStageOffset(array, loadingInformation.getStageOffset().getId(),"stages_min_amount", processId, processGroupId,processedList);
     getStageDuration(array, loadingInformation.getStageDuration().getId(),"stages_duration", processId, processGroupId,processedList);
     if(loadingInformation.getLoadingInformationStatus()!=null) {
       getLoadingInformationStatus(array, loadingInformation.getLoadingInformationStatus().getId(), "loading_information_status", processId, processGroupId, processedList);
     }
     if(loadingInformation.getArrivalStatus()!=null) {
       getArrivalStatus(array, loadingInformation.getArrivalStatus().getId(), "loading_information_status", processId, processGroupId,processedList);
     }
     if(loadingInformation.getDepartureStatus()!=null) {
       getDepartureStatus(array, loadingInformation.getDepartureStatus().getId(), "loading_information_status", processId, processGroupId,processedList);
     }
    array.add(createJsonObject(
             new Gson().toJson(loadingInformation), processIdentifier, processId, processGroupId));
     processedList.add(processIdentifier);
  }
  private void getStageOffset(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<StageOffset> stageOffsetObj= stageOffsetRepository.findById(id);
    if(stageOffsetObj.isEmpty()){
      throw new GenericServiceException(
              "StageOffset with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
   array.add(createJsonObject(
            new Gson().toJson(stageOffsetObj.get()), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }
  private void getStageDuration(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<StageDuration> stageDurationObj= stageDurationRepository.findById(id);
    if(stageDurationObj.isEmpty()){
      throw new GenericServiceException(
              "StageDuration with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    array.add(createJsonObject(
            new Gson().toJson(stageDurationObj.get()), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }

  private void getLoadingInformationStatus(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<LoadingInformationStatus> loadingInformationStatusObj= loadingInformationStatusRepository.findById(id);
    if(loadingInformationStatusObj.isEmpty()){
      throw new GenericServiceException(
              "LoadingInformationStatus with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    array.add(createJsonObject(
            new Gson().toJson(loadingInformationStatusObj.get()), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }
  private void getArrivalStatus(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<LoadingInformationStatus> arrivalStatusObj= loadingInformationStatusRepository.findById(id);
    if(arrivalStatusObj.isEmpty()){
      throw new GenericServiceException(
              "LoadingInformationStatus with arrival status with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    array.add(createJsonObject(
            new Gson().toJson(arrivalStatusObj.get()), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }
  private void getDepartureStatus(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<LoadingInformationStatus> departureStatusObj= loadingInformationStatusRepository.findById(id);
    if(departureStatusObj.isEmpty()){
      throw new GenericServiceException(
              "LoadingInformationStatus with arrival status with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    array.add(createJsonObject(
            new Gson().toJson(departureStatusObj.get()), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }

  private void getLoadingPlanPortWiseDetails(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetailsObj= loadingPlanPortWiseDetailsRepository.findById(id);
    if(loadingPlanPortWiseDetailsObj.isEmpty()){
      throw new GenericServiceException(
              "LoadingPlanPortWiseDetails with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    LoadingPlanPortWiseDetails loadingPlanPortWiseDetails=loadingPlanPortWiseDetailsObj.get();
    loadingPlanPortWiseDetails.setLoadingPlanStowageDetails(null);
    loadingPlanPortWiseDetails.setLoadingPlanBallastDetails(null);
    loadingPlanPortWiseDetails.setLoadingPlanRobDetails(null);
    loadingPlanPortWiseDetails.setLoadingPlanStabilityParameters(null);
    loadingPlanPortWiseDetails.setDeballastingRates(null);
    loadingPlanPortWiseDetails.setLoadingSequence(null);
   // getLoadingSequence(array,loadingPlanPortWiseDetails.getLoadingSequence().getId(),"loading_sequence",processId,processGroupId, processedList);
    array.add(createJsonObject(
            new Gson().toJson(loadingPlanPortWiseDetails), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }

  private void getLoadingSequence(JsonArray array, Long id, String processIdentifier, String processId, String processGroupId, List<String> processedList) throws GenericServiceException {
    Optional<LoadingSequence> loadingSequence= loadingSequenceRepository.findById(id);
    if(loadingSequence.isEmpty()){
      throw new GenericServiceException(
              "LoadingSequence with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
    }
    LoadingSequence getObj=loadingSequence.get();
    getObj.setLoadingPlanPortWiseDetails(null);
    getObj.setCargoLoadingRates(null);
    getObj.setCargoValves(null);
    getObj.setBallastValves(null);
    getObj.setDeballastingRates(null);
    getObj.setBallastOperations(null);
    getObj.setLoadingInformation(null);
   // getLoadingInformation(array,getObj.getLoadingInformation().getId(),"loading_information",processId, processGroupId, processedList);
    array.add(
            createJsonObject(
                    new Gson().toJson(getObj), processIdentifier, processId, processGroupId));
    processedList.add(processIdentifier);
  }

  /**
   * method for create JsonObject
   *
   * @param jsonData - string jsonData
   * @param processIdentifier - processId
   * @param processId - processGroupId
   * @param processGroupId- processGroupId
   * @return JsonObject
   */
  public JsonObject createJsonObject(
      String jsonData, String processIdentifier, String processId, String processGroupId) {
    JsonObject jsonObject = new JsonObject();
    JsonObject metaData = new JsonObject();
    metaData.addProperty("processIdentifier", processIdentifier);
    metaData.addProperty("processId", processId);
    metaData.addProperty("processGroupId", processGroupId);
    jsonObject.add("meta_data", metaData);
    JsonArray array = new JsonArray();
    array.add(jsonData);
    jsonObject.add("data", array);
    return jsonObject;
  }

}






