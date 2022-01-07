/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for CrewDetails Author: Athul cp */
@Entity
@Table(name = "crew_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "crew_name")
  private String crewName;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "crew_rank_xid")
  private CrewRank crewRank;

  @OneToMany(mappedBy = "vessel", fetch = FetchType.EAGER)
  private Set<CrewVesselMapping> crewVesselMappingSet;
}
