/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @author ravi.r */
@Data
public class VoyagePorts {
  private String portName;
  private String portType;
  private String portOrder;
  private String anchorage;
  private String iconUrl;
  private String ata;
  private String atd;
  private String etd;
  private String eta;
  private String lat;
  private String lon;
}
