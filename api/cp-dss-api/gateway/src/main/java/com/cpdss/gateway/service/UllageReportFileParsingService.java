/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.AppContext;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.ListOfUllageReportResponse;
import com.cpdss.gateway.domain.TankDetailsRequest;
import com.cpdss.gateway.domain.UllageReportImportResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UllageReportFileParsingService {

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  private static final String SLOP_TANK_P = "SLP";
  private static final String SLOP_TANK_S = "SLS";

  public static final String ULLAGE_REPORT_SHEET = "Ullage Report";

  /**
   * Function to check the file type
   *
   * @param file
   * @param tankDetails
   * @param infoId
   * @param cargoNominationId
   * @param correlationId
   * @param isLoading
   * @return ListOfUllageReportResponse
   * @throws GenericServiceException
   */
  public ListOfUllageReportResponse importUllageReportFile(
      MultipartFile file,
      String tankDetails,
      Long infoId,
      Long cargoNominationId,
      String correlationId,
      boolean isLoading,
      Long vesselId)
      throws GenericServiceException {

    String originalFileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
    if (originalFileName != null
        && !(originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())
            .equals("xlsx")
        && !(originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())
            .equals("xls")) {
      throw new GenericServiceException(
          "unsupported file type",
          CommonErrorCodes.E_CPDSS_INVALID_EXCEL_FILE,
          HttpStatusCode.BAD_REQUEST);
    }
    ListOfUllageReportResponse response =
        parseUllageReportFile(tankDetails, infoId, cargoNominationId, file, isLoading, vesselId);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * To parse the excel file cell by cell
   *
   * @param tankDetails
   * @param infoId
   * @param cargoNominationId
   * @param file
   * @param isLoading
   * @return ListOfUllageReportResponse
   * @throws GenericServiceException
   */
  private ListOfUllageReportResponse parseUllageReportFile(
      String tankDetails,
      Long infoId,
      Long cargoNominationId,
      MultipartFile file,
      boolean isLoading,
      Long vesselId)
      throws GenericServiceException {

    try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
      Map<String, Long> tankDetailMap = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      List<TankDetailsRequest> list =
          Arrays.asList(mapper.readValue(tankDetails, TankDetailsRequest[].class));
      list.forEach(item -> tankDetailMap.put(item.getShortName(), item.getTankId()));
      saveUllageReportFileToSystemLocation(infoId, isLoading, workbook, vesselId);
      Sheet sheetAt = workbook.getSheet(ULLAGE_REPORT_SHEET);
      fileSheetIsCorrect(sheetAt);
      Iterator<Row> rowIterator = sheetAt.iterator();
      for (int rowCount = 0; rowCount < 7; rowCount++) {
        if (rowCount == 6) {
          checkIsTheFileContentFormat(rowIterator);
        }
        rowIterator.next();
      }
      boolean breakFlag = false;
      ListOfUllageReportResponse response = new ListOfUllageReportResponse();
      List<UllageReportImportResponse> innerResponseList = new ArrayList<>();
      while (rowIterator.hasNext()) {
        UllageReportImportResponse innerResponse = new UllageReportImportResponse();
        Row row = rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        for (int rowCell = 0; rowCell <= 12; rowCell++) {
          Cell cell = cellIterator.next();
          if (fetchCellValuesFromExcel(cell, rowCell, tankDetailMap, innerResponse)) {
            breakFlag = true;
            break;
          }
          innerResponse.setCargoNominationId(cargoNominationId);
        }
        if (breakFlag) {
          break;
        }
        innerResponseList.add(innerResponse);
      }
      response.setUllageReportResponse(innerResponseList);
      return response;
    } catch (IllegalStateException e) {
      throw new GenericServiceException(e.getMessage(), e.getMessage(), HttpStatusCode.BAD_REQUEST);
    } catch (Exception e) {
      throw new GenericServiceException(
          e.getMessage(),
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Fetch cell values from each cell
   *
   * @param cell
   * @param rowCell
   * @param tankDetailMap
   * @param innerResponse
   * @return
   */
  private boolean fetchCellValuesFromExcel(
      Cell cell,
      int rowCell,
      Map<String, Long> tankDetailMap,
      UllageReportImportResponse innerResponse) {

    if (rowCell == 0) {
      if (cell.getCellType().equals(CellType.BLANK)) {
        return true;
      } else {
        this.getTankNameFromCell(cell, innerResponse, tankDetailMap);
      }
    }
    if (rowCell == 3) {
      this.getUllageObservedFromCell(cell, innerResponse);
    }
    if (rowCell == 5) {
      this.getApiFromCell(cell, innerResponse);
    }
    if (rowCell == 6) {
      this.getTemperatureFromCell(cell, innerResponse);
    }
    if (rowCell == 12) {
      this.getWeightFromCell(cell, innerResponse);
    }
    return false;
  }

  /**
   * fetch tank name cell value
   *
   * @param cell
   * @param innerResponse
   * @param tankDetailMap
   */
  private void getTankNameFromCell(
      Cell cell, UllageReportImportResponse innerResponse, Map<String, Long> tankDetailMap) {
    if (!cell.getCellType().equals(CellType.STRING)) {
      throw new IllegalStateException(CommonErrorCodes.E_CPDSS_ULLAGE_INVALID_TANK);
    }
    String substring =
        cell.getStringCellValue().substring(cell.getStringCellValue().indexOf(' ') + 1);
    if (substring.trim().equals("P")) {
      innerResponse.setTank(SLOP_TANK_P);
      substring = SLOP_TANK_P;
    }
    if (substring.trim().equals("S")) {
      innerResponse.setTank(SLOP_TANK_S);
      substring = SLOP_TANK_S;
    }
    innerResponse.setTank(substring.trim());
    if (tankDetailMap.get(substring) == null) {
      innerResponse.setTankId(0L);
    } else {
      innerResponse.setTankId(tankDetailMap.get(substring));
    }
  }

  /**
   * fetch ullage observed cell value
   *
   * @param cell
   * @param innerResponse
   */
  private void getUllageObservedFromCell(Cell cell, UllageReportImportResponse innerResponse) {
    if (cell.getCellType().equals(CellType.BLANK)) {
      innerResponse.setUllageObserved(0.0);
    } else {
      if (!cell.getCellType().equals(CellType.NUMERIC)) {
        throw new IllegalStateException(CommonErrorCodes.E_CPDSS_ULLAGE_INVALID_ULLAGE_OBSERVED);
      }
      innerResponse.setUllageObserved(cell.getNumericCellValue());
    }
  }

  /**
   * fetch api cell value
   *
   * @param cell
   * @param innerResponse
   */
  private void getApiFromCell(Cell cell, UllageReportImportResponse innerResponse) {
    if (cell.getCellType().equals(CellType.BLANK)) {
      innerResponse.setApi(0.0);
    } else {
      if (!cell.getCellType().equals(CellType.NUMERIC)) {
        throw new IllegalStateException(CommonErrorCodes.E_CPDSS_ULLAGE_INVALID_API);
      }
      innerResponse.setApi(cell.getNumericCellValue());
    }
  }

  /**
   * fetch temperature cell value
   *
   * @param cell
   * @param innerResponse
   */
  private void getTemperatureFromCell(Cell cell, UllageReportImportResponse innerResponse) {
    if (cell.getCellType().equals(CellType.BLANK)) {
      innerResponse.setTemperature(0.0);
    } else {
      try {
        innerResponse.setTemperature(
            Double.valueOf(
                cell.getStringCellValue()
                    .trim()
                    .substring(0, cell.getStringCellValue().trim().indexOf(' '))));
      } catch (Exception e) {
        throw new IllegalStateException(CommonErrorCodes.E_CPDSS_ULLAGE_INVALID_TEMPERATURE);
      }
    }
  }

  /**
   * fetch weight cell value
   *
   * @param cell
   * @param innerResponse
   */
  private void getWeightFromCell(Cell cell, UllageReportImportResponse innerResponse) {
    if (cell.getCellType().equals(CellType.BLANK)) {
      innerResponse.setWeight(0.0);
    } else {
      if (!cell.getCellType().equals(CellType.NUMERIC)) {
        throw new IllegalStateException(CommonErrorCodes.E_CPDSS_ULLAGE_INVALID_WEIGHT);
      }
      innerResponse.setWeight(cell.getNumericCellValue());
    }
  }

  /**
   * Validate is file contents format is expected
   *
   * @param rowIterator
   */
  private void checkIsTheFileContentFormat(Iterator<Row> rowIterator) {
    Row row = rowIterator.next();
    Iterator<Cell> cellIterator = row.cellIterator();
    Cell cell = cellIterator.next();
    if (cell.getCellType().equals(CellType.BLANK)) {
      throw new IllegalStateException(CommonErrorCodes.E_CPDSS_EMPTY_EXCEL_FILE);
    } else {
      try {
        if (!cell.getStringCellValue().equals("Tank")) {
          throw new IllegalStateException(CommonErrorCodes.E_CPDSS_INVALID_CONTENT);
        }
      } catch (Exception e) {
        throw new IllegalStateException(CommonErrorCodes.E_CPDSS_INVALID_CONTENT);
      }
    }
  }

  /**
   * Save ullage report file to local system.
   *
   * @param infoId
   * @param isLoading
   * @param workbook
   * @param vesselId
   * @throws IOException
   */
  private void saveUllageReportFileToSystemLocation(
      Long infoId, boolean isLoading, Workbook workbook, Long vesselId) throws IOException {
    String userId =
        AppContext.getCurrentUserId() == null ? "Unknown" : AppContext.getCurrentUserId();
    String fileNamePrefix = isLoading ? "Loading_" : "Discharging_";
    String fileName =
        fileNamePrefix
            + "Ullage_Report_"
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            + "_"
            + infoId
            + "_"
            + userId
            + ".xls";
    String directoryString = rootFolder + vesselId + "\\";
    File directory = new File(directoryString);

    if (!directory.exists() && !directory.isDirectory()) {
      directory.mkdir();
    }
    FileOutputStream out = new FileOutputStream(directoryString + fileName);
    workbook.write(out);
    out.close();
  }

  /**
   * Check if the file sheet is correct
   *
   * @param sheet
   */
  private void fileSheetIsCorrect(Sheet sheet) {

    if (sheet == null) {
      throw new IllegalStateException(CommonErrorCodes.E_CPDSS_INVALID_CONTENT);
    }
  }
}
