/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.grpc.GrpcConfig;
import com.cpdss.common.logging.Log4j2Config;
import com.cpdss.common.springdata.SpringDataConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({CommonConfig.class, Log4j2Config.class, GrpcConfig.class, SpringDataConfig.class})
public class AppConfig {

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
