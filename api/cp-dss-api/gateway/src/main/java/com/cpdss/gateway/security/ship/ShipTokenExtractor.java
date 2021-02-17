/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Class to extract the JWT token from the header
 *
 * @author suhail.k
 */
@Component
public class ShipTokenExtractor {

  private static final Logger logger = LogManager.getLogger(ShipTokenExtractor.class);

  private static final String JWT_HEADER_PREFIX = "Bearer ";
  private static final String TOKEN_HEADER = "Authorization";

  /**
   * Extract the JWT token from the given header value.
   *
   * @param header
   * @return
   */
  public Optional<String> extract(final String header) {
    if (header == null || header.isEmpty()) {
      logger.warn("Authorization header is blank");
      return Optional.empty();
    } else if (header.length() <= JWT_HEADER_PREFIX.length()
        || !header.startsWith(JWT_HEADER_PREFIX)) {
      logger.warn("Invalid authorization header size or header type.");
      return Optional.empty();
    }
    return Optional.of(header.substring(JWT_HEADER_PREFIX.length(), header.length()));
  }

  /**
   * Returns the Optional object with JWT token
   *
   * @param request
   * @return
   */
  public Optional<String> extract(HttpServletRequest request) {
    logger.debug("Inside security filter: JWT validation.");
    Optional<String> authToken = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
    if (authToken.isPresent()) {
      return extract(authToken.get());
    }
    return Optional.empty();
  }
}
