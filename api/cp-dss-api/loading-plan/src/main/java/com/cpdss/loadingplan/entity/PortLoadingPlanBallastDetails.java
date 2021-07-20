/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

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
@Table(name = "port_loading_plan_stowage_ballast_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortLoadingPlanBallastDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_information_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "corrected_ullage")
  private BigDecimal correctedUllage;

  @Column(name = "weight")
  private BigDecimal weight;

  @Column(name = "rdg_ullage")
  private BigDecimal rdgUllage;

  @Column(name = "filling_percentage")
  private BigDecimal fillingPercentage;

  @Column(name = "correction_factor")
  private BigDecimal correctionFactor;

  @Column(name = "observed_m3")
  private BigDecimal observedM3;

  @Column(name = "observed_barrels")
  private BigDecimal observedBarrels;

  @Column(name = "observed_barrels_at_60")
  private BigDecimal observedBarrelsAt60;

  @Column(name = "sg")
  private BigDecimal sg;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "operation_xid")
  private Long operationXId;

  @Column(name = "port_condition_xid")
  private Long portConditionXId;

  @Column(name = "operation_type")
  private Long operationType;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "actual_quantity")
  private BigDecimal actualQuantity;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "port_rotation_xid")
  private Long portRotationXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "arrival_departutre")
  private Integer conditionType;

  @Column(name = "actual_planned")
  private Integer valueType;
}
