/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

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

/**
 * Voyage history entity
 *
 * @author suhail.k
 */
@Entity
@Table(name = "voyage_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyageHistory extends EntityDoc {

  @Column(name = "vessel_xid")
  private Long vesselId;

  @ManyToOne
  @JoinColumn(name = "voyage_xid")
  private Voyage voyage;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "loading_port_xid")
  private Long loadingPortId;

  @Column(name = "port_order")
  private Integer portOrder;

  @Column(name = "is_active")
  private Boolean isActive;
}
