/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanComments {
  private Long id;
  private String userName;
  private String dataAndTime;
  private String comment;
}
