/* Licensed under Apache-2.0 */
package com.cpdss.loadicatorintegration.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ld_other_tank_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherTankDetails extends EntityDoc {

  @Column(name = "stowage_plan_id")
  private Long stowagePlanId;

  @Column(name = "tank_name", length = 100)
  private String tankName;

  @Column(name = "tank_id")
  private Long tankId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "short_name", length = 100)
  private String shortName;
}
