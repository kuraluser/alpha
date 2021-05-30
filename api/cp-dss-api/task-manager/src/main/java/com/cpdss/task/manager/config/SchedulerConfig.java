/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.config;

import com.cpdss.task.manager.component.SchedulerJobFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** SchedulerConfig Class */
@Configuration
public class SchedulerConfig {

  /**
   * create scheduler factory
   *
   * @param dataSource - Data Source.
   * @param applicationContext - Application Context.
   * @param quartzProperties - Quartz Properties.
   * @return SchedulerFactoryBean
   */
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(
      final DataSource dataSource,
      final ApplicationContext applicationContext,
      final QuartzProperties quartzProperties) {

    final SchedulerJobFactory jobFactory = new SchedulerJobFactory();
    jobFactory.setApplicationContext(applicationContext);

    final Properties properties = new Properties();
    properties.putAll(quartzProperties.getProperties());

    final SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setOverwriteExistingJobs(false);
    factory.setDataSource(dataSource);
    factory.setQuartzProperties(properties);
    factory.setJobFactory(jobFactory);
    return factory;
  }
}
