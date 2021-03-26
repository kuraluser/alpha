/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.security.ship;

import static com.cpdss.gateway.custom.Constants.AUTHORIZATION_HEADER;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.gateway.custom.Constants.SHIP_TOKEN_SUBJECT;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * In ship environment, an updated token will be added with every response
 *
 * @author suhail.k
 */
@ControllerAdvice
@SuppressWarnings("rawtypes")
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_SHIP)
@Log4j2
public class ShipResponseBodyAdvice implements ResponseBodyAdvice {

  private static final String UPDATED_TOKEN_HEADER = "token";

  @Autowired private ShipJwtService shipJwtService;

  /** Intercept every response */
  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  /** Read the JWT token from request, create new token from it */
  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    if (body instanceof CommonErrorResponse) {
      return body;
    }
    String token = null;
    if (request instanceof ServletServerHttpRequest) {
      ServletServerHttpRequest req = (ServletServerHttpRequest) (request);
      token = req.getHeaders().getFirst(AUTHORIZATION_HEADER);
    }
    if (response instanceof ServletServerHttpResponse && null != token) {
      ServletServerHttpResponse res = (ServletServerHttpResponse) (response);
      if (HttpStatus.OK.value() == res.getServletResponse().getStatus()) {
        try {
          Jws<Claims> claims = this.shipJwtService.parseClaims(token.replace("Bearer", ""));
          res.getHeaders()
              .set(
                  UPDATED_TOKEN_HEADER,
                  this.shipJwtService.generateToken(claims.getBody(), SHIP_TOKEN_SUBJECT));
        } catch (GenericServiceException e) {
          log.error("Error parsing token", e);
        }
      }
    }
    return body;
  }
}
