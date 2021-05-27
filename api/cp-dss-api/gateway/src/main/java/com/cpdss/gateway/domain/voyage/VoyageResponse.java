/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.voyage;

import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.PortRotation;
import java.util.List;
import lombok.Data;

@Data
public class VoyageResponse {

  private Long id;
  private String voyageNumber;
  private String startDate;
  private String endDate;
  private String status;
  private String charterer;
  private String actualStartDate;
  private String actualEndDate;
  private Long statusId;

  List<PortRotation> portRotations;
  LoadableStudy activeLs;
}
