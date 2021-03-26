/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.security.ship;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.gateway.custom.Constants.SHIP_TOKEN_SUBJECT;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Service class for all JWT related operations such as token parsing, generation etc
 *
 * @author suhail.k
 */
@Component
@ConditionalOnProperty(name = CPDSS_BUILD_ENV, havingValue = CPDSS_BUILD_ENV_SHIP)
@Log4j2
public class ShipJwtService {

  public static final String USER_ID_CLAIM = "USER_ID";

  @Value("${ship.jwt.secret}")
  private String tokenSigningKey;

  @Value("${ship.jwt.validity}")
  private long tokenValidity;

  /**
   * Parses and validates JWT Token signature.
   *
   * @param signingKey
   * @return the jwt claims
   * @throws GenericServiceException
   * @throws ExpiredJwtException
   * @throws UnsupportedJwtException
   * @throws MalformedJwtException
   * @throws SignatureException
   * @throws ExpiredJwtException
   * @throws IllegalArgumentException
   */
  public Jws<Claims> parseClaims(String token) throws GenericServiceException {
    try {
      return Jwts.parser().setSigningKey(tokenSigningKey.getBytes()).parseClaimsJws(token);
    } catch (ExpiredJwtException
        | UnsupportedJwtException
        | MalformedJwtException
        | SignatureException
        | IllegalArgumentException e) {
      log.warn("Failed to parse the token {}, {}", token, e);
      throw new GenericServiceException(
          "Failed to parse the token",
          CommonErrorCodes.E_HTTP_INVALID_TOKEN,
          HttpStatusCode.UNAUTHORIZED);
    }
  }

  /**
   * Generate JWT token from user entity
   *
   * @param user
   * @return
   */
  public String generateToken(Users user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(USER_ID_CLAIM, user.getId());
    return this.generateToken(claims, SHIP_TOKEN_SUBJECT);
  }

  /**
   * Generate JWT token
   *
   * @param claims
   * @param subject
   * @return
   */
  public String generateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
        .signWith(SignatureAlgorithm.HS512, tokenSigningKey.getBytes())
        .compact();
  }
}
