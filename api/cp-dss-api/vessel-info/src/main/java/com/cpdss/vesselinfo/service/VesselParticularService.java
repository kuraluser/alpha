/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest;
import com.cpdss.common.generated.VesselInfo.VesselParticulars;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author sanalkumar.k
 *
 */
@Slf4j
@Service
public class VesselParticularService {

	@Autowired
	VesselRepository vesselRepository;

	@Autowired
	VesselTankRepository vesselTankRepository;

	@Autowired
	VesselPumpRepository vesselPumpRepository;

	@Autowired
	PumpTypeRepository pumpTypeRepository;

	@Autowired
	TankTypeRepository tankTypeRepository;

	@Autowired
	VesselManifoldRepository vesselManifoldRepository;

	@Autowired
	VesselFlowRateRepository vesselFlowRateRepository;

	@Autowired
	VesselDraftConditionRepository vesselDraftConditionRepository;

	@Autowired
	VesselVendingCapacityRepository vesselVentingCapacityRepository;

	@Autowired
	VesselPumpService vesselPumpService;

	private static final Long MANIFOLD_PARAMETER_TYPE = 1L;
	private static final Long WING_TANK_PARAMETER_TYPES = 4L;
	private static final Long SLOP_TANK_PARAMETER_TYPES = 8L;
	private static final Long BALLAST_PUMP_TYPE = 2L;
	private static final Long SUMMER = 1L;
	private static final Long TROPICAL = 2L;
	private static final Long CARGO_TANK_CATEGORY_ID = 1L;
	private static final Integer PERCENTAGE = 98;

	/**
	 * Method to get different vessel details for loading plan excel
	 * 
	 * @param builder
	 * @param request
	 * @throws GenericServiceException
	 */
	public void getVesselParticulars(VesselParticulars.Builder builder, LoadingInfoRulesRequest request)
			throws GenericServiceException {

		Vessel vessel = vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
		if (vessel == null) {
			log.info("Invalid vessel id : vessel particulars failed");
			throw new GenericServiceException("Vessel with given id does not exist",
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		}
		builder.setShipMaxLoadingRate(String.valueOf(vessel.getMaxLoadRate()));
		getTankWiseLoadingRate(vessel, builder);
		getBallastPumpDetails(vessel, builder);
		getVesselManiFold(vessel, builder);
		getVesselDraftCondition(vessel, builder);
		getCargoTankCapacity(vessel, builder);
		getVesselVentingCapacity(vessel.getId(), builder);
	}

	/**
	 * vessel venting capacity details
	 * 
	 * @param vessel
	 * @param builder
	 */
	private void getVesselVentingCapacity(Long vessel, VesselParticulars.Builder builder) {
		VesselVentingCapacity ventingCapacity = vesselVentingCapacityRepository.findByVesselIdAndIsActiveTrue(vessel);
		if (ventingCapacity != null) {
			Optional.ofNullable(ventingCapacity.getHighVelocityPressure())
					.ifPresent(value -> builder.setHighVelocityVentingPressure(String.valueOf(value)));
			Optional.ofNullable(ventingCapacity.getHighVelocityVaccum())
					.ifPresent(value -> builder.setHighVelocityVentingVaccum(String.valueOf(value)));
			Optional.ofNullable(ventingCapacity.getPvBreakerPressure())
					.ifPresent(value -> builder.setPvBreakerVentingPressure(String.valueOf(value)));
			Optional.ofNullable(ventingCapacity.getPvBreakerVaccum())
					.ifPresent(value -> builder.setPvBreakerVentingVaccum(String.valueOf(value)));
		}
	}

	/**
	 * Tank capacity
	 * 
	 * @param vessel
	 * @param builder
	 */
	private void getCargoTankCapacity(Vessel vessel, VesselParticulars.Builder builder) {
		List<VesselTank> tanks = vesselTankRepository.findByVesselAndIsActive(vessel, true);
		if (tanks != null && !tanks.isEmpty()) {
			BigDecimal sum = tanks.stream()
					.filter(item -> item.getTankCategory().getId().equals(CARGO_TANK_CATEGORY_ID))
					.map(item -> item.getFullCapacityCubm()).reduce(BigDecimal.ZERO, BigDecimal::add);
			builder.setCargoTankCapacity(String.valueOf(sum.multiply(new BigDecimal(PERCENTAGE / 100))));
		}

	}

	/**
	 * Draft conditions
	 * 
	 * @param vessel
	 * @param builder
	 */
	private void getVesselDraftCondition(Vessel vessel, VesselParticulars.Builder builder) {
		List<VesselDraftCondition> draftConditionList = vesselDraftConditionRepository.findByVesselAndIsActive(vessel,
				true);
		if (draftConditionList != null && !draftConditionList.isEmpty()) {
			Supplier<Stream<VesselDraftCondition>> streamSupplier = () -> draftConditionList.stream()
					.filter(item -> item.getDraftCondition().getId().equals(SUMMER));
			Optional.ofNullable(
					streamSupplier.get().max(Comparator.comparing(VesselDraftCondition::getDraftExtreme)).get().getDraftExtreme())
					.ifPresent(value -> builder.setSummerDraft(String.valueOf(value)));

			Optional.ofNullable(
					streamSupplier.get().max(Comparator.comparing(VesselDraftCondition::getDeadweight)).get().getDeadweight())
					.ifPresent(value -> builder.setSummerDeadweight(String.valueOf(value)));

			Optional.ofNullable(
					streamSupplier.get().max(Comparator.comparing(VesselDraftCondition::getDisplacement)).get().getDisplacement())
					.ifPresent(value -> builder.setSummerDisplacement(String.valueOf(value)));

			Optional.ofNullable(
					draftConditionList.stream().filter(item -> item.getDraftCondition().getId().equals(TROPICAL))
							.max(Comparator.comparing(VesselDraftCondition::getDraftExtreme)).get().getDraftExtreme())
					.ifPresent(value -> builder.setTropicalDraft(String.valueOf(value)));
		}

	}

	/**
	 * Manifold count
	 * 
	 * @param vessel
	 * @param builder
	 */
	private void getVesselManiFold(Vessel vessel, VesselParticulars.Builder builder) {
		List<VesselManifold> manifoldList = vesselManifoldRepository.findByVesselXidAndIsActiveTrue(vessel.getId());
		builder.setShipManifold(manifoldList.size());
	}

	/**
	 * Pump details
	 * 
	 * @param vessel
	 * @param builder
	 */
	private void getBallastPumpDetails(Vessel vessel, VesselParticulars.Builder builder) {
		// no:of ballast pumps
		List<VesselPumps> pumpList = vesselPumpRepository.findAllByVesselAndIsActiveTrue(vessel, Pageable.unpaged())
				.toList();
		if (pumpList != null && !pumpList.isEmpty()) {
			Supplier<Stream<VesselPumps>> streamSupplier = () -> pumpList.stream()
					.filter(item -> item.getPumpType().getId().equals(BALLAST_PUMP_TYPE));
			builder.setBallastPumpCount(streamSupplier.get().count());
			Optional.ofNullable(
					streamSupplier.get().max(Comparator.comparing(VesselPumps::getCapacity)).get().getCapacity())
					.ifPresent(builder::setBallastPumpCount);
		}

	}

	/**
	 * Flow rate based on tanks
	 * 
	 * @param vessel
	 * @param builder
	 */
	private void getTankWiseLoadingRate(Vessel vessel, VesselParticulars.Builder builder) {
		// max flow rate of manifold
		List<VesselFlowRate> flowRates = vesselFlowRateRepository.findByVessel(vessel);
		if (flowRates != null) {
			Optional.ofNullable(getFlowRateSix(flowRates, MANIFOLD_PARAMETER_TYPE))
					.ifPresent(builder::setShipMaxFlowRate);
			String slopTankLoadingRate = getFlowRateSix(flowRates, SLOP_TANK_PARAMETER_TYPES);
			// max flow rate of slop tank P
			Optional.ofNullable(slopTankLoadingRate).ifPresent(builder::setMaxLoadingRateSlopP);
			// max flow rate of slop tank S - both are same
			Optional.ofNullable(slopTankLoadingRate).ifPresent(builder::setMaxLoadingRateSlopS);
			// max flow rate of wing tanks
			Optional.ofNullable(getFlowRateSix(flowRates, WING_TANK_PARAMETER_TYPES))
					.ifPresent(builder::setShipMaxFlowRatePerTank);
		}
	}

	/**
	 * @param flowRates
	 * @param parameterType
	 * @return
	 */
	private String getFlowRateSix(List<VesselFlowRate> flowRates, Long parameterType) {
		Optional<VesselFlowRate> opt = flowRates.stream()
				.filter(item -> item.getFlowRateParameter().getId().equals(parameterType)).findFirst();
		if (opt.isPresent()) {
			return String.valueOf(opt.get().getFlowRateSix());
		}
		return null;
	}
}
