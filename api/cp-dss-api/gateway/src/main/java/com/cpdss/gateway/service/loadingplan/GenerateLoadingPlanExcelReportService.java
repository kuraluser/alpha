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
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelDetails;
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
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParamsOfLoadingSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategory;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategoryForSequence;
import com.cpdss.gateway.domain.loadingplan.sequence.TankWithSequenceUllage;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingPlanServiceImpl;
import com.cpdss.gateway.utility.ExcelExportUtility;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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
	public static String SHEET_NAMES[] = { "CRUD - 021 pg1", "CRUD - 021 pg2", "CRUD - 021 pg3" };

	@Value("${gateway.attachement.rootFolder}")
	private String rootFolder;

	@Autowired
	LoadingPlanGrpcService loadingPlanGrpcService;
	@Autowired
	ExcelExportUtility excelExportUtil;
	@Autowired
	private VesselInfoService vesselInfoService;
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
	@SuppressWarnings("unchecked")
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
//		excelData.setSheetTwo(buildSheetTwo(requestPayload, vesselId, voyageId, infoId, portRotationId));
		excelData.setSheetThree(buildSheetThree(vesselId, voyageId, infoId, portRotationId));
//		threadPool.shutdown();
		return excelData;
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
		sheetThree.setCargoTanks(
				getCargoTanks(loadingSequenceResponse.getCargoTankCategories(), loadingSequenceResponse.getCargos()));
		sheetThree.setBallastTanks(loadingSequenceResponse.getBallastTankCategories());
		sheetThree.setStabilityParams(getStabilityParams(loadingSequenceResponse.getStabilityParams()));
		sheetThree.setLoadingRates(getLoadingRate(loadingSequenceResponse.getCargoLoadingRates(),
				loadingSequenceResponse.getStageTickPositions()));
		sheetThree.setTickPoints(getTickPoints(loadingSequenceResponse.getMinXAxisValue(),
				loadingSequenceResponse.getStageTickPositions()));
		sheetThree.setCargoTankUllage(getCargoTankUllage(loadingSequenceResponse.getStageTickPositions(),
				loadingSequenceResponse.getCargos(), loadingSequenceResponse.getCargoTankCategories()));
		sheetThree.setBallastTankUllage(getBallastTankUllage(loadingSequenceResponse.getStageTickPositions(),
				loadingSequenceResponse.getBallasts(), loadingSequenceResponse.getBallastTankCategories()));
		log.info("Building sheet 3 : completed");
		return sheetThree;
	}

	/**
	 * Get sounding of ballast tanks in each tick position
	 * 
	 * @param stageTickPositions
	 * @param ballasts
	 * @param ballastTankCategories
	 * @return
	 */
	private List<TankWithSequenceUllage> getBallastTankUllage(Set<Long> stageTickPositions, List<Ballast> ballasts,
			List<TankCategory> ballastTankCategories) {
		List<TankWithSequenceUllage> tanksUllageList = new ArrayList<>();
		// Getting ullages on each tick position of each tank
		for (TankCategory tank : ballastTankCategories) {
			TankWithSequenceUllage tanksUllageObj = new TankWithSequenceUllage();
			tanksUllageObj.setTankId(tank.getId());
			List<Ballast> ballastListOfpresentTank = ballasts.stream()
					.filter(cargo -> cargo.getTankId().equals(tank.getId())).collect(Collectors.toList());
			List<String> ullages = new ArrayList<>();
			stageTickPositions.forEach(position -> {
				Optional<Ballast> ballastMatch = ballastListOfpresentTank.stream()
						.filter(cargo -> cargo.getStart().equals(position)).findFirst();
				if (ballastMatch.isPresent()) {
					ullages.add(ballastMatch.get().getSounding().toString());
				} else {
					ullages.add("");
				}
			});
			tanksUllageObj.setUllage(ullages);
			tanksUllageList.add(tanksUllageObj);
		}
		return tanksUllageList;
	}

	/**
	 * Get ullage of ballast tanks in each tick position
	 * 
	 * @param loadingSequenceResponse
	 * @param tankCategories
	 * @return
	 */
	private List<TankWithSequenceUllage> getCargoTankUllage(Set<Long> stageTickPositions, List<Cargo> cargos,
			List<TankCategory> tankCategories) {
		List<TankWithSequenceUllage> tanksUllageList = new ArrayList<>();
		// Getting ullages on each tick position of each tank
		for (TankCategory tank : tankCategories) {
			TankWithSequenceUllage tanksUllageObj = new TankWithSequenceUllage();
			tanksUllageObj.setTankId(tank.getId());
			List<Cargo> cargoListOfpresentTank = cargos.stream().filter(cargo -> cargo.getTankId().equals(tank.getId()))
					.collect(Collectors.toList());
			List<String> ullages = new ArrayList<>();
			stageTickPositions.forEach(position -> {
				Optional<Cargo> cargoMatch = cargoListOfpresentTank.stream()
						.filter(cargo -> cargo.getStart().equals(position)).findFirst();
				if (cargoMatch.isPresent()) {
					ullages.add(cargoMatch.get().getUllage().toString());
				} else {
					ullages.add("");
				}
			});
			tanksUllageObj.setUllage(ullages);
			tanksUllageList.add(tanksUllageObj);
		}
		return tanksUllageList;
	}

	/**
	 * Convert tick position form millisecond to hours
	 * 
	 * @param minXAxisValue
	 * @param stageTickPositions
	 * @return
	 */
	private List<Long> getTickPoints(Long minXAxisValue, Set<Long> stageTickPositions) {
		List<Long> tickPoints = new ArrayList<>();
		stageTickPositions.stream().forEach(xValue -> {
			tickPoints.add((TimeUnit.MILLISECONDS.toHours(xValue - minXAxisValue)) % 24);
		});
		return tickPoints.stream().sorted().collect(Collectors.toList());
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
		for (TankCategory item : cargoTankCategories) {
			TankCategoryForSequence tankCategoryObj = new TankCategoryForSequence();
			tankCategoryObj.setId(item.getId());
			tankCategoryObj.setTankName(item.getTankName());
			tankCategoryObj.setUllage(item.getUllage());
			tankCategoryObj.setDisplayOrder(item.getDisplayOrder());
			Optional<Cargo> cargoOpt = cargos.stream().filter(cargo -> cargo.getTankId().equals(item.getId()))
					.findFirst();
			if (cargoOpt.isEmpty()) {
				tankCategoryObj.setCargoName(cargoOpt.get().getName());
				tankCategoryObj.setColorCode(cargoOpt.get().getColor());
			}
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

		cargoLoadingRates.stream().forEach(item -> {
			int index = 0;
			for (Long position : stageTickPositions) {
				if (position >= item.getStartTime() && position <= item.getEndTime()) {
					LoadingRateForSequence loadingRate = new LoadingRateForSequence();
					loadingRate.setPosision(index);
					String rateString = "";
					item.getLoadingRates().stream().forEach(rate -> {
						rateString.concat("/").concat(rate.toString());
					});
					loadingRate.setRate(rateString);
					loadingRates.add(loadingRate);
					break;
				}
				index++;
			}
		});
		return loadingRates.stream().sorted(Comparator.comparingInt(LoadingRateForSequence::getPosision))
				.collect(Collectors.toList());
	}

	/**
	 * @param stabilityParams
	 * @return
	 */
	private StabilityParamsOfLoadingSequence getStabilityParams(List<StabilityParam> stabilityParams) {
		StabilityParamsOfLoadingSequence sequenceStability = new StabilityParamsOfLoadingSequence();
		List<String> fwList = new ArrayList<>();
		List<String> afterList = new ArrayList<>();
		List<String> trimList = new ArrayList<>();
		List<String> gmList = new ArrayList<>();
		List<String> sfList = new ArrayList<>();
		List<String> bmList = new ArrayList<>();
		for (StabilityParam stabilityParam : stabilityParams) {
			switch (stabilityParam.getName()) {
			case "fore_draft":
				stabilityParam.getData().forEach(i -> {
					fwList.add(i.get(1).toString());
				});
				break;
			case "aft_draft":
				stabilityParam.getData().forEach(i -> {
					afterList.add(i.get(1).toString());
				});
				break;
			case "trim":
				stabilityParam.getData().forEach(i -> {
					trimList.add(i.get(1).toString());
				});
				break;
			case "gm":
				stabilityParam.getData().forEach(i -> {
					gmList.add(i.get(1).toString());
				});
				break;
			case "sf":
				stabilityParam.getData().forEach(i -> {
					sfList.add(i.get(1).toString());
				});
				break;
			case "bm":
				stabilityParam.getData().forEach(i -> {
					bmList.add(i.get(1).toString());
				});
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
		sequenceStability.setShearingForce(sfList);
		return sequenceStability;
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
				// Adding cargo details in this tank
				setCargoTankValues(loadingPlanStowageDetails, loadableQuantityCargoDetailsList, tankCargoDetails);
				tankCargoDetailsList.add(tankCargoDetails);
			});
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
