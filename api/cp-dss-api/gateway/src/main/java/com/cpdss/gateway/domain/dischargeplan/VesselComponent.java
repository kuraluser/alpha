/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vessel Component For Algo Receiving
 *
 * @author sreemanikandan.k
 * @since 03-12-2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VesselComponent {
  private Long id;
  private Long vesselId;
  private String manifoldName;
  private String manifoldCode;
  private String tankType;
  private Integer machineTypeId;
  private String bottomLineName;
  private String bottomLineCode;
}
