/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceBlockingStub;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
              prOb.getId(), prOb.getTimezone(), prOb.getOffsetValue()));
    }
    tzRep.setTimezones(list);
    return tzRep;
  }
}
