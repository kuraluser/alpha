/* Licensed under Apache-2.0 */
package com.cpdss.common.utils;

import java.util.Map;

/**
 * Tenant Context class for multi tenency support
 *
 * @author krishna
 */
public class AppContext {

  private static String tenantKey = "tenant";

  private static String userIdKey = "userId";

  private static ThreadLocal<Map<String, String>> currentThreadLocal =
      new InheritableThreadLocal<>();

  public static String getCurrentTenant() {
    return currentThreadLocal.get().get(tenantKey);
  }

  public static void setCurrentTenant(String tenant) {
    currentThreadLocal.get().put(tenantKey, tenant);
  }

  public static String getCurrentUserId() {
    return currentThreadLocal.get().get(userIdKey);
  }

  public static void setCurrentUserId(String userId) {
    currentThreadLocal.get().put(userIdKey, userId);
  }

  public static void clear() {
    currentThreadLocal.set(null);
  }
}
