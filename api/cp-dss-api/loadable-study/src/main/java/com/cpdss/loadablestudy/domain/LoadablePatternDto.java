/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.loadablestudy.entity.LoadablePlan;
import java.util.Collection;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePatternDto {

  private Long id;

  private Integer caseNumber;

  private String constraints;

  private String differenceColor;

  private Long loadableStudyStatus;

  private Boolean isActive;

  private Boolean feedbackLoop;

  private Integer feedbackLoopCount;

  private Collection<LoadablePlan> loadablePlanCollection;

  private LoadableStudyDto loadableStudy;
}
