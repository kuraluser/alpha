/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADING_OPERATION_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.ApiTempHistory;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.CargoNominationValveSegregation;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.repository.ApiTempHistoryRepository;
import com.cpdss.loadablestudy.repository.CargoNominationOperationDetailsRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.CargoNominationValveSegregationRepository;
import com.cpdss.loadablestudy.repository.CargoOperationRepository;
import com.cpdss.loadablestudy.repository.CommingleCargoRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/** @author arun.j */
@Log4j2
@Service
@Transactional
public class CargoNominationService {

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private VoyageService voyageService;

  @Autowired private LoadablePatternService loadablePatternService;

  @Autowired private CommingleCargoRepository commingleCargoRepository;

  @Autowired private ApiTempHistoryRepository apiTempHistoryRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Autowired CargoOperationRepository cargoOperationRepository;

  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;

  @Autowired private SynopticService synopticService;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private CargoNominationValveSegregationRepository valveSegregationRepository;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceBlockingStub loadingPlanGrpcService;

  /**
   * fetch cargo nomination based on the loadable study id
   *
   * @param loadableStudyId
   * @return
   * @throws GenericServiceException
   */
  public List<CargoNomination> getCargoNominationByLoadableStudyId(Long loadableStudyId)
      throws GenericServiceException {
    List<CargoNomination> cargos = getCargoNominations(loadableStudyId);
    if (cargos.isEmpty()) {
      throw new GenericServiceException(
          "cargo nomination data missing",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    return cargos;
  }

  public List<CargoNomination> getCargoNominations(Long loadableStudyId) {
    List<CargoNomination> cargos =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
    return cargos;
  }

  public List<CargoNomination> saveDsichargeStudyCargoNominations(
      Long dischargeStudyId, Long loadableStudyId, Long portId, Long operationId)
      throws GenericServiceException {
    log.info("ds save API LS id :: " + loadableStudyId);
    List<CargoNomination> cargos = getCargoNominationByLoadableStudyId(loadableStudyId);
    log.info(
        "ds save API LS cargos :: "
            + cargos.stream().map(CargoNomination::getId).collect(Collectors.toList()));

    List<CargoNomination> dischargeStudycargos = new ArrayList<>();
    // Fetching max quantity from Bill of Ladding
    getMaxQuantityMTFromBillofLadding(cargos);
    cargos
        .parallelStream()
        .forEach(
            cargo -> {
              CargoNomination newCargo =
                  createDsCargoNomination(dischargeStudyId, cargo, portId, operationId);
              log.info(
                  "ds save API DS cargo... cargo id ::  "
                      + newCargo.getCargoXId()
                      + " , abb::"
                      + newCargo.getAbbreviation());
              dischargeStudycargos.add(newCargo);
            });
    List<CargoNomination> savedCargos = cargoNominationRepository.saveAll(dischargeStudycargos);
    log.info(
        "ds save API saved DS cargos :: "
            + savedCargos.stream().map(CargoNomination::getId).collect(Collectors.toList()));
    return savedCargos;
  }

  public CargoNomination createDsCargoNomination(
      Long dischargeStudyId, CargoNomination cargo, Long portId, Long operationId) {
    CargoNomination dischargeStudyCargo = new CargoNomination();
    dischargeStudyCargo.setAbbreviation(cargo.getAbbreviation());
    dischargeStudyCargo.setApi(cargo.getApi());
    dischargeStudyCargo.setCargoXId(cargo.getCargoXId());
    dischargeStudyCargo.setColor(cargo.getColor());
    dischargeStudyCargo.setIsActive(true);
    dischargeStudyCargo.setLoadableStudyXId(dischargeStudyId);
    dischargeStudyCargo.setMaxTolerance(cargo.getMaxTolerance());
    dischargeStudyCargo.setMinTolerance(cargo.getMinTolerance());
    dischargeStudyCargo.setPriority(cargo.getPriority());
    dischargeStudyCargo.setQuantity(cargo.getQuantity());
    dischargeStudyCargo.setSegregationXId(cargo.getSegregationXId());
    dischargeStudyCargo.setTemperature(cargo.getTemperature());
    dischargeStudyCargo.setVersion(cargo.getVersion());
    dischargeStudyCargo.setLsCargoNominationId(cargo.getId());
    dischargeStudyCargo.setCargoNominationPortDetails(
        createCargoNominationPortDetails(dischargeStudyCargo, cargo, portId, operationId));
    return dischargeStudyCargo;
  }

  private void getMaxQuantityMTFromBillofLadding(List<CargoNomination> dischargeStudycargos)
      throws GenericServiceException {

    List<Long> cargoIds =
        dischargeStudycargos
            .parallelStream()
            .map(CargoNomination::getId)
            .collect(Collectors.toList());
    MaxQuantityRequest.Builder request = MaxQuantityRequest.newBuilder();
    request.addAllCargoNominationId(cargoIds);
    MaxQuantityResponse cargoNominationMaxQuantityResponse =
        loadingPlanGrpcService.getCargoNominationMaxQuantity(request.build());
    if (!SUCCESS.equals(cargoNominationMaxQuantityResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "max quantity from loading plan is not available",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    List<MaxQuantityDetails> cargoMaxQuantityList =
        cargoNominationMaxQuantityResponse.getCargoMaxQuantityList();
    dischargeStudycargos.forEach(
        item -> {
          Optional<MaxQuantityDetails> itemQuantity =
              cargoMaxQuantityList.stream()
                  .filter(quantity -> item.getId().equals(quantity.getCargoNominationId()))
                  .findFirst();
          if (itemQuantity.isPresent()) {
            String api = itemQuantity.get().getApi();
            item.setApi(new BigDecimal(itemQuantity.get().getApi()));
          }
          item.setQuantity(
              itemQuantity.isPresent()
                  ? new BigDecimal(itemQuantity.get().getMaxQuantity())
                  : null);
          item.setApi(
              itemQuantity.isPresent() ? new BigDecimal(itemQuantity.get().getApi()) : null);
          item.setTemperature(
              itemQuantity.isPresent() ? new BigDecimal(itemQuantity.get().getTemp()) : null);
        });
  }

  public Set<CargoNominationPortDetails> createCargoNominationPortDetails(
      CargoNomination dischargeStudyCargo, CargoNomination cargo, Long portId, Long operationId) {
    CargoNominationPortDetails portDetail = new CargoNominationPortDetails();
    portDetail.setPortId(portId);
    portDetail.setOperationId(operationId);
    portDetail.setIsActive(true);
    portDetail.setCargoNomination(dischargeStudyCargo);

    if (cargo != null) {
      portDetail.setQuantity(cargo.getQuantity());
      portDetail.setMode(2L);
    } else {
      portDetail.setQuantity(new BigDecimal(0));
      portDetail.setMode(1L);
    }
    return new HashSet<CargoNominationPortDetails>(Arrays.asList(portDetail));
  }

  public LoadableStudy.CargoNominationReply.Builder saveCargoNomination(
      LoadableStudy.CargoNominationRequest request,
      LoadableStudy.CargoNominationReply.Builder cargoNominationReplyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudy.isPresent()) {
      throw new GenericServiceException(
          "Loadable Study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyRecord = loadableStudy.get();
    this.voyageService.checkIfVoyageClosed(loadableStudy.get().getVoyage().getId());
    loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudy.get());

    CargoNomination cargoNomination = null;
    List<Long> existingCargoPortIds = null;
    ApiTempHistory apiTempHistory = null;
    if (request.getCargoNominationDetail() != null
        && request.getCargoNominationDetail().getId() != 0) {
      Optional<CargoNomination> existingCargoNomination =
          this.cargoNominationRepository.findByIdAndIsActive(
              request.getCargoNominationDetail().getId(), true);
      if (!existingCargoNomination.isPresent()) {
        throw new GenericServiceException(
            "Cargo Nomination does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      cargoNomination = existingCargoNomination.get();

      if (existingCargoNomination.get().getCargoXId()
          != request.getCargoNominationDetail().getCargoId()) {
        this.commingleCargoRepository.deleteCommingleCargoByLodableStudyXIdAndCargoXId(
            loadableStudyRecord.getId(), existingCargoNomination.get().getCargoXId());
      }

      if (!CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
        existingCargoPortIds =
            cargoNomination.getCargoNominationPortDetails().stream()
                .map(CargoNominationPortDetails::getPortId)
                .collect(Collectors.toList());
      }
      cargoNomination = buildCargoNomination(cargoNomination, request, loadableStudyRecord);
      apiTempHistory = buildApiTempHistory(cargoNomination, request, existingCargoPortIds);
    } else if (request.getCargoNominationDetail() != null
        && request.getCargoNominationDetail().getId() == 0) {
      cargoNomination = new CargoNomination();
      cargoNomination = buildCargoNomination(cargoNomination, request, loadableStudyRecord);
      apiTempHistory = buildApiTempHistory(cargoNomination, request, existingCargoPortIds);
    }

    // update port rotation table with loading ports from cargo nomination

    // validate if requested are already added as transit ports
    if (!cargoNomination.getCargoNominationPortDetails().isEmpty()) {
      List<Long> requestedPortIds =
          cargoNomination.getCargoNominationPortDetails().stream()
              .map(CargoNominationPortDetails::getPortId)
              .collect(Collectors.toList());
      loadableStudyPortRotationService.validateTransitPorts(loadableStudyRecord, requestedPortIds);
    }
    // update loadable study level isCargoNominationComplete status
    if (request.getCargoNominationDetail() != null) {
      loadableStudyRecord.setIsCargoNominationComplete(
          request.getCargoNominationDetail().getIsCargoNominationComplete());
      this.loadableStudyRepository.save(loadableStudyRecord);
    }

    this.cargoNominationRepository.save(cargoNomination);
    this.apiTempHistoryRepository.save(apiTempHistory);
    this.updatePortRotationWithLoadingPorts(
        loadableStudyRecord, cargoNomination, existingCargoPortIds);
    cargoNominationReplyBuilder
        .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS))
        .setCargoNominationId((cargoNomination.getId() != null) ? cargoNomination.getId() : 0);
    return cargoNominationReplyBuilder;
  }

  private CargoNomination buildCargoNomination(
      CargoNomination cargoNomination,
      LoadableStudy.CargoNominationRequest request,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy)
      throws GenericServiceException {
    if (!request.getCargoNominationDetail().getLoadingPortDetailsList().isEmpty()) {
      List<Long> requestedPortIds =
          request.getCargoNominationDetail().getLoadingPortDetailsList().stream()
              .map(LoadableStudy.LoadingPortDetail::getPortId)
              .collect(Collectors.toList());
      List<Long> transitPorts =
          this.loadableStudyPortRotationRepository.getTransitPorts(loadableStudy, requestedPortIds);
      if (!CollectionUtils.isEmpty(transitPorts)) {
        throw new GenericServiceException(
            "Ports exist as transit ports "
                + StringUtils.collectionToCommaDelimitedString(transitPorts),
            CommonErrorCodes.E_CPDSS_TRANSIT_PORT_EXISTS,
            HttpStatusCode.BAD_REQUEST);
      }
    }
    cargoNomination.setLoadableStudyXId(request.getCargoNominationDetail().getLoadableStudyId());
    cargoNomination.setPriority(request.getCargoNominationDetail().getPriority());
    cargoNomination.setCargoXId(request.getCargoNominationDetail().getCargoId());
    cargoNomination.setAbbreviation(request.getCargoNominationDetail().getAbbreviation());
    cargoNomination.setColor(request.getCargoNominationDetail().getColor());
    cargoNomination.setQuantity(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getQuantity())
            ? new BigDecimal(request.getCargoNominationDetail().getQuantity())
            : null);
    cargoNomination.setMaxTolerance(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getMaxTolerance())
            ? new BigDecimal(request.getCargoNominationDetail().getMaxTolerance())
            : null);
    cargoNomination.setMinTolerance(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getMinTolerance())
            ? new BigDecimal(request.getCargoNominationDetail().getMinTolerance())
            : null);
    cargoNomination.setApi(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getApiEst())
            ? new BigDecimal(request.getCargoNominationDetail().getApiEst())
            : null);
    cargoNomination.setTemperature(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getTempEst())
            ? new BigDecimal(request.getCargoNominationDetail().getTempEst())
            : null);
    cargoNomination.setSegregationXId(request.getCargoNominationDetail().getSegregationId());
    // activate the records to be saved
    cargoNomination.setIsActive(true);
    if (!request.getCargoNominationDetail().getLoadingPortDetailsList().isEmpty()) {
      // clear any existing CargoNominationPortDetails otherwise create new
      if (cargoNomination.getCargoNominationPortDetails() != null) {
        cargoNomination.getCargoNominationPortDetails().clear();
      }
      Set<CargoNominationPortDetails> cargoNominationPortDetailsList =
          request.getCargoNominationDetail().getLoadingPortDetailsList().stream()
              .map(
                  loadingPortDetail -> {
                    CargoNominationPortDetails cargoNominationPortDetails =
                        new CargoNominationPortDetails();
                    cargoNominationPortDetails.setCargoNomination(cargoNomination);
                    cargoNominationPortDetails.setPortId(loadingPortDetail.getPortId());
                    cargoNominationPortDetails.setQuantity(
                        !loadingPortDetail.getQuantity().isEmpty()
                            ? new BigDecimal(loadingPortDetail.getQuantity())
                            : null);
                    cargoNominationPortDetails.setIsActive(true);
                    return cargoNominationPortDetails;
                  })
              .collect(Collectors.toSet());
      // clear any existing CargoNominationPortDetails otherwise create new
      if (cargoNomination.getCargoNominationPortDetails() != null) {
        cargoNomination.getCargoNominationPortDetails().addAll(cargoNominationPortDetailsList);
      } else {
        cargoNomination.setCargoNominationPortDetails(cargoNominationPortDetailsList);
      }
    }
    return cargoNomination;
  }

  private ApiTempHistory buildApiTempHistory(
      CargoNomination cargoNomination,
      LoadableStudy.CargoNominationRequest request,
      List<Long> existingCargoPortIds) {

    Long portId = null;
    if (existingCargoPortIds != null && existingCargoPortIds.size() > 0) {
      portId = existingCargoPortIds.stream().findFirst().get();
    }
    return ApiTempHistory.builder()
        .vesselId(request.getVesselId())
        .cargoId(cargoNomination.getCargoXId())
        .loadingPortId(portId != null ? portId : null)
        .api(cargoNomination.getApi())
        .isActive(true)
        .temp(cargoNomination.getTemperature())
        .build();
  }

  /**
   * Fetch ports for the specific loadableStudy already available in port rotation and update to
   * port rotation along with port master fields water density, maxDraft, maxAirDraft only those
   * ports that are not already available
   *
   * @param cargoNomination
   * @throws GenericServiceException
   */
  private void updatePortRotationWithLoadingPorts(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      CargoNomination cargoNomination,
      List<Long> existingCargoPortIds)
      throws GenericServiceException {
    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
            loadableStudy, cargoOperationRepository.getOne(LOADING_OPERATION_ID), true);
    List<Long> requestedPortIds = null;
    List<Long> existingPortIds = null;
    if (!cargoNomination.getCargoNominationPortDetails().isEmpty()) {
      requestedPortIds =
          cargoNomination.getCargoNominationPortDetails().stream()
              .map(CargoNominationPortDetails::getPortId)
              .collect(Collectors.toList());
    }
    if (!loadableStudyPortRotations.isEmpty()) {
      existingPortIds =
          loadableStudyPortRotations.stream()
              .map(LoadableStudyPortRotation::getPortXId)
              .collect(Collectors.toList());
    }
    // remove existing cargo portIds from port rotation and synoptical if not
    // available in request
    if (!CollectionUtils.isEmpty(requestedPortIds)
        && !CollectionUtils.isEmpty(existingCargoPortIds)) {
      existingCargoPortIds.removeAll(requestedPortIds);
      if (!CollectionUtils.isEmpty(existingCargoPortIds)) {
        existingCargoPortIds.forEach(
            existingPortId -> {
              Long otherCargoRefExistCount =
                  this.cargoNominationRepository.getCountCargoNominationWithPortIds(
                      cargoNomination.getLoadableStudyXId(), cargoNomination, existingPortId);
              if (Objects.equals(otherCargoRefExistCount, Long.valueOf("0"))) {
                log.info("deleting port {}", existingPortId);
                loadableStudyPortRotationRepository.deleteLoadingPortRotationByPort(
                    loadableStudy, existingPortId);
                synopticalTableRepository.deleteSynopticalPorts(
                    loadableStudy.getId(), existingPortId);
                onHandQuantityRepository.deleteByLoadableStudyAndPortXId(
                    loadableStudy, existingPortId);
              }
            });
      }
    }

    // Set port ordering after deletion
    loadableStudyPortRotationService.setPortOrdering(loadableStudy);

    // remove loading portIds from request which are already available in port
    // rotation for the
    // specific loadable study
    if (!CollectionUtils.isEmpty(requestedPortIds) && !CollectionUtils.isEmpty(existingPortIds)) {
      requestedPortIds.removeAll(existingPortIds);
    }
    // fetch the specific ports attributes like waterDensity and draft values from
    // port master
    if (!CollectionUtils.isEmpty(requestedPortIds)) {
      PortInfo.GetPortInfoByPortIdsRequest.Builder reqBuilder =
          PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
      buildGetPortInfoByPortIdsRequest(reqBuilder, cargoNomination);
      PortInfo.PortReply portReply = portInfoGrpcService.getPortInfoByPortIds(reqBuilder.build());
      if (portReply != null
          && portReply.getResponseStatus() != null
          && !SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Error in calling port service",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      // update loadable-study-port-rotation with ports from cargoNomination and port
      // attributes
      buildAndSaveLoadableStudyPortRotationEntities(loadableStudy, requestedPortIds, portReply);
      Boolean isPortRotationComplete = true;
      for (LoadableStudyPortRotation portRotation :
          this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
              loadableStudy.getId(), true)) {
        portRotation.setIsPortRotationOhqComplete(false);
        if ((portRotation.getSeaWaterDensity() == null)
            || (portRotation.getMaxDraft() == null)
            || (portRotation.getAirDraftRestriction() == null)) {
          isPortRotationComplete = false;
        }
      }
      loadableStudy.setIsPortsComplete(isPortRotationComplete);
      this.loadableStudyRepository.save(loadableStudy);
      // Set port ordering after updation
      loadableStudyPortRotationService.setPortOrdering(loadableStudy);
    }

    //    AtomicLong newPortOrder = new AtomicLong(0);
    //    loadableStudyPortRotations.forEach(
    //        portRotation -> {
    //          portRotation.setPortOrder(newPortOrder.incrementAndGet());
    //        });
    // this.loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotations);
  }

  /**
   * Builds the request for fetching the port attributes from port master
   *
   * @param cargoNomination
   */
  private void buildGetPortInfoByPortIdsRequest(
      PortInfo.GetPortInfoByPortIdsRequest.Builder reqBuilder, CargoNomination cargoNomination) {
    // build fetch port details request object
    if (cargoNomination != null
        && !CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
      cargoNomination
          .getCargoNominationPortDetails()
          .forEach(
              loadingPort -> {
                ofNullable(loadingPort.getPortId()).ifPresent(reqBuilder::addId);
              });
    }
  }

  /**
   * Create Port rotation entities for each loading port from cargoNomination with pre-populate port
   * master attributes
   *
   * @param loadableStudy
   * @param requestedPortIds
   * @param portReply
   */
  private void buildAndSaveLoadableStudyPortRotationEntities(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      List<Long> requestedPortIds,
      PortInfo.PortReply portReply) {
    if (!CollectionUtils.isEmpty(requestedPortIds)
        && portReply != null
        && !CollectionUtils.isEmpty(portReply.getPortsList())) {
      AtomicLong atomLong =
          new AtomicLong(
              loadableStudyPortRotationService.findMaxPortOrderForLoadableStudy(loadableStudy));
      List<LoadableStudyPortRotation> portRotationList = new ArrayList<>();
      requestedPortIds.stream()
          .forEach(
              requestedPortId ->
                  portReply.getPortsList().stream()
                      .filter(port -> Objects.equals(requestedPortId, port.getId()))
                      .forEach(
                          port -> {
                            LoadableStudyPortRotation portRotationEntity =
                                new LoadableStudyPortRotation();
                            portRotationEntity.setLoadableStudy(loadableStudy);
                            portRotationEntity.setPortXId(port.getId());
                            portRotationEntity.setOperation(
                                this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));
                            portRotationEntity.setSeaWaterDensity(
                                !StringUtils.isEmpty(port.getWaterDensity())
                                    ? new BigDecimal(port.getWaterDensity())
                                    : null);
                            portRotationEntity.setMaxDraft(
                                !StringUtils.isEmpty(port.getMaxDraft())
                                    ? new BigDecimal(port.getMaxDraft())
                                    : null);
                            portRotationEntity.setAirDraftRestriction(
                                !StringUtils.isEmpty(port.getMaxAirDraft())
                                    ? new BigDecimal(port.getMaxAirDraft())
                                    : null);
                            portRotationEntity.setPortOrder(atomLong.incrementAndGet());
                            // add ports to synoptical table by reusing the function called by
                            // port-rotation flow
                            synopticService.buildPortsInfoSynopticalTable(
                                portRotationEntity, LOADING_OPERATION_ID, port.getId());
                            portRotationList.add(portRotationEntity);
                          }));
      loadableStudyPortRotationRepository.saveAll(portRotationList);
    }
  }

  public LoadableStudy.CargoNominationReply.Builder getCargoNominationById(
      LoadableStudy.CargoNominationRequest request,
      LoadableStudy.CargoNominationReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(request.getLoadableStudyId());
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    List<CargoNomination> cargoNominationList =
        this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
            request.getLoadableStudyId(), true);

    List<ApiTempHistory> apiTempHistories =
        apiTempHistoryRepository.findByOrderByCreatedDateTimeDesc();

    buildCargoNominationReply(cargoNominationList, apiTempHistories, replyBuilder);
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }

  private void buildCargoNominationReply(
      List<CargoNomination> cargoNominationList,
      List<ApiTempHistory> apiTempHistoriesAll,
      com.cpdss.common.generated.LoadableStudy.CargoNominationReply.Builder
          cargoNominationReplyBuilder) {
    if (!CollectionUtils.isEmpty(cargoNominationList)) {
      cargoNominationList.forEach(
          cargoNomination -> {
            LoadableStudy.CargoNominationDetail.Builder builder =
                LoadableStudy.CargoNominationDetail.newBuilder();
            ofNullable(cargoNomination.getId()).ifPresent(builder::setId);
            ofNullable(cargoNomination.getLoadableStudyXId())
                .ifPresent(builder::setLoadableStudyId);
            ofNullable(cargoNomination.getPriority()).ifPresent(builder::setPriority);
            ofNullable(cargoNomination.getColor()).ifPresent(builder::setColor);
            ofNullable(cargoNomination.getCargoXId()).ifPresent(builder::setCargoId);
            ofNullable(cargoNomination.getAbbreviation()).ifPresent(builder::setAbbreviation);
            Optional.ofNullable(cargoNomination.getApi())
                .ifPresent(val -> builder.setApi(String.valueOf(val)));
            Optional.ofNullable(cargoNomination.getTemperature())
                .ifPresent(val -> builder.setTemperature(String.valueOf(val)));

            ofNullable(cargoNomination.getQuantity())
                .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
            // build inner loadingPort details object
            if (!CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
              cargoNomination.getCargoNominationPortDetails().stream()
                  .filter(operation -> operation.getIsActive())
                  .forEach(
                      loadingPort -> {
                        if (loadingPort.getIsActive()) {
                          LoadableStudy.LoadingPortDetail.Builder loadingPortDetailBuilder =
                              LoadableStudy.LoadingPortDetail.newBuilder();
                          ofNullable(loadingPort.getPortId())
                              .ifPresent(loadingPortDetailBuilder::setPortId);
                          ofNullable(loadingPort.getQuantity())
                              .ifPresent(
                                  quantity ->
                                      loadingPortDetailBuilder.setQuantity(
                                          String.valueOf(quantity)));
                          ofNullable(loadingPort.getMode())
                              .ifPresent(loadingPortDetailBuilder::setMode);
                          ofNullable(loadingPort.getOperationId())
                              .ifPresent(loadingPortDetailBuilder::setOperationId);
                          builder.addLoadingPortDetails(loadingPortDetailBuilder);
                        }
                      });
            }

            if (!CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
              CargoNominationPortDetails cargoNominationPortDetail =
                  cargoNomination.getCargoNominationPortDetails().iterator().next();
              if (cargoNomination.getCargoXId() != null
                  && cargoNominationPortDetail.getPortId() != null) {

                List<ApiTempHistory> apiTempHistories =
                    apiTempHistoryRepository
                        .findByLoadingPortIdAndCargoIdAndIsActiveOrderByCreatedDateTimeDesc(
                            cargoNominationPortDetail.getPortId(),
                            cargoNomination.getCargoXId(),
                            true);
                if (!CollectionUtils.isEmpty(apiTempHistories)) {
                  ApiTempHistory apiTempHistory = apiTempHistories.get(0);
                  Optional.ofNullable(apiTempHistory.getApi())
                      .ifPresent(api -> builder.setApiEst(String.valueOf(api)));
                  Optional.ofNullable(apiTempHistory.getTemp())
                      .ifPresent(temperature -> builder.setTempEst(String.valueOf(temperature)));
                }
                Optional.ofNullable(cargoNomination.getApi())
                    .ifPresent(api -> builder.setApiEst(String.valueOf(api)));
                Optional.ofNullable(cargoNomination.getTemperature())
                    .ifPresent(temperature -> builder.setTempEst(String.valueOf(temperature)));
              }
            }
            Optional.ofNullable(cargoNomination.getMaxTolerance())
                .ifPresent(maxTolerance -> builder.setMaxTolerance(String.valueOf(maxTolerance)));
            ofNullable(cargoNomination.getMinTolerance())
                .ifPresent(minTolerance -> builder.setMinTolerance(String.valueOf(minTolerance)));
            ofNullable(cargoNomination.getSegregationXId()).ifPresent(builder::setSegregationId);
            ofNullable(getMaxQuantityFromBillOfLadding(cargoNomination.getLsCargoNominationId()))
                .ifPresent(builder::setMaxQuantity);
            cargoNominationReplyBuilder.addCargoNominations(builder);

            if (!CollectionUtils.isEmpty(apiTempHistoriesAll)) {

              apiTempHistoriesAll.forEach(
                  apiTempHistory -> {
                    LoadableStudy.CargoHistoryDetail.Builder cargoBuilder =
                        LoadableStudy.CargoHistoryDetail.newBuilder();

                    Optional.ofNullable(apiTempHistory.getCargoId())
                        .ifPresent(cargoBuilder::setCargoId);

                    Optional.ofNullable(apiTempHistory.getVesselId())
                        .ifPresent(cargoBuilder::setVesselId);

                    Optional.ofNullable(apiTempHistory.getLoadingPortId())
                        .ifPresent(cargoBuilder::setLoadingPortId);

                    if (apiTempHistory.getApi() != null) {
                      Optional.ofNullable(apiTempHistory.getApi().toString())
                          .ifPresent(cargoBuilder::setApi);
                    }

                    if (apiTempHistory.getTemp() != null) {
                      Optional.ofNullable(apiTempHistory.getTemp().toString())
                          .ifPresent(cargoBuilder::setTemperature);
                    }

                    cargoNominationReplyBuilder.addCargoHistory(cargoBuilder);
                  });
            }
          });
    }
  }

  // Get max Quantity in a Cargo Nomination
  public String getMaxQuantityFromBillOfLadding(Long cargoNominationId) {
    if (cargoNominationId != null) {

      log.info(
          "Getting max quantity of each cargo nomination, from loading plan {}", cargoNominationId);
      BillOfLaddingRequest request =
          BillOfLaddingRequest.newBuilder().setCargoNominationId(cargoNominationId).build();
      LoadingInformationSynopticalReply reply =
          loadingPlanGrpcService.getBillOfLaddingDetails(request);
      if (SUCCESS.equals(reply.getResponseStatus().getStatus())
          && !CollectionUtils.isEmpty(reply.getBillOfLaddingList())) {
        if (reply.getBillOfLaddingList().size() == 1) {
          return reply.getBillOfLaddingList().get(0).getQuantityKl();
        } else {
          return String.valueOf(
              reply.getBillOfLaddingList().stream()
                  .map(item -> new BigDecimal(item.getQuantityKl()))
                  .reduce(BigDecimal::add)
                  .get());
        }
      }
    }
    log.info("No Bill of ladding data present for cargo nomination {}", cargoNominationId);
    return null;
  }

  public LoadableStudy.ValveSegregationReply.Builder getValveSegregation(
      LoadableStudy.ValveSegregationRequest request,
      LoadableStudy.ValveSegregationReply.Builder reply) {
    Iterable<CargoNominationValveSegregation> segregationsList =
        valveSegregationRepository.findAll();
    segregationsList.forEach(
        segregation -> {
          LoadableStudy.ValveSegregation.Builder segregationDetail =
              LoadableStudy.ValveSegregation.newBuilder();
          if (segregation.getId() != null) {
            segregationDetail.setId(segregation.getId());
          }
          if (!StringUtils.isEmpty(segregation.getName())) {
            segregationDetail.setName(segregation.getName());
          }
          reply.addValveSegregation(segregationDetail);
        });
    Common.ResponseStatus.Builder responseStatus = Common.ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    reply.setResponseStatus(responseStatus);
    return reply;
  }

  public LoadableStudy.CargoNominationReply.Builder deleteCargoNomination(
      LoadableStudy.CargoNominationRequest request,
      LoadableStudy.CargoNominationReply.Builder cargoNominationReplyBuilder)
      throws GenericServiceException {
    Optional<CargoNomination> existingCargoNomination =
        this.cargoNominationRepository.findById(request.getCargoNominationId());
    if (!existingCargoNomination.isPresent()) {
      throw new GenericServiceException(
          "Cargo Nomination does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    this.validateDeleteCargoNomination(existingCargoNomination.get());

    this.commingleCargoRepository.deleteCommingleCargoByLodableStudyXId(
        existingCargoNomination.get().getLoadableStudyXId());
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findById(existingCargoNomination.get().getLoadableStudyXId());
    if (loadableStudyOpt.isPresent()) {
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy = loadableStudyOpt.get();
      loadableStudy.setDischargeCargoNominationId(null);
      this.loadableStudyRepository.save(loadableStudy);
    }
    this.cargoNominationOperationDetailsRepository.deleteCargoNominationPortDetails(
        request.getCargoNominationId());
    /*
     * delete respective loading ports from port rotation table if ports not
     * associated with any other cargo nomination belonging to the same loadable
     * study
     */
    if (!existingCargoNomination.get().getCargoNominationPortDetails().isEmpty()) {
      List<Long> requestedPortIds =
          existingCargoNomination.get().getCargoNominationPortDetails().stream()
              .map(CargoNominationPortDetails::getPortId)
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(requestedPortIds)) {
        requestedPortIds.forEach(
            requestPortId -> {
              Long otherCargoRefExistCount =
                  this.cargoNominationRepository.getCountCargoNominationWithPortIds(
                      existingCargoNomination.get().getLoadableStudyXId(),
                      existingCargoNomination.get(),
                      requestPortId);
              if (Objects.equals(otherCargoRefExistCount, Long.valueOf("0"))
                  && loadableStudyOpt.isPresent()) {
                loadableStudyPortRotationRepository.deleteLoadingPortRotationByPort(
                    loadableStudyOpt.get(), requestPortId);
                synopticalTableRepository.deleteSynopticalPorts(
                    loadableStudyOpt.get().getId(), requestPortId);
                onHandQuantityRepository.deleteByLoadableStudyAndPortXId(
                    loadableStudyOpt.get(), requestPortId);
              }
            });
      }
    }

    // Setting port order after deletion
    if (loadableStudyOpt.isPresent()) {
      loadableStudyPortRotationService.setPortOrdering(loadableStudyOpt.get());
    }

    this.cargoNominationRepository.deleteCargoNomination(request.getCargoNominationId());
    cargoNominationReplyBuilder.setResponseStatus(
        Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    return cargoNominationReplyBuilder;
  }

  void validateDeleteCargoNomination(CargoNomination cargoNomination)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> entityOpt =
        this.loadableStudyRepository.findById(cargoNomination.getLoadableStudyXId());
    if (!entityOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    this.voyageService.checkIfVoyageClosed(entityOpt.get().getVoyage().getId());
    loadablePatternService.isPatternGeneratedOrConfirmed(entityOpt.get());
  }

  public LoadableStudy.CargoNominationDetailReply.Builder getCargoNominationByCargoNominationId(
      LoadableStudy.CargoNominationRequest request,
      LoadableStudy.CargoNominationDetailReply.Builder builder) {
    CargoNomination cargoNomination =
        this.cargoNominationRepository.getOne(request.getCargoNominationId());
    if (cargoNomination != null) {
      com.cpdss.common.generated.LoadableStudy.CargoNominationDetail.Builder cargoNom =
          com.cpdss.common.generated.LoadableStudy.CargoNominationDetail.newBuilder();
      cargoNom.setId(cargoNomination.getId());
      cargoNom.setAbbreviation(cargoNomination.getAbbreviation());
      cargoNom.setColor(cargoNomination.getColor());
      CargoInfo.CargoRequest.Builder cargoReq = CargoInfo.CargoRequest.newBuilder();
      cargoReq.setCargoId(cargoNomination.getCargoXId());
      com.cpdss.common.generated.CargoInfo.CargoDetailReply cargoDetailReply =
          this.getCargoInfoById(cargoReq.build());
      cargoNom.setCargoName(cargoDetailReply.getCargoDetail().getCrudeType());
      cargoNom.setCargoId(cargoNomination.getCargoXId());
      Optional.ofNullable(cargoNomination.getApi())
          .ifPresent(api -> cargoNom.setApi(api.toString()));
      builder.setCargoNominationdetail(cargoNom);
    }
    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  public com.cpdss.common.generated.CargoInfo.CargoDetailReply getCargoInfoById(
      CargoInfo.CargoRequest build) {
    return cargoInfoGrpcService.getCargoInfoById(build);
  }

  /** @param loadableStudy void */
  public void validateLoadableStudyWithCommingle(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy) throws GenericServiceException {
    List<CargoNomination> cargoNominations =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudy.getId(), true);
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos =
        commingleCargoRepository.findByLoadableStudyXIdAndPurposeXidAndIsActive(
            loadableStudy.getId(), 2L, true);
    if (!cargoNominations.isEmpty() && !commingleCargos.isEmpty()) {
      BigDecimal cargoSum =
          cargoNominations.stream().map(CargoNomination::getQuantity).reduce(BigDecimal::add).get();
      BigDecimal commingleCargoSum =
          commingleCargos.stream()
              .map(com.cpdss.loadablestudy.entity.CommingleCargo::getQuantity)
              .reduce(BigDecimal::add)
              .get();
      if (commingleCargoSum.compareTo(cargoSum) == 1) {
        log.info("commingle quanity is gerater for LS - {}", loadableStudy.getId());
        throw new GenericServiceException(
            "Commingle quanity is gerater for LS - {}" + loadableStudy.getId(),
            CommonErrorCodes.E_CPDSS_LS_INVALID_COMMINGLE_QUANTITY,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
    }
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy void
   * @param modelMapper
   */
  public void buildCargoNominationDetails(
      long loadableStudyId,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    List<CargoNomination> cargoNominations =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);

    loadableStudy.setCargoNomination(new ArrayList<>());
    cargoNominations.forEach(
        cargoNomination -> {
          com.cpdss.loadablestudy.domain.CargoNomination cargoNominationDto =
              new com.cpdss.loadablestudy.domain.CargoNomination();
          cargoNominationDto =
              modelMapper.map(
                  cargoNomination, com.cpdss.loadablestudy.domain.CargoNomination.class);
          CargoInfo.CargoRequest.Builder cargoReq = CargoInfo.CargoRequest.newBuilder();
          cargoReq.setCargoId(cargoNomination.getCargoXId());
          com.cpdss.common.generated.CargoInfo.CargoDetailReply cargoDetailReply =
              this.getCargoInfoById(cargoReq.build());
          if (cargoDetailReply.getResponseStatus().getStatus().equals(SUCCESS)) {
            log.info("Fetched cargo info of cargo with id {}", cargoNomination.getCargoXId());
            cargoNominationDto.setIsCondensateCargo(
                cargoDetailReply.getCargoDetail().getIsCondensateCargo());
            cargoNominationDto.setIsHrvpCargo(cargoDetailReply.getCargoDetail().getIsHrvpCargo());
          } else {
            log.error(
                "Could not fetch cargo info of cargo with id {}", cargoNomination.getCargoXId());
          }
          loadableStudy.getCargoNomination().add(cargoNominationDto);
        });
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy void
   */
  public void buildCargoNominationPortDetails(
      long loadableStudyId, com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
    List<CargoNomination> cargoNominations =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
    loadableStudy.setCargoNominationOperationDetails(new ArrayList<>());
    List<CargoNominationPortDetails> cargoNominationOperationDetails =
        cargoNominationOperationDetailsRepository.findByCargoNominationAndIsActive(
            cargoNominations, true);
    cargoNominationOperationDetails.forEach(
        cargoNominationOperationDetail -> {
          com.cpdss.loadablestudy.domain.CargoNominationOperationDetails
              cargoNominationOperationDetailDto =
                  new com.cpdss.loadablestudy.domain.CargoNominationOperationDetails();
          cargoNominationOperationDetailDto.setCargoNominationId(
              cargoNominationOperationDetail.getCargoNomination().getId());
          cargoNominationOperationDetailDto.setId(cargoNominationOperationDetail.getId());
          cargoNominationOperationDetailDto.setPortId(cargoNominationOperationDetail.getPortId());
          cargoNominationOperationDetailDto.setQuantity(
              String.valueOf(cargoNominationOperationDetail.getQuantity()));
          loadableStudy.getCargoNominationOperationDetails().add(cargoNominationOperationDetailDto);
        });
  }

  public void saveAll(List<CargoNomination> entities) {
    cargoNominationRepository.saveAll(entities);
  }

  public List<CargoNomination> getMaxQuantityForCargoNomination(
      List<Long> cargoNominations, Set<CargoNomination> firstPortCargos)
      throws GenericServiceException {
    MaxQuantityRequest.Builder request = MaxQuantityRequest.newBuilder();
    request.addAllCargoNominationId(cargoNominations);
    MaxQuantityResponse cargoNominationMaxQuantityResponse =
        loadingPlanGrpcService.getCargoNominationMaxQuantity(request.build());
    if (!SUCCESS.equals(cargoNominationMaxQuantityResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "max quantity from loading plan is not available",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    List<MaxQuantityDetails> cargoMaxQuantityList =
        cargoNominationMaxQuantityResponse.getCargoMaxQuantityList();
    cargoMaxQuantityList
        .parallelStream()
        .forEach(
            quantity -> {
              Optional<CargoNomination> cargoNomination =
                  firstPortCargos.stream()
                      .filter(
                          cargo ->
                              cargo
                                  .getLsCargoNominationId()
                                  .equals(quantity.getCargoNominationId()))
                      .findFirst();
              if (cargoNomination.isPresent()) {
                cargoNomination.get().setQuantity(new BigDecimal(quantity.getMaxQuantity()));
              }
            });
    return new ArrayList<CargoNomination>(firstPortCargos);
  }
}
