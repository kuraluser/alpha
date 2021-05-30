/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.grpc.GrpcServerConfig;
import com.cpdss.common.logging.Log4j2Config;
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
  GrpcServerConfig.class
})
public class AppConfig {}
