/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.*;
import com.cpdss.dischargeplan.domain.ReasonForDelay;
import com.cpdss.dischargeplan.domain.cargo.DischargePlanPortWiseDetails;
import com.cpdss.dischargeplan.domain.cargo.OnBoardQuantity;
import com.cpdss.dischargeplan.domain.cargo.OnHandQuantity;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.CowPlanDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.ReasonForDelayRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargePlanAlgoService {

  @Autowired DischargeInformationService dischargeInformationService;

  @Autowired DischargeBerthDetailRepository dischargeBerthDetailRepository;

  @Autowired ReasonForDelayRepository reasonForDelayRepository;

  @Autowired CowPlanDetailRepository cowPlanDetailRepository;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  public void buildDischargeInformation(
      DischargeInformationRequest request,
      com.cpdss.dischargeplan.domain.DischargeInformationAlgoRequest algoRequest)
      throws GenericServiceException {
    com.cpdss.dischargeplan.entity.DischargeInformation entity =
        dischargeInformationService.getDischargeInformation(request.getDischargeInfoId());
    com.cpdss.dischargeplan.domain.DischargeInformation disDto =
        new com.cpdss.dischargeplan.domain.DischargeInformation();
    if (entity != null) {

      // basic details
      algoRequest.setVoyageId(entity.getVoyageXid());
      algoRequest.setVesselId(entity.getVesselXid());
      algoRequest.setPortId(entity.getPortXid());
      disDto.setDischargeInfoId(entity.getId());
      disDto.setSynopticTableId(entity.getSynopticTableXid());

      // discharge details
      DischargeDetails dischargeDetails = new DischargeDetails();
      dischargeDetails.setStartTime(
          entity.getStartTime() != null ? entity.getStartTime().toString() : null);
      // sunrise and sunset from DS(synoptic)
      this.getAndSetDataFromSynopticTable(entity.getPortRotationXid(), dischargeDetails);
      TrimAllowed trimAllowed = new TrimAllowed();
      trimAllowed.setInitialTrim(entity.getInitialTrim());
      trimAllowed.setMaximumTrim(entity.getMaximumTrim());
      trimAllowed.setFinalTrim(entity.getFinalTrim());
      dischargeDetails.setTrimAllowed(trimAllowed);
      disDto.setDischargeDetails(dischargeDetails);

      // discharge rates
      DischargeRates dischargeRates = new DischargeRates();
      BeanUtils.copyProperties(entity, dischargeRates);
      disDto.setDischargeRates(dischargeRates);

      // discharge berths
      disDto.setBerthDetails(this.buildDischargeBerthDto(entity.getId()));

      // discharge machines
      disDto.setMachineryInUses(this.buildDischargeMachines(entity));

      // discharge stages
      DischargeStages dischargeStages = new DischargeStages();
      dischargeStages.setStageDuration(entity.getDischargingStagesDuration().getDuration());
      dischargeStages.setStageOffset(entity.getDischargingStagesMinAmount().getMinAmount());
      dischargeStages.setTrackGradeSwitch(entity.getIsTrackGradeSwitching());
      dischargeStages.setTrackStartEndStage(entity.getIsTrackStartEndStage());
      disDto.setDischargeStages(dischargeStages);

      // discharge delays
      disDto.setDischargeSequences(this.buildDischargeDelays(entity));

      // discharge cow
      disDto.setCowPlan(this.buildDischargeCowDetails(entity));

      // post discharge rate
      PostDischargeRates postDischargeRates = new PostDischargeRates();
      BeanUtils.copyProperties(entity, postDischargeRates);
      disDto.setPostDischargeRates(postDischargeRates);

      // discharge cargo details, LoadablePlanPortWiseDetails (OHQ, OBQ)
      this.buildDischargePlanPortWiseDetails(algoRequest, entity);

      // Build hourly based tide details which upload from loading info page - TO DO
      // buildPortTideDetails(algoRequest, loadingInfoOpt.get().getPortXId());

      // Build Loading Rule, service is in loading-plan (self) - TO DO
      // buildLoadingRules(algoRequest, loadingInfoOpt.get());

      algoRequest.setDischargeInformation(disDto);
    }
  }

  private void getAndSetDataFromSynopticTable(
      Long portRotationXid, DischargeDetails dischargeDetails) throws GenericServiceException {
    LoadableStudy.LoadingPlanCommonResponse response =
        this.loadableStudyService.getSynopticDataForLoadingPlan(
            LoadableStudy.LoadingPlanIdRequest.newBuilder()
                .setIdType("PORT_ROTATION")
                .setId(portRotationXid)
                .build());
    if (!response.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
      log.error("Failed to get Synoptic data from LS ", response.getResponseStatus().getMessage());
      throw new GenericServiceException(
          "Failed to get Synoptic Data for Port",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    var synopticData =
        response.getSynopticDataList().stream()
            .filter(v -> v.getOperationType().equalsIgnoreCase("ARR"))
            .findFirst()
            .get();
    if (!synopticData.getTimeOfSunrise().isEmpty()) {
      dischargeDetails.setTimeOfSunrise(synopticData.getTimeOfSunrise());
    }
    if (!synopticData.getTimeOfSunset().isEmpty()) {
      dischargeDetails.setTimeOfSunset(synopticData.getTimeOfSunset());
    }
    log.info("Setting sunrise/sunset from Synoptic table - Port R Id {}", portRotationXid);
  }

  private CowPlan buildDischargeCowDetails(
      com.cpdss.dischargeplan.entity.DischargeInformation entity) {
    CowPlan cowPlan = new CowPlan();
    CowPlanDetail cpd = this.cowPlanDetailRepository.findByDischargingId(entity.getId()).get();
    BeanUtils.copyProperties(cpd, cowPlan);

    if (!cpd.getCowTankDetails().isEmpty()) {
      cowPlan.setTopCowTankIds(
          cpd.getCowTankDetails().stream()
              .filter(v -> v.getCowTypeXid().equals(Common.COW_TYPE.TOP_COW_VALUE))
              .map(CowTankDetail::getTankXid)
              .collect(Collectors.toList()));

      cowPlan.setBottomCowTankIds(
          cpd.getCowTankDetails().stream()
              .filter(v -> v.getCowTypeXid().equals(Common.COW_TYPE.BOTTOM_COW_VALUE))
              .map(CowTankDetail::getTankXid)
              .collect(Collectors.toList()));

      cowPlan.setAllCowTankIds(
          cpd.getCowTankDetails().stream()
              .filter(v -> v.getCowTypeXid().equals(Common.COW_TYPE.ALL_COW_VALUE))
              .map(CowTankDetail::getTankXid)
              .collect(Collectors.toList()));
    }

    if (!cpd.getCowWithDifferentCargos().isEmpty()) {
      List<CargoForCowDetails> cargoForCowDetails = new ArrayList<>();
      var gp1 =
          cpd.getCowWithDifferentCargos().stream()
              .collect(Collectors.groupingBy(CowWithDifferentCargo::getCargoXid)); // group by cargo
      for (Map.Entry<Long, List<CowWithDifferentCargo>> map1 : gp1.entrySet()) {
        CowWithDifferentCargo firstItem = map1.getValue().stream().findFirst().get();
        CargoForCowDetails cargoForCow = new CargoForCowDetails();
        cargoForCow.setCargoId(firstItem.getCargoXid());
        cargoForCow.setCargoNominationId(firstItem.getCargoNominationXid());
        cargoForCow.setWashingCargoId(firstItem.getWashingCargoXid());
        cargoForCow.setWashingCargoNominationId(firstItem.getWashingCargoNominationXid());
        cargoForCow.setTankIds(
            map1.getValue().stream()
                .map(CowWithDifferentCargo::getTankXid)
                .collect(Collectors.toList()));
        cargoForCowDetails.add(cargoForCow);
      }
      cowPlan.setCargoCowTankIds(cargoForCowDetails);
    }
    return cowPlan;
  }

  private DischargeSequences buildDischargeDelays(
      com.cpdss.dischargeplan.entity.DischargeInformation entity) {
    DischargeSequences dischargeSequences = new DischargeSequences();

    // Set Master data of Reasons
    List<com.cpdss.dischargeplan.entity.ReasonForDelay> list =
        this.reasonForDelayRepository.findAllByIsActiveTrue();
    List<ReasonForDelay> reasonsForDelay = new ArrayList<ReasonForDelay>();
    list.forEach(
        delayReason -> {
          ReasonForDelay reason = new ReasonForDelay();
          reason.setId(delayReason.getId());
          reason.setReason(delayReason.getReason());
          reasonsForDelay.add(reason);
        });
    dischargeSequences.setReasonForDelays(reasonsForDelay); // Master data

    // User Data from DB
    List<DischargeDelays> loadingDelaysList = new ArrayList<>();
    entity
        .getDischargingDelays()
        .forEach(
            delay -> {
              DischargeDelays ld = new DischargeDelays();
              ld.setCargoId(delay.getCargoXid());
              ld.setCargoNominationId(delay.getCargoNominationXid());
              ld.setDuration(delay.getDuration());
              ld.setId(delay.getId());
              ld.setDischargeInfoId(delay.getDischargingInformation().getId());
              ld.setQuantity(delay.getQuantity());
              ld.setReasonForDelayIds(
                  delay.getDischargingDelayReasons().stream()
                      .map(DischargingDelayReason::getId)
                      .collect(Collectors.toList()));
              loadingDelaysList.add(ld);
            });
    dischargeSequences.setLoadingDelays(loadingDelaysList);
    return dischargeSequences;
  }

  private CargoMachineryInUse buildDischargeMachines(
      com.cpdss.dischargeplan.entity.DischargeInformation entity) throws GenericServiceException {
    CargoMachineryInUse cargoMachineryInUse = new CargoMachineryInUse();
    Set<DischargingMachineryInUse> list = entity.getDischargingMachineryInUses();
    List<DischargeMachinesInUse> machineList = new ArrayList<>();

    VesselInfo.VesselPumpsResponse grpcReply =
        this.vesselInfoService.getVesselPumpsByVesselId(
            VesselInfo.VesselIdRequest.newBuilder().setVesselId(entity.getVesselXid()).build());

    if (!DischargePlanConstants.SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel pump details from Loadable-Study MS",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }

    for (DischargingMachineryInUse machine : list) {
      DischargeMachinesInUse loadingMachine = new DischargeMachinesInUse();
      loadingMachine.setCapacity(machine.getCapacity());
      loadingMachine.setId(machine.getId());
      loadingMachine.setLoadingInfoId(entity.getId());
      loadingMachine.setMachineId(machine.getMachineXid());
      loadingMachine.setMachineTypeId(machine.getMachineTypeXid());
      this.setMachineNameAndTypeByIdAndTypeId(
          grpcReply, machine.getMachineXid(), machine.getMachineTypeXid(), loadingMachine);
      machineList.add(loadingMachine);
    }
    cargoMachineryInUse.setMachinesInUses(machineList);
    return cargoMachineryInUse;
  }

  private void setMachineNameAndTypeByIdAndTypeId(
      VesselInfo.VesselPumpsResponse grpcReply,
      Long machineId,
      Integer typeId,
      DischargeMachinesInUse loadingMachine) {
    if (typeId == Common.MachineType.VESSEL_PUMP_VALUE) {
      Optional<VesselInfo.VesselPump> vesselPump =
          grpcReply.getVesselPumpList().stream().filter(v -> v.getId() == machineId).findFirst();
      if (vesselPump.isPresent()) {
        loadingMachine.setMachineName(vesselPump.get().getPumpName());
        loadingMachine.setMachineTypeName(Common.MachineType.VESSEL_PUMP.name());
      }
    }
    if (typeId == Common.MachineType.MANIFOLD_VALUE) {
      Optional<VesselInfo.VesselComponent> manifold =
          grpcReply.getVesselManifoldList().stream()
              .filter(v -> v.getId() == machineId)
              .findFirst();
      if (manifold.isPresent()) {
        loadingMachine.setMachineName(manifold.get().getComponentName());
        loadingMachine.setMachineTypeName(Common.MachineType.MANIFOLD.name());
        loadingMachine.setTankTypeName(manifold.get().getTankTypeName());
      }
    }
    if (typeId == Common.MachineType.BOTTOM_LINE_VALUE) {
      Optional<VesselInfo.VesselComponent> bottomLine =
          grpcReply.getVesselBottomLineList().stream()
              .filter(v -> v.getId() == machineId)
              .findFirst();
      if (bottomLine.isPresent()) {
        loadingMachine.setMachineName(bottomLine.get().getComponentName());
        loadingMachine.setMachineTypeName(Common.MachineType.BOTTOM_LINE.name());
      }
    }
  }

  private void buildDischargePlanPortWiseDetails(
      DischargeInformationAlgoRequest algoRequest,
      com.cpdss.dischargeplan.entity.DischargeInformation entity)
      throws GenericServiceException {
    LoadableStudy.LoadablePlanDetailsRequest.Builder requestBuilder =
        LoadableStudy.LoadablePlanDetailsRequest.newBuilder();
    requestBuilder.setLoadablePatternId(entity.getDischargingPatternXid());
    LoadableStudy.LoadablePatternPortWiseDetailsJson response =
        this.loadableStudyService.getLoadablePatternDetailsJson(requestBuilder.build());
    if (!response.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get Portwise details from Loadable-Study MS",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      algoRequest.setPlanPortWiseDetails(
          Arrays.asList(
              mapper.readValue(
                  response.getLoadablePatternDetails(), DischargePlanPortWiseDetails[].class)));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Failed to deserialize port wise details",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    buildOnHandQuantities(algoRequest, entity, response.getLoadableStudyId());
    buildOnBoardQuantities(algoRequest, entity, response.getLoadableStudyId());
  }

  private DischargeBerthDetails buildDischargeBerthDto(Long id) {
    DischargeBerthDetails berthDetails = new DischargeBerthDetails();
    List<DischargingBerthDetail> list =
        dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(id);
    if (!list.isEmpty()) {
      berthDetails.setSelectedBerths(
          list.stream()
              .map(
                  var -> {
                    BerthDetails dto = new BerthDetails();
                    Optional.ofNullable(
                            this.getBerthNameByPortIdAndBerthId(
                                var.getDischargingInformation().getPortXid(), var.getBerthXid()))
                        .ifPresent(dto::setBerthName);
                    Optional.ofNullable(var.getId()).ifPresent(dto::setDischargeBerthId);
                    Optional.ofNullable(var.getDischargingInformation().getId())
                        .ifPresent(dto::setDischargeInfoId);
                    Optional.ofNullable(var.getBerthXid()).ifPresent(dto::setBerthId);
                    Optional.ofNullable(var.getDepth()).ifPresent(dto::setMaxShipDepth);
                    Optional.ofNullable(var.getMaxManifoldHeight())
                        .ifPresent(dto::setMaxManifoldHeight);
                    Optional.ofNullable(var.getMaxManifoldPressure())
                        .ifPresent(dto::setMaxManifoldPressure);
                    Optional.ofNullable(var.getSeaDraftLimitation())
                        .ifPresent(dto::setSeaDraftLimitation);
                    Optional.ofNullable(var.getAirDraftLimitation())
                        .ifPresent(dto::setAirDraftLimitation);
                    Optional.ofNullable(var.getIsCargoCirculation())
                        .ifPresent(dto::setCargoCirculation);
                    Optional.ofNullable(var.getIsAirPurge()).ifPresent(dto::setAirPurge);
                    Optional.ofNullable(var.getLineContentDisplacement())
                        .ifPresent(dto::setLineDisplacement);
                    return dto;
                  })
              .collect(Collectors.toList()));
    }
    return berthDetails;
  }

  private String getBerthNameByPortIdAndBerthId(Long portXid, Long berthXid) {
    try {
      PortInfo.PortIdRequest.Builder idRequest = PortInfo.PortIdRequest.newBuilder();
      PortInfo.BerthInfoResponse response =
          portInfoServiceBlockingStub.getBerthDetailsByPortId(idRequest.setPortId(portXid).build());
      log.info(
          "Get berth Name ({}) from port service - status {}",
          berthXid,
          response.getResponseStatus().getStatus());
      if (response.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
        return response.getBerthsList().stream()
            .filter(v -> v.getId() == berthXid)
            .map(PortInfo.BerthDetail::getBerthName)
            .findFirst()
            .get();
      }
    } catch (Exception e) {

    }
    return null;
  }

  private void buildOnBoardQuantities(
      DischargeInformationAlgoRequest algoRequest,
      com.cpdss.dischargeplan.entity.DischargeInformation dischargeInformation,
      long loadableStudyId)
      throws NumberFormatException, GenericServiceException {
    log.info(
        "Populating onBoardQuantities of Port {} in Loadable Study {}",
        dischargeInformation.getPortXid(),
        loadableStudyId);
    LoadableStudy.OnBoardQuantityRequest request =
        LoadableStudy.OnBoardQuantityRequest.newBuilder()
            .setVoyageId(dischargeInformation.getVoyageXid())
            .setLoadableStudyId(loadableStudyId)
            .setVesselId(dischargeInformation.getVesselXid())
            .setPortId(dischargeInformation.getPortXid())
            .build();
    LoadableStudy.OnBoardQuantityReply grpcReply = loadableStudyService.getOnBoardQuantity(request);
    if (!DischargePlanConstants.SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch on board quantities from Loadable-Study MS",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }

    List<OnBoardQuantity> onBoardQuantities = new ArrayList<OnBoardQuantity>();
    grpcReply
        .getOnBoardQuantityList()
        .forEach(
            detail -> {
              OnBoardQuantity dto = new OnBoardQuantity();
              dto.setId(detail.getId());
              dto.setCargoId(0 == detail.getCargoId() ? null : detail.getCargoId());
              dto.setColorCode(isEmpty(detail.getColorCode()) ? null : detail.getColorCode());
              dto.setAbbreviation(
                  isEmpty(detail.getAbbreviation()) ? null : detail.getAbbreviation());
              dto.setSounding(
                  isEmpty(detail.getSounding())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getSounding()));
              dto.setQuantity(
                  isEmpty(detail.getWeight())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getWeight()));
              dto.setActualWeight(
                  isEmpty(detail.getActualWeight())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getActualWeight()));
              dto.setVolume(
                  isEmpty(detail.getVolume())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getVolume()));
              dto.setTankId(detail.getTankId());
              dto.setTankName(detail.getTankName());
              dto.setApi(
                  isEmpty(detail.getDensity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDensity()));
              if (detail.getTemperature() != null && detail.getTemperature().length() > 0) {
                dto.setTemperature(new BigDecimal(detail.getTemperature()));
              }

              onBoardQuantities.add(dto);
            });

    algoRequest.setOnBoardQuantity(onBoardQuantities);
  }

  private void buildOnHandQuantities(
      DischargeInformationAlgoRequest algoRequest,
      com.cpdss.dischargeplan.entity.DischargeInformation dischargeInformation,
      long loadableStudyId)
      throws NumberFormatException, GenericServiceException {
    log.info(
        "Populating onHandQuantities of Port Rotation {} in Loadable Study {}",
        dischargeInformation.getPortRotationXid(),
        loadableStudyId);
    LoadableStudy.OnHandQuantityRequest request =
        LoadableStudy.OnHandQuantityRequest.newBuilder()
            .setCompanyId(1L)
            .setVesselId(dischargeInformation.getVesselXid())
            .setLoadableStudyId(loadableStudyId)
            .setPortRotationId(dischargeInformation.getPortRotationXid())
            .build();
    LoadableStudy.OnHandQuantityReply grpcReply = loadableStudyService.getOnHandQuantity(request);
    if (!DischargePlanConstants.SUCCESS.equals(grpcReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch on hand quantities from Loadable-Study MS",
          grpcReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(grpcReply.getResponseStatus().getCode())));
    }

    List<OnHandQuantity> ohqList = new ArrayList<OnHandQuantity>();
    grpcReply
        .getOnHandQuantityList()
        .forEach(
            detail -> {
              OnHandQuantity onHandQuantity = new OnHandQuantity();
              onHandQuantity.setId(detail.getId());
              onHandQuantity.setTankId(detail.getTankId());
              onHandQuantity.setTankName(detail.getTankName());
              onHandQuantity.setFuelTypeId(detail.getFuelTypeId());
              onHandQuantity.setFuelTypeName(detail.getFuelType());
              onHandQuantity.setFuelTypeShortName(detail.getFuelTypeShortName());
              onHandQuantity.setPortRotationId(detail.getPortRotationId());
              onHandQuantity.setPortId(detail.getPortId());
              onHandQuantity.setArrivalQuantity(
                  isEmpty(detail.getArrivalQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getArrivalQuantity()));
              onHandQuantity.setActualArrivalQuantity(
                  isEmpty(detail.getActualArrivalQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getActualArrivalQuantity()));
              onHandQuantity.setArrivalVolume(
                  isEmpty(detail.getArrivalVolume())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getArrivalVolume()));
              onHandQuantity.setDepartureQuantity(
                  isEmpty(detail.getDepartureQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDepartureQuantity()));
              onHandQuantity.setActualDepartureQuantity(
                  isEmpty(detail.getActualDepartureQuantity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getActualDepartureQuantity()));
              onHandQuantity.setDepartureVolume(
                  isEmpty(detail.getDepartureVolume())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDepartureVolume()));
              onHandQuantity.setColorCode(
                  isEmpty(detail.getColorCode()) ? null : detail.getColorCode());
              onHandQuantity.setDensity(
                  isEmpty(detail.getDensity())
                      ? BigDecimal.ZERO
                      : new BigDecimal(detail.getDensity()));
              ohqList.add(onHandQuantity);
            });
    algoRequest.setOnHandQuantity(ohqList);
  }
}
