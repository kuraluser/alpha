/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author arun.j */
@Data
public class DischargeStudyAlgoJson {

  private String module;
  private Long id;
  private Long voyageId;
  private String voyageNo;
  private Long vesselId;
  private String name;
  private List<CommingleCargo> commingleCargos;
  private List<LoadableStudyInstruction> instructionMaster;
  private List<DischargeStudyPortRotationJson> dischargeStudyPortRotation;
  private List<CargoNomination> cargoNomination;
  private List<CargoNominationOperationDetails> cargoNominationOperationDetails;
  private List<OnHandQuantity> onHandQuantity;
  private List<PortDetails> portDetails;
  private List<CowHistory> cowHistory;
  private ArrivalConditionJson loadablePlanPortWiseDetails;
  private List<RulePlans> dischargeStudyRuleList;
  private CowDetail cowDetails;
}
