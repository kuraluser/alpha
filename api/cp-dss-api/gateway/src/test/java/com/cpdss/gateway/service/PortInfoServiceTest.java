/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {PortInfoService.class})
public class PortInfoServiceTest {

  @Autowired private PortInfoService portInfoService;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testGetBerthInfoByPortId() {
    Long id = 1L;
    Mockito.when(this.portInfoServiceBlockingStub.getBerthDetailsByPortId(Mockito.any()))
        .thenReturn(getBIR());
    ReflectionTestUtils.setField(
        portInfoService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    var response = this.portInfoService.getBerthInfoByPortId(id);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  private PortInfo.BerthInfoResponse getBIR() {
    PortInfo.BerthInfoResponse response =
        PortInfo.BerthInfoResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testGetBerthInfoByPortIdNullReturn() {
    Long id = 1L;
    Mockito.when(this.portInfoServiceBlockingStub.getBerthDetailsByPortId(Mockito.any()))
        .thenReturn(getBIRNS());
    ReflectionTestUtils.setField(
        portInfoService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    var response = this.portInfoService.getBerthInfoByPortId(id);
  }

  private PortInfo.BerthInfoResponse getBIRNS() {
    PortInfo.BerthInfoResponse response = PortInfo.BerthInfoResponse.newBuilder().build();
    return response;
  }

  @Test
  void testGetPortInformationByPortId() {
    Long portId = 1L;
    String correlationId = "1";
    Mockito.when(portInfoServiceBlockingStub.getPortInfoByPortIds(Mockito.any()))
        .thenReturn(getPR());
    ReflectionTestUtils.setField(
        portInfoService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.portInfoService.getPortInformationByPortId(portId, correlationId);
      assertEquals(1L, response.getPortDetails().getCountryId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPR() {
    List<PortInfo.BerthDetail> list1 = new ArrayList<>();
    PortInfo.BerthDetail berthDetail =
        PortInfo.BerthDetail.newBuilder()
            .setId(1L)
            .setBerthName("1")
            .setBerthDatumDepth("1")
            .setMaxDwt("1")
            .setMaxManifoldHeight("1")
            .setMaxShipDepth("1")
            .setPortId(1L)
            .setRegulationAndRestriction("1")
            .setMaxLoa("1")
            .setUkc("1")
            .build();
    list1.add(berthDetail);
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder()
            .addAllBerthDetails(list1)
            .setMaxPermissibleDraft("1")
            .setCode("1")
            .setId(1L)
            .setName("1")
            .setTimezone("1")
            .setTimezoneId(1L)
            .setTimezoneAbbreviation("1")
            .setTimezoneOffsetVal("1")
            .setTideHeightFrom("1")
            .setTideHeightTo("1")
            .setLat("1")
            .setLon("1")
            .setCountryId(1L)
            .setCountryName("1")
            .setWaterDensity("1")
            .build();
    list.add(portDetail);
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetPortInformationByPortIdException() {
    Long portId = 1L;
    String correlationId = "1";
    Mockito.when(portInfoServiceBlockingStub.getPortInfoByPortIds(Mockito.any()))
        .thenReturn(getPRNS());
    ReflectionTestUtils.setField(
        portInfoService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.portInfoService.getPortInformationByPortId(portId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPRNS() {
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("200").build())
            .build();
    return reply;
  }

  @Test
  void testgetAllCountrys() {
    String correlationId = "1";
    Mockito.when(portInfoServiceBlockingStub.getAllCountries(Mockito.any())).thenReturn(getCR());
    ReflectionTestUtils.setField(
        portInfoService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.portInfoService.getAllCountrys(correlationId);
      assertEquals(1L, response.getCountrys().get(0).getId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.CountryReply getCR() {
    List<PortInfo.Country> list = new ArrayList<>();
    PortInfo.Country country = PortInfo.Country.newBuilder().setId(1L).setCountryName("1").build();
    list.add(country);
    PortInfo.CountryReply reply =
        PortInfo.CountryReply.newBuilder()
            .addAllCountries(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testgetAllCountrysException() {
    String correlationId = "1";
    Mockito.when(portInfoServiceBlockingStub.getAllCountries(Mockito.any())).thenReturn(getCRNS());
    ReflectionTestUtils.setField(
        portInfoService, "portInfoServiceBlockingStub", this.portInfoServiceBlockingStub);
    try {
      var response = this.portInfoService.getAllCountrys(correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.CountryReply getCRNS() {
    PortInfo.CountryReply reply =
        PortInfo.CountryReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }
}
