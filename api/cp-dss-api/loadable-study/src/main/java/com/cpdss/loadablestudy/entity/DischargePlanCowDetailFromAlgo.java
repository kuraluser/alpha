/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.CascadeType;
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
@Table(name = "discharge_plan_cow_details_from_algo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargePlanCowDetailFromAlgo extends EntityDoc {
  private static final long serialVersionUID = 6722941798154545447L;

  @Column(name = "discharge_plan_xid")
  private Long dischargePlanId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "voyage_xid")
  private Long voyageId;

  @Column(name = "cow_type_xid")
  private Long cowType;

  @Column(name = "is_active")
  private Boolean isActive = true;

  @Column(name = "tank_xid")
  private String tankIds;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "quantity_cargo_details_xid")
  private DischargePatternQuantityCargoPortwiseDetails dischargePatternQuantityCargoPortwiseDetails;
}
