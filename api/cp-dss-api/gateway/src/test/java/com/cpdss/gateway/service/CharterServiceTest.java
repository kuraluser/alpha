/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {CharterService.class, PortInfoService.class})
public class CharterServiceTest {

  @Autowired CharterService charterService;

  @MockBean
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoServiceBlockingStub;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testGetAllCharters() {
    String correlationId = "1";

    Mockito.when(vesselInfoServiceBlockingStub.getAllCharterDetails(Mockito.any()))
        .thenReturn(getCharters());
    Mockito.when(portInfoServiceBlockingStub.getAllCountries(Mockito.any()))
        .thenReturn(getAllCountries());

    ReflectionTestUtils.setField(
        charterService, "vesselInfoServiceBlockingStub", this.vesselInfoServiceBlockingStub);
    ReflectionTestUtils.setField(
        charterService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);

    try {
      /** method params* */
      int pageNo = 0;
      int pageSize = 1;
      String sortBy = "id";
      String orderBy = "asc";
      Map<String, String> params = new HashMap<>();
      String correlationIdHeader = correlationId;
      /** method params* */
      var response =
          this.charterService.getCharterDetails(
              pageNo, pageSize, sortBy, orderBy, params, correlationIdHeader);

      assertEquals(1L, response.getCharterDetails().get(0).getId());
    } catch (GenericServiceException exception) {
      exception.printStackTrace();
    }
  }

  @Test
  void testgetAllCharterDetailsException() {
    String correlationId = "1";
    Mockito.when(vesselInfoServiceBlockingStub.getAllCharterDetails(Mockito.any()))
        .thenReturn(getCharterNS());
    ReflectionTestUtils.setField(
        charterService, "vesselInfoServiceBlockingStub", this.vesselInfoServiceBlockingStub);
    try {
      /** method params* */
      int pageNo = 0;
      int pageSize = 1;
      String sortBy = "id";
      String orderBy = "asc";
      Map<String, String> params = new HashMap<>();
      String correlationIdHeader = correlationId;
      /** method params* */
      var response =
          this.charterService.getCharterDetails(
              pageNo, pageSize, sortBy, orderBy, params, correlationIdHeader);

      assertEquals(FAILED, response.getResponseStatus().getStatus());
    } catch (GenericServiceException exception) {
      exception.printStackTrace();
    }
  }

  private VesselInfo.CharterDetailedReply getCharters() {

    List<VesselInfo.CharterDetailed> list = new ArrayList<>();

    VesselInfo.CharterVesselMappingDetail charterVesselMappingDetail =
        VesselInfo.CharterVesselMappingDetail.newBuilder().setCharterId(1L).setVesselId(1L).build();

    ArrayList<VesselInfo.CharterVesselMappingDetail> charterVesselMappingDetailList =
        new ArrayList<>();
    charterVesselMappingDetailList.add(charterVesselMappingDetail);

    VesselInfo.CharterDetailed charterDetailed =
        VesselInfo.CharterDetailed.newBuilder()
            .setId(1L)
            .setCharterName("CharterName1")
            .setCharterTypeId(1L)
            .setCharterTypeName("Voyage")
            .setCharterCompanyId(1L)
            .setCharterCompanyName("CharterCompany1")
            .setCharterCountryId(5L)
            .setCharterCountryName("AUSTRALIA")
            .build();

    list.add(charterDetailed);

    VesselInfo.CharterDetailedReply reply =
        VesselInfo.CharterDetailedReply.newBuilder()
            .addAllCharterDetails(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private VesselInfo.CharterDetailedReply getCharterNS() {
    VesselInfo.CharterDetailedReply reply =
        VesselInfo.CharterDetailedReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }

  private PortInfo.CountryReply getAllCountries() {
    List<PortInfo.Country> list = new ArrayList<>();
    PortInfo.Country country =
        PortInfo.Country.newBuilder().setId(5L).setCountryName("AUSTRALIA").build();
    list.add(country);
    PortInfo.CountryReply reply =
        PortInfo.CountryReply.newBuilder()
            .addAllCountries(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }
}
