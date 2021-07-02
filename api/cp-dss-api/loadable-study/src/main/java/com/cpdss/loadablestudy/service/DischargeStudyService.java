package com.cpdss.loadablestudy.service;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.FAILED;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.DischargeStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.dischargestudy.DischargeStudyModel.DischargeStudyRequest;
import com.cpdss.common.generated.dischargestudy.DischargeStudyServiceGrpc.DischargeStudyServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
/**
 * 
 * @author arun.j
 *
 */
@Log4j2
@GrpcService
@Transactional
public class DischargeStudyService extends DischargeStudyServiceImplBase {
	
	  @Autowired private LoadableStudyRepository dischargeStudyRepository;
	  @Autowired VoyageService voyageService;

	@Override
		public void deleteDischargeStudy(DischargeStudyRequest request,
				StreamObserver<DischargeStudyReply> responseObserver) {
		DischargeStudyReply.Builder replyBuilder = DischargeStudyReply.newBuilder();
	    try {
	      Optional<LoadableStudy> entityOpt =
	          this.dischargeStudyRepository.findById(request.getDischargeStudyId());
	      if (!entityOpt.isPresent()) {
	        throw new GenericServiceException(
	            "Loadable study does not exist",
	            CommonErrorCodes.E_HTTP_BAD_REQUEST,
	            HttpStatusCode.BAD_REQUEST);
	      }
	      LoadableStudy entity = entityOpt.get();
	      this.voyageService.checkIfVoyageClosed(entity.getVoyage().getId());

	      entity.setActive(false);
	      this.dischargeStudyRepository.save(entity);
	      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
	    } catch (GenericServiceException e) {
	      log.error("GenericServiceException when deleting discharge study", e);
	      replyBuilder.setResponseStatus(
	          ResponseStatus.newBuilder()
	              .setCode(e.getCode())
	              .setMessage(e.getMessage())
	              .setStatus(FAILED)
	              .setHttpStatusCode(e.getStatus().value())
	              .build());
	    } catch (Exception e) {
	      log.error("Exception when deleting discharge study", e);
	      replyBuilder.setResponseStatus(
	          ResponseStatus.newBuilder()
	              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
	              .setMessage("Exception  when deleting discharge study")
	              .setStatus(FAILED)
	              .setHttpStatusCode(Integer.valueOf(CommonErrorCodes.E_GEN_INTERNAL_ERR))
	              .build());
	    } finally {
	      responseObserver.onNext(replyBuilder.build());
	      responseObserver.onCompleted();
	    }
	  }

}
