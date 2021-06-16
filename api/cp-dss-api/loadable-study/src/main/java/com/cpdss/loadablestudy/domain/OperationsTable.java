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
  private String arrFwdDraft;
  private String arrAftDraft;
  private String arrDisplacement;
  private String depFwdDraft;
  private String depAftDraft;
  private String depDisp;
}
