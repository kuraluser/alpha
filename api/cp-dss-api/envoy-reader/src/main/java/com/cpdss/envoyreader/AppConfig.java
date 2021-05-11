/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.grpc.GrpcConfig;
import com.cpdss.common.logging.Log4j2Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonConfig.class, Log4j2Config.class, GrpcConfig.class})
public class AppConfig {}
