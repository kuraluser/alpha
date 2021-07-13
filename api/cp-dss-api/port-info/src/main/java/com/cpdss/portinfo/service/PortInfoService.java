/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply;
import com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfo.TimezoneResponse;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.portinfo.entity.BerthInfo;
import com.cpdss.portinfo.entity.BerthManifold;
import com.cpdss.portinfo.entity.PortInfo;
import com.cpdss.portinfo.entity.Timezone;
import com.cpdss.portinfo.repository.*;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/** Service with operations related to port information */
@Log4j2
@GrpcService
public class PortInfoService extends PortInfoServiceImplBase {

  @Autowired private PortInfoRepository portRepository;
  @Autowired private CargoPortMappingRepository cargoPortMappingRepository;
  @Autowired private TimezoneRepository timezoneRepository;

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
      getPorts(portReply, portList);
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

  /** Retrieves portInfo for a list of port ids */
  @Override
  public void getPortInfoByPortIds(
      GetPortInfoByPortIdsRequest request, StreamObserver<PortReply> responseObserver) {
    PortReply.Builder portReply = PortReply.newBuilder();
    try {
      if (request != null && CollectionUtils.isEmpty(request.getIdList())) {
        throw new GenericServiceException(
            INVALID_PORTID, CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      if (request != null) {
        List<PortInfo> portList = portRepository.findByIdInAndIsActive(request.getIdList(), true);
        getPorts(portReply, portList);
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

  private void getPorts(PortReply.Builder portReply, List<PortInfo> portList) {
    Comparator<BerthInfo> byMaxDraftComparator =
        Comparator.comparing(
            BerthInfo::getMaximumDraft, Comparator.nullsFirst(Comparator.naturalOrder()));
    Comparator<BerthInfo> byAirDraftComparator =
        Comparator.comparing(
            BerthInfo::getAirDraft, Comparator.nullsFirst(Comparator.naturalOrder()));
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
          if (port.getTimezone() != null) {
            portDetail.setTimezone(port.getTimezone().getTimezone());
            portDetail.setTimezoneOffsetVal(port.getTimezone().getOffsetValue());
            portDetail.setTimezoneId(port.getTimezone().getId());
            portDetail.setTimezoneAbbreviation(port.getTimezone().getAbbreviation());
          }

          Optional.ofNullable(port.getCountry())
              .ifPresent(item -> portDetail.setCountryName(port.getCountry().getName()));

          if (!port.getBerthInfoSet().isEmpty()) {
            Optional<BigDecimal> minDraftOfBerths =
                port.getBerthInfoSet().stream()
                    .filter(v -> v.getMaximumDraft() != null)
                    .map(BerthInfo::getMaximumDraft)
                    .min(BigDecimal::compareTo);
            if (minDraftOfBerths.isPresent()) {
              portDetail.setMaxDraft(minDraftOfBerths.get().toString());
            }
            Optional<BigDecimal> minAirDraftOfBerths =
                port.getBerthInfoSet().stream()
                    .filter(v -> v.getAirDraft() != null)
                    .map(BerthInfo::getAirDraft)
                    .min(BigDecimal::compareTo);
            if (minAirDraftOfBerths.isPresent()) {
              portDetail.setMaxAirDraft(minAirDraftOfBerths.get().toString());
            }
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
   * Request object have Page Number and Offset Value
   *
   * @param request
   * @param responseObserver
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

  @Autowired BerthInfoRepository berthInfoRepository;

  @Autowired BerthManifoldRepository berthManifoldRepository;

  @Override
  public void getBerthDetailsByPortId(
      com.cpdss.common.generated.PortInfo.PortIdRequest request,
      StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse> responseObserver) {
    com.cpdss.common.generated.PortInfo.BerthInfoResponse.Builder builder =
        com.cpdss.common.generated.PortInfo.BerthInfoResponse.newBuilder();
    ResponseStatus.Builder builder1 = ResponseStatus.newBuilder().setStatus("FAILED");
    try {
      Optional<PortInfo> portInfo = portRepository.findByIdAndIsActiveTrue(request.getPortId());
      if (portInfo.isPresent()) {
        List<BerthInfo> berthInfoList =
            berthInfoRepository.findAllByPortInfoAndIsActiveTrue(portInfo.get().getId());
        this.buildBerthInfoToGrpcResponse(berthInfoList, builder);
        log.info(
            "Berth Info size {}, for Port Id {}", berthInfoList.size(), portInfo.get().getId());
        builder1.setStatus("SUCCESS");
      }
      builder.setResponseStatus(builder1);
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

  private void buildBerthInfoToGrpcResponse(
      List<BerthInfo> list, com.cpdss.common.generated.PortInfo.BerthInfoResponse.Builder builder) {
    for (BerthInfo bi : list) {
      com.cpdss.common.generated.PortInfo.BerthDetail.Builder builder2 =
          com.cpdss.common.generated.PortInfo.BerthDetail.newBuilder();
      Optional.ofNullable(bi.getId()).ifPresent(builder2::setId);
      Optional.ofNullable(bi.getPortInfo().getId()).ifPresent(builder2::setPortId);
      Optional.ofNullable(bi.getMaxShipChannel())
          .ifPresent(v -> builder2.setMaxShipChannel(String.valueOf(v)));
      Optional.ofNullable(bi.getBerthName())
          .ifPresent(v -> builder2.setBerthName(String.valueOf(v)));
      Optional.ofNullable(bi.getMaxShipDepth())
          .ifPresent(v -> builder2.setMaxShipDepth(String.valueOf(v)));

      Optional.ofNullable(bi.getMaximumDraft())
          .ifPresent(v -> builder2.setSeaDraftLimitation(String.valueOf(v)));
      builder2.setMaxManifoldHeight(this.getMaxManifoldHeight(bi).toString());

      Optional.ofNullable(bi.getAirDraft())
          .ifPresent(v -> builder2.setAirDraftLimitation(String.valueOf(v)));
      Optional.ofNullable(bi.getMaximumLoa()).ifPresent(v -> builder2.setMaxLoa(String.valueOf(v)));
      Optional.ofNullable(bi.getMaximumDraft())
          .ifPresent(v -> builder2.setMaxDraft(String.valueOf(v)));

      Optional.ofNullable(bi.getLineDisplacement())
          .ifPresent(v -> builder2.setLineDisplacement(String.valueOf(v)));
      builder.addBerths(builder2);
    }
  }

  private BigDecimal getMaxManifoldHeight(BerthInfo berth) {
    try {
      List<BerthManifold> var1 =
          berthManifoldRepository.findByBerthInfoAndIsActiveTrue(berth.getId());
      if (!var1.isEmpty()) {
        BerthManifold va =
            var1.stream().max(Comparator.comparing(BerthManifold::getManifoldHeight)).get();
        if (va.getManifoldHeight() != null) return va.getManifoldHeight();
      }
    } catch (Exception e) {
      log.error("Failed to get manifold data for berth Id {}, {}", berth.getId(), e.getMessage());
    }
    return BigDecimal.ZERO;
  }
}
