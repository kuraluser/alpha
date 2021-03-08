/* Licensed under Apache-2.0 */
package com.cpdss.gateway;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.logging.Log4j2Config;
import com.cpdss.common.rest.RestConfig;
import com.cpdss.gateway.repository.UsersRepository;
import com.cpdss.gateway.security.GatewaySecurityConfig;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Configuration for unit tests
 *
 * @author suhail.k
 */
@TestConfiguration
@Import({CommonConfig.class, Log4j2Config.class, RestConfig.class, GatewaySecurityConfig.class})
@ComponentScan(
    basePackageClasses = {KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class},
    basePackages = {"com.cpdss.gateway.security.ship", "com.cpdss.gateway.repository"})
public class GatewayTestConfiguration {

  @MockBean UsersRepository usersRepository;
}
