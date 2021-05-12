/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.service.impl;

import static com.cpdss.common.rest.CommonErrorCodes.*;
import static com.cpdss.task.manager.commons.TaskManagerConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.task.manager.component.JobScheduleCreator;
import com.cpdss.task.manager.domain.SchedulerJobInfoRequest;
import com.cpdss.task.manager.entity.SchedulerJobInfo;
import com.cpdss.task.manager.jobs.ExecuteJob;
import com.cpdss.task.manager.repository.SchedulerRepository;
import com.cpdss.task.manager.service.SchedulerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation Class for Schedule job */
@Log4j2
@Transactional
@Service
public class SchedulerServiceImpl implements SchedulerService {

  @Autowired private SchedulerFactoryBean schedulerFactoryBean;

  @Autowired private SchedulerRepository schedulerRepository;

  @Autowired private ApplicationContext context;

  @Autowired private JobScheduleCreator scheduleCreator;

  @Autowired private ObjectMapper mapper;

  private static final String JOB_KEY_ALREADY_EXIST = "scheduled.job.already.exist";
  private static final String JOB_SCHEDULER_INTERNAL_ERROR = "scheduler.internal.error";
  private static final String INTERNAL_ERROR = "task.internal.error";
  private static final String INVALID_CRON_EXPRESSION = "invalid.cron.expression";
  private static final String INVALID_TASK_URL = "invalid.task.url";
  private static final String TASK_NOT_FOUND = "task.not.found";

  /**
   * To Schedule all new scheduler jobs at app startup.
   *
   * @throws GenericServiceException - throws GenericServiceException.
   */
  @Override
  public void startAllSchedulers() throws GenericServiceException {
    final List<SchedulerJobInfo> jobInfoList = schedulerRepository.findAll();
    if (!jobInfoList.isEmpty()) {
      final Scheduler scheduler = schedulerFactoryBean.getScheduler();
      for (final SchedulerJobInfo jobInfo : jobInfoList) {
        createJobDetailJobTriggerAndScheduleJob(scheduler, jobInfo);
      }
    }
  }

  /**
   * To schedule new job
   *
   * @param jobInfoRequest - Details of the job that need to schedule.
   * @throws GenericServiceException - throws GenericServiceException.
   */
  @Override
  public SchedulerJobInfoRequest scheduleNewJob(final SchedulerJobInfoRequest jobInfoRequest)
      throws GenericServiceException {
    if (jobInfoRequest.getCronExpression() == null) {
      validateSchedulerJobInfo(jobInfoRequest);
    }
    final SchedulerJobInfo jobInfo = mapper.convertValue(jobInfoRequest, SchedulerJobInfo.class);
    final Scheduler scheduler = schedulerFactoryBean.getScheduler();
    jobInfo.setJobGroup(JOB_GROUP);

    createJobDetailJobTriggerAndScheduleJob(scheduler, jobInfo);
    final SchedulerJobInfo jobInfoResponse = schedulerRepository.save(jobInfo);
    return mapper.convertValue(jobInfoResponse, SchedulerJobInfoRequest.class);
  }

  /**
   * validate scheduler job info
   *
   * @param jobInfoRequest - SchedulerJobInfoRequest
   * @throws GenericServiceException - throws GenericServiceException.
   */
  private void validateSchedulerJobInfo(final SchedulerJobInfoRequest jobInfoRequest)
      throws GenericServiceException {

    if (jobInfoRequest.getTaskFrequency() == null) {
      throw new GenericServiceException(
          "dto.task.frequency.must.not.blank", E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    if (jobInfoRequest.getTaskStartDate() == null) {
      throw new GenericServiceException(
          "dto.task.start.date.must.not.blank", E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    if (jobInfoRequest.getTaskEndDate() == null) {
      throw new GenericServiceException(
          "dto.task.end.date.must.not.blank", E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    if (jobInfoRequest.getTaskStartTime() == null) {
      throw new GenericServiceException(
          "dto.task.start.time.must.not.blank", E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    if (jobInfoRequest.getTaskEndTime() == null) {
      throw new GenericServiceException(
          "dto.task.end.time.must.not.blank", E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    if (jobInfoRequest.getTaskStartDate().isAfter(jobInfoRequest.getTaskEndDate())) {
      throw new GenericServiceException(
          "dto.task.startDate.must.less.than.endDate",
          E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (jobInfoRequest.getTaskStartDate().isEqual(jobInfoRequest.getTaskEndDate())
        && jobInfoRequest.getTaskStartTime().isAfter(jobInfoRequest.getTaskEndTime())) {
      throw new GenericServiceException(
          "dto.task.startTime.must.less.than.endTime",
          E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * To create the jobDetail, JobTrigger and schedule the job.
   *
   * @param scheduler - Scheduler
   * @param jobInfo - Details of the job that need to schedule.
   * @throws GenericServiceException - throws GenericServiceException.
   */
  private void createJobDetailJobTriggerAndScheduleJob(
      final Scheduler scheduler, final SchedulerJobInfo jobInfo) throws GenericServiceException {

    try {
      JobDetail jobDetail =
          JobBuilder.newJob(ExecuteJob.class)
              .withIdentity(jobInfo.getTaskName(), jobInfo.getJobGroup())
              .build();
      if (!scheduler.checkExists(jobDetail.getKey())) {

        jobDetail = createJobDetails(jobInfo);
        final Trigger trigger = createTriggerForTheJob(jobInfo);
        scheduler.scheduleJob(jobDetail, trigger);
      } else {
        log.error(JOB_KEY_ALREADY_EXIST);
        throw new GenericServiceException(
            JOB_KEY_ALREADY_EXIST, E_HTTP_CONFLICT, HttpStatusCode.CONFLICT);
      }
    } catch (final SchedulerException e) {
      log.error(JOB_SCHEDULER_INTERNAL_ERROR);
      throw new GenericServiceException(
          JOB_SCHEDULER_INTERNAL_ERROR,
          E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e);
    } catch (final ParseException e) {
      log.error(INVALID_CRON_EXPRESSION);
      throw new GenericServiceException(
          INVALID_CRON_EXPRESSION, E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    } catch (final IllegalArgumentException e) {
      log.error(e.getMessage());
      throw new GenericServiceException(
          e.getMessage(), E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * To create jobDataMap for the corresponding job
   *
   * @param jobInfo - Details of the job that need to schedule.
   * @return JobDataMap - Data needed for the job at the time of execution.
   * @throws IllegalArgumentException
   */
  private JobDataMap createJobDataMap(final SchedulerJobInfo jobInfo) {

    final Boolean urlValid = isUrlValid(jobInfo.getTaskURI());
    if (Boolean.FALSE.equals(urlValid)) {
      throw new IllegalArgumentException(INVALID_TASK_URL);
    }
    final JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put(TASK_URL, jobInfo.getTaskURI());
    jobDataMap.put(TIME_FREQUENCY, String.valueOf(jobInfo.getTaskFrequency()));
    jobDataMap.put(REQUEST_BODY, new Gson().toJson(jobInfo.getTaskReqParam()));

    return jobDataMap;
  }

  /**
   * To create job details of the job
   *
   * @param jobInfo - Details of the job that need to schedule.
   * @return JobDetail
   */
  private JobDetail createJobDetails(final SchedulerJobInfo jobInfo) {

    return scheduleCreator.createJob(
        ExecuteJob.class,
        false,
        context,
        jobInfo.getTaskName(),
        jobInfo.getJobGroup(),
        createJobDataMap(jobInfo));
  }

  /**
   * To create the trigger for the corresponding job
   *
   * @param jobInfo - Details of the job that need to schedule.
   * @return trigger - Trigger to fire the job on scheduled time.
   * @throws ParseException - throws ParseException.
   */
  private Trigger createTriggerForTheJob(final SchedulerJobInfo jobInfo) throws ParseException {

    if (jobInfo.getCronExpression() != null) {
      if (!CronExpression.isValidExpression(jobInfo.getCronExpression())) {
        throw new ParseException(INVALID_CRON_EXPRESSION, 0);
      }
      return scheduleCreator.createCronTrigger(
          jobInfo.getTaskName(),
          jobInfo.getCronExpression(),
          SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW,
          jobInfo.getJobGroup());
    } else {

      final long frequencyInMillySec = jobInfo.getTaskFrequency() * 1000L;

      final LocalDateTime startLdt =
          LocalDateTime.of(jobInfo.getTaskStartDate(), jobInfo.getTaskStartTime());
      final Date startDate = Date.from(startLdt.atZone(ZoneId.systemDefault()).toInstant());

      final LocalDateTime endLdt =
          LocalDateTime.of(jobInfo.getTaskEndDate(), jobInfo.getTaskEndTime());
      final Date endDate = Date.from(endLdt.atZone(ZoneId.systemDefault()).toInstant());

      return scheduleCreator.createSimpleTrigger(
          jobInfo.getTaskName(),
          startDate,
          endDate,
          frequencyInMillySec,
          SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW,
          jobInfo.getJobGroup());
    }
  }

  /**
   * To List all the scheduled jobs
   *
   * @return Page of SchedulerJobInfoDTO
   * @throws GenericServiceException - throws GenericServiceException.
   */
  @Override
  public Page<SchedulerJobInfoRequest> findAll(final Pageable pageable)
      throws GenericServiceException {

    try {
      final Page<SchedulerJobInfo> jobInfos = schedulerRepository.findAll(pageable);
      return toPageObjectDto(jobInfos);
    } catch (final Exception e) {
      log.error(INTERNAL_ERROR);
      throw new GenericServiceException(
          INTERNAL_ERROR, E_HTTP_INTERNAL_SERVER_ERROR, HttpStatusCode.INTERNAL_SERVER_ERROR, e);
    }
  }

  /**
   * Delete a scheduled job.
   *
   * @param taskName - task name.
   * @throws GenericServiceException - throws GenericServiceException.
   */
  @Override
  public void deleteJob(final String taskName) throws GenericServiceException {

    try {
      final Optional<SchedulerJobInfo> jobInfo = schedulerRepository.findByTaskName(taskName);
      if (jobInfo.isPresent()) {
        final JobKey key = new JobKey(jobInfo.get().getTaskName(), jobInfo.get().getJobGroup());
        schedulerFactoryBean.getScheduler().deleteJob(key);
        schedulerRepository.deleteById(jobInfo.get().getId());
        log.info(taskName + " job deleted successfully");
      } else {
        log.error(TASK_NOT_FOUND);
        throw new GenericServiceException(
            TASK_NOT_FOUND, E_HTTP_NOT_FOUND, HttpStatusCode.NOT_FOUND);
      }
    } catch (final SchedulerException e) {
      log.error(JOB_SCHEDULER_INTERNAL_ERROR);
      throw new GenericServiceException(
          JOB_SCHEDULER_INTERNAL_ERROR,
          E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e);
    }
  }

  /**
   * convert Page<SchedulerJobInfo> entitys To Page<SchedulerJobInfoDTO> dtos
   *
   * @param schedulerJobInfoPage - Page of SchedulerJobInfo
   * @return Page of SchedulerJobInfoRequest
   */
  private Page<SchedulerJobInfoRequest> toPageObjectDto(
      final Page<SchedulerJobInfo> schedulerJobInfoPage) {

    return schedulerJobInfoPage.map(this::convertToObjectDto);
  }

  /**
   * Map SchedulerJobInfo entity To SchedulerJobInfoDTO dto
   *
   * @param jobInfo - Details of the job that need to schedule.
   * @return SchedulerJobInfoRequest
   */
  private SchedulerJobInfoRequest convertToObjectDto(final SchedulerJobInfo jobInfo) {

    return mapper.convertValue(jobInfo, SchedulerJobInfoRequest.class);
  }

  /**
   * Check url is a valid URL
   *
   * @param url - string url
   * @return boolean
   */
  private Boolean isUrlValid(final String url) {
    try {
      new URL(url).toURI();
      return true;

    } catch (final Exception e) {
      return false;
    }
  }
}
