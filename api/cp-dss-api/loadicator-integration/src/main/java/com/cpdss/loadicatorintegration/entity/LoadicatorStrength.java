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
@Table(name = "ld_strength")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadicatorStrength extends EntityDoc {
  @Column(name = "stowageplan_id")
  private Long stowagePlanId;

  @Column(name = "shearing_force_persent_value")
  private BigDecimal shearingForcePresentValue;

  @Column(name = "shearing_force_judgement")
  private String shearingForceJudgement;

  @Column(name = "sf_frame_number")
  private BigDecimal sfFrameNumber;

  @Column(name = "sf_side_shell_value")
  private BigDecimal sfSideShellValue;

  @Column(name = "sf_side_shell_judgement")
  private String sfSideShellJudgement;

  @Column(name = "sf_side_shell_frame_number")
  private BigDecimal sfSideShellFrameNumber;

  @Column(name = "sf_hopper_value")
  private BigDecimal sfHopperValue;

  @Column(name = "sf_hopper_judgement")
  private String sfHopperJudgement;

  @Column(name = "sf_hopper_frame_number")
  private BigDecimal sfHopperFrameNumber;

  @Column(name = "outer_longi_bhd_value")
  private BigDecimal outerLongiBhdValue;

  @Column(name = "outer_longi_bhd_judgement")
  private String outerLongiBhdJudgement;

  @Column(name = "outer_longi_bhd_frame_number")
  private BigDecimal outerLongiBhdFrameNumber;

  @Column(name = "inner_longi_bhd_value")
  private BigDecimal innerLongiBhdValue;

  @Column(name = "inner_longi_bhd_judgement")
  private String innerLongiBhdJudgement;

  @Column(name = "inner_longi_bhd_frame_number")
  private BigDecimal innerLongiBhdFrameNumber;

  @Column(name = "bending_moment_persent_value")
  private BigDecimal bendingMomentPersentValue;

  @Column(name = "bending_moment_persent_judgement")
  private String bendingMomentPersentJudgement;

  @Column(name = "bending_moment_persent_frame_number")
  private BigDecimal bendingMomentPersentFrameNumber;

  @Column(name = "error_status")
  private Boolean errorStatus;

  @Column(name = "error_details")
  private String errorDetails;

  @Column(name = "message_text")
  private String messageText;
}
