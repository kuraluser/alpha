/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
  GrpcServerAutoConfiguration.class, // Create required server beans
  GrpcServerFactoryAutoConfiguration.class, // Select server implementation
  GrpcClientAutoConfiguration.class
}) // Support @GrpcClient annotation
public class LoadableStudyServiceIntegrationTestConfiguration {

  @Bean
  LoadableStudyService loadableStudyService() {
    return new LoadableStudyService();
  }

  @Bean
  SynopticService synopticService() {
    return new SynopticService();
  }

  @Bean
  DischargeStudyService dischargeStudyService() {
    return new DischargeStudyService();
  }

  @Bean
  PortInstructionService portInstructionService() {
    return new PortInstructionService();
  }

  @Bean
  PortInfoImplForLoadableStudyServiceIntegrationTest portInfoServiceImpl() {
    return new PortInfoImplForLoadableStudyServiceIntegrationTest();
  }
}
