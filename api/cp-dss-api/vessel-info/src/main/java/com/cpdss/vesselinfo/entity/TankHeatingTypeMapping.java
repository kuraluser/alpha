/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "tank_heating_type_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TankHeatingTypeMapping extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "vessel_xid")
  private Integer vesselId;

  @Column(name = "tankx_id")
  private Integer tankId;

  @Column(name = "heating_type_xid")
  private Integer heatingTypeId;

  @Column(name = "is_active")
  private Boolean isActive;
}
