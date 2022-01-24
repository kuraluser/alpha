/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author sanalkumar.k */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commingle_cargo_to_discharge_portwise_details")
public class CommingleCargoToDischargePortwiseDetails extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "discharge_study_xid")
  private Long dischargeStudyId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "port_rotation_xid")
  private Long portRotationId;

  @Column(name = "grade")
  private String grade;

  @Column(name = "tank_name")
  private String tankName;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "api")
  private Double api;

  @Column(name = "temperature")
  private Double temperature;

  @Column(name = "cargo1_abbreviation")
  private String cargo1Abbreviation;

  @Column(name = "cargo2_abbreviation")
  private String cargo2Abbreviation;

  @Column(name = "cargo1_percentage")
  private Double cargo1Percentage;

  @Column(name = "cargo2_percentage")
  private Double cargo2Percentage;

  @Column(name = "cargo1_bbls_dbs")
  private BigDecimal cargo1BblsDbs;

  @Column(name = "cargo2_bbls_dbs")
  private BigDecimal cargo2BblsDbs;

  @Column(name = "cargo1_bbls_60f")
  private BigDecimal cargo1Bbls60f;

  @Column(name = "cargo2_bbls_60f")
  private BigDecimal cargo2Bbls60f;

  @Column(name = "cargo1_lt")
  private BigDecimal cargo1Lt;

  @Column(name = "cargo2_lt")
  private BigDecimal cargo2Lt;

  @Column(name = "cargo1_mt")
  private BigDecimal cargo1Mt;

  @Column(name = "cargo2_mt")
  private BigDecimal cargo2Mt;

  @Column(name = "cargo1_kl")
  private BigDecimal cargo1Kl;

  @Column(name = "cargo2_kl")
  private BigDecimal cargo2Kl;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternId;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "order_quantity")
  private String orderQuantity;

  @Column(name = "loading_order")
  private Integer loadingOrder;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "filling_ratio")
  private Double fillingRatio;

  @Column(name = "corrected_ullage")
  private Double correctedUllage;

  @Column(name = "rdg_ullage")
  private Double rdgUllage;

  @Column(name = "correction_factor")
  private Double correctionFactor;

  @Column(name = "slop_quantity")
  private BigDecimal slopQuantity;

  @Column(name = "time_required_for_loading")
  private String timeRequiredForLoading;

  @Column(name = "cargo1_loading_port_xid")
  private Long cargo1LoadingPortId;

  @Column(name = "cargo2_loading_port_xid")
  private Long cargo2LoadingPortId;

  @Column(name = "short_name")
  private String shortName;

  @Column(name = "ullage")
  private Double ullage;

  @Column(name = "cargo_nomination1_xid")
  private Long cargoNomination1XId;

  @Column(name = "cargo_nomination2_xid")
  private Long cargoNomination2XId;

  @Column(name = "cargo1_xid")
  private Long cargo1XId;

  @Column(name = "cargo2_xid")
  private Long cargo2XId;

  @Column(name = "quantity_m3")
  private BigDecimal quantityM3;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "quantity1_mt")
  private BigDecimal quantity1MT;

  @Column(name = "quantity2_mt")
  private BigDecimal quantity2MT;

  @Column(name = "quantity1_m3")
  private BigDecimal quantity1M3;

  @Column(name = "quantity2_m3")
  private BigDecimal quantity2M3;

  @Column(name = "ullage_1")
  private BigDecimal ullage1;

  @Column(name = "ullage_2")
  private BigDecimal ullage2;

  @Column(name = "bl_figure")
  private BigDecimal blfigure;

  @Column(name = "cargo1_bl_figure")
  private Double cargo1BLfigure;

  @Column(name = "cargo2_bl_figure")
  private Double cargo2BLfigure;
}
