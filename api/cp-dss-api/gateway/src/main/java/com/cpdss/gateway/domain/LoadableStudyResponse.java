/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/**
 * Loadable study response class containing list of {@link LoadableStudy}
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_NULL)
public class LoadableStudyResponse {

  private CommonSuccessResponse responseStatus;

  private List<LoadableStudy> loadableStudies;

  private Long loadableStudyId;
}
