/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.DischargeStudy;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.BillOfLadding;
import com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class DischargeStudyResponse {
  private CommonSuccessResponse responseStatus;
  private List<BillOfLadding> billOfLaddings;
  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
}
