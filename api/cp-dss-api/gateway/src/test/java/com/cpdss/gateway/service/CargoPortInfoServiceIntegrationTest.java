/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.CargosResponse;
import com.cpdss.gateway.domain.PortsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest(
    properties = {
      "grpc.server.inProcessName=test", // Enable inProcess server
      "grpc.server.port=-1", // Disable external server
      "grpc.client.cargoInfoService.address=in-process:test", // Configure the client to connect to
      // the inProcess server
      "grpc.client.portInfoService.address=in-process:test"
    })
@SpringJUnitConfig(classes = {CargoPortInfoServiceIntegrationConfiguration.class})
@DirtiesContext
class CargoPortInfoServiceIntegrationTest {

  private static final String HTTP_STATUS_200 = "200";
  private static final String SUCCESS = "SUCCESS";

  @Autowired private CargoPortInfoService cargoPortInfoService;

  @Test
  void testGetPortsByCargoId() throws GenericServiceException {
    CargoInfoImplForCargoInfoServiceIntegration.testIteration = 0;
    HttpHeaders httpHeaders = new HttpHeaders();
    PortsResponse response = cargoPortInfoService.getPortsByCargoId(Long.valueOf(0), httpHeaders);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(SUCCESS);
  }

  @Test
  void testGetPortsByCargoIdWithException() throws GenericServiceException {
    CargoInfoImplForCargoInfoServiceIntegration.testIteration = 0;
    HttpHeaders httpHeaders = new HttpHeaders();
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              cargoPortInfoService.getPortsByCargoId(1L, httpHeaders);
            });
    String expectedMessage = "Error in calling port service";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testGetPorts() throws GenericServiceException {
    PortInfoImplForCargoInfoServiceIntegration.testIteration = 0;
    HttpHeaders httpHeaders = new HttpHeaders();
    PortsResponse response = cargoPortInfoService.getPorts(httpHeaders);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(HTTP_STATUS_200);
  }

  @Test
  void testGetPortsWithException() throws GenericServiceException {
    PortInfoImplForCargoInfoServiceIntegration.testIteration = 1;
    HttpHeaders httpHeaders = new HttpHeaders();
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              cargoPortInfoService.getPorts(httpHeaders);
            });
    String expectedMessage = "Error in calling port service";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testGetCargos() throws GenericServiceException {
    CargoInfoImplForCargoInfoServiceIntegration.testIteration = 0;
    HttpHeaders httpHeaders = new HttpHeaders();
    CargosResponse response = cargoPortInfoService.getCargos(httpHeaders);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(HTTP_STATUS_200);
  }

  @Test
  void testGetCargosForException() throws GenericServiceException {
    CargoInfoImplForCargoInfoServiceIntegration.testIteration = 1;
    HttpHeaders httpHeaders = new HttpHeaders();
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              cargoPortInfoService.getCargos(httpHeaders);
            });
    String expectedMessage = "Error in calling cargo service";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }
}
