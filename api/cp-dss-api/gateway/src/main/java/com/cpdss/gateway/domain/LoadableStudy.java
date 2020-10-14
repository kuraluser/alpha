/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;

import lombok.Data;

/**
 * DTO for Loadable study
 *
 * @author suhail.k
 */
@Data
public class LoadableStudy {

  private Long id;

  private String name;

  private String detail;

  private String status;

  private String createdDate;
  
  private String charterer;
  
  private String subCharterer;
  
  private BigDecimal draftMark;
  
  private Long loadLineXId;
  
  private BigDecimal draftRestriction;
  
  private BigDecimal maxTempExpected;
}
