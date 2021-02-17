/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security;

import com.cpdss.gateway.security.cloud.KeycloakDynamicConfigResolver;
import com.cpdss.gateway.security.cloud.ShoreAuthenticationProcessingFilter;
import com.cpdss.gateway.security.ship.ShipAuthenticationProvider;
import com.cpdss.gateway.security.ship.ShipAuthorizationFilter;
import com.cpdss.gateway.security.ship.ShipTokenExtractor;
import com.cpdss.gateway.security.ship.ShipUserAuthenticationProvider;
import com.cpdss.gateway.security.ship.ShipUserDetailService;
import com.cpdss.gateway.security.ship.SkipPathRequestMatcher;
import java.util.Arrays;
import java.util.List;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * Security configuration for the gateway module Contains nested static class one for shore
 * authentication and another one for ship authentication
 *
 * @author suhail.k
 */
@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

  public static final String SHORE_API_PREFIX = "/api/cloud/";
  // TODO to be changed to commented values after the demo
  public static final String SHIP_API_PREFIX = "/api/s/"; // "/api/ship/";
  public static final String AUTHORIZATION_HEADER = "authorization";

  private static final String SHIP_URL_PATTERN = SHIP_API_PREFIX + "**";

  private static final List<String> permitAllEndpointList = Arrays.asList("/api/ship/authenticate");

  @Configuration
  @ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
  public static class CloudSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      super.configure(http);
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
          // TODO to be changed to commented value after the demo
          // .antMatchers(SHIP_URL_PATTERN)
          .antMatchers("/**")
          .permitAll()
          .and()
          .authorizeRequests()
          .anyRequest()
          .authenticated();
    }

    @Override
    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter()
        throws Exception {
      KeycloakAuthenticationProcessingFilter filter =
          new ShoreAuthenticationProcessingFilter(authenticationManagerBean());
      filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
      return filter;
    }

    /**
     * Dynamic keycloak configuration resolver
     *
     * @return
     */
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
      return new KeycloakDynamicConfigResolver();
    }

    /**
     * Setting keycloak as authentication provider
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    /** Defines the session authentication strategy. */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
      return new NullAuthenticatedSessionStrategy();
    }

    // necessary due to
    // http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
        KeycloakAuthenticationProcessingFilter filter) {
      FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
      registrationBean.setEnabled(false);
      return registrationBean;
    }

    // necessary due to
    // http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
        KeycloakPreAuthActionsFilter filter) {
      FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
      registrationBean.setEnabled(false);
      return registrationBean;
    }
  }

  @Configuration
  @Order(1)
  public static class ShipSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private ShipAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private ShipUserAuthenticationProvider shipUserAuthenticationProvider;
    @Autowired private ShipUserDetailService userDetailService;
    @Autowired private ShipTokenExtractor jwtTokenExtractor;
    @Autowired private AuthenticationFailureHandler failureHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

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
          .antMatcher(SHIP_URL_PATTERN)
          .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .addFilterBefore(
              buildJwtAuthorizationFilter(permitAllEndpointList, SHIP_URL_PATTERN),
              UsernamePasswordAuthenticationFilter.class);
    }

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
}
