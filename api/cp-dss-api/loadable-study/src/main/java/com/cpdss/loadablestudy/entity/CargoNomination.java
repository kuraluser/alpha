/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for Cargo Nomination */
@Entity
@Table(name = "cargonomination")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoNomination extends EntityDoc {

  @Column(name = "loadablestudyxid")
  private Long loadableStudyXId;

  @Column(name = "priority")
  private Long priority;

  @Column(name = "cargoxid")
  private Long cargoXId;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "cargocolor")
  private String color;

  @Column(name = "maxtolerence")
  private BigDecimal maxTolerance;

  @Column(name = "mintolerence")
  private BigDecimal minTolerance;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "valvesegregationxid")
  private Long segregationXId;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @OneToMany(
      mappedBy = "cargoNomination",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private Set<CargoNominationPortDetails> cargoNominationPortDetails;
}
