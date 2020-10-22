/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for portinfo table */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portinfo")
public class PortInfo extends EntityDoc {

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "portInfo")
  private Set<CargoPortMapping> cargoportmappingSet;
}
