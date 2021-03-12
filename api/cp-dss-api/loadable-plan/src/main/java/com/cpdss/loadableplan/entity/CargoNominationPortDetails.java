/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.entity;

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

/** Entity for Cargo Nomination Port Details */
@Entity
@Table(name = "cargonominationoperationdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoNominationPortDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "cargonominationxid")
  private CargoNomination cargoNomination;

  @Column(name = "portxid")
  private Long portId;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "quantity")
  private BigDecimal quantity;
}
