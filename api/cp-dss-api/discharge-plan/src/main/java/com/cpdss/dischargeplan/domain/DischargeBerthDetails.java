/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class DischargeBerthDetails implements Serializable {
  private List<BerthDetails> selectedBerths; // To loading-plan DB, as they edited
}
