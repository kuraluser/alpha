/* Licensed under Apache-2.0 */
package com.cpdss.gateway;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for unit tests
 *
 * @author suhail.k
 */
@TestConfiguration
@Import({AppConfig.class})
public class GatewayTestConfiguration {}
