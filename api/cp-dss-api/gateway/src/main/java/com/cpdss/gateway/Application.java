/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway;

import com.cpdss.gateway.service.SyncRedisMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/** Bootstrap class for SpringData test microservice */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Autowired SyncRedisMasterService syncRedisMasterService;

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext context) {
    return args -> {
      syncRedisMasterService.updateRedisFromMasterData();
    };
  }
}
