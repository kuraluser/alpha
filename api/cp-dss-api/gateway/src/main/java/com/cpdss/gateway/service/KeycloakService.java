/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.keycloak.KeycloakAuthenticationResponse;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import java.util.Objects;
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

  @Value("${kc.admin.url}")
  private String keycloakApiUrl;

  @Value("${kc.admin.username}")
  private String keycloakApiUserName;

  @Value("${kc.admin.password}")
  private String keycloakApiPassword;

  @Value("${kc.admin.clientId}")
  private String getKeycloakApiClientId;

  public static final String REST_SERVICE_EXCEPTION_MSG = "Exception calling REST Service";
  public static final String KEYCLOAK_EXCEPTION_MSG = "Invalid Message From Keycloak";

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
    map.add("username", userName);
    map.add("password", password);
    map.add("grant_type", "password");
    map.add("client_id", clientId);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

    try {
      ResponseEntity<KeycloakAuthenticationResponse> response =
          this.restTemplate.postForEntity(
              keycloakApiUrl + "/realms/master/protocol/openid-connect/token",
              request,
              KeycloakAuthenticationResponse.class);

      if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
        return Objects.requireNonNull(response.getBody());
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
      ResponseEntity<T> response =
          this.restTemplate.exchange(apiUrl, httpMethod, entity, responseType);

      if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
        return Objects.requireNonNull(response.getBody());
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
   * Method to get all users for a specific realm
   *
   * @param realm Realm name
   * @return KeycloakListUsersResponse List of users
   */
  public KeycloakUser[] getUsers(final String realm) throws GenericServiceException {
    return callKeycloakApi(
        HttpMethod.GET, keycloakApiUrl + "/admin/realms/" + realm + "/users", KeycloakUser[].class);
  }

  /**
   * Method to get a specific user
   *
   * @param userId UserId value
   * @param realmName Realm name
   * @return KeycloakUser user object
   */
  public KeycloakUser getUser(final String userId, final String realmName)
      throws GenericServiceException {
    return callKeycloakApi(
        HttpMethod.GET,
        keycloakApiUrl + "/admin/realms/" + realmName + "/users/" + userId,
        KeycloakUser.class);
  }
}
