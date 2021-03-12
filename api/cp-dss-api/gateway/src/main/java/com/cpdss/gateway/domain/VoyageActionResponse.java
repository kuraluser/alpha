/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class VoyageActionResponse {
  private CommonSuccessResponse responseStatus;
  private long voyageId;
}
