/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for filter criteria of data
 *
 * @author sreekumar.k
 */
@Data
@AllArgsConstructor
public class FilterCriteria {
  private String key;
  private String operation;
  private Object value;
}
