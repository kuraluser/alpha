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
import com.cpdss.gateway.domain.loadingplan.CargoQuantity;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenerateLoadingPlanExcelReportService {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

	public final String TEMPLATE = "/reports/Vessel_{id}_Loading_Plan_Template.xlsx";
	public final String OUT_LOCATION = "/reports/Vessel_{id}_Loading_Plan_Report.xlsx";

	@Value("${gateway.attachement.rootFolder}")
	private String rootFolder;

	@Autowired
	LoadingPlanGrpcService loadingPlanGrpcService;

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
	 * @return
	 * @throws GenericServiceException
	 * @throws IOException
	 */
	public byte[] generateExcel(LoadingPlanReportRequest requestPayload, Long vesselId, Long voyageId, Long infoId,
			Long portRotationId) throws GenericServiceException, IOException {
		log.info("Inside generateExcel method");
		String outFile = rootFolder + getLoadingPlanTemplateForVessel(OUT_LOCATION, vesselId);
		OutputStream outStream = new FileOutputStream(outFile);
		Map<String, Object> dataMap = getDataMappedForExcel(requestPayload, vesselId, voyageId, infoId, portRotationId);

		// TODO identify template from vessel Type
		try (InputStream input = this.getClass()
				.getResourceAsStream(getLoadingPlanTemplateForVessel(TEMPLATE, vesselId))) {
			if (input != null) {
				Context context = getContext(dataMap);
				// Stamping values into excel template
				JxlsHelper.getInstance().processTemplate(input, outStream, context);
			} else {
				log.info("No input template found for Vessel Id : {}", vesselId);
				throw new GenericServiceException("Excel generation failed : No input template found",
						CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException("Excel generation failed" + e.getMessage(),
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		} finally {
			closeAndFlushOutput(outStream);
		}

		// Returning Output file as byte array
		FileInputStream fis = new FileInputStream(new File(outFile));
		// TODO put an entry in DB for Communication
		return IOUtils.toByteArray(fis);
	}

	/**
	 * @param data
	 * @return
	 */
	private Context getContext(Map<String, Object> data) {
		Context context = new Context();

		for (Entry<String, Object> element : data.entrySet()) {
			context.putVar(element.getKey(), element.getValue());
		}
		return context;
	}

	/**
	 * @param location
	 * @param vesselId
	 * @return
	 */
	private String getLoadingPlanTemplateForVessel(String location, Long vesselId) {
		log.info("Generating file location {} ", location.replace("{id}", vesselId.toString()));
		return location.replace("{id}", vesselId.toString());
	}

	private void closeAndFlushOutput(OutputStream outStream) {
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	private Map<String, Object> getDataMappedForExcel(LoadingPlanReportRequest requestPayload, Long vesselId,
			Long voyageId, Long infoId, Long portRotationId) throws GenericServiceException {
		LoadingPlanExcelDetails excelData = new LoadingPlanExcelDetails();
		LoadingPlanExcelLoadingPlanDetails sheetOne = new LoadingPlanExcelLoadingPlanDetails();
		getBasicVesselDetails(sheetOne, vesselId, voyageId, infoId, portRotationId);
		// Condition type 1 is arrival
		sheetOne.setArrivalCondition(getVesselConditionDetails(requestPayload, 1));
		sheetOne.setDeparcherCondition(getVesselConditionDetails(requestPayload, 2));
		excelData.setSheetOne(sheetOne);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> dataMap = mapper.convertValue(excelData, Map.class);
		dataMap.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " : " + entry.getValue()); // TODO remove
		});
		return dataMap;
	}

	/**
	 * Fecth basic vessel and Port details
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
	 * @param build
	 * @return PortReply
	 */
	public PortInfo.PortReply getPortInfo(PortInfo.GetPortInfoByPortIdsRequest build) {
		return portInfoGrpcService.getPortInfoByPortIds(build);
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
		setCargoQuantityList(vesselCondition, requestPayload.getCurrentPortCargos(), conditionType);
		return vesselCondition;

	}

	/**
	 * Get list of cargo in a port
	 * 
	 * @param vesselCondition
	 * @param currentPortCargos
	 * @param conditionType
	 */
	private void setCargoQuantityList(ArrivalDeparcherCondition vesselCondition,
			List<LoadableQuantityCargoDetails> currentPortCargos, Integer conditionType) {
		List<CargoQuantity> list = new ArrayList<>();
		currentPortCargos.forEach(item -> {
			CargoQuantity cargo = new CargoQuantity();
			Optional.ofNullable(item.getCargoAbbreviation()).ifPresent(cargo::setCargoName);
			Optional.ofNullable(item.getColorCode()).ifPresent(cargo::setColorCode);
			Optional.ofNullable(item.getLoadableMT()).ifPresent(cargo::setQuantity);
			list.add(cargo);
		});
		vesselCondition.setCargoDetails(list);
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
