/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.PortInfo.PortEmptyRequest;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfo.TimezoneResponse;
import com.cpdss.portinfo.entity.*;
import com.cpdss.portinfo.repository.*;
import com.google.protobuf.Empty;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Test class for methods related to port info */
@SpringJUnitConfig(
    classes = {
      PortInfoService.class,
    })
public class PortInfoServiceTest {
  @Autowired private PortInfoService portService;

  @MockBean private PortInfoRepository portRepository;
  @MockBean private TimezoneRepository timezoneRepository;
  @MockBean private CargoPortMappingRepository cargoPortMappingRepository;
  @MockBean private CountryRepository countryRepository;
  @MockBean BerthInfoRepository berthInfoRepository;
  @MockBean BerthManifoldRepository berthManifoldRepository;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testGetPortInfo() {
    PortRequest portRequest = PortRequest.newBuilder().setLoadableStudyId(1L).build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();

    when(portRepository.findAllByOrderByName()).thenReturn(getPortInfoList());

    portService.getPortInfo(portRequest, responseObserver);
    List<PortReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoWithException() {
    PortRequest portRequest = PortRequest.newBuilder().setLoadableStudyId(1L).build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();

    when(portRepository.findAllByOrderByName()).thenThrow(new RuntimeException("1"));

    portService.getPortInfo(portRequest, responseObserver);
    List<PortReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<com.cpdss.portinfo.domain.PortInfo> getPortInfoDomainList() {
    List<com.cpdss.portinfo.domain.PortInfo> portList = new ArrayList<>();
    com.cpdss.portinfo.domain.PortInfo portInfo = new com.cpdss.portinfo.domain.PortInfo(1l, "1");
    portList.add(portInfo);
    return portList;
  }

  @Test
  void testGetPortInfoByCargoId() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request =
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest.newBuilder()
            .setCargoId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply> responseObserver =
        StreamRecorder.create();

    when(this.cargoPortMappingRepository.getPortsInfo(anyLong()))
        .thenReturn(getPortInfoDomainList());

    portService.getPortInfoByCargoId(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoByCargoIdWithException() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request =
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest.newBuilder()
            .setCargoId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply> responseObserver =
        StreamRecorder.create();

    when(this.cargoPortMappingRepository.getPortsInfo(anyLong()))
        .thenThrow(new RuntimeException("1"));

    portService.getPortInfoByCargoId(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoByPortIds() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request =
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
            .addAllId(Arrays.asList(1l))
            .build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();

    when(portRepository.findByIdInAndIsActive(anyList(), anyBoolean()))
        .thenReturn(getPortInfoList());

    portService.getPortInfoByPortIds(request, responseObserver);
    List<PortReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoByPortIdsWithException() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request =
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
            .addAllId(Arrays.asList(1l))
            .build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();

    when(portRepository.findByIdInAndIsActive(anyList(), anyBoolean()))
        .thenThrow(new RuntimeException("1"));

    portService.getPortInfoByPortIds(request, responseObserver);
    List<PortReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetTimezone() {
    com.cpdss.common.generated.PortInfo.PortEmptyRequest request =
        PortEmptyRequest.newBuilder().build();
    StreamRecorder<TimezoneResponse> responseObserver = StreamRecorder.create();

    when(timezoneRepository.findAll()).thenReturn(getTimezoneList());

    portService.getTimezone(request, responseObserver);
    List<TimezoneResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetTimezoneWithException() {
    com.cpdss.common.generated.PortInfo.PortEmptyRequest request =
        PortEmptyRequest.newBuilder().build();
    StreamRecorder<TimezoneResponse> responseObserver = StreamRecorder.create();

    when(timezoneRepository.findAll()).thenThrow(new RuntimeException("1"));

    portService.getTimezone(request, responseObserver);
    List<TimezoneResponse> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoByPaging() {
    com.cpdss.common.generated.PortInfo.PortRequestWithPaging request =
        com.cpdss.common.generated.PortInfo.PortRequestWithPaging.newBuilder().build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();
    List<Object[]> objectArray = new ArrayList<>();
    Object[] array = new Object[2];
    array[0] = new BigInteger("1");
    array[1] = "1";
    objectArray.add(array);

    when(portRepository.findPortsIdAndNames()).thenReturn(objectArray);

    portService.getPortInfoByPaging(request, responseObserver);
    List<PortReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoByPagingWithException() {
    com.cpdss.common.generated.PortInfo.PortRequestWithPaging request =
        com.cpdss.common.generated.PortInfo.PortRequestWithPaging.newBuilder().build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();

    when(portRepository.findPortsIdAndNames()).thenThrow(new RuntimeException("1"));

    portService.getPortInfoByPaging(request, responseObserver);
    List<PortReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals("FAILURE", replies.get(0).getResponseStatus().getStatus());
  }

  private List<BerthInfo> getBerthInfoList() {
    List<BerthInfo> berthInfoList = new ArrayList<>();
    BerthInfo berthInfo = new BerthInfo();
    berthInfo.setId(1l);
    berthInfo.setPortInfo(getPortInfoList().get(0));
    berthInfo.setMaxShipChannel(new BigDecimal(1));
    berthInfo.setBerthName("1");
    berthInfo.setMaxShipDepth(new BigDecimal(1));
    berthInfo.setMaximumDraft(new BigDecimal(1));
    berthInfo.setLineDisplacement(new BigDecimal(1));
    berthInfo.setAirDraft(new BigDecimal(1));
    berthInfo.setMaximumLoa(new BigDecimal(1));
    berthInfo.setHoseConnection("1");
    berthInfo.setUnderKeelClearance("1");
    berthInfo.setMaximumDwt(new BigDecimal(1));
    berthInfo.setBerthDatumDepth(new BigDecimal(1));
    berthInfo.setIsActive(true);
    berthInfoList.add(berthInfo);
    return berthInfoList;
  }

  @Test
  void testGetBerthDetailsByPortId() {
    com.cpdss.common.generated.PortInfo.PortIdRequest request =
        com.cpdss.common.generated.PortInfo.PortIdRequest.newBuilder().build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.BerthInfoResponse> responseObserver =
        StreamRecorder.create();
    List<BerthManifold> var1 = new ArrayList<>();
    BerthManifold manifold = new BerthManifold();
    manifold.setManifoldHeight(new BigDecimal(1));
    var1.add(manifold);

    when(berthManifoldRepository.findByBerthInfoAndIsActiveTrue(anyLong())).thenReturn(var1);
    when(berthInfoRepository.findAllByPortInfoAndIsActiveTrue(anyLong()))
        .thenReturn(getBerthInfoList());
    when(portRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getPortInfoList().get(0)));

    portService.getBerthDetailsByPortId(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.BerthInfoResponse> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetBerthDetailsByPortIdWithException() {
    com.cpdss.common.generated.PortInfo.PortIdRequest request =
        com.cpdss.common.generated.PortInfo.PortIdRequest.newBuilder().build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.BerthInfoResponse> responseObserver =
        StreamRecorder.create();

    when(portRepository.findByIdAndIsActiveTrue(anyLong())).thenThrow(new RuntimeException("1"));

    portService.getBerthDetailsByPortId(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.BerthInfoResponse> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoByPortIds() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request =
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
            .addAllId(Arrays.asList(1l))
            .build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.CargoInfos> responseObserver =
        StreamRecorder.create();
    List<CargoPortMapping> cargoIds = new ArrayList<>();
    CargoPortMapping portMapping = new CargoPortMapping();
    portMapping.setCargoXId(1l);
    portMapping.setPortInfo(getPortInfoList().get(0));

    when(this.cargoPortMappingRepository.findByportInfo_idIn(anyList())).thenReturn(cargoIds);

    portService.getCargoInfoByPortIds(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.CargoInfos> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoByPortIdsWithException() {
    com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request =
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.newBuilder()
            .addAllId(Arrays.asList(1l))
            .build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.CargoInfos> responseObserver =
        StreamRecorder.create();

    when(this.cargoPortMappingRepository.findByportInfo_idIn(anyList()))
        .thenThrow(new RuntimeException("1"));

    portService.getCargoInfoByPortIds(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.CargoInfos> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingPlanBerthData() {
    com.cpdss.common.generated.PortInfo.BerthIdsRequest request =
        com.cpdss.common.generated.PortInfo.BerthIdsRequest.newBuilder()
            .addAllBerthIds(Arrays.asList(1l))
            .build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData> responseObserver =
        StreamRecorder.create();

    when(berthInfoRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getBerthInfoList().get(0)));

    portService.getLoadingPlanBerthData(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadingPlanBerthDataWithException() {
    com.cpdss.common.generated.PortInfo.BerthIdsRequest request =
        com.cpdss.common.generated.PortInfo.BerthIdsRequest.newBuilder()
            .addAllBerthIds(Arrays.asList(1l))
            .build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData> responseObserver =
        StreamRecorder.create();

    when(berthInfoRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenThrow(new RuntimeException("1"));

    portService.getLoadingPlanBerthData(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData> replies =
        responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetAllCountries() {
    Empty request = Empty.newBuilder().build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.CountryReply> responseObserver =
        StreamRecorder.create();
    List<Object[]> list = new ArrayList<>();
    Object[] array = new Object[2];
    array[0] = 1l;
    array[1] = "1";
    list.add(array);

    when(countryRepository.findCountryIdAndNames()).thenReturn(list);

    portService.getAllCountries(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.CountryReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetAllCountriesWithException() {
    Empty request = Empty.newBuilder().build();
    StreamRecorder<com.cpdss.common.generated.PortInfo.CountryReply> responseObserver =
        StreamRecorder.create();

    when(countryRepository.findCountryIdAndNames()).thenThrow(new RuntimeException("1"));

    portService.getAllCountries(request, responseObserver);
    List<com.cpdss.common.generated.PortInfo.CountryReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<Timezone> getTimezoneList() {
    List<Timezone> timezoneList = new ArrayList<>();
    Timezone timezone = new Timezone();
    timezone.setId(1l);
    timezone.setTimezone("1");
    timezone.setRegion("1");
    timezone.setOffsetValue("1");
    timezone.setAbbreviation("1");
    timezoneList.add(timezone);
    return timezoneList;
  }

  private List<PortInfo> getPortInfoList() {
    List<PortInfo> portList = new ArrayList<>();
    PortInfo portInfo = new PortInfo();
    portInfo.setId(1l);
    portInfo.setName("1");
    portInfo.setCode("1");
    portInfo.setDensitySeaWater(new BigDecimal(1));
    portInfo.setAverageTideHeight(new BigDecimal(1));
    portInfo.setTideHeight(new BigDecimal(1));
    portInfo.setHwTideFrom(new BigDecimal(1));
    portInfo.setHwTideTo(new BigDecimal(1));
    portInfo.setHwTideTimeFrom(LocalTime.now());
    portInfo.setHwTideTimeTo(LocalTime.now());
    portInfo.setLwTideFrom(new BigDecimal(1));
    portInfo.setLwTideTo(new BigDecimal(1));
    portInfo.setLwTideTimeFrom(LocalTime.now());
    portInfo.setLwTideTimeTo(LocalTime.now());
    portInfo.setTimeOfSunrise(LocalTime.now());
    portInfo.setTimeOfSunSet(LocalTime.now());
    portInfo.setControllingDepth(new BigDecimal(1));
    portInfo.setUnderKeelClearance("1");

    portInfo.setTimezone(getTimezoneList().get(0));

    Country country = new Country();
    country.setName("1");
    country.setId(1l);
    portInfo.setCountry(country);

    Set<BerthInfo> berthInfoSet = new HashSet<>();
    BerthInfo berthInfo = new BerthInfo();
    berthInfo.setId(1l);
    berthInfo.setMaxShipChannel(new BigDecimal(1));
    berthInfo.setBerthName("1");
    berthInfo.setPortInfo(portInfo);
    berthInfo.setMaxShipDepth(new BigDecimal(1));
    berthInfo.setMaximumDraft(new BigDecimal(1));
    berthInfo.setLineDisplacement(new BigDecimal(1));
    berthInfo.setAirDraft(new BigDecimal(1));
    berthInfo.setMaximumLoa(new BigDecimal(1));
    berthInfo.setHoseConnection("1");
    berthInfo.setUnderKeelClearance("1");
    berthInfo.setMaximumDwt(new BigDecimal(1));
    berthInfo.setBerthDatumDepth(new BigDecimal(1));
    berthInfo.setIsActive(true);
    berthInfoSet.add(berthInfo);
    portInfo.setBerthInfoSet(berthInfoSet);

    portInfo.setLattitude("1");
    portInfo.setLongitude("1");
    portInfo.setTideHeightFrom(new BigDecimal(1));
    portInfo.setTideHeightTo(new BigDecimal(1));
    portInfo.setMaxPermissibleDraft(new BigDecimal(1));

    portList.add(portInfo);
    return portList;
  }
}
