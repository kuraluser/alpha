/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** Request class for Port rotation list */
@Data
// @JsonInclude(Include.NON_EMPTY) // commented out since Web team would need the null values
public class PortRotationRequest {

  private Long loadableStudyId;
  private List<PortRotation> portList;
}
