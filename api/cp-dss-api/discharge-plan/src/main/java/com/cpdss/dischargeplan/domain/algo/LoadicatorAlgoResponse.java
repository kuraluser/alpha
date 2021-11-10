/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import java.util.List;
import lombok.Data;

/**
 * DTO for get loadicator data into DP
 *
 * @author JohnSoorajXavier
 * @since 08-11-2021
 */
@Data
public class LoadicatorAlgoResponse {

  private String processId;
  private Long loadingInformationId;
  private Long vesselId;
  private Long portId;
  private List<LoadicatorResult> loadicatorResults;
}
