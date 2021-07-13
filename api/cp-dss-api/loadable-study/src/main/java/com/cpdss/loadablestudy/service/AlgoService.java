/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.INVALID_LOADABLE_STUDY_ID;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import com.cpdss.loadablestudy.repository.*;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class AlgoService {

  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  @Autowired AlgoErrorHeadingRepository algoErrorHeadingRepository;

  @Autowired LoadableStudyRepository loadableStudyRepository;

  @Autowired LoadablePatternRepository loadablePatternRepository;

  @Autowired AlgoErrorsRepository algoErrorsRepository;

  public LoadableStudy.AlgoStatusReply.Builder saveAlgoLoadableStudyStatus(
      LoadableStudy.AlgoStatusRequest request, LoadableStudy.AlgoStatusReply.Builder replyBuilder) {
    log.info("Inside saveAlgoLoadableStudyStatus service");
    Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
        loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(request.getProcesssId(), true);
    if (!loadableStudyAlgoStatusOpt.isPresent()) {
      log.info("Invalid process id for updating loadable study status");
      replyBuilder.setResponseStatus(
          Common.ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
              .setMessage("Invalid process Id")
              .build());
    } else {
      log.info(
          "updated algo status with process-id "
              + request.getProcesssId()
              + " to "
              + request.getLoadableStudystatusId());
      loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
          request.getLoadableStudystatusId(), request.getProcesssId(), true);
      replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    }
    return replyBuilder;
  }

  public void fetchAllErrors(
      com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
      StreamObserver<LoadableStudy.AlgoErrors> responseObserver) {
    String errorHeading = request.getErrorHeading();
    LoadableStudy.AlgoErrors.Builder builder = LoadableStudy.AlgoErrors.newBuilder();
    try {
      if (errorHeading != null && errorHeading.length() > 0) {
        Optional<List<AlgoErrorHeading>> alogError =
            algoErrorHeadingRepository.findAllByErrorHeading(errorHeading);
        if (alogError.isPresent()) {
          log.info(
              "Alog Error for Heading {}, Found with size {}",
              errorHeading,
              alogError.get().size());
          for (AlgoErrorHeading alEr : alogError.get()) {
            List<String> res = new ArrayList<>();
            res.addAll(
                alEr.getAlgoErrors().stream()
                    .map(val -> val.getErrorMessage())
                    .collect(Collectors.toList()));
            builder.addAllErrorMessages(res);
            builder.setErrorHeading(request.getErrorHeading());
          }
        }
      }
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
   * Algorithm Error For Loadable Study
   *
   * @param loadableStudy - Object
   * @param replyBuilder - GRPC Object
   */
  public void buildLoadableStudyErrorDetails(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.Builder replyBuilder) {

    Optional<List<AlgoErrorHeading>> alogError =
        algoErrorHeadingRepository.findByLoadableStudyAndIsActive(loadableStudy, true);
    if (alogError.isPresent()) {
      log.info("Adding ALGO error");
      for (AlgoErrorHeading errorHeading : alogError.get()) {
        LoadableStudy.AlgoErrors.Builder errorBuilder = LoadableStudy.AlgoErrors.newBuilder();

        Optional<List<com.cpdss.loadablestudy.entity.AlgoErrors>> algoError =
            algoErrorsRepository.findByAlgoErrorHeadingAndIsActive(errorHeading, true);
        if (algoError.isPresent()) {
          List<String> res = new ArrayList<>();
          res.addAll(
              algoError.get().stream()
                  .map(val -> val.getErrorMessage())
                  .collect(Collectors.toList()));
          errorBuilder.addAllErrorMessages(res);
        }

        errorBuilder.setErrorHeading(errorHeading.getErrorHeading());
        replyBuilder.addAlgoErrors(errorBuilder);
      }
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * @param request
   * @param loadableStudy
   * @param loadablePattern
   * @param isPatternErrorSaving
   */
  public void saveAlgoErrorToDB(
      LoadableStudy.LoadablePatternAlgoRequest request,
      LoadablePattern loadablePattern,
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy,
      boolean isPatternErrorSaving) {
    request
        .getAlgoErrorsList()
        .forEach(
            algoError -> {
              AlgoErrorHeading algoErrorHeading = new AlgoErrorHeading();
              algoErrorHeading.setErrorHeading(algoError.getErrorHeading());
              if (isPatternErrorSaving) {
                algoErrorHeading.setLoadablePattern(loadablePattern);
              } else {
                algoErrorHeading.setLoadableStudy(loadableStudy);
              }
              algoErrorHeading.setIsActive(true);
              algoErrorHeadingRepository.save(algoErrorHeading);
              algoError
                  .getErrorMessagesList()
                  .forEach(
                      error -> {
                        com.cpdss.loadablestudy.entity.AlgoErrors algoErrors =
                            new com.cpdss.loadablestudy.entity.AlgoErrors();
                        algoErrors.setAlgoErrorHeading(algoErrorHeading);
                        algoErrors.setErrorMessage(error);
                        algoErrors.setIsActive(true);
                        algoErrorsRepository.save(algoErrors);
                      });
            });
  }

  public LoadableStudy.AlgoErrorReply.Builder getAlgoErrors(
      LoadableStudy.AlgoErrorRequest request, LoadableStudy.AlgoErrorReply.Builder replyBuilder)
      throws GenericServiceException {
    if (request.getLoadablePatternId() != 0) {
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
      if (!loadablePatternOpt.isPresent()) {
        log.info(INVALID_LOADABLE_PATTERN_ID, request.getLoadablePatternId());
        throw new GenericServiceException(
            INVALID_LOADABLE_PATTERN_ID,
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);

      } else {
        log.info("inside getAlgoErrors loadable study service - getting error details");
        buildLoadablePatternErrorDetails(loadablePatternOpt.get(), replyBuilder);
      }
    } else {
      if (request.getLoadableStudyId() > 0) {
        Optional<com.cpdss.loadablestudy.entity.LoadableStudy> ls =
            loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
        if (!ls.isPresent()) {
          log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadablePatternId());
          throw new GenericServiceException(
              INVALID_LOADABLE_STUDY_ID,
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        log.info("Algo Error Fetch, Loadable Study, id - {}", ls.get().getId());
        buildLoadableStudyErrorDetails(ls.get(), replyBuilder);
      }
    }
    return replyBuilder;
  }

  /**
   * @param loadablePattern
   * @param replyBuilder void
   */
  private void buildLoadablePatternErrorDetails(
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.Builder replyBuilder) {

    Optional<List<AlgoErrorHeading>> alogError =
        algoErrorHeadingRepository.findByLoadablePatternAndIsActive(loadablePattern, true);
    if (alogError.isPresent()) {
      log.info("Adding ALGO error");
      for (AlgoErrorHeading errorHeading : alogError.get()) {
        LoadableStudy.AlgoErrors.Builder errorBuilder = LoadableStudy.AlgoErrors.newBuilder();

        Optional<List<com.cpdss.loadablestudy.entity.AlgoErrors>> algoError =
            algoErrorsRepository.findByAlgoErrorHeadingAndIsActive(errorHeading, true);
        if (algoError.isPresent()) {
          List<String> res = new ArrayList<>();
          res.addAll(
              algoError.get().stream()
                  .map(val -> val.getErrorMessage())
                  .collect(Collectors.toList()));
          errorBuilder.addAllErrorMessages(res);
        }

        errorBuilder.setErrorHeading(errorHeading.getErrorHeading());
        replyBuilder.addAlgoErrors(errorBuilder);
      }
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }
}
