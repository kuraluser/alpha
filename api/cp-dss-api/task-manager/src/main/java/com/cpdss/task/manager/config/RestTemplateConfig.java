/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** RestTemplateConfig Class */
@Configuration
public class RestTemplateConfig {

  /**
   * Get RestTemplate bean
   *
   * @return RestTemplate instance
   */
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
