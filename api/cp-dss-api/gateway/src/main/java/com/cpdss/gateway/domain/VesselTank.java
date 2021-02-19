/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
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

  private Long coatingTypeId;

  private String lcg;

  private String vcg;

  private String tcg;

  private BigDecimal fillCapcityCubm;

  private String fullCapacityCubm;

  private BigDecimal density;

  private Integer group;

  private Integer order;

  private Integer displayOrder;

  private BigDecimal fullCapcityCubm;

  private Boolean isLoadicatorUsing;
}
