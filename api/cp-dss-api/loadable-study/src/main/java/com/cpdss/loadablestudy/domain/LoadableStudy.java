/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_JSON_MODULE_NAME;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableStudy {
  private String module = LOADABLE_STUDY_JSON_MODULE_NAME;
  private Long id;
  private Long voyageId;
  private String voyageNo;
  private Long vesselId;
  private String name;
  private String details;
  private String charterer;
  private String subCharterer;
  private String draftMark;
  private Long loadlineId;
  private String draftRestriction;
  private String estimatedMaxSG;
  private String maxTempExpected;
  private String maxAirTemp;
  private String maxWaterTemp;
  private Boolean loadOnTop;
  private Long cargoToBeDischargeFirstId;
  private LoadableQuantity loadableQuantity;
  private List<CommingleCargo> commingleCargos;
  private List<LoadableStudyPortRotation> loadableStudyPortRotation;
  private List<CargoNomination> cargoNomination;
  private List<CargoNominationOperationDetails> cargoNominationOperationDetails;
  private List<OnHandQuantity> onHandQuantity;
  private List<OnBoardQuantity> onBoardQuantity;
  private List<PortDetails> portDetails;
  private Boolean feedbackLoop;
  private Integer feedbackLoopCount;
  private List<RulePlans> loadableStudyRuleList;
  private List<LoadableStudyAttachment> LoadableStudyAttachment;
  private List<SynopticalTable> synopticalTableDetails;
}
