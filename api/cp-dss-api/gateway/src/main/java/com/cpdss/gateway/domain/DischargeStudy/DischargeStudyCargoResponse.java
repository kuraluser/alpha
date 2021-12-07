/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.DischargeStudy;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DischargeStudyCargoResponse {
  private CommonSuccessResponse responseStatus;
  private Long dischargeStudyId;
  private Long cowId;
  private BigDecimal percentage;
  private List<Long> tanks = new ArrayList<>();
  private List<PortRotation> portList;
  private Long dischargePatternId;
  private BigDecimal loadableQuantity;
}
