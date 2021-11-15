/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.mockito.ArgumentMatchers.*;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.gateway.domain.UpdateUllage;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UllageServiceTest {

  @Mock private LoadableStudyService loadableStudyService;

  private UpdateUllage dummyUllageHttpRequest(boolean isBallast) {
    UpdateUllage ullage = new UpdateUllage();
    ullage.setId(1l);
    ullage.setTankId(1l);
    ullage.setCorrectedUllage(new BigDecimal(1l));
    ullage.setCorrectionFactor(new BigDecimal(1l));
    ullage.setFillingRatio("test");
    ullage.setIsBallast(isBallast);
    ullage.setQuantityMt(new BigDecimal(1l));
    return ullage;
  }

  private LoadableStudy.UpdateUllageReply dummyUllageReply() {
    LoadableStudy.UpdateUllageReply.Builder uBuilder = LoadableStudy.UpdateUllageReply.newBuilder();
    Common.ResponseStatus.Builder rRuilder = Common.ResponseStatus.newBuilder();
    rRuilder.setStatus("SUCCESS");
    uBuilder.setResponseStatus(rRuilder);
    return uBuilder.build();
  }

  // To-Do Rework
  //  @ValueSource(strings = {"hello1", "hello2"})
  //  @ParameterizedTest
  //  public void testTest(String var) throws GenericServiceException {
  //    UpdateUllage ullage = dummyUllageHttpRequest(true);
  //    LoadableStudy.UpdateUllageRequest.Builder grpcRequest =
  //        LoadableStudy.UpdateUllageRequest.newBuilder();
  //    loadableStudyService.buildUpdateUllageRequest(ullage, 1l, grpcRequest);
  //    when(loadableStudyService.updateUllage(grpcRequest.build())).thenReturn(dummyUllageReply());
  //
  //  }
}
