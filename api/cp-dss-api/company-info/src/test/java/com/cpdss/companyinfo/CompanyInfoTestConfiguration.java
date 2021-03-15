/* Licensed at AlphaOri Technologies */
package com.cpdss.companyinfo;

import com.cpdss.common.config.CommonConfig;
import com.cpdss.common.logging.Log4j2Config;
import com.cpdss.common.rest.RestConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Test configuration for company info microservice
 *
 * @author suhail.k
 */
@TestConfiguration
@Import({CommonConfig.class, Log4j2Config.class, RestConfig.class})
public class CompanyInfoTestConfiguration {}
