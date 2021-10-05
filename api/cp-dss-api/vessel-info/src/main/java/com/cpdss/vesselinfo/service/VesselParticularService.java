/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest;
import com.cpdss.common.generated.VesselInfo.VesselParticulars;
import com.cpdss.common.generated.VesselInfo.VesselParticulars.Builder;
import com.cpdss.common.generated.VesselInfo.VesselPumpsResponse;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
			throw new GenericServiceException("Vessel with given id does not exist",
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		}
		// Getting max flow rate from vessel
		builder.setShipMaxLoadingRate(String.valueOf(vessel.getMaxLoadRate()));
		getTankWiseLoadingRate(vessel, builder);
		getBallastPumpDetails(vessel, builder);
		getVesselManiFold(vessel, builder);
		getVesselDraftCondition(vessel, builder);
		getCargoTankCapacity(vessel, builder);

	}

	/**
	 * @param vessel
	 * @param builder
	 */
	private void getCargoTankCapacity(Vessel vessel, Builder builder) {
		List<VesselTank> tanks = vesselTankRepository.findByVesselAndIsActive(vessel, true);
		BigDecimal sum = tanks.stream().filter(item->item.getTankCategory().getId().equals(CARGO_TANK_CATEGORY_ID))
		.map(item-> item.getFullCapacityCubm()).reduce(BigDecimal.ZERO,BigDecimal::add);
		builder.setCargoTankCapacity(String.valueOf(sum.multiply(new BigDecimal(PERCENTAGE/100))));
	}

	/**
	 * @param vessel
	 * @param builder
	 */
	private void getVesselDraftCondition(Vessel vessel, Builder builder) {
		List<VesselDraftCondition> draftConditionList = vesselDraftConditionRepository.findByVesselAndIsActive(vessel,
				true);
		Stream<VesselDraftCondition> stream = draftConditionList.stream()
				.filter(item -> item.getDraftCondition().getId().equals(SUMMER));
		builder.setSummerDraft(String.valueOf(
				stream.max(Comparator.comparing(VesselDraftCondition::getDraftExtreme)).get().getDraftExtreme()));
		builder.setSummerDeadweight(String
				.valueOf(stream.max(Comparator.comparing(VesselDraftCondition::getDeadweight)).get().getDeadweight()));
		builder.setSummerDisplacement(String.valueOf(
				stream.max(Comparator.comparing(VesselDraftCondition::getDisplacement)).get().getDisplacement()));

		builder.setTropicalDraft(String
				.valueOf(draftConditionList.stream().filter(item -> item.getDraftCondition().getId().equals(TROPICAL))
						.max(Comparator.comparing(VesselDraftCondition::getDraftExtreme)).get().getDraftExtreme()));
	}

	/**
	 * @param vessel
	 * @param builder
	 */
	private void getVesselManiFold(Vessel vessel, Builder builder) {
		List<VesselManifold> manifoldList = vesselManifoldRepository.findByVesselXidAndIsActiveTrue(vessel.getId());
		builder.setShipManifold(manifoldList.size());
	}

	/**
	 * @param vessel
	 * @param builder
	 */
	private void getBallastPumpDetails(Vessel vessel, VesselParticulars.Builder builder) {
		// no:of ballast pumps
		List<VesselPumps> pumpList = vesselPumpRepository.findAllByVesselAndIsActiveTrue(vessel, Pageable.unpaged())
				.toList();
		Stream<VesselPumps> stream = pumpList.stream().filter(item -> item.getPumpType().equals(BALLAST_PUMP_TYPE));
		builder.setBallastPumpCount(stream.count());
		builder.setBallastPumpCount(stream.max(Comparator.comparing(VesselPumps::getCapacity)).get().getCapacity());
	}

	/**
	 * @param vessel
	 * @param builder
	 */
	private void getTankWiseLoadingRate(Vessel vessel, VesselParticulars.Builder builder) {
		// max flow rate of manifold
		List<VesselFlowRate> flowRates = vesselFlowRateRepository.findByVesselAndIsActiveTrue(vessel);
		builder.setShipMaxFlowRate(getFlowRateSix(flowRates, MANIFOLD_PARAMETER_TYPE));
		String slopTankLoadingRate = getFlowRateSix(flowRates, SLOP_TANK_PARAMETER_TYPES);
		// max flow rate of slop tank P
		builder.setMaxLoadingRateSlopP(slopTankLoadingRate);
		// max flow rate of slop tank S - both are same
		builder.setMaxLoadingRateSlopS(slopTankLoadingRate);
		// max flow rate of wing tanks
		builder.setShipMaxFlowRatePerTank(getFlowRateSix(flowRates, WING_TANK_PARAMETER_TYPES));
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
