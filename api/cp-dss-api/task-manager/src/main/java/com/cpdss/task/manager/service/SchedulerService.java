/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.task.manager.domain.SchedulerJobInfoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** SchedulerService Interface */
public interface SchedulerService {

  /**
   * To Schedule all new scheduler jobs at app startup.
   *
   * @throws GenericServiceException - throws GenericServiceException.
   */
  void startAllSchedulers() throws GenericServiceException;

  /**
   * To schedule new job
   *
   * @param jobInfo - Details of the job that need to schedule.
   * @return SchedulerJobInfoRequest.
   * @throws GenericServiceException - throws GenericServiceException.
   */
  SchedulerJobInfoRequest scheduleNewJob(SchedulerJobInfoRequest jobInfo)
      throws GenericServiceException;

  /**
   * List all the scheduled jobs
   *
   * @param pageable - Pageable
   * @return Page of SchedulerJobInfoRequest
   * @throws GenericServiceException - throws GenericServiceException.
   */
  Page<SchedulerJobInfoRequest> findAll(Pageable pageable) throws GenericServiceException;

  /**
   * Delete scheduled job.
   *
   * @param taskName - task name.
   * @throws GenericServiceException - throws GenericServiceException.
   */
  void deleteJob(String taskName) throws GenericServiceException;
}
