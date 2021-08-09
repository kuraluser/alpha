/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/** @Author jerin.g */
@Data
public class DischargePlanDetailsResponse {
  
  private Long portId;

  private Long  instructionId;

  private Long cowType;

  private String dischargeRate;

  private List<Cargo> cargoNominations;

  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;

  @JsonInclude(Include.NON_NULL)
  private List<List<VesselTank>> tankLists;

  private List<BackLoading> backLoadings;
}
