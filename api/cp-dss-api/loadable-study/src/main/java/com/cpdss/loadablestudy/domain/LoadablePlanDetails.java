/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePlanDetails {

  private Long caseNumber;
  private LoadablePlanPortWiseDetails loadablePlanPortWiseDetails;
}
