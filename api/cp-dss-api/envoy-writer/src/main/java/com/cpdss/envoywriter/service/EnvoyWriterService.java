/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.service;

import static com.cpdss.envoywriter.common.Utility.*;

import com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson;
import com.cpdss.common.generated.EnvoyWriter.WriterReply;
import com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder;
import com.cpdss.envoywriter.entity.SequenceNumber;
import com.cpdss.envoywriter.repository.SequenceNumberRepository;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
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

  @Value("${cpdss.communucation.writer.url}")
  private String writerUrl;

  @Autowired private SequenceNumberRepository sequenceNumberRepository;

  private static final Long SEQUENCE_NUMBER_ID = 1L;

  @Autowired private RestTemplate restTemplate;
  public static final String FILE_PREFIX = "temp";
  public static final String FILE_SUFFIX = ".zip";
  public static final String FILE_NAME = "temp.txt";

  public WriterReply passDataToCommunicationServer(LoadableStudyJson request, Builder builder)
      throws IOException {

    Optional<SequenceNumber> numberOpt = sequenceNumberRepository.findById(SEQUENCE_NUMBER_ID);

    Long sequenceNumber = numberOpt.get().getSequenceNumber();
    updateSequenceNumber(sequenceNumber, numberOpt.get());

    String encryptedString = encrypt(request.getLoadableStudy(), request.getImoNumber(), salt);
    FileOutputStream out = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(encryptedString.length());
      bos.write(encryptedString.getBytes());

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try (ZipOutputStream zos = new ZipOutputStream(baos)) {

        /* File is not on the disk, temp.txt indicates
        only the file name to be put into the zip */
        ZipEntry entry = new ZipEntry(FILE_NAME);

        zos.putNextEntry(entry);
        zos.write(encryptedString.getBytes());
        zos.closeEntry();

        /* use more Entries to add more files
        and use closeEntry() to close each file entry */

      } catch (IOException ioe) {
        ioe.printStackTrace();
      }

      File tempFile = java.io.File.createTempFile(FILE_PREFIX, FILE_SUFFIX);
      tempFile.deleteOnExit();
      out = new FileOutputStream(tempFile);

      out.write(baos.toByteArray());

      FileSystemResource fileSystemResource = new FileSystemResource(tempFile);
      String checkSum = getCheckSum(tempFile);
      LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
      map.add("file", fileSystemResource);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      headers.set("checksum", checkSum);
      headers.set("algo", "md5");
      headers.set("split", "y");

      HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
          new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
      ResponseEntity<String> result =
          restTemplate.exchange(
              writerUrl + "/push/kazusa/ls/96/96", HttpMethod.POST, requestEntity, String.class);

      System.out.println("-- " + result);

    } catch (IOException e) {
      log.error("Error while hashing: " + e);
    } finally {
      out.close();
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
