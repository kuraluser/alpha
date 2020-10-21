/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/**
 * Response class for Port rotation
 *
 * @author suhail.k
 */
@Data
public class PortRotationResponse {

  private CommonSuccessResponse responseStatus;

  private List<PortRotation> portList;

  private List<Operation> operations;
}
