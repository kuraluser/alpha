package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyReader;
import com.cpdss.common.generated.EnvoyReaderServiceGrpc;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

@Log4j2
@Service
public class CommunicationService {
    @Autowired
    private LoadableStudyServiceShore loadableStudyServiceShore;
    @Autowired
    VoyageService voyageService;
    @Autowired
    LoadableQuantityService loadableQuantityService;
    @Autowired
    LoadableStudyService loadableStudyService;
    @Autowired
    JsonDataService jsonDataService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadableStudyRepository loadableStudyRepository;
    @Value("${loadablestudy.attachement.rootFolder}")
    private String rootFolder;

    @Value("${algo.loadablestudy.api.url}")
    private String loadableStudyUrl;

    @GrpcClient("envoyReaderService")
    private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

    public void saveLoadableStudyShore(Map<String, String> taskReqParams) {

        try {
            EnvoyReader.EnvoyReaderResultReply erReply = getResultFromEnvoyReaderShore(taskReqParams);
            if (!LoadableStudiesConstants.SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
                throw new GenericServiceException(
                        "Failed to get Result from Communication Server",
                        erReply.getResponseStatus().getCode(),
                        HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
            }
            String jsonResult = erReply.getPatternResultJson();
            LoadableStudy loadableStudyEntity =
                    loadableStudyServiceShore.setLoadablestudyShore(jsonResult, erReply.getMessageId());
            if (loadableStudyEntity != null) {
                voyageService.checkIfVoyageClosed(loadableStudyEntity.getVoyage().getId());
                this.loadableQuantityService.validateLoadableStudyWithLQ(loadableStudyEntity);
                ModelMapper modelMapper = new ModelMapper();
                com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
                        new com.cpdss.loadablestudy.domain.LoadableStudy();

                loadableStudyService.buildLoadableStudy(
                        loadableStudyEntity.getId(), loadableStudyEntity, loadableStudy, modelMapper);
                ObjectMapper objectMapper = new ObjectMapper();

                objectMapper.writeValue(
                        new File(
                                this.rootFolder
                                        + "/json/loadableStudyFromShip_"
                                        + loadableStudyEntity.getId()
                                        + ".json"),
                        loadableStudy);

                this.jsonDataService.saveJsonToDatabase(
                        loadableStudyEntity.getId(),
                        LoadableStudiesConstants.LOADABLE_STUDY_REQUEST,
                        objectMapper.writeValueAsString(loadableStudy));

                AlgoResponse algoResponse =
                        restTemplate.postForObject(loadableStudyUrl, loadableStudy, AlgoResponse.class);
                loadableStudyService.updateProcessIdForLoadableStudy(
                        algoResponse.getProcessId(), loadableStudyEntity, LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID);

                loadableStudyRepository.updateLoadableStudyStatus(
                        LoadableStudiesConstants.LOADABLE_STUDY_PROCESSING_STARTED_ID, loadableStudyEntity.getId());
            }

        } catch (GenericServiceException e) {
            log.error("GenericServiceException when generating pattern", e);

        } catch (ResourceAccessException e) {
            log.info("Error calling ALGO ");

        } catch (Exception e) {
            log.error("Exception when when calling algo  ", e);
        }
    }

    private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReaderShore(
            Map<String, String> taskReqParams) {
        EnvoyReader.EnvoyReaderResultRequest.Builder request =
                EnvoyReader.EnvoyReaderResultRequest.newBuilder();
        request.setMessageType(taskReqParams.get("messageType"));
        request.setClientId(taskReqParams.get("ClientId"));
        request.setShipId(taskReqParams.get("ShipId"));
        return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
    }

    public void saveAlgoPatternFromShore(Map<String, String> taskReqParams) {
        try {
            EnvoyReader.EnvoyReaderResultReply erReply = getResultFromEnvoyReaderShore(taskReqParams);
            if (!LoadableStudiesConstants.SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
                throw new GenericServiceException(
                        "Failed to get Result from Communication Server",
                        erReply.getResponseStatus().getCode(),
                        HttpStatusCode.valueOf(Integer.valueOf(erReply.getResponseStatus().getCode())));
            }
            String jsonResult = erReply.getPatternResultJson();
            com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder load = com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.newBuilder();
            // load.setLoadableStudyId(request.getLoadableStudyId());
            if (!jsonResult.isEmpty())
                loadableStudyService.saveLoadablePatternDetails(erReply.getPatternResultJson(), load);
        } catch (GenericServiceException e) {
            log.error("GenericServiceException when saving pattern", e);
        }
    }
}
