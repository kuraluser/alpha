/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableStudy {
  private Integer id;
  private String shipCode;
  private String voyageNo;
  private Integer vesselId;
  private String name;
  private String details;
  private String charterer;
  private String subCharterer;
  private String draftMark;
  private Integer loadlineId;
  private String draftRestriction;
  private String estimatedMaxSG;
  private String maxTempExpected;
  private String loadableStudyStatus;
  private LoadableQuantity loadableQuantity;
  private List<LoadableStudyPortRotation> loadableStudyPortRotation;
  private List<CargoNomination> cargoNomination;
  private List<CargoNominationOperationDetails> cargoNominationOperationDetails;
  private List<OnHandQuantity> handQuantity;
  private List<OnBoardQuantity> boardQuantity;
  private List<PortDetails> portDetails;
}
