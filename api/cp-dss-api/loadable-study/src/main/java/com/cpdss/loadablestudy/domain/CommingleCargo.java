/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class CommingleCargo {

  private Long id;

  private Long loadableStudyXId;

  private Long purposeXid;

  private String tankIds;

  private Long cargo1Id;

  private String cargo1Percentage;

  private Long cargo2Id;

  private String cargo2Percentage;

  private String quantity;

  private Boolean isSlopOnly;
}
