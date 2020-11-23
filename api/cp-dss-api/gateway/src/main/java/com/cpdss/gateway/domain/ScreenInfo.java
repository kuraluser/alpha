/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Dto for screen information */
@Data
@AllArgsConstructor
public class ScreenInfo {

  private Long id;
  private String name;
  private String languageKey;
  private boolean add;
  private boolean edit;
  private boolean delete;
  private boolean view;
}
