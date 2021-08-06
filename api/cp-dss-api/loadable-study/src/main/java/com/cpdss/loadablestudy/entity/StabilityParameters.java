/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "stability_parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StabilityParameters extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "port_xid")
  private Long portXid;

  @Column(name = "fwd_draft")
  private String fwdDraft;

  @Column(name = "aft_draft")
  private String aftDraft;

  @Column(name = "mean_draft")
  private String meanDraft;

  @Column(name = "trim_value")
  private String trimValue;

  @Column(name = "heal")
  private String heal;

  @Column(name = "bending_moment")
  private String bendingMoment;

  @Column(name = "shearing_force")
  private String shearingForce;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;
  
  //DS field
  @Column(name = "air_draft")
  private String airDraft;
}
