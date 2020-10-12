/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
/** Class has mehtod  to save voyage*/
@Log4j2
@GrpcService
@Service
public class LoadableStudyService extends LoadableStudyServiceImplBase {

  @Autowired 
  private VoyageRepository voyageRepository;

  private static final String SUCCESS = "SUCCESS";
  
  


 /**
  * 
  * @param request
  * @param responseObserver
  */
  @Override
  public void saveVoyage(VoyageRequest request, StreamObserver<VoyageReply> responseObserver) {
    try {

      //validation for duplicate voyages
        if(!voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(request.getCompanyId(), request.getVesselId(), request.getVoyageNo()).isEmpty()) { 
        	 VoyageReply reply = VoyageReply.newBuilder().setMessage("VOYATE_EXISTS").setStatus(SUCCESS).build();
        	 responseObserver.onNext(reply);
             responseObserver.onCompleted();
             
        } else {
      

      Voyage voyage = new Voyage();
      voyage.setIsactive(true);
      voyage.setCompanyXId(request.getCompanyId());
      voyage.setVesselXId(request.getVesselId());
      voyage.setVoyageNo(request.getVoyageNo());
      voyage.setCaptainXId(request.getCaptainId());
      voyage.setChiefOfficerXId(request.getChiefOfficerId());
      voyageRepository.save(voyage);

      // when Db save is complete we return to client a success message
      VoyageReply reply = VoyageReply.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
        }
    } catch (Exception e) {

      log.error("Error in saving Voyage ", e);
      //			throw new GenericServiceException("Error in saving Voyage", "ERR",
      // HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
  }
}
