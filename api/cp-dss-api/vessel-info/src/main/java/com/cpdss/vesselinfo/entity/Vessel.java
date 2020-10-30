/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Set;
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

/**
 * Vessel entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vessel")
public class Vessel extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "masterxid")
  private Long masterXId;

  @Column(name = "cheifofficerxid")
  private Long cheifOfficerXId;

  @ManyToOne
  @JoinColumn(name = "flagxid")
  private VesselFlag vesselFlag;

  @Column(name = "imonumber")
  private String imoNumber;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "companyxid")
  private Long companyXId;

  @Column(name = "deadweight")
  private String deadWeight;

  @OneToMany(mappedBy = "vessel")
  private Set<VesselDraftCondition> vesselDraftConditions;
}
