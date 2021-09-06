/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "port_loading_plan_rob_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortLoadingPlanRobDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loading_information_xid")
  private Long loadingInformation;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "port_rotation_xid")
  private Long portRotationXId;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "quantity_mt")
  private BigDecimal quantity;

  @Column(name = "quantity_m3")
  private BigDecimal quantityM3;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "arrival_departutre")
  private Integer conditionType;

  @Column(name = "actual_planned")
  private Integer valueType;

  @Column(name = "density")
  private BigDecimal density;

  @Column(name = "colour_code")
  private String colorCode;
}
