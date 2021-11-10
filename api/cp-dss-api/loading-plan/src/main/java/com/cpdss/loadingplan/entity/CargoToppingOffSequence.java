/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cargo_topping_off_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CargoToppingOffSequence extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "order_number")
  private Integer orderNumber;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "cargo_xid")
  private Long cargoXId;

  @Column(name = "ullage")
  private BigDecimal ullage;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "filling_ratio")
  private BigDecimal fillingRatio;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "display_order")
  private Integer displayOrder;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationXId;

  @Column(name = "abbreviation")
  private String abbreviation;
}
