/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

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

/** Entity for Cargo Nomination Port Details */
@Entity
@Table(name = "cargonominationoperationdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoNominationPortDetails extends EntityDoc {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cargonominationxid")
  private CargoNomination cargoNomination;

  @Column(name = "portxid")
  private Long portId;

  @Column(name = "quantity")
  private BigDecimal quantity;
}
