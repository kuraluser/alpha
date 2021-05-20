/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.service;

import static com.cpdss.envoywriter.common.Utility.*;

import com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson;
import com.cpdss.common.generated.EnvoyWriter.WriterReply;
import com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Transactional
@Service
public class EnvoyWriterService {

  @Value("${cpdss.communication.salt}")
  private String salt;

  @Autowired private SequenceNumberRepository sequenceNumberRepository;

  private static final Long SEQUENCE_NUMBER_ID = 1L;

  @Autowired private RestTemplate restTemplate;

  public WriterReply passDataToCommunicationServer(LoadableStudyJson request, Builder builder) {

    Optional<SequenceNumber> numberOpt = sequenceNumberRepository.findById(SEQUENCE_NUMBER_ID);

    Long sequenceNumber = numberOpt.get().getSequenceNumber();
    updateSequenceNumber(sequenceNumber, numberOpt.get());

    String encryptedString = encrypt(request.getLoadableStudy(), request.getImoNumber(), salt);

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
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      zos.closeEntry();
      zos.close();

      HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
          new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
      ResponseEntity<String> result =
          restTemplate.exchange(
              "http://localhost:8085/api/cloud/vessels/1/voyages/1/loadable-studies/1",
              HttpMethod.POST,
              requestEntity,
              String.class);

      // System.out.println("-- " + checkSum);

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

    } catch (IOException e) {
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
