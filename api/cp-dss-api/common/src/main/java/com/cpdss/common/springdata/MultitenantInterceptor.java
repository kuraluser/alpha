/* Licensed under Apache-2.0 */
package com.cpdss.common.springdata;

import com.cpdss.common.utils.TenantContext;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor for multitenant support
 *
 * @author krishna
 */
@Log4j2
public class MultitenantInterceptor extends HandlerInterceptorAdapter {

  /** Method to intercept the request and set the multi tenant id in the TenantContext */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
      throws Exception {
    String tenantID = request.getHeader("X-TenantID");
    if (tenantID == null) {
      log.error("X-TenantID not present in the Request Header");
      throw new ConstraintViolationException(
          "X-TenantID not present in the Request Header", new HashSet<>());
    }
    TenantContext.setCurrentTenant(tenantID);
    return true;
  }

  /** Method to clear the multitenant context */
  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    TenantContext.clear();
  }
}
