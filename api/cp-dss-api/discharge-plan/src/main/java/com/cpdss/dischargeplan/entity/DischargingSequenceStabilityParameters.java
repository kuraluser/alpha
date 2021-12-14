/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discharging_sequence_stability_parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargingSequenceStabilityParameters extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_information_xid", referencedColumnName = "id")
  private DischargeInformation dischargingInformation;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "time")
  private Integer time;

  @Column(name = "draft")
  private BigDecimal draft;

  @Column(name = "fore_draft")
  private BigDecimal foreDraft;

  @Column(name = "aft_draft")
  private BigDecimal aftDraft;

  @Column(name = "mean_draft")
  private BigDecimal meanDraft;

  @Column(name = "trim")
  private BigDecimal trim;

  @Column(name = "list")
  private BigDecimal list;

  @Column(name = "bending_moment")
  private BigDecimal bendingMoment;

  @Column(name = "shearing_force")
  private BigDecimal shearingForce;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "gom_value")
  private BigDecimal gomValue;

  @Column(name = "sf_frame_number")
  private BigDecimal sfFrameNumber;

  @Column(name = "bm_frame_number")
  private BigDecimal bmFrameNumber;

  @Transient private Long communicationRelatedEntityId;
}
