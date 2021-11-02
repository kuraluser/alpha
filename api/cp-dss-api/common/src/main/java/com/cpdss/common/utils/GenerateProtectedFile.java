/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility for generate password protect file
 *
 * @author sajith.m
 */
@Slf4j
public class GenerateProtectedFile {

  public static final String DATE_FORMAT = "dd-MM-yyyy";

  private GenerateProtectedFile() {
    super();
  }

  /**
   * To generate password protected excel file
   *
   * @param unProtectedFile
   * @param password
   * @return File
   * @throws IOException
   * @throws GenericServiceException
   */
  public static File generatePasswordProtectedFile(File unProtectedFile, String password)
      throws IOException, GenericServiceException {
    // code for password protection

    String originalFileName = unProtectedFile.getName() == null ? "" : unProtectedFile.getName();
    if (originalFileName != null
        && ((originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())
                .equals("xlsx")
            || (originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())
                .equals("xls"))) {
      FileInputStream fileInput = new FileInputStream(unProtectedFile);
      try (BufferedInputStream bufferInput = new BufferedInputStream(fileInput)) {

        try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
          EncryptionInfo info =
              new EncryptionInfo(
                  EncryptionMode.agile, CipherAlgorithm.aes192, HashAlgorithm.sha384, -1, -1, null);

          Encryptor enc = info.getEncryptor();
          enc.confirmPassword(password);

          try (Workbook workbook = new XSSFWorkbook(bufferInput)) {
            try (FileOutputStream fileOut = new FileOutputStream(unProtectedFile)) {
              OutputStream outputStream = enc.getDataStream(fileSystem);
              workbook.write(outputStream);
              outputStream.close();
              fileSystem.writeFilesystem(fileOut);
            }
            return unProtectedFile;
          }
        }
      } catch (Exception e) {
        log.info("Exception in generate password protected file " + e.getLocalizedMessage());
        return unProtectedFile;
      }
    } else {
      throw new GenericServiceException(
          "Exception in generate password protected file",
          CommonErrorCodes.E_CPDSS_INVALID_EXCEL_FILE,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * set password protection on excel workbook
   *
   * @param workbook
   * @param voyageNum
   * @param fileCreatedDate
   * @return Workbook
   */
  public static void setPasswordToWorkbook(
      XSSFWorkbook workbook, String voyageNum, String fileCreatedDate, FileOutputStream outFile) {

    try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
      Date date1 = new SimpleDateFormat(DATE_FORMAT).parse(fileCreatedDate);
      Instant instant = date1.toInstant();
      ZoneId defaultZoneId = ZoneId.systemDefault();
      LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      String password = voyageNum + localDate.format(formatter).replaceAll("\\D", "");
      log.info("password for voyage " + voyageNum + " " + password);
      EncryptionInfo info =
          new EncryptionInfo(
              EncryptionMode.agile, CipherAlgorithm.aes192, HashAlgorithm.sha384, -1, -1, null);

      Encryptor enc = info.getEncryptor();
      enc.confirmPassword(password);

      OutputStream outputStream = enc.getDataStream(fileSystem);
      workbook.write(outputStream);
      outputStream.close();
      fileSystem.writeFilesystem(outFile);

    } catch (Exception e) {
      log.info("Exception in generate password protected file " + e.getLocalizedMessage());
    }
  }

  /**
   * generate password protected excel file with custom password
   *
   * @param unProtectedFile
   * @param voyageNum
   * @param fileCreatedDate
   * @return File
   * @throws IOException
   * @throws GenericServiceException
   */
  public static File generatePasswordProtectedFile(
      File unProtectedFile, String voyageNum, LocalDate fileCreatedDate)
      throws IOException, GenericServiceException {
    // combine voyageNum + fileCreatedDate with out time
    String password = voyageNum + fileCreatedDate.toString();
    return generatePasswordProtectedFile(unProtectedFile, password);
  }
}
