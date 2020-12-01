/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "suction_bellmouth_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuctionBellmouthType extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "name")
  private String name;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "suctionBellmouthType")
  private Collection<SuctionBellmouthMapping> suctionBellmouthMappingCollection;
}
