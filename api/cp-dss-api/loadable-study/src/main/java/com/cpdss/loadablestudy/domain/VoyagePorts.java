/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoyagePorts {
  String portId;
  String eta;
  String etd;
  String portOrder;
  String portType;
  String anchorage;
  String iconUrl;
  String ata;
  String atd;
  String lat;
  String lon;
  String portName;
}
