/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV;
import static com.cpdss.gateway.custom.Constants.CPDSS_BUILD_ENV_SHIP;
import static com.cpdss.gateway.custom.Constants.SHIP_TOKEN_SUBJECT;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class ShipJwtService {

  private static final Logger logger = LogManager.getLogger(ShipJwtService.class);

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
   * @throws ExpiredJwtException
   * @throws UnsupportedJwtException
   * @throws MalformedJwtException
   * @throws SignatureException
   * @throws ExpiredJwtException
   * @throws IllegalArgumentException
   */
  public Jws<Claims> parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(tokenSigningKey.getBytes()).parseClaimsJws(token);
    } catch (ExpiredJwtException eje) {
      logger.warn("Request to parse expired JWT {}, {}", token, eje);
      throw eje;
    } catch (UnsupportedJwtException uje) {
      logger.warn("Request to parse unsupported JWT {}, {}", token, uje);
      throw uje;
    } catch (MalformedJwtException mje) {
      logger.warn("Request to parse invalid JWT {}, {}", token, mje);
      throw mje;
    } catch (SignatureException se) {
      logger.warn("Request to parse JWT with invalid signature {}, {}", token, se);
      throw se;
    } catch (IllegalArgumentException iae) {
      logger.warn("Request to parse empty or null JWT {}, {}", token, iae);
      throw iae;
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
