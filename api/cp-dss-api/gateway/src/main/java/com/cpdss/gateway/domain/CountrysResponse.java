/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
public class CountrysResponse {

  private List<CountryInfo> countrys;

  private CommonSuccessResponse responseStatus;
}
