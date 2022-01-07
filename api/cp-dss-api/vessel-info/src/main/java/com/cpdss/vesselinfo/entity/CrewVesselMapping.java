/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for CrewVesselMapping Author: Athul cp */
@Entity
@Table(name = "crew_vessel_mapping")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrewVesselMapping extends EntityDoc {

  @Column(name = "crew_xid")
  private Long crewXId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "vessel_xid")
  private Vessel vessel;

  @Column(name = "is_active")
  private Boolean isActive;
}
