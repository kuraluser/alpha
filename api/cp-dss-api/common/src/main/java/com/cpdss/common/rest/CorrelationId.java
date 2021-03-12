/* Licensed at AlphaOri Technologies */
package com.cpdss.common.rest;

/**
 * Correlation id interface for the API requests
 *
 * @author krishna
 */
public interface CorrelationId {

  String getCorrelationId();

  void setCorrelationId(String correlationId);
}
