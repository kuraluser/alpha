/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.gateway.domain.AlgoError;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {AlgoErrorService.class})
public class AlgoErrorServiceTest {

  @Autowired AlgoErrorService algoErrorService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testSaveAllAlgoErrors() {
    List<AlgoError> algoError = new ArrayList<>();
    AlgoError error = new AlgoError();
    error.setErrorHeading("1");
    error.setErrorDetails(getLS());
    algoError.add(error);
    Mockito.when(loadableStudyServiceBlockingStub.saveAlgoErrors(Mockito.any()))
        .thenReturn(getAE());
    ReflectionTestUtils.setField(
        algoErrorService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response = this.algoErrorService.saveAllAlgoErrors(algoError);
      assertEquals("1", response.get(0).getErrorHeading());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<String> getLS() {
    List<String> strings = new ArrayList<>();
    strings.add("1");
    return strings;
  }

  private LoadableStudy.AlgoErrors getAE() {
    List<String> list = new ArrayList<>();
    list.add("1");
    LoadableStudy.AlgoErrors algoErrors =
        LoadableStudy.AlgoErrors.newBuilder()
            .addAllErrorMessages(list)
            .setErrorHeading("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return algoErrors;
  }

  @Test
  void testSaveSingleAlogError() {
    AlgoError error = new AlgoError();
    error.setErrorHeading("1");
    error.setErrorDetails(getLS());
    Mockito.when(loadableStudyServiceBlockingStub.saveAlgoErrors(Mockito.any()))
        .thenReturn(getAE());
    ReflectionTestUtils.setField(
        algoErrorService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response = this.algoErrorService.saveSingleAlogError(error);
      assertEquals("1", response.getErrorHeading());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveSingleAlogErrorException() {
    AlgoError error = new AlgoError();
    error.setErrorHeading("1");
    error.setErrorDetails(getLS());
    Mockito.when(loadableStudyServiceBlockingStub.saveAlgoErrors(Mockito.any()))
        .thenReturn(getAENS());
    ReflectionTestUtils.setField(
        algoErrorService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response = this.algoErrorService.saveSingleAlogError(error);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.AlgoErrors getAENS() {
    List<String> list = new ArrayList<>();
    list.add("1");
    LoadableStudy.AlgoErrors algoErrors =
        LoadableStudy.AlgoErrors.newBuilder()
            .addAllErrorMessages(list)
            .setErrorHeading("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setHttpStatusCode(400).build())
            .build();
    return algoErrors;
  }
}
