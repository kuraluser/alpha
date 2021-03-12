/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.CargoHistoryReply;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CargoHistory;
import com.cpdss.gateway.domain.CargoHistoryResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/** LoadableStudyCargoService - service class for loadable study cargo related operations */
@Service
@Log4j2
public class LoadableStudyCargoService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  /**
   * Get cargo history api and temp details using loadable-study service
   *
   * @param loadableStudyId
   * @param headers
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
    builder.setPage(page);
    builder.setPageSize(pageSize);
    builder.setSortBy(sortBy);
    builder.setOrderBy(orderBy);
    if (!CollectionUtils.isEmpty(filterParams)) {
      builder.putAllFilterParams(filterParams);
    }
    Optional.ofNullable(fromStartDate).ifPresent(builder::setFromStartDate);
    Optional.ofNullable(toStartDate).ifPresent(builder::setToStartDate);
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
    return cargoHistoryResponse;
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
                cargoHistory.setApi(
                    cargoHistoryDetail.getApi() != null
                        ? new BigDecimal(cargoHistoryDetail.getApi())
                        : new BigDecimal("0"));
                cargoHistory.setTemperature(
                    (cargoHistoryDetail.getTemperature() != null
                            && !cargoHistoryDetail.getTemperature().trim().isEmpty())
                        ? new BigDecimal(cargoHistoryDetail.getTemperature())
                        : new BigDecimal("0"));
                cargoHistoryList.add(cargoHistory);
              });
      cargoHistoryResponse.setMonthlyHistory(cargoHistoryList);
    }
    return cargoHistoryResponse;
  }
}
