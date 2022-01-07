/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {CrewService.class})
public class CrewServiceTest {

  @Autowired CrewService crewService;

  @MockBean
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testGetAllCrewRank() {
    String correlationId = "1";
    Mockito.when(vesselInfoServiceBlockingStub.getAllCrewRank(Mockito.any())).thenReturn(getCR());
    ReflectionTestUtils.setField(
        crewService, "vesselInfoServiceBlockingStub", this.vesselInfoServiceBlockingStub);
    try {
      var response = this.crewService.getCrewRanks(correlationId);
      assertEquals(1L, response.getCrewRankList().get(0).getId());
    } catch (GenericServiceException exception) {
      exception.printStackTrace();
    }
  }

  @Test
  void testgetAllCrewRankException() {
    String correlationId = "1";
    Mockito.when(vesselInfoServiceBlockingStub.getAllCrewRank(Mockito.any())).thenReturn(getCRNS());
    ReflectionTestUtils.setField(
        crewService, "vesselInfoServiceBlockingStub", this.vesselInfoServiceBlockingStub);
    try {
      var response = this.crewService.getCrewRanks(correlationId);
      assertEquals(FAILED, response.getResponseStatus().getStatus());
    } catch (GenericServiceException exception) {
      exception.printStackTrace();
    }
  }

  private VesselInfo.CrewReply getCR() {
    List<VesselInfo.CrewRank> list = new ArrayList<>();
    VesselInfo.CrewRank crewRank =
        VesselInfo.CrewRank.newBuilder().setId(1L).setCrewName("1").build();
    list.add(crewRank);
    VesselInfo.CrewReply reply =
        VesselInfo.CrewReply.newBuilder()
            .addAllCrewRanks(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private VesselInfo.CrewReply getCRNS() {
    VesselInfo.CrewReply reply =
        VesselInfo.CrewReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }
}
