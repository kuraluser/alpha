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

/** @author jerin.g */
@Entity
@Table(name = "loadable_plan_quantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "cargo_nomination_xid")
  private Integer cargoNominationXId;

  @Column(name = "estimated_api")
  private BigDecimal estimatedApi;

  @Column(name = "estimalted_temperature")
  private BigDecimal estimaltedTemperature;

  @Column(name = "order_quantity")
  private BigDecimal orderQuantity;

  @Column(name = "loadable_quantity")
  private BigDecimal loadableQuantity;

  @Column(name = "difference_percentage")
  private BigDecimal differencePercentage;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadableplanxid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePlan loadablePlanXId;
}
