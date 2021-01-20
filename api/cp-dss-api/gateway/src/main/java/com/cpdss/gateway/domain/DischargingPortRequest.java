/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class DischargingPortRequest {

  private Long loadableStudyId;

  private List<Long> portIds;

  private Long dischargingCargoId;
}
