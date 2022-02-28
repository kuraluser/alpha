/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.chartermaster;

import com.cpdss.gateway.domain.Vessel;
import java.util.List;
import lombok.Data;

@Data
public class CharterDetailed {
  private Long id;
  private String charterName;
  private Long charterTypeId;
  private String charterTypeName;
  private Long charterCountryId;
  private String charterCountryName;
  private Long charterCompanyId;
  private String charterCompanyName;

  private List<Vessel> vesselInformation;
}
