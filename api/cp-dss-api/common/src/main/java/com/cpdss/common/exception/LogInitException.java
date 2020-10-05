/* Licensed under Apache-2.0 */
package com.cpdss.common.exception;

/**
 * Custom exception for Logging config init
 *
 * @author r.krishnakumar
 */
public class LogInitException extends RuntimeException {

  public LogInitException(String message) {
    super(message);
  }

  public LogInitException(String message, Throwable e) {
    super(message, e);
  }
}
