/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * On hand quantity response
 *
 * @author suhail.k
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class OnBoardQuantity {

  private Long id;

  private Long portId;

  private Long tankId;

  private String tankName;

  private Long cargoId;

  private BigDecimal sounding;

  private BigDecimal quantity;

  private BigDecimal actualWeight;

  private BigDecimal volume;

  private String colorCode;

  private String abbreviation;

  private Long loadableStudyId;

  private BigDecimal api;

  private Boolean loadOnTop;

  private Boolean isObqComplete;

  private BigDecimal temperature = BigDecimal.ZERO;
}
