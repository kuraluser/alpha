/* Licensed under Apache-2.0 */
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

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "operation_type")
  private String operationType;

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternId;
}