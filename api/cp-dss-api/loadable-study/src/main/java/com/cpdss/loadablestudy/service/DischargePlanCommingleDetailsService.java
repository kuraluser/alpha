/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;

/**
 * Interface to declare methods related to Discharge plan commingle details
 *
 * @author sreemanikandan.k
 * @since 24/01/2022
 */
public interface DischargePlanCommingleDetailsService {

  void fetchDischargeCommingleDetails(
      com.cpdss.common.generated.LoadableStudy.DischargeCommingleRequest request,
      com.cpdss.common.generated.LoadableStudy.DischargeCommingleReply.Builder replyBuilder)
      throws GenericServiceException;
}
