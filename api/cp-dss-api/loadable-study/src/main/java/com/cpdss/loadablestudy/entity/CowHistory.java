/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JohnSooraj
 * @since 21-09-21
 */
@Entity
@Table(name = "cow_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CowHistory extends EntityDoc {
  @Column(name = "vessel_xid")
  private Long vesselId;

  @Column(name = "voyage_xid")
  private Long voyageId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "cow_type_xid")
  private Long cowTypeId;

  @Column(name = "is_active")
  private Boolean isActive;
}
