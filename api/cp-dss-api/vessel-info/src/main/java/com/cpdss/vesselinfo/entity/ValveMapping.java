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
@Table(name = "valve_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValveMapping extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "valve_capacity")
  private BigDecimal valveCapacity;

  @Column(name = "maximum_temperature")
  private BigDecimal maximumTemperature;

  @JoinColumn(name = "type_xid", referencedColumnName = "id")
  @ManyToOne
  private ValveType valveType;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
