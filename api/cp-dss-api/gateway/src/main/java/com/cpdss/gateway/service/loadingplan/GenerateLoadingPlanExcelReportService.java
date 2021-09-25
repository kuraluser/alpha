/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
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
import com.cpdss.gateway.domain.loadingplan.LoadingPlanReportRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanStabilityParam;
import com.cpdss.gateway.domain.loadingplan.TankCargoDetails;
import com.cpdss.gateway.domain.loadingplan.TankRow;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.utility.ExcelExportUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.io.IOUtils;
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

	public String TEMPLATES_FILE_LOCATION = "/reports/Vessel_{type}_Loading_Plan_Template.xlsx";
	public String OUTPUT_FILE_LOCATION = "/reports/Vessel_{id}_Loading_Plan_{voy}_{port}.xlsx";

	@Autowired
	LoadingPlanGrpcService loadingPlanGrpcService;

	@Autowired
	ExcelExportUtility excelExportUtil;

	@Autowired
	private VesselInfoService vesselInfoService;

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
	public byte[] generateLoadingPlanExcel(LoadingPlanReportRequest requestPayload, Long vesselId, Long voyageId,
			Long infoId, Long portRotationId, Boolean downloadRequired) throws GenericServiceException, IOException {
		// Building data required for Loading plan Excel
		LoadingPlanExcelDetails loadinPlanExcelDetails = getDataForExcel(requestPayload, vesselId, voyageId, infoId,
				portRotationId);
		// Setting file name of output file
		getFileName(vesselId, voyageId, loadinPlanExcelDetails.getSheetOne().getPortName());
		// Setting file name of input file based on vessel type
		getLoadingPlanTemplateForVessel(vesselId);
		// Getting data mapped and calling excel builder utility
		FileInputStream resultFileStream = new FileInputStream(
				excelExportUtil.generateExcel(loadinPlanExcelDetails, TEMPLATES_FILE_LOCATION, OUTPUT_FILE_LOCATION));

		if (resultFileStream != null) {
			// TODO put an entry in DB for Communication
		}

		// Returning Output file as byte array for local download
		if (downloadRequired) {
			return IOUtils.toByteArray(resultFileStream);
		}
		// No need to for local download if file generated form event trigger
		return null;
	}

	/**
	 * Get corresponding Loading plan template of a vessel based on its type.
	 * 
	 * @param vesselId
	 * @return
	 */
	private void getLoadingPlanTemplateForVessel(Long vesselId) {
		VesselReply reply = vesselInfoGrpcService
				.getVesselDetailByVesselId(VesselRequest.newBuilder().setVesselId(vesselId).build());
		if (!SUCCESS.equalsIgnoreCase(reply.getResponseStatus().getStatus())) {
			throw new GenericServiceException("Error in calling vessel service - While checking vessel Type",
					CommonErrorCodes.E_GEN_INTERNAL_ERR, HttpStatusCode.INTERNAL_SERVER_ERROR);
		}
		reply.getVesselTypeId();
		Optional.ofNullable(reply.getVesselTypeId())
				.ifPresent(TEMPLATES_FILE_LOCATION.replace("{type}", reply.getVesselTypeId().toString()));
	}

	/**
	 * Get fully qualified name of output file
	 */
	private void getFileName(Long vesselId, Long voyageId, String portName) {
		OUTPUT_FILE_LOCATION.replace("{id}", vesselId.toString()).replace("{voy}", voyageId.toString())
				.replace("{port}", portName);
	}

	/**
	 * Method to get data mapped for excel sheets
	 * 
	 * @param requestPayload
	 * @param vesselId
	 * @param voyageId
	 * @param infoId
	 * @param portRotationId
	 * @return
	 * @throws GenericServiceException
	 */
	@SuppressWarnings("unchecked")
	private LoadingPlanExcelDetails getDataForExcel(LoadingPlanReportRequest requestPayload, Long vesselId,
			Long voyageId, Long infoId, Long portRotationId) throws GenericServiceException {
		LoadingPlanExcelDetails excelData = new LoadingPlanExcelDetails();
		excelData.setSheetOne(buildSheetOne(requestPayload, vesselId, voyageId, infoId, portRotationId));
//		excelData.setSheetTwo(buildSheetTwo(requestPayload, vesselId, voyageId, infoId, portRotationId));
		return excelData;
	}

	/**
	 * Build data model for Sheet 1
	 * 
	 * @return sheet one
	 * @throws GenericServiceException
	 */
	private LoadingPlanExcelLoadingPlanDetails buildSheetOne(LoadingPlanReportRequest requestPayload, Long vesselId,
			Long voyageId, Long infoId, Long portRotationId) throws GenericServiceException {
		// TODO call getloading plan call here and get the response
		LoadingPlanExcelLoadingPlanDetails sheetOne = new LoadingPlanExcelLoadingPlanDetails();
		getBasicVesselDetails(sheetOne, vesselId, voyageId, infoId, portRotationId);
		// Condition type 1 is arrival
		sheetOne.setArrivalCondition(getVesselConditionDetails(requestPayload, 1));
		sheetOne.setDeparcherCondition(getVesselConditionDetails(requestPayload, 2));
		sheetOne.setCargoTobeLoaded(getCargoTobeLoadedDetails(requestPayload));
		sheetOne.setBerthInformation(getBerthInfoDetails(requestPayload));
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
	 * @param requestPayload
	 * @return
	 */
	private List<BerthInformation> getBerthInfoDetails(LoadingPlanReportRequest requestPayload) {
		List<BerthInformation> berthInfoList = new ArrayList<>();
		List<BerthDetails> berthDetailsList = requestPayload.getLoadingInformation().getBerthDetails()
				.getSelectedBerths();
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
				Optional.ofNullable(item.getItemsToBeAgreedWith())
						.ifPresent(berthInformation::setItemsAgreedWithTerminal);
				berthInfoList.add(berthInformation);
			});
		}
		return berthInfoList;
	}
	
	/** Calling port GROC for getting Port name
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
	private List<CargoTobeLoaded> getCargoTobeLoadedDetails(LoadingPlanReportRequest requestPayload) {
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
	private ArrivalDeparcherCondition getVesselConditionDetails(LoadingPlanReportRequest requestPayload,
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
	private void setCargoQuantityList(ArrivalDeparcherCondition vesselCondition,
			LoadingPlanReportRequest requestPayload, Integer conditionType) {
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
			LoadingPlanReportRequest requestPayload, Integer conditionType) {
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
		}
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
			LoadingPlanReportRequest requestPayload, Integer conditionType) throws GenericServiceException {
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
