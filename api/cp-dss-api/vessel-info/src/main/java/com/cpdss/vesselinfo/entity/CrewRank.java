/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for CrewRank Author: Athul cp */
@Entity
@Table(name = "crew_rank")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrewRank extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "rank_name")
  private String rankName;

  @Column(name = "rank_short_name")
  private String rankShortName;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "crewRank")
  private List<CrewDetails> crewDetails;
}
