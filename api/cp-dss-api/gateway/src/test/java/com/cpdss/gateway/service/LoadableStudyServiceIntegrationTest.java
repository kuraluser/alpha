/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.CargoGroup;
import com.cpdss.gateway.domain.CommingleCargo;
import com.cpdss.gateway.domain.CommingleCargoResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationRequest;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.VoyageStatusRequest;
import com.cpdss.gateway.domain.VoyageStatusResponse;
import com.cpdss.gateway.repository.UsersRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest(
    properties = {
      "grpc.server.inProcessName=test", // Enable inProcess server
      "grpc.server.port=-1", // Disable external server
      "grpc.client.loadableStudyService.address=in-process:test",
      "grpc.client.vesselInfoService.address=in-process:test" // Configure the client to connect to
      // the inProcess server
    })
@SpringJUnitConfig(
    classes = {
      LoadableStudyServiceIntegrationConfiguration.class,
      VesselInfoImplForLoadableStudyServiceIntegration.class
    })
@DirtiesContext
class LoadableStudyServiceIntegrationTest {

  private static final String HTTP_STATUS_200 = "200";
  private static final String SUCCESS = "SUCCESS";

  @Autowired private LoadableStudyService loadableStudyService;

  @MockBean private UsersRepository usersRepository;

  @Test
  void testGetCommingleCargo() throws GenericServiceException {
    CommingleCargoResponse response =
        loadableStudyService.getCommingleCargo(Long.valueOf(0), Long.valueOf(0));
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(HTTP_STATUS_200);
  }

  @Test
  void testSaveCommingleCargo() throws GenericServiceException {
    CommingleCargoResponse response =
        loadableStudyService.saveCommingleCargo(Long.valueOf(0), createCommingleCargo());
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(HTTP_STATUS_200);
  }

  private CommingleCargo createCommingleCargo() {
    CommingleCargo commingleCargo = new CommingleCargo();
    commingleCargo.setPurposeId(1L);
    commingleCargo.setSlopOnly(true);
    List<Long> preferredTanks = new ArrayList<>();
    preferredTanks.add(1L);
    commingleCargo.setPreferredTanks(preferredTanks);
    CargoGroup cargoGroup = new CargoGroup();
    cargoGroup.setCargo1Id(1L);
    cargoGroup.setCargo1pct(new BigDecimal("10"));
    cargoGroup.setCargo2Id(1L);
    cargoGroup.setCargo2pct(new BigDecimal("10"));
    cargoGroup.setQuantity(new BigDecimal("20"));
    List<CargoGroup> cargoGroups = new ArrayList<>();
    cargoGroups.add(cargoGroup);
    commingleCargo.setCargoGroups(cargoGroups);
    return commingleCargo;
  }

  @Test
  void testSavePortRotationList() throws GenericServiceException {
    PortRotationResponse response =
        loadableStudyService.savePortRotationList(createPortRotationListRequest(), "");
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(HTTP_STATUS_200);
  }

  private PortRotationRequest createPortRotationListRequest() {
    PortRotationRequest portRotationRequest = new PortRotationRequest();
    portRotationRequest.setLoadableStudyId(1L);
    List<PortRotation> portRotationList = new ArrayList<>();
    PortRotation request = new PortRotation();
    request.setId(2L);
    request.setDistanceBetweenPorts(new BigDecimal("0"));
    request.setEta(LocalDateTime.now().toString());
    request.setEtd(request.getEta());
    request.setLayCanFrom(LocalDate.now().toString());
    request.setLayCanTo(request.getLayCanFrom());
    request.setLoadableStudyId(1L);
    portRotationList.add(request);
    portRotationRequest.setPortList(portRotationList);
    return portRotationRequest;
  }

  @Test
  void testGetVoyageStatus() throws GenericServiceException {
    VoyageStatusResponse response =
        loadableStudyService.getVoyageStatus(
            createVoyageStatusRequest(),
            Long.valueOf(1),
            Long.valueOf(839),
            Long.valueOf(1453),
            Long.valueOf(4),
            "");
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(HTTP_STATUS_200);
  }

  private VoyageStatusRequest createVoyageStatusRequest() {
    VoyageStatusRequest voyageStatusRequest = new VoyageStatusRequest();
    voyageStatusRequest.setPortOrder(Long.valueOf(4));
    voyageStatusRequest.setOperationType("ARR");
    return voyageStatusRequest;
  }
}
