/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class RulesInputs {

  private String prefix;

  private String defaultValue;

  private String type;

  private String max;

  private String min;

  private String value;

  private String suffix;

  private String id;
}
