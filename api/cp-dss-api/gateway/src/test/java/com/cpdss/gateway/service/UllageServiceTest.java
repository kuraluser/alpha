/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
public class UllageServiceTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadableStudyService loadableStudyService;

  @MockBean private CargoNominationResponse cargoNominationResponse;

  @MockBean private PortRotationResponse portRotationResponse;

  @MockBean private CommingleCargoResponse commingleCargoResponse;

  @MockBean private VoyageStatusResponse voyageStatusResponse;

  @MockBean private LoadableStudyCargoService loadableStudyCargoService;

  @MockBean AlgoErrorService algoErrorService;

  @MockBean
  @Qualifier("cargoRedisSyncService")
  private RedisMasterSyncService redisCargoService;

  @MockBean
  @Qualifier("vesselRedisSyncService")
  private RedisMasterSyncService redisVesselService;

  @MockBean
  @Qualifier("portRedisSyncService")
  private RedisMasterSyncService redisPortService;

  @MockBean SyncRedisMasterService syncRedisMasterService;

  private UpdateUllage dummyUllageHttpRequest(boolean isBallast) {
    UpdateUllage ullage = new UpdateUllage();
    ullage.setId(anyLong());
    ullage.setTankId(anyLong());
    ullage.setCorrectedUllage(new BigDecimal(anyLong()));
    ullage.setCorrectionFactor(new BigDecimal(anyLong()));
    ullage.setFillingRatio(anyString());
    ullage.setIsBallast(isBallast);
    ullage.setQuantityMt(new BigDecimal(anyLong()));
    return ullage;
  }

  private LoadableStudy.UpdateUllageReply dummyUllageReply() {
    LoadableStudy.UpdateUllageReply.Builder uBuilder = LoadableStudy.UpdateUllageReply.newBuilder();
    Common.ResponseStatus.Builder rRuilder = Common.ResponseStatus.newBuilder();
    rRuilder.setStatus("SUCCESS");
    uBuilder.setResponseStatus(rRuilder);
    return uBuilder.build();
  }

  @ValueSource(strings = {"hello1", "hello2"})
  @ParameterizedTest
  public void testTest(String var) throws GenericServiceException {
    UpdateUllage ullage = dummyUllageHttpRequest(true);
    loadableStudyService.updateUllage(ullage, anyLong(), anyString());
    LoadableStudy.UpdateUllageRequest.Builder grpcRequest =
        LoadableStudy.UpdateUllageRequest.newBuilder();
    loadableStudyService.buildUpdateUllageRequest(ullage, anyLong(), grpcRequest);
    when(loadableStudyService.updateUllage(grpcRequest.build())).thenReturn(dummyUllageReply());
  }
}
