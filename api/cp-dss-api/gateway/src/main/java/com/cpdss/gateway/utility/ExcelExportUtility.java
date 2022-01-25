/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.service.loadingplan.GenerateLoadingPlanExcelReportStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.jxls.area.XlsArea;
import org.jxls.command.EachCommand;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** @author sanalkumar.k */
@Slf4j
@Component
public class ExcelExportUtility {

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  /**
   * Method to read data from request and Stamp in existing template
   *
   * @param dataObj Object to be drawn into excel
   * @param inputFileLocation Input file path
   * @param outputFileLocation Output file path
   * @return File output
   * @throws GenericServiceException In case of failure
   * @throws IOException In case of I/O issues
   */
  public File generateExcel(Object dataObj, String inputFileLocation, String outputFileLocation)
      throws GenericServiceException, IOException {

    log.info(
        "Inside generateExcel utility - Creating "
            + outputFileLocation
            + " file using "
            + inputFileLocation
            + " template.");
    OutputStream outStream = new FileOutputStream(outputFileLocation);

    try (InputStream inStream = this.getClass().getResourceAsStream(inputFileLocation)) {
      if (inStream != null) {
        // Converting object in to excel context
        Context context = getContext(dataObj);

        // Stamping values into excel template
        JxlsHelper.getInstance().processTemplate(inStream, outStream, context);

      } else {
        log.info("Invalid template location - no file present");
        throw new GenericServiceException(
            "Excel generation failed : No input template found",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Excel generation failed" + e.getMessage(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } finally {
      closeAndFlushOutput(outStream);
    }
    return new File(outputFileLocation);
  }

  /**
   * Create JSLX context form map
   *
   * @param dataObj
   * @return
   */
  private Context getContext(Object dataObj) {
    Context context = new Context();
    ObjectMapper mapper = new ObjectMapper();
    // object to a map
    @SuppressWarnings("unchecked")
    Map<String, Object> dataMap = mapper.convertValue(dataObj, Map.class);
    // Creating JXLS context
    for (Entry<String, Object> element : dataMap.entrySet()) {
      context.putVar(element.getKey(), element.getValue());
    }
    return context;
  }

  /**
   * Closing I/O streams
   *
   * @param outStream
   */
  private void closeAndFlushOutput(OutputStream outStream) {
    try {
      outStream.flush();
      outStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to set dynamic styling using area Listener and write data to excel
   *
   * @throws IOException
   * @throws EncryptedDocumentException
   */
  private void bindDataUsingAreaListener(
      InputStream inStream, OutputStream outStream, Context context)
      throws EncryptedDocumentException, IOException {
    Transformer transformer = TransformerFactory.createTransformer(inStream, outStream);
    // Parent area
    XlsArea xlsArea =
        new XlsArea(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.PARENT_AREA,
            transformer);
    // Child area
    XlsArea arrivalConditionArea =
        new XlsArea(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.ARRIVAL_TANK_AREA,
            transformer);
    // child area
    XlsArea deparcherConditionArea =
        new XlsArea(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.DEPARCHER_TANK_AREA,
            transformer);
    // Adding listener for dynamic cell coloring
    arrivalConditionArea.addAreaListener(
        new GenerateLoadingPlanExcelReportStyle(arrivalConditionArea));
    deparcherConditionArea.addAreaListener(
        new GenerateLoadingPlanExcelReportStyle(deparcherConditionArea));
    // Child loop area
    XlsArea arrivalCargoListArea =
        new XlsArea(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.ARRIVAL_CARGO_AREA,
            transformer);
    // loop
    EachCommand arrivalCargoListEachCommand =
        new EachCommand(
            "arrCargoList", "sheetOne.arrivalCondition.cargoDetails", arrivalCargoListArea);
    // Child loop area
    XlsArea deparcherCargoListArea =
        new XlsArea(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.DEPARCHER_CARGO_AREA,
            transformer);
    // loop
    EachCommand deparcherCargoListEachCommand =
        new EachCommand(
            "depCargoList", "sheetOne.deparcherCondition.cargoDetails", deparcherCargoListArea);

    // Adding loop logic to parent area
    xlsArea.addCommand(
        new AreaRef(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.ARRIVAL_CARGO_AREA),
        arrivalCargoListEachCommand);
    xlsArea.addCommand(
        new AreaRef(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.DEPARCHER_CARGO_AREA),
        deparcherCargoListEachCommand);

    // Applying template parent area to out file
    xlsArea.applyAt(
        new CellRef(
            GenerateLoadingPlanExcelReportStyle.SHEET_NAMES[0]
                + "!"
                + GenerateLoadingPlanExcelReportStyle.PARENT_AREA.split(":")[0]),
        context);
    xlsArea.processFormulas();
    // Stamping values into excel template
    transformer.write();
  }

  /**
   * Method to get contrast color for a given background color
   *
   * @param backgroundColor Color value of background
   * @return Contrast Color object
   */
  public Color getContrastColor(Color backgroundColor) {
    double lumaValue =
        ((0.299 * backgroundColor.getRed())
                + (0.587 * backgroundColor.getGreen())
                + (0.114 * backgroundColor.getBlue()))
            / 255;
    //    Threshold set to 0.5 for lumaValue
    return lumaValue > 0.5 ? Color.BLACK : Color.WHITE;
  }

  /** Set dynamic styling on template */
  private void setCellStyling(InputStream inStream, OutputStream outStream, Context context)
      throws EncryptedDocumentException, IOException {
    //		Workbook workbook = WorkbookFactory.create(inStream);
    //		// creating JxlsPlus transformer for the workbook
    //		PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
    //		// creating XlsCommentAreaBuilder instance
    //		AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
    //		// using area builder to construct a list of processing areas
    //		List<Area> xlsAreaList = areaBuilder.build();
    // getting the main area from the list
    //		Area xlsArea = xlsAreaList.get(1);
    //		System.out.println(xlsArea.getAreaRef().getSheetName());
    //
  }
}
