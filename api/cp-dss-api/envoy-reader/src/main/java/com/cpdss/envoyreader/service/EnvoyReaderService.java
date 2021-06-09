/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.service;

import static com.cpdss.envoyreader.common.Utility.*;
import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.EnvoyReader;
import com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply.Builder;
import com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.envoyreader.domain.ReaderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

/** @Author jerin.g */
@Service
@Log4j2
public class EnvoyReaderService {

  @Autowired private RestTemplate restTemplate;

  @Value("${cpdss.communucation.shore.reader.url}")
  private String downloadShoreUrl;

  @Value("${cpdss.communucation.ship.reader.url}")
  private String downloadShipUrl;

  public static final String FILE_PREFIX = "temp";
  public static final String FILE_SUFFIX = ".zip";
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @SuppressWarnings({"resource", "rawtypes"})
  public EnvoyReader.EnvoyReaderResultReply getDataFromCommunicationServer(
      EnvoyReaderResultRequest request, Builder readerBuilder) throws GenericServiceException {
    try {
      ReaderResponse response =
          restTemplate.execute(
              downloadUrlBuilder(request),
              HttpMethod.GET,
              null,
              clientHttpResponse -> {
                ReaderResponse responseUrl = new ReaderResponse();
                File ret = File.createTempFile(FILE_PREFIX, FILE_SUFFIX);
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                responseUrl.setFile(ret);
                responseUrl.setResponseHeader(clientHttpResponse.getHeaders());
                responseUrl.setHttpStatus(clientHttpResponse.getStatusCode());
                return responseUrl;
              });

      // read message info from header
      ObjectMapper mapper = new ObjectMapper();
      ReaderResponse responseDto =
          mapper.readValue(
              response.getResponseHeader().getFirst("message-info"), ReaderResponse.class);

      // checksum compare
      if (!isCheckSum(response.getFile(), responseDto.getChecksum())) {
        throw new GenericServiceException(
            "checksum does not match",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }

      String decryptedData = null;
      ZipFile zipFile = new ZipFile(response.getFile());
      for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
        ZipEntry entry = (ZipEntry) e.nextElement();
        if (!entry.isDirectory()) {

          StringBuilder out = getTxtFiles(zipFile.getInputStream(entry));
          decryptedData = decrypt(out.toString(), request.getShipId());
          System.out.println("-- " + decryptedData);
        }
      }

      buildReaderResponse(decryptedData, responseDto, readerBuilder);

    } catch (JsonProcessingException e) {
      log.error("JsonProcessingException in sending file: " + e);
      readerBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("JsonProcessingException in Envoy Reader micro service")
              .setStatus(FAILED)
              .build());
    } catch (IOException e) {
      log.error("JsonProcessingException in sending file: " + e);
      readerBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("IOException in Envoy Reader micro service")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("JsonProcessingException in sending file: " + e);
      readerBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("IOException in Envoy Reader micro service")
              .setStatus(FAILED)
              .build());
    }
    return readerBuilder.build();
  }

  /**
   * @param decryptedData
   * @param responseDto
   * @param readerBuilder void
   */
  private void buildReaderResponse(
      String decryptedData, ReaderResponse responseDto, Builder readerBuilder) {
    readerBuilder.setMessageId(responseDto.getUniqueId());
    readerBuilder.setClientId(responseDto.getClientId());
    readerBuilder.setMessageType(responseDto.getMessageType());
    readerBuilder.setPatternResultJson(decryptedData);
    readerBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * @param request
   * @return String
   */
  private String downloadUrlBuilder(EnvoyReaderResultRequest request) {
    String separator = "/";
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder
        .append(
            request.getMessageType().equals(MessageTypes.LOADABLESTUDY)
                ? downloadShoreUrl
                : downloadShipUrl)
        .append(separator)
        .append("download")
        .append(separator)
        .append(request.getClientId())
        .append(separator)
        .append(request.getMessageType())
        .append(separator)
        .append(request.getShipId());
    return valueOf(urlBuilder);
  }

  /**
   * @param in
   * @return StringBuilder
   */
  private static StringBuilder getTxtFiles(InputStream in) {
    StringBuilder out = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        out.append(line);
      }
    } catch (IOException e) {
      log.error("Error while downloading " + e);
    }
    return out;
  }
}
