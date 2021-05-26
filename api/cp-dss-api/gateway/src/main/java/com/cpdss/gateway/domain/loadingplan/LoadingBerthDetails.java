/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoadingBerthDetails implements Serializable {

  /**
   * THIS COMMENT NEED TO REMOVE
   *
   * <p>For the GET api, need to return all berth for Port. For Save api. Should collect the data
   * edited/non-edited
   */
  private List<BerthDetails> availableBerths; // From db, per port selected

  private List<BerthDetails> selectedBerths; // To loading-plan DB, as they edited

  public LoadingBerthDetails(List<BerthDetails> availableBerths) {
    this.availableBerths = availableBerths;
  }
}
