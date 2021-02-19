/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** Voyage status response */
@Data
@JsonInclude(Include.NON_EMPTY)
public class VoyageStatusResponse {

  private CommonSuccessResponse responseStatus;

  private List<SynopticalCargoBallastRecord> cargoQuantities;

  private List<SynopticalCargoBallastRecord> ballastQuantities;

  private List<OnHandQuantity> bunkerQuantities;

  private List<List<VesselTank>> cargoTanks;

  private List<List<VesselTank>> bunkerTanks;

  private List<List<VesselTank>> bunkerRearTanks;

  private List<List<VesselTank>> ballastFrontTanks;

  private List<List<VesselTank>> ballastCenterTanks;

  private List<List<VesselTank>> ballastRearTanks;

  private List<Cargo> cargoConditions;

  private StabilityConditions stabilityConditions;

  private BunkerConditions bunkerConditions;
}
