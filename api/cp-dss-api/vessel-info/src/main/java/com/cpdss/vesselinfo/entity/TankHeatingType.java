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
@Table(name = "tank_heating_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TankHeatingType extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "heating_type")
  private String heatingType;

  @Column(name = "is_active")
  private Boolean isActive;
}
