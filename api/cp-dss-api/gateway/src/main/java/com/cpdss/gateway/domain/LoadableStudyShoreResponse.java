/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/** @author ravi.r */
@Data
public class LoadableStudyShoreResponse {
  private CommonSuccessResponse responseStatus;
  private List<LoadableStudyShore> shoreList;
}
