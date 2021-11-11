/* Licensed at AlphaOri Technologies */
package com.cpdss.common.exception;

/**
 * Common GrpcServer Interrupt Exception
 *
 * @author krishna
 */
public class CommonGrpcServerInterruptException extends RuntimeException {

  public CommonGrpcServerInterruptException(String errorMessage) {
    super(errorMessage);
  }

  public CommonGrpcServerInterruptException(String errorMessage, Throwable throwable) {
    super(errorMessage, throwable);
  }
}
