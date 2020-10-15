/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import io.grpc.stub.StreamObserver;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Service
@Transactional
public class LoadableStudyService extends LoadableStudyServiceImplBase {

  @Autowired private VoyageRepository voyageRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String VOYAGEEXISTS = "VOYAGEEXISTS";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
 
  /**
   * method for save voyage
   *
   * @param request - voyage request details
   * @param responseObserver - grpc class
   * @return
   */
  @Override
  public void saveVoyage(VoyageRequest request, StreamObserver<VoyageReply> responseObserver) {
    VoyageReply reply = null;
    try {

      // validation for duplicate voyages
      if (!voyageRepository
          .findByCompanyXIdAndVesselXIdAndVoyageNo(
              request.getCompanyId(), request.getVesselId(), request.getVoyageNo())
          .isEmpty()) {
        reply = VoyageReply.newBuilder().setMessage(VOYAGEEXISTS).setStatus(SUCCESS).build();
      } else {

        Voyage voyage = new Voyage();
        voyage.setIsActive(true);
        voyage.setCompanyXId(request.getCompanyId());
        voyage.setVesselXId(request.getVesselId());
        voyage.setVoyageNo(request.getVoyageNo());
        voyage.setCaptainXId(request.getCaptainId());
        voyage.setChiefOfficerXId(request.getChiefOfficerId());
        voyage = voyageRepository.save(voyage);

        // when Db save is complete we return to client a success message
        reply =
            VoyageReply.newBuilder()
                .setMessage(SUCCESS)
                .setStatus(SUCCESS)
                .setVoyageId(voyage.getId())
                .build();
      }
    } catch (Exception e) {

      log.error("Error in saving Voyage ", e);
      reply = VoyageReply.newBuilder().setMessage("FAIL").setStatus("FAIL").build();

    } finally {
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }


  /**
   * Method to find list of loadable studies based on vessel and voyage
   *
   * @param {@link LoadableStudyRequest} - The grpc generated message
   * @param {@link StreamObserver}
   */
  @Override
  public void findLoadableStudiesByVesselAndVoyage(
      LoadableStudyRequest request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = null;
    try {
      log.info("inside loadable study service - findLoadableStudiesByVesselAndVoyage");
      List<LoadableStudy> loadableStudies =
          this.loadableStudyRepository.findByVesselXIdAndVoyage(
              request.getVesselId(), this.voyageRepository.getOne(request.getVoyageId()));
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);
      for (LoadableStudy study : loadableStudies) {
        replyBuilder.addLoadableStudies(
            LoadableStudyDetail.newBuilder()
                .setId(study.getId())
                .setName(study.getName())
                .setDetail(study.getDetails())
                .setCreatedDate(dateTimeFormatter.format(study.getCreatedDate()))
                .setStatus(study.getLoadableStudyStatus())
                .setCharterer(study.getCharterer())
                .setSubCharterer(study.getSubCharterer())
                .setDraftMark(
                    String.valueOf(
                        study.getDraftMark())) // TODO check for Bigdecimal usage with GRPC
                .setLoadLineXId(study.getLoadLineXId())
                .setDraftRestriction(String.valueOf(study.getDraftRestriction()))
                .setMaxTempExpected(String.valueOf(study.getMaxTempExpected()))
                .build());
      }

    } catch (Exception e) {
      log.error("Error fetching loadable studies", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(StatusReply.newBuilder().setStatus(FAILED).build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
