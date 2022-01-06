/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.constants.AlgoErrorHeaderConstants;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.AlgoErrors;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.repository.AlgoErrorHeadingRepository;
import com.cpdss.loadablestudy.repository.AlgoErrorsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlgoErrorService {

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Autowired AlgoErrorsRepository algoErrorsRepository;

  @Autowired AlgoErrorHeadingRepository algoErrorHeadingRepository;

  @Autowired ScheduledTaskRequest scheduledTaskRequest;

  @Autowired LoadableStudyRepository loadableStudyRepository;

  @Autowired LoadablePatternRepository loadablePatternRepository;

  @Autowired LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  /** Save GRPC Algo Error into Entity Object */
  public void saveAlgoError(
      com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
      StreamObserver<LoadableStudy.AlgoErrors> responseObserver) {
    LoadableStudy.AlgoErrors.Builder builder = LoadableStudy.AlgoErrors.newBuilder();
    AlgoErrorHeading heading = null;
    try {
      if (request.getErrorHeading().length() > 0) {
        // check existing or not
        Optional<AlgoErrorHeading> alErHead =
            algoErrorHeadingRepository.findByErrorHeading(request.getErrorHeading());
        if (alErHead.isPresent()) {
          heading = alErHead.get();
        } else {
          heading = new AlgoErrorHeading();
          heading.setErrorHeading(request.getErrorHeading());
          algoErrorHeadingRepository.save(heading);
        }
      }
      log.info("Alog Error heading and id {}, {}", heading.getId(), heading.getErrorHeading());
      List<String> rep = request.getErrorMessagesList();
      for (int i = 0; i < rep.size(); i++) {
        String var1 = rep.get(i);
        AlgoErrors algoErrors = new AlgoErrors();
        algoErrors.setErrorMessage(var1);
        algoErrors.setAlgoErrorHeading(heading);
        algoErrorsRepository.save(algoErrors);
      }
      log.info("Alog error messages added, Size - {}", builder.getErrorMessagesCount());
      builder.setId(heading.getId());
      builder.addAllErrorMessages(rep);
      builder.setErrorHeading(heading.getErrorHeading());
      builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(
          Common.ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED));
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Saves ALGO Internal Server Error details
   *
   * @param id
   * @param processId
   * @param isPatternError
   * @param error
   */
  public void saveAlgoInternalServerError(
      Long id, String processId, Boolean isPatternError, String error) {
    algoErrorsRepository.deleteAlgoErrorByLSId(false, id);
    algoErrorHeadingRepository.deleteAlgoErrorHeadingByLSId(false, id);
    // algoService.saveAlgoErrorToDB(request, new LoadablePattern(), loadableStudyOpt.get(), false);
    AlgoErrorHeading algoErrorHeading = new AlgoErrorHeading();
    algoErrorHeading.setErrorHeading(AlgoErrorHeaderConstants.ALGO_INTERNAL_SERVER_ERROR);
    if (isPatternError) {
      Optional<LoadablePattern> loadablePatternOpt =
          loadablePatternRepository.findByIdAndIsActive(id, true);
      algoErrorHeading.setLoadablePattern(loadablePatternOpt.get());
    } else {
      Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
          loadableStudyRepository.findByIdAndIsActive(id, true);
      algoErrorHeading.setLoadableStudy(loadableStudyOpt.get());
    }
    algoErrorHeading.setIsActive(true);
    algoErrorHeadingRepository.save(algoErrorHeading);
    com.cpdss.loadablestudy.entity.AlgoErrors algoErrors =
        new com.cpdss.loadablestudy.entity.AlgoErrors();
    algoErrors.setAlgoErrorHeading(algoErrorHeading);
    algoErrors.setErrorMessage(error);
    algoErrors.setIsActive(true);
    algoErrorsRepository.save(algoErrors);
    if (processId != null) {
      AlgoErrors processIdMsg = new AlgoErrors();
      processIdMsg.setAlgoErrorHeading(algoErrorHeading);
      processIdMsg.setErrorMessage(processId);
      processIdMsg.setIsActive(true);
      algoErrorsRepository.save(processIdMsg);
    }
  }
}
