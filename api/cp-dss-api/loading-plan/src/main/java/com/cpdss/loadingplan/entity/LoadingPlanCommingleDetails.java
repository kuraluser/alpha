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

/** @author pranav.k */
@Entity
@Table(name = "loading_plan_commingle_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingPlanCommingleDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(
      name = "loading_plan_portwise_details_xid",
      referencedColumnName = "id",
      nullable = true)
  private LoadingPlanPortWiseDetails loadingPlanPortWiseDetails;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "cargo_nomination1_xid")
  private Long cargoNomination1XId;

  @Column(name = "cargo_nomination2_xid")
  private Long cargoNomination2XId;

  @Column(name = "cargo1_xid")
  private Long cargo1XId;

  @Column(name = "cargo2_xid")
  private Long cargo2XId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "ullage")
  private BigDecimal ullage;

  @Column(name = "quantity_m3")
  private BigDecimal quantityM3;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "quantity1_mt")
  private BigDecimal quantity1MT;

  @Column(name = "quantity2_mt")
  private BigDecimal quantity2MT;

  @Column(name = "quantity1_m3")
  private BigDecimal quantity1M3;

  @Column(name = "quantity2_m3")
  private BigDecimal quantity2M3;

  @Column(name = "ullage_1")
  private BigDecimal ullage1;

  @Column(name = "ullage_2")
  private BigDecimal ullage2;
}
