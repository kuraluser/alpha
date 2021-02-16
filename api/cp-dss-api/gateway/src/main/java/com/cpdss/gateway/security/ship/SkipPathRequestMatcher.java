/* Licensed under Apache-2.0 */
package com.cpdss.gateway.security.ship;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Request matcher for skipping the JWT token validation.
 *
 * @author suhail.k
 */
public class SkipPathRequestMatcher implements RequestMatcher {

  private static final Logger logger = LogManager.getLogger(SkipPathRequestMatcher.class);

  private OrRequestMatcher matchers;
  private RequestMatcher processingMatcher;

  /**
   * Parameterized constructor of SkipPathRequestMatcher.
   *
   * @param pathsToSkip
   * @param processingPath
   */
  public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
    List<RequestMatcher> m =
        pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
    matchers = new OrRequestMatcher(m);
    processingMatcher = new AntPathRequestMatcher(processingPath);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    if (matchers.matches(request)) {
      logger.debug("SkipPathRequestMatcher - Matched the request", request);
      return false;
    }
    return processingMatcher.matches(request);
  }
}
