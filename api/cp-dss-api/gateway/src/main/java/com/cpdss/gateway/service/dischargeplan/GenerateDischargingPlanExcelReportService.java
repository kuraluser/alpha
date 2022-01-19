/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static com.cpdss.common.constants.FileRepoConstants.*;

import com.cpdss.common.domain.FileRepoReply;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;
import com.cpdss.gateway.domain.LoadLine;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.dischargeplan.CargoForCowDetails;
import com.cpdss.gateway.domain.dischargeplan.CargoPumpDetailsForSequence;
import com.cpdss.gateway.domain.dischargeplan.CowPlan;
import com.cpdss.gateway.domain.dischargeplan.CowPlanForExcel;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargePlanResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionGroup;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionSubHeader;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructions;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanExcelDetails;
import com.cpdss.gateway.domain.dischargeplan.DischargingPlanExcelDischargingSequenceDetails;
import com.cpdss.gateway.domain.dischargeplan.TanksWashedWithCargo;
import com.cpdss.gateway.domain.loadingplan.ArrivalDeparcherCondition;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.BerthInformation;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.CargoQuantity;
import com.cpdss.gateway.domain.loadingplan.CargoTobeLoaded;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionForExcel;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingInstructionDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingPlanDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanStabilityParam;
import com.cpdss.gateway.domain.loadingplan.TankCargoDetails;
import com.cpdss.gateway.domain.loadingplan.TankRow;
import com.cpdss.gateway.domain.loadingplan.VesselParticularsForExcel;
import com.cpdss.gateway.domain.loadingplan.sequence.Ballast;
import com.cpdss.gateway.domain.loadingplan.sequence.BallastPump;
import com.cpdss.gateway.domain.loadingplan.sequence.Cargo;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoLoadingRate;
import com.cpdss.gateway.domain.loadingplan.sequence.CleaningTank;
import com.cpdss.gateway.domain.loadingplan.sequence.CowTankDetail;
import com.cpdss.gateway.domain.loadingplan.sequence.DriveTank;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingRateForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.QuantityLoadingStatus;
import com.cpdss.gateway.domain.loadingplan.sequence.ShearingForce;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParamsOfLoadingSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategory;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategoryForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankWithSequenceDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.FileRepoService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.utility.ExcelExportUtility;
import com.cpdss.gateway.utility.UnitConversionUtility;
import com.cpdss.gateway.utility.UnitTypes;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** @author sanalkumar.k */
@Slf4j
@Service
public class GenerateDischargingPlanExcelReportService {
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final String YES = "Yes";
  public static final String NO = "No";
  public static final String ZERO_VALUE = "0.0";

  public String SUB_FOLDER_NAME = "/reports/discharging";
  public String TEMPLATES_FILE_LOCATION =
      "/reports/discharging/Vessel_{type}_Discharging_Plan_Template.xlsx";
  public String OUTPUT_FILE_LOCATION =
      "/reports/discharging/Vessel_{id}_Discharging_Plan_{voy}_{port}.xlsx";
  public String TEMP_LOCATION = "temp_discharging.xlsx";
  public final Integer START_ROW = 14;
  public final Integer END_ROW = 69;
  public final Integer END_COLUMN = 25;
  public VesselParticularsForExcel vesselParticular = null;
  public String SHEET_NAMES[] = {"CRUD - 021 pg1", "CRUD - 021 pg2", "CRUD - 021 pg3"};
  public Long INSTRUCTION_ORDER[] = {1L, 2L, 3L, 4L, 5L};
  public Long CARGO_PUMP_IDS[] = {1L, 2L, 3L, 6L, 29L};
  public List<TankCargoDetails> cargoTanks = null;
  public List<TankCargoDetails> ballastTanks = null;
  public CargoMachineryInUse cargoMachinery = null;
  public String cargoNames = null;
  public final String UFPT = "UFPT";
  public final String LFPT = "LFPT";
  public final Long GS_PUMP_ID = 3L;
  public final String GS_PUMP_COLOR_CODE = "#ac6ffc";
  public final Long IGS_PUMP_ID = 4L;
  public final String IGS_PUMP_COLOR_CODE = "#f274c0";
  public final Long STRIPPING_PUMP_ID = 5L;
  public final String STRIPPING_PUMP_COLOR_CODE = "#fcc986";
  public final Long BALLAST_PUMP_ID = 2L;
  public final Long CARGO_PUMP_ID = 1L;
  public final String CARGO_PUMP_COLOR_CODE = "#ea8343";
  public final String DEFAULT_PUMP_COLOR_CODE = "#7099c2";
  public Long BALLAST_PUMP_IDS[] = {GS_PUMP_ID, IGS_PUMP_ID, STRIPPING_PUMP_ID};
  public final String COW_FULL = "*FULL*";
  public String COW_FULL_ICON = "/reports/discharging/full_wash_icon.png";
  public final String COW_TOP = "*TOP*";
  public String COW_TOP_ICON = "/reports/discharging/top_wash_icon.png";
  public final String COW_BOTTOM = "*BOTTOM*";
  public String COW_BOTTOM_ICON = "/reports/discharging/bottom_wash_icon.png";

  public String voyageDate = null;

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  @Autowired DischargeInformationService dischargeInformationService;
  @Autowired ExcelExportUtility excelExportUtil;
  @Autowired private VesselInfoService vesselInfoService;
  @Autowired private DischargingInstructionService dischargingInstructionService;
  @Autowired LoadingPlanGrpcService loadingPlanGrpcService;
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
  public byte[] generateDischargingPlanExcel(
      DischargePlanResponse requestPayload,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId,
      Boolean downloadRequired)
      throws Exception {
    log.info("Generating Discharging plan excel for Vessel {}", vesselId);

    // Setting file name of input file based on vessel type
    TEMPLATES_FILE_LOCATION = getLoadingPlanTemplateForVessel(vesselId);

    // Building data required for Loading plan Excel
    DischargingPlanExcelDetails loadinPlanExcelDetails =
        getDataForExcel(requestPayload, vesselId, voyageId, infoId, portRotationId);
    log.info("Data model ready for stamping : " + loadinPlanExcelDetails.toString());

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
        // Adding password protection
        workbook.write(outFile);
        // GenerateProtectedFile.setPasswordToWorkbook(
        // workbook, loadinPlanExcelDetails.getSheetOne().getVoyageNumber(), voyageDate,
        // outFile);
        // resultFileStream.close();

        // Putting entry in file repo
        FileRepoReply reply =
            FileRepoService.addFileToRepo(
                null,
                loadinPlanExcelDetails.getSheetOne().getVoyageNumber(),
                actualFileName.split("/")[actualFileName.split("/").length - 1],
                SUB_FOLDER_NAME + "/",
                FileRepoSection.DISCHARGE_PLAN,
                "Process",
                null,
                vesselId,
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
  private void setCellStyle(XSSFWorkbook workbook, DischargingPlanExcelDetails data) {
    XSSFSheet sheet1 = workbook.getSheet(SHEET_NAMES[0]);
    XSSFSheet sheet3 = workbook.getSheet(SHEET_NAMES[2]);
    styleSheetOne(workbook, sheet1, data.getSheetOne());
    styleSheetThree(workbook, sheet3, data.getSheetThree());
  }

  /** Set color in sheet three tanks and sequence cells */
  private void styleSheetThree(
      XSSFWorkbook workbook, XSSFSheet sheet, DischargingPlanExcelDischargingSequenceDetails data) {
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
    for (row = 6; row <= 86; row++) {
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
    if (data.getCargoTobeDischarged() != null && !data.getCargoTobeDischarged().isEmpty()) {
      for (col = 5; col <= 5 + (data.getCargoTobeDischarged().size() * 5); col += 5) {
        row = 61;
        cell = sheet.getRow(row).getCell(col);
        setCargoColor(workbook, sheet, cell, null, data.getCargoTobeDischarged(), null);
      }
    }
  }

  /**
   * Color cargo name cells
   *
   * @throws GenericServiceException
   */
  private void setCargoColor(
      XSSFWorkbook workbook,
      XSSFSheet sheet,
      XSSFCell cell,
      List<CargoQuantity> cargoDetails,
      List<CargoTobeLoaded> cargoTobeDischarged,
      List<TankCategoryForSequence> cargoListSequence) {
    String cellValue = getCellValue(cell);
    if (cellValue != null && !cellValue.isBlank()) {
      if (cargoDetails != null) {
        Optional<CargoQuantity> opt =
            cargoDetails.stream().filter(item -> cellValue.equals(item.getCargoName())).findFirst();
        if (opt.isPresent()) {
          fillColor(workbook, cell, opt.get().getColorCode());
        }
      } else if (cargoTobeDischarged != null) {
        Optional<CargoTobeLoaded> opt =
            cargoTobeDischarged.stream()
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
          fillColor(workbook, cell, cellValue.split(" ")[0]);
        }
        if (cellValue.contains("*")) {
          drawCow(workbook, cell, sheet, cellValue.split(" ")[1]);
        }
        if (cellValue.contains("*") || cellValue.contains("#")) {
          cell.setCellValue("");
        }
      }
    }
  }

  /**
   * Method to insert Cow icon in to excel cell
   *
   * @param workbook
   * @param cell
   * @param sheet
   * @param type
   * @throws GenericServiceException
   */
  private void drawCow(XSSFWorkbook workbook, XSSFCell cell, XSSFSheet sheet, String type) {
    try {
      InputStream inputStream = null;
      if (type.equals(COW_FULL)) {
        inputStream = this.getClass().getResourceAsStream(COW_FULL_ICON);
      } else if (type.equals(COW_TOP)) {
        inputStream = this.getClass().getResourceAsStream(COW_TOP_ICON);
      } else if (type.equals(COW_BOTTOM)) {
        inputStream = this.getClass().getResourceAsStream(COW_BOTTOM_ICON);
      }
      if (inputStream != null) {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        // Adds a picture to the workbook
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

        // Returns an object that handles instantiating concrete classes
        XSSFCreationHelper helper = workbook.getCreationHelper();
        // Creates the top-level drawing patriarch.
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        // Create an anchor that is attached to the worksheet
        XSSFClientAnchor anchor = helper.createClientAnchor();
        // create an anchor with upper left cell _and_ bottom right cell
        anchor.setCol1(cell.getColumnIndex());
        anchor.setRow1(cell.getRowIndex());
        anchor.setCol2(cell.getColumnIndex());
        anchor.setRow2(cell.getRowIndex());
        // Creates a picture
        XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
        // Reset the image to the original size
        pict.resize();
        inputStream.close();
      } else {
        log.info("COW icons missing - skipping cow plan rendering");
      }

    } catch (IOException ioex) {
      ioex.printStackTrace();
      log.info("Generating excel failed ,Rendering cow Icons failed");
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
    Color color = Color.decode(colorCode.toUpperCase());
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
  private DischargingPlanExcelDetails getDataForExcel(
      DischargePlanResponse requestPayload,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId)
      throws GenericServiceException, InterruptedException, ExecutionException, ParseException {
    log.info("Building details for excel sheetwise ");
    DischargingPlanExcelDetails excelData = new DischargingPlanExcelDetails();
    if (requestPayload == null) {
      // Calling discharging plan details service
      requestPayload =
          dischargeInformationService.getDischargingPlan(
              vesselId, voyageId, infoId, portRotationId, null);
    }
    voyageDate = requestPayload.getVoyageDate();
    // Get a list of all ballast tanks for sheet3
    getAllBallastTanks(
        requestPayload.getBallastFrontTanks(),
        requestPayload.getBallastCenterTanks(),
        requestPayload.getBallastRearTanks());
    // Get machinery in use for sheet 2
    cargoMachinery = requestPayload.getDischargingInformation().getMachineryInUses();

    excelData.setSheetOne(
        buildSheetOne(requestPayload, vesselId, voyageId, infoId, portRotationId));
    List<VesselTank> tanks = new ArrayList<>();
    requestPayload.getCargoTanks().stream()
        .forEach(
            item -> {
              item.stream().forEach(tank -> tanks.add(tank));
            });
    excelData.setSheetTwo(
        buildSheetTwo(
            vesselId,
            voyageId,
            infoId,
            portRotationId,
            requestPayload.getDischargingInformation(),
            tanks));
    excelData.setSheetThree(buildSheetThree(vesselId, voyageId, infoId, portRotationId));
    return excelData;
  }

  /**
   * Preparing sheet 2
   *
   * @param cowPlan
   * @param tanks
   */
  private LoadingPlanExcelLoadingInstructionDetails buildSheetTwo(
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId,
      DischargeInformation dischargeInformation,
      List<VesselTank> tanks)
      throws GenericServiceException {
    log.info("Building sheet 2 : Discharging instructions");
    // Calling discharging plan get instructions details service
    DischargingInstructionResponse dischargingInstructionResponse =
        dischargingInstructionService.getDischargingInstructions(vesselId, infoId, portRotationId);
    LoadingPlanExcelLoadingInstructionDetails sheetTwo =
        new LoadingPlanExcelLoadingInstructionDetails();
    sheetTwo.setInstructions(getInstructions(dischargingInstructionResponse));
    sheetTwo.setVesselPurticulars(getVesselPurticulars(vesselId));
    if (cargoMachinery != null) {
      sheetTwo.setCargoPump(
          cargoMachinery.getVesselPumps().stream()
              .filter(item -> item.getPumpTypeId().equals(CARGO_PUMP_ID))
              .map(item -> item.getPumpName())
              .collect(Collectors.joining(",")));
      sheetTwo.setCargoPumpCount(
          String.valueOf(
              cargoMachinery.getVesselPumps().stream()
                  .filter(item -> item.getPumpTypeId().equals(CARGO_PUMP_ID))
                  .count()));
      sheetTwo.setBallastPumpCount(
          cargoMachinery.getVesselPumps().stream()
              .filter(item -> item.getPumpTypeId().equals(BALLAST_PUMP_ID))
              .count());
      sheetTwo.setBallastPump(sheetTwo.getBallastPumpCount() > 0 ? YES : NO);
      sheetTwo.setStrippingPumpCount(
          cargoMachinery.getVesselPumps().stream()
              .filter(item -> item.getPumpTypeId().equals(STRIPPING_PUMP_ID))
              .count());
      sheetTwo.setStrippingPump(sheetTwo.getStrippingPumpCount() > 0 ? YES : NO);
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
    CowPlan cowPlan = dischargeInformation.getCowPlan();
    CowPlanForExcel cowPlanObj = new CowPlanForExcel();
    if (cowPlan != null) {
      cowPlanObj.setAllCow(
          tanks.stream()
              .filter(item -> cowPlan.getAllCow().contains(item.getId()))
              .map(item -> item.getShortName())
              .collect(Collectors.joining(",")));
      cowPlanObj.setAllCowCount(cowPlan.getAllCow().size());
      cowPlanObj.setTopCow(
          tanks.stream()
              .filter(item -> cowPlan.getTopCow().contains(item.getId()))
              .map(item -> item.getShortName())
              .collect(Collectors.joining(",")));
      cowPlanObj.setTopCowCount(cowPlan.getTopCow().size());
      cowPlanObj.setBottomCow(
          tanks.stream()
              .filter(item -> cowPlan.getBottomCow().contains(item.getId()))
              .map(item -> item.getShortName())
              .collect(Collectors.joining(",")));
      cowPlanObj.setBottomCowCount(cowPlan.getBottomCow().size());
      cowPlanObj.setCowStart(cowPlan.getCowStart());
      cowPlanObj.setCowFinish(cowPlan.getCowEnd());
      cowPlanObj.setCowHours(
          (cowPlan.getCowDuration() != null && !cowPlan.getCowDuration().isEmpty())
              ? String.valueOf(Double.parseDouble(cowPlan.getCowDuration()) / 60)
              : "");
      List<TanksWashedWithCargo> tanksWashedWithCargoList = new ArrayList<>();
      if (cowPlan.getCargoCow() != null) {
        for (CargoForCowDetails cargoCow : cowPlan.getCargoCow()) {
          TanksWashedWithCargo tanksWashedWithDifCargo = new TanksWashedWithCargo();
          tanksWashedWithDifCargo.setTankName(
              tanks.stream()
                  .filter(item -> cargoCow.getTankIds().contains(item.getId()))
                  .map(item -> item.getShortName())
                  .collect(Collectors.joining(",")));
          Optional<DischargeQuantityCargoDetails> loadedCargo =
              dischargeInformation.getCargoVesselTankDetails().getDischargeQuantityCargoDetails()
                  .stream()
                  .filter(
                      item ->
                          cargoCow
                              .getCargoNominationId()
                              .equals(item.getDischargeCargoNominationId()))
                  .findFirst();
          tanksWashedWithDifCargo.setLoadedCargoName(
              loadedCargo.isPresent() ? loadedCargo.get().getCargoAbbreviation() : "");
          Optional<DischargeQuantityCargoDetails> washedCargo =
              dischargeInformation.getCargoVesselTankDetails().getDischargeQuantityCargoDetails()
                  .stream()
                  .filter(
                      item ->
                          cargoCow
                              .getWashingCargoNominationId()
                              .equals(item.getDischargeCargoNominationId()))
                  .findFirst();
          tanksWashedWithDifCargo.setWashingCargoName(
              washedCargo.isPresent() ? washedCargo.get().getCargoAbbreviation() : "");
          tanksWashedWithCargoList.add(tanksWashedWithDifCargo);
        }
      } else {
        TanksWashedWithCargo tanksWashedWithDifCargo = new TanksWashedWithCargo();
        // Setting empty value to preserve excel alignment
        tanksWashedWithDifCargo.setLoadedCargoName("");
        tanksWashedWithDifCargo.setTankName("");
        tanksWashedWithDifCargo.setWashingCargoName("");
        tanksWashedWithCargoList.add(tanksWashedWithDifCargo);
      }
      cowPlanObj.setTanksWashedWithDifCargo(tanksWashedWithCargoList);
    }
    sheetTwo.setCowPlan(cowPlanObj);
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
      DischargingInstructionResponse loadingSequenceResponse) {
    List<LoadingInstructionForExcel> instructionsList = new ArrayList<>();
    int group = 0;
    int groupHeading = 8;
    for (Long headerId : INSTRUCTION_ORDER) {
      Optional<DischargingInstructionGroup> ligOpt =
          loadingSequenceResponse.getDischargingInstructionGroupList().stream()
              .filter(value -> value.getGroupId().equals(headerId))
              .findAny();

      String heading = ligOpt.isEmpty() ? "" : ligOpt.get().getGroupName();

      List<DischargingInstructionSubHeader> listUnderHeader =
          loadingSequenceResponse.getDischargingInstructionSubHeader().stream()
              .filter(item -> item.getInstructionHeaderId().equals(headerId) && item.getIsChecked())
              .collect(Collectors.toList());
      int count = 1;
      if (!listUnderHeader.isEmpty()) {
        for (DischargingInstructionSubHeader item : listUnderHeader) {
          LoadingInstructionForExcel instructionObj = new LoadingInstructionForExcel();
          instructionObj.setHeading(groupHeading + "   " + heading);
          instructionObj.setGroup(group);
          instructionObj.setInstruction(count + ".  " + item.getSubHeaderName());
          instructionsList.add(instructionObj);
          count++;
          if (!item.getIsSingleHeader() && item.getDischargingInstructionsList().size() > 0) {
            int childCount = 1;
            for (DischargingInstructions subItem : item.getDischargingInstructionsList()) {
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
        instructionObj.setHeading(groupHeading + "   " + heading);
        instructionObj.setGroup(group);
        instructionObj.setInstruction("**No instructions available under this section**");
        instructionsList.add(instructionObj);
      }
      group++;
      groupHeading++;
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
  private DischargingPlanExcelDischargingSequenceDetails buildSheetThree(
      Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException {
    log.info("Building sheet 3 : Discharging sequence chart");
    // Calling Discharging plan get sequence details service
    LoadingSequenceResponse dischargingSequenceResponse =
        dischargeInformationService.getDischargingSequence(vesselId, voyageId, infoId);
    DischargingPlanExcelDischargingSequenceDetails sheetThree =
        new DischargingPlanExcelDischargingSequenceDetails();
    // Calculating no:of stages in sequence
    sheetThree.setTickPoints(
        getTickPoints(
            dischargingSequenceResponse.getMinXAxisValue(),
            dischargingSequenceResponse.getStageTickPositions()));
    log.info("Sequence identified with {} Sections ", sheetThree.getTickPoints().size());
    if (sheetThree.getTickPoints().size() > 0) {
      // Getting all tanks present in sequence
      sheetThree.setCargoTanks(
          getCargoTanks(
              dischargingSequenceResponse.getAllCargoTankCategories(),
              dischargingSequenceResponse.getCargos()));
      if (sheetThree.getCargoTanks().size() > 0) {
        // Getting ullage mapped against each tank if present
        getCargoTankUllageAndQuantity(
            dischargingSequenceResponse.getStageTickPositions(),
            dischargingSequenceResponse.getCargos(),
            sheetThree.getCargoTanks(),
            dischargingSequenceResponse.getCleaningTanks());
        sheetThree.setDischargingRates(
            getDischargingRate(
                dischargingSequenceResponse.getCargoDischargingRates(),
                dischargingSequenceResponse.getStageTickPositions()));
        sheetThree.setDriveOilTanks(
            getDriveOilTanks(
                dischargingSequenceResponse.getStageTickPositions(),
                dischargingSequenceResponse.getCargoTankCategories(),
                dischargingSequenceResponse.getDriveTanks()));
        sheetThree.setTankToTanks(null); // TODO
      }
      sheetThree.setBallastTanks(
          getBallastTanks(
              dischargingSequenceResponse.getAllBallastTankCategories(),
              dischargingSequenceResponse.getBallasts()));
      if (sheetThree.getBallastTanks().size() > 0) {
        getBallastTankUllageAndQuantity(
            dischargingSequenceResponse.getStageTickPositions(),
            dischargingSequenceResponse.getBallasts(),
            sheetThree.getBallastTanks());
      }
      sheetThree.setCargoPumps(
          getCargoPumpDetails(
              dischargingSequenceResponse.getStageTickPositions(),
              dischargingSequenceResponse.getCargoPumps(),
              Arrays.asList(CARGO_PUMP_IDS)));
      sheetThree.setBallastPumps(
          getCargoPumpDetails(
              dischargingSequenceResponse.getStageTickPositions(),
              dischargingSequenceResponse.getBallastPumps(),
              Arrays.asList(BALLAST_PUMP_IDS)));
      sheetThree.setStabilityParams(
          getStabilityParams(
              dischargingSequenceResponse.getStabilityParams(), sheetThree.getTickPoints().size()));
    }
    log.info("Building sheet 3 : completed");
    return sheetThree;
  }

  /**
   * Drive oil tank details
   *
   * @param stageTickPositions
   * @param cargoTankCategories
   * @param driveTanks
   * @return
   */
  private List<String> getDriveOilTanks(
      Set<Long> stageTickPositions,
      List<TankCategory> cargoTankCategories,
      List<DriveTank> driveTanks) {
    List<String> driveOilTanks = new ArrayList<>();
    List<Long> positions = getListFromSet(stageTickPositions);
    IntStream.range(0, positions.size() - 1)
        .forEach(
            i -> {
              driveOilTanks.add("");
            });
    for (DriveTank tank : driveTanks) {
      for (int i = 0; i < positions.size() - 1; i++) {
        if (tank.getStart() >= positions.get(i)) {
          driveOilTanks.set(
              i,
              driveOilTanks.get(i).isBlank()
                  ? tank.getTankName()
                  : driveOilTanks.get(i) + "," + tank.getTankName());
        } else if (tank.getStart() < positions.get(i) && tank.getEnd() >= positions.get(i + 1)) {
          driveOilTanks.set(
              i,
              driveOilTanks.get(i).isBlank()
                  ? tank.getTankName()
                  : driveOilTanks.get(i) + "," + tank.getTankName());
        }
      }
    }
    return driveOilTanks;
  }

  /**
   * Cargo Pump details
   *
   * @param stageTickPositions
   * @param list
   * @param cargoPumps
   */
  private List<CargoPumpDetailsForSequence> getCargoPumpDetails(
      Set<Long> stageTickPositions, List<BallastPump> cargoPumps, List<Long> pumpIds) {
    List<Long> positionList = getListFromSet(stageTickPositions);
    List<CargoPumpDetailsForSequence> cargoPumpDetailsList = new ArrayList<>();
    // Getting ullages on each tick position of each tank
    for (Long pumpId : pumpIds) {
      CargoPumpDetailsForSequence cargoPumpObj = new CargoPumpDetailsForSequence();
      TankWithSequenceDetails cargoPumpDetailsObj = new TankWithSequenceDetails();
      cargoPumpDetailsObj.setTankId(pumpId);
      cargoPumpDetailsObj.setUllage(new ArrayList<>());
      cargoPumpDetailsObj.setStatus(new ArrayList<>());
      List<BallastPump> pumpList =
          cargoPumps.stream()
              .filter(item -> item.getPumpId().equals(pumpId))
              .collect(Collectors.toList());
      if (!pumpList.isEmpty()) {
        List<String> rates = new ArrayList<>();
        List<QuantityLoadingStatus> pumpStatusList = new ArrayList<>();
        IntStream.range(0, positionList.size())
            .forEach(
                i -> {
                  QuantityLoadingStatus quantityLoadingStatus = new QuantityLoadingStatus();
                  pumpStatusList.add(quantityLoadingStatus);
                });
        Optional<BallastPump> pumpMatch = Optional.empty();
        // checking if 0th position have a mapping
        pumpMatch =
            pumpList.stream()
                .filter(
                    pumpItem ->
                        pumpItem.getStart().equals(positionList.get(0))
                            && pumpItem.getEnd().equals(positionList.get(0)))
                .findFirst();
        setCargoPumpDetails(pumpMatch, rates, pumpStatusList.get(0));
        for (BallastPump pump : pumpList) {
          for (int i = 1; i < positionList.size(); i++) {
            if (pump.getStart() <= positionList.get(i) && pump.getEnd() >= positionList.get(i)) {
              setCargoPumpDetails(Optional.of(pump), rates, pumpStatusList.get(i));
            }
          }
        }
        cargoPumpDetailsObj.setUllage(rates);
        cargoPumpDetailsObj.setStatus(pumpStatusList);
      } else {
        // setting empty values for look and feel
        IntStream.range(0, stageTickPositions.size())
            .forEach(i -> cargoPumpDetailsObj.getUllage().add(""));
        IntStream.range(0, stageTickPositions.size())
            .forEach(
                i ->
                    cargoPumpDetailsObj
                        .getStatus()
                        .add(new QuantityLoadingStatus(false, "", "", false, "")));
      }
      cargoPumpObj.setCargoTankUllage(cargoPumpDetailsObj);
      cargoPumpDetailsList.add(cargoPumpObj);
    }
    return cargoPumpDetailsList;
  }

  /**
   * Method to set cargo pump data against a tick position
   *
   * @param cargoMatch
   * @param ullages
   * @param quantityStatusList
   */
  private void setCargoPumpDetails(
      Optional<BallastPump> pumpMatch, List<String> rates, QuantityLoadingStatus pumpStatus) {
    if (pumpMatch.isPresent()) {
      if (pumpMatch.get().getQuantityM3() != null && pumpMatch.get().getRate() != null) {
        rates.add(pumpMatch.get().getRate().toString());
        pumpStatus.setPresent(true);
        pumpStatus.setColorCode(getPumpColourCode(pumpMatch.get().getPumpId()));
      } else {
        pumpStatus.setPresent(false);
      }
    } else {
      rates.add("");
      pumpStatus.setPresent(false);
    }
  }

  private String getPumpColourCode(Long pumpId) {
    return pumpId.equals(GS_PUMP_ID)
        ? GS_PUMP_COLOR_CODE
        : pumpId.equals(IGS_PUMP_ID)
            ? IGS_PUMP_COLOR_CODE
            : pumpId.equals(STRIPPING_PUMP_ID)
                ? STRIPPING_PUMP_COLOR_CODE
                : DEFAULT_PUMP_COLOR_CODE;
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
   * @param cleaningTank
   * @param loadingSequenceResponse
   * @param tankCategories
   * @return
   */
  private void getCargoTankUllageAndQuantity(
      Set<Long> stageTickPositions,
      List<Cargo> cargos,
      List<TankCategoryForSequence> tankList,
      CleaningTank cleaningTank) {
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
        // Finding COW detils of present tank
        CleaningTank cleaningTankOfPresentTank = null;
        if (cleaningTank != null) {
          cleaningTankOfPresentTank = new CleaningTank();
          cleaningTankOfPresentTank.setFullTanks(
              cleaningTank.getFullTanks().stream()
                  .filter(cowTank -> cowTank.getTankId().equals(tank.getId()))
                  .collect(Collectors.toList()));
          cleaningTankOfPresentTank.setTopTanks(
              cleaningTank.getTopTanks().stream()
                  .filter(cowTank -> cowTank.getTankId().equals(tank.getId()))
                  .collect(Collectors.toList()));
          cleaningTankOfPresentTank.setBottomTanks(
              cleaningTank.getBottomTanks().stream()
                  .filter(cowTank -> cowTank.getTankId().equals(tank.getId()))
                  .collect(Collectors.toList()));
        }
        if (positionList.size() > 0) {
          // Finding ullage and values in first tick position
          cargoMatch =
              cargoListOfpresentTank.stream()
                  .filter(
                      cargo ->
                          cargo.getStart().equals(positionList.get(0))
                              && cargo.getEnd().equals(positionList.get(0)))
                  .findFirst();

          setUllageAndQuantityCargo(
              cargoMatch,
              ullages,
              quantityStatusList,
              positionList.get(0),
              positionList.get(0),
              cleaningTankOfPresentTank);
        }

        // Finding ullage in each tick position
        for (int i = 1; i < positionList.size() - 1; i++) {
          // avoiding last tick position since this is not needed for sequence
          Long position = positionList.get(i);
          cargoMatch =
              cargoListOfpresentTank.stream()
                  .filter(cargo -> cargo.getStart().equals(position) && cargo.getEnd() > position)
                  .findFirst();
          if (cargoMatch.isEmpty()) {
            cargoMatch =
                cargoListOfpresentTank.stream()
                    .filter(cargo -> cargo.getStart() < position && cargo.getEnd() > position)
                    .findFirst();
          }
          setUllageAndQuantityCargo(
              cargoMatch,
              ullages,
              quantityStatusList,
              position,
              positionList.get(i + 1),
              cleaningTankOfPresentTank);
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

  /**
   * Method to set cow data against a tick position
   *
   * @param end
   */
  private void setCowPlanStatus(
      QuantityLoadingStatus dischargingStatus, Long start, Long end, CleaningTank cleaningTank) {
    if (getCowTankDetailForTank(cleaningTank.getFullTanks(), start, end)) {
      dischargingStatus.setCowType(COW_FULL);
    } else if (getCowTankDetailForTank(cleaningTank.getTopTanks(), start, end)) {
      dischargingStatus.setCowType(COW_TOP);
    } else if (getCowTankDetailForTank(cleaningTank.getBottomTanks(), start, end)) {
      dischargingStatus.setCowType(COW_BOTTOM);
    }
    if (dischargingStatus.getCowType() != null) {
      dischargingStatus.setCowWashStatus(true);
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
   * @param cleaningTank
   * @param position
   * @param position
   * @param long1
   * @param cleaningTank
   */
  private void setUllageAndQuantityCargo(
      Optional<Cargo> cargoMatch,
      List<String> ullages,
      List<QuantityLoadingStatus> quantityStatusList,
      Long start,
      Long end,
      CleaningTank cleaningTank) {
    QuantityLoadingStatus dischargingStatus = new QuantityLoadingStatus();
    if (cargoMatch.isPresent()) {
      ullages.add(UnitConversionUtility.setPrecision(cargoMatch.get().getUllage(), 3));
      if (cargoMatch.get().getQuantity().compareTo(BigDecimal.ZERO) > 0
          && cargoMatch.get().getCargoNominationId() > 0
          && cargoMatch.get().getColor() != null) {
        dischargingStatus.setPresent(true);
        dischargingStatus.setCargoName(cargoMatch.get().getAbbreviation());
        dischargingStatus.setColorCode(cargoMatch.get().getColor());
      } else {
        dischargingStatus.setPresent(false);
      }
    } else {
      ullages.add("");
      dischargingStatus.setPresent(false);
    }
    // Initializing cow values as null
    dischargingStatus.setCowWashStatus(false);
    dischargingStatus.setCowType(null);
    // Finding cow plan details
    if (cleaningTank != null
        && (!cleaningTank.getFullTanks().isEmpty()
            || !cleaningTank.getTopTanks().isEmpty()
            || !cleaningTank.getBottomTanks().isEmpty())) {
      setCowPlanStatus(dischargingStatus, start, end, cleaningTank);
    }
    quantityStatusList.add(dischargingStatus);
  }

  /**
   * Method to check if cow details present or not.
   *
   * @param cowTanks
   * @param position
   * @param end
   * @return
   * @return
   */
  private boolean getCowTankDetailForTank(List<CowTankDetail> cowTanks, Long start, Long end) {
    for (CowTankDetail cowTank : cowTanks) {
      if (cowTank.getStart() >= start && cowTank.getEnd() <= end) {
        return true;
      }
    }
    return false;
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
      ullages.add(UnitConversionUtility.setPrecision(ballastMatch.get().getSounding(), 3));
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
        .map(i -> UnitConversionUtility.setPrecision(i, 2))
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
    return tankList.stream()
        .sorted(Comparator.comparing(TankCategoryForSequence::getDisplayOrder))
        .collect(Collectors.toList());
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
    //    return tankList.stream()
    //            .sorted(Comparator.comparing(TankCategoryForSequence::getDisplayOrder))
    //            .collect(Collectors.toList());
    return tankList;
  }

  /**
   * Finding discharging rate
   *
   * @param cargoLoadingRates
   * @param stageTickPositions
   * @return
   */
  private List<LoadingRateForSequence> getDischargingRate(
      List<CargoLoadingRate> cargoDischargingRates, Set<Long> stageTickPositions) {
    List<LoadingRateForSequence> dischargingRates = new ArrayList<>();
    List<Long> positions = getListFromSet(stageTickPositions);
    IntStream.range(0, positions.size() - 1)
        .forEach(
            i -> {
              LoadingRateForSequence lrsObj = new LoadingRateForSequence();
              lrsObj.setPosision(i);
              lrsObj.setRate("");
              dischargingRates.add(lrsObj);
            });
    List<CargoLoadingRate> selectedItem =
        cargoDischargingRates.stream()
            .filter(item -> !item.getDischargingRates().isEmpty())
            .collect(Collectors.toList());
    for (CargoLoadingRate rate : selectedItem) {
      int start = positions.indexOf(rate.getStartTime());
      int end = positions.indexOf(rate.getEndTime());
      for (int i = start; i < end; i++) {
        for (BigDecimal value : rate.getDischargingRates()) {
          if (dischargingRates.get(i).getRate().isEmpty()) {
            dischargingRates.get(i).setRate(value.toString());
          } else if (!dischargingRates.get(i).getRate().contains(value.toString())) {
            dischargingRates
                .get(i)
                .setRate(dischargingRates.get(i).getRate() + "/" + value.toString());
          }
        }
      }
    }
    // Setting zero value incase of no discharging rate present.
    dischargingRates.stream()
        .forEach(
            item -> {
              if (item.getRate().isEmpty()) {
                item.setRate(ZERO_VALUE);
              }
            });
    return dischargingRates;
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
    List<String> sfFrNoList = new ArrayList<>();
    List<String> bmList = new ArrayList<>();
    List<String> bmFrNoList = new ArrayList<>();
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
        case "sfFrameNumber":
          matchStabilityParam(sfFrNoList, stabilityParam.getData(), size);
          break;
        case "bm":
          matchStabilityParam(bmList, stabilityParam.getData(), size);
          break;
        case "bmFrameNumber":
          matchStabilityParam(bmFrNoList, stabilityParam.getData(), size);
          break;
        case "ukc":
          matchStabilityParam(ukcList, stabilityParam.getData(), size);
          break;
        default:
          break;
      }
    }
    List<ShearingForce> sf = new ArrayList<>();
    sequenceStability.setAfter(afterList);
    sequenceStability.setFw(fwList);
    IntStream.range(0, bmFrNoList.size())
        .forEach(
            i -> {
              ShearingForce listItem = new ShearingForce();
              listItem.setFrameNumber(bmFrNoList.get(i));
              listItem.setPercentage(bmList.get(i));
              sf.add(listItem);
            });
    ;
    sequenceStability.setBm(sf);
    sequenceStability.setGm(gmList);
    sequenceStability.setTrim(trimList);
    sequenceStability.setUkc(ukcList);
    sf.clear();
    IntStream.range(0, sfFrNoList.size())
        .forEach(
            i -> {
              ShearingForce listItem = new ShearingForce();
              listItem.setFrameNumber(sfFrNoList.get(i));
              listItem.setPercentage(sfList.get(i));
              sf.add(listItem);
            });
    ;
    sequenceStability.setShearingForce(sf);
    return sequenceStability;
  }

  private void matchStabilityParam(List<String> paramsList, List<List> params, Integer size) {
    if (params.isEmpty()) {
      // Setting empty value for excel look and feel
      IntStream.range(0, size).forEach(i -> paramsList.add("0.0"));
    } else {
      params.forEach(
          i -> {
            if (i.get(1) != null && !i.get(1).toString().isBlank()) {
              paramsList.add(
                  UnitConversionUtility.setPrecision(Double.parseDouble(i.get(1).toString()), 3));
            } else {
              paramsList.add("0.0");
            }
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
      DischargePlanResponse requestPayload,
      Long vesselId,
      Long voyageId,
      Long infoId,
      Long portRotationId)
      throws GenericServiceException, ParseException {
    log.info("Building sheet 1 : Discharging plan diagram");
    LoadingPlanExcelLoadingPlanDetails sheetOne = new LoadingPlanExcelLoadingPlanDetails();
    getBasicVesselDetails(sheetOne, vesselId, voyageId, infoId, portRotationId);
    // Condition type 1 is arrival
    sheetOne.setArrivalCondition(getVesselConditionDetails(requestPayload, 1));
    sheetOne.setDeparcherCondition(getVesselConditionDetails(requestPayload, 2));
    sheetOne.setCargoTobeDischarged(
        getCargoTobeDischarged(requestPayload.getDischargingInformation()));
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
        activeVoyage.getDischargePortRotations().stream()
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
      LoadingPlanExcelLoadingPlanDetails sheetOne, DischargePlanResponse requestPayload) {
    List<BerthInformation> berthInfoList = new ArrayList<>();
    List<BerthDetails> berthDetailsList =
        requestPayload.getDischargingInformation().getBerthDetails().getSelectedBerths();
    List<BerthDetails> allBerths =
        requestPayload.getDischargingInformation().getBerthDetails().getAvailableBerths();
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
  private List<CargoTobeLoaded> getCargoTobeDischarged(DischargeInformation requestPayload) {
    List<CargoTobeLoaded> cargoTobeDischargedList = new ArrayList<>();
    List<DischargeQuantityCargoDetails> dischargeQuantityCargoList =
        requestPayload.getCargoVesselTankDetails().getDischargeQuantityCargoDetails();
    if (!dischargeQuantityCargoList.isEmpty()) {
      dischargeQuantityCargoList.forEach(
          item -> {
            CargoTobeLoaded cargoTobeDischarged = new CargoTobeLoaded();
            Optional.ofNullable(item.getCargoAbbreviation())
                .ifPresent(cargoTobeDischarged::setCargoName);
            Optional.ofNullable(item.getColorCode()).ifPresent(cargoTobeDischarged::setColorCode);
            Optional.ofNullable(item.getEstimatedAPI())
                .ifPresent(
                    value ->
                        cargoTobeDischarged.setApi(
                            UnitConversionUtility.setPrecision(Double.parseDouble(value), 2)));
            Optional.ofNullable(item.getEstimatedTemp())
                .ifPresent(
                    value ->
                        cargoTobeDischarged.setTemperature(
                            UnitConversionUtility.setPrecision(Double.parseDouble(value), 2)));
            Optional.ofNullable(item.getLoadingPorts())
                .ifPresent(
                    ports ->
                        cargoTobeDischarged.setLoadingPort(
                            ports.stream().collect(Collectors.joining(","))));
            Optional.ofNullable(item.getCargoNominationQuantity())
                .ifPresent(
                    value -> {
                      if (item.getEstimatedAPI() != null && item.getEstimatedTemp() != null) {
                        cargoTobeDischarged.setNomination(
                            UnitConversionUtility.convertToBBLS(
                                    UnitTypes.MT,
                                    Double.parseDouble(item.getEstimatedAPI()),
                                    Double.parseDouble(item.getEstimatedTemp()),
                                    Double.parseDouble(value))
                                .toString());
                      }
                    });
            Optional.ofNullable(item.getDischargeMT())
                .ifPresent(cargoTobeDischarged::setShipLoadable);
            Optional.ofNullable(item.getMaxTolerence())
                .ifPresent(cargoTobeDischarged::setTolerance);
            Optional.ofNullable(item.getDifferencePercentage())
                .ifPresent(cargoTobeDischarged::setDifference);
            Optional.ofNullable(item.getTimeRequiredForDischarging())
                .ifPresent(cargoTobeDischarged::setTimeRequiredForDischarging);
            Optional.ofNullable(item.getSlopQuantity())
                .ifPresent(
                    value -> {
                      if (item.getEstimatedAPI() != null && item.getEstimatedTemp() != null) {
                        cargoTobeDischarged.setSlopQuantity(
                            UnitConversionUtility.convertToBBLS(
                                    UnitTypes.MT,
                                    Double.parseDouble(item.getEstimatedAPI()),
                                    Double.parseDouble(item.getEstimatedTemp()),
                                    Double.parseDouble(value))
                                .toString());
                      }
                    });
            cargoTobeDischargedList.add(cargoTobeDischarged);
          });
    }
    return cargoTobeDischargedList;
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
      DischargePlanResponse requestPayload, Integer conditionType) throws GenericServiceException {
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
      DischargePlanResponse requestPayload,
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
      DischargePlanResponse requestPayload,
      Integer conditionType) {
    List<LoadingPlanBallastDetails> dischargingPlanBallastDetailsList =
        requestPayload.getPlanBallastDetails();
    List<TankCargoDetails> tankCargoDetailsList = new ArrayList<>();
    Double ballastWaterSum = 0.0;
    // Ballast rear tanks
    getBallastTankDetails(
        tankCargoDetailsList,
        requestPayload.getBallastRearTanks(),
        dischargingPlanBallastDetailsList,
        conditionType);
    ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
    findAptFpt(vesselCondition, tankCargoDetailsList, "R");
    tankCargoDetailsList.clear();
    // Ballast front tanks
    getBallastTankDetails(
        tankCargoDetailsList,
        requestPayload.getBallastFrontTanks(),
        dischargingPlanBallastDetailsList,
        conditionType);
    ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
    findAptFpt(vesselCondition, tankCargoDetailsList, "F");
    tankCargoDetailsList.clear();
    // Ballast center tanks
    getBallastTankDetails(
        tankCargoDetailsList,
        requestPayload.getBallastCenterTanks(),
        dischargingPlanBallastDetailsList,
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
   * @param dischargingPlanBallastDetailsList
   */
  private void getBallastTankDetails(
      List<TankCargoDetails> tankCargoDetailsList,
      List<List<VesselTank>> ballastTanks,
      List<LoadingPlanBallastDetails> dischargingPlanBallastDetailsList,
      Integer conditionType) {
    ballastTanks.forEach(
        tankList -> {
          tankList.forEach(
              tank -> {
                Optional<LoadingPlanBallastDetails> dischargingPlanBallastDetailsOpt =
                    dischargingPlanBallastDetailsList.stream()
                        .filter(
                            item ->
                                item.getTankId().equals(tank.getId())
                                    && item.getConditionType().equals(conditionType)
                                    && item.getValueType().equals(2))
                        .findAny();
                TankCargoDetails tankCargoDetails = new TankCargoDetails();
                tankCargoDetails.setTankName(tank.getShortName());
                tankCargoDetails.setId(tank.getId());
                setBallasTankValues(dischargingPlanBallastDetailsOpt, tankCargoDetails);
                tankCargoDetailsList.add(tankCargoDetails);
              });
        });
  }

  /**
   * @param dischargingPlanBallastDetailsOpt
   * @return
   */
  private void setBallasTankValues(
      Optional<LoadingPlanBallastDetails> dischargingPlanBallastDetailsOpt,
      TankCargoDetails tankCargoDetails) {
    if (dischargingPlanBallastDetailsOpt.isPresent()) {
      Optional.ofNullable(dischargingPlanBallastDetailsOpt.get().getQuantityMT())
          .ifPresent(i -> tankCargoDetails.setQuantity(Double.parseDouble(i)));
      Optional.ofNullable(dischargingPlanBallastDetailsOpt.get().getColorCode())
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
      DischargePlanResponse requestPayload,
      Integer conditionType)
      throws GenericServiceException {
    setStabilityParamForReport(
        vesselCondition, requestPayload.getPlanStabilityParams(), conditionType);

    List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetailsList =
        requestPayload
            .getDischargingInformation()
            .getCargoVesselTankDetails()
            .getDischargeQuantityCargoDetails();
    List<TankCargoDetails> tankCargoDetailsList = new ArrayList<>();
    for (List<VesselTank> tankGroup : requestPayload.getCargoTanks()) {
      tankGroup.forEach(
          item -> {
            Optional<LoadingPlanStowageDetails> dischargingPlanStowageDetails =
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
                dischargingPlanStowageDetails,
                dischargeQuantityCargoDetailsList,
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
      List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetailsList,
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
        Optional<DischargeQuantityCargoDetails> quantityCargoDetails =
            dischargeQuantityCargoDetailsList.stream()
                .filter(
                    i ->
                        i.getDischargeCargoNominationId()
                            .equals(loadingPlanStowageDetails.get().getCargoNominationId()))
                .findFirst();
        if (quantityCargoDetails.isPresent()) {
          Optional.ofNullable(quantityCargoDetails.get().getColorCode())
              .ifPresent(tankCargoDetails::setColorCode);
          Optional.ofNullable(quantityCargoDetails.get().getCargoAbbreviation())
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
      return UnitConversionUtility.setPrecision(
          (quantityOBSKL / Double.parseDouble(tankFullCapacity)) * 100, 2);
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
          .ifPresent(vesselCondition::setDraftF);
      Optional.ofNullable(stabilityParam.get().getTrim()).ifPresent(vesselCondition::setTrim);
    }
  }
}
