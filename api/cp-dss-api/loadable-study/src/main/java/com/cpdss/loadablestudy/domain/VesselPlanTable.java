/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VesselPlanTable {
  private String vesselName;
  private String voyageNo;
  private String date;
  private List<Float> frameFromCellsList;
  private List<Float> frameToCellsList;
  private List<VesselTanksTable> vesselTanksTableList;
}
