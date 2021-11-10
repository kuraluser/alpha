/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "port_loading_plan_stability_parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PortLoadingPlanStabilityParameters extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_information_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "port_rotation_xid")
  private Long portRotationXId;

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

  @Column(name = "shearing_force")
  private BigDecimal shearingForce;

  @Column(name = "bending_moment")
  private BigDecimal bendingMoment;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "arrival_departutre")
  private Integer conditionType;

  @Column(name = "actual_planned")
  private Integer valueType;

  @Column(name = "freeboard")
  private BigDecimal freeboard;

  @Column(name = "manifold_height")
  private BigDecimal manifoldHeight;
}
