/* Licensed under Apache-2.0 */
package com.cpdss.cargoinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargomaster")
public class Cargo extends EntityDoc {

  @Column(name = "crudetype")
  private String crudeType;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "api")
  private String api;

  @Column(name = "companyxid")
  private String companyXId;
}
