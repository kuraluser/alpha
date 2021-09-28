/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import java.util.List;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class PatternDetails {
  private List<LoadablePlanQuantity> loadablePlanQuantityList;
  private List<LoadablePatternCargoToppingOffSequence> toppingOffSequenceList;
  private List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails;
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetailsList;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetailsList;
  private List<LoadablePlanComminglePortwiseDetails> loadablePlanComminglePortwiseDetailsList;
  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
      loadablePatternCargoDetailsList;
  private List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetailsList;
  private List<com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData>
      synopticalTableLoadicatorData;
}
