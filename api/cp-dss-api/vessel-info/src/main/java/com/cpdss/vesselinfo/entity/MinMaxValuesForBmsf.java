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
@Table(name = "min_max_values_for_bmsf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinMaxValuesForBmsf extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "min_bm")
  private BigDecimal minBm;

  @Column(name = "max_bm")
  private BigDecimal maxBm;

  @Column(name = "min_sf")
  private BigDecimal minSf;

  @Column(name = "max_sf")
  private BigDecimal maxSf;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
