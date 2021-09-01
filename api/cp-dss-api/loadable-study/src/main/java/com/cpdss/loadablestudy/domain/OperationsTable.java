/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Builder;
import lombok.Data;

/** Details of OperationsTable - Loadable Plan Report */
@Builder
@Data
public class OperationsTable {
  private String operation;
  private String portName;
  private String country;
  private String laycanRange;
  private String eta;
  private String etd;
  private double arrFwdDraft;
  private double arrAftDraft;
  private double arrDisplacement;
  private double depFwdDraft;
  private double depAftDraft;
  private double depDisp;
}
