/* Licensed at AlphaOri Technologies */
package com.cpdss.common.exception;

import com.cpdss.common.utils.HttpStatusCode;
import lombok.Getter;

/**
 * Custom exceptions for services
 *
 * @author r.krishnakumar
 */
@Getter
public class GenericServiceException extends Exception {

  private final String code;

  private final HttpStatusCode status;

  public GenericServiceException(String message, String code, HttpStatusCode status) {
    super(message);
    this.code = code;
    this.status = status;
  }

  public GenericServiceException(String message, String code, HttpStatusCode status, Throwable e) {
    super(message, e);
    this.code = code;
    this.status = status;
  }
}
