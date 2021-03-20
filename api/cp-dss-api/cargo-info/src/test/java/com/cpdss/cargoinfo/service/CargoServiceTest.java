/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.cargoinfo.repository.CargoRepository;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.Common.ResponseStatus;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** Test class for cargo related methods */
// @SpringBootTest
public class CargoServiceTest {

  @InjectMocks private CargoService cargoService;

  @Mock private CargoRepository cargoRepository;

  @Mock private Cargo cargo;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(cargo.getAbbreviation()).thenReturn("testAbbr");
    when(cargo.getApi()).thenReturn("testApi");
    when(cargo.getCrudeType()).thenReturn("testCrudeType");
    List<Cargo> cargoList = new ArrayList<>();
    cargoList.add(cargo);
    when(cargoRepository.findAll()).thenReturn(cargoList);
  }

  @Test
  void testGetCargoInfo() throws Exception {
    CargoRequest cargoRequest = CargoRequest.newBuilder().setVoyageId(111).build();
    StreamRecorder<CargoReply> responseObserver = StreamRecorder.create();
    cargoService.getCargoInfo(cargoRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoReply returnedCargoReply = results.get(0);
    CargoReply.Builder cargoReply = CargoReply.newBuilder();
    //		CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
    //		cargoDetail.setAbbreviation("testAbbr");
    //		cargoDetail.setApi("testApi");
    //		cargoReply.addCargos(cargoDetail);
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus("SUCCESS");
    cargoReply.setResponseStatus(responseStatus);
    assertEquals(cargoReply.getResponseStatus(), returnedCargoReply.getResponseStatus());
  }
}
