/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.service;

import static com.cpdss.envoyreader.common.Utility.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyReader.ReaderReply.Builder;
import com.cpdss.common.generated.EnvoyReader.ResultJson;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
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

  @Value("${cpdss.communication.salt}")
  private String salt;

  @Value("${cpdss.communucation.reader.url}")
  private String readerUrl;

  public static final String FILE_PREFIX = "temp";
  public static final String FILE_SUFFIX = ".zip";

  @SuppressWarnings({"resource", "rawtypes"})
  public void getResult(ResultJson request, Builder builder) throws GenericServiceException {
    try {

      ReaderResponse response =
          restTemplate.execute(
              readerUrl + "/download/kazusa/ls/9513402",
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

      ObjectMapper mapper = new ObjectMapper();

      ReaderResponse checkSumDto =
          mapper.readValue(
              response.getResponseHeader().getFirst("message-info"), ReaderResponse.class);

      if (!isCheckSum(response.getFile(), checkSumDto.getChecksum())) {
        throw new GenericServiceException(
            "checksum does not match",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }

      ZipFile zipFile = new ZipFile(response.getFile());
      for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
        ZipEntry entry = (ZipEntry) e.nextElement();
        if (!entry.isDirectory()) {

          StringBuilder out = getTxtFiles(zipFile.getInputStream(entry));

          System.out.println("--- " + decrypt(out.toString(), "123", salt));
        }
      }
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
