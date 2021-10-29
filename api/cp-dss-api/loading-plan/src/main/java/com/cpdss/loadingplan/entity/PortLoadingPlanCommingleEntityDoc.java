/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract class for all Entity class
 *
 * @author r.krishnakumar
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class PortLoadingPlanCommingleEntityDoc extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "grade")
  private String grade;

  @Column(name = "tank_name")
  private String tankName;

  @Column(name = "quantity")
  private String quantity;

  @Column(name = "api")
  private String api;

  @Column(name = "temperature")
  private String temperature;

  @Column(name = "cargo1_abbreviation")
  private String cargo1Abbreviation;

  @Column(name = "cargo2_abbreviation")
  private String cargo2Abbreviation;

  @Column(name = "cargo1_percentage")
  private String cargo1Percentage;

  @Column(name = "cargo2_percentage")
  private String cargo2Percentage;

  @Column(name = "cargo1_bbls_dbs")
  private String cargo1BblsDbs;

  @Column(name = "cargo2_bbls_dbs")
  private String cargo2BblsDbs;

  @Column(name = "cargo1_bbls_60f")
  private String cargo1Bbls60f;

  @Column(name = "cargo2_bbls_60f")
  private String cargo2Bbls60f;

  @Column(name = "cargo1_lt")
  private String cargo1Lt;

  @Column(name = "cargo2_lt")
  private String cargo2Lt;

  @Column(name = "cargo1_mt")
  private String cargo1Mt;

  @Column(name = "cargo2_mt")
  private String cargo2Mt;

  @Column(name = "cargo1_kl")
  private String cargo1Kl;

  @Column(name = "cargo2_kl")
  private String cargo2Kl;

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
  private String fillingRatio;

  @Column(name = "corrected_ullage")
  private Long correctedUllage;

  @Column(name = "rdg_ullage")
  private String rdgUllage;

  @Column(name = "correction_factor")
  private String correctionFactor;

  @Column(name = "slop_quantity")
  private String slopQuantity;

  @Column(name = "time_required_for_loading")
  private String timeRequiredForLoading;

  @Column(name = "cargo1_loading_port_xid")
  private Long cargo1LoadingPortId;

  @Column(name = "cargo2_loading_port_xid")
  private Long cargo2LoadingPortId;

  @Column(name = "short_name")
  private String shortName;

  @Column(name = "ullage")
  private String ullage;

  @Column(name = "cargo_nomination1_xid")
  private Long cargoNomination1XId;

  @Column(name = "cargo_nomination2_xid")
  private Long cargoNomination2XId;

  @Column(name = "cargo1_xid")
  private Long cargo1XId;

  @Column(name = "cargo2_xid")
  private Long cargo2XId;

  @Column(name = "quantity_m3")
  private String quantityM3;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "arrival_departure")
  private Integer conditionType;

  @Column(name = "actual_planned")
  private Integer valueType;

  @Column(name = "quantity1_mt")
  private String quantity1MT;

  @Column(name = "quantity2_mt")
  private String quantity2MT;

  @Column(name = "quantity1_m3")
  private String quantity1M3;

  @Column(name = "quantity2_m3")
  private String quantity2M3;

  @Column(name = "ullage_1")
  private String ullage1;

  @Column(name = "ullage_2")
  private String ullage2;
}
