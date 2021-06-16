/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "loadable_plan_commingle_details_portwise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadablePlanComminglePortwiseDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "operation_type ")
  private String operationType;

  @Column(name = "loadable_plan_xid")
  private Long loadablePlanId;

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

  @JoinColumn(name = "loadable_pattern_xid")
  @ManyToOne
  private LoadablePattern loadablePattern;

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
  private String correctedUllage;

  @Column(name = "correction_factor")
  private String correctionFactor;

  @Column(name = "rdg_ullage")
  private String rdgUllage;

  @Column(name = "port_rotation_xid")
  private Long portRotationXid;

  @Column(name = "actual_quantity")
  private BigDecimal actualQuantity;

  @Column(name = "cargo1_nomination_xid")
  private Long cargo1NominationId;

  @Column(name = "cargo2_nomination_xid")
  private Long cargo2NominationId;

  @Column(name = "short_name")
  private String tankShortName;
}
