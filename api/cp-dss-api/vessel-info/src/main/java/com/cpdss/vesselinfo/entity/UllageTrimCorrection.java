/* Licensed under Apache-2.0 */
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

@Entity
@Table(name = "ullage_trim_correction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UllageTrimCorrection extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "vessel_xid")
  private Vessel vessel;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "ullage_depth")
  private BigDecimal ullageDepth;

  @Column(name = "trim_m1")
  private BigDecimal trimM1;

  @Column(name = "trim_m2")
  private BigDecimal trimM2;

  @Column(name = "trim_m3")
  private BigDecimal trimM3;

  @Column(name = "trim_m4")
  private BigDecimal trimM4;

  @Column(name = "trim_m5")
  private BigDecimal trimM5;

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

  @Column(name = "trim_6")
  private BigDecimal trim6;

  @Column(name = "is_active")
  private Boolean isActive;
}
