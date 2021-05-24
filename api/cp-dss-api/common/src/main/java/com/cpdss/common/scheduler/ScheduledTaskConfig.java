package com.cpdss.common.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;

/**
 * Default configuration class for ScheduledTaskRequest
 *
 * @author r.krishnakumar
 */
@Configuration
@DependsOn({"log", "GrpcChannelConfigurer"})
public class ScheduledTaskConfig {

    /**
     * Initializing Task Scheduled Request Bean
     *
     * @return
     */
    @Bean
    public ScheduledTaskRequest scheduledTaskRequest() {
        return new ScheduledTaskRequest();
    }


    /**
     * Initializing Task Execution Bean and Listener
     *
     * @return
     */
    @Bean
    public ExecuteTask executeTask() {
        return new ExecuteTask();
    }

}
