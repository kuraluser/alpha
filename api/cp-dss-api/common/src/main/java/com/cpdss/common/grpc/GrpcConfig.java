/* Licensed at AlphaOri Technologies */
package com.cpdss.common.grpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration class for GRPC
 *
 * @author r.krishnakumar
 */
@Configuration
@Import({GrpcServerConfig.class, GrpcClientConfig.class})
public class GrpcConfig {}
