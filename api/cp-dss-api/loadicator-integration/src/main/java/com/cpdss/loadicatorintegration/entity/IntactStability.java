/* Licensed under Apache-2.0 */
package com.cpdss.loadicatorintegration.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ld_intact_stability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntactStability extends EntityDoc {
  @Column(name = "stowageplan_id")
  private Long stowagePlanId;

  @Column(name = "bigintial_gomvalue")
  private BigDecimal bigintialGomvalue;

  @Column(name = "bigintialgomjudgement")
  private String bigintialGomjudgement;

  @Column(name = "maximumrightinglevervalue")
  private BigDecimal maximumRightingLeverValue;

  @Column(name = "maximumrightingleverjudgement")
  private String maximumRightingLeverJudgement;

  @Column(name = "angleatmaxrlevervalue")
  private BigDecimal angleatMaxrLeverValue;

  @Column(name = "angleatmaxrleverjudgement")
  private String angleatMaxrLeverJudgement;

  @Column(name = "areaofstability_0_30_value")
  private BigDecimal areaOfStability_0_30_Value;

  @Column(name = "areaofstability_0_30_judgement")
  private String areaOfstability_0_30_Judgement;

  @Column(name = "areaofstability_0_40_value")
  private BigDecimal areaOfStability_0_40_Value;

  @Column(name = "areaofstability_0_40_judgement")
  private String areaOfStability_0_40_Judgement;

  @Column(name = "areaofstability_30_40_value")
  private BigDecimal areaOfStability_30_40_Value;

  @Column(name = "areaofstability_30_40_judgement")
  private String areaOfStability_30_40_Judgement;

  @Column(name = "heel_by_steady_wind_value")
  private BigDecimal heelBySteadyWindValue;

  @Column(name = "heel_by_steady_wind_judgement")
  private String heelBySteadyWindJudgement;

  @Column(name = "stability_area_ba_value")
  private BigDecimal stabilityAreaBaValue;

  @Column(name = "stability_area_ba_judgement")
  private String stabilityAreaBaJudgement;

  @Column(name = "gm_allowable_curve_check_value")
  private BigDecimal gmAllowableCurveCheckValue;

  @Column(name = "gm_allowable_curve_check_judgement")
  private String gm_allowableCurveCheckJudgement;

  @Column(name = "error_status")
  private Boolean errorStatus;

  @Column(name = "error_details")
  private String errorDetails;

  @Column(name = "message_text")
  private String messageText;
}
