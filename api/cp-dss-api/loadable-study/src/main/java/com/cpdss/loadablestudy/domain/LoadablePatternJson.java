/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;

import lombok.Data;

/**
 *
 * @author Sanal
 */
@Data
public class LoadablePatternJson {

	  private Long loadablePatternId;
	  private Long loadableStudyStatusId;
	  private Integer caseNumber;
	  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
	  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
	  private List<LoadablePlanStowageDetailsJson> loadablePlanStowageDetails;
	  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
	  private StabilityParameter stabilityParameters;
	  private Boolean confirmPlanEligibility;

}
