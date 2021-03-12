/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
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
@Table(name = "bending_moment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BendingMoment extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "base_draft")
  private BigDecimal baseDraft;

  @Column(name = "base_value")
  private BigDecimal baseValue;

  @Column(name = "draft_correction")
  private BigDecimal draftCorrection;

  @Column(name = "trim_correction")
  private BigDecimal trimCorrection;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
