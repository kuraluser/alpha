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

/** @author jerin.g */
@Entity
@Table(name = "hydrostatic_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HydrostaticTable extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "trim")
  private BigDecimal trim;

  @Column(name = "draft")
  private BigDecimal draft;

  @Column(name = "displacement")
  private BigDecimal displacement;

  @Column(name = "lcb")
  private BigDecimal lcb;

  @Column(name = "lcf")
  private BigDecimal lcf;

  @Column(name = "mtc")
  private BigDecimal mtc;

  @Column(name = "tpc")
  private BigDecimal tpc;

  @Column(name = "vcb")
  private BigDecimal vcb;

  @Column(name = "tkm")
  private BigDecimal tkm;

  @Column(name = "lkm")
  private BigDecimal lkm;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
