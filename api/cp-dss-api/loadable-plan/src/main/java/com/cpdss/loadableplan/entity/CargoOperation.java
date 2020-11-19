/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for Cargo Operations
 *
 * @author suhail.k
 */
@Entity
@Table(name = "cargooperation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoOperation extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "isactive")
  private Boolean isActive;
}
