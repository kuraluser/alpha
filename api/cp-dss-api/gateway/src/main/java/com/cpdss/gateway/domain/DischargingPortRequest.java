/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DischargingPortRequest {

  private Long loadableStudyId;

  @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private List<Long> portIds;
}
