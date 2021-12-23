/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(Include.NON_EMPTY)
public class Port {
  private Long id;
  private String name;
  private String code;
  private BigDecimal waterDensity;
  private BigDecimal maxDraft;
  private BigDecimal maxAirDraft;
  private String timezone;
  private String timezoneOffsetVal;
  private String timezoneAbbreviation;
  private String countryName;
  private Long countryId;
}
