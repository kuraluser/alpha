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
@Table(name = "suction_bellmouth_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuctionBellmouthMapping extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "tankxid")
  private Integer tankxid;

  @Column(name = "material")
  private String material;

  @Column(name = "reducer")
  private String reducer;

  @Column(name = "nominal_size")
  private String nominalSize;

  @Column(name = "clear_ht_above_sump_btm")
  private BigDecimal clearHtAboveSumpBtm;

  @Column(name = "clear_ht_above_tank_btm")
  private BigDecimal clearHtAboveTankBtm;

  @Column(name = "suction_area_ratio")
  private String suctionAreaRatio;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "bell_mouthtype_xid", referencedColumnName = "id")
  @ManyToOne
  private SuctionBellmouthType suctionBellmouthType;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
