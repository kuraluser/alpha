/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class Cargo {
  private Long id;
  private String name;
  private String abbreviation;
  private String api;
  private String companyXId;
}
