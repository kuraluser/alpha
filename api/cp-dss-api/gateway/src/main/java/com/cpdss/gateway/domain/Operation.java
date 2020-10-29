/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * DTO for cargo operation
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class Operation {

  private Long id;

  private String operationName;
}
