/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "loadable_pattern_cargo_details")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePatternCargoDetails extends EntityDoc {

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "planned_quantity")
  private BigDecimal plannedQuantity;

  @Column(name = "actual_quantity")
  private BigDecimal actualQuantity;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "operation_type")
  private String operationType;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "corrected_ullage")
  private BigDecimal correctedUllage;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "cargo_xid")
  private Long cargoId;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "port_rotation_xid")
  private Long portRotationId;

  @Column(name = "correction_factor")
  private String correctionFactor;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "filling_ratio")
  private String fillingRatio;

  @Column(name = "cargo_nomination_temperature")
  private BigDecimal cargoNominationTemperature;

  // DS fields
  @Column(name = "on_board")
  private BigDecimal onBoard;

  @Column(name = "max_tank_volume")
  private BigDecimal maxTankVolume;

  @Column(name = "actual_api")
  private BigDecimal actualApi;

  @Column(name = "actual_temperature")
  private BigDecimal actualTemperature;

  @Column(name = "actual_rdg_ullage")
  private BigDecimal actualRdgUllage;

  @Transient private Long communicationRelatedEntityId;
}
