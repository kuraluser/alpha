/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceBlockingStub;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply;
import com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest;
import com.cpdss.common.generated.PortInfo.PortEmptyRequest;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfo.Timezone;
import com.cpdss.common.generated.PortInfo.TimezoneResponse;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.cargomaster.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** PortInfoService - service class for cargo and port info related operations */
@Service
@Log4j2
public class CargoPortInfoService {

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @GrpcClient("cargoInfoService")
  private CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  /**
   * Retrieves the port information from port master for cargo
   *
   * @param cargoId
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public PortsResponse getPortsByCargoId(Long cargoId, HttpHeaders headers)
      throws GenericServiceException {
    PortsResponse portsResponse = new PortsResponse();
    // Retrieve port information from port master
    GetPortInfoByCargoIdRequest portRequest =
        GetPortInfoByCargoIdRequest.newBuilder().setCargoId(cargoId).build();
    GetPortInfoByCargoIdReply portReply =
        portInfoServiceBlockingStub.getPortInfoByCargoId(portRequest);
    if (portReply != null
        && portReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      // set response status irrespective of whether port details are available
      CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
      commonSuccessResponse.setStatus(SUCCESS);
      portsResponse.setResponseStatus(commonSuccessResponse);
      buildPortsResponse(portsResponse, portReply);
    } else {
      throw new GenericServiceException(
          "Error in calling port service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portsResponse;
  }

  /**
   * @param portsResponse
   * @param portReply
   * @return
   */
  private PortsResponse buildPortsResponse(
      PortsResponse portsResponse, GetPortInfoByCargoIdReply portReply) {
    if (portReply != null && !portReply.getPortsList().isEmpty()) {
      List<Port> portList = new ArrayList<>();
      portReply
          .getPortsList()
          .forEach(
              portDetail -> {
                Port port = new Port();
                port.setId(portDetail.getId());
                port.setName(portDetail.getName());
                portList.add(port);
              });
      portsResponse.setPorts(portList);
    }
    return portsResponse;
  }

  /**
   * Retrieves the ports information from port master
   *
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public PortsResponse getPorts(HttpHeaders headers) throws GenericServiceException {
    PortsResponse portsResponse = new PortsResponse();
    // Retrieve port information from port master
    PortRequest portRequest = PortRequest.newBuilder().build();
    PortReply portReply = portInfoServiceBlockingStub.getPortInfo(portRequest);
    if (portReply != null
        && portReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
      commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
      portsResponse.setResponseStatus(commonSuccessResponse);
      buildPortsResponse(portsResponse, portReply);
    } else {
      throw new GenericServiceException(
          "Error in calling port service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portsResponse;
  }

  /**
   * @param portsResponse
   * @param portReply
   * @return
   */
  private PortsResponse buildPortsResponse(PortsResponse portsResponse, PortReply portReply) {
    if (portReply != null && !portReply.getPortsList().isEmpty()) {
      List<Port> portList = new ArrayList<>();
      portReply
          .getPortsList()
          .forEach(
              portDetail -> {
                Port port = new Port();
                port.setId(portDetail.getId());
                port.setName(portDetail.getName());
                port.setCode(portDetail.getCode());
                port.setWaterDensity(
                    !portDetail.getWaterDensity().isEmpty()
                        ? new BigDecimal(portDetail.getWaterDensity())
                        : null);
                port.setMaxDraft(
                    !portDetail.getMaxDraft().isEmpty()
                        ? new BigDecimal(portDetail.getMaxDraft())
                        : null);
                port.setMaxAirDraft(
                    !portDetail.getMaxAirDraft().isEmpty()
                        ? new BigDecimal(portDetail.getMaxAirDraft())
                        : null);
                port.setTimezone(portDetail.getTimezone());
                port.setTimezoneOffsetVal(portDetail.getTimezoneOffsetVal());
                port.setTimezoneAbbreviation(portDetail.getTimezoneAbbreviation());
                portList.add(port);
              });
      portsResponse.setPorts(portList);
    }
    return portsResponse;
  }

  /**
   * Retrieves the cargo information from cargo master
   *
   * @param headers
   * @return
   * @throws GenericServiceException
   */
  public CargosResponse getCargos(HttpHeaders headers) throws GenericServiceException {
    CargosResponse cargosResponse = new CargosResponse();
    // Retrieve cargo information from cargo master
    CargoRequest cargoRequest = CargoRequest.newBuilder().build();
    CargoReply cargoReply = cargoInfoServiceBlockingStub.getCargoInfo(cargoRequest);
    if (cargoReply != null
        && cargoReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
      CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
      commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
      cargosResponse.setResponseStatus(commonSuccessResponse);
      buildCargoResponse(cargosResponse, cargoReply);
    } else {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargosResponse;
  }

  /**
   * @param cargosResponse
   * @param cargoReply
   * @return
   */
  private CargosResponse buildCargoResponse(CargosResponse cargosResponse, CargoReply cargoReply) {
    if (cargoReply != null && !cargoReply.getCargosList().isEmpty()) {
      List<Cargo> cargoList = new ArrayList<>();
      cargoReply
          .getCargosList()
          .forEach(
              cargoDetail -> {
                Cargo cargo = new Cargo();
                cargo.setId(cargoDetail.getId());
                cargo.setApi(cargoDetail.getApi());
                cargo.setAbbreviation(cargoDetail.getAbbreviation());
                cargo.setName(cargoDetail.getCrudeType());
                cargoList.add(cargo);
              });
      cargosResponse.setCargos(cargoList);
    }
    return cargosResponse;
  }

  /**
   * Grpc Call to port-info, on getTimezone service.
   *
   * @return {@link com.cpdss.gateway.domain.TimezoneRestResponse}
   * @throws GenericServiceException
   */
  public TimezoneRestResponse getTimezones() throws GenericServiceException {
    PortEmptyRequest request = PortEmptyRequest.newBuilder().build();
    TimezoneResponse grpcRep = portInfoServiceBlockingStub.getTimezone(request);
    if (grpcRep != null
        && grpcRep.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(grpcRep.getResponseStatus().getStatus())) {
      TimezoneRestResponse restResponse = new TimezoneRestResponse();
      restResponse.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), ""));
      buildTimezoneResponse(restResponse, grpcRep);
      log.info("Fetching all time zones, size {}", grpcRep.getTimezonesCount());
      return restResponse;
    } else {
      log.error("Failed to fetch timezones from port-info");
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  private TimezoneRestResponse buildTimezoneResponse(
      TimezoneRestResponse tzRep, TimezoneResponse tzRlp) {
    List<com.cpdss.gateway.domain.Timezone> list = new ArrayList<>();
    for (Timezone prOb : tzRlp.getTimezonesList()) {
      list.add(
          new com.cpdss.gateway.domain.Timezone(
              prOb.getId(), prOb.getTimezone(), prOb.getOffsetValue(), prOb.getAbbreviation()));
    }
    tzRep.setTimezones(list);
    return tzRep;
  }

  /**
   * Fetching all master cargos with Pagination, Sorting and Filtering
   *
   * @param page
   * @param pageSize
   * @param sortBy
   * @param orderBy
   * @param params
   * @param correlationIdHeader
   * @return cargosResponse
   * @throws GenericServiceException
   */
  public CargosDetailedResponse getCargosDetailed(
      int page,
      int pageSize,
      String sortBy,
      String orderBy,
      Map<String, String> params,
      String correlationIdHeader)
      throws GenericServiceException {
    CargosDetailedResponse cargosResponse = new CargosDetailedResponse();
    // Retrieve cargo information from cargo master
    CargoRequest.Builder cargoRequestBuilder = CargoRequest.newBuilder();
    cargoRequestBuilder.setPage(page);
    cargoRequestBuilder.setPageSize(pageSize);
    cargoRequestBuilder.setSortBy(sortBy);
    cargoRequestBuilder.setOrderBy(orderBy);
    cargoRequestBuilder.setCompanyId(1L);
    params.forEach(
        (key, value) ->
            cargoRequestBuilder.addParam(
                CargoInfo.Param.newBuilder().setKey(key).setValue(value).build()));
    CargoInfo.CargoDetailedReply cargoReply =
        cargoInfoServiceBlockingStub.getCargoInfoDetailed(cargoRequestBuilder.build());
    if (cargoReply != null
        && SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
      // Get all cargo port mappings
      List<PortInfo.CargoPortMappingDetail> cargoPortMappings = this.getAllPortCargoMappings();
      CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
      commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
      commonSuccessResponse.setCorrelationId(correlationIdHeader);
      cargosResponse.setResponseStatus(commonSuccessResponse);
      buildCargoDetailedResponse(cargosResponse, cargoReply, cargoPortMappings);
    } else {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargosResponse;
  }

  /**
   * Fetching all cargo port mappings
   *
   * @return cargoPortReply.getPortsList()
   * @throws GenericServiceException
   */
  private List<PortInfo.CargoPortMappingDetail> getAllPortCargoMappings()
      throws GenericServiceException {
    PortInfo.CargoPortRequest cargoPortRequest = PortInfo.CargoPortRequest.newBuilder().build();
    PortInfo.CargoPortReply cargoPortReply =
        this.portInfoServiceBlockingStub.getAllCargoPortMapping(cargoPortRequest);
    System.out.println(cargoPortReply.getPorts(0));
    if (cargoPortReply == null
        || cargoPortReply.getResponseStatus() == null
        || !SUCCESS.equalsIgnoreCase(cargoPortReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in retrieving all cargo mappings",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoPortReply.getPortsList();
  }

  /**
   * Building CargoDetailedResponse
   *
   * @param cargosResponse
   * @param cargoReply
   * @param cargoPortMappings
   */
  private void buildCargoDetailedResponse(
      CargosDetailedResponse cargosResponse,
      CargoInfo.CargoDetailedReply cargoReply,
      List<PortInfo.CargoPortMappingDetail> cargoPortMappings) {
    if (cargoReply != null && !cargoReply.getCargosList().isEmpty()) {
      List<CargoDetailed> cargoList = new ArrayList<>();
      cargoReply
          .getCargosList()
          .forEach(
              cargo -> {
                CargoDetailed cargoDetail = new CargoDetailed();
                cargoDetail.setAbbreviation(
                    cargo.getAbbreviation() == null ? "" : cargo.getAbbreviation());
                cargoDetail.setApi(cargo.getApi() == null ? "" : cargo.getApi());
                cargoDetail.setId(cargo.getId());
                cargoDetail.setName(cargo.getName());
                cargoDetail.setBenzene(cargo.getBenzene());
                cargoDetail.setCloudPoint(cargo.getCloudPoint());
                cargoDetail.setTemp(cargo.getTemp());
                cargoDetail.setReidVapourPressure(cargo.getReidVapourPressure());
                cargoDetail.setGas(cargo.getGas());
                cargoDetail.setTotalWax(cargo.getTotalWax());
                cargoDetail.setPourPoint(cargo.getPourPoint());
                cargoDetail.setCloudPoint(cargo.getCloudPoint());
                cargoDetail.setViscosity(cargo.getViscosity());
                cargoDetail.setCowCodes(cargo.getCowCodes());
                cargoDetail.setHydrogenSulfideOil(cargo.getHydrogenSulfideOil());
                cargoDetail.setHydrogenSulfideVapour(cargo.getHydrogenSulfideVapour());
                cargoDetail.setSpecialInstrictionsRemark(cargo.getSpecialInstrictionsRemark());
                // adding ports that are mapped to this cargo
                List<PortInfo.CargoPortMappingDetail> cargoPorts =
                    cargoPortMappings.stream()
                        .filter(
                            cargoPortMappingDetail ->
                                cargoPortMappingDetail.getCargoId() == cargo.getId())
                        .collect(Collectors.toList());
                List<CargoPortMapping> cargoPortMappingList = this.buildMappings(cargoPorts);
                cargoDetail.setLoadingInformation(cargoPortMappingList);
                cargoList.add(cargoDetail);
              });
      cargosResponse.setCargos(cargoList);
      cargosResponse.setTotalElements(cargoReply.getTotalElements());
    }
  }

  /**
   * Build CargoPortMappingList
   *
   * @param cargoPorts
   * @return cargoPortMappingList
   */
  private List<CargoPortMapping> buildMappings(List<PortInfo.CargoPortMappingDetail> cargoPorts) {
    List<CargoPortMapping> cargoPortMappingList = new ArrayList<>();
    cargoPorts.stream()
        .forEach(
            cargoPort -> {
              CargoPortMapping cargoPortMapping = new CargoPortMapping();
              cargoPortMapping.setId(cargoPort.getId());
              CargoPort port = new CargoPort();
              port.setId(cargoPort.getPortId());
              port.setCode(cargoPort.getPortCode());
              port.setName(cargoPort.getPortName());
              port.setMaxAirDraft(cargoPort.getMaxAirDraft());
              port.setMaxDraft(cargoPort.getMaxDraft());
              port.setWaterDensity(cargoPort.getWaterDensity());
              cargoPortMapping.setPort(port);
              cargoPortMappingList.add(cargoPortMapping);
            });
    return cargoPortMappingList;
  }

  public CargoDetailedResponse getCargosDetailedById(HttpHeaders headers, Long cargoId)
      throws GenericServiceException {
    CargoDetailedResponse cargoResponse = new CargoDetailedResponse();
    // Retrieve cargo information from cargo master
    CargoRequest cargoRequest = CargoRequest.newBuilder().setCargoId(cargoId).build();
    CargoInfo.CargoByIdDetailedReply cargoReply =
        cargoInfoServiceBlockingStub.getCargoInfoDetailedById(cargoRequest);
    if (cargoReply != null
        && cargoReply.getResponseStatus() != null
        && SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
      System.out.println(cargoReply.getCargo());
      // Get all cargo port mappings
      List<PortInfo.CargoPortMappingDetail> cargoPortMappings =
          this.getAllPortCargoMappingsById(cargoId);
      System.out.println(cargoPortMappings.size());
      CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
      commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
      cargoResponse.setResponseStatus(commonSuccessResponse);
      cargoResponse = buildCargoByIdDetailedResponse(cargoResponse, cargoReply, cargoPortMappings);
    } else {
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoResponse;
  }

  private CargoDetailedResponse buildCargoByIdDetailedResponse(
      CargoDetailedResponse cargoResponse,
      CargoInfo.CargoByIdDetailedReply cargoReply,
      List<PortInfo.CargoPortMappingDetail> cargoPortMappings) {
    CargoInfo.CargoDetailed cargo = cargoReply.getCargo();
    CargoDetailed cargoDetail = new CargoDetailed();
    cargoDetail.setAbbreviation(cargo.getAbbreviation() == null ? "" : cargo.getAbbreviation());
    cargoDetail.setApi(cargo.getApi() == null ? "" : cargo.getApi());
    cargoDetail.setId(cargo.getId());
    cargoDetail.setName(cargo.getName());
    cargoDetail.setBenzene(cargo.getBenzene());
    cargoDetail.setCloudPoint(cargo.getCloudPoint());
    cargoDetail.setTemp(cargo.getTemp());
    cargoDetail.setReidVapourPressure(cargo.getReidVapourPressure());
    cargoDetail.setGas(cargo.getGas());
    cargoDetail.setTotalWax(cargo.getTotalWax());
    cargoDetail.setPourPoint(cargo.getPourPoint());
    cargoDetail.setCloudPoint(cargo.getCloudPoint());
    cargoDetail.setViscosity(cargo.getViscosity());
    cargoDetail.setCowCodes(cargo.getCowCodes());
    cargoDetail.setHydrogenSulfideOil(cargo.getHydrogenSulfideOil());
    cargoDetail.setHydrogenSulfideVapour(cargo.getHydrogenSulfideVapour());
    cargoDetail.setSpecialInstrictionsRemark(cargo.getSpecialInstrictionsRemark());
    List<CargoPortMapping> mappings = this.buildMappings(cargoPortMappings);
    cargoDetail.setLoadingInformation(mappings);
    cargoResponse.setCargo(cargoDetail);
    return cargoResponse;
  }

  private List<PortInfo.CargoPortMappingDetail> getAllPortCargoMappingsById(Long cargoId)
      throws GenericServiceException {
    PortInfo.CargoPortRequest cargoPortRequest =
        PortInfo.CargoPortRequest.newBuilder().setCargoId(cargoId).build();
    PortInfo.CargoPortReply cargoPortReply =
        this.portInfoServiceBlockingStub.getAllCargoPortMappingById(cargoPortRequest);
    if (cargoPortReply == null
        || cargoPortReply.getResponseStatus() == null
        || !SUCCESS.equalsIgnoreCase(cargoPortReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in retrieving all cargo mappings",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return cargoPortReply.getPortsList();
  }
}
