/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Vessel tank entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vesseltank")
public class VesselTank extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "vesselxid")
  private Vessel vessel;

  @ManyToOne
  @JoinColumn(name = "tankcategoryxid")
  private TankCategory tankCategory;

  @Column(name = "tankname")
  private String tankName;

  @Column(name = "framenumberfrom")
  private String frameNumberFrom;

  @Column(name = "framenumberto")
  private String frameNumberTo;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "shortname")
  private String shortName;
}
