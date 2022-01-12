/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterCriteria {
  private String key;
  private String operation;
  private Object value;
  private String attributeName;
}
