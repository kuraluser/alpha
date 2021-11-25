/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import java.util.Comparator;

public class EductorSequenceNumberComparator implements Comparator<String> {
  @Override
  public int compare(String o1, String o2) {
    return extractInt(o1) - extractInt(o2);
  }

  int extractInt(String s) {
    String num = s.replaceAll("\\D", "");
    // return 0 if no digits found
    return num.isEmpty() ? 0 : Integer.parseInt(num);
  }
}
