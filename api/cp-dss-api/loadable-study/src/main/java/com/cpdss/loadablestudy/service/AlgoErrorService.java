/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.scheduler.ScheduledTaskRequest;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.AlgoErrors;
import com.cpdss.loadablestudy.repository.AlgoErrorHeadingRepository;
import com.cpdss.loadablestudy.repository.AlgoErrorsRepository;
import io.grpc.stub.StreamObserver;
import java.util.*;
import java.util.stream.Collectors;
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

  /*
   * @PostConstruct public void init() { new Thread( () -> { try { Thread.sleep(15
   * * 1000); System.out.println("EXECUTING"); LocalDateTime dateTime =
   * LocalDateTime.now(); LocalDateTime endDateTime =
   * dateTime.plus(Duration.ofDays(2)); ScheduledTaskProperties properties = new
   * ScheduledTaskProperties(); properties.setTaskName("TEST ASYNC TASK NAME");
   * properties.setTaskFrequency(30);
   * properties.setTaskType(ScheduledTaskProperties.TaskTypeEnum.ASYNC);
   * properties.setTaskStartDate(dateTime.toLocalDate());
   * properties.setTaskStartTime(dateTime.toLocalTime());
   * properties.setTaskEndDate(endDateTime.toLocalDate());
   * properties.setTaskEndTime(endDateTime.toLocalTime());
   * properties.setTaskURI("localhost:9090"); Map<String, String> requestParam =
   * new HashMap<>(); requestParam.put("test", "myval");
   * properties.setTaskReqParam(requestParam);
   * scheduledTaskRequest.createScheduledTaskRequest(properties);
   *
   * } catch (InterruptedException | GenericServiceException e) {
   * e.printStackTrace(); } }) .start(); }
   */

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

  public void fetchAllErrors(
      com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors> responseObserver) {
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
}
