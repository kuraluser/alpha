/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VesselComponent {
  private Long id;
  private Long vesselId;
  private String componentName;
  private String componentCode;
  private Long componentType;
  private Integer machineTypeId;
}
