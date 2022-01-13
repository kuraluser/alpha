/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.crewmaster;

import com.cpdss.gateway.domain.Vessel;
import lombok.Data;

@Data
public class CrewVesselMapping {
  private Long id;
  private Vessel vessel;
}
