/* Licensed under Apache-2.0 */
package com.cpdss.gateway;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.logging.Log4j2Config;
import com.cpdss.common.rest.RestConfig;
import com.cpdss.gateway.repository.UsersRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

/**
 * Configuration for unit tests
 *
 * @author suhail.k
 */
@TestConfiguration
@Import({CommonConfig.class, Log4j2Config.class, RestConfig.class})
public class GatewayTestConfiguration {

  @MockBean UsersRepository usersRepository;
}
