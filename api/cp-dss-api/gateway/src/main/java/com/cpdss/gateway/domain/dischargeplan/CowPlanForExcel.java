/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;
import lombok.Data;

@Data
public class CowPlanForExcel {

  private String allCow;
  private String topCow;
  private String bottomCow;
  private Integer allCowCount;
  private Integer topCowCount;
  private Integer bottomCowCount;
  private String cowStart;
  private String cowFinish;
  private String cowHours;
  private List<TanksWashedWithCargo> tanksWashedWithDifCargo;
}
