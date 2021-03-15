/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

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
}
