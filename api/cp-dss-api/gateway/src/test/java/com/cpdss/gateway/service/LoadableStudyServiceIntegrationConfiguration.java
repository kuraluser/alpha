/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

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
  GrpcClientAutoConfiguration.class // Support @GrpcClient annotation
})
public class LoadableStudyServiceIntegrationConfiguration {

  @Bean
  LoadableStudyService loadableStudyService() {
    return new LoadableStudyService();
  }

  @Bean
  LoadableStudyImplForLoadableStudyServiceIntegration loadableStudyServiceImpl() {
    return new LoadableStudyImplForLoadableStudyServiceIntegration();
  }
}
