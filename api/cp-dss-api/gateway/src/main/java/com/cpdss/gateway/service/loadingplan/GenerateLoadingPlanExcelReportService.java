/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanExcelLoadingPlanDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanReportRequest;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class GenerateLoadingPlanExcelReportService {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

	public final String TEMPLATE = "/reports/Vessel_{id}_Loading_Plan_Template.xlsx";
	public final String TEMP_LOCATION = "Temp_out.xlsx";

	@Autowired
	LoadingPlanGrpcService loadingPlanGrpcService;

	@GrpcClient("vesselInfoService")
	private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
	
	/** Method to read data from request and Stamp in existing template
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
		OutputStream outStream = new FileOutputStream("Temp_out.xlsx");

		Map<String, Object> dataMap = getDataMappedForExcel(requestPayload,vesselId,voyageId,infoId,portRotationId);

		try (InputStream input = this.getClass().getResourceAsStream(getLoadingPlanTemplateForVessel(vesselId))) {
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
		FileInputStream fis = new FileInputStream(new File(TEMP_LOCATION));
		return IOUtils.toByteArray(fis);
	}

	private Context getContext(Map<String, Object> data) {
		Context context = new Context();

		for (Entry<String, Object> element : data.entrySet()) {
			context.putVar(element.getKey(), element.getValue());
		}
		return context;
	}

	private String getLoadingPlanTemplateForVessel(Long vesselId) {
		log.info("Generating file {} ", TEMPLATE.replace("{id}", vesselId.toString()));
		return TEMPLATE.replace("{id}", vesselId.toString());
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
	 * @param requestPayload
	 * @param vesselId
	 * @param voyageId
	 * @param infoId
	 * @param portRotationId
	 * @return
	 * @throws GenericServiceException
	 */
	private Map<String, Object> getDataMappedForExcel(LoadingPlanReportRequest requestPayload, Long vesselId,
			Long voyageId, Long infoId, Long portRotationId) throws GenericServiceException {
		LoadingPlanExcelDetails excelData = new LoadingPlanExcelDetails();
		getBasicVesselDetails(excelData.getSheetOne(),requestPayload,vesselId,voyageId,infoId,portRotationId);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> dataMap = mapper.convertValue(excelData, Map.class);
		dataMap.entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " : " + entry.getValue());
		});
		return dataMap;
	}

	/**
	 * @param loadingPlanExcelLoadingPlanDetails
	 * @param requestPayload
	 * @param vesselId
	 * @param voyageId
	 * @param infoId
	 * @param portRotationId
	 * @throws GenericServiceException
	 */
	private void getBasicVesselDetails(LoadingPlanExcelLoadingPlanDetails loadingPlanExcelLoadingPlanDetails,
			com.cpdss.gateway.domain.loadingplan.LoadingPlanReportRequest requestPayload, Long vesselId, Long voyageId,
			Long infoId, Long portRotationId) throws GenericServiceException {
		VoyageResponse activeVoyage = this.loadingPlanGrpcService.getActiveVoyageDetails(vesselId);
		Optional<PortRotation> portRotation = activeVoyage.getPortRotations().stream()
				.filter(v -> v.getId().equals(portRotationId)).findFirst();
		VesselInfo.VesselReply vesselReply = vesselInfoGrpcService
				.getVesselDetailByVesselId(VesselInfo.VesselRequest.newBuilder().setVesselId(vesselId).build());
		VesselInfo.VesselDetail vesselDetail = vesselReply.getVesselsList().stream().findFirst()
				.orElseThrow(() -> new GenericServiceException(
						String.format("Vessel details not found for VesselId: %d", vesselId),
						CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST));

//		excelData.setVesselId(vesselId);
		loadingPlanExcelLoadingPlanDetails.setVesselName(vesselDetail.getName());
		loadingPlanExcelLoadingPlanDetails.setMaster(String.valueOf(vesselDetail.getCaptainId()));
		loadingPlanExcelLoadingPlanDetails.setCo(String.valueOf(vesselDetail.getCheifOfficerId()));
//		excelData.setVoyageId(voyageId);
		loadingPlanExcelLoadingPlanDetails.setVoyageNumber(activeVoyage.getVoyageNumber());
		portRotation.ifPresent(item -> loadingPlanExcelLoadingPlanDetails.setPortName(String.valueOf(item.getPortId())));
		portRotation.ifPresent(item -> loadingPlanExcelLoadingPlanDetails.setEta(item.getEta()));
		portRotation.ifPresent(item -> loadingPlanExcelLoadingPlanDetails.setEtd(item.getEtd()));

//		excelData.setArrivalCondition(buildArrivalDeparcherConditionForReport(requestPayload, 1));
//		excelData.setDeparcherCondition(buildArrivalDeparcherConditionForReport(requestPayload, 2));
	}

//	private ArrivalDeparcherCondition buildArrivalDeparcherConditionForReport(
//			com.cpdss.gateway.domain.loadingplan.LoadingPlanReportRequest requestPayload, Integer conditionType)
//			throws GenericServiceException {
//		ArrivalDeparcherCondition.Builder builder = ArrivalDeparcherCondition.newBuilder();
//		builder.setStabilityParams(setStabilityParamForReport(requestPayload.getPlanStabilityParams(), conditionType));
//		setCargoDetailsForReport(builder, requestPayload.getLoadingInformation(), conditionType);
//		setCargoTanksWithQuantityForReport(builder, requestPayload.getCargoTanks(),
//				requestPayload.getPlanStowageDetails(), requestPayload.getLoadingInformation(), conditionType);
//		setBallastTanksWithQuantityForReport(builder, requestPayload.getBallastCenterTanks(),
//				requestPayload.getPlanBallastDetails(), conditionType);
//		setBallastTanksWithQuantityForReport(builder, requestPayload.getBallastFrontTanks(),
//				requestPayload.getPlanBallastDetails(), conditionType);
//		setBallastTanksWithQuantityForReport(builder, requestPayload.getBallastRearTanks(),
//				requestPayload.getPlanBallastDetails(), conditionType);
//
//		return builder.build();
//	}
//
//	private void setCargoDetailsForReport(Builder builder, LoadingInformation loadingInformation,
//			Integer conditionType) {
//		List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList = loadingInformation
//				.getCargoVesselTankDetails().getLoadableQuantityCargoDetails();
//		loadableQuantityCargoDetailsList.forEach(item -> {
//			LoadingDelayCargos.Builder loadingDelayCargos = LoadingDelayCargos.newBuilder();
//			loadingDelayCargos.setCargoId(item.getCargoId());
//			loadingDelayCargos.setCargoShorName(item.getCargoAbbreviation());
//			loadingDelayCargos.setColourCode(item.getColorCode());
//		});
//
//	}
//
//	private void setBallastTanksWithQuantityForReport(ArrivalDeparcherCondition.Builder builder,
//			List<List<VesselTank>> ballastTanks, List<LoadingPlanBallastDetails> planBallastDetails,
//			Integer conditionType) {
//		for (List<VesselTank> tankGroup : ballastTanks) {
//			tankGroup.forEach(item -> {
//				TanksWithQuantity.Builder tankDetailBuilder = TanksWithQuantity.newBuilder();
//				setTankDetailBuilder(tankDetailBuilder, item);
//				setBallastQuantity(tankDetailBuilder, planBallastDetails, item, conditionType);
//				builder.addBallastTanksWithQuantity(tankDetailBuilder.build());
//			});
//
//		}
//	}
//
//	private void setBallastQuantity(TanksWithQuantity.Builder tankDetailBuilder,
//			List<LoadingPlanBallastDetails> planBallastDetailsList, VesselTank item, Integer conditionType) {
//		Optional<LoadingPlanBallastDetails> planBallastDetails = planBallastDetailsList.stream()
//				.filter(pbd -> pbd.getTankId().equals(item.getId()) && pbd.getConditionType().equals(conditionType)
//						&& pbd.getValueType().equals(2))
//				.findAny();
//		if (planBallastDetails.isPresent()) {
//			BallastQuantityDetails.Builder ballastQuantityDetailsBuilder = BallastQuantityDetails.newBuilder();
//			Optional.ofNullable(planBallastDetails.get().getTankId())
//					.ifPresent(ballastQuantityDetailsBuilder::setTankId);
//			Optional.ofNullable(planBallastDetails.get().getTankName())
//					.ifPresent(ballastQuantityDetailsBuilder::setTankName);
//			Optional.ofNullable(planBallastDetails.get().getQuantityMT())
//					.ifPresent(ballastQuantityDetailsBuilder::setQuantityMT);
//			Optional.ofNullable(planBallastDetails.get().getQuantityM3())
//					.ifPresent(ballastQuantityDetailsBuilder::setQuantityM3);
//			Optional.ofNullable(planBallastDetails.get().getSounding())
//					.ifPresent(ballastQuantityDetailsBuilder::setSounding);
//			Optional.ofNullable(planBallastDetails.get().getConditionType())
//					.ifPresent(ballastQuantityDetailsBuilder::setConditionType);
//			Optional.ofNullable(planBallastDetails.get().getValueType())
//					.ifPresent(ballastQuantityDetailsBuilder::setValueType);
//			Optional.ofNullable(planBallastDetails.get().getColorCode())
//					.ifPresent(ballastQuantityDetailsBuilder::setColorCode);
//			tankDetailBuilder.setBallastQuantityDetails(ballastQuantityDetailsBuilder.build());
//		}
//	}
//
//	private void setTankDetailBuilder(TanksWithQuantity.Builder tankDetailBuilder, VesselTank item) {
//		Optional.ofNullable(item.getId()).ifPresent(tankDetailBuilder::setId);
//		Optional.ofNullable(item.getCategoryId()).ifPresent(tankDetailBuilder::setCategoryId);
//		Optional.ofNullable(item.getCategoryName()).ifPresent(tankDetailBuilder::setCategoryName);
//		Optional.ofNullable(item.getName()).ifPresent(tankDetailBuilder::setName);
//		Optional.ofNullable(item.getFrameNumberFrom()).ifPresent(tankDetailBuilder::setFrameNumberFrom);
//		Optional.ofNullable(item.getFrameNumberTo()).ifPresent(tankDetailBuilder::setFrameNumberTo);
//		Optional.ofNullable(item.getShortName()).ifPresent(tankDetailBuilder::setShortName);
//		Optional.ofNullable(item.getFillCapcityCubm())
//				.ifPresent(i -> tankDetailBuilder.setFillCapcityCubm(i.longValue()));
//		Optional.ofNullable(item.getDensity()).ifPresent(i -> tankDetailBuilder.setDensity(i.longValue()));
//		Optional.ofNullable(item.getGroup()).ifPresent(tankDetailBuilder::setGroup);
//		Optional.ofNullable(item.getOrder()).ifPresent(tankDetailBuilder::setOrder);
//		tankDetailBuilder.setIsSlopTank(item.isSlopTank());
//	}
//
//	private void setCargoTanksWithQuantityForReport(ArrivalDeparcherCondition.Builder builder,
//			List<List<VesselTank>> cargoTanks, List<LoadingPlanStowageDetails> planStowageDetails,
//			LoadingInformation loadingInformation, Integer conditionType) throws GenericServiceException {
//		for (List<VesselTank> tankGroup : cargoTanks) {
//			tankGroup.forEach(item -> {
//				TanksWithQuantity.Builder tankDetailBuilder = TanksWithQuantity.newBuilder();
//				setTankDetailBuilder(tankDetailBuilder, item);
//
//				Optional<LoadingPlanStowageDetails> loadingPlanStowageDetails = planStowageDetails.stream()
//						.filter(psd -> psd.getTankId().equals(item.getId())
//								&& psd.getConditionType().equals(conditionType) && psd.getValueType().equals(2))
//						.findAny();
//				List<LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList = loadingInformation
//						.getCargoVesselTankDetails().getLoadableQuantityCargoDetails();
//				if (loadingPlanStowageDetails.isPresent()) {
//					CargoQuantityDetails.Builder cargoQuantityDetailsBuilder = CargoQuantityDetails.newBuilder();
//					Optional.ofNullable(loadingPlanStowageDetails.get().getQuantityMT())
//							.ifPresent(cargoQuantityDetailsBuilder::setQuantityMT);
//					Optional.ofNullable(loadingPlanStowageDetails.get().getQuantityM3())
//							.ifPresent(cargoQuantityDetailsBuilder::setQuantityM3);
//					Optional.ofNullable(loadingPlanStowageDetails.get().getApi())
//							.ifPresent(cargoQuantityDetailsBuilder::setApi);
//					Optional.ofNullable(loadingPlanStowageDetails.get().getTemperature())
//							.ifPresent(cargoQuantityDetailsBuilder::setTemperature);
//					Optional.ofNullable(loadingPlanStowageDetails.get().getUllage())
//							.ifPresent(cargoQuantityDetailsBuilder::setUllage);
//					Optional<LoadableQuantityCargoDetails> loadableQuantityCargoDetails = loadableQuantityCargoDetailsList
//							.stream().filter(i -> i.getCargoNominationId()
//									.equals(loadingPlanStowageDetails.get().getCargoNominationId()))
//							.findFirst();
//					if (loadableQuantityCargoDetails.isPresent()) {
//						Optional.ofNullable(loadableQuantityCargoDetails.get().getCargoId())
//								.ifPresent(cargoQuantityDetailsBuilder::setCargoId);
//						Optional.ofNullable(loadableQuantityCargoDetails.get().getColorCode())
//								.ifPresent(cargoQuantityDetailsBuilder::setColorCode);
//						Optional.ofNullable(loadableQuantityCargoDetails.get().getCargoAbbreviation())
//								.ifPresent(cargoQuantityDetailsBuilder::setAbbreviation);
//					}
//					tankDetailBuilder.setCargoQuantityDetails(cargoQuantityDetailsBuilder.build());
//				}
//				builder.addCargoTanksWithQuantity(tankDetailBuilder.build());
//			});
//		}
//	}
//
//	private LoadingPlanStabilityParameters setStabilityParamForReport(
//			List<LoadingPlanStabilityParam> planStabilityParams, Integer conditionType) {
//		LoadingPlanStabilityParameters.Builder loadingPlanStabilityParameters = LoadingPlanStabilityParameters
//				.newBuilder();
//		Optional<LoadingPlanStabilityParam> stabilityParam = planStabilityParams.stream()
//				.filter(item -> item.getConditionType().equals(conditionType)).findAny();
//		if (stabilityParam.isPresent()) {
//			Optional.ofNullable(stabilityParam.get().getAftDraft())
//					.ifPresent(loadingPlanStabilityParameters::setAftDraft);
//			Optional.ofNullable(stabilityParam.get().getForeDraft())
//					.ifPresent(loadingPlanStabilityParameters::setForeDraft);
//			Optional.ofNullable(stabilityParam.get().getTrim()).ifPresent(loadingPlanStabilityParameters::setTrim);
//		}
//		return loadingPlanStabilityParameters.build();
//	}

}
