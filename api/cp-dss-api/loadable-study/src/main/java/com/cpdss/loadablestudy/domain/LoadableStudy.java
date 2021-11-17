/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_JSON_MODULE_NAME;

import com.fasterxml.jackson.annotation.JsonInclude;
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
  private Boolean loadOnTopForSlopTank; // 'loadOnTop' was older name
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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<LoadableStudyAttachment> LoadableStudyAttachment;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<SynopticalTable> synopticalTableDetails;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private VoyageDto voyage;

  private Long duplicatedFrom;
  private Long loadableStudyStatusId;
  private Boolean isCargoNominationCompleted;
  private Boolean isPortsCompleted;
  private Boolean isOHQCompleted;
  private Boolean isOBQCompleted;
  private Boolean isDischargePortCompleted;
  private Long planingTypeId;
  private Integer caseNo;

  // Last 3 voyage data and cargo, tank allocations
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<VoyageHistoryDto> voyageCargoHistories;
}
