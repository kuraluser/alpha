/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Vessel flag entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@Table(name = "vesselflag")
public class VesselFlag extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "flagimagepath")
  private String flagImagePath;
}
