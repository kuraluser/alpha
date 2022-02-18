/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.chartermaster;

import com.cpdss.gateway.domain.Vessel;
import lombok.Data;

@Data
public class CharterVesselMapping {
  private Long id;
  private Vessel vessel;
}
