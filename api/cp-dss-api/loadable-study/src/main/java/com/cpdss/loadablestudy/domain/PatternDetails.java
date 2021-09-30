/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class PatternDetails {
  private List<LoadablePlanQuantityDto> loadablePlanQuantityList;
  private List<LoadablePatternCargoToppingOffSequenceDto> toppingOffSequenceList;
  private List<LoadablePlanCommingleDetailsDto> loadablePlanCommingleDetails;
  private List<LoadablePlanStowageDetailsDto> loadablePlanStowageDetailsList;
  private List<LoadablePlanBallastDetailsDto> loadablePlanBallastDetailsList;
  private List<LoadablePlanComminglePortwiseDetailsDto> loadablePlanComminglePortwiseDetailsList;
  private List<LoadablePatternCargoDetailsDto> loadablePatternCargoDetailsList;
  private List<LoadablePlanStowageBallastDetailsDto> loadablePlanStowageBallastDetailsList;
  private List<SynopticalTableLoadicatorDataDto> synopticalTableLoadicatorData;
}
