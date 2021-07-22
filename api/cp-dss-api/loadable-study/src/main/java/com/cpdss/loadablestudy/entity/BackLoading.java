/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author arun.j */
@Entity
@Table(name = "cargo_back_loading")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackLoading extends EntityDoc {

  private static final long serialVersionUID = -5326537398456003514L;

  private Long id;

  @Column(name = "discharge_study_xid")
  private Long dischargeStudyId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "cargo_xid")
  private Long cargoId;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "cargo_colour")
  private String colour;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "is_active")
  private boolean is_active;
}
