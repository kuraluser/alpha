/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.*;
import com.cpdss.common.generated.PortInfo.CountryReply.Builder;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.portinfo.domain.CargoPortInfoSpecification;
import com.cpdss.portinfo.domain.FilterCriteria;
import com.cpdss.portinfo.domain.PortInfoSpecification;
import com.cpdss.portinfo.entity.*;
import com.cpdss.portinfo.entity.CargoPortMapping;
import com.cpdss.portinfo.entity.Country;
import com.cpdss.portinfo.entity.Timezone;
import com.cpdss.portinfo.repository.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.instrument.util.StringUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** Service with operations related to port information */
@Log4j2
@GrpcService
@Transactional
public class PortInfoService extends PortInfoServiceImplBase {

  @Autowired private PortInfoRepository portRepository;
  @Autowired private CargoPortMappingRepository cargoPortMappingRepository;
  @Autowired private TimezoneRepository timezoneRepository;
  @Autowired private CountryRepository countryRepository;
  @Autowired BerthInfoRepository berthInfoRepository;
  @Autowired BerthManifoldRepository berthManifoldRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String INVALID_CARGOID = "INVALID_CARGOID";
  private static final String INVALID_PORTID = "INVALID_PORTID";

  /** retrieves port info from port master */
  @Override
  public void getPortInfo(PortRequest request, StreamObserver<PortReply> responseObserver) {
    PortReply.Builder portReply = PortReply.newBuilder();
    try {
      List<PortInfo> portList = portRepository.findAllByOrderByName();
      Boolean berthDataNotNeed = request.getBerthDataNotNeed();
      getPorts(portReply, portList, berthDataNotNeed);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      portReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPortInfo method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      portReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(portReply.build());
      responseObserver.onCompleted();
    }
  }

  /** Retrieves the port info from cargoportmapping table for the requested cargoId */
  @Override
  public void getPortInfoByCargoId(
      GetPortInfoByCargoIdRequest request,
      StreamObserver<GetPortInfoByCargoIdReply> responseObserver) {
    GetPortInfoByCargoIdReply.Builder replyBuilder = GetPortInfoByCargoIdReply.newBuilder();
    try {
      if (request.getCargoId() == 0) {
        throw new GenericServiceException(
            INVALID_CARGOID, CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<com.cpdss.portinfo.domain.PortInfo> portInfoList =
          this.cargoPortMappingRepository.getPortsInfo(request.getCargoId());
      portInfoList.forEach(
          port -> {
            PortDetail.Builder portDetail = PortDetail.newBuilder();
            Optional.ofNullable(port.getId()).ifPresent(portDetail::setId);
            Optional.ofNullable(port.getName()).ifPresent(portDetail::setName);
            replyBuilder.addPorts(portDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      replyBuilder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPortInfoByCargoId method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Retrieves portInfo for a list of port ids
   *
   * @param request grpc request of list of port ids
   * @param responseObserver grpc response of port info
   */
  @Override
  public void getPortInfoByPortIds(
      GetPortInfoByPortIdsRequest request, StreamObserver<PortReply> responseObserver) {
    PortReply.Builder portReply = PortReply.newBuilder();
    try {
      if (request != null && CollectionUtils.isEmpty(request.getIdList())) {
        log.error("No port id found!");
        throw new GenericServiceException(
            INVALID_PORTID, CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
      }
      if (request != null) {
        List<PortInfo> portList = portRepository.findByIdInAndIsActive(request.getIdList(), true);
        getPorts(portReply, portList, false);
      }
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      portReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPortInfoByPortIds method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      portReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(portReply.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Assigns port details to reply builder
   *
   * @param portReply port reply builder
   * @param portList list of port details
   * @param berthDataNotNeed boolean flag to check if berth data are not needed
   */
  private void getPorts(
      PortReply.Builder portReply, List<PortInfo> portList, Boolean berthDataNotNeed) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    portList.forEach(
        port -> {
          PortDetail.Builder portDetail = PortDetail.newBuilder();
          Optional.ofNullable(port.getId()).ifPresent(portDetail::setId);
          Optional.ofNullable(port.getName()).ifPresent(portDetail::setName);
          Optional.ofNullable(port.getCode()).ifPresent(portDetail::setCode);
          Optional.ofNullable(port.getDensitySeaWater())
              .ifPresent(waterDensity -> portDetail.setWaterDensity(String.valueOf(waterDensity)));
          Optional.ofNullable(port.getAverageTideHeight())
              .ifPresent(
                  averageTideHeight ->
                      portDetail.setAverageTideHeight(String.valueOf(averageTideHeight)));
          Optional.ofNullable(port.getTideHeight())
              .ifPresent(tideHeight -> portDetail.setTideHeight(String.valueOf(tideHeight)));

          Optional.ofNullable(port.getHwTideFrom())
              .ifPresent(item -> portDetail.setHwTideFrom(String.valueOf(item)));
          Optional.ofNullable(port.getHwTideTo())
              .ifPresent(item -> portDetail.setHwTideTo(String.valueOf(item)));
          Optional.ofNullable(port.getHwTideTimeFrom())
              .ifPresent(item -> portDetail.setHwTideTimeFrom(timeFormatter.format(item)));
          Optional.ofNullable(port.getHwTideTimeTo())
              .ifPresent(item -> portDetail.setHwTideTimeTo(timeFormatter.format(item)));

          Optional.ofNullable(port.getLwTideFrom())
              .ifPresent(item -> portDetail.setLwTideFrom(String.valueOf(item)));
          Optional.ofNullable(port.getLwTideTo())
              .ifPresent(item -> portDetail.setLwTideTo(String.valueOf(item)));
          Optional.ofNullable(port.getLwTideTimeFrom())
              .ifPresent(item -> portDetail.setLwTideTimeFrom(timeFormatter.format(item)));
          Optional.ofNullable(port.getLwTideTimeTo())
              .ifPresent(item -> portDetail.setLwTideTimeTo(timeFormatter.format(item)));

          Optional.ofNullable(port.getTimeOfSunrise())
              .ifPresent(item -> portDetail.setSunriseTime(timeFormatter.format(item)));
          Optional.ofNullable(port.getTimeOfSunSet())
              .ifPresent(item -> portDetail.setSunsetTime(timeFormatter.format(item)));

          Optional.ofNullable(port.getControllingDepth())
              .ifPresent(v -> portDetail.setControllingDepth(v.toString()));
          Optional.ofNullable(port.getUnderKeelClearance())
              .ifPresent(portDetail::setUnderKeelClearance);

          if (port.getTimezone() != null) {
            portDetail.setTimezone(port.getTimezone().getTimezone());
            portDetail.setTimezoneOffsetVal(port.getTimezone().getOffsetValue());
            portDetail.setTimezoneId(port.getTimezone().getId());
            portDetail.setTimezoneAbbreviation(port.getTimezone().getAbbreviation());
          }

          Optional.ofNullable(port.getCountry())
              .ifPresent(item -> portDetail.setCountryName(port.getCountry().getName()));
          Optional.ofNullable(port.getCountry())
              .ifPresent(item -> portDetail.setCountryId(port.getCountry().getId()));
          Optional.ofNullable(port.getAmbientTemperature())
              .ifPresent(
                  item ->
                      portDetail.setAmbientTemperature(port.getAmbientTemperature().toString()));
          Set<BerthInfo> berthInfoSet = port.getBerthInfoSet();
          if (!berthInfoSet.isEmpty()) {
            Optional<BigDecimal> minDraftOfBerths =
                berthInfoSet.stream()
                    .map(BerthInfo::getMaximumDraft)
                    .filter(Objects::nonNull)
                    .min(BigDecimal::compareTo);
            minDraftOfBerths.ifPresent(bigDecimal -> portDetail.setMaxDraft(bigDecimal.toString()));
            Optional<BigDecimal> minAirDraftOfBerths =
                berthInfoSet.stream()
                    .map(BerthInfo::getAirDraft)
                    .filter(Objects::nonNull)
                    .min(BigDecimal::compareTo);
            minAirDraftOfBerths.ifPresent(
                bigDecimal -> portDetail.setMaxAirDraft(bigDecimal.toString()));
          }

          Optional.ofNullable(port.getLattitude()).ifPresent(portDetail::setLat);
          Optional.ofNullable(port.getLongitude()).ifPresent(portDetail::setLon);
          // To set berth details for each port in the response
          Optional.ofNullable(port.getTideHeightFrom())
              .ifPresent(
                  tideHeightFrom -> portDetail.setTideHeightFrom(String.valueOf(tideHeightFrom)));
          Optional.ofNullable(port.getTideHeightTo())
              .ifPresent(tideHeightTo -> portDetail.setTideHeightTo(String.valueOf(tideHeightTo)));
          Optional.ofNullable(port.getMaxPermissibleDraft())
              .ifPresent(draft -> portDetail.setMaxPermissibleDraft(String.valueOf(draft)));

          if (berthDataNotNeed == null || !berthDataNotNeed) {
            List<BerthInfo> berthList =
                berthInfoSet.stream().filter(BerthInfo::getIsActive).collect(Collectors.toList());
            buildBerthInfoResponse(berthList, portDetail);
          }

          portReply.addPorts(portDetail);
        });
  }

  /**
   * Fetch All Timezone data from DB
   *
   * @param request - Empty Object
   * @param responseObserver - Id, Timezone, Offset-Value as object list
   */
  @Override
  public void getTimezone(
      com.cpdss.common.generated.PortInfo.PortEmptyRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.TimezoneResponse> responseObserver) {
    TimezoneResponse.Builder replyBuilder = TimezoneResponse.newBuilder();
    try {
      List<Timezone> timezoneList = timezoneRepository.findAll();
      log.info("Fetch all timezone success with size {}", timezoneList.size());
      for (Timezone tz : timezoneList) {
        com.cpdss.common.generated.PortInfo.Timezone.Builder timezone =
            com.cpdss.common.generated.PortInfo.Timezone.newBuilder();
        timezone.setId(tz.getId());
        timezone.setTimezone(tz.getTimezone() + " " + tz.getRegion());
        timezone.setOffsetValue(tz.getOffsetValue());
        timezone.setAbbreviation(tz.getAbbreviation());
        replyBuilder.addTimezones(timezone);
      }
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      replyBuilder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Fetch timezone failed, e - {}", e.getMessage());
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Fetches port info by paging. Request object has Page Number and Offset Value
   *
   * @param request grpc request
   * @param responseObserver grpc reply of port details
   */
  @Override
  public void getPortInfoByPaging(
      com.cpdss.common.generated.PortInfo.PortRequestWithPaging request,
      StreamObserver<PortReply> responseObserver) {

    PortReply.Builder builder = PortReply.newBuilder();
    try {
      List<Object[]> objectArray = portRepository.findPortsIdAndNames();
      objectArray.forEach(
          var1 -> {
            PortDetail.Builder reply = PortDetail.newBuilder();
            if (var1[0] != null) { // First param Id
              BigInteger val = (BigInteger) var1[0];
              reply.setId(val.longValue());
            }
            if (var1[1] != null) { // Second param Name
              reply.setName((String) var1[1]);
            }
            builder.addPorts(reply);
          });
      log.info("Port Name and Id returned with list size {}", objectArray.size());
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      builder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getCargoInfoByPage method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      builder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Gets berth details using port id
   *
   * @param request port id request from grpc
   * @param responseObserver response output of berth info details
   */
  @Override
  public void getBerthDetailsByPortId(
      com.cpdss.common.generated.PortInfo.PortIdRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse> responseObserver) {
    com.cpdss.common.generated.PortInfo.BerthInfoResponse.Builder builder =
        com.cpdss.common.generated.PortInfo.BerthInfoResponse.newBuilder();
    ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder().setStatus("FAILED");
    try {
      Optional<PortInfo> portInfo = portRepository.findByIdAndIsActiveTrue(request.getPortId());
      if (portInfo.isPresent()) {
        List<BerthInfo> berthInfoList =
            portInfo.get().getBerthInfoSet().stream()
                .filter(BerthInfo::getIsActive)
                .collect(Collectors.toList());
        Optional.ofNullable(portInfo.get().getCode()).ifPresent(builder::setPortCode);
        this.buildBerthInfoToGrpcResponse(berthInfoList, builder);
        log.info(
            "Berth Info size {}, for Port Id {}", berthInfoList.size(), portInfo.get().getId());
        responseStatusBuilder.setStatus("SUCCESS");
      }
      builder.setResponseStatus(responseStatusBuilder);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to get berth info for Port {}", request.getPortId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Builds berth info details to grpc builder
   *
   * @param berthInfos list of berth info details
   * @param builder berth info response builder object
   */
  private void buildBerthInfoToGrpcResponse(
      List<BerthInfo> berthInfos,
      com.cpdss.common.generated.PortInfo.BerthInfoResponse.Builder builder) {

    log.info("Inside buildBerthInfoToGrpcResponse method!");

    for (BerthInfo berthInfo : berthInfos) {

      com.cpdss.common.generated.PortInfo.BerthDetail.Builder berthDetailBuilder =
          com.cpdss.common.generated.PortInfo.BerthDetail.newBuilder();

      // Set fields
      Optional.ofNullable(berthInfo.getId()).ifPresent(berthDetailBuilder::setId);
      Optional.ofNullable(berthInfo.getPortInfo().getId()).ifPresent(berthDetailBuilder::setPortId);
      Optional.ofNullable(berthInfo.getMaxShipChannel())
          .ifPresent(channel -> berthDetailBuilder.setMaxShipChannel(String.valueOf(channel)));
      Optional.ofNullable(berthInfo.getBerthName()).ifPresent(berthDetailBuilder::setBerthName);
      Optional.ofNullable(berthInfo.getMaxShipDepth())
          .ifPresent(depth -> berthDetailBuilder.setMaxShipDepth(String.valueOf(depth)));

      Optional.ofNullable(berthInfo.getMaximumDraft())
          .ifPresent(draft -> berthDetailBuilder.setSeaDraftLimitation(String.valueOf(draft)));
      berthDetailBuilder.setMaxManifoldHeight(getMaxManifoldHeight(berthInfo).toString());
      berthDetailBuilder.setMaxManifoldPressure(getMaxManifoldPressure(berthInfo).toString());

      Optional.ofNullable(berthInfo.getAirDraft())
          .ifPresent(draft -> berthDetailBuilder.setAirDraftLimitation(String.valueOf(draft)));
      Optional.ofNullable(berthInfo.getMaximumLoa())
          .ifPresent(loa -> berthDetailBuilder.setMaxLoa(String.valueOf(loa)));
      Optional.ofNullable(berthInfo.getMaximumDraft())
          .ifPresent(draft -> berthDetailBuilder.setMaxDraft(String.valueOf(draft)));

      Optional.ofNullable(berthInfo.getLineDisplacement())
          .ifPresent(
              displacement -> berthDetailBuilder.setLineDisplacement(String.valueOf(displacement)));

      Optional.ofNullable(berthInfo.getHoseConnection())
          .ifPresent(berthDetailBuilder::setHoseConnection);
      Optional.ofNullable(berthInfo.getUnderKeelClearance()).ifPresent(berthDetailBuilder::setUkc);

      if (berthInfo.getPortInfo() != null) {
        Optional.ofNullable(berthInfo.getPortInfo().getMaxPermissibleDraft())
            .ifPresent(draft -> berthDetailBuilder.setPortMaxPermissibleDraft(draft.toString()));
      }
      Optional.ofNullable(berthInfo.getDisplacement())
          .ifPresent(displacement -> berthDetailBuilder.setDisplacement(displacement.toString()));

      builder.addBerths(berthDetailBuilder);
    }
  }

  /**
   * Gets maximum of all manifold pressure entries against a berth
   *
   * @param berth berth info details
   * @return big decimal value of max manifold pressure
   */
  private BigDecimal getMaxManifoldPressure(BerthInfo berth) {

    log.info("Inside getMaxManifoldPressure method!");

    try {

      List<BerthManifold> berthManifolds =
          berth.getBerthManifolds().stream()
              .filter(BerthManifold::getIsActive)
              .collect(Collectors.toList());
      if (!berthManifolds.isEmpty()) {
        BerthManifold berthManifold =
            berthManifolds.stream().max(Comparator.comparing(BerthManifold::getMaxPressure)).get();
        if (berthManifold.getMaxPressure() != null) return berthManifold.getMaxPressure();
      }
    } catch (Exception e) {
      log.error("Failed to get manifold data for berth Id {}, {}", berth.getId(), e.getMessage());
    }
    return BigDecimal.ZERO;
  }

  /**
   * Gets maximum of all manifold height entries against a berth
   *
   * @param berth berth info details
   * @return big decimal value of max manifold height
   */
  private BigDecimal getMaxManifoldHeight(BerthInfo berth) {

    log.info("Inside getMaxManifoldHeight method!");

    try {

      List<BerthManifold> berthManifolds =
          berth.getBerthManifolds().stream()
              .filter(BerthManifold::getIsActive)
              .collect(Collectors.toList());
      if (!berthManifolds.isEmpty()) {
        BerthManifold berthManifold =
            berthManifolds.stream()
                .max(Comparator.comparing(BerthManifold::getManifoldHeight))
                .get();
        if (berthManifold.getManifoldHeight() != null) return berthManifold.getManifoldHeight();
      }
    } catch (Exception e) {
      log.error("Failed to get manifold data for berth Id {}, {}", berth.getId(), e.getMessage());
    }
    return BigDecimal.ZERO;
  }

  @Override
  public void getCargoInfoByPortIds(
      GetPortInfoByPortIdsRequest request, StreamObserver<CargoInfos> responseObserver) {

    CargoInfos.Builder replyBuilder = CargoInfos.newBuilder();
    try {
      if (request.getIdList() == null || request.getIdList().isEmpty()) {
        throw new GenericServiceException(
            INVALID_PORTID, CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<CargoPortMapping> cargoIds =
          this.cargoPortMappingRepository.findByportInfo_idIn(request.getIdList());
      cargoIds.forEach(
          cargo -> {
            com.cpdss.common.generated.PortInfo.CargoPortMapping.Builder mapping =
                com.cpdss.common.generated.PortInfo.CargoPortMapping.newBuilder();
            mapping.setCargoId(cargo.getCargoXId());
            mapping.setPortId(cargo.getPortInfo().getId());
            replyBuilder.addCargoPorts(mapping.build());
          });

      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      replyBuilder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPortInfoByCargoId method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * In Request pass berth Ids, and return port/berth data for loading plan algo
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getLoadingPlanBerthData(
      com.cpdss.common.generated.PortInfo.BerthIdsRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData> responseObserver) {

    com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData.Builder builder =
        com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData.newBuilder();
    try {
      if (!request.getBerthIdsList().isEmpty()) {
        Optional<BerthInfo> berthInfo =
            berthInfoRepository.findByIdAndIsActiveTrue(request.getBerthIds(0));
        if (berthInfo.isPresent()) {
          log.info("RPC Call into getLoadingPlanBerthData with Ids {}", request.getBerthIdsList());
          builder.setBerthId(berthInfo.get().getId());
          if (berthInfo.get().getPortInfo() != null) {
            Optional.ofNullable(berthInfo.get().getPortInfo().getId())
                .ifPresent(v -> builder.setPortId(v));
            Optional.ofNullable(berthInfo.get().getPortInfo().getControllingDepth())
                .ifPresent(v -> builder.setPortControllingDepth(v.toString()));
            Optional.ofNullable(berthInfo.get().getPortInfo().getUnderKeelClearance())
                .ifPresent(v -> builder.setPortUKC(v));
            Optional.ofNullable(berthInfo.get().getPortInfo().getDensitySeaWater())
                .ifPresent(v -> builder.setPortSeawaterDensity(v.toString()));
          }

          Optional.ofNullable(berthInfo.get().getUnderKeelClearance())
              .ifPresent(builder::setBerthUKC);
        }
        ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
        responseStatus.setStatus(SUCCESS);
        builder.setResponseStatus(responseStatus);
      }
    } catch (Exception e) {
      log.error("Error in getLoadingPlanBerthData method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      builder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Builds berth info response
   *
   * @param berthInfos list of berth details
   * @param portDetail port details builder object
   */
  private void buildBerthInfoResponse(List<BerthInfo> berthInfos, PortDetail.Builder portDetail) {

    for (BerthInfo berthInfo : berthInfos) {

      com.cpdss.common.generated.PortInfo.BerthDetail.Builder builder =
          com.cpdss.common.generated.PortInfo.BerthDetail.newBuilder();

      Optional.ofNullable(berthInfo.getId()).ifPresent(builder::setId);
      Optional.ofNullable(berthInfo.getPortInfo().getId()).ifPresent(builder::setPortId);
      Optional.ofNullable(berthInfo.getMaxShipChannel())
          .ifPresent(v -> builder.setMaxShipChannel(String.valueOf(v)));
      Optional.ofNullable(berthInfo.getBerthName()).ifPresent(builder::setBerthName);
      Optional.ofNullable(berthInfo.getMaxShipDepth())
          .ifPresent(v -> builder.setMaxShipDepth(String.valueOf(v)));
      builder.setMaxManifoldHeight(this.getMaxManifoldHeight(berthInfo).toString());

      Optional.ofNullable(berthInfo.getAirDraft())
          .ifPresent(v -> builder.setAirDraftLimitation(String.valueOf(v)));
      Optional.ofNullable(berthInfo.getMaximumLoa())
          .ifPresent(v -> builder.setMaxLoa(String.valueOf(v)));
      Optional.ofNullable(berthInfo.getMaximumDwt())
          .ifPresent(v -> builder.setMaxDwt(String.valueOf(v)));
      Optional.ofNullable(berthInfo.getMaximumDraft())
          .ifPresent(v -> builder.setMaxDraft(String.valueOf(v)));

      Optional.ofNullable(berthInfo.getLineDisplacement())
          .ifPresent(v -> builder.setLineDisplacement(String.valueOf(v)));

      Optional.ofNullable(berthInfo.getHoseConnection()).ifPresent(builder::setHoseConnection);
      Optional.ofNullable(berthInfo.getUnderKeelClearance()).ifPresent(builder::setUkc);
      Optional.ofNullable(berthInfo.getBerthDatumDepth())
          .ifPresent(v -> builder.setBerthDatumDepth(String.valueOf(v)));

      buildManifoldDetails(berthInfo.getBerthManifolds(), builder);

      portDetail.addBerthDetails(builder);
    }
  }

  /**
   * Builds manifold details from entity
   *
   * @param berthManifolds set of berth manifold details
   * @param builder berth detail builder object
   * @see #buildManifoldDetails(List, BerthInfo)
   */
  private void buildManifoldDetails(
      Set<BerthManifold> berthManifolds, BerthDetail.Builder builder) {

    log.info("Inside buildManifoldDetails method!");

    if (!CollectionUtils.isEmpty(berthManifolds)) {
      berthManifolds.forEach(
          berthManifold -> {
            ManifoldDetail.Builder manifoldDetailBuilder = ManifoldDetail.newBuilder();

            // Set fields
            Optional.ofNullable(berthManifold.getManifoldName())
                .ifPresent(manifoldDetailBuilder::setManifoldName);
            Optional.ofNullable(berthManifold.getConnectionNumber())
                .ifPresent(manifoldDetailBuilder::setConnectionNumber);
            Optional.ofNullable(berthManifold.getManifoldSize())
                .ifPresent(manifoldDetailBuilder::setManifoldSize);
            Optional.ofNullable(berthManifold.getMaxLoadingRate())
                .ifPresent(manifoldDetailBuilder::setMaxLoadingRate);
            Optional.ofNullable(berthManifold.getMaxDischargeRate())
                .ifPresent(manifoldDetailBuilder::setMaxDischargeRate);

            manifoldDetailBuilder.setManifoldHeight(
                returnZeroIfNull(berthManifold.getManifoldHeight()));
            manifoldDetailBuilder.setMaxPressure(returnZeroIfNull(berthManifold.getMaxPressure()));

            builder.addManifoldDetails(manifoldDetailBuilder.build());
          });
    }
  }

  /**
   * Returns string value of BigDecimal input if not null. Else returns string value of BigDecimal
   * zero
   *
   * @param bigDecimalInputValue input value of BigDecimal
   * @return output string value of BigDecimal input
   */
  private String returnZeroIfNull(BigDecimal bigDecimalInputValue) {

    return String.valueOf(Objects.requireNonNullElse(bigDecimalInputValue, BigDecimal.ZERO));
  }

  /** Get all country details */
  @Override
  public void getAllCountries(Empty request, StreamObserver<CountryReply> responseObserver) {
    Builder builder = CountryReply.newBuilder();

    try {
      List<Object[]> list = countryRepository.findCountryIdAndNames();
      log.info("Fetch all country success with size {}", list.size());
      for (Object[] obj : list) {
        com.cpdss.common.generated.PortInfo.Country.Builder country =
            com.cpdss.common.generated.PortInfo.Country.newBuilder();
        long id = (long) obj[0];
        country.setId(id);
        String name = (String) obj[1];
        country.setCountryName(name);
        builder.addCountries(country);
      }
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      builder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Fetch country failed, e - {}", e.getMessage());
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      builder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /** Retrieves the port info from cargoportmapping table for all cargoes */
  @Override
  public void getAllCargoPortMapping(
      com.cpdss.common.generated.PortInfo.CargoPortRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.CargoPortReply> responseObserver) {
    com.cpdss.common.generated.PortInfo.CargoPortReply.Builder replyBuilder =
        com.cpdss.common.generated.PortInfo.CargoPortReply.newBuilder();
    try {
      List<CargoPortMapping> portMappings = new ArrayList<>();
      if (StringUtils.isEmpty(request.getPortName())) {
        portMappings = this.cargoPortMappingRepository.findAll();
      } else {
        Specification<CargoPortMapping> specification =
            Specification.where(
                new CargoPortInfoSpecification(
                    new FilterCriteria(
                        "name", "like-with-join", request.getPortName(), "portInfo")));
        portMappings = this.cargoPortMappingRepository.findAll(specification);
      }

      System.out.println(portMappings.size());
      portMappings.forEach(
          portMapping -> {
            com.cpdss.common.generated.PortInfo.CargoPortMappingDetail.Builder portDetail =
                com.cpdss.common.generated.PortInfo.CargoPortMappingDetail.newBuilder();
            Optional.ofNullable(portMapping.getId()).ifPresent(portDetail::setId);
            Optional.ofNullable(portMapping.getCargoXId()).ifPresent(portDetail::setCargoId);
            if (portMapping.getPortInfo() == null) {
              System.out.println("NULLLLLLLLLLLLLLLLLLLLLL");
            } else {
              portDetail.setPortId(
                  portMapping.getPortInfo().getId() == null
                      ? 0
                      : portMapping.getPortInfo().getId());
              portDetail.setPortName(
                  portMapping.getPortInfo().getName() == null
                      ? ""
                      : portMapping.getPortInfo().getName());
              portDetail.setPortCode(
                  portMapping.getPortInfo().getCode() == null
                      ? ""
                      : portMapping.getPortInfo().getCode());
              portDetail.setWaterDensity(
                  portMapping.getPortInfo().getDensitySeaWater() == null
                      ? ""
                      : portMapping.getPortInfo().getDensitySeaWater().toString());
              portDetail.setMaxDraft(
                  portMapping.getPortInfo().getMaxPermissibleDraft() == null
                      ? ""
                      : portMapping.getPortInfo().getMaxPermissibleDraft().toString());
              portDetail.setMaxAirDraft(
                  portMapping.getPortInfo().getMaxPermissibleDraft() == null
                      ? ""
                      : portMapping.getPortInfo().getMaxPermissibleDraft().toString());
            }
            replyBuilder.addPorts(portDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      replyBuilder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPortInfoByCargoId method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Retrieves the port info from cargoportmapping table for a particular cargo */
  @Override
  public void getAllCargoPortMappingById(
      com.cpdss.common.generated.PortInfo.CargoPortRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.CargoPortReply> responseObserver) {
    com.cpdss.common.generated.PortInfo.CargoPortReply.Builder replyBuilder =
        com.cpdss.common.generated.PortInfo.CargoPortReply.newBuilder();
    try {
      List<CargoPortMapping> portMappings =
          this.cargoPortMappingRepository.findByCargoXId(request.getCargoId());
      buildCargoPortReply(portMappings, replyBuilder);

    } catch (Exception e) {
      log.error("Error in getPortInfoByCargoId method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Building cargo port reply
   *
   * @param portMappings
   * @param replyBuilder
   */
  private void buildCargoPortReply(
      List<CargoPortMapping> portMappings,
      com.cpdss.common.generated.PortInfo.CargoPortReply.Builder replyBuilder) {

    portMappings.forEach(
        portMapping -> {
          com.cpdss.common.generated.PortInfo.CargoPortMappingDetail.Builder portDetail =
              com.cpdss.common.generated.PortInfo.CargoPortMappingDetail.newBuilder();
          Optional.ofNullable(portMapping.getId()).ifPresent(portDetail::setId);
          Optional.ofNullable(portMapping.getCargoXId()).ifPresent(portDetail::setCargoId);
          Optional.ofNullable(portMapping.getPortInfo().getId()).ifPresent(portDetail::setPortId);
          Optional.ofNullable(portMapping.getPortInfo().getName())
              .ifPresent(portDetail::setPortName);
          Optional.ofNullable(portMapping.getPortInfo().getCode())
              .ifPresent(portDetail::setPortCode);
          Optional.ofNullable(portMapping.getPortInfo().getDensitySeaWater())
              .ifPresent(density -> portDetail.setWaterDensity(density.toString()));
          Optional.ofNullable(portMapping.getPortInfo().getMaxPermissibleDraft())
              .ifPresent(draft -> portDetail.setMaxDraft(draft.toString()));
          Optional.ofNullable(portMapping.getPortInfo().getCountry())
              .ifPresent(
                  country -> {
                    Optional.ofNullable(country.getId()).ifPresent(portDetail::setCountryId);
                    Optional.ofNullable(country.getName()).ifPresent(portDetail::setCountryName);
                  });
          replyBuilder.addPorts(portDetail);
        });
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    replyBuilder.setResponseStatus(responseStatus);
  }

  /**
   * Saving all cargo port mappings
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void saveAllCargoPortMappings(
      com.cpdss.common.generated.PortInfo.CargoPortMappingRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.CargoPortReply> responseObserver) {
    com.cpdss.common.generated.PortInfo.CargoPortReply.Builder replyBuilder =
        com.cpdss.common.generated.PortInfo.CargoPortReply.newBuilder();
    try {

      List<CargoPortMapping> portMappings = new ArrayList<>();

      Optional.of(request.getCargoPortMappingList())
          .ifPresent(
              cargoPortMappings -> {
                cargoPortMappings.forEach(
                    cargoPortMapping -> {
                      Optional<PortInfo> portInfoWrapper =
                          this.portRepository.findByIdAndIsActiveTrue(cargoPortMapping.getPortId());
                      portInfoWrapper.ifPresent(
                          portInfo -> {
                            Optional<CargoPortMapping> cargoPortMappingWrapper =
                                this.cargoPortMappingRepository.findByCargoXIdAndPortIdAndIsActive(
                                    cargoPortMapping.getCargoId(),
                                    cargoPortMapping.getPortId(),
                                    true);
                            if (cargoPortMappingWrapper.isEmpty()) {
                              CargoPortMapping cargoPortMappingEntity = new CargoPortMapping();
                              cargoPortMappingEntity.setCargoXId(cargoPortMapping.getCargoId());
                              cargoPortMappingEntity.setPortInfo(portInfo);
                              cargoPortMappingEntity.setIsActive(true);
                              portMappings.add(
                                  this.cargoPortMappingRepository.save(cargoPortMappingEntity));
                            } else {
                              portMappings.add(cargoPortMappingWrapper.get());
                            }
                          });
                    });
              });

      buildCargoPortReply(portMappings, replyBuilder);

    } catch (Exception e) {
      log.error("Error in save Cargo Port Mapping method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Retrieve all Master cargos with Pagination, Sorting and Filtering
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getPortInfoDetailed(
      PortRequest request, io.grpc.stub.StreamObserver<PortReply> responseObserver) {
    PortReply.Builder portReplyBuilder = PortReply.newBuilder();
    try {

      // Filtering
      List<String> filterKeys =
          Arrays.asList("id", "name", "code", "densitySeaWater", "timezone", "countryName");
      Map<String, String> params = new HashMap<>();
      request.getParamList().forEach(param -> params.put(param.getKey(), param.getValue()));
      Map<String, String> filterParams =
          params.entrySet().stream()
              .filter(e -> filterKeys.contains(e.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      Specification<PortInfo> specification =
          Specification.where(
              new PortInfoSpecification(new FilterCriteria("isActive", ":", true, "")));

      for (Map.Entry<String, String> entry : filterParams.entrySet()) {
        String filterKey = entry.getKey();
        String value = entry.getValue();
        if (filterKey.equals("densitySeaWater")) {
          specification =
              specification.and(
                  new PortInfoSpecification(new FilterCriteria(filterKey, ":", value, "")));
        } else if (filterKey.equals("timezone")) {
          if (value.contains(" ")) {
            String[] valueArray = value.split(" ");
            value = valueArray[0] + "%" + valueArray[1];
          }
          specification =
              specification.and(
                  new PortInfoSpecification(
                      new FilterCriteria(filterKey, "like-with-join", value, "timezone")));
        } else if (filterKey.equals("countryName")) {
          specification =
              specification.and(
                  new PortInfoSpecification(
                      new FilterCriteria("name", "like-with-join", value, "country")));
        } else {
          specification =
              specification.and(
                  new PortInfoSpecification(new FilterCriteria(filterKey, "like", value, "")));
        }
      }

      // Paging and sorting
      Pageable paging =
          PageRequest.of(
              request.getPage(),
              request.getPageSize(),
              Sort.by(
                  Sort.Direction.valueOf(request.getOrderBy().toUpperCase()), request.getSortBy()));
      Page<PortInfo> pagedResult = this.portRepository.findAll(specification, paging);

      List<PortInfo> ports = pagedResult.toList();
      ports.forEach(
          portInfo -> {
            PortDetail.Builder portDetail = PortDetail.newBuilder();
            buildCargoDetailed(portInfo, portDetail);
            portReplyBuilder.addPorts(portDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      portReplyBuilder.setResponseStatus(responseStatus);
      portReplyBuilder.setTotalElements(pagedResult.getTotalElements());
    } catch (Exception e) {
      log.error("Error in getPortInfoDetailed method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      portReplyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(portReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Building port info detail
   *
   * @param portInfo
   * @param portDetail
   */
  private void buildCargoDetailed(PortInfo portInfo, PortDetail.Builder portDetail) {

    Optional.ofNullable(portInfo.getId()).ifPresent(portDetail::setId);
    Optional.ofNullable(portInfo.getName()).ifPresent(portDetail::setName);
    Optional.ofNullable(portInfo.getCode()).ifPresent(portDetail::setCode);
    Optional.ofNullable(portInfo.getDensitySeaWater())
        .ifPresent(waterDensity -> portDetail.setWaterDensity(String.valueOf(waterDensity)));

    if (portInfo.getTimezone() != null) {
      portDetail.setTimezone(portInfo.getTimezone().getTimezone());
      portDetail.setTimezoneOffsetVal(portInfo.getTimezone().getOffsetValue());
      portDetail.setTimezoneId(portInfo.getTimezone().getId());
      portDetail.setTimezoneAbbreviation(portInfo.getTimezone().getAbbreviation());
    }

    Optional.ofNullable(portInfo.getCountry())
        .ifPresent(item -> portDetail.setCountryName(portInfo.getCountry().getName()));
    Optional.ofNullable(portInfo.getCountry())
        .ifPresent(item -> portDetail.setCountryId(portInfo.getCountry().getId()));
  }

  /**
   * Saves a new port info or edits existing one
   *
   * @param request grpc port detail request
   * @param responseObserver saved port detail response
   */
  @Override
  public void savePortInfo(
      PortDetail request,
      io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortInfoReply>
          responseObserver) {
    PortInfoReply.Builder portReply = PortInfoReply.newBuilder();

    try {
      PortInfo portInfo;
      if (request.getId() == 0) {
        portInfo = new PortInfo();
      } else {
        portInfo = this.portRepository.getById(request.getId());
      }
      buildPortEntity(request, portInfo);
      PortInfo savedPortInfo = this.portRepository.save(portInfo);

      getPort(portReply, savedPortInfo);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      portReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in savePort method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILED");
      portReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(portReply.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Fetches single Port Response
   *
   * @param portReply port reply builder
   * @param portInfo port info details
   */
  private void getPort(PortInfoReply.Builder portReply, PortInfo portInfo) {

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    PortDetail.Builder portDetail = PortDetail.newBuilder();
    Optional.ofNullable(portInfo.getId()).ifPresent(portDetail::setId);
    Optional.ofNullable(portInfo.getName()).ifPresent(portDetail::setName);
    Optional.ofNullable(portInfo.getCode()).ifPresent(portDetail::setCode);
    Optional.ofNullable(portInfo.getDensitySeaWater())
        .ifPresent(waterDensity -> portDetail.setWaterDensity(String.valueOf(waterDensity)));
    Optional.ofNullable(portInfo.getAverageTideHeight())
        .ifPresent(
            averageTideHeight ->
                portDetail.setAverageTideHeight(String.valueOf(averageTideHeight)));
    Optional.ofNullable(portInfo.getTideHeight())
        .ifPresent(tideHeight -> portDetail.setTideHeight(String.valueOf(tideHeight)));

    Optional.ofNullable(portInfo.getHwTideFrom())
        .ifPresent(item -> portDetail.setHwTideFrom(String.valueOf(item)));
    Optional.ofNullable(portInfo.getHwTideTo())
        .ifPresent(item -> portDetail.setHwTideTo(String.valueOf(item)));
    Optional.ofNullable(portInfo.getHwTideTimeFrom())
        .ifPresent(item -> portDetail.setHwTideTimeFrom(timeFormatter.format(item)));
    Optional.ofNullable(portInfo.getHwTideTimeTo())
        .ifPresent(item -> portDetail.setHwTideTimeTo(timeFormatter.format(item)));

    Optional.ofNullable(portInfo.getLwTideFrom())
        .ifPresent(item -> portDetail.setLwTideFrom(String.valueOf(item)));
    Optional.ofNullable(portInfo.getLwTideTo())
        .ifPresent(item -> portDetail.setLwTideTo(String.valueOf(item)));
    Optional.ofNullable(portInfo.getLwTideTimeFrom())
        .ifPresent(item -> portDetail.setLwTideTimeFrom(timeFormatter.format(item)));
    Optional.ofNullable(portInfo.getLwTideTimeTo())
        .ifPresent(item -> portDetail.setLwTideTimeTo(timeFormatter.format(item)));

    Optional.ofNullable(portInfo.getTimeOfSunrise())
        .ifPresent(item -> portDetail.setSunriseTime(timeFormatter.format(item)));
    Optional.ofNullable(portInfo.getTimeOfSunSet())
        .ifPresent(item -> portDetail.setSunsetTime(timeFormatter.format(item)));

    Optional.ofNullable(portInfo.getControllingDepth())
        .ifPresent(v -> portDetail.setControllingDepth(v.toString()));
    Optional.ofNullable(portInfo.getUnderKeelClearance())
        .ifPresent(portDetail::setUnderKeelClearance);

    if (portInfo.getTimezone() != null) {
      portDetail.setTimezone(portInfo.getTimezone().getTimezone());
      portDetail.setTimezoneOffsetVal(portInfo.getTimezone().getOffsetValue());
      portDetail.setTimezoneId(portInfo.getTimezone().getId());
      portDetail.setTimezoneAbbreviation(portInfo.getTimezone().getAbbreviation());
    }

    Optional.ofNullable(portInfo.getCountry())
        .ifPresent(item -> portDetail.setCountryName(portInfo.getCountry().getName()));
    Optional.ofNullable(portInfo.getCountry())
        .ifPresent(item -> portDetail.setCountryId(portInfo.getCountry().getId()));

    if (!portInfo.getBerthInfoSet().isEmpty()) {
      Optional<BigDecimal> minDraftOfBerths =
          portInfo.getBerthInfoSet().stream()
              .map(BerthInfo::getMaximumDraft)
              .filter(Objects::nonNull)
              .min(BigDecimal::compareTo);
      minDraftOfBerths.ifPresent(bigDecimal -> portDetail.setMaxDraft(bigDecimal.toString()));
      Optional<BigDecimal> minAirDraftOfBerths =
          portInfo.getBerthInfoSet().stream()
              .map(BerthInfo::getAirDraft)
              .filter(Objects::nonNull)
              .min(BigDecimal::compareTo);
      minAirDraftOfBerths.ifPresent(bigDecimal -> portDetail.setMaxAirDraft(bigDecimal.toString()));
    }

    Optional.ofNullable(portInfo.getLattitude()).ifPresent(portDetail::setLat);
    Optional.ofNullable(portInfo.getLongitude()).ifPresent(portDetail::setLon);
    // To set berth details for each portInfo in the response
    Optional.ofNullable(portInfo.getTideHeightFrom())
        .ifPresent(tideHeightFrom -> portDetail.setTideHeightFrom(String.valueOf(tideHeightFrom)));
    Optional.ofNullable(portInfo.getTideHeightTo())
        .ifPresent(tideHeightTo -> portDetail.setTideHeightTo(String.valueOf(tideHeightTo)));
    Optional.ofNullable(portInfo.getMaxPermissibleDraft())
        .ifPresent(draft -> portDetail.setMaxPermissibleDraft(String.valueOf(draft)));
    Optional.ofNullable(portInfo.getAmbientTemperature())
        .ifPresent(ambientTemp -> portDetail.setAmbientTemperature(String.valueOf(ambientTemp)));
    Set<BerthInfo> berthInfoSet =
        portInfo.getBerthInfoSet() == null ? new HashSet<>() : portInfo.getBerthInfoSet();
    List<BerthInfo> berthList =
        berthInfoSet.stream()
            .filter(item -> item.getIsActive() != null && item.getIsActive().equals(true))
            .collect(Collectors.toList());
    buildBerthInfoResponse(berthList, portDetail);

    portReply.setPort(portDetail);
  }

  private void buildPortEntity(PortDetail request, PortInfo portInfo)
      throws GenericServiceException {

    if (request.getId() == 0
        || (portInfo.getCountry() != null
            && portInfo.getCountry().getId() != request.getCountryId())) {
      Country country = this.countryRepository.getById(request.getCountryId());
      portInfo.setCountry(country);
    }

    portInfo.setDensitySeaWater(returnZeroIfBlank(request.getWaterDensity()));
    portInfo.setMaxPermissibleDraft(returnZeroIfBlank(request.getMaxPermissibleDraft()));
    portInfo.setCode(request.getCode());
    portInfo.setName(request.getName());
    if (request.getId() == 0
        || (portInfo.getTimezone() != null
            && portInfo.getTimezone().getId() != request.getTimezoneId())) {
      Timezone timezone = this.timezoneRepository.getById(request.getTimezoneId());
      portInfo.setTimezone(timezone);
    }

    portInfo.setTideHeightTo(returnZeroIfBlank(request.getTideHeightTo()));
    portInfo.setTideHeightFrom(returnZeroIfBlank(request.getTideHeightFrom()));
    portInfo.setLattitude(request.getLat());
    portInfo.setLongitude(request.getLon());
    portInfo.setAmbientTemperature(returnZeroIfBlank(request.getAmbientTemperature()));
    portInfo.setIsActive(true);
    Set<BerthInfo> berthInfoList =
        setBerthInformationForThePorts(request.getBerthDetailsList(), portInfo);
    portInfo.setBerthInfoSet(berthInfoList);
  }

  /**
   * Sets BerthInformation for Ports
   *
   * @param berthDetailsList list of berth details
   * @param portInfo port info details
   * @return set of berth info details
   */
  private Set<BerthInfo> setBerthInformationForThePorts(
      List<BerthDetail> berthDetailsList, PortInfo portInfo) throws GenericServiceException {
    Set<BerthInfo> berthList = new HashSet<>();
    if (berthDetailsList == null) return berthList;
    for (BerthDetail berth : berthDetailsList) {
      BerthInfo berthResponse;
      if (berth.getId() == 0) {
        berthResponse = new BerthInfo();
      } else {
        Optional<BerthInfo> berthOptResponse =
            this.berthInfoRepository.findByIdAndIsActiveTrue(berth.getId());
        if (berthOptResponse.isEmpty()) {
          log.error(
              "Error in Save Port -> Berth Detail get -> portId: {}, berth id: {}",
              portInfo.getId(),
              berth.getId());
          throw new GenericServiceException(
              "Invalid Berth Id",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        berthResponse = berthOptResponse.get();
      }
      berthResponse.setBerthName(berth.getBerthName());
      berthResponse.setBerthDatumDepth(returnZeroIfBlank(berth.getBerthDatumDepth()));
      berthResponse.setMaximumDraft(returnZeroIfBlank(berth.getMaxDraft()));
      berthResponse.setMaxShipDepth(returnZeroIfBlank(berth.getMaxShipDepth()));
      berthResponse.setMaximumDwt(returnZeroIfBlank(berth.getMaxDwt()));
      berthResponse.setMaximumLoa(returnZeroIfBlank(berth.getMaxLoa()));
      berthResponse.setUnderKeelClearance(berth.getUkc());
      berthResponse.setIsActive(true);
      berthResponse.setPortInfo(portInfo);

      buildManifoldDetails(berth.getManifoldDetailsList(), berthResponse);

      berthList.add(berthResponse);
    }
    return berthList;
  }

  /**
   * Builds manifold details entity from grpc
   *
   * @param manifoldDetailsList list of manifold details from grpc request
   * @param berthResponse berth info entity
   * @see #buildManifoldDetails(Set, BerthDetail.Builder)
   */
  private void buildManifoldDetails(
      List<ManifoldDetail> manifoldDetailsList, BerthInfo berthResponse) {

    log.info("Inside buildManifoldDetails method!");

    Set<BerthManifold> berthManifolds = new HashSet<>();
    if (!CollectionUtils.isEmpty(manifoldDetailsList)) {

      manifoldDetailsList.forEach(
          manifoldDetail -> {
            BerthManifold berthManifold = new BerthManifold();

            // Set fields
            Optional.of(manifoldDetail.getManifoldName()).ifPresent(berthManifold::setManifoldName);
            Optional.of(manifoldDetail.getConnectionNumber())
                .ifPresent(berthManifold::setConnectionNumber);
            Optional.of(manifoldDetail.getManifoldSize()).ifPresent(berthManifold::setManifoldSize);
            Optional.of(manifoldDetail.getMaxLoadingRate())
                .ifPresent(berthManifold::setMaxLoadingRate);
            Optional.of(manifoldDetail.getMaxDischargeRate())
                .ifPresent(berthManifold::setMaxDischargeRate);

            berthManifold.setManifoldHeight(returnZeroIfBlank(manifoldDetail.getManifoldHeight()));
            berthManifold.setMaxPressure(returnZeroIfBlank(manifoldDetail.getMaxPressure()));
            berthManifold.setBerthInfo(berthResponse);
            berthManifold.setIsActive(true);
            berthManifolds.add(berthManifold);
          });
    }
    berthResponse.setBerthManifolds(berthManifolds);
  }

  /**
   * Returns big decimal value of provided string else returns big decimal zero
   *
   * @param string input string
   * @return big decimal value
   */
  private BigDecimal returnZeroIfBlank(String string) {

    return StringUtils.isBlank(string) ? BigDecimal.ZERO : new BigDecimal(string);
  }

  /**
   * Deleting cargo port mappings by cargoId
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteCargoPortMappings(
      CargoPortRequest request, StreamObserver<CargoPortReply> responseObserver) {
    com.cpdss.common.generated.PortInfo.CargoPortReply.Builder replyBuilder =
        com.cpdss.common.generated.PortInfo.CargoPortReply.newBuilder();
    try {

      Integer numberOfRowsUpdated =
          this.cargoPortMappingRepository.deleteAllByCargoXId(request.getCargoId());
      if (numberOfRowsUpdated == 0) {
        log.info("No rows updated!");
      }
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      replyBuilder.setResponseStatus(responseStatus);

    } catch (Exception e) {
      log.error("Error in save Cargo Port Mapping method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      replyBuilder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getACountry(
      com.cpdss.common.generated.PortInfo.Country countryRequest,
      StreamObserver<CountryDetailReply> responseObserver) {

    CountryDetailReply.Builder builder = CountryDetailReply.newBuilder();
    try {

      Optional<Country> countryEntity = countryRepository.findById(countryRequest.getId());

      log.info("Fetch a country with id {}", countryRequest.getId());

      com.cpdss.common.generated.PortInfo.Country.Builder country =
          com.cpdss.common.generated.PortInfo.Country.newBuilder();

      countryEntity.ifPresent(
          c -> {
            country.setCountryName(c.getName());
            country.setId(c.getId());
          });

      builder.setCountry(country);

      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      builder.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Fetch country failed, e - {}", e.getMessage());
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      builder.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }
}
