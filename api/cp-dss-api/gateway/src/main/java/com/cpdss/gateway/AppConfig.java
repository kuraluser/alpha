/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.grpc.GrpcClientConfig;
import com.cpdss.common.logging.Log4j2Config;
import com.cpdss.common.redis.CacheConfig;
import com.cpdss.common.redis.RedisConfig;
import com.cpdss.common.rest.RestConfig;
import com.cpdss.common.springdata.SpringDataConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Configuration manager to add configurations for external systems */
@Configuration
@Import({
  CommonConfig.class,
  Log4j2Config.class,
  RestConfig.class,
  SpringDataConfig.class,
  GrpcClientConfig.class,
  RedisConfig.class,
  CacheConfig.class
})
public class AppConfig {}
