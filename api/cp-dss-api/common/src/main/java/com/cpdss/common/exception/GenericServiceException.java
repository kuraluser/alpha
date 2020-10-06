/* Licensed under Apache-2.0 */
package com.cpdss.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exceptions for services
 *
 * @author r.krishnakumar
 */
@Getter
public class GenericServiceException extends Exception {

  private final String code;

  private final HttpStatus status;

  public GenericServiceException(String message, String code, HttpStatus status) {
    super(message);
    this.code = code;
    this.status = status;
  }

  public GenericServiceException(String message, String code, HttpStatus status, Throwable e) {
    super(message, e);
    this.code = code;
    this.status = status;
  }
}
