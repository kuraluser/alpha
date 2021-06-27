/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadingBerthDetails implements Serializable {

  /**
   * THIS COMMENT NEED TO REMOVE
   *
   * <p>For the GET api, need to return all berth for Port. For Save api. Should collect the data
   * edited/non-edited
   */
  private List<BerthDetails> availableBerths; // From db, per port selected

  private List<BerthDetails> selectedBerths; // To loading-plan DB, as they edited
}
