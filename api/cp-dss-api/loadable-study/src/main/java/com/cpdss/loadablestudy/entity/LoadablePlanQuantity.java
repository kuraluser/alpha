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
@Table(name = "loadableplanquantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "cargonominationxid")
  private Integer cargoNominationXId;

  @Column(name = "estimatedapi")
  private BigDecimal estimatedApi;

  @Column(name = "estimaltedtemperature")
  private BigDecimal estimaltedTemperature;

  @Column(name = "orderquantity")
  private BigDecimal orderQuantity;

  @Column(name = "loadablequantity")
  private BigDecimal loadableQuantity;

  @Column(name = "differencepercentage")
  private BigDecimal differencePercentage;

  @Column(name = "isactive")
  private Boolean isActive;

  @JoinColumn(name = "loadableplanxid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePlan loadablePlanXId;
}
