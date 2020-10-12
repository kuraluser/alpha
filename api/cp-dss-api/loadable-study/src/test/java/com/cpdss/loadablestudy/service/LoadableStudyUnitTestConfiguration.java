package com.cpdss.loadablestudy.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author jerin.g
 *
 */
@Configuration
public class LoadableStudyUnitTestConfiguration {

	@Bean
	LoadableStudyService loadableStudyService() {
        return new LoadableStudyService();
    }
	
}
