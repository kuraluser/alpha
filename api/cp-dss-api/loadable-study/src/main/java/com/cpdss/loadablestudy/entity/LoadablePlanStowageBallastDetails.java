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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loadable_plan_stowage_ballast_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanStowageBallastDetails extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "loadable_plan_xid")
  private LoadablePlan loadablePlan;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "actual_quantity")
  private BigDecimal actualQuantity;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "operation_type")
  private String operationType;

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "corrected_ullage")
  private String correctedUllage;

  @Column(name = "sg")
  private String sg;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "port_rotation_xid")
  private Long portRotationId;
}
