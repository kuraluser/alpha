/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;

import io.grpc.internal.testing.StreamRecorder;

/** @Author jerin.g */
@SpringJUnitConfig(classes = {LoadableStudyService.class})
public class LoadableStudyServiceTest {

  @Autowired private LoadableStudyService loadableStudyService;

  @MockBean private VoyageRepository voyageRepository;

  @MockBean private LoadableStudyRepository loadableStudyRepository;
  
  private static final String SUCCESS = "SUCCESS";
  private static final String VOYAGE = "VOYAGE";
  private static final String FAILED = "FAILED";
  private static final String LOADABLE_STUDY_NAME = "LS";
  private static final String LOADABLE_STUDY_DETAILS = "details";
  private static final String LOADABLE_STUDY_STATUS = "pending";
  
  private static final String CHARTERER = "charterer";
  private static final String SUB_CHARTERER = "sub-chartere";
  private static final String DRAFT_MARK = "1000";
  private static final Long LOAD_LINE_ID = 1L;
  private static final String DRAFT_RESTRICTION = "1000";
  private static final String MAX_TEMP_EXPECTED = "100";

  /**
   * method for positive test case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testSaveVoyage() throws GenericServiceException {
    VoyageRequest request =
        VoyageRequest.newBuilder()
            .setCaptainId(1)
            .setChiefOfficerId(1)
            .setCompanyId(1)
            .setVesselId(1)
            .setVoyageNo(VOYAGE)
            .build();
    StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    Mockito.when(this.voyageRepository.save(voyage)).thenReturn(voyage);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()))
        .thenReturn(new ArrayList<Voyage>());

    loadableStudyService.saveVoyage(request, responseObserver);

    assertNull(responseObserver.getError());
    List<VoyageReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(
        VoyageReply.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build(), response);
  }

  /**
   * method for negative case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testSaveVoyageFailure() throws GenericServiceException {
    VoyageRequest request =
        VoyageRequest.newBuilder()
            .setCaptainId(1)
            .setChiefOfficerId(1)
            .setCompanyId(1)
            .setVesselId(1)
            .setVoyageNo(VOYAGE)
            .build();
    StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    Mockito.when(this.voyageRepository.save(voyage)).thenReturn(voyage);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()))
        .thenReturn(voyages);

    loadableStudyService.saveVoyage(request, responseObserver);

    assertNull(responseObserver.getError());
    List<VoyageReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(
        VoyageReply.newBuilder().setMessage("VOYATE_EXISTS").setStatus(SUCCESS).build(),
        response);
  }

  @Test
  void testFindLoadableStudiesByVesselAndVoyage() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.getOne(anyLong())).thenReturn(new Voyage());
    when(this.loadableStudyRepository.findByVesselXIdAndVoyage(anyLong(), any(Voyage.class)))
        .thenReturn(this.createLoadableStudyEntityList());
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testFindLoadableStudiesServiceException() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.getOne(anyLong())).thenReturn(new Voyage());
    when(this.loadableStudyRepository.findByVesselXIdAndVoyage(anyLong(), any(Voyage.class)))
        .thenThrow(RuntimeException.class);
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private LoadableStudyRequest createLoadableStudyRequest() {
    return LoadableStudyRequest.newBuilder().setVesselId(1L).setVoyageId(1L).build();
  }

  private List<LoadableStudy> createLoadableStudyEntityList() {
    List<LoadableStudy> entityList = new ArrayList<LoadableStudy>();
    IntStream.range(0, 10)
        .forEach(
            i -> {
              LoadableStudy entity = new LoadableStudy();
              entity.setId(Long.valueOf(i));
              entity.setName(LOADABLE_STUDY_NAME + i);
              entity.setDetails(LOADABLE_STUDY_DETAILS + i);
              entity.setCreatedDate(LocalDate.now());
              entity.setLoadableStudyStatus(LOADABLE_STUDY_STATUS);
              entity.setCharterer(CHARTERER);
              entity.setSubCharterer(SUB_CHARTERER);
              entity.setDraftMark(new BigDecimal(DRAFT_MARK));
              entity.setLoadLineXId(LOAD_LINE_ID);
              entity.setDraftRestriction(new BigDecimal(DRAFT_RESTRICTION));
              entity.setMaxTempExpected(new BigDecimal(MAX_TEMP_EXPECTED));
              entityList.add(entity);
            });
    return entityList;
  }
}
