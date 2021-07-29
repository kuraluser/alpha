/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.loadablestudy.entity.BillOfLanding;
import java.util.List;
import lombok.Data;

@Data
public class UllageBillRequest {
  List<BillOfLanding> billOfLandingList;
  List<UpdateUllage> ullageUpdList;
}
