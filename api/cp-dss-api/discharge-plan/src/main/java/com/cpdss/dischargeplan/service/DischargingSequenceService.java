/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.DischargePlanPortWiseDetails;
import com.cpdss.common.generated.discharge_plan.DischargeSequenceReply;
import com.cpdss.common.generated.discharge_plan.DischargingRate;
import com.cpdss.common.generated.discharge_plan.DischargingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.EductorOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.BallastOperation;
import com.cpdss.dischargeplan.entity.CargoDischargingRate;
import com.cpdss.dischargeplan.entity.DeballastingRate;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanCommingleDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.entity.DischargingPlanStowageDetails;
import com.cpdss.dischargeplan.entity.DischargingSequenceStabilityParameters;
import com.cpdss.dischargeplan.entity.EductionOperation;
import com.cpdss.dischargeplan.repository.BallastOperationRepository;
import com.cpdss.dischargeplan.repository.CargoDischargingRateRepository;
import com.cpdss.dischargeplan.repository.DeballastingRateRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargingPlanBallastDetailsRepository;
import com.cpdss.dischargeplan.repository.DischargingPlanCommingleDetailsRepository;
import com.cpdss.dischargeplan.repository.DischargingPlanPortWiseDetailsRepository;
import com.cpdss.dischargeplan.repository.DischargingPlanRobDetailsRepository;
import com.cpdss.dischargeplan.repository.DischargingPlanStabilityParametersRepository;
import com.cpdss.dischargeplan.repository.DischargingPlanStowageDetailsRepository;
import com.cpdss.dischargeplan.repository.DischargingSequenceRepository;
import com.cpdss.dischargeplan.repository.DischargingSequenceStabiltyParametersRepository;
import com.cpdss.dischargeplan.repository.EductionOperationRepository;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class DischargingSequenceService {
	@Autowired
	DischargeInformationRepository dischargingInformationRepository;
	@Autowired
	DischargingSequenceRepository dischargingSequenceRepository;
	@Autowired
	DeballastingRateRepository deBallastingRateRepository;
	@Autowired
	CargoDischargingRateRepository cargoLoadingRateRepository;
	@Autowired
	BallastOperationRepository ballastOperationRepository;
	@Autowired
	DischargingPlanPortWiseDetailsRepository portWiseDetailsRepository;
	@Autowired
	DischargingPlanBallastDetailsRepository ballastDetailsRepository;
	@Autowired
	DischargingPlanRobDetailsRepository robDetailsRepository;
	@Autowired
	DischargingPlanStabilityParametersRepository stabilityParametersRepository;
	@Autowired
	DischargingPlanStowageDetailsRepository stowageDetailsRepository;
	@Autowired
	EductionOperationRepository eductionOperationRepository;

	@Autowired
	DischargingSequenceStabiltyParametersRepository dischargingSequenceStabilityParamsRepository;

	@Autowired
	DischargingPlanCommingleDetailsRepository commingleDetailsRepository;

	@GrpcClient("loadableStudyService")
	LoadableStudyServiceBlockingStub loadableStudyGrpcService;

	/**
	 * Get Sequences
	 *
	 * @param request
	 * @param builder
	 * @throws Exception
	 */
	public void getDischargingSequences(LoadingSequenceRequest request, DischargeSequenceReply.Builder builder)
			throws Exception {
		log.info("Fetching Discharging sequences of discharging information {}", request.getLoadingInfoId());
		Optional<DischargeInformation> dischargingInfoOpt = dischargingInformationRepository
				.findByIdAndIsActiveTrue(request.getLoadingInfoId());
		if (dischargingInfoOpt.isEmpty()) {
			log.info("Cannot find discharging information with id {}", request.getLoadingInfoId());
			throw new GenericServiceException("Could not find discharging information " + request.getLoadingInfoId(),
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		}

		String startDate = this.getStartDate(dischargingInfoOpt.get().getPortRotationXid());
		builder.setStartDate(startDate);
		builder.setInterval(dischargingInfoOpt.get().getDischargingStagesDuration().getDuration());
		builder.setVesselId(dischargingInfoOpt.get().getVesselXid());
		builder.setVoyageId(dischargingInfoOpt.get().getVoyageXid());
		builder.setPortId(dischargingInfoOpt.get().getPortXid());
		builder.setDischargePatternId(dischargingInfoOpt.get().getDischargingPatternXid());
		builder.setPortRotationId(dischargingInfoOpt.get().getPortRotationXid());
		List<com.cpdss.dischargeplan.entity.DischargingSequence> dischargingSequences = dischargingSequenceRepository
				.findByDischargeInformationAndIsActiveOrderBySequenceNumber(dischargingInfoOpt.get(), true);
		buildDischargingSequences(dischargingSequences, builder);

		List<DischargingSequenceStabilityParameters> stabilityParameters = dischargingSequenceStabilityParamsRepository
				.findByDischargingInformationAndIsActiveOrderByTime(dischargingInfoOpt.get(), true);
		buildDischargingSequenceStabilityParameters(stabilityParameters, builder);
	}

	private void buildDischargingSequenceStabilityParameters(
			List<DischargingSequenceStabilityParameters> stabilityParameters, DischargeSequenceReply.Builder builder) {
		log.info("Populating Loading sequence stability parameters");
		stabilityParameters.forEach(param -> {
			LoadingPlanStabilityParameters.Builder paramBuilder = LoadingPlanStabilityParameters.newBuilder();
			Optional.ofNullable(param.getAftDraft())
					.ifPresent(aftDraft -> paramBuilder.setAftDraft(String.valueOf(aftDraft)));
			Optional.ofNullable(param.getBendingMoment()).ifPresent(bm -> paramBuilder.setBm(String.valueOf(bm)));
			Optional.ofNullable(param.getForeDraft())
					.ifPresent(foreDraft -> paramBuilder.setForeDraft(String.valueOf(foreDraft)));
			Optional.ofNullable(param.getShearingForce()).ifPresent(sf -> paramBuilder.setSf(String.valueOf(sf)));
			Optional.ofNullable(param.getTrim()).ifPresent(trim -> paramBuilder.setTrim(String.valueOf(trim)));
			Optional.ofNullable(param.getList()).ifPresent(list -> paramBuilder.setList(String.valueOf(list)));
			Optional.ofNullable(param.getTime()).ifPresent(paramBuilder::setTime);
			// Optional.ofNullable(param.getGomValue()).ifPresent(value ->
			// paramBuilder.setGomValue(value.toString())); TODO
			builder.addDischargeSequenceStabilityParameters(paramBuilder.build());
		});
	}

	private String getStartDate(Long portRotationXId) throws Exception {
		log.info("Fetching port rotation details with id {} from loadable study MS", portRotationXId);
		PortRotationRequest.Builder builder = PortRotationRequest.newBuilder();
		builder.setId(portRotationXId);
		PortRotationDetailReply reply = loadableStudyGrpcService
				.getLoadableStudyPortRotationByPortRotationId(builder.build());
		if (!reply.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
			throw new Exception("Could not find port rotation with id " + portRotationXId);
		}
		return reply.getPortRotationDetail().getEta();
	}

	private void buildDischargingSequences(List<com.cpdss.dischargeplan.entity.DischargingSequence> dischargeSequences,
			DischargeSequenceReply.Builder builder) {
		dischargeSequences.forEach(dischargeSequence -> {
			com.cpdss.common.generated.discharge_plan.DischargingSequence.Builder sequenceBuilder = com.cpdss.common.generated.discharge_plan.DischargingSequence
					.newBuilder();
			Optional.ofNullable(dischargeSequence.getCargoDischargingRate1())
					.ifPresent(rate1 -> sequenceBuilder.setCargoDischargingRate1(String.valueOf(rate1)));
			Optional.ofNullable(dischargeSequence.getCargoDischargingRate2())
					.ifPresent(rate2 -> sequenceBuilder.setCargoDischargingRate2(String.valueOf(rate2)));
			Optional.ofNullable(dischargeSequence.getCargoNominationXId())
					.ifPresent(sequenceBuilder::setCargoNominationId);
			Optional.ofNullable(dischargeSequence.getEndTime()).ifPresent(sequenceBuilder::setEndTime);
			Optional.ofNullable(dischargeSequence.getPortXId()).ifPresent(sequenceBuilder::setPortId);
			Optional.ofNullable(dischargeSequence.getSequenceNumber()).ifPresent(sequenceBuilder::setSequenceNumber);
			Optional.ofNullable(dischargeSequence.getStageName()).ifPresent(sequenceBuilder::setStageName);
			Optional.ofNullable(dischargeSequence.getStartTime()).ifPresent(sequenceBuilder::setStartTime);
			Optional.ofNullable(dischargeSequence.getToLoadicator()).ifPresent(sequenceBuilder::setToLoadicator);

			List<DeballastingRate> deBallastingRates = deBallastingRateRepository
					.findByDischargingSequenceAndIsActiveTrueOrderById(dischargeSequence);
			buildDeBallastingRates(sequenceBuilder, deBallastingRates);

			List<CargoDischargingRate> cargoDischargeRates = cargoLoadingRateRepository
					.findByDischargingSequenceAndIsActiveTrueOrderById(dischargeSequence);
			buildCargoLoadingRates(sequenceBuilder, cargoDischargeRates);

			// Includes both ballast and cargo pump details here
			List<BallastOperation> ballastOperations = ballastOperationRepository
					.findByDischargingSequenceAndIsActiveTrueOrderById(dischargeSequence);
			buildBallastOperations(sequenceBuilder, ballastOperations);

			List<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetails = portWiseDetailsRepository
					.findByDischargingSequenceAndIsActiveTrueOrderById(dischargeSequence);
			buildDischargingPlanPortWiseDetails(sequenceBuilder, dischargingPlanPortWiseDetails);
//			EductionOperation eductionOperation = eductionOperationRepository
//					.findByDischargingSequenceAndIsActiveTrue(dischargeSequence);
//			if (eductionOperation != null) {
//				buildEductionOperations(sequenceBuilder, eductionOperation);
//			}
			builder.addDischargeSequences(sequenceBuilder.build());
		});
	}

	/**
	 * @param sequenceBuilder
	 * @param eductionOperation
	 */
	private void buildEductionOperations(DischargingSequence.Builder sequenceBuilder,
			EductionOperation eductionOperation) {
		log.info("Populating eduction operation, tanks: {}, pumps: {}", eductionOperation.getTanksUsed(),
				eductionOperation.getEductorsUsed());
		EductorOperation.Builder builder = EductorOperation.newBuilder();
		Optional.ofNullable(eductionOperation.getEductorsUsed()).ifPresent(builder::setPumpsUsed);
		Optional.ofNullable(eductionOperation.getEndTime()).ifPresent(builder::setEndTime);
		Optional.ofNullable(eductionOperation.getId()).ifPresent(builder::setId);
		Optional.ofNullable(eductionOperation.getStartTime()).ifPresent(builder::setStartTime);
		Optional.ofNullable(eductionOperation.getTanksUsed()).ifPresent(builder::setTanksUsed);
		sequenceBuilder.setEductorOperation(builder.build());
	}

	private void buildBallastOperations(DischargingSequence.Builder sequenceBuilder,
			List<BallastOperation> ballastOperations) {
		log.info("Populating Ballast Operations");
		ballastOperations.forEach(ballastOp -> {
			PumpOperation.Builder builder = PumpOperation.newBuilder();
			Optional.ofNullable(ballastOp.getEndTime()).ifPresent(builder::setEndTime);
			Optional.ofNullable(ballastOp.getPumpName()).ifPresent(builder::setPumpName);
			Optional.ofNullable(ballastOp.getPumpXId()).ifPresent(builder::setPumpXId);
			Optional.ofNullable(ballastOp.getQuantityM3())
					.ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
			Optional.ofNullable(ballastOp.getRate()).ifPresent(rate -> builder.setRate(String.valueOf(rate)));
			Optional.ofNullable(ballastOp.getStartTime()).ifPresent(builder::setStartTime);
			sequenceBuilder.addBallastOperations(builder.build());
		});
	}

	private void buildDischargingPlanPortWiseDetails(DischargingSequence.Builder sequenceBuilder,
			List<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetails) {
		log.info("Populating Portwise details");
		dischargingPlanPortWiseDetails.forEach(portWiseDetails -> {
			DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder = DischargePlanPortWiseDetails.newBuilder();
			Optional.ofNullable(portWiseDetails.getTime()).ifPresent(portWiseDetailsBuilder::setTime);

			List<DeballastingRate> deBallastingRates = deBallastingRateRepository
					.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(portWiseDetails);
			buildDeBallastingRates(portWiseDetailsBuilder, deBallastingRates);

			List<DischargingPlanBallastDetails> ballastDetails = ballastDetailsRepository
					.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(portWiseDetails);
			buildBallastDetails(portWiseDetailsBuilder, ballastDetails);

			List<DischargingPlanRobDetails> robDetails = robDetailsRepository
					.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(portWiseDetails);
			buildRobDetails(portWiseDetailsBuilder, robDetails);

			Optional<DischargingPlanStabilityParameters> stabilityParametersOpt = stabilityParametersRepository
					.findByDischargingPlanPortWiseDetailsAndIsActiveTrue(portWiseDetails);
			buildStabilityParams(portWiseDetailsBuilder, stabilityParametersOpt);

			List<DischargingPlanCommingleDetails> commingleDetails = commingleDetailsRepository
					.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(portWiseDetails);
			buildCommingleDetails(portWiseDetailsBuilder, commingleDetails);

			List<DischargingPlanStowageDetails> stowageDetails = stowageDetailsRepository
					.findByDischargingPlanPortWiseDetailsAndIsActiveTrueOrderById(portWiseDetails);
			buildStowageDetails(portWiseDetailsBuilder, stowageDetails);

			sequenceBuilder.addDischargePlanPortWiseDetails(portWiseDetailsBuilder.build());
		});
	}

	private void buildCommingleDetails(DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
			List<DischargingPlanCommingleDetails> commingleDetails) {
		commingleDetails.forEach(commingle -> {
			LoadingPlanCommingleDetails.Builder builder = LoadingPlanCommingleDetails.newBuilder();
			Optional.ofNullable(commingle.getAbbreviation()).ifPresent(builder::setAbbreviation);
			Optional.ofNullable(commingle.getApi()).ifPresent(api -> builder.setApi(api.toString()));
			Optional.ofNullable(commingle.getCargo1XId()).ifPresent(builder::setCargo1Id);
			Optional.ofNullable(commingle.getCargo2XId()).ifPresent(builder::setCargo2Id);
			Optional.ofNullable(commingle.getCargoNomination1XId()).ifPresent(builder::setCargoNomination1Id);
			Optional.ofNullable(commingle.getCargoNomination2XId()).ifPresent(builder::setCargoNomination2Id);
			Optional.ofNullable(commingle.getId()).ifPresent(builder::setId);
			Optional.ofNullable(commingle.getQuantity())
					.ifPresent(quantity -> builder.setQuantityMT(quantity.toString()));
			Optional.ofNullable(commingle.getQuantityM3())
					.ifPresent(quantityM3 -> builder.setQuantityM3(quantityM3.toString()));
			Optional.ofNullable(commingle.getTankXId()).ifPresent(builder::setTankId);
			Optional.ofNullable(commingle.getTemperature())
					.ifPresent(temperature -> builder.setTemperature(temperature.toString()));
			Optional.ofNullable(commingle.getUllage()).ifPresent(ullage -> builder.setUllage(ullage.toString()));
			portWiseDetailsBuilder.addDischargingPlanCommingleDetails(builder.build());
		});
	}

	private void buildStowageDetails(DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
			List<DischargingPlanStowageDetails> dischargePlanStowageDetails) {
		dischargePlanStowageDetails.forEach(stowage -> {
			LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
			Optional.ofNullable(stowage.getQuantity())
					.ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
			Optional.ofNullable(stowage.getQuantityM3())
					.ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
			Optional.ofNullable(stowage.getTankXId()).ifPresent(builder::setTankId);
			Optional.ofNullable(stowage.getApi()).ifPresent(api -> builder.setApi(String.valueOf(api)));
			Optional.ofNullable(stowage.getCargoNominationId()).ifPresent(builder::setCargoNominationId);
			Optional.ofNullable(stowage.getTemperature())
					.ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));
			Optional.ofNullable(stowage.getUllage()).ifPresent(ullage -> builder.setUllage(String.valueOf(ullage)));
			Optional.ofNullable(stowage.getAbbreviation()).ifPresent(builder::setAbbreviation);
			Optional.ofNullable(stowage.getColorCode()).ifPresent(builder::setColorCode);
			portWiseDetailsBuilder.addDischargingPlanStowageDetails(builder.build());
		});
	}

	private void buildStabilityParams(DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
			Optional<DischargingPlanStabilityParameters> loadingPlanStabilityParameters) {
		LoadingPlanStabilityParameters.Builder builder = LoadingPlanStabilityParameters.newBuilder();
		Optional.ofNullable(loadingPlanStabilityParameters.get().getBm())
				.ifPresent(bm -> builder.setBm(String.valueOf(bm)));
		Optional.ofNullable(loadingPlanStabilityParameters.get().getDraft())
				.ifPresent(draft -> builder.setDraft(String.valueOf(draft)));
		Optional.ofNullable(loadingPlanStabilityParameters.get().getSf())
				.ifPresent(sf -> builder.setSf(String.valueOf(sf)));
		portWiseDetailsBuilder.setDischargingPlanStabilityParameters(builder.build());
	}

	private void buildRobDetails(DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
			List<DischargingPlanRobDetails> robDetails) {
		robDetails.forEach(rob -> {
			LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
			Optional.ofNullable(rob.getQuantity()).ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
			Optional.ofNullable(rob.getQuantityM3())
					.ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
			Optional.ofNullable(rob.getTankXId()).ifPresent(builder::setTankId);
			portWiseDetailsBuilder.addDischargingPlanRobDetails(builder.build());
		});
	}

	private void buildBallastDetails(DischargePlanPortWiseDetails.Builder portWiseDetailsBuilder,
			List<DischargingPlanBallastDetails> ballastDetails) {
		ballastDetails.forEach(ballast -> {
			LoadingPlanTankDetails.Builder builder = LoadingPlanTankDetails.newBuilder();
			Optional.ofNullable(ballast.getQuantity())
					.ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
			Optional.ofNullable(ballast.getQuantityM3())
					.ifPresent(quantityM3 -> builder.setQuantityM3(String.valueOf(quantityM3)));
			Optional.ofNullable(ballast.getSounding())
					.ifPresent(sounding -> builder.setSounding(String.valueOf(sounding)));
			Optional.ofNullable(ballast.getTankXId()).ifPresent(builder::setTankId);
			Optional.ofNullable(ballast.getColorCode()).ifPresent(builder::setColorCode);
			portWiseDetailsBuilder.addDischargingPlanBallastDetails(builder.build());
		});
	}

	private void buildDeBallastingRates(DischargePlanPortWiseDetails.Builder portwiseBuilder,
			List<DeballastingRate> deballastingRates) {
		deballastingRates.forEach(rate -> {
			DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
			Optional.ofNullable(rate.getDeBallastingRate())
					.ifPresent(deBallastingRate -> builder.setDeBallastingRate(String.valueOf(deBallastingRate)));
			Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
			Optional.ofNullable(rate.getTime()).ifPresent(builder::setTime);
			portwiseBuilder.addDeballastingRates(builder.build());
		});
	}

	private void buildCargoLoadingRates(DischargingSequence.Builder sequenceBuilder,
			List<CargoDischargingRate> cargoLoadingRates) {
		cargoLoadingRates.forEach(rate -> {
			DischargingRate.Builder builder = DischargingRate.newBuilder();
			Optional.ofNullable(rate.getDischargingRate())
					.ifPresent(loadingRate -> builder.setDischargingRate(String.valueOf(loadingRate)));
			Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
			builder.setEndTime(sequenceBuilder.getEndTime());
			builder.setStartTime(sequenceBuilder.getStartTime());
			sequenceBuilder.addDischargingRates(builder.build());
		});
	}

	private void buildDeBallastingRates(DischargingSequence.Builder sequenceBuilder,
			List<DeballastingRate> deballastingRates) {
		deballastingRates.forEach(rate -> {
			DeBallastingRate.Builder builder = DeBallastingRate.newBuilder();
			Optional.ofNullable(rate.getDeBallastingRate())
					.ifPresent(deBallastingRate -> builder.setDeBallastingRate(String.valueOf(deBallastingRate)));
			Optional.ofNullable(rate.getTankXId()).ifPresent(builder::setTankId);
			Optional.ofNullable(rate.getTime()).ifPresent(builder::setTime);
			sequenceBuilder.addDeBallastingRates(builder.build());
		});
	}
}
