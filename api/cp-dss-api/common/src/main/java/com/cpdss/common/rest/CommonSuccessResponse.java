/* Licensed under Apache-2.0 */
package com.cpdss.common.rest;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Error response for the rest api
 *
 * @author krishna
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class CommonSuccessResponse implements CorrelationId {

  @NotBlank private String status;

  @NotBlank private String correlationId;
}
