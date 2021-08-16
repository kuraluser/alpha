/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VesselManiFold {
  private Long maniFoldId;
  private Long vesselId;
  private String componentName;
  private String componentCode;
  private Long componentType;
  private Integer machineTypeId;
  private String componentTypeName;
}
