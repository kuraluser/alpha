/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ld_cargo_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoData extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "stowageplan_id")
  private StowagePlan stowagePlan;

  @Column(name = "cargo_id")
  private Long cargoId;

  @Column(name = "cargo_name", length = 100)
  private String cargoName;

  @Column(name = "cargo_abbrev", length = 100)
  private String cargoAbbrev;

  @Column(name = "standard_temp", length = 100)
  private String standardTemp;

  @Column(name = "grade", length = 100)
  private String grade;

  @Column(name = "density")
  private BigDecimal density;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "degf")
  private BigDecimal degf;

  @Column(name = "degc")
  private BigDecimal degc;
}
