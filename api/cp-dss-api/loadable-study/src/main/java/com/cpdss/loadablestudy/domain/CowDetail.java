/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/**
 * DTO for loadable study port rotation
 *
 * @author Sanal
 */
@Data
public class CowDetail {

  private Long id;
  private Long type;
  private Long percentage;
  private String tanks;
}
