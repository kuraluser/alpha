/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "fuel_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelMaster extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "fuelMaster")
  private Collection<VesselTank> vesselTankCollection;

  @OneToMany(mappedBy = "fuelMaster")
  private Collection<ConsumptionDetails> consumptionDetailsCollection;
}
