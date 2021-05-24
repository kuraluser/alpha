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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargo_topping_off_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

  @Column(name = "volume")
  private BigDecimal volume;

  @Column(name = "weight")
  private BigDecimal weight;

  @Column(name = "filling_ratio")
  private BigDecimal fillingRatio;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "is_active")
  private Boolean isActive;
}
