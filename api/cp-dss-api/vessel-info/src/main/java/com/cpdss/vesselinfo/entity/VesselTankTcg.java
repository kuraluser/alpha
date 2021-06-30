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
@Table(name = "vessel_tank_tcg")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselTankTcg extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "capacity")
  private BigDecimal capacity;

  @Column(name = "tcg")
  private BigDecimal tcg;

  @Column(name = "lcg")
  private BigDecimal lcg;

  @Column(name = "vcg")
  private BigDecimal vcg;

  @Column(name = "inertia")
  private BigDecimal inertia;

  @Column(name = "is_active")
  private Boolean isActive;
}
