/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.cargoinfo.repository.CargoRepository;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import io.grpc.internal.testing.StreamRecorder;
import java.time.LocalDate;
import java.util.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/** Test class for cargo related methods */
@SpringBootTest(classes = {CargoService.class})
public class CargoServiceTest {
  @Autowired CargoService cargoService;

  @MockBean private CargoRepository cargoRepository;
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILURE = "FAILURE";

  @Test
  void testGetCargoInfo() {
    CargoRequest request = CargoRequest.newBuilder().build();
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.findAll()).thenReturn(getCargoList());

    cargoService.getCargoInfo(request, responseObserver);
    List<CargoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoWithException() {
    CargoRequest request = CargoRequest.newBuilder().build();
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.findAll()).thenThrow(new RuntimeException("1"));

    cargoService.getCargoInfo(request, responseObserver);
    List<CargoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoByPaging() {
    CargoInfo.CargoRequestWithPaging request =
        CargoInfo.CargoRequestWithPaging.newBuilder().setOffset(1l).setLimit(1l).build();
    List<Cargo> cargoList = getCargoList();
    Page<Cargo> cargoPage = new PageImpl<>(cargoList);
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.findAll(any(Pageable.class))).thenReturn(cargoPage);

    cargoService.getCargoInfoByPaging(request, responseObserver);
    List<CargoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoByPagingWithException() {
    CargoInfo.CargoRequestWithPaging request =
        CargoInfo.CargoRequestWithPaging.newBuilder().setOffset(1l).setLimit(1l).build();
    List<Cargo> cargoList = getCargoList();
    Page<Cargo> cargoPage = new PageImpl<>(cargoList);
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("1"));

    cargoService.getCargoInfoByPaging(request, responseObserver);
    List<CargoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoById() {
    CargoRequest request = CargoRequest.newBuilder().build();
    StreamRecorder<CargoInfo.CargoDetailReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.getOne(anyLong())).thenReturn(getCargoList().get(0));

    cargoService.getCargoInfoById(request, responseObserver);
    List<CargoInfo.CargoDetailReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoByIdWithException() {
    CargoRequest request = CargoRequest.newBuilder().build();
    StreamRecorder<CargoInfo.CargoDetailReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.getOne(anyLong())).thenThrow(new RuntimeException("1"));

    cargoService.getCargoInfoById(request, responseObserver);
    List<CargoInfo.CargoDetailReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfosByCargoIds() {
    CargoInfo.CargoListRequest request = CargoInfo.CargoListRequest.newBuilder().build();
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.findByIdIn(anyList())).thenReturn(getCargoList());

    cargoService.getCargoInfosByCargoIds(request, responseObserver);
    List<CargoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfosByCargoIdsWithException() {
    CargoInfo.CargoListRequest request = CargoInfo.CargoListRequest.newBuilder().build();
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();

    when(cargoRepository.findByIdIn(anyList())).thenThrow(new RuntimeException("1"));

    cargoService.getCargoInfosByCargoIds(request, responseObserver);
    List<CargoReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(strings = {"id", "lastUpdated", "port"})
  void testGetCargoInfoDetailed(String str) {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder()
            .addAllParam(
                Arrays.asList(
                    CargoInfo.Param.newBuilder().setKey(str).setValue("10-11-2011").build()))
            .setPage(1)
            .setPageSize(1)
            .setOrderBy("ASC")
            .setSortBy("ASC")
            .build();
    StreamRecorder<CargoInfo.CargoDetailedReply> responseObserver = StreamRecorder.create();
    List<Cargo> cargoList = getCargoList();
    Page<Cargo> cargoPage = new PageImpl<>(cargoList);

    when(this.cargoRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(cargoPage);

    cargoService.getCargoInfoDetailed(request, responseObserver);
    List<CargoInfo.CargoDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoDetailedNullValues() {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder()
            .addAllParam(
                Arrays.asList(CargoInfo.Param.newBuilder().setKey("id").setValue("1").build()))
            .setPage(1)
            .setPageSize(1)
            .setOrderBy("asc")
            .setSortBy("asc")
            .build();
    StreamRecorder<CargoInfo.CargoDetailedReply> responseObserver = StreamRecorder.create();
    Cargo cargo = new Cargo();
    cargo.setId(1l);
    List<Cargo> cargoList = Arrays.asList(cargo);
    Page<Cargo> cargoPage = new PageImpl<>(cargoList);

    when(this.cargoRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(cargoPage);

    cargoService.getCargoInfoDetailed(request, responseObserver);
    List<CargoInfo.CargoDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoDetailedWithException() {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder()
            .addAllParam(
                Arrays.asList(CargoInfo.Param.newBuilder().setKey("id").setValue("1").build()))
            .setPage(1)
            .setPageSize(1)
            .setOrderBy("asc")
            .setSortBy("asc")
            .build();
    StreamRecorder<CargoInfo.CargoDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenThrow(new RuntimeException("1"));

    cargoService.getCargoInfoDetailed(request, responseObserver);
    List<CargoInfo.CargoDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoDetailedById() {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder().setCargoId(1l).build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.findById(anyLong())).thenReturn(Optional.of(getCargoList().get(0)));

    cargoService.getCargoInfoDetailedById(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoDetailedByIdWithException() {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder().setCargoId(1l).build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.findById(anyLong())).thenThrow(new RuntimeException("1"));

    cargoService.getCargoInfoDetailedById(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1})
  void testDeleteCargoById(Integer in) {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder().setCargoId(1l).build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.deleteByCargoId(anyLong())).thenReturn(in);

    cargoService.deleteCargoById(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeleteCargoByIdWithException() {
    com.cpdss.common.generated.CargoInfo.CargoRequest request =
        CargoRequest.newBuilder().setCargoId(1l).build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.deleteByCargoId(anyLong())).thenThrow(new RuntimeException("1"));

    cargoService.deleteCargoById(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveCargo() {
    com.cpdss.common.generated.CargoInfo.CargoDetailed request =
        CargoInfo.CargoDetailed.newBuilder()
            .setId(0)
            .setName("1")
            .setAbbreviation("1")
            .setApi("1")
            .setAssayDate(LocalDate.now().toString())
            .setReidVapourPressure("1")
            .setGas("1")
            .setTotalWax("1")
            .setPourPoint("1")
            .setCloudPoint("1")
            .setViscosity("1")
            .setTemp("1")
            .setCowCodes("1")
            .setHydrogenSulfideOil("1")
            .setHydrogenSulfideVapour("1")
            .setBenzene("1")
            .setSpecialInstrictionsRemark("1")
            .build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.save(any(Cargo.class))).thenReturn(getCargoList().get(0));

    cargoService.saveCargo(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveCargoElse() {
    com.cpdss.common.generated.CargoInfo.CargoDetailed request =
        CargoInfo.CargoDetailed.newBuilder()
            .setId(1l)
            .setName("1")
            .setAbbreviation("1")
            .setApi("1")
            .setReidVapourPressure("1")
            .setGas("1")
            .setTotalWax("1")
            .setPourPoint("1")
            .setCloudPoint("1")
            .setViscosity("1")
            .setTemp("1")
            .setCowCodes("1")
            .setHydrogenSulfideOil("1")
            .setHydrogenSulfideVapour("1")
            .setBenzene("1")
            .setSpecialInstrictionsRemark("1")
            .build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.getById(anyLong())).thenReturn(getCargoList().get(0));
    when(this.cargoRepository.save(any(Cargo.class))).thenReturn(getCargoList().get(0));

    cargoService.saveCargo(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveCargoWithGenericException() {
    com.cpdss.common.generated.CargoInfo.CargoDetailed request =
        CargoInfo.CargoDetailed.newBuilder().setId(0).setName("1").build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.findByCrudeTypeIgnoreCaseAndIsActiveTrue(anyString()))
        .thenReturn(getCargoList());

    cargoService.saveCargo(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveCargoWithGenericException2() {
    com.cpdss.common.generated.CargoInfo.CargoDetailed request =
        CargoInfo.CargoDetailed.newBuilder().setId(0).setAbbreviation("1").build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();
    List<Cargo> cargoList = new ArrayList<>();

    when(this.cargoRepository.findByCrudeTypeIgnoreCaseAndIsActiveTrue(anyString()))
        .thenReturn(cargoList);
    when(this.cargoRepository.findByAbbreviationIgnoreCaseAndIsActiveTrue(anyString()))
        .thenReturn(getCargoList());

    cargoService.saveCargo(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveCargoWithException() {
    com.cpdss.common.generated.CargoInfo.CargoDetailed request =
        CargoInfo.CargoDetailed.newBuilder().setId(1l).build();
    StreamRecorder<CargoInfo.CargoByIdDetailedReply> responseObserver = StreamRecorder.create();

    when(this.cargoRepository.getById(anyLong())).thenThrow(new RuntimeException("1"));

    cargoService.saveCargo(request, responseObserver);
    List<CargoInfo.CargoByIdDetailedReply> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    Assert.assertNull(responseObserver.getError());
    Assert.assertEquals(FAILURE, replies.get(0).getResponseStatus().getStatus());
  }

  private List<Cargo> getCargoList() {
    List<Cargo> cargoList = new ArrayList<>();
    Cargo cargo = new Cargo();
    cargo.setCrudeType("1");
    cargo.setId(1l);
    cargo.setAbbreviation("1");
    cargo.setApi("1");
    cargo.setIsCondensateCargo(true);
    cargo.setBenzene("1");
    cargo.setMaxCloudPoint("1");
    cargo.setMinLoadTemp("1");
    cargo.setGasC4("1");
    cargo.setTotalWax("1");
    cargo.setMaxPourPoint("1");
    cargo.setMaxCloudPoint("1");
    cargo.setViscocityT1("1");
    cargo.setCowCodeRecommendedSummer("1");
    cargo.setH2sVapourPhaseConfirmed("1");
    cargo.setRemarks("1");
    cargo.setFromRvp("1");
    cargo.setLastUpdated(LocalDate.now());
    cargo.setH2sOilPhase("1");
    cargoList.add(cargo);
    return cargoList;
  }
}
