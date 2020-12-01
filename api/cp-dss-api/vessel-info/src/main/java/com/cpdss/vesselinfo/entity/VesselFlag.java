/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "vesselflag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselFlag extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "flagimagepath")
  private String flagImagePath;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "vesselflag")
  private Collection<Vessel> vesselCollection;
}
