/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class VesselTank {

  private Long id;

  private Long categoryId;

  private String categoryName;

  private String name;

  private String frameNumberFrom;

  private String frameNumberTo;

  private String shortName;

  private String heightFrom;

  private String heightTo;

  private boolean isSlopTank;

  private BigDecimal fillCapcityCubm;

  private BigDecimal density;

  private Integer group;

  private Integer order;
}
