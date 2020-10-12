/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.VoyageRepository;

import io.grpc.internal.testing.StreamRecorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


/** @Author jerin.g */
@SpringBootTest
@SpringJUnitConfig(classes = {LoadableStudyUnitTestConfiguration.class})
public class LoadableStudyServiceTest {

  @Autowired
  private LoadableStudyService loadableStudyService;

  @MockBean 
  private VoyageRepository voyageRepository;

/**
 * method for positive test case
 * @throws GenericServiceException
 * void
 */
  @Test
  public void testSaveVoyage() throws GenericServiceException {
	  VoyageRequest request = VoyageRequest.newBuilder()
	            .setCaptainId(1)
	            .setChiefOfficerId(1)
	            .setCompanyId(1)
	            .setVesselId(1)
	            .setVoyageNo("VOYAGE")
	            .build();
      StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
      Voyage voyage = new Voyage();
      Mockito.when(this.voyageRepository.save(voyage)).thenReturn(voyage);
      List<Voyage> voyages = new ArrayList<Voyage>();
      voyages.add(voyage);
      Mockito.when(this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(new ArrayList<Voyage>());
     
      loadableStudyService.saveVoyage(request, responseObserver);
     
      assertNull(responseObserver.getError());
      List<VoyageReply> results = responseObserver.getValues();
      assertEquals(1, results.size());
      VoyageReply response = results.get(0);
      assertEquals(VoyageReply.newBuilder()
              .setMessage("SUCCESS")
              .setStatus("SUCCESS")
              .build(), response);

  }
  
  /**
   * method for negative case
   * @throws GenericServiceException
   * void
   */
  @Test
  public void testSaveVoyageFailure() throws GenericServiceException {
	  VoyageRequest request = VoyageRequest.newBuilder()
	            .setCaptainId(1)
	            .setChiefOfficerId(1)
	            .setCompanyId(1)
	            .setVesselId(1)
	            .setVoyageNo("VOYAGE")
	            .build();
      StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
      Voyage voyage = new Voyage();
      Mockito.when(this.voyageRepository.save(voyage)).thenReturn(voyage);
      List<Voyage> voyages = new ArrayList<Voyage>();
      voyages.add(voyage);
      Mockito.when(this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(voyages);
     
      loadableStudyService.saveVoyage(request, responseObserver);
      
      assertNull(responseObserver.getError());
      List<VoyageReply> results = responseObserver.getValues();
      assertEquals(1, results.size());
      VoyageReply response = results.get(0);
      assertEquals(VoyageReply.newBuilder()
              .setMessage("VOYATE_EXISTS")
              .setStatus("SUCCESS")
              .build(), response);

  }
}
