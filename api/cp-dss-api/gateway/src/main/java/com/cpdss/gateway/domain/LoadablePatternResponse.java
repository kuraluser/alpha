/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePatternResponse {
  private CommonSuccessResponse responseStatus;
  private List<List<VesselTank>> tankLists;
  private List<LoadablePattern> loadablePatterns;
  private String loadablePatternCreatedDate;
  private String loadableStudyName;
}
