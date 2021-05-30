/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.component;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.task.manager.service.SchedulerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** SchedulerStartUpHandler Class to start all scheduled jobs when application starts */
@Log4j2
@Component
public class SchedulerStartUpHandler implements ApplicationRunner {

  @Autowired private SchedulerService schedulerService;

  /**
   * run scheduler
   *
   * @param args - Application Arguments
   * @throws GenericServiceException - Custom exceptions thrown from service.
   */
  @Override
  public void run(final ApplicationArguments args) throws GenericServiceException {

    schedulerService.startAllSchedulers();
    log.info("Schedule all new scheduler jobs at app startup - complete");
  }
}
