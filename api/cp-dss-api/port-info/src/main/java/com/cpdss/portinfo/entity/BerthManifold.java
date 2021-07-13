/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

/** The persistent class for the berth_manifold database table. */
@Data
@Entity
@Table(name = "berth_manifold")
public class BerthManifold extends EntityDoc implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "capacity_limit")
  private BigDecimal capacityLimit;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "loading_rate_limit")
  private BigDecimal loadingRateLimit;

  @Column(name = "manifold_connection_number")
  private Integer manifoldConnectionNumber;

  @Column(name = "manifold_height")
  private BigDecimal manifoldHeight;

  @Column(name = "manifold_name")
  private String manifoldName;

  @Column(name = "max_loading_rate")
  private BigDecimal maxLoadingRate;

  @Column(name = "max_pressure")
  private BigDecimal maxPressure;

  // bi-directional many-to-one association to BerthInfo
  @ManyToOne
  @JoinColumn(name = "berth_xid")
  private BerthInfo berthInfo;
}
