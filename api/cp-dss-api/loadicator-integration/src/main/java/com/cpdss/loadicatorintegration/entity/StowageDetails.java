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
@Table(name = "ld_stowage_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StowageDetails extends EntityDoc {

  @Column(name = "stowageplan_id")
  private Long stowagePlanId;

  @Column(name = "cargo_name", length = 100)
  private String cargoName;

  @Column(name = "cargo_book_id")
  private Long cargoBookId;

  @Column(name = "specific_gravity")
  private BigDecimal specificGravity;

  @Column(name = "cargo_id")
  private Long cargoId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "tank_name", length = 100)
  private String tankName;

  @Column(name = "tank_id")
  private Long tankId;

  @Column(name = "short_name", length = 100)
  private String shortName;
}
