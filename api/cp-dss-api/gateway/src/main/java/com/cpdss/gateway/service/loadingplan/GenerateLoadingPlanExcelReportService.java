/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.LoadLine;
import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.filerepo.FileRepoReply;
import com.cpdss.gateway.domain.loadingplan.ArrivalDeparcherCondition;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.BerthInformation;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.CargoQuantity;
import com.cpdss.gateway.domain.loadingplan.CargoTobeLoaded;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionForExcel;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionGroup;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionSubHeader;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructions;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingInstructionDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingPlanDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanStabilityParam;
import com.cpdss.gateway.domain.loadingplan.TankCargoDetails;
import com.cpdss.gateway.domain.loadingplan.TankRow;
import com.cpdss.gateway.domain.loadingplan.VesselParticularsForExcel;
import com.cpdss.gateway.domain.loadingplan.sequence.Ballast;
import com.cpdss.gateway.domain.loadingplan.sequence.Cargo;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoLoadingRate;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanExcelLoadingSequenceDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingRateForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.QuantityLoadingStatus;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParamsOfLoadingSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategory;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategoryForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankWithSequenceDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.FileRepoService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingPlanServiceImpl;
import com.cpdss.gateway.utility.ExcelExportUtility;
import com.cpdss.gateway.utility.UnitConversionUtility;
import com.cpdss.gateway.utility.UnitTypes;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** @author sanalkumar.k */
@Slf4j
@Service
public class GenerateLoadingPlanExcelReportService {
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final String YES = "Yes";
  public static final String NO = "No";

  public String SUB_FOLDER_NAME = "/reports";
  public String TEMPLATES_FILE_LOCATION = "/reports/Vessel_{type}_Loading_Plan_Template.xlsx";
  // public String TEMPLATES_FILE_LOCATION =
  // "/reports/Vessel_1_Loading_Plan_Template.xlsx";
  public String OUTPUT_FILE_LOCATION = "/reports/Vessel_{id}_Loading_Plan_{voy}_{port}.xlsx";
  public String TEMP_LOCATION = "temp.xlsx";
  public final Integer START_ROW = 14;
  public final Integer END_ROW = 71;
  public final Integer END_COLUMN = 25;
  public VesselParticularsForExcel vesselParticular = null;
  public String SHEET_NAMES[] = {"CRUD - 021 pg1", "CRUD - 021 pg2", "CRUD - 021 pg3"};
  public Long INSTRUCTION_ORDER[] = {1L, 17L, 13L, 15L, 2L, 14L, 11L, 4L};
  public List<TankCargoDetails> cargoTanks = null;
  public List<TankCargoDetails> ballastTanks = null;
  public CargoMachineryInUse cargoMachinery = null;
  public String cargoNames = null;
  public final String UFPT = "UFPT";
  public final String LFPT = "LFPT";
  public final Long GS_PUMP_ID = 3L;
  public final Long IGS_PUMP_ID = 4L;
  public final Long BALLAST_PUMP_ID = 2L;
  public String voyageDate = null;

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;
  @Autowired ExcelExportUtility excelExportUtil;
  @Autowired private VesselInfoService vesselInfoService;
  @Autowired private LoadingInstructionService loadingInstructionService;
  @Autowired private LoadingPlanServiceImpl loadingPlanServiceImpl;
  @Autowired private FileRepoService FileRepoService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  /**
   * Method to read data from request and Stamp in existing template
   *
   * @param requestPayload
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @param portRotationId
   * @param downloadRequired
   * @return
   * @throws GenericServiceException
   * @throws IOException
   */
  public byte[] generateLoadingPlanExcel(
      LoadingPlanResponse requestPayload,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId,
      Boolean downloadRequired)
      throws Exception {
    log.info("Generating Loading plan excel for Vessel {}", vesselId);

    // Setting file name of input file based on vessel type
    TEMPLATES_FILE_LOCATION = getLoadingPlanTemplateForVessel(vesselId);

    // Building data required for Loading plan Excel
    LoadingPlanExcelDetails loadinPlanExcelDetails =
        getDataForExcel(requestPayload, vesselId, voyageId, infoId, portRotationId);

    String actualFileName =
        getFileName(
            vesselId,
            loadinPlanExcelDetails.getSheetOne().getVoyageNumber(),
            loadinPlanExcelDetails.getSheetOne().getPortName());

    File theDir = new File(rootFolder + SUB_FOLDER_NAME);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    // Setting file name of output file
    StringBuilder outputLocation = new StringBuilder(rootFolder + actualFileName);

    // Getting data mapped and calling excel builder utility
    FileInputStream resultFileStream =
        new FileInputStream(
            excelExportUtil.generateExcel(
                loadinPlanExcelDetails, TEMPLATES_FILE_LOCATION, TEMP_LOCATION));

    if (resultFileStream != null) {
      FileOutputStream outFile = new FileOutputStream(outputLocation.toString());
      log.info("Excel generated, setting color based on cargo in all sheets");
      XSSFWorkbook workbook;
      workbook = new XSSFWorkbook(resultFileStream);
      try {

        setCellStyle(workbook, loadinPlanExcelDetails);

        // Adding password protection code commented for temporary
        // GenerateProtectedFile.setPasswordToWorkbook(
        //    workbook, loadinPlanExcelDetails.getSheetOne().getVoyageNumber(), voyageDate,
        // outFile);
        workbook.write(outFile);
        resultFileStream.close();

        // Putting entry in file repo
        FileRepoReply reply =
            FileRepoService.addFileToRepo(
                null,
                loadinPlanExcelDetails.getSheetOne().getVoyageNumber(),
                actualFileName.split("/")[actualFileName.split("/").length - 1],
                SUB_FOLDER_NAME + "/",
                "Loading",
                "Process",
                null,
                true);
        if (reply.getResponseStatus().getStatus().equals(String.valueOf(HttpStatus.OK.value()))) {
          log.info("Succesfully added entry in FileRepo : {}", reply.getId());
        } else {
          log.info("Data entry in file repo failed");
        }
        // Returning Output file as byte array for local download
        resultFileStream = new FileInputStream(outputLocation.toString());
        if (downloadRequired && resultFileStream != null) {
          log.info("Excel created.");
          return IOUtils.toByteArray(resultFileStream);
        }
      } catch (GenericServiceException e) {
        e.printStackTrace();
        log.info("Excel export failed.");
        throw new GenericServiceException(
            "Generating excel failed. " + e.getMessage(),
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      } catch (Exception e) {
        e.printStackTrace();
        log.info("Applying style in excel failed");
        throw new GenericServiceException(
            "Generating excel failed ,Styling cells encountereed an exception",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      } finally {
        outFile.close();
        workbook.close();
        resultFileStream.close();
      }
    }
    // No need to for local download if file generated from event trigger
    log.info("No local download required so returning null");
    return new byte[0];
  }

  /** Add style in excel */
  private void setCellStyle(XSSFWorkbook workbook, LoadingPlanExcelDetails data) {
    XSSFSheet sheet1 = workbook.getSheet(SHEET_NAMES[0]);
    XSSFSheet sheet3 = workbook.getSheet(SHEET_NAMES[2]);
    styleSheetOne(workbook, sheet1, data.getSheetOne());
    styleSheetThree(workbook, sheet3, data.getSheetThree());
  }

  /** Set color in sheet three tanks and sequence cells */
  private void styleSheetThree(
      XSSFWorkbook workbook, XSSFSheet sheet, LoadingPlanExcelLoadingSequenceDetails data) {
    XSSFCell cell = null;
    int row = 0;
    int col = 0;
    // Cargo name column color
    for (row = 6; row <= 39; row++) {
      col = 1;
      cell = sheet.getRow(row).getCell(col);
      setCargoColor(workbook, sheet, cell, null, null, data.getCargoTanks());
    }
    // Sequence diagram color cargo
    for (row = 6; row <= 70; row++) {
      for (col = 4; col <= 4 + (data.getTickPoints().size() * 2); col++) {
        cell = sheet.getRow(row).getCell(col);
        setCargoColor(workbook, sheet, cell, null, null, null);
      }
    }
  }

  /** Set color in sheet one tanks and cargo cells */
  private void styleSheetOne(
      XSSFWorkbook workbook, XSSFSheet sheet, LoadingPlanExcelLoadingPlanDetails data) {
    XSSFCell cell = null;
    int row = 0;
    int col = 0;
    // Tank layout color
    for (row = START_ROW; row <= END_ROW; row++) {
      for (col = 1; col <= END_COLUMN; col++) {
        cell = sheet.getRow(row).getCell(col);
        if (row >= 14 && row < 29) {
          if (setTankColor(
              workbook,
              sheet,
              cell,
              data.getArrivalCondition().getCargoCenterTanks().getTank(),
              null)) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              data.getArrivalCondition().getCargoTopTanks().getTank(),
              null)) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              data.getArrivalCondition().getCargoBottomTanks().getTank(),
              null)) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              null,
              data.getArrivalCondition().getBallastTopTanks().getTank())) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              null,
              data.getArrivalCondition().getBallastBottomTanks().getTank())) {
            continue;
          }
          // APT FPT
          if (setAPTFPTTankColor(
              workbook, sheet, cell, data.getArrivalCondition().getApt(), null, null, null)) {
            continue;
          }
          if (data.getArrivalCondition().getFpt() != null) {
            if (setAPTFPTTankColor(
                workbook, sheet, cell, null, data.getArrivalCondition().getFpt(), null, null)) {
              continue;
            }
          } else {
            if (setAPTFPTTankColor(
                workbook, sheet, cell, null, null, data.getArrivalCondition().getLfpt(), null)) {
              continue;
            }
            if (setAPTFPTTankColor(
                workbook, sheet, cell, null, null, null, data.getArrivalCondition().getUfpt())) {
              continue;
            }
          }

        } else if (row >= 32 && row < 46) {
          if (setTankColor(
              workbook,
              sheet,
              cell,
              data.getDeparcherCondition().getCargoCenterTanks().getTank(),
              null)) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              data.getDeparcherCondition().getCargoTopTanks().getTank(),
              null)) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              data.getDeparcherCondition().getCargoBottomTanks().getTank(),
              null)) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              null,
              data.getDeparcherCondition().getBallastTopTanks().getTank())) {
            continue;
          }
          if (setTankColor(
              workbook,
              sheet,
              cell,
              null,
              data.getDeparcherCondition().getBallastBottomTanks().getTank())) {
            continue;
          }
          // APT FPT
          if (setAPTFPTTankColor(
              workbook, sheet, cell, data.getDeparcherCondition().getApt(), null, null, null)) {
            continue;
          }
          if (data.getDeparcherCondition().getFpt() != null) {
            if (setAPTFPTTankColor(
                workbook, sheet, cell, null, data.getDeparcherCondition().getFpt(), null, null)) {
              continue;
            }
          } else {
            if (setAPTFPTTankColor(
                workbook, sheet, cell, null, null, data.getDeparcherCondition().getLfpt(), null)) {
              continue;
            }
            if (setAPTFPTTankColor(
                workbook, sheet, cell, null, null, null, data.getDeparcherCondition().getUfpt())) {
              continue;
            }
          }
        }
      }
    }
    // List of cargo oil area color
    for (row = 15; row <= 39; row++) {
      col = 19;
      cell = sheet.getRow(row).getCell(col);
      if (row <= 22) {
        setCargoColor(
            workbook, sheet, cell, data.getArrivalCondition().getCargoDetails(), null, null);
      } else if (row <= 39) {
        setCargoColor(
            workbook, sheet, cell, data.getDeparcherCondition().getCargoDetails(), null, null);
      }
    }
    // Cargo to be loaded area color
    for (col = 5; col <= 5 + (data.getCargoTobeLoaded().size() * 5); col += 5) {
      row = 61;
      cell = sheet.getRow(row).getCell(col);
      setCargoColor(workbook, sheet, cell, null, data.getCargoTobeLoaded(), null);
    }
  }

  /** Color cargo name cells */
  private void setCargoColor(
      XSSFWorkbook workbook,
      XSSFSheet sheet1,
      XSSFCell cell,
      List<CargoQuantity> cargoDetails,
      List<CargoTobeLoaded> cargoTobeLoaded,
      List<TankCategoryForSequence> cargoListSequence) {
    String cellValue = getCellValue(cell);
    if (cellValue != null && !cellValue.isBlank()) {
      if (cargoDetails != null) {
        Optional<CargoQuantity> opt =
            cargoDetails.stream().filter(item -> cellValue.equals(item.getCargoName())).findFirst();
        if (opt.isPresent()) {
          fillColor(workbook, cell, opt.get().getColorCode());
        }
      } else if (cargoTobeLoaded != null) {
        Optional<CargoTobeLoaded> opt =
            cargoTobeLoaded.stream()
                .filter(item -> cellValue.equals(item.getCargoName()))
                .findFirst();
        if (opt.isPresent()) {
          fillColor(workbook, cell, opt.get().getColorCode());
        }
      } else if (cargoListSequence != null) {
        Optional<TankCategoryForSequence> opt =
            cargoListSequence.stream()
                .filter(item -> cellValue.equals(item.getCargoName()))
                .findFirst();
        if (opt.isPresent()) {
          fillColor(workbook, cell, opt.get().getColorCode());
        }
      } else {
        if (cellValue.contains("#")) {
          fillColor(workbook, cell, cellValue);
          cell.setCellValue("");
        }
      }
    }
  }

  /** Fills color in provided cell using color code */
  private void fillColor(XSSFWorkbook workbook, XSSFCell cell, String colorCode) {
    XSSFCellStyle newCellStyle = workbook.createCellStyle();
    XSSFCellStyle cellStyle = cell.getCellStyle();
    newCellStyle.setDataFormat(cellStyle.getDataFormat());
    newCellStyle.setFont(workbook.getFontAt(cellStyle.getFontIndex()));
    newCellStyle.setAlignment(cellStyle.getAlignment());
    newCellStyle.setVerticalAlignment(cellStyle.getVerticalAlignment());
    newCellStyle.setBorderBottom(cellStyle.getBorderBottom());
    newCellStyle.setBorderTop(cellStyle.getBorderTop());
    newCellStyle.setBorderLeft(cellStyle.getBorderLeft());
    newCellStyle.setBorderRight(cellStyle.getBorderRight());
    // setting cell color
    Color color = Color.decode(colorCode);
    newCellStyle.setFillForegroundColor(new XSSFColor(color, new DefaultIndexedColorMap()));
    newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    // Changing font color
    XSSFFont font = cellStyle.getFont();
    XSSFFont newFont = workbook.createFont();
    newFont.setBold(font.getBold());
    newFont.setFontName(font.getFontName());
    newFont.setFontHeight(font.getFontHeight());
    newFont.setItalic(font.getItalic());
    // if (Integer.parseInt(colorCode.substring(1), 16) > 0xffffff / 2) {
    // font.setColor(new XSSFColor(Color.BLACK));
    // } else {
    // font.setColor(new XSSFColor(Color.WHITE));
    // }
    Color tempColor = excelExportUtil.getContrastColor(color);
    newFont.setColor(new XSSFColor(tempColor, new DefaultIndexedColorMap()));
    newCellStyle.setFont(newFont);
    //
    cell.setCellStyle(newCellStyle);
  }

  /** Dynamically add colours in ballast end tanks */
  private boolean setAPTFPTTankColor(
      XSSFWorkbook workbook,
      XSSFSheet sheet,
      XSSFCell cell,
      TankCargoDetails apt,
      TankCargoDetails fpt,
      TankCargoDetails lfpt,
      TankCargoDetails ufpt) {
    int row = 0;
    int col = 0;
    String cellValue = getCellValue(cell);
    if (cellValue != null && !cellValue.isBlank()) {
      TankCargoDetails tankFromFile = null;
      if (apt != null) {
        tankFromFile = getTank(cellValue, Arrays.asList(apt));
      } else if (fpt != null) {
        tankFromFile = getTank(cellValue, Arrays.asList(fpt));
      } else if (ufpt != null) {
        tankFromFile = getTank(cellValue, Arrays.asList(ufpt));
      } else if (lfpt != null) {
        tankFromFile = getTank(cellValue, Arrays.asList(lfpt));
      }
      if (tankFromFile != null) {
        if (tankFromFile.getColorCode() != null
            && !tankFromFile.getColorCode().isBlank()
            && tankFromFile.getQuantity() != null
            && tankFromFile.getQuantity() > 0) {
          CellAddress address = cell.getAddress();
          row = address.getRow();
          col = address.getColumn();
          setTankCellsColors(workbook, sheet, row, col, tankFromFile);
          setTankCellsColors(workbook, sheet, row + 1, col, tankFromFile);
          return true;
        }
      }
    }
    return false;
  }

  /** Dynamically add colours in tank layout */
  private Boolean setTankColor(
      XSSFWorkbook workbook,
      XSSFSheet sheet,
      XSSFCell cell,
      List<TankCargoDetails> cargoTanks,
      List<TankCargoDetails> ballastTanks) {
    int row = 0;
    int col = 0;
    String cellValue = getCellValue(cell);
    if (cellValue != null && !cellValue.isBlank()) {
      TankCargoDetails tankFromFile = null;
      if (cargoTanks != null) {
        tankFromFile = getTank(cellValue, cargoTanks);
        if (tankFromFile != null) {
          if (tankFromFile.getColorCode() != null
              && !tankFromFile.getColorCode().isBlank()
              && tankFromFile.getQuantity() != null
              && tankFromFile.getQuantity() > 0) {
            CellAddress address = cell.getAddress();
            row = address.getRow();
            col = address.getColumn();
            setTankCellsColors(workbook, sheet, row, col, tankFromFile);
            setTankCellsColors(workbook, sheet, row, col + 1, tankFromFile);
            setTankCellsColors(workbook, sheet, row + 1, col, tankFromFile);
            setTankCellsColors(workbook, sheet, row + 2, col, tankFromFile);
            setTankCellsColors(workbook, sheet, row + 2, col + 1, tankFromFile);
            return true;
          }
        }
      } else if (ballastTanks != null) {
        tankFromFile = getTank(cellValue, ballastTanks);
        if (tankFromFile != null) {
          if (tankFromFile.getColorCode() != null
              && !tankFromFile.getColorCode().isBlank()
              && tankFromFile.getQuantity() != null
              && tankFromFile.getQuantity() > 0) {
            CellAddress address = cell.getAddress();
            row = address.getRow();
            col = address.getColumn();
            setTankCellsColors(workbook, sheet, row, col, tankFromFile);
            setTankCellsColors(workbook, sheet, row, col + 1, tankFromFile);
            setTankCellsColors(workbook, sheet, row + 1, col, tankFromFile);
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * @param workbook
   * @param sheet
   * @param cell
   * @param tankFromFile
   */
  private void setTankCellsColors(
      XSSFWorkbook workbook, XSSFSheet sheet, int row, int col, TankCargoDetails tankFromFile) {
    XSSFCell cell = sheet.getRow(row).getCell(col);
    if (cell != null) {
      fillColor(workbook, cell, tankFromFile.getColorCode());
    } else {
      cell = sheet.getRow(row).createCell(col);
      fillColor(workbook, cell, tankFromFile.getColorCode());
    }
  }

  /**
   * @param value
   * @param tankList
   * @return
   */
  private TankCargoDetails getTank(String value, List<TankCargoDetails> tankList) {
    for (TankCargoDetails tank : tankList) {
      if (tank.getTankName().equals(value) && tank.getQuantity() > 0) {
        return tank;
      }
    }
    return null;
  }

  /**
   * @param cell
   * @return
   */
  private String getCellValue(Cell cell) {
    if (cell != null) {
      if (cell.getCellType().equals(CellType.NUMERIC)) {
        return ((Double) cell.getNumericCellValue()).toString();
      } else if (cell.getCellType().equals(CellType.BOOLEAN)) {
        return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
      } else {
        return cell.getStringCellValue();
      }
    }
    return null;
  }

  /**
   * Get corresponding Loading plan template of a vessel based on its type.
   *
   * @throws GenericServiceException
   * @throws NumberFormatException
   */
  private String getLoadingPlanTemplateForVessel(Long vesselId)
      throws NumberFormatException, GenericServiceException {
    log.info("Getting excel template based on vessel Type");
    vesselParticular = getVesselPurticulars(vesselId);
    if (vesselParticular.getVesselTypeId() != null) {
      return TEMPLATES_FILE_LOCATION.replace(
          "{type}", vesselParticular.getVesselTypeId().toString());
    } else {
      log.info(
          "Generating excel failed ,Vessel Type undefined for vessel {}. No file template found",
          vesselId);
      throw new GenericServiceException(
          "Generating excel failed ,Vessel Type undefined for vessel {}. No file template found",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /** Get fully qualified name of output file */
  private String getFileName(Long vesselId, String voyNo, String portName) {
    return OUTPUT_FILE_LOCATION
        .replace("{id}", vesselId.toString())
        .replace("{voy}", voyNo)
        .replace("{port}", portName)
        .replace(" ", "_");
  }

  /** Method to get data mapped for excel sheets */
  private LoadingPlanExcelDetails getDataForExcel(
      LoadingPlanResponse requestPayload,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId)
      throws GenericServiceException, InterruptedException, ExecutionException, ParseException {
    log.info("Building details for excel sheetwise ");
    LoadingPlanExcelDetails excelData = new LoadingPlanExcelDetails();
    if (requestPayload == null) {
      // Calling loading plan get plan details service
      requestPayload =
          loadingPlanServiceImpl.getLoadingPlan(vesselId, voyageId, infoId, portRotationId);
    }
    voyageDate = requestPayload.getVoyageDate();
    // Get a list of all ballast tanks for sheet3
    getAllBallastTanks(
        requestPayload.getBallastFrontTanks(),
        requestPayload.getBallastCenterTanks(),
        requestPayload.getBallastRearTanks());
    // Get machinery in use for sheet 2
    cargoMachinery = requestPayload.getLoadingInformation().getMachineryInUses();

    excelData.setSheetOne(
        buildSheetOne(requestPayload, vesselId, voyageId, infoId, portRotationId));
    excelData.setSheetTwo(buildSheetTwo(vesselId, voyageId, infoId, portRotationId));
    excelData.setSheetThree(buildSheetThree(vesselId, voyageId, infoId, portRotationId));
    return excelData;
  }

  /** Preparing sheet 2 */
  private LoadingPlanExcelLoadingInstructionDetails buildSheetTwo(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException {
    log.info("Building sheet 2 : Loading instructions");
    // Calling loading plan get instructions details service
    LoadingInstructionResponse loadingSequenceResponse =
        loadingInstructionService.getLoadingInstructions(vesselId, infoId, portRotationId);
    LoadingPlanExcelLoadingInstructionDetails sheetTwo =
        new LoadingPlanExcelLoadingInstructionDetails();
    sheetTwo.setInstructions(getInstructions(loadingSequenceResponse));
    sheetTwo.setVesselPurticulars(getVesselPurticulars(vesselId));
    if (cargoMachinery != null) {
      sheetTwo.setManifoldNames(
          cargoMachinery.getVesselManifold().stream()
              .map(item -> item.getComponentCode())
              .collect(Collectors.joining(",")));
      sheetTwo.setBallastPump(
          cargoMachinery.getVesselPumps().stream()
                  .anyMatch(item -> item.getPumpTypeId().equals(BALLAST_PUMP_ID))
              ? YES
              : NO);
      sheetTwo.setGsPump(
          cargoMachinery.getVesselPumps().stream()
                  .anyMatch(item -> item.getPumpTypeId().equals(GS_PUMP_ID))
              ? YES
              : NO);
      sheetTwo.setIgsPump(
          cargoMachinery.getVesselPumps().stream()
                  .anyMatch(item -> item.getPumpTypeId().equals(IGS_PUMP_ID))
              ? YES
              : NO);
    }
    sheetTwo.setCargoNames(cargoNames);
    log.info("Building sheet 2 : Completed");
    return sheetTwo;
  }

  /**
   * Vessel Particular details from vessel info service
   *
   * @param vesselId
   * @return
   * @throws GenericServiceException
   * @throws NumberFormatException
   */
  private VesselParticularsForExcel getVesselPurticulars(Long vesselId)
      throws NumberFormatException, GenericServiceException {
    if (vesselParticular == null) {
      return vesselInfoService.getVesselParticulars(vesselId);
    }
    return vesselParticular;
  }

  /**
   * Get list of instructions segregated against heading
   *
   * @param integer
   * @param loadingSequenceResponse
   * @return
   */
  private List<LoadingInstructionForExcel> getInstructions(
      LoadingInstructionResponse loadingSequenceResponse) {
    List<LoadingInstructionForExcel> instructionsList = new ArrayList<>();
    int group = 0;
    int groupHeading = 5;
    List<Integer> continuityList = Arrays.asList(0, 4, 5, 6, 7, 8);
    for (Long headerId : INSTRUCTION_ORDER) {
      Optional<LoadingInstructionGroup> ligOpt =
          loadingSequenceResponse.getLoadingInstructionGroupList().stream()
              .filter(value -> value.getGroupId().equals(headerId))
              .findAny();

      String heading = ligOpt.isEmpty() ? "" : ligOpt.get().getGroupName();

      List<LoadingInstructionSubHeader> listUnderHeader =
          loadingSequenceResponse.getLoadingInstructionSubHeader().stream()
              .filter(item -> item.getInstructionHeaderId().equals(headerId) && item.getIsChecked())
              .collect(Collectors.toList());
      int count = 1;
      if (!listUnderHeader.isEmpty()) {
        for (LoadingInstructionSubHeader item : listUnderHeader) {
          LoadingInstructionForExcel instructionObj = new LoadingInstructionForExcel();
          instructionObj.setGroup(group);
          if (continuityList.contains(group)) {
            instructionObj.setHeading(groupHeading + ".  " + heading);
          } else {
            instructionObj.setHeading("   " + heading);
          }
          instructionObj.setInstruction(count + ".  " + item.getSubHeaderName());
          if (group == 0) {
            instructionObj.setCargoLoadingVisible(true);
          } else if (group == 5) {
            instructionObj.setMachineryInuseVisible(true);
          }
          instructionsList.add(instructionObj);
          count++;
          if (!item.getIsSingleHeader() && item.getLoadingInstructionsList().size() > 0) {
            int childCount = 1;
            for (LoadingInstructions subItem : item.getLoadingInstructionsList()) {
              if (subItem.getIsChecked()) {
                LoadingInstructionForExcel subInstructionObj = new LoadingInstructionForExcel();
                subInstructionObj.setGroup(group);
                subInstructionObj.setHeading(heading);
                subInstructionObj.setInstruction(
                    "    " + childCount + ".  " + subItem.getInstruction());
                instructionsList.add(subInstructionObj);
                childCount++;
              }
            }
          }
        }
      } else {
        LoadingInstructionForExcel instructionObj = new LoadingInstructionForExcel();
        instructionObj.setGroup(group);
        if (continuityList.contains(group)) {
          instructionObj.setHeading(groupHeading + ".  " + heading);
        } else {
          instructionObj.setHeading("   " + heading);
        }
        instructionObj.setInstruction("**No instructions available under this section**");
        if (group == 0) {
          instructionObj.setCargoLoadingVisible(true);
        } else if (group == 5) {
          instructionObj.setMachineryInuseVisible(true);
        }
        instructionsList.add(instructionObj);
      }
      if (continuityList.contains(group)) {
        groupHeading++;
        if (group == 0 || group == 5) {
          groupHeading++;
        }
      }
      group++;
    }
    return instructionsList;
  }

  /**
   * List of all ballast tanks available for ship
   *
   * @param ballastFrontTanks
   * @param ballastCenterTanks
   * @param ballastRearTanks
   */
  private void getAllBallastTanks(
      List<List<VesselTank>> ballastFrontTanks,
      List<List<VesselTank>> ballastCenterTanks,
      List<List<VesselTank>> ballastRearTanks) {
    List<TankCargoDetails> tanks = new ArrayList<>();
    for (List<VesselTank> tankList : ballastFrontTanks) {
      for (VesselTank tank : tankList) {
        TankCargoDetails tankObj = new TankCargoDetails();
        tankObj.setTankName(tank.getShortName());
        tankObj.setId(tank.getId());
        if (!tank.getShortName().contains("VOID")) {
          tanks.add(tankObj);
        }
      }
    }
    for (List<VesselTank> tankList : ballastCenterTanks) {
      for (VesselTank tank : tankList) {
        TankCargoDetails tankObj = new TankCargoDetails();
        tankObj.setTankName(tank.getShortName());
        tankObj.setId(tank.getId());
        if (!tank.getShortName().contains("VOID")) {
          tanks.add(tankObj);
        }
      }
    }
    for (List<VesselTank> tankList : ballastRearTanks) {
      for (VesselTank tank : tankList) {
        TankCargoDetails tankObj = new TankCargoDetails();
        tankObj.setTankName(tank.getShortName());
        tankObj.setId(tank.getId());
        if (!tank.getShortName().contains("VOID")) {
          tanks.add(tankObj);
        }
      }
    }
    ballastTanks = tanks;
  }

  /**
   * Build data model for Sheet 3
   *
   * @return sheet one
   * @throws GenericServiceException
   */
  private LoadingPlanExcelLoadingSequenceDetails buildSheetThree(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException {
    log.info("Building sheet 3 : Loading sequence chart");
    // Calling loading plan get sequence details service
    LoadingSequenceResponse loadingSequenceResponse =
        loadingPlanServiceImpl.getLoadingSequence(vesselId, voyageId, infoId);
    LoadingPlanExcelLoadingSequenceDetails sheetThree =
        new LoadingPlanExcelLoadingSequenceDetails();
    // Calculating no:of stages in sequence
    sheetThree.setTickPoints(
        getTickPoints(
            loadingSequenceResponse.getMinXAxisValue(),
            loadingSequenceResponse.getStageTickPositions()));
    log.info("Sequence identified with {} Sections ", sheetThree.getTickPoints().size());
    if (sheetThree.getTickPoints().size() > 0) {
      // Getting all tanks present in sequence
      sheetThree.setCargoTanks(
          getCargoTanks(
              loadingSequenceResponse.getCargoTankCategories(),
              loadingSequenceResponse.getCargos()));
      if (sheetThree.getCargoTanks().size() > 0) {
        // Getting ullage mapped against each tank if present
        getCargoTankUllageAndQuantity(
            loadingSequenceResponse.getStageTickPositions(),
            loadingSequenceResponse.getCargos(),
            sheetThree.getCargoTanks());
        sheetThree.setLoadingRates(
            getLoadingRate(
                loadingSequenceResponse.getCargoLoadingRates(),
                loadingSequenceResponse.getStageTickPositions()));
        sheetThree.setInitialLoadingRate(
            sheetThree.getLoadingRates().get(0).getRate().split("/")[0]);
      }
      sheetThree.setBallastTanks(
          getBallastTanks(
              loadingSequenceResponse.getBallastTankCategories(),
              loadingSequenceResponse.getBallasts()));
      if (sheetThree.getBallastTanks().size() > 0) {
        getBallastTankUllageAndQuantity(
            loadingSequenceResponse.getStageTickPositions(),
            loadingSequenceResponse.getBallasts(),
            sheetThree.getBallastTanks());
      }
      sheetThree.setStabilityParams(
          getStabilityParams(
              loadingSequenceResponse.getStabilityParams(), sheetThree.getTickPoints().size()));
    }
    log.info("Building sheet 3 : completed");
    return sheetThree;
  }

  /**
   * Get sounding of ballast tanks in each tick position
   *
   * @param stageTickPositions
   * @param ballasts
   * @param list
   * @return
   */
  private void getBallastTankUllageAndQuantity(
      Set<Long> stageTickPositions,
      List<Ballast> ballasts,
      List<TankCategoryForSequence> tankList) {
    List<Long> positionList = getListFromSet(stageTickPositions);
    // Getting ullages on each tick position of each tank
    for (TankCategoryForSequence tank : tankList) {
      TankWithSequenceDetails tanksUllageObj = new TankWithSequenceDetails();
      tanksUllageObj.setTankId(tank.getId());
      tanksUllageObj.setUllage(new ArrayList<>());
      tanksUllageObj.setStatus(new ArrayList<>());
      List<Ballast> ballastListOfpresentTank =
          ballasts.stream()
              .filter(cargo -> cargo.getTankId().equals(tank.getId()))
              .collect(Collectors.toList());
      if (!ballastListOfpresentTank.isEmpty()) {
        List<String> ullages = new ArrayList<>();
        List<QuantityLoadingStatus> ballastStatusList = new ArrayList<>();
        Optional<Ballast> ballastMatch = Optional.empty();
        // Finding ullage and values in first tick position
        ballastMatch =
            ballastListOfpresentTank.stream()
                .filter(
                    ballast ->
                        ballast.getStart().equals(positionList.get(0))
                            && ballast.getEnd().equals(positionList.get(0)))
                .findFirst();
        setUllageAndQuantityBallast(ballastMatch, ullages, ballastStatusList);
        for (Long position : positionList) {
          // avoiding last tick position since this is not needed for sequence
          if (!positionList.get(positionList.size() - 1).equals(position)) {
            ballastMatch =
                ballastListOfpresentTank.stream()
                    .filter(
                        ballast ->
                            ballast.getStart().equals(position) && ballast.getEnd() > position)
                    .findFirst();
            setUllageAndQuantityBallast(ballastMatch, ullages, ballastStatusList);
          }
        }
        tanksUllageObj.setUllage(ullages);
        tanksUllageObj.setStatus(ballastStatusList);
      } else {
        // setting empty values for look and feel
        IntStream.range(0, stageTickPositions.size())
            .forEach(i -> tanksUllageObj.getUllage().add(""));
        IntStream.range(0, stageTickPositions.size())
            .forEach(
                i ->
                    tanksUllageObj
                        .getStatus()
                        .add(new QuantityLoadingStatus(false, "", "", false, "")));
      }
      tank.setCargoTankUllage(tanksUllageObj);
    }
  }

  /**
   * Get ullage of ballast tanks in each tick position
   *
   * @param tankList
   * @param loadingSequenceResponse
   * @param tankCategories
   * @return
   */
  private void getCargoTankUllageAndQuantity(
      Set<Long> stageTickPositions, List<Cargo> cargos, List<TankCategoryForSequence> tankList) {
    List<Long> positionList = getListFromSet(stageTickPositions);
    // Getting ullages on each tick position of each tank
    for (TankCategoryForSequence tank : tankList) {
      TankWithSequenceDetails tanksUllageObj = new TankWithSequenceDetails();
      tanksUllageObj.setTankId(tank.getId());
      tanksUllageObj.setUllage(new ArrayList<>());
      tanksUllageObj.setStatus(new ArrayList<>());
      List<Cargo> cargoListOfpresentTank =
          cargos.stream()
              .filter(cargo -> cargo.getTankId().equals(tank.getId()))
              .collect(Collectors.toList());
      if (!cargoListOfpresentTank.isEmpty()) {
        List<String> ullages = new ArrayList<>();
        List<QuantityLoadingStatus> quantityStatusList = new ArrayList<>();
        Optional<Cargo> cargoMatch = Optional.empty();
        // Finding ullage and values in first tick position
        cargoMatch =
            cargoListOfpresentTank.stream()
                .filter(
                    cargo ->
                        cargo.getStart().equals(positionList.get(0))
                            && cargo.getEnd().equals(positionList.get(0)))
                .findFirst();
        setUllageAndQuantityCargo(cargoMatch, ullages, quantityStatusList);
        // Finding ullage in each tick position
        for (Long position : positionList) {
          // avoiding last tick position since this is not needed for sequence
          if (!positionList.get(positionList.size() - 1).equals(position)) {
            cargoMatch =
                cargoListOfpresentTank.stream()
                    .filter(cargo -> cargo.getStart().equals(position) && cargo.getEnd() > position)
                    .findFirst();
            setUllageAndQuantityCargo(cargoMatch, ullages, quantityStatusList);
          }
        }
        tanksUllageObj.setUllage(ullages);
        tanksUllageObj.setStatus(quantityStatusList);

      } else {
        // setting empty values for look and feel in excel
        IntStream.range(0, stageTickPositions.size())
            .forEach(i -> tanksUllageObj.getUllage().add(""));
        IntStream.range(0, stageTickPositions.size())
            .forEach(
                i ->
                    tanksUllageObj
                        .getStatus()
                        .add(new QuantityLoadingStatus(false, "", "", false, "")));
      }
      tank.setCargoTankUllage(tanksUllageObj);
    }
  }

  private List<Long> getListFromSet(Set<Long> stageTickPositions) {
    List<Long> list = new ArrayList<>();
    for (Long item : stageTickPositions) {
      list.add(item);
    }
    list.stream().sorted();
    return list;
  }

  /**
   * Method to set data against a tick position
   *
   * @param cargoMatch
   * @param ullages
   * @param quantityStatusList
   */
  private void setUllageAndQuantityCargo(
      Optional<Cargo> cargoMatch,
      List<String> ullages,
      List<QuantityLoadingStatus> quantityStatusList) {
    QuantityLoadingStatus loadingStatus = new QuantityLoadingStatus();
    if (cargoMatch.isPresent()) {
      ullages.add(cargoMatch.get().getUllage().toString());
      if (cargoMatch.get().getQuantity().compareTo(BigDecimal.ZERO) > 0
          && cargoMatch.get().getCargoNominationId() > 0
          && cargoMatch.get().getColor() != null) {
        loadingStatus.setPresent(true);
        loadingStatus.setCargoName(cargoMatch.get().getAbbreviation());
        loadingStatus.setColorCode(cargoMatch.get().getColor());
      } else {
        loadingStatus.setPresent(false);
      }
    } else {
      ullages.add("");
      loadingStatus.setPresent(false);
    }
    quantityStatusList.add(loadingStatus);
  }

  /**
   * Method to set data against a tick position
   *
   * @param cargoMatch
   * @param ullages
   * @param quantityStatusList
   */
  private void setUllageAndQuantityBallast(
      Optional<Ballast> ballastMatch,
      List<String> ullages,
      List<QuantityLoadingStatus> ballastStatusList) {
    QuantityLoadingStatus ballastStatus = new QuantityLoadingStatus();
    if (ballastMatch.isPresent()) {
      ullages.add(ballastMatch.get().getSounding().toString());
      if (ballastMatch.get().getColor() != null) {
        ballastStatus.setPresent(true);
        ballastStatus.setColorCode(ballastMatch.get().getColor());
      } else {
        ballastStatus.setPresent(false);
      }
    } else {
      ullages.add("");
      ballastStatus.setPresent(false);
    }
    ballastStatusList.add(ballastStatus);
  }

  /**
   * Convert tick position form millisecond to hours
   *
   * @param minXAxisValue
   * @param stageTickPositions
   * @return
   */
  private List<String> getTickPoints(Long minXAxisValue, Set<Long> stageTickPositions) {
    List<Double> tickPoints = new ArrayList<>();
    for (Long xValue : stageTickPositions) {
      Double hours = ((xValue.doubleValue() - minXAxisValue.doubleValue()) / 1000) / 3600;
      tickPoints.add(hours);
    }
    return tickPoints.stream()
        .sorted()
        .map(i -> String.format("%.2f", i))
        .collect(Collectors.toList());
  }

  /**
   * Get tank mapped with the cargo inside
   *
   * @param cargoTankCategories
   * @param cargos
   * @return
   */
  private List<TankCategoryForSequence> getCargoTanks(
      List<TankCategory> cargoTankCategories, List<Cargo> cargos) {
    List<TankCategoryForSequence> tankList = new ArrayList<>();
    // List of tanks with cargo details
    for (TankCargoDetails cargoTank : cargoTanks) {
      TankCategoryForSequence tankCategoryObj = new TankCategoryForSequence();
      tankCategoryObj.setId(cargoTank.getId());
      tankCategoryObj.setTankName(cargoTank.getTankName());
      cargoTankCategories.forEach(
          item -> {
            if (item.getId().equals(cargoTank.getId())) {
              tankCategoryObj.setUllage(item.getUllage());
              tankCategoryObj.setDisplayOrder(item.getDisplayOrder());
              tankCategoryObj.setQuantity(item.getQuantity());
              Optional<Cargo> cargoOpt =
                  cargos.stream()
                      .filter(
                          cargo ->
                              cargo.getTankId().equals(item.getId())
                                  && cargo.getQuantity().compareTo(BigDecimal.ZERO) > 0)
                      .findFirst();
              if (cargoOpt.isPresent()) {
                tankCategoryObj.setCargoName(cargoOpt.get().getAbbreviation());
                tankCategoryObj.setColorCode(cargoOpt.get().getColor());
              } else {
                tankCategoryObj.setCargoName("");
              }
            }
          });
      tankList.add(tankCategoryObj);
    }
    return tankList;
  }

  private List<TankCategoryForSequence> getBallastTanks(
      List<TankCategory> ballastTankCategories, List<Ballast> ballasts) {
    List<TankCategoryForSequence> tankList = new ArrayList<>();
    // List of tanks with cargo details
    for (TankCargoDetails tank : ballastTanks) {
      TankCategoryForSequence tankCategoryObj = new TankCategoryForSequence();
      tankCategoryObj.setId(tank.getId());
      tankCategoryObj.setTankName(tank.getTankName());
      ballastTankCategories.forEach(
          item -> {
            if (item.getId().equals(tank.getId())) {
              Optional<Ballast> ballastOpt =
                  ballasts.stream()
                      .filter(
                          cargo ->
                              cargo.getTankId().equals(item.getId())
                                  && cargo.getQuantity().compareTo(BigDecimal.ZERO) > 0)
                      .findFirst();
              if (ballastOpt.isPresent()) {
                tankCategoryObj.setColorCode(ballastOpt.get().getColor());
              }
            }
          });
      tankList.add(tankCategoryObj);
    }
    return tankList;
  }

  /**
   * Finding loading rate
   *
   * @param cargoLoadingRates
   * @param stageTickPositions
   * @return
   */
  private List<LoadingRateForSequence> getLoadingRate(
      List<CargoLoadingRate> cargoLoadingRates, Set<Long> stageTickPositions) {
    List<LoadingRateForSequence> loadingRates = new ArrayList<>();
    List<Long> positions = getListFromSet(stageTickPositions);
    IntStream.range(0, positions.size() - 1)
        .forEach(
            i -> {
              LoadingRateForSequence lrsObj = new LoadingRateForSequence();
              lrsObj.setPosision(i);
              lrsObj.setRate("");
              loadingRates.add(lrsObj);
            });
    List<CargoLoadingRate> selectedItem =
        cargoLoadingRates.stream()
            .filter(item -> !item.getLoadingRates().isEmpty())
            .collect(Collectors.toList());
    for (CargoLoadingRate rate : selectedItem) {
      int start = positions.indexOf(rate.getStartTime());
      int end = positions.indexOf(rate.getEndTime());
      for (int i = start; i < end; i++) {
        for (BigDecimal value : rate.getLoadingRates()) {
          if (loadingRates.get(i).getRate().isEmpty()) {
            loadingRates.get(i).setRate(value.toString());
          } else if (!loadingRates.get(i).getRate().contains(value.toString())) {
            loadingRates.get(i).setRate(loadingRates.get(i).getRate() + "/" + value.toString());
          }
        }
      }
    }
    return loadingRates;
  }

  /**
   * Stability params of each type
   *
   * @param stabilityParams
   * @param size
   * @return
   */
  private StabilityParamsOfLoadingSequence getStabilityParams(
      List<StabilityParam> stabilityParams, Integer size) {
    StabilityParamsOfLoadingSequence sequenceStability = new StabilityParamsOfLoadingSequence();
    List<String> fwList = new ArrayList<>();
    List<String> afterList = new ArrayList<>();
    List<String> trimList = new ArrayList<>();
    List<String> gmList = new ArrayList<>();
    List<String> sfList = new ArrayList<>();
    List<String> bmList = new ArrayList<>();
    List<String> ukcList = new ArrayList<>();
    for (StabilityParam stabilityParam : stabilityParams) {
      switch (stabilityParam.getName()) {
        case "fore_draft":
          matchStabilityParam(fwList, stabilityParam.getData(), size);
          break;
        case "aft_draft":
          matchStabilityParam(afterList, stabilityParam.getData(), size);
          break;
        case "trim":
          matchStabilityParam(trimList, stabilityParam.getData(), size);
          break;
        case "gm":
          matchStabilityParam(gmList, stabilityParam.getData(), size);
          break;
        case "sf":
          matchStabilityParam(sfList, stabilityParam.getData(), size);
          break;
        case "bm":
          matchStabilityParam(bmList, stabilityParam.getData(), size);
          break;
        case "ukc":
          matchStabilityParam(ukcList, stabilityParam.getData(), size);
          break;
        default:
          break;
      }
    }
    sequenceStability.setAfter(afterList);
    sequenceStability.setFw(fwList);
    sequenceStability.setBm(bmList);
    sequenceStability.setGm(gmList);
    sequenceStability.setTrim(trimList);
    sequenceStability.setUkc(ukcList);
    sequenceStability.setShearingForce(sfList);
    sequenceStability.setShearingForce(sfList);
    return sequenceStability;
  }

  private void matchStabilityParam(List<String> paramsList, List<List> params, Integer size) {
    if (params.isEmpty()) {
      // Setting empty value for excel look and feel
      IntStream.range(0, size).forEach(i -> paramsList.add(""));
    } else {
      params.forEach(
          i -> {
            paramsList.add(i.get(1).toString());
          });
    }
  }

  /**
   * Build data model for Sheet 1
   *
   * @return sheet one
   * @throws GenericServiceException
   * @throws ParseException
   */
  private LoadingPlanExcelLoadingPlanDetails buildSheetOne(
      LoadingPlanResponse requestPayload,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId)
      throws GenericServiceException, ParseException {
    log.info("Building sheet 1 : Loading plan diagram");
    LoadingPlanExcelLoadingPlanDetails sheetOne = new LoadingPlanExcelLoadingPlanDetails();
    getBasicVesselDetails(sheetOne, vesselId, voyageId, infoId, portRotationId);
    // Condition type 1 is arrival
    sheetOne.setArrivalCondition(getVesselConditionDetails(requestPayload, 1));
    sheetOne.setDeparcherCondition(getVesselConditionDetails(requestPayload, 2));
    sheetOne.setCargoTobeLoaded(getCargoTobeLoadedDetails(requestPayload));
    getBerthInfoDetails(sheetOne, requestPayload);
    log.info("Building sheet 1 : Completed");
    return sheetOne;
  }

  /**
   * Fetch basic vessel and Port details
   *
   * @param excelData
   * @param requestPayload
   * @param vesselId
   * @param voyageId
   * @param infoId
   * @param portRotationId
   * @throws GenericServiceException
   * @throws ParseException
   */
  private void getBasicVesselDetails(
      LoadingPlanExcelLoadingPlanDetails sheetOne,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId)
      throws GenericServiceException, ParseException {
    VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
    Optional<PortRotation> portRotation =
        activeVoyage.getPortRotations().stream()
            .filter(v -> v.getId().equals(portRotationId))
            .findFirst();
    VesselResponse vesselResponse = vesselInfoService.getVesselsByCompany(1L, null);
    Vessel vessel =
        vesselResponse.getVessels().stream()
            .filter(i -> i.getId().equals(vesselId))
            .findFirst()
            .orElseThrow(
                () ->
                    new GenericServiceException(
                        String.format("Vessel details not found for VesselId: %d", vesselId),
                        CommonErrorCodes.E_HTTP_BAD_REQUEST,
                        HttpStatusCode.BAD_REQUEST));
    if (vessel != null) {
      sheetOne.setVesselName(vessel.getName());
      sheetOne.setMaster(String.valueOf(vessel.getCaptainName()));
      sheetOne.setCo(String.valueOf(vessel.getChiefOfficerName()));
      sheetOne.setVoyageNumber(activeVoyage.getVoyageNumber());
    }
    if (portRotation.isPresent()) {
      PortInfo.GetPortInfoByPortIdsRequest request =
          PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
              .addId(portRotation.get().getPortId())
              .build();
      PortInfo.PortDetail portReply =
          getPortInfo(request).getPortsList().stream()
              .findFirst()
              .orElse(PortInfo.PortDetail.getDefaultInstance());
      if (portReply != null) {
        sheetOne.setPortName(portReply.getName());
        sheetOne.setEta(dateFormater(portRotation.get().getEta()));
        sheetOne.setEtd(dateFormater(portRotation.get().getEtd()));
      }
    }
    if (activeVoyage.getActiveLs() != null) {
      Optional<LoadLine> loadLine =
          vessel.getLoadlines().stream()
              .filter(item -> item.getId().equals(activeVoyage.getActiveLs().getLoadLineXId()))
              .findFirst();
      loadLine.ifPresent(item -> sheetOne.setLoadLineZone(item.getName()));
      if (activeVoyage.getActiveLs().getDraftRestriction() != null) {
        sheetOne.setVesselCompliance("No");
      } else {
        sheetOne.setVesselCompliance("Yes");
      }
    }
  }

  private String dateFormater(String eta) throws ParseException {
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    Date date = dt.parse(eta);
    SimpleDateFormat dt1 = new SimpleDateFormat("dd MMMM yyyy");
    return dt1.format(date);
  }

  /**
   * Berth information details
   *
   * @param sheetOne
   * @param requestPayload
   * @return
   */
  private void getBerthInfoDetails(
      LoadingPlanExcelLoadingPlanDetails sheetOne, LoadingPlanResponse requestPayload) {
    List<BerthInformation> berthInfoList = new ArrayList<>();
    List<BerthDetails> berthDetailsList =
        requestPayload.getLoadingInformation().getBerthDetails().getSelectedBerths();
    List<BerthDetails> allBerths =
        requestPayload.getLoadingInformation().getBerthDetails().getAvailableBerths();
    String itemsAgreedWithTerminal = "";
    if (!berthDetailsList.isEmpty()) {
      berthDetailsList.forEach(
          item -> {
            BerthInformation berthInformation = new BerthInformation();
            Optional<BerthDetails> opt =
                allBerths.stream()
                    .filter(berth -> berth.getBerthId().equals(item.getBerthId()))
                    .findAny();
            Optional.ofNullable(opt.get().getBerthName()).ifPresent(berthInformation::setBerthName);
            Optional.ofNullable(item.getHoseConnections())
                .ifPresent(berthInformation::setHoseConnection);
            Optional.ofNullable(item.getMaxManifoldPressure())
                .ifPresent(berthInformation::setMaxManifoldPressure);
            Optional.ofNullable(item.getAirDraftLimitation())
                .ifPresent(berthInformation::setAirDraftLimitation);
            Optional.ofNullable(item.getAirPurge()).ifPresent(berthInformation::setAirPurge);
            Optional.ofNullable(item.getRegulationAndRestriction())
                .ifPresent(berthInformation::setSpecialRegulation);
            Optional.ofNullable(item.getItemsToBeAgreedWith())
                .ifPresent(
                    i -> {
                      if (!itemsAgreedWithTerminal.contains(i)) {
                        itemsAgreedWithTerminal.concat(",/n" + i);
                      }
                    });
            berthInfoList.add(berthInformation);
          });
    } else {
      // Adding an empty element for excel proper alignment
      berthInfoList.add(new BerthInformation());
    }

    sheetOne.setBerthInformation(berthInfoList);
    sheetOne.setItemsAgreedWithTerminal(itemsAgreedWithTerminal);
  }

  /**
   * Calling port GRPC for getting Port name
   *
   * @param build
   * @return PortReply
   */
  public PortInfo.PortReply getPortInfo(PortInfo.GetPortInfoByPortIdsRequest build) {
    return portInfoGrpcService.getPortInfoByPortIds(build);
  }

  /**
   * Fetch cargo to be loaded details
   *
   * @param requestPayload
   * @return
   */
  private List<CargoTobeLoaded> getCargoTobeLoadedDetails(LoadingPlanResponse requestPayload) {
    List<CargoTobeLoaded> cargoTobeLoadedList = new ArrayList<>();
    List<LoadableQuantityCargoDetails> LoadableQuantityCargoList =
        requestPayload
            .getLoadingInformation()
            .getCargoVesselTankDetails()
            .getLoadableQuantityCargoDetails();
    if (!LoadableQuantityCargoList.isEmpty()) {
      LoadableQuantityCargoList.forEach(
          item -> {
            CargoTobeLoaded cargoTobeLoaded = new CargoTobeLoaded();
            Optional.ofNullable(item.getCargoAbbreviation())
                .ifPresent(cargoTobeLoaded::setCargoName);
            Optional.ofNullable(item.getColorCode()).ifPresent(cargoTobeLoaded::setColorCode);
            Optional.ofNullable(item.getEstimatedAPI()).ifPresent(cargoTobeLoaded::setApi);
            Optional.ofNullable(item.getEstimatedTemp()).ifPresent(cargoTobeLoaded::setTemperature);
            Optional.ofNullable(item.getLoadingPorts())
                .ifPresent(
                    ports ->
                        cargoTobeLoaded.setLoadingPort(
                            ports.stream().collect(Collectors.joining(","))));
            Optional.ofNullable(item.getCargoNominationQuantity())
                .ifPresent(cargoTobeLoaded::setNomination);
            Optional.ofNullable(item.getLoadableMT()).ifPresent(cargoTobeLoaded::setShipLoadable);
            Optional.ofNullable(item.getMaxTolerence()).ifPresent(cargoTobeLoaded::setTolerance);
            Optional.ofNullable(item.getDifferencePercentage())
                .ifPresent(cargoTobeLoaded::setDifference);
            Optional.ofNullable(item.getTimeRequiredForLoading())
                .ifPresent(cargoTobeLoaded::setTimeRequiredForLoading);
            Optional.ofNullable(item.getSlopQuantity()).ifPresent(cargoTobeLoaded::setSlopQuantity);
            cargoTobeLoadedList.add(cargoTobeLoaded);
          });
    }
    return cargoTobeLoadedList;
  }

  /**
   * Build tank params
   *
   * @param excelData
   * @param requestPayload
   * @param
   * @throws GenericServiceException
   */
  private ArrivalDeparcherCondition getVesselConditionDetails(
      LoadingPlanResponse requestPayload, Integer conditionType) throws GenericServiceException {
    ArrivalDeparcherCondition vesselCondition = new ArrivalDeparcherCondition();
    setCargoTanksWithQuantityForReport(vesselCondition, requestPayload, conditionType);
    setBallastTanksWithQuantityForReport(vesselCondition, requestPayload, conditionType);
    setCargoQuantityList(vesselCondition, requestPayload, conditionType);
    return vesselCondition;
  }

  /**
   * Get list of cargo in a port
   *
   * @param vesselCondition
   * @param requestPayload
   * @param conditionType
   */
  private void setCargoQuantityList(
      ArrivalDeparcherCondition vesselCondition,
      LoadingPlanResponse requestPayload,
      Integer conditionType) {
    List<CargoQuantity> cargoQuantitylist = new ArrayList<>();
    requestPayload
        .getCurrentPortCargos()
        .forEach(
            item -> {
              CargoQuantity cargo = new CargoQuantity();
              Double totalQuantity = 0.0;
              Optional.ofNullable(item.getCargoAbbreviation()).ifPresent(cargo::setCargoName);
              Optional.ofNullable(item.getColorCode()).ifPresent(cargo::setColorCode);
              totalQuantity =
                  requestPayload.getPlanStowageDetails().stream()
                      .filter(
                          psd ->
                              psd.getConditionType().equals(conditionType)
                                  && psd.getCargoId().equals(item.getCargoId())
                                  && psd.getValueType().equals(2))
                      .mapToDouble(i -> Double.parseDouble(i.getQuantityMT()))
                      .sum();
              cargo.setQuantity(totalQuantity.toString());
              cargoQuantitylist.add(cargo);
            });
    vesselCondition.setCargoDetails(cargoQuantitylist);
    if (cargoNames == null) {
      cargoNames =
          cargoQuantitylist.stream()
              .map(item -> item.getCargoName())
              .collect(Collectors.joining(", "));
    }
  }

  /**
   * Ballast Tank and quantity details w.r.t condition type
   *
   * @param vesselCondition
   * @param requestPayload
   * @param conditionType
   * @throws GenericServiceException
   */
  private void setBallastTanksWithQuantityForReport(
      ArrivalDeparcherCondition vesselCondition,
      LoadingPlanResponse requestPayload,
      Integer conditionType) {
    List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList =
        requestPayload.getPlanBallastDetails();
    List<TankCargoDetails> tankCargoDetailsList = new ArrayList<>();
    Double ballastWaterSum = 0.0;
    // Ballast rear tanks
    getBallastTankDetails(
        tankCargoDetailsList,
        requestPayload.getBallastRearTanks(),
        loadingPlanBallastDetailsList,
        conditionType);
    ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
    findAptFpt(vesselCondition, tankCargoDetailsList, "R");
    tankCargoDetailsList.clear();
    // Ballast front tanks
    getBallastTankDetails(
        tankCargoDetailsList,
        requestPayload.getBallastFrontTanks(),
        loadingPlanBallastDetailsList,
        conditionType);
    ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
    findAptFpt(vesselCondition, tankCargoDetailsList, "F");
    tankCargoDetailsList.clear();
    // Ballast center tanks
    getBallastTankDetails(
        tankCargoDetailsList,
        requestPayload.getBallastCenterTanks(),
        loadingPlanBallastDetailsList,
        conditionType);
    ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
    vesselCondition.setBallastTopTanks(getTankAgainstPossision(tankCargoDetailsList, "P"));
    vesselCondition.setBallastBottomTanks(getTankAgainstPossision(tankCargoDetailsList, "S"));
    vesselCondition.setBallastWaterSegregated(ballastWaterSum);
  }

  /**
   * @param vesselCondition
   * @param tankCargoDetailsList
   * @param position
   * @return
   */
  private void findAptFpt(
      ArrivalDeparcherCondition vesselCondition,
      List<TankCargoDetails> tankCargoDetailsList,
      String position) {

    if (position.equalsIgnoreCase("R")) {
      vesselCondition.setApt(tankCargoDetailsList.get(0));
    } else {
      // Checking if FPT tanks are separated or not
      if (tankCargoDetailsList.size() == 1) {
        vesselCondition.setFpt(tankCargoDetailsList.get(0));
        vesselCondition.setFptTankMerged(true);
      } else {
        vesselCondition.setFptTankMerged(false);
        tankCargoDetailsList.forEach(
            item -> {
              if (item.getTankName().equalsIgnoreCase(LFPT)) {
                vesselCondition.setLfpt(item);
              } else if (item.getTankName().equalsIgnoreCase(UFPT)) {
                vesselCondition.setUfpt(item);
              }
            });
      }
    }
  }

  /**
   * Mapping quantity against each ballast tank
   *
   * @param tankCargoDetailsList
   * @param ballastTanks
   * @param loadingPlanBallastDetailsList
   */
  private void getBallastTankDetails(
      List<TankCargoDetails> tankCargoDetailsList,
      List<List<VesselTank>> ballastTanks,
      List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList,
      Integer conditionType) {
    ballastTanks.forEach(
        tankList -> {
          tankList.forEach(
              tank -> {
                Optional<LoadingPlanBallastDetails> loadingPlanBallastDetailsOpt =
                    loadingPlanBallastDetailsList.stream()
                        .filter(
                            item ->
                                item.getTankId().equals(tank.getId())
                                    && item.getConditionType().equals(conditionType)
                                    && item.getValueType().equals(2))
                        .findAny();
                TankCargoDetails tankCargoDetails = new TankCargoDetails();
                tankCargoDetails.setTankName(tank.getShortName());
                tankCargoDetails.setId(tank.getId());
                setBallasTankValues(loadingPlanBallastDetailsOpt, tankCargoDetails);
                tankCargoDetailsList.add(tankCargoDetails);
              });
        });
  }

  /**
   * @param loadingPlanBallastDetailsOpt
   * @return
   */
  private void setBallasTankValues(
      Optional<LoadingPlanBallastDetails> loadingPlanBallastDetailsOpt,
      TankCargoDetails tankCargoDetails) {
    if (loadingPlanBallastDetailsOpt.isPresent()) {
      Optional.ofNullable(loadingPlanBallastDetailsOpt.get().getQuantityMT())
          .ifPresent(i -> tankCargoDetails.setQuantity(Double.parseDouble(i)));
      Optional.ofNullable(loadingPlanBallastDetailsOpt.get().getColorCode())
          .ifPresent(tankCargoDetails::setColorCode);
    } else {
      tankCargoDetails.setQuantity(0.0);
      tankCargoDetails.setColorCode("");
    }
    tankCargoDetails.setCargoName("");
    tankCargoDetails.setUllage("0");
    tankCargoDetails.setFillingRatio("0");
  }

  /**
   * Cargo Tank and quantity details w.r.t condition type
   *
   * @param vesselCondition
   * @param requestPayload
   * @param conditionType
   * @throws GenericServiceException
   */
  private void setCargoTanksWithQuantityForReport(
      ArrivalDeparcherCondition vesselCondition,
      LoadingPlanResponse requestPayload,
      Integer conditionType)
      throws GenericServiceException {
    setStabilityParamForReport(
        vesselCondition, requestPayload.getPlanStabilityParams(), conditionType);

    List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList =
        requestPayload
            .getLoadingInformation()
            .getCargoVesselTankDetails()
            .getLoadableQuantityCargoDetails();
    List<TankCargoDetails> tankCargoDetailsList = new ArrayList<>();
    for (List<VesselTank> tankGroup : requestPayload.getCargoTanks()) {
      tankGroup.forEach(
          item -> {
            Optional<LoadingPlanStowageDetails> loadingPlanStowageDetails =
                requestPayload.getPlanStowageDetails().stream()
                    .filter(
                        psd ->
                            psd.getTankId().equals(item.getId())
                                && psd.getConditionType().equals(conditionType)
                                && psd.getValueType().equals(2))
                    .findAny();
            TankCargoDetails tankCargoDetails = new TankCargoDetails();
            tankCargoDetails.setTankName(item.getShortName());
            tankCargoDetails.setId(item.getId());
            // Adding cargo details in this tank
            setCargoTankValues(
                loadingPlanStowageDetails,
                loadableQuantityCargoDetailsList,
                tankCargoDetails,
                item.getFullCapacityCubm());
            tankCargoDetailsList.add(tankCargoDetails);
          });
    }
    // Getting all tank list for later
    if (cargoTanks == null) {
      cargoTanks = tankCargoDetailsList;
    }
    vesselCondition.setCargoTopTanks(getTankAgainstPossision(tankCargoDetailsList, "P"));
    vesselCondition.setCargoCenterTanks(getTankAgainstPossision(tankCargoDetailsList, "C"));
    vesselCondition.setCargoBottomTanks(getTankAgainstPossision(tankCargoDetailsList, "S"));
  }

  /** Populates cargo values in to each tank */
  private void setCargoTankValues(
      Optional<LoadingPlanStowageDetails> loadingPlanStowageDetails,
      List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList,
      TankCargoDetails tankCargoDetails,
      String tankFullCapacity) {

    if (loadingPlanStowageDetails.isPresent()) {
      Optional.ofNullable(loadingPlanStowageDetails.get().getQuantityMT())
          .ifPresent(i -> tankCargoDetails.setQuantity(Double.parseDouble(i)));
      Optional.ofNullable(loadingPlanStowageDetails.get().getApi())
          .ifPresent(tankCargoDetails::setApi);
      Optional.ofNullable(loadingPlanStowageDetails.get().getTemperature())
          .ifPresent(tankCargoDetails::setTemperature);
      Optional.ofNullable(loadingPlanStowageDetails.get().getUllage())
          .ifPresent(tankCargoDetails::setUllage);

      if (tankCargoDetails.getQuantity() > 0
          && tankCargoDetails.getTemperature() != null
          && !tankCargoDetails.getTemperature().isBlank()
          && tankCargoDetails.getApi() != null
          && !tankCargoDetails.getApi().isBlank()) {
        tankCargoDetails.setFillingRatio(
            getFillingRatio(
                tankCargoDetails.getQuantity(),
                tankCargoDetails.getApi(),
                tankCargoDetails.getTemperature(),
                tankFullCapacity));
      } else {
        tankCargoDetails.setFillingRatio("0");
      }
      if (!loadingPlanStowageDetails.get().getColorCode().isEmpty()
          && !loadingPlanStowageDetails.get().getAbbreviation().isEmpty()) {
        Optional.ofNullable(loadingPlanStowageDetails.get().getColorCode())
            .ifPresent(tankCargoDetails::setColorCode);
        Optional.ofNullable(loadingPlanStowageDetails.get().getAbbreviation())
            .ifPresent(tankCargoDetails::setCargoName);
      } else {
        Optional<LoadableQuantityCargoDetails> loadableQuantityCargoDetails =
            loadableQuantityCargoDetailsList.stream()
                .filter(
                    i ->
                        i.getCargoNominationId()
                            .equals(loadingPlanStowageDetails.get().getCargoNominationId()))
                .findFirst();
        if (loadableQuantityCargoDetails.isPresent()) {
          Optional.ofNullable(loadableQuantityCargoDetails.get().getColorCode())
              .ifPresent(tankCargoDetails::setColorCode);
          Optional.ofNullable(loadableQuantityCargoDetails.get().getCargoAbbreviation())
              .ifPresent(tankCargoDetails::setCargoName);
        }
      }

    } else {
      tankCargoDetails.setQuantity(0.0);
      tankCargoDetails.setCargoName("");
      tankCargoDetails.setColorCode("");
      tankCargoDetails.setUllage("0");
      tankCargoDetails.setFillingRatio("0");
    }
  }

  /**
   * Calculate filling ratio Convert MT to BBLS first then Convert it to OBSKL Divide quantity in
   * OBSKL with Maximum tank capacity to find ratio
   *
   * @param tankFullCapacity
   */
  private String getFillingRatio(
      Double quantityMT, String api, String temperature, String tankFullCapacity) {
    if (Double.parseDouble(api) > 0 && Double.parseDouble(temperature) > 0) {
      Double quantityBBLS =
          UnitConversionUtility.convertToBBLS(
              UnitTypes.MT, Double.parseDouble(api), Double.parseDouble(temperature), quantityMT);
      Double quantityOBSKL =
          UnitConversionUtility.convertFromBBLS(
              UnitTypes.OBSKL,
              Double.parseDouble(api),
              Double.parseDouble(temperature),
              quantityBBLS);
      return String.format("%.2f", (quantityOBSKL / Double.parseDouble(tankFullCapacity)) * 100);
    }
    return "0";
  }

  /**
   * Returns list of tank w.r.t position
   *
   * @param tankDetailsList
   * @param position
   * @return
   */
  private TankRow getTankAgainstPossision(List<TankCargoDetails> tankDetailsList, String position) {
    TankRow tankRow = new TankRow();
    List<TankCargoDetails> tempList =
        tankDetailsList.stream()
            .filter(item -> item.getTankName().endsWith(position))
            .collect(Collectors.toList());
    tankRow.setTank(tempList);
    return tankRow;
  }

  /**
   * Stability params w.r.t condition type
   *
   * @param vesselCondition
   * @param planStabilityParams
   * @param conditionType
   */
  private void setStabilityParamForReport(
      ArrivalDeparcherCondition vesselCondition,
      List<LoadingPlanStabilityParam> planStabilityParams,
      Integer conditionType) {
    Optional<LoadingPlanStabilityParam> stabilityParam =
        planStabilityParams.stream()
            .filter(item -> item.getConditionType().equals(conditionType))
            .findAny();
    if (stabilityParam.isPresent()) {
      Optional.ofNullable(stabilityParam.get().getAftDraft()).ifPresent(vesselCondition::setDraftA);
      Optional.ofNullable(stabilityParam.get().getForeDraft())
          .ifPresent(vesselCondition::setDraftA);
      Optional.ofNullable(stabilityParam.get().getTrim()).ifPresent(vesselCondition::setTrim);
    }
  }
}
