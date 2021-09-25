/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.vessel;

import lombok.Data;

@Data
public class VesselManifold {
  Long id;
  String manifoldName;
  String manifoldCode;
  String tankType;
}
