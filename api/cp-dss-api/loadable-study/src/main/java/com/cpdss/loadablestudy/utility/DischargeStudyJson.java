/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.utility;

import static java.util.Optional.ofNullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo.VesselTankRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankResponse;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadablestudy.domain.CargoNomination;
import com.cpdss.loadablestudy.domain.CargoNominationOperationDetails;
import com.cpdss.loadablestudy.domain.CowDetail;
import com.cpdss.loadablestudy.domain.DischargeStudyAlgoJson;
import com.cpdss.loadablestudy.domain.DischargeStudyPortInstructionDetailsJson;
import com.cpdss.loadablestudy.domain.LoadableStudyInstruction;
import com.cpdss.loadablestudy.domain.OnHandQuantity;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.domain.DischargeStudyPortRotationJson;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.DischargeStudyCowDetail;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.PortInstruction;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.PortInstructionRepository;
import com.cpdss.loadablestudy.service.CowDetailService;
import com.cpdss.loadablestudy.service.LoadableStudyPortRotationService;

import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Log4j2
public class DischargeStudyJson {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	public static final String INVALID_DISCHARGE_STUDY_ID = "INVALID_DISCHARGE_STUDY_ID";
	public static final String INVALID_DISCHARGE_QUANTITY = "INVALID_DISCHARGE_QUANTITY";

	@Autowired
	private PortInstructionRepository portInstructionRepository;

	@Autowired
	private CargoNominationRepository cargoNominationRepository;

	@Autowired
	private LoadableStudyRepository loadableStudyRepository;

	@Autowired
	private LoadableStudyPortRotationService loadableStudyPortRotationService;

	@Autowired
	private CowDetailService cowDetailService;

	@Autowired
	private OnHandQuantityRepository onHandQuantityRepository;

	@GrpcClient("vesselInfoService")
	private VesselInfoServiceBlockingStub vesselInfoGrpcService;

	@GrpcClient("portInfoService")
	private PortInfoServiceBlockingStub portInfoGrpcService;

	public void generateJson(Long dischargeStudyId, Long voyageId, String voyageNo, Long vesselId, String dischargeName)
			throws GenericServiceException {
		log.info("Generating Discharge study request Json for  :{}", dischargeStudyId);
		DischargeStudyAlgoJson dischargeStudyAlgoJson = new DischargeStudyAlgoJson();

		Optional<LoadableStudy> loadableStudy = this.loadableStudyRepository.findByIdAndIsActive(dischargeStudyId,
				true);

		if (!loadableStudy.isPresent()) {
			log.info(INVALID_DISCHARGE_STUDY_ID, dischargeStudyId);
			throw new GenericServiceException("Discharge study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST,
					null);
		} else {
			dischargeStudyAlgoJson.setId(dischargeStudyId);
			dischargeStudyAlgoJson.setVoyageId(voyageId);
			dischargeStudyAlgoJson.setVoyageNo(voyageNo);
			dischargeStudyAlgoJson.setVesselId(vesselId);
			dischargeStudyAlgoJson.setName(dischargeName);

			List<LoadableStudyInstruction> instructionsDetails = getAllLoadableStudyInstruction();
			dischargeStudyAlgoJson.setInstructionMaster(instructionsDetails);

			dischargeStudyAlgoJson.setCommingleCargos(new ArrayList<>()); // reserved for future.Keeping empty for now

			List<DischargeStudyPortRotationJson> portRotationList = getDischargeStudyPortRotation(dischargeStudyId, instructionsDetails);
			dischargeStudyAlgoJson.setDischargeStudyPortRotation(portRotationList);

			List<CargoNomination> cargoNominations = getCargoNomination(dischargeStudyId);
			dischargeStudyAlgoJson.setCargoNomination(cargoNominations);
			dischargeStudyAlgoJson.setCargoNominationOperationDetails(
					getCargoNominationOperationDetails(dischargeStudyId, cargoNominations));

			dischargeStudyAlgoJson.setOnHandQuantity(getOnHandQuantity(dischargeStudyId));

			dischargeStudyAlgoJson.setPortDetails(getPortDetails(portRotationList));

			dischargeStudyAlgoJson.setCowHistory(new ArrayList<>()); // reserved for future.Keeping empty for now
			
			dischargeStudyAlgoJson.setLoadablePlanPortWiseDetails(getLoadablePlanPortWiseDetails(dischargeStudyId,voyageId));

		}
	}

	private List<?> getLoadablePlanPortWiseDetails(Long dischargeStudyId, Long voyageId) {
		log.info("Fetching LoadablePlan PortWise Details for discharge id {}", dischargeStudyId);
		return null;
	}

	private List<PortDetails> getPortDetails(List<DischargeStudyPortRotationJson> portRotationList) {
		log.info("Fetching Port details for {} ports", portRotationList.size());
		if(!CollectionUtils.isEmpty(portRotationList)) {
			List<PortDetails> portDetailsList = new ArrayList<>();
			List<Long> portIds = portRotationList.stream().map(item -> item.getPortId()).collect(Collectors.toList());
			PortInfo.PortReply portReply = getPortInfo(portIds);
			if(portReply.getResponseStatus().getStatus() == SUCCESS) {
				portReply.getPortsList().forEach(item -> {
					PortDetails portdetail = new PortDetails();	
					ofNullable(item.getId()).ifPresent(portdetail::setId);
					ofNullable(item.getName()).ifPresent(portdetail::setName);
					ofNullable(item.getCode()).ifPresent(portdetail::setCode);
					ofNullable(item.getAverageTideHeight()).ifPresent(portdetail::setAverageTideHeight);
					ofNullable(item.getTideHeight()).ifPresent(portdetail::setTideHeight);
					ofNullable(item.getSerializedSize()).ifPresent(i -> portdetail.setDensitySeaWater(i.toString()));
					ofNullable(item.getCountryName()).ifPresent(portdetail::setCountryName);
					portDetailsList.add(portdetail);
				});
				log.info("Found {} items", portDetailsList.size());
				return portDetailsList;
			}
		}
		log.info("No Port details  found ");
		return null;
	}

	/**
	 * @param build
	 * @return PortReply
	 */
	public PortInfo.PortReply getPortInfo(List<Long> portIds) {
		PortInfo.GetPortInfoByPortIdsRequest request =
		          PortInfo.GetPortInfoByPortIdsRequest.newBuilder().addAllId(portIds).build();
		return portInfoGrpcService.getPortInfoByPortIds(request);
	}

	private List<OnHandQuantity> getOnHandQuantity(Long dischargeStudyId) {
		log.info("Fetching OnHand quantity details for discharge id {}", dischargeStudyId);
		List<com.cpdss.loadablestudy.entity.OnHandQuantity> ohqList = onHandQuantityRepository
				.findByDischargeStudyIdAndActive(dischargeStudyId);
		if (!CollectionUtils.isEmpty(ohqList)) {
			List<OnHandQuantity> onHandQuantityList = new ArrayList<>();
			ohqList.forEach(item -> {
				OnHandQuantity onHandQuantity = new OnHandQuantity();
				ofNullable(item.getId()).ifPresent(onHandQuantity::setId);
				ofNullable(item.getPortXId()).ifPresent(onHandQuantity::setPortId);
				ofNullable(item.getFuelTypeXId()).ifPresent(onHandQuantity::setFueltypeId);
				ofNullable(item.getTankXId()).ifPresent(onHandQuantity::setTankId);
				ofNullable(item.getArrivalVolume()).ifPresent(i -> onHandQuantity.setArrivalVolume(i.toString()));
				ofNullable(item.getArrivalQuantity()).ifPresent(i -> onHandQuantity.setArrivalQuantity(i.toString()));
				ofNullable(item.getDepartureVolume()).ifPresent(i -> onHandQuantity.setDepartureVolume(i.toString()));
				ofNullable(item.getDepartureQuantity())
						.ifPresent(i -> onHandQuantity.setDepartureQuantity(i.toString()));
				onHandQuantityList.add(onHandQuantity);
			});
			log.info("Found {} items", onHandQuantityList.size());
			return onHandQuantityList;
		}
		log.info("No  OnHand quantity details found ");
		return null;
	}

	private List<CargoNominationOperationDetails> getCargoNominationOperationDetails(Long dischargeStudyId,
			List<CargoNomination> cargoNominations) {
		log.info("Fetching CargoNomination Operation details for discharge id {}", dischargeStudyId);
		List<com.cpdss.loadablestudy.entity.CargoNomination> cargoNominationReply = getCargoNominationforDischargeID(
				dischargeStudyId);
		if (!CollectionUtils.isEmpty(cargoNominationReply)) {
			List<CargoNominationOperationDetails> cargoNominationOperationDetailsList = new ArrayList<>();
			cargoNominationReply.forEach(item -> {
				if (!CollectionUtils.isEmpty(item.getCargoNominationPortDetails())) {
					Iterator<CargoNominationPortDetails> iterator = item.getCargoNominationPortDetails().iterator();
					while (iterator.hasNext()) {
						CargoNominationPortDetails iteratorItem = iterator.next();
						CargoNominationOperationDetails cargoNominationOperation = new CargoNominationOperationDetails();
						ofNullable(iteratorItem.getId()).ifPresent(cargoNominationOperation::setId);
						ofNullable(item.getId()).ifPresent(cargoNominationOperation::setCargoNominationId);
						ofNullable(iteratorItem.getPortId()).ifPresent(cargoNominationOperation::setPortId);
						ofNullable(iteratorItem.getQuantity())
								.ifPresent(i -> cargoNominationOperation.setQuantity(i.toString()));
						cargoNominationOperationDetailsList.add(cargoNominationOperation);
					}
				}
			});
			log.info("Found {} items", cargoNominationOperationDetailsList.size());
			return cargoNominationOperationDetailsList;
		}
		log.info("No CargoNomination operation details found ");
		return null;
	}

	private List<com.cpdss.loadablestudy.entity.CargoNomination> getCargoNominationforDischargeID(
			Long dischargeStudyId) {
		return this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(dischargeStudyId,
				true);
	}

	private List<CargoNomination> getCargoNomination(Long dischargeStudyId) {
		log.info("Fetching CargoNomination details for discharge id {}", dischargeStudyId);
		List<CargoNomination> cargoNominationList = new ArrayList<>();
		List<com.cpdss.loadablestudy.entity.CargoNomination> cargoNominationReply = getCargoNominationforDischargeID(
				dischargeStudyId);
		if (!CollectionUtils.isEmpty(cargoNominationReply)) {
			cargoNominationReply.forEach(item -> {
				CargoNomination nomination = new CargoNomination();
				ofNullable(item.getId()).ifPresent(nomination::setId);
				ofNullable(item.getLoadableStudyXId()).ifPresent(nomination::setLoadableStudyId);
				ofNullable(item.getPriority()).ifPresent(nomination::setPriority);
				ofNullable(item.getColor()).ifPresent(nomination::setColor);
				ofNullable(item.getCargoXId()).ifPresent(nomination::setCargoId);
				ofNullable(item.getAbbreviation()).ifPresent(nomination::setAbbreviation);
				ofNullable(item.getQuantity()).ifPresent(nomination::setQuantity);
				ofNullable(item.getMaxTolerance()).ifPresent(nomination::setMaxTolerance);
				ofNullable(item.getMinTolerance()).ifPresent(nomination::setMinTolerance);
				ofNullable(item.getApi()).ifPresent(nomination::setApi);
				ofNullable(item.getTemperature()).ifPresent(nomination::setTemperature);
				ofNullable(item.getSegregationXId()).ifPresent(nomination::setSegregationId);
				nomination.setIsCondensateCargo(null); // for future
				nomination.setIsHrvpCargo(null); // for future
				cargoNominationList.add(nomination);
			});
			log.info("Found {} items", cargoNominationList.size());
			return cargoNominationList;
		}
		log.info("No CargoNomination details found ");
		return null;
	}

	private List<DischargeStudyPortRotationJson> getDischargeStudyPortRotation(Long dischargeStudyId,
			List<LoadableStudyInstruction> instructionsDetails) {
		log.info("Fetching Port Roataion details ");
		List<DischargeStudyPortRotationJson> portRotationList = new ArrayList<>();
		PortRotationRequest.Builder request = PortRotationRequest.newBuilder();
		request.setLoadableStudyId(dischargeStudyId);
		PortRotationReply reply = loadableStudyPortRotationService
				.getPortRotationByLoadableStudyId(request.build(), PortRotationReply.newBuilder()).build();
		if (reply.getResponseStatus().getStatus() != SUCCESS) {
			log.info("No port rotaion details found for Discharge ID  :{}", dischargeStudyId);
			return null;
		} else {
			reply.getPortsList().forEach(port -> {
				DischargeStudyPortRotationJson portRotation = new DischargeStudyPortRotationJson();
				portRotation.setId(port.getId());
				portRotation.setDischargeStudyId(port.getLoadableStudyId());
				portRotation.setPortId(port.getPortId());
				portRotation.setBerthId(port.getBerthId());
				portRotation.setOperationId(port.getOperationId());
				portRotation.setSeaWaterDensity(port.getSeaWaterDensity());
				portRotation.setDistanceBetweenPorts(port.getDistanceBetweenPorts());
				portRotation.setTimeOfStay(port.getTimeOfStay());
				portRotation.setMaxDraft(port.getMaxDraft());
				portRotation.setMaxAirDraft(port.getMaxDraft());
				portRotation.setEta(port.getEta());
				portRotation.setEtd(port.getEtd());
				portRotation.setPortOrder(port.getPortOrder());
				portRotation.setCowDetails(getCowDetails(dischargeStudyId, port.getPortId()));
				portRotation.setInstructions(getPortInstructions(port, instructionsDetails));

				portRotationList.add(portRotation);
			});
			log.info("Found {} items", portRotationList.size());
			return portRotationList;
		}

	}

	private CowDetail getCowDetails(Long dischargeStudyId, Long portId) {
		log.info("Getting Cow details for {}", portId);
		DischargeStudyCowDetail reply = cowDetailService.getCowDetailForOnePort(dischargeStudyId, portId);
		if (reply != null) {

			CowDetail cowDetail = new CowDetail();
			cowDetail.setId(reply.getId());
			cowDetail.setType(reply.getCowType());
			cowDetail.setPercentage(reply.getPercentage());

			List<Long> tankIdList = Stream.of(reply.getTankIds().split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
			VesselTankRequest.Builder tankRequest = VesselTankRequest.newBuilder();
			tankRequest.addAllTankIds(tankIdList);
			VesselTankResponse replyBuilder = this.vesselInfoGrpcService.getVesselInfoBytankIds(tankRequest.build());
			if (replyBuilder.getVesselTankOrderList() != null) {
				log.info("No Tank Sort name found for Discharge ID  :{}", dischargeStudyId);
				cowDetail.setTanks(replyBuilder.getVesselTankOrderList().stream().map(item -> item.getShortName())
						.collect(Collectors.joining(",")));
			}
			return cowDetail;
		}
		log.info("No COW Details found for port ID  :{}", portId);
		return null;

	}

	private List<DischargeStudyPortInstructionDetailsJson> getPortInstructions(PortRotationDetail port,
			List<LoadableStudyInstruction> instructionsDetails) {
		log.info("Getting Instruction details for port {}", port.getPortId());
		List<DischargeStudyPortInstructionDetailsJson> instructionList = new ArrayList<DischargeStudyPortInstructionDetailsJson>();
		port.getInstructionIdList().forEach(instructionId -> {
			instructionsDetails.forEach(instructionMasterItem -> {
				if (instructionId == instructionMasterItem.getId()) {
					DischargeStudyPortInstructionDetailsJson portInstruction = new DischargeStudyPortInstructionDetailsJson();
					portInstruction.setId(instructionId);
					portInstruction.setPortInstruction(instructionMasterItem.getInstruction());
					instructionList.add(portInstruction);
				}
			});
		});
		log.info("Found {} items", instructionList.size());
		return instructionList;
	}

	// Getting all instructions from master table PortInstruction
	private List<LoadableStudyInstruction> getAllLoadableStudyInstruction() {

		List<PortInstruction> instructionsDetails = portInstructionRepository.findByIsActive(true);
		return instructionsDetails.stream()
				.map(item -> new LoadableStudyInstruction(item.getId(), item.getPortInstruction()))
				.collect(Collectors.toList());
	}
}
