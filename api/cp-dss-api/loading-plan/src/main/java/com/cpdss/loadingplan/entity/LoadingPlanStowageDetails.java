/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
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
@Table(name = "loading_plan_stowage_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingPlanStowageDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(
      name = "loading_plan_portwise_details_xid",
      referencedColumnName = "id",
      nullable = true)
  private LoadingPlanPortWiseDetails loadingPlanPortWiseDetails;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "quantity")
  private String quantity;

  @Column(name = "api")
  private String api;

  @Column(name = "temperature")
  private String temperature;

  @Column(name = "is_active")
  private Boolean isActive;
}
