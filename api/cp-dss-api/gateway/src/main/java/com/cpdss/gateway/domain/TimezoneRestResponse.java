/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
public class TimezoneRestResponse {

  private CommonSuccessResponse responseStatus;
  private List<Timezone> timezones;
}
