/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.Map;
import lombok.Data;

@Data
public class CountrysMapResponse {

  private Map<Long, String> countryMap;

  private CommonSuccessResponse responseStatus;
}
