/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.security.cloud;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_CLOUD;

import java.util.Arrays;
import java.util.List;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * Cloud security config is activated when build env is cloud It is dependent on the property
 * 'cpdss.build.env'
 *
 * @author suhail.k
 */
@KeycloakConfiguration
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_CLOUD)
public class CloudSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  private static final List<String> permitAllEndpointList =
      Arrays.asList(
          "/actuator/health",
          "/api/cloud/vessel-details/*",
          "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-study-status",
          "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/loadable-patterns",
          "/api/cloud//vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-patterns/{loadablePatternId}/validate-loadable-plan");

  /** Registers the KeycloakAuthenticationProvider with the authentication manager. */
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

  /** Seuciryt configuration */
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
        .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
        .permitAll()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated();
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

  /** Registers the auth processing fileter */
  @Override
  protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter()
      throws Exception {
    KeycloakAuthenticationProcessingFilter filter =
        new CloudAuthenticationProcessingFilter(authenticationManagerBean());
    filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
    return filter;
  }

  /**
   * To avoid double bean registration by spring boot see
   * http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
   *
   * @param filter
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
      KeycloakAuthenticationProcessingFilter filter) {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    registrationBean.setEnabled(false);
    return registrationBean;
  }

  /**
   * To avoid double bean registration by spring boot see
   * http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
   *
   * @param filter
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
      KeycloakPreAuthActionsFilter filter) {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    registrationBean.setEnabled(false);
    return registrationBean;
  }

  /**
   * To avoid double bean registration by spring boot see
   * http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
   *
   * @param filter
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(
      KeycloakAuthenticatedActionsFilter filter) {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    registrationBean.setEnabled(false);
    return registrationBean;
  }

  /**
   * To avoid double bean registration by spring boot see
   * http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
   *
   * @param filter
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
      KeycloakSecurityContextRequestFilter filter) {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    registrationBean.setEnabled(false);
    return registrationBean;
  }

  /** session manager */
  @Bean
  @Override
  @ConditionalOnMissingBean(HttpSessionManager.class)
  protected HttpSessionManager httpSessionManager() {
    return new HttpSessionManager();
  }
}
