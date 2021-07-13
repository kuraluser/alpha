/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.domain.ApiTempHistorySpecification;
import com.cpdss.loadablestudy.domain.SearchCriteria;
import com.cpdss.loadablestudy.entity.ApiTempHistory;
import com.cpdss.loadablestudy.entity.PurposeOfCommingle;
import com.cpdss.loadablestudy.repository.ApiTempHistoryRepository;
import com.cpdss.loadablestudy.repository.CommingleCargoRepository;
import com.cpdss.loadablestudy.repository.PurposeOfCommingleRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
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
        apiTempHistoryRepository.findByLoadingPortIdAndCargoIdOrderByCreatedDateTimeDesc(
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
              new com.cpdss.loadablestudy.domain.CommingleCargo();
          commingleCargoDto =
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
          loadableStudy.getCommingleCargos().add(commingleCargoDto);
        });
  }
}
