/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class Permission {
  private boolean add;
  private boolean edit;
  private boolean delete;
  private boolean view;
}
