/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.gateway.domain.loadingplan.ArrivalDeparcherCondition;
import com.cpdss.gateway.domain.loadingplan.TankCargoDetails;
import java.awt.Color;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.jxls.area.Area;
import org.jxls.area.XlsArea;
import org.jxls.common.AreaListener;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;

/**
 * @author sanalkumar.k
 *
 */
@Slf4j
public class GenerateLoadingPlanExcelReportStyle implements AreaListener {

	XlsArea area;
	PoiTransformer transformer;

	public GenerateLoadingPlanExcelReportStyle(Area area) {
		this.area = (XlsArea) area;
		transformer = (PoiTransformer) area.getTransformer();
	}

	@Override
	public void beforeApplyAtCell(CellRef cellRef, Context context) {
	}

	@Override
	public void afterApplyAtCell(CellRef cellRef, Context context) {
	}

	@Override
	public void beforeTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
	}

	@Override
	public void afterTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
		ArrivalDeparcherCondition vesselCondition = (ArrivalDeparcherCondition) context
				.getVar("sheetOne.arrivalCondition");
		// setCargoColor(vesselCondition.getBallastTopTanks().getTank(),targetCell);
		setCargoColor(vesselCondition.getCargoTopTanks().getTank(), targetCell);
		vesselCondition = (ArrivalDeparcherCondition) context.getVar("sheetOne.deparcherCondition");
		// setCargoColor(vesselCondition.getBallastTopTanks().getTank(),targetCell);
		setCargoColor(vesselCondition.getCargoTopTanks().getTank(), targetCell);
	}

	private void setCargoColor(List<TankCargoDetails> tanks, CellRef targetCell) {
		for (TankCargoDetails tank : tanks) {
			if (!tank.getColorCode().isBlank()) {
				Workbook workbook = transformer.getWorkbook();
				Sheet sheet = workbook.getSheet(targetCell.getSheetName());
				Cell cell = sheet.getRow(targetCell.getRow()).getCell(targetCell.getCol());
				String value = cell.getStringCellValue();
				if (value.equals(tank.getTankName()) || value.equals(tank.getCargoName())
						|| value.equals(tank.getQuantity().toString())
						|| value.equals(tank.getFillingRatio().toString()) || value.equals(tank.getUllage())) {
					CellStyle cellStyle = cell.getCellStyle();
					CellStyle newCellStyle = workbook.createCellStyle();
					newCellStyle.setDataFormat(cellStyle.getDataFormat());
					newCellStyle.setFont(workbook.getFontAt(cellStyle.getFontIndex()));
					newCellStyle.setFillBackgroundColor(cellStyle.getFillBackgroundColor());
					newCellStyle.setFillForegroundColor(
							new XSSFColor(Color.decode(tank.getColorCode()), new DefaultIndexedColorMap()).getIndex());
					cell.setCellStyle(newCellStyle);
				}

			}

		}
	}

}
