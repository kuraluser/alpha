/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class Resource {
  private Long id;
  private String name;
  private String languageKey;
  private Permission permission;
}
