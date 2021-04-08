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
import com.cpdss.portinfo.entity.PortInfo;
import com.cpdss.portinfo.entity.Timezone;
import com.cpdss.portinfo.repository.CargoPortMappingRepository;
import com.cpdss.portinfo.repository.PortInfoRepository;
import com.cpdss.portinfo.repository.TimezoneRepository;
import io.grpc.stub.StreamObserver;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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
          }

          if (!port.getBerthInfoSet().isEmpty()) {
            BerthInfo maxDraftBerthInfo =
                Collections.max(port.getBerthInfoSet(), byMaxDraftComparator);
            Optional.ofNullable(maxDraftBerthInfo.getMaximumDraft())
                .ifPresent(maxDraft -> portDetail.setMaxDraft(String.valueOf(maxDraft)));
            BerthInfo maxAirDraftBerthInfo =
                Collections.max(port.getBerthInfoSet(), byAirDraftComparator);
            Optional.ofNullable(maxAirDraftBerthInfo.getAirDraft())
                .ifPresent(airDraft -> portDetail.setMaxAirDraft(String.valueOf(airDraft)));
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
}
