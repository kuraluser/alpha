/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.service;

import static com.cpdss.envoywriter.common.Utility.encrypt;
import static com.cpdss.envoywriter.common.Utility.getCheckSum;
import static java.lang.String.valueOf;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest;
import com.cpdss.common.generated.EnvoyWriter.WriterReply;
import com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.envoywriter.domain.StatusCheckResponse;
import com.cpdss.envoywriter.domain.WriterResponse;
import com.cpdss.envoywriter.entity.SequenceNumber;
import com.cpdss.envoywriter.repository.SequenceNumberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Transactional
@Service
public class EnvoyWriterService {

  @Value("${cpdss.communication.ship.writer.url}")
  private String writerShipUrl;

  @Value("${cpdss.communication.shore.writer.url}")
  private String writerShoreUrl;

  @Value("${cpdss.build.env}")
  private String env;

  @Autowired private SequenceNumberRepository sequenceNumberRepository;
  @Autowired private RestTemplate restTemplate;

  private static final Long SEQUENCE_NUMBER_ID = 1L;

  public static final String FILE_PREFIX = "temp";
  public static final String FILE_SUFFIX = ".zip";
  public static final String FILE_NAME = "temp.txt";
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  public WriterReply passDataToCommunicationServer(
      EnvoyWriterRequest request, Builder writerBuilder) throws IOException {

    Optional<SequenceNumber> numberOpt = sequenceNumberRepository.findById(SEQUENCE_NUMBER_ID);
    SequenceNumber obj = new SequenceNumber();
    Long sequenceNumber = 0L;
    if (numberOpt.isPresent()) {
      sequenceNumber = numberOpt.get().getSequenceNumber();
      obj = numberOpt.get();
    }
    updateSequenceNumber(sequenceNumber, obj);
    String uuid = null;
    if (request.getMessageId().isEmpty()) uuid = UUID.randomUUID().toString();
    else uuid = request.getMessageId();
    String encryptedString = encrypt(request.getJsonPayload(), request.getImoNumber());
    FileOutputStream out = null;
    try {
      // creating temp zip file
      File tempFile = java.io.File.createTempFile(FILE_PREFIX, FILE_SUFFIX);
      tempFile.deleteOnExit();
      out = new FileOutputStream(tempFile);
      out.write(
          createByteArrayWithZipStreamFromEncryptedData(encryptedString, writerBuilder)
              .toByteArray());

      // uplod file as FileSystemResource
      FileSystemResource fileSystemResource = new FileSystemResource(tempFile);
      LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
      map.add("file", fileSystemResource);

      HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
          new HttpEntity<LinkedMultiValueMap<String, Object>>(
              map, createHeaderParameters(getCheckSum(tempFile)));

      ResponseEntity<String> result =
          restTemplate.exchange(
              uploadUrlBuilder(request, uuid, sequenceNumber),
              HttpMethod.POST,
              requestEntity,
              String.class);
      log.debug("Envoy writer file upload response: {}", result);

      ObjectMapper mapper = new ObjectMapper();
      WriterResponse writerResponse = mapper.readValue(result.getBody(), WriterResponse.class);

      buildWriterResponse(writerBuilder, writerResponse /*, encryptedString*/);

    } catch (IOException e) {
      log.error("IOException in sending file: " + e);
      writerBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("IOException in Envoy Writer micro service")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Error in sending file: " + e);
      writerBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in Envoy Writer micro service")
              .setStatus(FAILED)
              .build());
    } finally {
      out.close();
    }
    return writerBuilder.build();
  }

  /**
   * @param writerBuilder
   * @param writerResponse
   */
  private void buildWriterResponse(
      Builder writerBuilder, WriterResponse writerResponse /*, String uuid*/) {
    writerBuilder.setMessageId(writerResponse.getMessageId());
    writerBuilder.setMessage(writerResponse.getMessage());
    writerBuilder.setShipId(writerResponse.getShipId());
    writerBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * @param encryptedString
   * @return ByteArrayOutputStream
   * @throws IOException
   */
  private ByteArrayOutputStream createByteArrayWithZipStreamFromEncryptedData(
      String encryptedString, Builder writerBuilder) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ZipOutputStream zos = new ZipOutputStream(baos)) {

      /* File is not on the disk, temp.txt indicates
      only the file name to be put into the zip */
      ZipEntry entry = new ZipEntry(FILE_NAME);
      zos.putNextEntry(entry);
      zos.write(encryptedString.getBytes());
      zos.closeEntry();
    }
    return baos;
  }

  /**
   * @param checkSum
   * @return MultiValueMap<String,String>
   */
  private HttpHeaders createHeaderParameters(String checkSum) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.set("checksum", checkSum);
    headers.set("algo", "md5");
    headers.set("split", "y");
    return headers;
  }

  /**
   * void
   *
   * @param sequenceNumber
   * @param number
   */
  @Transactional
  private void updateSequenceNumber(Long sequenceNumber, SequenceNumber number) {
    number.setSequenceNumber(sequenceNumber + 1L);
    sequenceNumberRepository.save(number);
  }

  /**
   * @param request
   * @param uuid
   * @param sequenceNumber
   * @return String
   */
  private String uploadUrlBuilder(EnvoyWriterRequest request, String uuid, Long sequenceNumber) {
    String separator = "/";
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder
        .append(env.equals("ship") ? writerShipUrl : writerShoreUrl)
        .append(separator)
        .append("push")
        .append(separator)
        .append(request.getClientId())
        .append(separator)
        .append(env.equals("ship") ? "" : request.getImoNumber())
        .append(env.equals("ship") ? "" : separator)
        .append(request.getMessageType())
        .append(separator)
        .append(uuid)
        .append(separator)
        .append(sequenceNumber);
    return valueOf(urlBuilder);
  }

  public void checkStatus(EnvoyWriterRequest request, Builder statusCheckbuilder)
      throws IOException {

    ResponseEntity<String> result =
        restTemplate.getForEntity(statucCheckUrlBuilder(request), String.class);
    log.debug("Status check response: {}", result);

    ObjectMapper mapper = new ObjectMapper();

    // TODO remove StatusCheckResponse DTO and direct map to proto
    StatusCheckResponse statusCheckResponse =
        mapper.readValue(result.getBody(), StatusCheckResponse.class);
    buildStatusCheckResponse(statusCheckbuilder, statusCheckResponse);
  }

  /**
   * @param statusCheckBuilder
   * @param statusCheckResponse void
   */
  private void buildStatusCheckResponse(
      Builder statusCheckBuilder, StatusCheckResponse statusCheckResponse) {
    statusCheckBuilder.setStatusCode(statusCheckResponse.getStatusCode());
    statusCheckBuilder.setMessage(statusCheckResponse.getMessage());
    statusCheckBuilder.setMessageId(statusCheckResponse.getMessageId());
    Optional.ofNullable(statusCheckResponse.getEventUploadStatus())
        .ifPresent(statusCheckBuilder::setEventUploadStatus);
    Optional.ofNullable(statusCheckResponse.getEventDownloadStatus())
        .ifPresent(statusCheckBuilder::setEventDownloadStatus);
    statusCheckBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * @param request
   * @return URI
   */
  private String statucCheckUrlBuilder(EnvoyWriterRequest request) {
    String separator = "/";
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder
        .append(env.equals("ship") ? writerShipUrl : writerShoreUrl)
        .append(separator)
        .append("status")
        .append(separator)
        .append(request.getClientId())
        .append(separator)
        .append(request.getMessageId())
        .append(separator)
        .append(request.getImoNumber());
    return valueOf(urlBuilder);
  }
}
