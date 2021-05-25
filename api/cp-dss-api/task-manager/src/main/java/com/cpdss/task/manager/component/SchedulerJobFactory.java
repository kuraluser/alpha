/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.component;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/** SchedulerJobFactory Class for SchedulerConfig */
public class SchedulerJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

  private AutowireCapableBeanFactory beanFactory;

  /**
   * Set application context
   *
   * @param context Application context
   */
  @Override
  public void setApplicationContext(final ApplicationContext context) {
    beanFactory = context.getAutowireCapableBeanFactory();
  }

  /**
   * Create job instance
   *
   * @param bundle Trigger Fired Bundle
   * @return job Object
   * @throws Exception UnExpected error
   */
  @Override
  protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
    final Object job = super.createJobInstance(bundle);
    beanFactory.autowireBean(job);
    return job;
  }
}
