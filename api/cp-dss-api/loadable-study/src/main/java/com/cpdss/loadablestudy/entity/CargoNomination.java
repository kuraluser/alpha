/* Licensed at AlphaOri Technologies */
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cargo_nomination")
public class CargoNomination extends EntityDoc {

  @Column(name = "loadable_study_xid")
  private Long loadableStudyXId;

  @Column(name = "priority")
  private Long priority;

  @Column(name = "cargo_xid")
  private Long cargoXId;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "cargo_color")
  private String color;

  @Column(name = "max_tolerence")
  private BigDecimal maxTolerance;

  @Column(name = "min_tolerence")
  private BigDecimal minTolerance;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "valve_segregation_xid")
  private Long segregationXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "mode_xid")
  private Long mode;

  @OneToMany(
      mappedBy = "cargoNomination",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private Set<CargoNominationPortDetails> cargoNominationPortDetails;
}
