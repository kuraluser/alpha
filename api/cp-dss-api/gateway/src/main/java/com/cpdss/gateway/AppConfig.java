/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.grpc.GrpcClientConfig;
import com.cpdss.common.logging.Log4j2Config;
import com.cpdss.common.redis.CacheConfig;
import com.cpdss.common.redis.RedisConfig;
import com.cpdss.common.rest.RestConfig;
import com.cpdss.common.scheduler.ScheduledTaskConfig;
import com.cpdss.common.springdata.SpringDataConfig;
import com.cpdss.gateway.service.SyncRedisMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/** Configuration manager to add configurations for external systems */
@Configuration
@Import({
  CommonConfig.class,
  Log4j2Config.class,
  RestConfig.class,
  SpringDataConfig.class,
  GrpcClientConfig.class,
  RedisConfig.class,
  CacheConfig.class,
  ScheduledTaskConfig.class
})
public class AppConfig {

  @Autowired private SyncRedisMasterService syncRedisMasterService;

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext context) {
    return args -> {
      syncRedisMasterService.updateRedisFromMasterData();
    };
  }
}
