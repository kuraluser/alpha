/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.VesselInfo;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselCowParameters;
import com.cpdss.vesselinfo.repository.VesselCowParameterRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      VesselCowService.class,
    })
public class VesselCowServiceTest {
  @Autowired VesselCowService vesselCowService;

  @MockBean VesselCowParameterRepository vesselCowParameterRepository;

  @Test
  void testBuildVesselCowParameters() {
    VesselInfo.VesselAlgoReply.Builder replyBuilder = VesselInfo.VesselAlgoReply.newBuilder();
    Vessel vessel = new Vessel();
    vessel.setId(1l);

    List<VesselCowParameters> vcp = new ArrayList<>();
    VesselCowParameters parameters = new VesselCowParameters();
    parameters.setId(1l);
    parameters.setVesselId(1l);
    parameters.setTopCowMaxDuration(new BigDecimal(1));
    parameters.setTopCowMinDuration(new BigDecimal(1));
    parameters.setBottomCowMaxDuration(new BigDecimal(1));
    parameters.setBottomCowMinDuration(new BigDecimal(1));
    parameters.setFullCowMaxDuration(new BigDecimal(1));
    parameters.setFullCowMinDuration(new BigDecimal(1));
    parameters.setTopWashMaxAngle(new BigDecimal(1));
    parameters.setTopWashMinAngle(new BigDecimal(1));
    parameters.setBottomWashMaxAngle(new BigDecimal(1));
    parameters.setBottomWashMinAngle(new BigDecimal(1));
    vcp.add(parameters);

    when(this.vesselCowParameterRepository.findAllByVesselIdAndIsActiveTrue(anyLong()))
        .thenReturn(vcp);

    vesselCowService.buildVesselCowParameters(replyBuilder, vessel);
    assertEquals(1l, replyBuilder.getVesselCowParameters(0).getVesselId());
  }
}
