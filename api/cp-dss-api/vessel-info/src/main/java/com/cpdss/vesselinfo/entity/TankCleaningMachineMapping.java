/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
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
@Table(name = "tank_cleaning_machine_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TankCleaningMachineMapping extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "tank_cleaning_machine_type_xid", referencedColumnName = "id")
  @ManyToOne
  private TankCleaningMachineType tankCleaningMachineType;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
