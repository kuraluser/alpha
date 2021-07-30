/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude
public class UllageBillRequest {
  List<BillOfLanding> billOfLandingList;
  List<UpdateUllage> ullageUpdList;
}
