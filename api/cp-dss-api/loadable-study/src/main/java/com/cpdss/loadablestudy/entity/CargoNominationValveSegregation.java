/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for cargonominationvalvesegregation */
@Entity
@Table(name = "cargonominationvalvesegregation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoNominationValveSegregation extends EntityDoc {

  @Column(name = "name")
  private String name;
}
