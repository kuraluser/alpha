/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadableStudyCargoService.class})
public class LoadableStudyCargoServiceTest {

  @Autowired LoadableStudyCargoService loadableStudyCargoService;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @MockBean
  @Qualifier("cargoRedisSyncService")
  private RedisMasterSyncService redisCargoService;

  @MockBean
  @Qualifier("vesselRedisSyncService")
  private RedisMasterSyncService redisVesselService;

  @MockBean
  @Qualifier("portRedisSyncService")
  private RedisMasterSyncService redisPortService;

  @Test
  void testGetAllCargoHistory() {
    Map<String, String> filterParams = new HashMap<>();
    filterParams.put("vesselName", "1");
    filterParams.put("loadingPortName", "1");
    filterParams.put("grade", "1");
    filterParams.put("loadedYear", "1");
    filterParams.put("loadedMonth", "1");
    filterParams.put("loadedDay", "1");
    filterParams.put("api", "1");
    filterParams.put("temperature", "1");
    int page = 1;
    int pageSize = 1;
    String sortBy = "vesselName";
    String orderBy = "asc";
    String fromStartDate = "1";
    String toStartDate = "1";
    Mockito.when(loadableStudyServiceBlockingStub.getAllCargoHistory(Mockito.any()))
        .thenReturn(getCHR());
    ReflectionTestUtils.setField(
        loadableStudyCargoService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadableStudyCargoService.getAllCargoHistory(
              filterParams, page, pageSize, sortBy, orderBy, fromStartDate, toStartDate);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.CargoHistoryReply getCHR() {
    List<LoadableStudy.CargoHistoryDetail> list = new ArrayList<>();
    LoadableStudy.CargoHistoryDetail detail =
        LoadableStudy.CargoHistoryDetail.newBuilder()
            .setVesselId(1L)
            .setLoadingPortId(1L)
            .setCargoId(1L)
            .setLoadedDate("1")
            .setLoadedMonth(1)
            .setLoadedYear(2012)
            .setLoadedDay(23)
            .setApi("1")
            .setTemperature("1")
            .build();
    list.add(detail);
    LoadableStudy.CargoHistoryReply reply =
        LoadableStudy.CargoHistoryReply.newBuilder()
            .setTotal(1L)
            .addAllCargoHistory(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetAllCargoHistoryException() {
    Map<String, String> filterParams = new HashMap<>();
    filterParams.put("vesselName", "1");
    filterParams.put("loadingPortName", "1");
    filterParams.put("grade", "1");
    filterParams.put("loadedYear", "1");
    filterParams.put("loadedMonth", "1");
    filterParams.put("loadedDay", "1");
    filterParams.put("api", "1");
    filterParams.put("temperature", "1");
    int page = 1;
    int pageSize = 1;
    String sortBy = "vesselName";
    String orderBy = "asc";
    String fromStartDate = "1";
    String toStartDate = "1";
    Mockito.when(loadableStudyServiceBlockingStub.getAllCargoHistory(Mockito.any()))
        .thenReturn(getCHRNS());
    ReflectionTestUtils.setField(
        loadableStudyCargoService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadableStudyCargoService.getAllCargoHistory(
              filterParams, page, pageSize, sortBy, orderBy, fromStartDate, toStartDate);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.CargoHistoryReply getCHRNS() {
    LoadableStudy.CargoHistoryReply reply =
        LoadableStudy.CargoHistoryReply.newBuilder()
            .setTotal(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testGetAllCargoHistoryException2() {
    Map<String, String> filterParams = new HashMap<>();
    filterParams.put("vesselName", "1");
    filterParams.put("loadingPortName", "1");
    filterParams.put("grade", "1");
    filterParams.put("loadedYear", "1");
    filterParams.put("loadedMonth", "1");
    filterParams.put("loadedDay", "1");
    filterParams.put("api", "1");
    filterParams.put("temperature", "1");
    int page = 1;
    int pageSize = 1;
    String sortBy = "vesselName";
    String orderBy = "asc";
    String fromStartDate = "1";
    String toStartDate = "1";
    Mockito.when(loadableStudyServiceBlockingStub.getAllCargoHistory(Mockito.any()))
        .thenReturn(getCHRNS1());
    ReflectionTestUtils.setField(
        loadableStudyCargoService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadableStudyCargoService.getAllCargoHistory(
              filterParams, page, pageSize, sortBy, orderBy, fromStartDate, toStartDate);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.CargoHistoryReply getCHRNS1() {
    LoadableStudy.CargoHistoryReply reply =
        LoadableStudy.CargoHistoryReply.newBuilder()
            .setTotal(1L)
            .setResponseStatus(
                Common.ResponseStatus.newBuilder()
                    .setHttpStatusCode(400)
                    .setCode("400")
                    .setStatus(FAILED)
                    .build())
            .build();
    return reply;
  }

  @Test
  void testGetCargoHistoryByPortAndCargo() {
    Long vesselId = 1L;
    Long portId = 1L;
    Long cargoId = 1L;
    Mockito.when(loadableStudyServiceBlockingStub.getCargoHistoryByCargo(Mockito.any()))
        .thenReturn(getLCR());
    ReflectionTestUtils.setField(
        loadableStudyCargoService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadableStudyCargoService.getCargoHistoryByPortAndCargo(vesselId, portId, cargoId);
      assertEquals("1", response.getApi());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LatestCargoReply getLCR() {
    LoadableStudy.LatestCargoReply reply =
        LoadableStudy.LatestCargoReply.newBuilder()
            .setVesselId(1L)
            .setCargoId(1L)
            .setPortId(1L)
            .setApi("1")
            .setTemperature("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetCargoHistoryByPortAndCargoException() {
    Long vesselId = 1L;
    Long portId = 1L;
    Long cargoId = 1L;
    Mockito.when(loadableStudyServiceBlockingStub.getCargoHistoryByCargo(Mockito.any()))
        .thenReturn(getLCRNS());
    ReflectionTestUtils.setField(
        loadableStudyCargoService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadableStudyCargoService.getCargoHistoryByPortAndCargo(vesselId, portId, cargoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LatestCargoReply getLCRNS() {
    LoadableStudy.LatestCargoReply reply =
        LoadableStudy.LatestCargoReply.newBuilder()
            .setVesselId(1L)
            .setCargoId(1L)
            .setPortId(1L)
            .setApi("1")
            .setTemperature("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(FAILED).build())
            .build();
    return reply;
  }

  @Test
  void testGetCargoHistoryByPortAndCargoException1() {
    Long vesselId = 1L;
    Long portId = 1L;
    Long cargoId = 1L;
    Mockito.when(loadableStudyServiceBlockingStub.getCargoHistoryByCargo(Mockito.any()))
        .thenReturn(getLCRNS1());
    ReflectionTestUtils.setField(
        loadableStudyCargoService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.loadableStudyCargoService.getCargoHistoryByPortAndCargo(vesselId, portId, cargoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LatestCargoReply getLCRNS1() {
    LoadableStudy.LatestCargoReply reply =
        LoadableStudy.LatestCargoReply.newBuilder()
            .setVesselId(1L)
            .setCargoId(1L)
            .setPortId(1L)
            .setApi("1")
            .setTemperature("1")
            .setResponseStatus(
                Common.ResponseStatus.newBuilder()
                    .setStatus(FAILED)
                    .setCode("400")
                    .setHttpStatusCode(400)
                    .build())
            .build();
    return reply;
  }
}
