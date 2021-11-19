/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableQuantityCommingleCargoDetails {
  @JsonInclude(Include.NON_NULL)
  private Long id;

  private String grade;
  private String tankName;
  private String quantity;
  private String api;
  private String temp;
  private String cargo1Abbreviation;
  private String cargo2Abbreviation;
  private String cargo1Percentage;
  private String cargo2Percentage;

  @JsonInclude(Include.NON_NULL)
  private String cargo1Bblsdbs;

  @JsonInclude(Include.NON_NULL)
  private String cargo2Bblsdbs;

  @JsonInclude(Include.NON_NULL)
  private String cargo1Bbls60f;

  @JsonInclude(Include.NON_NULL)
  private String cargo2Bbls60f;

  @JsonInclude(Include.NON_NULL)
  private String cargo1LT;

  @JsonInclude(Include.NON_NULL)
  private String cargo2LT;

  private String cargo1MT;

  private String cargo2MT;

  @JsonInclude(Include.NON_NULL)
  private String cargo1KL;

  @JsonInclude(Include.NON_NULL)
  private String cargo2KL;

  private String orderedQuantity;
  private Integer priority;
  private Integer loadingOrder;
  private Long cargo1NominationId;
  private Long cargo2NominationId;

  private Long tankId;
  private String fillingRatio;
  private String correctedUllage; // ullage(M)
  private String rdgUllage;
  private String correctionFactor;
  private String slopQuantity;
  private BigDecimal actualQuantity;

  private List<CargoToppingOffSequence> toppingSequence;
  private String timeRequiredForLoading;
  private String tankShortName;
  private Long toppingOffCargoId;
  private String colorCode;
  private BigDecimal quantity1MT;
  private BigDecimal quantity2MT;
  private BigDecimal quantity1M3;
  private BigDecimal quantity2M3;
  private Long cargo1Id;
  private Long cargo2Id;
}
