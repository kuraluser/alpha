/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class LoadablePlanCommingleDetails {
  private Long id;
  private Long loadingInformationId;
  private Long dischargeInformationId;
  private String grade;
  private String tankName;
  private Double quantity;
  private Double api;
  private Double temperature;
  private String cargo1Abbreviation;
  private String cargo2Abbreviation;
  private Double cargo2Percentage;
  private Double cargo1BblsDbs;
  private Double cargo2BblsDbs;
  private Double cargo1Bbls60f;
  private Double cargo2Bbls60f;
  private Double cargo1Lt;
  private Double cargo2Lt;
  private Double cargo1Mt;
  private Double cargo2Mt;
  private Double cargo1Kl;
  private Double cargo2Kl;
  private Long loadablePatternId;
  private Long DischargePatternId;
  private Long priority;
  private Double orderQuantity;
  private Long loadingOrder;
  private Long tankId;
  private Double fillingRatio;
  private Double correctedUllage;
  private Double correctionFactor;
  private Double rdgUllage;
  private Double slopQuantity;
  private Double timeRequiredForLoading;
  private Double cargo1Percentage;
  private Double quantity1MT;
  private Double quantity2MT;
  private Double quantity1M3;
  private Double quantity2M3;
  private Double ullage1;
  private Double ullage2;
  private String colorCode;
  private Long cargoNomination1Id;
  private Long cargoNomination2Id;
  private Long cargo1Id;
  private Long cargo2Id;
  private Double ullage;
}
