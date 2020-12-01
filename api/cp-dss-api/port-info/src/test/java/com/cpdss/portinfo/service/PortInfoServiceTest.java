/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.portinfo.entity.PortInfo;
import com.cpdss.portinfo.repository.PortInfoRepository;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** Test class for methods related to port info */
// @SpringBootTest
public class PortInfoServiceTest {

  @InjectMocks private PortInfoService portService;

  @Mock private PortInfoRepository portRepository;

  @Mock private PortInfo port;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(port.getId()).thenReturn(1L);
    when(port.getName()).thenReturn("testName");
    List<PortInfo> portList = new ArrayList<>();
    portList.add(port);
    when(portRepository.findAll()).thenReturn(portList);
  }

  @Test
  void testGetPortInfo() throws Exception {
    PortRequest portRequest = PortRequest.newBuilder().setLoadableStudyId(1L).build();
    StreamRecorder<PortReply> responseObserver = StreamRecorder.create();
    portService.getPortInfo(portRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<PortReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    PortReply returnedPortReply = results.get(0);
    PortReply.Builder portReply = PortReply.newBuilder();
    //		CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
    //		cargoDetail.setAbbreviation("testAbbr");
    //		cargoDetail.setApi("testApi");
    //		cargoReply.addCargos(cargoDetail);
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus("SUCCESS");
    portReply.setResponseStatus(responseStatus);
    assertEquals(portReply.getResponseStatus(), returnedPortReply.getResponseStatus());
  }
}