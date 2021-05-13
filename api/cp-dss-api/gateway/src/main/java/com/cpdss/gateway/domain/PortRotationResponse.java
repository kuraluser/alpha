/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/**
 * Response class for Port rotation
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class PortRotationResponse {

  private CommonSuccessResponse responseStatus;

  private List<PortRotation> portList;

  private List<Operation> operations;

  private Long id;

  private Long lastModifiedPortId = 0L;
}
