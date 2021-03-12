/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

@Data
public class UllageUpdateResponse {

  private Long id;

  private String correctionFactor;

  private String correctedUllage;

  private String quantityMt;
}
