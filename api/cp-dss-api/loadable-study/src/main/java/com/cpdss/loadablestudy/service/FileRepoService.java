/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.domain.FileRepoReply;
import com.cpdss.loadablestudy.domain.FileRepoAddRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/** Service class for file repo operations */
@Service
@Log4j2
public class FileRepoService {

  @Autowired private RestTemplate restTemplate;

  private static final String SERVICE_URL = "http://gateway-service:8080";
  private static final String ADD_FILE_REPO_CONTEXT = "/file-repo";

  /**
   * Method to add file to file repo
   *
   * @param fileRepoAddRequest request object
   * @return response from file repo API
   */
  public FileRepoReply addFileToFileRepo(FileRepoAddRequest fileRepoAddRequest) {

    // Set the headers
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    // Set request
    MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("file", fileRepoAddRequest.getFile());
    requestBody.add("voyageNo", fileRepoAddRequest.getVoyageNo());
    requestBody.add("fileName", fileRepoAddRequest.getFileName());
    requestBody.add("fileType", fileRepoAddRequest.getFileType());
    requestBody.add("section", fileRepoAddRequest.getSection().getSection());
    requestBody.add("category", fileRepoAddRequest.getCategory());
    requestBody.add("vesselId", fileRepoAddRequest.getVesselId());

    HttpEntity<MultiValueMap<String, Object>> requestEntity =
        new HttpEntity<>(requestBody, headers);

    // Call API
    return restTemplate.postForObject(
        SERVICE_URL + ADD_FILE_REPO_CONTEXT, requestEntity, FileRepoReply.class);
  }
}
