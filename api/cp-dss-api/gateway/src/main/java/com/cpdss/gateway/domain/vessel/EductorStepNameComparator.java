/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import java.util.Comparator;

public class EductorStepNameComparator implements Comparator<String> {
  @Override
  public int compare(String o1, String o2) {
    return o1.compareTo(o2);
  }
}
