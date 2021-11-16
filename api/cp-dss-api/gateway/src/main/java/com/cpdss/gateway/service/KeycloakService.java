/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.keycloak.KeycloakAuthenticationResponse;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import com.cpdss.gateway.security.cloud.KeycloakDynamicConfigResolver;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Class for Keycloak service functionality
 *
 * @author sreekumar.k
 */
@Service
@Log4j2
public class KeycloakService {
  @Autowired private RestTemplate restTemplate;

  @Autowired(required = false)
  private KeycloakDynamicConfigResolver keycloakDynamicConfigResolver;

  @Value("${keycloak.auth-server-url}")
  private String keycloakApiUrl;

  @Value("${kc.admin.username}")
  private String keycloakApiUserName;

  @Value("${kc.admin.password}")
  private String keycloakApiPassword;

  @Value("${kc.admin.clientId}")
  private String getKeycloakApiClientId;

  public static final String REST_SERVICE_EXCEPTION_MSG = "Exception calling REST Service";
  public static final String CONVERSION_EXCEPTION_MSG = "Exception converting object";
  public static final String KEYCLOAK_EXCEPTION_MSG = "Invalid Message From Keycloak";
  public static final String GRANT_TYPE_VALUE = "password";
  public static final String USER_NAME_KEY = "username";
  public static final String PASSWORD_KEY = "password";
  public static final String GRANT_TYPE_KEY = "grant_type";
  public static final String CLIENT_ID_KEY = "client_id";

  /**
   * Method to authenticate Keycloak
   *
   * @param userName Username value
   * @param password Password value
   * @param clientId Client-Id value
   * @return KeycloakAuthenticationResponse
   */
  private KeycloakAuthenticationResponse authenticate(
      final String userName, final String password, final String clientId)
      throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add(USER_NAME_KEY, userName);
    map.add(PASSWORD_KEY, password);
    map.add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE);
    map.add(CLIENT_ID_KEY, clientId);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

    try {
      ResponseEntity<Object> response =
          this.restTemplate.postForEntity(
              keycloakApiUrl + "/realms/master/protocol/openid-connect/token",
              request,
              Object.class);

      if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(response.getBody(), KeycloakAuthenticationResponse.class);
      } else {
        log.error(KEYCLOAK_EXCEPTION_MSG + ": {}", response);
        throw new GenericServiceException(
            KEYCLOAK_EXCEPTION_MSG,
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.valueOf(response.getStatusCode().value()));
      }
    } catch (RestClientException e) {
      log.error(REST_SERVICE_EXCEPTION_MSG, e);
      throw new GenericServiceException(
          REST_SERVICE_EXCEPTION_MSG,
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e);
    }
  }

  /**
   * Method to authenticate and send request to Keycloak
   *
   * @param httpMethod HttpMethod type
   * @param apiUrl Api URL value
   * @param responseType Response type to be returned
   * @param <T> Return class type
   * @return Generic response type
   */
  private <T> T callKeycloakApi(
      final HttpMethod httpMethod, final String apiUrl, final Class<T> responseType)
      throws GenericServiceException {
    //      Authenticate
    KeycloakAuthenticationResponse authenticationResponse =
        this.authenticate(keycloakApiUserName, keycloakApiPassword, getKeycloakApiClientId);

    //    Call requested API
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(authenticationResponse.getAccess_token());
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<Object> response =
          this.restTemplate.exchange(apiUrl, httpMethod, entity, Object.class);

      if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return responseType.cast(
            objectMapper.convertValue(response.getBody(), Class.forName(responseType.getName())));
      } else {
        log.error(KEYCLOAK_EXCEPTION_MSG + ": {}", response);
        throw new GenericServiceException(
            KEYCLOAK_EXCEPTION_MSG,
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.valueOf(response.getStatusCode().value()));
      }
    } catch (RestClientException e) {
      log.error(REST_SERVICE_EXCEPTION_MSG, e);
      throw new GenericServiceException(
          REST_SERVICE_EXCEPTION_MSG,
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e);
    } catch (ClassNotFoundException e) {
      log.error(CONVERSION_EXCEPTION_MSG, e);
      throw new GenericServiceException(
          CONVERSION_EXCEPTION_MSG,
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e);
    }
  }

  /**
   * Method to get all users for a specific realm
   *
   * @return KeycloakListUsersResponse List of users
   */
  public KeycloakUser[] getUsers() throws GenericServiceException {
    return callKeycloakApi(
        HttpMethod.GET,
        keycloakApiUrl + "/admin/realms/" + keycloakDynamicConfigResolver.getRealm() + "/users",
        KeycloakUser[].class);
  }

  /**
   * Method to get a specific user
   *
   * @param userId UserId value
   * @return KeycloakUser user object
   */
  public KeycloakUser getUser(final String userId) throws GenericServiceException {
    return callKeycloakApi(
        HttpMethod.GET,
        keycloakApiUrl
            + "/admin/realms/"
            + keycloakDynamicConfigResolver.getRealm()
            + "/users/"
            + userId,
        KeycloakUser.class);
  }
}
