/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Request class for Port rotation list
 *
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class PortRotationRequest {

  private Long loadableStudyId;
  private List<PortRotation> portList;

}
