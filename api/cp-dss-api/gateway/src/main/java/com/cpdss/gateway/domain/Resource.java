/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class Resource {
  private Long id;
  private String name;
  private String languageKey;
  private Permission permission;
}
