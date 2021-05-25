/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.component;

import java.text.ParseException;
import java.util.Date;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/** JobSchedulerCreator for create job and trigger */
@Component
public class JobScheduleCreator {

  /**
   * Create Quartz Job.
   *
   * @param jobClass - Class whose executeInternal() method needs to be called.
   * @param isDurable - Specify the job's durability, i.e. whether it should remain stored in the
   *     job store.
   * @param context - Spring application context.
   * @param jobName - Job name.
   * @param jobGroup - Job group.
   * @param map - job data map.
   * @return JobDetail object
   */
  public JobDetail createJob(
      final Class<? extends QuartzJobBean> jobClass,
      final boolean isDurable,
      final ApplicationContext context,
      final String jobName,
      final String jobGroup,
      final JobDataMap map) {
    final JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(jobClass);
    factoryBean.setDurability(isDurable);
    factoryBean.setApplicationContext(context);
    factoryBean.setName(jobName);
    factoryBean.setGroup(jobGroup);
    factoryBean.setRequestsRecovery(true);
    factoryBean.setDescription(jobName + jobGroup);
    map.put(jobName + jobGroup, jobClass.getName());
    factoryBean.setJobDataMap(map);

    factoryBean.afterPropertiesSet();

    return factoryBean.getObject();
  }

  /**
   * Create simple trigger.
   *
   * @param triggerName - Trigger name.
   * @param startTime - Trigger start time.
   * @param endTime - Trigger end time.
   * @param repeatTime - Job repeat period mills
   * @param misFireInstruction - Misfire instruction (what to do in case of misfire happens).
   * @param jobGroup - Job group.
   * @return {@link SimpleTrigger}
   */
  public SimpleTrigger createSimpleTrigger(
      final String triggerName,
      final Date startTime,
      final Date endTime,
      final Long repeatTime,
      final int misFireInstruction,
      final String jobGroup) {

    final SimpleTriggerImpl simple = new SimpleTriggerImpl();
    simple.setStartTime(startTime);
    simple.setEndTime(endTime);
    simple.setGroup(jobGroup);
    simple.setName(triggerName);
    simple.setRepeatInterval(repeatTime);
    simple.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    simple.setMisfireInstruction(misFireInstruction);
    return simple;
  }

  /**
   * Create cron trigger.
   *
   * @param triggerName - Trigger name.
   * @param cronExpression - Cron expression.
   * @param misFireInstruction - Misfire instruction (what to do in case of misfire happens).
   * @param jobGroup - Job group.
   * @return CronTrigger
   * @throws ParseException - Error has been reached unexpectedly while parsing.
   */
  public CronTrigger createCronTrigger(
      final String triggerName,
      final String cronExpression,
      final int misFireInstruction,
      final String jobGroup)
      throws ParseException {

    final CronTriggerImpl cronTrigger = new CronTriggerImpl();
    cronTrigger.setCronExpression(cronExpression);
    cronTrigger.setName(triggerName);
    cronTrigger.setGroup(jobGroup);
    cronTrigger.setMisfireInstruction(misFireInstruction);
    return cronTrigger;
  }
}
