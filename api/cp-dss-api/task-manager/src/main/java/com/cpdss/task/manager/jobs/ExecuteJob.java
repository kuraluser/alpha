/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.jobs;

import static com.cpdss.task.manager.commons.TaskManagerConstants.*;

import com.cpdss.task.manager.commons.TaskManagerConstants;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/** Job Class to Execute The Job */
@Log4j2
@Component
public class ExecuteJob extends QuartzJobBean {

  @Autowired private RestTemplate restTemplate;

  /**
   * executeInternal call the target job url using restTemplate. In case in error it try to refire
   * the job.
   *
   * @param context - JobExecutionContext.
   * @throws JobExecutionException - JobExecutionException
   */
  @Override
  protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
    final JobDetail detail = context.getJobDetail();
    log.info(JOB_STARTED + detail.getKey());
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<String> requestEntity =
        new HttpEntity<>(detail.getJobDataMap().getString(REQUEST_BODY), headers);
    try {
      final ResponseEntity<String> responseEntity =
          restTemplate.exchange(
              detail.getJobDataMap().getString(TASK_URL),
              HttpMethod.POST,
              requestEntity,
              String.class);
      log.info("output..: " + responseEntity.getBody());
      log.info(JOB_COMPLETED + detail.getKey());
    } catch (RestClientException e) {

      // In case any error during rest call, the job will refire immediately
      //	if the time interval is greater or equal to one hour.
      final JobExecutionException jobException = new JobExecutionException(e);
      if (detail.getJobDataMap().getInt(TIME_FREQUENCY) >= TaskManagerConstants.JOB_REFIRE_VALUE) {
        log.info(JOB_REFIRED + detail.getKey());
        jobException.setRefireImmediately(true);
      }
      log.error(detail.getKey() + JOB_INTERRUPTED + e.getMessage());
    }
  }
}
