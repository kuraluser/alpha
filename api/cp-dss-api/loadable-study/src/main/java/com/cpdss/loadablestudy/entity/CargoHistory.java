/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cargo history entity
 *
 * @author suhail.k
 */
@Entity
@Table(name = "cargo_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoHistory extends EntityDoc {

  @Column(name = "vessel_xid")
  private Long vesselId;

  @ManyToOne
  @JoinColumn(name = "voyage_xid")
  private Voyage voyage;

  @Column(name = "tank_xid")
  private Long tankId;

  @ManyToOne
  @JoinColumn(name = "cargo_nomination_xid")
  private CargoNomination cargoNomination;

  @Column(name = "loading_port_xid")
  private Long loadingPortId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "discharge_port_xid")
  private Long dischargePort;
}
