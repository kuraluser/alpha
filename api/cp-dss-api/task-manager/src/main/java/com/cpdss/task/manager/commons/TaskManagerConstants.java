/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.commons;

/** CommonContents class */
public final class TaskManagerConstants {

  public static final String TASK_URL = "url";
  public static final String TIME_FREQUENCY = "frequency";
  public static final String REQUEST_BODY = "body";
  public static final String JOB_CLASS = "com.rambus.cmcc.tss.jobs.ExecuteJob";
  public static final String JOB_GROUP = "CPDSS";
  public static final int JOB_REFIRE_VALUE = 3600;
  public static final String JOB_STARTED = "job execution started, task name : ";
  public static final String JOB_COMPLETED = "job execution completed, task name : ";
  public static final String JOB_INTERRUPTED = " job execution interrupted due to : ";
  public static final String JOB_REFIRED = "job execution refired, task name : ";

  private TaskManagerConstants() {}
}
