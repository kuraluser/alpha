/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Tank category entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tankcategory")
public class TankCategory extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "shortname")
  private String shortName;

  @Column(name = "isactive")
  private Boolean isActive;
}
