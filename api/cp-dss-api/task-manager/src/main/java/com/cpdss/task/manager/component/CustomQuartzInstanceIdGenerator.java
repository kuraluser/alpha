/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.component;

import java.util.UUID;
import org.quartz.SchedulerException;
import org.quartz.spi.InstanceIdGenerator;

/** CustomQuartzInstanceIdGenerator Class */
public class CustomQuartzInstanceIdGenerator implements InstanceIdGenerator {

  /**
   * Generate instance id
   *
   * @return randomUUID
   * @throws SchedulerException - Exception thrown by the Quartz
   */
  @Override
  public String generateInstanceId() throws SchedulerException {
    try {
      return UUID.randomUUID().toString();
    } catch (Exception ex) {
      throw new SchedulerException("Couldn't generate UUID!", ex);
    }
  }
}
