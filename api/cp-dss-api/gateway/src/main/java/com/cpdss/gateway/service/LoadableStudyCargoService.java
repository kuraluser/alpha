/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.CargoHistoryReply;
import com.cpdss.common.generated.LoadableStudy.LatestCargoReply;
import com.cpdss.common.generated.LoadableStudy.LatestCargoRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CargoHistory;
import com.cpdss.gateway.domain.CargoHistoryResponse;
import com.cpdss.gateway.domain.LatestApiTempCargoResponse;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** LoadableStudyCargoService - service class for loadable study cargo related operations */
@Service
@Log4j2
public class LoadableStudyCargoService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  @Autowired
  @Qualifier("cargoRedisSyncService")
  private RedisMasterSyncService redisCargoService;

  @Autowired
  @Qualifier("vesselRedisSyncService")
  private RedisMasterSyncService redisVesselService;

  @Autowired
  @Qualifier("portRedisSyncService")
  private RedisMasterSyncService redisPortService;

  /**
   * Get cargo history api and temp details using loadable-study service
   *
   * @param filterParams
   * @param page
   * @param pageSize
   * @param sortBy
   * @param orderBy
   * @param fromStartDate
   * @param toStartDate
   * @return
   * @throws GenericServiceException
   */
  public CargoHistoryResponse getAllCargoHistory(
      Map<String, String> filterParams,
      int page,
      int pageSize,
      String sortBy,
      String orderBy,
      String fromStartDate,
      String toStartDate)
      throws GenericServiceException {
    CargoHistoryResponse cargoHistoryResponse = new CargoHistoryResponse();
    // Build response with response status
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    cargoHistoryResponse.setResponseStatus(commonSuccessResponse);

    // Build cargo history payload for grpc call
    com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest.Builder builder =
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest.newBuilder();

    log.info("Cargo History Filter - {}", filterParams.keySet());
    log.info("Cargo History Sort   - {}, direction - {}", sortBy, orderBy);

    // Below are the filter options, multiple filter available
    if (filterParams.get("vesselName") != null) { // Vessel Sort
      builder.putFilterParams(
          "vesselId",
          getFilterData(redisVesselService, "IN", filterParams.get("vesselName"), null));
    }
    if (filterParams.get("loadingPortName") != null) { // Port Sort
      builder.putFilterParams(
          "loadingPortId",
          getFilterData(redisPortService, "IN", filterParams.get("loadingPortName"), null));
    }
    if (filterParams.get("grade") != null) { // Cargo Sort
      builder.putFilterParams(
          "cargoId", getFilterData(redisCargoService, "IN", filterParams.get("grade"), null));
    }
    if (filterParams.get("startDate") != null) {
      builder.putFilterParams(
          "loadedDate",
          getFilterData(
              null, "BETWEEN", filterParams.get("startDate"), filterParams.get("endDate")));
    }
    if (filterParams.get("loadedYear") != null) {
      builder.putFilterParams(
          "year", getFilterData(null, "LIKE", filterParams.get("loadedYear"), null));
    }
    if (filterParams.get("loadedMonth") != null) {
      builder.putFilterParams(
          "month", getFilterData(null, "LIKE", filterParams.get("loadedMonth"), null));
    }
    if (filterParams.get("loadedDay") != null) {
      builder.putFilterParams(
          "date", getFilterData(null, "LIKE", filterParams.get("loadedDay"), null));
    }
    if (filterParams.get("api") != null) {
      builder.putFilterParams("api", getFilterData(null, "LIKE", filterParams.get("api"), null));
    }
    if (filterParams.get("temperature") != null) {
      builder.putFilterParams(
          "temp", getFilterData(null, "LIKE", filterParams.get("temperature"), null));
    }

    // due to the sorting for non-table elements, fetch
    // full data (after the filter apply) into here and manually sort
    builder.setPage(-1);
    builder.setPageSize(-1);

    String SORT_BY = sortBy;
    if (sortBy.equals("vesselName") || sortBy.equals("loadingPortName") || sortBy.equals("grade")) {
      SORT_BY = "year";
    }
    if (sortBy.equals("api")) {
      SORT_BY = "api";
    } else if (sortBy.equals("temperature")) {
      SORT_BY = "temp";
    } else {
      SORT_BY =
          sortBy.equals("loadedYear")
              ? "year"
              : sortBy.equals("loadedMonth")
                  ? "month"
                  : sortBy.equals("loadedDay") ? "date" : "year";
    }
    builder.setSortBy(SORT_BY); // Required at LS
    builder.setOrderBy(orderBy); // Required at LS
    // start date and end date moved to above

    com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest cargoHistoryRequest =
        builder.build();
    CargoHistoryReply cargoHistoryReply =
        loadableStudyServiceBlockingStub.getAllCargoHistory(cargoHistoryRequest);
    if (!SUCCESS.equals(cargoHistoryReply.getResponseStatus().getStatus())) {
      if (!StringUtils.isEmpty(cargoHistoryReply.getResponseStatus().getCode())) {
        throw new GenericServiceException(
            "GenericServiceException getAllCargoHistory "
                + cargoHistoryReply.getResponseStatus().getMessage(),
            cargoHistoryReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(
                Integer.valueOf(cargoHistoryReply.getResponseStatus().getHttpStatusCode())));
      } else {
        throw new GenericServiceException(
            "GenericServiceException getAllCargoHistory",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
    }
    buildAllCargoHistoryResponse(cargoHistoryResponse, cargoHistoryReply);
    updateCargoHistoryIdToNames(cargoHistoryResponse);
    sortCargoHistoryData(cargoHistoryResponse, sortBy, orderBy, page, pageSize);
    return cargoHistoryResponse;
  }

  /**
   * Add Filter Specification to grpc Builder
   *
   * @param service - For Redis {vessel, port, cargo}
   * @param operation - For DB Operations {IN, LIKE, BETWEEN}
   * @param val1
   * @param val2
   * @return object of filter
   */
  private LoadableStudy.FilterSpecification getFilterData(
      RedisMasterSyncService service, String operation, String val1, String val2) {
    LoadableStudy.FilterSpecification.Builder filterBuilder =
        LoadableStudy.FilterSpecification.newBuilder();
    if (service != null && operation.equals("IN")) { // Fill vessel, port, cargo ids
      Map<Long, String> cacheData = service.filterByName(val1);
      if (cacheData.size() > 0) {
        filterBuilder.addAllIds(
            sortRedisIdAndName(cacheData, val2).entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));
      } else {
        filterBuilder.addAllIds(Arrays.asList(-1l));
      }
    }
    if (service == null && operation.equals("BETWEEN")) { // For date range filter
      filterBuilder.addAllValues(Arrays.asList(val1, val2));
    }
    if (service == null && operation.equals("LIKE")) {
      filterBuilder.addAllValues(Arrays.asList(val1));
    }
    filterBuilder.setOperation(operation);
    log.info("Cargo History - Filter Specification {}", filterBuilder);
    return filterBuilder.build();
  }

  private CargoHistoryResponse sortCargoHistoryData(
      CargoHistoryResponse cargoHistoryResponse,
      String sortKey,
      String order,
      Integer pageNo,
      Integer pageSize) {
    if (cargoHistoryResponse.getCargoHistory() == null
        || cargoHistoryResponse.getCargoHistory().isEmpty()) {
      return cargoHistoryResponse;
    }
    List<CargoHistory> sortingList = new ArrayList<>(cargoHistoryResponse.getCargoHistory());
    cargoHistoryResponse.getCargoHistory().clear();
    if (sortKey.equalsIgnoreCase("vesselName")) {
      if (order.equalsIgnoreCase("asc"))
        Collections.sort(sortingList, Comparator.comparing(CargoHistory::getVesselName));
      else
        Collections.sort(sortingList, (o1, o2) -> o2.getVesselName().compareTo(o1.getVesselName()));
    }
    if (sortKey.equalsIgnoreCase("loadingPortName")) {
      if (order.equalsIgnoreCase("asc"))
        Collections.sort(sortingList, Comparator.comparing(CargoHistory::getLoadingPortName));
      else
        Collections.sort(
            sortingList, (o1, o2) -> o2.getLoadingPortName().compareTo(o1.getLoadingPortName()));
    }
    if (sortKey.equalsIgnoreCase("grade")) {
      if (order.equalsIgnoreCase("asc"))
        Collections.sort(sortingList, Comparator.comparing(CargoHistory::getGrade));
      else Collections.sort(sortingList, (o1, o2) -> o2.getGrade().compareTo(o1.getGrade()));
    }
    // as we have to sort non-table columns, fetch all from tbl and sort/page here
    // ApiTempHistory - table keeps ids of vessel. cargo, port
    int offset = (pageNo * pageSize);
    int limit = offset + pageSize;
    log.info("page offset {}, limit {}", offset, limit);
    List<CargoHistory> pagedList = null;
    if (limit < sortingList.size()) {
      pagedList = sortingList.subList(offset, limit);
    } else {
      try {
        if (offset <= sortingList.size()) {
          pagedList = sortingList.subList(offset, (sortingList.size()));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    cargoHistoryResponse.setCargoHistory(pagedList);
    return cargoHistoryResponse;
  }

  private CargoHistoryResponse updateCargoHistoryIdToNames(
      CargoHistoryResponse cargoHistoryResponse) {
    // Pre load redis data
    Map<Long, String> vR = redisVesselService.fetchAllIdAndName();
    Map<Long, String> cR = redisCargoService.fetchAllIdAndName();
    Map<Long, String> pR = redisPortService.fetchAllIdAndName();
    if (cargoHistoryResponse.getCargoHistory() != null) {
      for (CargoHistory ch : cargoHistoryResponse.getCargoHistory()) {
        ch.setGrade(cR.get(ch.getCargoId()));
        ch.setVesselName(vR.get(ch.getVesselId()));
        ch.setLoadingPortName(pR.get(ch.getLoadingPortId()));
      }
    }
    return cargoHistoryResponse;
  }

  /**
   * Sort Map - Long, String
   *
   * @param redisData - required
   * @param direction - required
   * @return Sorted Map OR Unsorted map - if direction null
   */
  private Map<Long, String> sortRedisIdAndName(Map<Long, String> redisData, String direction) {
    if (direction != null) {
      List<Map.Entry<Long, String>> list =
          new LinkedList<Map.Entry<Long, String>>(redisData.entrySet());

      // Sorting the list based on values
      Collections.sort(
          list,
          new Comparator<Map.Entry<Long, String>>() {
            public int compare(Map.Entry<Long, String> o1, Map.Entry<Long, String> o2) {
              if (direction.equalsIgnoreCase("asc")) {
                return o1.getValue().compareTo(o2.getValue());
              } else {
                return o2.getValue().compareTo(o1.getValue());
              }
            }
          });
      // Maintaining insertion order with the help of LinkedList
      Map<Long, String> sortedMap = new LinkedHashMap<Long, String>();
      for (Map.Entry<Long, String> entry : list) {
        sortedMap.put(entry.getKey(), entry.getValue());
      }
      return sortedMap;
    } else {
      return redisData;
    }
  }

  /**
   * Builds the cargoHistory
   *
   * @param cargoHistoryResponse
   * @param reply
   * @return
   */
  private CargoHistoryResponse buildAllCargoHistoryResponse(
      CargoHistoryResponse cargoHistoryResponse, CargoHistoryReply reply) {
    if (reply != null && !reply.getCargoHistoryList().isEmpty()) {
      List<CargoHistory> cargoHistoryList = new ArrayList<>();
      reply
          .getCargoHistoryList()
          .forEach(
              cargoHistoryDetail -> {
                CargoHistory cargoHistory = new CargoHistory();
                cargoHistory.setVesselId(cargoHistoryDetail.getVesselId());
                cargoHistory.setLoadingPortId(cargoHistoryDetail.getLoadingPortId());
                cargoHistory.setCargoId(cargoHistoryDetail.getCargoId());
                cargoHistory.setLoadedDate(cargoHistoryDetail.getLoadedDate());
                cargoHistory.setLoadedMonth(cargoHistoryDetail.getLoadedMonth());
                cargoHistory.setLoadedDay(cargoHistoryDetail.getLoadedDay());
                cargoHistory.setLoadedYear(cargoHistoryDetail.getLoadedYear());

                if (cargoHistoryDetail.getApi() != null
                    && !cargoHistoryDetail.getApi().trim().isEmpty()
                    && cargoHistoryDetail.getApi().length() > 0) {
                  cargoHistory.setApi(new BigDecimal(cargoHistoryDetail.getApi()));
                } else {
                  cargoHistory.setApi(new BigDecimal("0"));
                }
                if (cargoHistoryDetail.getTemperature() != null
                    && !cargoHistoryDetail.getTemperature().trim().isEmpty()
                    && cargoHistoryDetail.getTemperature().length() > 0) {
                  cargoHistory.setTemperature(new BigDecimal(cargoHistoryDetail.getTemperature()));
                } else {
                  cargoHistory.setTemperature(new BigDecimal("0"));
                }
                cargoHistoryList.add(cargoHistory);
              });
      cargoHistoryResponse.setCargoHistory(cargoHistoryList);
      cargoHistoryResponse.setTotalElements(reply.getTotal());
    }
    return cargoHistoryResponse;
  }

  public LatestApiTempCargoResponse getCargoHistoryByPortAndCargo(
      @Min(value = 1, message = "400") Long vesselId,
      @Min(value = 1, message = "400") Long portId,
      @Min(value = 1, message = "400") Long cargoId)
      throws GenericServiceException {

    LatestCargoRequest.Builder builder = LatestCargoRequest.newBuilder();
    builder.setVesselId(vesselId).setPortId(portId).setCargoId(cargoId);

    LatestCargoRequest latestCargoRequest = builder.build();
    LatestCargoReply latestCargoReply =
        loadableStudyServiceBlockingStub.getCargoHistoryByCargo(latestCargoRequest);

    if (!SUCCESS.equals(latestCargoReply.getResponseStatus().getStatus())) {
      if (!StringUtils.isEmpty(latestCargoReply.getResponseStatus().getCode())) {
        throw new GenericServiceException(
            "GenericServiceException getCargoHistoryByCargo "
                + latestCargoReply.getResponseStatus().getMessage(),
            latestCargoReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(
                Integer.valueOf(latestCargoReply.getResponseStatus().getHttpStatusCode())));
      } else {
        throw new GenericServiceException(
            "GenericServiceException getCargoHistoryByCargo",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
    }

    return LatestApiTempCargoResponse.builder()
        .vesselId(latestCargoReply.getVesselId())
        .loadingPortId(latestCargoReply.getPortId())
        .cargoId(latestCargoReply.getCargoId())
        .api(latestCargoReply.getApi())
        .temperature(latestCargoReply.getTemperature())
        .build();
  }
}
