/* Licensed at AlphaOri Technologies */
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
@Table(name = "ld_trim")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadicatorTrim extends EntityDoc {

  @Column(name = "stowageplan_id")
  private Long stowagePlanId;

  @Column(name = "fore_draft_value")
  private BigDecimal foreDraft;

  @Column(name = "aft_draft_value")
  private BigDecimal aftDraft;

  @Column(name = "trim_value")
  private BigDecimal trim;

  @Column(name = "heel_value")
  private BigDecimal heel;

  @Column(name = "mean_draft_value")
  private BigDecimal meanDraft;

  @Column(name = "mean_draft_judgement")
  private String meanDraftJudgement;

  @Column(name = "displacement_value")
  private BigDecimal displacementValue;

  @Column(name = "displacement_judgement")
  private String displacementJudgement;

  @Column(name = "maximum_draft_value")
  private BigDecimal maximumDraft;

  @Column(name = "maximum_draft_judgement")
  private String maximumDraftJudgement;

  @Column(name = "air_draft_value")
  private BigDecimal airDraft;

  @Column(name = "air_draft_judgement")
  private String airDraftJudgement;

  @Column(name = "minimum_fore_draft_in_rough_weather_value")
  private BigDecimal minimumForeDraftInRoughWeatherValue;

  @Column(name = "minimum_fore_draft_in_rough_weather_judgement")
  private String minimumForeDraftInRoughWeatherValueJudgement;

  @Column(name = "maximum_allowable_visibility")
  private BigDecimal maximumAllowableVisibility;

  @Column(name = "maximum_allowable_judement")
  private String maximumAllowableJudgement;

  @Column(name = "error_status")
  private Boolean errorStatus;

  @Column(name = "error_details")
  private String errorDetails;

  @Column(name = "message_text")
  private String messageText;

  @Column(name = "hogsag")
  private BigDecimal deflection;
}
