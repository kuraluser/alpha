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
@Table(name = "ullage_table_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UllageTableData extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "ullage_depth")
  private BigDecimal ullageDepth;

  @Column(name = "even_keel_capacity_barrel")
  private BigDecimal evenKeelCapacityBarrel;

  @Column(name = "even_keel_capacity_cubm")
  private BigDecimal evenKeelCapacityCubm;

  @Column(name = "sound_depth")
  private BigDecimal soundDepth;

  @Column(name = "tank_short_name")
  private String tankShortName;

  @Column(name = "even_keel_weight_mton")
  private BigDecimal evenKeelWeightMton;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;

  @JoinColumn(name = "tank_xid", referencedColumnName = "id")
  @ManyToOne
  private VesselTank vesselTank;
}
