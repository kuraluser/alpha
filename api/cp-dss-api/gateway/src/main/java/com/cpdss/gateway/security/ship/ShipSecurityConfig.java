/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.security.ship;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security config for ship side. Activated only when build env is ship. Dependent on property
 * 'cpdss.build.env'
 *
 * @author suhail.k
 */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_SHIP)
public class ShipSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private ShipAuthenticationProvider jwtAuthenticationProvider;
  @Autowired private ShipUserAuthenticationProvider shipUserAuthenticationProvider;
  @Autowired private ShipUserDetailService userDetailService;
  @Autowired private ShipTokenExtractor jwtTokenExtractor;
  @Autowired private AuthenticationFailureHandler failureHandler;

  private static final List<String> permitAllEndpointList =
      Arrays.asList(
          "/api/ship/authenticate",
          "/actuator/health",
          "/api/cloud/vessel-details/*",
          "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-study-status",
          "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-patterns",
          "/api/cloud//vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-patterns/{loadablePatternId}/pattern-validate-result");

  public static final String AUTHORIZATION_HEADER = "authorization";

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /** Security config */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
        .permitAll()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            buildJwtAuthorizationFilter(permitAllEndpointList, "/**"),
            UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * Password encoder bean for ship user
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthenticationProvider);
    auth.authenticationProvider(shipUserAuthenticationProvider)
        .userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder());
  }

  /**
   * Builds the JWTAuthorization Filter.
   *
   * @param pathsToSkip
   * @param pattern
   * @return JwtAuthorizationFilter
   * @throws Exception
   */
  protected ShipAuthorizationFilter buildJwtAuthorizationFilter(
      List<String> pathsToSkip, String pattern) throws Exception {
    SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
    ShipAuthorizationFilter filter =
        new ShipAuthorizationFilter(failureHandler, jwtTokenExtractor, matcher);
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }
}
