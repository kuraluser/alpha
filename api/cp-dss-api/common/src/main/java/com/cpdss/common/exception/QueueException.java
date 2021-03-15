/* Licensed at AlphaOri Technologies */
package com.cpdss.common.exception;

/**
 * Custom exception for kafka queue issues
 *
 * @author r.krishnakumar
 */
public class QueueException extends Exception {

  public QueueException(String message) {
    super(message);
  }

  public QueueException(String message, Throwable e) {
    super(message, e);
  }
}
