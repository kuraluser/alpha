/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The persistent class for the vessel_manifold database table. */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vessel_manifold")
public class VesselManifold extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "manifold_code")
  private String manifoldCode;

  @Column(name = "manifold_name")
  private String manifoldName;

  @Column(name = "vessel_xid")
  private Long vesselXid;

  // bi-directional many-to-one association to TankType
  @ManyToOne
  @JoinColumn(name = "manifold_type_xid")
  private TankType tankType;
}
