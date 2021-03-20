/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.CargoHistoryReply;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CargoHistory;
import com.cpdss.gateway.domain.CargoHistoryResponse;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
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

    if (filterParams.get("vesselName") != null) { // Vessel Sort
      builder.putFilterParams(
          "vesselId", getFilterData(redisVesselService, filterParams.get("vesselName"), null));
    }
    if (filterParams.get("loadingPort") != null) { // Port Sort
      builder.putFilterParams(
          "loadingPortId", getFilterData(redisPortService, filterParams.get("loadingPort"), null));
    }
    if (filterParams.get("grade") != null) { // Cargo Sort
      builder.putFilterParams(
          "cargoId", getFilterData(redisCargoService, filterParams.get("grade"), null));
    }
    if (filterParams.get("startDate") != null) {
      builder.putFilterParams(
          "loadedDate",
          getFilterData(null, filterParams.get("startDate"), filterParams.get("endDate")));
    }

    // due to the sorting for non-table elements, fetch
    // full data (after the filter apply) into here and manually sort
    builder.setPage(-1);
    builder.setPageSize(-1);

    if (!sortBy.equalsIgnoreCase("vesselName")
        && !sortBy.equalsIgnoreCase("loadingPort")
        && !sortBy.equalsIgnoreCase("grade")) {
      builder.setSortBy(sortBy); // Required at LS
    }
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

  private CargoHistoryResponse sortCargoHistoryData(
      CargoHistoryResponse cargoHistoryResponse,
      String sortKey,
      String order,
      Integer pageNo,
      Integer pageSize) {
    List<CargoHistory> beforeSort = new ArrayList<>(cargoHistoryResponse.getCargoHistory());
    if (beforeSort == null) return cargoHistoryResponse;
    cargoHistoryResponse.getCargoHistory().clear();
    if (sortKey.equalsIgnoreCase("vesselName")) {
      if (order.equalsIgnoreCase("asc"))
        Collections.sort(beforeSort, Comparator.comparing(CargoHistory::getVesselName));
      else
        Collections.sort(beforeSort, (o1, o2) -> o2.getVesselName().compareTo(o1.getVesselName()));
    }
    if (sortKey.equalsIgnoreCase("loadingPort")) {
      if (order.equalsIgnoreCase("asc"))
        Collections.sort(beforeSort, Comparator.comparing(CargoHistory::getLoadingPortName));
      else
        Collections.sort(
            beforeSort, (o1, o2) -> o2.getLoadingPortName().compareTo(o1.getLoadingPortName()));
    }
    if (sortKey.equalsIgnoreCase("grade")) {
      if (order.equalsIgnoreCase("asc"))
        Collections.sort(beforeSort, Comparator.comparing(CargoHistory::getGrade));
      else Collections.sort(beforeSort, (o1, o2) -> o2.getGrade().compareTo(o1.getGrade()));
    }
    // as we have to sort non-table columns, fetch all from tbl and sort/page here
    // ApiTempHistory - table keeps ids of vessel. cargo, port
    int offset = pageNo + (pageNo * (pageSize - 1));
    int limit = offset + (pageSize - 1);
    log.info("page offset {}, limit {}", offset, limit);
    List<CargoHistory> pagedList = beforeSort.subList(offset, limit);
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
   * Filter Ids means, Which column need to Filter (only for Filter)
   *
   * <p>Here Filter and Sort the ids
   *
   * @param service - Redis Bean
   * @param val1 - if bean preset var 1 = date
   * @param val2
   * @return grpc generated FilterIds build
   */
  private LoadableStudy.FilterIds getFilterData(
      RedisMasterSyncService service, String val1, String val2) {
    LoadableStudy.FilterIds.Builder filterBuilder = LoadableStudy.FilterIds.newBuilder();
    if (service != null) {
      Map<Long, String> cacheData = service.filterByName(val1);
      filterBuilder.addAllIds(
          sortRedisIdAndName(cacheData, val2).entrySet().stream()
              .map(Map.Entry::getKey)
              .collect(Collectors.toList()));
    } else {
      try {
        if (val1 != null && val2 != null) filterBuilder.addAllValues(Arrays.asList(val1, val2));

      } catch (Exception e) {
        log.info("Date range filter failed ", e);
      }
    }
    return filterBuilder.build();
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
}
