/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.crewmaster;

import java.util.List;
import lombok.Data;

@Data
public class CrewDetailed {
  private Long id;
  private String crewName;
  private String crewRank;
  private List<CrewVesselMapping> vesselInformation;
}
