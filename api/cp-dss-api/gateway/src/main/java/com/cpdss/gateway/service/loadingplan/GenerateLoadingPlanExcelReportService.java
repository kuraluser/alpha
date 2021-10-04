/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.loadingplan.ArrivalDeparcherCondition;
import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.BerthInformation;
import com.cpdss.gateway.domain.loadingplan.CargoQuantity;
import com.cpdss.gateway.domain.loadingplan.CargoTobeLoaded;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionForExcel;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionList;
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
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingPlanServiceImpl;
import com.cpdss.gateway.utility.ExcelExportUtility;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author sanalkumar.k
 *
 */
@Slf4j
@Service
public class GenerateLoadingPlanExcelReportService {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

//	public String TEMPLATES_FILE_LOCATION = "/reports/Vessel_{type}_Loading_Plan_Template.xlsx";
	public String TEMPLATES_FILE_LOCATION = "/reports/Vessel_1_Loading_Plan_Template.xlsx";
	public String OUTPUT_FILE_LOCATION = "/reports/Vessel_{id}_Loading_Plan_{voy}_{port}.xlsx";
	public String TEMP_LOCATION = "temp.xlsx";
	public final Integer START_ROW = 14;
	public final Integer END_ROW = 71;
	public final Integer END_COLUMN = 25;
	public String SHEET_NAMES[] = { "CRUD - 021 pg1", "CRUD - 021 pg2", "CRUD - 021 pg3" };
	public Long INSTRUCTION_ORDER[] = { 1L, 17L, 13L, 15L, 2L, 14L, 11L, 4L };
	public List<TankCargoDetails> cargoTanks = null;
	public List<TankCargoDetails> ballastTanks = null;

	@Value("${gateway.attachement.rootFolder}")
	private String rootFolder;

	@Autowired
	LoadingPlanGrpcService loadingPlanGrpcService;
	@Autowired
	ExcelExportUtility excelExportUtil;
	@Autowired
	private VesselInfoService vesselInfoService;
	@Autowired
	private LoadingInstructionService loadingInstructionService;
	@Autowired
	private LoadingPlanServiceImpl loadingPlanServiceImpl;
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
	public byte[] generateLoadingPlanExcel(LoadingPlanResponse requestPayload, Long vesselId, Long voyageId,
			Long infoId, Long portRotationId, Boolean downloadRequired) throws Exception {
		log.info("Generating Loading plan excel for Vessel {}", vesselId);
		// Setting file name of input file based on vessel type
		TEMPLATES_FILE_LOCATION = getLoadingPlanTemplateForVessel(vesselId);

		// Building data required for Loading plan Excel
		LoadingPlanExcelDetails loadinPlanExcelDetails = getDataForExcel(requestPayload, vesselId, voyageId, infoId,
				portRotationId);
		// Setting file name of output file
		OUTPUT_FILE_LOCATION = rootFolder
				+ getFileName(vesselId, loadinPlanExcelDetails.getSheetOne().getVoyageNumber(),
						loadinPlanExcelDetails.getSheetOne().getPortName());

		// Getting data mapped and calling excel builder utility
//		FileInputStream resultFileStream = new FileInputStream(
//				excelExportUtil.generateExcel(loadinPlanExcelDetails, TEMPLATES_FILE_LOCATION, OUTPUT_FILE_LOCATION));

		// Getting data mapped and calling excel builder utility
		FileInputStream resultFileStream = new FileInputStream(
				excelExportUtil.generateExcel(loadinPlanExcelDetails, TEMPLATES_FILE_LOCATION, TEMP_LOCATION));

		FileOutputStream outFile = new FileOutputStream(OUTPUT_FILE_LOCATION);
		if (resultFileStream != null) {
			// TODO put an entry in DB for Communication
			log.info("Excel generated, setting color based on cargo in all sheets");
			Workbook workbook = new XSSFWorkbook(resultFileStream);
			setCellStyle(workbook, loadinPlanExcelDetails);
			workbook.write(outFile);
			outFile.close();
			workbook.close();
		}
		resultFileStream.close();
		resultFileStream = new FileInputStream(OUTPUT_FILE_LOCATION);
		// Returning Output file as byte array for local download
		if (downloadRequired && resultFileStream != null) {
			return IOUtils.toByteArray(resultFileStream);
		}
		// No need to for local download if file generated form event trigger
		log.info("No local download required so returning null");
		return null;
	}

	/**
	 * Add style in excel
	 * 
	 * @param workbook
	 * 
	 * @param resultFileStream
	 * @param loadinPlanExcelDetails
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	private void setCellStyle(Workbook workbook, LoadingPlanExcelDetails data)
			throws EncryptedDocumentException, IOException {

		Sheet sheet = workbook.getSheet(SHEET_NAMES[0]);
		Cell cell = null;
		for (int row = START_ROW; row <= END_ROW; row++) {
			for (int col = 1; col <= END_COLUMN; col++) {
				cell = sheet.getRow(row).getCell(col);
				if (row >= 14 && row < 29) {
					setCargoColor(workbook, cell,
							data.getSheetOne().getArrivalCondition().getCargoCenterTanks().getTank());
					setCargoColor(workbook, cell,
							data.getSheetOne().getArrivalCondition().getCargoTopTanks().getTank());
					setCargoColor(workbook, cell,
							data.getSheetOne().getArrivalCondition().getCargoBottomTanks().getTank());
				} else if (row >= 32 && row < 46) {
					setCargoColor(workbook, cell,
							data.getSheetOne().getDeparcherCondition().getCargoCenterTanks().getTank());
					setCargoColor(workbook, cell,
							data.getSheetOne().getDeparcherCondition().getCargoTopTanks().getTank());
					setCargoColor(workbook, cell,
							data.getSheetOne().getDeparcherCondition().getCargoBottomTanks().getTank());
				}

			}
		}

	}

	/**
	 * @param workbook
	 * @param cell
	 * @param tanks
	 */
	private void setCargoColor(Workbook workbook, Cell cell, List<TankCargoDetails> tanks) {
		String value = getCellValue(cell);
		if (value != null && !value.isBlank()) {
			TankCargoDetails tankFromFile = getTank(value, tanks);
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
			} else {
				return cell.getStringCellValue();
			}
		}
		return null;
	}

	/**
	 * Get corresponding Loading plan template of a vessel based on its type.
	 */
	private String getLoadingPlanTemplateForVessel(Long vesselId) { // TODO
		log.info("Getting excel template based on vessel Type");
//		VesselReply reply = vesselInfoGrpcService
//				.getVesselDetailByVesselId(VesselRequest.newBuilder().setVesselId(vesselId).build());
//		if (!SUCCESS.equalsIgnoreCase(reply.getResponseStatus().getStatus())) {
//			throw new GenericServiceException("Error in calling vessel service - While checking vessel Type",
//					CommonErrorCodes.E_GEN_INTERNAL_ERR, HttpStatusCode.INTERNAL_SERVER_ERROR);
//		}
//		reply.getVesselTypeId();
//		Optional.ofNullable(reply.getVesselTypeId())
//				.ifPresent(TEMPLATES_FILE_LOCATION.replace("{type}", reply.getVesselTypeId().toString()));
		return TEMPLATES_FILE_LOCATION;
	}

	/**
	 * Get fully qualified name of output file
	 */
	private String getFileName(Long vesselId, String voyNo, String portName) {
		return OUTPUT_FILE_LOCATION.replace("{id}", vesselId.toString()).replace("{voy}", voyNo)
				.replace("{port}", portName).replace(" ", "_");
	}

	/**
	 * Method to get data mapped for excel sheets
	 */
	private LoadingPlanExcelDetails getDataForExcel(LoadingPlanResponse requestPayload, Long vesselId, Long voyageId,
			Long infoId, Long portRotationId) throws GenericServiceException, InterruptedException, ExecutionException {
		log.info("Getting details for excel sheetwise ");
		// ExecutorService threadPool = Executors.newFixedThreadPool(3);
		// Callable<LoadingPlanExcelLoadingPlanDetails> callable = () ->
		// buildSheetOne(requestPayload, vesselId, voyageId,
		// infoId, portRotationId);
		// Future<LoadingPlanExcelLoadingPlanDetails> future =
		// threadPool.submit(callable);
		LoadingPlanExcelDetails excelData = new LoadingPlanExcelDetails();
		// excelData.setSheetOne(future.get());
		excelData.setSheetOne(buildSheetOne(requestPayload, vesselId, voyageId, infoId, portRotationId));
		excelData.setSheetTwo(buildSheetTwo(vesselId, voyageId, infoId, portRotationId));
		excelData.setSheetThree(buildSheetThree(vesselId, voyageId, infoId, portRotationId));
//		threadPool.shutdown();
		return excelData;
	}

	/**
	 * Preparing sheet 2
	 * 
	 */
	private LoadingPlanExcelLoadingInstructionDetails buildSheetTwo(Long vesselId, Long voyageId, Long infoId,
			Long portRotationId) throws GenericServiceException {
		log.info("Building sheet 2 : Loading instructions");
		// Calling loading plan get sequence details service
		LoadingInstructionResponse loadingSequenceResponse = loadingInstructionService.getLoadingInstructions(vesselId,
				infoId, portRotationId);
		LoadingPlanExcelLoadingInstructionDetails sheetTwo = new LoadingPlanExcelLoadingInstructionDetails();
		sheetTwo.setInstructions(getInstructions(loadingSequenceResponse));
		log.info("Building sheet 2 : Completed");
		return sheetTwo;
	}

	/**
	 * Get list of instructions segregated against heading
	 * 
	 * @param integer
	 * @param loadingSequenceResponse
	 * @return
	 */
	private List<LoadingInstructionForExcel> getInstructions(LoadingInstructionResponse loadingSequenceResponse) {
		List<LoadingInstructionForExcel> instructionsList = new ArrayList<>();
		int group = 0;
		for (Long headerId : INSTRUCTION_ORDER) {
			String heading = loadingSequenceResponse.getLoadingInstructionGroupList().stream()
					.filter(value -> value.getGroupId().equals(headerId)).findAny().get().getGroupName();
			List<LoadingInstructionSubHeader> listUnderHeader = loadingSequenceResponse.getLoadingInstructionSubHeader()
					.stream().filter(item -> item.getInstructionHeaderId().equals(headerId) && item.getIsChecked())
					.collect(Collectors.toList());
			int count = 1;
			if (!listUnderHeader.isEmpty()) {
				for (LoadingInstructionSubHeader item : listUnderHeader) {
					LoadingInstructionForExcel instructionObj = new LoadingInstructionForExcel();
					instructionObj.setGroup(group);
					instructionObj.setHeading(group + 5 + ".  " + heading);
					instructionObj.setInstruction(count + ".  " + item.getSubHeaderName());
					instructionsList.add(instructionObj);
					count++;
					if (!item.getIsSingleHeader() && item.getLoadingInstructionsList().size() > 0) {
						int childCount = 1;
						for (LoadingInstructions subItem : item.getLoadingInstructionsList()) {
							if (subItem.getIsChecked()) {
								LoadingInstructionForExcel subInstructionObj = new LoadingInstructionForExcel();
								subInstructionObj.setGroup(group);
								subInstructionObj.setHeading(heading);
								subInstructionObj
										.setInstruction("    " + childCount + ".  " + subItem.getInstruction());
								instructionsList.add(subInstructionObj);
								childCount++;
							}
						}
					}
				}
			} else {
				LoadingInstructionForExcel instructionObj = new LoadingInstructionForExcel();
				instructionObj.setGroup(group);
				instructionObj.setHeading(group + 5 + ".  " + heading);
				instructionObj.setInstruction("-No instructions available under this section-");
				instructionsList.add(instructionObj);
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
	private void getAllBallastTanks(List<List<VesselTank>> ballastFrontTanks, List<List<VesselTank>> ballastCenterTanks,
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
	private LoadingPlanExcelLoadingSequenceDetails buildSheetThree(Long vesselId, Long voyageId, Long infoId,
			Long portRotationId) throws GenericServiceException {
		log.info("Building sheet 3 : Loading sequence chart");
		// Calling loading plan get sequence details service
		LoadingSequenceResponse loadingSequenceResponse = loadingPlanServiceImpl.getLoadingSequence(vesselId, voyageId,
				infoId);

		LoadingPlanExcelLoadingSequenceDetails sheetThree = new LoadingPlanExcelLoadingSequenceDetails();
		// Calculating no of stages in sequence
		sheetThree.setTickPoints(getTickPoints(loadingSequenceResponse.getMinXAxisValue(),
				loadingSequenceResponse.getStageTickPositions()));
		log.info("Sequence identified with {} Sections ", sheetThree.getTickPoints().size());
		if (sheetThree.getTickPoints().size() > 0) {
			// Getting all tanks present in sequence
			sheetThree.setCargoTanks(getCargoTanks(loadingSequenceResponse.getCargoTankCategories(),
					loadingSequenceResponse.getCargos()));
			log.info("CArgo list " + sheetThree.getCargoTanks().size()); // TODO check tank number against template
			if (sheetThree.getCargoTanks().size() > 0) {
				// Getting ullage mapped against each tank if present
				getCargoTankUllageAndQuantity(loadingSequenceResponse.getStageTickPositions(),
						loadingSequenceResponse.getCargos(), sheetThree.getCargoTanks());
				sheetThree.setLoadingRates(getLoadingRate(loadingSequenceResponse.getCargoLoadingRates(),
						loadingSequenceResponse.getStageTickPositions()));
			}
			sheetThree.setBallastTanks(getBallastTanks(loadingSequenceResponse.getBallastTankCategories(),
					loadingSequenceResponse.getBallasts()));
			log.info("BallastTanks list " + sheetThree.getBallastTanks().size());// TODO check tank number against
																					// template
			if (sheetThree.getBallastTanks().size() > 0) {
				getBallastTankUllageAndQuantity(loadingSequenceResponse.getStageTickPositions(),
						loadingSequenceResponse.getBallasts(), sheetThree.getBallastTanks());
			}
			sheetThree.setStabilityParams(getStabilityParams(loadingSequenceResponse.getStabilityParams(),
					sheetThree.getTickPoints().size()));
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
	private void getBallastTankUllageAndQuantity(Set<Long> stageTickPositions, List<Ballast> ballasts,
			List<TankCategoryForSequence> tankList) {
		// Getting ullages on each tick position of each tank
		for (TankCategoryForSequence tank : tankList) {
			TankWithSequenceDetails tanksUllageObj = new TankWithSequenceDetails();
			tanksUllageObj.setTankId(tank.getId());
			tanksUllageObj.setUllage(new ArrayList<>());
			tanksUllageObj.setStatus(new ArrayList<>());
			List<Ballast> ballastListOfpresentTank = ballasts.stream()
					.filter(cargo -> cargo.getTankId().equals(tank.getId())).collect(Collectors.toList());
			if (!ballastListOfpresentTank.isEmpty()) {
				List<String> ullages = new ArrayList<>();
				List<QuantityLoadingStatus> ballastStatusList = new ArrayList<>();
				stageTickPositions.forEach(position -> {
					QuantityLoadingStatus ballastStatus = new QuantityLoadingStatus();
					Optional<Ballast> ballastMatch = ballastListOfpresentTank.stream()
							.filter(cargo -> cargo.getStart().equals(position)).findFirst();
					if (ballastMatch.isPresent()) {
						ullages.add(ballastMatch.get().getSounding().toString());
						if (ballastMatch.get().getQuantity().compareTo(BigDecimal.ZERO) > 0
								&& ballastMatch.get().getColor() != null) {
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
				});
				tanksUllageObj.setUllage(ullages);
				tanksUllageObj.setStatus(ballastStatusList);
			} else {
				// setting empty values for look and feel
				IntStream.range(0, stageTickPositions.size()).forEach(i -> tanksUllageObj.getUllage().add(""));
				IntStream.range(0, stageTickPositions.size())
						.forEach(i -> tanksUllageObj.getStatus().add(new QuantityLoadingStatus(false, "", "")));
			}
			tank.setCargoTankUllage(tanksUllageObj);
		}

	}

	/**
	 * Get ullage of ballast tanks in each tick position
	 * 
	 * @param tankList
	 * 
	 * @param loadingSequenceResponse
	 * @param tankCategories
	 * @return
	 */
	private void getCargoTankUllageAndQuantity(Set<Long> stageTickPositions, List<Cargo> cargos,
			List<TankCategoryForSequence> tankList) {
		// Getting ullages on each tick position of each tank
		for (TankCategoryForSequence tank : tankList) {
			TankWithSequenceDetails tanksUllageObj = new TankWithSequenceDetails();
			tanksUllageObj.setTankId(tank.getId());
			tanksUllageObj.setUllage(new ArrayList<>());
			tanksUllageObj.setStatus(new ArrayList<>());
			List<Cargo> cargoListOfpresentTank = cargos.stream().filter(cargo -> cargo.getTankId().equals(tank.getId()))
					.collect(Collectors.toList());
			if (!cargoListOfpresentTank.isEmpty()) {
				List<String> ullages = new ArrayList<>();
				List<QuantityLoadingStatus> quantityStatusList = new ArrayList<>();
				// Finding ullage in each tick position
				stageTickPositions.forEach(position -> {
					QuantityLoadingStatus loadingStatus = new QuantityLoadingStatus();
					Optional<Cargo> cargoMatch = cargoListOfpresentTank.stream()
							.filter(cargo -> cargo.getStart().equals(position) && cargo.getEnd() > position)
							.findFirst();
					if (cargoMatch.isPresent()) {
						ullages.add(cargoMatch.get().getUllage().toString());
						if (cargoMatch.get().getQuantity().compareTo(BigDecimal.ZERO) > 0
								&& cargoMatch.get().getCargoNominationId() > 0 && cargoMatch.get().getColor() != null) {
							loadingStatus.setPresent(true);
							loadingStatus.setCargoName(cargoMatch.get().getAbbreviation());
							loadingStatus.setColorCode(cargoMatch.get().getColor());
						} else {
							loadingStatus.setPresent(false);
						}
					} else {
						// Avoiding entry in 0th position
						Optional<Cargo> firstPosition = cargoListOfpresentTank.stream()
								.filter(cargo -> cargo.getStart().equals(position) && cargo.getEnd().equals(position))
								.findFirst();
						if (!firstPosition.isPresent()) {
							ullages.add("");
							loadingStatus.setPresent(false);
						}
					}
					// Avoiding entry in 0th position
					Optional<Cargo> firstPosition = cargoListOfpresentTank.stream()
							.filter(cargo -> cargo.getStart().equals(position) && cargo.getEnd().equals(position))
							.findFirst();
					if (!firstPosition.isPresent()) {
						quantityStatusList.add(loadingStatus);
					}
				});
				tanksUllageObj.setUllage(ullages);
				tanksUllageObj.setStatus(quantityStatusList);

			} else {
				// setting empty values for look and feel
				IntStream.range(0, stageTickPositions.size() - 1).forEach(i -> tanksUllageObj.getUllage().add(""));
				IntStream.range(0, stageTickPositions.size() - 1)
						.forEach(i -> tanksUllageObj.getStatus().add(new QuantityLoadingStatus(false, "", "")));
			}
			tank.setCargoTankUllage(tanksUllageObj);
		}
	}

	/**
	 * Convert tick position form millisecond to hours
	 * 
	 * @param minXAxisValue
	 * @param stageTickPositions
	 * @return
	 */
	private List<String> getTickPoints(Long minXAxisValue, Set<Long> stageTickPositions) {
		List<String> tickPoints = new ArrayList<>();
		for (Long xValue : stageTickPositions) {
			Long hours = TimeUnit.MILLISECONDS.toHours(xValue - minXAxisValue);
			if (tickPoints.contains(hours.toString())) {
				Double temp = ((xValue.doubleValue() - minXAxisValue.doubleValue()) / 1000) / 3600;
				String result = String.format("%.2f", temp);
				tickPoints.add(result);
			} else {
				tickPoints.add(hours.toString());
			}
		}
		tickPoints.remove(tickPoints.indexOf("0"));
		return tickPoints;
	}

	/**
	 * Get tank mapped with the cargo inside
	 * 
	 * @param cargoTankCategories
	 * @param cargos
	 * @return
	 */
	private List<TankCategoryForSequence> getCargoTanks(List<TankCategory> cargoTankCategories, List<Cargo> cargos) {
		List<TankCategoryForSequence> tankList = new ArrayList<>();
		// List of tanks with cargo details
		for (TankCargoDetails cargoTank : cargoTanks) {
			TankCategoryForSequence tankCategoryObj = new TankCategoryForSequence();
			tankCategoryObj.setId(cargoTank.getId());
			tankCategoryObj.setTankName(cargoTank.getTankName());
			cargoTankCategories.forEach(item -> {
				if (item.getId().equals(cargoTank.getId())) {
					tankCategoryObj.setUllage(item.getUllage());
					tankCategoryObj.setDisplayOrder(item.getDisplayOrder());
					Optional<Cargo> cargoOpt = cargos.stream().filter(cargo -> cargo.getTankId().equals(item.getId())
							&& cargo.getQuantity().compareTo(BigDecimal.ZERO) > 0).findFirst();
					if (cargoOpt.isPresent()) {
						tankCategoryObj.setQuantity(cargoOpt.get().getQuantity());
						tankCategoryObj.setCargoName(cargoOpt.get().getName());
						tankCategoryObj.setColorCode(cargoOpt.get().getColor());
					}
				}
			});
			tankList.add(tankCategoryObj);
		}
		return tankList;
	}

	private List<TankCategoryForSequence> getBallastTanks(List<TankCategory> ballastTankCategories,
			List<Ballast> ballasts) {
		List<TankCategoryForSequence> tankList = new ArrayList<>();
		// List of tanks with cargo details
		for (TankCargoDetails tank : ballastTanks) {
			TankCategoryForSequence tankCategoryObj = new TankCategoryForSequence();
			tankCategoryObj.setId(tank.getId());
			tankCategoryObj.setTankName(tank.getTankName());
			ballastTankCategories.forEach(item -> {
				if (item.getId().equals(tank.getId())) {
					Optional<Ballast> ballastOpt = ballasts.stream()
							.filter(cargo -> cargo.getTankId().equals(item.getId())
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
	 * @param cargoLoadingRates
	 * @param stageTickPositions
	 * @return
	 */
	private List<LoadingRateForSequence> getLoadingRate(List<CargoLoadingRate> cargoLoadingRates,
			Set<Long> stageTickPositions) {
		List<LoadingRateForSequence> loadingRates = new ArrayList<>();
		List<Long> positions = new ArrayList<>();
		for (Long i : stageTickPositions) {
			positions.add(i);
		}
		positions.stream().sorted();
		IntStream.range(0, stageTickPositions.size() - 1).forEach(i -> {
			LoadingRateForSequence lrsObj = new LoadingRateForSequence();
			lrsObj.setPosision(i);
			lrsObj.setRate("");
			loadingRates.add(lrsObj);
		});
		List<CargoLoadingRate> selectedItem = cargoLoadingRates.stream()
				.filter(item -> !item.getLoadingRates().isEmpty()).collect(Collectors.toList());
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
	private StabilityParamsOfLoadingSequence getStabilityParams(List<StabilityParam> stabilityParams, Integer size) {
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
			IntStream.range(0, size + 1).forEach(i -> paramsList.add(""));
		} else {
			params.forEach(i -> {
				paramsList.add(i.get(1).toString());
			});
		}
	}

	/**
	 * Build data model for Sheet 1
	 * 
	 * @return sheet one
	 * @throws GenericServiceException
	 */
	private LoadingPlanExcelLoadingPlanDetails buildSheetOne(LoadingPlanResponse requestPayload, Long vesselId,
			Long voyageId, Long infoId, Long portRotationId) throws GenericServiceException {
		log.info("Building sheet 1 : Loading plan diagram");
		if (requestPayload == null) {
			// Calling loading plan get plan details service
			requestPayload = loadingPlanServiceImpl.getLoadingPlan(vesselId, voyageId, infoId, portRotationId);
		}
		// Get a list of all ballast tanks for sheet3
		getAllBallastTanks(requestPayload.getBallastFrontTanks(), requestPayload.getBallastCenterTanks(),
				requestPayload.getBallastRearTanks());

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
	 */
	private void getBasicVesselDetails(LoadingPlanExcelLoadingPlanDetails sheetOne, Long vesselId, Long voyageId,
			Long infoId, Long portRotationId) throws GenericServiceException {
		VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
		Optional<PortRotation> portRotation = activeVoyage.getPortRotations().stream()
				.filter(v -> v.getId().equals(portRotationId)).findFirst();
		VesselResponse vesselResponse = vesselInfoService.getVesselsByCompany(1L, null);
		Vessel vessel = vesselResponse.getVessels().stream().filter(i -> i.getId().equals(vesselId)).findFirst()
				.orElseThrow(() -> new GenericServiceException(
						String.format("Vessel details not found for VesselId: %d", vesselId),
						CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST));
		if (vessel != null) {
			sheetOne.setVesselName(vessel.getName());
			sheetOne.setMaster(String.valueOf(vessel.getCaptainName()));
			sheetOne.setCo(String.valueOf(vessel.getChiefOfficerName()));
			sheetOne.setVoyageNumber(activeVoyage.getVoyageNumber());
		}
		if (portRotation.isPresent()) {
			PortInfo.GetPortInfoByPortIdsRequest request = PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
					.addId(portRotation.get().getPortId()).build();
			PortInfo.PortDetail portReply = getPortInfo(request).getPortsList().stream().findFirst()
					.orElse(PortInfo.PortDetail.getDefaultInstance());
			if (portReply != null) {
				sheetOne.setPortName(portReply.getName());
				sheetOne.setEta(portRotation.get().getEta());
				sheetOne.setEtd(portRotation.get().getEtd());
			}
		}
//		sheetOne.setVesselCompliance(); TODO
//		sheetOne.setLoadLineZone(); TODO
	}

	/**
	 * Berth information details
	 * 
	 * @param sheetOne
	 * 
	 * @param requestPayload
	 * @return
	 */
	private void getBerthInfoDetails(LoadingPlanExcelLoadingPlanDetails sheetOne, LoadingPlanResponse requestPayload) {
		List<BerthInformation> berthInfoList = new ArrayList<>();
		List<BerthDetails> berthDetailsList = requestPayload.getLoadingInformation().getBerthDetails()
				.getSelectedBerths();
		String itemsAgreedWithTerminal = "";
		if (!berthDetailsList.isEmpty()) {
			berthDetailsList.forEach(item -> {
				BerthInformation berthInformation = new BerthInformation();
				Optional.ofNullable(item.getBerthName()).ifPresent(berthInformation::setBerthName);
				Optional.ofNullable(item.getHoseConnections()).ifPresent(berthInformation::setHoseConnection);
				Optional.ofNullable(item.getMaxManifoldPressure()).ifPresent(berthInformation::setMaxManifoldPressure);
				Optional.ofNullable(item.getAirDraftLimitation()).ifPresent(berthInformation::setAirDraftLimitation);
				Optional.ofNullable(item.getAirPurge()).ifPresent(berthInformation::setAirPurge);
				Optional.ofNullable(item.getRegulationAndRestriction())
						.ifPresent(berthInformation::setSpecialRegulation);
				Optional.ofNullable(item.getItemsToBeAgreedWith()).ifPresent(i -> {
					if (!itemsAgreedWithTerminal.contains(i)) {
						itemsAgreedWithTerminal.concat(",/n" + i);
					}
				});
				berthInfoList.add(berthInformation);
			});
		}

		sheetOne.setBerthInformation(berthInfoList);
		sheetOne.setItemsAgreedWithTerminal(itemsAgreedWithTerminal);
	}

	/**
	 * Calling port GROC for getting Port name
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
		List<LoadableQuantityCargoDetails> LoadableQuantityCargoList = requestPayload.getLoadingInformation()
				.getCargoVesselTankDetails().getLoadableQuantityCargoDetails();
		if (!LoadableQuantityCargoList.isEmpty()) {
			LoadableQuantityCargoList.forEach(item -> {
				CargoTobeLoaded cargoTobeLoaded = new CargoTobeLoaded();
				Optional.ofNullable(item.getCargoAbbreviation()).ifPresent(cargoTobeLoaded::setCargoName);
				Optional.ofNullable(item.getColorCode()).ifPresent(cargoTobeLoaded::setColorCode);
				Optional.ofNullable(item.getEstimatedAPI()).ifPresent(cargoTobeLoaded::setApi);
				Optional.ofNullable(item.getEstimatedTemp()).ifPresent(cargoTobeLoaded::setTemperature);
//				TODO loading port coming as a list expected only a string
//				Optional.ofNullable(item.getLoadingPorts()).ifPresent(cargoTobeLoaded::setLoadingPort);
				Optional.ofNullable(item.getCargoNominationQuantity()).ifPresent(cargoTobeLoaded::setNomination);
				Optional.ofNullable(item.getLoadableMT()).ifPresent(cargoTobeLoaded::setShipLoadable);
				Optional.ofNullable(item.getMaxTolerence()).ifPresent(cargoTobeLoaded::setTolerance);
				Optional.ofNullable(item.getDifferencePercentage()).ifPresent(cargoTobeLoaded::setDifference);
				Optional.ofNullable(item.getTimeRequiredForLoading())
						.ifPresent(cargoTobeLoaded::setTimeRequiredForLoading);
				Optional.ofNullable(item.getSlopQuantity()).ifPresent(cargoTobeLoaded::setSlopQuantity);
				cargoTobeLoadedList.add(cargoTobeLoaded);
			});
		}
		return cargoTobeLoadedList;
	}

	/**
	 * Build arrival tank params
	 * 
	 * @param excelData
	 * @param requestPayload
	 * @param
	 * @throws GenericServiceException
	 */
	private ArrivalDeparcherCondition getVesselConditionDetails(LoadingPlanResponse requestPayload,
			Integer conditionType) throws GenericServiceException {
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
	private void setCargoQuantityList(ArrivalDeparcherCondition vesselCondition, LoadingPlanResponse requestPayload,
			Integer conditionType) {
		List<CargoQuantity> cargoQuantitylist = new ArrayList<>();
		requestPayload.getCurrentPortCargos().forEach(item -> {
			CargoQuantity cargo = new CargoQuantity();
			Double totalQuantity = 0.0;
			Optional.ofNullable(item.getCargoAbbreviation()).ifPresent(cargo::setCargoName);
			Optional.ofNullable(item.getColorCode()).ifPresent(cargo::setColorCode);
			totalQuantity = requestPayload.getPlanStowageDetails().stream()
					.filter(psd -> psd.getConditionType().equals(conditionType)
							&& psd.getCargoId().equals(item.getCargoId()) && psd.getValueType().equals(2))
					.mapToDouble(i -> Double.parseDouble(i.getQuantityMT())).sum();
			cargo.setQuantity(totalQuantity.toString());
			cargoQuantitylist.add(cargo);
		});
		vesselCondition.setCargoDetails(cargoQuantitylist);
	}

	/**
	 * Ballast Tank and quantity details w.r.t condition type
	 * 
	 * @param vesselCondition
	 * @param requestPayload
	 * @param conditionType
	 * @throws GenericServiceException
	 */
	private void setBallastTanksWithQuantityForReport(ArrivalDeparcherCondition vesselCondition,
			LoadingPlanResponse requestPayload, Integer conditionType) {
		List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList = requestPayload.getPlanBallastDetails();
		List<TankCargoDetails> tankCargoDetailsList = new ArrayList<>();
		Double ballastWaterSum = 0.0;
		// Ballast rear tanks
		getBallastTankDetails(tankCargoDetailsList, requestPayload.getBallastRearTanks(), loadingPlanBallastDetailsList,
				conditionType);
		ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
		vesselCondition.setApt(findAptFpt(tankCargoDetailsList, "R"));
		tankCargoDetailsList.clear();
		// Ballast front tanks
		getBallastTankDetails(tankCargoDetailsList, requestPayload.getBallastFrontTanks(),
				loadingPlanBallastDetailsList, conditionType);
		ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
		vesselCondition.setFpt(findAptFpt(tankCargoDetailsList, "F"));
		tankCargoDetailsList.clear();
		// Ballast center tanks
		getBallastTankDetails(tankCargoDetailsList, requestPayload.getBallastCenterTanks(),
				loadingPlanBallastDetailsList, conditionType);
		ballastWaterSum += tankCargoDetailsList.stream().mapToDouble(i -> i.getQuantity()).sum();
		vesselCondition.setBallastTopTanks(getTankAgainstPossision(tankCargoDetailsList, "P"));
		vesselCondition.setBallastBottomTanks(getTankAgainstPossision(tankCargoDetailsList, "S"));
		vesselCondition.setBallastWaterSegregated(ballastWaterSum);

	}

	/**
	 * @param tankCargoDetailsList
	 * @param position
	 * @return
	 */
	private TankCargoDetails findAptFpt(List<TankCargoDetails> tankCargoDetailsList, String position) {

		if (position.equalsIgnoreCase("R")) {
			return tankCargoDetailsList.get(0);
		} else {
			Double totalQuantity = 0.0;
			for (TankCargoDetails item : tankCargoDetailsList) {
				totalQuantity += item.getQuantity();
			}
			tankCargoDetailsList.get(0).setQuantity(totalQuantity);
			return tankCargoDetailsList.get(0);
		}
	}

	/**
	 * Mapping quantity against each ballast tank
	 * 
	 * @param tankCargoDetailsList
	 * @param ballastTanks
	 * @param loadingPlanBallastDetailsList
	 */
	private void getBallastTankDetails(List<TankCargoDetails> tankCargoDetailsList, List<List<VesselTank>> ballastTanks,
			List<LoadingPlanBallastDetails> loadingPlanBallastDetailsList, Integer conditionType) {
		ballastTanks.forEach(tankList -> {
			tankList.forEach(tank -> {
				Optional<LoadingPlanBallastDetails> loadingPlanBallastDetailsOpt = loadingPlanBallastDetailsList
						.stream()
						.filter(item -> item.getTankId().equals(tank.getId())
								&& item.getConditionType().equals(conditionType) && item.getValueType().equals(2))
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
	private void setBallasTankValues(Optional<LoadingPlanBallastDetails> loadingPlanBallastDetailsOpt,
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
		tankCargoDetails.setColorCode("");
		tankCargoDetails.setUllage("0");
		tankCargoDetails.setFillingRatio(0L);
	}

	/**
	 * Cargo Tank and quantity details w.r.t condition type
	 * 
	 * @param vesselCondition
	 * @param requestPayload
	 * @param conditionType
	 * @throws GenericServiceException
	 */
	private void setCargoTanksWithQuantityForReport(ArrivalDeparcherCondition vesselCondition,
			LoadingPlanResponse requestPayload, Integer conditionType) throws GenericServiceException {
		setStabilityParamForReport(vesselCondition, requestPayload.getPlanStabilityParams(), conditionType);

		List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList = requestPayload.getLoadingInformation()
				.getCargoVesselTankDetails().getLoadableQuantityCargoDetails();
		List<TankCargoDetails> tankCargoDetailsList = new ArrayList<>();
		for (List<VesselTank> tankGroup : requestPayload.getCargoTanks()) {
			tankGroup.forEach(item -> {
				Optional<LoadingPlanStowageDetails> loadingPlanStowageDetails = requestPayload.getPlanStowageDetails()
						.stream()
						.filter(psd -> psd.getTankId().equals(item.getId())
								&& psd.getConditionType().equals(conditionType) && psd.getValueType().equals(2))
						.findAny();
				TankCargoDetails tankCargoDetails = new TankCargoDetails();
				tankCargoDetails.setTankName(item.getShortName());
				tankCargoDetails.setId(item.getId());
				// Adding cargo details in this tank
				setCargoTankValues(loadingPlanStowageDetails, loadableQuantityCargoDetailsList, tankCargoDetails);
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

	/**
	 * Populates cargo values in to each tank
	 * 
	 * @param tankCargoDetailsList
	 * @param loadingPlanStowageDetails
	 * @param loadableQuantityCargoDetailsList
	 */
	private void setCargoTankValues(Optional<LoadingPlanStowageDetails> loadingPlanStowageDetails,
			List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList, TankCargoDetails tankCargoDetails) {

		if (loadingPlanStowageDetails.isPresent()) {
			Optional.ofNullable(loadingPlanStowageDetails.get().getQuantityMT())
					.ifPresent(i -> tankCargoDetails.setQuantity(Double.parseDouble(i)));
			Optional.ofNullable(loadingPlanStowageDetails.get().getApi()).ifPresent(tankCargoDetails::setApi);
			Optional.ofNullable(loadingPlanStowageDetails.get().getTemperature())
					.ifPresent(tankCargoDetails::setTemperature);
			Optional.ofNullable(loadingPlanStowageDetails.get().getUllage()).ifPresent(tankCargoDetails::setUllage);
			// TODO filling ratio
			tankCargoDetails.setFillingRatio(0L);
			Optional<LoadableQuantityCargoDetails> loadableQuantityCargoDetails = loadableQuantityCargoDetailsList
					.stream().filter(i -> i.getCargoNominationId()
							.equals(loadingPlanStowageDetails.get().getCargoNominationId()))
					.findFirst();
			if (loadableQuantityCargoDetails.isPresent()) {
				Optional.ofNullable(loadableQuantityCargoDetails.get().getColorCode())
						.ifPresent(tankCargoDetails::setColorCode);
				Optional.ofNullable(loadableQuantityCargoDetails.get().getCargoAbbreviation())
						.ifPresent(tankCargoDetails::setCargoName);
			}
		} else {
			tankCargoDetails.setQuantity(0.0);
			tankCargoDetails.setCargoName("");
			tankCargoDetails.setColorCode("");
			tankCargoDetails.setUllage("0");
			tankCargoDetails.setFillingRatio(0L);
		}
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
		List<TankCargoDetails> tempList = tankDetailsList.stream().filter(item -> item.getTankName().endsWith(position))
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
	private void setStabilityParamForReport(ArrivalDeparcherCondition vesselCondition,
			List<LoadingPlanStabilityParam> planStabilityParams, Integer conditionType) {
		Optional<LoadingPlanStabilityParam> stabilityParam = planStabilityParams.stream()
				.filter(item -> item.getConditionType().equals(conditionType)).findAny();
		if (stabilityParam.isPresent()) {
			Optional.ofNullable(stabilityParam.get().getAftDraft()).ifPresent(vesselCondition::setDraftF);
			Optional.ofNullable(stabilityParam.get().getForeDraft()).ifPresent(vesselCondition::setDraftA);
			Optional.ofNullable(stabilityParam.get().getTrim()).ifPresent(vesselCondition::setTrim);
		}
	}

}
