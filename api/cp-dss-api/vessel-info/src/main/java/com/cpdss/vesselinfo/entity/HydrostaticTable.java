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
@Table(name = "hydrostatictable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HydrostaticTable extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

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

  @Column(name = "isactive")
  private Boolean isActive;

  @JoinColumn(name = "vesselxid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vesselId;
}
