/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.service;

import static com.cpdss.envoywriter.common.Utility.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest;
import com.cpdss.common.generated.EnvoyWriter.WriterReply;
import com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.envoywriter.domain.CommunicationResponse;
import com.cpdss.envoywriter.entity.SequenceNumber;
import com.cpdss.envoywriter.repository.SequenceNumberRepository;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Transactional
@Service
public class EnvoyWriterService {

  @Value("${cpdss.communication.salt}")
  private String salt;

  @Autowired private SequenceNumberRepository sequenceNumberRepository;

  private static final Long SEQUENCE_NUMBER_ID = 1L;

  @Autowired private RestTemplate restTemplate;

  public WriterReply passDataToCommunicationServer(EnvoyWriterRequest request, Builder builder) {

    Optional<SequenceNumber> numberOpt = sequenceNumberRepository.findById(SEQUENCE_NUMBER_ID);

    Long sequenceNumber = numberOpt.get().getSequenceNumber();
    updateSequenceNumber(sequenceNumber, numberOpt.get());

    String encryptedString = encrypt(request.getJsonPayload(), request.getImoNumber(), salt);

    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(encryptedString.length());
      bos.write(encryptedString.getBytes());

      ZipOutputStream zos = new ZipOutputStream(bos);

      ZipInputStream zipStream =
          new ZipInputStream(new ByteArrayInputStream(encryptedString.getBytes()));
      ZipEntry entry = null;
      while ((entry = zipStream.getNextEntry()) != null) {

        String entryName = entry.getName();

        FileOutputStream out = new FileOutputStream(entryName);

        byte[] byteBuff = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = zipStream.read(byteBuff)) != -1) {
          out.write(byteBuff, 0, bytesRead);
        }

        out.close();
        zipStream.closeEntry();
      }
      zipStream.close();

      // String checkSum = getCheckSum(zos);
      LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
      map.add("files", entry);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.ALL);
      zos.closeEntry();
      zos.close();

      UriComponentsBuilder uri =
          UriComponentsBuilder.fromHttpUrl(
              "http://192.168.3.151:4900/push/clientId/messageType/messageId/seq");

      uri.queryParam("clientId", request.getVesselName());
      uri.queryParam("messageType", request.getRequestType());
      // uri.queryParam("messageId", name);//is it UUID?
      uri.queryParam("seq", sequenceNumber);

      HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
          new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
      ResponseEntity<CommunicationResponse> response =
          restTemplate.exchange(
              uri.build().encode().toUri(),
              HttpMethod.POST,
              requestEntity,
              CommunicationResponse.class);

      // System.out.println("-- " + checkSum);
      if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
        CommunicationResponse communicationResponse = response.getBody();
        builder.setMessageId(communicationResponse.getMessageId());
        builder.setMessage(communicationResponse.getMessage());
        builder.setShipId(communicationResponse.getShipId());
      } else {
        log.error("Error in Communication + ");
        throw new GenericServiceException(
            "Error in Communication",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.valueOf(response.getStatusCode().value()));
      }
      byte[] zipBytes = bos.toByteArray();
      ByteArrayInputStream bis = new ByteArrayInputStream(zipBytes);
      ZipInputStream zis = new ZipInputStream(bis);

      ByteArrayOutputStream buffer = (ByteArrayOutputStream) bos;
      byte[] bytes = buffer.toByteArray();
      InputStream inputStream = new ByteArrayInputStream(bytes);

      String text =
          new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
              .lines()
              .collect(Collectors.joining("\n"));

      System.out.println("--- " + text);
      System.out.println("encryptedString " + encryptedString);

    } catch (IOException | GenericServiceException e) {
      log.error("Error while hashing: " + e);
    }

    return builder.build();
  }

  /**
   * void
   *
   * @param sequenceNumber
   * @param number
   */
  private void updateSequenceNumber(Long sequenceNumber, SequenceNumber number) {
    number.setSequenceNumber(sequenceNumber + 1L);
    sequenceNumberRepository.save(number);
  }
}
