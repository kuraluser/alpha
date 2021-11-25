/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import java.util.Comparator;

public class EductorStageNameComparator implements Comparator<VesselValveEducationProcess> {
  @Override
  public int compare(VesselValveEducationProcess o1, VesselValveEducationProcess o2) {
    return o1.getStageNumber().compareTo(o2.getStageNumber());
  }
}
