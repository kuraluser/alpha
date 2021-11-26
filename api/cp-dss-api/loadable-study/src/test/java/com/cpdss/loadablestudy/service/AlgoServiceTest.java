/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.AlgoErrors;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import com.cpdss.loadablestudy.repository.*;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

@SpringJUnitConfig(
    classes = {
      AlgoService.class,
    })
public class AlgoServiceTest {
  @Autowired AlgoService algoService;
  @MockBean LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean LoadablePatternRepository loadablePatternRepository;
  @MockBean AlgoErrorsRepository algoErrorsRepository;
  @MockBean LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean RestTemplate restTemplate;
  @MockBean AlgoErrorService algoErrorService;
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Test
  void testSaveAlgoLoadableStudyStatus() {
    com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request =
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.newBuilder()
            .setProcesssId("1")
            .setLoadableStudystatusId(1l)
            .build();
    LoadableStudy.AlgoStatusReply.Builder replyBuilder = LoadableStudy.AlgoStatusReply.newBuilder();
    LoadableStudyAlgoStatus algoStatus = new LoadableStudyAlgoStatus();
    Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt = Optional.of(algoStatus);
    Mockito.when(
            loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
                Mockito.anyString(), Mockito.anyBoolean()))
        .thenReturn(loadableStudyAlgoStatusOpt);
    var result = algoService.saveAlgoLoadableStudyStatus(request, replyBuilder);
    Mockito.verify(loadableStudyAlgoStatusRepository)
        .updateLoadableStudyAlgoStatus(
            Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testFetchAllErrors() {
    com.cpdss.common.generated.LoadableStudy.AlgoErrors request =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .build();
    List<AlgoErrorHeading> algoErrorHeadingList = new ArrayList<>();
    AlgoErrorHeading algoErrorHeading = new AlgoErrorHeading();
    List<AlgoErrors> algoErrorsList = new ArrayList<>();
    AlgoErrors algoErrors = new AlgoErrors();
    algoErrors.setErrorMessage("1");
    algoErrorsList.add(algoErrors);
    algoErrorHeading.setAlgoErrors(algoErrorsList);
    algoErrorHeadingList.add(algoErrorHeading);
    Optional<List<AlgoErrorHeading>> alogError = Optional.of(algoErrorHeadingList);
    Mockito.when(algoErrorHeadingRepository.findAllByErrorHeading(Mockito.anyString()))
        .thenReturn(alogError);
    StreamRecorder<LoadableStudy.AlgoErrors> responseObserver = StreamRecorder.create();
    algoService.fetchAllErrors(request, responseObserver);
    List<LoadableStudy.AlgoErrors> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    Assert.assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testFetchAllErrorsWithException() {
    com.cpdss.common.generated.LoadableStudy.AlgoErrors request =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .build();
    AlgoService spyService = Mockito.spy(this.algoService);

    Mockito.when(algoErrorHeadingRepository.findAllByErrorHeading(Mockito.anyString()))
        .thenThrow(new RuntimeException("error"));
    StreamRecorder<LoadableStudy.AlgoErrors> responseObserver = StreamRecorder.create();
    spyService.fetchAllErrors(request, responseObserver);
    List<LoadableStudy.AlgoErrors> replies = responseObserver.getValues();
    Assert.assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    Assert.assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testBuildLoadableStudyErrorDetails() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.Builder replyBuilder =
        LoadableStudy.AlgoErrorReply.newBuilder();
    Mockito.when(
            algoErrorHeadingRepository.findByLoadableStudyAndIsActive(
                Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
                Mockito.anyBoolean()))
        .thenReturn(getAlgoErrorOpt());
    List<AlgoErrors> algoErrorsList = new ArrayList<>();
    AlgoErrors algoErrors = new AlgoErrors();
    algoErrors.setErrorMessage("1");
    algoErrorsList.add(algoErrors);
    Optional<List<com.cpdss.loadablestudy.entity.AlgoErrors>> algoError =
        Optional.of(algoErrorsList);
    Mockito.when(
            algoErrorsRepository.findByAlgoErrorHeadingAndIsActive(
                Mockito.any(AlgoErrorHeading.class), Mockito.anyBoolean()))
        .thenReturn(algoError);
    algoService.buildLoadableStudyErrorDetails(loadableStudy, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testSaveAlgoErrorToDB() {
    List<String> errorMsgs = new ArrayList();
    errorMsgs.add("1");
    LoadableStudy.AlgoErrors algoErrors =
        LoadableStudy.AlgoErrors.newBuilder()
            .setErrorHeading("1")
            .addAllErrorMessages(errorMsgs)
            .build();
    LoadableStudy.LoadablePatternAlgoRequest request =
        LoadableStudy.LoadablePatternAlgoRequest.newBuilder().addAlgoErrors(algoErrors).build();
    LoadablePattern loadablePattern = new LoadablePattern();
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    boolean isPatternErrorSaving = true;
    algoService.saveAlgoErrorToDB(request, loadablePattern, loadableStudy, isPatternErrorSaving);
    Mockito.verify(algoErrorsRepository).save(Mockito.any(AlgoErrors.class));
  }

  @Test
  void testgetAlgoErrors() {
    LoadableStudy.AlgoErrorRequest request =
        LoadableStudy.AlgoErrorRequest.newBuilder().setLoadablePatternId(1l).build();
    LoadableStudy.AlgoErrorReply.Builder replyBuilder = LoadableStudy.AlgoErrorReply.newBuilder();
    LoadablePattern loadablePattern = new LoadablePattern();
    Optional<LoadablePattern> loadablePatternOpt = Optional.of(loadablePattern);
    List<AlgoErrors> algoErrorsList = new ArrayList<>();
    AlgoErrors algoErrors = new AlgoErrors();
    algoErrors.setErrorMessage("1");
    algoErrorsList.add(algoErrors);
    Optional<List<com.cpdss.loadablestudy.entity.AlgoErrors>> algoError =
        Optional.of(algoErrorsList);
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();

    Mockito.when(
            algoErrorsRepository.findByAlgoErrorHeadingAndIsActive(
                Mockito.any(AlgoErrorHeading.class), Mockito.anyBoolean()))
        .thenReturn(algoError);
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadablePatternOpt);
    Mockito.when(
            algoErrorHeadingRepository.findByLoadablePatternAndIsActive(
                Mockito.any(LoadablePattern.class), Mockito.anyBoolean()))
        .thenReturn(getAlgoErrorOpt());
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> ls = Optional.of(loadableStudy);
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(ls);
    try {
      var result = algoService.getAlgoErrors(request, replyBuilder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testgetAlgoErrorsElse() {
    LoadableStudy.AlgoErrorRequest request =
        LoadableStudy.AlgoErrorRequest.newBuilder()
            .setLoadablePatternId(0)
            .setLoadableStudyId(1l)
            .build();
    LoadableStudy.AlgoErrorReply.Builder replyBuilder = LoadableStudy.AlgoErrorReply.newBuilder();
    List<AlgoErrors> algoErrorsList = new ArrayList<>();
    AlgoErrors algoErrors = new AlgoErrors();
    algoErrors.setErrorMessage("1");
    algoErrorsList.add(algoErrors);
    Optional<List<com.cpdss.loadablestudy.entity.AlgoErrors>> algoError =
        Optional.of(algoErrorsList);
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();

    Mockito.when(
            algoErrorsRepository.findByAlgoErrorHeadingAndIsActive(
                Mockito.any(AlgoErrorHeading.class), Mockito.anyBoolean()))
        .thenReturn(algoError);
    Mockito.when(
            algoErrorHeadingRepository.findByLoadablePatternAndIsActive(
                Mockito.any(LoadablePattern.class), Mockito.anyBoolean()))
        .thenReturn(getAlgoErrorOpt());
    Mockito.when(
            algoErrorHeadingRepository.findByLoadableStudyAndIsActive(
                Mockito.any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
                Mockito.anyBoolean()))
        .thenReturn(getAlgoErrorOpt());
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> ls = Optional.of(loadableStudy);
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(ls);
    try {
      var result = algoService.getAlgoErrors(request, replyBuilder);
      assertEquals(SUCCESS, result.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<List<AlgoErrorHeading>> getAlgoErrorOpt() {
    List<AlgoErrorHeading> algoErrorHeadingList = new ArrayList<>();
    AlgoErrorHeading algoErrorHeading = new AlgoErrorHeading();
    List<AlgoErrors> algoErrorsList = new ArrayList<>();
    AlgoErrors algoErrors = new AlgoErrors();
    algoErrors.setErrorMessage("1");
    algoErrorsList.add(algoErrors);
    algoErrorHeading.setAlgoErrors(algoErrorsList);
    algoErrorHeading.setErrorHeading("1");
    algoErrorHeadingList.add(algoErrorHeading);
    Optional<List<AlgoErrorHeading>> alogError = Optional.of(algoErrorHeadingList);
    return alogError;
  }
}
