/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SynopticalTableRequest {

  @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  @Size(min = 2, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private List<SynopticalRecord> synopticalRecords;
}
