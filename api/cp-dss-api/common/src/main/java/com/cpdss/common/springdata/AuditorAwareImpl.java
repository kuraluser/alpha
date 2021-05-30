/* Licensed at AlphaOri Technologies */
package com.cpdss.common.springdata;

import com.cpdss.common.utils.AppContext;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/** AuditorAware implementation */
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    String userIdentifier = "unknown";
    // To-Do Need to move to Spring Security interceptor
    //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //    if (authentication != null) {
    //      userIdentifier = authentication.getName();
    //    }
    if (AppContext.getCurrentUserId() != null) {
      userIdentifier = AppContext.getCurrentUserId();
    }
    return Optional.ofNullable(userIdentifier);
  }
}
