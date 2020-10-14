/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/**
 * Loadable study response class containing list of {@link LoadableStudy}
 *
 * @author suhail.k
 */
@Data
public class LoadableStudyResponse {

  private CommonSuccessResponse responseStatus;

  private List<LoadableStudy> loadableStudies;
}
