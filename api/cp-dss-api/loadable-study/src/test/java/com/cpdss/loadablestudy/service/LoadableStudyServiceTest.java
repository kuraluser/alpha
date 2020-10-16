/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.google.protobuf.ByteString;
import io.grpc.internal.testing.StreamRecorder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;

import io.grpc.internal.testing.StreamRecorder;






/** @Author jerin.g 
 * 
 * Class for writing test cases for loadable study
 * */

@SpringJUnitConfig(classes = {LoadableStudyService.class})
public class LoadableStudyServiceTest {

  @Autowired private LoadableStudyService loadableStudyService;

  @MockBean private VoyageRepository voyageRepository;
  
  @MockBean private LoadableStudyRepository loadableStudyRepository;

  @MockBean private LoadableQuantityRepository loadableQuantityRepository;

  
  private static final String SUCCESS = "SUCCESS";
  private static final String VOYAGE = "VOYAGE";
  private static final String VOYAGEEXISTS = "VOYAGEEXISTS";
  private static final String FAILED = "FAILED";
  private static final String INVALID_LOADABLE_STUDY = "INVALID_LOADABLE_STUDY";
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
   * method for positive test case of save voyage
   *
   * @throws GenericServiceException
   * @returns void
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
    /* used for grpc testing */
    StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);

    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()))
        .thenReturn(new ArrayList<Voyage>());

    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    loadableStudyService.saveVoyage(request, responseObserver);

    assertNull(responseObserver.getError());
    List<VoyageReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(
        VoyageReply.newBuilder()
            .setMessage(SUCCESS)
            .setStatus(SUCCESS)
            .setVoyageId((long) 1)
            .build(),
        response);
  }

  /**
   * method for negative test case for voyage save
   *
   * @throws GenericServiceException
   * @returns void
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
    /* used for grpc testing */
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
        VoyageReply.newBuilder().setMessage(VOYAGEEXISTS).setStatus(SUCCESS).build(), response);
  }

  @Test
  public void testLoadableQuantity() throws GenericServiceException {
	  
	  LoadableQuantityRequest loadableQuantityRequest =
		        LoadableQuantityRequest.newBuilder()
		            .setConstant("100")
		            .setDisplacmentDraftRestriction("100")
		            .setDistanceFromLastPort("100")
		            .setDwt("100")
		            .setEstDOOnBoard("100")
		            .setEstFOOnBoard("100")
		            .setEstFreshWaterOnBoard("100")
		            .setEstSagging("100")
		            .setEstSeaDensity("100")
		            .setEstTotalFOConsumption("100")
		            .setFoConsumptionPerDay("100")
		            .setLimitingDraft("100")
		            .setOtherIfAny("100")
		            .setSaggingDeduction("100")
		            .setSgCorrection("100")
		            .setTotalQuantity("100")
		            .setTpc("100")
		            .setVesselAverageSpeed("100")
		            .setVesselLightWeight("100")
		            .setLoadableStudyId(1)
		            .build();
	  
	    StreamRecorder<LoadableQuantityReply> responseObserver = StreamRecorder.create();
	    
	    LoadableStudy loadableStudy = Mockito.mock(LoadableStudy.class);
	    LoadableQuantity loadableQuantity = new LoadableQuantity();
	    loadableQuantity.setId((long) 1);
	    
	    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(loadableStudy));
	    Mockito.when(this.loadableQuantityRepository.save(ArgumentMatchers.any(LoadableQuantity.class))).thenReturn(loadableQuantity);
	    
	    loadableStudyService.saveLoadableQuantity(loadableQuantityRequest, responseObserver);
	    
	    assertNull(responseObserver.getError());
	    List<LoadableQuantityReply> results = responseObserver.getValues();
	    assertEquals(1, results.size());
	    LoadableQuantityReply response = results.get(0);
	    assertEquals(
	    		LoadableQuantityReply.newBuilder().setMessage("SUCCESS").setStatus("SUCCESS").setLoadableQuantityId((long) 1).build(), response);
	    
  }
  
  /**
   * loadabe study saving failing test case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testLoadableQuantityFailure() throws GenericServiceException {
	  
	  LoadableQuantityRequest loadableQuantityRequest =
		        LoadableQuantityRequest.newBuilder()
		            .setConstant("100")
		            .setDisplacmentDraftRestriction("100")
		            .setDistanceFromLastPort("100")
		            .setDwt("100")
		            .setEstDOOnBoard("100")
		            .setEstFOOnBoard("100")
		            .setEstFreshWaterOnBoard("100")
		            .setEstSagging("100")
		            .setEstSeaDensity("100")
		            .setEstTotalFOConsumption("100")
		            .setFoConsumptionPerDay("100")
		            .setLimitingDraft("100")
		            .setOtherIfAny("100")
		            .setSaggingDeduction("100")
		            .setSgCorrection("100")
		            .setTotalQuantity("100")
		            .setTpc("100")
		            .setVesselAverageSpeed("100")
		            .setVesselLightWeight("100")
		            .setLoadableStudyId(1)
		            .build();
	  
	    StreamRecorder<LoadableQuantityReply> responseObserver = StreamRecorder.create();
	    
	    
	    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.<LoadableStudy>empty());
	    
	    loadableStudyService.saveLoadableQuantity(loadableQuantityRequest, responseObserver);
	    
	    assertNull(responseObserver.getError());
	    List<LoadableQuantityReply> results = responseObserver.getValues();
	    assertEquals(1, results.size());
	    LoadableQuantityReply response = results.get(0);
	    assertEquals(
	    		LoadableQuantityReply.newBuilder().setMessage(INVALID_LOADABLE_STUDY).setStatus("SUCCESS").build(), response);
	    
	   
  }

  
  

  @Test
  void testFindLoadableStudiesByVesselAndVoyage() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
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
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findByVesselXIdAndVoyage(anyLong(), any(Voyage.class)))
        .thenThrow(RuntimeException.class);
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testFindLoadableStudiesInvalidVoyage() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.empty());
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  /** Test loadable study saving */
  @Test
  void testSaveLoadableStudy() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  @Test
  void testSaveLoadableStudyWithNullValues() {
    LoadableStudyDetail request =
        LoadableStudyDetail.newBuilder()
            .setName(LOADABLE_STUDY_NAME)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setLoadLineXId(LOAD_LINE_ID)
            .addAttachments(
                LoadableStudyAttachment.newBuilder()
                    .setByteString(ByteString.copyFrom("test content".getBytes()))
                    .setFileName("test name")
                    .build())
            .build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  @Test
  void testSaveLoadableStudyInvalidVoyage() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(
        String.valueOf(HttpStatus.BAD_REQUEST.value()),
        replies.get(0).getResponseStatus().getCode());
  }

  @Test
  void testSaveLoadableStudyInvalidCreatedFromStudy() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(
        String.valueOf(HttpStatus.BAD_REQUEST.value()),
        replies.get(0).getResponseStatus().getCode());
  }

  @Test
  void testSaveLoadableStudyRuntimeException() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    when(this.voyageRepository.findById(anyLong())).thenThrow(RuntimeException.class);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
        replies.get(0).getResponseStatus().getCode());
  }

  /**
   * Create save request for loadable study
   *
   * @return {@link LoadableStudyDetail}
   */
  private LoadableStudyDetail createLoadableStudySaveRequest() {
    LoadableStudyDetail request =
        LoadableStudyDetail.newBuilder()
            .setName(LOADABLE_STUDY_NAME)
            .setDetail(LOADABLE_STUDY_DETAILS)
            .setCharterer(CHARTERER)
            .setSubCharterer(SUB_CHARTERER)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setDraftMark(DRAFT_MARK)
            .setDraftRestriction(DRAFT_RESTRICTION)
            .setMaxTempExpected(MAX_TEMP_EXPECTED)
            .setLoadLineXId(LOAD_LINE_ID)
            .setDuplicatedFromId(1L)
            .addAttachments(
                LoadableStudyAttachment.newBuilder()
                    .setByteString(ByteString.copyFrom("test content".getBytes()))
                    .setFileName("test name")
                    .build())
            .build();
    return request;
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
