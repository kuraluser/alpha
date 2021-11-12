/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_CLOUD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.keycloak.KeycloakAuthenticationResponse;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import com.cpdss.gateway.security.cloud.KeycloakDynamicConfigResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = {CPDSS_BUILD_ENV + "=" + CPDSS_BUILD_ENV_CLOUD})
@SpringJUnitConfig(classes = {KeycloakService.class})
class KeycloakServiceTest {
  @Autowired KeycloakService keycloakService;

  @MockBean private RestTemplate restTemplate;

  @MockBean private KeycloakAuthenticationResponse keycloakAuthenticationResponse;

  @MockBean private KeycloakDynamicConfigResolver keycloakDynamicConfigResolver;

  private final String USER_ID = "4b5608ff-b77b-40c6-9645-d69856d4aafa";
  private final String REALM_NAME = "TestRealm";
  public static final String KEYCLOAK_EXCEPTION_MSG = "Invalid Message From Keycloak";
  public static final String REST_SERVICE_EXCEPTION_MSG = "Exception calling REST Service";

  /** Positive test case for getUser method */
  @Test
  void getUserPositiveTest() throws GenericServiceException {
    when(restTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(createKeycloakAuthenticationResponse());
    when(restTemplate.exchange(
            anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
        .thenReturn(createGetUserResponse());
    assertThat(keycloakService.getUser(USER_ID).getId()).isEqualTo(USER_ID);
  }

  /** Positive test case for getUsers method */
  @Test
  void getUsersPositiveTest() throws GenericServiceException {
    KeycloakUser[] users = {getKeycloakUser()};

    when(restTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(createKeycloakAuthenticationResponse());
    when(restTemplate.exchange(
            anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
        .thenReturn(createGetUsersResponse());
    assertArrayEquals(keycloakService.getUsers(), users);
  }

  /** Negative test case for getUser method. Handling RestClientException on authentication */
  @Test
  void getUserRestClientExceptionAuthentication() throws GenericServiceException {
    when(restTemplate.postForEntity(anyString(), any(), any()))
        .thenThrow(RestClientException.class);
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              keycloakService.getUser(USER_ID);
            });
    assertEquals(REST_SERVICE_EXCEPTION_MSG, exception.getMessage());
  }

  /** Negative test case for getUser method. Handling Invalid Status Codes on authentication */
  @Test
  void getUserInvalidStatusCodeAuthentication() throws GenericServiceException {
    when(restTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              keycloakService.getUser(USER_ID);
            });
    assertEquals(KEYCLOAK_EXCEPTION_MSG, exception.getMessage());
  }

  /** Negative test case for getUser method. Handling RestClientException on callKeycloakApi */
  @Test
  void getUserRestClientExceptionCallKeycloakApi() throws GenericServiceException {
    when(restTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(createKeycloakAuthenticationResponse());
    when(restTemplate.exchange(
            anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
        .thenThrow(RestClientException.class);
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              keycloakService.getUser(USER_ID);
            });
    assertEquals(REST_SERVICE_EXCEPTION_MSG, exception.getMessage());
  }

  /** Negative test case for getUser method. Handling Invalid Status Codes on callKeycloakApi */
  @Test
  void getUserInvalidStatusCodeCallKeycloakApi() throws GenericServiceException {
    when(restTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(createKeycloakAuthenticationResponse());
    when(restTemplate.exchange(
            anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
        .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              keycloakService.getUser(USER_ID);
            });
    assertEquals(KEYCLOAK_EXCEPTION_MSG, exception.getMessage());
  }

  /**
   * Method to create authentication response for Keycloak
   *
   * @return ResponseEntity<KeycloakAuthenticationResponse> keycloakAuthentication response object
   */
  private ResponseEntity<Object> createKeycloakAuthenticationResponse() {
    return new ResponseEntity<>(
        new KeycloakAuthenticationResponse("testToken", 10, 10, "refresh", "Bearer", "Admin"),
        HttpStatus.OK);
  }

  /**
   * Method to get Keycloak user object
   *
   * @return KeycloakUser user object
   */
  private KeycloakUser getKeycloakUser() {
    return new KeycloakUser(USER_ID, "TestUserName", "FirstName", "LastName", "xyz@xyz.com", 1L);
  }

  /**
   * Method to create get user response object
   *
   * @return ResponseEntity<KeycloakUser> user object
   */
  private ResponseEntity<Object> createGetUserResponse() {
    return new ResponseEntity<>(getKeycloakUser(), HttpStatus.OK);
  }

  /**
   * Method to create get users response object
   *
   * @return ResponseEntity<KeycloakUser [ ]> users object
   */
  private ResponseEntity<Object> createGetUsersResponse() {
    return new ResponseEntity<>(new KeycloakUser[] {getKeycloakUser()}, HttpStatus.OK);
  }
}
