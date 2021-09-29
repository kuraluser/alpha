/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingPlanDetails;
import com.cpdss.gateway.domain.loadingplan.TankCargoDetails;
import com.cpdss.gateway.domain.loadingplan.TankRow;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
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

	public static String SHEET_NAMES[] = { "CRUD - 021 pg1" };

	public static String PARENT_AREA = "A1:Z72";

	public static String ARRIVAL_TANK_AREA = "B14:Q27";
	
	public static String DEPARCHER_TANK_AREA = "B33:Q45";
	
	public static String ARRIVAL_CARGO_AREA = "T15:Z15";
	
	public static String DEPARCHER_CARGO_AREA = "T31:Z31";
	
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
//		Workbook workbook = transformer.getWorkbook();
//		Sheet sheet = workbook.getSheet(cellRef.getSheetName());
//		Cell cell = sheet.getRow(16).getCell(2);
//		String value = null;
//		if (cell != null) {
//			
//			if (cell.getCellType().equals(CellType.NUMERIC)) {
//				value = ((Double) cell.getNumericCellValue()).toString();
//			} else {
//				value = cell.getStringCellValue();
//			}
//		}
//		System.out.println(value);
	}

	@Override
	public void beforeTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
	}

	/**
	 * Method for applying color dynamically on vessel tanks based of cargo
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void afterTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
		log.info(targetCell.getCellName());
		Map<String, Object> dataMap = (Map<String, Object>) context.getVar("sheetOne");
		ObjectMapper mapper = new ObjectMapper();
		LoadingPlanExcelLoadingPlanDetails data = mapper.convertValue(dataMap,
				LoadingPlanExcelLoadingPlanDetails.class);
//		setCellStyleColor(targetCell, data);
		if (targetCell.getRow() >= 14 && targetCell.getRow() < 29) {
			setTankColor(targetCell, data.getArrivalCondition().getCargoCenterTanks().getTank());
			setTankColor(targetCell, data.getArrivalCondition().getCargoTopTanks().getTank());
			setTankColor(targetCell, data.getArrivalCondition().getCargoBottomTanks().getTank());
		} else if (targetCell.getRow() >= 32 && targetCell.getRow() < 46) {
			setTankColor(targetCell, data.getDeparcherCondition().getCargoCenterTanks().getTank());
			setTankColor(targetCell, data.getDeparcherCondition().getCargoTopTanks().getTank());
			setTankColor(targetCell, data.getDeparcherCondition().getCargoBottomTanks().getTank());
		}

	}

	private void setTankColor(CellRef targetCell, List<TankCargoDetails> tankList) {
		Workbook workbook = transformer.getWorkbook();
		Sheet sheet = workbook.getSheet(targetCell.getSheetName());
		Cell cell = sheet.getRow(targetCell.getRow()).getCell(targetCell.getCol());
		if (cell != null) {
			String value = getCellValue(cell);
			if (value != null && !value.isBlank()) {
				TankCargoDetails tankFromFile = getTank(value, tankList);
				if (tankFromFile != null) {
					if (tankFromFile.getColorCode() != null && !tankFromFile.getColorCode().isBlank()
							&& tankFromFile.getQuantity() != null && tankFromFile.getQuantity() > 0) {
						CellStyle cellStyle = cell.getCellStyle();
						CellStyle newCellStyle = workbook.createCellStyle();
						newCellStyle.setDataFormat(cellStyle.getDataFormat());
						newCellStyle.setFont(workbook.getFontAt(cellStyle.getFontIndex()));
						newCellStyle.setFillForegroundColor(
								new XSSFColor(Color.decode(tankFromFile.getColorCode()), new DefaultIndexedColorMap())
										.getIndex());
						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						cell.setCellStyle(newCellStyle);
					}
				}
			}
		}
	}

	private TankCargoDetails getTank(String value, List<TankCargoDetails> tankList) {
		for (TankCargoDetails tank : tankList) {
			if (tank.getTankName().equals(value) && tank.getQuantity() > 0) {
				return tank;
			}
		}
		return null;
	}

	private void setCellStyleColor(CellRef targetCell, LoadingPlanExcelLoadingPlanDetails data) {
		Workbook workbook = transformer.getWorkbook();
		Sheet sheet = workbook.getSheet(targetCell.getSheetName());
		Cell cell = sheet.getRow(targetCell.getRow()).getCell(targetCell.getCol());
		if (cell != null) {
			String value = getCellValue(cell);
			if (value != null && !value.isBlank()) {
				TankCargoDetails tankFromFile = checkIfTankCell(value, data);
				if (tankFromFile != null) {
					if (tankFromFile.getColorCode() != null && !tankFromFile.getColorCode().isBlank()
							&& tankFromFile.getQuantity() != null && tankFromFile.getQuantity() > 0) {
						CellStyle cellStyle = cell.getCellStyle();
						CellStyle newCellStyle = workbook.createCellStyle();
						newCellStyle.setDataFormat(cellStyle.getDataFormat());
						newCellStyle.setFont(workbook.getFontAt(cellStyle.getFontIndex()));
//						newCellStyle.setFillBackgroundColor(cellStyle.getFillBackgroundColor());
						newCellStyle.setFillForegroundColor(
								new XSSFColor(Color.decode(tankFromFile.getColorCode()), new DefaultIndexedColorMap())
										.getIndex());
						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						cell.setCellStyle(newCellStyle);
					}
				}
			}

		}

	}

	private String getCellValue(Cell cell) {
		if (cell != null) {
			if (cell.getCellType().equals(CellType.NUMERIC)) {
				return ((Double) cell.getNumericCellValue()).toString();
			} else {
				return cell.getStringCellValue();
			}
		}
		return null;
	}

	private TankCargoDetails checkIfTankCell(String value, LoadingPlanExcelLoadingPlanDetails data) {
		TankCargoDetails tankFromFile = null;
		if (value.contains(".tank[")) {
			String index = value.substring(value.indexOf("[") + 1, value.indexOf("]"));
			if (value.contains(".arrivalCondition.")) {
				if (value.contains(".cargoTopTanks.")) {
					tankFromFile = data.getArrivalCondition().getCargoTopTanks().getTank()
							.get(Integer.getInteger(index));
				} else if (value.contains(".cargoBottomTanks.")) {
					tankFromFile = data.getArrivalCondition().getCargoBottomTanks().getTank()
							.get(Integer.getInteger(index));
				} else if (value.contains(".cargoCenterTanks.")) {
					tankFromFile = data.getArrivalCondition().getCargoCenterTanks().getTank()
							.get(Integer.getInteger(index));
				}

			} else if (value.contains(".deparcherCondition.")) {
				if (value.contains(".cargoTopTanks.")) {
					tankFromFile = data.getDeparcherCondition().getCargoTopTanks().getTank()
							.get(Integer.getInteger(index));
				} else if (value.contains(".cargoBottomTanks.")) {
					tankFromFile = data.getDeparcherCondition().getCargoBottomTanks().getTank()
							.get(Integer.getInteger(index));
				} else if (value.contains(".cargoCenterTanks.")) {
					tankFromFile = data.getDeparcherCondition().getCargoCenterTanks().getTank()
							.get(Integer.getInteger(index));
				}
			}
		}
		return tankFromFile;
	}

}
