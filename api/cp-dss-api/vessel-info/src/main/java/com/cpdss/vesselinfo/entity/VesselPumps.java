/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "vessel_pumps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselPumps extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "pump_name")
  private String pumpName;

  @Column(name = "pump_code")
  private String pumpCode;

  @Column(name = "capacity")
  private Long capacity;

  @Column(name = "head")
  private Long head;

  @Column(name = "description")
  private String description;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "pumptype_xid", referencedColumnName = "id")
  @ManyToOne
  private PumpType pumpType;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;

  @OneToMany(mappedBy = "vesselPumps")
  private Collection<VesselPumpTankMapping> vesselPumpTankMappingCollection;
}
