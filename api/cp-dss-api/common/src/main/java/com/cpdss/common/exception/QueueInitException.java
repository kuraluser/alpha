/* Licensed under Apache-2.0 */
package com.cpdss.common.exception;

/**
 * Custom exception for kafka queue init
 *
 * @author r.krishnakumar
 */
public class QueueInitException extends RuntimeException {

  public QueueInitException(String message) {
    super(message);
  }

  public QueueInitException(String message, Throwable e) {
    super(message, e);
  }
}
