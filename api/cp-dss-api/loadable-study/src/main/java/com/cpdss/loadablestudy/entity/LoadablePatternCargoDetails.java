/* Licensed under Apache-2.0 */
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
}
