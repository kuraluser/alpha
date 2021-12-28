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
import com.cpdss.portinfo.entity.CargoPortMapping;
import com.cpdss.portinfo.entity.Country;
import com.cpdss.portinfo.entity.Timezone;
import com.cpdss.portinfo.entity.*;
import com.cpdss.portinfo.repository.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.instrument.util.StringUtils;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

          Optional.ofNullable(port.getLattitude())
              .ifPresent(item -> portDetail.setLat(String.valueOf(item)));
          Optional.ofNullable(port.getLongitude())
              .ifPresent(item -> portDetail.setLon(String.valueOf(item)));
          // To set berth details for each port in the response
          Optional.ofNullable(port.getTideHeightFrom())
              .ifPresent(
                  tideHeightFrom -> portDetail.setTideHeightFrom(String.valueOf(tideHeightFrom)));
          Optional.ofNullable(port.getTideHeightTo())
              .ifPresent(tideHeightTo -> portDetail.setTideHeightTo(String.valueOf(tideHeightTo)));
          Optional.ofNullable(port.getMaxPermissibleDraft())
              .ifPresent(draft -> portDetail.setMaxPermissibleDraft(String.valueOf(draft)));
          Set<BerthInfo> berthInfoSet = port.getBerthInfoSet();
          List<BerthInfo> berthList =
              berthInfoSet.stream()
                  .filter(item -> item.getIsActive().equals(true))
                  .collect(Collectors.toList());
          buildBerthInfoResponse(berthList, portDetail);
          // ##
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
      Optional.ofNullable(bi.getBerthName()).ifPresent(v -> builder2.setBerthName(v));
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

      Optional.ofNullable(bi.getHoseConnection()).ifPresent(builder2::setHoseConnection);

      Optional.ofNullable(bi.getUnderKeelClearance()).ifPresent(builder2::setUkc);
      if (bi.getPortInfo() != null) {
        Optional.ofNullable(bi.getPortInfo().getMaxPermissibleDraft())
            .ifPresent(t -> builder2.setPortMaxPermissibleDraft(t.toString()));
      }
      Optional.ofNullable(bi.getDisplacement())
          .ifPresent(displacement -> builder2.setDisplacement(displacement.toString()));
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

  private void buildBerthInfoResponse(List<BerthInfo> list, PortDetail.Builder portDetail) {
    for (BerthInfo bi : list) {
      com.cpdss.common.generated.PortInfo.BerthDetail.Builder builder2 =
          com.cpdss.common.generated.PortInfo.BerthDetail.newBuilder();
      Optional.ofNullable(bi.getId()).ifPresent(builder2::setId);
      Optional.ofNullable(bi.getPortInfo().getId()).ifPresent(builder2::setPortId);
      Optional.ofNullable(bi.getMaxShipChannel())
          .ifPresent(v -> builder2.setMaxShipChannel(String.valueOf(v)));
      Optional.ofNullable(bi.getBerthName()).ifPresent(v -> builder2.setBerthName(v));
      Optional.ofNullable(bi.getMaxShipDepth())
          .ifPresent(v -> builder2.setMaxShipDepth(String.valueOf(v)));
      builder2.setMaxManifoldHeight(this.getMaxManifoldHeight(bi).toString());

      Optional.ofNullable(bi.getAirDraft())
          .ifPresent(v -> builder2.setAirDraftLimitation(String.valueOf(v)));
      Optional.ofNullable(bi.getMaximumLoa()).ifPresent(v -> builder2.setMaxLoa(String.valueOf(v)));
      Optional.ofNullable(bi.getMaximumDwt()).ifPresent(v -> builder2.setMaxDwt(String.valueOf(v)));
      Optional.ofNullable(bi.getMaximumDraft())
          .ifPresent(v -> builder2.setMaxDraft(String.valueOf(v)));

      Optional.ofNullable(bi.getLineDisplacement())
          .ifPresent(v -> builder2.setLineDisplacement(String.valueOf(v)));

      Optional.ofNullable(bi.getHoseConnection()).ifPresent(builder2::setHoseConnection);
      Optional.ofNullable(bi.getUnderKeelClearance()).ifPresent(builder2::setUkc);
      Optional.ofNullable(bi.getBerthDatumDepth())
          .ifPresent(v -> builder2.setBerthDatumDepth(String.valueOf(v)));
      portDetail.addBerthDetails(builder2);
    }
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
      PortInfo savePortInfo = this.portRepository.save(portInfo);
      List<PortInfo> portInfoList = new ArrayList<>();
      portInfoList.add(savePortInfo);
      getPort(portReply, savePortInfo);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      portReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in savePort method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      portReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(portReply.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Single Port Response
   *
   * @param portReply
   * @param portInfo
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
              .filter(v -> v.getMaximumDraft() != null)
              .map(BerthInfo::getMaximumDraft)
              .min(BigDecimal::compareTo);
      if (minDraftOfBerths.isPresent()) {
        portDetail.setMaxDraft(minDraftOfBerths.get().toString());
      }
      Optional<BigDecimal> minAirDraftOfBerths =
          portInfo.getBerthInfoSet().stream()
              .filter(v -> v.getAirDraft() != null)
              .map(BerthInfo::getAirDraft)
              .min(BigDecimal::compareTo);
      if (minAirDraftOfBerths.isPresent()) {
        portDetail.setMaxAirDraft(minAirDraftOfBerths.get().toString());
      }
    }

    Optional.ofNullable(portInfo.getLattitude())
        .ifPresent(item -> portDetail.setLat(String.valueOf(item)));
    Optional.ofNullable(portInfo.getLongitude())
        .ifPresent(item -> portDetail.setLon(String.valueOf(item)));
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
      if (country != null) portInfo.setCountry(country);
    }

    portInfo.setDensitySeaWater(
        isNullOrEmpty(request.getWaterDensity())
            ? null
            : new BigDecimal(request.getWaterDensity()));
    portInfo.setMaxPermissibleDraft(
        isNullOrEmpty(request.getMaxPermissibleDraft())
            ? null
            : new BigDecimal(request.getMaxPermissibleDraft()));
    portInfo.setCode(request.getCode());
    portInfo.setName(request.getName());
    if (request.getId() == 0
        || (portInfo.getTimezone() != null
            && portInfo.getTimezone().getId() != request.getTimezoneId())) {
      Timezone timezone = this.timezoneRepository.getById(request.getTimezoneId());
      if (timezone != null) portInfo.setTimezone(timezone);
    }

    portInfo.setTideHeightTo(
        isNullOrEmpty(request.getTideHeightTo())
            ? null
            : new BigDecimal(request.getTideHeightTo()));
    portInfo.setTideHeightFrom(
        isNullOrEmpty(request.getTideHeightFrom())
            ? null
            : new BigDecimal(request.getTideHeightFrom()));
    portInfo.setLattitude(isNullOrEmpty(request.getLat()) ? null : request.getLat());
    portInfo.setLongitude(isNullOrEmpty(request.getLon()) ? null : request.getLon());
    portInfo.setAmbientTemperature(
        isNullOrEmpty(request.getAmbientTemperature())
            ? null
            : new BigDecimal(request.getAmbientTemperature()));
    portInfo.setIsActive(true);
    Set<BerthInfo> berthInfoList =
        setBerthInformationForThePorts(request.getBerthDetailsList(), portInfo);
    portInfo.setBerthInfoSet(berthInfoList);
  }

  /**
   * set BerthInformation for Ports
   *
   * @param berthDetailsList
   * @param portInfo
   * @return
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
              "Error in Save Port -> Berth Detail get -> portId: "
                  + portInfo.getId()
                  + " berth id: "
                  + berth.getId());
          throw new GenericServiceException(
              "Invalid Berth Id",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        berthResponse = berthOptResponse.get();
      }
      berthResponse.setBerthName(berth.getBerthName());
      berthResponse.setBerthDatumDepth(
          isNullOrEmpty(berth.getBerthDatumDepth())
              ? null
              : new BigDecimal(berth.getBerthDatumDepth()));
      berthResponse.setMaximumDraft(
          isNullOrEmpty(berth.getMaxDraft()) ? null : new BigDecimal(berth.getMaxDraft()));
      berthResponse.setMaxShipDepth(
          isNullOrEmpty(berth.getMaxShipDepth()) ? null : new BigDecimal(berth.getMaxShipDepth()));
      berthResponse.setMaximumDwt(
          isNullOrEmpty(berth.getMaxDwt()) ? null : new BigDecimal(berth.getMaxDwt()));
      berthResponse.setMaximumLoa(
          isNullOrEmpty(berth.getMaxLoa()) ? null : new BigDecimal(berth.getMaxLoa()));
      berthResponse.setUnderKeelClearance(berth.getUkc());
      berthResponse.setIsActive(true);
      berthResponse.setPortInfo(portInfo);
      berthList.add(berthResponse);
    }
    return berthList;
  }

  /**
   * to check strting is null or empty
   *
   * @param value
   * @return
   */
  public Boolean isNullOrEmpty(String value) {
    if (value == null || value.trim().isEmpty()) return true;
    return false;
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
}
