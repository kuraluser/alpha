/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.ApiTempHistorySpecification;
import com.cpdss.loadablestudy.domain.SearchCriteria;
import com.cpdss.loadablestudy.domain.VoyageHistoryDto;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Master Service For CargoService Operations
 *
 * @author vinothkumar M
 * @since 27-05-2021
 */
@Slf4j
@Service
public class CargoService {

  @Autowired private ApiTempHistoryRepository apiTempHistoryRepository;
  @Autowired private PurposeOfCommingleRepository purposeOfCommingleRepository;
  @Autowired private CommingleCargoRepository commingleCargoRepository;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private LoadableStudyService loadableStudyService;
  @Autowired private OnHandQuantityService onHandQuantityService;
  @Autowired private VoyageService voyageService;
  @Autowired private LoadablePatternService loadablePatternService;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private CommingleColourRepository commingleColourRepository;
  @Autowired CargoHistoryRepository cargoHistoryRepository;
  @Autowired VoyageStatusRepository voyageStatusRepository;
  @Autowired VoyageRepository voyageRepository;
  @Autowired LoadableStudyStatusRepository loadableStudyStatusRepository;
  @Autowired LoadablePatternRepository loadablePatternRepository;
  @Autowired LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;

  @GrpcClient("loadingPlanService")
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub dischargePlanService;

  public LoadableStudy.CargoHistoryReply.Builder getAllCargoHistory(
      LoadableStudy.CargoHistoryRequest request,
      LoadableStudy.CargoHistoryReply.Builder replyBuilder) {
    List<ApiTempHistory> apiTempHistList = null;

    // Paging and sorting while filtering is handled separately
    Pageable pageable = null;
    if (request.getSortBy().length() > 0 && request.getOrderBy().length() > 0) {
      pageable =
          PageRequest.of(
              0,
              Integer.MAX_VALUE,
              Sort.by(
                  Sort.Direction.valueOf(request.getOrderBy().toUpperCase()), request.getSortBy()));
      log.info(
          "Cargo History grpc: page {}, page size {},  sort-order {}, sort-by {}",
          request.getPage(),
          request.getPageSize(),
          request.getOrderBy(),
          request.getSortBy());
    } else {
      pageable = PageRequest.of(0, Integer.MAX_VALUE);
      log.info(
          "Cargo History grpc: page {}, page size {}", request.getPage(), request.getPageSize());
    }

    // apply date filter for loaded date
    if (!request.getFilterParamsMap().isEmpty()) {
      Map<String, LoadableStudy.FilterSpecification> map = request.getFilterParamsMap();

      Specification<ApiTempHistory> specification =
          Specification.where(
              new ApiTempHistorySpecification(new SearchCriteria("id", "GREATER_THAN", 0)));

      for (Map.Entry<String, com.cpdss.common.generated.LoadableStudy.FilterSpecification> var1 :
          map.entrySet()) {
        if (var1.getValue().getIdsList() != null && !var1.getValue().getIdsList().isEmpty()) {
          specification =
              specification.and(
                  new ApiTempHistorySpecification(
                      new SearchCriteria(
                          var1.getKey(),
                          var1.getValue().getOperation(),
                          var1.getValue().getIdsList())));
        }
        if (var1.getValue().getValuesList() != null
            && var1.getValue().getOperation().equals("BETWEEN")) {
          // Expected data Date range of loaded date
          String startDate = var1.getValue().getValuesList().get(0);
          String endDate = var1.getValue().getValuesList().get(1);
          LocalDateTime fromDate =
              LocalDateTime.from(DateTimeFormatter.ofPattern(DATE_FORMAT).parse(startDate));
          LocalDateTime toDate =
              LocalDateTime.from(DateTimeFormatter.ofPattern(DATE_FORMAT).parse(endDate));
          specification =
              specification.and(
                  new ApiTempHistorySpecification(
                      new SearchCriteria(
                          var1.getKey(),
                          var1.getValue().getOperation(),
                          Arrays.asList(fromDate, toDate))));
        }
        if (var1.getValue().getValuesList() != null
            && var1.getValue().getOperation().equalsIgnoreCase("LIKE")) {
          specification =
              specification.and(
                  new ApiTempHistorySpecification(
                      new SearchCriteria(
                          var1.getKey(),
                          var1.getValue().getOperation(),
                          var1.getValue().getValues(0))));
        }

        log.info("Cargo History grpc: Filter Key {}, Value {}", var1.getKey(), var1.getValue());
      }

      Page<ApiTempHistory> pagedResult = apiTempHistoryRepository.findAll(specification, pageable);
      apiTempHistList = pagedResult.toList();
      replyBuilder.setTotal(pagedResult.getTotalElements());
      log.info("ApiTempHistory paged result total {}", pagedResult.getTotalElements());
    } else { // on page load, no filter case
      Page<com.cpdss.loadablestudy.entity.ApiTempHistory> pagedResult =
          this.apiTempHistoryRepository.findAll(pageable);
      apiTempHistList = pagedResult.toList();
      replyBuilder.setTotal(pagedResult.getTotalElements());
      log.info("ApiTempHistory no filter paged result total {}", pagedResult.getTotalElements());
    }
    buildAllCargoHistoryReply(apiTempHistList, replyBuilder);
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }

  private void buildAllCargoHistoryReply(
      List<com.cpdss.loadablestudy.entity.ApiTempHistory> apiTempHistList,
      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.Builder replyBuilder) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ETA_ETD_FORMAT);
    if (!CollectionUtils.isEmpty(apiTempHistList)) {
      apiTempHistList.forEach(
          apiTempRecord -> {
            LoadableStudy.CargoHistoryDetail.Builder builder =
                LoadableStudy.CargoHistoryDetail.newBuilder();
            Optional.ofNullable(apiTempRecord.getVesselId()).ifPresent(builder::setVesselId);
            Optional.ofNullable(apiTempRecord.getCargoId()).ifPresent(builder::setCargoId);
            Optional.ofNullable(apiTempRecord.getLoadingPortId())
                .ifPresent(builder::setLoadingPortId);
            Optional.ofNullable(apiTempRecord.getLoadedDate())
                .ifPresent(loadedDate -> builder.setLoadedDate(formatter.format(loadedDate)));
            Optional.ofNullable(apiTempRecord.getYear()).ifPresent(builder::setLoadedYear);
            Optional.ofNullable(apiTempRecord.getMonth()).ifPresent(builder::setLoadedMonth);
            Optional.ofNullable(apiTempRecord.getDate()).ifPresent(builder::setLoadedDay);
            Optional.ofNullable(apiTempRecord.getApi())
                .ifPresent(api -> builder.setApi(String.valueOf(api)));
            Optional.ofNullable(apiTempRecord.getTemp())
                .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));
            replyBuilder.addCargoHistory(builder);
          });
    }
  }

  public LoadableStudy.LatestCargoReply.Builder getCargoHistoryByCargo(
      LoadableStudy.LatestCargoRequest request,
      LoadableStudy.LatestCargoReply.Builder replyBuilder) {
    List<ApiTempHistory> apiHistories =
        apiTempHistoryRepository
            .findByLoadingPortIdAndCargoIdAndLoadedDateNotNullOrderByLoadedDateDesc(
                request.getPortId(), request.getCargoId());
    if (apiHistories != null && apiHistories.size() > 0) {
      ApiTempHistory apiTempHistory = apiHistories.get(0);
      replyBuilder
          .setVesselId(request.getVesselId())
          .setPortId(request.getPortId())
          .setCargoId(request.getCargoId())
          .setApi(String.valueOf(apiTempHistory.getApi()))
          .setTemperature(String.valueOf(apiTempHistory.getTemp()));

      replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } else {
      replyBuilder
          .setVesselId(request.getVesselId())
          .setPortId(request.getPortId())
          .setCargoId(request.getCargoId());
      replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    }
    return replyBuilder;
  }

  public LoadableStudy.PurposeOfCommingleReply.Builder getPurposeOfCommingle(
      LoadableStudy.PurposeOfCommingleRequest request,
      LoadableStudy.PurposeOfCommingleReply.Builder reply) {
    Iterable<PurposeOfCommingle> purposeList = purposeOfCommingleRepository.findAll();
    purposeList.forEach(
        purposeEntity -> {
          com.cpdss.common.generated.LoadableStudy.PurposeOfCommingle.Builder purpose =
              com.cpdss.common.generated.LoadableStudy.PurposeOfCommingle.newBuilder();

          if (purposeEntity.getId() != null) {
            purpose.setId(purposeEntity.getId());
          }
          if (!StringUtils.isEmpty(purposeEntity.getPurpose())) {
            purpose.setName(purposeEntity.getPurpose());
          }
          reply.addPurposeOfCommingle(purpose);
        });
    Common.ResponseStatus.Builder responseStatus = Common.ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    reply.setResponseStatus(responseStatus);
    return reply;
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy void
   * @param modelMapper
   */
  public void buildCommingleCargoDetails(
      Long loadableStudyId,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {

    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos =
        commingleCargoRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
    loadableStudy.setCommingleCargos(new ArrayList<>());

    commingleCargos.forEach(
        commingleCargo -> {
          com.cpdss.loadablestudy.domain.CommingleCargo commingleCargoDto =
              modelMapper.map(commingleCargo, com.cpdss.loadablestudy.domain.CommingleCargo.class);
          commingleCargoDto.setCargo1Id(commingleCargo.getCargo1Xid());
          commingleCargoDto.setCargo2Id(commingleCargo.getCargo2Xid());
          commingleCargoDto.setCargo1Percentage(
              null != commingleCargo.getCargo1Pct()
                  ? commingleCargo.getCargo1Pct().toString()
                  : null);
          commingleCargoDto.setCargo2Percentage(
              null != commingleCargo.getCargo2Pct()
                  ? commingleCargo.getCargo2Pct().toString()
                  : null);
          commingleCargoDto.setCommingleColour(commingleCargo.getCommingleColour());
          loadableStudy.getCommingleCargos().add(commingleCargoDto);
        });
  }

  public LoadableStudy.CommingleCargoReply.Builder getCommingleCargo(
      LoadableStudy.CommingleCargoRequest request,
      LoadableStudy.CommingleCargoReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoList =
        this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
            request.getLoadableStudyId(), true);
    // get preferred tanks
    VesselInfo.VesselRequest.Builder vesselGrpcRequest = VesselInfo.VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(request.getVesselId());
    vesselGrpcRequest.addAllTankCategories(CARGO_TANK_CATEGORIES);
    VesselInfo.VesselReply vesselReply =
        loadableStudyService.getVesselTanks(vesselGrpcRequest.build());
    if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs",
          vesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
    }
    buildCommingleCargoReply(commingleCargoList, replyBuilder, vesselReply);
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }

  /**
   * build commingleCargo reply with commingle values from db
   *
   * @param commingleCargoList
   * @param replyBuilder
   */
  private void buildCommingleCargoReply(
      List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoList,
      LoadableStudy.CommingleCargoReply.Builder replyBuilder,
      VesselInfo.VesselReply vesselReply) {
    if (!CollectionUtils.isEmpty(commingleCargoList)) {
      commingleCargoList.forEach(
          commingleCargo -> {
            LoadableStudy.CommingleCargo.Builder builder =
                LoadableStudy.CommingleCargo.newBuilder();
            ofNullable(commingleCargo.getId()).ifPresent(builder::setId);
            ofNullable(commingleCargo.getPurposeXid()).ifPresent(builder::setPurposeId);
            ofNullable(commingleCargo.getIsSlopOnly()).ifPresent(builder::setSlopOnly);
            // Convert comma separated tank list to arrays
            if (commingleCargo.getTankIds() != null && !commingleCargo.getTankIds().isEmpty()) {
              List<Long> tankIdList =
                  Stream.of(commingleCargo.getTankIds().split(","))
                      .map(String::trim)
                      .map(Long::parseLong)
                      .collect(Collectors.toList());
              builder.addAllPreferredTanks(tankIdList);
            }
            ofNullable(commingleCargo.getCargo1Xid()).ifPresent(builder::setCargo1Id);
            ofNullable(commingleCargo.getCargo1Pct())
                .ifPresent(cargo1Pct -> builder.setCargo1Pct(String.valueOf(cargo1Pct)));
            ofNullable(commingleCargo.getCargo2Xid()).ifPresent(builder::setCargo2Id);
            ofNullable(commingleCargo.getCargo2Pct())
                .ifPresent(cargo2Pct -> builder.setCargo2Pct(String.valueOf(cargo2Pct)));
            ofNullable(commingleCargo.getQuantity())
                .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
            Optional.ofNullable(commingleCargo.getCargoNomination1Id())
                .ifPresent(builder::setCargoNomination1Id);
            Optional.ofNullable(commingleCargo.getCargoNomination2Id())
                .ifPresent(builder::setCargoNomination2Id);
            replyBuilder.addCommingleCargo(builder);
          });
    }
    // build preferred tanks
    replyBuilder.addAllTanks(onHandQuantityService.groupTanks(vesselReply.getVesselTanksList()));
  }

  public LoadableStudy.CommingleCargoReply.Builder saveCommingleCargo(
      LoadableStudy.CommingleCargoRequest request,
      LoadableStudy.CommingleCargoReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());
    loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudyOpt.get());

    if (!CollectionUtils.isEmpty(request.getCommingleCargoList())) {
      // for existing commingle cargo find missing ids in request and delete them
      deleteCommingleCargo(request);
      Long loadableStudyId = request.getLoadableStudyId();
      List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleEntities = new ArrayList<>();
      // for id = 0 save as new commingle cargo
      AtomicInteger counter = new AtomicInteger(0);
      request
          .getCommingleCargoList()
          .forEach(
              commingleCargo -> {
                try {
                  com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity = null;
                  if (commingleCargo != null && commingleCargo.getId() != 0) {
                    Optional<com.cpdss.loadablestudy.entity.CommingleCargo> existingCommingleCargo =
                        this.commingleCargoRepository.findByIdAndIsActive(
                            commingleCargo.getId(), true);
                    if (!existingCommingleCargo.isPresent()) {
                      throw new GenericServiceException(
                          "commingle cargo does not exist",
                          CommonErrorCodes.E_HTTP_BAD_REQUEST,
                          HttpStatusCode.BAD_REQUEST);
                    }
                    commingleCargoEntity = existingCommingleCargo.get();
                    commingleCargoEntity =
                        buildCommingleCargo(
                            commingleCargoEntity, commingleCargo, loadableStudyId, counter);
                  } else if (commingleCargo != null && commingleCargo.getId() == 0) {
                    commingleCargoEntity = new com.cpdss.loadablestudy.entity.CommingleCargo();
                    commingleCargoEntity =
                        buildCommingleCargo(
                            commingleCargoEntity, commingleCargo, loadableStudyId, counter);
                  }

                  commingleEntities.add(commingleCargoEntity);
                } catch (Exception e) {
                  log.error("Exception in creating entities for save commingle cargo", e);
                  throw new RuntimeException(e);
                }
              });
      // save all entities
      this.commingleCargoRepository.saveAll(commingleEntities);
    } else {
      List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoEntityList =
          this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);
      List<Long> existingCommingleCargoIds = null;
      if (!commingleCargoEntityList.isEmpty()) {
        existingCommingleCargoIds =
            commingleCargoEntityList.stream()
                .map(com.cpdss.loadablestudy.entity.CommingleCargo::getId)
                .collect(Collectors.toList());
      }
      if (!CollectionUtils.isEmpty(existingCommingleCargoIds)) {
        commingleCargoRepository.deleteCommingleCargo(existingCommingleCargoIds);
      }
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }

  /**
   * delete commingle cargo not available in the save request
   *
   * @param request
   */
  private void deleteCommingleCargo(LoadableStudy.CommingleCargoRequest request) {
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoEntityList =
        this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
            request.getLoadableStudyId(), true);
    List<Long> requestedCommingleCargoIds = null;
    List<Long> existingCommingleCargoIds = null;
    if (!request.getCommingleCargoList().isEmpty()) {
      requestedCommingleCargoIds =
          request.getCommingleCargoList().stream()
              .map(LoadableStudy.CommingleCargo::getId)
              .collect(Collectors.toList());
    }
    if (!commingleCargoEntityList.isEmpty()) {
      existingCommingleCargoIds =
          commingleCargoEntityList.stream()
              .map(com.cpdss.loadablestudy.entity.CommingleCargo::getId)
              .collect(Collectors.toList());
    }
    /**
     * find commingle ids available for the loadable study that are not available in save request so
     * that they can be deleted
     */
    if (!CollectionUtils.isEmpty(requestedCommingleCargoIds)
        && !CollectionUtils.isEmpty(existingCommingleCargoIds)) {
      existingCommingleCargoIds.removeAll(requestedCommingleCargoIds);
      commingleCargoRepository.deleteCommingleCargo(existingCommingleCargoIds);
    }
  }

  /**
   * build entity to save in commingle cargo
   *
   * @param commingleCargoEntity
   * @param loadableStudyId
   * @return
   */
  private com.cpdss.loadablestudy.entity.CommingleCargo buildCommingleCargo(
      com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity,
      LoadableStudy.CommingleCargo requestRecord,
      Long loadableStudyId,
      AtomicInteger counter) {
    List<Long> cargoNominationIds = new ArrayList<>();
    cargoNominationIds.add(requestRecord.getCargoNomination1Id());
    cargoNominationIds.add(requestRecord.getCargoNomination2Id());
    // fetch the max priority for the cargoNomination ids and set as priority for
    // commingle
    Long maxPriority =
        cargoNominationRepository.getMaxPriorityCargoNominationIn(cargoNominationIds);
    commingleCargoEntity.setPriority(maxPriority != null ? maxPriority.intValue() : null);
    commingleCargoEntity.setCargoNomination1Id(requestRecord.getCargoNomination1Id());
    commingleCargoEntity.setCargoNomination2Id(requestRecord.getCargoNomination2Id());
    commingleCargoEntity.setLoadableStudyXId(loadableStudyId);
    commingleCargoEntity.setPurposeXid(requestRecord.getPurposeId());
    commingleCargoEntity.setTankIds(
        StringUtils.collectionToCommaDelimitedString(requestRecord.getPreferredTanksList()));
    commingleCargoEntity.setCargo1Xid(requestRecord.getCargo1Id());
    commingleCargoEntity.setCargo1Pct(
        !StringUtils.isEmpty(requestRecord.getCargo1Pct())
            ? new BigDecimal(requestRecord.getCargo1Pct())
            : null);
    commingleCargoEntity.setCargo2Xid(requestRecord.getCargo2Id());
    commingleCargoEntity.setCargo2Pct(
        !StringUtils.isEmpty(requestRecord.getCargo2Pct())
            ? new BigDecimal(requestRecord.getCargo2Pct())
            : null);
    commingleCargoEntity.setQuantity(
        !StringUtils.isEmpty(requestRecord.getQuantity())
            ? new BigDecimal(requestRecord.getQuantity())
            : null);
    commingleCargoEntity.setIsActive(true);
    commingleCargoEntity.setIsSlopOnly(requestRecord.getSlopOnly());
    String abbreviation = COMMINGLE + counter.incrementAndGet();
    commingleCargoEntity.setAbbreviation(abbreviation);
    Optional<CommingleColour> commingleColour =
        this.commingleColourRepository.findByAbbreviationAndIsActive(abbreviation, true);
    commingleCargoEntity.setCommingleColour(commingleColour.get().getCommingleColour());
    return commingleCargoEntity;
  }

  public LoadableStudy.CargoHistoryReply.Builder getCargoApiTempHistory(
      LoadableStudy.CargoHistoryRequest request,
      LoadableStudy.CargoHistoryReply.Builder replyBuilder) {
    // fetch the history for last 5 years
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR) - 5;
    List<com.cpdss.loadablestudy.entity.ApiTempHistory> apiTempHistList =
        this.apiTempHistoryRepository.findApiTempHistoryWithYearAfter(request.getCargoId(), year);
    buildCargoHistoryReply(apiTempHistList, replyBuilder);
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    return replyBuilder;
  }

  private void buildCargoHistoryReply(
      List<com.cpdss.loadablestudy.entity.ApiTempHistory> apiTempHistList,
      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.Builder replyBuilder) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ETA_ETD_FORMAT);
    if (!CollectionUtils.isEmpty(apiTempHistList)) {
      apiTempHistList.forEach(
          apiTempRecord -> {
            LoadableStudy.CargoHistoryDetail.Builder builder =
                LoadableStudy.CargoHistoryDetail.newBuilder();
            Optional.ofNullable(apiTempRecord.getCargoId()).ifPresent(builder::setCargoId);
            Optional.ofNullable(apiTempRecord.getLoadingPortId())
                .ifPresent(builder::setLoadingPortId);
            Optional.ofNullable(apiTempRecord.getLoadedDate())
                .ifPresent(loadedDate -> builder.setLoadedDate(formatter.format(loadedDate)));
            Optional.ofNullable(apiTempRecord.getYear()).ifPresent(builder::setLoadedYear);
            Optional.ofNullable(apiTempRecord.getMonth()).ifPresent(builder::setLoadedMonth);
            Optional.ofNullable(apiTempRecord.getDate()).ifPresent(builder::setLoadedDay);
            Optional.ofNullable(apiTempRecord.getApi())
                .ifPresent(api -> builder.setApi(String.valueOf(api)));
            Optional.ofNullable(apiTempRecord.getTemp())
                .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));
            replyBuilder.addCargoHistory(builder);
          });
    }
  }

  public void saveCargoHistoryFromOperationsModule(Long vesselId, Long voyageId) {
    Common.CargoHistoryOpsRequest.Builder request = Common.CargoHistoryOpsRequest.newBuilder();
    request.setVesselId(vesselId).setVoyageId(voyageId);
    Common.CargoHistoryOpsRequest.Builder request2 = Common.CargoHistoryOpsRequest.newBuilder();
    request.setVesselId(vesselId).setVoyageId(voyageId);
    var loading = loadingPlanService.getLoadingPlanCargoHistory(request.build());
    var discharge = dischargePlanService.getDischargePlanCargoHistory(request2.build());
    // Call 1 for Loading data save
    if (!loading.getCargoHistoryList().isEmpty()) {
      this.saveCargoHistoryBatch(this.buildCargoHistoryData(loading, true));
      log.info(
          "Save Cargo History data fro Loading Plan Success, Size - {}",
          loading.getCargoHistoryCount());
    }
    // Call 2 for discharge
    if (!discharge.getCargoHistoryList().isEmpty()) {
      this.saveCargoHistoryBatch(this.buildCargoHistoryData(discharge, false));
      log.info(
          "Save Cargo History data fro Discharge Plan Success, Size - {}",
          discharge.getCargoHistoryCount());
    }
  }

  private List<CargoHistory> buildCargoHistoryData(
      Common.CargoHistoryResponse response, boolean isLoading) {
    List<CargoHistory> histories = new ArrayList<>();
    try {
      for (Common.CargoHistoryOps var1 : response.getCargoHistoryList()) {
        CargoHistory entity = new CargoHistory();
        entity.setTankId(var1.getTankId());
        entity.setApi(var1.getApi().isEmpty() ? null : new BigDecimal(var1.getApi()));
        entity.setTemperature(
            var1.getTemperature().isEmpty() ? null : new BigDecimal(var1.getTemperature()));
        entity.setCargoNomination(
            cargoNominationRepository.findByIdAndIsActive(var1.getCargoNominationId(), true).get());
        entity.setQuantity(
            var1.getQuantity().isEmpty() ? null : new BigDecimal(var1.getQuantity()));
        if (isLoading) {
          entity.setLoadingPortId(var1.getPortId());
        } else {
          entity.setDischargePort(var1.getPortId());
        }
        histories.add(entity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return histories;
  }

  private void saveCargoHistoryBatch(List<CargoHistory> cargoHistories) {
    try {
      cargoHistoryRepository.saveAll(cargoHistories);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<VoyageHistoryDto> buildPreviousVoyageDetails() throws GenericServiceException {

    List<VoyageHistoryDto> response = new ArrayList<>();
    var vyStatus = voyageStatusRepository.getById(CLOSE_VOYAGE_STATUS);
    var lsStatus = loadableStudyStatusRepository.findById(LS_STATUS_CONFIRMED);

    Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "actualEndDate"));
    var voyageList = voyageRepository.findRecentClosedVoyageDetails(vyStatus, 1l, pageable);
    if (!voyageList.isEmpty()) {
      for (Voyage entity : voyageList) {
        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> ls =
            entity.getLoadableStudies().stream()
                .filter(v -> v.getLoadableStudyStatus().getId().equals(lsStatus.get().getId()))
                .findFirst();
        List<LoadablePattern> lps =
            loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
                ls.get(), true);
        if (!lps.isEmpty()) {
          Optional<LoadablePattern> lpOp =
              lps.stream()
                  .filter(v -> v.getLoadableStudyStatus().equals(lsStatus.get().getId()))
                  .findFirst();

          List<LoadablePlanStowageDetails> loadablePlanStowageDetails =
              loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
                  lpOp.get(), true);
          List<CargoNomination> cargoNominations =
              cargoNominationRepository.findByLoadableStudyXIdAndIsActive(ls.get().getId(), true);
          for (CargoNomination cn : cargoNominations) {
            if (cn.getCargoXId() != null && cn.getCargoXId() > 0) {
              cn.setIsCondensateCargo(this.updateCargoNominationWithDetails(cn.getCargoXId()));
            }
          }
          VoyageHistoryDto dto = new VoyageHistoryDto(entity);
          dto.addAllStowageDetails(loadablePlanStowageDetails);
          dto.addAllCargoNominations(cargoNominations);
          response.add(dto);
        }
      }
    }
    return response;
  }

  private Boolean updateCargoNominationWithDetails(Long cargoId) {
    CargoInfo.CargoRequest.Builder cargoReq = CargoInfo.CargoRequest.newBuilder();
    cargoReq.setCargoId(cargoId);
    com.cpdss.common.generated.CargoInfo.CargoDetailReply rpcRepay =
        cargoInfoGrpcService.getCargoInfoById(cargoReq.build());
    if (SUCCESS.equals(rpcRepay.getResponseStatus().getStatus())) {
      return rpcRepay.getCargoDetail().getIsCondensateCargo();
    } else {
      log.info("Failed to get Data for - CondensateCargo - id - {}", cargoId);
      return null;
    }
  }
}
