/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

  @Column(name = "draft")
  private BigDecimal draft;

  @Column(name = "trim_m1")
  private BigDecimal trimM1;

  @Column(name = "trim_0")
  private BigDecimal trim0;

  @Column(name = "trim_1")
  private BigDecimal trim1;

  @Column(name = "trim_2")
  private BigDecimal trim2;

  @Column(name = "trim_3")
  private BigDecimal trim3;

  @Column(name = "trim_4")
  private BigDecimal trim4;

  @Column(name = "trim_5")
  private BigDecimal trim5;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
